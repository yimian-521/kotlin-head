#!/usr/bin/env python3
"""
兼容度检测器 v2.0 — 多语言版
支持 Kotlin + Node.js 双引擎，自动识别项目类型。
用法: python3 compat_check.py <项目A路径> <项目B路径>
输出: 总体兼容度% + 六维雷达 + 问题清单 + 修复方向
"""

import os, sys, re, json
from pathlib import Path
from collections import defaultdict
from typing import Dict, List, Tuple, Optional

# ═══════════════════════════════════════
# 数据模型
# ═══════════════════════════════════════

class Issue:
    """兼容性问题"""
    def __init__(self, dimension: str, severity: str, title: str, detail: str, fix: str):
        self.dimension = dimension      # 维度名
        self.severity = severity         # CRITICAL / WARNING / INFO
        self.title = title               # 简短标题
        self.detail = detail             # 详细说明
        self.fix = fix                   # 修复建议

class DimensionScore:
    """单维度评分"""
    def __init__(self, name: str, score: float, max_score: float, issues: List[Issue]):
        self.name = name
        self.score = score
        self.max_score = max_score
        self.issues = issues
        self.pct = round(score / max_score * 100, 1) if max_score > 0 else 0

    def grade(self) -> str:
        if self.pct >= 95: return "🟢 原生级"
        elif self.pct >= 80: return "🟡 可嵌入"
        elif self.pct >= 60: return "🟠 需适配"
        else: return "🔴 严重冲突"

class CompatibilityReport:
    """完整兼容度报告"""
    def __init__(self):
        self.dimensions: List[DimensionScore] = []
        self.overall_pct: float = 0.0
        self.lang_a: str = "unknown"
        self.lang_b: str = "unknown"

# ═══════════════════════════
# 项目类型识别
# ═══════════════════════════

SKIP_DIRS = {'.git', '.gradle', 'build', 'node_modules', '__pycache__', '.idea'}

def detect_project_type(project_path: str) -> str:
    """识别项目类型：kotlin / nodejs / unknown"""
    has_kt = False
    has_js = False
    for root, dirs, files in os.walk(project_path):
        dirs[:] = [d for d in dirs if d not in SKIP_DIRS]
        for f in files:
            if f in ('build.gradle', 'build.gradle.kts'):
                return "kotlin"
            if f == 'package.json':
                return "nodejs"
            if f.endswith('.kt'): has_kt = True
            if f.endswith(('.js','.mjs')): has_js = True
    if has_kt: return "kotlin"
    if has_js: return "nodejs"
    return "unknown"

# ═══════════════════════════════════════
# Gradle 解析器
# ═══════════════════════════════════════

def find_gradle_files(project_path: str) -> List[str]:
    """找到所有 build.gradle / build.gradle.kts"""
    result = []
    for root, dirs, files in os.walk(project_path):
        dirs[:] = [d for d in dirs if d not in ('.git', '.gradle', 'build', 'node_modules')]
        for f in files:
            if f in ('build.gradle', 'build.gradle.kts'):
                result.append(os.path.join(root, f))
    return result

def parse_kotlin_version(gradle_paths: List[str]) -> Optional[str]:
    """从 Gradle 文件中提取 Kotlin 版本"""
    patterns = [
        r'kotlin\(.jvm.\).*\n.*version\s*=\s*"([\d.]+)"',   # kotlin("jvm") version "x.y.z"
        r"kotlin\(.jvm.\).*\n.*version\s*=\s*'([\d.]+)'",
        r'org\.jetbrains\.kotlin:kotlin-stdlib:([\d.]+)',
        r'kotlin_version\s*=\s*["\']([\d.]+)["\']',
        r'id\("org\.jetbrains\.kotlin\.jvm"\).*\n.*version\s+"([\d.]+)"',
    ]
    for path in gradle_paths:
        try:
            content = Path(path).read_text(encoding='utf-8', errors='ignore')
            for pat in patterns:
                m = re.search(pat, content, re.MULTILINE)
                if m:
                    return m.group(1)
        except Exception:
            pass
    return None

