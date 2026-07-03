/**
 * github-sop v0.2.0 — GitHub 发版助手（多仓库 + 私有开关）
 * stdin JSON-RPC + HED按钮交互，token外部配置
 * 免免 & 望安，2026-07-04
 * 
 * Actions: status / check / release / repos / switch / toggle_private / help
 */

const CONFIG_FILE = "/home/.github_sop_config.json";
const fs = require("fs");

function loadConfig() {
    try { return JSON.parse(fs.readFileSync(CONFIG_FILE, "utf8")); }
    catch(e) { return null; }
}

function saveConfig(cfg) {
    fs.writeFileSync(CONFIG_FILE, JSON.stringify(cfg, null, 2));
}

let CONFIG = loadConfig();
if (!CONFIG) {
    console.error("请先创建: " + CONFIG_FILE);
    console.error('{"token":"ghp_xxx","repos":[{"repo":"y/n","localDir":"/sdcard/...","branch":"main"}],"current":"y/n","allow_private":false}');
    process.exit(1);
}

CONFIG.repos = CONFIG.repos || [];
CONFIG.allow_private = CONFIG.allow_private || false;
if (!CONFIG.current && CONFIG.repos.length > 0) CONFIG.current = CONFIG.repos[0].repo;

function currentRepo() {
    const r = CONFIG.repos.find(x => x.repo === CONFIG.current);
    if (!r) throw new Error("未配置仓库: " + CONFIG.current);
    r.apiBase = `https://api.github.com/repos/${r.repo}`;
    r.tmpDir = `/tmp/${r.repo.split("/")[1]}`;
    r.sshRemote = `git@github.com:${r.repo}.git`;
    r.gitUser = r.repo.split("/")[0];
    r.gitEmail = `${r.gitUser}@github`;
    return r;
}

const { execSync } = require("child_process");

function exec(cmd, t) {
    try {
        const out = execSync(cmd, { timeout: t || 15000, encoding: "utf8", maxBuffer: 10*1024*1024 });
        return { ok: true, out: out.trim() };
    } catch(e) {
        return { ok: false, out: (e.stdout || "") + (e.stderr || "") + e.message };
    }
}

function http(method, path, body, base) {
    const apiBase = base || currentRepo().apiBase;
    const url = path.startsWith("http") ? path : apiBase + path;
    const bodyStr = body ? `-d '${JSON.stringify(body).replace(/'/g, "'\\''")}'` : "";
    const cmd = `curl -s -w "\\n%{http_code}" -X ${method} "${url}" -H "Authorization: Bearer ${CONFIG.token}" -H "Accept: application/vnd.github+json" -H "Content-Type: application/json" ${bodyStr}`;
    const r = exec(cmd, 15000);
    if (!r.ok) return { ok: false, error: r.out };
    const lines = r.out.split("\n");
    const code = parseInt(lines[lines.length - 1]) || 0;
    const json = lines.slice(0, -1).join("\n").trim();
    try { return { ok: code >= 200 && code < 300, data: json ? JSON.parse(json) : {}, code }; }
    catch(e) { return { ok: false, error: json.substring(0, 200), code }; }
}

// ═══════════ Actions ═══════════

function action_status() {
    const r = currentRepo();
    const latest = http("GET", "/releases/latest");
    let localVer = "无";
    try {
        const m = fs.readFileSync(r.localDir + "capabilities.json", "utf8").match(/"version"\s*:\s*"([^"]+)"/);
        if (m) localVer = m[1];
    } catch(e) {}
    return {
        repo: r.repo,
        allow_private: CONFIG.allow_private,
        github: { latest: latest.ok ? latest.data.tag_name : "?", prerelease: latest.ok ? latest.data.prerelease : "?" },
        local: { version: localVer }
    };
}

function action_repos() {
    const url = `https://api.github.com/user/repos?per_page=50&sort=updated&type=${CONFIG.allow_private ? "all" : "public"}`;
    const result = http("GET", url);
    if (!result.ok) return { error: "获取失败" };
    const configured = new Set(CONFIG.repos.map(r => r.repo));
    const repos = result.data.map(r => ({
        name: r.full_name,
        private: r.private,
        desc: (r.description || "").substring(0, 80),
        configured: configured.has(r.full_name),
        current: r.full_name === CONFIG.current,
        branch: r.default_branch
    }));
    return { repos, count: repos.length, allow_private: CONFIG.allow_private };
}

