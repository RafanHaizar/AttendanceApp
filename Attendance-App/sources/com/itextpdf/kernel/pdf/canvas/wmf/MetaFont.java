package com.itextpdf.kernel.pdf.canvas.wmf;

import com.itextpdf.p026io.font.FontEncoding;
import com.itextpdf.p026io.font.FontProgram;
import com.itextpdf.p026io.font.FontProgramFactory;
import com.itextpdf.svg.SvgConstants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MetaFont extends MetaObject {
    static final int BOLDTHRESHOLD = 600;
    static final int DEFAULT_PITCH = 0;
    static final int ETO_CLIPPED = 4;
    static final int ETO_OPAQUE = 2;
    static final int FF_DECORATIVE = 5;
    static final int FF_DONTCARE = 0;
    static final int FF_MODERN = 3;
    static final int FF_ROMAN = 1;
    static final int FF_SCRIPT = 4;
    static final int FF_SWISS = 2;
    static final int FIXED_PITCH = 1;
    static final int MARKER_BOLD = 1;
    static final int MARKER_COURIER = 0;
    static final int MARKER_HELVETICA = 4;
    static final int MARKER_ITALIC = 2;
    static final int MARKER_SYMBOL = 12;
    static final int MARKER_TIMES = 8;
    static final int NAME_SIZE = 32;
    static final int VARIABLE_PITCH = 2;
    static final String[] fontNames = {"Courier", "Courier-Bold", "Courier-Oblique", "Courier-BoldOblique", "Helvetica", "Helvetica-Bold", "Helvetica-Oblique", "Helvetica-BoldOblique", "Times-Roman", "Times-Bold", "Times-Italic", "Times-BoldItalic", "Symbol", "ZapfDingbats"};
    float angle;
    int bold;
    int charset;
    FontEncoding encoding = null;
    String faceName = "arial";
    FontProgram font = null;
    int height;
    int italic;
    int pitchAndFamily;
    boolean strikeout;
    boolean underline;

    public MetaFont() {
        super(3);
    }

    public void init(InputMeta in) throws IOException {
        this.height = Math.abs(in.readShort());
        int i = 2;
        in.skip(2);
        double readShort = (double) in.readShort();
        Double.isNaN(readShort);
        this.angle = (float) ((readShort / 1800.0d) * 3.141592653589793d);
        in.skip(2);
        boolean z = true;
        this.bold = in.readShort() >= 600 ? 1 : 0;
        if (in.readByte() == 0) {
            i = 0;
        }
        this.italic = i;
        this.underline = in.readByte() != 0;
        if (in.readByte() == 0) {
            z = false;
        }
        this.strikeout = z;
        this.charset = in.readByte();
        in.skip(3);
        this.pitchAndFamily = in.readByte();
        byte[] name = new byte[32];
        int k = 0;
        while (k < 32) {
            int c = in.readByte();
            if (c != 0) {
                name[k] = (byte) c;
                k++;
            }
        }
        try {
            this.faceName = new String(name, 0, k, "Cp1252");
        } catch (UnsupportedEncodingException e) {
            this.faceName = new String(name, 0, k);
        }
        this.faceName = this.faceName.toLowerCase();
    }

    public FontProgram getFont() throws IOException {
        String fontName;
        FontProgram fontProgram = this.font;
        if (fontProgram != null) {
            return fontProgram;
        }
        FontProgram ff2 = FontProgramFactory.createRegisteredFont(this.faceName, (this.italic != 0 ? 2 : 0) | (this.bold != 0 ? 1 : 0));
        this.encoding = FontEncoding.createFontEncoding("Cp1252");
        this.font = ff2;
        if (ff2 != null) {
            return ff2;
        }
        if (!this.faceName.contains("courier") && !this.faceName.contains("terminal") && !this.faceName.contains("fixedsys")) {
            if (!this.faceName.contains("ms sans serif") && !this.faceName.contains("arial") && !this.faceName.contains("system")) {
                if (!this.faceName.contains("arial black")) {
                    if (!this.faceName.contains("times") && !this.faceName.contains("ms serif") && !this.faceName.contains("roman")) {
                        if (!this.faceName.contains(SvgConstants.Tags.SYMBOL)) {
                            int i = this.pitchAndFamily;
                            int pitch = i & 3;
                            switch ((i >> 4) & 7) {
                                case 1:
                                    fontName = fontNames[this.italic + 8 + this.bold];
                                    break;
                                case 2:
                                case 4:
                                case 5:
                                    fontName = fontNames[this.italic + 4 + this.bold];
                                    break;
                                case 3:
                                    fontName = fontNames[this.italic + 0 + this.bold];
                                    break;
                                default:
                                    switch (pitch) {
                                        case 1:
                                            fontName = fontNames[this.italic + 0 + this.bold];
                                            break;
                                        default:
                                            fontName = fontNames[this.italic + 4 + this.bold];
                                            break;
                                    }
                            }
                        } else {
                            fontName = fontNames[12];
                        }
                    } else {
                        fontName = fontNames[this.italic + 8 + this.bold];
                    }
                } else {
                    fontName = fontNames[this.italic + 4 + 1];
                }
            } else {
                fontName = fontNames[this.italic + 4 + this.bold];
            }
        } else {
            fontName = fontNames[this.italic + 0 + this.bold];
        }
        try {
            this.font = FontProgramFactory.createFont(fontName);
            this.encoding = FontEncoding.createFontEncoding("Cp1252");
            return this.font;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public FontEncoding getEncoding() {
        return this.encoding;
    }

    public float getAngle() {
        return this.angle;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public boolean isStrikeout() {
        return this.strikeout;
    }

    public float getFontSize(MetaState state) {
        return Math.abs(state.transformY(this.height) - state.transformY(0)) * WmfImageHelper.wmfFontCorrection;
    }
}
