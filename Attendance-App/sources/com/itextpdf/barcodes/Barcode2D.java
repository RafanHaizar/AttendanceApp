package com.itextpdf.barcodes;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

public abstract class Barcode2D {
    protected static final float DEFAULT_MODULE_SIZE = 1.0f;

    public abstract PdfFormXObject createFormXObject(Color color, PdfDocument pdfDocument);

    public abstract Rectangle getBarcodeSize();

    public abstract Rectangle placeBarcode(PdfCanvas pdfCanvas, Color color);

    public PdfFormXObject createFormXObject(PdfDocument document) {
        return createFormXObject((Color) null, document);
    }
}
