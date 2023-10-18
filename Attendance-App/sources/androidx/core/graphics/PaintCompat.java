package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import androidx.core.util.Pair;

public final class PaintCompat {
    private static final String EM_STRING = "m";
    private static final String TOFU_STRING = "󟿽";
    private static final ThreadLocal<Pair<Rect, Rect>> sRectThreadLocal = new ThreadLocal<>();

    public static boolean hasGlyph(Paint paint, String string) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.hasGlyph(paint, string);
        }
        int length = string.length();
        if (length == 1 && Character.isWhitespace(string.charAt(0))) {
            return true;
        }
        float missingGlyphWidth = paint.measureText(TOFU_STRING);
        float emGlyphWidth = paint.measureText("m");
        float width = paint.measureText(string);
        if (width == 0.0f) {
            return false;
        }
        if (string.codePointCount(0, string.length()) > 1) {
            if (width > 2.0f * emGlyphWidth) {
                return false;
            }
            float sumWidth = 0.0f;
            int i = 0;
            while (i < length) {
                int charCount = Character.charCount(string.codePointAt(i));
                sumWidth += paint.measureText(string, i, i + charCount);
                i += charCount;
            }
            if (width >= sumWidth) {
                return false;
            }
        }
        if (width != missingGlyphWidth) {
            return true;
        }
        Pair<Rect, Rect> rects = obtainEmptyRects();
        paint.getTextBounds(TOFU_STRING, 0, TOFU_STRING.length(), (Rect) rects.first);
        paint.getTextBounds(string, 0, length, (Rect) rects.second);
        return true ^ ((Rect) rects.first).equals(rects.second);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.graphics.PorterDuffXfermode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.graphics.PorterDuffXfermode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: android.graphics.PorterDuffXfermode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: android.graphics.PorterDuffXfermode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: android.graphics.PorterDuffXfermode} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean setBlendMode(android.graphics.Paint r4, androidx.core.graphics.BlendModeCompat r5) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            r2 = 1
            r3 = 0
            if (r0 < r1) goto L_0x0015
            if (r5 == 0) goto L_0x000f
            java.lang.Object r3 = androidx.core.graphics.BlendModeUtils.Api29Impl.obtainBlendModeFromCompat(r5)
            goto L_0x0010
        L_0x000f:
        L_0x0010:
            r0 = r3
            androidx.core.graphics.PaintCompat.Api29Impl.setBlendMode(r4, r0)
            return r2
        L_0x0015:
            if (r5 == 0) goto L_0x002a
            android.graphics.PorterDuff$Mode r0 = androidx.core.graphics.BlendModeUtils.obtainPorterDuffFromCompat(r5)
            if (r0 == 0) goto L_0x0022
            android.graphics.PorterDuffXfermode r3 = new android.graphics.PorterDuffXfermode
            r3.<init>(r0)
        L_0x0022:
            r4.setXfermode(r3)
            if (r0 == 0) goto L_0x0028
            goto L_0x0029
        L_0x0028:
            r2 = 0
        L_0x0029:
            return r2
        L_0x002a:
            r4.setXfermode(r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.PaintCompat.setBlendMode(android.graphics.Paint, androidx.core.graphics.BlendModeCompat):boolean");
    }

    private static Pair<Rect, Rect> obtainEmptyRects() {
        ThreadLocal<Pair<Rect, Rect>> threadLocal = sRectThreadLocal;
        Pair<Rect, Rect> rects = threadLocal.get();
        if (rects == null) {
            Pair<Rect, Rect> rects2 = new Pair<>(new Rect(), new Rect());
            threadLocal.set(rects2);
            return rects2;
        }
        ((Rect) rects.first).setEmpty();
        ((Rect) rects.second).setEmpty();
        return rects;
    }

    private PaintCompat() {
    }

    static class Api29Impl {
        private Api29Impl() {
        }

        static void setBlendMode(Paint paint, Object blendmode) {
            paint.setBlendMode((BlendMode) blendmode);
        }
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static boolean hasGlyph(Paint paint, String string) {
            return paint.hasGlyph(string);
        }
    }
}
