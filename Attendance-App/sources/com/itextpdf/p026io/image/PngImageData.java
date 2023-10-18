package com.itextpdf.p026io.image;

import java.net.URL;

/* renamed from: com.itextpdf.io.image.PngImageData */
public class PngImageData extends RawImageData {
    private byte[] colorPalette;
    private int colorType;
    private float gamma = 1.0f;
    private PngChromaticities pngChromaticities;

    protected PngImageData(byte[] bytes) {
        super(bytes, ImageType.PNG);
    }

    protected PngImageData(URL url) {
        super(url, ImageType.PNG);
    }

    public byte[] getColorPalette() {
        return this.colorPalette;
    }

    public void setColorPalette(byte[] colorPalette2) {
        this.colorPalette = colorPalette2;
    }

    public float getGamma() {
        return this.gamma;
    }

    public void setGamma(float gamma2) {
        this.gamma = gamma2;
    }

    public boolean isHasCHRM() {
        return this.pngChromaticities != null;
    }

    public PngChromaticities getPngChromaticities() {
        return this.pngChromaticities;
    }

    public void setPngChromaticities(PngChromaticities pngChromaticities2) {
        this.pngChromaticities = pngChromaticities2;
    }

    public int getColorType() {
        return this.colorType;
    }

    public void setColorType(int colorType2) {
        this.colorType = colorType2;
    }

    public boolean isIndexed() {
        return this.colorType == 3;
    }

    public boolean isGrayscaleImage() {
        return (this.colorType & 2) == 0;
    }
}
