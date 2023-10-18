package p004pl.droidsonroids.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.io.IOException;
import p004pl.droidsonroids.gif.GifViewUtils;

/* renamed from: pl.droidsonroids.gif.GifTextView */
public class GifTextView extends TextView {
    private GifViewUtils.GifViewAttributes viewAttributes;

    public GifTextView(Context context) {
        super(context);
    }

    public GifTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public GifTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle, 0);
    }

    public GifTextView(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        super(context, attrs, defStyle, defStyleRes);
        init(attrs, defStyle, defStyleRes);
    }

    private static void setDrawablesVisible(Drawable[] drawables, boolean visible) {
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                drawable.setVisible(visible, false);
            }
        }
    }

    private void init(AttributeSet attrs, int defStyle, int defStyleRes) {
        if (attrs != null) {
            Drawable left = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableLeft", 0));
            Drawable top = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableTop", 0));
            Drawable right = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableRight", 0));
            Drawable bottom = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableBottom", 0));
            Drawable start = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableStart", 0));
            Drawable end = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableEnd", 0));
            if (getLayoutDirection() == 0) {
                if (start == null) {
                    start = left;
                }
                if (end == null) {
                    end = right;
                }
            } else {
                if (start == null) {
                    start = right;
                }
                if (end == null) {
                    end = left;
                }
            }
            setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
            setBackground(getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", CommonCssConstants.BACKGROUND, 0)));
            this.viewAttributes = new GifViewUtils.GifViewAttributes(this, attrs, defStyle, defStyleRes);
            applyGifViewAttributes();
        }
        this.viewAttributes = new GifViewUtils.GifViewAttributes();
    }

    private void applyGifViewAttributes() {
        if (this.viewAttributes.mLoopCount >= 0) {
            for (Drawable drawable : getCompoundDrawables()) {
                GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, drawable);
            }
            for (Drawable drawable2 : getCompoundDrawablesRelative()) {
                GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, drawable2);
            }
            GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, getBackground());
        }
    }

    private Drawable getGifOrDefaultDrawable(int resId) {
        if (resId == 0) {
            return null;
        }
        Resources resources = getResources();
        String resourceTypeName = resources.getResourceTypeName(resId);
        if (!isInEditMode() && GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES.contains(resourceTypeName)) {
            try {
                return new GifDrawable(resources, resId);
            } catch (Resources.NotFoundException | IOException e) {
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawable(resId, getContext().getTheme());
        }
        return resources.getDrawable(resId);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        setCompoundDrawablesWithIntrinsicBounds(getGifOrDefaultDrawable(left), getGifOrDefaultDrawable(top), getGifOrDefaultDrawable(right), getGifOrDefaultDrawable(bottom));
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(getGifOrDefaultDrawable(start), getGifOrDefaultDrawable(top), getGifOrDefaultDrawable(end), getGifOrDefaultDrawable(bottom));
    }

    public Parcelable onSaveInstanceState() {
        Drawable[] savedDrawables = new Drawable[7];
        if (this.viewAttributes.freezesAnimation) {
            Drawable[] compoundDrawables = getCompoundDrawables();
            System.arraycopy(compoundDrawables, 0, savedDrawables, 0, compoundDrawables.length);
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            savedDrawables[4] = compoundDrawablesRelative[0];
            savedDrawables[5] = compoundDrawablesRelative[2];
            savedDrawables[6] = getBackground();
        }
        return new GifViewSavedState(super.onSaveInstanceState(), savedDrawables);
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        GifViewSavedState ss = (GifViewSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        Drawable[] compoundDrawables = getCompoundDrawables();
        ss.restoreState(compoundDrawables[0], 0);
        ss.restoreState(compoundDrawables[1], 1);
        ss.restoreState(compoundDrawables[2], 2);
        ss.restoreState(compoundDrawables[3], 3);
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        ss.restoreState(compoundDrawablesRelative[0], 4);
        ss.restoreState(compoundDrawablesRelative[2], 5);
        ss.restoreState(getBackground(), 6);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setCompoundDrawablesVisible(true);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setCompoundDrawablesVisible(false);
    }

    public void setBackgroundResource(int resId) {
        setBackground(getGifOrDefaultDrawable(resId));
    }

    private void setCompoundDrawablesVisible(boolean visible) {
        setDrawablesVisible(getCompoundDrawables(), visible);
        setDrawablesVisible(getCompoundDrawablesRelative(), visible);
    }

    public void setFreezesAnimation(boolean freezesAnimation) {
        this.viewAttributes.freezesAnimation = freezesAnimation;
    }
}
