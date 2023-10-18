package com.itextpdf.kernel.pdf;

import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PdfVersion implements Comparable<PdfVersion>, Serializable {
    public static final PdfVersion PDF_1_0 = createPdfVersion(1, 0);
    public static final PdfVersion PDF_1_1 = createPdfVersion(1, 1);
    public static final PdfVersion PDF_1_2 = createPdfVersion(1, 2);
    public static final PdfVersion PDF_1_3 = createPdfVersion(1, 3);
    public static final PdfVersion PDF_1_4 = createPdfVersion(1, 4);
    public static final PdfVersion PDF_1_5 = createPdfVersion(1, 5);
    public static final PdfVersion PDF_1_6 = createPdfVersion(1, 6);
    public static final PdfVersion PDF_1_7 = createPdfVersion(1, 7);
    public static final PdfVersion PDF_2_0 = createPdfVersion(2, 0);
    private static final long serialVersionUID = 6168855906667968169L;
    private static final List<PdfVersion> values = new ArrayList();
    private int major;
    private int minor;

    private PdfVersion(int major2, int minor2) {
        this.major = major2;
        this.minor = minor2;
    }

    public String toString() {
        return MessageFormatUtil.format("PDF-{0}.{1}", Integer.valueOf(this.major), Integer.valueOf(this.minor));
    }

    public PdfName toPdfName() {
        return new PdfName(MessageFormatUtil.format("{0}.{1}", Integer.valueOf(this.major), Integer.valueOf(this.minor)));
    }

    public static PdfVersion fromString(String value) {
        for (PdfVersion version : values) {
            if (version.toString().equals(value)) {
                return version;
            }
        }
        throw new IllegalArgumentException("The provided pdf version was not found.");
    }

    public static PdfVersion fromPdfName(PdfName name) {
        for (PdfVersion version : values) {
            if (version.toPdfName().equals(name)) {
                return version;
            }
        }
        throw new IllegalArgumentException("The provided pdf version was not found.");
    }

    public int compareTo(PdfVersion o) {
        int majorResult = Integer.compare(this.major, o.major);
        if (majorResult != 0) {
            return majorResult;
        }
        return Integer.compare(this.minor, o.minor);
    }

    public boolean equals(Object obj) {
        return getClass() == obj.getClass() && compareTo((PdfVersion) obj) == 0;
    }

    private static PdfVersion createPdfVersion(int major2, int minor2) {
        PdfVersion pdfVersion = new PdfVersion(major2, minor2);
        values.add(pdfVersion);
        return pdfVersion;
    }
}
