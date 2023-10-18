package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.svg.SvgConstants;
import java.util.HashSet;
import java.util.LinkedHashMap;

class MotionConstrainedPoint implements Comparable<MotionConstrainedPoint> {
    static final int CARTESIAN = 2;
    public static final boolean DEBUG = false;
    static final int PERPENDICULAR = 1;
    public static final String TAG = "MotionPaths";
    static String[] names = {CommonCssConstants.POSITION, SvgConstants.Attributes.f1641X, SvgConstants.Attributes.f1644Y, "width", "height", "pathRotate"};
    private float alpha = 1.0f;
    private boolean applyElevation = false;
    LinkedHashMap<String, ConstraintAttribute> attributes = new LinkedHashMap<>();
    private float elevation = 0.0f;
    private float height;
    private int mAnimateRelativeTo = -1;
    private int mDrawPath = 0;
    private Easing mKeyFrameEasing;
    int mMode = 0;
    private float mPathRotate = Float.NaN;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float mProgress = Float.NaN;
    double[] mTempDelta = new double[18];
    double[] mTempValue = new double[18];
    int mVisibilityMode = 0;
    private float position;
    private float rotation = 0.0f;
    private float rotationX = 0.0f;
    public float rotationY = 0.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float translationX = 0.0f;
    private float translationY = 0.0f;
    private float translationZ = 0.0f;
    int visibility;
    private float width;

    /* renamed from: x */
    private float f1016x;

