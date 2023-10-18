package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

class TransitionUtils {
    private static final boolean HAS_IS_ATTACHED_TO_WINDOW = true;
    private static final boolean HAS_OVERLAY = true;
    private static final boolean HAS_PICTURE_BITMAP;
    private static final int MAX_IMAGE_SIZE = 1048576;

    static {
        boolean z = true;
        if (Build.VERSION.SDK_INT < 28) {
            z = false;
        }
        HAS_PICTURE_BITMAP = z;
    }

    static View copyViewImage(ViewGroup sceneRoot, View view, View parent) {
        Matrix matrix = new Matrix();
        matrix.setTranslate((float) (-parent.getScrollX()), (float) (-parent.getScrollY()));
        ViewUtils.transformMatrixToGlobal(view, matrix);
        ViewUtils.transformMatrixToLocal(sceneRoot, matrix);
        RectF bounds = new RectF(0.0f, 0.0f, (float) view.getWidth(), (float) view.getHeight());
        matrix.mapRect(bounds);
        int left = Math.round(bounds.left);
        int top = Math.round(bounds.top);
        int right = Math.round(bounds.right);
        int bottom = Math.round(bounds.bottom);
        ImageView copy = new ImageView(view.getContext());
        copy.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Bitmap bitmap = createViewBitmap(view, matrix, bounds, sceneRoot);
        if (bitmap != null) {
            copy.setImageBitmap(bitmap);
        }
        copy.measure(View.MeasureSpec.makeMeasureSpec(right - left, 1073741824), View.MeasureSpec.makeMeasureSpec(bottom - top, 1073741824));
        copy.layout(left, top, right, bottom);
        return copy;
    }

    /* JADX WARNING: type inference failed for: r5v4, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.Bitmap createViewBitmap(android.view.View r11, android.graphics.Matrix r12, android.graphics.RectF r13, android.view.ViewGroup r14) {
        /*
            boolean r0 = HAS_IS_ATTACHED_TO_WINDOW
            if (r0 == 0) goto L_0x0013
            boolean r0 = r11.isAttachedToWindow()
            r0 = r0 ^ 1
            if (r14 != 0) goto L_0x000e
            r1 = 0
            goto L_0x0012
        L_0x000e:
            boolean r1 = r14.isAttachedToWindow()
        L_0x0012:
            goto L_0x0015
        L_0x0013:
            r0 = 0
            r1 = 0
        L_0x0015:
            r2 = 0
            r3 = 0
            boolean r4 = HAS_OVERLAY
            if (r4 == 0) goto L_0x0033
            if (r0 == 0) goto L_0x0033
            if (r1 != 0) goto L_0x0021
            r4 = 0
            return r4
        L_0x0021:
            android.view.ViewParent r5 = r11.getParent()
            r2 = r5
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            int r3 = r2.indexOfChild(r11)
            android.view.ViewGroupOverlay r5 = r14.getOverlay()
            r5.add(r11)
        L_0x0033:
            r5 = 0
            float r6 = r13.width()
            int r6 = java.lang.Math.round(r6)
            float r7 = r13.height()
            int r7 = java.lang.Math.round(r7)
            if (r6 <= 0) goto L_0x009a
            if (r7 <= 0) goto L_0x009a
            int r8 = r6 * r7
            float r8 = (float) r8
            r9 = 1233125376(0x49800000, float:1048576.0)
            float r9 = r9 / r8
            r8 = 1065353216(0x3f800000, float:1.0)
            float r8 = java.lang.Math.min(r8, r9)
            float r9 = (float) r6
            float r9 = r9 * r8
            int r6 = java.lang.Math.round(r9)
            float r9 = (float) r7
            float r9 = r9 * r8
            int r7 = java.lang.Math.round(r9)
            float r9 = r13.left
            float r9 = -r9
            float r10 = r13.top
            float r10 = -r10
            r12.postTranslate(r9, r10)
            r12.postScale(r8, r8)
            boolean r9 = HAS_PICTURE_BITMAP
            if (r9 == 0) goto L_0x0089
            android.graphics.Picture r9 = new android.graphics.Picture
            r9.<init>()
            android.graphics.Canvas r10 = r9.beginRecording(r6, r7)
            r10.concat(r12)
            r11.draw(r10)
            r9.endRecording()
            android.graphics.Bitmap r5 = android.graphics.Bitmap.createBitmap(r9)
            goto L_0x009a
        L_0x0089:
            android.graphics.Bitmap$Config r9 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r5 = android.graphics.Bitmap.createBitmap(r6, r7, r9)
            android.graphics.Canvas r9 = new android.graphics.Canvas
            r9.<init>(r5)
            r9.concat(r12)
            r11.draw(r9)
        L_0x009a:
            if (r4 == 0) goto L_0x00a8
            if (r0 == 0) goto L_0x00a8
            android.view.ViewGroupOverlay r4 = r14.getOverlay()
            r4.remove(r11)
            r2.addView(r11, r3)
        L_0x00a8:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.TransitionUtils.createViewBitmap(android.view.View, android.graphics.Matrix, android.graphics.RectF, android.view.ViewGroup):android.graphics.Bitmap");
    }

    static Animator mergeAnimators(Animator animator1, Animator animator2) {
        if (animator1 == null) {
            return animator2;
        }
        if (animator2 == null) {
            return animator1;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{animator1, animator2});
        return animatorSet;
    }

    static class MatrixEvaluator implements TypeEvaluator<Matrix> {
        final float[] mTempEndValues = new float[9];
        final Matrix mTempMatrix = new Matrix();
        final float[] mTempStartValues = new float[9];

        MatrixEvaluator() {
        }

        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            startValue.getValues(this.mTempStartValues);
            endValue.getValues(this.mTempEndValues);
            for (int i = 0; i < 9; i++) {
                float[] fArr = this.mTempEndValues;
                float f = fArr[i];
                float f2 = this.mTempStartValues[i];
                fArr[i] = f2 + (fraction * (f - f2));
            }
            this.mTempMatrix.setValues(this.mTempEndValues);
            return this.mTempMatrix;
        }
    }

    private TransitionUtils() {
    }
}
