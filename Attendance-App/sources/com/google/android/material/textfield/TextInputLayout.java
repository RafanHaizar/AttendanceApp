package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import com.google.android.material.C1087R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class TextInputLayout extends LinearLayout {
    public static final int BOX_BACKGROUND_FILLED = 1;
    public static final int BOX_BACKGROUND_NONE = 0;
    public static final int BOX_BACKGROUND_OUTLINE = 2;
    private static final int DEFAULT_PLACEHOLDER_FADE_DURATION = 87;
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_Design_TextInputLayout;
    private static final int[][] EDIT_TEXT_BACKGROUND_RIPPLE_STATE = {new int[]{16842919}, new int[0]};
    public static final int END_ICON_CLEAR_TEXT = 2;
    public static final int END_ICON_CUSTOM = -1;
    public static final int END_ICON_DROPDOWN_MENU = 3;
    public static final int END_ICON_NONE = 0;
    public static final int END_ICON_PASSWORD_TOGGLE = 1;
    private static final int INVALID_MAX_LENGTH = -1;
    private static final int LABEL_SCALE_ANIMATION_DURATION = 167;
    private static final String LOG_TAG = "TextInputLayout";
    private static final int NO_WIDTH = -1;
    private static final int PLACEHOLDER_START_DELAY = 67;
    private ValueAnimator animator;
    private boolean areCornerRadiiRtl;
    private MaterialShapeDrawable boxBackground;
    private boolean boxBackgroundApplied;
    private int boxBackgroundColor;
    private int boxBackgroundMode;
    private int boxCollapsedPaddingTopPx;
    private final int boxLabelCutoutPaddingPx;
    private int boxStrokeColor;
    private int boxStrokeWidthDefaultPx;
    private int boxStrokeWidthFocusedPx;
    private int boxStrokeWidthPx;
    private MaterialShapeDrawable boxUnderlineDefault;
    private MaterialShapeDrawable boxUnderlineFocused;
    final CollapsingTextHelper collapsingTextHelper;
    boolean counterEnabled;
    private int counterMaxLength;
    private int counterOverflowTextAppearance;
    private ColorStateList counterOverflowTextColor;
    private boolean counterOverflowed;
    private int counterTextAppearance;
    private ColorStateList counterTextColor;
    private TextView counterView;
    private int defaultFilledBackgroundColor;
    private ColorStateList defaultHintTextColor;
    private int defaultStrokeColor;
    private int disabledColor;
    private int disabledFilledBackgroundColor;
    EditText editText;
    private final LinkedHashSet<OnEditTextAttachedListener> editTextAttachedListeners;
    private Drawable endDummyDrawable;
    private int endDummyDrawableWidth;
    /* access modifiers changed from: private */
    public final EndCompoundLayout endLayout;
    private boolean expandedHintEnabled;
    private StateListDrawable filledDropDownMenuBackground;
    private int focusedFilledBackgroundColor;
    private int focusedStrokeColor;
    private ColorStateList focusedTextColor;
    private CharSequence hint;
    private boolean hintAnimationEnabled;
    private boolean hintEnabled;
    private boolean hintExpanded;
    private int hoveredFilledBackgroundColor;
    private int hoveredStrokeColor;
    private boolean inDrawableStateChanged;
    /* access modifiers changed from: private */
    public final IndicatorViewController indicatorViewController;
    private final FrameLayout inputFrame;
    private boolean isProvidingHint;
    private LengthCounter lengthCounter;
    private int maxEms;
    private int maxWidth;
    private int minEms;
    private int minWidth;
    private Drawable originalEditTextEndDrawable;
    private CharSequence originalHint;
    private MaterialShapeDrawable outlinedDropDownMenuBackground;
    /* access modifiers changed from: private */
    public boolean placeholderEnabled;
    private Fade placeholderFadeIn;
    private Fade placeholderFadeOut;
    private CharSequence placeholderText;
    private int placeholderTextAppearance;
    private ColorStateList placeholderTextColor;
    private TextView placeholderTextView;
    /* access modifiers changed from: private */
    public boolean restoringSavedState;
    private ShapeAppearanceModel shapeAppearanceModel;
    private Drawable startDummyDrawable;
    private int startDummyDrawableWidth;
    /* access modifiers changed from: private */
    public final StartCompoundLayout startLayout;
    private ColorStateList strokeErrorColor;
    private final Rect tmpBoundsRect;
    private final Rect tmpRect;
    private final RectF tmpRectF;
    private Typeface typeface;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BoxBackgroundMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EndIconMode {
    }

    public interface LengthCounter {
        int countLength(Editable editable);
    }

    public interface OnEditTextAttachedListener {
        void onEditTextAttached(TextInputLayout textInputLayout);
    }

    public interface OnEndIconChangedListener {
        void onEndIconChanged(TextInputLayout textInputLayout, int i);
    }

    static /* synthetic */ int lambda$new$0(Editable text) {
        if (text != null) {
            return text.length();
        }
        return 0;
    }

    public TextInputLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public TextInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.textInputStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TextInputLayout(android.content.Context r27, android.util.AttributeSet r28, int r29) {
        /*
            r26 = this;
            r0 = r26
            r7 = r28
            r8 = r29
            int r9 = DEF_STYLE_RES
            r1 = r27
            android.content.Context r2 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r2, r7, r8)
            r10 = -1
            r0.minEms = r10
            r0.maxEms = r10
            r0.minWidth = r10
            r0.maxWidth = r10
            com.google.android.material.textfield.IndicatorViewController r2 = new com.google.android.material.textfield.IndicatorViewController
            r2.<init>(r0)
            r0.indicatorViewController = r2
            com.google.android.material.textfield.TextInputLayout$$ExternalSyntheticLambda0 r2 = new com.google.android.material.textfield.TextInputLayout$$ExternalSyntheticLambda0
            r2.<init>()
            r0.lengthCounter = r2
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            r0.tmpRect = r2
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            r0.tmpBoundsRect = r2
            android.graphics.RectF r2 = new android.graphics.RectF
            r2.<init>()
            r0.tmpRectF = r2
            java.util.LinkedHashSet r2 = new java.util.LinkedHashSet
            r2.<init>()
            r0.editTextAttachedListeners = r2
            com.google.android.material.internal.CollapsingTextHelper r2 = new com.google.android.material.internal.CollapsingTextHelper
            r2.<init>(r0)
            r0.collapsingTextHelper = r2
            android.content.Context r11 = r26.getContext()
            r12 = 1
            r0.setOrientation(r12)
            r13 = 0
            r0.setWillNotDraw(r13)
            r0.setAddStatesFromChildren(r12)
            android.widget.FrameLayout r14 = new android.widget.FrameLayout
            r14.<init>(r11)
            r0.inputFrame = r14
            r14.setAddStatesFromChildren(r12)
            android.animation.TimeInterpolator r1 = com.google.android.material.animation.AnimationUtils.LINEAR_INTERPOLATOR
            r2.setTextSizeInterpolator(r1)
            android.animation.TimeInterpolator r1 = com.google.android.material.animation.AnimationUtils.LINEAR_INTERPOLATOR
            r2.setPositionInterpolator(r1)
            r1 = 8388659(0x800033, float:1.1755015E-38)
            r2.setCollapsedTextGravity(r1)
            int[] r3 = com.google.android.material.C1087R.styleable.TextInputLayout
            r1 = 5
            int[] r6 = new int[r1]
            int r1 = com.google.android.material.C1087R.styleable.TextInputLayout_counterTextAppearance
            r6[r13] = r1
            int r1 = com.google.android.material.C1087R.styleable.TextInputLayout_counterOverflowTextAppearance
            r6[r12] = r1
            int r1 = com.google.android.material.C1087R.styleable.TextInputLayout_errorTextAppearance
            r15 = 2
            r6[r15] = r1
            r1 = 3
            int r2 = com.google.android.material.C1087R.styleable.TextInputLayout_helperTextTextAppearance
            r6[r1] = r2
            r1 = 4
            int r2 = com.google.android.material.C1087R.styleable.TextInputLayout_hintTextAppearance
            r6[r1] = r2
            r1 = r11
            r2 = r28
            r4 = r29
            r5 = r9
            androidx.appcompat.widget.TintTypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r1, r2, r3, r4, r5, r6)
            com.google.android.material.textfield.StartCompoundLayout r2 = new com.google.android.material.textfield.StartCompoundLayout
            r2.<init>(r0, r1)
            r0.startLayout = r2
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_hintEnabled
            boolean r3 = r1.getBoolean(r3, r12)
            r0.hintEnabled = r3
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_hint
            java.lang.CharSequence r3 = r1.getText(r3)
            r0.setHint((java.lang.CharSequence) r3)
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_hintAnimationEnabled
            boolean r3 = r1.getBoolean(r3, r12)
            r0.hintAnimationEnabled = r3
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_expandedHintEnabled
            boolean r3 = r1.getBoolean(r3, r12)
            r0.expandedHintEnabled = r3
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_minEms
            boolean r3 = r1.hasValue(r3)
            if (r3 == 0) goto L_0x00d4
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_minEms
            int r3 = r1.getInt(r3, r10)
            r0.setMinEms(r3)
            goto L_0x00e5
        L_0x00d4:
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_minWidth
            boolean r3 = r1.hasValue(r3)
            if (r3 == 0) goto L_0x00e5
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_minWidth
            int r3 = r1.getDimensionPixelSize(r3, r10)
            r0.setMinWidth(r3)
        L_0x00e5:
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_maxEms
            boolean r3 = r1.hasValue(r3)
            if (r3 == 0) goto L_0x00f7
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_maxEms
            int r3 = r1.getInt(r3, r10)
            r0.setMaxEms(r3)
            goto L_0x0108
        L_0x00f7:
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_maxWidth
            boolean r3 = r1.hasValue(r3)
            if (r3 == 0) goto L_0x0108
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_maxWidth
            int r3 = r1.getDimensionPixelSize(r3, r10)
            r0.setMaxWidth(r3)
        L_0x0108:
            com.google.android.material.shape.ShapeAppearanceModel$Builder r3 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r11, (android.util.AttributeSet) r7, (int) r8, (int) r9)
            com.google.android.material.shape.ShapeAppearanceModel r3 = r3.build()
            r0.shapeAppearanceModel = r3
            android.content.res.Resources r3 = r11.getResources()
            int r4 = com.google.android.material.C1087R.dimen.mtrl_textinput_box_label_cutout_padding
            int r3 = r3.getDimensionPixelOffset(r4)
            r0.boxLabelCutoutPaddingPx = r3
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_boxCollapsedPaddingTop
            int r3 = r1.getDimensionPixelOffset(r3, r13)
            r0.boxCollapsedPaddingTopPx = r3
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_boxStrokeWidth
            android.content.res.Resources r4 = r11.getResources()
            int r5 = com.google.android.material.C1087R.dimen.mtrl_textinput_box_stroke_width_default
            int r4 = r4.getDimensionPixelSize(r5)
            int r3 = r1.getDimensionPixelSize(r3, r4)
            r0.boxStrokeWidthDefaultPx = r3
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_boxStrokeWidthFocused
            android.content.res.Resources r4 = r11.getResources()
            int r5 = com.google.android.material.C1087R.dimen.mtrl_textinput_box_stroke_width_focused
            int r4 = r4.getDimensionPixelSize(r5)
            int r3 = r1.getDimensionPixelSize(r3, r4)
            r0.boxStrokeWidthFocusedPx = r3
            int r3 = r0.boxStrokeWidthDefaultPx
            r0.boxStrokeWidthPx = r3
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_boxCornerRadiusTopStart
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r3 = r1.getDimension(r3, r4)
            int r5 = com.google.android.material.C1087R.styleable.TextInputLayout_boxCornerRadiusTopEnd
            float r5 = r1.getDimension(r5, r4)
            int r6 = com.google.android.material.C1087R.styleable.TextInputLayout_boxCornerRadiusBottomEnd
            float r6 = r1.getDimension(r6, r4)
            int r9 = com.google.android.material.C1087R.styleable.TextInputLayout_boxCornerRadiusBottomStart
            float r4 = r1.getDimension(r9, r4)
            com.google.android.material.shape.ShapeAppearanceModel r9 = r0.shapeAppearanceModel
            com.google.android.material.shape.ShapeAppearanceModel$Builder r9 = r9.toBuilder()
            r16 = 0
            int r17 = (r3 > r16 ? 1 : (r3 == r16 ? 0 : -1))
            if (r17 < 0) goto L_0x0179
            r9.setTopLeftCornerSize((float) r3)
        L_0x0179:
            int r17 = (r5 > r16 ? 1 : (r5 == r16 ? 0 : -1))
            if (r17 < 0) goto L_0x0180
            r9.setTopRightCornerSize((float) r5)
        L_0x0180:
            int r17 = (r6 > r16 ? 1 : (r6 == r16 ? 0 : -1))
            if (r17 < 0) goto L_0x0187
            r9.setBottomRightCornerSize((float) r6)
        L_0x0187:
            int r16 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r16 < 0) goto L_0x018e
            r9.setBottomLeftCornerSize((float) r4)
        L_0x018e:
            com.google.android.material.shape.ShapeAppearanceModel r15 = r9.build()
            r0.shapeAppearanceModel = r15
            int r15 = com.google.android.material.C1087R.styleable.TextInputLayout_boxBackgroundColor
            android.content.res.ColorStateList r15 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (androidx.appcompat.widget.TintTypedArray) r1, (int) r15)
            if (r15 == 0) goto L_0x01fc
            int r10 = r15.getDefaultColor()
            r0.defaultFilledBackgroundColor = r10
            r0.boxBackgroundColor = r10
            boolean r10 = r15.isStateful()
            r17 = -16842910(0xfffffffffefeff62, float:-1.6947497E38)
            if (r10 == 0) goto L_0x01d2
            int[] r10 = new int[r12]
            r10[r13] = r17
            r13 = -1
            int r10 = r15.getColorForState(r10, r13)
            r0.disabledFilledBackgroundColor = r10
            r10 = 2
            int[] r12 = new int[r10]
            r12 = {16842908, 16842910} // fill-array
            int r12 = r15.getColorForState(r12, r13)
            r0.focusedFilledBackgroundColor = r12
            int[] r12 = new int[r10]
            r12 = {16843623, 16842910} // fill-array
            int r10 = r15.getColorForState(r12, r13)
            r0.hoveredFilledBackgroundColor = r10
            r17 = r3
            goto L_0x0209
        L_0x01d2:
            int r10 = r0.defaultFilledBackgroundColor
            r0.focusedFilledBackgroundColor = r10
            int r10 = com.google.android.material.C1087R.C1088color.mtrl_filled_background_color
            android.content.res.ColorStateList r10 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r11, r10)
            r12 = 1
            int[] r13 = new int[r12]
            r12 = 0
            r13[r12] = r17
            r12 = -1
            int r13 = r10.getColorForState(r13, r12)
            r0.disabledFilledBackgroundColor = r13
            r13 = 1
            int[] r12 = new int[r13]
            r13 = 16843623(0x1010367, float:2.3696E-38)
            r17 = r3
            r3 = 0
            r12[r3] = r13
            r13 = -1
            int r12 = r10.getColorForState(r12, r13)
            r0.hoveredFilledBackgroundColor = r12
            goto L_0x0209
        L_0x01fc:
            r17 = r3
            r3 = 0
            r0.boxBackgroundColor = r3
            r0.defaultFilledBackgroundColor = r3
            r0.disabledFilledBackgroundColor = r3
            r0.focusedFilledBackgroundColor = r3
            r0.hoveredFilledBackgroundColor = r3
        L_0x0209:
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_textColorHint
            boolean r3 = r1.hasValue(r3)
            if (r3 == 0) goto L_0x021b
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_android_textColorHint
            android.content.res.ColorStateList r3 = r1.getColorStateList(r3)
            r0.focusedTextColor = r3
            r0.defaultHintTextColor = r3
        L_0x021b:
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_boxStrokeColor
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (androidx.appcompat.widget.TintTypedArray) r1, (int) r3)
            int r10 = com.google.android.material.C1087R.styleable.TextInputLayout_boxStrokeColor
            r12 = 0
            int r10 = r1.getColor(r10, r12)
            r0.focusedStrokeColor = r10
            int r10 = com.google.android.material.C1087R.C1088color.mtrl_textinput_default_box_stroke_color
            int r10 = androidx.core.content.ContextCompat.getColor(r11, r10)
            r0.defaultStrokeColor = r10
            int r10 = com.google.android.material.C1087R.C1088color.mtrl_textinput_disabled_color
            int r10 = androidx.core.content.ContextCompat.getColor(r11, r10)
            r0.disabledColor = r10
            int r10 = com.google.android.material.C1087R.C1088color.mtrl_textinput_hovered_box_stroke_color
            int r10 = androidx.core.content.ContextCompat.getColor(r11, r10)
            r0.hoveredStrokeColor = r10
            if (r3 == 0) goto L_0x0247
            r0.setBoxStrokeColorStateList(r3)
        L_0x0247:
            int r10 = com.google.android.material.C1087R.styleable.TextInputLayout_boxStrokeErrorColor
            boolean r10 = r1.hasValue(r10)
            if (r10 == 0) goto L_0x0258
            int r10 = com.google.android.material.C1087R.styleable.TextInputLayout_boxStrokeErrorColor
            android.content.res.ColorStateList r10 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (androidx.appcompat.widget.TintTypedArray) r1, (int) r10)
            r0.setBoxStrokeErrorColor(r10)
        L_0x0258:
            int r10 = com.google.android.material.C1087R.styleable.TextInputLayout_hintTextAppearance
            r12 = -1
            int r10 = r1.getResourceId(r10, r12)
            if (r10 == r12) goto L_0x026c
            int r12 = com.google.android.material.C1087R.styleable.TextInputLayout_hintTextAppearance
            r13 = 0
            int r12 = r1.getResourceId(r12, r13)
            r0.setHintTextAppearance(r12)
            goto L_0x026d
        L_0x026c:
            r13 = 0
        L_0x026d:
            int r12 = com.google.android.material.C1087R.styleable.TextInputLayout_errorTextAppearance
            int r12 = r1.getResourceId(r12, r13)
            int r13 = com.google.android.material.C1087R.styleable.TextInputLayout_errorContentDescription
            java.lang.CharSequence r13 = r1.getText(r13)
            r19 = r3
            int r3 = com.google.android.material.C1087R.styleable.TextInputLayout_errorAccessibilityLiveRegion
            r20 = r4
            r4 = 1
            int r3 = r1.getInt(r3, r4)
            int r4 = com.google.android.material.C1087R.styleable.TextInputLayout_errorEnabled
            r21 = r5
            r5 = 0
            boolean r4 = r1.getBoolean(r4, r5)
            r18 = r6
            int r6 = com.google.android.material.C1087R.styleable.TextInputLayout_helperTextTextAppearance
            int r6 = r1.getResourceId(r6, r5)
            int r7 = com.google.android.material.C1087R.styleable.TextInputLayout_helperTextEnabled
            boolean r7 = r1.getBoolean(r7, r5)
            int r5 = com.google.android.material.C1087R.styleable.TextInputLayout_helperText
            java.lang.CharSequence r5 = r1.getText(r5)
            int r8 = com.google.android.material.C1087R.styleable.TextInputLayout_placeholderTextAppearance
            r22 = r9
            r9 = 0
            int r8 = r1.getResourceId(r8, r9)
            int r9 = com.google.android.material.C1087R.styleable.TextInputLayout_placeholderText
            java.lang.CharSequence r9 = r1.getText(r9)
            r23 = r10
            int r10 = com.google.android.material.C1087R.styleable.TextInputLayout_counterEnabled
            r24 = r11
            r11 = 0
            boolean r10 = r1.getBoolean(r10, r11)
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_counterMaxLength
            r25 = r15
            r15 = -1
            int r11 = r1.getInt(r11, r15)
            r0.setCounterMaxLength(r11)
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_counterTextAppearance
            r15 = 0
            int r11 = r1.getResourceId(r11, r15)
            r0.counterTextAppearance = r11
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_counterOverflowTextAppearance
            int r11 = r1.getResourceId(r11, r15)
            r0.counterOverflowTextAppearance = r11
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_boxBackgroundMode
            int r11 = r1.getInt(r11, r15)
            r0.setBoxBackgroundMode(r11)
            r0.setErrorContentDescription(r13)
            r0.setErrorAccessibilityLiveRegion(r3)
            int r11 = r0.counterOverflowTextAppearance
            r0.setCounterOverflowTextAppearance(r11)
            r0.setHelperTextTextAppearance(r6)
            r0.setErrorTextAppearance(r12)
            int r11 = r0.counterTextAppearance
            r0.setCounterTextAppearance(r11)
            r0.setPlaceholderText(r9)
            r0.setPlaceholderTextAppearance(r8)
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_errorTextColor
            boolean r11 = r1.hasValue(r11)
            if (r11 == 0) goto L_0x030e
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_errorTextColor
            android.content.res.ColorStateList r11 = r1.getColorStateList(r11)
            r0.setErrorTextColor(r11)
        L_0x030e:
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_helperTextTextColor
            boolean r11 = r1.hasValue(r11)
            if (r11 == 0) goto L_0x031f
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_helperTextTextColor
            android.content.res.ColorStateList r11 = r1.getColorStateList(r11)
            r0.setHelperTextColor(r11)
        L_0x031f:
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_hintTextColor
            boolean r11 = r1.hasValue(r11)
            if (r11 == 0) goto L_0x0330
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_hintTextColor
            android.content.res.ColorStateList r11 = r1.getColorStateList(r11)
            r0.setHintTextColor(r11)
        L_0x0330:
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_counterTextColor
            boolean r11 = r1.hasValue(r11)
            if (r11 == 0) goto L_0x0341
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_counterTextColor
            android.content.res.ColorStateList r11 = r1.getColorStateList(r11)
            r0.setCounterTextColor(r11)
        L_0x0341:
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_counterOverflowTextColor
            boolean r11 = r1.hasValue(r11)
            if (r11 == 0) goto L_0x0352
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_counterOverflowTextColor
            android.content.res.ColorStateList r11 = r1.getColorStateList(r11)
            r0.setCounterOverflowTextColor(r11)
        L_0x0352:
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_placeholderTextColor
            boolean r11 = r1.hasValue(r11)
            if (r11 == 0) goto L_0x0363
            int r11 = com.google.android.material.C1087R.styleable.TextInputLayout_placeholderTextColor
            android.content.res.ColorStateList r11 = r1.getColorStateList(r11)
            r0.setPlaceholderTextColor(r11)
        L_0x0363:
            com.google.android.material.textfield.EndCompoundLayout r11 = new com.google.android.material.textfield.EndCompoundLayout
            r11.<init>(r0, r1)
            r0.endLayout = r11
            int r15 = com.google.android.material.C1087R.styleable.TextInputLayout_android_enabled
            r16 = r3
            r3 = 1
            boolean r15 = r1.getBoolean(r15, r3)
            r1.recycle()
            r3 = 2
            androidx.core.view.ViewCompat.setImportantForAccessibility(r0, r3)
            int r3 = android.os.Build.VERSION.SDK_INT
            r27 = r1
            r1 = 26
            if (r3 < r1) goto L_0x0386
            r1 = 1
            androidx.core.view.ViewCompat.setImportantForAutofill(r0, r1)
        L_0x0386:
            r14.addView(r2)
            r14.addView(r11)
            r0.addView(r14)
            r0.setEnabled(r15)
            r0.setHelperTextEnabled(r7)
            r0.setErrorEnabled(r4)
            r0.setCounterEnabled(r10)
            r0.setHelperText(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(params);
            flp.gravity = (flp.gravity & -113) | 16;
            this.inputFrame.addView(child, flp);
            this.inputFrame.setLayoutParams(params);
            updateInputLayoutMargins();
            setEditText((EditText) child);
            return;
        }
        super.addView(child, index, params);
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable getBoxBackground() {
        int i = this.boxBackgroundMode;
        if (i == 1 || i == 2) {
            return this.boxBackground;
        }
        throw new IllegalStateException();
    }

    public void setBoxBackgroundMode(int boxBackgroundMode2) {
        if (boxBackgroundMode2 != this.boxBackgroundMode) {
            this.boxBackgroundMode = boxBackgroundMode2;
            if (this.editText != null) {
                onApplyBoxBackgroundMode();
            }
        }
    }

    public int getBoxBackgroundMode() {
        return this.boxBackgroundMode;
    }

    private void onApplyBoxBackgroundMode() {
        assignBoxBackgroundByMode();
        updateEditTextBoxBackgroundIfNeeded();
        updateTextInputBoxState();
        updateBoxCollapsedPaddingTop();
        adjustFilledEditTextPaddingForLargeFont();
        if (this.boxBackgroundMode != 0) {
            updateInputLayoutMargins();
        }
        setDropDownMenuBackgroundIfNeeded();
    }

    private void assignBoxBackgroundByMode() {
        switch (this.boxBackgroundMode) {
            case 0:
                this.boxBackground = null;
                this.boxUnderlineDefault = null;
                this.boxUnderlineFocused = null;
                return;
            case 1:
                this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
                this.boxUnderlineDefault = new MaterialShapeDrawable();
                this.boxUnderlineFocused = new MaterialShapeDrawable();
                return;
            case 2:
                if (!this.hintEnabled || (this.boxBackground instanceof CutoutDrawable)) {
                    this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
                } else {
                    this.boxBackground = CutoutDrawable.create(this.shapeAppearanceModel);
                }
                this.boxUnderlineDefault = null;
                this.boxUnderlineFocused = null;
                return;
            default:
                throw new IllegalArgumentException(this.boxBackgroundMode + " is illegal; only @BoxBackgroundMode constants are supported.");
        }
    }

    /* access modifiers changed from: package-private */
    public void updateEditTextBoxBackgroundIfNeeded() {
        EditText editText2 = this.editText;
        if (editText2 != null && this.boxBackground != null) {
            if ((this.boxBackgroundApplied || editText2.getBackground() == null) && this.boxBackgroundMode != 0) {
                ViewCompat.setBackground(this.editText, getEditTextBoxBackground());
                this.boxBackgroundApplied = true;
            }
        }
    }

    private Drawable getEditTextBoxBackground() {
        EditText editText2 = this.editText;
        if (!(editText2 instanceof AutoCompleteTextView) || EditTextUtils.isEditable(editText2)) {
            return this.boxBackground;
        }
        int rippleColor = MaterialColors.getColor(this.editText, C1087R.attr.colorControlHighlight);
        int i = this.boxBackgroundMode;
        if (i == 2) {
            return getOutlinedBoxBackgroundWithRipple(getContext(), this.boxBackground, rippleColor, EDIT_TEXT_BACKGROUND_RIPPLE_STATE);
        }
        if (i == 1) {
            return getFilledBoxBackgroundWithRipple(this.boxBackground, this.boxBackgroundColor, rippleColor, EDIT_TEXT_BACKGROUND_RIPPLE_STATE);
        }
        return null;
    }

    private static Drawable getOutlinedBoxBackgroundWithRipple(Context context, MaterialShapeDrawable boxBackground2, int rippleColor, int[][] states) {
        int surfaceColor = MaterialColors.getColor(context, C1087R.attr.colorSurface, LOG_TAG);
        MaterialShapeDrawable rippleBackground = new MaterialShapeDrawable(boxBackground2.getShapeAppearanceModel());
        int pressedBackgroundColor = MaterialColors.layer(rippleColor, surfaceColor, 0.1f);
        rippleBackground.setFillColor(new ColorStateList(states, new int[]{pressedBackgroundColor, 0}));
        if (Build.VERSION.SDK_INT >= 21) {
            rippleBackground.setTint(surfaceColor);
            ColorStateList rippleColorStateList = new ColorStateList(states, new int[]{pressedBackgroundColor, surfaceColor});
            MaterialShapeDrawable mask = new MaterialShapeDrawable(boxBackground2.getShapeAppearanceModel());
            mask.setTint(-1);
            return new LayerDrawable(new Drawable[]{new RippleDrawable(rippleColorStateList, rippleBackground, mask), boxBackground2});
        }
        return new LayerDrawable(new Drawable[]{rippleBackground, boxBackground2});
    }

    private static Drawable getFilledBoxBackgroundWithRipple(MaterialShapeDrawable boxBackground2, int boxBackgroundColor2, int rippleColor, int[][] states) {
        int[] colors = {MaterialColors.layer(rippleColor, boxBackgroundColor2, 0.1f), boxBackgroundColor2};
        if (Build.VERSION.SDK_INT >= 21) {
            return new RippleDrawable(new ColorStateList(states, colors), boxBackground2, boxBackground2);
        }
        MaterialShapeDrawable rippleBackground = new MaterialShapeDrawable(boxBackground2.getShapeAppearanceModel());
        rippleBackground.setFillColor(new ColorStateList(states, colors));
        return new LayerDrawable(new Drawable[]{boxBackground2, rippleBackground});
    }

    private void setDropDownMenuBackgroundIfNeeded() {
        EditText editText2 = this.editText;
        if (editText2 instanceof AutoCompleteTextView) {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText2;
            if (Build.VERSION.SDK_INT >= 21 && autoCompleteTextView.getDropDownBackground() == null) {
                int i = this.boxBackgroundMode;
                if (i == 2) {
                    autoCompleteTextView.setDropDownBackgroundDrawable(getOrCreateOutlinedDropDownMenuBackground());
                } else if (i == 1) {
                    autoCompleteTextView.setDropDownBackgroundDrawable(getOrCreateFilledDropDownMenuBackground());
                }
            }
        }
    }

    private Drawable getOrCreateOutlinedDropDownMenuBackground() {
        if (this.outlinedDropDownMenuBackground == null) {
            this.outlinedDropDownMenuBackground = getDropDownMaterialShapeDrawable(true);
        }
        return this.outlinedDropDownMenuBackground;
    }

    private Drawable getOrCreateFilledDropDownMenuBackground() {
        if (this.filledDropDownMenuBackground == null) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            this.filledDropDownMenuBackground = stateListDrawable;
            stateListDrawable.addState(new int[]{16842922}, getOrCreateOutlinedDropDownMenuBackground());
            this.filledDropDownMenuBackground.addState(new int[0], getDropDownMaterialShapeDrawable(false));
        }
        return this.filledDropDownMenuBackground;
    }

    private MaterialShapeDrawable getDropDownMaterialShapeDrawable(boolean roundedTopCorners) {
        float elevation;
        float cornerRadius = (float) getResources().getDimensionPixelOffset(C1087R.dimen.mtrl_shape_corner_size_small_component);
        float topCornerRadius = roundedTopCorners ? cornerRadius : 0.0f;
        EditText editText2 = this.editText;
        if (editText2 instanceof MaterialAutoCompleteTextView) {
            elevation = ((MaterialAutoCompleteTextView) editText2).getPopupElevation();
        } else {
            elevation = (float) getResources().getDimensionPixelOffset(C1087R.dimen.m3_comp_outlined_autocomplete_menu_container_elevation);
        }
        int verticalPadding = getResources().getDimensionPixelOffset(C1087R.dimen.mtrl_exposed_dropdown_menu_popup_vertical_padding);
        ShapeAppearanceModel shapeAppearanceModel2 = ShapeAppearanceModel.builder().setTopLeftCornerSize(topCornerRadius).setTopRightCornerSize(topCornerRadius).setBottomLeftCornerSize(cornerRadius).setBottomRightCornerSize(cornerRadius).build();
        MaterialShapeDrawable popupDrawable = MaterialShapeDrawable.createWithElevationOverlay(getContext(), elevation);
        popupDrawable.setShapeAppearanceModel(shapeAppearanceModel2);
        popupDrawable.setPadding(0, verticalPadding, 0, verticalPadding);
        return popupDrawable;
    }

    private void updateBoxCollapsedPaddingTop() {
        if (this.boxBackgroundMode != 1) {
            return;
        }
        if (MaterialResources.isFontScaleAtLeast2_0(getContext())) {
            this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(C1087R.dimen.material_font_2_0_box_collapsed_padding_top);
        } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(C1087R.dimen.material_font_1_3_box_collapsed_padding_top);
        }
    }

    private void adjustFilledEditTextPaddingForLargeFont() {
        if (this.editText != null && this.boxBackgroundMode == 1) {
            if (MaterialResources.isFontScaleAtLeast2_0(getContext())) {
                EditText editText2 = this.editText;
                ViewCompat.setPaddingRelative(editText2, ViewCompat.getPaddingStart(editText2), getResources().getDimensionPixelSize(C1087R.dimen.material_filled_edittext_font_2_0_padding_top), ViewCompat.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(C1087R.dimen.material_filled_edittext_font_2_0_padding_bottom));
            } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
                EditText editText3 = this.editText;
                ViewCompat.setPaddingRelative(editText3, ViewCompat.getPaddingStart(editText3), getResources().getDimensionPixelSize(C1087R.dimen.material_filled_edittext_font_1_3_padding_top), ViewCompat.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(C1087R.dimen.material_filled_edittext_font_1_3_padding_bottom));
            }
        }
    }

    public void setBoxCollapsedPaddingTop(int boxCollapsedPaddingTop) {
        this.boxCollapsedPaddingTopPx = boxCollapsedPaddingTop;
    }

    public int getBoxCollapsedPaddingTop() {
        return this.boxCollapsedPaddingTopPx;
    }

    public void setBoxStrokeWidthResource(int boxStrokeWidthResId) {
        setBoxStrokeWidth(getResources().getDimensionPixelSize(boxStrokeWidthResId));
    }

    public void setBoxStrokeWidth(int boxStrokeWidth) {
        this.boxStrokeWidthDefaultPx = boxStrokeWidth;
        updateTextInputBoxState();
    }

    public int getBoxStrokeWidth() {
        return this.boxStrokeWidthDefaultPx;
    }

    public void setBoxStrokeWidthFocusedResource(int boxStrokeWidthFocusedResId) {
        setBoxStrokeWidthFocused(getResources().getDimensionPixelSize(boxStrokeWidthFocusedResId));
    }

    public void setBoxStrokeWidthFocused(int boxStrokeWidthFocused) {
        this.boxStrokeWidthFocusedPx = boxStrokeWidthFocused;
        updateTextInputBoxState();
    }

    public int getBoxStrokeWidthFocused() {
        return this.boxStrokeWidthFocusedPx;
    }

    public void setBoxStrokeColor(int boxStrokeColor2) {
        if (this.focusedStrokeColor != boxStrokeColor2) {
            this.focusedStrokeColor = boxStrokeColor2;
            updateTextInputBoxState();
        }
    }

    public int getBoxStrokeColor() {
        return this.focusedStrokeColor;
    }

    public void setBoxStrokeColorStateList(ColorStateList boxStrokeColorStateList) {
        if (boxStrokeColorStateList.isStateful()) {
            this.defaultStrokeColor = boxStrokeColorStateList.getDefaultColor();
            this.disabledColor = boxStrokeColorStateList.getColorForState(new int[]{-16842910}, -1);
            this.hoveredStrokeColor = boxStrokeColorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
            this.focusedStrokeColor = boxStrokeColorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        } else if (this.focusedStrokeColor != boxStrokeColorStateList.getDefaultColor()) {
            this.focusedStrokeColor = boxStrokeColorStateList.getDefaultColor();
        }
        updateTextInputBoxState();
    }

    public void setBoxStrokeErrorColor(ColorStateList strokeErrorColor2) {
        if (this.strokeErrorColor != strokeErrorColor2) {
            this.strokeErrorColor = strokeErrorColor2;
            updateTextInputBoxState();
        }
    }

    public ColorStateList getBoxStrokeErrorColor() {
        return this.strokeErrorColor;
    }

    public void setBoxBackgroundColorResource(int boxBackgroundColorId) {
        setBoxBackgroundColor(ContextCompat.getColor(getContext(), boxBackgroundColorId));
    }

    public void setBoxBackgroundColor(int boxBackgroundColor2) {
        if (this.boxBackgroundColor != boxBackgroundColor2) {
            this.boxBackgroundColor = boxBackgroundColor2;
            this.defaultFilledBackgroundColor = boxBackgroundColor2;
            this.focusedFilledBackgroundColor = boxBackgroundColor2;
            this.hoveredFilledBackgroundColor = boxBackgroundColor2;
            applyBoxAttributes();
        }
    }

    public void setBoxBackgroundColorStateList(ColorStateList boxBackgroundColorStateList) {
        int defaultColor = boxBackgroundColorStateList.getDefaultColor();
        this.defaultFilledBackgroundColor = defaultColor;
        this.boxBackgroundColor = defaultColor;
        this.disabledFilledBackgroundColor = boxBackgroundColorStateList.getColorForState(new int[]{-16842910}, -1);
        this.focusedFilledBackgroundColor = boxBackgroundColorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        this.hoveredFilledBackgroundColor = boxBackgroundColorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
        applyBoxAttributes();
    }

    public int getBoxBackgroundColor() {
        return this.boxBackgroundColor;
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel2) {
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable != null && materialShapeDrawable.getShapeAppearanceModel() != shapeAppearanceModel2) {
            this.shapeAppearanceModel = shapeAppearanceModel2;
            applyBoxAttributes();
        }
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    public void setBoxCornerFamily(int cornerFamily) {
        this.shapeAppearanceModel = this.shapeAppearanceModel.toBuilder().setTopLeftCorner(cornerFamily, this.shapeAppearanceModel.getTopLeftCornerSize()).setTopRightCorner(cornerFamily, this.shapeAppearanceModel.getTopRightCornerSize()).setBottomLeftCorner(cornerFamily, this.shapeAppearanceModel.getBottomLeftCornerSize()).setBottomRightCorner(cornerFamily, this.shapeAppearanceModel.getBottomRightCornerSize()).build();
        applyBoxAttributes();
    }

    public void setBoxCornerRadiiResources(int boxCornerRadiusTopStartId, int boxCornerRadiusTopEndId, int boxCornerRadiusBottomEndId, int boxCornerRadiusBottomStartId) {
        setBoxCornerRadii(getContext().getResources().getDimension(boxCornerRadiusTopStartId), getContext().getResources().getDimension(boxCornerRadiusTopEndId), getContext().getResources().getDimension(boxCornerRadiusBottomStartId), getContext().getResources().getDimension(boxCornerRadiusBottomEndId));
    }

    public void setBoxCornerRadii(float boxCornerRadiusTopStart, float boxCornerRadiusTopEnd, float boxCornerRadiusBottomStart, float boxCornerRadiusBottomEnd) {
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        this.areCornerRadiiRtl = isLayoutRtl;
        float boxCornerRadiusTopLeft = isLayoutRtl ? boxCornerRadiusTopEnd : boxCornerRadiusTopStart;
        float boxCornerRadiusTopRight = isLayoutRtl ? boxCornerRadiusTopStart : boxCornerRadiusTopEnd;
        float boxCornerRadiusBottomLeft = isLayoutRtl ? boxCornerRadiusBottomEnd : boxCornerRadiusBottomStart;
        float boxCornerRadiusBottomRight = isLayoutRtl ? boxCornerRadiusBottomStart : boxCornerRadiusBottomEnd;
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable == null || materialShapeDrawable.getTopLeftCornerResolvedSize() != boxCornerRadiusTopLeft || this.boxBackground.getTopRightCornerResolvedSize() != boxCornerRadiusTopRight || this.boxBackground.getBottomLeftCornerResolvedSize() != boxCornerRadiusBottomLeft || this.boxBackground.getBottomRightCornerResolvedSize() != boxCornerRadiusBottomRight) {
            this.shapeAppearanceModel = this.shapeAppearanceModel.toBuilder().setTopLeftCornerSize(boxCornerRadiusTopLeft).setTopRightCornerSize(boxCornerRadiusTopRight).setBottomLeftCornerSize(boxCornerRadiusBottomLeft).setBottomRightCornerSize(boxCornerRadiusBottomRight).build();
            applyBoxAttributes();
        }
    }

    public float getBoxCornerRadiusTopStart() {
        if (ViewUtils.isLayoutRtl(this)) {
            return this.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(this.tmpRectF);
        }
        return this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.tmpRectF);
    }

    public float getBoxCornerRadiusTopEnd() {
        if (ViewUtils.isLayoutRtl(this)) {
            return this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.tmpRectF);
        }
        return this.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(this.tmpRectF);
    }

    public float getBoxCornerRadiusBottomEnd() {
        if (ViewUtils.isLayoutRtl(this)) {
            return this.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(this.tmpRectF);
        }
        return this.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(this.tmpRectF);
    }

    public float getBoxCornerRadiusBottomStart() {
        if (ViewUtils.isLayoutRtl(this)) {
            return this.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(this.tmpRectF);
        }
        return this.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(this.tmpRectF);
    }

    public void setTypeface(Typeface typeface2) {
        if (typeface2 != this.typeface) {
            this.typeface = typeface2;
            this.collapsingTextHelper.setTypefaces(typeface2);
            this.indicatorViewController.setTypefaces(typeface2);
            TextView textView = this.counterView;
            if (textView != null) {
                textView.setTypeface(typeface2);
            }
        }
    }

    public Typeface getTypeface() {
        return this.typeface;
    }

    public void setLengthCounter(LengthCounter lengthCounter2) {
        this.lengthCounter = lengthCounter2;
    }

    public LengthCounter getLengthCounter() {
        return this.lengthCounter;
    }

    public void dispatchProvideAutofillStructure(ViewStructure structure, int flags) {
        EditText editText2 = this.editText;
        if (editText2 == null) {
            super.dispatchProvideAutofillStructure(structure, flags);
        } else if (this.originalHint != null) {
            boolean wasProvidingHint = this.isProvidingHint;
            this.isProvidingHint = false;
            CharSequence hint2 = editText2.getHint();
            this.editText.setHint(this.originalHint);
            try {
                super.dispatchProvideAutofillStructure(structure, flags);
            } finally {
                this.editText.setHint(hint2);
                this.isProvidingHint = wasProvidingHint;
            }
        } else {
            structure.setAutofillId(getAutofillId());
            onProvideAutofillStructure(structure, flags);
            onProvideAutofillVirtualStructure(structure, flags);
            structure.setChildCount(this.inputFrame.getChildCount());
            for (int i = 0; i < this.inputFrame.getChildCount(); i++) {
                View child = this.inputFrame.getChildAt(i);
                ViewStructure childStructure = structure.newChild(i);
                child.dispatchProvideAutofillStructure(childStructure, flags);
                if (child == this.editText) {
                    childStructure.setHint(getHint());
                }
            }
        }
    }

    private void setEditText(EditText editText2) {
        if (this.editText == null) {
            if (getEndIconMode() != 3 && !(editText2 instanceof TextInputEditText)) {
                Log.i(LOG_TAG, "EditText added is not a TextInputEditText. Please switch to using that class instead.");
            }
            this.editText = editText2;
            int i = this.minEms;
            if (i != -1) {
                setMinEms(i);
            } else {
                setMinWidth(this.minWidth);
            }
            int i2 = this.maxEms;
            if (i2 != -1) {
                setMaxEms(i2);
            } else {
                setMaxWidth(this.maxWidth);
            }
            this.boxBackgroundApplied = false;
            onApplyBoxBackgroundMode();
            setTextInputAccessibilityDelegate(new AccessibilityDelegate(this));
            this.collapsingTextHelper.setTypefaces(this.editText.getTypeface());
            this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
            if (Build.VERSION.SDK_INT >= 21) {
                this.collapsingTextHelper.setExpandedLetterSpacing(this.editText.getLetterSpacing());
            }
            int editTextGravity = this.editText.getGravity();
            this.collapsingTextHelper.setCollapsedTextGravity((editTextGravity & -113) | 48);
            this.collapsingTextHelper.setExpandedTextGravity(editTextGravity);
            this.editText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    TextInputLayout textInputLayout = TextInputLayout.this;
                    textInputLayout.updateLabelState(!textInputLayout.restoringSavedState);
                    if (TextInputLayout.this.counterEnabled) {
                        TextInputLayout.this.updateCounter(s);
                    }
                    if (TextInputLayout.this.placeholderEnabled) {
                        TextInputLayout.this.updatePlaceholderText(s);
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
            if (this.defaultHintTextColor == null) {
                this.defaultHintTextColor = this.editText.getHintTextColors();
            }
            if (this.hintEnabled) {
                if (TextUtils.isEmpty(this.hint)) {
                    CharSequence hint2 = this.editText.getHint();
                    this.originalHint = hint2;
                    setHint(hint2);
                    this.editText.setHint((CharSequence) null);
                }
                this.isProvidingHint = true;
            }
            if (this.counterView != null) {
                updateCounter(this.editText.getText());
            }
            updateEditTextBackground();
            this.indicatorViewController.adjustIndicatorPadding();
            this.startLayout.bringToFront();
            this.endLayout.bringToFront();
            dispatchOnEditTextAttached();
            this.endLayout.updateSuffixTextViewPadding();
            if (!isEnabled()) {
                editText2.setEnabled(false);
            }
            updateLabelState(false, true);
            return;
        }
        throw new IllegalArgumentException("We already have an EditText, can only have one");
    }

    private void updateInputLayoutMargins() {
        if (this.boxBackgroundMode != 1) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.inputFrame.getLayoutParams();
            int newTopMargin = calculateLabelMarginTop();
            if (newTopMargin != lp.topMargin) {
                lp.topMargin = newTopMargin;
                this.inputFrame.requestLayout();
            }
        }
    }

    public int getBaseline() {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            return editText2.getBaseline() + getPaddingTop() + calculateLabelMarginTop();
        }
        return super.getBaseline();
    }

    /* access modifiers changed from: package-private */
    public void updateLabelState(boolean animate) {
        updateLabelState(animate, false);
    }

    private void updateLabelState(boolean animate, boolean force) {
        ColorStateList colorStateList;
        TextView textView;
        int disabledHintColor;
        boolean isEnabled = isEnabled();
        EditText editText2 = this.editText;
        boolean hasText = editText2 != null && !TextUtils.isEmpty(editText2.getText());
        EditText editText3 = this.editText;
        boolean hasFocus = editText3 != null && editText3.hasFocus();
        ColorStateList colorStateList2 = this.defaultHintTextColor;
        if (colorStateList2 != null) {
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(colorStateList2);
        }
        if (!isEnabled) {
            ColorStateList colorStateList3 = this.defaultHintTextColor;
            if (colorStateList3 != null) {
                disabledHintColor = colorStateList3.getColorForState(new int[]{-16842910}, this.disabledColor);
            } else {
                disabledHintColor = this.disabledColor;
            }
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(ColorStateList.valueOf(disabledHintColor));
        } else if (shouldShowError()) {
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(this.indicatorViewController.getErrorViewTextColors());
        } else if (this.counterOverflowed && (textView = this.counterView) != null) {
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(textView.getTextColors());
        } else if (hasFocus && (colorStateList = this.focusedTextColor) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
        }
        if (hasText || !this.expandedHintEnabled || (isEnabled() && hasFocus)) {
            if (force || this.hintExpanded) {
                collapseHint(animate);
            }
        } else if (force || !this.hintExpanded) {
            expandHint(animate);
        }
    }

    public EditText getEditText() {
        return this.editText;
    }

    public void setMinEms(int minEms2) {
        this.minEms = minEms2;
        EditText editText2 = this.editText;
        if (editText2 != null && minEms2 != -1) {
            editText2.setMinEms(minEms2);
        }
    }

    public int getMinEms() {
        return this.minEms;
    }

    public void setMaxEms(int maxEms2) {
        this.maxEms = maxEms2;
        EditText editText2 = this.editText;
        if (editText2 != null && maxEms2 != -1) {
            editText2.setMaxEms(maxEms2);
        }
    }

    public int getMaxEms() {
        return this.maxEms;
    }

    public void setMinWidth(int minWidth2) {
        this.minWidth = minWidth2;
        EditText editText2 = this.editText;
        if (editText2 != null && minWidth2 != -1) {
            editText2.setMinWidth(minWidth2);
        }
    }

    public void setMinWidthResource(int minWidthId) {
        setMinWidth(getContext().getResources().getDimensionPixelSize(minWidthId));
    }

    public int getMinWidth() {
        return this.minWidth;
    }

    public void setMaxWidth(int maxWidth2) {
        this.maxWidth = maxWidth2;
        EditText editText2 = this.editText;
        if (editText2 != null && maxWidth2 != -1) {
            editText2.setMaxWidth(maxWidth2);
        }
    }

    public void setMaxWidthResource(int maxWidthId) {
        setMaxWidth(getContext().getResources().getDimensionPixelSize(maxWidthId));
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setHint(CharSequence hint2) {
        if (this.hintEnabled) {
            setHintInternal(hint2);
            sendAccessibilityEvent(2048);
        }
    }

    public void setHint(int textHintId) {
        setHint(textHintId != 0 ? getResources().getText(textHintId) : null);
    }

    private void setHintInternal(CharSequence hint2) {
        if (!TextUtils.equals(hint2, this.hint)) {
            this.hint = hint2;
            this.collapsingTextHelper.setText(hint2);
            if (!this.hintExpanded) {
                openCutout();
            }
        }
    }

    public CharSequence getHint() {
        if (this.hintEnabled) {
            return this.hint;
        }
        return null;
    }

    public void setHintEnabled(boolean enabled) {
        if (enabled != this.hintEnabled) {
            this.hintEnabled = enabled;
            if (!enabled) {
                this.isProvidingHint = false;
                if (!TextUtils.isEmpty(this.hint) && TextUtils.isEmpty(this.editText.getHint())) {
                    this.editText.setHint(this.hint);
                }
                setHintInternal((CharSequence) null);
            } else {
                CharSequence editTextHint = this.editText.getHint();
                if (!TextUtils.isEmpty(editTextHint)) {
                    if (TextUtils.isEmpty(this.hint)) {
                        setHint(editTextHint);
                    }
                    this.editText.setHint((CharSequence) null);
                }
                this.isProvidingHint = true;
            }
            if (this.editText != null) {
                updateInputLayoutMargins();
            }
        }
    }

    public boolean isHintEnabled() {
        return this.hintEnabled;
    }

    public boolean isProvidingHint() {
        return this.isProvidingHint;
    }

    public void setHintTextAppearance(int resId) {
        this.collapsingTextHelper.setCollapsedTextAppearance(resId);
        this.focusedTextColor = this.collapsingTextHelper.getCollapsedTextColor();
        if (this.editText != null) {
            updateLabelState(false);
            updateInputLayoutMargins();
        }
    }

    public void setHintTextColor(ColorStateList hintTextColor) {
        if (this.focusedTextColor != hintTextColor) {
            if (this.defaultHintTextColor == null) {
                this.collapsingTextHelper.setCollapsedTextColor(hintTextColor);
            }
            this.focusedTextColor = hintTextColor;
            if (this.editText != null) {
                updateLabelState(false);
            }
        }
    }

    public ColorStateList getHintTextColor() {
        return this.focusedTextColor;
    }

    public void setDefaultHintTextColor(ColorStateList textColor) {
        this.defaultHintTextColor = textColor;
        this.focusedTextColor = textColor;
        if (this.editText != null) {
            updateLabelState(false);
        }
    }

    public ColorStateList getDefaultHintTextColor() {
        return this.defaultHintTextColor;
    }

    public void setErrorEnabled(boolean enabled) {
        this.indicatorViewController.setErrorEnabled(enabled);
    }

    public void setErrorTextAppearance(int errorTextAppearance) {
        this.indicatorViewController.setErrorTextAppearance(errorTextAppearance);
    }

    public void setErrorTextColor(ColorStateList errorTextColor) {
        this.indicatorViewController.setErrorViewTextColor(errorTextColor);
    }

    public int getErrorCurrentTextColors() {
        return this.indicatorViewController.getErrorViewCurrentTextColor();
    }

    public void setHelperTextTextAppearance(int helperTextTextAppearance) {
        this.indicatorViewController.setHelperTextAppearance(helperTextTextAppearance);
    }

    public void setHelperTextColor(ColorStateList helperTextColor) {
        this.indicatorViewController.setHelperTextViewTextColor(helperTextColor);
    }

    public boolean isErrorEnabled() {
        return this.indicatorViewController.isErrorEnabled();
    }

    public void setHelperTextEnabled(boolean enabled) {
        this.indicatorViewController.setHelperTextEnabled(enabled);
    }

    public void setHelperText(CharSequence helperText) {
        if (!TextUtils.isEmpty(helperText)) {
            if (!isHelperTextEnabled()) {
                setHelperTextEnabled(true);
            }
            this.indicatorViewController.showHelper(helperText);
        } else if (isHelperTextEnabled()) {
            setHelperTextEnabled(false);
        }
    }

    public boolean isHelperTextEnabled() {
        return this.indicatorViewController.isHelperTextEnabled();
    }

    public int getHelperTextCurrentTextColor() {
        return this.indicatorViewController.getHelperTextViewCurrentTextColor();
    }

    public void setErrorContentDescription(CharSequence errorContentDescription) {
        this.indicatorViewController.setErrorContentDescription(errorContentDescription);
    }

    public CharSequence getErrorContentDescription() {
        return this.indicatorViewController.getErrorContentDescription();
    }

    public void setErrorAccessibilityLiveRegion(int errorAccessibilityLiveRegion) {
        this.indicatorViewController.setErrorAccessibilityLiveRegion(errorAccessibilityLiveRegion);
    }

    public int getErrorAccessibilityLiveRegion() {
        return this.indicatorViewController.getErrorAccessibilityLiveRegion();
    }

    public void setError(CharSequence errorText) {
        if (!this.indicatorViewController.isErrorEnabled()) {
            if (!TextUtils.isEmpty(errorText)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (!TextUtils.isEmpty(errorText)) {
            this.indicatorViewController.showError(errorText);
        } else {
            this.indicatorViewController.hideError();
        }
    }

    public void setErrorIconDrawable(int resId) {
        this.endLayout.setErrorIconDrawable(resId);
    }

    public void setErrorIconDrawable(Drawable errorIconDrawable) {
        this.endLayout.setErrorIconDrawable(errorIconDrawable);
    }

    public Drawable getErrorIconDrawable() {
        return this.endLayout.getErrorIconDrawable();
    }

    public void setErrorIconTintList(ColorStateList errorIconTintList) {
        this.endLayout.setErrorIconTintList(errorIconTintList);
    }

    public void setErrorIconTintMode(PorterDuff.Mode errorIconTintMode) {
        this.endLayout.setErrorIconTintMode(errorIconTintMode);
    }

    public void setCounterEnabled(boolean enabled) {
        if (this.counterEnabled != enabled) {
            if (enabled) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.counterView = appCompatTextView;
                appCompatTextView.setId(C1087R.C1090id.textinput_counter);
                Typeface typeface2 = this.typeface;
                if (typeface2 != null) {
                    this.counterView.setTypeface(typeface2);
                }
                this.counterView.setMaxLines(1);
                this.indicatorViewController.addIndicator(this.counterView, 2);
                MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams) this.counterView.getLayoutParams(), getResources().getDimensionPixelOffset(C1087R.dimen.mtrl_textinput_counter_margin_start));
                updateCounterTextAppearanceAndColor();
                updateCounter();
            } else {
                this.indicatorViewController.removeIndicator(this.counterView, 2);
                this.counterView = null;
            }
            this.counterEnabled = enabled;
        }
    }

    public void setCounterTextAppearance(int counterTextAppearance2) {
        if (this.counterTextAppearance != counterTextAppearance2) {
            this.counterTextAppearance = counterTextAppearance2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterTextColor(ColorStateList counterTextColor2) {
        if (this.counterTextColor != counterTextColor2) {
            this.counterTextColor = counterTextColor2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public ColorStateList getCounterTextColor() {
        return this.counterTextColor;
    }

    public void setCounterOverflowTextAppearance(int counterOverflowTextAppearance2) {
        if (this.counterOverflowTextAppearance != counterOverflowTextAppearance2) {
            this.counterOverflowTextAppearance = counterOverflowTextAppearance2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterOverflowTextColor(ColorStateList counterOverflowTextColor2) {
        if (this.counterOverflowTextColor != counterOverflowTextColor2) {
            this.counterOverflowTextColor = counterOverflowTextColor2;
            updateCounterTextAppearanceAndColor();
        }
    }

    public ColorStateList getCounterOverflowTextColor() {
        return this.counterOverflowTextColor;
    }

    public boolean isCounterEnabled() {
        return this.counterEnabled;
    }

    public void setCounterMaxLength(int maxLength) {
        if (this.counterMaxLength != maxLength) {
            if (maxLength > 0) {
                this.counterMaxLength = maxLength;
            } else {
                this.counterMaxLength = -1;
            }
            if (this.counterEnabled) {
                updateCounter();
            }
        }
    }

    private void updateCounter() {
        if (this.counterView != null) {
            EditText editText2 = this.editText;
            updateCounter(editText2 == null ? null : editText2.getText());
        }
    }

    /* access modifiers changed from: package-private */
    public void updateCounter(Editable text) {
        int length = this.lengthCounter.countLength(text);
        boolean wasCounterOverflowed = this.counterOverflowed;
        int i = this.counterMaxLength;
        if (i == -1) {
            this.counterView.setText(String.valueOf(length));
            this.counterView.setContentDescription((CharSequence) null);
            this.counterOverflowed = false;
        } else {
            this.counterOverflowed = length > i;
            updateCounterContentDescription(getContext(), this.counterView, length, this.counterMaxLength, this.counterOverflowed);
            if (wasCounterOverflowed != this.counterOverflowed) {
                updateCounterTextAppearanceAndColor();
            }
            this.counterView.setText(BidiFormatter.getInstance().unicodeWrap(getContext().getString(C1087R.string.character_counter_pattern, new Object[]{Integer.valueOf(length), Integer.valueOf(this.counterMaxLength)})));
        }
        if (this.editText != null && wasCounterOverflowed != this.counterOverflowed) {
            updateLabelState(false);
            updateTextInputBoxState();
            updateEditTextBackground();
        }
    }

    private static void updateCounterContentDescription(Context context, TextView counterView2, int length, int counterMaxLength2, boolean counterOverflowed2) {
        int i;
        if (counterOverflowed2) {
            i = C1087R.string.character_counter_overflowed_content_description;
        } else {
            i = C1087R.string.character_counter_content_description;
        }
        counterView2.setContentDescription(context.getString(i, new Object[]{Integer.valueOf(length), Integer.valueOf(counterMaxLength2)}));
    }

    public void setPlaceholderText(CharSequence placeholderText2) {
        if (this.placeholderTextView == null) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
            this.placeholderTextView = appCompatTextView;
            appCompatTextView.setId(C1087R.C1090id.textinput_placeholder);
            ViewCompat.setImportantForAccessibility(this.placeholderTextView, 2);
            Fade createPlaceholderFadeTransition = createPlaceholderFadeTransition();
            this.placeholderFadeIn = createPlaceholderFadeTransition;
            createPlaceholderFadeTransition.setStartDelay(67);
            this.placeholderFadeOut = createPlaceholderFadeTransition();
            setPlaceholderTextAppearance(this.placeholderTextAppearance);
            setPlaceholderTextColor(this.placeholderTextColor);
        }
        if (TextUtils.isEmpty(placeholderText2)) {
            setPlaceholderTextEnabled(false);
        } else {
            if (!this.placeholderEnabled) {
                setPlaceholderTextEnabled(true);
            }
            this.placeholderText = placeholderText2;
        }
        updatePlaceholderText();
    }

    public CharSequence getPlaceholderText() {
        if (this.placeholderEnabled) {
            return this.placeholderText;
        }
        return null;
    }

    private void setPlaceholderTextEnabled(boolean placeholderEnabled2) {
        if (this.placeholderEnabled != placeholderEnabled2) {
            if (placeholderEnabled2) {
                addPlaceholderTextView();
            } else {
                removePlaceholderTextView();
                this.placeholderTextView = null;
            }
            this.placeholderEnabled = placeholderEnabled2;
        }
    }

    private Fade createPlaceholderFadeTransition() {
        Fade placeholderFadeTransition = new Fade();
        placeholderFadeTransition.setDuration((long) MotionUtils.resolveThemeDuration(getContext(), C1087R.attr.motionDurationShort2, 87));
        placeholderFadeTransition.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), C1087R.attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR));
        return placeholderFadeTransition;
    }

    private void updatePlaceholderText() {
        EditText editText2 = this.editText;
        updatePlaceholderText(editText2 == null ? null : editText2.getText());
    }

    /* access modifiers changed from: private */
    public void updatePlaceholderText(Editable text) {
        if (this.lengthCounter.countLength(text) != 0 || this.hintExpanded) {
            hidePlaceholderText();
        } else {
            showPlaceholderText();
        }
    }

    private void showPlaceholderText() {
        if (this.placeholderTextView != null && this.placeholderEnabled && !TextUtils.isEmpty(this.placeholderText)) {
            this.placeholderTextView.setText(this.placeholderText);
            TransitionManager.beginDelayedTransition(this.inputFrame, this.placeholderFadeIn);
            this.placeholderTextView.setVisibility(0);
            this.placeholderTextView.bringToFront();
            announceForAccessibility(this.placeholderText);
        }
    }

    private void hidePlaceholderText() {
        TextView textView = this.placeholderTextView;
        if (textView != null && this.placeholderEnabled) {
            textView.setText((CharSequence) null);
            TransitionManager.beginDelayedTransition(this.inputFrame, this.placeholderFadeOut);
            this.placeholderTextView.setVisibility(4);
        }
    }

    private void addPlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            this.inputFrame.addView(textView);
            this.placeholderTextView.setVisibility(0);
        }
    }

    private void removePlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    public void setPlaceholderTextColor(ColorStateList placeholderTextColor2) {
        if (this.placeholderTextColor != placeholderTextColor2) {
            this.placeholderTextColor = placeholderTextColor2;
            TextView textView = this.placeholderTextView;
            if (textView != null && placeholderTextColor2 != null) {
                textView.setTextColor(placeholderTextColor2);
            }
        }
    }

    public ColorStateList getPlaceholderTextColor() {
        return this.placeholderTextColor;
    }

    public void setPlaceholderTextAppearance(int placeholderTextAppearance2) {
        this.placeholderTextAppearance = placeholderTextAppearance2;
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, placeholderTextAppearance2);
        }
    }

    public int getPlaceholderTextAppearance() {
        return this.placeholderTextAppearance;
    }

    public void setPrefixText(CharSequence prefixText) {
        this.startLayout.setPrefixText(prefixText);
    }

    public CharSequence getPrefixText() {
        return this.startLayout.getPrefixText();
    }

    public TextView getPrefixTextView() {
        return this.startLayout.getPrefixTextView();
    }

    public void setPrefixTextColor(ColorStateList prefixTextColor) {
        this.startLayout.setPrefixTextColor(prefixTextColor);
    }

    public ColorStateList getPrefixTextColor() {
        return this.startLayout.getPrefixTextColor();
    }

    public void setPrefixTextAppearance(int prefixTextAppearance) {
        this.startLayout.setPrefixTextAppearance(prefixTextAppearance);
    }

    public void setSuffixText(CharSequence suffixText) {
        this.endLayout.setSuffixText(suffixText);
    }

    public CharSequence getSuffixText() {
        return this.endLayout.getSuffixText();
    }

    public TextView getSuffixTextView() {
        return this.endLayout.getSuffixTextView();
    }

    public void setSuffixTextColor(ColorStateList suffixTextColor) {
        this.endLayout.setSuffixTextColor(suffixTextColor);
    }

    public ColorStateList getSuffixTextColor() {
        return this.endLayout.getSuffixTextColor();
    }

    public void setSuffixTextAppearance(int suffixTextAppearance) {
        this.endLayout.setSuffixTextAppearance(suffixTextAppearance);
    }

    public void setEnabled(boolean enabled) {
        recursiveSetEnabled(this, enabled);
        super.setEnabled(enabled);
    }

    private static void recursiveSetEnabled(ViewGroup vg, boolean enabled) {
        int count = vg.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enabled);
            if (child instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) child, enabled);
            }
        }
    }

    public int getCounterMaxLength() {
        return this.counterMaxLength;
    }

    /* access modifiers changed from: package-private */
    public CharSequence getCounterOverflowDescription() {
        TextView textView;
        if (!this.counterEnabled || !this.counterOverflowed || (textView = this.counterView) == null) {
            return null;
        }
        return textView.getContentDescription();
    }

    private void updateCounterTextAppearanceAndColor() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        TextView textView = this.counterView;
        if (textView != null) {
            setTextAppearanceCompatWithErrorFallback(textView, this.counterOverflowed ? this.counterOverflowTextAppearance : this.counterTextAppearance);
            if (!this.counterOverflowed && (colorStateList2 = this.counterTextColor) != null) {
                this.counterView.setTextColor(colorStateList2);
            }
            if (this.counterOverflowed && (colorStateList = this.counterOverflowTextColor) != null) {
                this.counterView.setTextColor(colorStateList);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setTextAppearanceCompatWithErrorFallback(TextView textView, int textAppearance) {
        boolean useDefaultColor = false;
        try {
            TextViewCompat.setTextAppearance(textView, textAppearance);
            if (Build.VERSION.SDK_INT >= 23 && textView.getTextColors().getDefaultColor() == -65281) {
                useDefaultColor = true;
            }
        } catch (Exception e) {
            useDefaultColor = true;
        }
        if (useDefaultColor) {
            TextViewCompat.setTextAppearance(textView, C1087R.C1093style.TextAppearance_AppCompat_Caption);
            textView.setTextColor(ContextCompat.getColor(getContext(), C1087R.C1088color.design_error));
        }
    }

    private int calculateLabelMarginTop() {
        if (!this.hintEnabled) {
            return 0;
        }
        switch (this.boxBackgroundMode) {
            case 0:
                return (int) this.collapsingTextHelper.getCollapsedTextHeight();
            case 2:
                return (int) (this.collapsingTextHelper.getCollapsedTextHeight() / 2.0f);
            default:
                return 0;
        }
    }

    private Rect calculateCollapsedTextBounds(Rect rect) {
        if (this.editText != null) {
            Rect bounds = this.tmpBoundsRect;
            boolean isRtl = ViewUtils.isLayoutRtl(this);
            bounds.bottom = rect.bottom;
            switch (this.boxBackgroundMode) {
                case 1:
                    bounds.left = getLabelLeftBoundAlightWithPrefix(rect.left, isRtl);
                    bounds.top = rect.top + this.boxCollapsedPaddingTopPx;
                    bounds.right = getLabelRightBoundAlignedWithSuffix(rect.right, isRtl);
                    return bounds;
                case 2:
                    bounds.left = rect.left + this.editText.getPaddingLeft();
                    bounds.top = rect.top - calculateLabelMarginTop();
                    bounds.right = rect.right - this.editText.getPaddingRight();
                    return bounds;
                default:
                    bounds.left = getLabelLeftBoundAlightWithPrefix(rect.left, isRtl);
                    bounds.top = getPaddingTop();
                    bounds.right = getLabelRightBoundAlignedWithSuffix(rect.right, isRtl);
                    return bounds;
            }
        } else {
            throw new IllegalStateException();
        }
    }

    private int getLabelLeftBoundAlightWithPrefix(int rectLeft, boolean isRtl) {
        int left = this.editText.getCompoundPaddingLeft() + rectLeft;
        if (getPrefixText() == null || isRtl) {
            return left;
        }
        return (left - getPrefixTextView().getMeasuredWidth()) + getPrefixTextView().getPaddingLeft();
    }

    private int getLabelRightBoundAlignedWithSuffix(int rectRight, boolean isRtl) {
        int right = rectRight - this.editText.getCompoundPaddingRight();
        if (getPrefixText() == null || !isRtl) {
            return right;
        }
        return right + (getPrefixTextView().getMeasuredWidth() - getPrefixTextView().getPaddingRight());
    }

    private Rect calculateExpandedTextBounds(Rect rect) {
        if (this.editText != null) {
            Rect bounds = this.tmpBoundsRect;
            float labelHeight = this.collapsingTextHelper.getExpandedTextHeight();
            bounds.left = rect.left + this.editText.getCompoundPaddingLeft();
            bounds.top = calculateExpandedLabelTop(rect, labelHeight);
            bounds.right = rect.right - this.editText.getCompoundPaddingRight();
            bounds.bottom = calculateExpandedLabelBottom(rect, bounds, labelHeight);
            return bounds;
        }
        throw new IllegalStateException();
    }

    private int calculateExpandedLabelTop(Rect rect, float labelHeight) {
        if (isSingleLineFilledTextField()) {
            return (int) (((float) rect.centerY()) - (labelHeight / 2.0f));
        }
        return rect.top + this.editText.getCompoundPaddingTop();
    }

    private int calculateExpandedLabelBottom(Rect rect, Rect bounds, float labelHeight) {
        if (isSingleLineFilledTextField()) {
            return (int) (((float) bounds.top) + labelHeight);
        }
        return rect.bottom - this.editText.getCompoundPaddingBottom();
    }

    private boolean isSingleLineFilledTextField() {
        if (this.boxBackgroundMode != 1 || this.editText.getMinLines() > 1) {
            return false;
        }
        return true;
    }

    private int calculateBoxBackgroundColor() {
        int backgroundColor = this.boxBackgroundColor;
        if (this.boxBackgroundMode == 1) {
            return MaterialColors.layer(MaterialColors.getColor((View) this, C1087R.attr.colorSurface, 0), this.boxBackgroundColor);
        }
        return backgroundColor;
    }

    private void applyBoxAttributes() {
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable != null) {
            ShapeAppearanceModel shapeAppearanceModel2 = materialShapeDrawable.getShapeAppearanceModel();
            ShapeAppearanceModel shapeAppearanceModel3 = this.shapeAppearanceModel;
            if (shapeAppearanceModel2 != shapeAppearanceModel3) {
                this.boxBackground.setShapeAppearanceModel(shapeAppearanceModel3);
            }
            if (canDrawOutlineStroke()) {
                this.boxBackground.setStroke((float) this.boxStrokeWidthPx, this.boxStrokeColor);
            }
            int calculateBoxBackgroundColor = calculateBoxBackgroundColor();
            this.boxBackgroundColor = calculateBoxBackgroundColor;
            this.boxBackground.setFillColor(ColorStateList.valueOf(calculateBoxBackgroundColor));
            applyBoxUnderlineAttributes();
            updateEditTextBoxBackgroundIfNeeded();
        }
    }

    private void applyBoxUnderlineAttributes() {
        ColorStateList colorStateList;
        if (this.boxUnderlineDefault != null && this.boxUnderlineFocused != null) {
            if (canDrawStroke()) {
                MaterialShapeDrawable materialShapeDrawable = this.boxUnderlineDefault;
                if (this.editText.isFocused()) {
                    colorStateList = ColorStateList.valueOf(this.defaultStrokeColor);
                } else {
                    colorStateList = ColorStateList.valueOf(this.boxStrokeColor);
                }
                materialShapeDrawable.setFillColor(colorStateList);
                this.boxUnderlineFocused.setFillColor(ColorStateList.valueOf(this.boxStrokeColor));
            }
            invalidate();
        }
    }

    private boolean canDrawOutlineStroke() {
        return this.boxBackgroundMode == 2 && canDrawStroke();
    }

    private boolean canDrawStroke() {
        return this.boxStrokeWidthPx > -1 && this.boxStrokeColor != 0;
    }

    /* access modifiers changed from: package-private */
    public void updateEditTextBackground() {
        Drawable editTextBackground;
        TextView textView;
        EditText editText2 = this.editText;
        if (editText2 != null && this.boxBackgroundMode == 0 && (editTextBackground = editText2.getBackground()) != null) {
            if (DrawableUtils.canSafelyMutateDrawable(editTextBackground)) {
                editTextBackground = editTextBackground.mutate();
            }
            if (shouldShowError()) {
                editTextBackground.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(getErrorCurrentTextColors(), PorterDuff.Mode.SRC_IN));
            } else if (!this.counterOverflowed || (textView = this.counterView) == null) {
                DrawableCompat.clearColorFilter(editTextBackground);
                this.editText.refreshDrawableState();
            } else {
                editTextBackground.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldShowError() {
        return this.indicatorViewController.errorShouldBeShown();
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, (ClassLoader) null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        CharSequence error;
        boolean isEndIconChecked;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.error = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            this.isEndIconChecked = source.readInt() != 1 ? false : true;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            TextUtils.writeToParcel(this.error, dest, flags);
            dest.writeInt(this.isEndIconChecked ? 1 : 0);
        }

        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + this.error + "}";
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        if (shouldShowError()) {
            ss.error = getError();
        }
        ss.isEndIconChecked = this.endLayout.isEndIconChecked();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setError(ss.error);
        if (ss.isEndIconChecked) {
            post(new Runnable() {
                public void run() {
                    TextInputLayout.this.endLayout.checkEndIcon();
                }
            });
        }
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        this.restoringSavedState = true;
        super.dispatchRestoreInstanceState(container);
        this.restoringSavedState = false;
    }

    public CharSequence getError() {
        if (this.indicatorViewController.isErrorEnabled()) {
            return this.indicatorViewController.getErrorText();
        }
        return null;
    }

    public CharSequence getHelperText() {
        if (this.indicatorViewController.isHelperTextEnabled()) {
            return this.indicatorViewController.getHelperText();
        }
        return null;
    }

    public boolean isHintAnimationEnabled() {
        return this.hintAnimationEnabled;
    }

    public void setHintAnimationEnabled(boolean enabled) {
        this.hintAnimationEnabled = enabled;
    }

    public boolean isExpandedHintEnabled() {
        return this.expandedHintEnabled;
    }

    public void setExpandedHintEnabled(boolean enabled) {
        if (this.expandedHintEnabled != enabled) {
            this.expandedHintEnabled = enabled;
            updateLabelState(false);
        }
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        boolean isLayoutDirectionRtl = true;
        if (layoutDirection != 1) {
            isLayoutDirectionRtl = false;
        }
        if (isLayoutDirectionRtl != this.areCornerRadiiRtl) {
            float boxCornerRadiusTopLeft = this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.tmpRectF);
            float boxCornerRadiusTopRight = this.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(this.tmpRectF);
            float boxCornerRadiusBottomLeft = this.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(this.tmpRectF);
            float boxCornerRadiusBottomRight = this.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(this.tmpRectF);
            CornerTreatment topLeftTreatment = this.shapeAppearanceModel.getTopLeftCorner();
            CornerTreatment topRightTreatment = this.shapeAppearanceModel.getTopRightCorner();
            ShapeAppearanceModel newShapeAppearanceModel = ShapeAppearanceModel.builder().setTopLeftCorner(topRightTreatment).setTopRightCorner(topLeftTreatment).setBottomLeftCorner(this.shapeAppearanceModel.getBottomRightCorner()).setBottomRightCorner(this.shapeAppearanceModel.getBottomLeftCorner()).setTopLeftCornerSize(boxCornerRadiusTopRight).setTopRightCornerSize(boxCornerRadiusTopLeft).setBottomLeftCornerSize(boxCornerRadiusBottomRight).setBottomRightCornerSize(boxCornerRadiusBottomLeft).build();
            this.areCornerRadiiRtl = isLayoutDirectionRtl;
            setShapeAppearanceModel(newShapeAppearanceModel);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        boolean updatedHeight = updateEditTextHeightBasedOnIcon();
        boolean updatedIcon = updateDummyDrawables();
        if (updatedHeight || updatedIcon) {
            this.editText.post(new Runnable() {
                public void run() {
                    TextInputLayout.this.editText.requestLayout();
                }
            });
        }
        updatePlaceholderMeasurementsBasedOnEditText();
        this.endLayout.updateSuffixTextViewPadding();
    }

    private boolean updateEditTextHeightBasedOnIcon() {
        int maxIconHeight;
        if (this.editText == null || this.editText.getMeasuredHeight() >= (maxIconHeight = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight()))) {
            return false;
        }
        this.editText.setMinimumHeight(maxIconHeight);
        return true;
    }

    private void updatePlaceholderMeasurementsBasedOnEditText() {
        EditText editText2;
        if (this.placeholderTextView != null && (editText2 = this.editText) != null) {
            this.placeholderTextView.setGravity(editText2.getGravity());
            this.placeholderTextView.setPadding(this.editText.getCompoundPaddingLeft(), this.editText.getCompoundPaddingTop(), this.editText.getCompoundPaddingRight(), this.editText.getCompoundPaddingBottom());
        }
    }

    public void setStartIconDrawable(int resId) {
        setStartIconDrawable(resId != 0 ? AppCompatResources.getDrawable(getContext(), resId) : null);
    }

    public void setStartIconDrawable(Drawable startIconDrawable) {
        this.startLayout.setStartIconDrawable(startIconDrawable);
    }

    public Drawable getStartIconDrawable() {
        return this.startLayout.getStartIconDrawable();
    }

    public void setStartIconMinSize(int iconSize) {
        this.startLayout.setStartIconMinSize(iconSize);
    }

    public int getStartIconMinSize() {
        return this.startLayout.getStartIconMinSize();
    }

    public void setStartIconOnClickListener(View.OnClickListener startIconOnClickListener) {
        this.startLayout.setStartIconOnClickListener(startIconOnClickListener);
    }

    public void setStartIconOnLongClickListener(View.OnLongClickListener startIconOnLongClickListener) {
        this.startLayout.setStartIconOnLongClickListener(startIconOnLongClickListener);
    }

    public void setStartIconVisible(boolean visible) {
        this.startLayout.setStartIconVisible(visible);
    }

    public boolean isStartIconVisible() {
        return this.startLayout.isStartIconVisible();
    }

    public void refreshStartIconDrawableState() {
        this.startLayout.refreshStartIconDrawableState();
    }

    public void setStartIconCheckable(boolean startIconCheckable) {
        this.startLayout.setStartIconCheckable(startIconCheckable);
    }

    public boolean isStartIconCheckable() {
        return this.startLayout.isStartIconCheckable();
    }

    public void setStartIconContentDescription(int resId) {
        setStartIconContentDescription(resId != 0 ? getResources().getText(resId) : null);
    }

    public void setStartIconContentDescription(CharSequence startIconContentDescription) {
        this.startLayout.setStartIconContentDescription(startIconContentDescription);
    }

    public CharSequence getStartIconContentDescription() {
        return this.startLayout.getStartIconContentDescription();
    }

    public void setStartIconTintList(ColorStateList startIconTintList) {
        this.startLayout.setStartIconTintList(startIconTintList);
    }

    public void setStartIconTintMode(PorterDuff.Mode startIconTintMode) {
        this.startLayout.setStartIconTintMode(startIconTintMode);
    }

    public void setEndIconMode(int endIconMode) {
        this.endLayout.setEndIconMode(endIconMode);
    }

    public int getEndIconMode() {
        return this.endLayout.getEndIconMode();
    }

    public void setEndIconOnClickListener(View.OnClickListener endIconOnClickListener) {
        this.endLayout.setEndIconOnClickListener(endIconOnClickListener);
    }

    public void setErrorIconOnClickListener(View.OnClickListener errorIconOnClickListener) {
        this.endLayout.setErrorIconOnClickListener(errorIconOnClickListener);
    }

    public void setEndIconOnLongClickListener(View.OnLongClickListener endIconOnLongClickListener) {
        this.endLayout.setEndIconOnLongClickListener(endIconOnLongClickListener);
    }

    public void setErrorIconOnLongClickListener(View.OnLongClickListener errorIconOnLongClickListener) {
        this.endLayout.setErrorIconOnLongClickListener(errorIconOnLongClickListener);
    }

    public void refreshErrorIconDrawableState() {
        this.endLayout.refreshErrorIconDrawableState();
    }

    public void setEndIconVisible(boolean visible) {
        this.endLayout.setEndIconVisible(visible);
    }

    public boolean isEndIconVisible() {
        return this.endLayout.isEndIconVisible();
    }

    public void setEndIconActivated(boolean endIconActivated) {
        this.endLayout.setEndIconActivated(endIconActivated);
    }

    public void refreshEndIconDrawableState() {
        this.endLayout.refreshEndIconDrawableState();
    }

    public void setEndIconCheckable(boolean endIconCheckable) {
        this.endLayout.setEndIconCheckable(endIconCheckable);
    }

    public boolean isEndIconCheckable() {
        return this.endLayout.isEndIconCheckable();
    }

    public void setEndIconDrawable(int resId) {
        this.endLayout.setEndIconDrawable(resId);
    }

    public void setEndIconDrawable(Drawable endIconDrawable) {
        this.endLayout.setEndIconDrawable(endIconDrawable);
    }

    public Drawable getEndIconDrawable() {
        return this.endLayout.getEndIconDrawable();
    }

    public void setEndIconMinSize(int iconSize) {
        this.endLayout.setEndIconMinSize(iconSize);
    }

    public int getEndIconMinSize() {
        return this.endLayout.getEndIconMinSize();
    }

    public void setStartIconScaleType(ImageView.ScaleType scaleType) {
        this.startLayout.setStartIconScaleType(scaleType);
    }

    public ImageView.ScaleType getStartIconScaleType() {
        return this.startLayout.getStartIconScaleType();
    }

    public void setEndIconScaleType(ImageView.ScaleType scaleType) {
        this.endLayout.setEndIconScaleType(scaleType);
    }

    public ImageView.ScaleType getEndIconScaleType() {
        return this.endLayout.getEndIconScaleType();
    }

    public void setEndIconContentDescription(int resId) {
        this.endLayout.setEndIconContentDescription(resId);
    }

    public void setEndIconContentDescription(CharSequence endIconContentDescription) {
        this.endLayout.setEndIconContentDescription(endIconContentDescription);
    }

    public CharSequence getEndIconContentDescription() {
        return this.endLayout.getEndIconContentDescription();
    }

    public void setEndIconTintList(ColorStateList endIconTintList) {
        this.endLayout.setEndIconTintList(endIconTintList);
    }

    public void setEndIconTintMode(PorterDuff.Mode endIconTintMode) {
        this.endLayout.setEndIconTintMode(endIconTintMode);
    }

    public void addOnEndIconChangedListener(OnEndIconChangedListener listener) {
        this.endLayout.addOnEndIconChangedListener(listener);
    }

    public void removeOnEndIconChangedListener(OnEndIconChangedListener listener) {
        this.endLayout.removeOnEndIconChangedListener(listener);
    }

    public void clearOnEndIconChangedListeners() {
        this.endLayout.clearOnEndIconChangedListeners();
    }

    public void addOnEditTextAttachedListener(OnEditTextAttachedListener listener) {
        this.editTextAttachedListeners.add(listener);
        if (this.editText != null) {
            listener.onEditTextAttached(this);
        }
    }

    public void removeOnEditTextAttachedListener(OnEditTextAttachedListener listener) {
        this.editTextAttachedListeners.remove(listener);
    }

    public void clearOnEditTextAttachedListeners() {
        this.editTextAttachedListeners.clear();
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(int resId) {
        this.endLayout.setPasswordVisibilityToggleDrawable(resId);
    }

    @Deprecated
    public void setPasswordVisibilityToggleDrawable(Drawable icon) {
        this.endLayout.setPasswordVisibilityToggleDrawable(icon);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(int resId) {
        this.endLayout.setPasswordVisibilityToggleContentDescription(resId);
    }

    @Deprecated
    public void setPasswordVisibilityToggleContentDescription(CharSequence description) {
        this.endLayout.setPasswordVisibilityToggleContentDescription(description);
    }

    @Deprecated
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.endLayout.getPasswordVisibilityToggleDrawable();
    }

    @Deprecated
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.endLayout.getPasswordVisibilityToggleContentDescription();
    }

    @Deprecated
    public boolean isPasswordVisibilityToggleEnabled() {
        return this.endLayout.isPasswordVisibilityToggleEnabled();
    }

    @Deprecated
    public void setPasswordVisibilityToggleEnabled(boolean enabled) {
        this.endLayout.setPasswordVisibilityToggleEnabled(enabled);
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintList(ColorStateList tintList) {
        this.endLayout.setPasswordVisibilityToggleTintList(tintList);
    }

    @Deprecated
    public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode mode) {
        this.endLayout.setPasswordVisibilityToggleTintMode(mode);
    }

    @Deprecated
    public void passwordVisibilityToggleRequested(boolean shouldSkipAnimations) {
        this.endLayout.togglePasswordVisibilityToggle(shouldSkipAnimations);
    }

    public void setTextInputAccessibilityDelegate(AccessibilityDelegate delegate) {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            ViewCompat.setAccessibilityDelegate(editText2, delegate);
        }
    }

    /* access modifiers changed from: package-private */
    public CheckableImageButton getEndIconView() {
        return this.endLayout.getEndIconView();
    }

    private void dispatchOnEditTextAttached() {
        Iterator it = this.editTextAttachedListeners.iterator();
        while (it.hasNext()) {
            ((OnEditTextAttachedListener) it.next()).onEditTextAttached(this);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean updateDummyDrawables() {
        if (this.editText == null) {
            return false;
        }
        boolean updatedIcon = false;
        if (shouldUpdateStartDummyDrawable()) {
            int right = this.startLayout.getMeasuredWidth() - this.editText.getPaddingLeft();
            if (this.startDummyDrawable == null || this.startDummyDrawableWidth != right) {
                ColorDrawable colorDrawable = new ColorDrawable();
                this.startDummyDrawable = colorDrawable;
                this.startDummyDrawableWidth = right;
                colorDrawable.setBounds(0, 0, right, 1);
            }
            Drawable[] compounds = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            Drawable drawable = compounds[0];
            Drawable drawable2 = this.startDummyDrawable;
            if (drawable != drawable2) {
                TextViewCompat.setCompoundDrawablesRelative(this.editText, drawable2, compounds[1], compounds[2], compounds[3]);
                updatedIcon = true;
            }
        } else if (this.startDummyDrawable != null) {
            Drawable[] compounds2 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            TextViewCompat.setCompoundDrawablesRelative(this.editText, (Drawable) null, compounds2[1], compounds2[2], compounds2[3]);
            this.startDummyDrawable = null;
            updatedIcon = true;
        }
        if (shouldUpdateEndDummyDrawable()) {
            int right2 = this.endLayout.getSuffixTextView().getMeasuredWidth() - this.editText.getPaddingRight();
            View iconView = this.endLayout.getCurrentEndIconView();
            if (iconView != null) {
                right2 = iconView.getMeasuredWidth() + right2 + MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams) iconView.getLayoutParams());
            }
            Drawable[] compounds3 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            Drawable drawable3 = this.endDummyDrawable;
            if (drawable3 == null || this.endDummyDrawableWidth == right2) {
                if (drawable3 == null) {
                    ColorDrawable colorDrawable2 = new ColorDrawable();
                    this.endDummyDrawable = colorDrawable2;
                    this.endDummyDrawableWidth = right2;
                    colorDrawable2.setBounds(0, 0, right2, 1);
                }
                Drawable drawable4 = compounds3[2];
                Drawable drawable5 = this.endDummyDrawable;
                if (drawable4 == drawable5) {
                    return updatedIcon;
                }
                this.originalEditTextEndDrawable = compounds3[2];
                TextViewCompat.setCompoundDrawablesRelative(this.editText, compounds3[0], compounds3[1], drawable5, compounds3[3]);
                return true;
            }
            this.endDummyDrawableWidth = right2;
            drawable3.setBounds(0, 0, right2, 1);
            TextViewCompat.setCompoundDrawablesRelative(this.editText, compounds3[0], compounds3[1], this.endDummyDrawable, compounds3[3]);
            return true;
        } else if (this.endDummyDrawable == null) {
            return updatedIcon;
        } else {
            Drawable[] compounds4 = TextViewCompat.getCompoundDrawablesRelative(this.editText);
            if (compounds4[2] == this.endDummyDrawable) {
                TextViewCompat.setCompoundDrawablesRelative(this.editText, compounds4[0], compounds4[1], this.originalEditTextEndDrawable, compounds4[3]);
                updatedIcon = true;
            }
            this.endDummyDrawable = null;
            return updatedIcon;
        }
    }

    private boolean shouldUpdateStartDummyDrawable() {
        return (getStartIconDrawable() != null || (getPrefixText() != null && getPrefixTextView().getVisibility() == 0)) && this.startLayout.getMeasuredWidth() > 0;
    }

    private boolean shouldUpdateEndDummyDrawable() {
        return (this.endLayout.isErrorIconVisible() || ((this.endLayout.hasEndIcon() && isEndIconVisible()) || this.endLayout.getSuffixText() != null)) && this.endLayout.getMeasuredWidth() > 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        EditText editText2 = this.editText;
        if (editText2 != null) {
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(this, editText2, rect);
            updateBoxUnderlineBounds(rect);
            if (this.hintEnabled) {
                this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
                int editTextGravity = this.editText.getGravity();
                this.collapsingTextHelper.setCollapsedTextGravity((editTextGravity & -113) | 48);
                this.collapsingTextHelper.setExpandedTextGravity(editTextGravity);
                this.collapsingTextHelper.setCollapsedBounds(calculateCollapsedTextBounds(rect));
                this.collapsingTextHelper.setExpandedBounds(calculateExpandedTextBounds(rect));
                this.collapsingTextHelper.recalculate();
                if (cutoutEnabled() && !this.hintExpanded) {
                    openCutout();
                }
            }
        }
    }

    private void updateBoxUnderlineBounds(Rect bounds) {
        if (this.boxUnderlineDefault != null) {
            this.boxUnderlineDefault.setBounds(bounds.left, bounds.bottom - this.boxStrokeWidthDefaultPx, bounds.right, bounds.bottom);
        }
        if (this.boxUnderlineFocused != null) {
            this.boxUnderlineFocused.setBounds(bounds.left, bounds.bottom - this.boxStrokeWidthFocusedPx, bounds.right, bounds.bottom);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawHint(canvas);
        drawBoxUnderline(canvas);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.collapsingTextHelper.maybeUpdateFontWeightAdjustment(newConfig);
    }

    private void drawHint(Canvas canvas) {
        if (this.hintEnabled) {
            this.collapsingTextHelper.draw(canvas);
        }
    }

    private void drawBoxUnderline(Canvas canvas) {
        MaterialShapeDrawable materialShapeDrawable;
        if (this.boxUnderlineFocused != null && (materialShapeDrawable = this.boxUnderlineDefault) != null) {
            materialShapeDrawable.draw(canvas);
            if (this.editText.isFocused()) {
                Rect focusedUnderlineBounds = this.boxUnderlineFocused.getBounds();
                Rect defaultUnderlineBounds = this.boxUnderlineDefault.getBounds();
                float hintExpansionFraction = this.collapsingTextHelper.getExpansionFraction();
                int midpointX = defaultUnderlineBounds.centerX();
                focusedUnderlineBounds.left = AnimationUtils.lerp(midpointX, defaultUnderlineBounds.left, hintExpansionFraction);
                focusedUnderlineBounds.right = AnimationUtils.lerp(midpointX, defaultUnderlineBounds.right, hintExpansionFraction);
                this.boxUnderlineFocused.draw(canvas);
            }
        }
    }

    private void collapseHint(boolean animate) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (!animate || !this.hintAnimationEnabled) {
            this.collapsingTextHelper.setExpansionFraction(1.0f);
        } else {
            animateToExpansionFraction(1.0f);
        }
        this.hintExpanded = false;
        if (cutoutEnabled()) {
            openCutout();
        }
        updatePlaceholderText();
        this.startLayout.onHintStateChanged(false);
        this.endLayout.onHintStateChanged(false);
    }

    private boolean cutoutEnabled() {
        return this.hintEnabled && !TextUtils.isEmpty(this.hint) && (this.boxBackground instanceof CutoutDrawable);
    }

    private void openCutout() {
        if (cutoutEnabled()) {
            RectF cutoutBounds = this.tmpRectF;
            this.collapsingTextHelper.getCollapsedTextActualBounds(cutoutBounds, this.editText.getWidth(), this.editText.getGravity());
            if (cutoutBounds.width() > 0.0f && cutoutBounds.height() > 0.0f) {
                applyCutoutPadding(cutoutBounds);
                cutoutBounds.offset((float) (-getPaddingLeft()), (((float) (-getPaddingTop())) - (cutoutBounds.height() / 2.0f)) + ((float) this.boxStrokeWidthPx));
                ((CutoutDrawable) this.boxBackground).setCutout(cutoutBounds);
            }
        }
    }

    private void recalculateCutout() {
        if (cutoutEnabled() && !this.hintExpanded) {
            closeCutout();
            openCutout();
        }
    }

    private void closeCutout() {
        if (cutoutEnabled()) {
            ((CutoutDrawable) this.boxBackground).removeCutout();
        }
    }

    private void applyCutoutPadding(RectF cutoutBounds) {
        cutoutBounds.left -= (float) this.boxLabelCutoutPaddingPx;
        cutoutBounds.right += (float) this.boxLabelCutoutPaddingPx;
    }

    /* access modifiers changed from: package-private */
    public boolean cutoutIsOpen() {
        return cutoutEnabled() && ((CutoutDrawable) this.boxBackground).hasCutout();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (!this.inDrawableStateChanged) {
            boolean z = true;
            this.inDrawableStateChanged = true;
            super.drawableStateChanged();
            int[] state = getDrawableState();
            boolean changed = false;
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            if (collapsingTextHelper2 != null) {
                changed = false | collapsingTextHelper2.setState(state);
            }
            if (this.editText != null) {
                if (!ViewCompat.isLaidOut(this) || !isEnabled()) {
                    z = false;
                }
                updateLabelState(z);
            }
            updateEditTextBackground();
            updateTextInputBoxState();
            if (changed) {
                invalidate();
            }
            this.inDrawableStateChanged = false;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        r0 = r6.editText;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateTextInputBoxState() {
        /*
            r6 = this;
            com.google.android.material.shape.MaterialShapeDrawable r0 = r6.boxBackground
            if (r0 == 0) goto L_0x00e3
            int r0 = r6.boxBackgroundMode
            if (r0 != 0) goto L_0x000a
            goto L_0x00e3
        L_0x000a:
            boolean r0 = r6.isFocused()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001f
            android.widget.EditText r0 = r6.editText
            if (r0 == 0) goto L_0x001d
            boolean r0 = r0.hasFocus()
            if (r0 == 0) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r0 = 0
            goto L_0x0020
        L_0x001f:
            r0 = 1
        L_0x0020:
            boolean r3 = r6.isHovered()
            if (r3 != 0) goto L_0x0033
            android.widget.EditText r3 = r6.editText
            if (r3 == 0) goto L_0x0031
            boolean r3 = r3.isHovered()
            if (r3 == 0) goto L_0x0031
            goto L_0x0033
        L_0x0031:
            r3 = 0
            goto L_0x0034
        L_0x0033:
            r3 = 1
        L_0x0034:
            boolean r4 = r6.shouldShowError()
            if (r4 != 0) goto L_0x0042
            android.widget.TextView r4 = r6.counterView
            if (r4 == 0) goto L_0x0043
            boolean r4 = r6.counterOverflowed
            if (r4 == 0) goto L_0x0043
        L_0x0042:
            r1 = 1
        L_0x0043:
            boolean r4 = r6.isEnabled()
            if (r4 != 0) goto L_0x004e
            int r4 = r6.disabledColor
            r6.boxStrokeColor = r4
            goto L_0x008c
        L_0x004e:
            boolean r4 = r6.shouldShowError()
            if (r4 == 0) goto L_0x0063
            android.content.res.ColorStateList r4 = r6.strokeErrorColor
            if (r4 == 0) goto L_0x005c
            r6.updateStrokeErrorColor(r0, r3)
            goto L_0x008c
        L_0x005c:
            int r4 = r6.getErrorCurrentTextColors()
            r6.boxStrokeColor = r4
            goto L_0x008c
        L_0x0063:
            boolean r4 = r6.counterOverflowed
            if (r4 == 0) goto L_0x007a
            android.widget.TextView r4 = r6.counterView
            if (r4 == 0) goto L_0x007a
            android.content.res.ColorStateList r5 = r6.strokeErrorColor
            if (r5 == 0) goto L_0x0073
            r6.updateStrokeErrorColor(r0, r3)
            goto L_0x008c
        L_0x0073:
            int r4 = r4.getCurrentTextColor()
            r6.boxStrokeColor = r4
            goto L_0x008c
        L_0x007a:
            if (r0 == 0) goto L_0x0081
            int r4 = r6.focusedStrokeColor
            r6.boxStrokeColor = r4
            goto L_0x008c
        L_0x0081:
            if (r3 == 0) goto L_0x0088
            int r4 = r6.hoveredStrokeColor
            r6.boxStrokeColor = r4
            goto L_0x008c
        L_0x0088:
            int r4 = r6.defaultStrokeColor
            r6.boxStrokeColor = r4
        L_0x008c:
            int r4 = android.os.Build.VERSION.SDK_INT
            r5 = 29
            if (r4 < r5) goto L_0x0095
            r6.updateCursorColor(r1)
        L_0x0095:
            com.google.android.material.textfield.EndCompoundLayout r4 = r6.endLayout
            r4.onTextInputBoxStateUpdated()
            r6.refreshStartIconDrawableState()
            int r4 = r6.boxBackgroundMode
            r5 = 2
            if (r4 != r5) goto L_0x00bc
            int r4 = r6.boxStrokeWidthPx
            if (r0 == 0) goto L_0x00b1
            boolean r5 = r6.isEnabled()
            if (r5 == 0) goto L_0x00b1
            int r5 = r6.boxStrokeWidthFocusedPx
            r6.boxStrokeWidthPx = r5
            goto L_0x00b5
        L_0x00b1:
            int r5 = r6.boxStrokeWidthDefaultPx
            r6.boxStrokeWidthPx = r5
        L_0x00b5:
            int r5 = r6.boxStrokeWidthPx
            if (r5 == r4) goto L_0x00bc
            r6.recalculateCutout()
        L_0x00bc:
            int r4 = r6.boxBackgroundMode
            if (r4 != r2) goto L_0x00df
            boolean r2 = r6.isEnabled()
            if (r2 != 0) goto L_0x00cb
            int r2 = r6.disabledFilledBackgroundColor
            r6.boxBackgroundColor = r2
            goto L_0x00df
        L_0x00cb:
            if (r3 == 0) goto L_0x00d4
            if (r0 != 0) goto L_0x00d4
            int r2 = r6.hoveredFilledBackgroundColor
            r6.boxBackgroundColor = r2
            goto L_0x00df
        L_0x00d4:
            if (r0 == 0) goto L_0x00db
            int r2 = r6.focusedFilledBackgroundColor
            r6.boxBackgroundColor = r2
            goto L_0x00df
        L_0x00db:
            int r2 = r6.defaultFilledBackgroundColor
            r6.boxBackgroundColor = r2
        L_0x00df:
            r6.applyBoxAttributes()
            return
        L_0x00e3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.updateTextInputBoxState():void");
    }

    private void updateStrokeErrorColor(boolean hasFocus, boolean isHovered) {
        int defaultStrokeErrorColor = this.strokeErrorColor.getDefaultColor();
        int hoveredStrokeErrorColor = this.strokeErrorColor.getColorForState(new int[]{16843623, 16842910}, defaultStrokeErrorColor);
        int focusedStrokeErrorColor = this.strokeErrorColor.getColorForState(new int[]{16843518, 16842910}, defaultStrokeErrorColor);
        if (hasFocus) {
            this.boxStrokeColor = focusedStrokeErrorColor;
        } else if (isHovered) {
            this.boxStrokeColor = hoveredStrokeErrorColor;
        } else {
            this.boxStrokeColor = defaultStrokeErrorColor;
        }
    }

    private void updateCursorColor(boolean isOnError) {
        ColorStateList cursorColor = MaterialColors.getColorStateListOrNull(getContext(), C1087R.attr.colorControlActivated);
        EditText editText2 = this.editText;
        if (editText2 != null && editText2.getTextCursorDrawable() != null && cursorColor != null) {
            Drawable cursorDrawable = this.editText.getTextCursorDrawable();
            if (isOnError) {
                ColorStateList colorStateList = this.strokeErrorColor;
                if (colorStateList == null) {
                    colorStateList = ColorStateList.valueOf(this.boxStrokeColor);
                }
                cursorColor = colorStateList;
            }
            DrawableCompat.setTintList(cursorDrawable, cursorColor);
        }
    }

    private void expandHint(boolean animate) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (!animate || !this.hintAnimationEnabled) {
            this.collapsingTextHelper.setExpansionFraction(0.0f);
        } else {
            animateToExpansionFraction(0.0f);
        }
        if (cutoutEnabled() && ((CutoutDrawable) this.boxBackground).hasCutout()) {
            closeCutout();
        }
        this.hintExpanded = true;
        hidePlaceholderText();
        this.startLayout.onHintStateChanged(true);
        this.endLayout.onHintStateChanged(true);
    }

    /* access modifiers changed from: package-private */
    public void animateToExpansionFraction(float target) {
        if (this.collapsingTextHelper.getExpansionFraction() != target) {
            if (this.animator == null) {
                ValueAnimator valueAnimator = new ValueAnimator();
                this.animator = valueAnimator;
                valueAnimator.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), C1087R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
                this.animator.setDuration((long) MotionUtils.resolveThemeDuration(getContext(), C1087R.attr.motionDurationMedium4, 167));
                this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animator) {
                        TextInputLayout.this.collapsingTextHelper.setExpansionFraction(((Float) animator.getAnimatedValue()).floatValue());
                    }
                });
            }
            this.animator.setFloatValues(new float[]{this.collapsingTextHelper.getExpansionFraction(), target});
            this.animator.start();
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean isHintExpanded() {
        return this.hintExpanded;
    }

    /* access modifiers changed from: package-private */
    public final boolean isHelperTextDisplayed() {
        return this.indicatorViewController.helperTextIsDisplayed();
    }

    /* access modifiers changed from: package-private */
    public final int getHintCurrentCollapsedTextColor() {
        return this.collapsingTextHelper.getCurrentCollapsedTextColor();
    }

    /* access modifiers changed from: package-private */
    public final float getHintCollapsedTextHeight() {
        return this.collapsingTextHelper.getCollapsedTextHeight();
    }

    public static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final TextInputLayout layout;

        public AccessibilityDelegate(TextInputLayout layout2) {
            this.layout = layout2;
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            String hint;
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = info;
            super.onInitializeAccessibilityNodeInfo(host, info);
            EditText editText = this.layout.getEditText();
            CharSequence inputText = editText != null ? editText.getText() : null;
            CharSequence hintText = this.layout.getHint();
            CharSequence errorText = this.layout.getError();
            CharSequence placeholderText = this.layout.getPlaceholderText();
            int maxCharLimit = this.layout.getCounterMaxLength();
            CharSequence counterOverflowDesc = this.layout.getCounterOverflowDescription();
            boolean showingText = !TextUtils.isEmpty(inputText);
            boolean hasHint = !TextUtils.isEmpty(hintText);
            boolean isHintCollapsed = !this.layout.isHintExpanded();
            boolean showingError = !TextUtils.isEmpty(errorText);
            boolean contentInvalid = showingError || !TextUtils.isEmpty(counterOverflowDesc);
            String hint2 = hasHint ? hintText.toString() : "";
            this.layout.startLayout.setupAccessibilityNodeInfo(accessibilityNodeInfoCompat);
            if (showingText) {
                accessibilityNodeInfoCompat.setText(inputText);
                hint = hint2;
                EditText editText2 = editText;
            } else if (!TextUtils.isEmpty(hint2)) {
                hint = hint2;
                accessibilityNodeInfoCompat.setText(hint);
                if (!isHintCollapsed || placeholderText == null) {
                } else {
                    EditText editText3 = editText;
                    accessibilityNodeInfoCompat.setText(hint + ", " + placeholderText);
                }
            } else {
                hint = hint2;
                EditText editText4 = editText;
                if (placeholderText != null) {
                    accessibilityNodeInfoCompat.setText(placeholderText);
                }
            }
            if (!TextUtils.isEmpty(hint)) {
                CharSequence charSequence = hintText;
                if (Build.VERSION.SDK_INT >= 26) {
                    accessibilityNodeInfoCompat.setHintText(hint);
                } else {
                    accessibilityNodeInfoCompat.setText(showingText ? inputText + ", " + hint : hint);
                }
                accessibilityNodeInfoCompat.setShowingHintText(!showingText);
            }
            accessibilityNodeInfoCompat.setMaxTextLength((inputText == null || inputText.length() != maxCharLimit) ? -1 : maxCharLimit);
            if (contentInvalid) {
                accessibilityNodeInfoCompat.setError(showingError ? errorText : counterOverflowDesc);
            }
            View helperTextView = this.layout.indicatorViewController.getHelperTextView();
            if (helperTextView != null) {
                accessibilityNodeInfoCompat.setLabelFor(helperTextView);
            }
            this.layout.endLayout.getEndIconDelegate().onInitializeAccessibilityNodeInfo(host, accessibilityNodeInfoCompat);
        }

        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onPopulateAccessibilityEvent(host, event);
            this.layout.endLayout.getEndIconDelegate().onPopulateAccessibilityEvent(host, event);
        }
    }
}
