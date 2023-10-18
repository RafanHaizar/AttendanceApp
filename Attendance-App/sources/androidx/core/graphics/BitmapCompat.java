package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.os.Build;

public final class BitmapCompat {
    public static boolean hasMipMap(Bitmap bitmap) {
        return Api17Impl.hasMipMap(bitmap);
    }

    public static void setHasMipMap(Bitmap bitmap, boolean hasMipMap) {
        Api17Impl.setHasMipMap(bitmap, hasMipMap);
    }

    public static int getAllocationByteCount(Bitmap bitmap) {
        return Api19Impl.getAllocationByteCount(bitmap);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01e5, code lost:
        if (androidx.core.graphics.BitmapCompat.Api27Impl.isAlreadyF16AndLinear(r8) == false) goto L_0x01fc;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap createScaledBitmap(android.graphics.Bitmap r30, int r31, int r32, android.graphics.Rect r33, boolean r34) {
        /*
            r0 = r30
            r1 = r31
            r2 = r32
            r3 = r33
            if (r1 <= 0) goto L_0x0250
            if (r2 <= 0) goto L_0x0250
            if (r3 == 0) goto L_0x0036
            boolean r4 = r33.isEmpty()
            if (r4 != 0) goto L_0x002d
            int r4 = r3.left
            if (r4 < 0) goto L_0x002d
            int r4 = r3.right
            int r5 = r30.getWidth()
            if (r4 > r5) goto L_0x002d
            int r4 = r3.top
            if (r4 < 0) goto L_0x002d
            int r4 = r3.bottom
            int r5 = r30.getHeight()
            if (r4 > r5) goto L_0x002d
            goto L_0x0036
        L_0x002d:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "srcRect must be contained by srcBm!"
            r4.<init>(r5)
            throw r4
        L_0x0036:
            r4 = r30
            int r5 = android.os.Build.VERSION.SDK_INT
            r6 = 27
            if (r5 < r6) goto L_0x0042
            android.graphics.Bitmap r4 = androidx.core.graphics.BitmapCompat.Api27Impl.copyBitmapIfHardware(r30)
        L_0x0042:
            if (r3 == 0) goto L_0x0049
            int r5 = r33.width()
            goto L_0x004d
        L_0x0049:
            int r5 = r30.getWidth()
        L_0x004d:
            if (r3 == 0) goto L_0x0054
            int r7 = r33.height()
            goto L_0x0058
        L_0x0054:
            int r7 = r30.getHeight()
        L_0x0058:
            float r8 = (float) r1
            float r9 = (float) r5
            float r8 = r8 / r9
            float r9 = (float) r2
            float r10 = (float) r7
            float r9 = r9 / r10
            if (r3 == 0) goto L_0x0063
            int r11 = r3.left
            goto L_0x0064
        L_0x0063:
            r11 = 0
        L_0x0064:
            if (r3 == 0) goto L_0x0069
            int r12 = r3.top
            goto L_0x006a
        L_0x0069:
            r12 = 0
        L_0x006a:
            r13 = 1
            if (r11 != 0) goto L_0x008d
            if (r12 != 0) goto L_0x008d
            int r14 = r30.getWidth()
            if (r1 != r14) goto L_0x008d
            int r14 = r30.getHeight()
            if (r2 != r14) goto L_0x008d
            boolean r6 = r30.isMutable()
            if (r6 == 0) goto L_0x008c
            if (r0 != r4) goto L_0x008c
            android.graphics.Bitmap$Config r6 = r30.getConfig()
            android.graphics.Bitmap r6 = r0.copy(r6, r13)
            return r6
        L_0x008c:
            return r4
        L_0x008d:
            android.graphics.Paint r14 = new android.graphics.Paint
            r14.<init>(r13)
            r14.setFilterBitmap(r13)
            int r15 = android.os.Build.VERSION.SDK_INT
            r10 = 29
            if (r15 < r10) goto L_0x009f
            androidx.core.graphics.BitmapCompat.Api29Impl.setPaintBlendMode(r14)
            goto L_0x00a9
        L_0x009f:
            android.graphics.PorterDuffXfermode r10 = new android.graphics.PorterDuffXfermode
            android.graphics.PorterDuff$Mode r15 = android.graphics.PorterDuff.Mode.SRC
            r10.<init>(r15)
            r14.setXfermode(r10)
        L_0x00a9:
            if (r5 != r1) goto L_0x00c2
            if (r7 != r2) goto L_0x00c2
            android.graphics.Bitmap$Config r6 = r4.getConfig()
            android.graphics.Bitmap r6 = android.graphics.Bitmap.createBitmap(r1, r2, r6)
            android.graphics.Canvas r10 = new android.graphics.Canvas
            r10.<init>(r6)
            int r13 = -r11
            float r13 = (float) r13
            int r15 = -r12
            float r15 = (float) r15
            r10.drawBitmap(r4, r13, r15, r14)
            return r6
        L_0x00c2:
            r17 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r17 = java.lang.Math.log(r17)
            r10 = 1065353216(0x3f800000, float:1.0)
            int r15 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r15 <= 0) goto L_0x00dc
            r15 = r14
            double r13 = (double) r8
            double r13 = java.lang.Math.log(r13)
            double r13 = r13 / r17
            double r13 = java.lang.Math.ceil(r13)
            int r13 = (int) r13
            goto L_0x00e9
        L_0x00dc:
            r15 = r14
            double r13 = (double) r8
            double r13 = java.lang.Math.log(r13)
            double r13 = r13 / r17
            double r13 = java.lang.Math.floor(r13)
            int r13 = (int) r13
        L_0x00e9:
            int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r10 <= 0) goto L_0x00fc
            r10 = r7
            double r6 = (double) r9
            double r6 = java.lang.Math.log(r6)
            double r6 = r6 / r17
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            goto L_0x0109
        L_0x00fc:
            r10 = r7
            double r6 = (double) r9
            double r6 = java.lang.Math.log(r6)
            double r6 = r6 / r17
            double r6 = java.lang.Math.floor(r6)
            int r6 = (int) r6
        L_0x0109:
            r7 = r13
            r20 = r6
            r21 = 0
            r22 = 0
            if (r34 == 0) goto L_0x016b
            int r14 = android.os.Build.VERSION.SDK_INT
            r3 = 27
            if (r14 < r3) goto L_0x0162
            boolean r3 = androidx.core.graphics.BitmapCompat.Api27Impl.isAlreadyF16AndLinear(r30)
            if (r3 != 0) goto L_0x0162
            if (r13 <= 0) goto L_0x0127
            r3 = 1
            int r19 = sizeAtStep(r5, r1, r3, r7)
            goto L_0x012a
        L_0x0127:
            r3 = 1
            r19 = r5
        L_0x012a:
            r23 = r19
            if (r6 <= 0) goto L_0x0135
            r14 = r20
            int r20 = sizeAtStep(r10, r2, r3, r14)
            goto L_0x0139
        L_0x0135:
            r14 = r20
            r20 = r10
        L_0x0139:
            r24 = r20
            r20 = r6
            r6 = r23
            r23 = r8
            r8 = r24
            r24 = r9
            android.graphics.Bitmap r9 = androidx.core.graphics.BitmapCompat.Api27Impl.createBitmapWithSourceColorspace(r6, r8, r0, r3)
            android.graphics.Canvas r3 = new android.graphics.Canvas
            r3.<init>(r9)
            r25 = r6
            int r6 = -r11
            float r6 = (float) r6
            r26 = r8
            int r8 = -r12
            float r8 = (float) r8
            r3.drawBitmap(r4, r6, r8, r15)
            r11 = 0
            r12 = 0
            r6 = r9
            r21 = r4
            r4 = r6
            r22 = 1
            goto L_0x0173
        L_0x0162:
            r23 = r8
            r24 = r9
            r14 = r20
            r20 = r6
            goto L_0x0173
        L_0x016b:
            r23 = r8
            r24 = r9
            r14 = r20
            r20 = r6
        L_0x0173:
            android.graphics.Rect r3 = new android.graphics.Rect
            r3.<init>(r11, r12, r5, r10)
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>()
            r8 = r21
        L_0x017f:
            if (r13 != 0) goto L_0x018c
            if (r20 == 0) goto L_0x0184
            goto L_0x018c
        L_0x0184:
            if (r8 == r0) goto L_0x018b
            if (r8 == 0) goto L_0x018b
            r8.recycle()
        L_0x018b:
            return r4
        L_0x018c:
            if (r13 >= 0) goto L_0x0191
            int r13 = r13 + 1
            goto L_0x0195
        L_0x0191:
            if (r13 <= 0) goto L_0x0195
            int r13 = r13 + -1
        L_0x0195:
            if (r20 >= 0) goto L_0x019c
            int r20 = r20 + 1
            r9 = r20
            goto L_0x01a5
        L_0x019c:
            if (r20 <= 0) goto L_0x01a3
            int r20 = r20 + -1
            r9 = r20
            goto L_0x01a5
        L_0x01a3:
            r9 = r20
        L_0x01a5:
            r20 = r11
            int r11 = sizeAtStep(r5, r1, r13, r7)
            r21 = r12
            int r12 = sizeAtStep(r10, r2, r9, r14)
            r25 = r3
            r3 = 0
            r6.set(r3, r3, r11, r12)
            if (r13 != 0) goto L_0x01be
            if (r9 != 0) goto L_0x01be
            r16 = 1
            goto L_0x01c0
        L_0x01be:
            r16 = 0
        L_0x01c0:
            if (r8 == 0) goto L_0x01d0
            int r3 = r8.getWidth()
            if (r3 != r1) goto L_0x01d0
            int r3 = r8.getHeight()
            if (r3 != r2) goto L_0x01d0
            r3 = 1
            goto L_0x01d1
        L_0x01d0:
            r3 = 0
        L_0x01d1:
            if (r8 == 0) goto L_0x01f8
            if (r8 == r0) goto L_0x01f8
            if (r34 == 0) goto L_0x01e8
            r27 = r11
            int r11 = android.os.Build.VERSION.SDK_INT
            r28 = r12
            r12 = 27
            if (r11 < r12) goto L_0x01ec
            boolean r12 = androidx.core.graphics.BitmapCompat.Api27Impl.isAlreadyF16AndLinear(r8)
            if (r12 == 0) goto L_0x01f2
            goto L_0x01ec
        L_0x01e8:
            r27 = r11
            r28 = r12
        L_0x01ec:
            if (r16 == 0) goto L_0x01f3
            if (r3 == 0) goto L_0x01f2
            if (r22 == 0) goto L_0x01f3
        L_0x01f2:
            goto L_0x01fc
        L_0x01f3:
            r29 = r3
            r3 = 27
            goto L_0x0233
        L_0x01f8:
            r27 = r11
            r28 = r12
        L_0x01fc:
            if (r8 == r0) goto L_0x0203
            if (r8 == 0) goto L_0x0203
            r8.recycle()
        L_0x0203:
            r12 = r22
            if (r13 <= 0) goto L_0x0209
            r11 = r12
            goto L_0x020a
        L_0x0209:
            r11 = r13
        L_0x020a:
            int r11 = sizeAtStep(r5, r1, r11, r7)
            if (r9 <= 0) goto L_0x0212
            r1 = r12
            goto L_0x0213
        L_0x0212:
            r1 = r9
        L_0x0213:
            int r1 = sizeAtStep(r10, r2, r1, r14)
            int r2 = android.os.Build.VERSION.SDK_INT
            r29 = r3
            r3 = 27
            if (r2 < r3) goto L_0x022b
            if (r34 == 0) goto L_0x0225
            if (r16 != 0) goto L_0x0225
            r2 = 1
            goto L_0x0226
        L_0x0225:
            r2 = 0
        L_0x0226:
            android.graphics.Bitmap r8 = androidx.core.graphics.BitmapCompat.Api27Impl.createBitmapWithSourceColorspace(r11, r1, r0, r2)
            goto L_0x0233
        L_0x022b:
            android.graphics.Bitmap$Config r2 = r4.getConfig()
            android.graphics.Bitmap r8 = android.graphics.Bitmap.createBitmap(r11, r1, r2)
        L_0x0233:
            android.graphics.Canvas r1 = new android.graphics.Canvas
            r1.<init>(r8)
            r2 = r25
            r1.drawBitmap(r4, r2, r6, r15)
            r11 = r4
            r4 = r8
            r8 = r11
            r2.set(r6)
            r1 = r31
            r3 = r2
            r11 = r20
            r12 = r21
            r2 = r32
            r20 = r9
            goto L_0x017f
        L_0x0250:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "dstW and dstH must be > 0!"
            r1.<init>(r2)
            goto L_0x0259
        L_0x0258:
            throw r1
        L_0x0259:
            goto L_0x0258
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.BitmapCompat.createScaledBitmap(android.graphics.Bitmap, int, int, android.graphics.Rect, boolean):android.graphics.Bitmap");
    }

    public static int sizeAtStep(int srcSize, int dstSize, int step, int totalSteps) {
        if (step == 0) {
            return dstSize;
        }
        if (step > 0) {
            return (1 << (totalSteps - step)) * srcSize;
        }
        return dstSize << ((-step) - 1);
    }

    private BitmapCompat() {
    }

    static class Api17Impl {
        private Api17Impl() {
        }

        static boolean hasMipMap(Bitmap bitmap) {
            return bitmap.hasMipMap();
        }

        static void setHasMipMap(Bitmap bitmap, boolean hasMipMap) {
            bitmap.setHasMipMap(hasMipMap);
        }
    }

    static class Api19Impl {
        private Api19Impl() {
        }

        static int getAllocationByteCount(Bitmap bitmap) {
            return bitmap.getAllocationByteCount();
        }
    }

    static class Api27Impl {
        private Api27Impl() {
        }

        static Bitmap createBitmapWithSourceColorspace(int w, int h, Bitmap src, boolean linear) {
            Bitmap.Config config = src.getConfig();
            ColorSpace colorSpace = src.getColorSpace();
            ColorSpace linearCs = ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
            if (linear && !src.getColorSpace().equals(linearCs)) {
                config = Bitmap.Config.RGBA_F16;
                colorSpace = linearCs;
            } else if (src.getConfig() == Bitmap.Config.HARDWARE) {
                config = Bitmap.Config.ARGB_8888;
                if (Build.VERSION.SDK_INT >= 31) {
                    config = Api31Impl.getHardwareBitmapConfig(src);
                }
            }
            return Bitmap.createBitmap(w, h, config, src.hasAlpha(), colorSpace);
        }

        static boolean isAlreadyF16AndLinear(Bitmap b) {
            return b.getConfig() == Bitmap.Config.RGBA_F16 && b.getColorSpace().equals(ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB));
        }

        static Bitmap copyBitmapIfHardware(Bitmap bm) {
            if (bm.getConfig() != Bitmap.Config.HARDWARE) {
                return bm;
            }
            Bitmap.Config newConfig = Bitmap.Config.ARGB_8888;
            if (Build.VERSION.SDK_INT >= 31) {
                newConfig = Api31Impl.getHardwareBitmapConfig(bm);
            }
            return bm.copy(newConfig, true);
        }
    }

    static class Api29Impl {
        private Api29Impl() {
        }

        static void setPaintBlendMode(Paint paint) {
            paint.setBlendMode(BlendMode.SRC);
        }
    }

    static class Api31Impl {
        private Api31Impl() {
        }

        static Bitmap.Config getHardwareBitmapConfig(Bitmap bm) {
            if (bm.getHardwareBuffer().getFormat() == 22) {
                return Bitmap.Config.RGBA_F16;
            }
            return Bitmap.Config.ARGB_8888;
        }
    }
}
