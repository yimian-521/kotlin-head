const { Lexer } = require('./lexer')

const src = "fun fibonacci(n:Int):Int { if(n<=1)return n else return fibonacci(n-1)+fibonacci(n-2) }"
const N = 500_000

// 预热
for(let w=0;w<100;w++) new Lexer(src).tokenize()

// 测量
const t0=process.hrtime.bigint()
for(let i=0;i<N;i++) new Lexer(src).tokenize()
const ns=Number(process.hrtime.bigint()-t0)/N

// 空循环
let d=0; const t1=process.hrtime.bigint()
for(let i=0;i<N;i++){d++}
const loop=Number(process.hrtime.bigint()-t1)/N

const tokens=new Lexer(src).tokenize()
console.log(`\nkotlin-head Node.js — 完整 Kotlin Lexer`)
console.log(`  源码: ${src.slice(0,40)}...`)
console.log(`  每次: ${ns.toFixed(0)}ns  (净:${(ns-loop).toFixed(0)}ns)`)
console.log(`  token数: ${tokens.length}`)
console.log(`\n对比:`)
console.log(`  JVM (kotlinc):   4,515ns`)
console.log(`  Node (完整移植):  ${ns.toFixed(0)}ns  → ${(4515/ns).toFixed(1)}x faster`)
console.log(`  C (gcc -O3):        55ns  → ${(55/ns).toFixed(1)}x (理论目标)`)
console.log(`  TCC (全链路):   19,800ns  (含Codegen)`)
console.log('\n  如果Kotlin/Native编译 -> <100ns 可达')