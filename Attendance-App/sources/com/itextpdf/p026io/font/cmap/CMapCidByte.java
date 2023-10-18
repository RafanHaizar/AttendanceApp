package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.util.IntHashtable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.UByte;

/* renamed from: com.itextpdf.io.font.cmap.CMapCidByte */
public class CMapCidByte extends AbstractCMap {
    private static final long serialVersionUID = 4956059671207068672L;
    private final byte[] EMPTY = new byte[0];
    private List<byte[]> codeSpaceRanges = new ArrayList();
    private Map<Integer, byte[]> map = new HashMap();

    /* access modifiers changed from: package-private */
    public void addChar(String mark, CMapObject code) {
        if (code.isNumber()) {
            this.map.put(Integer.valueOf(((Integer) code.getValue()).intValue()), decodeStringToByte(mark));
        }
    }

    public byte[] lookup(int cid) {
        byte[] ser = this.map.get(Integer.valueOf(cid));
        if (ser == null) {
            return this.EMPTY;
        }
        return ser;
    }

    public IntHashtable getReversMap() {
        IntHashtable code2cid = new IntHashtable(this.map.size());
        for (Integer intValue : this.map.keySet()) {
            int cid = intValue.intValue();
            int byteCode = 0;
            for (byte b : this.map.get(Integer.valueOf(cid))) {
                byteCode = (byteCode << 8) + (b & UByte.MAX_VALUE);
            }
            code2cid.put(byteCode, cid);
        }
        return code2cid;
    }

    public List<byte[]> getCodeSpaceRanges() {
        return this.codeSpaceRanges;
    }

    /* access modifiers changed from: package-private */
    public void addCodeSpaceRange(byte[] low, byte[] high) {
        this.codeSpaceRanges.add(low);
        this.codeSpaceRanges.add(high);
    }
}