def parse_kotlin_deps(gradle_paths: List[str]) -> Dict[str, str]:
    """提取所有依赖及其版本 -> {group:artifact: version}"""
    deps = {}
    dep_pattern = re.compile(
        r'(?:implementation|api|compileOnly|runtimeOnly|testImplementation|'
        r'kapt|annotationProcessor)\s*[\(]\s*"([^"]+):([^"]+):([^"]+)"'
    )
    for path in gradle_paths:
        try:
            content = Path(path).read_text(encoding='utf-8', errors='ignore')
            for m in dep_pattern.finditer(content):
                group, artifact, version = m.groups()
                key = f"{group}:{artifact}"
                deps[key] = version
        except Exception:
            pass
    return deps

# ═══════════════════════════════════════
# Kotlin 源码解析器
# ═══════════════════════════════════════

def find_kt_files(project_path: str) -> List[str]:
    """找到所有 .kt 文件"""
    result = []
    for root, dirs, files in os.walk(project_path):
        dirs[:] = [d for d in dirs if d not in ('.git', '.gradle', 'build', 'node_modules')]
        for f in files:
            if f.endswith('.kt'):
                result.append(os.path.join(root, f))
    return result

def find_main_functions(kt_files: List[str]) -> List[Tuple[str, str]]:
    """找到所有 main 函数 -> [(文件路径, 函数签名)]"""
    mains = []
    pat = re.compile(r'fun\s+main\s*\([^)]*\)')
    for path in kt_files:
        try:
            content = Path(path).read_text(encoding='utf-8', errors='ignore')
            for m in pat.finditer(content):
                mains.append((path, m.group()))
        except Exception:
            pass
    return mains

def find_object_declarations(kt_files: List[str]) -> List[Tuple[str, str]]:
    """找到所有 object 声明 -> [(文件路径, object名)]"""
    objects = []
    pat = re.compile(r'\bobject\s+(\w+)')
    for path in kt_files:
        try:
            content = Path(path).read_text(encoding='utf-8', errors='ignore')
            for m in pat.finditer(content):
                objects.append((path, m.group(1)))
        except Exception:
            pass
    return objects

def find_public_api(kt_files: List[str]) -> Dict[str, List[str]]:
    """找到所有公开的类/接口/object -> {名称: [文件路径]}（排除短名称和常见变量名）"""
    api = defaultdict(list)
    # 只匹配顶级声明：class / interface / object / enum class
    # 排除 import 行和注释中的
    pat = re.compile(r'^(?!\s*//|\s*\*|\s*import)(?:public\s+)?(?:data\s+)?(?:sealed\s+)?(?:abstract\s+)?(?:open\s+)?(class|interface|object|enum\s+class)\s+(\w+)', re.MULTILINE)
    skip = {'if','when','for','while','return','true','false','null','is','in','as','it',
            'cur','arr','e','j','i','k','v','x','y','n','s','r','val','var','fun',
            'Any','Unit','Nothing','String','Int','Long','Boolean','Float','Double',
            'List','Map','Set','Array','T','R','E','K','V'}
    for path in kt_files:
        try:
            content = Path(path).read_text(encoding='utf-8', errors='ignore')
            for m in pat.finditer(content):
                name = m.group(2)
                if name not in skip and len(name) >= 2:
                    api[name].append(path)
        except Exception:
            pass
    return api

def find_package_declarations(kt_files: List[str]) -> Dict[str, List[str]]:
    """找到所有包声明 -> {包名: [文件路径]}"""
    pkgs = defaultdict(list)
    pat = re.compile(r'^package\s+([\w.]+)', re.MULTILINE)
    for path in kt_files:
        try:
            content = Path(path).read_text(encoding='utf-8', errors='ignore')
            for m in pat.finditer(content):
                pkgs[m.group(1)].append(path)
        except Exception:
            pass
    return pkgs

def find_singletons(kt_files: List[str]) -> List[Tuple[str, str]]:
    """找到潜在的单例模式（companion object + object）"""
    singletons = []
    pat = re.compile(r'(?:companion\s+)?object\s+(\w+)')
    for path in kt_files:
        try:
            content = Path(path).read_text(encoding='utf-8', errors='ignore')
            for m in pat.finditer(content):
                if m.group(0).startswith('companion'):
                    singletons.append((path, f"companion: {m.group(1)}"))
                else:
                    singletons.append((path, f"object: {m.group(1)}"))
        except Exception:
            pass
    return singletons

