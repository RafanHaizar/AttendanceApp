package com.itextpdf.kernel.pdf.action;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import java.util.ArrayList;
import java.util.List;

public class PdfActionOcgState {
    private List<PdfDictionary> ocgs;
    private PdfName state;

    public PdfActionOcgState(PdfName state2, List<PdfDictionary> ocgs2) {
        this.state = state2;
        this.ocgs = ocgs2;
    }

    public PdfName getState() {
        return this.state;
    }

    public List<PdfDictionary> getOcgs() {
        return this.ocgs;
    }

    public List<PdfObject> getObjectList() {
        List<PdfObject> states = new ArrayList<>();
        states.add(this.state);
        states.addAll(this.ocgs);
        return states;
    }
}
