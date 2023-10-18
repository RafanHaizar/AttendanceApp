package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.svg.SvgConstants;
import java.util.Arrays;
import java.util.HashMap;

public class MotionPaths implements Comparable<MotionPaths> {
    public static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    public static final int PERPENDICULAR = 1;
    public static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";
    static String[] names = {CommonCssConstants.POSITION, SvgConstants.Attributes.f1641X, SvgConstants.Attributes.f1644Y, "width", "height", "pathRotate"};
    HashMap<String, CustomVariable> customAttributes = new HashMap<>();
    float height;
    int mAnimateCircleAngleTo;
    int mAnimateRelativeTo = -1;
    int mDrawPath = 0;
    Easing mKeyFrameEasing;
    int mMode = 0;
    int mPathMotionArc = -1;
    float mPathRotate = Float.NaN;
    float mProgress = Float.NaN;
    float mRelativeAngle = Float.NaN;
    Motion mRelativeToController = null;
    double[] mTempDelta = new double[18];
    double[] mTempValue = new double[18];
    float position;
    float time;
    float width;

    /* renamed from: x */
    float f984x;

    /* renamed from: y */
    float f985y;

    public MotionPaths() {
    }

    /* access modifiers changed from: package-private */
    public void initCartesian(MotionKeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        MotionKeyPosition motionKeyPosition = c;
        MotionPaths motionPaths = startTimePoint;
        MotionPaths motionPaths2 = endTimePoint;
        float position2 = ((float) motionKeyPosition.mFramePosition) / 100.0f;
        this.time = position2;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float scaleWidth = Float.isNaN(motionKeyPosition.mPercentWidth) ? position2 : motionKeyPosition.mPercentWidth;
        float scaleHeight = Float.isNaN(motionKeyPosition.mPercentHeight) ? position2 : motionKeyPosition.mPercentHeight;
        float f = motionPaths2.width;
        float f2 = motionPaths.width;
        float scaleX = f - f2;
        float f3 = motionPaths2.height;
        float f4 = motionPaths.height;
        float scaleY = f3 - f4;
        this.position = this.time;
        float path = position2;
        float f5 = motionPaths.f984x;
        float position3 = position2;
        float position4 = motionPaths.f985y;
        float endCenterX = motionPaths2.f984x + (f / 2.0f);
        float endCenterY = motionPaths2.f985y + (f3 / 2.0f);
        float pathVectorX = endCenterX - (f5 + (f2 / 2.0f));
        float pathVectorY = endCenterY - (position4 + (f4 / 2.0f));
        this.f984x = (float) ((int) ((f5 + (pathVectorX * path)) - ((scaleX * scaleWidth) / 2.0f)));
        this.f985y = (float) ((int) ((position4 + (pathVectorY * path)) - ((scaleY * scaleHeight) / 2.0f)));
        this.width = (float) ((int) (f2 + (scaleX * scaleWidth)));
        this.height = (float) ((int) (f4 + (scaleY * scaleHeight)));
        float dxdx = Float.isNaN(motionKeyPosition.mPercentX) ? position3 : motionKeyPosition.mPercentX;
        float dydx = Float.isNaN(motionKeyPosition.mAltPercentY) ? 0.0f : motionKeyPosition.mAltPercentY;
        float dydy = Float.isNaN(motionKeyPosition.mPercentY) ? position3 : motionKeyPosition.mPercentY;
        float dxdy = Float.isNaN(motionKeyPosition.mAltPercentX) ? 0.0f : motionKeyPosition.mAltPercentX;
        this.mMode = 0;
        this.f984x = (float) ((int) (((motionPaths.f984x + (pathVectorX * dxdx)) + (pathVectorY * dxdy)) - ((scaleX * scaleWidth) / 2.0f)));
        this.f985y = (float) ((int) (((motionPaths.f985y + (pathVectorX * dydx)) + (pathVectorY * dydy)) - ((scaleY * scaleHeight) / 2.0f)));
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }

