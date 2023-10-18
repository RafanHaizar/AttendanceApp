package com.itextpdf.kernel.pdf.collection;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDate;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;

public class PdfCollectionField extends PdfObjectWrapper<PdfDictionary> {
    public static final int CREATIONDATE = 6;
    public static final int DATE = 1;
    public static final int DESC = 4;
    public static final int FILENAME = 3;
    public static final int MODDATE = 5;
    public static final int NUMBER = 2;
    public static final int SIZE = 7;
    public static final int TEXT = 0;
    private static final long serialVersionUID = 4766153544105870238L;
    protected int subType;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected PdfCollectionField(com.itextpdf.kernel.pdf.PdfDictionary r10) {
        /*
            r9 = this;
            r9.<init>(r10)
            com.itextpdf.kernel.pdf.PdfName r0 = com.itextpdf.kernel.pdf.PdfName.Subtype
            com.itextpdf.kernel.pdf.PdfName r0 = r10.getAsName(r0)
            java.lang.String r0 = r0.getValue()
            int r1 = r0.hashCode()
            r2 = 0
            r3 = 6
            r4 = 5
            r5 = 4
            r6 = 3
            r7 = 2
            r8 = 1
            switch(r1) {
                case -1404350032: goto L_0x0058;
                case 68: goto L_0x004e;
                case 70: goto L_0x0044;
                case 78: goto L_0x003a;
                case 2126513: goto L_0x0030;
                case 2577441: goto L_0x0026;
                case 1749851981: goto L_0x001c;
                default: goto L_0x001b;
            }
        L_0x001b:
            goto L_0x0062
        L_0x001c:
            java.lang.String r1 = "CreationDate"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x001b
            r1 = 5
            goto L_0x0063
        L_0x0026:
            java.lang.String r1 = "Size"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x001b
            r1 = 6
            goto L_0x0063
        L_0x0030:
            java.lang.String r1 = "Desc"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x001b
            r1 = 3
            goto L_0x0063
        L_0x003a:
            java.lang.String r1 = "N"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x001b
            r1 = 1
            goto L_0x0063
        L_0x0044:
            java.lang.String r1 = "F"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x001b
            r1 = 2
            goto L_0x0063
        L_0x004e:
            java.lang.String r1 = "D"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x001b
            r1 = 0
            goto L_0x0063
        L_0x0058:
            java.lang.String r1 = "ModDate"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x001b
            r1 = 4
            goto L_0x0063
        L_0x0062:
            r1 = -1
        L_0x0063:
            switch(r1) {
                case 0: goto L_0x007c;
                case 1: goto L_0x0079;
                case 2: goto L_0x0076;
                case 3: goto L_0x0073;
                case 4: goto L_0x0070;
                case 5: goto L_0x006d;
                case 6: goto L_0x0069;
                default: goto L_0x0066;
            }
        L_0x0066:
            r9.subType = r2
            goto L_0x007f
        L_0x0069:
            r1 = 7
            r9.subType = r1
            goto L_0x007f
        L_0x006d:
            r9.subType = r3
            goto L_0x007f
        L_0x0070:
            r9.subType = r4
            goto L_0x007f
        L_0x0073:
            r9.subType = r5
            goto L_0x007f
        L_0x0076:
            r9.subType = r6
            goto L_0x007f
        L_0x0079:
            r9.subType = r7
            goto L_0x007f
        L_0x007c:
            r9.subType = r8
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.pdf.collection.PdfCollectionField.<init>(com.itextpdf.kernel.pdf.PdfDictionary):void");
    }

    public PdfCollectionField(String name, int subType2) {
        super(new PdfDictionary());
        ((PdfDictionary) getPdfObject()).put(PdfName.f1357N, new PdfString(name));
        this.subType = subType2;
        switch (subType2) {
            case 1:
                ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.f1312D);
                return;
            case 2:
                ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.f1357N);
                return;
            case 3:
                ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.f1324F);
                return;
            case 4:
                ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.Desc);
                return;
            case 5:
                ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.ModDate);
                return;
            case 6:
                ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.CreationDate);
                return;
            case 7:
                ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.Size);
                return;
            default:
                ((PdfDictionary) getPdfObject()).put(PdfName.Subtype, PdfName.f1385S);
                return;
        }
    }

    public PdfCollectionField setOrder(int order) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1361O, new PdfNumber(order));
        return this;
    }

    public PdfNumber getOrder() {
        return ((PdfDictionary) getPdfObject()).getAsNumber(PdfName.f1361O);
    }

    public PdfCollectionField setVisibility(boolean visible) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1406V, PdfBoolean.valueOf(visible));
        return this;
    }

    public PdfBoolean getVisibility() {
        return ((PdfDictionary) getPdfObject()).getAsBoolean(PdfName.f1406V);
    }

    public PdfCollectionField setEditable(boolean editable) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1320E, PdfBoolean.valueOf(editable));
        return this;
    }

    public PdfBoolean getEditable() {
        return ((PdfDictionary) getPdfObject()).getAsBoolean(PdfName.f1320E);
    }

    public PdfObject getValue(String value) {
        switch (this.subType) {
            case 0:
                return new PdfString(value);
            case 1:
                return new PdfDate(PdfDate.decode(value)).getPdfObject();
            case 2:
                return new PdfNumber(Double.parseDouble(value.trim()));
            default:
                throw new PdfException(PdfException._1IsNotAnAcceptableValueForTheField2).setMessageParams(value, ((PdfDictionary) getPdfObject()).getAsString(PdfName.f1357N).getValue());
        }
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
