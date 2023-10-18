package com.google.android.material.color;

import android.content.Context;
import android.content.res.loader.ResourcesLoader;
import java.util.Map;

final class ResourcesLoaderUtils {
    private ResourcesLoaderUtils() {
    }

    static boolean addResourcesLoaderToContext(Context context, Map<Integer, Integer> colorReplacementMap) {
        ResourcesLoader resourcesLoader = ColorResourcesLoaderCreator.create(context, colorReplacementMap);
        if (resourcesLoader == null) {
            return false;
        }
        context.getResources().addLoaders(new ResourcesLoader[]{resourcesLoader});
        return true;
    }

    static boolean isColorResource(int attrType) {
        return 28 <= attrType && attrType <= 31;
    }
}
