package com.google.android.material.slider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.android.tools.r8.annotations.SynthesizedClassV2;
import com.google.android.material.C1087R;
import com.google.android.material.internal.ThemeEnforcement;
import java.util.ArrayList;
import java.util.List;

public class RangeSlider extends BaseSlider<RangeSlider, OnChangeListener, OnSliderTouchListener> {
    private float minSeparation;
    private int separationUnit;

    public interface OnChangeListener extends BaseOnChangeListener<RangeSlider> {

        @SynthesizedClassV2(kind = 8, versionHash = "ea87655719898b9807d7a88878e9de051d12af172d2fab563c9881b5e404e7d4")
        /* renamed from: com.google.android.material.slider.RangeSlider$OnChangeListener$-CC  reason: invalid class name */
        public final /* synthetic */ class CC {
        }

        void onValueChange(RangeSlider rangeSlider, float f, boolean z);
    }

    public interface OnSliderTouchListener extends BaseOnSliderTouchListener<RangeSlider> {

        @SynthesizedClassV2(kind = 8, versionHash = "ea87655719898b9807d7a88878e9de051d12af172d2fab563c9881b5e404e7d4")
        /* renamed from: com.google.android.material.slider.RangeSlider$OnSliderTouchListener$-CC  reason: invalid class name */
        public final /* synthetic */ class CC {
        }

        void onStartTrackingTouch(RangeSlider rangeSlider);

        void onStopTrackingTouch(RangeSlider rangeSlider);
    }

    public /* bridge */ /* synthetic */ void addOnChangeListener(BaseOnChangeListener baseOnChangeListener) {
        super.addOnChangeListener(baseOnChangeListener);
    }

    public /* bridge */ /* synthetic */ void addOnSliderTouchListener(BaseOnSliderTouchListener baseOnSliderTouchListener) {
        super.addOnSliderTouchListener(baseOnSliderTouchListener);
    }

    public /* bridge */ /* synthetic */ void clearOnChangeListeners() {
        super.clearOnChangeListeners();
    }

    public /* bridge */ /* synthetic */ void clearOnSliderTouchListeners() {
        super.clearOnSliderTouchListeners();
    }

