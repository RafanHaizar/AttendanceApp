package com.google.android.material.textview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.C1087R;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialTextView extends AppCompatTextView {
    public MaterialTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public MaterialTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, 0), attrs, defStyleAttr);
        initialize(attrs, defStyleAttr, 0);
    }

    @Deprecated
    public MaterialTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, defStyleRes), attrs, defStyleAttr);
        initialize(attrs, defStyleAttr, defStyleRes);
    }

    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        if (canApplyTextAppearanceLineHeight(context)) {
            applyLineHeightFromViewAppearance(context.getTheme(), resId);
        }
    }

    private void initialize(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        int resId;
        Context context = getContext();
        if (canApplyTextAppearanceLineHeight(context)) {
            Resources.Theme theme = context.getTheme();
            if (!viewAttrsHasLineHeight(context, theme, attrs, defStyleAttr, defStyleRes) && (resId = findViewAppearanceResourceId(theme, attrs, defStyleAttr, defStyleRes)) != -1) {
                applyLineHeightFromViewAppearance(theme, resId);
            }
        }
    }

    private void applyLineHeightFromViewAppearance(Resources.Theme theme, int resId) {
        TypedArray attributes = theme.obtainStyledAttributes(resId, C1087R.styleable.MaterialTextAppearance);
        int lineHeight = readFirstAvailableDimension(getContext(), attributes, C1087R.styleable.MaterialTextAppearance_android_lineHeight, C1087R.styleable.MaterialTextAppearance_lineHeight);
        attributes.recycle();
        if (lineHeight >= 0) {
            setLineHeight(lineHeight);
        }
    }

    private static boolean canApplyTextAppearanceLineHeight(Context context) {
        return MaterialAttributes.resolveBoolean(context, C1087R.attr.textAppearanceLineHeightEnabled, true);
    }

    private static int readFirstAvailableDimension(Context context, TypedArray attributes, int... indices) {
        int lineHeight = -1;
        for (int index = 0; index < indices.length && lineHeight < 0; index++) {
            lineHeight = MaterialResources.getDimensionPixelSize(context, attributes, indices[index], -1);
        }
        return lineHeight;
    }

    private static boolean viewAttrsHasLineHeight(Context context, Resources.Theme theme, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray attributes = theme.obtainStyledAttributes(attrs, C1087R.styleable.MaterialTextView, defStyleAttr, defStyleRes);
        int lineHeight = readFirstAvailableDimension(context, attributes, C1087R.styleable.MaterialTextView_android_lineHeight, C1087R.styleable.MaterialTextView_lineHeight);
        attributes.recycle();
        if (lineHeight != -1) {
            return true;
        }
        return false;
    }

    private static int findViewAppearanceResourceId(Resources.Theme theme, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray attributes = theme.obtainStyledAttributes(attrs, C1087R.styleable.MaterialTextView, defStyleAttr, defStyleRes);
        int appearanceAttrId = attributes.getResourceId(C1087R.styleable.MaterialTextView_android_textAppearance, -1);
        attributes.recycle();
        return appearanceAttrId;
    }
}
