package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDate;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import java.util.List;

public class PdfTrapNetworkAnnotation extends PdfAnnotation {
    private static final long serialVersionUID = 5118904991630303608L;

    public PdfTrapNetworkAnnotation(Rectangle rect, PdfFormXObject appearanceStream) {
        super(rect);
        if (appearanceStream.getProcessColorModel() != null) {
            setNormalAppearance((PdfDictionary) appearanceStream.getPdfObject());
            setFlags(68);
            return;
        }
        throw new PdfException("Process color model must be set in appearance stream for Trap Network annotation!");
    }

    protected PdfTrapNetworkAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.TrapNet;
    }

    public PdfTrapNetworkAnnotation setLastModified(PdfDate lastModified) {
        return (PdfTrapNetworkAnnotation) put(PdfName.LastModified, lastModified.getPdfObject());
    }

    public PdfString getLastModified() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.LastModified);
    }

    public PdfTrapNetworkAnnotation setVersion(PdfArray version) {
        return (PdfTrapNetworkAnnotation) put(PdfName.Version, version);
    }

    public PdfArray getVersion() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.Version);
    }

    public PdfTrapNetworkAnnotation setAnnotStates(PdfArray annotStates) {
        return (PdfTrapNetworkAnnotation) put(PdfName.AnnotStates, annotStates);
    }

    public PdfArray getAnnotStates() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.AnnotStates);
    }

    public PdfTrapNetworkAnnotation setFauxedFonts(PdfArray fauxedFonts) {
        return (PdfTrapNetworkAnnotation) put(PdfName.FontFauxing, fauxedFonts);
    }

    public PdfTrapNetworkAnnotation setFauxedFonts(List<PdfFont> fauxedFonts) {
        PdfArray arr = new PdfArray();
        for (PdfFont f : fauxedFonts) {
            arr.add(f.getPdfObject());
        }
        return setFauxedFonts(arr);
    }

    public PdfArray getFauxedFonts() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.FontFauxing);
    }
}
