package androidx.core.graphics;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import androidx.core.content.res.FontResourcesParserCompat;
import java.lang.reflect.Field;

final class WeightTypefaceApi14 {
    private static final String NATIVE_INSTANCE_FIELD = "native_instance";
    private static final String TAG = "WeightTypeface";
    private static final Field sNativeInstance;
    private static final Object sWeightCacheLock = new Object();
    private static final LongSparseArray<SparseArray<Typeface>> sWeightTypefaceCache = new LongSparseArray<>(3);

    static {
        Field nativeInstance;
        try {
            nativeInstance = Typeface.class.getDeclaredField(NATIVE_INSTANCE_FIELD);
            nativeInstance.setAccessible(true);
        } catch (Exception e) {
            Log.e(TAG, e.getClass().getName(), e);
            nativeInstance = null;
        }
        sNativeInstance = nativeInstance;
    }

    private static boolean isPrivateApiAvailable() {
        return sNativeInstance != null;
    }

    static Typeface createWeightStyle(TypefaceCompatBaseImpl compat, Context context, Typeface base, int weight, boolean italic) {
        if (!isPrivateApiAvailable()) {
            return null;
        }
        int key = (weight << 1) | italic;
        synchronized (sWeightCacheLock) {
            long baseNativeInstance = getNativeInstance(base);
            LongSparseArray<SparseArray<Typeface>> longSparseArray = sWeightTypefaceCache;
            SparseArray<Typeface> innerCache = longSparseArray.get(baseNativeInstance);
            if (innerCache == null) {
                innerCache = new SparseArray<>(4);
                longSparseArray.put(baseNativeInstance, innerCache);
            } else {
                Typeface typeface = innerCache.get(key);
                if (typeface != null) {
                    return typeface;
                }
            }
            Typeface typeface2 = getBestFontFromFamily(compat, context, base, weight, italic);
            if (typeface2 == null) {
                typeface2 = platformTypefaceCreate(base, weight, italic);
            }
            innerCache.put((int) key, typeface2);
            return typeface2;
        }
    }

    private static Typeface platformTypefaceCreate(Typeface base, int weight, boolean italic) {
        int style;
        boolean isBold = weight >= 600;
        if (!isBold && !italic) {
            style = 0;
        } else if (!isBold) {
            style = 2;
        } else if (!italic) {
            style = 1;
        } else {
            style = 3;
        }
        return Typeface.create(base, style);
    }

    private static Typeface getBestFontFromFamily(TypefaceCompatBaseImpl compat, Context context, Typeface base, int weight, boolean italic) {
        FontResourcesParserCompat.FontFamilyFilesResourceEntry family = compat.getFontFamily(base);
        if (family == null) {
            return null;
        }
        return compat.createFromFontFamilyFilesResourceEntry(context, family, context.getResources(), weight, italic);
    }

    private static long getNativeInstance(Typeface typeface) {
        try {
            return ((Number) sNativeInstance.get(typeface)).longValue();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private WeightTypefaceApi14() {
    }
}
