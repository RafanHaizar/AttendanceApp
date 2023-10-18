package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.DrawUtils;

public class EllipseSvgNodeRenderer extends AbstractSvgNodeRenderer {

    /* renamed from: cx */
    float f1650cx;

    /* renamed from: cy */
    float f1651cy;

    /* renamed from: rx */
    float f1652rx;

    /* renamed from: ry */
    float f1653ry;

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        PdfCanvas cv = context.getCurrentCanvas();
        cv.writeLiteral("% ellipse\n");
        if (setParameters()) {
            double d = (double) this.f1650cx;
            double d2 = (double) this.f1652rx;
            Double.isNaN(d);
            Double.isNaN(d2);
            cv.moveTo(d + d2, (double) this.f1651cy);
            float f = this.f1650cx;
            double d3 = (double) f;
            float f2 = this.f1652rx;
            double d4 = (double) f2;
            Double.isNaN(d3);
            Double.isNaN(d4);
            double d5 = d3 - d4;
            float f3 = this.f1651cy;
            double d6 = (double) f3;
            float f4 = this.f1653ry;
            double d7 = (double) f4;
            Double.isNaN(d6);
            Double.isNaN(d7);
            double d8 = d6 - d7;
            double d9 = (double) f;
            double d10 = (double) f2;
            Double.isNaN(d9);
            Double.isNaN(d10);
            double d11 = d9 + d10;
            double d12 = (double) f3;
            double d13 = (double) f4;
            Double.isNaN(d12);
            Double.isNaN(d13);
            DrawUtils.arc(d5, d8, d11, d13 + d12, 0.0d, 360.0d, cv);
        }
    }

    /* access modifiers changed from: protected */
    public Rectangle getObjectBoundingBox(SvgDrawContext context) {
        if (!setParameters()) {
            return super.getObjectBoundingBox(context);
        }
        float f = this.f1650cx;
        float f2 = this.f1652rx;
        float f3 = this.f1651cy;
        float f4 = this.f1653ry;
        return new Rectangle(f - f2, f3 - f4, f2 + f2, f4 + f4);
    }

    /* access modifiers changed from: protected */
    public boolean setParameters() {
        this.f1650cx = 0.0f;
        this.f1651cy = 0.0f;
        if (getAttribute(SvgConstants.Attributes.f1632CX) != null) {
            this.f1650cx = CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1632CX));
        }
        if (getAttribute(SvgConstants.Attributes.f1633CY) != null) {
            this.f1651cy = CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1633CY));
        }
        if (getAttribute(SvgConstants.Attributes.f1639RX) == null || CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1639RX)) <= 0.0f) {
            return false;
        }
        this.f1652rx = CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1639RX));
        if (getAttribute(SvgConstants.Attributes.f1640RY) == null || CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1640RY)) <= 0.0f) {
            return false;
        }
        this.f1653ry = CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1640RY));
        return true;
    }

    public ISvgNodeRenderer createDeepCopy() {
        EllipseSvgNodeRenderer copy = new EllipseSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }
}
