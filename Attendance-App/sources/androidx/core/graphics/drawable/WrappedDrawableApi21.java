package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Log;
import java.lang.reflect.Method;

class WrappedDrawableApi21 extends WrappedDrawableApi14 {
    private static final String TAG = "WrappedDrawableApi21";
    private static Method sIsProjectedDrawableMethod;

    WrappedDrawableApi21(Drawable drawable) {
        super(drawable);
        findAndCacheIsProjectedDrawableMethod();
    }

    WrappedDrawableApi21(WrappedDrawableState state, Resources resources) {
        super(state, resources);
        findAndCacheIsProjectedDrawableMethod();
    }

    public void setHotspot(float x, float y) {
        this.mDrawable.setHotspot(x, y);
    }

    public void setHotspotBounds(int left, int top, int right, int bottom) {
        this.mDrawable.setHotspotBounds(left, top, right, bottom);
    }

    public void getOutline(Outline outline) {
        this.mDrawable.getOutline(outline);
    }

    public Rect getDirtyBounds() {
        return this.mDrawable.getDirtyBounds();
    }

    public void setTintList(ColorStateList tint) {
        if (isCompatTintEnabled()) {
            super.setTintList(tint);
        } else {
            this.mDrawable.setTintList(tint);
        }
    }

    public void setTint(int tintColor) {
        if (isCompatTintEnabled()) {
            super.setTint(tintColor);
        } else {
            this.mDrawable.setTint(tintColor);
        }
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        if (isCompatTintEnabled()) {
            super.setTintMode(tintMode);
        } else {
            this.mDrawable.setTintMode(tintMode);
        }
    }

    public boolean setState(int[] stateSet) {
        if (!super.setState(stateSet)) {
            return false;
        }
        invalidateSelf();
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isCompatTintEnabled() {
        if (Build.VERSION.SDK_INT != 21) {
            return false;
        }
        Drawable drawable = this.mDrawable;
        if ((drawable instanceof GradientDrawable) || (drawable instanceof DrawableContainer) || (drawable instanceof InsetDrawable) || (drawable instanceof RippleDrawable)) {
            return true;
        }
        return false;
    }

    public boolean isProjected() {
        Method method;
        if (!(this.mDrawable == null || (method = sIsProjectedDrawableMethod) == null)) {
            try {
                return ((Boolean) method.invoke(this.mDrawable, new Object[0])).booleanValue();
            } catch (Exception ex) {
                Log.w(TAG, "Error calling Drawable#isProjected() method", ex);
            }
        }
        return false;
    }

    private void findAndCacheIsProjectedDrawableMethod() {
        if (sIsProjectedDrawableMethod == null) {
            try {
                sIsProjectedDrawableMethod = Drawable.class.getDeclaredMethod("isProjected", new Class[0]);
            } catch (Exception ex) {
                Log.w(TAG, "Failed to retrieve Drawable#isProjected() method", ex);
            }
        }
    }
}
