package androidx.core.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    protected final Method mAbortCreation;
    protected final Method mAddFontFromAssetManager;
    protected final Method mAddFontFromBuffer;
    protected final Method mCreateFromFamiliesWithDefault;
    protected final Class<?> mFontFamily;
    protected final Constructor<?> mFontFamilyCtor;
    protected final Method mFreeze;

    public TypefaceCompatApi26Impl() {
        Method abortCreation;
        Method freeze;
        Method addFontFromBuffer;
        Method addFontFromAssetManager;
        Method addFontFromAssetManager2;
        Constructor<?> fontFamilyCtor;
        Class<?> fontFamily;
        try {
            fontFamily = obtainFontFamily();
            fontFamilyCtor = obtainFontFamilyCtor(fontFamily);
            addFontFromAssetManager2 = obtainAddFontFromAssetManagerMethod(fontFamily);
            addFontFromAssetManager = obtainAddFontFromBufferMethod(fontFamily);
            addFontFromBuffer = obtainFreezeMethod(fontFamily);
            freeze = obtainAbortCreationMethod(fontFamily);
            abortCreation = obtainCreateFromFamiliesWithDefaultMethod(fontFamily);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            fontFamily = null;
            fontFamilyCtor = null;
            addFontFromAssetManager2 = null;
            addFontFromAssetManager = null;
            addFontFromBuffer = null;
            freeze = null;
            abortCreation = null;
        }
        this.mFontFamily = fontFamily;
        this.mFontFamilyCtor = fontFamilyCtor;
        this.mAddFontFromAssetManager = addFontFromAssetManager2;
        this.mAddFontFromBuffer = addFontFromAssetManager;
        this.mFreeze = addFontFromBuffer;
        this.mAbortCreation = freeze;
        this.mCreateFromFamiliesWithDefault = abortCreation;
    }

    private boolean isFontFamilyPrivateAPIAvailable() {
        if (this.mAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.mAddFontFromAssetManager != null;
    }

    private Object newFamily() {
        try {
            return this.mFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return null;
        }
    }

    private boolean addFontFromAssetManager(Context context, Object family, String fileName, int ttcIndex, int weight, int style, FontVariationAxis[] axes) {
        try {
            return ((Boolean) this.mAddFontFromAssetManager.invoke(family, new Object[]{context.getAssets(), fileName, 0, false, Integer.valueOf(ttcIndex), Integer.valueOf(weight), Integer.valueOf(style), axes})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }

    private boolean addFontFromBuffer(Object family, ByteBuffer buffer, int ttcIndex, int weight, int style) {
        try {
            return ((Boolean) this.mAddFontFromBuffer.invoke(family, new Object[]{buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Integer.valueOf(style)})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Object familyArray = Array.newInstance(this.mFontFamily, 1);
            Array.set(familyArray, 0, family);
            return (Typeface) this.mCreateFromFamiliesWithDefault.invoke((Object) null, new Object[]{familyArray, -1, -1});
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    private boolean freeze(Object family) {
        try {
            return ((Boolean) this.mFreeze.invoke(family, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }

    private void abortCreation(Object family) {
        try {
            this.mAbortCreation.invoke(family, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }
    }

    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, entry, resources, style);
        }
        Object fontFamily = newFamily();
        if (fontFamily == null) {
            return null;
        }
        for (FontResourcesParserCompat.FontFileResourceEntry fontFile : entry.getEntries()) {
            if (!addFontFromAssetManager(context, fontFamily, fontFile.getFileName(), fontFile.getTtcIndex(), fontFile.getWeight(), fontFile.isItalic() ? 1 : 0, FontVariationAxis.fromFontVariationSettings(fontFile.getVariationSettings()))) {
                abortCreation(fontFamily);
                return null;
            }
        }
        if (!freeze(fontFamily)) {
            return null;
        }
        return createFromFamiliesWithDefault(fontFamily);
    }

    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) {
        Typeface typeface;
        int i;
        ParcelFileDescriptor pfd;
        Throwable th;
        CancellationSignal cancellationSignal2 = cancellationSignal;
        FontsContractCompat.FontInfo[] fontInfoArr = fonts;
        int i2 = style;
        if (fontInfoArr.length < 1) {
            return null;
        }
        if (!isFontFamilyPrivateAPIAvailable()) {
            FontsContractCompat.FontInfo bestFont = findBestInfo(fontInfoArr, i2);
            try {
                pfd = context.getContentResolver().openFileDescriptor(bestFont.getUri(), "r", cancellationSignal2);
                if (pfd == null) {
                    if (pfd != null) {
                        pfd.close();
                    }
                    return null;
                }
                Typeface build = new Typeface.Builder(pfd.getFileDescriptor()).setWeight(bestFont.getWeight()).setItalic(bestFont.isItalic()).build();
                if (pfd != null) {
                    pfd.close();
                }
                return build;
            } catch (IOException e) {
                return null;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            Map<Uri, ByteBuffer> uriBuffer = TypefaceCompatUtil.readFontInfoIntoByteBuffer(context, fontInfoArr, cancellationSignal2);
            Object fontFamily = newFamily();
            if (fontFamily == null) {
                return null;
            }
            int length = fontInfoArr.length;
            boolean atLeastOneFont = false;
            int i3 = 0;
            while (i3 < length) {
                FontsContractCompat.FontInfo font = fontInfoArr[i3];
                ByteBuffer fontBuffer = uriBuffer.get(font.getUri());
                if (fontBuffer == null) {
                    i = i3;
                } else {
                    i = i3;
                    if (!addFontFromBuffer(fontFamily, fontBuffer, font.getTtcIndex(), font.getWeight(), font.isItalic() ? 1 : 0)) {
                        abortCreation(fontFamily);
                        return null;
                    }
                    atLeastOneFont = true;
                }
                i3 = i + 1;
            }
            if (!atLeastOneFont) {
                abortCreation(fontFamily);
                return null;
            } else if (freeze(fontFamily) && (typeface = createFromFamiliesWithDefault(fontFamily)) != null) {
                return Typeface.create(typeface, i2);
            } else {
                return null;
            }
        }
        throw th;
    }

    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, id, path, style);
        }
        Object fontFamily = newFamily();
        if (fontFamily == null) {
            return null;
        }
        if (!addFontFromAssetManager(context, fontFamily, path, 0, -1, -1, (FontVariationAxis[]) null)) {
            abortCreation(fontFamily);
            return null;
        } else if (!freeze(fontFamily)) {
            return null;
        } else {
            return createFromFamiliesWithDefault(fontFamily);
        }
    }

    /* access modifiers changed from: protected */
    public Class<?> obtainFontFamily() throws ClassNotFoundException {
        return Class.forName(FONT_FAMILY_CLASS);
    }

    /* access modifiers changed from: protected */
    public Constructor<?> obtainFontFamilyCtor(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getConstructor(new Class[0]);
    }

    /* access modifiers changed from: protected */
    public Method obtainAddFontFromAssetManagerMethod(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, new Class[]{AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class});
    }

    /* access modifiers changed from: protected */
    public Method obtainAddFontFromBufferMethod(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(ADD_FONT_FROM_BUFFER_METHOD, new Class[]{ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE});
    }

    /* access modifiers changed from: protected */
    public Method obtainFreezeMethod(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(FREEZE_METHOD, new Class[0]);
    }

    /* access modifiers changed from: protected */
    public Method obtainAbortCreationMethod(Class<?> fontFamily) throws NoSuchMethodException {
        return fontFamily.getMethod(ABORT_CREATION_METHOD, new Class[0]);
    }

    /* access modifiers changed from: protected */
    public Method obtainCreateFromFamiliesWithDefaultMethod(Class<?> fontFamily) throws NoSuchMethodException {
        Method m = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, new Class[]{Array.newInstance(fontFamily, 1).getClass(), Integer.TYPE, Integer.TYPE});
        m.setAccessible(true);
        return m;
    }

    /* access modifiers changed from: package-private */
    public Typeface createWeightStyle(Context context, Typeface base, int weight, boolean italic) {
        Typeface out = null;
        try {
            out = WeightTypefaceApi26.createWeightStyle(base, weight, italic);
        } catch (RuntimeException e) {
        }
        if (out == null) {
            return super.createWeightStyle(context, base, weight, italic);
        }
        return out;
    }
}
