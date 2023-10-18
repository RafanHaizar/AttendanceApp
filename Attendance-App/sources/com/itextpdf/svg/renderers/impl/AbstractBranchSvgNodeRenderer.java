package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.NoninvertibleTransformException;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.renderers.IBranchSvgNodeRenderer;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.SvgCssUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;

public abstract class AbstractBranchSvgNodeRenderer extends AbstractSvgNodeRenderer implements IBranchSvgNodeRenderer {
    private final List<ISvgNodeRenderer> children = new ArrayList();

    public abstract ISvgNodeRenderer createDeepCopy();

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        if (getChildren().size() > 0) {
            PdfStream stream = new PdfStream();
            stream.put(PdfName.Type, PdfName.XObject);
            stream.put(PdfName.Subtype, PdfName.Form);
            PdfFormXObject xObject = (PdfFormXObject) PdfXObject.makeXObject(stream);
            PdfCanvas newCanvas = new PdfCanvas(xObject, context.getCurrentCanvas().getDocument());
            applyViewBox(context);
            boolean overflowVisible = isOverflowVisible(this);
            if (!(this instanceof MarkerSvgNodeRenderer) || !overflowVisible) {
                stream.put(PdfName.BBox, new PdfArray(context.getCurrentViewPort().clone()));
            } else {
                writeBBoxAccordingToVisibleOverflow(context, stream);
            }
            if (this instanceof MarkerSvgNodeRenderer) {
                ((MarkerSvgNodeRenderer) this).applyMarkerAttributes(context);
            }
            context.pushCanvas(newCanvas);
            if (!(this instanceof MarkerSvgNodeRenderer) || !overflowVisible) {
                applyViewportClip(context);
            }
            applyViewportTranslationCorrection(context);
            for (ISvgNodeRenderer child : getChildren()) {
                if (!(child instanceof MarkerSvgNodeRenderer)) {
                    newCanvas.saveState();
                    child.draw(context);
                    newCanvas.restoreState();
                }
            }
            cleanUp(context);
            context.getCurrentCanvas().addXObject(xObject, 0.0f, 0.0f);
        }
    }

    /* access modifiers changed from: package-private */
    public void applyViewBox(SvgDrawContext context) {
        if (this.attributesAndStyles == null || !this.attributesAndStyles.containsKey(SvgConstants.Attributes.VIEWBOX)) {
            calculateAndApplyViewBox(context, new float[]{0.0f, 0.0f, context.getCurrentViewPort().getWidth(), context.getCurrentViewPort().getHeight()}, context.getCurrentViewPort());
            return;
        }
        calculateAndApplyViewBox(context, getViewBoxValues(), context.getCurrentViewPort());
    }

    /* access modifiers changed from: package-private */
    public String[] retrieveAlignAndMeet() {
        String meetOrSlice = SvgConstants.Values.MEET;
        String align = "xmidymid";
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.PRESERVE_ASPECT_RATIO)) {
            List<String> aspectRatioValuesSplitValues = SvgCssUtils.splitValueList((String) this.attributesAndStyles.get(SvgConstants.Attributes.PRESERVE_ASPECT_RATIO));
            align = aspectRatioValuesSplitValues.get(0).toLowerCase();
            if (aspectRatioValuesSplitValues.size() > 1) {
                meetOrSlice = aspectRatioValuesSplitValues.get(1).toLowerCase();
            }
        }
        if ((this instanceof MarkerSvgNodeRenderer) && !"none".equals(align) && SvgConstants.Values.MEET.equals(meetOrSlice)) {
            align = SvgConstants.Values.XMIN_YMIN;
        }
        return new String[]{align, meetOrSlice};
    }

    private void applyViewportClip(SvgDrawContext context) {
        PdfCanvas currentCanvas = context.getCurrentCanvas();
        currentCanvas.rectangle(context.getCurrentViewPort());
        currentCanvas.clip();
        currentCanvas.endPath();
    }

    private void applyViewportTranslationCorrection(SvgDrawContext context) {
        PdfCanvas currentCanvas = context.getCurrentCanvas();
        AffineTransform tf = calculateViewPortTranslation(context);
        if (!tf.isIdentity() && "none".equals(getAttribute(SvgConstants.Attributes.PRESERVE_ASPECT_RATIO))) {
            currentCanvas.concatMatrix(tf);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.kernel.geom.AffineTransform processAspectRatioPosition(com.itextpdf.svg.renderers.SvgDrawContext r16, float[] r17, java.lang.String r18, float r19, float r20) {
        /*
            r15 = this;
            r0 = r15
            com.itextpdf.kernel.geom.AffineTransform r1 = new com.itextpdf.kernel.geom.AffineTransform
            r1.<init>()
            com.itextpdf.kernel.geom.Rectangle r2 = r16.getCurrentViewPort()
            r3 = 0
            r4 = r17[r3]
            r5 = 2
            r6 = r17[r5]
            r7 = 1073741824(0x40000000, float:2.0)
            float r6 = r6 / r7
            float r4 = r4 + r6
            r6 = 1
            r8 = r17[r6]
            r9 = 3
            r10 = r17[r9]
            float r10 = r10 / r7
            float r8 = r8 + r10
            float r10 = r2.getX()
            float r11 = r2.getWidth()
            float r11 = r11 / r7
            float r10 = r10 + r11
            float r11 = r2.getY()
            float r12 = r2.getHeight()
            float r12 = r12 / r7
            float r11 = r11 + r12
            r7 = 0
            r12 = 0
            java.util.Map r13 = r0.attributesAndStyles
            java.lang.String r14 = "x"
            boolean r13 = r13.containsKey(r14)
            if (r13 == 0) goto L_0x0049
            java.util.Map r13 = r0.attributesAndStyles
            java.lang.Object r13 = r13.get(r14)
            java.lang.String r13 = (java.lang.String) r13
            float r7 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseAbsoluteLength(r13)
        L_0x0049:
            java.util.Map r13 = r0.attributesAndStyles
            java.lang.String r14 = "y"
            boolean r13 = r13.containsKey(r14)
            if (r13 == 0) goto L_0x0060
            java.util.Map r13 = r0.attributesAndStyles
            java.lang.Object r13 = r13.get(r14)
            java.lang.String r13 = (java.lang.String) r13
            float r12 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseAbsoluteLength(r13)
        L_0x0060:
            boolean r13 = r0 instanceof com.itextpdf.svg.renderers.impl.MarkerSvgNodeRenderer
            if (r13 != 0) goto L_0x006e
            float r13 = r2.getX()
            float r7 = r7 - r13
            float r13 = r2.getY()
            float r12 = r12 - r13
        L_0x006e:
            java.lang.String r13 = r18.toLowerCase()
            int r14 = r13.hashCode()
            switch(r14) {
                case -470941129: goto L_0x00df;
                case -470940901: goto L_0x00d4;
                case -470940891: goto L_0x00c9;
                case -260378341: goto L_0x00be;
                case -260378113: goto L_0x00b2;
                case -260378103: goto L_0x00a7;
                case -251143131: goto L_0x009c;
                case -251142903: goto L_0x0091;
                case -251142893: goto L_0x0086;
                case 3387192: goto L_0x007b;
                default: goto L_0x0079;
            }
        L_0x0079:
            goto L_0x00eb
        L_0x007b:
            java.lang.String r14 = "none"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 0
            goto L_0x00ec
        L_0x0086:
            java.lang.String r14 = "xminymin"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 1
            goto L_0x00ec
        L_0x0091:
            java.lang.String r14 = "xminymid"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 2
            goto L_0x00ec
        L_0x009c:
            java.lang.String r14 = "xminymax"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 3
            goto L_0x00ec
        L_0x00a7:
            java.lang.String r14 = "xmidymin"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 4
            goto L_0x00ec
        L_0x00b2:
            java.lang.String r14 = "xmidymid"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 9
            goto L_0x00ec
        L_0x00be:
            java.lang.String r14 = "xmidymax"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 5
            goto L_0x00ec
        L_0x00c9:
            java.lang.String r14 = "xmaxymin"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 6
            goto L_0x00ec
        L_0x00d4:
            java.lang.String r14 = "xmaxymid"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 7
            goto L_0x00ec
        L_0x00df:
            java.lang.String r14 = "xmaxymax"
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0079
            r13 = 8
            goto L_0x00ec
        L_0x00eb:
            r13 = -1
        L_0x00ec:
            switch(r13) {
                case 0: goto L_0x014c;
                case 1: goto L_0x0145;
                case 2: goto L_0x013e;
                case 3: goto L_0x0132;
                case 4: goto L_0x012b;
                case 5: goto L_0x011f;
                case 6: goto L_0x0113;
                case 7: goto L_0x0107;
                case 8: goto L_0x00f6;
                default: goto L_0x00ef;
            }
        L_0x00ef:
            float r3 = r10 - r4
            float r7 = r7 + r3
            float r3 = r11 - r8
            float r12 = r12 + r3
            goto L_0x014d
        L_0x00f6:
            float r3 = r2.getWidth()
            r5 = r17[r5]
            float r3 = r3 - r5
            float r7 = r7 + r3
            float r3 = r2.getHeight()
            r5 = r17[r9]
            float r3 = r3 - r5
            float r12 = r12 + r3
            goto L_0x014d
        L_0x0107:
            float r3 = r2.getWidth()
            r5 = r17[r5]
            float r3 = r3 - r5
            float r7 = r7 + r3
            float r3 = r11 - r8
            float r12 = r12 + r3
            goto L_0x014d
        L_0x0113:
            float r3 = r2.getWidth()
            r5 = r17[r5]
            float r3 = r3 - r5
            float r7 = r7 + r3
            r3 = r17[r6]
            float r12 = r12 - r3
            goto L_0x014d
        L_0x011f:
            float r3 = r10 - r4
            float r7 = r7 + r3
            float r3 = r2.getHeight()
            r5 = r17[r9]
            float r3 = r3 - r5
            float r12 = r12 + r3
            goto L_0x014d
        L_0x012b:
            float r3 = r10 - r4
            float r7 = r7 + r3
            r3 = r17[r6]
            float r12 = r12 - r3
            goto L_0x014d
        L_0x0132:
            r3 = r17[r3]
            float r7 = r7 - r3
            float r3 = r2.getHeight()
            r5 = r17[r9]
            float r3 = r3 - r5
            float r12 = r12 + r3
            goto L_0x014d
        L_0x013e:
            r3 = r17[r3]
            float r7 = r7 - r3
            float r3 = r11 - r8
            float r12 = r12 + r3
            goto L_0x014d
        L_0x0145:
            r3 = r17[r3]
            float r7 = r7 - r3
            r3 = r17[r6]
            float r12 = r12 - r3
            goto L_0x014d
        L_0x014c:
        L_0x014d:
            float r7 = r7 / r19
            float r12 = r12 / r20
            double r5 = (double) r7
            double r13 = (double) r12
            r1.translate(r5, r13)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.renderers.impl.AbstractBranchSvgNodeRenderer.processAspectRatioPosition(com.itextpdf.svg.renderers.SvgDrawContext, float[], java.lang.String, float, float):com.itextpdf.kernel.geom.AffineTransform");
    }

    private void cleanUp(SvgDrawContext context) {
        if (getParent() != null) {
            context.removeCurrentViewPort();
        }
        context.popCanvas();
    }

    public final void addChild(ISvgNodeRenderer child) {
        if (child != null) {
            this.children.add(child);
        }
    }

    public final List<ISvgNodeRenderer> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    /* access modifiers changed from: protected */
    public final void deepCopyChildren(AbstractBranchSvgNodeRenderer deepCopy) {
        for (ISvgNodeRenderer child : this.children) {
            ISvgNodeRenderer newChild = child.createDeepCopy();
            child.setParent(deepCopy);
            deepCopy.addChild(newChild);
        }
    }

    /* access modifiers changed from: package-private */
    public void postDraw(SvgDrawContext context) {
    }

    /* access modifiers changed from: package-private */
    public void setPartOfClipPath(boolean isPart) {
        super.setPartOfClipPath(isPart);
        for (ISvgNodeRenderer child : this.children) {
            if (child instanceof AbstractSvgNodeRenderer) {
                ((AbstractSvgNodeRenderer) child).setPartOfClipPath(isPart);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void calculateAndApplyViewBox(SvgDrawContext context, float[] values, Rectangle currentViewPort) {
        float scaleHeight;
        float scaleWidth;
        float scaleWidth2;
        float[] fArr = values;
        String[] alignAndMeet = retrieveAlignAndMeet();
        String align = alignAndMeet[0];
        String meetOrSlice = alignAndMeet[1];
        float scaleWidth3 = currentViewPort.getWidth() / fArr[2];
        float scaleHeight2 = currentViewPort.getHeight() / fArr[3];
        if (true ^ "none".equals(align)) {
            if (SvgConstants.Values.MEET.equals(meetOrSlice)) {
                scaleWidth2 = Math.min(scaleWidth3, scaleHeight2);
            } else {
                scaleWidth2 = Math.max(scaleWidth3, scaleHeight2);
            }
            scaleWidth = scaleWidth2;
            scaleHeight = scaleWidth2;
        } else {
            scaleWidth = scaleWidth3;
            scaleHeight = scaleHeight2;
        }
        AffineTransform scale = AffineTransform.getScaleInstance((double) scaleWidth, (double) scaleHeight);
        AffineTransform transform = processAspectRatioPosition(context, scaleViewBoxValues(fArr, scaleWidth, scaleHeight), align, scaleWidth, scaleHeight);
        if (!scale.isIdentity()) {
            context.getCurrentCanvas().concatMatrix(scale);
            context.getCurrentViewPort().setWidth(currentViewPort.getWidth() / scaleWidth).setX(currentViewPort.getX() / scaleWidth).setHeight(currentViewPort.getHeight() / scaleHeight).setY(currentViewPort.getY() / scaleHeight);
        }
        if (!transform.isIdentity()) {
            context.getCurrentCanvas().concatMatrix(transform);
            String[] strArr = alignAndMeet;
            context.getCurrentViewPort().setX(currentViewPort.getX() + (((float) transform.getTranslateX()) * -1.0f)).setY(currentViewPort.getY() + (((float) transform.getTranslateY()) * -1.0f));
            return;
        }
    }

    /* access modifiers changed from: package-private */
    public float[] getViewBoxValues() {
        List<String> valueStrings = SvgCssUtils.splitValueList((String) this.attributesAndStyles.get(SvgConstants.Attributes.VIEWBOX));
        float[] values = new float[valueStrings.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = CssUtils.parseAbsoluteLength(valueStrings.get(i));
        }
        return values;
    }

    private static float[] scaleViewBoxValues(float[] values, float scaleWidth, float scaleHeight) {
        float[] scaledViewBoxValues = new float[values.length];
        scaledViewBoxValues[0] = values[0] * scaleWidth;
        scaledViewBoxValues[1] = values[1] * scaleHeight;
        scaledViewBoxValues[2] = values[2] * scaleWidth;
        scaledViewBoxValues[3] = values[3] * scaleHeight;
        return scaledViewBoxValues;
    }

    private static boolean isOverflowVisible(AbstractSvgNodeRenderer currentElement) {
        return CommonCssConstants.VISIBLE.equals(currentElement.attributesAndStyles.get(CommonCssConstants.OVERFLOW)) || "auto".equals(currentElement.attributesAndStyles.get(CommonCssConstants.OVERFLOW));
    }

    private static void writeBBoxAccordingToVisibleOverflow(SvgDrawContext context, PdfStream stream) {
        PdfStream pdfStream = stream;
        List<PdfCanvas> canvases = new ArrayList<>();
        int canvasesSize = context.size();
        for (int i = 0; i < canvasesSize; i++) {
            canvases.add(context.popCanvas());
        }
        AffineTransform transform = new AffineTransform();
        int i2 = 1;
        int i3 = canvases.size() - 1;
        while (i3 >= 0) {
            PdfCanvas canvas = canvases.get(i3);
            Matrix matrix = canvas.getGraphicsState().getCtm();
            double d = (double) matrix.get(0);
            double d2 = (double) matrix.get(i2);
            double d3 = (double) matrix.get(3);
            double d4 = (double) matrix.get(4);
            List<PdfCanvas> canvases2 = canvases;
            double d5 = (double) matrix.get(6);
            Matrix matrix2 = matrix;
            AffineTransform affineTransform = r8;
            AffineTransform affineTransform2 = new AffineTransform(d, d2, d3, d4, d5, (double) matrix.get(7));
            transform.concatenate(affineTransform);
            context.pushCanvas(canvas);
            i3--;
            PdfStream pdfStream2 = stream;
            canvases = canvases2;
            canvasesSize = canvasesSize;
            i2 = 1;
        }
        SvgDrawContext svgDrawContext = context;
        int i4 = i3;
        List<PdfCanvas> list = canvases;
        int i5 = canvasesSize;
        try {
            AffineTransform transform2 = transform.createInverse();
            Point[] points = context.getRootViewPort().toPointsArray();
            transform2.transform(points, 0, points, 0, points.length);
            stream.put(PdfName.BBox, new PdfArray(Rectangle.calculateBBox(Arrays.asList(points))));
        } catch (NoninvertibleTransformException e) {
            NoninvertibleTransformException noninvertibleTransformException = e;
            stream.put(PdfName.BBox, new PdfArray(new Rectangle(0.0f, 0.0f, 0.0f, 0.0f)));
            LoggerFactory.getLogger((Class<?>) AbstractBranchSvgNodeRenderer.class).warn(SvgLogMessageConstant.UNABLE_TO_GET_INVERSE_MATRIX_DUE_TO_ZERO_DETERMINANT);
        }
    }
}