function action_switch(repoName) {
    let existing = CONFIG.repos.find(r => r.repo === repoName);
    if (!existing) {
        const info = http("GET", "", null, `https://api.github.com/repos/${repoName}`);
        if (!info.ok) return { error: "无权限或不存在: " + repoName };
        existing = {
            repo: repoName,
            localDir: `/sdcard/Download/Operit/search_vault/${repoName.split("/")[1]}/`,
            branch: info.data.default_branch || "main"
        };
        CONFIG.repos.push(existing);
    }
    CONFIG.current = repoName;
    saveConfig(CONFIG);
    return { switched: repoName, total: CONFIG.repos.length };
}

function action_check() {
    const r = currentRepo();
    const diffs = [];
    const remoteCap = http("GET", "/contents/capabilities.json");
    try {
        const localCap = fs.readFileSync(r.localDir + "capabilities.json", "utf8");
        if (remoteCap.ok && remoteCap.data.content) {
            const rv = Buffer.from(remoteCap.data.content, 'base64').toString().match(/"version"\s*:\s*"([^"]+)"/);
            const lv = localCap.match(/"version"\s*:\s*"([^"]+)"/);
            if (rv && lv && rv[1] !== lv[1]) diffs.push({ file: "capabilities.json", remote: rv[1], local: lv[1] });
        }
    } catch(e) {}
    return { repo: r.repo, diffs, count: diffs.length };
}

function action_release(params) {
    const r = currentRepo();
    const steps = [];
    const v = params.version || "", t = params.title || v, b = params.body || "";
    if (!v) return { error: "缺少 version" };

    steps.push({ s: 1, n: "复制文件", ok: exec(`cp ${r.localDir}capabilities.json ${r.localDir}CHANGELOG.md ${r.localDir}README.md ${r.tmpDir}/ 2>/dev/null`).ok });
    steps.push({ s: 2, n: "git push", ok: exec(`cd /tmp && rm -rf ${r.tmpDir.split("/")[2]} && git clone ${r.sshRemote} && cp ${r.localDir}capabilities.json ${r.localDir}CHANGELOG.md ${r.localDir}README.md ${r.tmpDir}/ && cd ${r.tmpDir} && git config user.email "${r.gitEmail}" && git config user.name "${r.gitUser}" && git add -A && git commit -m "v${v}: ${t}" && git push origin ${r.branch}`).ok });
    steps.push({ s: 3, n: "tag", ok: exec(`cd ${r.tmpDir} && git tag -a v${v} -m 'v${v}: ${t}' && git push origin v${v}`).ok });

    const rel = http("POST", "/releases", { tag_name: `v${v}`, name: `v${v} — ${t}`, body: b, prerelease: true });
    steps.push({ s: 4, n: "Release", ok: rel.ok, id: rel.ok ? rel.data.id : null });

    if (rel.ok) steps.push({ s: 5, n: "正式+Latest", ok: http("PATCH", `/releases/${rel.data.id}`, { prerelease: false, make_latest: "true" }).ok });
    steps.push({ s: 6, n: "描述", ok: http("PATCH", "", { description: `v${v} — ${t}` }).ok });

    return { repo: r.repo, version: v, steps };
}

function action_toggle_private() {
    CONFIG.allow_private = !CONFIG.allow_private;
    saveConfig(CONFIG);
    return { allow_private: CONFIG.allow_private };
}

// ═══════════ JSON-RPC ═══════════
function main(input) {
    const req = typeof input === "string" ? JSON.parse(input) : input;
    switch(req.action) {
        case "status":     return JSON.stringify(action_status(), null, 2);
        case "check":      return JSON.stringify(action_check(), null, 2);
        case "release":    return JSON.stringify(action_release(req), null, 2);
        case "repos":      return JSON.stringify(action_repos(), null, 2);
        case "switch":     return JSON.stringify(action_switch(req.repo), null, 2);
        case "toggle_private": return JSON.stringify(action_toggle_private(), null, 2);
        default:           return JSON.stringify({ error: "未知 action" });
    }
}

// ═══════════ HED ═══════════
const args = process.argv.slice(2);
const jsonMode = args.includes("--json") || !process.stdin.isTTY;

