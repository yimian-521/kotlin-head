// kotlin-head Node.js — 段位评估器 (Code Rank)
// 多维度量化代码质量 → 段位（青铜→王者）
// 基于 BugScanner + TypeChecker + Lexer + Parser 全管线

const { Lexer, TokType } = require('./lexer')
const { Parser } = require('./parser')
const { TypeChecker } = require('./checker')
const { BugScanner } = require('./bugscan')

const TIERS = [
  { name:'免免 MianMian', min:90, emoji:'🌸' },
  { name:'钻石 Diamond',  min:80, emoji:'💎' },
  { name:'铂金 Platinum', min:70, emoji:'🔷' },
  { name:'黄金 Gold',     min:60, emoji:'🥇' },
  { name:'白银 Silver',   min:45, emoji:'🥈' },
  { name:'青铜 Bronze',   min:0,  emoji:'🥉' },
]

// ── 维度权重（望安标准："一眼看完、抗摔、可删除、不恨自己"）──
const W = {
  bugDensity:    20,  // 零惊喜——代码做它看起来做的事
  robustness:    15,  // 抗摔——错误被处理，异常不裸奔（try/catch/err check）
  naming:        10,  // 一眼看完——命名准确，单字母滚蛋
  complexity:    10,  // 一眼看完——嵌套不超5层
  magicNumbers:  10,  // 不被未来自己恨——没有魔法数字
  consistency:   10,  // 一眼看完——风格统一，不混驼峰和下划线
  modularity:     5,  // 可删除——结构清晰，类/函数密度适中
  readability:    5,  // 一眼看完——空行+注释行比例（呼吸感）
  density:        5,  // 辅助
  funcLength:     5,  // 辅助
  comment:        5,  // 不被未来自己恨——解释"为什么"
}

function rank(src, opts = {}) {
  // opts.forceText → 跳过Kotlin全管线，直接纯文本模式
  if (opts.forceText) return _rankText(src)
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

  // 解析失败 → JS/纯文本模式
  if (!parseOk || !ast) return _rankText(src)

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
    raw: { lines, tokens:tokenCount, bugs:bugs.length, diags:checkDiags.length, parseOk, maxDepth, maxFnLen, mode: parseOk ? 'kotlin' : 'text' },
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
    const bar = '█'.repeat(Math.max(0, Math.round(d.score / 5))) + '░'.repeat(Math.max(0, 20 - Math.round(d.score / 5)))
    out += `${k.padEnd(8)} [${d.weight}%] ${bar} ${d.score}分  ${d.detail}\n`
  }
  out += '─'.repeat(50) + '\n'
  if (r.note) out += `📌 ${r.note}\n`
  if (!r.raw.parseOk) out += '⚠️  语法解析失败，部分维度不可用\n'
  return out
}

