package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import java.util.ArrayList;
import java.util.List;

final class IndicatorViewController {
    private static final int CAPTION_STATE_ERROR = 1;
    private static final int CAPTION_STATE_HELPER_TEXT = 2;
    private static final int CAPTION_STATE_NONE = 0;
    static final int COUNTER_INDEX = 2;
    private static final int DEFAULT_CAPTION_FADE_ANIMATION_DURATION = 167;
    private static final int DEFAULT_CAPTION_TRANSLATION_Y_ANIMATION_DURATION = 217;
    static final int ERROR_INDEX = 0;
    static final int HELPER_INDEX = 1;
    /* access modifiers changed from: private */
    public Animator captionAnimator;
    private FrameLayout captionArea;
    /* access modifiers changed from: private */
    public int captionDisplayed;
    private final int captionFadeInAnimationDuration;
    private final TimeInterpolator captionFadeInAnimationInterpolator;
    private final int captionFadeOutAnimationDuration;
    private final TimeInterpolator captionFadeOutAnimationInterpolator;
    private int captionToShow;
    private final int captionTranslationYAnimationDuration;
    private final TimeInterpolator captionTranslationYAnimationInterpolator;
    private final float captionTranslationYPx;
    private final Context context;
    private boolean errorEnabled;
    private CharSequence errorText;
    private int errorTextAppearance;
    /* access modifiers changed from: private */
    public TextView errorView;
    private int errorViewAccessibilityLiveRegion;
    private CharSequence errorViewContentDescription;
    private ColorStateList errorViewTextColor;
    private CharSequence helperText;
    private boolean helperTextEnabled;
    private int helperTextTextAppearance;
    private TextView helperTextView;
    private ColorStateList helperTextViewTextColor;
    private LinearLayout indicatorArea;
    private int indicatorsAdded;
    /* access modifiers changed from: private */
    public final TextInputLayout textInputView;
    private Typeface typeface;

