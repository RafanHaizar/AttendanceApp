package com.itextpdf.svg.renderers.impl;

import com.itextpdf.svg.renderers.ISvgNodeRenderer;

public class ImageSvgNodeRenderer extends AbstractSvgNodeRenderer {
    public ISvgNodeRenderer createDeepCopy() {
        ImageSvgNodeRenderer copy = new ImageSvgNodeRenderer();
        deepCopyAttributesAndStyles(copy);
        return copy;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v18, resolved type: java.lang.String} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doDraw(com.itextpdf.svg.renderers.SvgDrawContext r20) {
        /*
            r19 = this;
            r0 = r19
            com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver r1 = r20.getResourceResolver()
            if (r1 == 0) goto L_0x01cf
            java.util.Map r2 = r0.attributesAndStyles
            if (r2 != 0) goto L_0x000e
            goto L_0x01cf
        L_0x000e:
            java.util.Map r2 = r0.attributesAndStyles
            java.lang.String r3 = "xlink:href"
            java.lang.Object r2 = r2.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            com.itextpdf.kernel.pdf.xobject.PdfXObject r11 = r1.retrieveImageExtended(r2)
            if (r11 != 0) goto L_0x0020
            return
        L_0x0020:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r12 = r20.getCurrentCanvas()
            r3 = 0
            java.util.Map r4 = r0.attributesAndStyles
            java.lang.String r5 = "x"
            boolean r4 = r4.containsKey(r5)
            if (r4 == 0) goto L_0x003c
            java.util.Map r4 = r0.attributesAndStyles
            java.lang.Object r4 = r4.get(r5)
            java.lang.String r4 = (java.lang.String) r4
            float r3 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseAbsoluteLength(r4)
        L_0x003c:
            r4 = 0
            java.util.Map r5 = r0.attributesAndStyles
            java.lang.String r6 = "y"
            boolean r5 = r5.containsKey(r6)
            if (r5 == 0) goto L_0x0054
            java.util.Map r5 = r0.attributesAndStyles
            java.lang.Object r5 = r5.get(r6)
            java.lang.String r5 = (java.lang.String) r5
            float r4 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseAbsoluteLength(r5)
        L_0x0054:
            r5 = 0
            java.util.Map r6 = r0.attributesAndStyles
            java.lang.String r7 = "width"
            boolean r6 = r6.containsKey(r7)
            if (r6 == 0) goto L_0x006c
            java.util.Map r6 = r0.attributesAndStyles
            java.lang.Object r6 = r6.get(r7)
            java.lang.String r6 = (java.lang.String) r6
            float r5 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseAbsoluteLength(r6)
        L_0x006c:
            r6 = 0
            java.util.Map r7 = r0.attributesAndStyles
            java.lang.String r8 = "height"
            boolean r7 = r7.containsKey(r8)
            if (r7 == 0) goto L_0x0083
            java.util.Map r7 = r0.attributesAndStyles
            java.lang.Object r7 = r7.get(r8)
            java.lang.String r7 = (java.lang.String) r7
            float r6 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseAbsoluteLength(r7)
        L_0x0083:
            java.lang.String r7 = ""
            java.util.Map r8 = r0.attributesAndStyles
            java.lang.String r9 = com.itextpdf.svg.SvgConstants.Attributes.PRESERVE_ASPECT_RATIO
            boolean r8 = r8.containsKey(r9)
            if (r8 == 0) goto L_0x009a
            java.util.Map r8 = r0.attributesAndStyles
            java.lang.String r9 = com.itextpdf.svg.SvgConstants.Attributes.PRESERVE_ASPECT_RATIO
            java.lang.Object r8 = r8.get(r9)
            r7 = r8
            java.lang.String r7 = (java.lang.String) r7
        L_0x009a:
            java.lang.String r13 = r7.toLowerCase()
            java.lang.String r7 = "none"
            boolean r7 = r7.equals(r13)
            if (r7 != 0) goto L_0x01b8
            r7 = 0
            int r8 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r8 == 0) goto L_0x01b8
            int r7 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x01b8
            float r7 = r11.getWidth()
            float r7 = r7 / r5
            float r8 = r11.getHeight()
            float r8 = r8 / r6
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 <= 0) goto L_0x00cb
            r7 = r5
            float r8 = r11.getHeight()
            float r9 = r11.getWidth()
            float r8 = r8 / r9
            float r8 = r8 * r5
            goto L_0x00d7
        L_0x00cb:
            float r7 = r11.getWidth()
            float r8 = r11.getHeight()
            float r7 = r7 / r8
            float r7 = r7 * r6
            r8 = r6
        L_0x00d7:
            java.lang.String r9 = r13.toLowerCase()
            int r10 = r9.hashCode()
            switch(r10) {
                case -470941129: goto L_0x013c;
                case -470940901: goto L_0x0131;
                case -470940891: goto L_0x0126;
                case -260378341: goto L_0x011b;
                case -260378113: goto L_0x010f;
                case -260378103: goto L_0x0104;
                case -251143131: goto L_0x00f9;
                case -251142903: goto L_0x00ee;
                case -251142893: goto L_0x00e3;
                default: goto L_0x00e2;
            }
        L_0x00e2:
            goto L_0x0147
        L_0x00e3:
            java.lang.String r10 = "xminymin"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e2
            r9 = 0
            goto L_0x0148
        L_0x00ee:
            java.lang.String r10 = "xminymid"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e2
            r9 = 1
            goto L_0x0148
        L_0x00f9:
            java.lang.String r10 = "xminymax"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e2
            r9 = 2
            goto L_0x0148
        L_0x0104:
            java.lang.String r10 = "xmidymin"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e2
            r9 = 3
            goto L_0x0148
        L_0x010f:
            java.lang.String r10 = "xmidymid"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e2
            r9 = 8
            goto L_0x0148
        L_0x011b:
            java.lang.String r10 = "xmidymax"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e2
            r9 = 4
            goto L_0x0148
        L_0x0126:
            java.lang.String r10 = "xmaxymin"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e2
            r9 = 5
            goto L_0x0148
        L_0x0131:
            java.lang.String r10 = "xmaxymid"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e2
            r9 = 6
            goto L_0x0148
        L_0x013c:
            java.lang.String r10 = "xmaxymax"
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e2
            r9 = 7
            goto L_0x0148
        L_0x0147:
            r9 = -1
        L_0x0148:
            r10 = 1073741824(0x40000000, float:2.0)
            switch(r9) {
                case 0: goto L_0x01af;
                case 1: goto L_0x01a6;
                case 2: goto L_0x019e;
                case 3: goto L_0x0195;
                case 4: goto L_0x0185;
                case 5: goto L_0x017d;
                case 6: goto L_0x016d;
                case 7: goto L_0x015e;
                default: goto L_0x014d;
            }
        L_0x014d:
            float r9 = r7 - r5
            float r9 = java.lang.Math.abs(r9)
            float r9 = r9 / r10
            float r3 = r3 + r9
            float r9 = r8 - r6
            float r9 = java.lang.Math.abs(r9)
            float r9 = r9 / r10
            float r4 = r4 + r9
            goto L_0x01b0
        L_0x015e:
            float r9 = r7 - r5
            float r9 = java.lang.Math.abs(r9)
            float r3 = r3 + r9
            float r9 = r8 - r6
            float r9 = java.lang.Math.abs(r9)
            float r4 = r4 + r9
            goto L_0x01b0
        L_0x016d:
            float r9 = r7 - r5
            float r9 = java.lang.Math.abs(r9)
            float r3 = r3 + r9
            float r9 = r8 - r6
            float r9 = java.lang.Math.abs(r9)
            float r9 = r9 / r10
            float r4 = r4 + r9
            goto L_0x01b0
        L_0x017d:
            float r9 = r7 - r5
            float r9 = java.lang.Math.abs(r9)
            float r3 = r3 + r9
            goto L_0x01b0
        L_0x0185:
            float r9 = r7 - r5
            float r9 = java.lang.Math.abs(r9)
            float r9 = r9 / r10
            float r3 = r3 + r9
            float r9 = r8 - r6
            float r9 = java.lang.Math.abs(r9)
            float r4 = r4 + r9
            goto L_0x01b0
        L_0x0195:
            float r9 = r7 - r5
            float r9 = java.lang.Math.abs(r9)
            float r9 = r9 / r10
            float r3 = r3 + r9
            goto L_0x01b0
        L_0x019e:
            float r9 = r8 - r6
            float r9 = java.lang.Math.abs(r9)
            float r4 = r4 + r9
            goto L_0x01b0
        L_0x01a6:
            float r9 = r8 - r6
            float r9 = java.lang.Math.abs(r9)
            float r9 = r9 / r10
            float r4 = r4 + r9
            goto L_0x01b0
        L_0x01af:
        L_0x01b0:
            r5 = r7
            r6 = r8
            r14 = r3
            r15 = r4
            r16 = r5
            r10 = r6
            goto L_0x01bd
        L_0x01b8:
            r14 = r3
            r15 = r4
            r16 = r5
            r10 = r6
        L_0x01bd:
            float r17 = r15 + r10
            r6 = 0
            r7 = 0
            float r8 = -r10
            r3 = r12
            r4 = r11
            r5 = r16
            r9 = r14
            r18 = r10
            r10 = r17
            r3.addXObject(r4, r5, r6, r7, r8, r9, r10)
            return
        L_0x01cf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.renderers.impl.ImageSvgNodeRenderer.doDraw(com.itextpdf.svg.renderers.SvgDrawContext):void");
    }
}
