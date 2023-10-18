package com.itextpdf.svg.renderers.impl;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.TransformUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

public abstract class AbstractGradientSvgNodeRenderer extends NoDrawOperationSvgNodeRenderer {
    public abstract Color createColor(SvgDrawContext svgDrawContext, Rectangle rectangle, float f, float f2);

    /* access modifiers changed from: protected */
    public void doDraw(SvgDrawContext context) {
        throw new UnsupportedOperationException(SvgLogMessageConstant.DRAW_NO_DRAW);
    }

    /* access modifiers changed from: protected */
    public boolean isObjectBoundingBoxUnits() {
        String gradientUnits = getAttribute(SvgConstants.Attributes.GRADIENT_UNITS);
        if (SvgConstants.Values.GRADIENT_UNITS_USER_SPACE_ON_USE.equals(gradientUnits)) {
            return false;
        }
        if (gradientUnits != null && !SvgConstants.Values.GRADIENT_UNITS_OBJECT_BOUNDING_BOX.equals(gradientUnits)) {
            LoggerFactory.getLogger(getClass()).warn(MessageFormatUtil.format(SvgLogMessageConstant.GRADIENT_INVALID_GRADIENT_UNITS_LOG, gradientUnits));
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public AffineTransform getGradientTransform() {
        String gradientTransform = getAttribute(SvgConstants.Attributes.GRADIENT_TRANSFORM);
        if (gradientTransform == null || gradientTransform.isEmpty()) {
            return null;
        }
        return TransformUtils.parseTransform(gradientTransform);
    }

    /* access modifiers changed from: protected */
    public List<StopSvgNodeRenderer> getChildStopRenderers() {
        List<StopSvgNodeRenderer> stopRenderers = new ArrayList<>();
        for (ISvgNodeRenderer child : getChildren()) {
            if (child instanceof StopSvgNodeRenderer) {
                stopRenderers.add((StopSvgNodeRenderer) child);
            }
        }
        return stopRenderers;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.kernel.colors.gradients.GradientSpreadMethod parseSpreadMethod() {
        /*
            r4 = this;
            java.lang.String r0 = com.itextpdf.svg.SvgConstants.Attributes.SPREAD_METHOD
            java.lang.String r0 = r4.getAttribute(r0)
            if (r0 != 0) goto L_0x000b
            com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r1 = com.itextpdf.kernel.colors.gradients.GradientSpreadMethod.PAD
            return r1
        L_0x000b:
            int r1 = r0.hashCode()
            r2 = 0
            r3 = 1
            switch(r1) {
                case -934531685: goto L_0x002b;
                case 110739: goto L_0x0020;
                case 1085265597: goto L_0x0015;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x0036
        L_0x0015:
            java.lang.String r1 = "reflect"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0014
            r1 = 1
            goto L_0x0037
        L_0x0020:
            java.lang.String r1 = "pad"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0014
            r1 = 0
            goto L_0x0037
        L_0x002b:
            java.lang.String r1 = "repeat"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0014
            r1 = 2
            goto L_0x0037
        L_0x0036:
            r1 = -1
        L_0x0037:
            switch(r1) {
                case 0: goto L_0x0058;
                case 1: goto L_0x0055;
                case 2: goto L_0x0052;
                default: goto L_0x003a;
            }
        L_0x003a:
            java.lang.Class r1 = r4.getClass()
            org.slf4j.Logger r1 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r1)
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r2] = r0
            java.lang.String r2 = "Could not recognize gradient spread method value {0}"
            java.lang.String r2 = com.itextpdf.p026io.util.MessageFormatUtil.format(r2, r3)
            r1.warn(r2)
            com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r1 = com.itextpdf.kernel.colors.gradients.GradientSpreadMethod.PAD
            return r1
        L_0x0052:
            com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r1 = com.itextpdf.kernel.colors.gradients.GradientSpreadMethod.REPEAT
            return r1
        L_0x0055:
            com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r1 = com.itextpdf.kernel.colors.gradients.GradientSpreadMethod.REFLECT
            return r1
        L_0x0058:
            com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r1 = com.itextpdf.kernel.colors.gradients.GradientSpreadMethod.PAD
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.renderers.impl.AbstractGradientSvgNodeRenderer.parseSpreadMethod():com.itextpdf.kernel.colors.gradients.GradientSpreadMethod");
    }
}
