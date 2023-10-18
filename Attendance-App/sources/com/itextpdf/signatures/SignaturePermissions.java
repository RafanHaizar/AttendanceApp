package com.itextpdf.signatures;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import java.util.ArrayList;
import java.util.List;

public class SignaturePermissions {
    boolean annotationsAllowed = true;
    boolean certification = false;
    List<FieldLock> fieldLocks = new ArrayList();
    boolean fillInAllowed = true;

    public class FieldLock {
        PdfName action;
        PdfArray fields;

        public FieldLock(PdfName action2, PdfArray fields2) {
            this.action = action2;
            this.fields = fields2;
        }

        public PdfName getAction() {
            return this.action;
        }

        public PdfArray getFields() {
            return this.fields;
        }

        public String toString() {
            StringBuilder append = new StringBuilder().append(this.action.toString());
            PdfArray pdfArray = this.fields;
            return append.append(pdfArray == null ? "" : pdfArray.toString()).toString();
        }
    }

    public SignaturePermissions(PdfDictionary sigDict, SignaturePermissions previous) {
        if (previous != null) {
            this.annotationsAllowed &= previous.isAnnotationsAllowed();
            this.fillInAllowed &= previous.isFillInAllowed();
            this.fieldLocks.addAll(previous.getFieldLocks());
        }
        PdfArray ref = sigDict.getAsArray(PdfName.Reference);
        if (ref != null) {
            for (int i = 0; i < ref.size(); i++) {
                PdfDictionary dict = ref.getAsDictionary(i);
                PdfDictionary params = dict.getAsDictionary(PdfName.TransformParams);
                if (PdfName.DocMDP.equals(dict.getAsName(PdfName.TransformMethod))) {
                    this.certification = true;
                }
                PdfName action = params.getAsName(PdfName.Action);
                if (action != null) {
                    this.fieldLocks.add(new FieldLock(action, params.getAsArray(PdfName.Fields)));
                }
                PdfNumber p = params.getAsNumber(PdfName.f1367P);
                if (p != null) {
                    switch (p.intValue()) {
                        case 1:
                            this.fillInAllowed &= false;
                            break;
                        case 2:
                            break;
                    }
                    this.annotationsAllowed &= false;
                }
            }
        }
    }

    public boolean isCertification() {
        return this.certification;
    }

    public boolean isFillInAllowed() {
        return this.fillInAllowed;
    }

    public boolean isAnnotationsAllowed() {
        return this.annotationsAllowed;
    }

    public List<FieldLock> getFieldLocks() {
        return this.fieldLocks;
    }
}
