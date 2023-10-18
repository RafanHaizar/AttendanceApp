package com.itextpdf.layout.font;

final class FontCharacteristicsUtils {
    FontCharacteristicsUtils() {
    }

    static short normalizeFontWeight(short fw) {
        short fw2 = (short) ((fw / 100) * 100);
        if (fw2 < 100) {
            return 100;
        }
        if (fw2 > 900) {
            return 900;
        }
        return fw2;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static short parseFontWeight(java.lang.String r2) {
        /*
            r0 = -1
            if (r2 == 0) goto L_0x0046
            int r1 = r2.length()
            if (r1 != 0) goto L_0x000a
            goto L_0x0046
        L_0x000a:
            java.lang.String r1 = r2.trim()
            java.lang.String r2 = r1.toLowerCase()
            int r1 = r2.hashCode()
            switch(r1) {
                case -1039745817: goto L_0x0024;
                case 3029637: goto L_0x001a;
                default: goto L_0x0019;
            }
        L_0x0019:
            goto L_0x002f
        L_0x001a:
            java.lang.String r1 = "bold"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x0019
            r1 = 0
            goto L_0x0030
        L_0x0024:
            java.lang.String r1 = "normal"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x0019
            r1 = 1
            goto L_0x0030
        L_0x002f:
            r1 = -1
        L_0x0030:
            switch(r1) {
                case 0: goto L_0x003b;
                case 1: goto L_0x0038;
                default: goto L_0x0033;
            }
        L_0x0033:
            int r1 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x0044 }
            goto L_0x003e
        L_0x0038:
            r0 = 400(0x190, float:5.6E-43)
            return r0
        L_0x003b:
            r0 = 700(0x2bc, float:9.81E-43)
            return r0
        L_0x003e:
            short r1 = (short) r1     // Catch:{ NumberFormatException -> 0x0044 }
            short r0 = normalizeFontWeight(r1)     // Catch:{ NumberFormatException -> 0x0044 }
            return r0
        L_0x0044:
            r1 = move-exception
            return r0
        L_0x0046:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.font.FontCharacteristicsUtils.parseFontWeight(java.lang.String):short");
    }
}
