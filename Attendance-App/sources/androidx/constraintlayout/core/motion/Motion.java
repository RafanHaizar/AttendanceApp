package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKey;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.key.MotionKeyTrigger;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.DifferentialInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.core.motion.utils.ViewState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Motion implements TypedValues {
    static final int BOUNCE = 4;
    private static final boolean DEBUG = false;
    public static final int DRAW_PATH_AS_CONFIGURED = 4;
    public static final int DRAW_PATH_BASIC = 1;
    public static final int DRAW_PATH_CARTESIAN = 3;
    public static final int DRAW_PATH_NONE = 0;
    public static final int DRAW_PATH_RECTANGLE = 5;
    public static final int DRAW_PATH_RELATIVE = 2;
    public static final int DRAW_PATH_SCREEN = 6;
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
    public static final int HORIZONTAL_PATH_X = 2;
    public static final int HORIZONTAL_PATH_Y = 3;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final int INTERPOLATOR_UNDEFINED = -3;
    static final int LINEAR = 3;
    static final int OVERSHOOT = 5;
    public static final int PATH_PERCENT = 0;
    public static final int PATH_PERPENDICULAR = 1;
    public static final int ROTATION_LEFT = 2;
    public static final int ROTATION_RIGHT = 1;
    private static final int SPLINE_STRING = -1;
    private static final String TAG = "MotionController";
    public static final int VERTICAL_PATH_X = 4;
    public static final int VERTICAL_PATH_Y = 5;
    private int MAX_DIMENSION = 4;
    String[] attributeTable;
    private CurveFit mArcSpline;
    private int[] mAttributeInterpolatorCount;
    private String[] mAttributeNames;
    private HashMap<String, SplineSet> mAttributesMap;
    String mConstraintTag;
    float mCurrentCenterX;
    float mCurrentCenterY;
    private int mCurveFitType = -1;
    private HashMap<String, KeyCycleOscillator> mCycleMap;
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    int mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private ArrayList<MotionKey> mKeyList = new ArrayList<>();
    private MotionKeyTrigger[] mKeyTriggers;
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    float mMotionStagger = Float.NaN;
    private boolean mNoMovement = false;
    private int mPathMotionArc = -1;
    private DifferentialInterpolator mQuantizeMotionInterpolator = null;
    private float mQuantizeMotionPhase = Float.NaN;
    private int mQuantizeMotionSteps = -1;
    private CurveFit[] mSpline;
    float mStaggerOffset = 0.0f;
    float mStaggerScale = 1.0f;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    Rect mTempRect = new Rect();
    private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
    private int mTransformPivotTarget = -1;
    private MotionWidget mTransformPivotView = null;
    private float[] mValuesBuff = new float[4];
    private float[] mVelocity = new float[1];
    MotionWidget mView;

    public int getTransformPivotTarget() {
        return this.mTransformPivotTarget;
    }

    public void setTransformPivotTarget(int transformPivotTarget) {
        this.mTransformPivotTarget = transformPivotTarget;
        this.mTransformPivotView = null;
    }

    public MotionPaths getKeyFrame(int i) {
        return this.mMotionPaths.get(i);
    }

    public Motion(MotionWidget view) {
        setView(view);
    }

    public float getStartX() {
        return this.mStartMotionPath.f984x;
    }

    public float getStartY() {
        return this.mStartMotionPath.f985y;
    }

    public float getFinalX() {
        return this.mEndMotionPath.f984x;
    }

    public float getFinalY() {
        return this.mEndMotionPath.f985y;
    }

    public float getStartWidth() {
        return this.mStartMotionPath.width;
    }

    public float getStartHeight() {
        return this.mStartMotionPath.height;
    }

    public float getFinalWidth() {
        return this.mEndMotionPath.width;
    }

    public float getFinalHeight() {
        return this.mEndMotionPath.height;
    }

    public int getAnimateRelativeTo() {
        return this.mStartMotionPath.mAnimateRelativeTo;
    }

    public void setupRelative(Motion motionController) {
        this.mStartMotionPath.setupRelative(motionController, motionController.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motionController, motionController.mEndMotionPath);
    }

    public float getCenterX() {
        return this.mCurrentCenterX;
    }

    public float getCenterY() {
        return this.mCurrentCenterY;
    }

    public void getCenter(double p, float[] pos, float[] vel) {
        double[] position = new double[4];
        double[] velocity = new double[4];
        int[] iArr = new int[4];
        this.mSpline[0].getPos(p, position);
        this.mSpline[0].getSlope(p, velocity);
        Arrays.fill(vel, 0.0f);
        this.mStartMotionPath.getCenter(p, this.mInterpolateVariables, position, pos, velocity, vel);
    }

    public void buildPath(float[] points, int pointCount) {
        float position;
        double p;
        Motion motion = this;
        int i = pointCount;
        float f = 1.0f;
        float mils = 1.0f / ((float) (i - 1));
        HashMap<String, SplineSet> hashMap = motion.mAttributesMap;
        KeyCycleOscillator keyCycleOscillator = null;
        SplineSet trans_x = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, SplineSet> hashMap2 = motion.mAttributesMap;
        SplineSet trans_y = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, KeyCycleOscillator> hashMap3 = motion.mCycleMap;
        KeyCycleOscillator osc_x = hashMap3 == null ? null : hashMap3.get("translationX");
        HashMap<String, KeyCycleOscillator> hashMap4 = motion.mCycleMap;
        if (hashMap4 != null) {
            keyCycleOscillator = hashMap4.get("translationY");
        }
        KeyCycleOscillator osc_y = keyCycleOscillator;
        int i2 = 0;
        while (i2 < i) {
            float position2 = ((float) i2) * mils;
            float f2 = motion.mStaggerScale;
            if (f2 != f) {
                float f3 = motion.mStaggerOffset;
                if (position2 < f3) {
                    position2 = 0.0f;
                }
                if (position2 <= f3 || ((double) position2) >= 1.0d) {
                    position = position2;
                } else {
                    position = Math.min((position2 - f3) * f2, f);
                }
            } else {
                position = position2;
            }
            double p2 = (double) position;
            Easing easing = motion.mStartMotionPath.mKeyFrameEasing;
            Iterator<MotionPaths> it = motion.mMotionPaths.iterator();
            float start = 0.0f;
            Easing easing2 = easing;
            float end = Float.NaN;
            while (it.hasNext()) {
                MotionPaths frame = it.next();
                if (frame.mKeyFrameEasing != null) {
                    if (frame.time < position) {
                        easing2 = frame.mKeyFrameEasing;
                        start = frame.time;
                    } else if (Float.isNaN(end)) {
                        end = frame.time;
                    }
                }
            }
            if (easing2 != null) {
                if (Float.isNaN(end)) {
                    end = 1.0f;
                }
                double d = p2;
                float offset = (float) easing2.get((double) ((position - start) / (end - start)));
                float f4 = offset;
                float f5 = end;
                p = (double) (((end - start) * offset) + start);
            } else {
                float f6 = end;
                p = p2;
            }
            motion.mSpline[0].getPos(p, motion.mInterpolateData);
            CurveFit curveFit = motion.mArcSpline;
            if (curveFit != null) {
                double[] dArr = motion.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(p, dArr);
                }
            }
            double d2 = p;
            Easing easing3 = easing2;
            float position3 = position;
            motion.mStartMotionPath.getCenter(p, motion.mInterpolateVariables, motion.mInterpolateData, points, i2 * 2);
            if (osc_x != null) {
                int i3 = i2 * 2;
                points[i3] = points[i3] + osc_x.get(position3);
            } else if (trans_x != null) {
                int i4 = i2 * 2;
                points[i4] = points[i4] + trans_x.get(position3);
            }
            if (osc_y != null) {
                int i5 = (i2 * 2) + 1;
                points[i5] = points[i5] + osc_y.get(position3);
            } else if (trans_y != null) {
                int i6 = (i2 * 2) + 1;
                points[i6] = points[i6] + trans_y.get(position3);
            }
            i2++;
            f = 1.0f;
            motion = this;
        }
    }

    /* access modifiers changed from: package-private */
    public double[] getPos(double position) {
        this.mSpline[0].getPos(position, this.mInterpolateData);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                curveFit.getPos(position, dArr);
            }
        }
        return this.mInterpolateData;
    }

    /* access modifiers changed from: package-private */
    public void buildBounds(float[] bounds, int pointCount) {
        float mils;
        Motion motion = this;
        int i = pointCount;
        float f = 1.0f;
        float mils2 = 1.0f / ((float) (i - 1));
        HashMap<String, SplineSet> hashMap = motion.mAttributesMap;
        SplineSet trans_x = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, SplineSet> hashMap2 = motion.mAttributesMap;
        if (hashMap2 != null) {
            SplineSet splineSet = hashMap2.get("translationY");
        }
        HashMap<String, KeyCycleOscillator> hashMap3 = motion.mCycleMap;
        if (hashMap3 != null) {
            KeyCycleOscillator keyCycleOscillator = hashMap3.get("translationX");
        }
        HashMap<String, KeyCycleOscillator> hashMap4 = motion.mCycleMap;
        if (hashMap4 != null) {
            KeyCycleOscillator keyCycleOscillator2 = hashMap4.get("translationY");
        }
        int i2 = 0;
        while (i2 < i) {
            float position = ((float) i2) * mils2;
            float f2 = motion.mStaggerScale;
            if (f2 != f) {
                float f3 = motion.mStaggerOffset;
                if (position < f3) {
                    position = 0.0f;
                }
                if (position > f3 && ((double) position) < 1.0d) {
                    position = Math.min((position - f3) * f2, f);
                }
            }
            double p = (double) position;
            Easing easing = motion.mStartMotionPath.mKeyFrameEasing;
            float start = 0.0f;
            float end = Float.NaN;
            Iterator<MotionPaths> it = motion.mMotionPaths.iterator();
            while (it.hasNext()) {
                MotionPaths frame = it.next();
                if (frame.mKeyFrameEasing != null) {
                    if (frame.time < position) {
                        Easing easing2 = frame.mKeyFrameEasing;
                        start = frame.time;
                        easing = easing2;
                    } else if (Float.isNaN(end)) {
                        end = frame.time;
                    }
                }
                int i3 = pointCount;
            }
            if (easing != null) {
                if (Float.isNaN(end)) {
                    end = 1.0f;
                }
                mils = mils2;
                p = (double) (((end - start) * ((float) easing.get((double) ((position - start) / (end - start))))) + start);
            } else {
                mils = mils2;
            }
            motion.mSpline[0].getPos(p, motion.mInterpolateData);
            CurveFit curveFit = motion.mArcSpline;
            if (curveFit != null) {
                double[] dArr = motion.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos(p, dArr);
                }
            }
            motion.mStartMotionPath.getBounds(motion.mInterpolateVariables, motion.mInterpolateData, bounds, i2 * 2);
            i2++;
            motion = this;
            i = pointCount;
            mils2 = mils;
            trans_x = trans_x;
            f = 1.0f;
        }
    }

    private float getPreCycleDistance() {
        double p;
        float offset;
        int pointCount = 100;
        float[] points = new float[2];
        float mils = 1.0f / ((float) (100 - 1));
        float sum = 0.0f;
        double x = 0.0d;
        double y = 0.0d;
        int i = 0;
        while (i < pointCount) {
            float position = ((float) i) * mils;
            double p2 = (double) position;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            int pointCount2 = pointCount;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float start = 0.0f;
            Easing easing2 = easing;
            float end = Float.NaN;
            while (it.hasNext()) {
                MotionPaths frame = it.next();
                Iterator<MotionPaths> it2 = it;
                if (frame.mKeyFrameEasing != null) {
                    if (frame.time < position) {
                        Easing easing3 = frame.mKeyFrameEasing;
                        start = frame.time;
                        easing2 = easing3;
                    } else if (Float.isNaN(end)) {
                        end = frame.time;
                    }
                }
                it = it2;
            }
            if (easing2 != null) {
                if (Float.isNaN(end)) {
                    end = 1.0f;
                }
                double d = p2;
                offset = end;
                p = (double) (((end - start) * ((float) easing2.get((double) ((position - start) / (end - start))))) + start);
            } else {
                offset = end;
                p = p2;
            }
            this.mSpline[0].getPos(p, this.mInterpolateData);
            float f = offset;
            double d2 = p;
            Easing easing4 = easing2;
            float f2 = position;
            int i2 = i;
            this.mStartMotionPath.getCenter(p, this.mInterpolateVariables, this.mInterpolateData, points, 0);
            if (i2 > 0) {
                double d3 = (double) sum;
                double d4 = (double) points[1];
                Double.isNaN(d4);
                double d5 = (double) points[0];
                Double.isNaN(d5);
                double hypot = Math.hypot(y - d4, x - d5);
                Double.isNaN(d3);
                sum = (float) (d3 + hypot);
            }
            x = (double) points[0];
            y = (double) points[1];
            i = i2 + 1;
            pointCount = pointCount2;
        }
        return sum;
    }

    /* access modifiers changed from: package-private */
    public MotionKeyPosition getPositionKeyframe(int layoutWidth, int layoutHeight, float x, float y) {
        FloatRect start = new FloatRect();
        start.left = this.mStartMotionPath.f984x;
        start.top = this.mStartMotionPath.f985y;
        start.right = start.left + this.mStartMotionPath.width;
        start.bottom = start.top + this.mStartMotionPath.height;
        FloatRect end = new FloatRect();
        end.left = this.mEndMotionPath.f984x;
        end.top = this.mEndMotionPath.f985y;
        end.right = end.left + this.mEndMotionPath.width;
        end.bottom = end.top + this.mEndMotionPath.height;
        Iterator<MotionKey> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            MotionKey key = it.next();
            if ((key instanceof MotionKeyPosition) && ((MotionKeyPosition) key).intersects(layoutWidth, layoutHeight, start, end, x, y)) {
                return (MotionKeyPosition) key;
            }
        }
        return null;
    }

    public int buildKeyFrames(float[] keyFrames, int[] mode, int[] pos) {
        if (keyFrames == null) {
            return 0;
        }
        int count = 0;
        double[] time = this.mSpline[0].getTimePoints();
        if (mode != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            while (it.hasNext()) {
                mode[count] = it.next().mMode;
                count++;
            }
            count = 0;
        }
        if (pos != null) {
            Iterator<MotionPaths> it2 = this.mMotionPaths.iterator();
            while (it2.hasNext()) {
                pos[count] = (int) (it2.next().position * 100.0f);
                count++;
            }
            count = 0;
        }
        for (int i = 0; i < time.length; i++) {
            this.mSpline[0].getPos(time[i], this.mInterpolateData);
            this.mStartMotionPath.getCenter(time[i], this.mInterpolateVariables, this.mInterpolateData, keyFrames, count);
            count += 2;
        }
        return count / 2;
    }

    /* access modifiers changed from: package-private */
    public int buildKeyBounds(float[] keyBounds, int[] mode) {
        if (keyBounds == null) {
            return 0;
        }
        int count = 0;
        double[] time = this.mSpline[0].getTimePoints();
        if (mode != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            while (it.hasNext()) {
                mode[count] = it.next().mMode;
                count++;
            }
            count = 0;
        }
        for (double pos : time) {
            this.mSpline[0].getPos(pos, this.mInterpolateData);
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, keyBounds, count);
            count += 2;
        }
        return count / 2;
    }

    /* access modifiers changed from: package-private */
    public int getAttributeValues(String attributeType, float[] points, int pointCount) {
        float f = 1.0f / ((float) (pointCount - 1));
        SplineSet spline = this.mAttributesMap.get(attributeType);
        if (spline == null) {
            return -1;
        }
        for (int j = 0; j < points.length; j++) {
            points[j] = spline.get((float) (j / (points.length - 1)));
        }
        return points.length;
    }

    public void buildRect(float p, float[] path, int offset) {
        this.mSpline[0].getPos((double) getAdjustedPosition(p, (float[]) null), this.mInterpolateData);
        this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, offset);
    }

    /* access modifiers changed from: package-private */
    public void buildRectangles(float[] path, int pointCount) {
        float mils = 1.0f / ((float) (pointCount - 1));
        for (int i = 0; i < pointCount; i++) {
            this.mSpline[0].getPos((double) getAdjustedPosition(((float) i) * mils, (float[]) null), this.mInterpolateData);
            this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, i * 8);
        }
    }

    /* access modifiers changed from: package-private */
    public float getKeyFrameParameter(int type, float x, float y) {
        float dx = this.mEndMotionPath.f984x - this.mStartMotionPath.f984x;
        float dy = this.mEndMotionPath.f985y - this.mStartMotionPath.f985y;
        float startCenterX = this.mStartMotionPath.f984x + (this.mStartMotionPath.width / 2.0f);
        float startCenterY = this.mStartMotionPath.f985y + (this.mStartMotionPath.height / 2.0f);
        float hypotenuse = (float) Math.hypot((double) dx, (double) dy);
        if (((double) hypotenuse) < 1.0E-7d) {
            return Float.NaN;
        }
        float vx = x - startCenterX;
        float vy = y - startCenterY;
        if (((float) Math.hypot((double) vx, (double) vy)) == 0.0f) {
            return 0.0f;
        }
        float pathDistance = (vx * dx) + (vy * dy);
        switch (type) {
            case 0:
                return pathDistance / hypotenuse;
            case 1:
                return (float) Math.sqrt((double) ((hypotenuse * hypotenuse) - (pathDistance * pathDistance)));
            case 2:
                return vx / dx;
            case 3:
                return vy / dx;
            case 4:
                return vx / dy;
            case 5:
                return vy / dy;
            default:
                return 0.0f;
        }
    }

    private void insertKey(MotionPaths point) {
        MotionPaths redundant = null;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            MotionPaths p = it.next();
            if (point.position == p.position) {
                redundant = p;
            }
        }
        if (redundant != null) {
            this.mMotionPaths.remove(redundant);
        }
        int pos = Collections.binarySearch(this.mMotionPaths, point);
        if (pos == 0) {
            Utils.loge(TAG, " KeyPath position \"" + point.position + "\" outside of range");
        }
        this.mMotionPaths.add((-pos) - 1, point);
    }

    /* access modifiers changed from: package-private */
    public void addKeys(ArrayList<MotionKey> list) {
        this.mKeyList.addAll(list);
    }

    public void addKey(MotionKey key) {
        this.mKeyList.add(key);
    }

    public void setPathMotionArc(int arc) {
        this.mPathMotionArc = arc;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v36, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v32, resolved type: double[][]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setup(int r30, int r31, float r32, long r33) {
        /*
            r29 = this;
            r0 = r29
            r1 = r33
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            java.util.HashSet r5 = new java.util.HashSet
            r5.<init>()
            java.util.HashSet r6 = new java.util.HashSet
            r6.<init>()
            java.util.HashMap r7 = new java.util.HashMap
            r7.<init>()
            r8 = 0
            int r9 = r0.mPathMotionArc
            r10 = -1
            if (r9 == r10) goto L_0x0027
            androidx.constraintlayout.core.motion.MotionPaths r11 = r0.mStartMotionPath
            r11.mPathMotionArc = r9
        L_0x0027:
            androidx.constraintlayout.core.motion.MotionConstrainedPoint r9 = r0.mStartPoint
            androidx.constraintlayout.core.motion.MotionConstrainedPoint r11 = r0.mEndPoint
            r9.different(r11, r5)
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r9 = r0.mKeyList
            if (r9 == 0) goto L_0x0096
            java.util.Iterator r9 = r9.iterator()
        L_0x0036:
            boolean r11 = r9.hasNext()
            if (r11 == 0) goto L_0x0096
            java.lang.Object r11 = r9.next()
            androidx.constraintlayout.core.motion.key.MotionKey r11 = (androidx.constraintlayout.core.motion.key.MotionKey) r11
            boolean r12 = r11 instanceof androidx.constraintlayout.core.motion.key.MotionKeyPosition
            if (r12 == 0) goto L_0x006b
            r12 = r11
            androidx.constraintlayout.core.motion.key.MotionKeyPosition r12 = (androidx.constraintlayout.core.motion.key.MotionKeyPosition) r12
            androidx.constraintlayout.core.motion.MotionPaths r15 = new androidx.constraintlayout.core.motion.MotionPaths
            androidx.constraintlayout.core.motion.MotionPaths r14 = r0.mStartMotionPath
            androidx.constraintlayout.core.motion.MotionPaths r13 = r0.mEndMotionPath
            r18 = r13
            r13 = r15
            r17 = r14
            r14 = r30
            r10 = r15
            r15 = r31
            r16 = r12
            r13.<init>(r14, r15, r16, r17, r18)
            r0.insertKey(r10)
            int r10 = r12.mCurveFit
            r13 = -1
            if (r10 == r13) goto L_0x006a
            int r10 = r12.mCurveFit
            r0.mCurveFitType = r10
        L_0x006a:
            goto L_0x0094
        L_0x006b:
            boolean r10 = r11 instanceof androidx.constraintlayout.core.motion.key.MotionKeyCycle
            if (r10 == 0) goto L_0x0073
            r11.getAttributeNames(r6)
            goto L_0x0094
        L_0x0073:
            boolean r10 = r11 instanceof androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle
            if (r10 == 0) goto L_0x007b
            r11.getAttributeNames(r4)
            goto L_0x0094
        L_0x007b:
            boolean r10 = r11 instanceof androidx.constraintlayout.core.motion.key.MotionKeyTrigger
            if (r10 == 0) goto L_0x008e
            if (r8 != 0) goto L_0x0087
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            r8 = r10
        L_0x0087:
            r10 = r11
            androidx.constraintlayout.core.motion.key.MotionKeyTrigger r10 = (androidx.constraintlayout.core.motion.key.MotionKeyTrigger) r10
            r8.add(r10)
            goto L_0x0094
        L_0x008e:
            r11.setInterpolation(r7)
            r11.getAttributeNames(r5)
        L_0x0094:
            r10 = -1
            goto L_0x0036
        L_0x0096:
            r9 = 0
            if (r8 == 0) goto L_0x00a3
            androidx.constraintlayout.core.motion.key.MotionKeyTrigger[] r10 = new androidx.constraintlayout.core.motion.key.MotionKeyTrigger[r9]
            java.lang.Object[] r10 = r8.toArray(r10)
            androidx.constraintlayout.core.motion.key.MotionKeyTrigger[] r10 = (androidx.constraintlayout.core.motion.key.MotionKeyTrigger[]) r10
            r0.mKeyTriggers = r10
        L_0x00a3:
            boolean r10 = r5.isEmpty()
            java.lang.String r11 = ","
            java.lang.String r12 = "CUSTOM,"
            r13 = 1
            if (r10 != 0) goto L_0x01a8
            java.util.HashMap r10 = new java.util.HashMap
            r10.<init>()
            r0.mAttributesMap = r10
            java.util.Iterator r10 = r5.iterator()
        L_0x00b9:
            boolean r14 = r10.hasNext()
            if (r14 == 0) goto L_0x013e
            java.lang.Object r14 = r10.next()
            java.lang.String r14 = (java.lang.String) r14
            boolean r15 = r14.startsWith(r12)
            if (r15 == 0) goto L_0x011d
            androidx.constraintlayout.core.motion.utils.KeyFrameArray$CustomVar r15 = new androidx.constraintlayout.core.motion.utils.KeyFrameArray$CustomVar
            r15.<init>()
            java.lang.String[] r16 = r14.split(r11)
            r9 = r16[r13]
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r13 = r0.mKeyList
            java.util.Iterator r13 = r13.iterator()
        L_0x00dc:
            boolean r18 = r13.hasNext()
            if (r18 == 0) goto L_0x0112
            java.lang.Object r18 = r13.next()
            r19 = r3
            r3 = r18
            androidx.constraintlayout.core.motion.key.MotionKey r3 = (androidx.constraintlayout.core.motion.key.MotionKey) r3
            r18 = r8
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r8 = r3.mCustom
            if (r8 != 0) goto L_0x00f7
            r8 = r18
            r3 = r19
            goto L_0x00dc
        L_0x00f7:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r8 = r3.mCustom
            java.lang.Object r8 = r8.get(r9)
            androidx.constraintlayout.core.motion.CustomVariable r8 = (androidx.constraintlayout.core.motion.CustomVariable) r8
            if (r8 == 0) goto L_0x0109
            r20 = r9
            int r9 = r3.mFramePosition
            r15.append(r9, r8)
            goto L_0x010b
        L_0x0109:
            r20 = r9
        L_0x010b:
            r8 = r18
            r3 = r19
            r9 = r20
            goto L_0x00dc
        L_0x0112:
            r19 = r3
            r18 = r8
            r20 = r9
            androidx.constraintlayout.core.motion.utils.SplineSet r3 = androidx.constraintlayout.core.motion.utils.SplineSet.makeCustomSplineSet(r14, r15)
            goto L_0x0125
        L_0x011d:
            r19 = r3
            r18 = r8
            androidx.constraintlayout.core.motion.utils.SplineSet r3 = androidx.constraintlayout.core.motion.utils.SplineSet.makeSpline(r14, r1)
        L_0x0125:
            if (r3 != 0) goto L_0x012e
            r8 = r18
            r3 = r19
            r9 = 0
            r13 = 1
            goto L_0x00b9
        L_0x012e:
            r3.setType(r14)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r8 = r0.mAttributesMap
            r8.put(r14, r3)
            r8 = r18
            r3 = r19
            r9 = 0
            r13 = 1
            goto L_0x00b9
        L_0x013e:
            r19 = r3
            r18 = r8
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r3 = r0.mKeyList
            if (r3 == 0) goto L_0x0160
            java.util.Iterator r3 = r3.iterator()
        L_0x014a:
            boolean r8 = r3.hasNext()
            if (r8 == 0) goto L_0x0160
            java.lang.Object r8 = r3.next()
            androidx.constraintlayout.core.motion.key.MotionKey r8 = (androidx.constraintlayout.core.motion.key.MotionKey) r8
            boolean r9 = r8 instanceof androidx.constraintlayout.core.motion.key.MotionKeyAttributes
            if (r9 == 0) goto L_0x015f
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r9 = r0.mAttributesMap
            r8.addValues(r9)
        L_0x015f:
            goto L_0x014a
        L_0x0160:
            androidx.constraintlayout.core.motion.MotionConstrainedPoint r3 = r0.mStartPoint
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r8 = r0.mAttributesMap
            r9 = 0
            r3.addValues(r8, r9)
            androidx.constraintlayout.core.motion.MotionConstrainedPoint r3 = r0.mEndPoint
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r8 = r0.mAttributesMap
            r9 = 100
            r3.addValues(r8, r9)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r3 = r0.mAttributesMap
            java.util.Set r3 = r3.keySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x017b:
            boolean r8 = r3.hasNext()
            if (r8 == 0) goto L_0x01ac
            java.lang.Object r8 = r3.next()
            java.lang.String r8 = (java.lang.String) r8
            r9 = 0
            boolean r10 = r7.containsKey(r8)
            if (r10 == 0) goto L_0x019a
            java.lang.Object r10 = r7.get(r8)
            java.lang.Integer r10 = (java.lang.Integer) r10
            if (r10 == 0) goto L_0x019a
            int r9 = r10.intValue()
        L_0x019a:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r10 = r0.mAttributesMap
            java.lang.Object r10 = r10.get(r8)
            androidx.constraintlayout.core.motion.utils.SplineSet r10 = (androidx.constraintlayout.core.motion.utils.SplineSet) r10
            if (r10 == 0) goto L_0x01a7
            r10.setup(r9)
        L_0x01a7:
            goto L_0x017b
        L_0x01a8:
            r19 = r3
            r18 = r8
        L_0x01ac:
            boolean r3 = r4.isEmpty()
            if (r3 != 0) goto L_0x0293
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r3 = r0.mTimeCycleAttributesMap
            if (r3 != 0) goto L_0x01bd
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            r0.mTimeCycleAttributesMap = r3
        L_0x01bd:
            java.util.Iterator r3 = r4.iterator()
        L_0x01c1:
            boolean r8 = r3.hasNext()
            if (r8 == 0) goto L_0x023d
            java.lang.Object r8 = r3.next()
            java.lang.String r8 = (java.lang.String) r8
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r9 = r0.mTimeCycleAttributesMap
            boolean r9 = r9.containsKey(r8)
            if (r9 == 0) goto L_0x01d6
            goto L_0x01c1
        L_0x01d6:
            r9 = 0
            boolean r10 = r8.startsWith(r12)
            if (r10 == 0) goto L_0x0226
            androidx.constraintlayout.core.motion.utils.KeyFrameArray$CustomVar r10 = new androidx.constraintlayout.core.motion.utils.KeyFrameArray$CustomVar
            r10.<init>()
            java.lang.String[] r13 = r8.split(r11)
            r14 = 1
            r13 = r13[r14]
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r14 = r0.mKeyList
            java.util.Iterator r14 = r14.iterator()
        L_0x01ef:
            boolean r15 = r14.hasNext()
            if (r15 == 0) goto L_0x021d
            java.lang.Object r15 = r14.next()
            androidx.constraintlayout.core.motion.key.MotionKey r15 = (androidx.constraintlayout.core.motion.key.MotionKey) r15
            r20 = r3
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r3 = r15.mCustom
            if (r3 != 0) goto L_0x0204
            r3 = r20
            goto L_0x01ef
        L_0x0204:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r3 = r15.mCustom
            java.lang.Object r3 = r3.get(r13)
            androidx.constraintlayout.core.motion.CustomVariable r3 = (androidx.constraintlayout.core.motion.CustomVariable) r3
            if (r3 == 0) goto L_0x0216
            r21 = r4
            int r4 = r15.mFramePosition
            r10.append(r4, r3)
            goto L_0x0218
        L_0x0216:
            r21 = r4
        L_0x0218:
            r3 = r20
            r4 = r21
            goto L_0x01ef
        L_0x021d:
            r20 = r3
            r21 = r4
            androidx.constraintlayout.core.motion.utils.SplineSet r3 = androidx.constraintlayout.core.motion.utils.SplineSet.makeCustomSplineSet(r8, r10)
            goto L_0x022e
        L_0x0226:
            r20 = r3
            r21 = r4
            androidx.constraintlayout.core.motion.utils.SplineSet r3 = androidx.constraintlayout.core.motion.utils.SplineSet.makeSpline(r8, r1)
        L_0x022e:
            if (r3 != 0) goto L_0x0235
            r3 = r20
            r4 = r21
            goto L_0x01c1
        L_0x0235:
            r3.setType(r8)
            r3 = r20
            r4 = r21
            goto L_0x01c1
        L_0x023d:
            r21 = r4
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r3 = r0.mKeyList
            if (r3 == 0) goto L_0x0260
            java.util.Iterator r3 = r3.iterator()
        L_0x0247:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0260
            java.lang.Object r4 = r3.next()
            androidx.constraintlayout.core.motion.key.MotionKey r4 = (androidx.constraintlayout.core.motion.key.MotionKey) r4
            boolean r8 = r4 instanceof androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle
            if (r8 == 0) goto L_0x025f
            r8 = r4
            androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle r8 = (androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle) r8
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r9 = r0.mTimeCycleAttributesMap
            r8.addTimeValues(r9)
        L_0x025f:
            goto L_0x0247
        L_0x0260:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r3 = r0.mTimeCycleAttributesMap
            java.util.Set r3 = r3.keySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x026a:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0295
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            r8 = 0
            boolean r9 = r7.containsKey(r4)
            if (r9 == 0) goto L_0x0287
            java.lang.Object r9 = r7.get(r4)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r8 = r9.intValue()
        L_0x0287:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r9 = r0.mTimeCycleAttributesMap
            java.lang.Object r9 = r9.get(r4)
            androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet r9 = (androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet) r9
            r9.setup(r8)
            goto L_0x026a
        L_0x0293:
            r21 = r4
        L_0x0295:
            java.util.ArrayList<androidx.constraintlayout.core.motion.MotionPaths> r3 = r0.mMotionPaths
            int r3 = r3.size()
            r4 = 2
            int r3 = r3 + r4
            androidx.constraintlayout.core.motion.MotionPaths[] r3 = new androidx.constraintlayout.core.motion.MotionPaths[r3]
            r8 = 1
            androidx.constraintlayout.core.motion.MotionPaths r9 = r0.mStartMotionPath
            r10 = 0
            r3[r10] = r9
            int r9 = r3.length
            r10 = 1
            int r9 = r9 - r10
            androidx.constraintlayout.core.motion.MotionPaths r10 = r0.mEndMotionPath
            r3[r9] = r10
            java.util.ArrayList<androidx.constraintlayout.core.motion.MotionPaths> r9 = r0.mMotionPaths
            int r9 = r9.size()
            if (r9 <= 0) goto L_0x02bd
            int r9 = r0.mCurveFitType
            int r10 = androidx.constraintlayout.core.motion.key.MotionKey.UNSET
            if (r9 != r10) goto L_0x02bd
            r9 = 0
            r0.mCurveFitType = r9
        L_0x02bd:
            java.util.ArrayList<androidx.constraintlayout.core.motion.MotionPaths> r9 = r0.mMotionPaths
            java.util.Iterator r9 = r9.iterator()
        L_0x02c3:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x02d5
            java.lang.Object r10 = r9.next()
            androidx.constraintlayout.core.motion.MotionPaths r10 = (androidx.constraintlayout.core.motion.MotionPaths) r10
            int r11 = r8 + 1
            r3[r8] = r10
            r8 = r11
            goto L_0x02c3
        L_0x02d5:
            r9 = 18
            java.util.HashSet r10 = new java.util.HashSet
            r10.<init>()
            androidx.constraintlayout.core.motion.MotionPaths r11 = r0.mEndMotionPath
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r11 = r11.customAttributes
            java.util.Set r11 = r11.keySet()
            java.util.Iterator r11 = r11.iterator()
        L_0x02e8:
            boolean r13 = r11.hasNext()
            if (r13 == 0) goto L_0x0319
            java.lang.Object r13 = r11.next()
            java.lang.String r13 = (java.lang.String) r13
            androidx.constraintlayout.core.motion.MotionPaths r14 = r0.mStartMotionPath
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r14 = r14.customAttributes
            boolean r14 = r14.containsKey(r13)
            if (r14 == 0) goto L_0x0318
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.StringBuilder r14 = r14.append(r12)
            java.lang.StringBuilder r14 = r14.append(r13)
            java.lang.String r14 = r14.toString()
            boolean r14 = r5.contains(r14)
            if (r14 != 0) goto L_0x0318
            r10.add(r13)
        L_0x0318:
            goto L_0x02e8
        L_0x0319:
            r11 = 0
            java.lang.String[] r12 = new java.lang.String[r11]
            java.lang.Object[] r11 = r10.toArray(r12)
            java.lang.String[] r11 = (java.lang.String[]) r11
            r0.mAttributeNames = r11
            int r11 = r11.length
            int[] r11 = new int[r11]
            r0.mAttributeInterpolatorCount = r11
            r11 = 0
        L_0x032a:
            java.lang.String[] r12 = r0.mAttributeNames
            int r13 = r12.length
            if (r11 >= r13) goto L_0x0363
            r12 = r12[r11]
            int[] r13 = r0.mAttributeInterpolatorCount
            r14 = 0
            r13[r11] = r14
            r13 = 0
        L_0x0337:
            int r14 = r3.length
            if (r13 >= r14) goto L_0x0360
            r14 = r3[r13]
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r14 = r14.customAttributes
            boolean r14 = r14.containsKey(r12)
            if (r14 == 0) goto L_0x035d
            r14 = r3[r13]
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r14 = r14.customAttributes
            java.lang.Object r14 = r14.get(r12)
            androidx.constraintlayout.core.motion.CustomVariable r14 = (androidx.constraintlayout.core.motion.CustomVariable) r14
            if (r14 == 0) goto L_0x035d
            int[] r15 = r0.mAttributeInterpolatorCount
            r20 = r15[r11]
            int r22 = r14.numberOfInterpolatedValues()
            int r20 = r20 + r22
            r15[r11] = r20
            goto L_0x0360
        L_0x035d:
            int r13 = r13 + 1
            goto L_0x0337
        L_0x0360:
            int r11 = r11 + 1
            goto L_0x032a
        L_0x0363:
            r11 = 0
            r12 = r3[r11]
            int r11 = r12.mPathMotionArc
            r12 = -1
            if (r11 == r12) goto L_0x036d
            r11 = 1
            goto L_0x036e
        L_0x036d:
            r11 = 0
        L_0x036e:
            java.lang.String[] r12 = r0.mAttributeNames
            int r12 = r12.length
            int r12 = r12 + r9
            boolean[] r12 = new boolean[r12]
            r13 = 1
        L_0x0375:
            int r14 = r3.length
            if (r13 >= r14) goto L_0x0387
            r14 = r3[r13]
            int r15 = r13 + -1
            r15 = r3[r15]
            java.lang.String[] r4 = r0.mAttributeNames
            r14.different(r15, r12, r4, r11)
            int r13 = r13 + 1
            r4 = 2
            goto L_0x0375
        L_0x0387:
            r4 = 0
            r8 = 1
        L_0x0389:
            int r13 = r12.length
            if (r8 >= r13) goto L_0x0395
            boolean r13 = r12[r8]
            if (r13 == 0) goto L_0x0392
            int r4 = r4 + 1
        L_0x0392:
            int r8 = r8 + 1
            goto L_0x0389
        L_0x0395:
            int[] r8 = new int[r4]
            r0.mInterpolateVariables = r8
            r8 = 2
            int r13 = java.lang.Math.max(r8, r4)
            double[] r8 = new double[r13]
            r0.mInterpolateData = r8
            double[] r8 = new double[r13]
            r0.mInterpolateVelocity = r8
            r4 = 0
            r8 = 1
        L_0x03a8:
            int r14 = r12.length
            if (r8 >= r14) goto L_0x03b9
            boolean r14 = r12[r8]
            if (r14 == 0) goto L_0x03b6
            int[] r14 = r0.mInterpolateVariables
            int r15 = r4 + 1
            r14[r4] = r8
            r4 = r15
        L_0x03b6:
            int r8 = r8 + 1
            goto L_0x03a8
        L_0x03b9:
            int r8 = r3.length
            int[] r14 = r0.mInterpolateVariables
            int r14 = r14.length
            r15 = 2
            int[] r1 = new int[r15]
            r2 = 1
            r1[r2] = r14
            r2 = 0
            r1[r2] = r8
            java.lang.Class r2 = java.lang.Double.TYPE
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r2, r1)
            double[][] r1 = (double[][]) r1
            int r2 = r3.length
            double[] r2 = new double[r2]
            r8 = 0
        L_0x03d2:
            int r14 = r3.length
            if (r8 >= r14) goto L_0x03ec
            r14 = r3[r8]
            r15 = r1[r8]
            r22 = r4
            int[] r4 = r0.mInterpolateVariables
            r14.fillStandard(r15, r4)
            r4 = r3[r8]
            float r4 = r4.time
            double r14 = (double) r4
            r2[r8] = r14
            int r8 = r8 + 1
            r4 = r22
            goto L_0x03d2
        L_0x03ec:
            r22 = r4
            r4 = 0
        L_0x03ef:
            int[] r8 = r0.mInterpolateVariables
            int r14 = r8.length
            if (r4 >= r14) goto L_0x044e
            r8 = r8[r4]
            java.lang.String[] r14 = androidx.constraintlayout.core.motion.MotionPaths.names
            int r14 = r14.length
            if (r8 >= r14) goto L_0x0441
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String[] r15 = androidx.constraintlayout.core.motion.MotionPaths.names
            r23 = r5
            int[] r5 = r0.mInterpolateVariables
            r5 = r5[r4]
            r5 = r15[r5]
            java.lang.StringBuilder r5 = r14.append(r5)
            java.lang.String r14 = " ["
            java.lang.StringBuilder r5 = r5.append(r14)
            java.lang.String r5 = r5.toString()
            r14 = 0
        L_0x0419:
            int r15 = r3.length
            if (r14 >= r15) goto L_0x043c
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.StringBuilder r15 = r15.append(r5)
            r24 = r1[r14]
            r25 = r7
            r26 = r8
            r7 = r24[r4]
            java.lang.StringBuilder r7 = r15.append(r7)
            java.lang.String r5 = r7.toString()
            int r14 = r14 + 1
            r7 = r25
            r8 = r26
            goto L_0x0419
        L_0x043c:
            r25 = r7
            r26 = r8
            goto L_0x0447
        L_0x0441:
            r23 = r5
            r25 = r7
            r26 = r8
        L_0x0447:
            int r4 = r4 + 1
            r5 = r23
            r7 = r25
            goto L_0x03ef
        L_0x044e:
            r23 = r5
            r25 = r7
            java.lang.String[] r4 = r0.mAttributeNames
            int r4 = r4.length
            r5 = 1
            int r4 = r4 + r5
            androidx.constraintlayout.core.motion.utils.CurveFit[] r4 = new androidx.constraintlayout.core.motion.utils.CurveFit[r4]
            r0.mSpline = r4
            r4 = 0
        L_0x045c:
            java.lang.String[] r5 = r0.mAttributeNames
            int r7 = r5.length
            if (r4 >= r7) goto L_0x04ed
            r7 = 0
            r8 = 0
            double[][] r8 = (double[][]) r8
            r14 = 0
            r5 = r5[r4]
            r15 = 0
        L_0x0469:
            r24 = r9
            int r9 = r3.length
            if (r15 >= r9) goto L_0x04c4
            r9 = r3[r15]
            boolean r9 = r9.hasCustomData(r5)
            if (r9 == 0) goto L_0x04b3
            if (r8 != 0) goto L_0x049b
            int r9 = r3.length
            double[] r14 = new double[r9]
            int r9 = r3.length
            r26 = r10
            r10 = r3[r15]
            int r10 = r10.getCustomDataCount(r5)
            r27 = r11
            r28 = r12
            r11 = 2
            int[] r12 = new int[r11]
            r11 = 1
            r12[r11] = r10
            r10 = 0
            r12[r10] = r9
            java.lang.Class r9 = java.lang.Double.TYPE
            java.lang.Object r9 = java.lang.reflect.Array.newInstance(r9, r12)
            r8 = r9
            double[][] r8 = (double[][]) r8
            goto L_0x04a1
        L_0x049b:
            r26 = r10
            r27 = r11
            r28 = r12
        L_0x04a1:
            r9 = r3[r15]
            float r9 = r9.time
            double r9 = (double) r9
            r14[r7] = r9
            r9 = r3[r15]
            r10 = r8[r7]
            r11 = 0
            r9.getCustomData(r5, r10, r11)
            int r7 = r7 + 1
            goto L_0x04b9
        L_0x04b3:
            r26 = r10
            r27 = r11
            r28 = r12
        L_0x04b9:
            int r15 = r15 + 1
            r9 = r24
            r10 = r26
            r11 = r27
            r12 = r28
            goto L_0x0469
        L_0x04c4:
            r26 = r10
            r27 = r11
            r28 = r12
            double[] r9 = java.util.Arrays.copyOf(r14, r7)
            java.lang.Object[] r10 = java.util.Arrays.copyOf(r8, r7)
            r8 = r10
            double[][] r8 = (double[][]) r8
            androidx.constraintlayout.core.motion.utils.CurveFit[] r10 = r0.mSpline
            int r11 = r4 + 1
            int r12 = r0.mCurveFitType
            androidx.constraintlayout.core.motion.utils.CurveFit r12 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r12, r9, r8)
            r10[r11] = r12
            int r4 = r4 + 1
            r9 = r24
            r10 = r26
            r11 = r27
            r12 = r28
            goto L_0x045c
        L_0x04ed:
            r24 = r9
            r26 = r10
            r27 = r11
            r28 = r12
            androidx.constraintlayout.core.motion.utils.CurveFit[] r4 = r0.mSpline
            int r5 = r0.mCurveFitType
            androidx.constraintlayout.core.motion.utils.CurveFit r5 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r5, r2, r1)
            r7 = 0
            r4[r7] = r5
            r4 = r3[r7]
            int r4 = r4.mPathMotionArc
            r5 = -1
            if (r4 == r5) goto L_0x054a
            int r4 = r3.length
            int[] r5 = new int[r4]
            double[] r7 = new double[r4]
            r8 = 2
            int[] r9 = new int[r8]
            r10 = 1
            r9[r10] = r8
            r8 = 0
            r9[r8] = r4
            java.lang.Class r8 = java.lang.Double.TYPE
            java.lang.Object r8 = java.lang.reflect.Array.newInstance(r8, r9)
            double[][] r8 = (double[][]) r8
            r9 = 0
        L_0x051e:
            if (r9 >= r4) goto L_0x0544
            r10 = r3[r9]
            int r10 = r10.mPathMotionArc
            r5[r9] = r10
            r10 = r3[r9]
            float r10 = r10.time
            double r10 = (double) r10
            r7[r9] = r10
            r10 = r8[r9]
            r11 = r3[r9]
            float r11 = r11.f984x
            double r11 = (double) r11
            r14 = 0
            r10[r14] = r11
            r10 = r8[r9]
            r11 = r3[r9]
            float r11 = r11.f985y
            double r11 = (double) r11
            r15 = 1
            r10[r15] = r11
            int r9 = r9 + 1
            goto L_0x051e
        L_0x0544:
            androidx.constraintlayout.core.motion.utils.CurveFit r9 = androidx.constraintlayout.core.motion.utils.CurveFit.getArc(r5, r7, r8)
            r0.mArcSpline = r9
        L_0x054a:
            r4 = 2143289344(0x7fc00000, float:NaN)
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r0.mCycleMap = r5
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r5 = r0.mKeyList
            if (r5 == 0) goto L_0x05c0
            java.util.Iterator r5 = r6.iterator()
        L_0x055b:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x0587
            java.lang.Object r7 = r5.next()
            java.lang.String r7 = (java.lang.String) r7
            androidx.constraintlayout.core.motion.utils.KeyCycleOscillator r8 = androidx.constraintlayout.core.motion.utils.KeyCycleOscillator.makeWidgetCycle(r7)
            if (r8 != 0) goto L_0x056e
            goto L_0x055b
        L_0x056e:
            boolean r9 = r8.variesByPath()
            if (r9 == 0) goto L_0x057e
            boolean r9 = java.lang.Float.isNaN(r4)
            if (r9 == 0) goto L_0x057e
            float r4 = r29.getPreCycleDistance()
        L_0x057e:
            r8.setType(r7)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.KeyCycleOscillator> r9 = r0.mCycleMap
            r9.put(r7, r8)
            goto L_0x055b
        L_0x0587:
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r5 = r0.mKeyList
            java.util.Iterator r5 = r5.iterator()
        L_0x058d:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x05a6
            java.lang.Object r7 = r5.next()
            androidx.constraintlayout.core.motion.key.MotionKey r7 = (androidx.constraintlayout.core.motion.key.MotionKey) r7
            boolean r8 = r7 instanceof androidx.constraintlayout.core.motion.key.MotionKeyCycle
            if (r8 == 0) goto L_0x05a5
            r8 = r7
            androidx.constraintlayout.core.motion.key.MotionKeyCycle r8 = (androidx.constraintlayout.core.motion.key.MotionKeyCycle) r8
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.KeyCycleOscillator> r9 = r0.mCycleMap
            r8.addCycleValues(r9)
        L_0x05a5:
            goto L_0x058d
        L_0x05a6:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.KeyCycleOscillator> r5 = r0.mCycleMap
            java.util.Collection r5 = r5.values()
            java.util.Iterator r5 = r5.iterator()
        L_0x05b0:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x05c0
            java.lang.Object r7 = r5.next()
            androidx.constraintlayout.core.motion.utils.KeyCycleOscillator r7 = (androidx.constraintlayout.core.motion.utils.KeyCycleOscillator) r7
            r7.setup(r4)
            goto L_0x05b0
        L_0x05c0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.Motion.setup(int, int, float, long):void");
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.f984x + " y: " + this.mStartMotionPath.f985y + " end: x: " + this.mEndMotionPath.f984x + " y: " + this.mEndMotionPath.f985y;
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((float) this.mView.getX(), (float) this.mView.getY(), (float) this.mView.getWidth(), (float) this.mView.getHeight());
    }

    public void setView(MotionWidget view) {
        this.mView = view;
    }

    public MotionWidget getView() {
        return this.mView;
    }

    public void setStart(MotionWidget mw) {
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        this.mStartMotionPath.setBounds((float) mw.getX(), (float) mw.getY(), (float) mw.getWidth(), (float) mw.getHeight());
        this.mStartMotionPath.applyParameters(mw);
        this.mStartPoint.setState(mw);
    }

    public void setEnd(MotionWidget mw) {
        this.mEndMotionPath.time = 1.0f;
        this.mEndMotionPath.position = 1.0f;
        readView(this.mEndMotionPath);
        this.mEndMotionPath.setBounds((float) mw.getLeft(), (float) mw.getTop(), (float) mw.getWidth(), (float) mw.getHeight());
        this.mEndMotionPath.applyParameters(mw);
        this.mEndPoint.setState(mw);
    }

    public void setStartState(ViewState rect, MotionWidget v, int rotation, int preWidth, int preHeight) {
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        Rect r = new Rect();
        switch (rotation) {
            case 1:
                int cx = rect.left + rect.right;
                r.left = ((rect.top + rect.bottom) - rect.width()) / 2;
                r.top = preWidth - ((rect.height() + cx) / 2);
                r.right = r.left + rect.width();
                r.bottom = r.top + rect.height();
                break;
            case 2:
                int cx2 = rect.left + rect.right;
                r.left = preHeight - ((rect.width() + (rect.top + rect.bottom)) / 2);
                r.top = (cx2 - rect.height()) / 2;
                r.right = r.left + rect.width();
                r.bottom = r.top + rect.height();
                break;
        }
        this.mStartMotionPath.setBounds((float) r.left, (float) r.top, (float) r.width(), (float) r.height());
        this.mStartPoint.setState(r, v, rotation, rect.rotation);
    }

    /* access modifiers changed from: package-private */
    public void rotate(Rect rect, Rect out, int rotation, int preHeight, int preWidth) {
        switch (rotation) {
            case 1:
                int cx = rect.left + rect.right;
                out.left = ((rect.top + rect.bottom) - rect.width()) / 2;
                out.top = preWidth - ((rect.height() + cx) / 2);
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            case 2:
                int cx2 = rect.left + rect.right;
                out.left = preHeight - ((rect.width() + (rect.top + rect.bottom)) / 2);
                out.top = (cx2 - rect.height()) / 2;
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            case 3:
                int cx3 = rect.left + rect.right;
                int i = rect.top + rect.bottom;
                out.left = ((rect.height() / 2) + rect.top) - (cx3 / 2);
                out.top = preWidth - ((rect.height() + cx3) / 2);
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            case 4:
                int cx4 = rect.left + rect.right;
                out.left = preHeight - ((rect.width() + (rect.bottom + rect.top)) / 2);
                out.top = (cx4 - rect.height()) / 2;
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            default:
                return;
        }
    }

    private static DifferentialInterpolator getInterpolator(int type, String interpolatorString, int id) {
        switch (type) {
            case -1:
                final Easing easing = Easing.getInterpolator(interpolatorString);
                return new DifferentialInterpolator() {

                    /* renamed from: mX */
                    float f981mX;

                    public float getInterpolation(float x) {
                        this.f981mX = x;
                        return (float) easing.get((double) x);
                    }

                    public float getVelocity() {
                        return (float) easing.getDiff((double) this.f981mX);
                    }
                };
            default:
                return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void setBothStates(MotionWidget v) {
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        this.mNoMovement = true;
        this.mStartMotionPath.setBounds((float) v.getX(), (float) v.getY(), (float) v.getWidth(), (float) v.getHeight());
        this.mEndMotionPath.setBounds((float) v.getX(), (float) v.getY(), (float) v.getWidth(), (float) v.getHeight());
        this.mStartPoint.setState(v);
        this.mEndPoint.setState(v);
    }

    private float getAdjustedPosition(float position, float[] velocity) {
        if (velocity != null) {
            velocity[0] = 1.0f;
        } else {
            float f = this.mStaggerScale;
            if (((double) f) != 1.0d) {
                float f2 = this.mStaggerOffset;
                if (position < f2) {
                    position = 0.0f;
                }
                if (position > f2 && ((double) position) < 1.0d) {
                    position = Math.min((position - f2) * f, 1.0f);
                }
            }
        }
        float adjusted = position;
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        float start = 0.0f;
        float end = Float.NaN;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            MotionPaths frame = it.next();
            if (frame.mKeyFrameEasing != null) {
                if (frame.time < position) {
                    easing = frame.mKeyFrameEasing;
                    start = frame.time;
                } else if (Float.isNaN(end)) {
                    end = frame.time;
                }
            }
        }
        if (easing != null) {
            if (Float.isNaN(end)) {
                end = 1.0f;
            }
            float offset = (position - start) / (end - start);
            adjusted = ((end - start) * ((float) easing.get((double) offset))) + start;
            if (velocity != null) {
                velocity[0] = (float) easing.getDiff((double) offset);
            }
        }
        return adjusted;
    }

    /* access modifiers changed from: package-private */
    public void endTrigger(boolean start) {
    }

    public boolean interpolate(MotionWidget child, float global_position, long time, KeyCache keyCache) {
        float position;
        float section;
        MotionWidget motionWidget = child;
        float position2 = getAdjustedPosition(global_position, (float[]) null);
        int i = this.mQuantizeMotionSteps;
        if (i != -1) {
            float f = position2;
            float steps = 1.0f / ((float) i);
            float jump = ((float) Math.floor((double) (position2 / steps))) * steps;
            float section2 = (position2 % steps) / steps;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                section2 = (this.mQuantizeMotionPhase + section2) % 1.0f;
            }
            DifferentialInterpolator differentialInterpolator = this.mQuantizeMotionInterpolator;
            if (differentialInterpolator != null) {
                section = differentialInterpolator.getInterpolation(section2);
            } else {
                section = ((double) section2) > 0.5d ? 1.0f : 0.0f;
            }
            position = (section * steps) + jump;
        } else {
            position = position2;
        }
        HashMap<String, SplineSet> hashMap = this.mAttributesMap;
        if (hashMap != null) {
            for (SplineSet aSpline : hashMap.values()) {
                aSpline.setProperty(motionWidget, position);
            }
        }
        CurveFit[] curveFitArr = this.mSpline;
        if (curveFitArr != null) {
            curveFitArr[0].getPos((double) position, this.mInterpolateData);
            this.mSpline[0].getSlope((double) position, this.mInterpolateVelocity);
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr = this.mInterpolateData;
                if (dArr.length > 0) {
                    curveFit.getPos((double) position, dArr);
                    this.mArcSpline.getSlope((double) position, this.mInterpolateVelocity);
                }
            }
            if (!this.mNoMovement) {
                this.mStartMotionPath.setView(position, child, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, (double[]) null);
            }
            if (this.mTransformPivotTarget != -1) {
                if (this.mTransformPivotView == null) {
                    this.mTransformPivotView = child.getParent().findViewById(this.mTransformPivotTarget);
                }
                MotionWidget layout = this.mTransformPivotView;
                if (layout != null) {
                    float cy = ((float) (layout.getTop() + this.mTransformPivotView.getBottom())) / 2.0f;
                    float cx = ((float) (this.mTransformPivotView.getLeft() + this.mTransformPivotView.getRight())) / 2.0f;
                    if (child.getRight() - child.getLeft() > 0 && child.getBottom() - child.getTop() > 0) {
                        motionWidget.setPivotX(cx - ((float) child.getLeft()));
                        motionWidget.setPivotY(cy - ((float) child.getTop()));
                    }
                }
            }
            int i2 = 1;
            while (true) {
                CurveFit[] curveFitArr2 = this.mSpline;
                if (i2 >= curveFitArr2.length) {
                    break;
                }
                curveFitArr2[i2].getPos((double) position, this.mValuesBuff);
                this.mStartMotionPath.customAttributes.get(this.mAttributeNames[i2 - 1]).setInterpolatedValue(motionWidget, this.mValuesBuff);
                i2++;
            }
            if (this.mStartPoint.mVisibilityMode == 0) {
                if (position <= 0.0f) {
                    motionWidget.setVisibility(this.mStartPoint.visibility);
                } else if (position >= 1.0f) {
                    motionWidget.setVisibility(this.mEndPoint.visibility);
                } else if (this.mEndPoint.visibility != this.mStartPoint.visibility) {
                    motionWidget.setVisibility(4);
                }
            }
            if (this.mKeyTriggers != null) {
                int i3 = 0;
                while (true) {
                    MotionKeyTrigger[] motionKeyTriggerArr = this.mKeyTriggers;
                    if (i3 >= motionKeyTriggerArr.length) {
                        break;
                    }
                    motionKeyTriggerArr[i3].conditionallyFire(position, motionWidget);
                    i3++;
                }
            }
        } else {
            float float_l = this.mStartMotionPath.f984x + ((this.mEndMotionPath.f984x - this.mStartMotionPath.f984x) * position);
            float float_t = this.mStartMotionPath.f985y + ((this.mEndMotionPath.f985y - this.mStartMotionPath.f985y) * position);
            int l = (int) (float_l + 0.5f);
            int t = (int) (float_t + 0.5f);
            int r = (int) (float_l + 0.5f + this.mStartMotionPath.width + ((this.mEndMotionPath.width - this.mStartMotionPath.width) * position));
            int b = (int) (0.5f + float_t + this.mStartMotionPath.height + ((this.mEndMotionPath.height - this.mStartMotionPath.height) * position));
            int i4 = r - l;
            int i5 = b - t;
            motionWidget.layout(l, t, r, b);
        }
        HashMap<String, KeyCycleOscillator> hashMap2 = this.mCycleMap;
        if (hashMap2 != null) {
            for (KeyCycleOscillator osc : hashMap2.values()) {
                if (osc instanceof KeyCycleOscillator.PathRotateSet) {
                    double[] dArr2 = this.mInterpolateVelocity;
                    ((KeyCycleOscillator.PathRotateSet) osc).setPathRotate(child, position, dArr2[0], dArr2[1]);
                } else {
                    osc.setProperty(motionWidget, position);
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void getDpDt(float position, float locationX, float locationY, float[] mAnchorDpDt) {
        double[] dArr;
        float f = position;
        float position2 = getAdjustedPosition(position, this.mVelocity);
        CurveFit[] curveFitArr = this.mSpline;
        if (curveFitArr != null) {
            curveFitArr[0].getSlope((double) position2, this.mInterpolateVelocity);
            this.mSpline[0].getPos((double) position2, this.mInterpolateData);
            float v = this.mVelocity[0];
            int i = 0;
            while (true) {
                dArr = this.mInterpolateVelocity;
                if (i >= dArr.length) {
                    break;
                }
                double d = dArr[i];
                double d2 = (double) v;
                Double.isNaN(d2);
                dArr[i] = d * d2;
                i++;
            }
            CurveFit curveFit = this.mArcSpline;
            if (curveFit != null) {
                double[] dArr2 = this.mInterpolateData;
                if (dArr2.length > 0) {
                    curveFit.getPos((double) position2, dArr2);
                    this.mArcSpline.getSlope((double) position2, this.mInterpolateVelocity);
                    this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
                    return;
                }
                return;
            }
            this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, dArr, this.mInterpolateData);
            return;
        }
        float dleft = this.mEndMotionPath.f984x - this.mStartMotionPath.f984x;
        float dTop = this.mEndMotionPath.f985y - this.mStartMotionPath.f985y;
        float dWidth = this.mEndMotionPath.width - this.mStartMotionPath.width;
        float dHeight = this.mEndMotionPath.height - this.mStartMotionPath.height;
        mAnchorDpDt[0] = ((1.0f - locationX) * dleft) + ((dleft + dWidth) * locationX);
        mAnchorDpDt[1] = ((1.0f - locationY) * dTop) + ((dTop + dHeight) * locationY);
    }

    /* access modifiers changed from: package-private */
    public void getPostLayoutDvDp(float position, int width, int height, float locationX, float locationY, float[] mAnchorDpDt) {
        VelocityMatrix vmat;
        float position2 = getAdjustedPosition(position, this.mVelocity);
        HashMap<String, SplineSet> hashMap = this.mAttributesMap;
        KeyCycleOscillator keyCycleOscillator = null;
        SplineSet trans_x = hashMap == null ? null : hashMap.get("translationX");
        HashMap<String, SplineSet> hashMap2 = this.mAttributesMap;
        SplineSet trans_y = hashMap2 == null ? null : hashMap2.get("translationY");
        HashMap<String, SplineSet> hashMap3 = this.mAttributesMap;
        SplineSet rotation = hashMap3 == null ? null : hashMap3.get("rotationZ");
        HashMap<String, SplineSet> hashMap4 = this.mAttributesMap;
        SplineSet scale_x = hashMap4 == null ? null : hashMap4.get("scaleX");
        HashMap<String, SplineSet> hashMap5 = this.mAttributesMap;
        SplineSet scale_y = hashMap5 == null ? null : hashMap5.get("scaleY");
        HashMap<String, KeyCycleOscillator> hashMap6 = this.mCycleMap;
        KeyCycleOscillator osc_x = hashMap6 == null ? null : hashMap6.get("translationX");
        HashMap<String, KeyCycleOscillator> hashMap7 = this.mCycleMap;
        KeyCycleOscillator osc_y = hashMap7 == null ? null : hashMap7.get("translationY");
        HashMap<String, KeyCycleOscillator> hashMap8 = this.mCycleMap;
        KeyCycleOscillator osc_r = hashMap8 == null ? null : hashMap8.get("rotationZ");
        HashMap<String, KeyCycleOscillator> hashMap9 = this.mCycleMap;
        KeyCycleOscillator osc_sx = hashMap9 == null ? null : hashMap9.get("scaleX");
        HashMap<String, KeyCycleOscillator> hashMap10 = this.mCycleMap;
        if (hashMap10 != null) {
            keyCycleOscillator = hashMap10.get("scaleY");
        }
        KeyCycleOscillator osc_sy = keyCycleOscillator;
        VelocityMatrix vmat2 = new VelocityMatrix();
        vmat2.clear();
        vmat2.setRotationVelocity(rotation, position2);
        vmat2.setTranslationVelocity(trans_x, trans_y, position2);
        vmat2.setScaleVelocity(scale_x, scale_y, position2);
        vmat2.setRotationVelocity(osc_r, position2);
        vmat2.setTranslationVelocity(osc_x, osc_y, position2);
        vmat2.setScaleVelocity(osc_sx, osc_sy, position2);
        CurveFit curveFit = this.mArcSpline;
        if (curveFit != null) {
            double[] dArr = this.mInterpolateData;
            if (dArr.length > 0) {
                curveFit.getPos((double) position2, dArr);
                this.mArcSpline.getSlope((double) position2, this.mInterpolateVelocity);
                vmat = vmat2;
                KeyCycleOscillator keyCycleOscillator2 = osc_x;
                KeyCycleOscillator osc_x2 = osc_r;
                KeyCycleOscillator keyCycleOscillator3 = osc_sx;
                KeyCycleOscillator keyCycleOscillator4 = osc_sy;
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            } else {
                vmat = vmat2;
                KeyCycleOscillator keyCycleOscillator5 = osc_sx;
                KeyCycleOscillator keyCycleOscillator6 = osc_sy;
                KeyCycleOscillator keyCycleOscillator7 = osc_x;
                KeyCycleOscillator osc_x3 = osc_r;
            }
            vmat.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
            return;
        }
        VelocityMatrix vmat3 = vmat2;
        KeyCycleOscillator osc_sx2 = osc_sx;
        KeyCycleOscillator osc_sy2 = osc_sy;
        KeyCycleOscillator osc_x4 = osc_x;
        KeyCycleOscillator osc_x5 = osc_r;
        if (this.mSpline != null) {
            float position3 = getAdjustedPosition(position2, this.mVelocity);
            this.mSpline[0].getSlope((double) position3, this.mInterpolateVelocity);
            this.mSpline[0].getPos((double) position3, this.mInterpolateData);
            float v = this.mVelocity[0];
            int i = 0;
            while (true) {
                double[] dArr2 = this.mInterpolateVelocity;
                if (i < dArr2.length) {
                    double d = dArr2[i];
                    double d2 = (double) v;
                    Double.isNaN(d2);
                    dArr2[i] = d * d2;
                    i++;
                } else {
                    float f = locationX;
                    float f2 = locationY;
                    float f3 = v;
                    this.mStartMotionPath.setDpDt(f, f2, mAnchorDpDt, this.mInterpolateVariables, dArr2, this.mInterpolateData);
                    vmat3.applyTransform(f, f2, width, height, mAnchorDpDt);
                    return;
                }
            }
        } else {
            float dleft = this.mEndMotionPath.f984x - this.mStartMotionPath.f984x;
            float dTop = this.mEndMotionPath.f985y - this.mStartMotionPath.f985y;
            mAnchorDpDt[0] = ((1.0f - locationX) * dleft) + ((dleft + (this.mEndMotionPath.width - this.mStartMotionPath.width)) * locationX);
            mAnchorDpDt[1] = ((1.0f - locationY) * dTop) + ((dTop + (this.mEndMotionPath.height - this.mStartMotionPath.height)) * locationY);
            vmat3.clear();
            VelocityMatrix vmat4 = vmat3;
            vmat4.setRotationVelocity(rotation, position2);
            vmat4.setTranslationVelocity(trans_x, trans_y, position2);
            vmat4.setScaleVelocity(scale_x, scale_y, position2);
            vmat4.setRotationVelocity(osc_x5, position2);
            KeyCycleOscillator osc_x6 = osc_x4;
            vmat4.setTranslationVelocity(osc_x6, osc_y, position2);
            KeyCycleOscillator osc_sy3 = osc_sy2;
            vmat4.setScaleVelocity(osc_sx2, osc_sy3, position2);
            KeyCycleOscillator keyCycleOscillator8 = osc_sy3;
            KeyCycleOscillator keyCycleOscillator9 = osc_x6;
            VelocityMatrix velocityMatrix = vmat4;
            vmat4.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
        }
    }

    public int getDrawPath() {
        int mode = this.mStartMotionPath.mDrawPath;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            mode = Math.max(mode, it.next().mDrawPath);
        }
        return Math.max(mode, this.mEndMotionPath.mDrawPath);
    }

    public void setDrawPath(int debugMode) {
        this.mStartMotionPath.mDrawPath = debugMode;
    }

    /* access modifiers changed from: package-private */
    public String name() {
        return this.mView.getName();
    }

    /* access modifiers changed from: package-private */
    public void positionKeyframe(MotionWidget view, MotionKeyPosition key, float x, float y, String[] attribute, float[] value) {
        FloatRect start = new FloatRect();
        start.left = this.mStartMotionPath.f984x;
        start.top = this.mStartMotionPath.f985y;
        start.right = start.left + this.mStartMotionPath.width;
        start.bottom = start.top + this.mStartMotionPath.height;
        FloatRect end = new FloatRect();
        end.left = this.mEndMotionPath.f984x;
        end.top = this.mEndMotionPath.f985y;
        end.right = end.left + this.mEndMotionPath.width;
        end.bottom = end.top + this.mEndMotionPath.height;
        key.positionAttributes(view, start, end, x, y, attribute, value);
    }

    public int getKeyFramePositions(int[] type, float[] pos) {
        int i = 0;
        int count = 0;
        Iterator<MotionKey> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            MotionKey key = it.next();
            int i2 = i + 1;
            type[i] = key.mFramePosition + (key.mType * 1000);
            float time = ((float) key.mFramePosition) / 100.0f;
            this.mSpline[0].getPos((double) time, this.mInterpolateData);
            this.mStartMotionPath.getCenter((double) time, this.mInterpolateVariables, this.mInterpolateData, pos, count);
            count += 2;
            i = i2;
        }
        return i;
    }

    public int getKeyFrameInfo(int type, int[] info) {
        int i = type;
        int count = 0;
        int cursor = 0;
        float[] pos = new float[2];
        Iterator<MotionKey> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            MotionKey key = it.next();
            if (key.mType == i || i != -1) {
                int len = cursor;
                info[cursor] = 0;
                int cursor2 = cursor + 1;
                info[cursor2] = key.mType;
                int cursor3 = cursor2 + 1;
                info[cursor3] = key.mFramePosition;
                float time = ((float) key.mFramePosition) / 100.0f;
                this.mSpline[0].getPos((double) time, this.mInterpolateData);
                float f = time;
                this.mStartMotionPath.getCenter((double) time, this.mInterpolateVariables, this.mInterpolateData, pos, 0);
                int cursor4 = cursor3 + 1;
                info[cursor4] = Float.floatToIntBits(pos[0]);
                int cursor5 = cursor4 + 1;
                info[cursor5] = Float.floatToIntBits(pos[1]);
                if (key instanceof MotionKeyPosition) {
                    MotionKeyPosition kp = (MotionKeyPosition) key;
                    int cursor6 = cursor5 + 1;
                    info[cursor6] = kp.mPositionType;
                    int cursor7 = cursor6 + 1;
                    info[cursor7] = Float.floatToIntBits(kp.mPercentX);
                    cursor5 = cursor7 + 1;
                    info[cursor5] = Float.floatToIntBits(kp.mPercentY);
                }
                cursor = cursor5 + 1;
                info[len] = cursor - len;
                count++;
            }
        }
        return count;
    }

    public boolean setValue(int id, int value) {
        switch (id) {
            case 509:
                setPathMotionArc(value);
                return true;
            case TypedValues.TransitionType.TYPE_AUTO_TRANSITION /*704*/:
                return true;
            default:
                return false;
        }
    }

    public boolean setValue(int id, float value) {
        return false;
    }

    public boolean setValue(int id, String value) {
        if (705 == id) {
            System.out.println("TYPE_INTERPOLATOR  " + value);
            this.mQuantizeMotionInterpolator = getInterpolator(-1, value, 0);
        }
        return false;
    }

    public boolean setValue(int id, boolean value) {
        return false;
    }

    public int getId(String name) {
        return 0;
    }
}