    /* renamed from: y */
    private float f1017y;

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
    public void different(MotionConstrainedPoint points, HashSet<String> keySet) {
        if (diff(this.alpha, points.alpha)) {
            keySet.add("alpha");
        }
        if (diff(this.elevation, points.elevation)) {
            keySet.add("elevation");
        }
        int i = this.visibility;
        int i2 = points.visibility;
        if (i != i2 && this.mVisibilityMode == 0 && (i == 0 || i2 == 0)) {
            keySet.add("alpha");
        }
        if (diff(this.rotation, points.rotation)) {
            keySet.add("rotation");
        }
        if (!Float.isNaN(this.mPathRotate) || !Float.isNaN(points.mPathRotate)) {
            keySet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.mProgress) || !Float.isNaN(points.mProgress)) {
            keySet.add("progress");
        }
        if (diff(this.rotationX, points.rotationX)) {
            keySet.add("rotationX");
        }
        if (diff(this.rotationY, points.rotationY)) {
            keySet.add("rotationY");
        }
        if (diff(this.mPivotX, points.mPivotX)) {
            keySet.add(Key.PIVOT_X);
        }
        if (diff(this.mPivotY, points.mPivotY)) {
            keySet.add(Key.PIVOT_Y);
        }
        if (diff(this.scaleX, points.scaleX)) {
            keySet.add("scaleX");
        }
        if (diff(this.scaleY, points.scaleY)) {
            keySet.add("scaleY");
        }
        if (diff(this.translationX, points.translationX)) {
            keySet.add("translationX");
        }
        if (diff(this.translationY, points.translationY)) {
            keySet.add("translationY");
        }
        if (diff(this.translationZ, points.translationZ)) {
            keySet.add("translationZ");
        }
    }

    /* access modifiers changed from: package-private */
    public void different(MotionConstrainedPoint points, boolean[] mask, String[] custom) {
        int c = 0 + 1;
        mask[0] = mask[0] | diff(this.position, points.position);
        int c2 = c + 1;
        mask[c] = mask[c] | diff(this.f1016x, points.f1016x);
        int c3 = c2 + 1;
        mask[c2] = mask[c2] | diff(this.f1017y, points.f1017y);
        int c4 = c3 + 1;
        mask[c3] = mask[c3] | diff(this.width, points.width);
        int i = c4 + 1;
        mask[c4] = mask[c4] | diff(this.height, points.height);
    }

    /* access modifiers changed from: package-private */
    public void fillStandard(double[] data, int[] toUse) {
        float[] set = {this.position, this.f1016x, this.f1017y, this.width, this.height, this.alpha, this.elevation, this.rotation, this.rotationX, this.rotationY, this.scaleX, this.scaleY, this.mPivotX, this.mPivotY, this.translationX, this.translationY, this.translationZ, this.mPathRotate};
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
        return this.attributes.containsKey(name);
    }

    /* access modifiers changed from: package-private */
    public int getCustomDataCount(String name) {
        return this.attributes.get(name).numberOfInterpolatedValues();
    }

    /* access modifiers changed from: package-private */
    public int getCustomData(String name, double[] value, int offset) {
        ConstraintAttribute a = this.attributes.get(name);
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
        this.f1016x = x;
        this.f1017y = y;
        this.width = w;
        this.height = h;
    }

    public int compareTo(MotionConstrainedPoint o) {
        return Float.compare(this.position, o.position);
    }

    public void applyParameters(View view) {
        this.visibility = view.getVisibility();
        this.alpha = view.getVisibility() != 0 ? 0.0f : view.getAlpha();
        this.applyElevation = false;
        if (Build.VERSION.SDK_INT >= 21) {
            this.elevation = view.getElevation();
        }
        this.rotation = view.getRotation();
        this.rotationX = view.getRotationX();
        this.rotationY = view.getRotationY();
        this.scaleX = view.getScaleX();
        this.scaleY = view.getScaleY();
        this.mPivotX = view.getPivotX();
        this.mPivotY = view.getPivotY();
        this.translationX = view.getTranslationX();
        this.translationY = view.getTranslationY();
        if (Build.VERSION.SDK_INT >= 21) {
            this.translationZ = view.getTranslationZ();
        }
    }

    public void applyParameters(ConstraintSet.Constraint c) {
        this.mVisibilityMode = c.propertySet.mVisibilityMode;
        this.visibility = c.propertySet.visibility;
        this.alpha = (c.propertySet.visibility == 0 || this.mVisibilityMode != 0) ? c.propertySet.alpha : 0.0f;
        this.applyElevation = c.transform.applyElevation;
        this.elevation = c.transform.elevation;
        this.rotation = c.transform.rotation;
        this.rotationX = c.transform.rotationX;
        this.rotationY = c.transform.rotationY;
        this.scaleX = c.transform.scaleX;
        this.scaleY = c.transform.scaleY;
        this.mPivotX = c.transform.transformPivotX;
        this.mPivotY = c.transform.transformPivotY;
        this.translationX = c.transform.translationX;
        this.translationY = c.transform.translationY;
        this.translationZ = c.transform.translationZ;
        this.mKeyFrameEasing = Easing.getInterpolator(c.motion.mTransitionEasing);
        this.mPathRotate = c.motion.mPathRotate;
        this.mDrawPath = c.motion.mDrawPath;
        this.mAnimateRelativeTo = c.motion.mAnimateRelativeTo;
        this.mProgress = c.propertySet.mProgress;
        for (String s : c.mCustomConstraints.keySet()) {
            ConstraintAttribute attr = c.mCustomConstraints.get(s);
            if (attr.isContinuous()) {
                this.attributes.put(s, attr);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addValues(java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r9, int r10) {
        /*
            r8 = this;
            java.util.Set r0 = r9.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0008:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x021d
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r2 = r9.get(r1)
            androidx.constraintlayout.motion.utils.ViewSpline r2 = (androidx.constraintlayout.motion.utils.ViewSpline) r2
            int r3 = r1.hashCode()
            r4 = 1
            switch(r3) {
                case -1249320806: goto L_0x00bb;
                case -1249320805: goto L_0x00b0;
                case -1225497657: goto L_0x00a4;
                case -1225497656: goto L_0x0098;
                case -1225497655: goto L_0x008c;
                case -1001078227: goto L_0x0080;
                case -908189618: goto L_0x0074;
                case -908189617: goto L_0x0068;
                case -760884510: goto L_0x005d;
                case -760884509: goto L_0x0052;
                case -40300674: goto L_0x0046;
                case -4379043: goto L_0x003b;
                case 37232917: goto L_0x002f;
                case 92909918: goto L_0x0024;
                default: goto L_0x0022;
            }
        L_0x0022:
            goto L_0x00c6
        L_0x0024:
            java.lang.String r3 = "alpha"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 0
            goto L_0x00c7
        L_0x002f:
            java.lang.String r3 = "transitionPathRotate"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 7
            goto L_0x00c7
        L_0x003b:
            java.lang.String r3 = "elevation"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 1
            goto L_0x00c7
        L_0x0046:
            java.lang.String r3 = "rotation"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 2
            goto L_0x00c7
        L_0x0052:
            java.lang.String r3 = "transformPivotY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 6
            goto L_0x00c7
        L_0x005d:
            java.lang.String r3 = "transformPivotX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 5
            goto L_0x00c7
        L_0x0068:
            java.lang.String r3 = "scaleY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 10
            goto L_0x00c7
        L_0x0074:
            java.lang.String r3 = "scaleX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 9
            goto L_0x00c7
        L_0x0080:
            java.lang.String r3 = "progress"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 8
            goto L_0x00c7
        L_0x008c:
            java.lang.String r3 = "translationZ"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 13
            goto L_0x00c7
        L_0x0098:
            java.lang.String r3 = "translationY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 12
            goto L_0x00c7
        L_0x00a4:
            java.lang.String r3 = "translationX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 11
            goto L_0x00c7
        L_0x00b0:
            java.lang.String r3 = "rotationY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 4
            goto L_0x00c7
        L_0x00bb:
            java.lang.String r3 = "rotationX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 3
            goto L_0x00c7
        L_0x00c6:
            r3 = -1
        L_0x00c7:
            r5 = 1065353216(0x3f800000, float:1.0)
            r6 = 0
            switch(r3) {
                case 0: goto L_0x01c9;
                case 1: goto L_0x01ba;
                case 2: goto L_0x01ab;
                case 3: goto L_0x019b;
                case 4: goto L_0x018b;
                case 5: goto L_0x017b;
                case 6: goto L_0x016b;
                case 7: goto L_0x015b;
                case 8: goto L_0x014b;
                case 9: goto L_0x013b;
                case 10: goto L_0x012b;
                case 11: goto L_0x011b;
                case 12: goto L_0x010b;
                case 13: goto L_0x00fb;
                default: goto L_0x00cd;
            }
        L_0x00cd:
            java.lang.String r3 = "CUSTOM"
            boolean r3 = r1.startsWith(r3)
            java.lang.String r5 = "MotionPaths"
            if (r3 == 0) goto L_0x0205
            java.lang.String r3 = ","
            java.lang.String[] r3 = r1.split(r3)
            r3 = r3[r4]
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r4 = r8.attributes
            boolean r4 = r4.containsKey(r3)
            if (r4 == 0) goto L_0x0204
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r4 = r8.attributes
            java.lang.Object r4 = r4.get(r3)
            androidx.constraintlayout.widget.ConstraintAttribute r4 = (androidx.constraintlayout.widget.ConstraintAttribute) r4
            boolean r6 = r2 instanceof androidx.constraintlayout.motion.utils.ViewSpline.CustomSet
            if (r6 == 0) goto L_0x01d8
            r5 = r2
            androidx.constraintlayout.motion.utils.ViewSpline$CustomSet r5 = (androidx.constraintlayout.motion.utils.ViewSpline.CustomSet) r5
            r5.setPoint((int) r10, (androidx.constraintlayout.widget.ConstraintAttribute) r4)
            goto L_0x0204
        L_0x00fb:
            float r3 = r8.translationZ
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0104
            goto L_0x0106
        L_0x0104:
            float r6 = r8.translationZ
        L_0x0106:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x010b:
            float r3 = r8.translationY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0114
            goto L_0x0116
        L_0x0114:
            float r6 = r8.translationY
        L_0x0116:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x011b:
            float r3 = r8.translationX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0124
            goto L_0x0126
        L_0x0124:
            float r6 = r8.translationX
        L_0x0126:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x012b:
            float r3 = r8.scaleY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0134
            goto L_0x0136
        L_0x0134:
            float r5 = r8.scaleY
        L_0x0136:
            r2.setPoint(r10, r5)
            goto L_0x021b
        L_0x013b:
            float r3 = r8.scaleX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0144
            goto L_0x0146
        L_0x0144:
            float r5 = r8.scaleX
        L_0x0146:
            r2.setPoint(r10, r5)
            goto L_0x021b
        L_0x014b:
            float r3 = r8.mProgress
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0154
            goto L_0x0156
        L_0x0154:
            float r6 = r8.mProgress
        L_0x0156:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x015b:
            float r3 = r8.mPathRotate
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0164
            goto L_0x0166
        L_0x0164:
            float r6 = r8.mPathRotate
        L_0x0166:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x016b:
            float r3 = r8.mPivotY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0174
            goto L_0x0176
        L_0x0174:
            float r6 = r8.mPivotY
        L_0x0176:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x017b:
            float r3 = r8.mPivotX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0184
            goto L_0x0186
        L_0x0184:
            float r6 = r8.mPivotX
        L_0x0186:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x018b:
            float r3 = r8.rotationY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0194
            goto L_0x0196
        L_0x0194:
            float r6 = r8.rotationY
        L_0x0196:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x019b:
            float r3 = r8.rotationX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x01a4
            goto L_0x01a6
        L_0x01a4:
            float r6 = r8.rotationX
        L_0x01a6:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x01ab:
            float r3 = r8.rotation
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x01b4
            goto L_0x01b6
        L_0x01b4:
            float r6 = r8.rotation
        L_0x01b6:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x01ba:
            float r3 = r8.elevation
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x01c3
            goto L_0x01c5
        L_0x01c3:
            float r6 = r8.elevation
        L_0x01c5:
            r2.setPoint(r10, r6)
            goto L_0x021b
        L_0x01c9:
            float r3 = r8.alpha
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x01d2
            goto L_0x01d4
        L_0x01d2:
            float r5 = r8.alpha
        L_0x01d4:
            r2.setPoint(r10, r5)
            goto L_0x021b
        L_0x01d8:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = " ViewSpline not a CustomSet frame = "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r10)
            java.lang.String r7 = ", value"
            java.lang.StringBuilder r6 = r6.append(r7)
            float r7 = r4.getValueToInterpolate()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r6 = r6.toString()
            android.util.Log.e(r5, r6)
        L_0x0204:
            goto L_0x021b
        L_0x0205:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "UNKNOWN spline "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r1)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r5, r3)
        L_0x021b:
            goto L_0x0008
        L_0x021d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionConstrainedPoint.addValues(java.util.HashMap, int):void");
    }

    public void setState(View view) {
        setBounds(view.getX(), view.getY(), (float) view.getWidth(), (float) view.getHeight());
        applyParameters(view);
    }

    public void setState(Rect rect, View view, int rotation2, float prevous) {
        setBounds((float) rect.left, (float) rect.top, (float) rect.width(), (float) rect.height());
        applyParameters(view);
        this.mPivotX = Float.NaN;
        this.mPivotY = Float.NaN;
        switch (rotation2) {
            case 1:
                this.rotation = prevous - 90.0f;
                return;
            case 2:
                this.rotation = 90.0f + prevous;
                return;
            default:
                return;
        }
    }

    public void setState(Rect cw, ConstraintSet constraintSet, int rotation2, int viewId) {
        setBounds((float) cw.left, (float) cw.top, (float) cw.width(), (float) cw.height());
        applyParameters(constraintSet.getParameters(viewId));
        switch (rotation2) {
            case 1:
            case 3:
                this.rotation -= 90.0f;
                return;
            case 2:
            case 4:
                float f = this.rotation + 90.0f;
                this.rotation = f;
                if (f > 180.0f) {
                    this.rotation = f - 360.0f;
                    return;
                }
                return;
            default:
                return;
        }
    }
}
