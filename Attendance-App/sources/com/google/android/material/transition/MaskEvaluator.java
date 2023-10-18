package com.google.android.material.transition;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.transition.MaterialContainerTransform;

class MaskEvaluator {
    private ShapeAppearanceModel currentShapeAppearanceModel;
    private final Path endPath = new Path();
    private final Path path = new Path();
    private final ShapeAppearancePathProvider pathProvider = ShapeAppearancePathProvider.getInstance();
    private final Path startPath = new Path();

    MaskEvaluator() {
    }

    /* access modifiers changed from: package-private */
    public void evaluate(float progress, ShapeAppearanceModel startShapeAppearanceModel, ShapeAppearanceModel endShapeAppearanceModel, RectF currentStartBounds, RectF currentStartBoundsMasked, RectF currentEndBoundsMasked, MaterialContainerTransform.ProgressThresholds shapeMaskThresholds) {
        ShapeAppearanceModel lerp = TransitionUtils.lerp(startShapeAppearanceModel, endShapeAppearanceModel, currentStartBounds, currentEndBoundsMasked, shapeMaskThresholds.getStart(), shapeMaskThresholds.getEnd(), progress);
        this.currentShapeAppearanceModel = lerp;
        RectF rectF = currentStartBoundsMasked;
        this.pathProvider.calculatePath(lerp, 1.0f, currentStartBoundsMasked, this.startPath);
        this.pathProvider.calculatePath(this.currentShapeAppearanceModel, 1.0f, currentEndBoundsMasked, this.endPath);
        if (Build.VERSION.SDK_INT >= 23) {
            this.path.op(this.startPath, this.endPath, Path.Op.UNION);
        }
    }

    /* access modifiers changed from: package-private */
    public void clip(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= 23) {
            canvas.clipPath(this.path);
            return;
        }
        canvas.clipPath(this.startPath);
        canvas.clipPath(this.endPath, Region.Op.UNION);
    }

    /* access modifiers changed from: package-private */
    public Path getPath() {
        return this.path;
    }

    /* access modifiers changed from: package-private */
    public ShapeAppearanceModel getCurrentShapeAppearanceModel() {
        return this.currentShapeAppearanceModel;
    }
}
