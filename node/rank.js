// kotlin-head Node.js — 段位评估器 (Code Rank)
// 多维度量化代码质量 → 段位（青铜→王者）
// 基于 BugScanner + TypeChecker + Lexer + Parser 全管线

const { Lexer, TokType } = require('./lexer')
const { Parser } = require('./parser')
const { TypeChecker } = require('./checker')
const { BugScanner } = require('./bugscan')

const TIERS = [
  { name:'王者 Grandmaster', min:90, emoji:'👑' },
  { name:'钻石 Diamond',    min:80, emoji:'💎' },
  { name:'铂金 Platinum',   min:70, emoji:'🔷' },
  { name:'黄金 Gold',       min:60, emoji:'🥇' },
  { name:'白银 Silver',     min:45, emoji:'🥈' },
  { name:'青铜 Bronze',     min:0,  emoji:'🥉' },
]

// 维度权重（免免可调）
const W = {
  bugDensity:   25,  // bug 越少分越高
  typeSafety:   20,  // 类型检查通过率
  naming:       15,  // 命名质量
  complexity:   15,  // 结构不过深
  density:      10,  // 代码密度适中
  funcLength:   10,  // 函数不过长
  comment:       5,  // 注释覆盖
}

function rank(src) {
  // ── 1. 基础分析 ──
  const lines = src.split('\n').length
  const chars = src.length
  const commentLines = src.split('\n').filter(l => /^\s*\/\//.test(l) || /^\s*\/\*/.test(l) || /^\s*\*/.test(l)).length

  const lexer = new Lexer(src)
  const tokens = lexer.tokenize()
  const tokenCount = tokens.length

  const parser = new Parser(tokens)
  let ast = null, parseOk = true
  try { ast = parser.parseFile() } catch(e) { parseOk = false }

  const checker = new TypeChecker()
  let checkDiags = []
  try { if (ast) checkDiags = checker.check(ast) } catch(e) {}

  const bugscan = new BugScanner()
  let bugs = []
  try { if (ast) bugs = bugscan.scan(ast) } catch(e) {}

  // ── 2. 各维度评分（0-100） ──

  // 2a. Bug 密度：bug/行数，0 bugs = 100分，每 0.01 bug/行扣 10 分
  const bugRatio = lines > 0 ? bugs.length / lines : 0
  const bugScore = Math.max(0, 100 - bugRatio * 1000)

  // 2b. 类型安全：类型诊断数越少越好
  const typeRatio = lines > 0 ? checkDiags.length / lines : 0
  const typeScore = Math.max(0, 100 - typeRatio * 500)

  // 2c. 命名质量：单字母变量比例
  const idents = tokens.filter(t => t.type === TokType.IDENT)
  const singleLetter = idents.filter(t => t.text.length === 1 && t.text !== '_').length
  const namingRatio = idents.length > 0 ? singleLetter / idents.length : 0
  const namingScore = Math.max(0, 100 - namingRatio * 200)

  // 2d. 结构复杂度：AST 深度
  const maxDepth = ast ? _astDepth(ast) : 0
  const depthScore = Math.max(0, 100 - (maxDepth - 5) * 10)

  // 2e. 代码密度：每行 token 数
  const tokPerLine = lines > 0 ? tokenCount / lines : 0
  const densityScore = tokPerLine > 2 && tokPerLine < 15 ? 100 
    : tokPerLine <= 2 ? Math.max(0, 100 - (2 - tokPerLine) * 30)
    : Math.max(0, 100 - (tokPerLine - 15) * 8)

  // 2f. 函数长度：最长函数
  const maxFnLen = ast ? _maxFnLen(ast) : 0
  const fnLenScore = Math.max(0, 100 - Math.max(0, maxFnLen - 20) * 2)

  // 2g. 注释
  const commentRatio = lines > 0 ? commentLines / lines : 0
  const commentScore = Math.min(100, commentRatio * 400)

  // ── 3. 加权总分 ──
  const total = 
    bugScore * W.bugDensity / 100 +
    typeScore * W.typeSafety / 100 +
    namingScore * W.naming / 100 +
    depthScore * W.complexity / 100 +
    densityScore * W.density / 100 +
    fnLenScore * W.funcLength / 100 +
    commentScore * W.comment / 100

  const tier = TIERS.find(t => total >= t.min) || TIERS[TIERS.length - 1]

  return {
    total: Math.round(total),
    tier: tier.name,
    emoji: tier.emoji,
    dimensions: {
      bug:       { score:Math.round(bugScore),   weight:W.bugDensity,  detail:`${bugs.length} bugs / ${lines}行` },
      type:      { score:Math.round(typeScore),   weight:W.typeSafety,  detail:`${checkDiags.length} 类型诊断` },
      naming:    { score:Math.round(namingScore), weight:W.naming,      detail:`${singleLetter}个单字母/${idents.length}标识符` },
      depth:     { score:Math.round(depthScore),  weight:W.complexity,  detail:`最大AST深度 ${maxDepth}` },
      density:   { score:Math.round(densityScore),weight:W.density,     detail:`${tokPerLine.toFixed(1)} token/行` },
      fnLen:     { score:Math.round(fnLenScore),  weight:W.funcLength,  detail:`最长函数 ${maxFnLen} 行` },
      comment:   { score:Math.round(commentScore),weight:W.comment,     detail:`${Math.round(commentRatio*100)}% 注释` },
    },
    raw: { lines, tokens:tokenCount, bugs:bugs.length, diags:checkDiags.length, parseOk, maxDepth, maxFnLen },
  }
}

// AST 最大深度
function _astDepth(node, d=0) {
  if (!node || typeof node !== 'object') return d
  let max = d
  for (const k of Object.keys(node)) {
    const v = node[k]
    if (Array.isArray(v)) { for (const x of v) max = Math.max(max, _astDepth(x, d+1)) }
    else if (v && typeof v === 'object' && v.type) max = Math.max(max, _astDepth(v, d+1))
  }
  return max
}

// 最长函数行数
function _maxFnLen(node) {
  if (!node) return 0
  let max = 0
  if (node.type === 'Fun' && node.span) {
    const len = (node.span.end?.line || 0) - (node.span.start?.line || 0) + 1
    if (len > max) max = len
  }
  for (const k of Object.keys(node)) {
    const v = node[k]
    if (Array.isArray(v)) for (const x of v) max = Math.max(max, _maxFnLen(x))
    else if (v && typeof v === 'object' && v.type) max = Math.max(max, _maxFnLen(v))
  }
  return max
}

// 格式化输出
function format(r) {
  let out = `\n${r.emoji} 段位: ${r.tier} (${r.total}分)\n`
  out += '─'.repeat(50) + '\n'
  for (const [k, d] of Object.entries(r.dimensions)) {
    const bar = '█'.repeat(Math.round(d.score / 5)) + '░'.repeat(20 - Math.round(d.score / 5))
    out += `${k.padEnd(8)} [${d.weight}%] ${bar} ${d.score}分  ${d.detail}\n`
  }
  out += '─'.repeat(50) + '\n'
  if (!r.raw.parseOk) out += '⚠️  语法解析失败，部分维度不可用\n'
  return out
}

module.exports = { rank, format, TIERS, W }