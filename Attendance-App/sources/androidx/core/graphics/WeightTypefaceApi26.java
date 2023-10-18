package androidx.core.graphics;

import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class WeightTypefaceApi26 {
    private static final String NATIVE_CREATE_FROM_TYPEFACE_WITH_EXACT_STYLE_METHOD = "nativeCreateFromTypefaceWithExactStyle";
    private static final String NATIVE_INSTANCE_FIELD = "native_instance";
    private static final String TAG = "WeightTypeface";
    private static final Constructor<Typeface> sConstructor;
    private static final Method sNativeCreateFromTypefaceWithExactStyle;
    private static final Field sNativeInstance;
    private static final Object sWeightCacheLock = new Object();
    private static final LongSparseArray<SparseArray<Typeface>> sWeightTypefaceCache = new LongSparseArray<>(3);

    static {
        Constructor<Typeface> constructor;
        Method nativeCreateFromTypefaceWithExactStyle;
        Field nativeInstance;
        try {
            nativeInstance = Typeface.class.getDeclaredField(NATIVE_INSTANCE_FIELD);
            nativeCreateFromTypefaceWithExactStyle = Typeface.class.getDeclaredMethod(NATIVE_CREATE_FROM_TYPEFACE_WITH_EXACT_STYLE_METHOD, new Class[]{Long.TYPE, Integer.TYPE, Boolean.TYPE});
            nativeCreateFromTypefaceWithExactStyle.setAccessible(true);
            constructor = Typeface.class.getDeclaredConstructor(new Class[]{Long.TYPE});
            constructor.setAccessible(true);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            Log.e(TAG, e.getClass().getName(), e);
            nativeInstance = null;
            nativeCreateFromTypefaceWithExactStyle = null;
            constructor = null;
        }
        sNativeInstance = nativeInstance;
        sNativeCreateFromTypefaceWithExactStyle = nativeCreateFromTypefaceWithExactStyle;
        sConstructor = constructor;
    }

    private static boolean isPrivateApiAvailable() {
        return sNativeInstance != null;
    }

    static Typeface createWeightStyle(Typeface base, int weight, boolean italic) {
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
            Typeface typeface2 = create(nativeCreateFromTypefaceWithExactStyle(baseNativeInstance, weight, italic));
            innerCache.put((int) key, typeface2);
            return typeface2;
        }
    }

    private static long getNativeInstance(Typeface typeface) {
        try {
            return sNativeInstance.getLong(typeface);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static long nativeCreateFromTypefaceWithExactStyle(long nativeInstance, int weight, boolean italic) {
        try {
            return ((Long) sNativeCreateFromTypefaceWithExactStyle.invoke((Object) null, new Object[]{Long.valueOf(nativeInstance), Integer.valueOf(weight), Boolean.valueOf(italic)})).longValue();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static Typeface create(long nativeInstance) {
        try {
            return sConstructor.newInstance(new Object[]{Long.valueOf(nativeInstance)});
        } catch (IllegalAccessException e) {
            return null;
        } catch (InstantiationException e2) {
            return null;
        } catch (InvocationTargetException e3) {
            return null;
        }
    }

    private WeightTypefaceApi26() {
    }
}