    public MotionPaths(int parentWidth, int parentHeight, MotionKeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        if (startTimePoint.mAnimateRelativeTo != -1) {
            initPolar(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
            return;
        }
        switch (c.mPositionType) {
            case 1:
                initPath(c, startTimePoint, endTimePoint);
                return;
            case 2:
                initScreen(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
                return;
            default:
                initCartesian(c, startTimePoint, endTimePoint);
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void initPolar(int parentWidth, int parentHeight, MotionKeyPosition c, MotionPaths s, MotionPaths e) {
        float f;
        float f2;
        float f3;
        float position2 = ((float) c.mFramePosition) / 100.0f;
        this.time = position2;
        this.mDrawPath = c.mDrawPath;
        this.mMode = c.mPositionType;
        float scaleWidth = Float.isNaN(c.mPercentWidth) ? position2 : c.mPercentWidth;
        float scaleHeight = Float.isNaN(c.mPercentHeight) ? position2 : c.mPercentHeight;
        float f4 = e.width;
        float f5 = s.width;
        float f6 = e.height;
        float f7 = s.height;
        this.position = this.time;
        this.width = (float) ((int) (f5 + ((f4 - f5) * scaleWidth)));
        this.height = (float) ((int) (f7 + ((f6 - f7) * scaleHeight)));
        float f8 = 1.0f - position2;
        float f9 = position2;
        switch (c.mPositionType) {
            case 1:
                float f10 = Float.isNaN(c.mPercentX) ? position2 : c.mPercentX;
                float f11 = e.f984x;
                float f12 = s.f984x;
                this.f984x = (f10 * (f11 - f12)) + f12;
                float f13 = Float.isNaN(c.mPercentY) ? position2 : c.mPercentY;
                float f14 = e.f985y;
                float f15 = s.f985y;
                this.f985y = (f13 * (f14 - f15)) + f15;
                break;
            case 2:
                if (Float.isNaN(c.mPercentX)) {
                    float f16 = e.f984x;
                    float f17 = s.f984x;
                    f = ((f16 - f17) * position2) + f17;
                } else {
                    f = c.mPercentX * Math.min(scaleHeight, scaleWidth);
                }
                this.f984x = f;
                if (Float.isNaN(c.mPercentY)) {
                    float f18 = e.f985y;
                    float f19 = s.f985y;
                    f2 = ((f18 - f19) * position2) + f19;
                } else {
                    f2 = c.mPercentY;
                }
                this.f985y = f2;
                break;
            default:
                if (Float.isNaN(c.mPercentX)) {
                    f3 = position2;
                } else {
                    f3 = c.mPercentX;
                }
                float f20 = e.f984x;
                float f21 = s.f984x;
                this.f984x = (f3 * (f20 - f21)) + f21;
                float f22 = Float.isNaN(c.mPercentY) ? position2 : c.mPercentY;
                float f23 = e.f985y;
                float f24 = s.f985y;
                this.f985y = (f22 * (f23 - f24)) + f24;
                break;
        }
        this.mAnimateRelativeTo = s.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    public void setupRelative(Motion mc, MotionPaths relative) {
        double dx = (double) (((this.f984x + (this.width / 2.0f)) - relative.f984x) - (relative.width / 2.0f));
        double dy = (double) (((this.f985y + (this.height / 2.0f)) - relative.f985y) - (relative.height / 2.0f));
        this.mRelativeToController = mc;
        this.f984x = (float) Math.hypot(dy, dx);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.f985y = (float) (Math.atan2(dy, dx) + 1.5707963267948966d);
        } else {
            this.f985y = (float) Math.toRadians((double) this.mRelativeAngle);
        }
    }

    /* access modifiers changed from: package-private */
    public void initScreen(int parentWidth, int parentHeight, MotionKeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        MotionKeyPosition motionKeyPosition = c;
        MotionPaths motionPaths = startTimePoint;
        MotionPaths motionPaths2 = endTimePoint;
        float position2 = ((float) motionKeyPosition.mFramePosition) / 100.0f;
        this.time = position2;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float scaleWidth = Float.isNaN(motionKeyPosition.mPercentWidth) ? position2 : motionKeyPosition.mPercentWidth;
        float scaleHeight = Float.isNaN(motionKeyPosition.mPercentHeight) ? position2 : motionKeyPosition.mPercentHeight;
        float f = motionPaths2.width;
        float f2 = motionPaths.width;
        float scaleX = f - f2;
        float f3 = motionPaths2.height;
        float f4 = motionPaths.height;
        float scaleY = f3 - f4;
        this.position = this.time;
        float path = position2;
        float f5 = motionPaths.f984x;
        float f6 = position2;
        float position3 = motionPaths.f985y;
        float endCenterX = motionPaths2.f984x + (f / 2.0f);
        float endCenterY = motionPaths2.f985y + (f3 / 2.0f);
        this.f984x = (float) ((int) ((f5 + ((endCenterX - (f5 + (f2 / 2.0f))) * path)) - ((scaleX * scaleWidth) / 2.0f)));
        this.f985y = (float) ((int) ((position3 + ((endCenterY - (position3 + (f4 / 2.0f))) * path)) - ((scaleY * scaleHeight) / 2.0f)));
        this.width = (float) ((int) (f2 + (scaleX * scaleWidth)));
        this.height = (float) ((int) (f4 + (scaleY * scaleHeight)));
        this.mMode = 2;
        if (!Float.isNaN(motionKeyPosition.mPercentX)) {
            this.f984x = (float) ((int) (motionKeyPosition.mPercentX * ((float) ((int) (((float) parentWidth) - this.width)))));
        }
        if (!Float.isNaN(motionKeyPosition.mPercentY)) {
            this.f985y = (float) ((int) (motionKeyPosition.mPercentY * ((float) ((int) (((float) parentHeight) - this.height)))));
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }

    /* access modifiers changed from: package-private */
    public void initPath(MotionKeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        MotionKeyPosition motionKeyPosition = c;
        MotionPaths motionPaths = startTimePoint;
        MotionPaths motionPaths2 = endTimePoint;
        float position2 = ((float) motionKeyPosition.mFramePosition) / 100.0f;
        this.time = position2;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float scaleWidth = Float.isNaN(motionKeyPosition.mPercentWidth) ? position2 : motionKeyPosition.mPercentWidth;
        float scaleHeight = Float.isNaN(motionKeyPosition.mPercentHeight) ? position2 : motionKeyPosition.mPercentHeight;
        float scaleX = motionPaths2.width - motionPaths.width;
        float scaleY = motionPaths2.height - motionPaths.height;
        this.position = this.time;
        float path = Float.isNaN(motionKeyPosition.mPercentX) ? position2 : motionKeyPosition.mPercentX;
        float f = motionPaths.f984x;
        float f2 = motionPaths.width;
        float f3 = motionPaths.f985y;
        float f4 = position2;
        float position3 = motionPaths.height;
        float endCenterX = motionPaths2.f984x + (motionPaths2.width / 2.0f);
        float endCenterY = motionPaths2.f985y + (motionPaths2.height / 2.0f);
        float pathVectorX = endCenterX - ((f2 / 2.0f) + f);
        float pathVectorY = endCenterY - (f3 + (position3 / 2.0f));
        this.f984x = (float) ((int) ((f + (pathVectorX * path)) - ((scaleX * scaleWidth) / 2.0f)));
        this.f985y = (float) ((int) ((f3 + (pathVectorY * path)) - ((scaleY * scaleHeight) / 2.0f)));
        this.width = (float) ((int) (f2 + (scaleX * scaleWidth)));
        this.height = (float) ((int) (position3 + (scaleY * scaleHeight)));
        MotionKeyPosition motionKeyPosition2 = c;
        float perpendicular = Float.isNaN(motionKeyPosition2.mPercentY) ? 0.0f : motionKeyPosition2.mPercentY;
        float f5 = endCenterX;
        this.mMode = 1;
        MotionPaths motionPaths3 = startTimePoint;
        float f6 = endCenterY;
        float f7 = (float) ((int) ((motionPaths3.f984x + (pathVectorX * path)) - ((scaleX * scaleWidth) / 2.0f)));
        this.f984x = f7;
        float f8 = pathVectorX;
        float f9 = (float) ((int) ((motionPaths3.f985y + (pathVectorY * path)) - ((scaleY * scaleHeight) / 2.0f)));
        this.f985y = f9;
        this.f984x = f7 + ((-pathVectorY) * perpendicular);
        this.f985y = f9 + (pathVectorX * perpendicular);
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition2.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition2.mPathMotionArc;
    }

    private static final float xRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return (((x - cx) * cos) - ((y - cy) * sin)) + cx;
    }

    private static final float yRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return ((x - cx) * sin) + ((y - cy) * cos) + cy;
    }

    private boolean diff(float a, float b) {
        if (Float.isNaN(a) || Float.isNaN(b)) {
            if (Float.isNaN(a) != Float.isNaN(b)) {
                return true;
            }
            return false;
        } else if (Math.abs(a - b) > 1.0E-6f) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void different(MotionPaths points, boolean[] mask, String[] custom, boolean arcMode) {
        boolean diffx = diff(this.f984x, points.f984x);
        boolean diffy = diff(this.f985y, points.f985y);
        int c = 0 + 1;
        mask[0] = mask[0] | diff(this.position, points.position);
        int c2 = c + 1;
        mask[c] = mask[c] | diffx | diffy | arcMode;
        int c3 = c2 + 1;
        mask[c2] = mask[c2] | diffx | diffy | arcMode;
        int c4 = c3 + 1;
        mask[c3] = mask[c3] | diff(this.width, points.width);
        int i = c4 + 1;
        mask[c4] = mask[c4] | diff(this.height, points.height);
    }

    /* access modifiers changed from: package-private */
    public void getCenter(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f;
        int[] iArr = toUse;
        float v_x = this.f984x;
        float v_y = this.f985y;
        float v_width = this.width;
        float v_height = this.height;
        for (int i = 0; i < iArr.length; i++) {
            float value = (float) data[i];
            switch (iArr[i]) {
                case 1:
                    v_x = value;
                    break;
                case 2:
                    v_y = value;
                    break;
                case 3:
                    v_width = value;
                    break;
                case 4:
                    v_height = value;
                    break;
            }
        }
        Motion motion = this.mRelativeToController;
        if (motion != null) {
            float[] pos = new float[2];
            float[] vel = new float[2];
            motion.getCenter(p, pos, vel);
            float rx = pos[0];
            float ry = pos[1];
            float radius = v_x;
            float[] fArr = vel;
            double d = (double) rx;
            double d2 = (double) radius;
            float f2 = v_x;
            float angle = v_y;
            float[] fArr2 = pos;
            double sin = Math.sin((double) angle);
            Double.isNaN(d2);
            Double.isNaN(d);
            double d3 = d + (d2 * sin);
            double d4 = (double) (v_width / 2.0f);
            Double.isNaN(d4);
            float v_x2 = (float) (d3 - d4);
            double d5 = (double) ry;
            double d6 = (double) radius;
            float v_x3 = v_x2;
            double cos = Math.cos((double) angle);
            Double.isNaN(d6);
            Double.isNaN(d5);
            double d7 = d5 - (d6 * cos);
            f = 2.0f;
            double d8 = (double) (v_height / 2.0f);
            Double.isNaN(d8);
            v_y = (float) (d7 - d8);
            v_x = v_x3;
        } else {
            float f3 = v_x;
            f = 2.0f;
        }
        point[offset] = (v_width / f) + v_x + 0.0f;
        point[offset + 1] = (v_height / f) + v_y + 0.0f;
    }

    /* access modifiers changed from: package-private */
    public void getCenter(double p, int[] toUse, double[] data, float[] point, double[] vdata, float[] velocity) {
        float translationX;
        int[] iArr = toUse;
        float v_x = this.f984x;
        float v_y = this.f985y;
        float v_width = this.width;
        float v_height = this.height;
        float dv_x = 0.0f;
        float dv_y = 0.0f;
        float dv_width = 0.0f;
        float dv_height = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float value = (float) data[i];
            float dvalue = (float) vdata[i];
            switch (iArr[i]) {
                case 1:
                    v_x = value;
                    dv_x = dvalue;
                    break;
                case 2:
                    v_y = value;
                    dv_y = dvalue;
                    break;
                case 3:
                    v_width = value;
                    dv_width = dvalue;
                    break;
                case 4:
                    v_height = value;
                    dv_height = dvalue;
                    break;
            }
        }
        float dangle = (dv_width / 2.0f) + dv_x;
        float v_x2 = (dv_height / 2.0f) + dv_y;
        Motion motion = this.mRelativeToController;
        if (motion != null) {
            float[] pos = new float[2];
            float[] vel = new float[2];
            float f = dv_width;
            float f2 = dv_height;
            motion.getCenter(p, pos, vel);
            float rx = pos[0];
            float ry = pos[1];
            float radius = v_x;
            float angle = v_y;
            float dradius = dv_x;
            float dangle2 = dv_y;
            float[] fArr = pos;
            float drx = vel[0];
            float f3 = v_x;
            float dry = vel[1];
            float f4 = dv_x;
            float f5 = dv_y;
            double d = (double) rx;
            float f6 = v_y;
            float radius2 = radius;
            double d2 = (double) radius2;
            float[] fArr2 = vel;
            float f7 = v_x2;
            float angle2 = angle;
            float angle3 = dangle;
            double sin = Math.sin((double) angle2);
            Double.isNaN(d2);
            Double.isNaN(d);
            double d3 = d + (d2 * sin);
            double d4 = (double) (v_width / 2.0f);
            Double.isNaN(d4);
            double d5 = (double) ry;
            double d6 = (double) radius2;
            translationX = 0.0f;
            double cos = Math.cos((double) angle2);
            Double.isNaN(d6);
            Double.isNaN(d5);
            double d7 = (double) (v_height / 2.0f);
            Double.isNaN(d7);
            double d8 = (double) drx;
            float dradius2 = dradius;
            double d9 = (double) dradius2;
            float f8 = drx;
            float f9 = ry;
            double sin2 = Math.sin((double) angle2);
            Double.isNaN(d9);
            Double.isNaN(d8);
            double d10 = d8 + (d9 * sin2);
            double cos2 = Math.cos((double) angle2);
            float v_x3 = (float) (d3 - d4);
            float dangle3 = dangle2;
            double d11 = (double) dangle3;
            Double.isNaN(d11);
            float dpos_x = (float) (d10 + (cos2 * d11));
            double d12 = (double) dry;
            double d13 = (double) dradius2;
            float dpos_x2 = dpos_x;
            double cos3 = Math.cos((double) angle2);
            Double.isNaN(d13);
            Double.isNaN(d12);
            double d14 = d12 - (d13 * cos3);
            double sin3 = Math.sin((double) angle2);
            double d15 = (double) dangle3;
            Double.isNaN(d15);
            v_x = v_x3;
            dangle = dpos_x2;
            v_y = (float) ((d5 - (d6 * cos)) - d7);
            v_x2 = (float) (d14 + (sin3 * d15));
        } else {
            float f10 = v_x;
            float f11 = v_y;
            float f12 = dv_x;
            float f13 = dv_y;
            float f14 = dv_width;
            float f15 = dv_height;
            translationX = 0.0f;
            float f16 = dangle;
            float f17 = v_x2;
        }
        point[0] = (v_width / 2.0f) + v_x + translationX;
        point[1] = (v_height / 2.0f) + v_y + 0.0f;
        velocity[0] = dangle;
        velocity[1] = v_x2;
    }

    /* access modifiers changed from: package-private */
    public void getCenterVelocity(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f;
        int[] iArr = toUse;
        float v_x = this.f984x;
        float v_y = this.f985y;
        float v_width = this.width;
        float v_height = this.height;
        for (int i = 0; i < iArr.length; i++) {
            float value = (float) data[i];
            switch (iArr[i]) {
                case 1:
                    v_x = value;
                    break;
                case 2:
                    v_y = value;
                    break;
                case 3:
                    v_width = value;
                    break;
                case 4:
                    v_height = value;
                    break;
            }
        }
        Motion motion = this.mRelativeToController;
        if (motion != null) {
            float[] pos = new float[2];
            float[] vel = new float[2];
            motion.getCenter(p, pos, vel);
            float rx = pos[0];
            float ry = pos[1];
            float radius = v_x;
            float[] fArr = vel;
            double d = (double) rx;
            double d2 = (double) radius;
            float f2 = v_x;
            float angle = v_y;
            float[] fArr2 = pos;
            double sin = Math.sin((double) angle);
            Double.isNaN(d2);
            Double.isNaN(d);
            double d3 = d + (d2 * sin);
            double d4 = (double) (v_width / 2.0f);
            Double.isNaN(d4);
            float v_x2 = (float) (d3 - d4);
            double d5 = (double) ry;
            double d6 = (double) radius;
            float v_x3 = v_x2;
            double cos = Math.cos((double) angle);
            Double.isNaN(d6);
            Double.isNaN(d5);
            double d7 = d5 - (d6 * cos);
            f = 2.0f;
            double d8 = (double) (v_height / 2.0f);
            Double.isNaN(d8);
            v_y = (float) (d7 - d8);
            v_x = v_x3;
        } else {
            float f3 = v_x;
            f = 2.0f;
        }
        point[offset] = (v_width / f) + v_x + 0.0f;
        point[offset + 1] = (v_height / f) + v_y + 0.0f;
    }

    /* access modifiers changed from: package-private */
    public void getBounds(int[] toUse, double[] data, float[] point, int offset) {
        float f = this.f984x;
        float f2 = this.f985y;
        float v_width = this.width;
        float v_height = this.height;
        for (int i = 0; i < toUse.length; i++) {
            float value = (float) data[i];
            switch (toUse[i]) {
                case 1:
                    float v_x = value;
                    break;
                case 2:
                    float v_y = value;
                    break;
                case 3:
                    v_width = value;
                    break;
                case 4:
                    v_height = value;
                    break;
            }
        }
        point[offset] = v_width;
        point[offset + 1] = v_height;
    }

    /* access modifiers changed from: package-private */
    public void setView(float position2, MotionWidget view, int[] toUse, double[] data, double[] slope, double[] cycle) {
        float v_x;
        float v_width;
        float v_height;
        float v_y;
        float path_rotate;
        float delta_path;
        float dv_height;
        MotionWidget motionWidget = view;
        int[] iArr = toUse;
        double[] dArr = slope;
        float v_x2 = this.f984x;
        float v_y2 = this.f985y;
        float v_width2 = this.width;
        float v_height2 = this.height;
        float dv_x = 0.0f;
        float dv_y = 0.0f;
        float dv_width = 0.0f;
        float dvalue = 0.0f;
        float delta_path2 = 0.0f;
        float path_rotate2 = Float.NaN;
        if (iArr.length != 0) {
            v_x = v_x2;
            if (this.mTempValue.length <= iArr[iArr.length - 1]) {
                int scratch_data_length = iArr[iArr.length - 1] + 1;
                this.mTempValue = new double[scratch_data_length];
                this.mTempDelta = new double[scratch_data_length];
            }
        } else {
            v_x = v_x2;
        }
        float v_y3 = v_y2;
        float v_width3 = v_width2;
        Arrays.fill(this.mTempValue, Double.NaN);
        for (int i = 0; i < iArr.length; i++) {
            this.mTempValue[iArr[i]] = data[i];
            this.mTempDelta[iArr[i]] = dArr[i];
        }
        int i2 = 0;
        float v_y4 = v_y3;
        float v_width4 = v_width3;
        while (true) {
            double[] dArr2 = this.mTempValue;
            if (i2 < dArr2.length) {
                double d = 0.0d;
                if (Double.isNaN(dArr2[i2])) {
                    if (cycle == null) {
                        dv_height = dvalue;
                        delta_path = delta_path2;
                    } else if (cycle[i2] == 0.0d) {
                        dv_height = dvalue;
                        delta_path = delta_path2;
                    }
                    dvalue = dv_height;
                    delta_path2 = delta_path;
                    i2++;
                    int[] iArr2 = toUse;
                }
                if (cycle != null) {
                    d = cycle[i2];
                }
                double deltaCycle = d;
                if (Double.isNaN(this.mTempValue[i2])) {
                    double d2 = deltaCycle;
                } else {
                    double d3 = deltaCycle;
                    deltaCycle = this.mTempValue[i2] + deltaCycle;
                }
                float value = (float) deltaCycle;
                dv_height = dvalue;
                delta_path = delta_path2;
                dvalue = (float) this.mTempDelta[i2];
                switch (i2) {
                    case 0:
                        delta_path2 = value;
                        dvalue = dv_height;
                        continue;
                    case 1:
                        dv_x = dvalue;
                        v_x = value;
                        dvalue = dv_height;
                        delta_path2 = delta_path;
                        continue;
                    case 2:
                        v_y4 = value;
                        dv_y = dvalue;
                        dvalue = dv_height;
                        delta_path2 = delta_path;
                        continue;
                    case 3:
                        v_width4 = value;
                        dv_width = dvalue;
                        dvalue = dv_height;
                        delta_path2 = delta_path;
                        continue;
                    case 4:
                        v_height2 = value;
                        float f = dvalue;
                        delta_path2 = delta_path;
                        continue;
                    case 5:
                        path_rotate2 = value;
                        dvalue = dv_height;
                        delta_path2 = delta_path;
                        continue;
                }
                dvalue = dv_height;
                delta_path2 = delta_path;
                i2++;
                int[] iArr22 = toUse;
            } else {
                float dv_height2 = dvalue;
                float f2 = delta_path2;
                Motion motion = this.mRelativeToController;
                if (motion != null) {
                    float[] pos = new float[2];
                    float[] vel = new float[2];
                    float v_y5 = v_y4;
                    motion.getCenter((double) position2, pos, vel);
                    float rx = pos[0];
                    float ry = pos[1];
                    float radius = v_x;
                    float dradius = dv_x;
                    float dangle = dv_y;
                    float drx = vel[0];
                    float dry = vel[1];
                    float[] fArr = vel;
                    float f3 = dv_height2;
                    double d4 = (double) rx;
                    float f4 = dv_x;
                    float f5 = dv_y;
                    double d5 = (double) radius;
                    float f6 = rx;
                    float[] fArr2 = pos;
                    float rx2 = v_y5;
                    float path_rotate3 = path_rotate2;
                    double sin = Math.sin((double) rx2);
                    Double.isNaN(d5);
                    Double.isNaN(d4);
                    double d6 = d4 + (d5 * sin);
                    double d7 = (double) (v_width4 / 2.0f);
                    Double.isNaN(d7);
                    float pos_x = (float) (d6 - d7);
                    double d8 = (double) ry;
                    double d9 = (double) radius;
                    float f7 = dv_width;
                    double cos = Math.cos((double) rx2);
                    Double.isNaN(d9);
                    Double.isNaN(d8);
                    double d10 = (double) (v_height2 / 2.0f);
                    Double.isNaN(d10);
                    float pos_y = (float) ((d8 - (d9 * cos)) - d10);
                    double d11 = (double) drx;
                    float dradius2 = dradius;
                    double d12 = (double) dradius2;
                    v_width = v_width4;
                    v_height = v_height2;
                    double sin2 = Math.sin((double) rx2);
                    Double.isNaN(d12);
                    Double.isNaN(d11);
                    double d13 = d11 + (d12 * sin2);
                    double d14 = (double) radius;
                    double cos2 = Math.cos((double) rx2);
                    Double.isNaN(d14);
                    double d15 = d14 * cos2;
                    float f8 = drx;
                    float dangle2 = dangle;
                    double d16 = (double) dangle2;
                    Double.isNaN(d16);
                    float dpos_x = (float) (d13 + (d15 * d16));
                    double d17 = (double) dry;
                    double d18 = (double) dradius2;
                    float f9 = ry;
                    double cos3 = Math.cos((double) rx2);
                    Double.isNaN(d18);
                    Double.isNaN(d17);
                    double d19 = d17 - (d18 * cos3);
                    double d20 = (double) radius;
                    double sin3 = Math.sin((double) rx2);
                    Double.isNaN(d20);
                    double d21 = d20 * sin3;
                    double d22 = (double) dangle2;
                    Double.isNaN(d22);
                    float dpos_y = (float) (d19 + (d21 * d22));
                    float dv_x2 = dpos_x;
                    float dv_y2 = dpos_y;
                    v_x = pos_x;
                    float v_y6 = pos_y;
                    if (dArr.length >= 2) {
                        dArr[0] = (double) dpos_x;
                        dArr[1] = (double) dpos_y;
                    }
                    if (!Float.isNaN(path_rotate3)) {
                        float f10 = dpos_x;
                        float f11 = dpos_y;
                        double d23 = (double) path_rotate3;
                        float f12 = rx2;
                        float f13 = radius;
                        path_rotate = dv_y2;
                        double degrees = Math.toDegrees(Math.atan2((double) dv_y2, (double) dv_x2));
                        Double.isNaN(d23);
                        motionWidget = view;
                        motionWidget.setRotationZ((float) (d23 + degrees));
                    } else {
                        float f14 = dpos_y;
                        float f15 = rx2;
                        float f16 = radius;
                        float f17 = path_rotate3;
                        motionWidget = view;
                        path_rotate = dv_y2;
                    }
                    float pos_x2 = dv_x2;
                    v_y = v_y6;
                    float f18 = path_rotate;
                } else {
                    float v_y7 = v_y4;
                    v_width = v_width4;
                    v_height = v_height2;
                    float dv_x3 = dv_x;
                    float dv_y3 = dv_y;
                    float dv_width2 = dv_width;
                    float dv_height3 = dv_height2;
                    if (!Float.isNaN(path_rotate2)) {
                        double d24 = (double) 0.0f;
                        double d25 = (double) path_rotate2;
                        double degrees2 = Math.toDegrees(Math.atan2((double) (dv_y3 + (dv_height3 / 2.0f)), (double) (dv_x3 + (dv_width2 / 2.0f))));
                        Double.isNaN(d25);
                        Double.isNaN(d24);
                        motionWidget.setRotationZ((float) (d24 + d25 + degrees2));
                    }
                    v_y = v_y7;
                    float f19 = dv_y3;
                    float f20 = dv_x3;
                }
                int l = (int) (v_x + 0.5f);
                int t = (int) (v_y + 0.5f);
                int r = (int) (v_x + 0.5f + v_width);
                int b = (int) (0.5f + v_y + v_height);
                int i3 = r - l;
                int i4 = b - t;
                motionWidget.layout(l, t, r, b);
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void getRect(int[] toUse, double[] data, float[] path, int offset) {
        float angle;
        int[] iArr = toUse;
        float v_x = this.f984x;
        float v_y = this.f985y;
        float v_width = this.width;
        float v_height = this.height;
        float alpha = 0.0f;
        float rotationX = 0.0f;
        int i = 0;
        while (true) {
            float alpha2 = alpha;
            if (i < iArr.length) {
                float rotationX2 = rotationX;
                float value = (float) data[i];
                switch (iArr[i]) {
                    case 0:
                        float delta_path = value;
                        break;
                    case 1:
                        v_x = value;
                        break;
                    case 2:
                        v_y = value;
                        break;
                    case 3:
                        v_width = value;
                        break;
                    case 4:
                        v_height = value;
                        break;
                }
                i++;
                alpha = alpha2;
                rotationX = rotationX2;
            } else {
                Motion motion = this.mRelativeToController;
                if (motion != null) {
                    float rx = motion.getCenterX();
                    float f = v_y;
                    double d = (double) rx;
                    float radius = v_x;
                    double d2 = (double) radius;
                    float f2 = v_y;
                    float f3 = rx;
                    angle = 0.0f;
                    double sin = Math.sin((double) v_y);
                    Double.isNaN(d2);
                    Double.isNaN(d);
                    double d3 = d + (d2 * sin);
                    double d4 = (double) (v_width / 2.0f);
                    Double.isNaN(d4);
                    v_x = (float) (d3 - d4);
                    float ry = this.mRelativeToController.getCenterY();
                    double d5 = (double) ry;
                    double d6 = (double) radius;
                    float f4 = radius;
                    float f5 = ry;
                    double cos = Math.cos((double) v_y);
                    Double.isNaN(d6);
                    Double.isNaN(d5);
                    double d7 = (double) (v_height / 2.0f);
                    Double.isNaN(d7);
                    v_y = (float) ((d5 - (d6 * cos)) - d7);
                } else {
                    float ry2 = v_x;
                    float f6 = v_y;
                    angle = 0.0f;
                }
                float x1 = v_x;
                float y1 = v_y;
                float x2 = v_x + v_width;
                float y2 = y1;
                float x3 = x2;
                float y3 = v_y + v_height;
                float x4 = x1;
                float y4 = y3;
                float cx = x1 + (v_width / 2.0f);
                float cy = y1 + (v_height / 2.0f);
                if (!Float.isNaN(Float.NaN)) {
                    cx = x1 + ((x2 - x1) * Float.NaN);
                }
                if (!Float.isNaN(Float.NaN)) {
                    cy = y1 + ((y3 - y1) * Float.NaN);
                }
                if (1.0f != 1.0f) {
                    float midx = (x1 + x2) / 2.0f;
                    x1 = ((x1 - midx) * 1.0f) + midx;
                    x2 = ((x2 - midx) * 1.0f) + midx;
                    x3 = ((x3 - midx) * 1.0f) + midx;
                    x4 = ((x4 - midx) * 1.0f) + midx;
                }
                if (1.0f != 1.0f) {
                    float midy = (y1 + y3) / 2.0f;
                    y1 = ((y1 - midy) * 1.0f) + midy;
                    y2 = ((y2 - midy) * 1.0f) + midy;
                    y3 = ((y3 - midy) * 1.0f) + midy;
                    y4 = ((y4 - midy) * 1.0f) + midy;
                }
                if (angle != 0.0f) {
                    float f7 = v_x;
                    float f8 = v_y;
                    float rotation = angle;
                    float rotation2 = v_width;
                    float f9 = v_height;
                    float sin2 = (float) Math.sin(Math.toRadians((double) rotation));
                    float cos2 = (float) Math.cos(Math.toRadians((double) rotation));
                    float f10 = cx;
                    float f11 = cy;
                    float f12 = x1;
                    float f13 = y1;
                    float tx1 = xRotate(sin2, cos2, f10, f11, f12, f13);
                    float ty1 = yRotate(sin2, cos2, f10, f11, f12, f13);
                    float f14 = x2;
                    float f15 = y2;
                    float tx2 = xRotate(sin2, cos2, f10, f11, f14, f15);
                    float ty2 = yRotate(sin2, cos2, f10, f11, f14, f15);
                    float f16 = x3;
                    float f17 = y3;
                    float tx3 = xRotate(sin2, cos2, f10, f11, f16, f17);
                    float ty3 = yRotate(sin2, cos2, f10, f11, f16, f17);
                    float f18 = x4;
                    float f19 = y4;
                    x1 = tx1;
                    y1 = ty1;
                    x2 = tx2;
                    y2 = ty2;
                    x3 = tx3;
                    y3 = ty3;
                    x4 = xRotate(sin2, cos2, f10, f11, f18, f19);
                    y4 = yRotate(sin2, cos2, f10, f11, f18, f19);
                } else {
                    float f20 = v_y;
                    float f21 = v_height;
                    float v_x2 = angle;
                    float rotation3 = v_width;
                }
                int offset2 = offset + 1;
                path[offset] = x1 + 0.0f;
                int offset3 = offset2 + 1;
                path[offset2] = y1 + 0.0f;
                int offset4 = offset3 + 1;
                path[offset3] = x2 + 0.0f;
                int offset5 = offset4 + 1;
                path[offset4] = y2 + 0.0f;
                int offset6 = offset5 + 1;
                path[offset5] = x3 + 0.0f;
                int offset7 = offset6 + 1;
                path[offset6] = y3 + 0.0f;
                int offset8 = offset7 + 1;
                path[offset7] = x4 + 0.0f;
                int i2 = offset8 + 1;
                path[offset8] = y4 + 0.0f;
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setDpDt(float locationX, float locationY, float[] mAnchorDpDt, int[] toUse, double[] deltaData, double[] data) {
        int[] iArr = toUse;
        float d_x = 0.0f;
        float d_y = 0.0f;
        float d_width = 0.0f;
        float d_height = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float deltaV = (float) deltaData[i];
            float f = (float) data[i];
            switch (iArr[i]) {
                case 1:
                    d_x = deltaV;
                    break;
                case 2:
                    d_y = deltaV;
                    break;
                case 3:
                    d_width = deltaV;
                    break;
                case 4:
                    d_height = deltaV;
                    break;
            }
        }
        float deltaX = d_x - ((0.0f * d_width) / 2.0f);
        float deltaY = d_y - ((0.0f * d_height) / 2.0f);
        mAnchorDpDt[0] = ((1.0f - locationX) * deltaX) + ((deltaX + ((0.0f + 1.0f) * d_width)) * locationX) + 0.0f;
        mAnchorDpDt[1] = ((1.0f - locationY) * deltaY) + ((deltaY + ((0.0f + 1.0f) * d_height)) * locationY) + 0.0f;
    }

    /* access modifiers changed from: package-private */
    public void fillStandard(double[] data, int[] toUse) {
        float[] set = {this.position, this.f984x, this.f985y, this.width, this.height, this.mPathRotate};
        int c = 0;
        for (int i = 0; i < toUse.length; i++) {
            if (toUse[i] < set.length) {
                data[c] = (double) set[toUse[i]];
                c++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasCustomData(String name) {
        return this.customAttributes.containsKey(name);
    }

    /* access modifiers changed from: package-private */
    public int getCustomDataCount(String name) {
        CustomVariable a = this.customAttributes.get(name);
        if (a == null) {
            return 0;
        }
        return a.numberOfInterpolatedValues();
    }

    /* access modifiers changed from: package-private */
    public int getCustomData(String name, double[] value, int offset) {
        CustomVariable a = this.customAttributes.get(name);
        if (a == null) {
            return 0;
        }
        if (a.numberOfInterpolatedValues() == 1) {
            value[offset] = (double) a.getValueToInterpolate();
            return 1;
        }
        int N = a.numberOfInterpolatedValues();
        float[] f = new float[N];
        a.getValuesToInterpolate(f);
        int i = 0;
        while (i < N) {
            value[offset] = (double) f[i];
            i++;
            offset++;
        }
        return N;
    }

    /* access modifiers changed from: package-private */
    public void setBounds(float x, float y, float w, float h) {
        this.f984x = x;
        this.f985y = y;
        this.width = w;
        this.height = h;
    }

    public int compareTo(MotionPaths o) {
        return Float.compare(this.position, o.position);
    }

    public void applyParameters(MotionWidget c) {
        this.mKeyFrameEasing = Easing.getInterpolator(c.motion.mTransitionEasing);
        this.mPathMotionArc = c.motion.mPathMotionArc;
        this.mAnimateRelativeTo = c.motion.mAnimateRelativeTo;
        this.mPathRotate = c.motion.mPathRotate;
        this.mDrawPath = c.motion.mDrawPath;
        this.mAnimateCircleAngleTo = c.motion.mAnimateCircleAngleTo;
        this.mProgress = c.propertySet.mProgress;
        this.mRelativeAngle = 0.0f;
        for (String s : c.getCustomAttributeNames()) {
            CustomVariable attr = c.getCustomAttribute(s);
            if (attr != null && attr.isContinuous()) {
                this.customAttributes.put(s, attr);
            }
        }
    }

    public void configureRelativeTo(Motion toOrbit) {
        double[] pos = toOrbit.getPos((double) this.mProgress);
    }
}