    public IndicatorViewController(TextInputLayout textInputView2) {
        Context context2 = textInputView2.getContext();
        this.context = context2;
        this.textInputView = textInputView2;
        this.captionTranslationYPx = (float) context2.getResources().getDimensionPixelSize(C1087R.dimen.design_textinput_caption_translate_y);
        this.captionTranslationYAnimationDuration = MotionUtils.resolveThemeDuration(context2, C1087R.attr.motionDurationShort4, DEFAULT_CAPTION_TRANSLATION_Y_ANIMATION_DURATION);
        this.captionFadeInAnimationDuration = MotionUtils.resolveThemeDuration(context2, C1087R.attr.motionDurationMedium4, 167);
        this.captionFadeOutAnimationDuration = MotionUtils.resolveThemeDuration(context2, C1087R.attr.motionDurationShort4, 167);
        this.captionTranslationYAnimationInterpolator = MotionUtils.resolveThemeInterpolator(context2, C1087R.attr.motionEasingEmphasizedDecelerateInterpolator, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        this.captionFadeInAnimationInterpolator = MotionUtils.resolveThemeInterpolator(context2, C1087R.attr.motionEasingEmphasizedDecelerateInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
        this.captionFadeOutAnimationInterpolator = MotionUtils.resolveThemeInterpolator(context2, C1087R.attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
    }

    /* access modifiers changed from: package-private */
    public void showHelper(CharSequence helperText2) {
        cancelCaptionAnimator();
        this.helperText = helperText2;
        this.helperTextView.setText(helperText2);
        int i = this.captionDisplayed;
        if (i != 2) {
            this.captionToShow = 2;
        }
        updateCaptionViewsVisibility(i, this.captionToShow, shouldAnimateCaptionView(this.helperTextView, helperText2));
    }

    /* access modifiers changed from: package-private */
    public void hideHelperText() {
        cancelCaptionAnimator();
        int i = this.captionDisplayed;
        if (i == 2) {
            this.captionToShow = 0;
        }
        updateCaptionViewsVisibility(i, this.captionToShow, shouldAnimateCaptionView(this.helperTextView, ""));
    }

    /* access modifiers changed from: package-private */
    public void showError(CharSequence errorText2) {
        cancelCaptionAnimator();
        this.errorText = errorText2;
        this.errorView.setText(errorText2);
        int i = this.captionDisplayed;
        if (i != 1) {
            this.captionToShow = 1;
        }
        updateCaptionViewsVisibility(i, this.captionToShow, shouldAnimateCaptionView(this.errorView, errorText2));
    }

    /* access modifiers changed from: package-private */
    public void hideError() {
        this.errorText = null;
        cancelCaptionAnimator();
        if (this.captionDisplayed == 1) {
            if (!this.helperTextEnabled || TextUtils.isEmpty(this.helperText)) {
                this.captionToShow = 0;
            } else {
                this.captionToShow = 2;
            }
        }
        updateCaptionViewsVisibility(this.captionDisplayed, this.captionToShow, shouldAnimateCaptionView(this.errorView, ""));
    }

    private boolean shouldAnimateCaptionView(TextView captionView, CharSequence captionText) {
        return ViewCompat.isLaidOut(this.textInputView) && this.textInputView.isEnabled() && (this.captionToShow != this.captionDisplayed || captionView == null || !TextUtils.equals(captionView.getText(), captionText));
    }

    private void updateCaptionViewsVisibility(int captionToHide, int captionToShow2, boolean animate) {
        int i = captionToShow2;
        boolean z = animate;
        if (captionToHide != i) {
            if (z) {
                AnimatorSet captionAnimator2 = new AnimatorSet();
                this.captionAnimator = captionAnimator2;
                List<Animator> captionAnimatorList = new ArrayList<>();
                List<Animator> list = captionAnimatorList;
                int i2 = captionToHide;
                int i3 = captionToShow2;
                createCaptionAnimators(list, this.helperTextEnabled, this.helperTextView, 2, i2, i3);
                createCaptionAnimators(list, this.errorEnabled, this.errorView, 1, i2, i3);
                AnimatorSetCompat.playTogether(captionAnimator2, captionAnimatorList);
                final int i4 = captionToShow2;
                final TextView captionViewFromDisplayState = getCaptionViewFromDisplayState(captionToHide);
                final int i5 = captionToHide;
                final TextView captionViewFromDisplayState2 = getCaptionViewFromDisplayState(i);
                captionAnimator2.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        int unused = IndicatorViewController.this.captionDisplayed = i4;
                        Animator unused2 = IndicatorViewController.this.captionAnimator = null;
                        TextView textView = captionViewFromDisplayState;
                        if (textView != null) {
                            textView.setVisibility(4);
                            if (i5 == 1 && IndicatorViewController.this.errorView != null) {
                                IndicatorViewController.this.errorView.setText((CharSequence) null);
                            }
                        }
                        TextView textView2 = captionViewFromDisplayState2;
                        if (textView2 != null) {
                            textView2.setTranslationY(0.0f);
                            captionViewFromDisplayState2.setAlpha(1.0f);
                        }
                    }

                    public void onAnimationStart(Animator animator) {
                        TextView textView = captionViewFromDisplayState2;
                        if (textView != null) {
                            textView.setVisibility(0);
                            captionViewFromDisplayState2.setAlpha(0.0f);
                        }
                    }
                });
                captionAnimator2.start();
            } else {
                setCaptionViewVisibilities(captionToHide, captionToShow2);
            }
            this.textInputView.updateEditTextBackground();
            this.textInputView.updateLabelState(z);
            this.textInputView.updateTextInputBoxState();
        }
    }

    private void setCaptionViewVisibilities(int captionToHide, int captionToShow2) {
        TextView captionViewDisplayed;
        TextView captionViewToShow;
        if (captionToHide != captionToShow2) {
            if (!(captionToShow2 == 0 || (captionViewToShow = getCaptionViewFromDisplayState(captionToShow2)) == null)) {
                captionViewToShow.setVisibility(0);
                captionViewToShow.setAlpha(1.0f);
            }
            if (!(captionToHide == 0 || (captionViewDisplayed = getCaptionViewFromDisplayState(captionToHide)) == null)) {
                captionViewDisplayed.setVisibility(4);
                if (captionToHide == 1) {
                    captionViewDisplayed.setText((CharSequence) null);
                }
            }
            this.captionDisplayed = captionToShow2;
        }
    }

