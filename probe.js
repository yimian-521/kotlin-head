// 云端守护探测脚本（GitHub Actions 每5分钟跑）——零依赖Node
const https = require('https');
const crypto = require('crypto');

const apiHost = 'api.feelgood.cn';
const keyA = '382d2157ff848da705a310d82d09a30f51a42dd8';
const pidReal = '7496026848112238611';

function post(path, body) {
  return new Promise((ok) => {
    const req = https.request({ hostname: apiHost, port: 443, path, method: 'POST', headers: { 'Content-Type': 'application/json', 'Content-Length': Buffer.byteLength(body), 'Origin': 'https://www.feelgood.cn', 'Referer': 'https://www.feelgood.cn/', 'User-Agent': 'guard-probe' } }, res => {
      let d = '';
      res.on('data', c => d += c);
      res.on('end', () => ok(d));
    });
    req.on('error', () => ok(''));
    req.setTimeout(8000, () => { try{req.destroy()}catch(e){} ok(''); });
    req.write(body);
    req.end();
  });
}

(async () => {
  // cropUrl 探测（比上传轻量——恢复即返回url）
  const ts = Date.now();
  const regionKey = '17b201c78f7b27a1e2ff404b8a2feda19ffe84c8';
  const s = crypto.createHash('sha256').update('1' + ts + regionKey).digest('hex');
  const b = JSON.stringify({ platform_id: '1', timestamp: String(ts), token: s, auth: { platform_id: '1', timestamp: String(ts), token: s }, uri: 'survey-image/ouf-MVYzHLxFfU9LuqPscwRMDM.png', crop: { type: 'absolute', x: 0, y: 0, width: 100, height: 100 } });
  const r = await post('/athena/survey/open/tool/batch/imagex/url', b);
  const isUp = r.includes('"url"') || r.includes('"uri"') || (r.length > 0 && !r.includes('50000') && !r.includes('40001'));
  const time = new Date().toISOString();
  if (isUp) {
    console.log('★窗口恢复★ ' + time + ' | ' + r.substring(0, 300));
    process.exit(0); // success → workflow 记录
  } else {
    console.log('守候中 ' + time + ' | ' + (r.includes('50000') ? '50000' : (r || '空')));
    process.exit(1); // 未恢复 → workflow 标记失败（但不告警邮件）
  }
})();