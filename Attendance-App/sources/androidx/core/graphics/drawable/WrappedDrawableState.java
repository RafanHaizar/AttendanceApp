package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;

final class WrappedDrawableState extends Drawable.ConstantState {
    int mChangingConfigurations;
    Drawable.ConstantState mDrawableState;
    ColorStateList mTint = null;
    PorterDuff.Mode mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE;

    WrappedDrawableState(WrappedDrawableState orig) {
        if (orig != null) {
            this.mChangingConfigurations = orig.mChangingConfigurations;
            this.mDrawableState = orig.mDrawableState;
            this.mTint = orig.mTint;
            this.mTintMode = orig.mTintMode;
        }
    }

    public Drawable newDrawable() {
        return newDrawable((Resources) null);
    }

    public Drawable newDrawable(Resources res) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new WrappedDrawableApi21(this, res);
        }
        return new WrappedDrawableApi14(this, res);
    }

    public int getChangingConfigurations() {
        int i = this.mChangingConfigurations;
        Drawable.ConstantState constantState = this.mDrawableState;
        return i | (constantState != null ? constantState.getChangingConfigurations() : 0);
    }

    /* access modifiers changed from: package-private */
    public boolean canConstantState() {
        return this.mDrawableState != null;
    }
}
