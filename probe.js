// 云端守护v2：双端探测（imagex cropUrl + cloudshot）+ 恢复自动合龙
const https = require('https');
const crypto = require('crypto');
const apiHost = 'api.feelgood.cn';
const regionKey = '17b201c78f7b27a1e2ff404b8a2feda19ffe84c8';
const keyA = '382d2157ff848da705a310d82d09a30f51a42dd8';
const pidReal = '7496026848112238611';
const ouf = 'ouf-MVYzHLxFfU9LuqPscwRMDM.png';

function post(path, body, extra) {
  return new Promise((ok) => {
    const req = https.request({ hostname: apiHost, port: 443, path, method: 'POST', headers: Object.assign({ 'Content-Type': 'application/json', 'Content-Length': Buffer.byteLength(body), 'User-Agent': 'guard-v2' }, extra || {}) }, res => {
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
function get(path) {
  return new Promise((ok) => {
    const req = https.request({ hostname: apiHost, port: 443, path, method: 'GET', headers: { 'User-Agent': 'guard-v2' } }, res => {
      let d = '';
      res.on('data', c => d += c);
      res.on('end', () => ok(d));
    });
    req.on('error', () => ok(''));
    req.setTimeout(8000, () => { try{req.destroy()}catch(e){} ok(''); });
    req.end();
  });
}

(async () => {
  const time = new Date().toISOString();
  const results = {};
  // ① imagex cropUrl（用已有 ouf）
  const ts = Date.now();
  const s = crypto.createHash('sha256').update('1' + ts + regionKey).digest('hex');
  const b = JSON.stringify({ platform_id: '1', timestamp: String(ts), token: s, auth: { platform_id: '1', timestamp: String(ts), token: s }, uri: 'survey-image/' + ouf, crop: { type: 'absolute', x: 0, y: 0, width: 100, height: 100 } });
  const crop = await post('/athena/survey/open/tool/batch/imagex/url', b);
  results.imagex = crop.includes('"url"') || crop.includes('"uri"') ? '★恢复!' + crop.substring(0, 200) : (crop.includes('50000') ? '50000' : (crop || '空'));

  // ② cloudshot
  const ts2 = Date.now();
  const s2 = crypto.createHash('sha256').update('1' + ts2 + regionKey).digest('hex');
  const cloud = await get('/api/v1/utils/cloudshot/?platform_id=1&timestamp=' + ts2 + '&token=' + s2);
  results.cloud = cloud.includes('502') ? '502' : (cloud.includes('PNG') || (cloud.length > 30 && !cloud.includes('502')) ? '★恢复!' + cloud.substring(0, 100) : (cloud || '空'));

  const recovered = results.imagex.startsWith('★') || results.cloud.startsWith('★');
  console.log(JSON.stringify({ time, results, recovered }));

  // 恢复时：创建 GitHub Issue（通知免免！）
  if (recovered && process.env.GITHUB_TOKEN) {
    const issueBody = '窗口恢复！\n时间: ' + time + '\nimagex: ' + results.imagex.substring(0, 300) + '\ncloud: ' + results.cloud.substring(0, 200) + '\n→ 用 /sdcard/Download/Operit/search_vault/读闭环_窗口守护与合龙.js 合龙';
    const body = JSON.stringify({ title: '★窗口恢复★ ' + time.substring(0, 19), body: issueBody });
    const req = https.request({ hostname: 'api.github.com', port: 443, path: '/repos/yimian-521/kotlin-head/issues', method: 'POST', headers: { 'Authorization': 'token ' + process.env.GITHUB_TOKEN, 'Content-Type': 'application/json', 'Content-Length': Buffer.byteLength(body), 'User-Agent': 'guard-v2', 'Accept': 'application/vnd.github+json' } }, res => {
      let d = '';
      res.on('data', c => d += c);
      res.on('end', () => console.log('Issue已创建: ' + res.statusCode));
    });
    req.on('error', e => console.log('Issue失败: ' + e.code));
    req.write(body);
    req.end();
  }
  process.exit(recovered ? 0 : 1);
})();