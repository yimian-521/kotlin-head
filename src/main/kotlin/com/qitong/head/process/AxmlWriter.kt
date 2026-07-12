package com.qitong.head.process

import java.io.ByteArrayOutputStream

/**
 * AxmlWriter — 最小Android二进制XML生成器（v1.0.5 零依赖）
 * AXML格式是Android编译后的Manifest格式，APK必须用它而非纯文本XML。
 */
object AxmlWriter {

    fun minimalManifest(packageName: String = "hello", label: String = "kotlin-head"): ByteArray {
        val buf = ByteArrayOutputStream()
        val w = BinOut(buf)

        // 收集所有需要的UTF-16字符串
        val strings = mutableListOf(
            "manifest", "package", "uses-sdk", "application", "activity", "intent-filter",
            "action", "category", "android", packageName, "android:versionCode", "android:versionName",
            "android:minSdkVersion", "android:targetSdkVersion", "android:label", label,
            "android:name", ".MainActivity", "android:exported", "android.intent.action.MAIN",
            "android.intent.category.LAUNCHER", "http://schemas.android.com/apk/res/android",
            "1", "21", "35"
        )

        // ═══ 计算StringPool大小 ═══
        val stringOffsets = mutableListOf<Int>()
        var strDataOff = 8 + 4 + 4 + 4 + 4 + 4 + 4  // chunk header + counts + flags + offsets
        strDataOff += strings.size * 4  // string offset table
        for (s in strings) {
            stringOffsets.add(strDataOff)
            strDataOff += 1 + s.toByteArray(Charsets.UTF_8).size + 1  // u8len + UTF8 + null
        }
        
        // StringPool chunk header
        w.int(0x001C0001)  // RES_STRING_POOL_TYPE
        w.int(strDataOff)   // chunk size
        w.int(strings.size) // stringCount
        w.int(0)            // styleCount
        w.int(0x100)        // flags: UTF-8
        w.int(28 + strings.size * 4) // stringsStart (from chunk start)
        w.int(0)            // stylesStart

        // String offset table
        for (o in stringOffsets) w.int(o - 8) // offsets relative to chunk start

        // String data
        for (s in strings) {
            val b = s.toByteArray(Charsets.UTF_8)
            w.byte(b.size)
            w.bytes(b)
            w.byte(0)
        }

        // XML nodes — 最小合法的节点树
        // 树结构: manifest → uses-sdk → application → activity → intent-filter → action + category
        // 每个节点: chunk(startElement) + attributes + chunk(endElement) + children

        val nsUri = strings.indexOf("http://schemas.android.com/apk/res/android") // idx=21
        val nsName = strings.indexOf("android")  // idx=8

        // ═══ XmlResourceMap（必须有，0x0180） ═══
        w.int(0x00080180)
        w.int(8)  // chunk size

        // ═══ START_NAMESPACE: xmlns:android ═══
        xmlNode(w, 0x00100100, -1, -1, nsUri, nsName)

        // ═══ manifest ═══
        w.int(0x00100102) // START_ELEMENT
        w.int(0)          // chunk size (placeholder)
        w.int(-1)         // line number
        w.int(-1)         // comment index
        w.int(0)          // namespace uri = strings[21]
        w.int(strings.indexOf("manifest")) // name = idx 0
        w.int(0x00140014) // attrStart/flags
        w.int(2)          // attributeCount
        w.int(0)          // classAttribute
        // attr: package
        w.int(nsName); w.int(strings.indexOf("package")); w.int(strings.indexOf(packageName))
        w.short(8); w.short(0); w.int(0x03000008) // typed value: string ref
        // attr: versionCode
        w.int(nsName); w.int(strings.indexOf("android:versionCode")); w.int(strings.indexOf("1"))
        w.short(16); w.short(0); w.int(0x10000001) // attr: versionName
        w.int(nsName); w.int(strings.indexOf("android:versionName")); w.int(strings.indexOf("1"))
        w.short(16); w.short(0); w.int(0x03000008)

        // ═══ uses-sdk ═══
        simpleElem(w, strings, nsName, "uses-sdk",
            "android:minSdkVersion" to "21",
            "android:targetSdkVersion" to "35"
        )
        endElem(w, nsUri, "uses-sdk")

        // ═══ application ═══
        simpleElem(w, strings, nsName, "application", "android:label" to label)
        // ═══ activity ═══
        simpleElem(w, strings, nsName, "activity", "android:name" to ".MainActivity", "android:exported" to "true")
        // ═══ intent-filter ═══
        emptyElem(w, strings, "intent-filter")
        simpleElem(w, strings, nsName, "action", "android:name" to "android.intent.action.MAIN")
        endElem(w, nsUri, "action")
        simpleElem(w, strings, nsName, "category", "android:name" to "android.intent.category.LAUNCHER")
        endElem(w, nsUri, "category")
        endElem(w, nsUri, "intent-filter")
        endElem(w, nsUri, "activity")
        endElem(w, nsUri, "application")
        endElem(w, nsUri, "manifest")

        // END_NAMESPACE
        xmlNode(w, 0x00100101, -1, -1, nsUri, nsName)

        return buf.toByteArray()
    }

    private fun simpleElem(w: BinOut, strs: List<String>, nsName: Int, tag: String, vararg attrs: Pair<String, String>) {
        w.int(0x00100102)
        val sizePos = w.pos()
        w.int(0) // placeholder
        w.int(-1); w.int(-1)
        w.int(0); w.int(strs.indexOf(tag))
        w.int(0x00140014)
        w.int(attrs.size); w.int(0)
        for ((aname, aval) in attrs) {
            w.int(nsName); w.int(strs.indexOf(aname)); w.int(strs.indexOf(aval))
            w.short(8); w.short(0); w.int(0x03000008) // string ref
        }
    }

    private fun endElem(w: BinOut, ns: Int, tag: String) {
        w.int(0x00100103); w.int(16)
        w.int(-1); w.int(-1); w.int(ns); w.int(0)
    }

    private fun emptyElem(w: BinOut, strs: List<String>, tag: String) {
        w.int(0x00100102); w.int(0); w.int(-1); w.int(-1)
        w.int(0); w.int(strs.indexOf(tag))
        w.int(0x00140014); w.int(0); w.int(0)
    }

    private fun xmlNode(w: BinOut, type: Int, line: Int = -1, comment: Int = -1, ns: Int, name: Int) {
        w.int(type); w.int(16 + 4*4)
        w.int(line); w.int(comment); w.int(ns); w.int(name)
    }

    private class BinOut(private val out: java.io.OutputStream) {
        private var count = 0
        fun pos() = count
        fun int(v: Int) {
            out.write(v and 0xFF); out.write((v shr 8) and 0xFF)
            out.write((v shr 16) and 0xFF); out.write((v shr 24) and 0xFF)
            count += 4
        }
        fun byte(v: Int) { out.write(v and 0xFF); count++ }
        fun bytes(b: ByteArray) { out.write(b); count += b.size }
        fun short(v: Int) {
            out.write(v and 0xFF); out.write((v shr 8) and 0xFF)
            count += 2
        }
    }
}