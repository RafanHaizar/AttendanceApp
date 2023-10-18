package com.itextpdf.barcodes;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.awt.Image;

public class BarcodeEANSUPP extends Barcode1D {
    protected Barcode1D ean;
    protected Barcode1D supp;

    public BarcodeEANSUPP(Barcode1D ean2, Barcode1D supp2) {
        super(ean2.document);
        this.f1170n = 8.0f;
        this.ean = ean2;
        this.supp = supp2;
    }

    public Rectangle getBarcodeSize() {
        Rectangle rect = this.ean.getBarcodeSize();
        rect.setWidth(rect.getWidth() + this.supp.getBarcodeSize().getWidth() + this.f1170n);
        return rect;
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
        PdfCanvas pdfCanvas = canvas;
        Color color = barColor;
        Color color2 = textColor;
        if (this.supp.getFont() != null) {
            this.supp.setBarHeight((this.ean.getBarHeight() + this.supp.getBaseline()) - (((float) this.supp.getFont().getFontProgram().getFontMetrics().getCapHeight()) * (this.supp.getSize() / 1000.0f)));
        } else {
            this.supp.setBarHeight(this.ean.getBarHeight());
        }
        Rectangle eanR = this.ean.getBarcodeSize();
        canvas.saveState();
        this.ean.placeBarcode(pdfCanvas, color, color2);
        canvas.restoreState();
        canvas.saveState();
        canvas.concatMatrix(1.0d, 0.0d, 0.0d, 1.0d, (double) (eanR.getWidth() + this.f1170n), (double) (eanR.getHeight() - this.ean.getBarHeight()));
        this.supp.placeBarcode(pdfCanvas, color, textColor);
        canvas.restoreState();
        return getBarcodeSize();
    }

    public Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        throw new UnsupportedOperationException(PdfException.TwoBarcodeMustBeExternally);
    }
}
