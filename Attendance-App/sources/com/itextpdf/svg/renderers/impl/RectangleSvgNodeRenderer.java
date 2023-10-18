package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import java.util.HashMap;
import java.util.List;

public class RectangleSvgNodeRenderer extends AbstractSvgNodeRenderer {
    private float height;

    /* renamed from: rx */
    private float f1658rx = 0.0f;
    private boolean rxPresent = false;

    /* renamed from: ry */
    private float f1659ry = 0.0f;
    private boolean ryPresent = false;
    private float width;

    /* renamed from: x */
    private float f1660x = 0.0f;

    /* renamed from: y */
    private float f1661y = 0.0f;

    public RectangleSvgNodeRenderer() {
        this.attributesAndStyles = new HashMap();
    }

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        PdfCanvas cv = context.getCurrentCanvas();
        cv.writeLiteral("% rect\n");
        setParameters();
        boolean z = this.rxPresent;
        boolean singleValuePresent = (z && !this.ryPresent) || (!z && this.ryPresent);
        if (!z && !this.ryPresent) {
            cv.rectangle((double) this.f1660x, (double) this.f1661y, (double) this.width, (double) this.height);
        } else if (singleValuePresent) {
            cv.writeLiteral("% circle rounded rect\n");
            cv.roundRectangle((double) this.f1660x, (double) this.f1661y, (double) this.width, (double) this.height, (double) findCircularRadius(this.f1658rx, this.f1659ry, this.width, this.height));
        } else {
            cv.writeLiteral("% ellipse rounded rect\n");
            cv.moveTo((double) (this.f1660x + this.f1658rx), (double) this.f1661y);
            cv.lineTo((double) ((this.f1660x + this.width) - this.f1658rx), (double) this.f1661y);
            float f = this.f1660x;
            float f2 = this.width;
            float f3 = (f + f2) - (this.f1658rx * 2.0f);
            float f4 = this.f1661y;
            arc(f3, f4, f + f2, f4 + (this.f1659ry * 2.0f), -90.0f, 90.0f, cv);
            cv.lineTo((double) (this.f1660x + this.width), (double) ((this.f1661y + this.height) - this.f1659ry));
            float f5 = this.f1660x;
            float f6 = this.width;
            float f7 = this.f1661y;
            float f8 = this.height;
            arc(f5 + f6, (f7 + f8) - (this.f1659ry * 2.0f), (f5 + f6) - (this.f1658rx * 2.0f), f8 + f7, 0.0f, 90.0f, cv);
            cv.lineTo((double) (this.f1660x + this.f1658rx), (double) (this.f1661y + this.height));
            float f9 = this.f1660x;
            float f10 = f9 + (this.f1658rx * 2.0f);
            float f11 = this.f1661y;
            float f12 = this.height;
            PdfCanvas pdfCanvas = cv;
            arc(f10, f11 + f12, f9, (f11 + f12) - (this.f1659ry * 2.0f), 90.0f, 90.0f, pdfCanvas);
            cv.lineTo((double) this.f1660x, (double) (this.f1661y + this.f1659ry));
            float f13 = this.f1660x;
            float f14 = this.f1661y;
            arc(f13, f14 + (this.f1659ry * 2.0f), f13 + (this.f1658rx * 2.0f), f14, 180.0f, 90.0f, pdfCanvas);
            cv.closePath();
        }
    }

    /* access modifiers changed from: protected */
    public Rectangle getObjectBoundingBox(SvgDrawContext context) {
        setParameters();
        return new Rectangle(this.f1660x, this.f1661y, this.width, this.height);
    }

    private void setParameters() {
        if (getAttribute(SvgConstants.Attributes.f1641X) != null) {
            this.f1660x = CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1641X));
        }
        if (getAttribute(SvgConstants.Attributes.f1644Y) != null) {
            this.f1661y = CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1644Y));
        }
        this.width = CssUtils.parseAbsoluteLength(getAttribute("width"));
        this.height = CssUtils.parseAbsoluteLength(getAttribute("height"));
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.f1639RX)) {
            this.f1658rx = checkRadius(CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1639RX)), this.width);
            this.rxPresent = true;
        }
        if (this.attributesAndStyles.containsKey(SvgConstants.Attributes.f1640RY)) {
            this.f1659ry = checkRadius(CssUtils.parseAbsoluteLength(getAttribute(SvgConstants.Attributes.f1640RY)), this.height);
            this.ryPresent = true;
        }
    }

    private void arc(float x1, float y1, float x2, float y2, float startAng, float extent, PdfCanvas cv) {
        List<double[]> ar = PdfCanvas.bezierArc((double) x1, (double) y1, (double) x2, (double) y2, (double) startAng, (double) extent);
        if (!ar.isEmpty()) {
            for (int k = 0; k < ar.size(); k++) {
                double[] pt = ar.get(k);
                cv.curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public float checkRadius(float radius, float distance) {
        if (radius <= 0.0f) {
            return 0.0f;
        }
        if (radius > distance / 2.0f) {
            return distance / 2.0f;
        }
        return radius;
    }

    /* access modifiers changed from: package-private */
    public float findCircularRadius(float rx, float ry, float width2, float height2) {
        return Math.min(Math.min(width2, height2) / 2.0f, Math.max(rx, ry));
    }

    public ISvgNodeRenderer createDeepCopy() {
        RectangleSvgNodeRenderer copy = new RectangleSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }
}
