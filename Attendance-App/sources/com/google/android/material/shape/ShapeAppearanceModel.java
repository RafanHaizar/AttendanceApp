package com.google.android.material.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import com.google.android.material.C1087R;

public class ShapeAppearanceModel {
    public static final CornerSize PILL = new RelativeCornerSize(0.5f);
    EdgeTreatment bottomEdge;
    CornerTreatment bottomLeftCorner;
    CornerSize bottomLeftCornerSize;
    CornerTreatment bottomRightCorner;
    CornerSize bottomRightCornerSize;
    EdgeTreatment leftEdge;
    EdgeTreatment rightEdge;
    EdgeTreatment topEdge;
    CornerTreatment topLeftCorner;
    CornerSize topLeftCornerSize;
    CornerTreatment topRightCorner;
    CornerSize topRightCornerSize;

    public interface CornerSizeUnaryOperator {
        CornerSize apply(CornerSize cornerSize);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public EdgeTreatment bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        /* access modifiers changed from: private */
        public CornerTreatment bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        /* access modifiers changed from: private */
        public CornerSize bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
        /* access modifiers changed from: private */
        public CornerTreatment bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        /* access modifiers changed from: private */
        public CornerSize bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
        /* access modifiers changed from: private */
        public EdgeTreatment leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        /* access modifiers changed from: private */
        public EdgeTreatment rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        /* access modifiers changed from: private */
        public EdgeTreatment topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        /* access modifiers changed from: private */
        public CornerTreatment topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        /* access modifiers changed from: private */
        public CornerSize topLeftCornerSize = new AbsoluteCornerSize(0.0f);
        /* access modifiers changed from: private */
        public CornerTreatment topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        /* access modifiers changed from: private */
        public CornerSize topRightCornerSize = new AbsoluteCornerSize(0.0f);

        public Builder() {
        }

        public Builder(ShapeAppearanceModel other) {
            this.topLeftCorner = other.topLeftCorner;
            this.topRightCorner = other.topRightCorner;
            this.bottomRightCorner = other.bottomRightCorner;
            this.bottomLeftCorner = other.bottomLeftCorner;
            this.topLeftCornerSize = other.topLeftCornerSize;
            this.topRightCornerSize = other.topRightCornerSize;
            this.bottomRightCornerSize = other.bottomRightCornerSize;
            this.bottomLeftCornerSize = other.bottomLeftCornerSize;
            this.topEdge = other.topEdge;
            this.rightEdge = other.rightEdge;
            this.bottomEdge = other.bottomEdge;
            this.leftEdge = other.leftEdge;
        }

