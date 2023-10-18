package p004pl.droidsonroids.gif;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.bouncycastle.crypto.tls.CipherSuite;

/* renamed from: pl.droidsonroids.gif.GifViewUtils */
final class GifViewUtils {
    static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    static final List<String> SUPPORTED_RESOURCE_TYPE_NAMES = Arrays.asList(new String[]{"raw", "drawable", "mipmap"});

    private GifViewUtils() {
    }

    static GifImageViewAttributes initImageView(ImageView view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs == null || view.isInEditMode()) {
            return new GifImageViewAttributes();
        }
        GifImageViewAttributes viewAttributes = new GifImageViewAttributes(view, attrs, defStyleAttr, defStyleRes);
        int loopCount = viewAttributes.mLoopCount;
        if (loopCount >= 0) {
            applyLoopCount(loopCount, view.getDrawable());
            applyLoopCount(loopCount, view.getBackground());
        }
        return viewAttributes;
    }

    static void applyLoopCount(int loopCount, Drawable drawable) {
        if (drawable instanceof GifDrawable) {
            ((GifDrawable) drawable).setLoopCount(loopCount);
        }
    }

    static boolean setResource(ImageView view, boolean isSrc, int resId) {
        Resources res = view.getResources();
        if (res != null) {
            try {
                if (!SUPPORTED_RESOURCE_TYPE_NAMES.contains(res.getResourceTypeName(resId))) {
                    return false;
                }
                GifDrawable d = new GifDrawable(res, resId);
                if (isSrc) {
                    view.setImageDrawable(d);
                    return true;
                }
                view.setBackground(d);
                return true;
            } catch (Resources.NotFoundException | IOException e) {
            }
        }
        return false;
    }

    static boolean setGifImageUri(ImageView imageView, Uri uri) {
        if (uri == null) {
            return false;
        }
        try {
            imageView.setImageDrawable(new GifDrawable(imageView.getContext().getContentResolver(), uri));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    static float getDensityScale(Resources res, int id) {
        int density;
        TypedValue value = new TypedValue();
        res.getValue(id, value, true);
        int resourceDensity = value.density;
        if (resourceDensity == 0) {
            density = CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
        } else if (resourceDensity != 65535) {
            density = resourceDensity;
        } else {
            density = 0;
        }
        int targetDensity = res.getDisplayMetrics().densityDpi;
        if (density <= 0 || targetDensity <= 0) {
            return 1.0f;
        }
        return ((float) targetDensity) / ((float) density);
    }

    /* renamed from: pl.droidsonroids.gif.GifViewUtils$GifViewAttributes */
    static class GifViewAttributes {
        boolean freezesAnimation;
        final int mLoopCount;

        GifViewAttributes(View view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            TypedArray gifViewAttributes = view.getContext().obtainStyledAttributes(attrs, C0480R.styleable.GifView, defStyleAttr, defStyleRes);
            this.freezesAnimation = gifViewAttributes.getBoolean(C0480R.styleable.GifView_freezesAnimation, false);
            this.mLoopCount = gifViewAttributes.getInt(C0480R.styleable.GifView_loopCount, -1);
            gifViewAttributes.recycle();
        }

        GifViewAttributes() {
            this.freezesAnimation = false;
            this.mLoopCount = -1;
        }
    }

    /* renamed from: pl.droidsonroids.gif.GifViewUtils$GifImageViewAttributes */
    static class GifImageViewAttributes extends GifViewAttributes {
        final int mBackgroundResId;
        final int mSourceResId;

        GifImageViewAttributes(ImageView view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(view, attrs, defStyleAttr, defStyleRes);
            this.mSourceResId = getResourceId(view, attrs, true);
            this.mBackgroundResId = getResourceId(view, attrs, false);
        }

        GifImageViewAttributes() {
            this.mSourceResId = 0;
            this.mBackgroundResId = 0;
        }

        private static int getResourceId(ImageView view, AttributeSet attrs, boolean isSrc) {
            int resId = attrs.getAttributeResourceValue(GifViewUtils.ANDROID_NS, isSrc ? "src" : CommonCssConstants.BACKGROUND, 0);
            if (resId > 0) {
                if (!GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES.contains(view.getResources().getResourceTypeName(resId)) || GifViewUtils.setResource(view, isSrc, resId)) {
                    return 0;
                }
                return resId;
            }
            return 0;
        }
    }
}
