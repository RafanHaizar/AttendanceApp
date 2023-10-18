package com.itextpdf.p026io.image;

import java.net.URL;

/* renamed from: com.itextpdf.io.image.BmpImageData */
public class BmpImageData extends RawImageData {
    private boolean noHeader;
    private int size;

    protected BmpImageData(URL url, boolean noHeader2) {
        super(url, ImageType.BMP);
        this.noHeader = noHeader2;
    }

    @Deprecated
    protected BmpImageData(URL url, boolean noHeader2, int size2) {
        this(url, noHeader2);
        this.size = size2;
    }

    protected BmpImageData(byte[] bytes, boolean noHeader2) {
        super(bytes, ImageType.BMP);
        this.noHeader = noHeader2;
    }

    @Deprecated
    protected BmpImageData(byte[] bytes, boolean noHeader2, int size2) {
        this(bytes, noHeader2);
        this.size = size2;
    }

    @Deprecated
    public int getSize() {
        return this.size;
    }

    public boolean isNoHeader() {
        return this.noHeader;
    }
}