# ═══════════════════════════════════════
# Node.js 解析器
# ═══════════════════════════════════════

def find_package_json(project_path: str) -> Optional[str]:
    """找到 package.json"""
    for root, dirs, files in os.walk(project_path):
        dirs[:] = [d for d in dirs if d not in SKIP_DIRS]
        for f in files:
            if f == 'package.json':
                return os.path.join(root, f)
    return None

def parse_node_version(pkg_path: Optional[str]) -> Optional[str]:
    """提取 engines.node 版本"""
    if not pkg_path: return None
    try:
        data = json.loads(Path(pkg_path).read_text(encoding='utf-8', errors='ignore'))
        eng = data.get('engines', {})
        return eng.get('node', None)
    except: return None

def parse_node_main(pkg_path: Optional[str]) -> Optional[str]:
    """提取 main 入口"""
    if not pkg_path: return None
    try:
        data = json.loads(Path(pkg_path).read_text(encoding='utf-8', errors='ignore'))
        return data.get('main', None)
    except: return None

def parse_node_deps(pkg_path: Optional[str]) -> Dict[str, str]:
    """提取 dependencies + devDependencies"""
    if not pkg_path: return {}
    try:
        data = json.loads(Path(pkg_path).read_text(encoding='utf-8', errors='ignore'))
        deps = {}
        for section in ('dependencies', 'devDependencies'):
            for k, v in data.get(section, {}).items():
                deps[k] = v.lstrip('^~>=')
        return deps
    except: return {}

def find_js_files(project_path: str) -> List[str]:
    """找到所有 .js / .mjs 文件"""
    result = []
    for root, dirs, files in os.walk(project_path):
        dirs[:] = [d for d in dirs if d not in SKIP_DIRS]
        for f in files:
            if f.endswith(('.js', '.mjs')):
                result.append(os.path.join(root, f))
    return result

def find_node_exports(js_files: List[str]) -> Dict[str, List[str]]:
    """找到所有 module.exports / exports.xxx -> {名称: [文件路径]}"""
    exports = defaultdict(list)
    pat = re.compile(r'(?:module\.exports\s*=\s*\{?\s*(\w+)|exports\.(\w+)\s*=)')
    for path in js_files:
        try:
            content = Path(path).read_text(encoding='utf-8', errors='ignore')
            for m in pat.finditer(content):
                name = m.group(1) or m.group(2)
                if name and name not in ('if','for','while','return','true','false','null','undefined'):
                    exports[name].append(path)
        except: pass
    return exports

def find_node_globals(js_files: List[str]) -> Dict[str, List[str]]:
    """找到全局变量声明（顶层 var/let/const 且不在函数内）"""
    globals_found = defaultdict(list)
    pat = re.compile(r'^(?:var|let|const)\s+(\w+)', re.MULTILINE)
    for path in js_files:
        try:
            content = Path(path).read_text(encoding='utf-8', errors='ignore')
            for m in pat.finditer(content):
                globals_found[m.group(1)].append(path)
        except: pass
    return globals_found

# ═══════════════════════════════════════
# Node.js 六维检测
# ═══════════════════════════════════════

def check_node_runtime(va: Optional[str], vb: Optional[str]) -> DimensionScore:
    """维度1：Node 版本兼容"""
    issues = []
    score = 100.0
    if va and vb:
        ma = tuple(int(x) for x in va.split('.')[:2])
        mb = tuple(int(x) for x in vb.split('.')[:2])
        if ma[0] != mb[0]:
            issues.append(Issue("运行时版本", "CRITICAL",
                f"Node 主版本不同: {va} vs {vb}",
                f"Node {va} 和 Node {vb} 主版本不兼容。API 可能不互通。",
                f"统一到较新版本，或使用 nvm 管理多版本。"))
            score = 20
        elif ma[1] != mb[1]:
            issues.append(Issue("运行时版本", "WARNING",
                f"Node 次版本不同: {va} vs {vb}",
                f"次版本差异通常兼容，但需确认特性依赖。",
                f"建议统一到相同 LTS 版本。"))
            score = 80
    elif va or vb:
        missing = 'B' if va else 'A'
        issues.append(Issue("运行时版本", "WARNING",
            f"项目{missing}未声明 engines.node",
            f"只有一方声明了 Node 版本。",
            f"补充 engines.node 字段。"))
        score = 60
    else:
        score = 100
    return DimensionScore("运行时版本", score, 100, issues)

