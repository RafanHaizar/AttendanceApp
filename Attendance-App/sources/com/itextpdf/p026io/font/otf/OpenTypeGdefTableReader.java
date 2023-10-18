package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import java.io.IOException;
import java.io.Serializable;

/* renamed from: com.itextpdf.io.font.otf.OpenTypeGdefTableReader */
public class OpenTypeGdefTableReader implements Serializable {
    static final int FLAG_IGNORE_BASE = 2;
    static final int FLAG_IGNORE_LIGATURE = 4;
    static final int FLAG_IGNORE_MARK = 8;
    private static final long serialVersionUID = 1564505797329158035L;
    private OtfClass glyphClass;
    private OtfClass markAttachmentClass;

    /* renamed from: rf */
    private final RandomAccessFileOrArray f1215rf;
    private final int tableLocation;

    public OpenTypeGdefTableReader(RandomAccessFileOrArray rf, int tableLocation2) {
        this.f1215rf = rf;
        this.tableLocation = tableLocation2;
    }

    public void readTable() throws IOException {
        int i = this.tableLocation;
        if (i > 0) {
            this.f1215rf.seek((long) i);
            this.f1215rf.readUnsignedInt();
            int glyphClassDefOffset = this.f1215rf.readUnsignedShort();
            this.f1215rf.readUnsignedShort();
            this.f1215rf.readUnsignedShort();
            int markAttachClassDefOffset = this.f1215rf.readUnsignedShort();
            if (glyphClassDefOffset > 0) {
                this.glyphClass = OtfClass.create(this.f1215rf, this.tableLocation + glyphClassDefOffset);
            }
            if (markAttachClassDefOffset > 0) {
                this.markAttachmentClass = OtfClass.create(this.f1215rf, this.tableLocation + markAttachClassDefOffset);
            }
        }
    }

    public boolean isSkip(int glyph, int flag) {
        OtfClass otfClass;
        OtfClass otfClass2 = this.glyphClass;
        if (!(otfClass2 == null || (flag & 14) == 0)) {
            int cla = otfClass2.getOtfClass(glyph);
            if (cla == 1 && (flag & 2) != 0) {
                return true;
            }
            if (cla == 3 && (flag & 8) != 0) {
                return true;
            }
            if (cla == 2 && (flag & 4) != 0) {
                return true;
            }
        }
        int markAttachmentType = flag >> 8;
        if (markAttachmentType == 0 || (otfClass = this.glyphClass) == null) {
            return false;
        }
        int currentGlyphClass = otfClass.getOtfClass(glyph);
        OtfClass otfClass3 = this.markAttachmentClass;
        int glyphMarkAttachmentClass = otfClass3 != null ? otfClass3.getOtfClass(glyph) : 0;
        if (currentGlyphClass != 3 || glyphMarkAttachmentClass == markAttachmentType) {
            return false;
        }
        return true;
    }

    public OtfClass getGlyphClassTable() {
        return this.glyphClass;
    }
}
