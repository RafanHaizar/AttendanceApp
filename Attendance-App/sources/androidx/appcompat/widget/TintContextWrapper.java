package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TintContextWrapper extends ContextWrapper {
    private static final Object CACHE_LOCK = new Object();
    private static ArrayList<WeakReference<TintContextWrapper>> sCache;
    private final Resources mResources;
    private final Resources.Theme mTheme;

    public static Context wrap(Context context) {
        if (!shouldWrap(context)) {
            return context;
        }
        synchronized (CACHE_LOCK) {
            ArrayList<WeakReference<TintContextWrapper>> arrayList = sCache;
            if (arrayList == null) {
                sCache = new ArrayList<>();
            } else {
                for (int i = arrayList.size() - 1; i >= 0; i--) {
                    WeakReference<TintContextWrapper> ref = sCache.get(i);
                    if (ref == null || ref.get() == null) {
                        sCache.remove(i);
                    }
                }
                for (int i2 = sCache.size() - 1; i2 >= 0; i2--) {
                    WeakReference<TintContextWrapper> ref2 = sCache.get(i2);
                    TintContextWrapper wrapper = ref2 != null ? (TintContextWrapper) ref2.get() : null;
                    if (wrapper != null && wrapper.getBaseContext() == context) {
                        return wrapper;
                    }
                }
            }
            TintContextWrapper wrapper2 = new TintContextWrapper(context);
            sCache.add(new WeakReference(wrapper2));
            return wrapper2;
        }
    }

    private static boolean shouldWrap(Context context) {
        if ((context instanceof TintContextWrapper) || (context.getResources() instanceof TintResources) || (context.getResources() instanceof VectorEnabledTintResources)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 21 || VectorEnabledTintResources.shouldBeUsed()) {
            return true;
        }
        return false;
    }

    private TintContextWrapper(Context base) {
        super(base);
        if (VectorEnabledTintResources.shouldBeUsed()) {
            VectorEnabledTintResources vectorEnabledTintResources = new VectorEnabledTintResources(this, base.getResources());
            this.mResources = vectorEnabledTintResources;
            Resources.Theme newTheme = vectorEnabledTintResources.newTheme();
            this.mTheme = newTheme;
            newTheme.setTo(base.getTheme());
            return;
        }
        this.mResources = new TintResources(this, base.getResources());
        this.mTheme = null;
    }

    public Resources.Theme getTheme() {
        Resources.Theme theme = this.mTheme;
        return theme == null ? super.getTheme() : theme;
    }

    public void setTheme(int resid) {
        Resources.Theme theme = this.mTheme;
        if (theme == null) {
            super.setTheme(resid);
        } else {
            theme.applyStyle(resid, true);
        }
    }

    public Resources getResources() {
        return this.mResources;
    }

    public AssetManager getAssets() {
        return this.mResources.getAssets();
    }
}
