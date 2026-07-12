package com.qitong.head.process

import java.io.ByteArrayOutputStream
import java.io.OutputStream

/**
 * DexWriter — 零依赖DEX生成器（v1.0.5）
 * 支持: minimal(空类) / withMain(可运行类)
 */
object DexWriter {
    private val MAGIC = byteArrayOf(0x64, 0x65, 0x78, 0x0a, 0x30, 0x33, 0x35, 0x00)
    private val NO_INDEX = 0xFFFFFFFF.toInt()

    fun minimal(className: String = "hello/World"): ByteArray {
        val buf = ByteArrayOutputStream(); val w = DexOutput(buf)
        val strings = listOf("L$className;")
        val headerSize = 0x70
        val strIdsOff = headerSize; val typeIdsOff = strIdsOff + 4; val protoIdsOff = typeIdsOff + 4
        val classDefsOff = protoIdsOff + 12; val dataOff = classDefsOff + 32

        var cur = dataOff; val sdo = mutableListOf<Int>()
        for (s in strings) { sdo.add(cur); cur += 1 + s.toByteArray(Charsets.UTF_8).size + 1 }
        val classDataOff = cur; val fileSize = classDataOff + 4

        w.bytes(MAGIC); w.int(0); w.bytes(ByteArray(20))
        w.int(fileSize); w.int(headerSize); w.int(0x12345678)
        w.int(0); w.int(0); w.int(0)
        w.int(strings.size); w.int(strIdsOff); w.int(1); w.int(typeIdsOff)
        w.int(1); w.int(protoIdsOff); w.int(0); w.int(0); w.int(0); w.int(0)
        w.int(1); w.int(classDefsOff); w.int(fileSize - dataOff); w.int(dataOff)

        for (o in sdo) w.int(o); w.int(0)
        w.int(1); w.int(0); w.int(0)
        w.int(0); w.int(1); w.int(NO_INDEX); w.int(0); w.int(NO_INDEX); w.int(0)
        w.int(classDataOff); w.int(0)

        for (s in strings) { val b = s.toByteArray(Charsets.UTF_8); w.uleb(b.size); w.bytes(b); w.byte(0) }
        w.uleb(0); w.uleb(0); w.uleb(0); w.uleb(0)
        return buf.toByteArray()
    }

    fun withMain(className: String = "hello/World"): ByteArray {
        val buf = ByteArrayOutputStream(); val w = DexOutput(buf)
        val strings = listOf("L$className;", "V", "VL", "main", "([Ljava/lang/String;)V", "Ljava/lang/Object;", "<init>")
        val headerSize = 0x70
        val strIdsOff = headerSize; val typeIdsOff = strIdsOff + strings.size * 4
        val protoIdsOff = typeIdsOff + 2 * 4; val methodIdsOff = protoIdsOff + 1 * 12
        val classDefsOff = methodIdsOff + 1 * 8; val dataOff = classDefsOff + 1 * 32

        var cur = dataOff; val sdo = mutableListOf<Int>()
        for (s in strings) { sdo.add(cur); cur += 1 + s.toByteArray(Charsets.UTF_8).size + 1 }
        val codeOff = cur; val classDataOff = codeOff + 18  // 16byte code_header + 2byte insn

        // DEX header
        w.bytes(MAGIC); w.int(0); w.bytes(ByteArray(20))
        val fileSize = classDataOff + 10; w.int(fileSize); w.int(headerSize); w.int(0x12345678)
        w.int(0); w.int(0); w.int(0)
        w.int(strings.size); w.int(strIdsOff); w.int(2); w.int(typeIdsOff)
        w.int(1); w.int(protoIdsOff); w.int(0); w.int(0)
        w.int(1); w.int(methodIdsOff); w.int(1); w.int(classDefsOff)
        w.int(fileSize - dataOff); w.int(dataOff)

        // string_ids
        for (o in sdo) w.int(o)
        // type_ids: t[0]=s[0](本类), t[1]=s[5](Object)
        w.int(0); w.int(5)
        // proto_ids: shorty=s[1]="V", return=0(void), params_off=0
        w.int(1); w.int(0); w.int(0)
        // method_ids: type=0, proto=0, name=s[3]="main"
        w.short(0); w.short(0); w.int(3)
        // class_defs
        w.int(0); w.int(1); w.int(NO_INDEX); w.int(0)
        w.int(NO_INDEX); w.int(0); w.int(classDataOff); w.int(0)

        // string data
        for (s in strings) { val b = s.toByteArray(Charsets.UTF_8); w.uleb(b.size); w.bytes(b); w.byte(0) }

        // code_item: regs=1, ins=1, outs=0, tries=0, debug=0, insns=1(2 bytes)
        w.short(1); w.short(1); w.short(0); w.short(0)
        w.int(0); w.int(1)
        // return-void = 0x0e (little-endian: 0e00)
        w.byte(0x0e); w.byte(0x00)

        // class_data_item
        w.uleb(0); w.uleb(0); w.uleb(0); w.uleb(0)
        return buf.toByteArray()
    }

    private class DexOutput(private val out: OutputStream) {
        fun byte(v: Int) { out.write(v and 0xFF) }
        fun bytes(b: ByteArray) { out.write(b) }
        fun short(v: Int) { out.write(v and 0xFF); out.write((v shr 8) and 0xFF) }
        fun int(v: Int) {
            out.write(v and 0xFF); out.write((v shr 8) and 0xFF)
            out.write((v shr 16) and 0xFF); out.write((v shr 24) and 0xFF)
        }
        fun uleb(v: Int) {
            var value = v
            do { var b = value and 0x7F; value = value ushr 7; if (value != 0) b = b or 0x80; out.write(b) } while (value != 0)
        }
    }
}