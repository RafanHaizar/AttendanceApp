package com.google.android.material.timepicker;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.timepicker.ClockHandView;
import java.util.Locale;

class TimePickerView extends ConstraintLayout implements TimePickerControls {
    static final String GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME = "android.view.View";
    private final ClockFaceView clockFace;
    private final ClockHandView clockHandView;
    private final Chip hourView;
    private final Chip minuteView;
    /* access modifiers changed from: private */
    public OnDoubleTapListener onDoubleTapListener;
    private OnPeriodChangeListener onPeriodChangeListener;
    /* access modifiers changed from: private */
    public OnSelectionChange onSelectionChangeListener;
    private final View.OnClickListener selectionListener;
    private final MaterialButtonToggleGroup toggle;

    interface OnDoubleTapListener {
        void onDoubleTap();
    }

    interface OnPeriodChangeListener {
        void onPeriodChange(int i);
    }

    interface OnSelectionChange {
        void onSelectionChanged(int i);
    }

    public TimePickerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TimePickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.selectionListener = new View.OnClickListener() {
            public void onClick(View v) {
                if (TimePickerView.this.onSelectionChangeListener != null) {
                    TimePickerView.this.onSelectionChangeListener.onSelectionChanged(((Integer) v.getTag(C1087R.C1090id.selection_type)).intValue());
                }
            }
        };
        LayoutInflater.from(context).inflate(C1087R.C1092layout.material_timepicker, this);
        this.clockFace = (ClockFaceView) findViewById(C1087R.C1090id.material_clock_face);
        MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) findViewById(C1087R.C1090id.material_clock_period_toggle);
        this.toggle = materialButtonToggleGroup;
        materialButtonToggleGroup.addOnButtonCheckedListener(new TimePickerView$$ExternalSyntheticLambda0(this));
        this.minuteView = (Chip) findViewById(C1087R.C1090id.material_minute_tv);
        this.hourView = (Chip) findViewById(C1087R.C1090id.material_hour_tv);
        this.clockHandView = (ClockHandView) findViewById(C1087R.C1090id.material_clock_hand);
        setupDoubleTap();
        setUpDisplay();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-android-material-timepicker-TimePickerView */
    public /* synthetic */ void mo24733x9f44237d(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if (isChecked && this.onPeriodChangeListener != null) {
            this.onPeriodChangeListener.onPeriodChange(checkedId == C1087R.C1090id.material_clock_period_pm_button ? 1 : 0);
        }
    }

    private void setupDoubleTap() {
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            public boolean onDoubleTap(MotionEvent e) {
                OnDoubleTapListener listener = TimePickerView.this.onDoubleTapListener;
                if (listener == null) {
                    return false;
                }
                listener.onDoubleTap();
                return true;
            }
        });
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (((Checkable) v).isChecked()) {
                    return gestureDetector.onTouchEvent(event);
                }
                return false;
            }
        };
        this.minuteView.setOnTouchListener(onTouchListener);
        this.hourView.setOnTouchListener(onTouchListener);
    }

    public void setMinuteHourDelegate(AccessibilityDelegateCompat clickActionDelegate) {
        ViewCompat.setAccessibilityDelegate(this.hourView, clickActionDelegate);
    }

    public void setHourClickDelegate(AccessibilityDelegateCompat clickActionDelegate) {
        ViewCompat.setAccessibilityDelegate(this.minuteView, clickActionDelegate);
    }

    private void setUpDisplay() {
        this.minuteView.setTag(C1087R.C1090id.selection_type, 12);
        this.hourView.setTag(C1087R.C1090id.selection_type, 10);
        this.minuteView.setOnClickListener(this.selectionListener);
        this.hourView.setOnClickListener(this.selectionListener);
        this.minuteView.setAccessibilityClassName(GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME);
        this.hourView.setAccessibilityClassName(GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME);
    }

    public void setValues(String[] values, int contentDescription) {
        this.clockFace.setValues(values, contentDescription);
    }

    public void setHandRotation(float rotation) {
        this.clockHandView.setHandRotation(rotation);
    }

    public void setHandRotation(float rotation, boolean animate) {
        this.clockHandView.setHandRotation(rotation, animate);
    }

    public void setAnimateOnTouchUp(boolean animating) {
        this.clockHandView.setAnimateOnTouchUp(animating);
    }

    public void updateTime(int period, int hourOfDay, int minute) {
        int checkedId;
        if (period == 1) {
            checkedId = C1087R.C1090id.material_clock_period_pm_button;
        } else {
            checkedId = C1087R.C1090id.material_clock_period_am_button;
        }
        this.toggle.check(checkedId);
        Locale current = getResources().getConfiguration().locale;
        String minuteFormatted = String.format(current, TimeModel.ZERO_LEADING_NUMBER_FORMAT, new Object[]{Integer.valueOf(minute)});
        String hourFormatted = String.format(current, TimeModel.ZERO_LEADING_NUMBER_FORMAT, new Object[]{Integer.valueOf(hourOfDay)});
        if (!TextUtils.equals(this.minuteView.getText(), minuteFormatted)) {
            this.minuteView.setText(minuteFormatted);
        }
        if (!TextUtils.equals(this.hourView.getText(), hourFormatted)) {
            this.hourView.setText(hourFormatted);
        }
    }

    public void setActiveSelection(int selection) {
        boolean z = true;
        updateSelection(this.minuteView, selection == 12);
        Chip chip = this.hourView;
        if (selection != 10) {
            z = false;
        }
        updateSelection(chip, z);
    }

    private void updateSelection(Chip chip, boolean isSelected) {
        int i;
        chip.setChecked(isSelected);
        if (isSelected) {
            i = 2;
        } else {
            i = 0;
        }
        ViewCompat.setAccessibilityLiveRegion(chip, i);
    }

    public void addOnRotateListener(ClockHandView.OnRotateListener onRotateListener) {
        this.clockHandView.addOnRotateListener(onRotateListener);
    }

    public void setOnActionUpListener(ClockHandView.OnActionUpListener onActionUpListener) {
        this.clockHandView.setOnActionUpListener(onActionUpListener);
    }

    /* access modifiers changed from: package-private */
    public void setOnPeriodChangeListener(OnPeriodChangeListener onPeriodChangeListener2) {
        this.onPeriodChangeListener = onPeriodChangeListener2;
    }

    /* access modifiers changed from: package-private */
    public void setOnSelectionChangeListener(OnSelectionChange onSelectionChangeListener2) {
        this.onSelectionChangeListener = onSelectionChangeListener2;
    }

    /* access modifiers changed from: package-private */
    public void setOnDoubleTapListener(OnDoubleTapListener listener) {
        this.onDoubleTapListener = listener;
    }

    public void showToggle() {
        this.toggle.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this && visibility == 0) {
            this.hourView.sendAccessibilityEvent(8);
        }
    }

    /* access modifiers changed from: package-private */
    public int getCurrentLevel() {
        return this.clockFace.getCurrentLevel();
    }

    /* access modifiers changed from: package-private */
    public void setCurrentLevel(int level) {
        this.clockFace.setCurrentLevel(level);
    }
}