def check_node_entry(main_a: Optional[str], main_b: Optional[str], js_count_a: int, js_count_b: int) -> DimensionScore:
    """维度2：入口兼容"""
    issues = []
    score = 100.0
    if main_a and main_b:
        issues.append(Issue("入口兼容", "WARNING",
            f"双方都有 main 入口: {main_a} vs {main_b}",
            f"两个 main 入口不能同时生效。",
            f"一方作为库暴露 API，另一方作为宿主引入。"))
        score = 70
    elif main_a or main_b:
        side = 'A' if main_a else 'B'
        issues.append(Issue("入口兼容", "INFO",
            f"仅项目{side}有 main 入口",
            f"适合作为宿主。另一方需暴露 API。",
            ""))
        score = 90
    else:
        score = 100
    return DimensionScore("入口兼容", score, 100, issues)

def check_node_deps(deps_a: Dict[str, str], deps_b: Dict[str, str]) -> DimensionScore:
    """维度3：依赖冲突"""
    issues = []
    score = 100.0
    common = set(deps_a.keys()) & set(deps_b.keys())
    conflicts = 0
    for key in common:
        if deps_a[key] != deps_b[key]:
            conflicts += 1
            issues.append(Issue("依赖冲突", "WARNING",
                f"依赖版本冲突: {key}: {deps_a[key]} vs {deps_b[key]}",
                f"npm 会选符合 semver 的版本，但可能不兼容。",
                f"统一版本，运行 npm ls {key} 确认。"))
    if conflicts > 0:
        score = max(10, 100 - conflicts * 15)
    return DimensionScore("依赖冲突", score, 100, issues)

def check_node_exports(exports_a: Dict, exports_b: Dict) -> DimensionScore:
    """维度4：导出冲突"""
    issues = []
    score = 100.0
    common = set(exports_a.keys()) & set(exports_b.keys())
    for name in common:
        issues.append(Issue("导出冲突", "WARNING",
            f"导出名称冲突: {name}",
            f"A: {exports_a[name][0]}\nB: {exports_b[name][0]}",
            f"require/import 时名称冲突。重命名或使用命名空间。"))
    if common:
        score = max(30, 100 - len(common) * 10)
    return DimensionScore("导出冲突", score, 100, issues)

def check_node_globals(globals_a: Dict, globals_b: Dict) -> DimensionScore:
    """维度5：全局状态冲突"""
    issues = []
    score = 100.0
    common = set(globals_a.keys()) & set(globals_b.keys())
    if common:
        issues.append(Issue("全局状态", "WARNING",
            f"同名全局变量: {', '.join(list(common)[:5])}",
            f"双方都声明了这些顶层变量——require 后可能互相覆盖。",
            f"用 IIFE 包裹或改用模块作用域。"))
        score = max(40, 100 - len(common) * 15)
    return DimensionScore("全局状态", score, 100, issues)

def check_node_architecture(pkg_a: Optional[str], pkg_b: Optional[str]) -> DimensionScore:
    """维度6：架构兼容"""
    issues = []
    score = 100.0
    name_a = None; name_b = None
    if pkg_a:
        try:
            data = json.loads(Path(pkg_a).read_text(encoding='utf-8', errors='ignore'))
            name_a = data.get('name', None)
        except Exception as e:
            issues.append(Issue("架构兼容", "WARNING",
                f"项目A package.json 解析失败",
                f"{pkg_a}: {e}",
                "检查 JSON 是否合法。"))
    if pkg_b:
        try:
            data = json.loads(Path(pkg_b).read_text(encoding='utf-8', errors='ignore'))
            name_b = data.get('name', None)
        except Exception as e:
            issues.append(Issue("架构兼容", "WARNING",
                f"项目B package.json 解析失败",
                f"{pkg_b}: {e}",
                "检查 JSON 是否合法。"))
    if name_a and name_a == name_b:
        issues.append(Issue("架构兼容", "CRITICAL",
            f"npm 包名冲突: {name_a}",
            f"两个项目 npm name 相同——无法同时作为依赖安装。",
            f"修改其中一方的 package.json name。"))
        score = 30
    return DimensionScore("架构兼容", score, 100, issues)

