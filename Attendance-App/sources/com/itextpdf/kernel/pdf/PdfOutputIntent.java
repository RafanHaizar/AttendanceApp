package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
import java.io.InputStream;

public class PdfOutputIntent extends PdfObjectWrapper<PdfDictionary> {
    private static final long serialVersionUID = -3814334679568337730L;

    public PdfOutputIntent(String outputConditionIdentifier, String outputCondition, String registryName, String info, InputStream iccStream) {
        super(new PdfDictionary());
        setOutputIntentSubtype(PdfName.GTS_PDFA1);
        ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.OutputIntent);
        if (outputCondition != null) {
            setOutputCondition(outputCondition);
        }
        if (outputConditionIdentifier != null) {
            setOutputConditionIdentifier(outputConditionIdentifier);
        }
        if (registryName != null) {
            setRegistryName(registryName);
        }
        if (info != null) {
            setInfo(info);
        }
        if (iccStream != null) {
            setDestOutputProfile(iccStream);
        }
    }

    public PdfOutputIntent(PdfDictionary outputIntentDict) {
        super(outputIntentDict);
    }

    public PdfStream getDestOutputProfile() {
        return ((PdfDictionary) getPdfObject()).getAsStream(PdfName.DestOutputProfile);
    }

    public void setDestOutputProfile(InputStream iccStream) {
        ((PdfDictionary) getPdfObject()).put(PdfName.DestOutputProfile, PdfCieBasedCs.IccBased.getIccProfileStream(iccStream));
    }

    public PdfString getInfo() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.Info);
    }

    public void setInfo(String info) {
        ((PdfDictionary) getPdfObject()).put(PdfName.Info, new PdfString(info));
    }

    public PdfString getRegistryName() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.RegistryName);
    }

    public void setRegistryName(String registryName) {
        ((PdfDictionary) getPdfObject()).put(PdfName.RegistryName, new PdfString(registryName));
    }

    public PdfString getOutputConditionIdentifier() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.OutputConditionIdentifier);
    }

    public void setOutputConditionIdentifier(String outputConditionIdentifier) {
        ((PdfDictionary) getPdfObject()).put(PdfName.OutputConditionIdentifier, new PdfString(outputConditionIdentifier));
    }

    public PdfString getOutputCondition() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.OutputCondition);
    }

    public void setOutputCondition(String outputCondition) {
        ((PdfDictionary) getPdfObject()).put(PdfName.OutputCondition, new PdfString(outputCondition));
    }

    public PdfName getOutputIntentSubtype() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.f1385S);
    }

    public void setOutputIntentSubtype(PdfName subtype) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1385S, subtype);
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
