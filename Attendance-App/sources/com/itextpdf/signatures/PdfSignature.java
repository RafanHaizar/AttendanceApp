package com.itextpdf.signatures;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDate;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.p026io.font.PdfEncodings;

public class PdfSignature extends PdfObjectWrapper<PdfDictionary> {
    public PdfSignature() {
        super(new PdfDictionary());
        put(PdfName.Type, PdfName.Sig);
    }

    public PdfSignature(PdfName filter, PdfName subFilter) {
        this();
        put(PdfName.Filter, filter);
        put(PdfName.SubFilter, subFilter);
    }

    public PdfSignature(PdfDictionary sigDictionary) {
        super(sigDictionary);
        PdfString contents = ((PdfDictionary) getPdfObject()).getAsString(PdfName.Contents);
        if (contents != null) {
            contents.markAsUnencryptedObject();
        }
    }

    public PdfName getSubFilter() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.SubFilter);
    }

    public PdfName getType() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Type);
    }

    public void setByteRange(int[] range) {
        PdfArray array = new PdfArray();
        for (int pdfNumber : range) {
            array.add(new PdfNumber(pdfNumber));
        }
        put(PdfName.ByteRange, array);
    }

    public PdfArray getByteRange() {
        return ((PdfDictionary) getPdfObject()).getAsArray(PdfName.ByteRange);
    }

    public void setContents(byte[] contents) {
        PdfString contentsString = new PdfString(contents).setHexWriting(true);
        contentsString.markAsUnencryptedObject();
        put(PdfName.Contents, contentsString);
    }

    public PdfString getContents() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.Contents);
    }

    public void setCert(byte[] cert) {
        put(PdfName.Cert, new PdfString(cert));
    }

    public PdfString getCert() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.Cert);
    }

    public void setName(String name) {
        put(PdfName.Name, new PdfString(name, PdfEncodings.UNICODE_BIG));
    }

    public String getName() {
        PdfString nameStr = ((PdfDictionary) getPdfObject()).getAsString(PdfName.Name);
        PdfName nameName = ((PdfDictionary) getPdfObject()).getAsName(PdfName.Name);
        if (nameStr != null) {
            return nameStr.toUnicodeString();
        }
        if (nameName != null) {
            return nameName.getValue();
        }
        return null;
    }

    public void setDate(PdfDate date) {
        put(PdfName.f1352M, date.getPdfObject());
    }

    public PdfString getDate() {
        return ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1352M);
    }

    public void setLocation(String location) {
        put(PdfName.Location, new PdfString(location, PdfEncodings.UNICODE_BIG));
    }

    public String getLocation() {
        PdfString locationStr = ((PdfDictionary) getPdfObject()).getAsString(PdfName.Location);
        if (locationStr != null) {
            return locationStr.toUnicodeString();
        }
        return null;
    }

    public void setReason(String reason) {
        put(PdfName.Reason, new PdfString(reason, PdfEncodings.UNICODE_BIG));
    }

    public String getReason() {
        PdfString reasonStr = ((PdfDictionary) getPdfObject()).getAsString(PdfName.Reason);
        if (reasonStr != null) {
            return reasonStr.toUnicodeString();
        }
        return null;
    }

    public void setSignatureCreator(String signatureCreator) {
        if (signatureCreator != null) {
            getPdfSignatureBuildProperties().setSignatureCreator(signatureCreator);
        }
    }

    public void setContact(String contactInfo) {
        put(PdfName.ContactInfo, new PdfString(contactInfo, PdfEncodings.UNICODE_BIG));
    }

    public PdfSignature put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    private PdfSignatureBuildProperties getPdfSignatureBuildProperties() {
        PdfDictionary buildPropDict = ((PdfDictionary) getPdfObject()).getAsDictionary(PdfName.Prop_Build);
        if (buildPropDict == null) {
            buildPropDict = new PdfDictionary();
            put(PdfName.Prop_Build, buildPropDict);
        }
        return new PdfSignatureBuildProperties(buildPropDict);
    }
}