    private void createCaptionAnimators(List<Animator> captionAnimatorList, boolean captionEnabled, TextView captionView, int captionState, int captionToHide, int captionToShow2) {
        if (captionView != null && captionEnabled) {
            boolean enableShowAnimation = false;
            if (captionState == captionToShow2 || captionState == captionToHide) {
                Animator animator = createCaptionOpacityAnimator(captionView, captionToShow2 == captionState);
                if (captionState == captionToShow2 && captionToHide != 0) {
                    enableShowAnimation = true;
                }
                if (enableShowAnimation) {
                    animator.setStartDelay((long) this.captionFadeOutAnimationDuration);
                }
                captionAnimatorList.add(animator);
                if (captionToShow2 == captionState && captionToHide != 0) {
                    Animator translationYAnimator = createCaptionTranslationYAnimator(captionView);
                    translationYAnimator.setStartDelay((long) this.captionFadeOutAnimationDuration);
                    captionAnimatorList.add(translationYAnimator);
                }
            }
        }
    }

    private ObjectAnimator createCaptionOpacityAnimator(TextView captionView, boolean display) {
        long j;
        TimeInterpolator timeInterpolator;
        ObjectAnimator opacityAnimator = ObjectAnimator.ofFloat(captionView, View.ALPHA, new float[]{display ? 1.0f : 0.0f});
        if (display) {
            j = (long) this.captionFadeInAnimationDuration;
        } else {
            j = (long) this.captionFadeOutAnimationDuration;
        }
        opacityAnimator.setDuration(j);
        if (display) {
            timeInterpolator = this.captionFadeInAnimationInterpolator;
        } else {
            timeInterpolator = this.captionFadeOutAnimationInterpolator;
        }
        opacityAnimator.setInterpolator(timeInterpolator);
        return opacityAnimator;
    }

    private ObjectAnimator createCaptionTranslationYAnimator(TextView captionView) {
        ObjectAnimator translationYAnimator = ObjectAnimator.ofFloat(captionView, View.TRANSLATION_Y, new float[]{-this.captionTranslationYPx, 0.0f});
        translationYAnimator.setDuration((long) this.captionTranslationYAnimationDuration);
        translationYAnimator.setInterpolator(this.captionTranslationYAnimationInterpolator);
        return translationYAnimator;
    }

