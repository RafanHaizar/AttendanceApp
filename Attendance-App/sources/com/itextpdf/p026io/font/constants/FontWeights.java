package com.itextpdf.p026io.font.constants;

/* renamed from: com.itextpdf.io.font.constants.FontWeights */
public final class FontWeights {
    public static final int BLACK = 900;
    public static final int BOLD = 700;
    public static final int EXTRA_BOLD = 800;
    public static final int EXTRA_LIGHT = 200;
    public static final int LIGHT = 300;
    public static final int MEDIUM = 500;
    public static final int NORMAL = 400;
    public static final int SEMI_BOLD = 600;
    public static final int THIN = 100;

    private FontWeights() {
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int fromType1FontWeight(java.lang.String r3) {
        /*
            r0 = 400(0x190, float:5.6E-43)
            java.lang.String r1 = r3.toLowerCase()
            int r2 = r1.hashCode()
            switch(r2) {
                case -1078030475: goto L_0x00df;
                case -1039745817: goto L_0x00d4;
                case -252885355: goto L_0x00c9;
                case 101145: goto L_0x00be;
                case 3029637: goto L_0x00b3;
                case 3029737: goto L_0x00a9;
                case 3559065: goto L_0x009e;
                case 93818879: goto L_0x0093;
                case 99152071: goto L_0x0088;
                case 102970646: goto L_0x007e;
                case 111384492: goto L_0x0071;
                case 750388719: goto L_0x0065;
                case 759540486: goto L_0x005a;
                case 851509730: goto L_0x004e;
                case 1086463900: goto L_0x0042;
                case 1223860979: goto L_0x0035;
                case 1453726769: goto L_0x0028;
                case 2115757011: goto L_0x001b;
                case 2124908778: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x00ea
        L_0x000f:
            java.lang.String r2 = "ultralight"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 0
            goto L_0x00eb
        L_0x001b:
            java.lang.String r2 = "ultrablack"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 16
            goto L_0x00eb
        L_0x0028:
            java.lang.String r2 = "ultrabold"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 12
            goto L_0x00eb
        L_0x0035:
            java.lang.String r2 = "semibold"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 9
            goto L_0x00eb
        L_0x0042:
            java.lang.String r2 = "regular"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 5
            goto L_0x00eb
        L_0x004e:
            java.lang.String r2 = "demibold"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 8
            goto L_0x00eb
        L_0x005a:
            java.lang.String r2 = "extralight"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 2
            goto L_0x00eb
        L_0x0065:
            java.lang.String r2 = "extrablack"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 18
            goto L_0x00eb
        L_0x0071:
            java.lang.String r2 = "ultra"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 15
            goto L_0x00eb
        L_0x007e:
            java.lang.String r2 = "light"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 3
            goto L_0x00eb
        L_0x0088:
            java.lang.String r2 = "heavy"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 13
            goto L_0x00eb
        L_0x0093:
            java.lang.String r2 = "black"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 14
            goto L_0x00eb
        L_0x009e:
            java.lang.String r2 = "thin"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 1
            goto L_0x00eb
        L_0x00a9:
            java.lang.String r2 = "book"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 4
            goto L_0x00eb
        L_0x00b3:
            java.lang.String r2 = "bold"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 10
            goto L_0x00eb
        L_0x00be:
            java.lang.String r2 = "fat"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 17
            goto L_0x00eb
        L_0x00c9:
            java.lang.String r2 = "extrabold"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 11
            goto L_0x00eb
        L_0x00d4:
            java.lang.String r2 = "normal"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 6
            goto L_0x00eb
        L_0x00df:
            java.lang.String r2 = "medium"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x000d
            r1 = 7
            goto L_0x00eb
        L_0x00ea:
            r1 = -1
        L_0x00eb:
            switch(r1) {
                case 0: goto L_0x010a;
                case 1: goto L_0x0107;
                case 2: goto L_0x0107;
                case 3: goto L_0x0104;
                case 4: goto L_0x0101;
                case 5: goto L_0x0101;
                case 6: goto L_0x0101;
                case 7: goto L_0x00fe;
                case 8: goto L_0x00fb;
                case 9: goto L_0x00fb;
                case 10: goto L_0x00f8;
                case 11: goto L_0x00f5;
                case 12: goto L_0x00f5;
                case 13: goto L_0x00f2;
                case 14: goto L_0x00f2;
                case 15: goto L_0x00f2;
                case 16: goto L_0x00f2;
                case 17: goto L_0x00ef;
                case 18: goto L_0x00ef;
                default: goto L_0x00ee;
            }
        L_0x00ee:
            goto L_0x010d
        L_0x00ef:
            r0 = 900(0x384, float:1.261E-42)
            goto L_0x010d
        L_0x00f2:
            r0 = 900(0x384, float:1.261E-42)
            goto L_0x010d
        L_0x00f5:
            r0 = 800(0x320, float:1.121E-42)
            goto L_0x010d
        L_0x00f8:
            r0 = 700(0x2bc, float:9.81E-43)
            goto L_0x010d
        L_0x00fb:
            r0 = 600(0x258, float:8.41E-43)
            goto L_0x010d
        L_0x00fe:
            r0 = 500(0x1f4, float:7.0E-43)
            goto L_0x010d
        L_0x0101:
            r0 = 400(0x190, float:5.6E-43)
            goto L_0x010d
        L_0x0104:
            r0 = 300(0x12c, float:4.2E-43)
            goto L_0x010d
        L_0x0107:
            r0 = 200(0xc8, float:2.8E-43)
            goto L_0x010d
        L_0x010a:
            r0 = 100
        L_0x010d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.constants.FontWeights.fromType1FontWeight(java.lang.String):int");
    }

    public static int normalizeFontWeight(int fontWeight) {
        int fontWeight2 = (fontWeight / 100) * 100;
        if (fontWeight2 < 100) {
            return 100;
        }
        if (fontWeight2 > 900) {
            return 900;
        }
        return fontWeight2;
    }
}
