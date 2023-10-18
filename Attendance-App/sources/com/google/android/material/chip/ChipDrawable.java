package com.google.android.material.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.internal.view.SupportMenu;
import androidx.core.text.BidiFormatter;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.canvas.CanvasCompat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.ref.WeakReference;
import java.util.Arrays;

public class ChipDrawable extends MaterialShapeDrawable implements TintAwareDrawable, Drawable.Callback, TextDrawableHelper.TextDrawableDelegate {
    private static final boolean DEBUG = false;
    private static final int[] DEFAULT_STATE = {16842910};
    private static final int MAX_CHIP_ICON_HEIGHT = 24;
    private static final String NAMESPACE_APP = "http://schemas.android.com/apk/res-auto";
    private static final ShapeDrawable closeIconRippleMask = new ShapeDrawable(new OvalShape());
    private int alpha = 255;
    private boolean checkable;
    private Drawable checkedIcon;
    private ColorStateList checkedIconTint;
    private boolean checkedIconVisible;
    private ColorStateList chipBackgroundColor;
    private float chipCornerRadius = -1.0f;
    private float chipEndPadding;
    private Drawable chipIcon;
    private float chipIconSize;
    private ColorStateList chipIconTint;
    private boolean chipIconVisible;
    private float chipMinHeight;
    private final Paint chipPaint = new Paint(1);
    private float chipStartPadding;
    private ColorStateList chipStrokeColor;
    private float chipStrokeWidth;
    private ColorStateList chipSurfaceColor;
    private Drawable closeIcon;
    private CharSequence closeIconContentDescription;
    private float closeIconEndPadding;
    private Drawable closeIconRipple;
    private float closeIconSize;
    private float closeIconStartPadding;
    private int[] closeIconStateSet;
    private ColorStateList closeIconTint;
    private boolean closeIconVisible;
    private ColorFilter colorFilter;
    private ColorStateList compatRippleColor;
    private final Context context;
    private boolean currentChecked;
    private int currentChipBackgroundColor;
    private int currentChipStrokeColor;
    private int currentChipSurfaceColor;
    private int currentCompatRippleColor;
    private int currentCompositeSurfaceBackgroundColor;
    private int currentTextColor;
    private int currentTint;
    private final Paint debugPaint;
    private WeakReference<Delegate> delegate = new WeakReference<>((Object) null);
    private final Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private boolean hasChipIconTint;
    private MotionSpec hideMotionSpec;
    private float iconEndPadding;
    private float iconStartPadding;
    private boolean isShapeThemingEnabled;
    private int maxWidth;
    private final PointF pointF = new PointF();
    private final RectF rectF = new RectF();
    private ColorStateList rippleColor;
    private final Path shapePath = new Path();
    private boolean shouldDrawText;
    private MotionSpec showMotionSpec;
    private CharSequence text;
    private final TextDrawableHelper textDrawableHelper;
    private float textEndPadding;
    private float textStartPadding;
    private ColorStateList tint;
    private PorterDuffColorFilter tintFilter;
    private PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
    private TextUtils.TruncateAt truncateAt;
    private boolean useCompatRipple;

    public interface Delegate {
        void onChipDrawableSizeChange();
    }

    public static ChipDrawable createFromAttributes(Context context2, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        ChipDrawable chip = new ChipDrawable(context2, attrs, defStyleAttr, defStyleRes);
        chip.loadFromAttributes(attrs, defStyleAttr, defStyleRes);
        return chip;
    }

    public static ChipDrawable createFromResource(Context context2, int id) {
        AttributeSet attrs = DrawableUtils.parseDrawableXml(context2, id, "chip");
        int style = attrs.getStyleAttribute();
        if (style == 0) {
            style = C1087R.C1093style.Widget_MaterialComponents_Chip_Entry;
        }
        return createFromAttributes(context2, attrs, C1087R.attr.chipStandaloneStyle, style);
    }

