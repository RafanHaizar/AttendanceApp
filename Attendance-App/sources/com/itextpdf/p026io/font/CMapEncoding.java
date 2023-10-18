package com.itextpdf.p026io.font;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.cmap.CMapCidByte;
import com.itextpdf.p026io.font.cmap.CMapCidUni;
import com.itextpdf.p026io.font.cmap.CMapLocationFromBytes;
import com.itextpdf.p026io.font.cmap.CMapParser;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.util.IntHashtable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import kotlin.UByte;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.font.CMapEncoding */
public class CMapEncoding implements Serializable {
    private static final List<byte[]> IDENTITY_H_V_CODESPACE_RANGES = Arrays.asList(new byte[][]{new byte[]{0, 0}, new byte[]{-1, -1}});
    private static final long serialVersionUID = 2418291066110642993L;
    private CMapCidByte cid2Code;
    private CMapCidUni cid2Uni;
    private String cmap;
    private IntHashtable code2Cid;
    private List<byte[]> codeSpaceRanges;
    private boolean isDirect;
    private String uniMap;

    public CMapEncoding(String cmap2) {
        this.cmap = cmap2;
        if (cmap2.equals(PdfEncodings.IDENTITY_H) || cmap2.equals(PdfEncodings.IDENTITY_V)) {
            this.isDirect = true;
        }
        this.codeSpaceRanges = IDENTITY_H_V_CODESPACE_RANGES;
    }

    public CMapEncoding(String cmap2, String uniMap2) {
        this.cmap = cmap2;
        this.uniMap = uniMap2;
        if (cmap2.equals(PdfEncodings.IDENTITY_H) || cmap2.equals(PdfEncodings.IDENTITY_V)) {
            this.cid2Uni = FontCache.getCid2UniCmap(uniMap2);
            this.isDirect = true;
            this.codeSpaceRanges = IDENTITY_H_V_CODESPACE_RANGES;
            return;
        }
        CMapCidByte cid2Byte = FontCache.getCid2Byte(cmap2);
        this.cid2Code = cid2Byte;
        this.code2Cid = cid2Byte.getReversMap();
        this.codeSpaceRanges = this.cid2Code.getCodeSpaceRanges();
    }

    public CMapEncoding(String cmap2, byte[] cmapBytes) {
        this.cmap = cmap2;
        CMapCidByte cMapCidByte = new CMapCidByte();
        this.cid2Code = cMapCidByte;
        try {
            CMapParser.parseCid(cmap2, cMapCidByte, new CMapLocationFromBytes(cmapBytes));
            this.code2Cid = this.cid2Code.getReversMap();
            this.codeSpaceRanges = this.cid2Code.getCodeSpaceRanges();
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(LogMessageConstant.FAILED_TO_PARSE_ENCODING_STREAM);
        }
    }

    public boolean isDirect() {
        return this.isDirect;
    }

    public boolean hasUniMap() {
        String str = this.uniMap;
        return str != null && str.length() > 0;
    }

    public String getRegistry() {
        if (isDirect()) {
            return "Adobe";
        }
        return this.cid2Code.getRegistry();
    }

    public String getOrdering() {
        if (isDirect()) {
            return "Identity";
        }
        return this.cid2Code.getOrdering();
    }

    public int getSupplement() {
        if (isDirect()) {
            return 0;
        }
        return this.cid2Code.getSupplement();
    }

    public String getUniMapName() {
        return this.uniMap;
    }

    public String getCmapName() {
        return this.cmap;
    }

    public boolean isBuiltWith(String cmap2) {
        return Objects.equals(cmap2, this.cmap);
    }

    @Deprecated
    public int getCmapCode(int cid) {
        if (this.isDirect) {
            return cid;
        }
        return toInteger(this.cid2Code.lookup(cid));
    }

    public byte[] getCmapBytes(int cid) {
        byte[] result = new byte[getCmapBytesLength(cid)];
        fillCmapBytes(cid, result, 0);
        return result;
    }

    public int fillCmapBytes(int cid, byte[] array, int offset) {
        if (this.isDirect) {
            int offset2 = offset + 1;
            array[offset] = (byte) ((65280 & cid) >> 8);
            int offset3 = offset2 + 1;
            array[offset2] = (byte) (cid & 255);
            return offset3;
        }
        byte[] bytes = this.cid2Code.lookup(cid);
        int i = 0;
        while (i < bytes.length) {
            array[offset] = bytes[i];
            i++;
            offset++;
        }
        return offset;
    }

    public void fillCmapBytes(int cid, ByteBuffer buffer) {
        if (this.isDirect) {
            buffer.append((byte) ((65280 & cid) >> 8));
            buffer.append((byte) (cid & 255));
            return;
        }
        buffer.append(this.cid2Code.lookup(cid));
    }

    public int getCmapBytesLength(int cid) {
        if (this.isDirect) {
            return 2;
        }
        return this.cid2Code.lookup(cid).length;
    }

    public int getCidCode(int cmapCode) {
        if (this.isDirect) {
            return cmapCode;
        }
        return this.code2Cid.get(cmapCode);
    }

    public boolean containsCodeInCodeSpaceRange(int code, int length) {
        for (int i = 0; i < this.codeSpaceRanges.size(); i += 2) {
            if (length == this.codeSpaceRanges.get(i).length) {
                int mask = 255;
                int totalShift = 0;
                byte[] low = this.codeSpaceRanges.get(i);
                byte[] high = this.codeSpaceRanges.get(i + 1);
                boolean fitsIntoRange = true;
                int ind = length - 1;
                while (ind >= 0) {
                    int actualByteValue = (code & mask) >> totalShift;
                    if (actualByteValue < (low[ind] & UByte.MAX_VALUE) || actualByteValue > (high[ind] & UByte.MAX_VALUE)) {
                        fitsIntoRange = false;
                    }
                    ind--;
                    totalShift += 8;
                    mask <<= 8;
                }
                if (fitsIntoRange) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int toInteger(byte[] bytes) {
        int result = 0;
        for (byte b : bytes) {
            result = (result << 8) + (b & UByte.MAX_VALUE);
        }
        return result;
    }
}
