/**
 * github-sop v0.1.0 — GitHub 发版助手
 * stdin JSON-RPC，内置 token + SSH + 仓库信息
 * 免免 & 望安，2026-07-04
 * 
 * Actions: release / status / check / help
 */

// ═══════════ 配置（内置） ═══════════
const CONFIG_FILE = "/home/.github_sop_config.json";

function loadConfig() {
    const fs = require("fs");
    try {
        return JSON.parse(fs.readFileSync(CONFIG_FILE, "utf8"));
    } catch(e) {
        return null;
    }
}

const CONFIG = loadConfig();
if (!CONFIG) {
    console.error("请先创建配置文件: " + CONFIG_FILE);
    console.error('示例: {"repo":"yimian-521/kotlin-head","token":"ghp_xxx","localDir":"...","branch":"main"}');
    process.exit(1);
}

// 自动推导
CONFIG.apiBase = CONFIG.apiBase || `https://api.github.com/repos/${CONFIG.repo}`;
CONFIG.tmpDir = CONFIG.tmpDir || `/tmp/${CONFIG.repo.split("/")[1]}`;
CONFIG.sshRemote = CONFIG.sshRemote || `git@github.com:${CONFIG.repo}.git`;
CONFIG.gitUser = CONFIG.gitUser || CONFIG.repo.split("/")[0];
CONFIG.gitEmail = CONFIG.gitEmail || `${CONFIG.gitUser}@github`;

// ═══════════ 工具函数 ═══════════
const { execSync } = require("child_process");

function exec(cmd, timeoutMs) {
    try {
        const out = execSync(cmd, { timeout: timeoutMs || 15000, encoding: "utf8", maxBuffer: 10*1024*1024 });
        return { ok: true, out: out.trim(), code: 0 };
    } catch(e) {
        return { ok: false, out: (e.stdout || "") + (e.stderr || "") + e.message, code: e.status || -1 };
    }
}

function http(method, path, body) {
    const url = path.startsWith("http") ? path : CONFIG.apiBase + path;
    const bodyStr = body ? `-d '${JSON.stringify(body).replace(/'/g, "'\\''")}'` : "";
    const cmd = `curl -s -w "\\n%{http_code}" -X ${method} "${url}" -H "Authorization: Bearer ${CONFIG.token}" -H "Accept: application/vnd.github+json" -H "Content-Type: application/json" ${bodyStr}`;
    const r = exec(cmd, 15000);
    if (!r.ok) return { ok: false, error: r.out };
    const lines = r.out.split("\n");
    const statusCode = parseInt(lines[lines.length - 1]) || 0;
    const jsonStr = lines.slice(0, -1).join("\n").trim();
    try { 
        const data = jsonStr ? JSON.parse(jsonStr) : {};
        return { ok: statusCode >= 200 && statusCode < 300, data, code: statusCode };
    } catch(e) { 
        return { ok: false, error: jsonStr.substring(0, 200), code: statusCode }; 
    }
}

// ═══════════ Actions ═══════════

function action_status() {
    const latest = http("GET", "/releases/latest");
    const repo = http("GET", "");
    let localVer = "无法读取";
    try {
        const cap = require("fs").readFileSync(CONFIG.localDir + "capabilities.json", "utf8");
        const m = cap.match(/"version"\s*:\s*"([^"]+)"/);
        if (m) localVer = m[1];
    } catch(e) {}

    return {
        github: {
            latest_release: latest.ok ? latest.data.tag_name : "获取失败",
            prerelease: latest.ok ? latest.data.prerelease : "?",
            repo_desc: repo.ok ? (repo.data.description || "").substring(0, 80) : "获取失败"
        },
        local: {
            version: localVer,
            dir: CONFIG.localDir
        }
    };
}

function action_check() {
    const diffs = [];
    // 检查本地 vs 远端 capabilities.json
    const remoteCap = http("GET", "/contents/capabilities.json");
    const localCap = exec(`cat ${CONFIG.localDir}capabilities.json`);
    
    if (remoteCap.ok && localCap.ok) {
        const remoteVer = (remoteCap.data.content ? 
            Buffer.from(remoteCap.data.content, 'base64').toString() : "").match(/"version"\s*:\s*"([^"]+)"/);
        const localVer = localCap.out.match(/"version"\s*:\s*"([^"]+)"/);
        if (remoteVer && localVer && remoteVer[1] !== localVer[1]) {
            diffs.push({ file: "capabilities.json", remote: remoteVer[1], local: localVer[1] });
        }
    }
    
    return { diffs, count: diffs.length };
}

