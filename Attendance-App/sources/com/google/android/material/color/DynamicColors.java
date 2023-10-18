package com.google.android.material.color;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import androidx.core.p001os.BuildCompat;
import com.google.android.material.C1087R;
import com.google.android.material.color.ColorResourcesOverride;
import com.google.android.material.color.DynamicColorsOptions;
import com.google.android.material.color.utilities.Scheme;
import com.google.android.material.resources.MaterialAttributes;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DynamicColors {
    private static final DeviceSupportCondition DEFAULT_DEVICE_SUPPORT_CONDITION;
    private static final Map<String, DeviceSupportCondition> DYNAMIC_COLOR_SUPPORTED_BRANDS;
    private static final Map<String, DeviceSupportCondition> DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS;
    private static final int[] DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE = {C1087R.attr.dynamicColorThemeOverlay};
    private static final DeviceSupportCondition SAMSUNG_DEVICE_SUPPORT_CONDITION;
    private static final int USE_DEFAULT_THEME_OVERLAY = 0;

    private interface DeviceSupportCondition {
        boolean isSupported();
    }

    public interface OnAppliedCallback {
        void onApplied(Activity activity);
    }

    public interface Precondition {
        boolean shouldApplyDynamicColors(Activity activity, int i);
    }

    static {
        C11521 r0 = new DeviceSupportCondition() {
            public boolean isSupported() {
                return true;
            }
        };
        DEFAULT_DEVICE_SUPPORT_CONDITION = r0;
        C11532 r1 = new DeviceSupportCondition() {
            private Long version;

            public boolean isSupported() {
                if (this.version == null) {
                    try {
                        Method method = Build.class.getDeclaredMethod("getLong", new Class[]{String.class});
                        method.setAccessible(true);
                        this.version = Long.valueOf(((Long) method.invoke((Object) null, new Object[]{"ro.build.version.oneui"})).longValue());
                    } catch (Exception e) {
                        this.version = -1L;
                    }
                }
                if (this.version.longValue() >= 40100) {
                    return true;
                }
                return false;
            }
        };
        SAMSUNG_DEVICE_SUPPORT_CONDITION = r1;
        Map<String, DeviceSupportCondition> deviceMap = new HashMap<>();
        deviceMap.put("fcnt", r0);
        deviceMap.put("google", r0);
        deviceMap.put("hmd global", r0);
        deviceMap.put("infinix", r0);
        deviceMap.put("infinix mobility limited", r0);
        deviceMap.put("itel", r0);
        deviceMap.put("kyocera", r0);
        deviceMap.put("lenovo", r0);
        deviceMap.put("lge", r0);
        deviceMap.put("motorola", r0);
        deviceMap.put("nothing", r0);
        deviceMap.put("oneplus", r0);
        deviceMap.put("oppo", r0);
        deviceMap.put("realme", r0);
        deviceMap.put("robolectric", r0);
        deviceMap.put("samsung", r1);
        deviceMap.put("sharp", r0);
        deviceMap.put("sony", r0);
        deviceMap.put("tcl", r0);
        deviceMap.put("tecno", r0);
        deviceMap.put("tecno mobile limited", r0);
        deviceMap.put("vivo", r0);
        deviceMap.put("wingtech", r0);
        deviceMap.put("xiaomi", r0);
        DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS = Collections.unmodifiableMap(deviceMap);
        Map<String, DeviceSupportCondition> deviceMap2 = new HashMap<>();
        deviceMap2.put("asus", r0);
        deviceMap2.put("jio", r0);
        DYNAMIC_COLOR_SUPPORTED_BRANDS = Collections.unmodifiableMap(deviceMap2);
    }

    private DynamicColors() {
    }

    public static void applyToActivitiesIfAvailable(Application application) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().build());
    }

    @Deprecated
    public static void applyToActivitiesIfAvailable(Application application, int theme) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().setThemeOverlay(theme).build());
    }

    @Deprecated
    public static void applyToActivitiesIfAvailable(Application application, Precondition precondition) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().setPrecondition(precondition).build());
    }

    @Deprecated
    public static void applyToActivitiesIfAvailable(Application application, int theme, Precondition precondition) {
        applyToActivitiesIfAvailable(application, new DynamicColorsOptions.Builder().setThemeOverlay(theme).setPrecondition(precondition).build());
    }

    public static void applyToActivitiesIfAvailable(Application application, DynamicColorsOptions dynamicColorsOptions) {
        application.registerActivityLifecycleCallbacks(new DynamicColorsActivityLifecycleCallbacks(dynamicColorsOptions));
    }

    @Deprecated
    public static void applyIfAvailable(Activity activity) {
        applyToActivityIfAvailable(activity);
    }

    @Deprecated
    public static void applyIfAvailable(Activity activity, int theme) {
        applyToActivityIfAvailable(activity, new DynamicColorsOptions.Builder().setThemeOverlay(theme).build());
    }

    @Deprecated
    public static void applyIfAvailable(Activity activity, Precondition precondition) {
        applyToActivityIfAvailable(activity, new DynamicColorsOptions.Builder().setPrecondition(precondition).build());
    }

    public static void applyToActivityIfAvailable(Activity activity) {
        applyToActivityIfAvailable(activity, new DynamicColorsOptions.Builder().build());
    }

    public static void applyToActivityIfAvailable(Activity activity, DynamicColorsOptions dynamicColorsOptions) {
        Scheme colorScheme;
        int i;
        if (isDynamicColorAvailable()) {
            int theme = 0;
            if (dynamicColorsOptions.getContentBasedSeedColor() == null) {
                if (dynamicColorsOptions.getThemeOverlay() == 0) {
                    i = getDefaultThemeOverlay(activity);
                } else {
                    i = dynamicColorsOptions.getThemeOverlay();
                }
                theme = i;
            }
            if (dynamicColorsOptions.getPrecondition().shouldApplyDynamicColors(activity, theme)) {
                if (dynamicColorsOptions.getContentBasedSeedColor() != null) {
                    if (MaterialAttributes.resolveBoolean(activity, C1087R.attr.isLightTheme, true)) {
                        colorScheme = Scheme.lightContent(dynamicColorsOptions.getContentBasedSeedColor().intValue());
                    } else {
                        colorScheme = Scheme.darkContent(dynamicColorsOptions.getContentBasedSeedColor().intValue());
                    }
                    ColorResourcesOverride resourcesOverride = ColorResourcesOverride.CC.getInstance();
                    if (resourcesOverride == null || !resourcesOverride.applyIfPossible(activity, MaterialColorUtilitiesHelper.createColorResourcesIdsToColorValues(colorScheme))) {
                        return;
                    }
                } else {
                    ThemeUtils.applyThemeOverlay(activity, theme);
                }
                dynamicColorsOptions.getOnAppliedCallback().onApplied(activity);
            }
        }
    }

    public static Context wrapContextIfAvailable(Context originalContext) {
        return wrapContextIfAvailable(originalContext, 0);
    }

    public static Context wrapContextIfAvailable(Context originalContext, int theme) {
        if (!isDynamicColorAvailable()) {
            return originalContext;
        }
        if (theme == 0) {
            theme = getDefaultThemeOverlay(originalContext);
        }
        return theme == 0 ? originalContext : new ContextThemeWrapper(originalContext, theme);
    }

    public static boolean isDynamicColorAvailable() {
        if (Build.VERSION.SDK_INT < 31) {
            return false;
        }
        if (BuildCompat.isAtLeastT()) {
            return true;
        }
        DeviceSupportCondition deviceSupportCondition = DYNAMIC_COLOR_SUPPORTED_MANUFACTURERS.get(Build.MANUFACTURER.toLowerCase());
        if (deviceSupportCondition == null) {
            deviceSupportCondition = DYNAMIC_COLOR_SUPPORTED_BRANDS.get(Build.BRAND.toLowerCase());
        }
        if (deviceSupportCondition == null || !deviceSupportCondition.isSupported()) {
            return false;
        }
        return true;
    }

    private static int getDefaultThemeOverlay(Context context) {
        TypedArray dynamicColorAttributes = context.obtainStyledAttributes(DYNAMIC_COLOR_THEME_OVERLAY_ATTRIBUTE);
        int theme = dynamicColorAttributes.getResourceId(0, 0);
        dynamicColorAttributes.recycle();
        return theme;
    }

    private static class DynamicColorsActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        private final DynamicColorsOptions dynamicColorsOptions;

        DynamicColorsActivityLifecycleCallbacks(DynamicColorsOptions options) {
            this.dynamicColorsOptions = options;
        }

        public void onActivityPreCreated(Activity activity, Bundle savedInstanceState) {
            DynamicColors.applyToActivityIfAvailable(activity, this.dynamicColorsOptions);
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }
}
