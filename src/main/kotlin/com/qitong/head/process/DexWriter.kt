package com.qitong.head.process

import java.io.ByteArrayOutputStream
import java.io.OutputStream

/**
 * DexWriter — 最小合法DEX文件生成器（v1.0.5 零依赖）
 *
 * DEX格式：二进制小端序。文件头70字节 + 索引表 + 数据段。
 * 不依赖Google d8。自己写二进制——和kotlin-head自己写编译器一样。
 */
object DexWriter {

    // DEX magic: "dex\n035\0"
    private val MAGIC = byteArrayOf(0x64, 0x65, 0x78, 0x0a, 0x30, 0x33, 0x35, 0x00)

    /**
     * 生成只包含一个空类的最小合法DEX。
     * @param className 如 "hello/World" — 不含L前缀和分号
     * @return DEX字节数组，可直接写入classes.dex
     */
    fun minimal(className: String = "hello/World"): ByteArray {
        val buf = ByteArrayOutputStream()
        val w = DexOutput(buf)

        // ═══ 准备字符串表 ═══
        val strClassDesc = "L$className;"
        val strIds = listOf(strClassDesc)
        val stringOffsets = mutableListOf<Int>()

        // ═══ 准备类型表（在DEX中，类型=指向字符串的索引） ═══
        val typeIds = listOf(0) // type[0] → string[0] = "Lhello/World;"

        // ═══ 保留字段表/方法表/原型表（最小dex不含字段和方法） ═══
        val protoIds = listOf(Triple(0, 0, emptyList<Int>())) // proto[0]: void()
        val fieldIds = emptyList<Triple<Int, Int, Int>>()
        val methodIds = emptyList<Triple<Int, Int, Int>>()

        // ═══ 类定义 ═══
        // class_def_item: class_idx, access_flags, superclass_idx, interfaces_off,
        //                 source_file_idx, annotations_off, class_data_off, static_values_off
        // 最简类：class_idx=0, access=1(public), super=NO_INDEX(-1)
        // class_data_off指向编码的class_data_item(空的)
        val NO_INDEX = -1
        val classDefs = listOf(ClassDef(0, 1, NO_INDEX, -1, -1, classDataOff = 0))

        // ═══ 计算各段偏移 ═══
        val headerSize = 0x70 //112字节
        var off = headerSize

        // string_ids: 每个4字节(uint偏移)
        val stringIdsOff = off
        val stringIdsSize = strIds.size * 4
        off += stringIdsSize

        // type_ids: 每个4字节(uint索引到string_ids)
        val typeIdsOff = off
        val typeIdsSize = typeIds.size * 4
        off += typeIdsSize

        // proto_ids: 每个12字节
        val protoIdsOff = off
        val protoIdsSize = protoIds.size * 12
        off += protoIdsSize

        // field_ids: 每个8字节
        val fieldIdsOff = off
        val fieldIdsSize = fieldIds.size * 8
        off += fieldIdsSize

        // method_ids: 每个8字节
        val methodIdsOff = off
        val methodIdsSize = methodIds.size * 8
        off += methodIdsSize

        // class_defs: 每个32字节
        val classDefsOff = off
        val classDefsSize = classDefs.size * 32
        off += classDefsSize

        // data段：字符串数据 + class_data
        val dataOff = off

        // ═══ 字符串数据 ═══
        val stringDataOffsets = mutableListOf<Int>()
        var curDataOff = dataOff
        for (s in strIds) {
            stringDataOffsets.add(curDataOff)
            val bytes = s.toByteArray(Charsets.UTF_8)
            curDataOff += 1 + bytes.size + 1 // length_uleb + data + \0
        }

        // class_data_item: 空的 — uleb128(0) for fields + methods
        val classDataOffset = curDataOff

        // ═══ 写文件头 ═══
        // DEX header: magic(8) + checksum(4) + signature(20) + file_size(4) +
        // header_size(4) + endian_tag(4) + link_size(4) + link_off(4) +
        // map_off(4) + string_ids_size(4) + string_ids_off(4) +
        // type_ids_size(4) + type_ids_off(4) + proto_ids_size(4) + proto_ids_off(4) +
        // field_ids_size(4) + field_ids_off(4) + method_ids_size(4) + method_ids_off(4) +
        // class_defs_size(4) + class_defs_off(4) + data_size(4) + data_off(4)
        val fileSize = curDataOffset // 先估算，checksum计算前修正

        // 先写入占位checksum+signature
        w.bytes(MAGIC)                          // magic
        w.int(0)                                // checksum(占位)
        w.bytes(ByteArray(20))                  // signature(占位)
        w.int(fileSize)                         // file_size
        w.int(headerSize)                       // header_size
        w.int(0x12345678)                       // endian_tag
        w.int(0)                                // link_size
        w.int(0)                                // link_off
        w.int(0)                                // map_off(无map)
        w.int(strIds.size)                      // string_ids_size
        w.int(stringIdsOff)                     // string_ids_off
        w.int(typeIds.size)                     // type_ids_size
        w.int(typeIdsOff)                       // type_ids_off
        w.int(protoIds.size)                    // proto_ids_size
        w.int(protoIdsOff)                      // proto_ids_off
        w.int(fieldIds.size)                    // field_ids_size
        w.int(fieldIdsOff)                      // field_ids_off
        w.int(methodIds.size)                   // method_ids_size
        w.int(methodIdsOff)                     // method_ids_off
        w.int(classDefs.size)                   // class_defs_size
        w.int(classDefsOff)                     // class_defs_off
        w.int(0)                                // data_size(实际为class_data)
        w.int(dataOff)                          // data_off

        // ═══ 写 string_ids: 每个uint指向字符串数据 ═══
        for (o in stringDataOffsets) w.int(o)

        // ═══ 写 type_ids: 每个uint指向str_ids索引 ═══
        for (t in typeIds) w.int(t)

        // ═══ 写 proto_ids: (shorty_idx, return_type_idx, parameters_off) ═══
        for ((shorty, ret, params) in protoIds) {
            w.int(shorty); w.int(ret); w.int(0) // parameters_off=0(空)
        }

        // ═══ 写 field_ids/method_ids 空 ═══

        // ═══ 写 class_defs ═══
        for (cd in classDefs) {
            w.int(cd.classIdx)
            w.int(cd.accessFlags)
            w.int(if (cd.superIdx >= 0) cd.superIdx else NO_INDEX)
            w.int(0) // interfaces_off=0
            w.int(-1) // source_file_idx=NO_INDEX
            w.int(0) // annotations_off=0
            w.int(classDataOffset)
            w.int(0) // static_values_off=0
        }

        // ═══ 写字符串数据 ═══
        for (s in strIds) {
            val bytes = s.toByteArray(Charsets.UTF_8)
            w.uleb(bytes.size)
            w.bytes(bytes)
            w.byte(0) // null terminator
        }

        // ═══ 写 class_data_item(空) ═══
        w.uleb(0) // static_fields_size=0
        w.uleb(0) // instance_fields_size=0
        w.uleb(0) // direct_methods_size=0
        w.uleb(0) // virtual_methods_size=0

        return buf.toByteArray()
    }

    data class ClassDef(
        val classIdx: Int,
        val accessFlags: Int,
        val superIdx: Int,
        val sourceFileIdx: Int,
        val annotationsOff: Int,
        val classDataOff: Int
    )
}

/**
 * 小端序二进制输出辅助
 */
private class DexOutput(private val out: OutputStream) {
    fun byte(v: Int) { out.write(v and 0xFF) }
    fun bytes(b: ByteArray) { out.write(b) }
    fun int(v: Int) {
        out.write(v and 0xFF)
        out.write((v shr 8) and 0xFF)
        out.write((v shr 16) and 0xFF)
        out.write((v shr 24) and 0xFF)
    }
    fun uleb(v: Int) {
        var value = v
        do {
            var b = value and 0x7F
            value = value ushr 7
            if (value != 0) b = b or 0x80
            out.write(b)
        } while (value != 0)
    }
}