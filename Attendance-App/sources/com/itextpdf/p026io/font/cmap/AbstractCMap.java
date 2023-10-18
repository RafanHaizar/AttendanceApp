package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.font.PdfEncodings;
import java.io.Serializable;
import java.util.ArrayList;

/* renamed from: com.itextpdf.io.font.cmap.AbstractCMap */
public abstract class AbstractCMap implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -9057458889624600915L;
    private String cmapName;
    private String ordering;
    private String registry;
    private int supplement;

    /* access modifiers changed from: package-private */
    public abstract void addChar(String str, CMapObject cMapObject);

    public String getName() {
        return this.cmapName;
    }

    /* access modifiers changed from: package-private */
    public void setName(String cmapName2) {
        this.cmapName = cmapName2;
    }

    public String getOrdering() {
        return this.ordering;
    }

    /* access modifiers changed from: package-private */
    public void setOrdering(String ordering2) {
        this.ordering = ordering2;
    }

    public String getRegistry() {
        return this.registry;
    }

    /* access modifiers changed from: package-private */
    public void setRegistry(String registry2) {
        this.registry = registry2;
    }

    public int getSupplement() {
        return this.supplement;
    }

    /* access modifiers changed from: package-private */
    public void setSupplement(int supplement2) {
        this.supplement = supplement2;
    }

    /* access modifiers changed from: package-private */
    public void addCodeSpaceRange(byte[] low, byte[] high) {
    }

    /* access modifiers changed from: package-private */
    public void addRange(String from, String to, CMapObject code) {
        byte[] a1 = decodeStringToByte(from);
        byte[] a2 = decodeStringToByte(to);
        if (a1.length != a2.length || a1.length == 0) {
            throw new IllegalArgumentException("Invalid map.");
        }
        byte[] sout = null;
        if (code.isString()) {
            sout = decodeStringToByte(code.toString());
        }
        int start = byteArrayToInt(a1);
        int end = byteArrayToInt(a2);
        for (int k = start; k <= end; k++) {
            intToByteArray(k, a1);
            String mark = PdfEncodings.convertToString(a1, (String) null);
            if (code.isArray()) {
                addChar(mark, ((ArrayList) code.getValue()).get(k - start));
            } else if (code.isNumber()) {
                addChar(mark, new CMapObject(4, Integer.valueOf((((Integer) code.getValue()).intValue() + k) - start)));
            } else if (code.isString()) {
                addChar(mark, new CMapObject(2, sout));
                if (sout != null) {
                    intToByteArray(byteArrayToInt(sout) + 1, sout);
                } else {
                    throw new AssertionError();
                }
            } else {
                continue;
            }
        }
    }

    public static byte[] decodeStringToByte(String range) {
        byte[] bytes = new byte[range.length()];
        for (int i = 0; i < range.length(); i++) {
            bytes[i] = (byte) range.charAt(i);
        }
        return bytes;
    }

    /* access modifiers changed from: protected */
    public String toUnicodeString(String value, boolean isHexWriting) {
        byte[] bytes = decodeStringToByte(value);
        if (isHexWriting) {
            return PdfEncodings.convertToString(bytes, PdfEncodings.UNICODE_BIG_UNMARKED);
        }
        if (bytes.length >= 2 && bytes[0] == -2 && bytes[1] == -1) {
            return PdfEncodings.convertToString(bytes, PdfEncodings.UNICODE_BIG);
        }
        return PdfEncodings.convertToString(bytes, PdfEncodings.PDF_DOC_ENCODING);
    }

    private static void intToByteArray(int n, byte[] b) {
        for (int k = b.length - 1; k >= 0; k--) {
            b[k] = (byte) n;
            n >>>= 8;
        }
    }

    private static int byteArrayToInt(byte[] b) {
        int n = 0;
        for (byte b2 : b) {
            n = (n << 8) | (b2 & 255);
        }
        return n;
    }
}
