package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.properties.XMPProperty;
import com.itextpdf.svg.SvgConstants;
import java.io.Serializable;

public class PdfAConformanceLevel implements Serializable {
    public static final PdfAConformanceLevel PDF_A_1A = new PdfAConformanceLevel("1", SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A);
    public static final PdfAConformanceLevel PDF_A_1B = new PdfAConformanceLevel("1", SvgConstants.Attributes.PATH_DATA_BEARING);
    public static final PdfAConformanceLevel PDF_A_2A = new PdfAConformanceLevel("2", SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A);
    public static final PdfAConformanceLevel PDF_A_2B = new PdfAConformanceLevel("2", SvgConstants.Attributes.PATH_DATA_BEARING);
    public static final PdfAConformanceLevel PDF_A_2U = new PdfAConformanceLevel("2", "U");
    public static final PdfAConformanceLevel PDF_A_3A = new PdfAConformanceLevel("3", SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A);
    public static final PdfAConformanceLevel PDF_A_3B = new PdfAConformanceLevel("3", SvgConstants.Attributes.PATH_DATA_BEARING);
    public static final PdfAConformanceLevel PDF_A_3U = new PdfAConformanceLevel("3", "U");
    private static final long serialVersionUID = 1481878095812910587L;
    private final String conformance;
    private final String part;

    private PdfAConformanceLevel(String part2, String conformance2) {
        this.conformance = conformance2;
        this.part = part2;
    }

    public String getConformance() {
        return this.conformance;
    }

    public String getPart() {
        return this.part;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.kernel.pdf.PdfAConformanceLevel getConformanceLevel(java.lang.String r5, java.lang.String r6) {
        /*
            java.lang.String r0 = r6.toUpperCase()
            java.lang.String r1 = "A"
            boolean r1 = r1.equals(r0)
            java.lang.String r2 = "B"
            boolean r2 = r2.equals(r0)
            java.lang.String r3 = "U"
            boolean r3 = r3.equals(r0)
            int r4 = r5.hashCode()
            switch(r4) {
                case 49: goto L_0x0032;
                case 50: goto L_0x0028;
                case 51: goto L_0x001e;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x003c
        L_0x001e:
            java.lang.String r4 = "3"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L_0x001d
            r4 = 2
            goto L_0x003d
        L_0x0028:
            java.lang.String r4 = "2"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L_0x001d
            r4 = 1
            goto L_0x003d
        L_0x0032:
            java.lang.String r4 = "1"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L_0x001d
            r4 = 0
            goto L_0x003d
        L_0x003c:
            r4 = -1
        L_0x003d:
            switch(r4) {
                case 0: goto L_0x005f;
                case 1: goto L_0x0050;
                case 2: goto L_0x0041;
                default: goto L_0x0040;
            }
        L_0x0040:
            goto L_0x0069
        L_0x0041:
            if (r1 == 0) goto L_0x0046
            com.itextpdf.kernel.pdf.PdfAConformanceLevel r4 = PDF_A_3A
            return r4
        L_0x0046:
            if (r2 == 0) goto L_0x004b
            com.itextpdf.kernel.pdf.PdfAConformanceLevel r4 = PDF_A_3B
            return r4
        L_0x004b:
            if (r3 == 0) goto L_0x0069
            com.itextpdf.kernel.pdf.PdfAConformanceLevel r4 = PDF_A_3U
            return r4
        L_0x0050:
            if (r1 == 0) goto L_0x0055
            com.itextpdf.kernel.pdf.PdfAConformanceLevel r4 = PDF_A_2A
            return r4
        L_0x0055:
            if (r2 == 0) goto L_0x005a
            com.itextpdf.kernel.pdf.PdfAConformanceLevel r4 = PDF_A_2B
            return r4
        L_0x005a:
            if (r3 == 0) goto L_0x0069
            com.itextpdf.kernel.pdf.PdfAConformanceLevel r4 = PDF_A_2U
            return r4
        L_0x005f:
            if (r1 == 0) goto L_0x0064
            com.itextpdf.kernel.pdf.PdfAConformanceLevel r4 = PDF_A_1A
            return r4
        L_0x0064:
            if (r2 == 0) goto L_0x0069
            com.itextpdf.kernel.pdf.PdfAConformanceLevel r4 = PDF_A_1B
            return r4
        L_0x0069:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.PdfAConformanceLevel.getConformanceLevel(java.lang.String, java.lang.String):com.itextpdf.kernel.pdf.PdfAConformanceLevel");
    }

    public static PdfAConformanceLevel getConformanceLevel(XMPMeta meta) {
        XMPProperty conformanceXmpProperty = null;
        XMPProperty partXmpProperty = null;
        try {
            conformanceXmpProperty = meta.getProperty(XMPConst.NS_PDFA_ID, XMPConst.CONFORMANCE);
            partXmpProperty = meta.getProperty(XMPConst.NS_PDFA_ID, "part");
        } catch (XMPException e) {
        }
        if (conformanceXmpProperty == null || partXmpProperty == null) {
            return null;
        }
        return getConformanceLevel(partXmpProperty.getValue(), conformanceXmpProperty.getValue());
    }
}
