package androidx.appcompat.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import androidx.appcompat.C0503R;

public class ContextThemeWrapper extends ContextWrapper {
    private static Configuration sEmptyConfig;
    private LayoutInflater mInflater;
    private Configuration mOverrideConfiguration;
    private Resources mResources;
    private Resources.Theme mTheme;
    private int mThemeResource;

    public ContextThemeWrapper() {
        super((Context) null);
    }

    public ContextThemeWrapper(Context base, int themeResId) {
        super(base);
        this.mThemeResource = themeResId;
    }

    public ContextThemeWrapper(Context base, Resources.Theme theme) {
        super(base);
        this.mTheme = theme;
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (this.mResources != null) {
            throw new IllegalStateException("getResources() or getAssets() has already been called");
        } else if (this.mOverrideConfiguration == null) {
            this.mOverrideConfiguration = new Configuration(overrideConfiguration);
        } else {
            throw new IllegalStateException("Override configuration has already been set");
        }
    }

    public Resources getResources() {
        return getResourcesInternal();
    }

    private Resources getResourcesInternal() {
        if (this.mResources == null) {
            if (this.mOverrideConfiguration == null || (Build.VERSION.SDK_INT >= 26 && isEmptyConfiguration(this.mOverrideConfiguration))) {
                this.mResources = super.getResources();
            } else {
                this.mResources = Api17Impl.createConfigurationContext(this, this.mOverrideConfiguration).getResources();
            }
        }
        return this.mResources;
    }

    public void setTheme(int resid) {
        if (this.mThemeResource != resid) {
            this.mThemeResource = resid;
            initializeTheme();
        }
    }

    public int getThemeResId() {
        return this.mThemeResource;
    }

    public Resources.Theme getTheme() {
        Resources.Theme theme = this.mTheme;
        if (theme != null) {
            return theme;
        }
        if (this.mThemeResource == 0) {
            this.mThemeResource = C0503R.C0509style.Theme_AppCompat_Light;
        }
        initializeTheme();
        return this.mTheme;
    }

    public Object getSystemService(String name) {
        if (!"layout_inflater".equals(name)) {
            return getBaseContext().getSystemService(name);
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.mInflater;
    }

    /* access modifiers changed from: protected */
    public void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        theme.applyStyle(resid, true);
    }

    private void initializeTheme() {
        boolean first = this.mTheme == null;
        if (first) {
            this.mTheme = getResources().newTheme();
            Resources.Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.mTheme.setTo(theme);
            }
        }
        onApplyThemeResource(this.mTheme, this.mThemeResource, first);
    }

    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    private static boolean isEmptyConfiguration(Configuration overrideConfiguration) {
        if (overrideConfiguration == null) {
            return true;
        }
        if (sEmptyConfig == null) {
            Configuration emptyConfig = new Configuration();
            emptyConfig.fontScale = 0.0f;
            sEmptyConfig = emptyConfig;
        }
        return overrideConfiguration.equals(sEmptyConfig);
    }

    static class Api17Impl {
        private Api17Impl() {
        }

        static Context createConfigurationContext(ContextThemeWrapper contextThemeWrapper, Configuration overrideConfiguration) {
            return contextThemeWrapper.createConfigurationContext(overrideConfiguration);
        }
    }
}