        public Builder setAllCorners(int cornerFamily, float cornerSize) {
            return setAllCorners(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setAllCornerSizes(cornerSize);
        }

        public Builder setAllCorners(CornerTreatment cornerTreatment) {
            return setTopLeftCorner(cornerTreatment).setTopRightCorner(cornerTreatment).setBottomRightCorner(cornerTreatment).setBottomLeftCorner(cornerTreatment);
        }

        public Builder setAllCornerSizes(CornerSize cornerSize) {
            return setTopLeftCornerSize(cornerSize).setTopRightCornerSize(cornerSize).setBottomRightCornerSize(cornerSize).setBottomLeftCornerSize(cornerSize);
        }

        public Builder setAllCornerSizes(float cornerSize) {
            return setTopLeftCornerSize(cornerSize).setTopRightCornerSize(cornerSize).setBottomRightCornerSize(cornerSize).setBottomLeftCornerSize(cornerSize);
        }

        public Builder setTopLeftCornerSize(float cornerSize) {
            this.topLeftCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        public Builder setTopLeftCornerSize(CornerSize cornerSize) {
            this.topLeftCornerSize = cornerSize;
            return this;
        }

        public Builder setTopRightCornerSize(float cornerSize) {
            this.topRightCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        public Builder setTopRightCornerSize(CornerSize cornerSize) {
            this.topRightCornerSize = cornerSize;
            return this;
        }

        public Builder setBottomRightCornerSize(float cornerSize) {
            this.bottomRightCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        public Builder setBottomRightCornerSize(CornerSize cornerSize) {
            this.bottomRightCornerSize = cornerSize;
            return this;
        }

        public Builder setBottomLeftCornerSize(float cornerSize) {
            this.bottomLeftCornerSize = new AbsoluteCornerSize(cornerSize);
            return this;
        }

        public Builder setBottomLeftCornerSize(CornerSize cornerSize) {
            this.bottomLeftCornerSize = cornerSize;
            return this;
        }

        public Builder setTopLeftCorner(int cornerFamily, float cornerSize) {
            return setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopLeftCornerSize(cornerSize);
        }

        public Builder setTopLeftCorner(int cornerFamily, CornerSize cornerSize) {
            return setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopLeftCornerSize(cornerSize);
        }

        public Builder setTopLeftCorner(CornerTreatment topLeftCorner2) {
            this.topLeftCorner = topLeftCorner2;
            float size = compatCornerTreatmentSize(topLeftCorner2);
            if (size != -1.0f) {
                setTopLeftCornerSize(size);
            }
            return this;
        }

        public Builder setTopRightCorner(int cornerFamily, float cornerSize) {
            return setTopRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopRightCornerSize(cornerSize);
        }

        public Builder setTopRightCorner(int cornerFamily, CornerSize cornerSize) {
            return setTopRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setTopRightCornerSize(cornerSize);
        }

        public Builder setTopRightCorner(CornerTreatment topRightCorner2) {
            this.topRightCorner = topRightCorner2;
            float size = compatCornerTreatmentSize(topRightCorner2);
            if (size != -1.0f) {
                setTopRightCornerSize(size);
            }
            return this;
        }

        public Builder setBottomRightCorner(int cornerFamily, float cornerSize) {
            return setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomRightCornerSize(cornerSize);
        }

        public Builder setBottomRightCorner(int cornerFamily, CornerSize cornerSize) {
            return setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomRightCornerSize(cornerSize);
        }

        public Builder setBottomRightCorner(CornerTreatment bottomRightCorner2) {
            this.bottomRightCorner = bottomRightCorner2;
            float size = compatCornerTreatmentSize(bottomRightCorner2);
            if (size != -1.0f) {
                setBottomRightCornerSize(size);
            }
            return this;
        }

        public Builder setBottomLeftCorner(int cornerFamily, float cornerSize) {
            return setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomLeftCornerSize(cornerSize);
        }

        public Builder setBottomLeftCorner(int cornerFamily, CornerSize cornerSize) {
            return setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(cornerFamily)).setBottomLeftCornerSize(cornerSize);
        }

        public Builder setBottomLeftCorner(CornerTreatment bottomLeftCorner2) {
            this.bottomLeftCorner = bottomLeftCorner2;
            float size = compatCornerTreatmentSize(bottomLeftCorner2);
            if (size != -1.0f) {
                setBottomLeftCornerSize(size);
            }
            return this;
        }

        public Builder setAllEdges(EdgeTreatment edgeTreatment) {
            return setLeftEdge(edgeTreatment).setTopEdge(edgeTreatment).setRightEdge(edgeTreatment).setBottomEdge(edgeTreatment);
        }

        public Builder setLeftEdge(EdgeTreatment leftEdge2) {
            this.leftEdge = leftEdge2;
            return this;
        }

        public Builder setTopEdge(EdgeTreatment topEdge2) {
            this.topEdge = topEdge2;
            return this;
        }

        public Builder setRightEdge(EdgeTreatment rightEdge2) {
            this.rightEdge = rightEdge2;
            return this;
        }

        public Builder setBottomEdge(EdgeTreatment bottomEdge2) {
            this.bottomEdge = bottomEdge2;
            return this;
        }

        private static float compatCornerTreatmentSize(CornerTreatment treatment) {
            if (treatment instanceof RoundedCornerTreatment) {
                return ((RoundedCornerTreatment) treatment).radius;
            }
            if (treatment instanceof CutCornerTreatment) {
                return ((CutCornerTreatment) treatment).size;
            }
            return -1.0f;
        }

        public ShapeAppearanceModel build() {
            return new ShapeAppearanceModel(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        return builder(context, attrs, defStyleAttr, defStyleRes, 0);
    }

    public static Builder builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int defaultCornerSize) {
        return builder(context, attrs, defStyleAttr, defStyleRes, (CornerSize) new AbsoluteCornerSize((float) defaultCornerSize));
    }

    public static Builder builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, CornerSize defaultCornerSize) {
        TypedArray a = context.obtainStyledAttributes(attrs, C1087R.styleable.MaterialShape, defStyleAttr, defStyleRes);
        int shapeAppearanceResId = a.getResourceId(C1087R.styleable.MaterialShape_shapeAppearance, 0);
        int shapeAppearanceOverlayResId = a.getResourceId(C1087R.styleable.MaterialShape_shapeAppearanceOverlay, 0);
        a.recycle();
        return builder(context, shapeAppearanceResId, shapeAppearanceOverlayResId, defaultCornerSize);
    }

    public static Builder builder(Context context, int shapeAppearanceResId, int shapeAppearanceOverlayResId) {
        return builder(context, shapeAppearanceResId, shapeAppearanceOverlayResId, 0);
    }

    private static Builder builder(Context context, int shapeAppearanceResId, int shapeAppearanceOverlayResId, int defaultCornerSize) {
        return builder(context, shapeAppearanceResId, shapeAppearanceOverlayResId, (CornerSize) new AbsoluteCornerSize((float) defaultCornerSize));
    }

    private static Builder builder(Context context, int shapeAppearanceResId, int shapeAppearanceOverlayResId, CornerSize defaultCornerSize) {
        Context context2 = new ContextThemeWrapper(context, shapeAppearanceResId);
        if (shapeAppearanceOverlayResId != 0) {
            context2 = new ContextThemeWrapper(context2, shapeAppearanceOverlayResId);
        }
        TypedArray a = context2.obtainStyledAttributes(C1087R.styleable.ShapeAppearance);
        try {
            int cornerFamily = a.getInt(C1087R.styleable.ShapeAppearance_cornerFamily, 0);
            int cornerFamilyTopLeft = a.getInt(C1087R.styleable.ShapeAppearance_cornerFamilyTopLeft, cornerFamily);
            int cornerFamilyTopRight = a.getInt(C1087R.styleable.ShapeAppearance_cornerFamilyTopRight, cornerFamily);
            int cornerFamilyBottomRight = a.getInt(C1087R.styleable.ShapeAppearance_cornerFamilyBottomRight, cornerFamily);
            int cornerFamilyBottomLeft = a.getInt(C1087R.styleable.ShapeAppearance_cornerFamilyBottomLeft, cornerFamily);
            CornerSize cornerSize = getCornerSize(a, C1087R.styleable.ShapeAppearance_cornerSize, defaultCornerSize);
            CornerSize cornerSizeTopLeft = getCornerSize(a, C1087R.styleable.ShapeAppearance_cornerSizeTopLeft, cornerSize);
            CornerSize cornerSizeTopRight = getCornerSize(a, C1087R.styleable.ShapeAppearance_cornerSizeTopRight, cornerSize);
            CornerSize cornerSizeBottomRight = getCornerSize(a, C1087R.styleable.ShapeAppearance_cornerSizeBottomRight, cornerSize);
            return new Builder().setTopLeftCorner(cornerFamilyTopLeft, cornerSizeTopLeft).setTopRightCorner(cornerFamilyTopRight, cornerSizeTopRight).setBottomRightCorner(cornerFamilyBottomRight, cornerSizeBottomRight).setBottomLeftCorner(cornerFamilyBottomLeft, getCornerSize(a, C1087R.styleable.ShapeAppearance_cornerSizeBottomLeft, cornerSize));
        } finally {
            a.recycle();
        }
    }

    private static CornerSize getCornerSize(TypedArray a, int index, CornerSize defaultValue) {
        TypedValue value = a.peekValue(index);
        if (value == null) {
            return defaultValue;
        }
        if (value.type == 5) {
            return new AbsoluteCornerSize((float) TypedValue.complexToDimensionPixelSize(value.data, a.getResources().getDisplayMetrics()));
        }
        if (value.type == 6) {
            return new RelativeCornerSize(value.getFraction(1.0f, 1.0f));
        }
        return defaultValue;
    }

    private ShapeAppearanceModel(Builder builder) {
        this.topLeftCorner = builder.topLeftCorner;
        this.topRightCorner = builder.topRightCorner;
        this.bottomRightCorner = builder.bottomRightCorner;
        this.bottomLeftCorner = builder.bottomLeftCorner;
        this.topLeftCornerSize = builder.topLeftCornerSize;
        this.topRightCornerSize = builder.topRightCornerSize;
        this.bottomRightCornerSize = builder.bottomRightCornerSize;
        this.bottomLeftCornerSize = builder.bottomLeftCornerSize;
        this.topEdge = builder.topEdge;
        this.rightEdge = builder.rightEdge;
        this.bottomEdge = builder.bottomEdge;
        this.leftEdge = builder.leftEdge;
    }

    public ShapeAppearanceModel() {
        this.topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
        this.topLeftCornerSize = new AbsoluteCornerSize(0.0f);
        this.topRightCornerSize = new AbsoluteCornerSize(0.0f);
        this.bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
        this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
        this.topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        this.rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        this.bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
        this.leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
    }

    public CornerTreatment getTopLeftCorner() {
        return this.topLeftCorner;
    }

    public CornerTreatment getTopRightCorner() {
        return this.topRightCorner;
    }

    public CornerTreatment getBottomRightCorner() {
        return this.bottomRightCorner;
    }

    public CornerTreatment getBottomLeftCorner() {
        return this.bottomLeftCorner;
    }

    public CornerSize getTopLeftCornerSize() {
        return this.topLeftCornerSize;
    }

    public CornerSize getTopRightCornerSize() {
        return this.topRightCornerSize;
    }

    public CornerSize getBottomRightCornerSize() {
        return this.bottomRightCornerSize;
    }

    public CornerSize getBottomLeftCornerSize() {
        return this.bottomLeftCornerSize;
    }

    public EdgeTreatment getLeftEdge() {
        return this.leftEdge;
    }

    public EdgeTreatment getTopEdge() {
        return this.topEdge;
    }

    public EdgeTreatment getRightEdge() {
        return this.rightEdge;
    }

    public EdgeTreatment getBottomEdge() {
        return this.bottomEdge;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public ShapeAppearanceModel withCornerSize(float cornerSize) {
        return toBuilder().setAllCornerSizes(cornerSize).build();
    }

    public ShapeAppearanceModel withCornerSize(CornerSize cornerSize) {
        return toBuilder().setAllCornerSizes(cornerSize).build();
    }

    public ShapeAppearanceModel withTransformedCornerSizes(CornerSizeUnaryOperator op) {
        return toBuilder().setTopLeftCornerSize(op.apply(getTopLeftCornerSize())).setTopRightCornerSize(op.apply(getTopRightCornerSize())).setBottomLeftCornerSize(op.apply(getBottomLeftCornerSize())).setBottomRightCornerSize(op.apply(getBottomRightCornerSize())).build();
    }

    public boolean isRoundRect(RectF bounds) {
        boolean hasDefaultEdges = this.leftEdge.getClass().equals(EdgeTreatment.class) && this.rightEdge.getClass().equals(EdgeTreatment.class) && this.topEdge.getClass().equals(EdgeTreatment.class) && this.bottomEdge.getClass().equals(EdgeTreatment.class);
        float cornerSize = this.topLeftCornerSize.getCornerSize(bounds);
        boolean cornersHaveSameSize = this.topRightCornerSize.getCornerSize(bounds) == cornerSize && this.bottomLeftCornerSize.getCornerSize(bounds) == cornerSize && this.bottomRightCornerSize.getCornerSize(bounds) == cornerSize;
        boolean hasRoundedCorners = (this.topRightCorner instanceof RoundedCornerTreatment) && (this.topLeftCorner instanceof RoundedCornerTreatment) && (this.bottomRightCorner instanceof RoundedCornerTreatment) && (this.bottomLeftCorner instanceof RoundedCornerTreatment);
        if (!hasDefaultEdges || !cornersHaveSameSize || !hasRoundedCorners) {
            return false;
        }
        return true;
    }
}
