package com.itextpdf.styledxmlparser.css.media;

import com.itextpdf.styledxmlparser.css.util.CssUtils;

public class MediaExpression {
    private static final float DEFAULT_FONT_SIZE = 12.0f;
    private String feature;
    private boolean maxPrefix;
    private boolean minPrefix;
    private String value;

    MediaExpression(String feature2, String value2) {
        this.feature = feature2.trim().toLowerCase();
        if (value2 != null) {
            this.value = value2.trim().toLowerCase();
        }
        boolean startsWith = feature2.startsWith("min-");
        this.minPrefix = startsWith;
        if (startsWith) {
            this.feature = feature2.substring("min-".length());
        }
        boolean startsWith2 = feature2.startsWith("max-");
        this.maxPrefix = startsWith2;
        if (startsWith2) {
            this.feature = feature2.substring("max-".length());
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean matches(com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription r7) {
        /*
            r6 = this;
            java.lang.String r0 = r6.feature
            int r1 = r0.hashCode()
            r2 = 1
            r3 = 0
            switch(r1) {
                case -1905977571: goto L_0x006d;
                case -1600030548: goto L_0x0061;
                case -1546463658: goto L_0x0057;
                case -1439500848: goto L_0x004c;
                case -1221029593: goto L_0x0042;
                case -115993112: goto L_0x0038;
                case 3181382: goto L_0x002e;
                case 3524221: goto L_0x0023;
                case 94842723: goto L_0x0019;
                case 113126854: goto L_0x000d;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0078
        L_0x000d:
            java.lang.String r1 = "width"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 8
            goto L_0x0079
        L_0x0019:
            java.lang.String r1 = "color"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 0
            goto L_0x0079
        L_0x0023:
            java.lang.String r1 = "scan"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 4
            goto L_0x0079
        L_0x002e:
            java.lang.String r1 = "grid"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 3
            goto L_0x0079
        L_0x0038:
            java.lang.String r1 = "color-index"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 1
            goto L_0x0079
        L_0x0042:
            java.lang.String r1 = "height"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 7
            goto L_0x0079
        L_0x004c:
            java.lang.String r1 = "orientation"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 5
            goto L_0x0079
        L_0x0057:
            java.lang.String r1 = "aspect-ratio"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 2
            goto L_0x0079
        L_0x0061:
            java.lang.String r1 = "resolution"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 9
            goto L_0x0079
        L_0x006d:
            java.lang.String r1 = "monochrome"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 6
            goto L_0x0079
        L_0x0078:
            r0 = -1
        L_0x0079:
            r1 = 0
            switch(r0) {
                case 0: goto L_0x0223;
                case 1: goto L_0x01e1;
                case 2: goto L_0x0180;
                case 3: goto L_0x0163;
                case 4: goto L_0x0158;
                case 5: goto L_0x014d;
                case 6: goto L_0x010b;
                case 7: goto L_0x00dc;
                case 8: goto L_0x00ad;
                case 9: goto L_0x007e;
                default: goto L_0x007d;
            }
        L_0x007d:
            return r3
        L_0x007e:
            java.lang.String r0 = r6.value
            float r0 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseResolution(r0)
            boolean r4 = r6.minPrefix
            if (r4 == 0) goto L_0x0093
            float r1 = r7.getResolution()
            int r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r1 < 0) goto L_0x0091
            goto L_0x0092
        L_0x0091:
            r2 = 0
        L_0x0092:
            return r2
        L_0x0093:
            boolean r4 = r6.maxPrefix
            if (r4 == 0) goto L_0x00a2
            float r1 = r7.getResolution()
            int r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r1 > 0) goto L_0x00a0
            goto L_0x00a1
        L_0x00a0:
            r2 = 0
        L_0x00a1:
            return r2
        L_0x00a2:
            float r4 = r7.getResolution()
            int r1 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x00ab
            goto L_0x00ac
        L_0x00ab:
            r2 = 0
        L_0x00ac:
            return r2
        L_0x00ad:
            java.lang.String r0 = r6.value
            float r0 = parseAbsoluteLength(r0)
            boolean r4 = r6.minPrefix
            if (r4 == 0) goto L_0x00c2
            float r1 = r7.getWidth()
            int r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r1 < 0) goto L_0x00c0
            goto L_0x00c1
        L_0x00c0:
            r2 = 0
        L_0x00c1:
            return r2
        L_0x00c2:
            boolean r4 = r6.maxPrefix
            if (r4 == 0) goto L_0x00d1
            float r1 = r7.getWidth()
            int r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r1 > 0) goto L_0x00cf
            goto L_0x00d0
        L_0x00cf:
            r2 = 0
        L_0x00d0:
            return r2
        L_0x00d1:
            float r4 = r7.getWidth()
            int r1 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x00da
            goto L_0x00db
        L_0x00da:
            r2 = 0
        L_0x00db:
            return r2
        L_0x00dc:
            java.lang.String r0 = r6.value
            float r0 = parseAbsoluteLength(r0)
            boolean r4 = r6.minPrefix
            if (r4 == 0) goto L_0x00f1
            float r1 = r7.getHeight()
            int r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r1 < 0) goto L_0x00ef
            goto L_0x00f0
        L_0x00ef:
            r2 = 0
        L_0x00f0:
            return r2
        L_0x00f1:
            boolean r4 = r6.maxPrefix
            if (r4 == 0) goto L_0x0100
            float r1 = r7.getHeight()
            int r1 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r1 > 0) goto L_0x00fe
            goto L_0x00ff
        L_0x00fe:
            r2 = 0
        L_0x00ff:
            return r2
        L_0x0100:
            float r4 = r7.getHeight()
            int r1 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0109
            goto L_0x010a
        L_0x0109:
            r2 = 0
        L_0x010a:
            return r2
        L_0x010b:
            java.lang.String r0 = r6.value
            java.lang.Integer r0 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseInteger(r0)
            boolean r1 = r6.minPrefix
            if (r1 == 0) goto L_0x0124
            if (r0 == 0) goto L_0x0122
            int r1 = r7.getMonochrome()
            int r4 = r0.intValue()
            if (r1 < r4) goto L_0x0122
            goto L_0x0123
        L_0x0122:
            r2 = 0
        L_0x0123:
            return r2
        L_0x0124:
            boolean r1 = r6.maxPrefix
            if (r1 == 0) goto L_0x0137
            if (r0 == 0) goto L_0x0135
            int r1 = r7.getMonochrome()
            int r4 = r0.intValue()
            if (r1 > r4) goto L_0x0135
            goto L_0x0136
        L_0x0135:
            r2 = 0
        L_0x0136:
            return r2
        L_0x0137:
            if (r0 != 0) goto L_0x0140
            int r1 = r7.getMonochrome()
            if (r1 <= 0) goto L_0x014b
            goto L_0x014a
        L_0x0140:
            int r1 = r0.intValue()
            int r4 = r7.getMonochrome()
            if (r1 != r4) goto L_0x014b
        L_0x014a:
            goto L_0x014c
        L_0x014b:
            r2 = 0
        L_0x014c:
            return r2
        L_0x014d:
            java.lang.String r0 = r6.value
            java.lang.String r1 = r7.getOrientation()
            boolean r0 = java.util.Objects.equals(r0, r1)
            return r0
        L_0x0158:
            java.lang.String r0 = r6.value
            java.lang.String r1 = r7.getScan()
            boolean r0 = java.util.Objects.equals(r0, r1)
            return r0
        L_0x0163:
            java.lang.String r0 = r6.value
            java.lang.Integer r0 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseInteger(r0)
            if (r0 == 0) goto L_0x0177
            int r1 = r0.intValue()
            if (r1 != 0) goto L_0x0177
            boolean r1 = r7.isGrid()
            if (r1 == 0) goto L_0x017d
        L_0x0177:
            boolean r1 = r7.isGrid()
            if (r1 == 0) goto L_0x017e
        L_0x017d:
            goto L_0x017f
        L_0x017e:
            r2 = 0
        L_0x017f:
            return r2
        L_0x0180:
            java.lang.String r0 = r6.value
            int[] r0 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseAspectRatio(r0)
            boolean r1 = r6.minPrefix
            if (r1 == 0) goto L_0x01a5
            if (r0 == 0) goto L_0x01a3
            r1 = r0[r3]
            float r1 = (float) r1
            float r4 = r7.getHeight()
            float r1 = r1 * r4
            r4 = r0[r2]
            float r4 = (float) r4
            float r5 = r7.getWidth()
            float r4 = r4 * r5
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 < 0) goto L_0x01a3
            goto L_0x01a4
        L_0x01a3:
            r2 = 0
        L_0x01a4:
            return r2
        L_0x01a5:
            boolean r1 = r6.maxPrefix
            if (r1 == 0) goto L_0x01c4
            if (r0 == 0) goto L_0x01c2
            r1 = r0[r3]
            float r1 = (float) r1
            float r4 = r7.getHeight()
            float r1 = r1 * r4
            r4 = r0[r2]
            float r4 = (float) r4
            float r5 = r7.getWidth()
            float r4 = r4 * r5
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 > 0) goto L_0x01c2
            goto L_0x01c3
        L_0x01c2:
            r2 = 0
        L_0x01c3:
            return r2
        L_0x01c4:
            if (r0 == 0) goto L_0x01df
            r1 = r0[r3]
            float r1 = (float) r1
            float r4 = r7.getHeight()
            float r1 = r1 * r4
            r4 = r0[r2]
            float r4 = (float) r4
            float r5 = r7.getWidth()
            float r4 = r4 * r5
            boolean r1 = com.itextpdf.styledxmlparser.css.util.CssUtils.compareFloats((float) r1, (float) r4)
            if (r1 == 0) goto L_0x01df
            goto L_0x01e0
        L_0x01df:
            r2 = 0
        L_0x01e0:
            return r2
        L_0x01e1:
            java.lang.String r0 = r6.value
            java.lang.Integer r0 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseInteger(r0)
            boolean r1 = r6.minPrefix
            if (r1 == 0) goto L_0x01fa
            if (r0 == 0) goto L_0x01f8
            int r1 = r7.getColorIndex()
            int r4 = r0.intValue()
            if (r1 < r4) goto L_0x01f8
            goto L_0x01f9
        L_0x01f8:
            r2 = 0
        L_0x01f9:
            return r2
        L_0x01fa:
            boolean r1 = r6.maxPrefix
            if (r1 == 0) goto L_0x020d
            if (r0 == 0) goto L_0x020b
            int r1 = r7.getColorIndex()
            int r4 = r0.intValue()
            if (r1 > r4) goto L_0x020b
            goto L_0x020c
        L_0x020b:
            r2 = 0
        L_0x020c:
            return r2
        L_0x020d:
            if (r0 != 0) goto L_0x0216
            int r1 = r7.getColorIndex()
            if (r1 == 0) goto L_0x0221
            goto L_0x0220
        L_0x0216:
            int r1 = r0.intValue()
            int r4 = r7.getColorIndex()
            if (r1 != r4) goto L_0x0221
        L_0x0220:
            goto L_0x0222
        L_0x0221:
            r2 = 0
        L_0x0222:
            return r2
        L_0x0223:
            java.lang.String r0 = r6.value
            java.lang.Integer r0 = com.itextpdf.styledxmlparser.css.util.CssUtils.parseInteger(r0)
            boolean r1 = r6.minPrefix
            if (r1 == 0) goto L_0x023c
            if (r0 == 0) goto L_0x023a
            int r1 = r7.getBitsPerComponent()
            int r4 = r0.intValue()
            if (r1 < r4) goto L_0x023a
            goto L_0x023b
        L_0x023a:
            r2 = 0
        L_0x023b:
            return r2
        L_0x023c:
            boolean r1 = r6.maxPrefix
            if (r1 == 0) goto L_0x024f
            if (r0 == 0) goto L_0x024d
            int r1 = r7.getBitsPerComponent()
            int r4 = r0.intValue()
            if (r1 > r4) goto L_0x024d
            goto L_0x024e
        L_0x024d:
            r2 = 0
        L_0x024e:
            return r2
        L_0x024f:
            if (r0 != 0) goto L_0x0258
            int r1 = r7.getBitsPerComponent()
            if (r1 == 0) goto L_0x0263
            goto L_0x0262
        L_0x0258:
            int r1 = r0.intValue()
            int r4 = r7.getBitsPerComponent()
            if (r1 != r4) goto L_0x0263
        L_0x0262:
            goto L_0x0264
        L_0x0263:
            r2 = 0
        L_0x0264:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.css.media.MediaExpression.matches(com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription):boolean");
    }

    private static float parseAbsoluteLength(String value2) {
        if (CssUtils.isRelativeValue(value2)) {
            return CssUtils.parseRelativeValue(value2, DEFAULT_FONT_SIZE);
        }
        return CssUtils.parseAbsoluteLength(value2);
    }
}
