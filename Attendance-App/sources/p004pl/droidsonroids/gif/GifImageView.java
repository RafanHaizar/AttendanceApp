package p004pl.droidsonroids.gif;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import p004pl.droidsonroids.gif.GifViewUtils;

/* renamed from: pl.droidsonroids.gif.GifImageView */
public class GifImageView extends ImageView {
    private boolean mFreezesAnimation;

    public GifImageView(Context context) {
        super(context);
    }

    public GifImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInit(GifViewUtils.initImageView(this, attrs, 0, 0));
    }

    public GifImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        postInit(GifViewUtils.initImageView(this, attrs, defStyle, 0));
    }

    public GifImageView(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        super(context, attrs, defStyle, defStyleRes);
        postInit(GifViewUtils.initImageView(this, attrs, defStyle, defStyleRes));
    }

    private void postInit(GifViewUtils.GifImageViewAttributes result) {
        this.mFreezesAnimation = result.freezesAnimation;
        if (result.mSourceResId > 0) {
            super.setImageResource(result.mSourceResId);
        }
        if (result.mBackgroundResId > 0) {
            super.setBackgroundResource(result.mBackgroundResId);
        }
    }

    public void setImageURI(Uri uri) {
        if (!GifViewUtils.setGifImageUri(this, uri)) {
            super.setImageURI(uri);
        }
    }

    public void setImageResource(int resId) {
        if (!GifViewUtils.setResource(this, true, resId)) {
            super.setImageResource(resId);
        }
    }

    public void setBackgroundResource(int resId) {
        if (!GifViewUtils.setResource(this, false, resId)) {
            super.setBackgroundResource(resId);
        }
    }

    public Parcelable onSaveInstanceState() {
        Drawable background = null;
        Drawable source = this.mFreezesAnimation ? getDrawable() : null;
        if (this.mFreezesAnimation) {
            background = getBackground();
        }
        return new GifViewSavedState(super.onSaveInstanceState(), source, background);
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        GifViewSavedState ss = (GifViewSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        ss.restoreState(getDrawable(), 0);
        ss.restoreState(getBackground(), 1);
    }

    public void setFreezesAnimation(boolean freezesAnimation) {
        this.mFreezesAnimation = freezesAnimation;
    }
}
