package com.google.android.material.timepicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.math.MathUtils;
import com.google.android.material.motion.MotionUtils;
import java.util.ArrayList;
import java.util.List;

class ClockHandView extends View {
    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private boolean animatingOnTouchUp;
    private final int animationDuration;
    private final TimeInterpolator animationInterpolator;
    private final float centerDotRadius;
    private boolean changedDuringTouch;
    private int circleRadius;
    private int currentLevel;
    private double degRad;
    private float downX;
    private float downY;
    private boolean isInTapRegion;
    private boolean isMultiLevel;
    private final List<OnRotateListener> listeners;
    private OnActionUpListener onActionUpListener;
    private float originalDeg;
    private final Paint paint;
    private final ValueAnimator rotationAnimator;
    private final int scaledTouchSlop;
    private final RectF selectorBox;
    private final int selectorRadius;
    private final int selectorStrokeWidth;

    public interface OnActionUpListener {
        void onActionUp(float f, boolean z);
    }

    public interface OnRotateListener {
        void onRotate(float f, boolean z);
    }

    public ClockHandView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClockHandView(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.materialClockStyle);
    }

    public ClockHandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.rotationAnimator = new ValueAnimator();
        this.listeners = new ArrayList();
        Paint paint2 = new Paint();
        this.paint = paint2;
        this.selectorBox = new RectF();
        this.currentLevel = 1;
        TypedArray a = context.obtainStyledAttributes(attrs, C1087R.styleable.ClockHandView, defStyleAttr, C1087R.C1093style.Widget_MaterialComponents_TimePicker_Clock);
        this.animationDuration = MotionUtils.resolveThemeDuration(context, C1087R.attr.motionDurationLong2, 200);
        this.animationInterpolator = MotionUtils.resolveThemeInterpolator(context, C1087R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.circleRadius = a.getDimensionPixelSize(C1087R.styleable.ClockHandView_materialCircleRadius, 0);
        this.selectorRadius = a.getDimensionPixelSize(C1087R.styleable.ClockHandView_selectorSize, 0);
        Resources res = getResources();
        this.selectorStrokeWidth = res.getDimensionPixelSize(C1087R.dimen.material_clock_hand_stroke_width);
        this.centerDotRadius = (float) res.getDimensionPixelSize(C1087R.dimen.material_clock_hand_center_dot_radius);
        int selectorColor = a.getColor(C1087R.styleable.ClockHandView_clockHandColor, 0);
        paint2.setAntiAlias(true);
        paint2.setColor(selectorColor);
        setHandRotation(0.0f);
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        ViewCompat.setImportantForAccessibility(this, 2);
        a.recycle();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!this.rotationAnimator.isRunning()) {
            setHandRotation(getHandRotation());
        }
    }

    public void setHandRotation(float degrees) {
        setHandRotation(degrees, false);
    }

    public void setHandRotation(float degrees, boolean animate) {
        ValueAnimator valueAnimator = this.rotationAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (!animate) {
            setHandRotationInternal(degrees, false);
            return;
        }
        Pair<Float, Float> animationValues = getValuesForAnimation(degrees);
        this.rotationAnimator.setFloatValues(new float[]{((Float) animationValues.first).floatValue(), ((Float) animationValues.second).floatValue()});
        this.rotationAnimator.setDuration((long) this.animationDuration);
        this.rotationAnimator.setInterpolator(this.animationInterpolator);
        this.rotationAnimator.addUpdateListener(new ClockHandView$$ExternalSyntheticLambda0(this));
        this.rotationAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationCancel(Animator animation) {
                animation.end();
            }
        });
        this.rotationAnimator.start();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setHandRotation$0$com-google-android-material-timepicker-ClockHandView */
    public /* synthetic */ void mo24639xb17f7076(ValueAnimator animation) {
        setHandRotationInternal(((Float) animation.getAnimatedValue()).floatValue(), true);
    }

    private Pair<Float, Float> getValuesForAnimation(float degrees) {
        float currentDegrees = getHandRotation();
        if (Math.abs(currentDegrees - degrees) > 180.0f) {
            if (currentDegrees > 180.0f && degrees < 180.0f) {
                degrees += 360.0f;
            }
            if (currentDegrees < 180.0f && degrees > 180.0f) {
                currentDegrees += 360.0f;
            }
        }
        return new Pair<>(Float.valueOf(currentDegrees), Float.valueOf(degrees));
    }

    private void setHandRotationInternal(float degrees, boolean animate) {
        float degrees2 = degrees % 360.0f;
        this.originalDeg = degrees2;
        this.degRad = Math.toRadians((double) (degrees2 - 90.0f));
        int leveledCircleRadius = getLeveledCircleRadius(this.currentLevel);
        float selCenterX = ((float) (getWidth() / 2)) + (((float) leveledCircleRadius) * ((float) Math.cos(this.degRad)));
        float selCenterY = ((float) (getHeight() / 2)) + (((float) leveledCircleRadius) * ((float) Math.sin(this.degRad)));
        RectF rectF = this.selectorBox;
        int i = this.selectorRadius;
        rectF.set(selCenterX - ((float) i), selCenterY - ((float) i), ((float) i) + selCenterX, ((float) i) + selCenterY);
        for (OnRotateListener listener : this.listeners) {
            listener.onRotate(degrees2, animate);
        }
        invalidate();
    }

    public void setAnimateOnTouchUp(boolean animating) {
        this.animatingOnTouchUp = animating;
    }

    public void addOnRotateListener(OnRotateListener listener) {
        this.listeners.add(listener);
    }

    public void setOnActionUpListener(OnActionUpListener listener) {
        this.onActionUpListener = listener;
    }

    public float getHandRotation() {
        return this.originalDeg;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSelector(canvas);
    }

    private void drawSelector(Canvas canvas) {
        Canvas canvas2 = canvas;
        int yCenter = getHeight() / 2;
        int xCenter = getWidth() / 2;
        int leveledCircleRadius = getLeveledCircleRadius(this.currentLevel);
        float selCenterX = ((float) xCenter) + (((float) leveledCircleRadius) * ((float) Math.cos(this.degRad)));
        float selCenterY = ((float) yCenter) + (((float) leveledCircleRadius) * ((float) Math.sin(this.degRad)));
        this.paint.setStrokeWidth(0.0f);
        canvas2.drawCircle(selCenterX, selCenterY, (float) this.selectorRadius, this.paint);
        double sin = Math.sin(this.degRad);
        double cos = Math.cos(this.degRad);
        float lineLength = (float) (leveledCircleRadius - this.selectorRadius);
        double d = (double) lineLength;
        Double.isNaN(d);
        float linePointX = (float) (((int) (d * cos)) + xCenter);
        double d2 = (double) lineLength;
        Double.isNaN(d2);
        float linePointY = (float) (((int) (d2 * sin)) + yCenter);
        this.paint.setStrokeWidth((float) this.selectorStrokeWidth);
        Canvas canvas3 = canvas;
        float linePointY2 = linePointY;
        float f = linePointX;
        float f2 = lineLength;
        canvas3.drawLine((float) xCenter, (float) yCenter, linePointX, linePointY2, this.paint);
        canvas2.drawCircle((float) xCenter, (float) yCenter, this.centerDotRadius, this.paint);
    }

    public RectF getCurrentSelectorBox() {
        return this.selectorBox;
    }

    public int getSelectorRadius() {
        return this.selectorRadius;
    }

    public void setCircleRadius(int circleRadius2) {
        this.circleRadius = circleRadius2;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        OnActionUpListener onActionUpListener2;
        int action = event.getActionMasked();
        boolean forceSelection = false;
        boolean actionDown = false;
        boolean actionUp = false;
        float x = event.getX();
        float y = event.getY();
        boolean z = false;
        switch (action) {
            case 0:
                this.downX = x;
                this.downY = y;
                this.isInTapRegion = true;
                this.changedDuringTouch = false;
                actionDown = true;
                break;
            case 1:
            case 2:
                int deltaX = (int) (x - this.downX);
                int deltaY = (int) (y - this.downY);
                this.isInTapRegion = (deltaX * deltaX) + (deltaY * deltaY) > this.scaledTouchSlop;
                if (this.changedDuringTouch) {
                    forceSelection = true;
                }
                if (action == 1) {
                    z = true;
                }
                actionUp = z;
                if (this.isMultiLevel) {
                    adjustLevel(x, y);
                    break;
                }
                break;
        }
        boolean handleTouchInput = handleTouchInput(x, y, forceSelection, actionDown, actionUp) | this.changedDuringTouch;
        this.changedDuringTouch = handleTouchInput;
        if (handleTouchInput && actionUp && (onActionUpListener2 = this.onActionUpListener) != null) {
            onActionUpListener2.onActionUp((float) getDegreesFromXY(x, y), this.isInTapRegion);
        }
        return true;
    }

    private void adjustLevel(float x, float y) {
        int i = 2;
        if (MathUtils.dist((float) (getWidth() / 2), (float) (getHeight() / 2), x, y) > ((float) getLeveledCircleRadius(2)) + ViewUtils.dpToPx(getContext(), 12)) {
            i = 1;
        }
        this.currentLevel = i;
    }

    private boolean handleTouchInput(float x, float y, boolean forceSelection, boolean touchDown, boolean actionUp) {
        int degrees = getDegreesFromXY(x, y);
        boolean z = false;
        boolean valueChanged = getHandRotation() != ((float) degrees);
        if (touchDown && valueChanged) {
            return true;
        }
        if (!valueChanged && !forceSelection) {
            return false;
        }
        float f = (float) degrees;
        if (actionUp && this.animatingOnTouchUp) {
            z = true;
        }
        setHandRotation(f, z);
        return true;
    }

    private int getDegreesFromXY(float x, float y) {
        int degrees = ((int) Math.toDegrees(Math.atan2((double) (y - ((float) (getHeight() / 2))), (double) (x - ((float) (getWidth() / 2)))))) + 90;
        if (degrees < 0) {
            return degrees + 360;
        }
        return degrees;
    }

    /* access modifiers changed from: package-private */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /* access modifiers changed from: package-private */
    public void setCurrentLevel(int level) {
        this.currentLevel = level;
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void setMultiLevel(boolean isMultiLevel2) {
        if (this.isMultiLevel && !isMultiLevel2) {
            this.currentLevel = 1;
        }
        this.isMultiLevel = isMultiLevel2;
        invalidate();
    }

    private int getLeveledCircleRadius(int level) {
        return level == 2 ? Math.round(((float) this.circleRadius) * 0.66f) : this.circleRadius;
    }
}
