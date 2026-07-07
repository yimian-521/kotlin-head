#!/usr/bin/env node
// kotlin-head 分体式沙盒模拟器 v0.2.0
// 验证核心概念：开关门 / 烫伤不传父进程 / 冻结≠冻死 / 父进程死不掉
// v0.2.0: 小安审计修复——删activeProbe死存储/openDoor简化/checkParent注释/清理冗余

const DOOR = { OPEN: '🔓', CLOSED: '🔒', LOCKED: '🚫' };

class Sandbox {
  constructor() {
    this.door = DOOR.OPEN;
    this.burnLog = [];
    this.deadProbes = 0;
    this.parentAlive = true;      // 核心概念：永远不死，用于对比传统编译器必崩
  }

  // 🔓 开门：给探针一份副本，只读不写
  openDoor(probeId, code) {
    this.door = DOOR.OPEN;
    console.log(`  🔓 开门 → 探针#${probeId} 拿到代码副本（只读），进入沙盒`);
    return probeId; // 返回探针id供调用方记录
  }

  // 🔒 关门：探针超时/异常，门立刻锁死
  closeDoor(probeId, reason) {
    this.door = DOOR.CLOSED;
    if (reason) console.log(`  🔒 关门 → 探针#${probeId} ${reason}，门锁死，数据全部丢弃`);
  }

  // 🚫 锁死：永久阻断
  lockDoor(probeId) {
    this.door = DOOR.LOCKED;
    console.log(`  🚫 锁死 → 探针#${probeId} 通道永久阻断，脏数据锁在门那边`);
  }

  // 探针执行（在沙盒里跑）
  runProbe(probeId, code, isDangerous = false) {
    this.openDoor(probeId, code);
    this.closeDoor(probeId, '执行中关门');

    console.log(`  🫳 探针#${probeId} 执行中... (门已关)`);
    
    try {
      if (isDangerous) {
        throw new Error(`🔥 三级烫伤: imageUrl 为 null → QQ SDK 主线程回调崩`);
      }
      console.log(`  ✅ 探针#${probeId} 执行完成`);
      // 门在 closeDoor 后是 CLOSED，安全执行完回到 OPEN
      this.door = DOOR.OPEN;
      return { success: true, result: `探针#${probeId}: 安全通过 ✅` };
    } catch (e) {
      // 烫了！
      this.deadProbes++;
      const burn = {
        probeId,
        error: e.message,
        time: new Date().toISOString(),
        severity: e.message.includes('三级') ? '🔴 三级烫伤' : '🟡 烫伤'
      };
      this.burnLog.push(burn);
      
      console.log(`  ${burn.severity} → 探针#${probeId} 崩了`);
      console.log(`  💀 探针自毁，沙盒回收`);
      
      this.lockDoor(probeId); // 锁死，脏数据绝不回流
      return { success: false, burn };
    }
  }

  // 父进程状态检查
  checkParent() {
    return {
      alive: this.parentAlive,
      message: this.parentAlive 
        ? '✅ 父进程正常运行，完全不知道探针烫过'
        : '💀 父进程崩溃（传统编译器会这样）'
    };
  }

  report() {
    console.log('\n📊 ========== 分体式沙盒报告 ==========');
    console.log(` 父进程状态: ${this.checkParent().message}`);
    console.log(` 探针死亡数: ${this.deadProbes}`);
    console.log(` 烫伤记录: ${this.burnLog.length} 条`);
    this.burnLog.forEach((b, i) => {
      console.log(`   [${i+1}] 探针#${b.probeId} | ${b.severity} | ${b.error.split(':')[0]}`);
    });
    console.log(` 结论: 探针死 ${this.deadProbes} 个，父进程一次没崩。试探可以永远继续。`);
    console.log('==========================================\n');
  }
}

// ========== 场景模拟 ==========
const args = process.argv.slice(2);
const mode = args[0] || '--demo';

