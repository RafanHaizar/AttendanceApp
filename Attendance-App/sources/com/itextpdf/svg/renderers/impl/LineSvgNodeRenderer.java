package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.IMarkerCapable;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.SvgCoordinateUtils;
import java.util.Map;

public class LineSvgNodeRenderer extends AbstractSvgNodeRenderer implements IMarkerCapable {

    /* renamed from: x1 */
    private float f1654x1 = 0.0f;

    /* renamed from: x2 */
    private float f1655x2 = 0.0f;

    /* renamed from: y1 */
    private float f1656y1 = 0.0f;

    /* renamed from: y2 */
    private float f1657y2 = 0.0f;

    public void doDraw(SvgDrawContext context) {
        PdfCanvas canvas = context.getCurrentCanvas();
        canvas.writeLiteral("% line\n");
        if (setParameterss()) {
            canvas.moveTo((double) this.f1654x1, (double) this.f1656y1).lineTo((double) this.f1655x2, (double) this.f1657y2);
        }
    }

    /* access modifiers changed from: protected */
    public Rectangle getObjectBoundingBox(SvgDrawContext context) {
        if (setParameterss()) {
            return new Rectangle(Math.min(this.f1654x1, this.f1655x2), Math.min(this.f1656y1, this.f1657y2), Math.abs(this.f1654x1 - this.f1655x2), Math.abs(this.f1656y1 - this.f1657y2));
        }
        return super.getObjectBoundingBox(context);
    }

    /* access modifiers changed from: protected */
    public boolean canElementFill() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public float getAttribute(Map<String, String> attributes, String key) {
        String value = attributes.get(key);
        if (value == null || value.isEmpty()) {
            return 0.0f;
        }
        return CssUtils.parseAbsoluteLength(attributes.get(key));
    }

    public ISvgNodeRenderer createDeepCopy() {
        LineSvgNodeRenderer copy = new LineSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drawMarker(com.itextpdf.svg.renderers.SvgDrawContext r5, com.itextpdf.svg.MarkerVertexType r6) {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            com.itextpdf.svg.MarkerVertexType r2 = com.itextpdf.svg.MarkerVertexType.MARKER_START
            boolean r2 = r2.equals(r6)
            if (r2 == 0) goto L_0x0023
            java.util.Map r2 = r4.attributesAndStyles
            java.lang.String r3 = "x1"
            java.lang.Object r2 = r2.get(r3)
            r0 = r2
            java.lang.String r0 = (java.lang.String) r0
            java.util.Map r2 = r4.attributesAndStyles
            java.lang.String r3 = "y1"
            java.lang.Object r2 = r2.get(r3)
            r1 = r2
            java.lang.String r1 = (java.lang.String) r1
            goto L_0x0043
        L_0x0023:
            com.itextpdf.svg.MarkerVertexType r2 = com.itextpdf.svg.MarkerVertexType.MARKER_END
            boolean r2 = r2.equals(r6)
            if (r2 == 0) goto L_0x0043
            java.util.Map r2 = r4.attributesAndStyles
            java.lang.String r3 = "x2"
            java.lang.Object r2 = r2.get(r3)
            r0 = r2
            java.lang.String r0 = (java.lang.String) r0
            java.util.Map r2 = r4.attributesAndStyles
            java.lang.String r3 = "y2"
            java.lang.Object r2 = r2.get(r3)
            r1 = r2
            java.lang.String r1 = (java.lang.String) r1
        L_0x0043:
            if (r0 == 0) goto L_0x004a
            if (r1 == 0) goto L_0x004a
            com.itextpdf.svg.renderers.impl.MarkerSvgNodeRenderer.drawMarker(r5, r0, r1, r6, r4)
        L_0x004a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.renderers.impl.LineSvgNodeRenderer.drawMarker(com.itextpdf.svg.renderers.SvgDrawContext, com.itextpdf.svg.MarkerVertexType):void");
    }

    public double getAutoOrientAngle(MarkerSvgNodeRenderer marker, boolean reverse) {
        Vector v = new Vector(getAttribute(this.attributesAndStyles, SvgConstants.Attributes.f1643X2) - getAttribute(this.attributesAndStyles, SvgConstants.Attributes.f1642X1), getAttribute(this.attributesAndStyles, SvgConstants.Attributes.f1646Y2) - getAttribute(this.attributesAndStyles, SvgConstants.Attributes.f1645Y1), 0.0f);
        double rotAngle = SvgCoordinateUtils.calculateAngleBetweenTwoVectors(new Vector(1.0f, 0.0f, 0.0f), v);
        return (v.get(1) < 0.0f || reverse) ? -1.0d * rotAngle : rotAngle;
    }

    private boolean setParameterss() {
        if (this.attributesAndStyles.size() <= 0) {
            return false;
        }
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.f1642X1)) {
            this.f1654x1 = getAttribute(this.attributesAndStyles, SvgConstants.Attributes.f1642X1);
        }
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.f1645Y1)) {
            this.f1656y1 = getAttribute(this.attributesAndStyles, SvgConstants.Attributes.f1645Y1);
        }
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.f1643X2)) {
            this.f1655x2 = getAttribute(this.attributesAndStyles, SvgConstants.Attributes.f1643X2);
        }
        if (!this.attributesAndStyles.containsKey(SvgConstants.Attributes.f1646Y2)) {
            return true;
        }
        this.f1657y2 = getAttribute(this.attributesAndStyles, SvgConstants.Attributes.f1646Y2);
        return true;
    }
}
