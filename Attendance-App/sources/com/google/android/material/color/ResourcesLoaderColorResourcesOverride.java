package com.google.android.material.color;

import android.content.Context;
import com.google.android.material.C1087R;
import java.util.Map;

class ResourcesLoaderColorResourcesOverride implements ColorResourcesOverride {
    private ResourcesLoaderColorResourcesOverride() {
    }

    public boolean applyIfPossible(Context context, Map<Integer, Integer> colorResourceIdsToColorValues) {
        if (!ResourcesLoaderUtils.addResourcesLoaderToContext(context, colorResourceIdsToColorValues)) {
            return false;
        }
        ThemeUtils.applyThemeOverlay(context, C1087R.C1093style.ThemeOverlay_Material3_PersonalizedColors);
        return true;
    }

    static ColorResourcesOverride getInstance() {
        return ResourcesLoaderColorResourcesOverrideSingleton.INSTANCE;
    }

    private static class ResourcesLoaderColorResourcesOverrideSingleton {
        /* access modifiers changed from: private */
        public static final ResourcesLoaderColorResourcesOverride INSTANCE = new ResourcesLoaderColorResourcesOverride();

        private ResourcesLoaderColorResourcesOverrideSingleton() {
        }
    }
}
