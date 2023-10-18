package androidx.transition;

import android.graphics.Matrix;
import android.view.View;

class ViewUtilsApi29 extends ViewUtilsApi23 {
    ViewUtilsApi29() {
    }

    public void setTransitionAlpha(View view, float alpha) {
        view.setTransitionAlpha(alpha);
    }

    public float getTransitionAlpha(View view) {
        return view.getTransitionAlpha();
    }

    public void setTransitionVisibility(View view, int visibility) {
        view.setTransitionVisibility(visibility);
    }

    public void setLeftTopRightBottom(View v, int left, int top, int right, int bottom) {
        v.setLeftTopRightBottom(left, top, right, bottom);
    }

    public void transformMatrixToGlobal(View view, Matrix matrix) {
        view.transformMatrixToGlobal(matrix);
    }

    public void transformMatrixToLocal(View view, Matrix matrix) {
        view.transformMatrixToLocal(matrix);
    }

    public void setAnimationMatrix(View view, Matrix matrix) {
        view.setAnimationMatrix(matrix);
    }
}
