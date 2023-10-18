package com.itextpdf.p026io.font.otf;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.util.IntHashtable;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.IOException;
import java.io.Serializable;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.font.otf.OtfClass */
public class OtfClass implements Serializable {
    public static final int GLYPH_BASE = 1;
    public static final int GLYPH_LIGATURE = 2;
    public static final int GLYPH_MARK = 3;
    private static final long serialVersionUID = -7584495836452964728L;
    private IntHashtable mapClass = new IntHashtable();

    private OtfClass(RandomAccessFileOrArray rf, int classLocation) throws IOException {
        rf.seek((long) classLocation);
        int classFormat = rf.readUnsignedShort();
        if (classFormat == 1) {
            int startGlyph = rf.readUnsignedShort();
            int endGlyph = startGlyph + rf.readUnsignedShort();
            for (int k = startGlyph; k < endGlyph; k++) {
                this.mapClass.put(k, rf.readUnsignedShort());
            }
        } else if (classFormat == 2) {
            int classRangeCount = rf.readUnsignedShort();
            for (int k2 = 0; k2 < classRangeCount; k2++) {
                int glyphEnd = rf.readUnsignedShort();
                int cl = rf.readUnsignedShort();
                for (int glyphStart = rf.readUnsignedShort(); glyphStart <= glyphEnd; glyphStart++) {
                    this.mapClass.put(glyphStart, cl);
                }
            }
        } else {
            throw new IOException("Invalid class format " + classFormat);
        }
    }

    public static OtfClass create(RandomAccessFileOrArray rf, int classLocation) {
        try {
            return new OtfClass(rf, classLocation);
        } catch (IOException e) {
            LoggerFactory.getLogger((Class<?>) OtfClass.class).error(MessageFormatUtil.format(LogMessageConstant.OPENTYPE_GDEF_TABLE_ERROR, e.getMessage()));
            return null;
        }
    }

    public int getOtfClass(int glyph) {
        return this.mapClass.get(glyph);
    }

    public boolean isMarkOtfClass(int glyph) {
        return hasClass(glyph) && getOtfClass(glyph) == 3;
    }

    public boolean hasClass(int glyph) {
        return this.mapClass.containsKey(glyph);
    }

    public int getOtfClass(int glyph, boolean strict) {
        if (!strict) {
            return this.mapClass.get(glyph);
        }
        if (this.mapClass.containsKey(glyph)) {
            return this.mapClass.get(glyph);
        }
        return -1;
    }
}
