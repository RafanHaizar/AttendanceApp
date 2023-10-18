package androidx.core.content;

import android.content.ContentProvider;
import android.content.Context;

public final class ContentProviderCompat {
    private ContentProviderCompat() {
    }

    public static Context requireContext(ContentProvider provider) {
        Context ctx = provider.getContext();
        if (ctx != null) {
            return ctx;
        }
        throw new IllegalStateException("Cannot find context from the provider.");
    }
}
