package androidx.transition;

import android.view.View;

class ViewUtilsApi19 extends ViewUtilsBase {
    private static boolean sTryHiddenTransitionAlpha = true;

    ViewUtilsApi19() {
    }

    public void setTransitionAlpha(View view, float alpha) {
        if (sTryHiddenTransitionAlpha) {
            try {
                view.setTransitionAlpha(alpha);
                return;
            } catch (NoSuchMethodError e) {
                sTryHiddenTransitionAlpha = false;
            }
        }
        view.setAlpha(alpha);
    }

    public float getTransitionAlpha(View view) {
        if (sTryHiddenTransitionAlpha) {
            try {
                return view.getTransitionAlpha();
            } catch (NoSuchMethodError e) {
                sTryHiddenTransitionAlpha = false;
            }
        }
        return view.getAlpha();
    }

    public void saveNonTransitionAlpha(View view) {
    }

    public void clearNonTransitionAlpha(View view) {
    }
}
