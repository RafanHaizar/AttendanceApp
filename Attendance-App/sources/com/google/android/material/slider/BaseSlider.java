package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.SeekBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.C1087R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewOverlayImpl;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.android.material.slider.BaseOnSliderTouchListener;
import com.google.android.material.slider.BaseSlider;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

abstract class BaseSlider<S extends BaseSlider<S, L, T>, L extends BaseOnChangeListener<S>, T extends BaseOnSliderTouchListener<S>> extends View {
    private static final int DEFAULT_LABEL_ANIMATION_ENTER_DURATION = 83;
    private static final int DEFAULT_LABEL_ANIMATION_EXIT_DURATION = 117;
    static final int DEF_STYLE_RES = C1087R.C1093style.Widget_MaterialComponents_Slider;
    private static final String EXCEPTION_ILLEGAL_DISCRETE_VALUE = "Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION = "minSeparation(%s) must be greater or equal to 0";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE = "minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE_UNIT = "minSeparation(%s) cannot be set as a dimension when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_STEP_SIZE = "The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range";
    private static final String EXCEPTION_ILLEGAL_VALUE = "Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)";
    private static final String EXCEPTION_ILLEGAL_VALUE_FROM = "valueFrom(%s) must be smaller than valueTo(%s)";
    private static final String EXCEPTION_ILLEGAL_VALUE_TO = "valueTo(%s) must be greater than valueFrom(%s)";
    private static final int HALO_ALPHA = 63;
    private static final int LABEL_ANIMATION_ENTER_DURATION_ATTR = C1087R.attr.motionDurationMedium4;
    private static final int LABEL_ANIMATION_ENTER_EASING_ATTR = C1087R.attr.motionEasingEmphasizedInterpolator;
    private static final int LABEL_ANIMATION_EXIT_DURATION_ATTR = C1087R.attr.motionDurationShort3;
    private static final int LABEL_ANIMATION_EXIT_EASING_ATTR = C1087R.attr.motionEasingEmphasizedAccelerateInterpolator;
    private static final int MIN_TOUCH_TARGET_DP = 48;
    private static final String TAG = BaseSlider.class.getSimpleName();
    private static final double THRESHOLD = 1.0E-4d;
    private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
    static final int UNIT_PX = 0;
    static final int UNIT_VALUE = 1;
    private static final String WARNING_FLOATING_POINT_ERROR = "Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.";
    private BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender;
    /* access modifiers changed from: private */
    public final AccessibilityHelper accessibilityHelper;
    private final AccessibilityManager accessibilityManager;
    private int activeThumbIdx;
    private final Paint activeTicksPaint;
    private final Paint activeTrackPaint;
    private final List<L> changeListeners;
    private Drawable customThumbDrawable;
    private List<Drawable> customThumbDrawablesForValues;
    private final MaterialShapeDrawable defaultThumbDrawable;
    private int defaultThumbRadius;
    private int defaultTrackHeight;
    private boolean dirtyConfig;
    private int focusedThumbIdx;
    private boolean forceDrawCompatHalo;
    private LabelFormatter formatter;
    private ColorStateList haloColor;
    private final Paint haloPaint;
    private int haloRadius;
    private final Paint inactiveTicksPaint;
    private final Paint inactiveTrackPaint;
    private boolean isLongPress;
    private int labelBehavior;
    private int labelPadding;
    private int labelStyle;
    /* access modifiers changed from: private */
    public final List<TooltipDrawable> labels;
    private boolean labelsAreAnimatedIn;
    private ValueAnimator labelsInAnimator;
    private ValueAnimator labelsOutAnimator;
    private MotionEvent lastEvent;
    private int minTouchTargetSize;
    private int minTrackSidePadding;
    private int minWidgetHeight;
    private final int scaledTouchSlop;
    private int separationUnit;
    private float stepSize;
    private boolean thumbIsPressed;
    private final Paint thumbPaint;
    private int thumbRadius;
    private ColorStateList tickColorActive;
    private ColorStateList tickColorInactive;
    private boolean tickVisible;
    private float[] ticksCoordinates;
    private float touchDownX;
    private final List<T> touchListeners;
    private float touchPosition;
    private ColorStateList trackColorActive;
    private ColorStateList trackColorInactive;
    private int trackHeight;
    private int trackSidePadding;
    private int trackWidth;
    private float valueFrom;
    private float valueTo;
    private ArrayList<Float> values;
    private int widgetHeight;

    public BaseSlider(Context context) {
        this(context, (AttributeSet) null);
    }

