package com.itextpdf.forms.xfdf;

import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfString;

public class ActionObject {
    private DestObject destination;
    private String fileOriginalName;
    private boolean isMap;
    private boolean isNewWindow;
    private PdfName nameAction;
    private PdfName type;
    private PdfString uri;

    public ActionObject(PdfName type2) {
        this.type = type2;
    }

    public PdfName getType() {
        return this.type;
    }

    public ActionObject setType(PdfName type2) {
        this.type = type2;
        return this;
    }

    public PdfString getUri() {
        return this.uri;
    }

    public ActionObject setUri(PdfString uri2) {
        this.uri = uri2;
        return this;
    }

    public boolean isMap() {
        return this.isMap;
    }

    public ActionObject setMap(boolean map) {
        this.isMap = map;
        return this;
    }

    public PdfName getNameAction() {
        return this.nameAction;
    }

    public ActionObject setNameAction(PdfName nameAction2) {
        this.nameAction = nameAction2;
        return this;
    }

    public String getFileOriginalName() {
        return this.fileOriginalName;
    }

    public ActionObject setFileOriginalName(String fileOriginalName2) {
        this.fileOriginalName = fileOriginalName2;
        return this;
    }

    public boolean isNewWindow() {
        return this.isNewWindow;
    }

    public ActionObject setNewWindow(boolean newWindow) {
        this.isNewWindow = newWindow;
        return this;
    }

    public DestObject getDestination() {
        return this.destination;
    }

    public ActionObject setDestination(DestObject destination2) {
        this.destination = destination2;
        return this;
    }
}
