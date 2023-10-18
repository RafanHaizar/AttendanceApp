package com.google.android.material.color;

import com.google.android.material.C1087R;
import com.google.android.material.color.utilities.Scheme;
import java.util.HashMap;
import java.util.Map;

final class MaterialColorUtilitiesHelper {
    private MaterialColorUtilitiesHelper() {
    }

    static Map<Integer, Integer> createColorResourcesIdsToColorValues(Scheme colorScheme) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_primary), Integer.valueOf(colorScheme.getPrimary()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_primary), Integer.valueOf(colorScheme.getOnPrimary()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_primary_inverse), Integer.valueOf(colorScheme.getInversePrimary()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_primary_container), Integer.valueOf(colorScheme.getPrimaryContainer()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_primary_container), Integer.valueOf(colorScheme.getOnPrimaryContainer()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_secondary), Integer.valueOf(colorScheme.getSecondary()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_secondary), Integer.valueOf(colorScheme.getOnSecondary()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_secondary_container), Integer.valueOf(colorScheme.getSecondaryContainer()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_secondary_container), Integer.valueOf(colorScheme.getOnSecondaryContainer()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_tertiary), Integer.valueOf(colorScheme.getTertiary()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_tertiary), Integer.valueOf(colorScheme.getOnTertiary()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_tertiary_container), Integer.valueOf(colorScheme.getTertiaryContainer()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_tertiary_container), Integer.valueOf(colorScheme.getOnTertiaryContainer()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_background), Integer.valueOf(colorScheme.getBackground()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_background), Integer.valueOf(colorScheme.getOnBackground()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_surface), Integer.valueOf(colorScheme.getSurface()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_surface), Integer.valueOf(colorScheme.getOnSurface()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_surface_variant), Integer.valueOf(colorScheme.getSurfaceVariant()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_surface_variant), Integer.valueOf(colorScheme.getOnSurfaceVariant()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_surface_inverse), Integer.valueOf(colorScheme.getInverseSurface()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_surface_inverse), Integer.valueOf(colorScheme.getInverseOnSurface()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_surface_outline), Integer.valueOf(colorScheme.getOutline()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_error), Integer.valueOf(colorScheme.getError()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_error), Integer.valueOf(colorScheme.getOnError()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_error_container), Integer.valueOf(colorScheme.getErrorContainer()));
        map.put(Integer.valueOf(C1087R.C1088color.material_personalized_color_on_error_container), Integer.valueOf(colorScheme.getOnErrorContainer()));
        return map;
    }
}
