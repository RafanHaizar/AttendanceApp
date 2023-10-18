package com.itextpdf.svg.renderers.impl;

import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;

public class UseSvgNodeRenderer extends AbstractSvgNodeRenderer {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.lang.String} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doDraw(com.itextpdf.svg.renderers.SvgDrawContext r15) {
        /*
            r14 = this;
            java.util.Map r0 = r14.attributesAndStyles
            if (r0 == 0) goto L_0x00c6
            java.util.Map r0 = r14.attributesAndStyles
            java.lang.String r1 = "xlink:href"
            java.lang.Object r0 = r0.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 != 0) goto L_0x001c
            java.util.Map r1 = r14.attributesAndStyles
            java.lang.String r2 = "href"
            java.lang.Object r1 = r1.get(r2)
            r0 = r1
            java.lang.String r0 = (java.lang.String) r0
        L_0x001c:
            if (r0 == 0) goto L_0x00c6
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x00c6
            boolean r1 = r14.isValidHref(r0)
            if (r1 == 0) goto L_0x00c6
            java.lang.String r1 = com.itextpdf.svg.utils.SvgTextUtil.filterReferenceValue(r0)
            boolean r2 = r15.isIdUsedByUseTagBefore(r1)
            if (r2 != 0) goto L_0x00c6
            com.itextpdf.svg.renderers.ISvgNodeRenderer r2 = r15.getNamedObject(r1)
            r3 = 0
            if (r2 != 0) goto L_0x003d
            r4 = r3
            goto L_0x0041
        L_0x003d:
            com.itextpdf.svg.renderers.ISvgNodeRenderer r4 = r2.createDeepCopy()
        L_0x0041:
            com.itextpdf.svg.css.impl.SvgNodeRendererInheritanceResolver r5 = new com.itextpdf.svg.css.impl.SvgNodeRendererInheritanceResolver
            r5.<init>()
            r5.applyInheritanceToSubTree(r14, r4)
            if (r4 == 0) goto L_0x00c6
            boolean r6 = r4 instanceof com.itextpdf.svg.renderers.impl.AbstractSvgNodeRenderer
            if (r6 == 0) goto L_0x0057
            r6 = r4
            com.itextpdf.svg.renderers.impl.AbstractSvgNodeRenderer r6 = (com.itextpdf.svg.renderers.impl.AbstractSvgNodeRenderer) r6
            boolean r7 = r14.partOfClipPath
            r6.setPartOfClipPath(r7)
        L_0x0057:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r6 = r15.getCurrentCanvas()
            r7 = 0
            r8 = 0
            java.util.Map r9 = r14.attributesAndStyles
            java.lang.String r10 = "x"
            boolean r9 = r9.containsKey(r10)
            if (r9 == 0) goto L_0x0074
            java.util.Map r9 = r14.attributesAndStyles
            java.lang.Object r9 = r9.get(r10)
            java.lang.String r9 = (java.lang.String) r9
            float r7 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseAbsoluteLength(r9)
        L_0x0074:
            java.util.Map r9 = r14.attributesAndStyles
            java.lang.String r10 = "y"
            boolean r9 = r9.containsKey(r10)
            if (r9 == 0) goto L_0x008b
            java.util.Map r9 = r14.attributesAndStyles
            java.lang.Object r9 = r9.get(r10)
            java.lang.String r9 = (java.lang.String) r9
            float r8 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseAbsoluteLength(r9)
        L_0x008b:
            r9 = 0
            r10 = 0
            boolean r11 = com.itextpdf.styledxmlparser.css.util.CssUtils.compareFloats((float) r7, (float) r10)
            if (r11 == 0) goto L_0x0099
            boolean r10 = com.itextpdf.styledxmlparser.css.util.CssUtils.compareFloats((float) r8, (float) r10)
            if (r10 != 0) goto L_0x00b8
        L_0x0099:
            double r10 = (double) r7
            double r12 = (double) r8
            com.itextpdf.kernel.geom.AffineTransform r10 = com.itextpdf.kernel.geom.AffineTransform.getTranslateInstance(r10, r12)
            r6.concatMatrix((com.itextpdf.kernel.geom.AffineTransform) r10)
            boolean r11 = r14.partOfClipPath
            if (r11 == 0) goto L_0x00b8
            com.itextpdf.kernel.geom.AffineTransform r11 = r10.createInverse()     // Catch:{ NoninvertibleTransformException -> 0x00ac }
            r9 = r11
            goto L_0x00b8
        L_0x00ac:
            r11 = move-exception
            java.lang.Class<com.itextpdf.svg.renderers.impl.UseSvgNodeRenderer> r12 = com.itextpdf.svg.renderers.impl.UseSvgNodeRenderer.class
            org.slf4j.Logger r12 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r12)
            java.lang.String r13 = "Non-invertible transformation matrix was used in a clipping path context. Clipped elements may show undefined behavior."
            r12.warn((java.lang.String) r13, (java.lang.Throwable) r11)
        L_0x00b8:
            r4.setParent(r14)
            r4.draw(r15)
            r4.setParent(r3)
            if (r9 == 0) goto L_0x00c6
            r6.concatMatrix((com.itextpdf.kernel.geom.AffineTransform) r9)
        L_0x00c6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.renderers.impl.UseSvgNodeRenderer.doDraw(com.itextpdf.svg.renderers.SvgDrawContext):void");
    }

    /* access modifiers changed from: package-private */
    public void postDraw(SvgDrawContext context) {
    }

    private boolean isValidHref(String name) {
        return name.startsWith("#");
    }

    public ISvgNodeRenderer createDeepCopy() {
        UseSvgNodeRenderer copy = new UseSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }
}
