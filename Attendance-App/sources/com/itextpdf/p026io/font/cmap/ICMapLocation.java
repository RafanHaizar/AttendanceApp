package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.source.PdfTokenizer;
import java.io.IOException;

/* renamed from: com.itextpdf.io.font.cmap.ICMapLocation */
public interface ICMapLocation {
    PdfTokenizer getLocation(String str) throws IOException;
}