if (jsonMode) {
    let input = "";
    process.stdin.on("data", d => input += d);
    process.stdin.on("end", () => { try { console.log(main(input)); } catch(e) { console.log(JSON.stringify({ error: e.message })); } });
} else {
    const rl = require("readline").createInterface({ input: process.stdin, output: process.stdout });
    let page = "main", relV = "", relT = "", relB = "";

    function render() {
        console.log("");
        switch(page) {
            case "main":
                const r = currentRepo();
                console.log("╔════════════════════════════╗");
                console.log("║   GitHub 发版助手 v0.2.0  ║");
                console.log("╚════════════════════════════╝");
                console.log("═══ " + r.repo + " ═══");
                console.log(" [1] 状态  [2] 差异  [3] 发版");
                console.log(" [4] SOP   [5] 换仓库  [6] 列表");
                console.log(" [q] 退出");
                break;
            case "status":
                const s = action_status();
                console.log("═══ " + s.repo + " ═══");
                console.log("远端: " + s.github.latest + (s.github.prerelease ? " (pre)" : " ✅"));
                console.log("本地: " + s.local.version);
                console.log("\n [b] 返回  [q] 退出");
                break;
            case "check":
                const c = action_check();
                console.log(c.count === 0 ? "✅ 一致" : "⚠️ " + c.count + " 处差异");
                c.diffs.forEach(d => console.log("  " + d.file + ": 本地" + d.local + " vs 远端" + d.remote));
                console.log("\n [b] 返回  [q] 退出");
                break;
            case "repos_list":
                const repos = action_repos();
                console.log("═══ 仓库列表 ═══");
                console.log("私有: " + (CONFIG.allow_private ? "✅" : "❌") + "  [0]切换");
                repos.repos.forEach((r, i) => {
                    const mark = r.configured ? "📁" : "  ";
                    const priv = r.private ? "🔒" : "🌐";
                    console.log(` [${i+1}] ${mark}${priv} ${r.name}${r.current ? " ←" : ""}`);
                });
                console.log("\n输入编号切换，[b]返回 [q]退出");
                break;
            case "release":
                console.log("═══ 发版 ═══\n输入版本号：");
                break;
            case "rel_title":
                console.log("输入标题：");
                break;
            case "rel_body":
                console.log("输入内容：");
                break;
            case "sop":
                console.log("═══ SOP ═══");
                console.log("1.更新三文件 2.git push 3.tag");
                console.log("4.Release 5.正式+Latest 6.描述 7.路线图✅");
                console.log("\n [b] 返回  [q] 退出");
                break;
        }
    }

    rl.on("line", (line) => {
        const i = line.trim().toLowerCase();
        if (i === "q") { rl.close(); return; }

        if (page.startsWith("rel_")) {
            if (page === "release") { relV = line.trim(); page = "rel_title"; render(); return; }
            if (page === "rel_title") { relT = line.trim(); page = "rel_body"; render(); return; }
            if (page === "rel_body") {
                relB = line.trim();
                console.log("\n═══ 确认 ═══");
                console.log("v" + relV + " — " + relT);
                console.log("确认？[y/N]");
                page = "rel_confirm"; return;
            }
            if (page === "rel_confirm") {
                if (i === "y") console.log(JSON.stringify(action_release({ version: relV, title: relT, body: relB }), null, 2));
                else console.log("已取消。");
                page = "main"; render(); return;
            }
        }

        if (page === "repos_list") {
            if (i === "b") { page = "main"; render(); return; }
            if (i === "0") { action_toggle_private(); page = "repos_list"; renderRepoList(); return; }
            const idx = parseInt(i) - 1;
            if (idx >= 0) {
                const repos = action_repos();
                if (repos.repos && repos.repos[idx]) {
                    const r = action_switch(repos.repos[idx].name);
                    console.log(r.error ? "❌" : "✅ " + r.switched);
                }
                page = "main"; render(); return;
            }
        }

        if (i === "b") { page = "main"; render(); return; }
        switch(i) {
            case "1": page = "status"; break;
            case "2": page = "check"; break;
            case "3": page = "release"; break;
            case "4": page = "sop"; break;
            case "5": case "6": page = "repos_list"; renderRepoList(); return;
            default: console.log("?"); break;
        }
        render();
    });

    function renderRepoList() {
        const repos = action_repos();
        repos.repos.forEach((r, i) => {
            const c = r.configured ? "📁" : "  ";
            const p = r.private ? "🔒" : "🌐";
            console.log(` [${i+1}] ${c}${p} ${r.name}${r.current ? " ←" : ""}`);
        });
        console.log("\n [0] 私有:" + (CONFIG.allow_private ? "关" : "开") + " [b]返回 [q]退出");
    }

    rl.on("close", () => process.exit(0));
    render();
}