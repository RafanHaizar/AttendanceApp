package com.itextpdf.kernel.colors;

public class DeviceGray extends Color {
    public static final DeviceGray BLACK = new DeviceGray();
    public static final DeviceGray GRAY = new DeviceGray(0.5f);
    public static final DeviceGray WHITE = new DeviceGray(1.0f);
    private static final long serialVersionUID = 8307729543359242834L;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DeviceGray(float r7) {
        /*
            r6 = this;
            com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs$Gray r0 = new com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs$Gray
            r0.<init>()
            r1 = 1
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
            r6.<init>(r0, r1)
            int r0 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r0 > 0) goto L_0x0027
            int r0 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x0032
        L_0x0027:
            java.lang.Class<com.itextpdf.kernel.colors.DeviceGray> r0 = com.itextpdf.kernel.colors.DeviceGray.class
            org.slf4j.Logger r0 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r0)
            java.lang.String r1 = "Some of colorant intensities are invalid: they are bigger than 1 or less than 0. We will force them to become 1 or 0 respectively."
            r0.warn(r1)
        L_0x0032:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.colors.DeviceGray.<init>(float):void");
    }

    public DeviceGray() {
        this(0.0f);
    }

    public static DeviceGray makeLighter(DeviceGray grayColor) {
        float v = grayColor.getColorValue()[0];
        if (v == 0.0f) {
            return new DeviceGray(0.3f);
        }
        return new DeviceGray(v * (Math.min(1.0f, 0.33f + v) / v));
    }

    public static DeviceGray makeDarker(DeviceGray grayColor) {
        float v = grayColor.getColorValue()[0];
        return new DeviceGray(v * Math.max(0.0f, (v - 0.33f) / v));
    }
}
