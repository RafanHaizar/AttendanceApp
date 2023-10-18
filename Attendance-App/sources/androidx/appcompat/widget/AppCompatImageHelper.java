package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.appcompat.C0503R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;

public class AppCompatImageHelper {
    private TintInfo mImageTint;
    private TintInfo mInternalImageTint;
    private int mLevel = 0;
    private TintInfo mTmpInfo;
    private final ImageView mView;

    public AppCompatImageHelper(ImageView view) {
        this.mView = view;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        int id;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attrs, C0503R.styleable.AppCompatImageView, defStyleAttr, 0);
        ImageView imageView = this.mView;
        ViewCompat.saveAttributeDataForStyleable(imageView, imageView.getContext(), C0503R.styleable.AppCompatImageView, attrs, a.getWrappedTypeArray(), defStyleAttr, 0);
        try {
            Drawable drawable = this.mView.getDrawable();
            if (!(drawable != null || (id = a.getResourceId(C0503R.styleable.AppCompatImageView_srcCompat, -1)) == -1 || (drawable = AppCompatResources.getDrawable(this.mView.getContext(), id)) == null)) {
                this.mView.setImageDrawable(drawable);
            }
            if (drawable != null) {
                DrawableUtils.fixDrawable(drawable);
            }
            if (a.hasValue(C0503R.styleable.AppCompatImageView_tint)) {
                ImageViewCompat.setImageTintList(this.mView, a.getColorStateList(C0503R.styleable.AppCompatImageView_tint));
            }
            if (a.hasValue(C0503R.styleable.AppCompatImageView_tintMode)) {
                ImageViewCompat.setImageTintMode(this.mView, DrawableUtils.parseTintMode(a.getInt(C0503R.styleable.AppCompatImageView_tintMode, -1), (PorterDuff.Mode) null));
            }
        } finally {
            a.recycle();
        }
    }

    public void setImageResource(int resId) {
        if (resId != 0) {
            Drawable d = AppCompatResources.getDrawable(this.mView.getContext(), resId);
            if (d != null) {
                DrawableUtils.fixDrawable(d);
            }
            this.mView.setImageDrawable(d);
        } else {
            this.mView.setImageDrawable((Drawable) null);
        }
        applySupportImageTint();
    }

    /* access modifiers changed from: package-private */
    public boolean hasOverlappingRendering() {
        Drawable background = this.mView.getBackground();
        if (Build.VERSION.SDK_INT < 21 || !(background instanceof RippleDrawable)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setSupportImageTintList(ColorStateList tint) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        this.mImageTint.mTintList = tint;
        this.mImageTint.mHasTintList = true;
        applySupportImageTint();
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getSupportImageTintList() {
        TintInfo tintInfo = this.mImageTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setSupportImageTintMode(PorterDuff.Mode tintMode) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        this.mImageTint.mTintMode = tintMode;
        this.mImageTint.mHasTintMode = true;
        applySupportImageTint();
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode getSupportImageTintMode() {
        TintInfo tintInfo = this.mImageTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void applySupportImageTint() {
        Drawable imageViewDrawable = this.mView.getDrawable();
        if (imageViewDrawable != null) {
            DrawableUtils.fixDrawable(imageViewDrawable);
        }
        if (imageViewDrawable == null) {
            return;
        }
        if (!shouldApplyFrameworkTintUsingColorFilter() || !applyFrameworkTintUsingColorFilter(imageViewDrawable)) {
            TintInfo tintInfo = this.mImageTint;
            if (tintInfo != null) {
                AppCompatDrawableManager.tintDrawable(imageViewDrawable, tintInfo, this.mView.getDrawableState());
                return;
            }
            TintInfo tintInfo2 = this.mInternalImageTint;
            if (tintInfo2 != null) {
                AppCompatDrawableManager.tintDrawable(imageViewDrawable, tintInfo2, this.mView.getDrawableState());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setInternalImageTint(ColorStateList tint) {
        if (tint != null) {
            if (this.mInternalImageTint == null) {
                this.mInternalImageTint = new TintInfo();
            }
            this.mInternalImageTint.mTintList = tint;
            this.mInternalImageTint.mHasTintList = true;
        } else {
            this.mInternalImageTint = null;
        }
        applySupportImageTint();
    }

    private boolean shouldApplyFrameworkTintUsingColorFilter() {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk <= 21) {
            return sdk == 21;
        }
        if (this.mInternalImageTint != null) {
            return true;
        }
        return false;
    }

    private boolean applyFrameworkTintUsingColorFilter(Drawable imageSource) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo info = this.mTmpInfo;
        info.clear();
        ColorStateList tintList = ImageViewCompat.getImageTintList(this.mView);
        if (tintList != null) {
            info.mHasTintList = true;
            info.mTintList = tintList;
        }
        PorterDuff.Mode mode = ImageViewCompat.getImageTintMode(this.mView);
        if (mode != null) {
            info.mHasTintMode = true;
            info.mTintMode = mode;
        }
        if (!info.mHasTintList && !info.mHasTintMode) {
            return false;
        }
        AppCompatDrawableManager.tintDrawable(imageSource, info, this.mView.getDrawableState());
        return true;
    }

    /* access modifiers changed from: package-private */
    public void obtainLevelFromDrawable(Drawable drawable) {
        this.mLevel = drawable.getLevel();
    }

    /* access modifiers changed from: package-private */
    public void applyImageLevel() {
        if (this.mView.getDrawable() != null) {
            this.mView.getDrawable().setLevel(this.mLevel);
        }
    }
}
