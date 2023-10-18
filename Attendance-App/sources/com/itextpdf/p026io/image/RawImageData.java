package com.itextpdf.p026io.image;

import java.net.URL;

/* renamed from: com.itextpdf.io.image.RawImageData */
public class RawImageData extends ImageData {
    public static final int CCITTG3_1D = 257;
    public static final int CCITTG3_2D = 258;
    public static final int CCITTG4 = 256;
    public static final int CCITT_BLACKIS1 = 1;
    public static final int CCITT_ENCODEDBYTEALIGN = 2;
    public static final int CCITT_ENDOFBLOCK = 8;
    public static final int CCITT_ENDOFLINE = 4;
    protected int typeCcitt;

    protected RawImageData(URL url, ImageType type) {
        super(url, type);
    }

    protected RawImageData(byte[] bytes, ImageType type) {
        super(bytes, type);
    }

    public boolean isRawImage() {
        return true;
    }

    public int getTypeCcitt() {
        return this.typeCcitt;
    }

    public void setTypeCcitt(int typeCcitt2) {
        this.typeCcitt = typeCcitt2;
    }
}