# ═══════════════════════════════════════
# 跨语言检测
# ═══════════════════════════════════════

def check_cross_lang(lang_a: str, lang_b: str) -> Issue:
    """跨语言项目兼容性——本质是能否通过 IPC/HTTP 通信"""
    if lang_a != lang_b:
        return Issue("跨语言", "INFO",
            f"语言不同: {lang_a} vs {lang_b}",
            f"不能直接合并源码。通过 IPC/HTTP/子进程通信实现集成。",
            "建议：一方暴露 HTTP API 或 CLI 接口，另一方调用。")
    else:
        return Issue("跨语言", "INFO",
            "同语言项目",
            f"双方都是 {lang_a}——走同语言检测。",
            "")

def check_kotlin_version(va: Optional[str], vb: Optional[str]) -> DimensionScore:
    """维度1：Kotlin 版本兼容"""
    issues = []
    score = 100.0
    if va and vb:
        # 提取主版本号
        ma = tuple(int(x) for x in va.split('.')[:2])
        mb = tuple(int(x) for x in vb.split('.')[:2])
        if ma[0] != mb[0]:
            issues.append(Issue("Kotlin版本", "CRITICAL",
                f"主版本不同: {va} vs {vb}",
                f"A用 {va}，B用 {vb}——主版本不兼容。编译产物字节码目标不同，无法合并。",
                f"统一到相同主版本。建议都升到较新的 {max(va, vb, key=lambda v: tuple(int(x) for x in v.split('.')))}。"))
            score = 0
        elif ma[1] != mb[1]:
            issues.append(Issue("Kotlin版本", "WARNING",
                f"次版本不同: {va} vs {vb}",
                f"A用 {va}，B用 {vb}——次版本差异通常兼容，但语言特性可能不匹配。",
                f"统一到 {max(va, vb, key=lambda v: tuple(int(x) for x in v.split('.')))}，避免特性差异。"))
            score = 80
    elif va or vb:
        found = va or vb
        missing = 'B' if va else 'A'
        issues.append(Issue("Kotlin版本", "WARNING",
            f"项目{missing}未检测到Kotlin版本",
            f"只有项目{'A' if va else 'B'}检测到 Kotlin {found}。",
            f"确认项目{missing}是否使用Kotlin。"))
        score = 50
    else:
        issues.append(Issue("Kotlin版本", "INFO", "两项目均未检测到Kotlin版本", "", ""))
        score = 100
    return DimensionScore("Kotlin版本", score, 100, issues)

def check_entry_conflict(mains_a: List, mains_b: List) -> DimensionScore:
    """维度2：入口冲突"""
    issues = []
    score = 100.0
    if mains_a and mains_b:
        issues.append(Issue("入口冲突", "CRITICAL",
            f"双方都有 main 函数（A: {len(mains_a)}个, B: {len(mains_b)}个）",
            f"A: {', '.join(m[1] for m in mains_a[:3])}...\nB: {', '.join(m[1] for m in mains_b[:3])}...\n两个 main 不能共存。",
            "选项1: 一方提供嵌入模式（去掉main，暴露API）。选项2: 用子进程/类加载器隔离。"))
        score = 10
    elif mains_a or mains_b:
        side = 'A' if mains_a else 'B'
        count = len(mains_a or mains_b)
        issues.append(Issue("入口冲突", "WARNING",
            f"仅项目{side}有 main 函数（{count}个）",
            f"项目{side}有 {count} 个 main，另一方没有——可以作为宿主。",
            f"无 main 的一方需暴露 API 供调用。"))
        score = 70
    else:
        score = 100
    return DimensionScore("入口冲突", score, 100, issues)

