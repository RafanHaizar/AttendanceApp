package androidx.core.p001os;

import android.os.LocaleList;
import java.util.Locale;

/* renamed from: androidx.core.os.LocaleListPlatformWrapper */
final class LocaleListPlatformWrapper implements LocaleListInterface {
    private final LocaleList mLocaleList;

    LocaleListPlatformWrapper(Object localeList) {
        this.mLocaleList = (LocaleList) localeList;
    }

    public Object getLocaleList() {
        return this.mLocaleList;
    }

    public Locale get(int index) {
        return this.mLocaleList.get(index);
    }

    public boolean isEmpty() {
        return this.mLocaleList.isEmpty();
    }

    public int size() {
        return this.mLocaleList.size();
    }

    public int indexOf(Locale locale) {
        return this.mLocaleList.indexOf(locale);
    }

    public boolean equals(Object other) {
        return this.mLocaleList.equals(((LocaleListInterface) other).getLocaleList());
    }

    public int hashCode() {
        return this.mLocaleList.hashCode();
    }

    public String toString() {
        return this.mLocaleList.toString();
    }

    public String toLanguageTags() {
        return this.mLocaleList.toLanguageTags();
    }

    public Locale getFirstMatch(String[] supportedLocales) {
        return this.mLocaleList.getFirstMatch(supportedLocales);
    }
}