    public BaseSlider(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.sliderStyle);
    }

    public BaseSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.labels = new ArrayList();
        this.changeListeners = new ArrayList();
        this.touchListeners = new ArrayList();
        this.labelsAreAnimatedIn = false;
        this.thumbIsPressed = false;
        this.values = new ArrayList<>();
        this.activeThumbIdx = -1;
        this.focusedThumbIdx = -1;
        this.stepSize = 0.0f;
        this.tickVisible = true;
        this.isLongPress = false;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.defaultThumbDrawable = materialShapeDrawable;
        this.customThumbDrawablesForValues = Collections.emptyList();
        this.separationUnit = 0;
        Context context2 = getContext();
        Paint paint = new Paint();
        this.inactiveTrackPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint2 = new Paint();
        this.activeTrackPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        Paint paint3 = new Paint(1);
        this.thumbPaint = paint3;
        paint3.setStyle(Paint.Style.FILL);
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Paint paint4 = new Paint(1);
        this.haloPaint = paint4;
        paint4.setStyle(Paint.Style.FILL);
        Paint paint5 = new Paint();
        this.inactiveTicksPaint = paint5;
        paint5.setStyle(Paint.Style.STROKE);
        paint5.setStrokeCap(Paint.Cap.ROUND);
        Paint paint6 = new Paint();
        this.activeTicksPaint = paint6;
        paint6.setStyle(Paint.Style.STROKE);
        paint6.setStrokeCap(Paint.Cap.ROUND);
        loadResources(context2.getResources());
        processAttributes(context2, attrs, defStyleAttr);
        setFocusable(true);
        setClickable(true);
        materialShapeDrawable.setShadowCompatibilityMode(2);
        this.scaledTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        AccessibilityHelper accessibilityHelper2 = new AccessibilityHelper(this);
        this.accessibilityHelper = accessibilityHelper2;
        ViewCompat.setAccessibilityDelegate(this, accessibilityHelper2);
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
    }

    private void loadResources(Resources resources) {
        this.minWidgetHeight = resources.getDimensionPixelSize(C1087R.dimen.mtrl_slider_widget_height);
        int dimensionPixelOffset = resources.getDimensionPixelOffset(C1087R.dimen.mtrl_slider_track_side_padding);
        this.minTrackSidePadding = dimensionPixelOffset;
        this.trackSidePadding = dimensionPixelOffset;
        this.defaultThumbRadius = resources.getDimensionPixelSize(C1087R.dimen.mtrl_slider_thumb_radius);
        this.defaultTrackHeight = resources.getDimensionPixelSize(C1087R.dimen.mtrl_slider_track_height);
        this.labelPadding = resources.getDimensionPixelSize(C1087R.dimen.mtrl_slider_label_padding);
    }

    private void processAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        ColorStateList colorStateList4;
        ColorStateList colorStateList5;
        Context context2 = context;
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.Slider, defStyleAttr, DEF_STYLE_RES, new int[0]);
        this.labelStyle = a.getResourceId(C1087R.styleable.Slider_labelStyle, C1087R.C1093style.Widget_MaterialComponents_Tooltip);
        this.valueFrom = a.getFloat(C1087R.styleable.Slider_android_valueFrom, 0.0f);
        this.valueTo = a.getFloat(C1087R.styleable.Slider_android_valueTo, 1.0f);
        setValues(Float.valueOf(this.valueFrom));
        this.stepSize = a.getFloat(C1087R.styleable.Slider_android_stepSize, 0.0f);
        this.minTouchTargetSize = (int) Math.ceil((double) a.getDimension(C1087R.styleable.Slider_minTouchTargetSize, (float) Math.ceil((double) ViewUtils.dpToPx(getContext(), 48))));
        boolean hasTrackColor = a.hasValue(C1087R.styleable.Slider_trackColor);
        int trackColorInactiveRes = hasTrackColor ? C1087R.styleable.Slider_trackColor : C1087R.styleable.Slider_trackColorInactive;
        int trackColorActiveRes = hasTrackColor ? C1087R.styleable.Slider_trackColor : C1087R.styleable.Slider_trackColorActive;
        ColorStateList trackColorInactive2 = MaterialResources.getColorStateList(context2, a, trackColorInactiveRes);
        if (trackColorInactive2 != null) {
            colorStateList = trackColorInactive2;
        } else {
            colorStateList = AppCompatResources.getColorStateList(context2, C1087R.C1088color.material_slider_inactive_track_color);
        }
        setTrackInactiveTintList(colorStateList);
        ColorStateList trackColorActive2 = MaterialResources.getColorStateList(context2, a, trackColorActiveRes);
        if (trackColorActive2 != null) {
            colorStateList2 = trackColorActive2;
        } else {
            colorStateList2 = AppCompatResources.getColorStateList(context2, C1087R.C1088color.material_slider_active_track_color);
        }
        setTrackActiveTintList(colorStateList2);
        this.defaultThumbDrawable.setFillColor(MaterialResources.getColorStateList(context2, a, C1087R.styleable.Slider_thumbColor));
        if (a.hasValue(C1087R.styleable.Slider_thumbStrokeColor)) {
            setThumbStrokeColor(MaterialResources.getColorStateList(context2, a, C1087R.styleable.Slider_thumbStrokeColor));
        }
        setThumbStrokeWidth(a.getDimension(C1087R.styleable.Slider_thumbStrokeWidth, 0.0f));
        ColorStateList haloColor2 = MaterialResources.getColorStateList(context2, a, C1087R.styleable.Slider_haloColor);
        if (haloColor2 != null) {
            colorStateList3 = haloColor2;
        } else {
            colorStateList3 = AppCompatResources.getColorStateList(context2, C1087R.C1088color.material_slider_halo_color);
        }
        setHaloTintList(colorStateList3);
        this.tickVisible = a.getBoolean(C1087R.styleable.Slider_tickVisible, true);
        boolean hasTickColor = a.hasValue(C1087R.styleable.Slider_tickColor);
        int tickColorInactiveRes = hasTickColor ? C1087R.styleable.Slider_tickColor : C1087R.styleable.Slider_tickColorInactive;
        int tickColorActiveRes = hasTickColor ? C1087R.styleable.Slider_tickColor : C1087R.styleable.Slider_tickColorActive;
        ColorStateList tickColorInactive2 = MaterialResources.getColorStateList(context2, a, tickColorInactiveRes);
        if (tickColorInactive2 != null) {
            colorStateList4 = tickColorInactive2;
        } else {
            colorStateList4 = AppCompatResources.getColorStateList(context2, C1087R.C1088color.material_slider_inactive_tick_marks_color);
        }
        setTickInactiveTintList(colorStateList4);
        ColorStateList tickColorActive2 = MaterialResources.getColorStateList(context2, a, tickColorActiveRes);
        if (tickColorActive2 != null) {
            colorStateList5 = tickColorActive2;
        } else {
            colorStateList5 = AppCompatResources.getColorStateList(context2, C1087R.C1088color.material_slider_active_tick_marks_color);
        }
        setTickActiveTintList(colorStateList5);
        setThumbRadius(a.getDimensionPixelSize(C1087R.styleable.Slider_thumbRadius, 0));
        setHaloRadius(a.getDimensionPixelSize(C1087R.styleable.Slider_haloRadius, 0));
        setThumbElevation(a.getDimension(C1087R.styleable.Slider_thumbElevation, 0.0f));
        setTrackHeight(a.getDimensionPixelSize(C1087R.styleable.Slider_trackHeight, 0));
        setLabelBehavior(a.getInt(C1087R.styleable.Slider_labelBehavior, 0));
        if (!a.getBoolean(C1087R.styleable.Slider_android_enabled, true)) {
            setEnabled(false);
        }
        a.recycle();
    }

    private boolean maybeIncreaseTrackSidePadding() {
        int newTrackSidePadding = this.minTrackSidePadding + Math.max(Math.max(this.thumbRadius - this.defaultThumbRadius, 0), Math.max((this.trackHeight - this.defaultTrackHeight) / 2, 0));
        if (this.trackSidePadding == newTrackSidePadding) {
            return false;
        }
        this.trackSidePadding = newTrackSidePadding;
        if (!ViewCompat.isLaidOut(this)) {
            return true;
        }
        updateTrackWidth(getWidth());
        return true;
    }

    private void validateValueFrom() {
        if (this.valueFrom >= this.valueTo) {
            throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE_FROM, new Object[]{Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
        }
    }

    private void validateValueTo() {
        if (this.valueTo <= this.valueFrom) {
            throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE_TO, new Object[]{Float.valueOf(this.valueTo), Float.valueOf(this.valueFrom)}));
        }
    }

    private boolean valueLandsOnTick(float value) {
        return isMultipleOfStepSize(value - this.valueFrom);
    }

    private boolean isMultipleOfStepSize(float value) {
        double result = new BigDecimal(Float.toString(value)).divide(new BigDecimal(Float.toString(this.stepSize)), MathContext.DECIMAL64).doubleValue();
        double round = (double) Math.round(result);
        Double.isNaN(round);
        return Math.abs(round - result) < THRESHOLD;
    }

    private void validateStepSize() {
        if (this.stepSize > 0.0f && !valueLandsOnTick(this.valueTo)) {
            throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_STEP_SIZE, new Object[]{Float.valueOf(this.stepSize), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
        }
    }

    private void validateValues() {
        Iterator<Float> it = this.values.iterator();
        while (it.hasNext()) {
            Float value = it.next();
            if (value.floatValue() < this.valueFrom || value.floatValue() > this.valueTo) {
                throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE, new Object[]{value, Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
            } else if (this.stepSize > 0.0f && !valueLandsOnTick(value.floatValue())) {
                throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_DISCRETE_VALUE, new Object[]{value, Float.valueOf(this.valueFrom), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)}));
            }
        }
    }

    private void validateMinSeparation() {
        float minSeparation = getMinSeparation();
        if (minSeparation >= 0.0f) {
            float f = this.stepSize;
            if (f > 0.0f && minSeparation > 0.0f) {
                if (this.separationUnit != 1) {
                    throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE_UNIT, new Object[]{Float.valueOf(minSeparation), Float.valueOf(this.stepSize)}));
                } else if (minSeparation < f || !isMultipleOfStepSize(minSeparation)) {
                    throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE, new Object[]{Float.valueOf(minSeparation), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)}));
                }
            }
        } else {
            throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_MIN_SEPARATION, new Object[]{Float.valueOf(minSeparation)}));
        }
    }

    private void warnAboutFloatingPointError() {
        float f = this.stepSize;
        if (f != 0.0f) {
            if (((float) ((int) f)) != f) {
                Log.w(TAG, String.format(WARNING_FLOATING_POINT_ERROR, new Object[]{"stepSize", Float.valueOf(f)}));
            }
            float f2 = this.valueFrom;
            if (((float) ((int) f2)) != f2) {
                Log.w(TAG, String.format(WARNING_FLOATING_POINT_ERROR, new Object[]{"valueFrom", Float.valueOf(f2)}));
            }
            float f3 = this.valueTo;
            if (((float) ((int) f3)) != f3) {
                Log.w(TAG, String.format(WARNING_FLOATING_POINT_ERROR, new Object[]{"valueTo", Float.valueOf(f3)}));
            }
        }
    }

    private void validateConfigurationIfDirty() {
        if (this.dirtyConfig) {
            validateValueFrom();
            validateValueTo();
            validateStepSize();
            validateValues();
            validateMinSeparation();
            warnAboutFloatingPointError();
            this.dirtyConfig = false;
        }
    }

    public float getValueFrom() {
        return this.valueFrom;
    }

    public void setValueFrom(float valueFrom2) {
        this.valueFrom = valueFrom2;
        this.dirtyConfig = true;
        postInvalidate();
    }

    public float getValueTo() {
        return this.valueTo;
    }

    public void setValueTo(float valueTo2) {
        this.valueTo = valueTo2;
        this.dirtyConfig = true;
        postInvalidate();
    }

    /* access modifiers changed from: package-private */
    public List<Float> getValues() {
        return new ArrayList(this.values);
    }

    /* access modifiers changed from: package-private */
    public void setValues(Float... values2) {
        ArrayList<Float> list = new ArrayList<>();
        Collections.addAll(list, values2);
        setValuesInternal(list);
    }

    /* access modifiers changed from: package-private */
    public void setValues(List<Float> values2) {
        setValuesInternal(new ArrayList(values2));
    }

    private void setValuesInternal(ArrayList<Float> values2) {
        if (!values2.isEmpty()) {
            Collections.sort(values2);
            if (this.values.size() != values2.size() || !this.values.equals(values2)) {
                this.values = values2;
                this.dirtyConfig = true;
                this.focusedThumbIdx = 0;
                updateHaloHotspot();
                createLabelPool();
                dispatchOnChangedProgrammatically();
                postInvalidate();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("At least one value must be set");
    }

    private void createLabelPool() {
        int i;
        if (this.labels.size() > this.values.size()) {
            List<TooltipDrawable> tooltipDrawables = this.labels.subList(this.values.size(), this.labels.size());
            for (TooltipDrawable label : tooltipDrawables) {
                if (ViewCompat.isAttachedToWindow(this)) {
                    detachLabelFromContentView(label);
                }
            }
            tooltipDrawables.clear();
        }
        while (true) {
            i = 0;
            if (this.labels.size() >= this.values.size()) {
                break;
            }
            TooltipDrawable tooltipDrawable = TooltipDrawable.createFromAttributes(getContext(), (AttributeSet) null, 0, this.labelStyle);
            this.labels.add(tooltipDrawable);
            if (ViewCompat.isAttachedToWindow(this)) {
                attachLabelToContentView(tooltipDrawable);
            }
        }
        if (this.labels.size() != 1) {
            i = 1;
        }
        int strokeWidth = i;
        for (TooltipDrawable label2 : this.labels) {
            label2.setStrokeWidth((float) strokeWidth);
        }
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public void setStepSize(float stepSize2) {
        if (stepSize2 < 0.0f) {
            throw new IllegalArgumentException(String.format(EXCEPTION_ILLEGAL_STEP_SIZE, new Object[]{Float.valueOf(stepSize2), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
        } else if (this.stepSize != stepSize2) {
            this.stepSize = stepSize2;
            this.dirtyConfig = true;
            postInvalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public void setCustomThumbDrawable(int drawableResId) {
        setCustomThumbDrawable(getResources().getDrawable(drawableResId));
    }

    /* access modifiers changed from: package-private */
    public void setCustomThumbDrawable(Drawable drawable) {
        this.customThumbDrawable = initializeCustomThumbDrawable(drawable);
        this.customThumbDrawablesForValues.clear();
        postInvalidate();
    }

    /* access modifiers changed from: package-private */
    public void setCustomThumbDrawablesForValues(int... customThumbDrawableResIds) {
        Drawable[] customThumbDrawables = new Drawable[customThumbDrawableResIds.length];
        for (int i = 0; i < customThumbDrawableResIds.length; i++) {
            customThumbDrawables[i] = getResources().getDrawable(customThumbDrawableResIds[i]);
        }
        setCustomThumbDrawablesForValues(customThumbDrawables);
    }

    /* access modifiers changed from: package-private */
    public void setCustomThumbDrawablesForValues(Drawable... customThumbDrawables) {
        this.customThumbDrawable = null;
        this.customThumbDrawablesForValues = new ArrayList();
        for (Drawable originalDrawable : customThumbDrawables) {
            this.customThumbDrawablesForValues.add(initializeCustomThumbDrawable(originalDrawable));
        }
        postInvalidate();
    }

    private Drawable initializeCustomThumbDrawable(Drawable originalDrawable) {
        Drawable drawable = originalDrawable.mutate().getConstantState().newDrawable();
        adjustCustomThumbDrawableBounds(drawable);
        return drawable;
    }

    private void adjustCustomThumbDrawableBounds(Drawable drawable) {
        int thumbDiameter = this.thumbRadius * 2;
        int originalWidth = drawable.getIntrinsicWidth();
        int originalHeight = drawable.getIntrinsicHeight();
        if (originalWidth == -1 && originalHeight == -1) {
            drawable.setBounds(0, 0, thumbDiameter, thumbDiameter);
            return;
        }
        float scaleRatio = ((float) thumbDiameter) / ((float) Math.max(originalWidth, originalHeight));
        drawable.setBounds(0, 0, (int) (((float) originalWidth) * scaleRatio), (int) (((float) originalHeight) * scaleRatio));
    }

    public int getFocusedThumbIndex() {
        return this.focusedThumbIdx;
    }

    public void setFocusedThumbIndex(int index) {
        if (index < 0 || index >= this.values.size()) {
            throw new IllegalArgumentException("index out of range");
        }
        this.focusedThumbIdx = index;
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(index);
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void setActiveThumbIndex(int index) {
        this.activeThumbIdx = index;
    }

    public int getActiveThumbIndex() {
        return this.activeThumbIdx;
    }

    public void addOnChangeListener(L listener) {
        this.changeListeners.add(listener);
    }

    public void removeOnChangeListener(L listener) {
        this.changeListeners.remove(listener);
    }

    public void clearOnChangeListeners() {
        this.changeListeners.clear();
    }

    public void addOnSliderTouchListener(T listener) {
        this.touchListeners.add(listener);
    }

    public void removeOnSliderTouchListener(T listener) {
        this.touchListeners.remove(listener);
    }

    public void clearOnSliderTouchListeners() {
        this.touchListeners.clear();
    }

    public boolean hasLabelFormatter() {
        return this.formatter != null;
    }

    public void setLabelFormatter(LabelFormatter formatter2) {
        this.formatter = formatter2;
    }

    public float getThumbElevation() {
        return this.defaultThumbDrawable.getElevation();
    }

    public void setThumbElevation(float elevation) {
        this.defaultThumbDrawable.setElevation(elevation);
    }

    public void setThumbElevationResource(int elevation) {
        setThumbElevation(getResources().getDimension(elevation));
    }

    public int getThumbRadius() {
        return this.thumbRadius;
    }

    public void setThumbRadius(int radius) {
        if (radius != this.thumbRadius) {
            this.thumbRadius = radius;
            this.defaultThumbDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCorners(0, (float) this.thumbRadius).build());
            MaterialShapeDrawable materialShapeDrawable = this.defaultThumbDrawable;
            int i = this.thumbRadius;
            materialShapeDrawable.setBounds(0, 0, i * 2, i * 2);
            Drawable drawable = this.customThumbDrawable;
            if (drawable != null) {
                adjustCustomThumbDrawableBounds(drawable);
            }
            for (Drawable customDrawable : this.customThumbDrawablesForValues) {
                adjustCustomThumbDrawableBounds(customDrawable);
            }
            updateWidgetLayout();
        }
    }

    public void setThumbRadiusResource(int radius) {
        setThumbRadius(getResources().getDimensionPixelSize(radius));
    }

    public void setThumbStrokeColor(ColorStateList thumbStrokeColor) {
        this.defaultThumbDrawable.setStrokeColor(thumbStrokeColor);
        postInvalidate();
    }

    public void setThumbStrokeColorResource(int thumbStrokeColorResourceId) {
        if (thumbStrokeColorResourceId != 0) {
            setThumbStrokeColor(AppCompatResources.getColorStateList(getContext(), thumbStrokeColorResourceId));
        }
    }

    public ColorStateList getThumbStrokeColor() {
        return this.defaultThumbDrawable.getStrokeColor();
    }

    public void setThumbStrokeWidth(float thumbStrokeWidth) {
        this.defaultThumbDrawable.setStrokeWidth(thumbStrokeWidth);
        postInvalidate();
    }

    public void setThumbStrokeWidthResource(int thumbStrokeWidthResourceId) {
        if (thumbStrokeWidthResourceId != 0) {
            setThumbStrokeWidth(getResources().getDimension(thumbStrokeWidthResourceId));
        }
    }

    public float getThumbStrokeWidth() {
        return this.defaultThumbDrawable.getStrokeWidth();
    }

    public int getHaloRadius() {
        return this.haloRadius;
    }

    public void setHaloRadius(int radius) {
        if (radius != this.haloRadius) {
            this.haloRadius = radius;
            Drawable background = getBackground();
            if (shouldDrawCompatHalo() || !(background instanceof RippleDrawable)) {
                postInvalidate();
            } else {
                DrawableUtils.setRippleDrawableRadius((RippleDrawable) background, this.haloRadius);
            }
        }
    }

    public void setHaloRadiusResource(int radius) {
        setHaloRadius(getResources().getDimensionPixelSize(radius));
    }

    public int getLabelBehavior() {
        return this.labelBehavior;
    }

    public void setLabelBehavior(int labelBehavior2) {
        if (this.labelBehavior != labelBehavior2) {
            this.labelBehavior = labelBehavior2;
            requestLayout();
        }
    }

    private boolean shouldAlwaysShowLabel() {
        return this.labelBehavior == 3;
    }

    public int getTrackSidePadding() {
        return this.trackSidePadding;
    }

    public int getTrackWidth() {
        return this.trackWidth;
    }

    public int getTrackHeight() {
        return this.trackHeight;
    }

    public void setTrackHeight(int trackHeight2) {
        if (this.trackHeight != trackHeight2) {
            this.trackHeight = trackHeight2;
            invalidateTrack();
            updateWidgetLayout();
        }
    }

    private void updateWidgetLayout() {
        boolean sizeChanged = maybeIncreaseWidgetHeight();
        boolean sidePaddingChanged = maybeIncreaseTrackSidePadding();
        if (sizeChanged) {
            requestLayout();
        } else if (sidePaddingChanged) {
            postInvalidate();
        }
    }

    private boolean maybeIncreaseWidgetHeight() {
        int topAndBottomPaddings = getPaddingTop() + getPaddingBottom();
        int newWidgetHeight = Math.max(this.minWidgetHeight, Math.max(this.trackHeight + topAndBottomPaddings, (this.thumbRadius * 2) + getPaddingTop() + getPaddingBottom()));
        if (newWidgetHeight == this.widgetHeight) {
            return false;
        }
        this.widgetHeight = newWidgetHeight;
        return true;
    }

    public ColorStateList getHaloTintList() {
        return this.haloColor;
    }

    public void setHaloTintList(ColorStateList haloColor2) {
        if (!haloColor2.equals(this.haloColor)) {
            this.haloColor = haloColor2;
            Drawable background = getBackground();
            if (shouldDrawCompatHalo() || !(background instanceof RippleDrawable)) {
                this.haloPaint.setColor(getColorForState(haloColor2));
                this.haloPaint.setAlpha(63);
                invalidate();
                return;
            }
            ((RippleDrawable) background).setColor(haloColor2);
        }
    }

    public ColorStateList getThumbTintList() {
        return this.defaultThumbDrawable.getFillColor();
    }

    public void setThumbTintList(ColorStateList thumbColor) {
        if (!thumbColor.equals(this.defaultThumbDrawable.getFillColor())) {
            this.defaultThumbDrawable.setFillColor(thumbColor);
            invalidate();
        }
    }

    public ColorStateList getTickTintList() {
        if (this.tickColorInactive.equals(this.tickColorActive)) {
            return this.tickColorActive;
        }
        throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
    }

    public void setTickTintList(ColorStateList tickColor) {
        setTickInactiveTintList(tickColor);
        setTickActiveTintList(tickColor);
    }

    public ColorStateList getTickActiveTintList() {
        return this.tickColorActive;
    }

    public void setTickActiveTintList(ColorStateList tickColor) {
        if (!tickColor.equals(this.tickColorActive)) {
            this.tickColorActive = tickColor;
            this.activeTicksPaint.setColor(getColorForState(tickColor));
            invalidate();
        }
    }

    public ColorStateList getTickInactiveTintList() {
        return this.tickColorInactive;
    }

    public void setTickInactiveTintList(ColorStateList tickColor) {
        if (!tickColor.equals(this.tickColorInactive)) {
            this.tickColorInactive = tickColor;
            this.inactiveTicksPaint.setColor(getColorForState(tickColor));
            invalidate();
        }
    }

    public boolean isTickVisible() {
        return this.tickVisible;
    }

    public void setTickVisible(boolean tickVisible2) {
        if (this.tickVisible != tickVisible2) {
            this.tickVisible = tickVisible2;
            postInvalidate();
        }
    }

    public ColorStateList getTrackTintList() {
        if (this.trackColorInactive.equals(this.trackColorActive)) {
            return this.trackColorActive;
        }
        throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
    }

    public void setTrackTintList(ColorStateList trackColor) {
        setTrackInactiveTintList(trackColor);
        setTrackActiveTintList(trackColor);
    }

    public ColorStateList getTrackActiveTintList() {
        return this.trackColorActive;
    }

    public void setTrackActiveTintList(ColorStateList trackColor) {
        if (!trackColor.equals(this.trackColorActive)) {
            this.trackColorActive = trackColor;
            this.activeTrackPaint.setColor(getColorForState(trackColor));
            invalidate();
        }
    }

    public ColorStateList getTrackInactiveTintList() {
        return this.trackColorInactive;
    }

    public void setTrackInactiveTintList(ColorStateList trackColor) {
        if (!trackColor.equals(this.trackColorInactive)) {
            this.trackColorInactive = trackColor;
            this.inactiveTrackPaint.setColor(getColorForState(trackColor));
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
        ViewOverlayImpl contentViewOverlay;
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != 0 && (contentViewOverlay = ViewUtils.getContentViewOverlay(this)) != null) {
            for (TooltipDrawable label : this.labels) {
                contentViewOverlay.remove(label);
            }
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setLayerType(enabled ? 0 : 2, (Paint) null);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (TooltipDrawable label : this.labels) {
            attachLabelToContentView(label);
        }
    }

    private void attachLabelToContentView(TooltipDrawable label) {
        label.setRelativeToView(ViewUtils.getContentView(this));
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender2 = this.accessibilityEventSender;
        if (accessibilityEventSender2 != null) {
            removeCallbacks(accessibilityEventSender2);
        }
        this.labelsAreAnimatedIn = false;
        for (TooltipDrawable label : this.labels) {
            detachLabelFromContentView(label);
        }
        super.onDetachedFromWindow();
    }

    private void detachLabelFromContentView(TooltipDrawable label) {
        ViewOverlayImpl contentViewOverlay = ViewUtils.getContentViewOverlay(this);
        if (contentViewOverlay != null) {
            contentViewOverlay.remove(label);
            label.detachView(ViewUtils.getContentView(this));
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = this.widgetHeight;
        int i2 = 0;
        if (this.labelBehavior == 1 || shouldAlwaysShowLabel()) {
            i2 = this.labels.get(0).getIntrinsicHeight();
        }
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(i + i2, 1073741824));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateTrackWidth(w);
        updateHaloHotspot();
    }

    private void maybeCalculateTicksCoordinates() {
        if (this.stepSize > 0.0f) {
            validateConfigurationIfDirty();
            int tickCount = Math.min((int) (((this.valueTo - this.valueFrom) / this.stepSize) + 1.0f), (this.trackWidth / (this.trackHeight * 2)) + 1);
            float[] fArr = this.ticksCoordinates;
            if (fArr == null || fArr.length != tickCount * 2) {
                this.ticksCoordinates = new float[(tickCount * 2)];
            }
            float interval = ((float) this.trackWidth) / ((float) (tickCount - 1));
            for (int i = 0; i < tickCount * 2; i += 2) {
                float[] fArr2 = this.ticksCoordinates;
                fArr2[i] = ((float) this.trackSidePadding) + ((((float) i) / 2.0f) * interval);
                fArr2[i + 1] = (float) calculateTrackCenter();
            }
        }
    }

    private void updateTrackWidth(int width) {
        this.trackWidth = Math.max(width - (this.trackSidePadding * 2), 0);
        maybeCalculateTicksCoordinates();
    }

    /* access modifiers changed from: private */
    public void updateHaloHotspot() {
        if (!shouldDrawCompatHalo() && getMeasuredWidth() > 0) {
            Drawable background = getBackground();
            if (background instanceof RippleDrawable) {
                int x = (int) ((normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * ((float) this.trackWidth)) + ((float) this.trackSidePadding));
                int y = calculateTrackCenter();
                int i = this.haloRadius;
                DrawableCompat.setHotspotBounds(background, x - i, y - i, x + i, i + y);
            }
        }
    }

    private int calculateTrackCenter() {
        int i = this.widgetHeight / 2;
        int i2 = 0;
        if (this.labelBehavior == 1 || shouldAlwaysShowLabel()) {
            i2 = this.labels.get(0).getIntrinsicHeight();
        }
        return i + i2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.dirtyConfig) {
            validateConfigurationIfDirty();
            maybeCalculateTicksCoordinates();
        }
        super.onDraw(canvas);
        int yCenter = calculateTrackCenter();
        drawInactiveTrack(canvas, this.trackWidth, yCenter);
        if (((Float) Collections.max(getValues())).floatValue() > this.valueFrom) {
            drawActiveTrack(canvas, this.trackWidth, yCenter);
        }
        maybeDrawTicks(canvas);
        if ((this.thumbIsPressed || isFocused()) && isEnabled()) {
            maybeDrawCompatHalo(canvas, this.trackWidth, yCenter);
        }
        if ((this.activeThumbIdx != -1 || shouldAlwaysShowLabel()) && isEnabled()) {
            ensureLabelsAdded();
        } else {
            ensureLabelsRemoved();
        }
        drawThumbs(canvas, this.trackWidth, yCenter);
    }

    private float[] getActiveRange() {
        float max = ((Float) Collections.max(getValues())).floatValue();
        float left = normalizeValue(this.values.size() == 1 ? this.valueFrom : ((Float) Collections.min(getValues())).floatValue());
        float right = normalizeValue(max);
        if (isRtl()) {
            return new float[]{right, left};
        }
        return new float[]{left, right};
    }

    private void drawInactiveTrack(Canvas canvas, int width, int yCenter) {
        float[] activeRange = getActiveRange();
        int i = this.trackSidePadding;
        float right = ((float) i) + (activeRange[1] * ((float) width));
        if (right < ((float) (i + width))) {
            canvas.drawLine(right, (float) yCenter, (float) (i + width), (float) yCenter, this.inactiveTrackPaint);
        }
        int i2 = this.trackSidePadding;
        float left = ((float) i2) + (activeRange[0] * ((float) width));
        if (left > ((float) i2)) {
            canvas.drawLine((float) i2, (float) yCenter, left, (float) yCenter, this.inactiveTrackPaint);
        }
    }

    private float normalizeValue(float value) {
        float f = this.valueFrom;
        float normalized = (value - f) / (this.valueTo - f);
        if (isRtl()) {
            return 1.0f - normalized;
        }
        return normalized;
    }

    private void drawActiveTrack(Canvas canvas, int width, int yCenter) {
        float[] activeRange = getActiveRange();
        int i = this.trackSidePadding;
        float right = ((float) i) + (activeRange[1] * ((float) width));
        canvas.drawLine(((float) i) + (activeRange[0] * ((float) width)), (float) yCenter, right, (float) yCenter, this.activeTrackPaint);
    }

    private void maybeDrawTicks(Canvas canvas) {
        if (this.tickVisible && this.stepSize > 0.0f) {
            float[] activeRange = getActiveRange();
            int leftPivotIndex = pivotIndex(this.ticksCoordinates, activeRange[0]);
            int rightPivotIndex = pivotIndex(this.ticksCoordinates, activeRange[1]);
            canvas.drawPoints(this.ticksCoordinates, 0, leftPivotIndex * 2, this.inactiveTicksPaint);
            canvas.drawPoints(this.ticksCoordinates, leftPivotIndex * 2, (rightPivotIndex * 2) - (leftPivotIndex * 2), this.activeTicksPaint);
            float[] fArr = this.ticksCoordinates;
            canvas.drawPoints(fArr, rightPivotIndex * 2, fArr.length - (rightPivotIndex * 2), this.inactiveTicksPaint);
        }
    }

    private void drawThumbs(Canvas canvas, int width, int yCenter) {
        for (int i = 0; i < this.values.size(); i++) {
            float value = this.values.get(i).floatValue();
            Drawable drawable = this.customThumbDrawable;
            if (drawable != null) {
                drawThumbDrawable(canvas, width, yCenter, value, drawable);
            } else if (i < this.customThumbDrawablesForValues.size()) {
                drawThumbDrawable(canvas, width, yCenter, value, this.customThumbDrawablesForValues.get(i));
            } else {
                if (!isEnabled()) {
                    canvas.drawCircle(((float) this.trackSidePadding) + (normalizeValue(value) * ((float) width)), (float) yCenter, (float) this.thumbRadius, this.thumbPaint);
                }
                drawThumbDrawable(canvas, width, yCenter, value, this.defaultThumbDrawable);
            }
        }
    }

    private void drawThumbDrawable(Canvas canvas, int width, int top, float value, Drawable thumbDrawable) {
        canvas.save();
        canvas.translate(((float) (this.trackSidePadding + ((int) (normalizeValue(value) * ((float) width))))) - (((float) thumbDrawable.getBounds().width()) / 2.0f), ((float) top) - (((float) thumbDrawable.getBounds().height()) / 2.0f));
        thumbDrawable.draw(canvas);
        canvas.restore();
    }

    private void maybeDrawCompatHalo(Canvas canvas, int width, int top) {
        if (shouldDrawCompatHalo()) {
            int centerX = (int) (((float) this.trackSidePadding) + (normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * ((float) width)));
            if (Build.VERSION.SDK_INT < 28) {
                int i = this.haloRadius;
                canvas.clipRect((float) (centerX - i), (float) (top - i), (float) (centerX + i), (float) (i + top), Region.Op.UNION);
            }
            canvas.drawCircle((float) centerX, (float) top, (float) this.haloRadius, this.haloPaint);
        }
    }

    private boolean shouldDrawCompatHalo() {
        return this.forceDrawCompatHalo || Build.VERSION.SDK_INT < 21 || !(getBackground() instanceof RippleDrawable);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        float x = event.getX();
        float f = (x - ((float) this.trackSidePadding)) / ((float) this.trackWidth);
        this.touchPosition = f;
        float max = Math.max(0.0f, f);
        this.touchPosition = max;
        this.touchPosition = Math.min(1.0f, max);
        switch (event.getActionMasked()) {
            case 0:
                this.touchDownX = x;
                if (!isInVerticalScrollingContainer()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (pickActiveThumb()) {
                        requestFocus();
                        this.thumbIsPressed = true;
                        snapTouchPosition();
                        updateHaloHotspot();
                        invalidate();
                        onStartTrackingTouch();
                        break;
                    }
                }
                break;
            case 1:
            case 3:
                this.thumbIsPressed = false;
                MotionEvent motionEvent = this.lastEvent;
                if (motionEvent != null && motionEvent.getActionMasked() == 0 && Math.abs(this.lastEvent.getX() - event.getX()) <= ((float) this.scaledTouchSlop) && Math.abs(this.lastEvent.getY() - event.getY()) <= ((float) this.scaledTouchSlop) && pickActiveThumb()) {
                    onStartTrackingTouch();
                }
                if (this.activeThumbIdx != -1) {
                    snapTouchPosition();
                    this.activeThumbIdx = -1;
                    onStopTrackingTouch();
                }
                invalidate();
                break;
            case 2:
                if (!this.thumbIsPressed) {
                    if (isInVerticalScrollingContainer() && Math.abs(x - this.touchDownX) < ((float) this.scaledTouchSlop)) {
                        return false;
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                    onStartTrackingTouch();
                }
                if (pickActiveThumb()) {
                    this.thumbIsPressed = true;
                    snapTouchPosition();
                    updateHaloHotspot();
                    invalidate();
                    break;
                }
                break;
        }
        setPressed(this.thumbIsPressed);
        this.lastEvent = MotionEvent.obtain(event);
        return true;
    }

    private static int pivotIndex(float[] coordinates, float position) {
        return Math.round(((float) ((coordinates.length / 2) - 1)) * position);
    }

    private double snapPosition(float position) {
        float f = this.stepSize;
        if (f <= 0.0f) {
            return (double) position;
        }
        int stepCount = (int) ((this.valueTo - this.valueFrom) / f);
        double round = (double) Math.round(((float) stepCount) * position);
        double d = (double) stepCount;
        Double.isNaN(round);
        Double.isNaN(d);
        return round / d;
    }

    /* access modifiers changed from: protected */
    public boolean pickActiveThumb() {
        if (this.activeThumbIdx != -1) {
            return true;
        }
        float touchValue = getValueOfTouchPositionAbsolute();
        float touchX = valueToX(touchValue);
        this.activeThumbIdx = 0;
        float activeThumbDiff = Math.abs(this.values.get(0).floatValue() - touchValue);
        for (int i = 1; i < this.values.size(); i++) {
            float valueDiff = Math.abs(this.values.get(i).floatValue() - touchValue);
            float valueX = valueToX(this.values.get(i).floatValue());
            if (Float.compare(valueDiff, activeThumbDiff) > 1) {
                break;
            }
            boolean movingForward = !isRtl() ? valueX - touchX < 0.0f : valueX - touchX > 0.0f;
            if (Float.compare(valueDiff, activeThumbDiff) < 0) {
                activeThumbDiff = valueDiff;
                this.activeThumbIdx = i;
            } else if (Float.compare(valueDiff, activeThumbDiff) != 0) {
                continue;
            } else if (Math.abs(valueX - touchX) < ((float) this.scaledTouchSlop)) {
                this.activeThumbIdx = -1;
                return false;
            } else if (movingForward) {
                activeThumbDiff = valueDiff;
                this.activeThumbIdx = i;
            }
        }
        if (this.activeThumbIdx != -1) {
            return true;
        }
        return false;
    }

    private float getValueOfTouchPositionAbsolute() {
        float position = this.touchPosition;
        if (isRtl()) {
            position = 1.0f - position;
        }
        float f = this.valueTo;
        float f2 = this.valueFrom;
        return ((f - f2) * position) + f2;
    }

    private boolean snapTouchPosition() {
        return snapActiveThumbToValue(getValueOfTouchPosition());
    }

    private boolean snapActiveThumbToValue(float value) {
        return snapThumbToValue(this.activeThumbIdx, value);
    }

    /* access modifiers changed from: private */
    public boolean snapThumbToValue(int idx, float value) {
        this.focusedThumbIdx = idx;
        if (((double) Math.abs(value - this.values.get(idx).floatValue())) < THRESHOLD) {
            return false;
        }
        this.values.set(idx, Float.valueOf(getClampedValue(idx, value)));
        dispatchOnChangedFromUser(idx);
        return true;
    }

    private float getClampedValue(int idx, float value) {
        float minSeparation = getMinSeparation();
        float minSeparation2 = this.separationUnit == 0 ? dimenToValue(minSeparation) : minSeparation;
        if (isRtl()) {
            minSeparation2 = -minSeparation2;
        }
        return MathUtils.clamp(value, idx + -1 < 0 ? this.valueFrom : this.values.get(idx - 1).floatValue() + minSeparation2, idx + 1 >= this.values.size() ? this.valueTo : this.values.get(idx + 1).floatValue() - minSeparation2);
    }

    private float dimenToValue(float dimen) {
        if (dimen == 0.0f) {
            return 0.0f;
        }
        float f = (dimen - ((float) this.trackSidePadding)) / ((float) this.trackWidth);
        float f2 = this.valueFrom;
        return (f * (f2 - this.valueTo)) + f2;
    }

    /* access modifiers changed from: protected */
    public void setSeparationUnit(int separationUnit2) {
        this.separationUnit = separationUnit2;
        this.dirtyConfig = true;
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public float getMinSeparation() {
        return 0.0f;
    }

    private float getValueOfTouchPosition() {
        double position = snapPosition(this.touchPosition);
        if (isRtl()) {
            position = 1.0d - position;
        }
        float f = this.valueTo;
        float f2 = this.valueFrom;
        double d = (double) (f - f2);
        Double.isNaN(d);
        double d2 = (double) f2;
        Double.isNaN(d2);
        return (float) ((d * position) + d2);
    }

    private float valueToX(float value) {
        return (normalizeValue(value) * ((float) this.trackWidth)) + ((float) this.trackSidePadding);
    }

    private static float getAnimatorCurrentValueOrDefault(ValueAnimator animator, float defaultValue) {
        if (animator == null || !animator.isRunning()) {
            return defaultValue;
        }
        float value = ((Float) animator.getAnimatedValue()).floatValue();
        animator.cancel();
        return value;
    }

    private ValueAnimator createLabelAnimator(boolean enter) {
        TimeInterpolator interpolator;
        int duration;
        float endFraction = 0.0f;
        float startFraction = getAnimatorCurrentValueOrDefault(enter ? this.labelsOutAnimator : this.labelsInAnimator, enter ? 0.0f : 1.0f);
        if (enter) {
            endFraction = 1.0f;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{startFraction, endFraction});
        if (enter) {
            duration = MotionUtils.resolveThemeDuration(getContext(), LABEL_ANIMATION_ENTER_DURATION_ATTR, 83);
            interpolator = MotionUtils.resolveThemeInterpolator(getContext(), LABEL_ANIMATION_ENTER_EASING_ATTR, AnimationUtils.DECELERATE_INTERPOLATOR);
        } else {
            duration = MotionUtils.resolveThemeDuration(getContext(), LABEL_ANIMATION_EXIT_DURATION_ATTR, 117);
            interpolator = MotionUtils.resolveThemeInterpolator(getContext(), LABEL_ANIMATION_EXIT_EASING_ATTR, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        }
        animator.setDuration((long) duration);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = ((Float) animation.getAnimatedValue()).floatValue();
                for (TooltipDrawable label : BaseSlider.this.labels) {
                    label.setRevealFraction(fraction);
                }
                ViewCompat.postInvalidateOnAnimation(BaseSlider.this);
            }
        });
        return animator;
    }

    private void ensureLabelsRemoved() {
        if (this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = false;
            ValueAnimator createLabelAnimator = createLabelAnimator(false);
            this.labelsOutAnimator = createLabelAnimator;
            this.labelsInAnimator = null;
            createLabelAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ViewOverlayImpl contentViewOverlay = ViewUtils.getContentViewOverlay(BaseSlider.this);
                    for (TooltipDrawable label : BaseSlider.this.labels) {
                        contentViewOverlay.remove(label);
                    }
                }
            });
            this.labelsOutAnimator.start();
        }
    }

    private void ensureLabelsAdded() {
        if (this.labelBehavior != 2) {
            if (!this.labelsAreAnimatedIn) {
                this.labelsAreAnimatedIn = true;
                ValueAnimator createLabelAnimator = createLabelAnimator(true);
                this.labelsInAnimator = createLabelAnimator;
                this.labelsOutAnimator = null;
                createLabelAnimator.start();
            }
            Iterator<TooltipDrawable> labelItr = this.labels.iterator();
            for (int i = 0; i < this.values.size() && labelItr.hasNext(); i++) {
                if (i != this.focusedThumbIdx) {
                    setValueForLabel(labelItr.next(), this.values.get(i).floatValue());
                }
            }
            if (labelItr.hasNext() != 0) {
                setValueForLabel(labelItr.next(), this.values.get(this.focusedThumbIdx).floatValue());
            } else {
                throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", new Object[]{Integer.valueOf(this.labels.size()), Integer.valueOf(this.values.size())}));
            }
        }
    }

    /* access modifiers changed from: private */
    public String formatValue(float value) {
        if (hasLabelFormatter()) {
            return this.formatter.getFormattedValue(value);
        }
        return String.format(((float) ((int) value)) == value ? "%.0f" : "%.2f", new Object[]{Float.valueOf(value)});
    }

    private void setValueForLabel(TooltipDrawable label, float value) {
        label.setText(formatValue(value));
        int left = (this.trackSidePadding + ((int) (normalizeValue(value) * ((float) this.trackWidth)))) - (label.getIntrinsicWidth() / 2);
        int top = calculateTrackCenter() - (this.labelPadding + this.thumbRadius);
        label.setBounds(left, top - label.getIntrinsicHeight(), label.getIntrinsicWidth() + left, top);
        Rect rect = new Rect(label.getBounds());
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, rect);
        label.setBounds(rect);
        ViewUtils.getContentViewOverlay(this).add(label);
    }

    private void invalidateTrack() {
        this.inactiveTrackPaint.setStrokeWidth((float) this.trackHeight);
        this.activeTrackPaint.setStrokeWidth((float) this.trackHeight);
        this.inactiveTicksPaint.setStrokeWidth(((float) this.trackHeight) / 2.0f);
        this.activeTicksPaint.setStrokeWidth(((float) this.trackHeight) / 2.0f);
    }

    private boolean isInVerticalScrollingContainer() {
        ViewParent p = getParent();
        while (true) {
            boolean canScrollVertically = false;
            if (!(p instanceof ViewGroup)) {
                return false;
            }
            ViewGroup parent = (ViewGroup) p;
            if (parent.canScrollVertically(1) || parent.canScrollVertically(-1)) {
                canScrollVertically = true;
            }
            if (canScrollVertically && parent.shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
    }

    private void dispatchOnChangedProgrammatically() {
        for (L listener : this.changeListeners) {
            Iterator<Float> it = this.values.iterator();
            while (it.hasNext()) {
                listener.onValueChange(this, it.next().floatValue(), false);
            }
        }
    }

    private void dispatchOnChangedFromUser(int idx) {
        for (L listener : this.changeListeners) {
            listener.onValueChange(this, this.values.get(idx).floatValue(), true);
        }
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        if (accessibilityManager2 != null && accessibilityManager2.isEnabled()) {
            scheduleAccessibilityEventSender(idx);
        }
    }

    private void onStartTrackingTouch() {
        for (T listener : this.touchListeners) {
            listener.onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch() {
        for (T listener : this.touchListeners) {
            listener.onStopTrackingTouch(this);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
        this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
        this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
        this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
        for (TooltipDrawable label : this.labels) {
            if (label.isStateful()) {
                label.setState(getDrawableState());
            }
        }
        if (this.defaultThumbDrawable.isStateful()) {
            this.defaultThumbDrawable.setState(getDrawableState());
        }
        this.haloPaint.setColor(getColorForState(this.haloColor));
        this.haloPaint.setAlpha(63);
    }

    private int getColorForState(ColorStateList colorStateList) {
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    /* access modifiers changed from: package-private */
    public void forceDrawCompatHalo(boolean force) {
        this.forceDrawCompatHalo = force;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isEnabled()) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.values.size() == 1) {
            this.activeThumbIdx = 0;
        }
        if (this.activeThumbIdx == -1) {
            Boolean handled = onKeyDownNoActiveThumb(keyCode, event);
            return handled != null ? handled.booleanValue() : super.onKeyDown(keyCode, event);
        }
        this.isLongPress |= event.isLongPress();
        Float increment = calculateIncrementForKey(keyCode);
        if (increment != null) {
            if (snapActiveThumbToValue(this.values.get(this.activeThumbIdx).floatValue() + increment.floatValue())) {
                updateHaloHotspot();
                postInvalidate();
            }
            return true;
        }
        switch (keyCode) {
            case 23:
            case 66:
                this.activeThumbIdx = -1;
                postInvalidate();
                return true;
            case 61:
                if (event.hasNoModifiers()) {
                    return moveFocus(1);
                }
                if (event.isShiftPressed()) {
                    return moveFocus(-1);
                }
                return false;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private Boolean onKeyDownNoActiveThumb(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 21:
                moveFocusInAbsoluteDirection(-1);
                return true;
            case 22:
                moveFocusInAbsoluteDirection(1);
                return true;
            case 23:
            case 66:
                this.activeThumbIdx = this.focusedThumbIdx;
                postInvalidate();
                return true;
            case 61:
                if (event.hasNoModifiers()) {
                    return Boolean.valueOf(moveFocus(1));
                }
                if (event.isShiftPressed()) {
                    return Boolean.valueOf(moveFocus(-1));
                }
                return false;
            case 69:
                moveFocus(-1);
                return true;
            case 70:
            case 81:
                moveFocus(1);
                return true;
            default:
                return null;
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        this.isLongPress = false;
        return super.onKeyUp(keyCode, event);
    }

    /* access modifiers changed from: package-private */
    public final boolean isRtl() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }

    private boolean moveFocus(int direction) {
        int oldFocusedThumbIdx = this.focusedThumbIdx;
        int clamp = (int) MathUtils.clamp(((long) oldFocusedThumbIdx) + ((long) direction), 0, (long) (this.values.size() - 1));
        this.focusedThumbIdx = clamp;
        if (clamp == oldFocusedThumbIdx) {
            return false;
        }
        if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = clamp;
        }
        updateHaloHotspot();
        postInvalidate();
        return true;
    }

    private boolean moveFocusInAbsoluteDirection(int direction) {
        if (isRtl()) {
            direction = direction == Integer.MIN_VALUE ? Integer.MAX_VALUE : -direction;
        }
        return moveFocus(direction);
    }

    private Float calculateIncrementForKey(int keyCode) {
        float increment = this.isLongPress ? calculateStepIncrement(20) : calculateStepIncrement();
        switch (keyCode) {
            case 21:
                return Float.valueOf(isRtl() ? increment : -increment);
            case 22:
                return Float.valueOf(isRtl() ? -increment : increment);
            case 69:
                return Float.valueOf(-increment);
            case 70:
            case 81:
                return Float.valueOf(increment);
            default:
                return null;
        }
    }

    private float calculateStepIncrement() {
        float f = this.stepSize;
        if (f == 0.0f) {
            return 1.0f;
        }
        return f;
    }

    /* access modifiers changed from: private */
    public float calculateStepIncrement(int stepFactor) {
        float increment = calculateStepIncrement();
        float numSteps = (this.valueTo - this.valueFrom) / increment;
        if (numSteps <= ((float) stepFactor)) {
            return increment;
        }
        return ((float) Math.round(numSteps / ((float) stepFactor))) * increment;
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (!gainFocus) {
            this.activeThumbIdx = -1;
            this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
            return;
        }
        focusThumbOnFocusGained(direction);
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
    }

    private void focusThumbOnFocusGained(int direction) {
        switch (direction) {
            case 1:
                moveFocus(Integer.MAX_VALUE);
                return;
            case 2:
                moveFocus(Integer.MIN_VALUE);
                return;
            case 17:
                moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
                return;
            case 66:
                moveFocusInAbsoluteDirection(Integer.MIN_VALUE);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public final int getAccessibilityFocusedVirtualViewId() {
        return this.accessibilityHelper.getAccessibilityFocusedVirtualViewId();
    }

    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    public boolean dispatchHoverEvent(MotionEvent event) {
        return this.accessibilityHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    private void scheduleAccessibilityEventSender(int idx) {
        BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender2 = this.accessibilityEventSender;
        if (accessibilityEventSender2 == null) {
            this.accessibilityEventSender = new AccessibilityEventSender();
        } else {
            removeCallbacks(accessibilityEventSender2);
        }
        this.accessibilityEventSender.setVirtualViewId(idx);
        postDelayed(this.accessibilityEventSender, 200);
    }

    private class AccessibilityEventSender implements Runnable {
        int virtualViewId;

        private AccessibilityEventSender() {
            this.virtualViewId = -1;
        }

        /* access modifiers changed from: package-private */
        public void setVirtualViewId(int virtualViewId2) {
            this.virtualViewId = virtualViewId2;
        }

        public void run() {
            BaseSlider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SliderState sliderState = new SliderState(super.onSaveInstanceState());
        sliderState.valueFrom = this.valueFrom;
        sliderState.valueTo = this.valueTo;
        sliderState.values = new ArrayList<>(this.values);
        sliderState.stepSize = this.stepSize;
        sliderState.hasFocus = hasFocus();
        return sliderState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        SliderState sliderState = (SliderState) state;
        super.onRestoreInstanceState(sliderState.getSuperState());
        this.valueFrom = sliderState.valueFrom;
        this.valueTo = sliderState.valueTo;
        setValuesInternal(sliderState.values);
        this.stepSize = sliderState.stepSize;
        if (sliderState.hasFocus) {
            requestFocus();
        }
    }

    static class SliderState extends View.BaseSavedState {
        public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator<SliderState>() {
            public SliderState createFromParcel(Parcel source) {
                return new SliderState(source);
            }

            public SliderState[] newArray(int size) {
                return new SliderState[size];
            }
        };
        boolean hasFocus;
        float stepSize;
        float valueFrom;
        float valueTo;
        ArrayList<Float> values;

        SliderState(Parcelable superState) {
            super(superState);
        }

        private SliderState(Parcel source) {
            super(source);
            this.valueFrom = source.readFloat();
            this.valueTo = source.readFloat();
            ArrayList<Float> arrayList = new ArrayList<>();
            this.values = arrayList;
            source.readList(arrayList, Float.class.getClassLoader());
            this.stepSize = source.readFloat();
            this.hasFocus = source.createBooleanArray()[0];
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(this.valueFrom);
            dest.writeFloat(this.valueTo);
            dest.writeList(this.values);
            dest.writeFloat(this.stepSize);
            dest.writeBooleanArray(new boolean[]{this.hasFocus});
        }
    }

    /* access modifiers changed from: package-private */
    public void updateBoundsForVirtualViewId(int virtualViewId, Rect virtualViewBounds) {
        int x = this.trackSidePadding + ((int) (normalizeValue(getValues().get(virtualViewId).floatValue()) * ((float) this.trackWidth)));
        int y = calculateTrackCenter();
        int i = this.thumbRadius;
        int i2 = this.minTouchTargetSize;
        if (i <= i2) {
            i = i2;
        }
        int touchTargetRadius = i / 2;
        virtualViewBounds.set(x - touchTargetRadius, y - touchTargetRadius, x + touchTargetRadius, y + touchTargetRadius);
    }

    private static class AccessibilityHelper extends ExploreByTouchHelper {
        private final BaseSlider<?, ?, ?> slider;
        final Rect virtualViewBounds = new Rect();

        AccessibilityHelper(BaseSlider<?, ?, ?> slider2) {
            super(slider2);
            this.slider = slider2;
        }

        /* access modifiers changed from: protected */
        public int getVirtualViewAt(float x, float y) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                this.slider.updateBoundsForVirtualViewId(i, this.virtualViewBounds);
                if (this.virtualViewBounds.contains((int) x, (int) y)) {
                    return i;
                }
            }
            return -1;
        }

        /* access modifiers changed from: protected */
        public void getVisibleVirtualViews(List<Integer> virtualViewIds) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                virtualViewIds.add(Integer.valueOf(i));
            }
        }

        /* access modifiers changed from: protected */
        public void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat info) {
            info.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
            List<Float> values = this.slider.getValues();
            float value = values.get(virtualViewId).floatValue();
            float valueFrom = this.slider.getValueFrom();
            float valueTo = this.slider.getValueTo();
            if (this.slider.isEnabled()) {
                if (value > valueFrom) {
                    info.addAction(8192);
                }
                if (value < valueTo) {
                    info.addAction(4096);
                }
            }
            info.setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat.obtain(1, valueFrom, valueTo, value));
            info.setClassName(SeekBar.class.getName());
            StringBuilder contentDescription = new StringBuilder();
            if (this.slider.getContentDescription() != null) {
                contentDescription.append(this.slider.getContentDescription()).append(",");
            }
            String verbalValue = this.slider.formatValue(value);
            String verbalValueType = this.slider.getContext().getString(C1087R.string.material_slider_value);
            if (values.size() > 1) {
                verbalValueType = startOrEndDescription(virtualViewId);
            }
            contentDescription.append(String.format(Locale.US, "%s, %s", new Object[]{verbalValueType, verbalValue}));
            info.setContentDescription(contentDescription.toString());
            this.slider.updateBoundsForVirtualViewId(virtualViewId, this.virtualViewBounds);
            info.setBoundsInParent(this.virtualViewBounds);
        }

        private String startOrEndDescription(int virtualViewId) {
            if (virtualViewId == this.slider.getValues().size() - 1) {
                return this.slider.getContext().getString(C1087R.string.material_slider_range_end);
            }
            if (virtualViewId == 0) {
                return this.slider.getContext().getString(C1087R.string.material_slider_range_start);
            }
            return "";
        }

        /* access modifiers changed from: protected */
        public boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (!this.slider.isEnabled()) {
                return false;
            }
            switch (action) {
                case 4096:
                case 8192:
                    float increment = this.slider.calculateStepIncrement(20);
                    if (action == 8192) {
                        increment = -increment;
                    }
                    if (this.slider.isRtl()) {
                        increment = -increment;
                    }
                    if (!this.slider.snapThumbToValue(virtualViewId, MathUtils.clamp(this.slider.getValues().get(virtualViewId).floatValue() + increment, this.slider.getValueFrom(), this.slider.getValueTo()))) {
                        return false;
                    }
                    this.slider.updateHaloHotspot();
                    this.slider.postInvalidate();
                    invalidateVirtualView(virtualViewId);
                    return true;
                case 16908349:
                    if (arguments == null || !arguments.containsKey(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE)) {
                        return false;
                    }
                    if (!this.slider.snapThumbToValue(virtualViewId, arguments.getFloat(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE))) {
                        return false;
                    }
                    this.slider.updateHaloHotspot();
                    this.slider.postInvalidate();
                    invalidateVirtualView(virtualViewId);
                    return true;
                default:
                    return false;
            }
        }
    }
}
