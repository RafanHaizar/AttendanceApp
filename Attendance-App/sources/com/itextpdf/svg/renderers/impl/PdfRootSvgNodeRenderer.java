package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import java.util.Map;

public class PdfRootSvgNodeRenderer implements ISvgNodeRenderer {
    ISvgNodeRenderer subTreeRoot;

    public PdfRootSvgNodeRenderer(ISvgNodeRenderer subTreeRoot2) {
        this.subTreeRoot = subTreeRoot2;
        subTreeRoot2.setParent(this);
    }

    public void setParent(ISvgNodeRenderer parent) {
    }

    public ISvgNodeRenderer getParent() {
        return null;
    }

    public void draw(SvgDrawContext context) {
        context.addViewPort(calculateViewPort(context));
        PdfCanvas currentCanvas = context.getCurrentCanvas();
        currentCanvas.concatMatrix(calculateTransformation(context));
        currentCanvas.writeLiteral("% svg root\n");
        this.subTreeRoot.draw(context);
    }

    public void setAttributesAndStyles(Map<String, String> map) {
    }

    public String getAttribute(String key) {
        return null;
    }

    public void setAttribute(String key, String value) {
    }

    public Map<String, String> getAttributeMapCopy() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public AffineTransform calculateTransformation(SvgDrawContext context) {
        Rectangle viewPort = context.getCurrentViewPort();
        float horizontal = viewPort.getX();
        float vertical = viewPort.getY() + viewPort.getHeight();
        AffineTransform transform = AffineTransform.getTranslateInstance(0.0d, 0.0d);
        transform.concatenate(AffineTransform.getTranslateInstance((double) horizontal, (double) vertical));
        transform.concatenate(new AffineTransform(1.0d, 0.0d, 0.0d, -1.0d, 0.0d, 0.0d));
        return transform;
    }

    /* access modifiers changed from: package-private */
    public Rectangle calculateViewPort(SvgDrawContext context) {
        PdfStream contentStream = context.getCurrentCanvas().getContentStream();
        if (contentStream.containsKey(PdfName.BBox)) {
            PdfArray bboxArray = contentStream.getAsArray(PdfName.BBox);
            float portX = bboxArray.getAsNumber(0).floatValue();
            float portY = bboxArray.getAsNumber(1).floatValue();
            return new Rectangle(portX, portY, bboxArray.getAsNumber(2).floatValue() - portX, bboxArray.getAsNumber(3).floatValue() - portY);
        }
        throw new SvgProcessingException(SvgLogMessageConstant.ROOT_SVG_NO_BBOX);
    }

    public ISvgNodeRenderer createDeepCopy() {
        return new PdfRootSvgNodeRenderer(this.subTreeRoot.createDeepCopy());
    }
}
