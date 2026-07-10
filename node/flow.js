// kotlin-head Node.js — 独立流式处理引擎 (零继承)
// 分块喂入 →逐个token产出 →像翻书，随时暂停继续

const KWS = new Set(['fun','val','var','class','data','if','else','return',
  'when','for','while','object','interface','enum','override','import','package',
  'true','false','try','catch','finally','as','companion'])

function createFlow(opts = {}) {
    let buf = ''; let i = 0; let line = 1; let col = 1
    let onToken = null; let onEnd = null; let paused = false; let done = false

    const isA = ch => (ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z')||ch==='_'
    const isD = ch => ch>='0'&&ch<='9'

    function emit(type, text, l, c) { if (onToken) onToken({type, text, line:l, col:c}) }
    function flush() { if (!done) { done = true; if (onEnd) onEnd() } }

    function pump() {
        while (i < buf.length && !paused) {
            const c = buf[i], l = line, _c = col
            if (c===' '||c==='\t') { i++; col++; continue }
            if (c==='\n') { i++; line++; col=1; continue }
            if (c==='/'&&buf[i+1]==='/') { while(i<buf.length&&buf[i]!=='\n')i++; continue }
            if (c==='/'&&buf[i+1]==='*') {
                i+=2; while(i<buf.length&&!(buf[i]==='*'&&buf[i+1]==='/')){if(buf[i]==='\n'){line++;col=1}else col++;i++} i+=2;col+=2; continue
            }
            if (c==='"') {
                i++; col++; let s=i
                while(i<buf.length&&buf[i]!=='"'){if(buf[i]==='\\\\'){i++;col++}i++;col++}
                emit('STR','"'+buf.slice(s,i)+'"',l,_c)
                if(i<buf.length){i++;col++}; continue
            }
            if (isD(c)) { let s=i; while(i<buf.length&&isD(buf[i])){i++;col++}; emit('INT',buf.slice(s,i),l,_c); continue }
            if (isA(c)) { let s=i; while(i<buf.length&&(isA(buf[i])||isD(buf[i]))){i++;col++}; const w=buf.slice(s,i); emit(KWS.has(w)?'KW':'ID',w,l,_c); continue }

            i++; col++
            if (c==='-'&&buf[i]==='>'){i++;col++;emit('ARROW','->',l,_c)}
            else if (c==='='&&buf[i]==='='){i++;col++;emit('EQEQ','==',l,_c)}
            else if (c==='!'&&buf[i]==='='){i++;col++;emit('NEQ','!=',l,_c)}
            else if (c==='<'&&buf[i]==='='){i++;col++;emit('LTEQ','<=',l,_c)}
            else if (c==='>'&&buf[i]==='='){i++;col++;emit('GTEQ','>=',l,_c)}
            else emit('SYM',c,l,_c)
        }
        if (i>1024) { buf=buf.slice(i); i=0 }
    }

    return {
        write(chunk) { buf+=chunk; pump() },
        end()       { done=true; pump(); flush() },
        pause()     { paused=true },
        resume()    { paused=false; pump() },
        onToken(fn) { onToken=fn; return this },
        onEnd(fn)   { onEnd=fn; return this },
        reset()     { buf=''; i=0; line=1; col=1; paused=false; done=false; return this },
        pos()       { return {line, col, i} }
    }
}

module.exports = { createFlow }