function action_release(params) {
    const steps = [];
    const version = params.version || "";
    const title = params.title || version;
    const body = params.body || "";
    
    if (!version) return { error: "缺少 version 参数" };
    
    // 第一步：复制本地文件
    const cp1 = exec(`cp ${CONFIG.localDir}capabilities.json ${CONFIG.localDir}CHANGELOG.md ${CONFIG.localDir}README.md ${CONFIG.tmpDir}/ 2>/dev/null`);
    steps.push({ step: 1, action: "复制本地文件", ok: cp1.ok });
    
    // 第二步：git clone + push
    const push = exec(`cd /tmp && rm -rf kotlin-head && git clone ${CONFIG.sshRemote} && cp ${CONFIG.localDir}capabilities.json ${CONFIG.localDir}CHANGELOG.md ${CONFIG.localDir}README.md ${CONFIG.tmpDir}/ && cd ${CONFIG.tmpDir} && git config user.email "${CONFIG.gitEmail}" && git config user.name "${CONFIG.gitUser}" && git add -A && git commit -m "v${version}: ${title}" && git push origin ${CONFIG.branch}`);
    steps.push({ step: 2, action: "git push", ok: push.ok, detail: push.out.substring(0, 200) });
    
    // 第三步：tag
    const tag = exec(`cd ${CONFIG.tmpDir} && git tag -a v${version} -m 'v${version}: ${title}' && git push origin v${version}`);
    steps.push({ step: 3, action: "打 tag", ok: tag.ok });
    
    // 第四步：创建 Release
    const rel = http("POST", "/releases", {
        tag_name: `v${version}`,
        name: `v${version} — ${title}`,
        body: body,
        prerelease: true
    });
    const releaseId = rel.ok ? rel.data.id : null;
    steps.push({ step: 4, action: "创建 Release", ok: rel.ok, release_id: releaseId });
    
    // 第五步：正式发行 + Latest
    if (releaseId) {
        const patch = http("PATCH", `/releases/${releaseId}`, { prerelease: false, make_latest: "true" });
        steps.push({ step: 5, action: "正式发行 + Latest", ok: patch.ok });
    }
    
    // 第六步：仓库描述
    const desc = http("PATCH", "", { description: `v${version} — ${title}` });
    steps.push({ step: 6, action: "仓库描述", ok: desc.ok });
    
    return { version, steps, all_ok: steps.every(s => s.ok) };
}

// ═══════════ HED 交互 ═══════════
function renderMain() {
    console.log("╔════════════════════════════╗");
    console.log("║   GitHub 发版助手 v0.1.0  ║");
    console.log("╚════════════════════════════╝");
    console.log("═══ " + CONFIG.repo + " ═══");
    console.log("[1] 查看当前状态");
    console.log("[2] 差异检查（本地 vs 远端）");
    console.log("[3] 发布新版本（七步SOP）");
    console.log("[4] 查看 SOP 手册");
    console.log("[h] 帮助  [q] 退出");
}

function renderHelp() {
    console.log("═══ SOP 七步 ═══");
    console.log("1. 更新本地三文件（capabilities/CHANGELOG/README）");
    console.log("2. git clone + cp + commit + push（SSH）");
    console.log("3. git tag + push tag");
    console.log("4. POST /releases 创建 Release");
    console.log("5. PATCH prerelease:false + make_latest:true");
    console.log("6. PATCH 仓库描述");
    console.log("7. README 路线图 🏗️→✅");
    console.log("");
    console.log("═══ stdin JSON-RPC ═══");
    console.log('{"action":"status"}     → 查看状态');
    console.log('{"action":"check"}      → 差异检查');
    console.log('{"action":"release","version":"0.6.4","title":"标题","body":"内容"}  → 发版');
    console.log('{"action":"help"}       → 帮助');
}

function renderSOP() {
    console.log("═══ 完整 SOP 手册 ═══");
    console.log("Token: " + CONFIG.token.substring(0, 10) + "...");
    console.log("SSH:  ~/.ssh/id_ed25519_github");
    console.log("仓库: " + CONFIG.repo + " (" + CONFIG.branch + ")");
    console.log("");
    console.log("手动发版步骤:");
    console.log("第二步: cd /tmp && rm -rf kotlin-head && git clone " + CONFIG.sshRemote);
    console.log("        cp 三文件到 /tmp/kotlin-head/ && git config + commit + push");
    console.log("第四步: POST " + CONFIG.apiBase + "/releases");
    console.log("        Headers: Authorization: token <token> + Accept: application/vnd.github+json");
    console.log("第五步: PATCH " + CONFIG.apiBase + "/releases/{id}");
    console.log("        Body: {prerelease:false, make_latest:\"true\"}");
    console.log("第六步: PATCH " + CONFIG.apiBase + " → Body: {description:\"...\"}");
}

// ═══════════ 主入口 ═══════════
function main(input) {
    const req = JSON.parse(input);
    const action = req.action || "status";
    
    switch(action) {
        case "status":  return JSON.stringify(action_status(), null, 2);
        case "check":   return JSON.stringify(action_check(), null, 2);
        case "release": return JSON.stringify(action_release(req), null, 2);
        case "help":    return JSON.stringify({ help: "stdin JSON-RPC: status / check / release / help" });
        default:        return JSON.stringify({ error: "未知 action: " + action });
    }
}

// 直接运行时进入 HED 交互
if (typeof __hed_mode !== "undefined" || process.argv.includes("--hed")) {
    renderMain();
} else {
    // 从 stdin 读取 JSON
    let input = "";
    process.stdin.on("data", chunk => input += chunk);
    process.stdin.on("end", () => {
        try { console.log(main(input)); }
        catch(e) { console.log(JSON.stringify({ error: e.message })); }
    });
}
