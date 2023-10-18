package com.itextpdf.p026io.image;

import com.itextpdf.p026io.codec.Jbig2SegmentReader;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.itextpdf.io.image.Jbig2ImageHelper */
class Jbig2ImageHelper {
    private byte[] globals;

    Jbig2ImageHelper() {
    }

    public static byte[] getGlobalSegment(RandomAccessFileOrArray ra) {
        try {
            Jbig2SegmentReader sr = new Jbig2SegmentReader(ra);
            sr.read();
            return sr.getGlobal(true);
        } catch (Exception e) {
            return null;
        }
    }

    public static void processImage(ImageData jbig2) {
        if (jbig2.getOriginalType() == ImageType.JBIG2) {
            Jbig2ImageData image = (Jbig2ImageData) jbig2;
            try {
                if (image.getData() == null) {
                    image.loadData();
                }
                RandomAccessFileOrArray raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(image.getData()));
                Jbig2SegmentReader sr = new Jbig2SegmentReader(raf);
                sr.read();
                Jbig2SegmentReader.Jbig2Page p = sr.getPage(image.getPage());
                raf.close();
                image.setHeight((float) p.pageBitmapHeight);
                image.setWidth((float) p.pageBitmapWidth);
                image.setBpc(1);
                image.setColorSpace(1);
                byte[] globals2 = sr.getGlobal(true);
                if (globals2 != null) {
                    Map<String, Object> decodeParms = new HashMap<>();
                    decodeParms.put("JBIG2Globals", globals2);
                    image.decodeParms = decodeParms;
                }
                image.setFilter("JBIG2Decode");
                image.setColorSpace(1);
                image.setBpc(1);
                image.data = p.getData(true);
            } catch (IOException e) {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.Jbig2ImageException, (Throwable) e);
            }
        } else {
            throw new IllegalArgumentException("JBIG2 image expected");
        }
    }
}
