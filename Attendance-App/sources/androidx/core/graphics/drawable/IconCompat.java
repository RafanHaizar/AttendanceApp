package androidx.core.graphics.drawable;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.versionedparcelable.CustomVersionedParcelable;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.svg.SvgConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

public class IconCompat extends CustomVersionedParcelable {
    private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25f;
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float BLUR_FACTOR = 0.010416667f;
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667f;
    static final String EXTRA_INT1 = "int1";
    static final String EXTRA_INT2 = "int2";
    static final String EXTRA_OBJ = "obj";
    static final String EXTRA_STRING1 = "string1";
    static final String EXTRA_TINT_LIST = "tint_list";
    static final String EXTRA_TINT_MODE = "tint_mode";
    static final String EXTRA_TYPE = "type";
    private static final float ICON_DIAMETER_FACTOR = 0.9166667f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334f;
    private static final String TAG = "IconCompat";
    public static final int TYPE_ADAPTIVE_BITMAP = 5;
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_RESOURCE = 2;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_URI = 4;
    public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;
    public byte[] mData = null;
    public int mInt1 = 0;
    public int mInt2 = 0;
    Object mObj1;
    public Parcelable mParcelable = null;
    public String mString1;
    public ColorStateList mTintList = null;
    PorterDuff.Mode mTintMode = DEFAULT_TINT_MODE;
    public String mTintModeStr = null;
    public int mType = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IconType {
    }

    public static IconCompat createWithResource(Context context, int resId) {
        ObjectsCompat.requireNonNull(context);
        return createWithResource(context.getResources(), context.getPackageName(), resId);
    }

    public static IconCompat createWithResource(Resources r, String pkg, int resId) {
        ObjectsCompat.requireNonNull(pkg);
        if (resId != 0) {
            IconCompat rep = new IconCompat(2);
            rep.mInt1 = resId;
            if (r != null) {
                try {
                    rep.mObj1 = r.getResourceName(resId);
                } catch (Resources.NotFoundException e) {
                    throw new IllegalArgumentException("Icon resource cannot be found");
                }
            } else {
                rep.mObj1 = pkg;
            }
            rep.mString1 = pkg;
            return rep;
        }
        throw new IllegalArgumentException("Drawable resource ID must not be 0");
    }

    public static IconCompat createWithBitmap(Bitmap bits) {
        ObjectsCompat.requireNonNull(bits);
        IconCompat rep = new IconCompat(1);
        rep.mObj1 = bits;
        return rep;
    }

    public static IconCompat createWithAdaptiveBitmap(Bitmap bits) {
        ObjectsCompat.requireNonNull(bits);
        IconCompat rep = new IconCompat(5);
        rep.mObj1 = bits;
        return rep;
    }

    public static IconCompat createWithData(byte[] data, int offset, int length) {
        ObjectsCompat.requireNonNull(data);
        IconCompat rep = new IconCompat(3);
        rep.mObj1 = data;
        rep.mInt1 = offset;
        rep.mInt2 = length;
        return rep;
    }

    public static IconCompat createWithContentUri(String uri) {
        ObjectsCompat.requireNonNull(uri);
        IconCompat rep = new IconCompat(4);
        rep.mObj1 = uri;
        return rep;
    }

    public static IconCompat createWithContentUri(Uri uri) {
        ObjectsCompat.requireNonNull(uri);
        return createWithContentUri(uri.toString());
    }

    public static IconCompat createWithAdaptiveBitmapContentUri(String uri) {
        ObjectsCompat.requireNonNull(uri);
        IconCompat rep = new IconCompat(6);
        rep.mObj1 = uri;
        return rep;
    }

    public static IconCompat createWithAdaptiveBitmapContentUri(Uri uri) {
        ObjectsCompat.requireNonNull(uri);
        return createWithAdaptiveBitmapContentUri(uri.toString());
    }

    public IconCompat() {
    }

    IconCompat(int mType2) {
        this.mType = mType2;
    }

    public int getType() {
        if (this.mType != -1 || Build.VERSION.SDK_INT < 23) {
            return this.mType;
        }
        return Api23Impl.getType(this.mObj1);
    }

