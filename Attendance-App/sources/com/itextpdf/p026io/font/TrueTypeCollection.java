package com.itextpdf.p026io.font;

import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.util.FileUtil;
import java.io.IOException;

/* renamed from: com.itextpdf.io.font.TrueTypeCollection */
public class TrueTypeCollection {
    private int TTCSize = 0;
    private boolean cached = true;
    protected RandomAccessFileOrArray raf;
    private byte[] ttc;
    private String ttcPath;

    public TrueTypeCollection(byte[] ttc2) throws IOException {
        this.raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(ttc2));
        this.ttc = ttc2;
        initFontSize();
    }

    public TrueTypeCollection(String ttcPath2) throws IOException {
        if (FileUtil.fileExists(ttcPath2)) {
            this.raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(ttcPath2));
            this.ttcPath = ttcPath2;
            initFontSize();
            return;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.FontFile1NotFound).setMessageParams(ttcPath2);
    }

    public FontProgram getFontByTccIndex(int ttcIndex) throws IOException {
        if (ttcIndex <= this.TTCSize - 1) {
            String str = this.ttcPath;
            if (str != null) {
                return FontProgramFactory.createFont(str, ttcIndex, this.cached);
            }
            return FontProgramFactory.createFont(this.ttc, ttcIndex, this.cached);
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TtcIndexDoesNotExistInThisTtcFile);
    }

    public int getTTCSize() {
        return this.TTCSize;
    }

    public boolean isCached() {
        return this.cached;
    }

    public void setCached(boolean cached2) {
        this.cached = cached2;
    }

    private void initFontSize() throws IOException {
        if (this.raf.readString(4, "Cp1252").equals("ttcf")) {
            this.raf.skipBytes(4);
            this.TTCSize = this.raf.readInt();
            return;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.InvalidTtcFile);
    }
}
