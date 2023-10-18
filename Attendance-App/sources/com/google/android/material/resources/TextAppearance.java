package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;

public class TextAppearance {
    private static final String TAG = "TextAppearance";
    private static final int TYPEFACE_MONOSPACE = 3;
    private static final int TYPEFACE_SANS = 1;
    private static final int TYPEFACE_SERIF = 2;
    /* access modifiers changed from: private */
    public Typeface font;
    public final String fontFamily;
    private final int fontFamilyResourceId;
    /* access modifiers changed from: private */
    public boolean fontResolved = false;
    public final boolean hasLetterSpacing;
    public final float letterSpacing;
    public final ColorStateList shadowColor;
    public final float shadowDx;
    public final float shadowDy;
    public final float shadowRadius;
    public final boolean textAllCaps;
    private ColorStateList textColor;
    public final ColorStateList textColorHint;
    public final ColorStateList textColorLink;
    private float textSize;
    public final int textStyle;
    public final int typeface;

    public TextAppearance(Context context, int id) {
        TypedArray a = context.obtainStyledAttributes(id, C1087R.styleable.TextAppearance);
        setTextSize(a.getDimension(C1087R.styleable.TextAppearance_android_textSize, 0.0f));
        setTextColor(MaterialResources.getColorStateList(context, a, C1087R.styleable.TextAppearance_android_textColor));
        this.textColorHint = MaterialResources.getColorStateList(context, a, C1087R.styleable.TextAppearance_android_textColorHint);
        this.textColorLink = MaterialResources.getColorStateList(context, a, C1087R.styleable.TextAppearance_android_textColorLink);
        this.textStyle = a.getInt(C1087R.styleable.TextAppearance_android_textStyle, 0);
        this.typeface = a.getInt(C1087R.styleable.TextAppearance_android_typeface, 1);
        int fontFamilyIndex = MaterialResources.getIndexWithValue(a, C1087R.styleable.TextAppearance_fontFamily, C1087R.styleable.TextAppearance_android_fontFamily);
        this.fontFamilyResourceId = a.getResourceId(fontFamilyIndex, 0);
        this.fontFamily = a.getString(fontFamilyIndex);
        this.textAllCaps = a.getBoolean(C1087R.styleable.TextAppearance_textAllCaps, false);
        this.shadowColor = MaterialResources.getColorStateList(context, a, C1087R.styleable.TextAppearance_android_shadowColor);
        this.shadowDx = a.getFloat(C1087R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.shadowDy = a.getFloat(C1087R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.shadowRadius = a.getFloat(C1087R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        a.recycle();
        if (Build.VERSION.SDK_INT >= 21) {
            TypedArray a2 = context.obtainStyledAttributes(id, C1087R.styleable.MaterialTextAppearance);
            this.hasLetterSpacing = a2.hasValue(C1087R.styleable.MaterialTextAppearance_android_letterSpacing);
            this.letterSpacing = a2.getFloat(C1087R.styleable.MaterialTextAppearance_android_letterSpacing, 0.0f);
            a2.recycle();
            return;
        }
        this.hasLetterSpacing = false;
        this.letterSpacing = 0.0f;
    }

    public Typeface getFont(Context context) {
        if (this.fontResolved) {
            return this.font;
        }
        if (!context.isRestricted()) {
            try {
                Typeface font2 = ResourcesCompat.getFont(context, this.fontFamilyResourceId);
                this.font = font2;
                if (font2 != null) {
                    this.font = Typeface.create(font2, this.textStyle);
                }
            } catch (Resources.NotFoundException | UnsupportedOperationException e) {
            } catch (Exception e2) {
                Log.d(TAG, "Error loading font " + this.fontFamily, e2);
            }
        }
        createFallbackFont();
        this.fontResolved = true;
        return this.font;
    }

    public void getFontAsync(Context context, final TextAppearanceFontCallback callback) {
        if (shouldLoadFontSynchronously(context)) {
            getFont(context);
        } else {
            createFallbackFont();
        }
        int i = this.fontFamilyResourceId;
        if (i == 0) {
            this.fontResolved = true;
        }
        if (this.fontResolved) {
            callback.onFontRetrieved(this.font, true);
            return;
        }
        try {
            ResourcesCompat.getFont(context, i, new ResourcesCompat.FontCallback() {
                public void onFontRetrieved(Typeface typeface) {
                    TextAppearance textAppearance = TextAppearance.this;
                    Typeface unused = textAppearance.font = Typeface.create(typeface, textAppearance.textStyle);
                    boolean unused2 = TextAppearance.this.fontResolved = true;
                    callback.onFontRetrieved(TextAppearance.this.font, false);
                }

                public void onFontRetrievalFailed(int reason) {
                    boolean unused = TextAppearance.this.fontResolved = true;
                    callback.onFontRetrievalFailed(reason);
                }
            }, (Handler) null);
        } catch (Resources.NotFoundException e) {
            this.fontResolved = true;
            callback.onFontRetrievalFailed(1);
        } catch (Exception e2) {
            Log.d(TAG, "Error loading font " + this.fontFamily, e2);
            this.fontResolved = true;
            callback.onFontRetrievalFailed(-3);
        }
    }

    public void getFontAsync(final Context context, final TextPaint textPaint, final TextAppearanceFontCallback callback) {
        updateTextPaintMeasureState(context, textPaint, getFallbackFont());
        getFontAsync(context, new TextAppearanceFontCallback() {
            public void onFontRetrieved(Typeface typeface, boolean fontResolvedSynchronously) {
                TextAppearance.this.updateTextPaintMeasureState(context, textPaint, typeface);
                callback.onFontRetrieved(typeface, fontResolvedSynchronously);
            }

            public void onFontRetrievalFailed(int i) {
                callback.onFontRetrievalFailed(i);
            }
        });
    }

    public Typeface getFallbackFont() {
        createFallbackFont();
        return this.font;
    }

    private void createFallbackFont() {
        String str;
        if (this.font == null && (str = this.fontFamily) != null) {
            this.font = Typeface.create(str, this.textStyle);
        }
        if (this.font == null) {
            switch (this.typeface) {
                case 1:
                    this.font = Typeface.SANS_SERIF;
                    break;
                case 2:
                    this.font = Typeface.SERIF;
                    break;
                case 3:
                    this.font = Typeface.MONOSPACE;
                    break;
                default:
                    this.font = Typeface.DEFAULT;
                    break;
            }
            this.font = Typeface.create(this.font, this.textStyle);
        }
    }

    public void updateDrawState(Context context, TextPaint textPaint, TextAppearanceFontCallback callback) {
        int i;
        int i2;
        updateMeasureState(context, textPaint, callback);
        ColorStateList colorStateList = this.textColor;
        if (colorStateList != null) {
            i = colorStateList.getColorForState(textPaint.drawableState, this.textColor.getDefaultColor());
        } else {
            i = ViewCompat.MEASURED_STATE_MASK;
        }
        textPaint.setColor(i);
        float f = this.shadowRadius;
        float f2 = this.shadowDx;
        float f3 = this.shadowDy;
        ColorStateList colorStateList2 = this.shadowColor;
        if (colorStateList2 != null) {
            i2 = colorStateList2.getColorForState(textPaint.drawableState, this.shadowColor.getDefaultColor());
        } else {
            i2 = 0;
        }
        textPaint.setShadowLayer(f, f2, f3, i2);
    }

    public void updateMeasureState(Context context, TextPaint textPaint, TextAppearanceFontCallback callback) {
        if (shouldLoadFontSynchronously(context)) {
            updateTextPaintMeasureState(context, textPaint, getFont(context));
        } else {
            getFontAsync(context, textPaint, callback);
        }
    }

    public void updateTextPaintMeasureState(Context context, TextPaint textPaint, Typeface typeface2) {
        Typeface boldTypeface = TypefaceUtils.maybeCopyWithFontWeightAdjustment(context, typeface2);
        if (boldTypeface != null) {
            typeface2 = boldTypeface;
        }
        textPaint.setTypeface(typeface2);
        int fake = this.textStyle & (typeface2.getStyle() ^ -1);
        textPaint.setFakeBoldText((fake & 1) != 0);
        textPaint.setTextSkewX((fake & 2) != 0 ? -0.25f : 0.0f);
        textPaint.setTextSize(this.textSize);
        if (Build.VERSION.SDK_INT >= 21 && this.hasLetterSpacing) {
            textPaint.setLetterSpacing(this.letterSpacing);
        }
    }

    public ColorStateList getTextColor() {
        return this.textColor;
    }

    public void setTextColor(ColorStateList textColor2) {
        this.textColor = textColor2;
    }

    public float getTextSize() {
        return this.textSize;
    }

    public void setTextSize(float textSize2) {
        this.textSize = textSize2;
    }

    private boolean shouldLoadFontSynchronously(Context context) {
        Typeface typeface2;
        if (TextAppearanceConfig.shouldLoadFontSynchronously()) {
            return true;
        }
        int i = this.fontFamilyResourceId;
        if (i != 0) {
            typeface2 = ResourcesCompat.getCachedFont(context, i);
        } else {
            typeface2 = null;
        }
        if (typeface2 != null) {
            return true;
        }
        return false;
    }
}
