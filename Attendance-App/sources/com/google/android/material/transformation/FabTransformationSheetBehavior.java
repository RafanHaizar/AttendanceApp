package com.google.android.material.transformation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.Positioning;
import com.google.android.material.transformation.FabTransformationBehavior;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class FabTransformationSheetBehavior extends FabTransformationBehavior {
    private Map<View, Integer> importantForAccessibilityMap;

    public FabTransformationSheetBehavior() {
    }

    public FabTransformationSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public FabTransformationBehavior.FabTransformationSpec onCreateMotionSpec(Context context, boolean expanded) {
        int specRes;
        if (expanded) {
            specRes = C1087R.animator.mtrl_fab_transformation_sheet_expand_spec;
        } else {
            specRes = C1087R.animator.mtrl_fab_transformation_sheet_collapse_spec;
        }
        FabTransformationBehavior.FabTransformationSpec spec = new FabTransformationBehavior.FabTransformationSpec();
        spec.timings = MotionSpec.createFromResource(context, specRes);
        spec.positioning = new Positioning(17, 0.0f, 0.0f);
        return spec;
    }

    /* access modifiers changed from: protected */
    public boolean onExpandedStateChange(View dependency, View child, boolean expanded, boolean animated) {
        updateImportantForAccessibility(child, expanded);
        return super.onExpandedStateChange(dependency, child, expanded, animated);
    }

    private void updateImportantForAccessibility(View sheet, boolean expanded) {
        ViewParent viewParent = sheet.getParent();
        if (viewParent instanceof CoordinatorLayout) {
            CoordinatorLayout parent = (CoordinatorLayout) viewParent;
            int childCount = parent.getChildCount();
            if (expanded) {
                this.importantForAccessibilityMap = new HashMap(childCount);
            }
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                boolean hasScrimBehavior = (child.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) && (((CoordinatorLayout.LayoutParams) child.getLayoutParams()).getBehavior() instanceof FabTransformationScrimBehavior);
                if (child != sheet && !hasScrimBehavior) {
                    if (!expanded) {
                        Map<View, Integer> map = this.importantForAccessibilityMap;
                        if (map != null && map.containsKey(child)) {
                            ViewCompat.setImportantForAccessibility(child, this.importantForAccessibilityMap.get(child).intValue());
                        }
                    } else {
                        this.importantForAccessibilityMap.put(child, Integer.valueOf(child.getImportantForAccessibility()));
                        ViewCompat.setImportantForAccessibility(child, 4);
                    }
                }
            }
            if (!expanded) {
                this.importantForAccessibilityMap = null;
            }
        }
    }
}
