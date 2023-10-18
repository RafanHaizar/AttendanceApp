package com.itextpdf.kernel.pdf.canvas.wmf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.p026io.image.ImageData;
import com.itextpdf.p026io.image.ImageType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WmfImageHelper {
    public static float wmfFontCorrection = 0.86f;
    private float plainHeight;
    private float plainWidth;
    private WmfImageData wmf;

    public WmfImageHelper(ImageData wmf2) {
        if (wmf2.getOriginalType() == ImageType.WMF) {
            this.wmf = (WmfImageData) wmf2;
            processParameters();
            return;
        }
        throw new IllegalArgumentException("WMF image expected");
    }

    private void processParameters() {
        String errorID;
        InputStream is = null;
        try {
            if (this.wmf.getData() == null) {
                is = this.wmf.getUrl().openStream();
                errorID = this.wmf.getUrl().toString();
            } else {
                is = new ByteArrayInputStream(this.wmf.getData());
                errorID = "Byte array";
            }
            InputMeta in = new InputMeta(is);
            if (in.readInt() == -1698247209) {
                in.readWord();
                int left = in.readShort();
                int top = in.readShort();
                int right = in.readShort();
                int bottom = in.readShort();
                int inch = in.readWord();
                this.wmf.setDpi(72, 72);
                this.wmf.setHeight((((float) (bottom - top)) / ((float) inch)) * 72.0f);
                this.wmf.setWidth((((float) (right - left)) / ((float) inch)) * 72.0f);
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
            } else {
                throw new PdfException(PdfException._1IsNotAValidPlaceableWindowsMetafile, (Object) errorID);
            }
        } catch (IOException e2) {
            throw new PdfException(PdfException.WmfImageException);
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                }
            }
            throw th;
        }
    }

    public PdfXObject createFormXObject(PdfDocument document) {
        InputStream is;
        PdfFormXObject pdfForm = new PdfFormXObject(new Rectangle(0.0f, 0.0f, this.wmf.getWidth(), this.wmf.getHeight()));
        PdfCanvas canvas = new PdfCanvas(pdfForm, document);
        InputStream is2 = null;
        try {
            if (this.wmf.getData() == null) {
                is = this.wmf.getUrl().openStream();
            } else {
                is = new ByteArrayInputStream(this.wmf.getData());
            }
            new MetaDo(is, canvas).readAll();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            return pdfForm;
        } catch (IOException e2) {
            throw new PdfException(PdfException.WmfImageException, (Throwable) e2);
        } catch (Throwable th) {
            if (is2 != null) {
                try {
                    is2.close();
                } catch (IOException e3) {
                }
            }
            throw th;
        }
    }
}