    public String getResPackage() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getResPackage(this.mObj1);
        }
        if (this.mType == 2) {
            String str = this.mString1;
            if (str == null || TextUtils.isEmpty(str)) {
                return ((String) this.mObj1).split(":", -1)[0];
            }
            return this.mString1;
        }
        throw new IllegalStateException("called getResPackage() on " + this);
    }

    public int getResId() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getResId(this.mObj1);
        }
        if (this.mType == 2) {
            return this.mInt1;
        }
        throw new IllegalStateException("called getResId() on " + this);
    }

    public Bitmap getBitmap() {
        if (this.mType != -1 || Build.VERSION.SDK_INT < 23) {
            int i = this.mType;
            if (i == 1) {
                return (Bitmap) this.mObj1;
            }
            if (i == 5) {
                return createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, true);
            }
            throw new IllegalStateException("called getBitmap() on " + this);
        }
        Object obj = this.mObj1;
        if (obj instanceof Bitmap) {
            return (Bitmap) obj;
        }
        return null;
    }

    public Uri getUri() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getUri(this.mObj1);
        }
        int i = this.mType;
        if (i == 4 || i == 6) {
            return Uri.parse((String) this.mObj1);
        }
        throw new IllegalStateException("called getUri() on " + this);
    }

    public IconCompat setTint(int tint) {
        return setTintList(ColorStateList.valueOf(tint));
    }

    public IconCompat setTintList(ColorStateList tintList) {
        this.mTintList = tintList;
        return this;
    }

    public IconCompat setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        return this;
    }

    @Deprecated
    public Icon toIcon() {
        return toIcon((Context) null);
    }

    public Icon toIcon(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.toIcon(this, context);
        }
        throw new UnsupportedOperationException("This method is only supported on API level 23+");
    }

    public void checkResource(Context context) {
        Object obj;
        if (this.mType == 2 && (obj = this.mObj1) != null) {
            String fullResName = (String) obj;
            if (fullResName.contains(":")) {
                String resName = fullResName.split(":", -1)[1];
                String resType = resName.split("/", -1)[0];
                String resName2 = resName.split("/", -1)[1];
                String resPackage = fullResName.split(":", -1)[0];
                if ("0_resource_name_obfuscated".equals(resName2)) {
                    Log.i(TAG, "Found obfuscated resource, not trying to update resource id for it");
                    return;
                }
                String appPackage = getResPackage();
                int id = getResources(context, appPackage).getIdentifier(resName2, resType, resPackage);
                if (this.mInt1 != id) {
                    Log.i(TAG, "Id has changed for " + appPackage + " " + fullResName);
                    this.mInt1 = id;
                }
            }
        }
    }

    public Drawable loadDrawable(Context context) {
        checkResource(context);
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.loadDrawable(toIcon(context), context);
        }
        Drawable result = loadDrawableInner(context);
        if (!(result == null || (this.mTintList == null && this.mTintMode == DEFAULT_TINT_MODE))) {
            result.mutate();
            DrawableCompat.setTintList(result, this.mTintList);
            DrawableCompat.setTintMode(result, this.mTintMode);
        }
        return result;
    }

    private Drawable loadDrawableInner(Context context) {
        switch (this.mType) {
            case 1:
                return new BitmapDrawable(context.getResources(), (Bitmap) this.mObj1);
            case 2:
                String resPackage = getResPackage();
                if (TextUtils.isEmpty(resPackage)) {
                    resPackage = context.getPackageName();
                }
                try {
                    return ResourcesCompat.getDrawable(getResources(context, resPackage), this.mInt1, context.getTheme());
                } catch (RuntimeException e) {
                    Log.e(TAG, String.format("Unable to load resource 0x%08x from pkg=%s", new Object[]{Integer.valueOf(this.mInt1), this.mObj1}), e);
                    break;
                }
            case 3:
                return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray((byte[]) this.mObj1, this.mInt1, this.mInt2));
            case 4:
                InputStream is = getUriInputStream(context);
                if (is != null) {
                    return new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(is));
                }
                break;
            case 5:
                return new BitmapDrawable(context.getResources(), createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, false));
            case 6:
                InputStream is2 = getUriInputStream(context);
                if (is2 != null) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        return Api26Impl.createAdaptiveIconDrawable((Drawable) null, new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(is2)));
                    }
                    return new BitmapDrawable(context.getResources(), createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream(is2), false));
                }
                break;
        }
        return null;
    }

    public InputStream getUriInputStream(Context context) {
        Uri uri = getUri();
        String scheme = uri.getScheme();
        if ("content".equals(scheme) || "file".equals(scheme)) {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (Exception e) {
                Log.w(TAG, "Unable to load image from URI: " + uri, e);
                return null;
            }
        } else {
            try {
                return new FileInputStream(new File((String) this.mObj1));
            } catch (FileNotFoundException e2) {
                Log.w(TAG, "Unable to load image from path: " + uri, e2);
                return null;
            }
        }
    }

    static Resources getResources(Context context, String resPackage) {
        if ("android".equals(resPackage)) {
            return Resources.getSystem();
        }
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(resPackage, 8192);
            if (ai != null) {
                return pm.getResourcesForApplication(ai);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, String.format("Unable to find pkg=%s for icon", new Object[]{resPackage}), e);
            return null;
        }
    }

    public void addToShortcutIntent(Intent outIntent, Drawable badge, Context c) {
        Bitmap icon;
        Bitmap icon2;
        checkResource(c);
        switch (this.mType) {
            case 1:
                icon = (Bitmap) this.mObj1;
                if (badge != null) {
                    icon = icon.copy(icon.getConfig(), true);
                    break;
                }
                break;
            case 2:
                try {
                    Context context = c.createPackageContext(getResPackage(), 0);
                    if (badge == null) {
                        outIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(context, this.mInt1));
                        return;
                    }
                    Drawable dr = ContextCompat.getDrawable(context, this.mInt1);
                    if (dr.getIntrinsicWidth() > 0) {
                        if (dr.getIntrinsicHeight() > 0) {
                            icon2 = Bitmap.createBitmap(dr.getIntrinsicWidth(), dr.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                            dr.setBounds(0, 0, icon2.getWidth(), icon2.getHeight());
                            dr.draw(new Canvas(icon2));
                            icon = icon2;
                            break;
                        }
                    }
                    int size = ((ActivityManager) context.getSystemService("activity")).getLauncherLargeIconSize();
                    icon2 = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                    dr.setBounds(0, 0, icon2.getWidth(), icon2.getHeight());
                    dr.draw(new Canvas(icon2));
                    icon = icon2;
                } catch (PackageManager.NameNotFoundException e) {
                    throw new IllegalArgumentException("Can't find package " + this.mObj1, e);
                }
            case 5:
                icon = createLegacyIconFromAdaptiveIcon((Bitmap) this.mObj1, true);
                break;
            default:
                throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
        }
        if (badge != null) {
            int w = icon.getWidth();
            int h = icon.getHeight();
            badge.setBounds(w / 2, h / 2, w, h);
            badge.draw(new Canvas(icon));
        }
        outIntent.putExtra("android.intent.extra.shortcut.ICON", icon);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        switch (this.mType) {
            case -1:
                bundle.putParcelable(EXTRA_OBJ, (Parcelable) this.mObj1);
                break;
            case 1:
            case 5:
                bundle.putParcelable(EXTRA_OBJ, (Bitmap) this.mObj1);
                break;
            case 2:
            case 4:
            case 6:
                bundle.putString(EXTRA_OBJ, (String) this.mObj1);
                break;
            case 3:
                bundle.putByteArray(EXTRA_OBJ, (byte[]) this.mObj1);
                break;
            default:
                throw new IllegalArgumentException("Invalid icon");
        }
        bundle.putInt("type", this.mType);
        bundle.putInt(EXTRA_INT1, this.mInt1);
        bundle.putInt(EXTRA_INT2, this.mInt2);
        bundle.putString(EXTRA_STRING1, this.mString1);
        ColorStateList colorStateList = this.mTintList;
        if (colorStateList != null) {
            bundle.putParcelable(EXTRA_TINT_LIST, colorStateList);
        }
        PorterDuff.Mode mode = this.mTintMode;
        if (mode != DEFAULT_TINT_MODE) {
            bundle.putString(EXTRA_TINT_MODE, mode.name());
        }
        return bundle;
    }

    public String toString() {
        if (this.mType == -1) {
            return String.valueOf(this.mObj1);
        }
        StringBuilder sb = new StringBuilder("Icon(typ=").append(typeToString(this.mType));
        switch (this.mType) {
            case 1:
            case 5:
                sb.append(" size=").append(((Bitmap) this.mObj1).getWidth()).append(SvgConstants.Attributes.f1641X).append(((Bitmap) this.mObj1).getHeight());
                break;
            case 2:
                sb.append(" pkg=").append(this.mString1).append(" id=").append(String.format("0x%08x", new Object[]{Integer.valueOf(getResId())}));
                break;
            case 3:
                sb.append(" len=").append(this.mInt1);
                if (this.mInt2 != 0) {
                    sb.append(" off=").append(this.mInt2);
                    break;
                }
                break;
            case 4:
            case 6:
                sb.append(" uri=").append(this.mObj1);
                break;
        }
        if (this.mTintList != null) {
            sb.append(" tint=");
            sb.append(this.mTintList);
        }
        if (this.mTintMode != DEFAULT_TINT_MODE) {
            sb.append(" mode=").append(this.mTintMode);
        }
        sb.append(")");
        return sb.toString();
    }

    public void onPreParceling(boolean isStream) {
        this.mTintModeStr = this.mTintMode.name();
        switch (this.mType) {
            case -1:
                if (!isStream) {
                    this.mParcelable = (Parcelable) this.mObj1;
                    return;
                }
                throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
            case 1:
            case 5:
                if (isStream) {
                    ByteArrayOutputStream data = new ByteArrayOutputStream();
                    ((Bitmap) this.mObj1).compress(Bitmap.CompressFormat.PNG, 90, data);
                    this.mData = data.toByteArray();
                    return;
                }
                this.mParcelable = (Parcelable) this.mObj1;
                return;
            case 2:
                this.mData = ((String) this.mObj1).getBytes(Charset.forName("UTF-16"));
                return;
            case 3:
                this.mData = (byte[]) this.mObj1;
                return;
            case 4:
            case 6:
                this.mData = this.mObj1.toString().getBytes(Charset.forName("UTF-16"));
                return;
            default:
                return;
        }
    }

    public void onPostParceling() {
        this.mTintMode = PorterDuff.Mode.valueOf(this.mTintModeStr);
        switch (this.mType) {
            case -1:
                Parcelable parcelable = this.mParcelable;
                if (parcelable != null) {
                    this.mObj1 = parcelable;
                    return;
                }
                throw new IllegalArgumentException("Invalid icon");
            case 1:
            case 5:
                Parcelable parcelable2 = this.mParcelable;
                if (parcelable2 != null) {
                    this.mObj1 = parcelable2;
                    return;
                }
                byte[] bArr = this.mData;
                this.mObj1 = bArr;
                this.mType = 3;
                this.mInt1 = 0;
                this.mInt2 = bArr.length;
                return;
            case 2:
            case 4:
            case 6:
                String str = new String(this.mData, Charset.forName("UTF-16"));
                this.mObj1 = str;
                if (this.mType == 2 && this.mString1 == null) {
                    String str2 = str;
                    this.mString1 = str.split(":", -1)[0];
                    return;
                }
                return;
            case 3:
                this.mObj1 = this.mData;
                return;
            default:
                return;
        }
    }

    private static String typeToString(int x) {
        switch (x) {
            case 1:
                return "BITMAP";
            case 2:
                return "RESOURCE";
            case 3:
                return "DATA";
            case 4:
                return XfdfConstants.URI;
            case 5:
                return "BITMAP_MASKABLE";
            case 6:
                return "URI_MASKABLE";
            default:
                return "UNKNOWN";
        }
    }

    public static IconCompat createFromBundle(Bundle bundle) {
        int type = bundle.getInt("type");
        IconCompat icon = new IconCompat(type);
        icon.mInt1 = bundle.getInt(EXTRA_INT1);
        icon.mInt2 = bundle.getInt(EXTRA_INT2);
        icon.mString1 = bundle.getString(EXTRA_STRING1);
        if (bundle.containsKey(EXTRA_TINT_LIST)) {
            icon.mTintList = (ColorStateList) bundle.getParcelable(EXTRA_TINT_LIST);
        }
        if (bundle.containsKey(EXTRA_TINT_MODE)) {
            icon.mTintMode = PorterDuff.Mode.valueOf(bundle.getString(EXTRA_TINT_MODE));
        }
        switch (type) {
            case -1:
            case 1:
            case 5:
                icon.mObj1 = bundle.getParcelable(EXTRA_OBJ);
                break;
            case 2:
            case 4:
            case 6:
                icon.mObj1 = bundle.getString(EXTRA_OBJ);
                break;
            case 3:
                icon.mObj1 = bundle.getByteArray(EXTRA_OBJ);
                break;
            default:
                Log.w(TAG, "Unknown type " + type);
                return null;
        }
        return icon;
    }

    public static IconCompat createFromIcon(Context context, Icon icon) {
        Preconditions.checkNotNull(icon);
        return Api23Impl.createFromIcon(context, icon);
    }

    public static IconCompat createFromIcon(Icon icon) {
        return Api23Impl.createFromIconInner(icon);
    }

    public static IconCompat createFromIconOrNullIfZeroResId(Icon icon) {
        if (Api23Impl.getType(icon) == 2 && Api23Impl.getResId(icon) == 0) {
            return null;
        }
        return Api23Impl.createFromIconInner(icon);
    }

    static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap adaptiveIconBitmap, boolean addShadow) {
        int size = (int) (((float) Math.min(adaptiveIconBitmap.getWidth(), adaptiveIconBitmap.getHeight())) * DEFAULT_VIEW_PORT_SCALE);
        Bitmap icon = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(icon);
        Paint paint = new Paint(3);
        float center = ((float) size) * 0.5f;
        float radius = ICON_DIAMETER_FACTOR * center;
        if (addShadow) {
            float blur = ((float) size) * BLUR_FACTOR;
            paint.setColor(0);
            paint.setShadowLayer(blur, 0.0f, ((float) size) * KEY_SHADOW_OFFSET_FACTOR, 1023410176);
            canvas.drawCircle(center, center, radius, paint);
            paint.setShadowLayer(blur, 0.0f, 0.0f, 503316480);
            canvas.drawCircle(center, center, radius, paint);
            paint.clearShadowLayer();
        }
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        BitmapShader shader = new BitmapShader(adaptiveIconBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix shift = new Matrix();
        shift.setTranslate(((float) (-(adaptiveIconBitmap.getWidth() - size))) / 2.0f, ((float) (-(adaptiveIconBitmap.getHeight() - size))) / 2.0f);
        shader.setLocalMatrix(shift);
        paint.setShader(shader);
        canvas.drawCircle(center, center, radius, paint);
        canvas.setBitmap((Bitmap) null);
        return icon;
    }

    static class Api28Impl {
        private Api28Impl() {
        }

        static String getResPackage(Object icon) {
            return ((Icon) icon).getResPackage();
        }

        static int getType(Object icon) {
            return ((Icon) icon).getType();
        }

        static int getResId(Object icon) {
            return ((Icon) icon).getResId();
        }

        static Uri getUri(Object icon) {
            return ((Icon) icon).getUri();
        }
    }

    static class Api26Impl {
        private Api26Impl() {
        }

        static Drawable createAdaptiveIconDrawable(Drawable backgroundDrawable, Drawable foregroundDrawable) {
            return new AdaptiveIconDrawable(backgroundDrawable, foregroundDrawable);
        }

        static Icon createWithAdaptiveBitmap(Bitmap bits) {
            return Icon.createWithAdaptiveBitmap(bits);
        }
    }

    static class Api30Impl {
        private Api30Impl() {
        }

        static Icon createWithAdaptiveBitmapContentUri(Uri uri) {
            return Icon.createWithAdaptiveBitmapContentUri(uri);
        }
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static IconCompat createFromIcon(Context context, Icon icon) {
            switch (getType(icon)) {
                case 2:
                    String resPackage = getResPackage(icon);
                    try {
                        return IconCompat.createWithResource(IconCompat.getResources(context, resPackage), resPackage, getResId(icon));
                    } catch (Resources.NotFoundException e) {
                        throw new IllegalArgumentException("Icon resource cannot be found");
                    }
                case 4:
                    return IconCompat.createWithContentUri(getUri(icon));
                case 6:
                    return IconCompat.createWithAdaptiveBitmapContentUri(getUri(icon));
                default:
                    IconCompat iconCompat = new IconCompat(-1);
                    iconCompat.mObj1 = icon;
                    return iconCompat;
            }
        }

        static int getType(Object icon) {
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.getType(icon);
            }
            try {
                return ((Integer) icon.getClass().getMethod("getType", new Class[0]).invoke(icon, new Object[0])).intValue();
            } catch (IllegalAccessException e) {
                Log.e(IconCompat.TAG, "Unable to get icon type " + icon, e);
                return -1;
            } catch (InvocationTargetException e2) {
                Log.e(IconCompat.TAG, "Unable to get icon type " + icon, e2);
                return -1;
            } catch (NoSuchMethodException e3) {
                Log.e(IconCompat.TAG, "Unable to get icon type " + icon, e3);
                return -1;
            }
        }

        static String getResPackage(Object icon) {
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.getResPackage(icon);
            }
            try {
                return (String) icon.getClass().getMethod("getResPackage", new Class[0]).invoke(icon, new Object[0]);
            } catch (IllegalAccessException e) {
                Log.e(IconCompat.TAG, "Unable to get icon package", e);
                return null;
            } catch (InvocationTargetException e2) {
                Log.e(IconCompat.TAG, "Unable to get icon package", e2);
                return null;
            } catch (NoSuchMethodException e3) {
                Log.e(IconCompat.TAG, "Unable to get icon package", e3);
                return null;
            }
        }

        static IconCompat createFromIconInner(Object icon) {
            Preconditions.checkNotNull(icon);
            switch (getType(icon)) {
                case 2:
                    return IconCompat.createWithResource((Resources) null, getResPackage(icon), getResId(icon));
                case 4:
                    return IconCompat.createWithContentUri(getUri(icon));
                case 6:
                    return IconCompat.createWithAdaptiveBitmapContentUri(getUri(icon));
                default:
                    IconCompat iconCompat = new IconCompat(-1);
                    iconCompat.mObj1 = icon;
                    return iconCompat;
            }
        }

        static int getResId(Object icon) {
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.getResId(icon);
            }
            try {
                return ((Integer) icon.getClass().getMethod("getResId", new Class[0]).invoke(icon, new Object[0])).intValue();
            } catch (IllegalAccessException e) {
                Log.e(IconCompat.TAG, "Unable to get icon resource", e);
                return 0;
            } catch (InvocationTargetException e2) {
                Log.e(IconCompat.TAG, "Unable to get icon resource", e2);
                return 0;
            } catch (NoSuchMethodException e3) {
                Log.e(IconCompat.TAG, "Unable to get icon resource", e3);
                return 0;
            }
        }

        static Uri getUri(Object icon) {
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.getUri(icon);
            }
            try {
                return (Uri) icon.getClass().getMethod("getUri", new Class[0]).invoke(icon, new Object[0]);
            } catch (IllegalAccessException e) {
                Log.e(IconCompat.TAG, "Unable to get icon uri", e);
                return null;
            } catch (InvocationTargetException e2) {
                Log.e(IconCompat.TAG, "Unable to get icon uri", e2);
                return null;
            } catch (NoSuchMethodException e3) {
                Log.e(IconCompat.TAG, "Unable to get icon uri", e3);
                return null;
            }
        }

        static Icon toIcon(IconCompat iconCompat, Context context) {
            Icon icon;
            switch (iconCompat.mType) {
                case -1:
                    return (Icon) iconCompat.mObj1;
                case 1:
                    icon = Icon.createWithBitmap((Bitmap) iconCompat.mObj1);
                    break;
                case 2:
                    icon = Icon.createWithResource(iconCompat.getResPackage(), iconCompat.mInt1);
                    break;
                case 3:
                    icon = Icon.createWithData((byte[]) iconCompat.mObj1, iconCompat.mInt1, iconCompat.mInt2);
                    break;
                case 4:
                    icon = Icon.createWithContentUri((String) iconCompat.mObj1);
                    break;
                case 5:
                    if (Build.VERSION.SDK_INT < 26) {
                        icon = Icon.createWithBitmap(IconCompat.createLegacyIconFromAdaptiveIcon((Bitmap) iconCompat.mObj1, false));
                        break;
                    } else {
                        icon = Api26Impl.createWithAdaptiveBitmap((Bitmap) iconCompat.mObj1);
                        break;
                    }
                case 6:
                    if (Build.VERSION.SDK_INT >= 30) {
                        icon = Api30Impl.createWithAdaptiveBitmapContentUri(iconCompat.getUri());
                        break;
                    } else if (context != null) {
                        InputStream is = iconCompat.getUriInputStream(context);
                        if (is != null) {
                            if (Build.VERSION.SDK_INT < 26) {
                                icon = Icon.createWithBitmap(IconCompat.createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream(is), false));
                                break;
                            } else {
                                icon = Api26Impl.createWithAdaptiveBitmap(BitmapFactory.decodeStream(is));
                                break;
                            }
                        } else {
                            throw new IllegalStateException("Cannot load adaptive icon from uri: " + iconCompat.getUri());
                        }
                    } else {
                        throw new IllegalArgumentException("Context is required to resolve the file uri of the icon: " + iconCompat.getUri());
                    }
                default:
                    throw new IllegalArgumentException("Unknown type");
            }
            if (iconCompat.mTintList != null) {
                icon.setTintList(iconCompat.mTintList);
            }
            if (iconCompat.mTintMode != IconCompat.DEFAULT_TINT_MODE) {
                icon.setTintMode(iconCompat.mTintMode);
            }
            return icon;
        }

        static Drawable loadDrawable(Icon icon, Context context) {
            return icon.loadDrawable(context);
        }
    }
}
