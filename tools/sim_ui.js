#!/usr/bin/env node
// kotlin-head --sim-ui 独立投影验证器 v0.1.0
// 轻量正则扫描 Compose 源码 → 列出按钮 + 追踪调用链 + 投影压测

const fs = require('fs');
const path = require('path');

const args = process.argv.slice(2);
const mode = args[0] || '--help';
const targetPath = (mode === '--bench' || mode === '-h' || mode === '--help') ? '' :
  (args.length >= 3 && !args[args.length-1].startsWith('--') && args[args.length-1].endsWith('.kt')) ? args[args.length-1] :
  (args[1] && !args[1].startsWith('--') && args[1].endsWith('.kt')) ? args[1] : '';

// === 持久化命名库 ===
const NAMES_FILE = (process.env.HOME || '/sdcard') + '/.kotlin-head/ui-names.json';
function loadNames() { try { return JSON.parse(fs.readFileSync(NAMES_FILE, 'utf8')); } catch (_) { return {}; } }
function saveNames(names) { fs.writeFileSync(NAMES_FILE, JSON.stringify(names, null, 2)); }

if (mode === '--help' || mode === '-h') {
  console.log('kotlin-head --sim-ui <源码.kt> [选项]');
  console.log('  --list          列出页面所有可点击按钮（区域+行号+追溯链）');
  console.log('  --click <编号>  模拟点击按钮，追踪调用链+BugScanner诊断');
  console.log('  --bench         投影机制压测（需配 --vars 和 --ui-count）');
  console.log('  --name  <编号> "<名字>"  命名按钮');
  console.log('  --name  <编号> ""        清空命名（回退到contentDescription）');
  console.log('  --name  <编号> "/"       强制清空（跳过contentDescription）');
  process.exit(0);
}

if (mode !== '--bench' && mode !== '--help' && mode !== '-h') {
  if (!targetPath || !fs.existsSync(targetPath)) {
    console.error('✖ 文件不存在: ' + targetPath);
    process.exit(1);
  }
}

const src = targetPath ? fs.readFileSync(targetPath, 'utf8') : '';
const fileName = targetPath ? path.basename(targetPath) : 'bench';
const names = loadNames();

