package com.itextpdf.p026io.font.cmap;

import com.itextpdf.p026io.font.constants.FontResources;
import com.itextpdf.p026io.source.PdfTokenizer;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.util.ResourceUtil;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.itextpdf.io.font.cmap.CMapLocationResource */
public class CMapLocationResource implements ICMapLocation {
    public PdfTokenizer getLocation(String location) throws IOException {
        String fullName = FontResources.CMAPS + location;
        InputStream inp = ResourceUtil.getResourceStream(fullName);
        if (inp != null) {
            return new PdfTokenizer(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(inp)));
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.Cmap1WasNotFound).setMessageParams(fullName);
    }
}
