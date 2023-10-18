package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

public class ImageViewCompat {
    public static ColorStateList getImageTintList(ImageView view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getImageTintList(view);
        }
        if (view instanceof TintableImageSourceView) {
            return ((TintableImageSourceView) view).getSupportImageTintList();
        }
        return null;
    }

    public static void setImageTintList(ImageView view, ColorStateList tintList) {
        Drawable imageViewDrawable;
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.setImageTintList(view, tintList);
            if (Build.VERSION.SDK_INT == 21 && (imageViewDrawable = view.getDrawable()) != null && Api21Impl.getImageTintList(view) != null) {
                if (imageViewDrawable.isStateful()) {
                    imageViewDrawable.setState(view.getDrawableState());
                }
                view.setImageDrawable(imageViewDrawable);
            }
        } else if (view instanceof TintableImageSourceView) {
            ((TintableImageSourceView) view).setSupportImageTintList(tintList);
        }
    }

    public static PorterDuff.Mode getImageTintMode(ImageView view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getImageTintMode(view);
        }
        if (view instanceof TintableImageSourceView) {
            return ((TintableImageSourceView) view).getSupportImageTintMode();
        }
        return null;
    }

    public static void setImageTintMode(ImageView view, PorterDuff.Mode mode) {
        Drawable imageViewDrawable;
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.setImageTintMode(view, mode);
            if (Build.VERSION.SDK_INT == 21 && (imageViewDrawable = view.getDrawable()) != null && Api21Impl.getImageTintList(view) != null) {
                if (imageViewDrawable.isStateful()) {
                    imageViewDrawable.setState(view.getDrawableState());
                }
                view.setImageDrawable(imageViewDrawable);
            }
        } else if (view instanceof TintableImageSourceView) {
            ((TintableImageSourceView) view).setSupportImageTintMode(mode);
        }
    }

    private ImageViewCompat() {
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static ColorStateList getImageTintList(ImageView imageView) {
            return imageView.getImageTintList();
        }

        static void setImageTintList(ImageView imageView, ColorStateList tint) {
            imageView.setImageTintList(tint);
        }

        static PorterDuff.Mode getImageTintMode(ImageView imageView) {
            return imageView.getImageTintMode();
        }

        static void setImageTintMode(ImageView imageView, PorterDuff.Mode tintMode) {
            imageView.setImageTintMode(tintMode);
        }
    }
}
