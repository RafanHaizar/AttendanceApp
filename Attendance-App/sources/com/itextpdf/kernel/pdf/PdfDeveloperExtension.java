package com.itextpdf.kernel.pdf;

public class PdfDeveloperExtension {
    public static final PdfDeveloperExtension ADOBE_1_7_EXTENSIONLEVEL3 = new PdfDeveloperExtension(PdfName.ADBE, PdfName.Pdf_Version_1_7, 3);
    public static final PdfDeveloperExtension ESIC_1_7_EXTENSIONLEVEL2 = new PdfDeveloperExtension(PdfName.ESIC, PdfName.Pdf_Version_1_7, 2);
    public static final PdfDeveloperExtension ESIC_1_7_EXTENSIONLEVEL5 = new PdfDeveloperExtension(PdfName.ESIC, PdfName.Pdf_Version_1_7, 5);
    protected PdfName baseVersion;
    protected int extensionLevel;
    protected PdfName prefix;

    public PdfDeveloperExtension(PdfName prefix2, PdfName baseVersion2, int extensionLevel2) {
        this.prefix = prefix2;
        this.baseVersion = baseVersion2;
        this.extensionLevel = extensionLevel2;
    }

    public PdfName getPrefix() {
        return this.prefix;
    }

    public PdfName getBaseVersion() {
        return this.baseVersion;
    }

    public int getExtensionLevel() {
        return this.extensionLevel;
    }

    public PdfDictionary getDeveloperExtensions() {
        PdfDictionary developerextensions = new PdfDictionary();
        developerextensions.put(PdfName.BaseVersion, this.baseVersion);
        developerextensions.put(PdfName.ExtensionLevel, new PdfNumber(this.extensionLevel));
        return developerextensions;
    }
}
