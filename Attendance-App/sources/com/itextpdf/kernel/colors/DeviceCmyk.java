package com.itextpdf.kernel.colors;

public class DeviceCmyk extends Color {
    public static final DeviceCmyk BLACK = new DeviceCmyk(0, 0, 0, 100);
    public static final DeviceCmyk CYAN = new DeviceCmyk(100, 0, 0, 0);
    public static final DeviceCmyk MAGENTA = new DeviceCmyk(0, 100, 0, 0);
    public static final DeviceCmyk YELLOW = new DeviceCmyk(0, 0, 100, 0);
    private static final long serialVersionUID = 5466518014595706050L;

    public DeviceCmyk() {
        this(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public DeviceCmyk(int c, int m, int y, int k) {
        this(((float) c) / 100.0f, ((float) m) / 100.0f, ((float) y) / 100.0f, ((float) k) / 100.0f);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DeviceCmyk(float r7, float r8, float r9, float r10) {
        /*
            r6 = this;
            com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs$Cmyk r0 = new com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs$Cmyk
            r0.<init>()
            r1 = 4
            float[] r1 = new float[r1]
            r2 = 0
            r3 = 1065353216(0x3f800000, float:1.0)
            int r4 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r4 <= 0) goto L_0x0012
            r4 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0019
        L_0x0012:
            int r4 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0018
            r4 = r7
            goto L_0x0019
        L_0x0018:
            r4 = 0
        L_0x0019:
            r5 = 0
            r1[r5] = r4
            int r4 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r4 <= 0) goto L_0x0023
            r4 = 1065353216(0x3f800000, float:1.0)
            goto L_0x002a
        L_0x0023:
            int r4 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0029
            r4 = r8
            goto L_0x002a
        L_0x0029:
            r4 = 0
        L_0x002a:
            r5 = 1
            r1[r5] = r4
            int r4 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r4 <= 0) goto L_0x0034
            r4 = 1065353216(0x3f800000, float:1.0)
            goto L_0x003b
        L_0x0034:
            int r4 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x003a
            r4 = r9
            goto L_0x003b
        L_0x003a:
            r4 = 0
        L_0x003b:
            r5 = 2
            r1[r5] = r4
            int r4 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r4 <= 0) goto L_0x0045
            r4 = 1065353216(0x3f800000, float:1.0)
            goto L_0x004c
        L_0x0045:
            int r4 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x004b
            r4 = r10
            goto L_0x004c
        L_0x004b:
            r4 = 0
        L_0x004c:
            r5 = 3
            r1[r5] = r4
            r6.<init>(r0, r1)
            int r0 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r0 > 0) goto L_0x0072
            int r0 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x0072
            int r0 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r0 > 0) goto L_0x0072
            int r0 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x0072
            int r0 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r0 > 0) goto L_0x0072
            int r0 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x0072
            int r0 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r0 > 0) goto L_0x0072
            int r0 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x007d
        L_0x0072:
            java.lang.Class<com.itextpdf.kernel.colors.DeviceCmyk> r0 = com.itextpdf.kernel.colors.DeviceCmyk.class
            org.slf4j.Logger r0 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r0)
            java.lang.String r1 = "Some of colorant intensities are invalid: they are bigger than 1 or less than 0. We will force them to become 1 or 0 respectively."
            r0.warn(r1)
        L_0x007d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.colors.DeviceCmyk.<init>(float, float, float, float):void");
    }

    public static DeviceCmyk makeLighter(DeviceCmyk cmykColor) {
        return convertRgbToCmyk(DeviceRgb.makeLighter(convertCmykToRgb(cmykColor)));
    }

    public static DeviceCmyk makeDarker(DeviceCmyk cmykColor) {
        return convertRgbToCmyk(DeviceRgb.makeDarker(convertCmykToRgb(cmykColor)));
    }
}
