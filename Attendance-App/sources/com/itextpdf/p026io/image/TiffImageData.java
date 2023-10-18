package com.itextpdf.p026io.image;

import com.itextpdf.p026io.IOException;
import com.itextpdf.p026io.codec.TIFFDirectory;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import java.net.URL;

/* renamed from: com.itextpdf.io.image.TiffImageData */
public class TiffImageData extends RawImageData {
    private boolean direct;
    private int page;
    private boolean recoverFromImageError;

    protected TiffImageData(URL url, boolean recoverFromImageError2, int page2, boolean direct2) {
        super(url, ImageType.TIFF);
        this.recoverFromImageError = recoverFromImageError2;
        this.page = page2;
        this.direct = direct2;
    }

    protected TiffImageData(byte[] bytes, boolean recoverFromImageError2, int page2, boolean direct2) {
        super(bytes, ImageType.TIFF);
        this.recoverFromImageError = recoverFromImageError2;
        this.page = page2;
        this.direct = direct2;
    }

    private static ImageData getImage(URL url, boolean recoverFromImageError2, int page2, boolean direct2) {
        return new TiffImageData(url, recoverFromImageError2, page2, direct2);
    }

    private static ImageData getImage(byte[] bytes, boolean recoverFromImageError2, int page2, boolean direct2) {
        return new TiffImageData(bytes, recoverFromImageError2, page2, direct2);
    }

    public static int getNumberOfPages(RandomAccessFileOrArray raf) {
        try {
            return TIFFDirectory.getNumDirectories(raf);
        } catch (Exception e) {
            throw new IOException(IOException.TiffImageException, (Throwable) e);
        }
    }

    public static int getNumberOfPages(byte[] bytes) {
        return getNumberOfPages(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(bytes)));
    }

    public boolean isRecoverFromImageError() {
        return this.recoverFromImageError;
    }

    public int getPage() {
        return this.page;
    }

    public boolean isDirect() {
        return this.direct;
    }

    public void setOriginalType(ImageType originalType) {
        this.originalType = originalType;
    }
}