    /* access modifiers changed from: package-private */
    public void cancelCaptionAnimator() {
        Animator animator = this.captionAnimator;
        if (animator != null) {
            animator.cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isCaptionView(int index) {
        return index == 0 || index == 1;
    }

    private TextView getCaptionViewFromDisplayState(int captionDisplayState) {
        switch (captionDisplayState) {
            case 1:
                return this.errorView;
            case 2:
                return this.helperTextView;
            default:
                return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void adjustIndicatorPadding() {
        if (canAdjustIndicatorPadding()) {
            EditText editText = this.textInputView.getEditText();
            boolean isFontScaleLarge = MaterialResources.isFontScaleAtLeast1_3(this.context);
            ViewCompat.setPaddingRelative(this.indicatorArea, getIndicatorPadding(isFontScaleLarge, C1087R.dimen.material_helper_text_font_1_3_padding_horizontal, ViewCompat.getPaddingStart(editText)), getIndicatorPadding(isFontScaleLarge, C1087R.dimen.material_helper_text_font_1_3_padding_top, this.context.getResources().getDimensionPixelSize(C1087R.dimen.material_helper_text_default_padding_top)), getIndicatorPadding(isFontScaleLarge, C1087R.dimen.material_helper_text_font_1_3_padding_horizontal, ViewCompat.getPaddingEnd(editText)), 0);
        }
    }

    private boolean canAdjustIndicatorPadding() {
        return (this.indicatorArea == null || this.textInputView.getEditText() == null) ? false : true;
    }

    private int getIndicatorPadding(boolean isFontScaleLarge, int largeFontPaddingRes, int defaultPadding) {
        if (isFontScaleLarge) {
            return this.context.getResources().getDimensionPixelSize(largeFontPaddingRes);
        }
        return defaultPadding;
    }

    /* access modifiers changed from: package-private */
    public void addIndicator(TextView indicator, int index) {
        if (this.indicatorArea == null && this.captionArea == null) {
            LinearLayout linearLayout = new LinearLayout(this.context);
            this.indicatorArea = linearLayout;
            linearLayout.setOrientation(0);
            this.textInputView.addView(this.indicatorArea, -1, -2);
            this.captionArea = new FrameLayout(this.context);
            this.indicatorArea.addView(this.captionArea, new LinearLayout.LayoutParams(0, -2, 1.0f));
            if (this.textInputView.getEditText() != null) {
                adjustIndicatorPadding();
            }
        }
        if (isCaptionView(index)) {
            this.captionArea.setVisibility(0);
            this.captionArea.addView(indicator);
        } else {
            this.indicatorArea.addView(indicator, new LinearLayout.LayoutParams(-2, -2));
        }
        this.indicatorArea.setVisibility(0);
        this.indicatorsAdded++;
    }

    /* access modifiers changed from: package-private */
    public void removeIndicator(TextView indicator, int index) {
        FrameLayout frameLayout;
        if (this.indicatorArea != null) {
            if (!isCaptionView(index) || (frameLayout = this.captionArea) == null) {
                this.indicatorArea.removeView(indicator);
            } else {
                frameLayout.removeView(indicator);
            }
            int i = this.indicatorsAdded - 1;
            this.indicatorsAdded = i;
            setViewGroupGoneIfEmpty(this.indicatorArea, i);
        }
    }

    private void setViewGroupGoneIfEmpty(ViewGroup viewGroup, int indicatorsAdded2) {
        if (indicatorsAdded2 == 0) {
            viewGroup.setVisibility(8);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorEnabled(boolean enabled) {
        if (this.errorEnabled != enabled) {
            cancelCaptionAnimator();
            if (enabled) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(this.context);
                this.errorView = appCompatTextView;
                appCompatTextView.setId(C1087R.C1090id.textinput_error);
                this.errorView.setTextAlignment(5);
                Typeface typeface2 = this.typeface;
                if (typeface2 != null) {
                    this.errorView.setTypeface(typeface2);
                }
                setErrorTextAppearance(this.errorTextAppearance);
                setErrorViewTextColor(this.errorViewTextColor);
                setErrorContentDescription(this.errorViewContentDescription);
                setErrorAccessibilityLiveRegion(this.errorViewAccessibilityLiveRegion);
                this.errorView.setVisibility(4);
                addIndicator(this.errorView, 0);
            } else {
                hideError();
                removeIndicator(this.errorView, 0);
                this.errorView = null;
                this.textInputView.updateEditTextBackground();
                this.textInputView.updateTextInputBoxState();
            }
            this.errorEnabled = enabled;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isErrorEnabled() {
        return this.errorEnabled;
    }

    /* access modifiers changed from: package-private */
    public boolean isHelperTextEnabled() {
        return this.helperTextEnabled;
    }

    /* access modifiers changed from: package-private */
    public void setHelperTextEnabled(boolean enabled) {
        if (this.helperTextEnabled != enabled) {
            cancelCaptionAnimator();
            if (enabled) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(this.context);
                this.helperTextView = appCompatTextView;
                appCompatTextView.setId(C1087R.C1090id.textinput_helper_text);
                this.helperTextView.setTextAlignment(5);
                Typeface typeface2 = this.typeface;
                if (typeface2 != null) {
                    this.helperTextView.setTypeface(typeface2);
                }
                this.helperTextView.setVisibility(4);
                ViewCompat.setAccessibilityLiveRegion(this.helperTextView, 1);
                setHelperTextAppearance(this.helperTextTextAppearance);
                setHelperTextViewTextColor(this.helperTextViewTextColor);
                addIndicator(this.helperTextView, 1);
                this.helperTextView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
                    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        View editText = IndicatorViewController.this.textInputView.getEditText();
                        if (editText != null) {
                            accessibilityNodeInfo.setLabeledBy(editText);
                        }
                    }
                });
            } else {
                hideHelperText();
                removeIndicator(this.helperTextView, 1);
                this.helperTextView = null;
                this.textInputView.updateEditTextBackground();
                this.textInputView.updateTextInputBoxState();
            }
            this.helperTextEnabled = enabled;
        }
    }

    /* access modifiers changed from: package-private */
    public View getHelperTextView() {
        return this.helperTextView;
    }

    /* access modifiers changed from: package-private */
    public boolean errorIsDisplayed() {
        return isCaptionStateError(this.captionDisplayed);
    }

    /* access modifiers changed from: package-private */
    public boolean errorShouldBeShown() {
        return isCaptionStateError(this.captionToShow);
    }

    private boolean isCaptionStateError(int captionState) {
        if (captionState != 1 || this.errorView == null || TextUtils.isEmpty(this.errorText)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean helperTextIsDisplayed() {
        return isCaptionStateHelperText(this.captionDisplayed);
    }

    /* access modifiers changed from: package-private */
    public boolean helperTextShouldBeShown() {
        return isCaptionStateHelperText(this.captionToShow);
    }

    private boolean isCaptionStateHelperText(int captionState) {
        return captionState == 2 && this.helperTextView != null && !TextUtils.isEmpty(this.helperText);
    }

    /* access modifiers changed from: package-private */
    public CharSequence getErrorText() {
        return this.errorText;
    }

    /* access modifiers changed from: package-private */
    public CharSequence getHelperText() {
        return this.helperText;
    }

    /* access modifiers changed from: package-private */
    public void setTypefaces(Typeface typeface2) {
        if (typeface2 != this.typeface) {
            this.typeface = typeface2;
            setTextViewTypeface(this.errorView, typeface2);
            setTextViewTypeface(this.helperTextView, typeface2);
        }
    }

    private void setTextViewTypeface(TextView captionView, Typeface typeface2) {
        if (captionView != null) {
            captionView.setTypeface(typeface2);
        }
    }

    /* access modifiers changed from: package-private */
    public int getErrorViewCurrentTextColor() {
        TextView textView = this.errorView;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getErrorViewTextColors() {
        TextView textView = this.errorView;
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setErrorViewTextColor(ColorStateList errorViewTextColor2) {
        this.errorViewTextColor = errorViewTextColor2;
        TextView textView = this.errorView;
        if (textView != null && errorViewTextColor2 != null) {
            textView.setTextColor(errorViewTextColor2);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorTextAppearance(int resId) {
        this.errorTextAppearance = resId;
        TextView textView = this.errorView;
        if (textView != null) {
            this.textInputView.setTextAppearanceCompatWithErrorFallback(textView, resId);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorContentDescription(CharSequence errorContentDescription) {
        this.errorViewContentDescription = errorContentDescription;
        TextView textView = this.errorView;
        if (textView != null) {
            textView.setContentDescription(errorContentDescription);
        }
    }

    /* access modifiers changed from: package-private */
    public void setErrorAccessibilityLiveRegion(int accessibilityLiveRegion) {
        this.errorViewAccessibilityLiveRegion = accessibilityLiveRegion;
        TextView textView = this.errorView;
        if (textView != null) {
            ViewCompat.setAccessibilityLiveRegion(textView, accessibilityLiveRegion);
        }
    }

    /* access modifiers changed from: package-private */
    public CharSequence getErrorContentDescription() {
        return this.errorViewContentDescription;
    }

    /* access modifiers changed from: package-private */
    public int getErrorAccessibilityLiveRegion() {
        return this.errorViewAccessibilityLiveRegion;
    }

    /* access modifiers changed from: package-private */
    public int getHelperTextViewCurrentTextColor() {
        TextView textView = this.helperTextView;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getHelperTextViewColors() {
        TextView textView = this.helperTextView;
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setHelperTextViewTextColor(ColorStateList helperTextViewTextColor2) {
        this.helperTextViewTextColor = helperTextViewTextColor2;
        TextView textView = this.helperTextView;
        if (textView != null && helperTextViewTextColor2 != null) {
            textView.setTextColor(helperTextViewTextColor2);
        }
    }

    /* access modifiers changed from: package-private */
    public void setHelperTextAppearance(int resId) {
        this.helperTextTextAppearance = resId;
        TextView textView = this.helperTextView;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, resId);
        }
    }
}
