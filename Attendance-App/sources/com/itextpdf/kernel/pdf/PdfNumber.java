package com.itextpdf.kernel.pdf;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.ByteUtils;
import java.nio.charset.StandardCharsets;
import org.slf4j.LoggerFactory;

public class PdfNumber extends PdfPrimitiveObject {
    private static final long serialVersionUID = -250799718574024246L;
    private boolean changed;
    private boolean isDouble;
    private double value;

    public PdfNumber(double value2) {
        this.changed = false;
        setValue(value2);
    }

    public PdfNumber(int value2) {
        this.changed = false;
        setValue(value2);
    }

    public PdfNumber(byte[] content) {
        super(content);
        this.changed = false;
        this.isDouble = true;
        this.value = Double.NaN;
    }

    private PdfNumber() {
        this.changed = false;
    }

    public byte getType() {
        return 8;
    }

    public double getValue() {
        if (Double.isNaN(this.value)) {
            generateValue();
        }
        return this.value;
    }

    public double doubleValue() {
        return getValue();
    }

    public float floatValue() {
        return (float) getValue();
    }

    public long longValue() {
        return (long) getValue();
    }

    public int intValue() {
        return (int) getValue();
    }

    public void setValue(int value2) {
        this.value = (double) value2;
        this.isDouble = false;
        this.content = null;
        this.changed = true;
    }

    public void setValue(double value2) {
        this.value = value2;
        this.isDouble = true;
        this.content = null;
    }

    public void increment() {
        double d = this.value + 1.0d;
        this.value = d;
        setValue(d);
    }

    public void decrement() {
        double d = this.value - 1.0d;
        this.value = d;
        setValue(d);
    }

    public String toString() {
        if (this.content != null) {
            return new String(this.content, StandardCharsets.ISO_8859_1);
        }
        if (this.isDouble) {
            return new String(ByteUtils.getIsoBytes(getValue()), StandardCharsets.ISO_8859_1);
        }
        return new String(ByteUtils.getIsoBytes(intValue()), StandardCharsets.ISO_8859_1);
    }

    public boolean equals(Object o) {
        return this == o || (o != null && getClass() == o.getClass() && Double.compare(((PdfNumber) o).value, this.value) == 0);
    }

    public boolean hasDecimalPoint() {
        return toString().contains(".");
    }

    public int hashCode() {
        if (this.changed) {
            LoggerFactory.getLogger((Class<?>) PdfReader.class).warn(LogMessageConstant.CALCULATE_HASHCODE_FOR_MODIFIED_PDFNUMBER);
            this.changed = false;
        }
        long hash = Double.doubleToLongBits(this.value);
        return (int) ((hash >>> 32) ^ hash);
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return new PdfNumber();
    }

    /* access modifiers changed from: protected */
    public boolean isDoubleNumber() {
        return this.isDouble;
    }

    /* access modifiers changed from: protected */
    public void generateContent() {
        if (this.isDouble) {
            this.content = ByteUtils.getIsoBytes(this.value);
        } else {
            this.content = ByteUtils.getIsoBytes((int) this.value);
        }
    }

    /* access modifiers changed from: protected */
    public void generateValue() {
        try {
            this.value = Double.parseDouble(new String(this.content, StandardCharsets.ISO_8859_1));
        } catch (NumberFormatException e) {
            this.value = Double.NaN;
        }
        this.isDouble = true;
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        PdfNumber number = (PdfNumber) from;
        this.value = number.value;
        this.isDouble = number.isDouble;
    }
}
