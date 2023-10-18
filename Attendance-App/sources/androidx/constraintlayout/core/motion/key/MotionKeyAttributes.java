package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.HashMap;
import java.util.HashSet;

public class MotionKeyAttributes extends MotionKey {
    private static final boolean DEBUG = false;
    public static final int KEY_TYPE = 1;
    static final String NAME = "KeyAttribute";
    private static final String TAG = "KeyAttributes";
    private float mAlpha = Float.NaN;
    private int mCurveFit = -1;
    private float mElevation = Float.NaN;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float mProgress = Float.NaN;
    private float mRotation = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private String mTransitionEasing;
    private float mTransitionPathRotate = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;
    private int mVisibility = 0;

    public MotionKeyAttributes() {
        this.mType = 1;
        this.mCustom = new HashMap();
    }

    public void getAttributeNames(HashSet<String> attributes) {
        if (!Float.isNaN(this.mAlpha)) {
            attributes.add("alpha");
        }
        if (!Float.isNaN(this.mElevation)) {
            attributes.add("elevation");
        }
        if (!Float.isNaN(this.mRotation)) {
            attributes.add("rotationZ");
        }
        if (!Float.isNaN(this.mRotationX)) {
            attributes.add("rotationX");
        }
        if (!Float.isNaN(this.mRotationY)) {
            attributes.add("rotationY");
        }
        if (!Float.isNaN(this.mPivotX)) {
            attributes.add("pivotX");
        }
        if (!Float.isNaN(this.mPivotY)) {
            attributes.add("pivotY");
        }
        if (!Float.isNaN(this.mTranslationX)) {
            attributes.add("translationX");
        }
        if (!Float.isNaN(this.mTranslationY)) {
            attributes.add("translationY");
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            attributes.add("translationZ");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            attributes.add("pathRotate");
        }
        if (!Float.isNaN(this.mScaleX)) {
            attributes.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            attributes.add("scaleY");
        }
        if (!Float.isNaN(this.mProgress)) {
            attributes.add("progress");
        }
        if (this.mCustom.size() > 0) {
            for (String s : this.mCustom.keySet()) {
                attributes.add("CUSTOM," + s);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0068, code lost:
        if (r1.equals("elevation") != false) goto L_0x00ed;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addValues(java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r8) {
        /*
            r7 = this;
            java.util.Set r0 = r8.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0008:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x01f3
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r2 = r8.get(r1)
            androidx.constraintlayout.core.motion.utils.SplineSet r2 = (androidx.constraintlayout.core.motion.utils.SplineSet) r2
            if (r2 != 0) goto L_0x001d
            goto L_0x0008
        L_0x001d:
            java.lang.String r3 = "CUSTOM"
            boolean r4 = r1.startsWith(r3)
            r5 = 1
            if (r4 == 0) goto L_0x0042
            int r3 = r3.length()
            int r3 = r3 + r5
            java.lang.String r3 = r1.substring(r3)
            java.util.HashMap r4 = r7.mCustom
            java.lang.Object r4 = r4.get(r3)
            androidx.constraintlayout.core.motion.CustomVariable r4 = (androidx.constraintlayout.core.motion.CustomVariable) r4
            if (r4 == 0) goto L_0x0008
            r5 = r2
            androidx.constraintlayout.core.motion.utils.SplineSet$CustomSpline r5 = (androidx.constraintlayout.core.motion.utils.SplineSet.CustomSpline) r5
            int r6 = r7.mFramePosition
            r5.setPoint((int) r6, (androidx.constraintlayout.core.motion.CustomVariable) r4)
            goto L_0x0008
        L_0x0042:
            int r3 = r1.hashCode()
            switch(r3) {
                case -1249320806: goto L_0x00e1;
                case -1249320805: goto L_0x00d6;
                case -1249320804: goto L_0x00cb;
                case -1225497657: goto L_0x00bf;
                case -1225497656: goto L_0x00b3;
                case -1225497655: goto L_0x00a7;
                case -1001078227: goto L_0x009b;
                case -987906986: goto L_0x0090;
                case -987906985: goto L_0x0085;
                case -908189618: goto L_0x0079;
                case -908189617: goto L_0x006c;
                case -4379043: goto L_0x0062;
                case 92909918: goto L_0x0057;
                case 803192288: goto L_0x004b;
                default: goto L_0x0049;
            }
        L_0x0049:
            goto L_0x00ec
        L_0x004b:
            java.lang.String r3 = "pathRotate"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 7
            goto L_0x00ed
        L_0x0057:
            java.lang.String r3 = "alpha"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 0
            goto L_0x00ed
        L_0x0062:
            java.lang.String r3 = "elevation"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            goto L_0x00ed
        L_0x006c:
            java.lang.String r3 = "scaleY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 9
            goto L_0x00ed
        L_0x0079:
            java.lang.String r3 = "scaleX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 8
            goto L_0x00ed
        L_0x0085:
            java.lang.String r3 = "pivotY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 6
            goto L_0x00ed
        L_0x0090:
            java.lang.String r3 = "pivotX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 5
            goto L_0x00ed
        L_0x009b:
            java.lang.String r3 = "progress"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 13
            goto L_0x00ed
        L_0x00a7:
            java.lang.String r3 = "translationZ"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 12
            goto L_0x00ed
        L_0x00b3:
            java.lang.String r3 = "translationY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 11
            goto L_0x00ed
        L_0x00bf:
            java.lang.String r3 = "translationX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 10
            goto L_0x00ed
        L_0x00cb:
            java.lang.String r3 = "rotationZ"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 2
            goto L_0x00ed
        L_0x00d6:
            java.lang.String r3 = "rotationY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 4
            goto L_0x00ed
        L_0x00e1:
            java.lang.String r3 = "rotationX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0049
            r5 = 3
            goto L_0x00ed
        L_0x00ec:
            r5 = -1
        L_0x00ed:
            switch(r5) {
                case 0: goto L_0x01e2;
                case 1: goto L_0x01d2;
                case 2: goto L_0x01c2;
                case 3: goto L_0x01b2;
                case 4: goto L_0x01a2;
                case 5: goto L_0x0192;
                case 6: goto L_0x0182;
                case 7: goto L_0x0171;
                case 8: goto L_0x0160;
                case 9: goto L_0x014f;
                case 10: goto L_0x013e;
                case 11: goto L_0x012d;
                case 12: goto L_0x011c;
                case 13: goto L_0x010b;
                default: goto L_0x00f0;
            }
        L_0x00f0:
            java.io.PrintStream r3 = java.lang.System.err
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "not supported by KeyAttributes "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r1)
            java.lang.String r4 = r4.toString()
            r3.println(r4)
            goto L_0x01f1
        L_0x010b:
            float r3 = r7.mProgress
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mProgress
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x011c:
            float r3 = r7.mTranslationZ
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mTranslationZ
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x012d:
            float r3 = r7.mTranslationY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mTranslationY
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x013e:
            float r3 = r7.mTranslationX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mTranslationX
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x014f:
            float r3 = r7.mScaleY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mScaleY
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x0160:
            float r3 = r7.mScaleX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mScaleX
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x0171:
            float r3 = r7.mTransitionPathRotate
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mTransitionPathRotate
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x0182:
            float r3 = r7.mRotationY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mPivotY
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x0192:
            float r3 = r7.mRotationX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mPivotX
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x01a2:
            float r3 = r7.mRotationY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mRotationY
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x01b2:
            float r3 = r7.mRotationX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mRotationX
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x01c2:
            float r3 = r7.mRotation
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mRotation
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x01d2:
            float r3 = r7.mElevation
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mElevation
            r2.setPoint(r3, r4)
            goto L_0x01f1
        L_0x01e2:
            float r3 = r7.mAlpha
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x01f1
            int r3 = r7.mFramePosition
            float r4 = r7.mAlpha
            r2.setPoint(r3, r4)
        L_0x01f1:
            goto L_0x0008
        L_0x01f3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.key.MotionKeyAttributes.addValues(java.util.HashMap):void");
    }

    public MotionKey clone() {
        return null;
    }

    public boolean setValue(int type, int value) {
        switch (type) {
            case 100:
                this.mFramePosition = value;
                return true;
            case 301:
                this.mCurveFit = value;
                return true;
            case 302:
                this.mVisibility = value;
                return true;
            default:
                if (!setValue(type, value)) {
                    return super.setValue(type, value);
                }
                return true;
        }
    }

    public boolean setValue(int type, float value) {
        switch (type) {
            case 100:
                this.mTransitionPathRotate = value;
                return true;
            case 303:
                this.mAlpha = value;
                return true;
            case 304:
                this.mTranslationX = value;
                return true;
            case 305:
                this.mTranslationY = value;
                return true;
            case 306:
                this.mTranslationZ = value;
                return true;
            case 307:
                this.mElevation = value;
                return true;
            case 308:
                this.mRotationX = value;
                return true;
            case 309:
                this.mRotationY = value;
                return true;
            case 310:
                this.mRotation = value;
                return true;
            case 311:
                this.mScaleX = value;
                return true;
            case 312:
                this.mScaleY = value;
                return true;
            case 313:
                this.mPivotX = value;
                return true;
            case 314:
                this.mPivotY = value;
                return true;
            case 315:
                this.mProgress = value;
                return true;
            case 316:
                this.mTransitionPathRotate = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    public void setInterpolation(HashMap<String, Integer> interpolation) {
        if (!Float.isNaN(this.mAlpha)) {
            interpolation.put("alpha", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mElevation)) {
            interpolation.put("elevation", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotation)) {
            interpolation.put("rotationZ", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationX)) {
            interpolation.put("rotationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mRotationY)) {
            interpolation.put("rotationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mPivotX)) {
            interpolation.put("pivotX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mPivotY)) {
            interpolation.put("pivotY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationX)) {
            interpolation.put("translationX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationY)) {
            interpolation.put("translationY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            interpolation.put("translationZ", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            interpolation.put("pathRotate", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleX)) {
            interpolation.put("scaleX", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mScaleY)) {
            interpolation.put("scaleY", Integer.valueOf(this.mCurveFit));
        }
        if (!Float.isNaN(this.mProgress)) {
            interpolation.put("progress", Integer.valueOf(this.mCurveFit));
        }
        if (this.mCustom.size() > 0) {
            for (String s : this.mCustom.keySet()) {
                interpolation.put("CUSTOM," + s, Integer.valueOf(this.mCurveFit));
            }
        }
    }

    public boolean setValue(int type, String value) {
        switch (type) {
            case 101:
                this.mTargetString = value;
                return true;
            case 317:
                this.mTransitionEasing = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    public int getId(String name) {
        return TypedValues.AttributesType.CC.getId(name);
    }

    public int getCurveFit() {
        return this.mCurveFit;
    }

    public void printAttributes() {
        HashSet<String> nameSet = new HashSet<>();
        getAttributeNames(nameSet);
        System.out.println(" ------------- " + this.mFramePosition + " -------------");
        String[] names = (String[]) nameSet.toArray(new String[0]);
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i] + ":" + getFloatValue(TypedValues.AttributesType.CC.getId(names[i])));
        }
    }

    private float getFloatValue(int id) {
        switch (id) {
            case 100:
                return (float) this.mFramePosition;
            case 303:
                return this.mAlpha;
            case 304:
                return this.mTranslationX;
            case 305:
                return this.mTranslationY;
            case 306:
                return this.mTranslationZ;
            case 307:
                return this.mElevation;
            case 308:
                return this.mRotationX;
            case 309:
                return this.mRotationY;
            case 310:
                return this.mRotation;
            case 311:
                return this.mScaleX;
            case 312:
                return this.mScaleY;
            case 313:
                return this.mPivotX;
            case 314:
                return this.mPivotY;
            case 315:
                return this.mProgress;
            case 316:
                return this.mTransitionPathRotate;
            default:
                return Float.NaN;
        }
    }
}