def check_dependency_conflict(deps_a: Dict[str, str], deps_b: Dict[str, str]) -> DimensionScore:
    """维度3：依赖冲突"""
    issues = []
    score = 100.0
    common_keys = set(deps_a.keys()) & set(deps_b.keys())
    conflicts = 0
    for key in common_keys:
        if deps_a[key] != deps_b[key]:
            conflicts += 1
            issues.append(Issue("依赖冲突", "WARNING",
                f"依赖版本冲突: {key}: {deps_a[key]} vs {deps_b[key]}",
                f"同一依赖 {key}，A 用 {deps_a[key]}，B 用 {deps_b[key]}。Gradle 会选较高版本，但可能不兼容。",
                f"统一版本到 {max(deps_a[key], deps_b[key], key=lambda v: tuple(int(x) for x in v.split('.') if x.isdigit()))}，测试兼容性。"))
    if conflicts > 0:
        score = max(10, 100 - conflicts * 15)
    return DimensionScore("依赖冲突", score, 100, issues)

def check_namespace_conflict(objects_a: List, objects_b: List, pkgs_a: Dict, pkgs_b: Dict) -> DimensionScore:
    """维度4：命名空间冲突"""
    issues = []
    score = 100.0
    # object 名称冲突
    names_a = {name for _, name in objects_a}
    names_b = {name for _, name in objects_b}
    common_names = names_a & names_b
    for name in common_names:
        paths_a = [p for p, n in objects_a if n == name]
        paths_b = [p for p, n in objects_b if n == name]
        issues.append(Issue("命名空间冲突", "CRITICAL",
            f"object 名称冲突: {name}",
            f"A: {paths_a[0]}\nB: {paths_b[0]}\n同名的 object 无法共存于同一 JVM。",
            f"重命名其中一方的 {name}。"))
        score = max(0, score - 30)
    # 包冲突
    pkg_common = set(pkgs_a.keys()) & set(pkgs_b.keys())
    for pkg in pkg_common:
        if pkg not in ('', 'com', 'org'):
            issues.append(Issue("命名空间冲突", "WARNING",
                f"包名重叠: {pkg}",
                f"A 和 B 都在 {pkg} 包下有文件。如果包内类名也相同，会有冲突。",
                f"检查 {pkg} 下的类名是否重复。"))
            score = max(10, score - 5)
    return DimensionScore("命名空间冲突", score, 100, issues)

def check_api_conflict(api_a: Dict[str, List[str]], api_b: Dict[str, List[str]]) -> DimensionScore:
    """维度5：API签名冲突"""
    issues = []
    score = 100.0
    common_api = set(api_a.keys()) & set(api_b.keys())
    critical_names = 0
    for name in common_api:
        paths_a = api_a[name]
        paths_b = api_b[name]
        # 如果同一个名称在两边都有多个定义，先看是不是不同包
        critical_names += 1
        issues.append(Issue("API冲突", "WARNING",
            f"公开名称冲突: {name}",
            f"A: {paths_a[0]}\nB: {paths_b[0]}",
            f"如果两者在不同包下——安全。如果在同包下——必须重命名。"))
    if critical_names > 0:
        score = max(20, 100 - critical_names * 5)
    return DimensionScore("API冲突", score, 100, issues)

def check_singleton_conflict(singletons_a: List, singletons_b: List) -> DimensionScore:
    """维度6：结构兼容（单例/全局状态）"""
    issues = []
    score = 100.0
    # 静态单例（非 companion 的 object）
    static_a = [s for s in singletons_a if s[1].startswith('object:')]
    static_b = [s for s in singletons_b if s[1].startswith('object:')]
    if len(static_a) > 0 and len(static_b) > 0:
        # 检查是否有同名的静态单例
        names_a = {s[1].replace('object: ', '') for s in static_a}
        names_b = {s[1].replace('object: ', '') for s in static_b}
        overlap = names_a & names_b
        if overlap:
            issues.append(Issue("结构冲突", "CRITICAL",
                f"静态单例冲突: {', '.join(overlap)}",
                f"同名单例在不同项目意味着不同的全局状态——合并后状态混乱。",
                f"重命名或合并为统一的配置管理单例。"))
            score = 30
        elif len(static_a) + len(static_b) > 20:
            issues.append(Issue("结构冲突", "WARNING",
                f"双方共有 {len(static_a) + len(static_b)} 个静态单例",
                f"大量单例暗示全局状态依赖——整合时容易互相污染。",
                f"考虑依赖注入替代静态单例，或至少文档化每个单例的职责边界。"))
            score = 70
    return DimensionScore("结构冲突", score, 100, issues)

