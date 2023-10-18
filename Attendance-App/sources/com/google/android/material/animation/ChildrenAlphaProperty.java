package com.google.android.material.animation;

import android.util.Property;
import android.view.ViewGroup;
import com.google.android.material.C1087R;

public class ChildrenAlphaProperty extends Property<ViewGroup, Float> {
    public static final Property<ViewGroup, Float> CHILDREN_ALPHA = new ChildrenAlphaProperty("childrenAlpha");

    private ChildrenAlphaProperty(String name) {
        super(Float.class, name);
    }

    public Float get(ViewGroup object) {
        Float alpha = (Float) object.getTag(C1087R.C1090id.mtrl_internal_children_alpha_tag);
        if (alpha != null) {
            return alpha;
        }
        return Float.valueOf(1.0f);
    }

    public void set(ViewGroup object, Float value) {
        float alpha = value.floatValue();
        object.setTag(C1087R.C1090id.mtrl_internal_children_alpha_tag, Float.valueOf(alpha));
        int count = object.getChildCount();
        for (int i = 0; i < count; i++) {
            object.getChildAt(i).setAlpha(alpha);
        }
    }
}