// ── JS/通用纯文本模式（Kotlin Parser失败时回退）──
function _rankText(src) {
  const lines = src.split('\n')
  const lineCount = lines.length
  const chars = src.length

  // 动态分类，计算调整后权重
  const fileType = _classify(src, lineCount)
  const TW = { ...W }
  const offsets = TYPE_W[fileType] || {}
  for (const k of Object.keys(offsets)) TW[k] = (TW[k] || 0) + offsets[k]
  // 归一化到100
  const twSum = Object.values(TW).reduce((a,b)=>a+b,0)
  for (const k of Object.keys(TW)) TW[k] = Math.round(TW[k] * 100 / twSum)

  // 启用校准时，以标杆特征为满分线
  const ideal = CAL || null

  // 注释
  const commentLines = lines.filter(l => /^\s*\/\//.test(l) || /^\s*\/\*/.test(l) || /^\s*\*/.test(l) || /^\s*\*\//.test(l)).length
  const commentRatio = lineCount > 0 ? commentLines / lineCount : 0
  const commentScore = ideal
    ? Math.min(100, (commentLines / lineCount) / (ideal.commentPct / 100) * 100)
    : Math.min(100, commentRatio * 400)

  // 命名：单字母变量比例
  const varMatches = src.match(/\b(const|let|var|val|fun|function|class)\s+([a-zA-Z_$]+)/g) || []
  const singleLetter = varMatches.filter(m => { const n = m.split(/\s+/)[1]; return n && n.length === 1 && n !== '_' }).length
  const namingRatio = varMatches.length > 0 ? singleLetter / varMatches.length : 0
  const namingIdeal = ideal ? ideal.singleLetterPct / 100 : 0
  const namingScore = ideal
    ? Math.max(0, 100 - Math.max(0, namingRatio - namingIdeal) * 300)
    : Math.max(0, 100 - namingRatio * 200)

  // 陷阱检测
  const looseEqual = (src.match(/[^=!<>]==[^=]/g) || []).length
  const varDecl = (src.match(/\bvar\s+/g) || []).length
  const evilEval = (src.match(/\beval\(/g) || []).length
  const consoleLog = (src.match(/\bconsole\.log\(/g) || []).length
  const dangerPatterns = looseEqual + varDecl + evilEval * 5 + consoleLog
  const dangerRatio = lineCount > 0 ? dangerPatterns / lineCount : 0
  const bugScore = Math.max(0, 100 - dangerRatio * 300)

  // 复杂度：最大花括号嵌套深度
  let maxDepth = 0, curDepth = 0
  for (const ch of src) {
    if (ch === '{') { curDepth++; if (curDepth > maxDepth) maxDepth = curDepth }
    if (ch === '}') curDepth--
  }
  const depthScore = ideal
    ? Math.max(0, 100 - Math.max(0, maxDepth - ideal.maxDepth) * 15)
    : lineCount > 500 ? Math.max(0, 100 - Math.max(0, maxDepth - 8) * 10)  // 大文件允许嵌套8层
    : Math.max(0, 100 - Math.max(0, maxDepth - 4) * 15)

  // 密度
  const charsPerLine = lineCount > 0 ? chars / lineCount : 0
  const densityScore = charsPerLine > 20 && charsPerLine < 100 ? 100
    : charsPerLine <= 20 ? Math.max(0, 100 - (20 - charsPerLine) * 5)
    : Math.max(0, 100 - (charsPerLine - 100) * 3)

  // 函数长度
  let maxFnLen = 0
  const fnRegex = /(?:function\s+\w+|=>)\s*\{/g
  let m
  while ((m = fnRegex.exec(src)) !== null) {
    let braceCount = 1, i = m.index + m[0].length
    let endLine = lines.length
    for (; i < src.length && braceCount > 0; i++) {
      if (src[i] === '{') braceCount++
      if (src[i] === '}') { braceCount--; if (braceCount === 0) endLine = src.substring(0, i).split('\n').length }
    }
    const len = endLine - src.substring(0, m.index).split('\n').length + 1
    if (len > maxFnLen) maxFnLen = len
  }
  const fnLenScore = Math.max(0, 100 - Math.max(0, maxFnLen - 30) * 2)

  // 新增：鲁棒性——错误处理覆盖率
  const tryCount = (src.match(/\btry\b/g) || []).length + (src.match(/\bcatch\b/g) || []).length
    + (src.match(/\bif\s+err\b/g) || []).length + (src.match(/\.catch\(/g) || []).length
    + (src.match(/\brecover\(\)/g) || []).length + (src.match(/\bdefer\b/g) || []).length
  const robustRatio = lineCount > 0 ? tryCount / (lineCount / 50) : 0  // 每50行至少1次错误处理
  const robustScore = Math.min(100, robustRatio * 100)

  // 新增：魔法数字——排除0,1,-1,2 的孤立数字
  const magicNums = (src.match(/(?<!\w)(?!0\b|1\b|-1\b|2\b)\d+(?!\w)/g) || []).length
  const magicRatio = lineCount > 0 ? magicNums / lineCount : 0
  // 新:大文件允许更多常量数字
  const magicScore = lineCount > 500
    ? Math.max(0, 100 - magicRatio * 500)  // 大文件：宽松
    : Math.max(0, 100 - magicRatio * 1000)

  // 新增：一致性——驼峰+下划线混合使用（主风格>90%不罚）
  const camelOnly = (src.match(/[a-z][A-Z]/g) || []).length
  const snakeOnly = (src.match(/[a-z]_[a-z]/g) || []).length
  const totalStyles = camelOnly + snakeOnly
  const mixed = totalStyles > 0 && camelOnly / totalStyles < 0.9 && snakeOnly / totalStyles < 0.9 ? 1 : 0
  const consistScore = mixed ? 50 : 100

  // 新增：模块化——类/函数/结构体声明密度（小文件宽松）
  const structCount = (src.match(/\b(class|struct|interface|object|enum)\s+\w+/g) || []).length
    + (src.match(/\b(func|fun|function|def)\s+\w+/g) || []).length
    + (src.match(/\b(const|let|var|val)\s+\w+\s*=\s*(function|\(.*\)\s*=>)/g) || []).length
  const modRatio = lineCount > 0 ? structCount / (lineCount / 30) : 0  // 每30行一个结构体
  // 小文件(<100行)不罚高密度，大文件不罚低密度
  const modScore = lineCount < 100 
    ? Math.min(100, modRatio * 60)  // 小文件：密集就密集
    : modRatio > 0.5 && modRatio < 3 ? 100 : Math.max(0, 100 - Math.abs(modRatio - 1) * 50)

  // 新增：可读性——空行+注释行呼吸感（小文件宽松）
  const blankLines = lines.filter(l => /^\s*$/.test(l)).length
  const breathLines = blankLines + commentLines
  const breathRatio = lineCount > 0 ? breathLines / lineCount : 0
  // 小文件(100行)允许高呼吸(常量/配置类天然多注释)
  const readScore = lineCount < 100
    ? (breathRatio < 0.7 ? 100 : Math.max(0, 100 - (breathRatio - 0.7) * 300))
    : breathRatio > 0.1 && breathRatio < 0.5 ? 100 : Math.max(0, 100 - Math.abs(breathRatio - 0.25) * 200)

  const total =
    bugScore * TW.bugDensity / 100 +
    robustScore * TW.robustness / 100 +
    namingScore * TW.naming / 100 +
    depthScore * TW.complexity / 100 +
    magicScore * TW.magicNumbers / 100 +
    consistScore * TW.consistency / 100 +
    modScore * TW.modularity / 100 +
    readScore * TW.readability / 100 +
    densityScore * TW.density / 100 +
    fnLenScore * TW.funcLength / 100 +
    commentScore * TW.comment / 100

  const tier = TIERS.find(t => total >= t.min) || TIERS[TIERS.length - 1]

  return {
    total: Math.round(total),
    tier: tier.name,
    emoji: tier.emoji,
    calibrated: !!ideal,
    dimensions: {
      bug:      { score:Math.round(bugScore),    weight:TW.bugDensity,  detail:`${dangerPatterns}陷阱/${lineCount}行` },
      robust:   { score:Math.round(robustScore),  weight:TW.robustness,  detail:`${tryCount}错误处理(@50行)` },
      naming:   { score:Math.round(namingScore),  weight:TW.naming,      detail:`${singleLetter}单字母/${varMatches.length}标识符` },
      depth:    { score:Math.round(depthScore),   weight:TW.complexity,   detail:`嵌套${maxDepth}${ideal?'(标杆'+ideal.maxDepth+')':''}` },
      magic:    { score:Math.round(magicScore),   weight:TW.magicNumbers, detail:`${magicNums}个魔法数字` },
      consist:  { score:Math.round(consistScore), weight:TW.consistency,  detail:mixed?'驼峰+下划线混用':'风格统一' },
      modular:  { score:Math.round(modScore),     weight:TW.modularity,   detail:`${structCount}个结构体(${modRatio.toFixed(1)}/30行)` },
      breath:   { score:Math.round(readScore),   weight:TW.readability,   detail:`${breathLines}空/注行(${Math.round(breathRatio*100)}%)` },
      density:  { score:Math.round(densityScore), weight:TW.density,      detail:`${charsPerLine.toFixed(1)}字符/行` },
      fnLen:    { score:Math.round(fnLenScore),   weight:TW.funcLength,   detail:`最长函数~${maxFnLen}行` },
      comment:  { score:Math.round(commentScore), weight:TW.comment,      detail:`${Math.round(commentRatio*100)}%注释${ideal?'(标杆'+ideal.commentPct+'%)':''}` },
    },
    raw: { lines:lineCount, tokens:0, bugs:dangerPatterns, diags:0, parseOk:false, maxDepth, maxFnLen, mode:'js/text', calibrated:!!ideal, fileType },
    note: fileType !== 'general' ? `动态标准: ${fileType}` : null,
  }
}

// ── 动态分类：根据文件特征自动推断类型，切换评分标准 ──
function _classify(src, lineCount) {
  const hasTest = /\b(test|Test|spec|Spec)\b/.test(src) || /\bassert\b/.test(src) || /\bexpect\b/.test(src)
  const hasUI = /\b(Button|UI|View|Widget|Dialog|Panel|Screen|render|display)\b/.test(src)
  const hasCore = /\b(lexer|parser|compil|checker|scanner|ast|token|ir|pass|transform|optimize|resolve)\b/i.test(src)
  const isScript = lineCount < 200 && !/\bclass\s+\w+/.test(src)
  const isConfig = lineCount < 100 && (src.split('\n').filter(l=>/^\s*\/\//.test(l)).length/lineCount > 0.3)
  const isLarge = lineCount > 800

  if (hasCore) return 'core'
  if (isLarge) return 'large'
  if (hasTest) return 'test'
  if (hasUI) return 'ui'
  if (isConfig) return 'config'
  if (isScript) return 'script'
  if (isLarge) return 'large'
  return 'general'
}

// 各类型权重偏移（叠加到基础W）
const TYPE_W = {
  core:   { robustness:+5, complexity:-5, comment:-5, magicNumbers:-5 }, // core在乎抗摔，宽松嵌套/注释/魔法数
  large:  { complexity:-5, magicNumbers:-5, comment:-3, robustness:+8 }, // 大文件：深度合理，鲁棒性关键
  test:   { bugDensity:-5, magicNumbers:-5, naming:-5, robustness:+15 },// 测试：重错误覆盖，轻bug/命名
  ui:     { complexity:-3, magicNumbers:-5, readability:+5, comment:+5 },// UI：重可读性，宽松深度
  config: { complexity:-5, magicNumbers:-5, modularity:-5, readability:+10 },// 配置：重可读性
  script: { modularity:-5, comment:-5, naming:-3, density:+3 },
  general: {},
}
function calibrate(sampleSrc) {
  const lines = sampleSrc.split('\n').length
  const chars = sampleSrc.length
  const commentLines = sampleSrc.split('\n').filter(l => /^\s*\/\//.test(l) || /^\s*\/\*/.test(l) || /^\s*\*/.test(l) || /^\s*\*\//.test(l)).length
  let maxDepth = 0, curDepth = 0
  for (const ch of sampleSrc) { if (ch === '{') { curDepth++; if (curDepth > maxDepth) maxDepth = curDepth } if (ch === '}') curDepth-- }
  const varMatches = sampleSrc.match(/\b(const|let|var|val|fun|function|class)\s+([a-zA-Z_$]+)/g) || []
  const singleLetter = varMatches.filter(m => { const n = m.split(/\s+/)[1]; return n && n.length === 1 && n !== '_' }).length

  return CAL = {
    commentPct: Math.round(commentLines / lines * 100),
    maxDepth,
    singleLetterPct: Math.round(singleLetter / Math.max(1, varMatches.length) * 100),
    lines,
    charsPerLine: Math.round(chars / lines),
    idealBugs: 0,
  }
}
let CAL = null  // calibrate()后生效，_rankText中引用

module.exports = { rank, format, TIERS, W, calibrate, get CAL() { return CAL } }