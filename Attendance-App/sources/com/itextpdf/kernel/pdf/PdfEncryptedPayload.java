package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;

public class PdfEncryptedPayload extends PdfObjectWrapper<PdfDictionary> {
    public PdfEncryptedPayload(String subtype) {
        this(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.EncryptedPayload);
        setSubtype(subtype);
    }

    private PdfEncryptedPayload(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public static PdfEncryptedPayload extractFrom(PdfFileSpec fileSpec) {
        if (fileSpec == null || !fileSpec.getPdfObject().isDictionary()) {
            return null;
        }
        return wrap(((PdfDictionary) fileSpec.getPdfObject()).getAsDictionary(PdfName.f1322EP));
    }

    public static PdfEncryptedPayload wrap(PdfDictionary dictionary) {
        PdfName type = dictionary.getAsName(PdfName.Type);
        if (type != null && !type.equals(PdfName.EncryptedPayload)) {
            throw new PdfException(PdfException.EncryptedPayloadShallHaveTypeEqualsToEncryptedPayloadIfPresent);
        } else if (dictionary.getAsName(PdfName.Subtype) != null) {
            return new PdfEncryptedPayload(dictionary);
        } else {
            throw new PdfException(PdfException.EncryptedPayloadShallHaveSubtype);
        }
    }

    public PdfName getSubtype() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Subtype);
    }

    public PdfEncryptedPayload setSubtype(String subtype) {
        return setSubtype(new PdfName(subtype));
    }

    public PdfEncryptedPayload setSubtype(PdfName subtype) {
        setModified();
        ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, subtype);
        return this;
    }

    public PdfName getVersion() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Version);
    }

    public PdfEncryptedPayload setVersion(String version) {
        return setVersion(new PdfName(version));
    }

    public PdfEncryptedPayload setVersion(PdfName version) {
        setModified();
        ((PdfDictionary) getPdfObject()).put(PdfName.Version, version);
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
