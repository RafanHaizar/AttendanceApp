package com.itextpdf.kernel.pdf.canvas.wmf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import java.io.IOException;

public class MetaBrush extends MetaObject {
    public static final int BS_DIBPATTERN = 5;
    public static final int BS_HATCHED = 2;
    public static final int BS_NULL = 1;
    public static final int BS_PATTERN = 3;
    public static final int BS_SOLID = 0;
    public static final int HS_BDIAGONAL = 3;
    public static final int HS_CROSS = 4;
    public static final int HS_DIAGCROSS = 5;
    public static final int HS_FDIAGONAL = 2;
    public static final int HS_HORIZONTAL = 0;
    public static final int HS_VERTICAL = 1;
    Color color = ColorConstants.WHITE;
    int hatch;
    int style = 0;

    public MetaBrush() {
        super(2);
    }

    public void init(InputMeta in) throws IOException {
        this.style = in.readWord();
        this.color = in.readColor();
        this.hatch = in.readWord();
    }

    public int getStyle() {
        return this.style;
    }

    public int getHatch() {
        return this.hatch;
    }

    public Color getColor() {
        return this.color;
    }
}
