package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.collection.LruCache;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Preconditions;

public class TypefaceCompat {
    private static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);
    private static final TypefaceCompatBaseImpl sTypefaceCompatImpl;

    static {
        if (Build.VERSION.SDK_INT >= 29) {
            sTypefaceCompatImpl = new TypefaceCompatApi29Impl();
        } else if (Build.VERSION.SDK_INT >= 28) {
            sTypefaceCompatImpl = new TypefaceCompatApi28Impl();
        } else if (Build.VERSION.SDK_INT >= 26) {
            sTypefaceCompatImpl = new TypefaceCompatApi26Impl();
        } else if (Build.VERSION.SDK_INT >= 24 && TypefaceCompatApi24Impl.isUsable()) {
            sTypefaceCompatImpl = new TypefaceCompatApi24Impl();
        } else if (Build.VERSION.SDK_INT >= 21) {
            sTypefaceCompatImpl = new TypefaceCompatApi21Impl();
        } else {
            sTypefaceCompatImpl = new TypefaceCompatBaseImpl();
        }
    }

    private TypefaceCompat() {
    }

    public static Typeface findFromCache(Resources resources, int id, String path, int cookie, int style) {
        return sTypefaceCache.get(createResourceUid(resources, id, path, cookie, style));
    }

    @Deprecated
    public static Typeface findFromCache(Resources resources, int id, int style) {
        return findFromCache(resources, id, (String) null, 0, style);
    }

    private static String createResourceUid(Resources resources, int id, String path, int cookie, int style) {
        return resources.getResourcePackageName(id) + '-' + path + '-' + cookie + '-' + id + '-' + style;
    }

    private static Typeface getSystemFontFamily(String familyName) {
        if (familyName == null || familyName.isEmpty()) {
            return null;
        }
        Typeface typeface = Typeface.create(familyName, 0);
        Typeface defaultTypeface = Typeface.create(Typeface.DEFAULT, 0);
        if (typeface == null || typeface.equals(defaultTypeface)) {
            return null;
        }
        return typeface;
    }

    public static Typeface createFromResourcesFamilyXml(Context context, FontResourcesParserCompat.FamilyResourceEntry entry, Resources resources, int id, String path, int cookie, int style, ResourcesCompat.FontCallback fontCallback, Handler handler, boolean isRequestFromLayoutInflator) {
        Typeface typeface;
        boolean isBlocking;
        int timeout;
        FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry = entry;
        ResourcesCompat.FontCallback fontCallback2 = fontCallback;
        Handler handler2 = handler;
        if (familyResourceEntry instanceof FontResourcesParserCompat.ProviderResourceEntry) {
            FontResourcesParserCompat.ProviderResourceEntry providerEntry = (FontResourcesParserCompat.ProviderResourceEntry) familyResourceEntry;
            Typeface fontFamilyTypeface = getSystemFontFamily(providerEntry.getSystemFontFamilyName());
            if (fontFamilyTypeface != null) {
                if (fontCallback2 != null) {
                    fontCallback2.callbackSuccessAsync(fontFamilyTypeface, handler2);
                }
                return fontFamilyTypeface;
            }
            if (isRequestFromLayoutInflator) {
                isBlocking = providerEntry.getFetchStrategy() == 0;
            } else {
                isBlocking = fontCallback2 == null;
            }
            if (isRequestFromLayoutInflator) {
                timeout = providerEntry.getTimeout();
            } else {
                timeout = -1;
            }
            typeface = FontsContractCompat.requestFont(context, providerEntry.getRequest(), style, isBlocking, timeout, ResourcesCompat.FontCallback.getHandler(handler), new ResourcesCallbackAdapter(fontCallback2));
            Context context2 = context;
            Resources resources2 = resources;
            int i = style;
        } else {
            Context context3 = context;
            typeface = sTypefaceCompatImpl.createFromFontFamilyFilesResourceEntry(context, (FontResourcesParserCompat.FontFamilyFilesResourceEntry) familyResourceEntry, resources, style);
            if (fontCallback2 != null) {
                if (typeface != null) {
                    fontCallback2.callbackSuccessAsync(typeface, handler2);
                } else {
                    fontCallback2.callbackFailAsync(-3, handler2);
                }
            }
        }
        if (typeface != null) {
            sTypefaceCache.put(createResourceUid(resources, id, path, cookie, style), typeface);
        }
        return typeface;
    }

    @Deprecated
    public static Typeface createFromResourcesFamilyXml(Context context, FontResourcesParserCompat.FamilyResourceEntry entry, Resources resources, int id, int style, ResourcesCompat.FontCallback fontCallback, Handler handler, boolean isRequestFromLayoutInflator) {
        return createFromResourcesFamilyXml(context, entry, resources, id, (String) null, 0, style, fontCallback, handler, isRequestFromLayoutInflator);
    }

    public static Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int cookie, int style) {
        Typeface typeface = sTypefaceCompatImpl.createFromResourcesFontFile(context, resources, id, path, style);
        if (typeface != null) {
            sTypefaceCache.put(createResourceUid(resources, id, path, cookie, style), typeface);
        }
        return typeface;
    }

    @Deprecated
    public static Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        return createFromResourcesFontFile(context, resources, id, path, 0, style);
    }

    public static Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) {
        return sTypefaceCompatImpl.createFromFontInfo(context, cancellationSignal, fonts, style);
    }

    private static Typeface getBestFontFromFamily(Context context, Typeface typeface, int style) {
        TypefaceCompatBaseImpl typefaceCompatBaseImpl = sTypefaceCompatImpl;
        FontResourcesParserCompat.FontFamilyFilesResourceEntry families = typefaceCompatBaseImpl.getFontFamily(typeface);
        if (families == null) {
            return null;
        }
        return typefaceCompatBaseImpl.createFromFontFamilyFilesResourceEntry(context, families, context.getResources(), style);
    }

    public static Typeface create(Context context, Typeface family, int style) {
        Typeface typefaceFromFamily;
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        } else if (Build.VERSION.SDK_INT >= 21 || (typefaceFromFamily = getBestFontFromFamily(context, family, style)) == null) {
            return Typeface.create(family, style);
        } else {
            return typefaceFromFamily;
        }
    }

    public static Typeface create(Context context, Typeface family, int weight, boolean italic) {
        if (context != null) {
            Preconditions.checkArgumentInRange(weight, 1, 1000, "weight");
            if (family == null) {
                family = Typeface.DEFAULT;
            }
            return sTypefaceCompatImpl.createWeightStyle(context, family, weight, italic);
        }
        throw new IllegalArgumentException("Context cannot be null");
    }

    public static void clearCache() {
        sTypefaceCache.evictAll();
    }

    public static class ResourcesCallbackAdapter extends FontsContractCompat.FontRequestCallback {
        private ResourcesCompat.FontCallback mFontCallback;

        public ResourcesCallbackAdapter(ResourcesCompat.FontCallback fontCallback) {
            this.mFontCallback = fontCallback;
        }

        public void onTypefaceRetrieved(Typeface typeface) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.mo15586x46c88379(typeface);
            }
        }

        public void onTypefaceRequestFailed(int reason) {
            ResourcesCompat.FontCallback fontCallback = this.mFontCallback;
            if (fontCallback != null) {
                fontCallback.mo15585xb24343b7(reason);
            }
        }
    }
}
