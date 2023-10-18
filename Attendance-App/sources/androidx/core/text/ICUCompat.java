package androidx.core.text;

import android.icu.util.ULocale;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public final class ICUCompat {
    private static final String TAG = "ICUCompat";
    private static Method sAddLikelySubtagsMethod;
    private static Method sGetScriptMethod;

    static {
        if (Build.VERSION.SDK_INT < 21) {
            try {
                Class<?> clazz = Class.forName("libcore.icu.ICU");
                sGetScriptMethod = clazz.getMethod("getScript", new Class[]{String.class});
                sAddLikelySubtagsMethod = clazz.getMethod("addLikelySubtags", new Class[]{String.class});
            } catch (Exception e) {
                sGetScriptMethod = null;
                sAddLikelySubtagsMethod = null;
                Log.w(TAG, e);
            }
        } else if (Build.VERSION.SDK_INT < 24) {
            try {
                sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[]{Locale.class});
            } catch (Exception e2) {
                throw new IllegalStateException(e2);
            }
        }
    }

    public static String maximizeAndGetScript(Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.getScript(Api24Impl.addLikelySubtags(Api24Impl.forLocale(locale)));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                return Api21Impl.getScript((Locale) sAddLikelySubtagsMethod.invoke((Object) null, new Object[]{locale}));
            } catch (InvocationTargetException e) {
                Log.w(TAG, e);
                return Api21Impl.getScript(locale);
            } catch (IllegalAccessException e2) {
                Log.w(TAG, e2);
                return Api21Impl.getScript(locale);
            }
        } else {
            String localeWithSubtags = addLikelySubtagsBelowApi21(locale);
            if (localeWithSubtags != null) {
                return getScriptBelowApi21(localeWithSubtags);
            }
            return null;
        }
    }

    private static String getScriptBelowApi21(String localeStr) {
        try {
            Method method = sGetScriptMethod;
            if (method != null) {
                return (String) method.invoke((Object) null, new Object[]{localeStr});
            }
        } catch (IllegalAccessException e) {
            Log.w(TAG, e);
        } catch (InvocationTargetException e2) {
            Log.w(TAG, e2);
        }
        return null;
    }

    private static String addLikelySubtagsBelowApi21(Locale locale) {
        String localeStr = locale.toString();
        try {
            Method method = sAddLikelySubtagsMethod;
            if (method != null) {
                return (String) method.invoke((Object) null, new Object[]{localeStr});
            }
        } catch (IllegalAccessException e) {
            Log.w(TAG, e);
        } catch (InvocationTargetException e2) {
            Log.w(TAG, e2);
        }
        return localeStr;
    }

    private ICUCompat() {
    }

    static class Api24Impl {
        private Api24Impl() {
        }

        static ULocale forLocale(Locale loc) {
            return ULocale.forLocale(loc);
        }

        static ULocale addLikelySubtags(Object loc) {
            return ULocale.addLikelySubtags((ULocale) loc);
        }

        static String getScript(Object uLocale) {
            return ((ULocale) uLocale).getScript();
        }
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static String getScript(Locale locale) {
            return locale.getScript();
        }
    }
}
