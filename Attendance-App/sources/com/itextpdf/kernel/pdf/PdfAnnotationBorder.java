package com.itextpdf.kernel.pdf;

public class PdfAnnotationBorder extends PdfObjectWrapper<PdfArray> {
    private static final long serialVersionUID = -4058970009483489460L;

    public PdfAnnotationBorder(float hRadius, float vRadius, float width) {
        this(hRadius, vRadius, width, (PdfDashPattern) null);
    }

    public PdfAnnotationBorder(float hRadius, float vRadius, float width, PdfDashPattern dash) {
        super(new PdfArray(new float[]{hRadius, vRadius, width}));
        if (dash != null) {
            PdfArray dashArray = new PdfArray();
            ((PdfArray) getPdfObject()).add(dashArray);
            if (dash.getDash() >= 0.0f) {
                dashArray.add(new PdfNumber((double) dash.getDash()));
            }
            if (dash.getGap() >= 0.0f) {
                dashArray.add(new PdfNumber((double) dash.getGap()));
            }
            if (dash.getPhase() >= 0.0f) {
                ((PdfArray) getPdfObject()).add(new PdfNumber((double) dash.getPhase()));
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }
}
