package androidx.core.p001os;

import android.os.Build;
import android.os.LocaleList;
import androidx.core.text.ICUCompat;
import java.util.Locale;

/* renamed from: androidx.core.os.LocaleListCompat */
public final class LocaleListCompat {
    private static final LocaleListCompat sEmptyLocaleList = create(new Locale[0]);
    private final LocaleListInterface mImpl;

    private LocaleListCompat(LocaleListInterface impl) {
        this.mImpl = impl;
    }

    @Deprecated
    public static LocaleListCompat wrap(Object localeList) {
        return wrap((LocaleList) localeList);
    }

    public static LocaleListCompat wrap(LocaleList localeList) {
        return new LocaleListCompat(new LocaleListPlatformWrapper(localeList));
    }

    public Object unwrap() {
        return this.mImpl.getLocaleList();
    }

    public static LocaleListCompat create(Locale... localeList) {
        if (Build.VERSION.SDK_INT >= 24) {
            return wrap(Api24Impl.createLocaleList(localeList));
        }
        return new LocaleListCompat(new LocaleListCompatWrapper(localeList));
    }

    public Locale get(int index) {
        return this.mImpl.get(index);
    }

    public boolean isEmpty() {
        return this.mImpl.isEmpty();
    }

    public int size() {
        return this.mImpl.size();
    }

    public int indexOf(Locale locale) {
        return this.mImpl.indexOf(locale);
    }

    public String toLanguageTags() {
        return this.mImpl.toLanguageTags();
    }

    public Locale getFirstMatch(String[] supportedLocales) {
        return this.mImpl.getFirstMatch(supportedLocales);
    }

    public static LocaleListCompat getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    public static LocaleListCompat forLanguageTags(String list) {
        Locale locale;
        if (list == null || list.isEmpty()) {
            return getEmptyLocaleList();
        }
        String[] tags = list.split(",", -1);
        Locale[] localeArray = new Locale[tags.length];
        for (int i = 0; i < localeArray.length; i++) {
            if (Build.VERSION.SDK_INT >= 21) {
                locale = Api21Impl.forLanguageTag(tags[i]);
            } else {
                locale = forLanguageTagCompat(tags[i]);
            }
            localeArray[i] = locale;
        }
        return create(localeArray);
    }

    static Locale forLanguageTagCompat(String str) {
        if (str.contains("-")) {
            String[] args = str.split("-", -1);
            if (args.length > 2) {
                return new Locale(args[0], args[1], args[2]);
            }
            if (args.length > 1) {
                return new Locale(args[0], args[1]);
            }
            if (args.length == 1) {
                return new Locale(args[0]);
            }
        } else if (!str.contains("_")) {
            return new Locale(str);
        } else {
            String[] args2 = str.split("_", -1);
            if (args2.length > 2) {
                return new Locale(args2[0], args2[1], args2[2]);
            }
            if (args2.length > 1) {
                return new Locale(args2[0], args2[1]);
            }
            if (args2.length == 1) {
                return new Locale(args2[0]);
            }
        }
        throw new IllegalArgumentException("Can not parse language tag: [" + str + "]");
    }

    public static LocaleListCompat getAdjustedDefault() {
        if (Build.VERSION.SDK_INT >= 24) {
            return wrap(Api24Impl.getAdjustedDefault());
        }
        return create(Locale.getDefault());
    }

    public static LocaleListCompat getDefault() {
        if (Build.VERSION.SDK_INT >= 24) {
            return wrap(Api24Impl.getDefault());
        }
        return create(Locale.getDefault());
    }

    public static boolean matchesLanguageAndScript(Locale supported, Locale desired) {
        if (BuildCompat.isAtLeastT()) {
            return LocaleList.matchesLanguageAndScript(supported, desired);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.matchesLanguageAndScript(supported, desired);
        }
        throw new UnsupportedOperationException("This method is only supported on API level 21+");
    }

    /* renamed from: androidx.core.os.LocaleListCompat$Api21Impl */
    static class Api21Impl {
        private static final Locale[] PSEUDO_LOCALE = {new Locale("en", "XA"), new Locale("ar", "XB")};

        private Api21Impl() {
        }

        static boolean matchesLanguageAndScript(Locale supported, Locale desired) {
            if (supported.equals(desired)) {
                return true;
            }
            if (!supported.getLanguage().equals(desired.getLanguage()) || isPseudoLocale(supported) || isPseudoLocale(desired)) {
                return false;
            }
            String supportedScr = ICUCompat.maximizeAndGetScript(supported);
            if (!supportedScr.isEmpty()) {
                return supportedScr.equals(ICUCompat.maximizeAndGetScript(desired));
            }
            String supportedRegion = supported.getCountry();
            if (supportedRegion.isEmpty() || supportedRegion.equals(desired.getCountry())) {
                return true;
            }
            return false;
        }

        private static boolean isPseudoLocale(Locale locale) {
            for (Locale pseudoLocale : PSEUDO_LOCALE) {
                if (pseudoLocale.equals(locale)) {
                    return true;
                }
            }
            return false;
        }

        static Locale forLanguageTag(String languageTag) {
            return Locale.forLanguageTag(languageTag);
        }
    }

    public boolean equals(Object other) {
        return (other instanceof LocaleListCompat) && this.mImpl.equals(((LocaleListCompat) other).mImpl);
    }

    public int hashCode() {
        return this.mImpl.hashCode();
    }

    public String toString() {
        return this.mImpl.toString();
    }

    /* renamed from: androidx.core.os.LocaleListCompat$Api24Impl */
    static class Api24Impl {
        private Api24Impl() {
        }

        static LocaleList createLocaleList(Locale... list) {
            return new LocaleList(list);
        }

        static LocaleList getAdjustedDefault() {
            return LocaleList.getAdjustedDefault();
        }

        static LocaleList getDefault() {
            return LocaleList.getDefault();
        }
    }
}
