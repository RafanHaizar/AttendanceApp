package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import org.xmlpull.v1.XmlPullParser;

public class ArcMotion extends PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = ((float) Math.tan(Math.toRadians(35.0d)));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
    private float mMaximumTangent = DEFAULT_MAX_TANGENT;
    private float mMinimumHorizontalAngle = 0.0f;
    private float mMinimumHorizontalTangent = 0.0f;
    private float mMinimumVerticalAngle = 0.0f;
    private float mMinimumVerticalTangent = 0.0f;

    public ArcMotion() {
    }

    public ArcMotion(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.ARC_MOTION);
        XmlPullParser parser = (XmlPullParser) attrs;
        setMinimumVerticalAngle(TypedArrayUtils.getNamedFloat(a, parser, "minimumVerticalAngle", 1, 0.0f));
        setMinimumHorizontalAngle(TypedArrayUtils.getNamedFloat(a, parser, "minimumHorizontalAngle", 0, 0.0f));
        setMaximumAngle(TypedArrayUtils.getNamedFloat(a, parser, "maximumAngle", 2, DEFAULT_MAX_ANGLE_DEGREES));
        a.recycle();
    }

    public void setMinimumHorizontalAngle(float angleInDegrees) {
        this.mMinimumHorizontalAngle = angleInDegrees;
        this.mMinimumHorizontalTangent = toTangent(angleInDegrees);
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public void setMinimumVerticalAngle(float angleInDegrees) {
        this.mMinimumVerticalAngle = angleInDegrees;
        this.mMinimumVerticalTangent = toTangent(angleInDegrees);
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    public void setMaximumAngle(float angleInDegrees) {
        this.mMaximumAngle = angleInDegrees;
        this.mMaximumTangent = toTangent(angleInDegrees);
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    private static float toTangent(float arcInDegrees) {
        if (arcInDegrees >= 0.0f && arcInDegrees <= 90.0f) {
            return (float) Math.tan(Math.toRadians((double) (arcInDegrees / 2.0f)));
        }
        throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
    }

    public Path getPath(float startX, float startY, float endX, float endY) {
        float minimumArcDist2;
        float ex;
        float ey;
        float newArcDistance2;
        float ex2;
        float ey2;
        float ex3;
        float ey3;
        float f = startX;
        float f2 = startY;
        Path path = new Path();
        path.moveTo(f, f2);
        float deltaX = endX - f;
        float deltaY = endY - f2;
        float h2 = (deltaX * deltaX) + (deltaY * deltaY);
        float dx = (f + endX) / 2.0f;
        float dy = (f2 + endY) / 2.0f;
        float midDist2 = h2 * 0.25f;
        boolean isMovingUpwards = f2 > endY;
        if (Math.abs(deltaX) < Math.abs(deltaY)) {
            float eDistY = Math.abs(h2 / (deltaY * 2.0f));
            if (isMovingUpwards) {
                ey = endY + eDistY;
                ex = endX;
            } else {
                ey = f2 + eDistY;
                ex = startX;
            }
            float f3 = this.mMinimumVerticalTangent;
            minimumArcDist2 = midDist2 * f3 * f3;
        } else {
            float eDistX = h2 / (deltaX * 2.0f);
            if (isMovingUpwards) {
                ex3 = f + eDistX;
                ey3 = startY;
            } else {
                ex3 = endX - eDistX;
                ey3 = endY;
            }
            float f4 = this.mMinimumHorizontalTangent;
            minimumArcDist2 = midDist2 * f4 * f4;
        }
        float arcDistX = dx - ex;
        float arcDistY = dy - ey;
        float arcDist2 = (arcDistX * arcDistX) + (arcDistY * arcDistY);
        float f5 = this.mMaximumTangent;
        float maximumArcDist2 = midDist2 * f5 * f5;
        if (arcDist2 < minimumArcDist2) {
            newArcDistance2 = minimumArcDist2;
        } else if (arcDist2 > maximumArcDist2) {
            newArcDistance2 = maximumArcDist2;
        } else {
            newArcDistance2 = 0.0f;
        }
        if (newArcDistance2 != 0.0f) {
            float ratio = (float) Math.sqrt((double) (newArcDistance2 / arcDist2));
            ey2 = dy + ((ey - dy) * ratio);
            ex2 = dx + ((ex - dx) * ratio);
        } else {
            ey2 = ey;
            ex2 = ex;
        }
        path.cubicTo((f + ex2) / 2.0f, (f2 + ey2) / 2.0f, (ex2 + endX) / 2.0f, (ey2 + endY) / 2.0f, endX, endY);
        return path;
    }
}
