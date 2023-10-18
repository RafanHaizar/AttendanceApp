package androidx.vectordrawable.graphics.drawable;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.TintAwareDrawable;

abstract class VectorDrawableCommon extends Drawable implements TintAwareDrawable {
    Drawable mDelegateDrawable;

    VectorDrawableCommon() {
    }

    public void setColorFilter(int color, PorterDuff.Mode mode) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setColorFilter(color, mode);
        } else {
            super.setColorFilter(color, mode);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.setLevel(level);
        }
        return super.onLevelChange(level);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setBounds(bounds);
        } else {
            super.onBoundsChange(bounds);
        }
    }

    public void setHotspot(float x, float y) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspot(drawable, x, y);
        }
    }

    public void setHotspotBounds(int left, int top, int right, int bottom) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            DrawableCompat.setHotspotBounds(drawable, left, top, right, bottom);
        }
    }

    public void setFilterBitmap(boolean filter) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setFilterBitmap(filter);
        }
    }

    public void jumpToCurrentState() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            DrawableCompat.jumpToCurrentState(drawable);
        }
    }

    public void applyTheme(Resources.Theme t) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            DrawableCompat.applyTheme(drawable, t);
        }
    }

    public void clearColorFilter() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.clearColorFilter();
        } else {
            super.clearColorFilter();
        }
    }

    public Drawable getCurrent() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.getCurrent();
        }
        return super.getCurrent();
    }

    public int getMinimumWidth() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.getMinimumWidth();
        }
        return super.getMinimumWidth();
    }

    public int getMinimumHeight() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.getMinimumHeight();
        }
        return super.getMinimumHeight();
    }

    public boolean getPadding(Rect padding) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.getPadding(padding);
        }
        return super.getPadding(padding);
    }

    public int[] getState() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.getState();
        }
        return super.getState();
    }

    public Region getTransparentRegion() {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.getTransparentRegion();
        }
        return super.getTransparentRegion();
    }

    public void setChangingConfigurations(int configs) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            drawable.setChangingConfigurations(configs);
        } else {
            super.setChangingConfigurations(configs);
        }
    }

    public boolean setState(int[] stateSet) {
        Drawable drawable = this.mDelegateDrawable;
        if (drawable != null) {
            return drawable.setState(stateSet);
        }
        return super.setState(stateSet);
    }
}
