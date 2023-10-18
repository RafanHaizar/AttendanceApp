package com.itextpdf.kernel.font;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.p026io.image.ImageData;
import com.itextpdf.p026io.source.ByteUtils;
import java.nio.charset.StandardCharsets;

public final class Type3Glyph extends PdfCanvas {
    private static final String D_0_STR = "d0\n";
    private static final String D_1_STR = "d1\n";

    /* renamed from: d0 */
    private static final byte[] f1255d0 = ByteUtils.getIsoBytes(D_0_STR);

    /* renamed from: d1 */
    private static final byte[] f1256d1 = ByteUtils.getIsoBytes(D_1_STR);
    private static final long serialVersionUID = 5811604071799271336L;
    private boolean isColor = false;
    private float llx;
    private float lly;
    private float urx;
    private float ury;

    /* renamed from: wx */
    private float f1257wx;

    Type3Glyph(PdfDocument pdfDocument, float wx, float llx2, float lly2, float urx2, float ury2, boolean isColor2) {
        super((PdfStream) new PdfStream().makeIndirect(pdfDocument), (PdfResources) null, pdfDocument);
        writeMetrics(wx, llx2, lly2, urx2, ury2, isColor2);
    }

    Type3Glyph(PdfStream pdfStream, PdfDocument document) {
        super(pdfStream, (PdfResources) null, document);
        if (pdfStream.getBytes() != null) {
            fillBBFromBytes(pdfStream.getBytes());
        }
    }

    public float getWx() {
        return this.f1257wx;
    }

    public float getLlx() {
        return this.llx;
    }

    public float getLly() {
        return this.lly;
    }

    public float getUrx() {
        return this.urx;
    }

    public float getUry() {
        return this.ury;
    }

    public boolean isColor() {
        return this.isColor;
    }

    private void writeMetrics(float wx, float llx2, float lly2, float urx2, float ury2, boolean isColor2) {
        this.isColor = isColor2;
        this.f1257wx = wx;
        this.llx = llx2;
        this.lly = lly2;
        this.urx = urx2;
        this.ury = ury2;
        if (isColor2) {
            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(wx)).writeSpace()).writeFloat(0.0f)).writeSpace()).writeBytes(f1255d0);
        } else {
            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(wx)).writeSpace()).writeFloat(0.0f)).writeSpace()).writeFloat(llx2)).writeSpace()).writeFloat(lly2)).writeSpace()).writeFloat(urx2)).writeSpace()).writeFloat(ury2)).writeSpace()).writeBytes(f1256d1);
        }
    }

    public PdfXObject addImage(ImageData image, float a, float b, float c, float d, float e, float f, boolean inlineImage) {
        if (this.isColor || (image.isMask() && (image.getBpc() == 1 || image.getBpc() > 255))) {
            return super.addImage(image, a, b, c, d, e, f, inlineImage);
        }
        throw new PdfException("Not colorized type3 fonts accept only mask images.");
    }

    private void fillBBFromBytes(byte[] bytes) {
        String str = new String(bytes, StandardCharsets.ISO_8859_1);
        int d0Pos = str.indexOf(D_0_STR);
        int d1Pos = str.indexOf(D_1_STR);
        if (d0Pos != -1) {
            this.isColor = true;
            String[] bbArray = str.substring(0, d0Pos - 1).split(" ");
            if (bbArray.length == 2) {
                this.f1257wx = Float.parseFloat(bbArray[0]);
            }
        } else if (d1Pos != -1) {
            this.isColor = false;
            String[] bbArray2 = str.substring(0, d1Pos - 1).split(" ");
            if (bbArray2.length == 6) {
                this.f1257wx = Float.parseFloat(bbArray2[0]);
                this.llx = Float.parseFloat(bbArray2[2]);
                this.lly = Float.parseFloat(bbArray2[3]);
                this.urx = Float.parseFloat(bbArray2[4]);
                this.ury = Float.parseFloat(bbArray2[5]);
            }
        }
    }
}
