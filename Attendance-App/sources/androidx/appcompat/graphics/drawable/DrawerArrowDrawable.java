package androidx.appcompat.graphics.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.C0503R;
import androidx.core.graphics.drawable.DrawableCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawerArrowDrawable extends Drawable {
    public static final int ARROW_DIRECTION_END = 3;
    public static final int ARROW_DIRECTION_LEFT = 0;
    public static final int ARROW_DIRECTION_RIGHT = 1;
    public static final int ARROW_DIRECTION_START = 2;
    private static final float ARROW_HEAD_ANGLE = ((float) Math.toRadians(45.0d));
    private float mArrowHeadLength;
    private float mArrowShaftLength;
    private float mBarGap;
    private float mBarLength;
    private int mDirection = 2;
    private float mMaxCutForBarSize;
    private final Paint mPaint;
    private final Path mPath = new Path();
    private float mProgress;
    private final int mSize;
    private boolean mSpin;
    private boolean mVerticalMirror = false;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrowDirection {
    }

    public DrawerArrowDrawable(Context context) {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        TypedArray a = context.getTheme().obtainStyledAttributes((AttributeSet) null, C0503R.styleable.DrawerArrowToggle, C0503R.attr.drawerArrowStyle, C0503R.C0509style.Base_Widget_AppCompat_DrawerArrowToggle);
        setColor(a.getColor(C0503R.styleable.DrawerArrowToggle_color, 0));
        setBarThickness(a.getDimension(C0503R.styleable.DrawerArrowToggle_thickness, 0.0f));
        setSpinEnabled(a.getBoolean(C0503R.styleable.DrawerArrowToggle_spinBars, true));
        setGapSize((float) Math.round(a.getDimension(C0503R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0f)));
        this.mSize = a.getDimensionPixelSize(C0503R.styleable.DrawerArrowToggle_drawableSize, 0);
        this.mBarLength = (float) Math.round(a.getDimension(C0503R.styleable.DrawerArrowToggle_barLength, 0.0f));
        this.mArrowHeadLength = (float) Math.round(a.getDimension(C0503R.styleable.DrawerArrowToggle_arrowHeadLength, 0.0f));
        this.mArrowShaftLength = a.getDimension(C0503R.styleable.DrawerArrowToggle_arrowShaftLength, 0.0f);
        a.recycle();
    }

    public void setArrowHeadLength(float length) {
        if (this.mArrowHeadLength != length) {
            this.mArrowHeadLength = length;
            invalidateSelf();
        }
    }

    public float getArrowHeadLength() {
        return this.mArrowHeadLength;
    }

    public void setArrowShaftLength(float length) {
        if (this.mArrowShaftLength != length) {
            this.mArrowShaftLength = length;
            invalidateSelf();
        }
    }

    public float getArrowShaftLength() {
        return this.mArrowShaftLength;
    }

    public float getBarLength() {
        return this.mBarLength;
    }

    public void setBarLength(float length) {
        if (this.mBarLength != length) {
            this.mBarLength = length;
            invalidateSelf();
        }
    }

    public void setColor(int color) {
        if (color != this.mPaint.getColor()) {
            this.mPaint.setColor(color);
            invalidateSelf();
        }
    }

    public int getColor() {
        return this.mPaint.getColor();
    }

    public void setBarThickness(float width) {
        if (this.mPaint.getStrokeWidth() != width) {
            this.mPaint.setStrokeWidth(width);
            double d = (double) (width / 2.0f);
            double cos = Math.cos((double) ARROW_HEAD_ANGLE);
            Double.isNaN(d);
            this.mMaxCutForBarSize = (float) (d * cos);
            invalidateSelf();
        }
    }

    public float getBarThickness() {
        return this.mPaint.getStrokeWidth();
    }

    public float getGapSize() {
        return this.mBarGap;
    }

    public void setGapSize(float gap) {
        if (gap != this.mBarGap) {
            this.mBarGap = gap;
            invalidateSelf();
        }
    }

    public void setDirection(int direction) {
        if (direction != this.mDirection) {
            this.mDirection = direction;
            invalidateSelf();
        }
    }

    public boolean isSpinEnabled() {
        return this.mSpin;
    }

    public void setSpinEnabled(boolean enabled) {
        if (this.mSpin != enabled) {
            this.mSpin = enabled;
            invalidateSelf();
        }
    }

    public int getDirection() {
        return this.mDirection;
    }

    public void setVerticalMirror(boolean verticalMirror) {
        if (this.mVerticalMirror != verticalMirror) {
            this.mVerticalMirror = verticalMirror;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        boolean flipToPointRight;
        Canvas canvas2 = canvas;
        Rect bounds = getBounds();
        boolean z = false;
        switch (this.mDirection) {
            case 0:
                flipToPointRight = false;
                break;
            case 1:
                flipToPointRight = true;
                break;
            case 3:
                if (DrawableCompat.getLayoutDirection(this) == 0) {
                    z = true;
                }
                flipToPointRight = z;
                break;
            default:
                if (DrawableCompat.getLayoutDirection(this) == 1) {
                    z = true;
                }
                flipToPointRight = z;
                break;
        }
        float f = this.mArrowHeadLength;
        float arrowHeadBarLength = lerp(this.mBarLength, (float) Math.sqrt((double) (f * f * 2.0f)), this.mProgress);
        float arrowShaftLength = lerp(this.mBarLength, this.mArrowShaftLength, this.mProgress);
        float arrowShaftCut = (float) Math.round(lerp(0.0f, this.mMaxCutForBarSize, this.mProgress));
        float rotation = lerp(0.0f, ARROW_HEAD_ANGLE, this.mProgress);
        double d = (double) arrowHeadBarLength;
        float canvasRotate = lerp(flipToPointRight ? 0.0f : -180.0f, flipToPointRight ? 180.0f : 0.0f, this.mProgress);
        double cos = Math.cos((double) rotation);
        Double.isNaN(d);
        float arrowWidth = (float) Math.round(d * cos);
        double d2 = (double) arrowHeadBarLength;
        double sin = Math.sin((double) rotation);
        Double.isNaN(d2);
        float arrowHeight = (float) Math.round(d2 * sin);
        this.mPath.rewind();
        float topBottomBarOffset = lerp(this.mBarGap + this.mPaint.getStrokeWidth(), -this.mMaxCutForBarSize, this.mProgress);
        float arrowEdge = (-arrowShaftLength) / 2.0f;
        this.mPath.moveTo(arrowEdge + arrowShaftCut, 0.0f);
        this.mPath.rLineTo(arrowShaftLength - (arrowShaftCut * 2.0f), 0.0f);
        this.mPath.moveTo(arrowEdge, topBottomBarOffset);
        this.mPath.rLineTo(arrowWidth, arrowHeight);
        this.mPath.moveTo(arrowEdge, -topBottomBarOffset);
        this.mPath.rLineTo(arrowWidth, -arrowHeight);
        this.mPath.close();
        canvas.save();
        float barThickness = this.mPaint.getStrokeWidth();
        float height = ((float) bounds.height()) - (3.0f * barThickness);
        float f2 = this.mBarGap;
        canvas2.translate((float) bounds.centerX(), ((float) ((((int) (height - (2.0f * f2))) / 4) * 2)) + (1.5f * barThickness) + f2);
        if (this.mSpin) {
            canvas2.rotate(((float) (this.mVerticalMirror ^ flipToPointRight ? -1 : 1)) * canvasRotate);
        } else if (flipToPointRight) {
            canvas2.rotate(180.0f);
        }
        Rect rect = bounds;
        canvas2.drawPath(this.mPath, this.mPaint);
        canvas.restore();
    }

    public void setAlpha(int alpha) {
        if (alpha != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public int getIntrinsicHeight() {
        return this.mSize;
    }

    public int getIntrinsicWidth() {
        return this.mSize;
    }

    public int getOpacity() {
        return -3;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public void setProgress(float progress) {
        if (this.mProgress != progress) {
            this.mProgress = progress;
            invalidateSelf();
        }
    }

    public final Paint getPaint() {
        return this.mPaint;
    }

    private static float lerp(float a, float b, float t) {
        return ((b - a) * t) + a;
    }
}
