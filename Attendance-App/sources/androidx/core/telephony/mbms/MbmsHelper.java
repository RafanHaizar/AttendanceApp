package androidx.core.telephony.mbms;

import android.content.Context;
import android.os.Build;
import android.telephony.mbms.ServiceInfo;
import java.util.Locale;
import java.util.Set;

public final class MbmsHelper {
    private MbmsHelper() {
    }

    public static CharSequence getBestNameForService(Context context, ServiceInfo serviceInfo) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getBestNameForService(context, serviceInfo);
        }
        return null;
    }

    static class Api28Impl {
        private Api28Impl() {
        }

        static CharSequence getBestNameForService(Context context, ServiceInfo serviceInfo) {
            Set<Locale> namedContentLocales = serviceInfo.getNamedContentLocales();
            if (namedContentLocales.isEmpty()) {
                return null;
            }
            String[] supportedLanguages = new String[namedContentLocales.size()];
            int i = 0;
            for (Locale l : serviceInfo.getNamedContentLocales()) {
                supportedLanguages[i] = l.toLanguageTag();
                i++;
            }
            Locale bestLocale = context.getResources().getConfiguration().getLocales().getFirstMatch(supportedLanguages);
            if (bestLocale == null) {
                return null;
            }
            return serviceInfo.getNameForLocale(bestLocale);
        }
    }
}
