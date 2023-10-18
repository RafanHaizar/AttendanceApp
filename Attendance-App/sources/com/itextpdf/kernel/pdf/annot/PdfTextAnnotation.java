package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfString;

public class PdfTextAnnotation extends PdfMarkupAnnotation {
    private static final long serialVersionUID = -2061119066076464569L;

    public PdfTextAnnotation(Rectangle rect) {
        super(rect);
    }

    protected PdfTextAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfName getSubtype() {
        return PdfName.Text;
    }

    public PdfString getState() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.State);
    }

    public PdfTextAnnotation setState(PdfString state) {
        return (PdfTextAnnotation) put(PdfName.State, state);
    }

    public PdfString getStateModel() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.StateModel);
    }

    public PdfTextAnnotation setStateModel(PdfString stateModel) {
        return (PdfTextAnnotation) put(PdfName.StateModel, stateModel);
    }

    public boolean getOpen() {
        return PdfBoolean.TRUE.equals(((PdfDictionary) getPdfObject()).getAsBoolean(PdfName.Open));
    }

    public PdfTextAnnotation setOpen(boolean open) {
        return (PdfTextAnnotation) put(PdfName.Open, PdfBoolean.valueOf(open));
    }

    public PdfName getIconName() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Name);
    }

    public PdfTextAnnotation setIconName(PdfName name) {
        return (PdfTextAnnotation) put(PdfName.Name, name);
    }
}
