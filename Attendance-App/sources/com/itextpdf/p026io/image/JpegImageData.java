package com.itextpdf.p026io.image;

import java.net.URL;

/* renamed from: com.itextpdf.io.image.JpegImageData */
public class JpegImageData extends ImageData {
    protected JpegImageData(URL url) {
        super(url, ImageType.JPEG);
    }

    protected JpegImageData(byte[] bytes) {
        super(bytes, ImageType.JPEG);
    }
}