if (mode === '--demo' || mode === '--all') {
  console.log('🧪 场景1: 120次试探——传统 vs 分体式\n');
  
  // 传统编译器：烫一次就死
  console.log('--- 传统编译器（大盒子）---');
  let traditionalCrashes = 0;
  for (let i = 0; i < 120; i++) {
    if (i === 0) {
      console.log(`  试探#1 → 🔥 烫了 → 编译器崩了 → 重启 → 状态全丢`);
      traditionalCrashes++;
      console.log(`  ⚠️ 剩余 119 次试探永远无法完成`);
      break;
    }
  }
  console.log(`  结果: ${traditionalCrashes} 次崩溃，试探完成率 ${(traditionalCrashes/120*100).toFixed(1)}%\n`);

  // 分体式沙盒
  console.log('--- 分体式沙盒（开关门）---');
  const sb = new Sandbox();
  const dangers = [1, 5, 13, 24, 46, 68, 90, 102, 116]; // 会烫伤的探针编号（1-based）
  
  for (let i = 0; i < 120; i++) {
    const probeId = i + 1;
    const isDanger = dangers.includes(probeId);
    const result = sb.runProbe(probeId, `testCode(${i})`, isDanger);
    
    if (!result.success) {
      console.log(`  💪 父进程: "刚才好像有人敲了下门？不管了，继续编译"`);
      console.log(`  🔄 换条臂，继续试探...\n`);
    }
  }
  
  sb.report();
  console.log(`  结果: 120/120 试探完成 ✅ | ${sb.deadProbes} 个探针烫伤 | 父进程 0 次崩溃`);
  console.log(`  对比: 传统崩在第1次就死了，分体式跑完全程\n`);
}

if (mode === '--door' || mode === '--all') {
  console.log('🚪 场景2: 门的三态演示\n');
  
  console.log('🔓 开门 = 父进程说: "你可以读 ShareCard 的类型定义，但只能读副本，不能写原文件"');
  console.log('   → 探针拿到副本，在沙盒里怎么改都不影响编译器本体\n');
  
  console.log('🔒 关门 = 父进程检测到探针超时/异常');
  console.log('   → 门立刻锁死，探针带回的任何数据全部丢弃');
  console.log('   → 沙盒区域内存在过的东西全部清理\n');
  
  console.log('🚪 门方向 = 只出不进');
  console.log('   → 数据可以从编译器流向探针（给它副本）');
  console.log('   → 不能从探针流向编译器（脏数据锁外面）\n');
}

if (mode === '--freeze' || mode === '--all') {
  console.log('❄️ 场景3: 冻结 ≠ 冻死\n');
  
  console.log('冻死（错）：把整个文件区域冰封 → 父进程骂街 "谁把我冻了？我还在编译呢！"');
  console.log('冻结（对）：只关探针回来的路 → 父进程继续干活，探针在门那边自生自灭\n');
  
  console.log('实际效果:');
  console.log('  编译 MainScreen.kt');
  console.log('  ├── 父进程: 正在编译 EditScreen.kt... ✅ 正常');
  console.log('  ├── 父进程: 正在编译 CardList.kt... ✅ 正常');
  console.log('  └── 探针#3: 在门那边烫了 💀 → 门锁死 → 父进程完全不知道');
  console.log('  结论: 冻结只冻探针回来的路，父进程该干嘛干嘛\n');
}

if (mode === '--help') {
  console.log('kotlin-head 分体式沙盒模拟器 v0.2.0');
  console.log('');
  console.log('用法: node tools/sandbox_sim.js [模式]');
  console.log('');
  console.log('  --demo    120次试探对比（传统 vs 分体式）');
  console.log('  --door    门的三态演示（开门/关门/门方向）');
  console.log('  --freeze  冻结≠冻死演示');
  console.log('  --all     跑全部场景');
  console.log('  --help    帮助');
}
