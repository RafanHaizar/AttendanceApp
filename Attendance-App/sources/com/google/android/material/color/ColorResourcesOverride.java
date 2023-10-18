package com.google.android.material.color;

import android.content.Context;
import android.os.Build;
import androidx.core.p001os.BuildCompat;
import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Map;

interface ColorResourcesOverride {
    boolean applyIfPossible(Context context, Map<Integer, Integer> map);

    @SynthesizedClassV2(kind = 8, versionHash = "ea87655719898b9807d7a88878e9de051d12af172d2fab563c9881b5e404e7d4")
    /* renamed from: com.google.android.material.color.ColorResourcesOverride$-CC  reason: invalid class name */
    public final /* synthetic */ class CC {
        public static ColorResourcesOverride getInstance() {
            if (30 <= Build.VERSION.SDK_INT && Build.VERSION.SDK_INT <= 33) {
                return ResourcesLoaderColorResourcesOverride.getInstance();
            }
            if (BuildCompat.isAtLeastU()) {
                return ResourcesLoaderColorResourcesOverride.getInstance();
            }
            return null;
        }
    }
}
