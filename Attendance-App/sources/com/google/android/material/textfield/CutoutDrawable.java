package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

class CutoutDrawable extends MaterialShapeDrawable {
    protected final RectF cutoutBounds;

    static CutoutDrawable create(ShapeAppearanceModel shapeAppearanceModel) {
        return new ImplApi18(shapeAppearanceModel);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private CutoutDrawable(ShapeAppearanceModel shapeAppearanceModel) {
        super(shapeAppearanceModel != null ? shapeAppearanceModel : new ShapeAppearanceModel());
        this.cutoutBounds = new RectF();
    }

    /* access modifiers changed from: package-private */
    public boolean hasCutout() {
        return !this.cutoutBounds.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public void setCutout(float left, float top, float right, float bottom) {
        if (left != this.cutoutBounds.left || top != this.cutoutBounds.top || right != this.cutoutBounds.right || bottom != this.cutoutBounds.bottom) {
            this.cutoutBounds.set(left, top, right, bottom);
            invalidateSelf();
        }
    }

    /* access modifiers changed from: package-private */
    public void setCutout(RectF bounds) {
        setCutout(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    /* access modifiers changed from: package-private */
    public void removeCutout() {
        setCutout(0.0f, 0.0f, 0.0f, 0.0f);
    }

    private static class ImplApi18 extends CutoutDrawable {
        ImplApi18(ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }

        /* access modifiers changed from: protected */
        public void drawStrokeShape(Canvas canvas) {
            if (this.cutoutBounds.isEmpty()) {
                CutoutDrawable.super.drawStrokeShape(canvas);
                return;
            }
            canvas.save();
            if (Build.VERSION.SDK_INT >= 26) {
                canvas.clipOutRect(this.cutoutBounds);
            } else {
                canvas.clipRect(this.cutoutBounds, Region.Op.DIFFERENCE);
            }
            CutoutDrawable.super.drawStrokeShape(canvas);
            canvas.restore();
        }
    }

    private static class ImplApi14 extends CutoutDrawable {
        private Paint cutoutPaint;
        private int savedLayer;

        ImplApi14(ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }

        public void draw(Canvas canvas) {
            preDraw(canvas);
            CutoutDrawable.super.draw(canvas);
            postDraw(canvas);
        }

        /* access modifiers changed from: protected */
        public void drawStrokeShape(Canvas canvas) {
            CutoutDrawable.super.drawStrokeShape(canvas);
            canvas.drawRect(this.cutoutBounds, getCutoutPaint());
        }

        private Paint getCutoutPaint() {
            if (this.cutoutPaint == null) {
                Paint paint = new Paint(1);
                this.cutoutPaint = paint;
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                this.cutoutPaint.setColor(-1);
                this.cutoutPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            }
            return this.cutoutPaint;
        }

        private void preDraw(Canvas canvas) {
            Drawable.Callback callback = getCallback();
            if (useHardwareLayer(callback)) {
                View viewCallback = (View) callback;
                if (viewCallback.getLayerType() != 2) {
                    viewCallback.setLayerType(2, (Paint) null);
                    return;
                }
                return;
            }
            saveCanvasLayer(canvas);
        }

        private void saveCanvasLayer(Canvas canvas) {
            if (Build.VERSION.SDK_INT >= 21) {
                this.savedLayer = canvas.saveLayer(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), (Paint) null);
                return;
            }
            this.savedLayer = canvas.saveLayer(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), (Paint) null, 31);
        }

        private void postDraw(Canvas canvas) {
            if (!useHardwareLayer(getCallback())) {
                canvas.restoreToCount(this.savedLayer);
            }
        }

        private boolean useHardwareLayer(Drawable.Callback callback) {
            return callback instanceof View;
        }
    }
}
