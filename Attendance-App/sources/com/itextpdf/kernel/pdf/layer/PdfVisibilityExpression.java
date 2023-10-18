package com.itextpdf.kernel.pdf.layer;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;

public class PdfVisibilityExpression extends PdfObjectWrapper<PdfArray> {
    private static final long serialVersionUID = 4152369893262322542L;

    public PdfVisibilityExpression(PdfArray visibilityExpressionArray) {
        super(visibilityExpressionArray);
        PdfName operator = visibilityExpressionArray.getAsName(0);
        if (visibilityExpressionArray.size() < 1 || (!PdfName.f1366Or.equals(operator) && !PdfName.And.equals(operator) && !PdfName.Not.equals(operator))) {
            throw new IllegalArgumentException("Invalid visibilityExpressionArray");
        }
    }

    public PdfVisibilityExpression(PdfName operator) {
        super(new PdfArray());
        if (operator == null || (!PdfName.f1366Or.equals(operator) && !PdfName.And.equals(operator) && !PdfName.Not.equals(operator))) {
            throw new IllegalArgumentException("Invalid operator");
        }
        ((PdfArray) getPdfObject()).add(operator);
    }

    public void addOperand(PdfLayer layer) {
        ((PdfArray) getPdfObject()).add(layer.getPdfObject());
        ((PdfArray) getPdfObject()).setModified();
    }

    public void addOperand(PdfVisibilityExpression expression) {
        ((PdfArray) getPdfObject()).add(expression.getPdfObject());
        ((PdfArray) getPdfObject()).setModified();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
