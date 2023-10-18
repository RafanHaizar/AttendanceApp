package androidx.transition;

import android.view.View;

class ViewUtilsApi22 extends ViewUtilsApi21 {
    private static boolean sTryHiddenSetLeftTopRightBottom = true;

    ViewUtilsApi22() {
    }

    public void setLeftTopRightBottom(View v, int left, int top, int right, int bottom) {
        if (sTryHiddenSetLeftTopRightBottom) {
            try {
                v.setLeftTopRightBottom(left, top, right, bottom);
            } catch (NoSuchMethodError e) {
                sTryHiddenSetLeftTopRightBottom = false;
            }
        }
    }
}
