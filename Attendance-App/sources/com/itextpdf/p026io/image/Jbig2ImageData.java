package com.itextpdf.p026io.image;

import com.itextpdf.p026io.IOException;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.codec.Jbig2SegmentReader;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import java.net.URL;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.image.Jbig2ImageData */
public class Jbig2ImageData extends ImageData {
    private int page;

    protected Jbig2ImageData(URL url, int page2) {
        super(url, ImageType.JBIG2);
        this.page = page2;
    }

    protected Jbig2ImageData(byte[] bytes, int page2) {
        super(bytes, ImageType.JBIG2);
        this.page = page2;
    }

    public int getPage() {
        return this.page;
    }

    public static int getNumberOfPages(byte[] bytes) {
        return getNumberOfPages(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(bytes)));
    }

    public static int getNumberOfPages(RandomAccessFileOrArray raf) {
        try {
            Jbig2SegmentReader sr = new Jbig2SegmentReader(raf);
            sr.read();
            return sr.numberOfPages();
        } catch (Exception e) {
            throw new IOException(IOException.Jbig2ImageException, (Throwable) e);
        }
    }

    public boolean canImageBeInline() {
        LoggerFactory.getLogger((Class<?>) ImageData.class).warn(LogMessageConstant.IMAGE_HAS_JBIG2DECODE_FILTER);
        return false;
    }
}
