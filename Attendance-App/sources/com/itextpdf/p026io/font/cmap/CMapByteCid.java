package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.IOException;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.itextpdf.io.font.cmap.CMapByteCid */
public class CMapByteCid extends AbstractCMap {
    private static final long serialVersionUID = 8843696844192313477L;
    private List<int[]> planes;

    /* renamed from: com.itextpdf.io.font.cmap.CMapByteCid$Cursor */
    protected static class Cursor {
        public int length;
        public int offset;

        public Cursor(int offset2, int length2) {
            this.offset = offset2;
            this.length = length2;
        }
    }

    public CMapByteCid() {
        ArrayList arrayList = new ArrayList();
        this.planes = arrayList;
        arrayList.add(new int[256]);
    }

    /* access modifiers changed from: package-private */
    public void addChar(String mark, CMapObject code) {
        if (code.isNumber()) {
            encodeSequence(decodeStringToByte(mark), ((Integer) code.getValue()).intValue());
        }
    }

    public String decodeSequence(byte[] cidBytes, int offset, int length) {
        StringBuilder sb = new StringBuilder();
        Cursor cursor = new Cursor(offset, length);
        while (true) {
            int decodeSingle = decodeSingle(cidBytes, cursor);
            int cid = decodeSingle;
            if (decodeSingle < 0) {
                return sb.toString();
            }
            sb.append((char) cid);
        }
    }

    /* access modifiers changed from: protected */
    public int decodeSingle(byte[] cidBytes, Cursor cursor) {
        int end = cursor.offset + cursor.length;
        int currentPlane = 0;
        while (cursor.offset < end) {
            int i = cursor.offset;
            cursor.offset = i + 1;
            cursor.length--;
            int cid = this.planes.get(currentPlane)[cidBytes[i] & 255];
            if ((32768 & cid) == 0) {
                return cid;
            }
            currentPlane = cid & 32767;
        }
        return -1;
    }

    private void encodeSequence(byte[] seq, int cid) {
        int size = seq.length - 1;
        int nextPlane = 0;
        int idx = 0;
        while (idx < size) {
            int[] plane = this.planes.get(nextPlane);
            int one = seq[idx] & 255;
            int c = plane[one];
            if (c == 0 || (c & 32768) != 0) {
                if (c == 0) {
                    this.planes.add(new int[256]);
                    c = (this.planes.size() - 1) | 32768;
                    plane[one] = c;
                }
                nextPlane = c & 32767;
                idx++;
            } else {
                throw new IOException("Inconsistent mapping.");
            }
        }
        int[] plane2 = this.planes.get(nextPlane);
        int one2 = seq[size] & 255;
        if ((32768 & plane2[one2]) == 0) {
            plane2[one2] = cid;
            return;
        }
        throw new IOException("Inconsistent mapping.");
    }
}