    private ChipDrawable(Context context2, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context2, attrs, defStyleAttr, defStyleRes);
        initializeElevationOverlay(context2);
        this.context = context2;
        TextDrawableHelper textDrawableHelper2 = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper2;
        this.text = "";
        textDrawableHelper2.getTextPaint().density = context2.getResources().getDisplayMetrics().density;
        this.debugPaint = null;
        int[] iArr = DEFAULT_STATE;
        setState(iArr);
        setCloseIconState(iArr);
        this.shouldDrawText = true;
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            closeIconRippleMask.setTint(-1);
        }
    }

    private void loadFromAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(this.context, attrs, C1087R.styleable.Chip, defStyleAttr, defStyleRes, new int[0]);
        this.isShapeThemingEnabled = a.hasValue(C1087R.styleable.Chip_shapeAppearance);
        setChipSurfaceColor(MaterialResources.getColorStateList(this.context, a, C1087R.styleable.Chip_chipSurfaceColor));
        setChipBackgroundColor(MaterialResources.getColorStateList(this.context, a, C1087R.styleable.Chip_chipBackgroundColor));
        setChipMinHeight(a.getDimension(C1087R.styleable.Chip_chipMinHeight, 0.0f));
        if (a.hasValue(C1087R.styleable.Chip_chipCornerRadius)) {
            setChipCornerRadius(a.getDimension(C1087R.styleable.Chip_chipCornerRadius, 0.0f));
        }
        setChipStrokeColor(MaterialResources.getColorStateList(this.context, a, C1087R.styleable.Chip_chipStrokeColor));
        setChipStrokeWidth(a.getDimension(C1087R.styleable.Chip_chipStrokeWidth, 0.0f));
        setRippleColor(MaterialResources.getColorStateList(this.context, a, C1087R.styleable.Chip_rippleColor));
        setText(a.getText(C1087R.styleable.Chip_android_text));
        TextAppearance textAppearance = MaterialResources.getTextAppearance(this.context, a, C1087R.styleable.Chip_android_textAppearance);
        textAppearance.setTextSize(a.getDimension(C1087R.styleable.Chip_android_textSize, textAppearance.getTextSize()));
        if (Build.VERSION.SDK_INT < 23) {
            textAppearance.setTextColor(MaterialResources.getColorStateList(this.context, a, C1087R.styleable.Chip_android_textColor));
        }
        setTextAppearance(textAppearance);
        switch (a.getInt(C1087R.styleable.Chip_android_ellipsize, 0)) {
            case 1:
                setEllipsize(TextUtils.TruncateAt.START);
                break;
            case 2:
                setEllipsize(TextUtils.TruncateAt.MIDDLE);
                break;
            case 3:
                setEllipsize(TextUtils.TruncateAt.END);
                break;
        }
        setChipIconVisible(a.getBoolean(C1087R.styleable.Chip_chipIconVisible, false));
        if (!(attrs == null || attrs.getAttributeValue(NAMESPACE_APP, "chipIconEnabled") == null || attrs.getAttributeValue(NAMESPACE_APP, "chipIconVisible") != null)) {
            setChipIconVisible(a.getBoolean(C1087R.styleable.Chip_chipIconEnabled, false));
        }
        setChipIcon(MaterialResources.getDrawable(this.context, a, C1087R.styleable.Chip_chipIcon));
        if (a.hasValue(C1087R.styleable.Chip_chipIconTint)) {
            setChipIconTint(MaterialResources.getColorStateList(this.context, a, C1087R.styleable.Chip_chipIconTint));
        }
        setChipIconSize(a.getDimension(C1087R.styleable.Chip_chipIconSize, -1.0f));
        setCloseIconVisible(a.getBoolean(C1087R.styleable.Chip_closeIconVisible, false));
        if (!(attrs == null || attrs.getAttributeValue(NAMESPACE_APP, "closeIconEnabled") == null || attrs.getAttributeValue(NAMESPACE_APP, "closeIconVisible") != null)) {
            setCloseIconVisible(a.getBoolean(C1087R.styleable.Chip_closeIconEnabled, false));
        }
        setCloseIcon(MaterialResources.getDrawable(this.context, a, C1087R.styleable.Chip_closeIcon));
        setCloseIconTint(MaterialResources.getColorStateList(this.context, a, C1087R.styleable.Chip_closeIconTint));
        setCloseIconSize(a.getDimension(C1087R.styleable.Chip_closeIconSize, 0.0f));
        setCheckable(a.getBoolean(C1087R.styleable.Chip_android_checkable, false));
        setCheckedIconVisible(a.getBoolean(C1087R.styleable.Chip_checkedIconVisible, false));
        if (!(attrs == null || attrs.getAttributeValue(NAMESPACE_APP, "checkedIconEnabled") == null || attrs.getAttributeValue(NAMESPACE_APP, "checkedIconVisible") != null)) {
            setCheckedIconVisible(a.getBoolean(C1087R.styleable.Chip_checkedIconEnabled, false));
        }
        setCheckedIcon(MaterialResources.getDrawable(this.context, a, C1087R.styleable.Chip_checkedIcon));
        if (a.hasValue(C1087R.styleable.Chip_checkedIconTint)) {
            setCheckedIconTint(MaterialResources.getColorStateList(this.context, a, C1087R.styleable.Chip_checkedIconTint));
        }
        setShowMotionSpec(MotionSpec.createFromAttribute(this.context, a, C1087R.styleable.Chip_showMotionSpec));
        setHideMotionSpec(MotionSpec.createFromAttribute(this.context, a, C1087R.styleable.Chip_hideMotionSpec));
        setChipStartPadding(a.getDimension(C1087R.styleable.Chip_chipStartPadding, 0.0f));
        setIconStartPadding(a.getDimension(C1087R.styleable.Chip_iconStartPadding, 0.0f));
        setIconEndPadding(a.getDimension(C1087R.styleable.Chip_iconEndPadding, 0.0f));
        setTextStartPadding(a.getDimension(C1087R.styleable.Chip_textStartPadding, 0.0f));
        setTextEndPadding(a.getDimension(C1087R.styleable.Chip_textEndPadding, 0.0f));
        setCloseIconStartPadding(a.getDimension(C1087R.styleable.Chip_closeIconStartPadding, 0.0f));
        setCloseIconEndPadding(a.getDimension(C1087R.styleable.Chip_closeIconEndPadding, 0.0f));
        setChipEndPadding(a.getDimension(C1087R.styleable.Chip_chipEndPadding, 0.0f));
        setMaxWidth(a.getDimensionPixelSize(C1087R.styleable.Chip_android_maxWidth, Integer.MAX_VALUE));
        a.recycle();
    }

    public void setUseCompatRipple(boolean useCompatRipple2) {
        if (this.useCompatRipple != useCompatRipple2) {
            this.useCompatRipple = useCompatRipple2;
            updateCompatRippleColor();
            onStateChange(getState());
        }
    }

    public boolean getUseCompatRipple() {
        return this.useCompatRipple;
    }

    public void setDelegate(Delegate delegate2) {
        this.delegate = new WeakReference<>(delegate2);
    }

    /* access modifiers changed from: protected */
    public void onSizeChange() {
        Delegate delegate2 = (Delegate) this.delegate.get();
        if (delegate2 != null) {
            delegate2.onChipDrawableSizeChange();
        }
    }

    public void getChipTouchBounds(RectF bounds) {
        calculateChipTouchBounds(getBounds(), bounds);
    }

    public void getCloseIconTouchBounds(RectF bounds) {
        calculateCloseIconTouchBounds(getBounds(), bounds);
    }

    public int getIntrinsicWidth() {
        return Math.min(Math.round(this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding + this.textDrawableHelper.getTextWidth(getText().toString()) + this.textEndPadding + calculateCloseIconWidth() + this.chipEndPadding), this.maxWidth);
    }

    public int getIntrinsicHeight() {
        return (int) this.chipMinHeight;
    }

    private boolean showsChipIcon() {
        return this.chipIconVisible && this.chipIcon != null;
    }

    private boolean showsCheckedIcon() {
        return this.checkedIconVisible && this.checkedIcon != null && this.currentChecked;
    }

    private boolean showsCloseIcon() {
        return this.closeIconVisible && this.closeIcon != null;
    }

    private boolean canShowCheckedIcon() {
        return this.checkedIconVisible && this.checkedIcon != null && this.checkable;
    }

    /* access modifiers changed from: package-private */
    public float calculateChipIconWidth() {
        if (showsChipIcon() || showsCheckedIcon()) {
            return this.iconStartPadding + getCurrentChipIconWidth() + this.iconEndPadding;
        }
        return 0.0f;
    }

    private float getCurrentChipIconWidth() {
        Drawable iconDrawable = this.currentChecked ? this.checkedIcon : this.chipIcon;
        float f = this.chipIconSize;
        if (f > 0.0f || iconDrawable == null) {
            return f;
        }
        return (float) iconDrawable.getIntrinsicWidth();
    }

    private float getCurrentChipIconHeight() {
        Drawable icon = this.currentChecked ? this.checkedIcon : this.chipIcon;
        float maxChipIconHeight = this.chipIconSize;
        if (maxChipIconHeight > 0.0f || icon == null) {
            return maxChipIconHeight;
        }
        float maxChipIconHeight2 = (float) Math.ceil((double) ViewUtils.dpToPx(this.context, 24));
        if (((float) icon.getIntrinsicHeight()) <= maxChipIconHeight2) {
            return (float) icon.getIntrinsicHeight();
        }
        return maxChipIconHeight2;
    }

    /* access modifiers changed from: package-private */
    public float calculateCloseIconWidth() {
        if (showsCloseIcon()) {
            return this.closeIconStartPadding + this.closeIconSize + this.closeIconEndPadding;
        }
        return 0.0f;
    }

    /* access modifiers changed from: package-private */
    public boolean isShapeThemingEnabled() {
        return this.isShapeThemingEnabled;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (!bounds.isEmpty() && getAlpha() != 0) {
            int saveCount = 0;
            if (this.alpha < 255) {
                saveCount = CanvasCompat.saveLayerAlpha(canvas, (float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, this.alpha);
            }
            drawChipSurface(canvas, bounds);
            drawChipBackground(canvas, bounds);
            if (this.isShapeThemingEnabled) {
                super.draw(canvas);
            }
            drawChipStroke(canvas, bounds);
            drawCompatRipple(canvas, bounds);
            drawChipIcon(canvas, bounds);
            drawCheckedIcon(canvas, bounds);
            if (this.shouldDrawText) {
                drawText(canvas, bounds);
            }
            drawCloseIcon(canvas, bounds);
            drawDebug(canvas, bounds);
            if (this.alpha < 255) {
                canvas.restoreToCount(saveCount);
            }
        }
    }

    private void drawChipSurface(Canvas canvas, Rect bounds) {
        if (!this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipSurfaceColor);
            this.chipPaint.setStyle(Paint.Style.FILL);
            this.rectF.set(bounds);
            canvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
    }

    private void drawChipBackground(Canvas canvas, Rect bounds) {
        if (!this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipBackgroundColor);
            this.chipPaint.setStyle(Paint.Style.FILL);
            this.chipPaint.setColorFilter(getTintColorFilter());
            this.rectF.set(bounds);
            canvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
    }

    private void drawChipStroke(Canvas canvas, Rect bounds) {
        if (this.chipStrokeWidth > 0.0f && !this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipStrokeColor);
            this.chipPaint.setStyle(Paint.Style.STROKE);
            if (!this.isShapeThemingEnabled) {
                this.chipPaint.setColorFilter(getTintColorFilter());
            }
            this.rectF.set(((float) bounds.left) + (this.chipStrokeWidth / 2.0f), ((float) bounds.top) + (this.chipStrokeWidth / 2.0f), ((float) bounds.right) - (this.chipStrokeWidth / 2.0f), ((float) bounds.bottom) - (this.chipStrokeWidth / 2.0f));
            float strokeCornerRadius = this.chipCornerRadius - (this.chipStrokeWidth / 2.0f);
            canvas.drawRoundRect(this.rectF, strokeCornerRadius, strokeCornerRadius, this.chipPaint);
        }
    }

    private void drawCompatRipple(Canvas canvas, Rect bounds) {
        this.chipPaint.setColor(this.currentCompatRippleColor);
        this.chipPaint.setStyle(Paint.Style.FILL);
        this.rectF.set(bounds);
        if (!this.isShapeThemingEnabled) {
            canvas.drawRoundRect(this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
            return;
        }
        calculatePathForSize(new RectF(bounds), this.shapePath);
        super.drawShape(canvas, this.chipPaint, this.shapePath, getBoundsAsRectF());
    }

    private void drawChipIcon(Canvas canvas, Rect bounds) {
        if (showsChipIcon()) {
            calculateChipIconBounds(bounds, this.rectF);
            float tx = this.rectF.left;
            float ty = this.rectF.top;
            canvas.translate(tx, ty);
            this.chipIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.chipIcon.draw(canvas);
            canvas.translate(-tx, -ty);
        }
    }

    private void drawCheckedIcon(Canvas canvas, Rect bounds) {
        if (showsCheckedIcon()) {
            calculateChipIconBounds(bounds, this.rectF);
            float tx = this.rectF.left;
            float ty = this.rectF.top;
            canvas.translate(tx, ty);
            this.checkedIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.checkedIcon.draw(canvas);
            canvas.translate(-tx, -ty);
        }
    }

    private void drawText(Canvas canvas, Rect bounds) {
        if (this.text != null) {
            Paint.Align align = calculateTextOriginAndAlignment(bounds, this.pointF);
            calculateTextBounds(bounds, this.rectF);
            if (this.textDrawableHelper.getTextAppearance() != null) {
                this.textDrawableHelper.getTextPaint().drawableState = getState();
                this.textDrawableHelper.updateTextPaintDrawState(this.context);
            }
            this.textDrawableHelper.getTextPaint().setTextAlign(align);
            boolean clip = Math.round(this.textDrawableHelper.getTextWidth(getText().toString())) > Math.round(this.rectF.width());
            int saveCount = 0;
            if (clip) {
                saveCount = canvas.save();
                canvas.clipRect(this.rectF);
            }
            CharSequence finalText = this.text;
            if (clip && this.truncateAt != null) {
                finalText = TextUtils.ellipsize(this.text, this.textDrawableHelper.getTextPaint(), this.rectF.width(), this.truncateAt);
            }
            canvas.drawText(finalText, 0, finalText.length(), this.pointF.x, this.pointF.y, this.textDrawableHelper.getTextPaint());
            if (clip) {
                canvas.restoreToCount(saveCount);
            }
        }
    }

    private void drawCloseIcon(Canvas canvas, Rect bounds) {
        if (showsCloseIcon()) {
            calculateCloseIconBounds(bounds, this.rectF);
            float tx = this.rectF.left;
            float ty = this.rectF.top;
            canvas.translate(tx, ty);
            this.closeIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
                this.closeIconRipple.setBounds(this.closeIcon.getBounds());
                this.closeIconRipple.jumpToCurrentState();
                this.closeIconRipple.draw(canvas);
            } else {
                this.closeIcon.draw(canvas);
            }
            canvas.translate(-tx, -ty);
        }
    }

    private void drawDebug(Canvas canvas, Rect bounds) {
        Paint paint = this.debugPaint;
        if (paint != null) {
            paint.setColor(ColorUtils.setAlphaComponent(ViewCompat.MEASURED_STATE_MASK, 127));
            canvas.drawRect(bounds, this.debugPaint);
            if (showsChipIcon() || showsCheckedIcon()) {
                calculateChipIconBounds(bounds, this.rectF);
                canvas.drawRect(this.rectF, this.debugPaint);
            }
            if (this.text != null) {
                canvas.drawLine((float) bounds.left, bounds.exactCenterY(), (float) bounds.right, bounds.exactCenterY(), this.debugPaint);
            }
            if (showsCloseIcon()) {
                calculateCloseIconBounds(bounds, this.rectF);
                canvas.drawRect(this.rectF, this.debugPaint);
            }
            this.debugPaint.setColor(ColorUtils.setAlphaComponent(SupportMenu.CATEGORY_MASK, 127));
            calculateChipTouchBounds(bounds, this.rectF);
            canvas.drawRect(this.rectF, this.debugPaint);
            this.debugPaint.setColor(ColorUtils.setAlphaComponent(-16711936, 127));
            calculateCloseIconTouchBounds(bounds, this.rectF);
            canvas.drawRect(this.rectF, this.debugPaint);
        }
    }

    private void calculateChipIconBounds(Rect bounds, RectF outBounds) {
        outBounds.setEmpty();
        if (showsChipIcon() || showsCheckedIcon()) {
            float offsetFromStart = this.chipStartPadding + this.iconStartPadding;
            float chipWidth = getCurrentChipIconWidth();
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                outBounds.left = ((float) bounds.left) + offsetFromStart;
                outBounds.right = outBounds.left + chipWidth;
            } else {
                outBounds.right = ((float) bounds.right) - offsetFromStart;
                outBounds.left = outBounds.right - chipWidth;
            }
            float chipHeight = getCurrentChipIconHeight();
            outBounds.top = bounds.exactCenterY() - (chipHeight / 2.0f);
            outBounds.bottom = outBounds.top + chipHeight;
        }
    }

    /* access modifiers changed from: package-private */
    public Paint.Align calculateTextOriginAndAlignment(Rect bounds, PointF pointF2) {
        pointF2.set(0.0f, 0.0f);
        Paint.Align align = Paint.Align.LEFT;
        if (this.text != null) {
            float offsetFromStart = this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                pointF2.x = ((float) bounds.left) + offsetFromStart;
                align = Paint.Align.LEFT;
            } else {
                pointF2.x = ((float) bounds.right) - offsetFromStart;
                align = Paint.Align.RIGHT;
            }
            pointF2.y = ((float) bounds.centerY()) - calculateTextCenterFromBaseline();
        }
        return align;
    }

    private float calculateTextCenterFromBaseline() {
        this.textDrawableHelper.getTextPaint().getFontMetrics(this.fontMetrics);
        return (this.fontMetrics.descent + this.fontMetrics.ascent) / 2.0f;
    }

    private void calculateTextBounds(Rect bounds, RectF outBounds) {
        outBounds.setEmpty();
        if (this.text != null) {
            float offsetFromStart = this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding;
            float offsetFromEnd = this.chipEndPadding + calculateCloseIconWidth() + this.textEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                outBounds.left = ((float) bounds.left) + offsetFromStart;
                outBounds.right = ((float) bounds.right) - offsetFromEnd;
            } else {
                outBounds.left = ((float) bounds.left) + offsetFromEnd;
                outBounds.right = ((float) bounds.right) - offsetFromStart;
            }
            outBounds.top = (float) bounds.top;
            outBounds.bottom = (float) bounds.bottom;
        }
    }

    private void calculateCloseIconBounds(Rect bounds, RectF outBounds) {
        outBounds.setEmpty();
        if (showsCloseIcon()) {
            float offsetFromEnd = this.chipEndPadding + this.closeIconEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                outBounds.right = ((float) bounds.right) - offsetFromEnd;
                outBounds.left = outBounds.right - this.closeIconSize;
            } else {
                outBounds.left = ((float) bounds.left) + offsetFromEnd;
                outBounds.right = outBounds.left + this.closeIconSize;
            }
            outBounds.top = bounds.exactCenterY() - (this.closeIconSize / 2.0f);
            outBounds.bottom = outBounds.top + this.closeIconSize;
        }
    }

    private void calculateChipTouchBounds(Rect bounds, RectF outBounds) {
        outBounds.set(bounds);
        if (showsCloseIcon()) {
            float offsetFromEnd = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                outBounds.right = ((float) bounds.right) - offsetFromEnd;
            } else {
                outBounds.left = ((float) bounds.left) + offsetFromEnd;
            }
        }
    }

    private void calculateCloseIconTouchBounds(Rect bounds, RectF outBounds) {
        outBounds.setEmpty();
        if (showsCloseIcon()) {
            float offsetFromEnd = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
            if (DrawableCompat.getLayoutDirection(this) == 0) {
                outBounds.right = (float) bounds.right;
                outBounds.left = outBounds.right - offsetFromEnd;
            } else {
                outBounds.left = (float) bounds.left;
                outBounds.right = ((float) bounds.left) + offsetFromEnd;
            }
            outBounds.top = (float) bounds.top;
            outBounds.bottom = (float) bounds.bottom;
        }
    }

    public boolean isStateful() {
        return isStateful(this.chipSurfaceColor) || isStateful(this.chipBackgroundColor) || isStateful(this.chipStrokeColor) || (this.useCompatRipple && isStateful(this.compatRippleColor)) || isStateful(this.textDrawableHelper.getTextAppearance()) || canShowCheckedIcon() || isStateful(this.chipIcon) || isStateful(this.checkedIcon) || isStateful(this.tint);
    }

    public boolean isCloseIconStateful() {
        return isStateful(this.closeIcon);
    }

    public boolean setCloseIconState(int[] stateSet) {
        if (Arrays.equals(this.closeIconStateSet, stateSet)) {
            return false;
        }
        this.closeIconStateSet = stateSet;
        if (showsCloseIcon()) {
            return onStateChange(getState(), stateSet);
        }
        return false;
    }

    public int[] getCloseIconState() {
        return this.closeIconStateSet;
    }

    public void onTextSizeChange() {
        onSizeChange();
        invalidateSelf();
    }

    public boolean onStateChange(int[] state) {
        if (this.isShapeThemingEnabled) {
            super.onStateChange(state);
        }
        return onStateChange(state, getCloseIconState());
    }

    private boolean onStateChange(int[] chipState, int[] closeIconState) {
        int newChipSurfaceColor;
        int newChipBackgroundColor;
        int newChipStrokeColor;
        int newCompatRippleColor;
        int newTextColor;
        int[] iArr = chipState;
        int[] iArr2 = closeIconState;
        boolean invalidate = super.onStateChange(chipState);
        boolean sizeChanged = false;
        ColorStateList colorStateList = this.chipSurfaceColor;
        if (colorStateList != null) {
            newChipSurfaceColor = colorStateList.getColorForState(iArr, this.currentChipSurfaceColor);
        } else {
            newChipSurfaceColor = 0;
        }
        int newChipSurfaceColor2 = compositeElevationOverlayIfNeeded(newChipSurfaceColor);
        if (this.currentChipSurfaceColor != newChipSurfaceColor2) {
            this.currentChipSurfaceColor = newChipSurfaceColor2;
            invalidate = true;
        }
        ColorStateList colorStateList2 = this.chipBackgroundColor;
        if (colorStateList2 != null) {
            newChipBackgroundColor = colorStateList2.getColorForState(iArr, this.currentChipBackgroundColor);
        } else {
            newChipBackgroundColor = 0;
        }
        int newChipBackgroundColor2 = compositeElevationOverlayIfNeeded(newChipBackgroundColor);
        if (this.currentChipBackgroundColor != newChipBackgroundColor2) {
            this.currentChipBackgroundColor = newChipBackgroundColor2;
            invalidate = true;
        }
        int newCompositeSurfaceBackgroundColor = MaterialColors.layer(newChipSurfaceColor2, newChipBackgroundColor2);
        boolean newChecked = true;
        if ((this.currentCompositeSurfaceBackgroundColor != newCompositeSurfaceBackgroundColor) || (getFillColor() == null)) {
            this.currentCompositeSurfaceBackgroundColor = newCompositeSurfaceBackgroundColor;
            setFillColor(ColorStateList.valueOf(newCompositeSurfaceBackgroundColor));
            invalidate = true;
        }
        ColorStateList colorStateList3 = this.chipStrokeColor;
        if (colorStateList3 != null) {
            newChipStrokeColor = colorStateList3.getColorForState(iArr, this.currentChipStrokeColor);
        } else {
            newChipStrokeColor = 0;
        }
        if (this.currentChipStrokeColor != newChipStrokeColor) {
            this.currentChipStrokeColor = newChipStrokeColor;
            invalidate = true;
        }
        if (this.compatRippleColor == null || !RippleUtils.shouldDrawRippleCompat(chipState)) {
            newCompatRippleColor = 0;
        } else {
            newCompatRippleColor = this.compatRippleColor.getColorForState(iArr, this.currentCompatRippleColor);
        }
        if (this.currentCompatRippleColor != newCompatRippleColor) {
            this.currentCompatRippleColor = newCompatRippleColor;
            if (this.useCompatRipple) {
                invalidate = true;
            }
        }
        if (this.textDrawableHelper.getTextAppearance() == null || this.textDrawableHelper.getTextAppearance().getTextColor() == null) {
            newTextColor = 0;
        } else {
            newTextColor = this.textDrawableHelper.getTextAppearance().getTextColor().getColorForState(iArr, this.currentTextColor);
        }
        if (this.currentTextColor != newTextColor) {
            this.currentTextColor = newTextColor;
            invalidate = true;
        }
        if (!hasState(getState(), 16842912) || !this.checkable) {
            newChecked = false;
        }
        if (!(this.currentChecked == newChecked || this.checkedIcon == null)) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.currentChecked = newChecked;
            invalidate = true;
            if (oldChipIconWidth != calculateChipIconWidth()) {
                sizeChanged = true;
            }
        }
        ColorStateList colorStateList4 = this.tint;
        int newTint = colorStateList4 != null ? colorStateList4.getColorForState(iArr, this.currentTint) : 0;
        if (this.currentTint != newTint) {
            this.currentTint = newTint;
            this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, this.tintMode);
            invalidate = true;
        }
        if (isStateful(this.chipIcon)) {
            invalidate |= this.chipIcon.setState(iArr);
        }
        if (isStateful(this.checkedIcon)) {
            invalidate |= this.checkedIcon.setState(iArr);
        }
        if (isStateful(this.closeIcon)) {
            int[] closeIconMergedState = new int[(iArr.length + iArr2.length)];
            int i = newChipSurfaceColor2;
            System.arraycopy(iArr, 0, closeIconMergedState, 0, iArr.length);
            System.arraycopy(iArr2, 0, closeIconMergedState, iArr.length, iArr2.length);
            invalidate |= this.closeIcon.setState(closeIconMergedState);
        }
        if (RippleUtils.USE_FRAMEWORK_RIPPLE && isStateful(this.closeIconRipple)) {
            invalidate |= this.closeIconRipple.setState(iArr2);
        }
        if (invalidate) {
            invalidateSelf();
        }
        if (sizeChanged) {
            onSizeChange();
        }
        return invalidate;
    }

    private static boolean isStateful(ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    private static boolean isStateful(Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }

    private static boolean isStateful(TextAppearance textAppearance) {
        return (textAppearance == null || textAppearance.getTextColor() == null || !textAppearance.getTextColor().isStateful()) ? false : true;
    }

    public boolean onLayoutDirectionChanged(int layoutDirection) {
        boolean invalidate = super.onLayoutDirectionChanged(layoutDirection);
        if (showsChipIcon()) {
            invalidate |= DrawableCompat.setLayoutDirection(this.chipIcon, layoutDirection);
        }
        if (showsCheckedIcon()) {
            invalidate |= DrawableCompat.setLayoutDirection(this.checkedIcon, layoutDirection);
        }
        if (showsCloseIcon()) {
            invalidate |= DrawableCompat.setLayoutDirection(this.closeIcon, layoutDirection);
        }
        if (!invalidate) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        boolean invalidate = super.onLevelChange(level);
        if (showsChipIcon()) {
            invalidate |= this.chipIcon.setLevel(level);
        }
        if (showsCheckedIcon()) {
            invalidate |= this.checkedIcon.setLevel(level);
        }
        if (showsCloseIcon()) {
            invalidate |= this.closeIcon.setLevel(level);
        }
        if (invalidate) {
            invalidateSelf();
        }
        return invalidate;
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean invalidate = super.setVisible(visible, restart);
        if (showsChipIcon()) {
            invalidate |= this.chipIcon.setVisible(visible, restart);
        }
        if (showsCheckedIcon()) {
            invalidate |= this.checkedIcon.setVisible(visible, restart);
        }
        if (showsCloseIcon()) {
            invalidate |= this.closeIcon.setVisible(visible, restart);
        }
        if (invalidate) {
            invalidateSelf();
        }
        return invalidate;
    }

    public void setAlpha(int alpha2) {
        if (this.alpha != alpha2) {
            this.alpha = alpha2;
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setColorFilter(ColorFilter colorFilter2) {
        if (this.colorFilter != colorFilter2) {
            this.colorFilter = colorFilter2;
            invalidateSelf();
        }
    }

    public ColorFilter getColorFilter() {
        return this.colorFilter;
    }

    public void setTintList(ColorStateList tint2) {
        if (this.tint != tint2) {
            this.tint = tint2;
            onStateChange(getState());
        }
    }

    public void setTintMode(PorterDuff.Mode tintMode2) {
        if (this.tintMode != tintMode2) {
            this.tintMode = tintMode2;
            this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, tintMode2);
            invalidateSelf();
        }
    }

    public int getOpacity() {
        return -3;
    }

    public void getOutline(Outline outline) {
        if (this.isShapeThemingEnabled) {
            super.getOutline(outline);
            return;
        }
        Rect bounds = getBounds();
        if (!bounds.isEmpty()) {
            outline.setRoundRect(bounds, this.chipCornerRadius);
        } else {
            outline.setRoundRect(0, 0, getIntrinsicWidth(), getIntrinsicHeight(), this.chipCornerRadius);
        }
        outline.setAlpha(((float) getAlpha()) / 255.0f);
    }

    public void invalidateDrawable(Drawable who) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    private void unapplyChildDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback) null);
        }
    }

    private void applyChildDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(this);
            DrawableCompat.setLayoutDirection(drawable, DrawableCompat.getLayoutDirection(this));
            drawable.setLevel(getLevel());
            drawable.setVisible(isVisible(), false);
            if (drawable == this.closeIcon) {
                if (drawable.isStateful()) {
                    drawable.setState(getCloseIconState());
                }
                DrawableCompat.setTintList(drawable, this.closeIconTint);
                return;
            }
            Drawable drawable2 = this.chipIcon;
            if (drawable == drawable2 && this.hasChipIconTint) {
                DrawableCompat.setTintList(drawable2, this.chipIconTint);
            }
            if (drawable.isStateful()) {
                drawable.setState(getState());
            }
        }
    }

    private ColorFilter getTintColorFilter() {
        ColorFilter colorFilter2 = this.colorFilter;
        return colorFilter2 != null ? colorFilter2 : this.tintFilter;
    }

    private void updateCompatRippleColor() {
        this.compatRippleColor = this.useCompatRipple ? RippleUtils.sanitizeRippleDrawableColor(this.rippleColor) : null;
    }

    private void setChipSurfaceColor(ColorStateList chipSurfaceColor2) {
        if (this.chipSurfaceColor != chipSurfaceColor2) {
            this.chipSurfaceColor = chipSurfaceColor2;
            onStateChange(getState());
        }
    }

    private static boolean hasState(int[] stateSet, int state) {
        if (stateSet == null) {
            return false;
        }
        for (int s : stateSet) {
            if (s == state) {
                return true;
            }
        }
        return false;
    }

    public void setTextSize(float size) {
        TextAppearance textAppearance = getTextAppearance();
        if (textAppearance != null) {
            textAppearance.setTextSize(size);
            this.textDrawableHelper.getTextPaint().setTextSize(size);
            onTextSizeChange();
        }
    }

    public void setTextColor(int color) {
        setTextColor(ColorStateList.valueOf(color));
    }

    public void setTextColor(ColorStateList color) {
        TextAppearance textAppearance = getTextAppearance();
        if (textAppearance != null) {
            textAppearance.setTextColor(color);
            invalidateSelf();
        }
    }

    public ColorStateList getChipBackgroundColor() {
        return this.chipBackgroundColor;
    }

    public void setChipBackgroundColorResource(int id) {
        setChipBackgroundColor(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setChipBackgroundColor(ColorStateList chipBackgroundColor2) {
        if (this.chipBackgroundColor != chipBackgroundColor2) {
            this.chipBackgroundColor = chipBackgroundColor2;
            onStateChange(getState());
        }
    }

    public float getChipMinHeight() {
        return this.chipMinHeight;
    }

    public void setChipMinHeightResource(int id) {
        setChipMinHeight(this.context.getResources().getDimension(id));
    }

    public void setChipMinHeight(float chipMinHeight2) {
        if (this.chipMinHeight != chipMinHeight2) {
            this.chipMinHeight = chipMinHeight2;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getChipCornerRadius() {
        return this.isShapeThemingEnabled ? getTopLeftCornerResolvedSize() : this.chipCornerRadius;
    }

    @Deprecated
    public void setChipCornerRadiusResource(int id) {
        setChipCornerRadius(this.context.getResources().getDimension(id));
    }

    @Deprecated
    public void setChipCornerRadius(float chipCornerRadius2) {
        if (this.chipCornerRadius != chipCornerRadius2) {
            this.chipCornerRadius = chipCornerRadius2;
            setShapeAppearanceModel(getShapeAppearanceModel().withCornerSize(chipCornerRadius2));
        }
    }

    public ColorStateList getChipStrokeColor() {
        return this.chipStrokeColor;
    }

    public void setChipStrokeColorResource(int id) {
        setChipStrokeColor(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setChipStrokeColor(ColorStateList chipStrokeColor2) {
        if (this.chipStrokeColor != chipStrokeColor2) {
            this.chipStrokeColor = chipStrokeColor2;
            if (this.isShapeThemingEnabled) {
                setStrokeColor(chipStrokeColor2);
            }
            onStateChange(getState());
        }
    }

    public float getChipStrokeWidth() {
        return this.chipStrokeWidth;
    }

    public void setChipStrokeWidthResource(int id) {
        setChipStrokeWidth(this.context.getResources().getDimension(id));
    }

    public void setChipStrokeWidth(float chipStrokeWidth2) {
        if (this.chipStrokeWidth != chipStrokeWidth2) {
            this.chipStrokeWidth = chipStrokeWidth2;
            this.chipPaint.setStrokeWidth(chipStrokeWidth2);
            if (this.isShapeThemingEnabled) {
                super.setStrokeWidth(chipStrokeWidth2);
            }
            invalidateSelf();
        }
    }

    public ColorStateList getRippleColor() {
        return this.rippleColor;
    }

    public void setRippleColorResource(int id) {
        setRippleColor(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setRippleColor(ColorStateList rippleColor2) {
        if (this.rippleColor != rippleColor2) {
            this.rippleColor = rippleColor2;
            updateCompatRippleColor();
            onStateChange(getState());
        }
    }

    public CharSequence getText() {
        return this.text;
    }

    public void setTextResource(int id) {
        setText(this.context.getResources().getString(id));
    }

    public void setText(CharSequence text2) {
        if (text2 == null) {
            text2 = "";
        }
        if (!TextUtils.equals(this.text, text2)) {
            this.text = text2;
            this.textDrawableHelper.setTextWidthDirty(true);
            invalidateSelf();
            onSizeChange();
        }
    }

    public TextAppearance getTextAppearance() {
        return this.textDrawableHelper.getTextAppearance();
    }

    public void setTextAppearanceResource(int id) {
        setTextAppearance(new TextAppearance(this.context, id));
    }

    public void setTextAppearance(TextAppearance textAppearance) {
        this.textDrawableHelper.setTextAppearance(textAppearance, this.context);
    }

    public TextUtils.TruncateAt getEllipsize() {
        return this.truncateAt;
    }

    public void setEllipsize(TextUtils.TruncateAt truncateAt2) {
        this.truncateAt = truncateAt2;
    }

    public boolean isChipIconVisible() {
        return this.chipIconVisible;
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return isChipIconVisible();
    }

    public void setChipIconVisible(int id) {
        setChipIconVisible(this.context.getResources().getBoolean(id));
    }

    public void setChipIconVisible(boolean chipIconVisible2) {
        if (this.chipIconVisible != chipIconVisible2) {
            boolean oldShowsChipIcon = showsChipIcon();
            this.chipIconVisible = chipIconVisible2;
            boolean newShowsChipIcon = showsChipIcon();
            if (oldShowsChipIcon != newShowsChipIcon) {
                if (newShowsChipIcon) {
                    applyChildDrawable(this.chipIcon);
                } else {
                    unapplyChildDrawable(this.chipIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Deprecated
    public void setChipIconEnabledResource(int id) {
        setChipIconVisible(id);
    }

    @Deprecated
    public void setChipIconEnabled(boolean chipIconEnabled) {
        setChipIconVisible(chipIconEnabled);
    }

    public Drawable getChipIcon() {
        Drawable drawable = this.chipIcon;
        if (drawable != null) {
            return DrawableCompat.unwrap(drawable);
        }
        return null;
    }

    public void setChipIconResource(int id) {
        setChipIcon(AppCompatResources.getDrawable(this.context, id));
    }

    public void setChipIcon(Drawable chipIcon2) {
        Drawable oldChipIcon = getChipIcon();
        if (oldChipIcon != chipIcon2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.chipIcon = chipIcon2 != null ? DrawableCompat.wrap(chipIcon2).mutate() : null;
            float newChipIconWidth = calculateChipIconWidth();
            unapplyChildDrawable(oldChipIcon);
            if (showsChipIcon()) {
                applyChildDrawable(this.chipIcon);
            }
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public ColorStateList getChipIconTint() {
        return this.chipIconTint;
    }

    public void setChipIconTintResource(int id) {
        setChipIconTint(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setChipIconTint(ColorStateList chipIconTint2) {
        this.hasChipIconTint = true;
        if (this.chipIconTint != chipIconTint2) {
            this.chipIconTint = chipIconTint2;
            if (showsChipIcon()) {
                DrawableCompat.setTintList(this.chipIcon, chipIconTint2);
            }
            onStateChange(getState());
        }
    }

    public float getChipIconSize() {
        return this.chipIconSize;
    }

    public void setChipIconSizeResource(int id) {
        setChipIconSize(this.context.getResources().getDimension(id));
    }

    public void setChipIconSize(float chipIconSize2) {
        if (this.chipIconSize != chipIconSize2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.chipIconSize = chipIconSize2;
            float newChipIconWidth = calculateChipIconWidth();
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public boolean isCloseIconVisible() {
        return this.closeIconVisible;
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return isCloseIconVisible();
    }

    public void setCloseIconVisible(int id) {
        setCloseIconVisible(this.context.getResources().getBoolean(id));
    }

    public void setCloseIconVisible(boolean closeIconVisible2) {
        if (this.closeIconVisible != closeIconVisible2) {
            boolean oldShowsCloseIcon = showsCloseIcon();
            this.closeIconVisible = closeIconVisible2;
            boolean newShowsCloseIcon = showsCloseIcon();
            if (oldShowsCloseIcon != newShowsCloseIcon) {
                if (newShowsCloseIcon) {
                    applyChildDrawable(this.closeIcon);
                } else {
                    unapplyChildDrawable(this.closeIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Deprecated
    public void setCloseIconEnabledResource(int id) {
        setCloseIconVisible(id);
    }

    @Deprecated
    public void setCloseIconEnabled(boolean closeIconEnabled) {
        setCloseIconVisible(closeIconEnabled);
    }

    public Drawable getCloseIcon() {
        Drawable drawable = this.closeIcon;
        if (drawable != null) {
            return DrawableCompat.unwrap(drawable);
        }
        return null;
    }

    public void setCloseIconResource(int id) {
        setCloseIcon(AppCompatResources.getDrawable(this.context, id));
    }

    public void setCloseIcon(Drawable closeIcon2) {
        Drawable oldCloseIcon = getCloseIcon();
        if (oldCloseIcon != closeIcon2) {
            float oldCloseIconWidth = calculateCloseIconWidth();
            this.closeIcon = closeIcon2 != null ? DrawableCompat.wrap(closeIcon2).mutate() : null;
            if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
                updateFrameworkCloseIconRipple();
            }
            float newCloseIconWidth = calculateCloseIconWidth();
            unapplyChildDrawable(oldCloseIcon);
            if (showsCloseIcon()) {
                applyChildDrawable(this.closeIcon);
            }
            invalidateSelf();
            if (oldCloseIconWidth != newCloseIconWidth) {
                onSizeChange();
            }
        }
    }

    private void updateFrameworkCloseIconRipple() {
        this.closeIconRipple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(getRippleColor()), this.closeIcon, closeIconRippleMask);
    }

    public ColorStateList getCloseIconTint() {
        return this.closeIconTint;
    }

    public void setCloseIconTintResource(int id) {
        setCloseIconTint(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setCloseIconTint(ColorStateList closeIconTint2) {
        if (this.closeIconTint != closeIconTint2) {
            this.closeIconTint = closeIconTint2;
            if (showsCloseIcon()) {
                DrawableCompat.setTintList(this.closeIcon, closeIconTint2);
            }
            onStateChange(getState());
        }
    }

    public float getCloseIconSize() {
        return this.closeIconSize;
    }

    public void setCloseIconSizeResource(int id) {
        setCloseIconSize(this.context.getResources().getDimension(id));
    }

    public void setCloseIconSize(float closeIconSize2) {
        if (this.closeIconSize != closeIconSize2) {
            this.closeIconSize = closeIconSize2;
            invalidateSelf();
            if (showsCloseIcon()) {
                onSizeChange();
            }
        }
    }

    public void setCloseIconContentDescription(CharSequence closeIconContentDescription2) {
        if (this.closeIconContentDescription != closeIconContentDescription2) {
            this.closeIconContentDescription = BidiFormatter.getInstance().unicodeWrap(closeIconContentDescription2);
            invalidateSelf();
        }
    }

    public CharSequence getCloseIconContentDescription() {
        return this.closeIconContentDescription;
    }

    public boolean isCheckable() {
        return this.checkable;
    }

    public void setCheckableResource(int id) {
        setCheckable(this.context.getResources().getBoolean(id));
    }

    public void setCheckable(boolean checkable2) {
        if (this.checkable != checkable2) {
            this.checkable = checkable2;
            float oldChipIconWidth = calculateChipIconWidth();
            if (!checkable2 && this.currentChecked) {
                this.currentChecked = false;
            }
            float newChipIconWidth = calculateChipIconWidth();
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public boolean isCheckedIconVisible() {
        return this.checkedIconVisible;
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return isCheckedIconVisible();
    }

    public void setCheckedIconVisible(int id) {
        setCheckedIconVisible(this.context.getResources().getBoolean(id));
    }

    public void setCheckedIconVisible(boolean checkedIconVisible2) {
        if (this.checkedIconVisible != checkedIconVisible2) {
            boolean oldShowsCheckedIcon = showsCheckedIcon();
            this.checkedIconVisible = checkedIconVisible2;
            boolean newShowsCheckedIcon = showsCheckedIcon();
            if (oldShowsCheckedIcon != newShowsCheckedIcon) {
                if (newShowsCheckedIcon) {
                    applyChildDrawable(this.checkedIcon);
                } else {
                    unapplyChildDrawable(this.checkedIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Deprecated
    public void setCheckedIconEnabledResource(int id) {
        setCheckedIconVisible(this.context.getResources().getBoolean(id));
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean checkedIconEnabled) {
        setCheckedIconVisible(checkedIconEnabled);
    }

    public Drawable getCheckedIcon() {
        return this.checkedIcon;
    }

    public void setCheckedIconResource(int id) {
        setCheckedIcon(AppCompatResources.getDrawable(this.context, id));
    }

    public void setCheckedIcon(Drawable checkedIcon2) {
        if (this.checkedIcon != checkedIcon2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.checkedIcon = checkedIcon2;
            float newChipIconWidth = calculateChipIconWidth();
            unapplyChildDrawable(this.checkedIcon);
            applyChildDrawable(this.checkedIcon);
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public ColorStateList getCheckedIconTint() {
        return this.checkedIconTint;
    }

    public void setCheckedIconTintResource(int id) {
        setCheckedIconTint(AppCompatResources.getColorStateList(this.context, id));
    }

    public void setCheckedIconTint(ColorStateList checkedIconTint2) {
        if (this.checkedIconTint != checkedIconTint2) {
            this.checkedIconTint = checkedIconTint2;
            if (canShowCheckedIcon()) {
                DrawableCompat.setTintList(this.checkedIcon, checkedIconTint2);
            }
            onStateChange(getState());
        }
    }

    public MotionSpec getShowMotionSpec() {
        return this.showMotionSpec;
    }

    public void setShowMotionSpecResource(int id) {
        setShowMotionSpec(MotionSpec.createFromResource(this.context, id));
    }

    public void setShowMotionSpec(MotionSpec showMotionSpec2) {
        this.showMotionSpec = showMotionSpec2;
    }

    public MotionSpec getHideMotionSpec() {
        return this.hideMotionSpec;
    }

    public void setHideMotionSpecResource(int id) {
        setHideMotionSpec(MotionSpec.createFromResource(this.context, id));
    }

    public void setHideMotionSpec(MotionSpec hideMotionSpec2) {
        this.hideMotionSpec = hideMotionSpec2;
    }

    public float getChipStartPadding() {
        return this.chipStartPadding;
    }

    public void setChipStartPaddingResource(int id) {
        setChipStartPadding(this.context.getResources().getDimension(id));
    }

    public void setChipStartPadding(float chipStartPadding2) {
        if (this.chipStartPadding != chipStartPadding2) {
            this.chipStartPadding = chipStartPadding2;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getIconStartPadding() {
        return this.iconStartPadding;
    }

    public void setIconStartPaddingResource(int id) {
        setIconStartPadding(this.context.getResources().getDimension(id));
    }

    public void setIconStartPadding(float iconStartPadding2) {
        if (this.iconStartPadding != iconStartPadding2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.iconStartPadding = iconStartPadding2;
            float newChipIconWidth = calculateChipIconWidth();
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public float getIconEndPadding() {
        return this.iconEndPadding;
    }

    public void setIconEndPaddingResource(int id) {
        setIconEndPadding(this.context.getResources().getDimension(id));
    }

    public void setIconEndPadding(float iconEndPadding2) {
        if (this.iconEndPadding != iconEndPadding2) {
            float oldChipIconWidth = calculateChipIconWidth();
            this.iconEndPadding = iconEndPadding2;
            float newChipIconWidth = calculateChipIconWidth();
            invalidateSelf();
            if (oldChipIconWidth != newChipIconWidth) {
                onSizeChange();
            }
        }
    }

    public float getTextStartPadding() {
        return this.textStartPadding;
    }

    public void setTextStartPaddingResource(int id) {
        setTextStartPadding(this.context.getResources().getDimension(id));
    }

    public void setTextStartPadding(float textStartPadding2) {
        if (this.textStartPadding != textStartPadding2) {
            this.textStartPadding = textStartPadding2;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getTextEndPadding() {
        return this.textEndPadding;
    }

    public void setTextEndPaddingResource(int id) {
        setTextEndPadding(this.context.getResources().getDimension(id));
    }

    public void setTextEndPadding(float textEndPadding2) {
        if (this.textEndPadding != textEndPadding2) {
            this.textEndPadding = textEndPadding2;
            invalidateSelf();
            onSizeChange();
        }
    }

    public float getCloseIconStartPadding() {
        return this.closeIconStartPadding;
    }

    public void setCloseIconStartPaddingResource(int id) {
        setCloseIconStartPadding(this.context.getResources().getDimension(id));
    }

    public void setCloseIconStartPadding(float closeIconStartPadding2) {
        if (this.closeIconStartPadding != closeIconStartPadding2) {
            this.closeIconStartPadding = closeIconStartPadding2;
            invalidateSelf();
            if (showsCloseIcon()) {
                onSizeChange();
            }
        }
    }

    public float getCloseIconEndPadding() {
        return this.closeIconEndPadding;
    }

    public void setCloseIconEndPaddingResource(int id) {
        setCloseIconEndPadding(this.context.getResources().getDimension(id));
    }

    public void setCloseIconEndPadding(float closeIconEndPadding2) {
        if (this.closeIconEndPadding != closeIconEndPadding2) {
            this.closeIconEndPadding = closeIconEndPadding2;
            invalidateSelf();
            if (showsCloseIcon()) {
                onSizeChange();
            }
        }
    }

    public float getChipEndPadding() {
        return this.chipEndPadding;
    }

    public void setChipEndPaddingResource(int id) {
        setChipEndPadding(this.context.getResources().getDimension(id));
    }

    public void setChipEndPadding(float chipEndPadding2) {
        if (this.chipEndPadding != chipEndPadding2) {
            this.chipEndPadding = chipEndPadding2;
            invalidateSelf();
            onSizeChange();
        }
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setMaxWidth(int maxWidth2) {
        this.maxWidth = maxWidth2;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldDrawText() {
        return this.shouldDrawText;
    }

    /* access modifiers changed from: package-private */
    public void setShouldDrawText(boolean shouldDrawText2) {
        this.shouldDrawText = shouldDrawText2;
    }
}