    public /* bridge */ /* synthetic */ boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return super.dispatchHoverEvent(motionEvent);
    }

    public /* bridge */ /* synthetic */ boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public /* bridge */ /* synthetic */ CharSequence getAccessibilityClassName() {
        return super.getAccessibilityClassName();
    }

    public /* bridge */ /* synthetic */ int getActiveThumbIndex() {
        return super.getActiveThumbIndex();
    }

    public /* bridge */ /* synthetic */ int getFocusedThumbIndex() {
        return super.getFocusedThumbIndex();
    }

    public /* bridge */ /* synthetic */ int getHaloRadius() {
        return super.getHaloRadius();
    }

    public /* bridge */ /* synthetic */ ColorStateList getHaloTintList() {
        return super.getHaloTintList();
    }

    public /* bridge */ /* synthetic */ int getLabelBehavior() {
        return super.getLabelBehavior();
    }

    public /* bridge */ /* synthetic */ float getStepSize() {
        return super.getStepSize();
    }

    public /* bridge */ /* synthetic */ float getThumbElevation() {
        return super.getThumbElevation();
    }

    public /* bridge */ /* synthetic */ int getThumbRadius() {
        return super.getThumbRadius();
    }

    public /* bridge */ /* synthetic */ ColorStateList getThumbStrokeColor() {
        return super.getThumbStrokeColor();
    }

    public /* bridge */ /* synthetic */ float getThumbStrokeWidth() {
        return super.getThumbStrokeWidth();
    }

    public /* bridge */ /* synthetic */ ColorStateList getThumbTintList() {
        return super.getThumbTintList();
    }

    public /* bridge */ /* synthetic */ ColorStateList getTickActiveTintList() {
        return super.getTickActiveTintList();
    }

    public /* bridge */ /* synthetic */ ColorStateList getTickInactiveTintList() {
        return super.getTickInactiveTintList();
    }

    public /* bridge */ /* synthetic */ ColorStateList getTickTintList() {
        return super.getTickTintList();
    }

    public /* bridge */ /* synthetic */ ColorStateList getTrackActiveTintList() {
        return super.getTrackActiveTintList();
    }

    public /* bridge */ /* synthetic */ int getTrackHeight() {
        return super.getTrackHeight();
    }

    public /* bridge */ /* synthetic */ ColorStateList getTrackInactiveTintList() {
        return super.getTrackInactiveTintList();
    }

    public /* bridge */ /* synthetic */ int getTrackSidePadding() {
        return super.getTrackSidePadding();
    }

    public /* bridge */ /* synthetic */ ColorStateList getTrackTintList() {
        return super.getTrackTintList();
    }

    public /* bridge */ /* synthetic */ int getTrackWidth() {
        return super.getTrackWidth();
    }

    public /* bridge */ /* synthetic */ float getValueFrom() {
        return super.getValueFrom();
    }

    public /* bridge */ /* synthetic */ float getValueTo() {
        return super.getValueTo();
    }

    public /* bridge */ /* synthetic */ boolean hasLabelFormatter() {
        return super.hasLabelFormatter();
    }

    public /* bridge */ /* synthetic */ boolean isTickVisible() {
        return super.isTickVisible();
    }

    public /* bridge */ /* synthetic */ boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public /* bridge */ /* synthetic */ boolean onKeyUp(int i, KeyEvent keyEvent) {
        return super.onKeyUp(i, keyEvent);
    }

    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public /* bridge */ /* synthetic */ void removeOnChangeListener(BaseOnChangeListener baseOnChangeListener) {
        super.removeOnChangeListener(baseOnChangeListener);
    }

    public /* bridge */ /* synthetic */ void removeOnSliderTouchListener(BaseOnSliderTouchListener baseOnSliderTouchListener) {
        super.removeOnSliderTouchListener(baseOnSliderTouchListener);
    }

    public /* bridge */ /* synthetic */ void setEnabled(boolean z) {
        super.setEnabled(z);
    }

    public /* bridge */ /* synthetic */ void setFocusedThumbIndex(int i) {
        super.setFocusedThumbIndex(i);
    }

    public /* bridge */ /* synthetic */ void setHaloRadius(int i) {
        super.setHaloRadius(i);
    }

    public /* bridge */ /* synthetic */ void setHaloRadiusResource(int i) {
        super.setHaloRadiusResource(i);
    }

    public /* bridge */ /* synthetic */ void setHaloTintList(ColorStateList colorStateList) {
        super.setHaloTintList(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setLabelBehavior(int i) {
        super.setLabelBehavior(i);
    }

    public /* bridge */ /* synthetic */ void setLabelFormatter(LabelFormatter labelFormatter) {
        super.setLabelFormatter(labelFormatter);
    }

    public /* bridge */ /* synthetic */ void setStepSize(float f) {
        super.setStepSize(f);
    }

    public /* bridge */ /* synthetic */ void setThumbElevation(float f) {
        super.setThumbElevation(f);
    }

    public /* bridge */ /* synthetic */ void setThumbElevationResource(int i) {
        super.setThumbElevationResource(i);
    }

    public /* bridge */ /* synthetic */ void setThumbRadius(int i) {
        super.setThumbRadius(i);
    }

    public /* bridge */ /* synthetic */ void setThumbRadiusResource(int i) {
        super.setThumbRadiusResource(i);
    }

    public /* bridge */ /* synthetic */ void setThumbStrokeColor(ColorStateList colorStateList) {
        super.setThumbStrokeColor(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setThumbStrokeColorResource(int i) {
        super.setThumbStrokeColorResource(i);
    }

    public /* bridge */ /* synthetic */ void setThumbStrokeWidth(float f) {
        super.setThumbStrokeWidth(f);
    }

    public /* bridge */ /* synthetic */ void setThumbStrokeWidthResource(int i) {
        super.setThumbStrokeWidthResource(i);
    }

    public /* bridge */ /* synthetic */ void setThumbTintList(ColorStateList colorStateList) {
        super.setThumbTintList(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setTickActiveTintList(ColorStateList colorStateList) {
        super.setTickActiveTintList(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setTickInactiveTintList(ColorStateList colorStateList) {
        super.setTickInactiveTintList(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setTickTintList(ColorStateList colorStateList) {
        super.setTickTintList(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setTickVisible(boolean z) {
        super.setTickVisible(z);
    }

    public /* bridge */ /* synthetic */ void setTrackActiveTintList(ColorStateList colorStateList) {
        super.setTrackActiveTintList(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setTrackHeight(int i) {
        super.setTrackHeight(i);
    }

    public /* bridge */ /* synthetic */ void setTrackInactiveTintList(ColorStateList colorStateList) {
        super.setTrackInactiveTintList(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setTrackTintList(ColorStateList colorStateList) {
        super.setTrackTintList(colorStateList);
    }

    public /* bridge */ /* synthetic */ void setValueFrom(float f) {
        super.setValueFrom(f);
    }

    public /* bridge */ /* synthetic */ void setValueTo(float f) {
        super.setValueTo(f);
    }

    public RangeSlider(Context context) {
        this(context, (AttributeSet) null);
    }

    public RangeSlider(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.sliderStyle);
    }

    public RangeSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.RangeSlider, defStyleAttr, DEF_STYLE_RES, new int[0]);
        if (a.hasValue(C1087R.styleable.RangeSlider_values)) {
            setValues(convertToFloat(a.getResources().obtainTypedArray(a.getResourceId(C1087R.styleable.RangeSlider_values, 0))));
        }
        this.minSeparation = a.getDimension(C1087R.styleable.RangeSlider_minSeparation, 0.0f);
        a.recycle();
    }

    public void setValues(Float... values) {
        super.setValues(values);
    }

    public void setValues(List<Float> values) {
        super.setValues(values);
    }

    public List<Float> getValues() {
        return super.getValues();
    }

    public void setCustomThumbDrawable(int drawableResId) {
        super.setCustomThumbDrawable(drawableResId);
    }

    public void setCustomThumbDrawable(Drawable drawable) {
        super.setCustomThumbDrawable(drawable);
    }

    public void setCustomThumbDrawablesForValues(int... drawableResIds) {
        super.setCustomThumbDrawablesForValues(drawableResIds);
    }

    public void setCustomThumbDrawablesForValues(Drawable... drawables) {
        super.setCustomThumbDrawablesForValues(drawables);
    }

    private static List<Float> convertToFloat(TypedArray values) {
        List<Float> ret = new ArrayList<>();
        for (int i = 0; i < values.length(); i++) {
            ret.add(Float.valueOf(values.getFloat(i, -1.0f)));
        }
        return ret;
    }

    public float getMinSeparation() {
        return this.minSeparation;
    }

    public void setMinSeparation(float minSeparation2) {
        this.minSeparation = minSeparation2;
        this.separationUnit = 0;
        setSeparationUnit(0);
    }

    public void setMinSeparationValue(float minSeparation2) {
        this.minSeparation = minSeparation2;
        this.separationUnit = 1;
        setSeparationUnit(1);
    }

    public Parcelable onSaveInstanceState() {
        RangeSliderState sliderState = new RangeSliderState(super.onSaveInstanceState());
        float unused = sliderState.minSeparation = this.minSeparation;
        int unused2 = sliderState.separationUnit = this.separationUnit;
        return sliderState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        RangeSliderState savedState = (RangeSliderState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.minSeparation = savedState.minSeparation;
        int access$100 = savedState.separationUnit;
        this.separationUnit = access$100;
        setSeparationUnit(access$100);
    }

    static class RangeSliderState extends AbsSavedState {
        public static final Parcelable.Creator<RangeSliderState> CREATOR = new Parcelable.Creator<RangeSliderState>() {
            public RangeSliderState createFromParcel(Parcel in) {
                return new RangeSliderState(in);
            }

            public RangeSliderState[] newArray(int size) {
                return new RangeSliderState[size];
            }
        };
        /* access modifiers changed from: private */
        public float minSeparation;
        /* access modifiers changed from: private */
        public int separationUnit;

        RangeSliderState(Parcelable superState) {
            super(superState);
        }

        private RangeSliderState(Parcel in) {
            super(in.readParcelable(RangeSliderState.class.getClassLoader()));
            this.minSeparation = in.readFloat();
            this.separationUnit = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.minSeparation);
            out.writeInt(this.separationUnit);
        }
    }
}