# ═══════════════════════════════════════
# 报告生成
# ═══════════════════════════════════════

class ProjectData:
    """一次项目扫描的全部数据——各语言解析器往里填"""
    def __init__(self):
        self.lang: str = "unknown"
        self.path: str = ""
        # Kotlin
        self.gradle_files: List[str] = []
        self.kt_files: List[str] = []
        self.kotlin_version: Optional[str] = None
        self.kotlin_deps: Dict[str, str] = {}
        self.main_funcs: List[Tuple[str, str]] = []
        self.objects: List[Tuple[str, str]] = []
        self.public_api: Dict[str, List[str]] = {}
        self.packages: Dict[str, List[str]] = {}
        self.singletons: List[Tuple[str, str]] = []
        # Node.js
        self.package_json: Optional[str] = None
        self.node_version: Optional[str] = None
        self.node_main: Optional[str] = None
        self.node_deps: Dict[str, str] = {}
        self.js_files: List[str] = []
        self.exports: Dict[str, List[str]] = {}
        self.globals: Dict[str, List[str]] = {}

def scan_project(project_path: str) -> ProjectData:
    """扫描一个项目，返回 ProjectData。只做采集，不做判断。"""
    data = ProjectData()
    data.path = project_path
    data.lang = detect_project_type(project_path)
    if data.lang == "kotlin":
        data.gradle_files = find_gradle_files(project_path)
        data.kt_files = find_kt_files(project_path)
        data.kotlin_version = parse_kotlin_version(data.gradle_files)
        data.kotlin_deps = parse_kotlin_deps(data.gradle_files)
        data.main_funcs = find_main_functions(data.kt_files)
        data.objects = find_object_declarations(data.kt_files)
        data.public_api = find_public_api(data.kt_files)
        data.packages = find_package_declarations(data.kt_files)
        data.singletons = find_singletons(data.kt_files)
    elif data.lang == "nodejs":
        data.package_json = find_package_json(project_path)
        data.node_version = parse_node_version(data.package_json)
        data.node_main = parse_node_main(data.package_json)
        data.node_deps = parse_node_deps(data.package_json)
        data.js_files = find_js_files(project_path)
        data.exports = find_node_exports(data.js_files)
        data.globals = find_node_globals(data.js_files)
    return data

def dispatch_check(a: ProjectData, b: ProjectData) -> List[DimensionScore]:
    """语言分派器：根据两个 ProjectData 的语言，分派到对应的检测器"""
    if a.lang == "kotlin" and b.lang == "kotlin":
        return [
            check_kotlin_version(a.kotlin_version, b.kotlin_version),
            check_entry_conflict(a.main_funcs, b.main_funcs),
            check_dependency_conflict(a.kotlin_deps, b.kotlin_deps),
            check_namespace_conflict(a.objects, b.objects, a.packages, b.packages),
            check_api_conflict(a.public_api, b.public_api),
            check_singleton_conflict(a.singletons, b.singletons),
        ]
    elif a.lang == "nodejs" and b.lang == "nodejs":
        return [
            check_node_runtime(a.node_version, b.node_version),
            check_node_entry(a.node_main, b.node_main, len(a.js_files), len(b.js_files)),
            check_node_deps(a.node_deps, b.node_deps),
            check_node_exports(a.exports, b.exports),
            check_node_globals(a.globals, b.globals),
            check_node_architecture(a.package_json, b.package_json),
        ]
    else:
        issues = [check_cross_lang(a.lang, b.lang)]
        if a.lang == "kotlin":
            issues.append(Issue("语言信息", "INFO", f"项目A (Kotlin)",
                f"Kotlin版本: {a.kotlin_version or '未知'} | 文件: {len(a.kt_files)}个.kt", ""))
        elif a.lang == "nodejs":
            issues.append(Issue("语言信息", "INFO", f"项目A (Node.js)",
                f"Node版本: {a.node_version or '未声明'} | 文件: {len(a.js_files)}个.js", ""))
        return [DimensionScore("跨语言兼容", 85, 100, issues)]

