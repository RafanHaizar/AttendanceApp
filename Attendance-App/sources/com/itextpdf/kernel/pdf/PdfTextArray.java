package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.font.PdfFont;
import java.util.Collection;

public class PdfTextArray extends PdfArray {
    private static final long serialVersionUID = 2555632135770071680L;
    private float lastNumber = Float.NaN;
    private StringBuilder lastString;

    public void add(PdfObject pdfObject) {
        if (pdfObject.isNumber()) {
            add(((PdfNumber) pdfObject).floatValue());
        } else if (pdfObject instanceof PdfString) {
            add(((PdfString) pdfObject).getValueBytes());
        }
    }

    public void addAll(PdfArray a) {
        if (a != null) {
            addAll((Collection<PdfObject>) a.list);
        }
    }

    public void addAll(Collection<PdfObject> c) {
        for (PdfObject obj : c) {
            add(obj);
        }
    }

    public boolean add(float number) {
        if (number == 0.0f) {
            return false;
        }
        if (!Float.isNaN(this.lastNumber)) {
            float f = this.lastNumber + number;
            this.lastNumber = f;
            if (f != 0.0f) {
                set(size() - 1, new PdfNumber((double) this.lastNumber));
            } else {
                remove(size() - 1);
            }
        } else {
            this.lastNumber = number;
            super.add(new PdfNumber((double) this.lastNumber));
        }
        this.lastString = null;
        return true;
    }

    public boolean add(String text, PdfFont font) {
        return add(font.convertToBytes(text));
    }

    public boolean add(byte[] text) {
        return add(new PdfString(text).getValue());
    }

    /* access modifiers changed from: protected */
    public boolean add(String text) {
        if (text.length() <= 0) {
            return false;
        }
        StringBuilder sb = this.lastString;
        if (sb != null) {
            sb.append(text);
            set(size() - 1, new PdfString(this.lastString.toString()));
        } else {
            this.lastString = new StringBuilder(text);
            super.add(new PdfString(this.lastString.toString()));
        }
        this.lastNumber = Float.NaN;
        return true;
    }
}
