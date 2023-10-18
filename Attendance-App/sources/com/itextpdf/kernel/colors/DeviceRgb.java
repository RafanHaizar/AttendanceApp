package com.itextpdf.kernel.colors;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.awt.Color;
import org.slf4j.LoggerFactory;

public class DeviceRgb extends Color {
    public static final Color BLACK = new DeviceRgb(0, 0, 0);
    public static final Color BLUE = new DeviceRgb(0, 0, 255);
    public static final Color GREEN = new DeviceRgb(0, 255, 0);
    public static final Color RED = new DeviceRgb(255, 0, 0);
    public static final Color WHITE = new DeviceRgb(255, 255, 255);
    private static final long serialVersionUID = 7172400358137528030L;

    public DeviceRgb(int r, int g, int b) {
        this(((float) r) / 255.0f, ((float) g) / 255.0f, ((float) b) / 255.0f);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DeviceRgb(float r7, float r8, float r9) {
        /*
            r6 = this;
            com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs$Rgb r0 = new com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs$Rgb
            r0.<init>()
            r1 = 3
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
            r6.<init>(r0, r1)
            int r0 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r0 > 0) goto L_0x0059
            int r0 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x0059
            int r0 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r0 > 0) goto L_0x0059
            int r0 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x0059
            int r0 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r0 > 0) goto L_0x0059
            int r0 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x0064
        L_0x0059:
            java.lang.Class<com.itextpdf.kernel.colors.DeviceRgb> r0 = com.itextpdf.kernel.colors.DeviceRgb.class
            org.slf4j.Logger r0 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r0)
            java.lang.String r1 = "Some of colorant intensities are invalid: they are bigger than 1 or less than 0. We will force them to become 1 or 0 respectively."
            r0.warn(r1)
        L_0x0064:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.colors.DeviceRgb.<init>(float, float, float):void");
    }

    public DeviceRgb(Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue());
        if (color.getAlpha() != 255) {
            LoggerFactory.getLogger((Class<?>) DeviceRgb.class).warn(MessageFormatUtil.format(LogMessageConstant.COLOR_ALPHA_CHANNEL_IS_IGNORED, Integer.valueOf(color.getAlpha())));
        }
    }

    public DeviceRgb() {
        this(0.0f, 0.0f, 0.0f);
    }

    public static DeviceRgb makeLighter(DeviceRgb rgbColor) {
        float r = rgbColor.getColorValue()[0];
        float g = rgbColor.getColorValue()[1];
        float b = rgbColor.getColorValue()[2];
        float v = Math.max(r, Math.max(g, b));
        if (v == 0.0f) {
            return new DeviceRgb(84, 84, 84);
        }
        float multiplier = Math.min(1.0f, 0.33f + v) / v;
        return new DeviceRgb(r * multiplier, g * multiplier, b * multiplier);
    }

    public static DeviceRgb makeDarker(DeviceRgb rgbColor) {
        float r = rgbColor.getColorValue()[0];
        float g = rgbColor.getColorValue()[1];
        float b = rgbColor.getColorValue()[2];
        float v = Math.max(r, Math.max(g, b));
        float multiplier = Math.max(0.0f, (v - 0.33f) / v);
        return new DeviceRgb(r * multiplier, g * multiplier, b * multiplier);
    }
}
