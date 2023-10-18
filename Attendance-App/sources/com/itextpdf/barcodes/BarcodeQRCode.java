package com.itextpdf.barcodes;

import com.itextpdf.barcodes.qrcode.ByteMatrix;
import com.itextpdf.barcodes.qrcode.EncodeHintType;
import com.itextpdf.barcodes.qrcode.QRCodeWriter;
import com.itextpdf.barcodes.qrcode.WriterException;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.util.Map;

public class BarcodeQRCode extends Barcode2D {

    /* renamed from: bm */
    ByteMatrix f1180bm;
    String code;
    Map<EncodeHintType, Object> hints;

    public BarcodeQRCode(String code2, Map<EncodeHintType, Object> hints2) {
        this.code = code2;
        this.hints = hints2;
        regenerate();
    }

    public BarcodeQRCode(String content) {
        this(content, (Map<EncodeHintType, Object>) null);
    }

    public BarcodeQRCode() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code2) {
        this.code = code2;
        regenerate();
    }

    public Map<EncodeHintType, Object> getHints() {
        return this.hints;
    }

    public void setHints(Map<EncodeHintType, Object> hints2) {
        this.hints = hints2;
        regenerate();
    }

    public void regenerate() {
        if (this.code != null) {
            try {
                this.f1180bm = new QRCodeWriter().encode(this.code, 1, 1, this.hints);
            } catch (WriterException ex) {
                throw new IllegalArgumentException(ex.getMessage(), ex.getCause());
            }
        }
    }

    public Rectangle getBarcodeSize() {
        return new Rectangle(0.0f, 0.0f, (float) this.f1180bm.getWidth(), (float) this.f1180bm.getHeight());
    }

    public Rectangle getBarcodeSize(float moduleSize) {
        return new Rectangle(0.0f, 0.0f, ((float) this.f1180bm.getWidth()) * moduleSize, ((float) this.f1180bm.getHeight()) * moduleSize);
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color foreground) {
        return placeBarcode(canvas, foreground, 1.0f);
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color foreground, float moduleSide) {
        float f = moduleSide;
        int width = this.f1180bm.getWidth();
        int height = this.f1180bm.getHeight();
        byte[][] mt = this.f1180bm.getArray();
        if (foreground != null) {
            canvas.setFillColor(foreground);
        }
        for (int y = 0; y < height; y++) {
            byte[] line = mt[y];
            for (int x = 0; x < width; x++) {
                if (line[x] == 0) {
                    canvas.rectangle((double) (((float) x) * f), (double) (((float) ((height - y) - 1)) * f), (double) f, (double) f);
                }
            }
        }
        canvas.fill();
        return getBarcodeSize(f);
    }

    public PdfFormXObject createFormXObject(Color foreground, PdfDocument document) {
        return createFormXObject(foreground, 1.0f, document);
    }

    public PdfFormXObject createFormXObject(Color foreground, float moduleSize, PdfDocument document) {
        Rectangle rectangle = null;
        PdfFormXObject xObject = new PdfFormXObject((Rectangle) null);
        xObject.setBBox(new PdfArray(placeBarcode(new PdfCanvas(xObject, document), foreground, moduleSize)));
        return xObject;
    }

    public Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        int f = foreground.getRGB();
        int g = background.getRGB();
        Canvas canvas = new Canvas();
        int width = this.f1180bm.getWidth();
        int height = this.f1180bm.getHeight();
        int[] pix = new int[(width * height)];
        byte[][] mt = this.f1180bm.getArray();
        for (int y = 0; y < height; y++) {
            byte[] line = mt[y];
            for (int x = 0; x < width; x++) {
                pix[(y * width) + x] = line[x] == 0 ? f : g;
            }
        }
        return canvas.createImage(new MemoryImageSource(width, height, pix, 0, width));
    }

    private byte[] getBitMatrix() {
        int width = this.f1180bm.getWidth();
        int height = this.f1180bm.getHeight();
        int stride = (width + 7) / 8;
        byte[] b = new byte[(stride * height)];
        byte[][] mt = this.f1180bm.getArray();
        for (int y = 0; y < height; y++) {
            byte[] line = mt[y];
            for (int x = 0; x < width; x++) {
                if (line[x] != 0) {
                    int offset = (stride * y) + (x / 8);
                    b[offset] = (byte) (b[offset] | ((byte) (128 >> (x % 8))));
                }
            }
        }
        return b;
    }
}
