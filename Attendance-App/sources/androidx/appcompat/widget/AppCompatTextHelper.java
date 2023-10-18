package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.appcompat.C0503R;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.WeakReference;
import java.util.Locale;

class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
    private boolean mAsyncFontPending;
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mFontWeight = -1;
    private int mStyle = 0;
    private final TextView mView;

    AppCompatTextHelper(TextView view) {
        this.mView = view;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(view);
    }

    /* access modifiers changed from: package-private */
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        boolean allCapsSet;
        boolean allCaps;
        ColorStateList textColorLink;
        ColorStateList textColor;
        String localeListString;
        String fontVariation;
        AppCompatDrawableManager drawableManager;
        String localeListString2;
        AppCompatDrawableManager drawableManager2;
        Drawable drawableBottom;
        AttributeSet attributeSet = attrs;
        int i = defStyleAttr;
        Context context = this.mView.getContext();
        AppCompatDrawableManager drawableManager3 = AppCompatDrawableManager.get();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attributeSet, C0503R.styleable.AppCompatTextHelper, i, 0);
        TextView textView = this.mView;
        ViewCompat.saveAttributeDataForStyleable(textView, textView.getContext(), C0503R.styleable.AppCompatTextHelper, attrs, a.getWrappedTypeArray(), defStyleAttr, 0);
        int ap = a.getResourceId(C0503R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (a.hasValue(C0503R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = createTintInfo(context, drawableManager3, a.getResourceId(C0503R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (a.hasValue(C0503R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = createTintInfo(context, drawableManager3, a.getResourceId(C0503R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (a.hasValue(C0503R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = createTintInfo(context, drawableManager3, a.getResourceId(C0503R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (a.hasValue(C0503R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = createTintInfo(context, drawableManager3, a.getResourceId(C0503R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        if (a.hasValue(C0503R.styleable.AppCompatTextHelper_android_drawableStart)) {
            this.mDrawableStartTint = createTintInfo(context, drawableManager3, a.getResourceId(C0503R.styleable.AppCompatTextHelper_android_drawableStart, 0));
        }
        if (a.hasValue(C0503R.styleable.AppCompatTextHelper_android_drawableEnd)) {
            this.mDrawableEndTint = createTintInfo(context, drawableManager3, a.getResourceId(C0503R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
        }
        a.recycle();
        boolean hasPwdTm = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        boolean allCaps2 = false;
        boolean allCapsSet2 = false;
        ColorStateList textColor2 = null;
        ColorStateList textColorHint = null;
        ColorStateList textColorLink2 = null;
        String fontVariation2 = null;
        String localeListString3 = null;
        if (ap != -1) {
            TintTypedArray a2 = TintTypedArray.obtainStyledAttributes(context, ap, C0503R.styleable.TextAppearance);
            if (!hasPwdTm && a2.hasValue(C0503R.styleable.TextAppearance_textAllCaps)) {
                allCapsSet2 = true;
                allCaps2 = a2.getBoolean(C0503R.styleable.TextAppearance_textAllCaps, false);
            }
            updateTypefaceAndStyle(context, a2);
            if (Build.VERSION.SDK_INT < 23) {
                if (a2.hasValue(C0503R.styleable.TextAppearance_android_textColor)) {
                    textColor2 = a2.getColorStateList(C0503R.styleable.TextAppearance_android_textColor);
                }
                if (a2.hasValue(C0503R.styleable.TextAppearance_android_textColorHint)) {
                    textColorHint = a2.getColorStateList(C0503R.styleable.TextAppearance_android_textColorHint);
                }
                if (a2.hasValue(C0503R.styleable.TextAppearance_android_textColorLink)) {
                    textColorLink2 = a2.getColorStateList(C0503R.styleable.TextAppearance_android_textColorLink);
                }
            }
            if (a2.hasValue(C0503R.styleable.TextAppearance_textLocale)) {
                localeListString3 = a2.getString(C0503R.styleable.TextAppearance_textLocale);
            }
            if (Build.VERSION.SDK_INT >= 26 && a2.hasValue(C0503R.styleable.TextAppearance_fontVariationSettings)) {
                fontVariation2 = a2.getString(C0503R.styleable.TextAppearance_fontVariationSettings);
            }
            a2.recycle();
        }
        TintTypedArray a3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, C0503R.styleable.TextAppearance, i, 0);
        if (hasPwdTm || !a3.hasValue(C0503R.styleable.TextAppearance_textAllCaps)) {
            allCaps = allCaps2;
            allCapsSet = allCapsSet2;
        } else {
            allCaps = a3.getBoolean(C0503R.styleable.TextAppearance_textAllCaps, false);
            allCapsSet = true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (a3.hasValue(C0503R.styleable.TextAppearance_android_textColor)) {
                textColor2 = a3.getColorStateList(C0503R.styleable.TextAppearance_android_textColor);
            }
            if (a3.hasValue(C0503R.styleable.TextAppearance_android_textColorHint)) {
                textColorHint = a3.getColorStateList(C0503R.styleable.TextAppearance_android_textColorHint);
            }
            if (a3.hasValue(C0503R.styleable.TextAppearance_android_textColorLink)) {
                textColorLink = textColor2;
                textColor = a3.getColorStateList(C0503R.styleable.TextAppearance_android_textColorLink);
            } else {
                ColorStateList colorStateList = textColorLink2;
                textColorLink = textColor2;
                textColor = colorStateList;
            }
        } else {
            ColorStateList colorStateList2 = textColorLink2;
            textColorLink = textColor2;
            textColor = colorStateList2;
        }
        if (a3.hasValue(C0503R.styleable.TextAppearance_textLocale)) {
            localeListString = a3.getString(C0503R.styleable.TextAppearance_textLocale);
        } else {
            localeListString = localeListString3;
        }
        String fontVariation3 = fontVariation2;
        if (Build.VERSION.SDK_INT < 26 || !a3.hasValue(C0503R.styleable.TextAppearance_fontVariationSettings)) {
            fontVariation = fontVariation3;
        } else {
            fontVariation = a3.getString(C0503R.styleable.TextAppearance_fontVariationSettings);
        }
        int i2 = ap;
        if (Build.VERSION.SDK_INT < 28) {
            drawableManager = drawableManager3;
        } else if (!a3.hasValue(C0503R.styleable.TextAppearance_android_textSize)) {
            drawableManager = drawableManager3;
        } else if (a3.getDimensionPixelSize(C0503R.styleable.TextAppearance_android_textSize, -1) == 0) {
            drawableManager = drawableManager3;
            this.mView.setTextSize(0, 0.0f);
        } else {
            drawableManager = drawableManager3;
        }
        updateTypefaceAndStyle(context, a3);
        a3.recycle();
        if (textColorLink != null) {
            this.mView.setTextColor(textColorLink);
        }
        if (textColorHint != null) {
            this.mView.setHintTextColor(textColorHint);
        }
        if (textColor != null) {
            this.mView.setLinkTextColor(textColor);
        }
        if (!hasPwdTm && allCapsSet) {
            setAllCaps(allCaps);
        }
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            if (this.mFontWeight == -1) {
                this.mView.setTypeface(typeface, this.mStyle);
            } else {
                this.mView.setTypeface(typeface);
            }
        }
        if (fontVariation != null) {
            Api26Impl.setFontVariationSettings(this.mView, fontVariation);
        }
        if (localeListString != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                Api24Impl.setTextLocales(this.mView, Api24Impl.forLanguageTags(localeListString));
            } else if (Build.VERSION.SDK_INT >= 21) {
                Api17Impl.setTextLocale(this.mView, Api21Impl.forLanguageTag(localeListString.split(",")[0]));
            }
        }
        this.mAutoSizeTextHelper.loadFromAttributes(attributeSet, i);
        if (!ViewUtils.SDK_LEVEL_SUPPORTS_AUTOSIZE) {
            localeListString2 = localeListString;
            ColorStateList colorStateList3 = textColor;
            ColorStateList colorStateList4 = textColorHint;
        } else if (this.mAutoSizeTextHelper.getAutoSizeTextType() != 0) {
            int[] autoSizeTextSizesInPx = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
            if (autoSizeTextSizesInPx.length <= 0) {
                localeListString2 = localeListString;
                ColorStateList colorStateList5 = textColor;
                ColorStateList colorStateList6 = textColorHint;
            } else if (((float) Api26Impl.getAutoSizeStepGranularity(this.mView)) != -1.0f) {
                localeListString2 = localeListString;
                ColorStateList colorStateList7 = textColor;
                ColorStateList colorStateList8 = textColorHint;
                Api26Impl.setAutoSizeTextTypeUniformWithConfiguration(this.mView, this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
            } else {
                localeListString2 = localeListString;
                ColorStateList colorStateList9 = textColor;
                ColorStateList colorStateList10 = textColorHint;
                Api26Impl.setAutoSizeTextTypeUniformWithPresetSizes(this.mView, autoSizeTextSizesInPx, 0);
            }
        } else {
            localeListString2 = localeListString;
            ColorStateList colorStateList11 = textColor;
            ColorStateList colorStateList12 = textColorHint;
        }
        TintTypedArray a4 = TintTypedArray.obtainStyledAttributes(context, attributeSet, C0503R.styleable.AppCompatTextView);
        Drawable drawableStart = null;
        Drawable drawableEnd = null;
        Drawable drawableLeft = null;
        Drawable drawableTop = null;
        int drawableLeftId = a4.getResourceId(C0503R.styleable.AppCompatTextView_drawableLeftCompat, -1);
        if (drawableLeftId != -1) {
            drawableManager2 = drawableManager;
            drawableLeft = drawableManager2.getDrawable(context, drawableLeftId);
        } else {
            drawableManager2 = drawableManager;
        }
        int i3 = drawableLeftId;
        Drawable drawableRight = null;
        int drawableTopId = a4.getResourceId(C0503R.styleable.AppCompatTextView_drawableTopCompat, -1);
        if (drawableTopId != -1) {
            drawableTop = drawableManager2.getDrawable(context, drawableTopId);
        }
        int i4 = drawableTopId;
        int drawableRightId = a4.getResourceId(C0503R.styleable.AppCompatTextView_drawableRightCompat, -1);
        if (drawableRightId != -1) {
            drawableRight = drawableManager2.getDrawable(context, drawableRightId);
        }
        int i5 = drawableRightId;
        int drawableBottomId = a4.getResourceId(C0503R.styleable.AppCompatTextView_drawableBottomCompat, -1);
        if (drawableBottomId != -1) {
            drawableBottom = drawableManager2.getDrawable(context, drawableBottomId);
        } else {
            drawableBottom = null;
        }
        int drawableStartId = a4.getResourceId(C0503R.styleable.AppCompatTextView_drawableStartCompat, -1);
        if (drawableStartId != -1) {
            drawableStart = drawableManager2.getDrawable(context, drawableStartId);
        }
        int i6 = drawableBottomId;
        int drawableEndId = a4.getResourceId(C0503R.styleable.AppCompatTextView_drawableEndCompat, -1);
        if (drawableEndId != -1) {
            drawableEnd = drawableManager2.getDrawable(context, drawableEndId);
        }
        int i7 = drawableEndId;
        String str = localeListString2;
        AppCompatDrawableManager appCompatDrawableManager = drawableManager2;
        int i8 = drawableStartId;
        ColorStateList colorStateList13 = textColorLink;
        String str2 = fontVariation;
        boolean z = hasPwdTm;
        setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom, drawableStart, drawableEnd);
        if (a4.hasValue(C0503R.styleable.AppCompatTextView_drawableTint)) {
            TextViewCompat.setCompoundDrawableTintList(this.mView, a4.getColorStateList(C0503R.styleable.AppCompatTextView_drawableTint));
        }
        if (a4.hasValue(C0503R.styleable.AppCompatTextView_drawableTintMode)) {
            TextViewCompat.setCompoundDrawableTintMode(this.mView, DrawableUtils.parseTintMode(a4.getInt(C0503R.styleable.AppCompatTextView_drawableTintMode, -1), (PorterDuff.Mode) null));
        }
        int firstBaselineToTopHeight = a4.getDimensionPixelSize(C0503R.styleable.AppCompatTextView_firstBaselineToTopHeight, -1);
        int lastBaselineToBottomHeight = a4.getDimensionPixelSize(C0503R.styleable.AppCompatTextView_lastBaselineToBottomHeight, -1);
        int lineHeight = a4.getDimensionPixelSize(C0503R.styleable.AppCompatTextView_lineHeight, -1);
        a4.recycle();
        if (firstBaselineToTopHeight != -1) {
            TextViewCompat.setFirstBaselineToTopHeight(this.mView, firstBaselineToTopHeight);
        }
        if (lastBaselineToBottomHeight != -1) {
            TextViewCompat.setLastBaselineToBottomHeight(this.mView, lastBaselineToBottomHeight);
        }
        if (lineHeight != -1) {
            TextViewCompat.setLineHeight(this.mView, lineHeight);
        }
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray a) {
        int fontFamilyId;
        String fontFamilyName;
        this.mStyle = a.getInt(C0503R.styleable.TextAppearance_android_textStyle, this.mStyle);
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 28) {
            int i = a.getInt(C0503R.styleable.TextAppearance_android_textFontWeight, -1);
            this.mFontWeight = i;
            if (i != -1) {
                this.mStyle = (this.mStyle & 2) | 0;
            }
        }
        if (a.hasValue(C0503R.styleable.TextAppearance_android_fontFamily) || a.hasValue(C0503R.styleable.TextAppearance_fontFamily)) {
            this.mFontTypeface = null;
            if (a.hasValue(C0503R.styleable.TextAppearance_fontFamily)) {
                fontFamilyId = C0503R.styleable.TextAppearance_fontFamily;
            } else {
                fontFamilyId = C0503R.styleable.TextAppearance_android_fontFamily;
            }
            final int fontWeight = this.mFontWeight;
            final int style = this.mStyle;
            if (!context.isRestricted()) {
                final WeakReference<TextView> textViewWeak = new WeakReference<>(this.mView);
                try {
                    Typeface typeface = a.getFont(fontFamilyId, this.mStyle, new ResourcesCompat.FontCallback() {
                        public void onFontRetrieved(Typeface typeface) {
                            int i;
                            if (Build.VERSION.SDK_INT >= 28 && (i = fontWeight) != -1) {
                                typeface = Api28Impl.create(typeface, i, (style & 2) != 0);
                            }
                            AppCompatTextHelper.this.onAsyncTypefaceReceived(textViewWeak, typeface);
                        }

                        public void onFontRetrievalFailed(int reason) {
                        }
                    });
                    if (typeface != null) {
                        if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
                            this.mFontTypeface = typeface;
                        } else {
                            this.mFontTypeface = Api28Impl.create(Typeface.create(typeface, 0), this.mFontWeight, (this.mStyle & 2) != 0);
                        }
                    }
                    this.mAsyncFontPending = this.mFontTypeface == null;
                } catch (Resources.NotFoundException | UnsupportedOperationException e) {
                }
            }
            if (this.mFontTypeface == null && (fontFamilyName = a.getString(fontFamilyId)) != null) {
                if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
                    this.mFontTypeface = Typeface.create(fontFamilyName, this.mStyle);
                    return;
                }
                Typeface create = Typeface.create(fontFamilyName, 0);
                int i2 = this.mFontWeight;
                if ((this.mStyle & 2) != 0) {
                    z = true;
                }
                this.mFontTypeface = Api28Impl.create(create, i2, z);
            }
        } else if (a.hasValue(C0503R.styleable.TextAppearance_android_typeface)) {
            this.mAsyncFontPending = false;
            switch (a.getInt(C0503R.styleable.TextAppearance_android_typeface, 1)) {
                case 1:
                    this.mFontTypeface = Typeface.SANS_SERIF;
                    return;
                case 2:
                    this.mFontTypeface = Typeface.SERIF;
                    return;
                case 3:
                    this.mFontTypeface = Typeface.MONOSPACE;
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onAsyncTypefaceReceived(WeakReference<TextView> textViewWeak, final Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface;
            final TextView textView = (TextView) textViewWeak.get();
            if (textView == null) {
                return;
            }
            if (ViewCompat.isAttachedToWindow(textView)) {
                final int style = this.mStyle;
                textView.post(new Runnable() {
                    public void run() {
                        textView.setTypeface(typeface, style);
                    }
                });
                return;
            }
            textView.setTypeface(typeface, this.mStyle);
        }
    }

    /* access modifiers changed from: package-private */
    public void onSetTextAppearance(Context context, int resId) {
        String fontVariation;
        ColorStateList textColorHint;
        ColorStateList textColorLink;
        ColorStateList textColor;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, resId, C0503R.styleable.TextAppearance);
        if (a.hasValue(C0503R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(a.getBoolean(C0503R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (a.hasValue(C0503R.styleable.TextAppearance_android_textColor) && (textColor = a.getColorStateList(C0503R.styleable.TextAppearance_android_textColor)) != null) {
                this.mView.setTextColor(textColor);
            }
            if (a.hasValue(C0503R.styleable.TextAppearance_android_textColorLink) && (textColorLink = a.getColorStateList(C0503R.styleable.TextAppearance_android_textColorLink)) != null) {
                this.mView.setLinkTextColor(textColorLink);
            }
            if (a.hasValue(C0503R.styleable.TextAppearance_android_textColorHint) && (textColorHint = a.getColorStateList(C0503R.styleable.TextAppearance_android_textColorHint)) != null) {
                this.mView.setHintTextColor(textColorHint);
            }
        }
        if (a.hasValue(C0503R.styleable.TextAppearance_android_textSize) && a.getDimensionPixelSize(C0503R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, a);
        if (Build.VERSION.SDK_INT >= 26 && a.hasValue(C0503R.styleable.TextAppearance_fontVariationSettings) && (fontVariation = a.getString(C0503R.styleable.TextAppearance_fontVariationSettings)) != null) {
            Api26Impl.setFontVariationSettings(this.mView, fontVariation);
        }
        a.recycle();
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            this.mView.setTypeface(typeface, this.mStyle);
        }
    }

    /* access modifiers changed from: package-private */
    public void setAllCaps(boolean allCaps) {
        this.mView.setAllCaps(allCaps);
    }

    /* access modifiers changed from: package-private */
    public void onSetCompoundDrawables() {
        applyCompoundDrawablesTints();
    }

    /* access modifiers changed from: package-private */
    public void applyCompoundDrawablesTints() {
        if (!(this.mDrawableLeftTint == null && this.mDrawableTopTint == null && this.mDrawableRightTint == null && this.mDrawableBottomTint == null)) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            Drawable[] compoundDrawables2 = Api17Impl.getCompoundDrawablesRelative(this.mView);
            applyCompoundDrawableTint(compoundDrawables2[0], this.mDrawableStartTint);
            applyCompoundDrawableTint(compoundDrawables2[2], this.mDrawableEndTint);
        }
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo info) {
        if (drawable != null && info != null) {
            AppCompatDrawableManager.tintDrawable(drawable, info, this.mView.getDrawableState());
        }
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager drawableManager, int drawableId) {
        ColorStateList tintList = drawableManager.getTintList(context, drawableId);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    /* access modifiers changed from: package-private */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!ViewUtils.SDK_LEVEL_SUPPORTS_AUTOSIZE) {
            autoSizeText();
        }
    }

    /* access modifiers changed from: package-private */
    public void setTextSize(int unit, float size) {
        if (!ViewUtils.SDK_LEVEL_SUPPORTS_AUTOSIZE && !isAutoSizeEnabled()) {
            setTextSizeInternal(unit, size);
        }
    }

    /* access modifiers changed from: package-private */
    public void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    /* access modifiers changed from: package-private */
    public boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    private void setTextSizeInternal(int unit, float size) {
        this.mAutoSizeTextHelper.setTextSizeInternal(unit, size);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeWithDefaults(int autoSizeTextType) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(autoSizeTextType);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeUniformWithConfiguration(int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] presetSizes, int unit) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    /* access modifiers changed from: package-private */
    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getCompoundDrawableTintList() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setCompoundDrawableTintList(ColorStateList tintList) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        this.mDrawableTint.mTintList = tintList;
        this.mDrawableTint.mHasTintList = tintList != null;
        setCompoundTints();
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode getCompoundDrawableTintMode() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setCompoundDrawableTintMode(PorterDuff.Mode tintMode) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        this.mDrawableTint.mTintMode = tintMode;
        this.mDrawableTint.mHasTintMode = tintMode != null;
        setCompoundTints();
    }

    private void setCompoundTints() {
        TintInfo tintInfo = this.mDrawableTint;
        this.mDrawableLeftTint = tintInfo;
        this.mDrawableTopTint = tintInfo;
        this.mDrawableRightTint = tintInfo;
        this.mDrawableBottomTint = tintInfo;
        this.mDrawableStartTint = tintInfo;
        this.mDrawableEndTint = tintInfo;
    }

    private void setCompoundDrawables(Drawable drawableLeft, Drawable drawableTop, Drawable drawableRight, Drawable drawableBottom, Drawable drawableStart, Drawable drawableEnd) {
        if (drawableStart != null || drawableEnd != null) {
            Drawable[] existingRel = Api17Impl.getCompoundDrawablesRelative(this.mView);
            Api17Impl.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mView, drawableStart != null ? drawableStart : existingRel[0], drawableTop != null ? drawableTop : existingRel[1], drawableEnd != null ? drawableEnd : existingRel[2], drawableBottom != null ? drawableBottom : existingRel[3]);
        } else if (drawableLeft != null || drawableTop != null || drawableRight != null || drawableBottom != null) {
            Drawable[] existingRel2 = Api17Impl.getCompoundDrawablesRelative(this.mView);
            if (existingRel2[0] == null && existingRel2[2] == null) {
                Drawable[] existingAbs = this.mView.getCompoundDrawables();
                this.mView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft != null ? drawableLeft : existingAbs[0], drawableTop != null ? drawableTop : existingAbs[1], drawableRight != null ? drawableRight : existingAbs[2], drawableBottom != null ? drawableBottom : existingAbs[3]);
                return;
            }
            Api17Impl.setCompoundDrawablesRelativeWithIntrinsicBounds(this.mView, existingRel2[0], drawableTop != null ? drawableTop : existingRel2[1], existingRel2[2], drawableBottom != null ? drawableBottom : existingRel2[3]);
        }
    }

    /* access modifiers changed from: package-private */
    public void populateSurroundingTextIfNeeded(TextView textView, InputConnection inputConnection, EditorInfo editorInfo) {
        if (Build.VERSION.SDK_INT < 30 && inputConnection != null) {
            EditorInfoCompat.setInitialSurroundingText(editorInfo, textView.getText());
        }
    }

    static class Api26Impl {
        private Api26Impl() {
        }

        static boolean setFontVariationSettings(TextView textView, String fontVariationSettings) {
            return textView.setFontVariationSettings(fontVariationSettings);
        }

        static int getAutoSizeStepGranularity(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        static void setAutoSizeTextTypeUniformWithConfiguration(TextView textView, int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
        }

        static void setAutoSizeTextTypeUniformWithPresetSizes(TextView textView, int[] presetSizes, int unit) {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
        }
    }

    static class Api24Impl {
        private Api24Impl() {
        }

        static void setTextLocales(TextView textView, LocaleList locales) {
            textView.setTextLocales(locales);
        }

        static LocaleList forLanguageTags(String list) {
            return LocaleList.forLanguageTags(list);
        }
    }

    static class Api17Impl {
        private Api17Impl() {
        }

        static void setTextLocale(TextView textView, Locale locale) {
            textView.setTextLocale(locale);
        }

        static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, Drawable start, Drawable top, Drawable end, Drawable bottom) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }

        static Drawable[] getCompoundDrawablesRelative(TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static Locale forLanguageTag(String languageTag) {
            return Locale.forLanguageTag(languageTag);
        }
    }

    static class Api28Impl {
        private Api28Impl() {
        }

        static Typeface create(Typeface family, int weight, boolean italic) {
            return Typeface.create(family, weight, italic);
        }
    }
}