// === 扫描：找页面（@Composable fun）===
const composableRe = /@Composable\s*\n\s*fun\s+(\w+)\s*\(/g;
const pages = [];
let m;
while ((m = composableRe.exec(src)) !== null) {
  pages.push({ name: m[1], pos: src.indexOf('fun ' + m[1], m.index) });
}

// === 扫描：找可点击按钮（onClick / clickable / IconButton）===
const buttons = [];

// 模式1: IconButton(onClick = callback)
const iconBtnRe = /IconButton\s*\(\s*onClick\s*=\s*(\w+)/g;
while ((m = iconBtnRe.exec(src)) !== null) {
  const callback = m[1];
  const lineNum = src.substring(0, m.index).split('\n').length;
  // 找contentDescription
  const nearby = src.substring(m.index, m.index + 200);
  const cdMatch = nearby.match(/contentDescription\s*=\s*"([^"]*)"/);
  const cdStr = cdMatch ? (cdMatch[1] === 'null' ? null : cdMatch[1]) : null;
  buttons.push({ line: lineNum, callback, contentDesc: cdStr, region: detectRegion(src, m.index) });
}

// 模式2: .clickable(onClick = ...)
const clickableRe = /\.clickable\s*\(\s*onClick\s*=\s*(\w+)/g;
while ((m = clickableRe.exec(src)) !== null) {
  const callback = m[1];
  const lineNum = src.substring(0, m.index).split('\n').length;
  buttons.push({ line: lineNum, callback, contentDesc: null, region: detectRegion(src, m.index) });
}

// 模式3: onClick = { callback(...) }
const onClkRe = /onClick\s*=\s*\{\s*(\w+)\s*\(/g;
while ((m = onClkRe.exec(src)) !== null) {
  const callback = m[1];
  const lineNum = src.substring(0, m.index).split('\n').length;
  buttons.push({ line: lineNum, callback, contentDesc: null, region: detectRegion(src, m.index) });
}

// 模式4: DropdownMenuItem(onClick = { callback(...) })
const ddItemRe = /DropdownMenuItem\s*\([\s\S]*?onClick\s*=\s*\{[^}]*?(\w+)\s*\(/g;
while ((m = ddItemRe.exec(src)) !== null) {
  const callback = m[1];
  const lineNum = src.substring(0, m.index).split('\n').length;
  buttons.push({ line: lineNum, callback, contentDesc: null, region: detectRegion(src, m.index) });
}

// 模式5: HED终端按钮 hPrintln(" [数字] 描述")
const hedBtnRe = /hPrintln\s*\(\s*"\s*\[(\d+)\]\s*([^"]+)"/g;
while ((m = hedBtnRe.exec(src)) !== null) {
  const num = m[1];
  const desc = m[2].replace(/\$\{[^}]*\}/g, '').trim(); // 去掉${}插值
  const lineNum = src.substring(0, m.index).split('\n').length;
  buttons.push({ line: lineNum, callback: 'HED:' + num, contentDesc: desc, region: 'HED终端' });
}

// 模式6: 传统View按钮 setOnClickListener / android:onClick
const viewBtnRe = /\.setOnClickListener\s*\{[^}]*?(\w+)\s*\(/g;
while ((m = viewBtnRe.exec(src)) !== null) {
  const callback = m[1];
  const lineNum = src.substring(0, m.index).split('\n').length;
  buttons.push({ line: lineNum, callback, contentDesc: null, region: detectRegion(src, m.index) });
}
const xmlClickRe = /android:onClick\s*=\s*"(\w+)"/g;
while ((m = xmlClickRe.exec(src)) !== null) {
  const callback = m[1];
  const lineNum = src.substring(0, m.index).split('\n').length;
  buttons.push({ line: lineNum, callback, contentDesc: null, region: 'XML布局' });
}

// 去重（同行同回调合并）
const seen = new Set();
const uniqueButtons = buttons.filter(b => {
  const key = b.line + ':' + b.callback;
  if (seen.has(key)) return false;
  seen.add(key);
  return true;
}).sort((a, b) => a.line - b.line);

// === 区域检测 ===
function detectRegion(src, pos) {
  const before = src.substring(0, pos);
  const lines = before.split('\n');
  // 倒查最近的结构组件
  for (let i = lines.length - 1; i >= 0; i--) {
    const l = lines[i].trim();
    if (l.includes('TopAppBar')) return '顶栏';
    if (l.includes('DropdownMenu')) return '下拉菜单';
    if (l.includes('FloatingActionButton')) return '悬浮按钮';
    if (l.includes('LazyColumn')) return '卡片列表';
    if (l.includes('Card(')) return '卡片项';
    if (l.includes('Scaffold')) return '页面根';
    if (l.includes('BottomBar')) return '底栏';
  }
  return '未知区域';
}

// === 追溯链：追onClick → 实际功能函数 ===
function traceCallback(cbName) {
  // 找 fun cbName(...) 定义，看它调了什么
  const funDefRe = new RegExp('fun\\s+' + cbName + '\\s*\\([^)]*\\)', 'g');
  const chain = [];
  let defMatch;
  while ((defMatch = funDefRe.exec(src)) !== null) {
    // 在函数体内找调用
    const bodyStart = defMatch.index + defMatch[0].length;
    const bodyEnd = src.indexOf('\n    }', bodyStart);
    if (bodyEnd === -1) continue;
    const body = src.substring(bodyStart, bodyEnd);
    // 找所有函数调用
    const callRe = /(\w+)\s*\(/g;
    let callMatch;
    while ((callMatch = callRe.exec(body)) !== null) {
      const called = callMatch[1];
      if (called !== cbName && !['if', 'when', 'for', 'while', 'return'].includes(called) && called[0] === called[0].toLowerCase()) {
        chain.push(called);
      }
    }
  }
  return [...new Set(chain)]; // 去重
}

// === 命名优先级 ===
function resolveName(btn, idx) {
  const key = fileName + ':callback:' + btn.callback;
  // 1. 开发者手动命名
  if (names[key] === '/') return '待命名';
  if (names[key] && names[key] !== '') return names[key];
  if (names[key] === '') { /* 跳过手动清空，走2 */ }
  // 2. contentDescription
  if (btn.contentDesc) return btn.contentDesc;
  // 3. 追溯链推断
  const chain = traceCallback(btn.callback);
  if (chain.length > 0) return camelToChinese(chain[0]);
  // 4. 兜底
  return '待命名';
}

function camelToChinese(s) {
  const dict = {
    share: '分享', delete: '删除', edit: '编辑', add: '添加', save: '保存',
    cancel: '取消', confirm: '确认', send: '发送', refresh: '刷新', copy: '复制',
    paste: '粘贴', undo: '撤销', redo: '重做', show: '显示', hide: '隐藏',
    web: '网页', page: '页面', card: '名片', qq: 'QQ', config: '配置'
  };
  let result = s;
  for (const [en, cn] of Object.entries(dict)) {
    result = result.replace(new RegExp(en, 'gi'), cn);
  }
  // 转小写首字母大写 → 更可读
  if (result === s) {
    result = s.replace(/([A-Z])/g, ' $1').trim();
    result = result.charAt(0).toUpperCase() + result.slice(1);
  }
  return result;
}

// ==================================================================
// 命令处理
// ==================================================================

if (mode === '--list') {
  const pageName = pages.length > 0 ? pages[0].name : fileName;
  console.log('📁 ' + fileName + ' · ' + pageName + '\n');
  
  uniqueButtons.forEach((btn, idx) => {
    const name = resolveName(btn, idx);
    const sourceTag = (names[fileName + ':callback:' + btn.callback]) ? '👤手动' :
      btn.contentDesc ? '🏷 源码' :
      (traceCallback(btn.callback).length > 0) ? '💡追溯' : '🏷 待命名';
    
    console.log('  [' + (idx + 1) + '] "' + name + '"   ← ' + btn.region + ' · 第' + btn.line + '行');
    console.log('      → ' + btn.callback + '()' + '                        ' + sourceTag);
    
    const chain = traceCallback(btn.callback);
    if (chain.length > 0) {
      const chainStr = chain.map(c => c + '()').join(' → ');
      console.log('      💡 追溯: ' + chainStr);
    }
    console.log();
  });
  
  console.log('  命名: --name <编号> "<名字>"   |  测试: --click <编号>   |  压测: --bench');
}

else if (mode === '--click') {
  const idx = parseInt(args[1] || '') - 1;
  if (isNaN(idx) || idx < 0 || idx >= uniqueButtons.length) {
    console.error('✖ 无效按钮编号。用 --list 查看可用按钮。');
    process.exit(1);
  }
  const btn = uniqueButtons[idx];
  const name = resolveName(btn, idx);
  const chain = traceCallback(btn.callback);
  
  console.log('=== 行为模拟：[' + (idx + 1) + '] "' + name + '" ===\n');
  console.log('  触发: ' + btn.callback + '()  (第' + btn.line + '行)\n');
  
  if (chain.length === 0) {
    console.log('  追溯链: 未找到下游调用（可能是直接状态修改或无参数回调）');
  } else {
    console.log('  调用链追踪:');
    console.log('    ' + btn.callback + '()');
    chain.forEach((c, i) => {
      const prefix = (i === chain.length - 1) ? '      └── ' : '      ├── ';
      console.log(prefix + c + '()');
    });
  }
  
  console.log('\n  === BugScanner 快速诊断 ===');
  
  // 检查三件套（正则扫描常见模式）
  const checks = [
    { pattern: /\brefreshConfig\b/, msg: '🔴 QQUtil.refreshConfig — 阻塞IO未挂suspend（主线程风险）' },
    { pattern: /\bshareToQQ\b/, msg: '🔴 tencent.shareToQQ — var引用可被撕裂（空安全假保障）' },
    { pattern: /\brunOnUiThread\b/, msg: '🟡 runOnUiThread — Activity可能已被回收（结构化并发留缝隙）' },
    { pattern: /SharedPreferences\b/, msg: '🟡 SharedPreferences — 磁盘I/O在主线（阻塞）' },
    { pattern: /\.getString\(/.source + '|' + /\.getInt\(/.source, msg: '🟡 .getString/.getInt — 同步磁盘读' },
  ];
  
  let found = 0;
  for (const chk of checks) {
    // 只在按钮相关的函数体内搜
    const funBody = findFunBody(btn.callback);
    if (funBody && new RegExp(chk.pattern).test(funBody)) {
      console.log('  ' + chk.msg);
      found++;
    }
  }
  if (found === 0) console.log('  ✅ 未发现已知风险模式');
  console.log('\n  建议: 用 --bench 跑投影压测验证UI层延迟');
}

else if (mode === '--bench') {
  const vars = (args[1] && !/^\d/.test(args[1]) ? args[1] : 'theme,fontSize,loading').split(',');
  const uiCount = parseInt(args[2] || '250');
  const pageCount = parseInt(args[3] || '1');
  
  runBenchmark(vars, uiCount, pageCount);
}

else if (mode === '--name') {
  const idx = parseInt(args[1] || '') - 1;
  const newName = args[2] || '';
  if (isNaN(idx) || idx < 0 || idx >= uniqueButtons.length) {
    console.error('✖ 无效按钮编号。用 --list 查看可用按钮。');
    process.exit(1);
  }
  const btn = uniqueButtons[idx];
  const key = fileName + ':callback:' + btn.callback;
  names[key] = newName;
  saveNames(names);
  
  const displayName = newName === '/' ? '待命名（强制跳过contentDescription）' :
    newName === '' ? '待命名（回退到源码contentDescription）' : '"' + newName + '"';
  console.log('✅ 按钮[' + (idx + 1) + '] → ' + displayName + ' | 已持久化');
}

else if (mode === '--params') {
  const idx = parseInt(args[1] || '') - 1;
  if (isNaN(idx) || idx < 0 || idx >= uniqueButtons.length) {
    console.error('✖ 无效按钮编号。用 --list 查看可用按钮。');
    process.exit(1);
  }
  const btn = uniqueButtons[idx];
  const name = resolveName(btn, idx);
  console.log('=== 参数追踪：[' + (idx + 1) + '] "' + name + '" ===\n');

  // 1. 在Composable参数表中找到这个回调的签名
  let cbSignature = null;
  const cbPattern = new RegExp(btn.callback + '\\s*:\\s*\\(([^)]*)\\)\\s*->\\s*(\\w+)', 'g');
  while ((m = cbPattern.exec(src)) !== null) {
    cbSignature = { params: m[1], returnType: m[2] };
  }
  // 2. 如果找不到精确匹配，找简化版（可能在lambda里直接调了onShareCard(card)）
  if (!cbSignature) {
    const simplePattern = new RegExp('\\{\\s*' + btn.callback + '\\s*\\(([^)]*)\\)', 'g');
    while ((m = simplePattern.exec(src)) !== null) {
      cbSignature = { params: m[1], returnType: 'Unit', isLambda: true };
    }
  }
  // 2b. lambda穿透：在按钮附近源码中找实际调用
  if (!cbSignature) {
    const btnPos = src.indexOf(btn.callback, src.indexOf('onClick', Math.max(0, (btn.line - 3) * 80)));
    if (btnPos > 0) {
      // 在按钮附近200字符内找所有函数调用
      const nearbyBlock = src.substring(Math.max(0, btnPos - 100), Math.min(src.length, btnPos + 400));
      const callPattern = /(\w+)\s*\(\s*(\w+)\s*\)/g;
      let cm;
      while ((cm = callPattern.exec(nearbyBlock)) !== null) {
        const maybeCb = cm[1];
        const maybeArg = cm[2];
        if (maybeCb !== btn.callback && maybeCb !== 'showMenu' && maybeCb[0] === maybeCb[0].toLowerCase()) {
          // 追这个maybeCb的类型签名
          const sigRe = new RegExp(maybeCb + '\\s*:\\s*\\(([^)]*)\\)\\s*->\\s*(\\w+)', 'g');
          let sm;
          while ((sm = sigRe.exec(src)) !== null) {
            cbSignature = { params: sm[1], returnType: sm[2], actualCall: maybeCb, actualArgs: maybeArg };
            break;
          }
          if (cbSignature) break;
        }
      }
    }
  }

  if (cbSignature) {
    console.log('  回调签名: ' + btn.callback + '(' + cbSignature.params + ')' + (cbSignature.isLambda ? ' ← lambda内联' : ''));
    console.log('  返回类型: ' + cbSignature.returnType + '\n');

    // 3. 提取参数名，找类型定义
    const paramParts = cbSignature.params.split(',').map(p => p.trim());
    const params = paramParts.map(p => {
      const parts = p.split(':').map(s => s.trim());
      return { name: parts[0], type: parts[1] || 'Any' };
    });

    params.forEach((param, pi) => {
      console.log('  📦 参数 ' + (pi + 1) + ': ' + param.name + ': ' + param.type);

      // 4. 找参数类型的data class定义
      const dcPattern = new RegExp('data class ' + param.type + '\\s*\\(([^)]*)\\)', 'g');
      let dcMatch;
      while ((dcMatch = dcPattern.exec(src)) !== null) {
        console.log('      └── 类型定义: data class ' + param.type);
        const fields = dcMatch[1].split(',').map(f => {
          const fp = f.trim().split(':').map(s => s.trim());
          const fieldName = fp[0].replace(/^(val|var)\s+/, '');
          const fieldType = fp[1] || 'Any';
          const isNullable = f.includes('?');
          const hasDefault = f.includes('=');
          return { name: fieldName, type: fieldType, nullable: isNullable, hasDefault };
        });

        console.log('      字段 (共' + fields.length + '个):');
        fields.forEach(f => {
          const status = [];
          if (f.nullable) status.push('可空');
          if (f.hasDefault) status.push('有默认值');
          
          // 检查字段是否在函数体内被使用
          const funBody = findFunBody(btn.callback) || '';
          const fieldUsed = new RegExp('\\b' + f.name + '\\b').test(funBody) ||
                           new RegExp(param.name + '\\.' + f.name).test(src);
          if (!fieldUsed && !f.hasDefault) status.push('⚠ 未在回调中使用');

          const riskIcon = f.nullable && !f.hasDefault ? '🔴' :
                          f.nullable ? '🟡' :
                          !fieldUsed && !f.hasDefault ? '⚪' : '✅';

          console.log('        ' + riskIcon + ' ' + f.name + ': ' + f.type +
            (status.length > 0 ? '  [' + status.join(', ') + ']' : ''));
        });
      }

      if (!dcMatch) {
        // 5. 在Composable函数体内追参数来源
        console.log('      └── 来源追踪:');
        // 找 items(cards) → card → 传入回调
        const itemsPattern = /items\s*\(\s*(\w+)\s*\)\s*\{[^}]*?\1\s*->/g;
        while ((m = itemsPattern.exec(src)) !== null) {
          const sourceList = m[1];
          // 找该列表的声明
          const listDeclPattern = new RegExp('(val|var)\\s+' + sourceList + '\\s*:\\s*List<(' + param.type + ')>');
          if (listDeclPattern.test(src)) {
            console.log('        来源: ' + sourceList + ': List<' + param.type + '> (来自Composable参数)');
          }
        }
        console.log('        ⚠ 跨文件追踪需要LiveDeclarationGraph（当前仅单文件正则）');
      }
    });
  } else {
    console.log('  ⚠ 未找到回调函数签名（可能是跨文件回调参数）');
    console.log('  建议: 在调用方文件中搜索' + btn.callback + '的实现');
  }
}

else {
  console.log('未知命令: ' + mode + '。用 --help 查看用法。');
}

// === 辅助：找函数体 ===
function findFunBody(funName) {
  const re = new RegExp('fun\\s+' + funName + '\\s*\\([^)]*\\)[^{]*\\{', 'g');
  const m = re.exec(src);
  if (!m) return null;
  const start = m.index + m[0].length;
  let depth = 1, i = start;
  while (i < src.length && depth > 0) {
    if (src[i] === '{') depth++;
    if (src[i] === '}') depth--;
    i++;
  }
  if (depth > 0) return null; // 🔧 修复#5: 括号不配对保护
  return src.substring(start, i - 1);
}

// === 压测引擎 ===
function runBenchmark(vars, uiCount, pageCount) {
  const total = uiCount * pageCount;
  console.log('=== 缓存式无变量函数 · 投影机制压测 ===\n');
  console.log('变量: ' + vars.join(', ') + ' | 每页UI: ' + uiCount + ' | 页数: ' + pageCount + ' | 总计: ' + total + '\n');
  
  function buildForked() {
    return Array.from({length: pageCount}, (_, p) => {
      const parent = {};
      vars.forEach(v => parent[v] = 0);
      const kids = splitKids(uiCount, 3);
      return { parent, kids, projectKid(kid, prt) {
        const k = vars.map(v => prt[v]).join('|') + '|' + kid.slice[0];
        if (kid.cache && kid.cache.key === k) return;
        kid.cache = { key: k };
      }, render() {
        let c = 0;
        for (const kid of kids) { this.projectKid(kid, this.parent); c += kid.slice[1] - kid.slice[0]; }
        return c;
      }};
    });
  }
  
  function splitKids(n, k) {
    const kids = [];
    const per = Math.ceil(n / k);
    for (let i = 0; i < k; i++) {
      const start = i * per;
      const end = Math.min(start + per, n);
      if (start < n) kids.push({ slice: [start, end], cache: null });
    }
    return kids;
  }
  
  // === 场景1: 正常 ===
  console.time('正常(初渲染)');
  for (let n = 0; n < 100; n++) {
    const pgs = buildForked();
    for (const pg of pgs) pg.render();
  }
  console.timeEnd('正常(初渲染)');
  
  // === 场景2: 切换页面 ===
  console.time('切换页面');
  for (let n = 0; n < 100; n++) {
    const pgs = buildForked();
    if (pageCount > 1) {
      pgs[1].parent[vars[0]] = 1;
      pgs[1].render();
    } else {
      pgs[0].render();
    }
  }
  console.timeEnd('切换页面');
  
  // === 场景3: 全局暗色 ===
  console.time('全局暗色(' + total + 'UI)');
  for (let n = 0; n < 100; n++) {
    const pgs = buildForked();
    for (const pg of pgs) {
      pg.parent[vars[0]] = 1; pg.parent[vars[1]] = 2; pg.parent[vars[2]] = 1;
    }
    for (const pg of pgs) pg.render();
  }
  console.timeEnd('全局暗色(' + total + 'UI)');
  
  console.log('\n✅ 投影机制压测完成 — 子变量数: ' + pageCount * 3 + ' (不随UI数增长)');
}

// 直接跑压测（无参数时）
if (mode === '--bench') { /* 已处理 */ }