def generate_report(project_a: str, project_b: str) -> CompatibilityReport:
    """入口：扫描→分派→评分，薄壳"""
    a = scan_project(project_a)
    b = scan_project(project_b)
    report = CompatibilityReport()
    report.lang_a = a.lang
    report.lang_b = b.lang
    report.dimensions = dispatch_check(a, b)
    report.overall_pct = round(
        sum(d.score for d in report.dimensions) / sum(d.max_score for d in report.dimensions) * 100, 1
    )
    return report
def print_report(report: CompatibilityReport, project_a: str, project_b: str):
    """格式化输出报告"""
    name_a = os.path.basename(project_a.rstrip('/'))
    name_b = os.path.basename(project_b.rstrip('/'))

    print("═" * 64)
    print(f"  兼容度检测报告")
    print(f"  {name_a}  ⇄  {name_b}")
    print("═" * 64)
    print()

    # 总体评分
    grade = "🟢 原生级兼容" if report.overall_pct >= 95 else \
            "🟡 轻度适配后可嵌入" if report.overall_pct >= 80 else \
            "🟠 需中等改造" if report.overall_pct >= 60 else \
            "🔴 严重冲突，需架构级调整"

    print(f"  总体兼容度: {report.overall_pct}%  {grade}")
    print()

    # 六维雷达
    print("  ═══ 六维评分 ═══")
    print(f"  {'维度':<16} {'得分':>6} {'评级':<16}")
    print(f"  {'─'*16} {'─'*6} {'─'*16}")
    for d in report.dimensions:
        bar = '█' * int(d.pct / 10) + '░' * (10 - int(d.pct / 10))
        print(f"  {d.name:<16} {d.pct:>5.1f}% {d.grade():<16}")
    print()

    # 问题清单
    all_issues = []
    for d in report.dimensions:
        all_issues.extend(d.issues)

    if not all_issues:
        print("  ✅ 未发现兼容性问题。两个项目可以无缝合并。")
        return

    critical = [i for i in all_issues if i.severity == 'CRITICAL']
    warnings = [i for i in all_issues if i.severity == 'WARNING']
    infos = [i for i in all_issues if i.severity == 'INFO']

    print(f"  ═══ 问题清单: 🔴{len(critical)} 致命  🟡{len(warnings)} 告警  🔵{len(infos)} 提示 ═══")
    print()

    for i, issue in enumerate(all_issues, 1):
        icon = {'CRITICAL': '🔴', 'WARNING': '🟡', 'INFO': '🔵'}[issue.severity]
        print(f"  [{i}] {icon} [{issue.dimension}] {issue.title}")
        print(f"      {issue.detail}")
        if issue.fix:
            print(f"      → {issue.fix}")
        print()

    # 修复路线图
    print("  ═══ 修复路线图 ═══")
    print()
    if critical:
        print(f"  第一步（必须）：解决 {len(critical)} 个致命问题")
        for i in critical:
            print(f"    - {i.title}")
        print()
    if warnings:
        print(f"  第二步（建议）：处理 {len(warnings)} 个告警")
        print(f"    完成后预计兼容度 → {report.overall_pct + min(20, len(warnings) * 3):.0f}%+")
        print()

    target = 100 if report.overall_pct >= 60 else max(60, report.overall_pct + 15)
    print(f"  目标兼容度: {target}%+ → 可安全嵌入/整合")
    print()
    print("═" * 64)

# ═══════════════════════════════════════
# 入口
# ═══════════════════════════════════════

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print("用法: python3 compat_check.py <项目A路径> <项目B路径>")
        print("示例: python3 compat_check.py ../kotlin-head ../qitong-gateway")
        sys.exit(1)

    path_a = sys.argv[1]
    path_b = sys.argv[2]

    if not os.path.isdir(path_a):
        print(f"错误: {path_a} 不是有效目录")
        sys.exit(1)
    if not os.path.isdir(path_b):
        print(f"错误: {path_b} 不是有效目录")
        sys.exit(1)

    report = generate_report(path_a, path_b)
    print_report(report, path_a, path_b)
