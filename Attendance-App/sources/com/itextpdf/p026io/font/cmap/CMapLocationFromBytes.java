package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.source.PdfTokenizer;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import java.io.IOException;

/* renamed from: com.itextpdf.io.font.cmap.CMapLocationFromBytes */
public class CMapLocationFromBytes implements ICMapLocation {
    private byte[] data;

    public CMapLocationFromBytes(byte[] data2) {
        this.data = data2;
    }

    public PdfTokenizer getLocation(String location) throws IOException {
        return new PdfTokenizer(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(this.data)));
    }
}
