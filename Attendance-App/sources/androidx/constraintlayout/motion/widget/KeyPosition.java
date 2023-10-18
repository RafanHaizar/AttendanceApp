package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.C0657R;
import java.util.HashMap;

public class KeyPosition extends KeyPositionBase {
    public static final String DRAWPATH = "drawPath";
    static final int KEY_TYPE = 2;
    static final String NAME = "KeyPosition";
    public static final String PERCENT_HEIGHT = "percentHeight";
    public static final String PERCENT_WIDTH = "percentWidth";
    public static final String PERCENT_X = "percentX";
    public static final String PERCENT_Y = "percentY";
    public static final String SIZE_PERCENT = "sizePercent";
    private static final String TAG = "KeyPosition";
    public static final String TRANSITION_EASING = "transitionEasing";
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    float mAltPercentX = Float.NaN;
    float mAltPercentY = Float.NaN;
    private float mCalculatedPositionX = Float.NaN;
    private float mCalculatedPositionY = Float.NaN;
    int mDrawPath = 0;
    int mPathMotionArc = UNSET;
    float mPercentHeight = Float.NaN;
    float mPercentWidth = Float.NaN;
    float mPercentX = Float.NaN;
    float mPercentY = Float.NaN;
    int mPositionType = 0;
    String mTransitionEasing = null;

    public KeyPosition() {
        this.mType = 2;
    }

    public void load(Context context, AttributeSet attrs) {
        Loader.read(this, context.obtainStyledAttributes(attrs, C0657R.styleable.KeyPosition));
    }

    public void addValues(HashMap<String, ViewSpline> hashMap) {
    }

    public void setType(int type) {
        this.mPositionType = type;
    }

    /* access modifiers changed from: package-private */
    public void calcPosition(int layoutWidth, int layoutHeight, float start_x, float start_y, float end_x, float end_y) {
        switch (this.mPositionType) {
            case 1:
                calcPathPosition(start_x, start_y, end_x, end_y);
                return;
            case 2:
                calcScreenPosition(layoutWidth, layoutHeight);
                return;
            default:
                calcCartesianPosition(start_x, start_y, end_x, end_y);
                return;
        }
    }

    private void calcScreenPosition(int layoutWidth, int layoutHeight) {
        float f = this.mPercentX;
        this.mCalculatedPositionX = (((float) (layoutWidth - 0)) * f) + ((float) (0 / 2));
        this.mCalculatedPositionY = (((float) (layoutHeight - 0)) * f) + ((float) (0 / 2));
    }

    private void calcPathPosition(float start_x, float start_y, float end_x, float end_y) {
        float pathVectorX = end_x - start_x;
        float pathVectorY = end_y - start_y;
        float f = this.mPercentX;
        float f2 = this.mPercentY;
        this.mCalculatedPositionX = (pathVectorX * f) + start_x + ((-pathVectorY) * f2);
        this.mCalculatedPositionY = (f * pathVectorY) + start_y + (f2 * pathVectorX);
    }

    private void calcCartesianPosition(float start_x, float start_y, float end_x, float end_y) {
        float pathVectorX = end_x - start_x;
        float pathVectorY = end_y - start_y;
        float dxdy = 0.0f;
        float dxdx = Float.isNaN(this.mPercentX) ? 0.0f : this.mPercentX;
        float dydx = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float dydy = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        if (!Float.isNaN(this.mAltPercentX)) {
            dxdy = this.mAltPercentX;
        }
        this.mCalculatedPositionX = (float) ((int) ((pathVectorX * dxdx) + start_x + (pathVectorY * dxdy)));
        this.mCalculatedPositionY = (float) ((int) ((pathVectorX * dydx) + start_y + (pathVectorY * dydy)));
    }

    /* access modifiers changed from: package-private */
    public float getPositionX() {
        return this.mCalculatedPositionX;
    }

    /* access modifiers changed from: package-private */
    public float getPositionY() {
        return this.mCalculatedPositionY;
    }

    public void positionAttributes(View view, RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        switch (this.mPositionType) {
            case 1:
                positionPathAttributes(start, end, x, y, attribute, value);
                return;
            case 2:
                positionScreenAttributes(view, start, end, x, y, attribute, value);
                return;
            default:
                positionCartAttributes(start, end, x, y, attribute, value);
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void positionPathAttributes(RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        float startCenterX = start.centerX();
        float startCenterY = start.centerY();
        float pathVectorX = end.centerX() - startCenterX;
        float pathVectorY = end.centerY() - startCenterY;
        float distance = (float) Math.hypot((double) pathVectorX, (double) pathVectorY);
        if (((double) distance) < 1.0E-4d) {
            System.out.println("distance ~ 0");
            value[0] = 0.0f;
            value[1] = 0.0f;
            return;
        }
        float dx = pathVectorX / distance;
        float dy = pathVectorY / distance;
        float perpendicular = (((y - startCenterY) * dx) - ((x - startCenterX) * dy)) / distance;
        float dist = (((x - startCenterX) * dx) + ((y - startCenterY) * dy)) / distance;
        if (attribute[0] == null) {
            attribute[0] = "percentX";
            attribute[1] = "percentY";
            value[0] = dist;
            value[1] = perpendicular;
        } else if ("percentX".equals(attribute[0])) {
            value[0] = dist;
            value[1] = perpendicular;
        }
    }

    /* access modifiers changed from: package-private */
    public void positionScreenAttributes(View view, RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        float startCenterX = start.centerX();
        float startCenterY = start.centerY();
        float centerX = end.centerX() - startCenterX;
        float centerY = end.centerY() - startCenterY;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        if (attribute[0] == null) {
            attribute[0] = "percentX";
            value[0] = x / ((float) width);
            attribute[1] = "percentY";
            value[1] = y / ((float) height);
        } else if ("percentX".equals(attribute[0])) {
            value[0] = x / ((float) width);
            value[1] = y / ((float) height);
        } else {
            value[1] = x / ((float) width);
            value[0] = y / ((float) height);
        }
    }

    /* access modifiers changed from: package-private */
    public void positionCartAttributes(RectF start, RectF end, float x, float y, String[] attribute, float[] value) {
        float startCenterX = start.centerX();
        float startCenterY = start.centerY();
        float pathVectorX = end.centerX() - startCenterX;
        float pathVectorY = end.centerY() - startCenterY;
        if (attribute[0] == null) {
            attribute[0] = "percentX";
            value[0] = (x - startCenterX) / pathVectorX;
            attribute[1] = "percentY";
            value[1] = (y - startCenterY) / pathVectorY;
        } else if ("percentX".equals(attribute[0])) {
            value[0] = (x - startCenterX) / pathVectorX;
            value[1] = (y - startCenterY) / pathVectorY;
        } else {
            value[1] = (x - startCenterX) / pathVectorX;
            value[0] = (y - startCenterY) / pathVectorY;
        }
    }

    public boolean intersects(int layoutWidth, int layoutHeight, RectF start, RectF end, float x, float y) {
        calcPosition(layoutWidth, layoutHeight, start.centerX(), start.centerY(), end.centerX(), end.centerY());
        if (Math.abs(x - this.mCalculatedPositionX) >= 20.0f || Math.abs(y - this.mCalculatedPositionY) >= 20.0f) {
            return false;
        }
        return true;
    }

    private static class Loader {
        private static final int CURVE_FIT = 4;
        private static final int DRAW_PATH = 5;
        private static final int FRAME_POSITION = 2;
        private static final int PATH_MOTION_ARC = 10;
        private static final int PERCENT_HEIGHT = 12;
        private static final int PERCENT_WIDTH = 11;
        private static final int PERCENT_X = 6;
        private static final int PERCENT_Y = 7;
        private static final int SIZE_PERCENT = 8;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TYPE = 9;
        private static SparseIntArray mAttrMap;

        private Loader() {
        }

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(C0657R.styleable.KeyPosition_motionTarget, 1);
            mAttrMap.append(C0657R.styleable.KeyPosition_framePosition, 2);
            mAttrMap.append(C0657R.styleable.KeyPosition_transitionEasing, 3);
            mAttrMap.append(C0657R.styleable.KeyPosition_curveFit, 4);
            mAttrMap.append(C0657R.styleable.KeyPosition_drawPath, 5);
            mAttrMap.append(C0657R.styleable.KeyPosition_percentX, 6);
            mAttrMap.append(C0657R.styleable.KeyPosition_percentY, 7);
            mAttrMap.append(C0657R.styleable.KeyPosition_keyPositionType, 9);
            mAttrMap.append(C0657R.styleable.KeyPosition_sizePercent, 8);
            mAttrMap.append(C0657R.styleable.KeyPosition_percentWidth, 11);
            mAttrMap.append(C0657R.styleable.KeyPosition_percentHeight, 12);
            mAttrMap.append(C0657R.styleable.KeyPosition_pathMotionArc, 10);
        }

        /* access modifiers changed from: private */
        public static void read(KeyPosition c, TypedArray a) {
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (mAttrMap.get(attr)) {
                    case 1:
                        if (!MotionLayout.IS_IN_EDIT_MODE) {
                            if (a.peekValue(attr).type != 3) {
                                c.mTargetId = a.getResourceId(attr, c.mTargetId);
                                break;
                            } else {
                                c.mTargetString = a.getString(attr);
                                break;
                            }
                        } else {
                            c.mTargetId = a.getResourceId(attr, c.mTargetId);
                            if (c.mTargetId != -1) {
                                break;
                            } else {
                                c.mTargetString = a.getString(attr);
                                break;
                            }
                        }
                    case 2:
                        c.mFramePosition = a.getInt(attr, c.mFramePosition);
                        break;
                    case 3:
                        if (a.peekValue(attr).type != 3) {
                            c.mTransitionEasing = Easing.NAMED_EASING[a.getInteger(attr, 0)];
                            break;
                        } else {
                            c.mTransitionEasing = a.getString(attr);
                            break;
                        }
                    case 4:
                        c.mCurveFit = a.getInteger(attr, c.mCurveFit);
                        break;
                    case 5:
                        c.mDrawPath = a.getInt(attr, c.mDrawPath);
                        break;
                    case 6:
                        c.mPercentX = a.getFloat(attr, c.mPercentX);
                        break;
                    case 7:
                        c.mPercentY = a.getFloat(attr, c.mPercentY);
                        break;
                    case 8:
                        float f = a.getFloat(attr, c.mPercentHeight);
                        c.mPercentWidth = f;
                        c.mPercentHeight = f;
                        break;
                    case 9:
                        c.mPositionType = a.getInt(attr, c.mPositionType);
                        break;
                    case 10:
                        c.mPathMotionArc = a.getInt(attr, c.mPathMotionArc);
                        break;
                    case 11:
                        c.mPercentWidth = a.getFloat(attr, c.mPercentWidth);
                        break;
                    case 12:
                        c.mPercentHeight = a.getFloat(attr, c.mPercentHeight);
                        break;
                    default:
                        Log.e(TypedValues.PositionType.NAME, "unused attribute 0x" + Integer.toHexString(attr) + "   " + mAttrMap.get(attr));
                        break;
                }
            }
            if (c.mFramePosition == -1) {
                Log.e(TypedValues.PositionType.NAME, "no frame position");
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setValue(java.lang.String r2, java.lang.Object r3) {
        /*
            r1 = this;
            int r0 = r2.hashCode()
            switch(r0) {
                case -1812823328: goto L_0x0049;
                case -1127236479: goto L_0x003e;
                case -1017587252: goto L_0x0033;
                case -827014263: goto L_0x0029;
                case -200259324: goto L_0x001e;
                case 428090547: goto L_0x0013;
                case 428090548: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0054
        L_0x0008:
            java.lang.String r0 = "percentY"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 6
            goto L_0x0055
        L_0x0013:
            java.lang.String r0 = "percentX"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 5
            goto L_0x0055
        L_0x001e:
            java.lang.String r0 = "sizePercent"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 4
            goto L_0x0055
        L_0x0029:
            java.lang.String r0 = "drawPath"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0055
        L_0x0033:
            java.lang.String r0 = "percentHeight"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 3
            goto L_0x0055
        L_0x003e:
            java.lang.String r0 = "percentWidth"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0055
        L_0x0049:
            java.lang.String r0 = "transitionEasing"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0055
        L_0x0054:
            r0 = -1
        L_0x0055:
            switch(r0) {
                case 0: goto L_0x0085;
                case 1: goto L_0x007e;
                case 2: goto L_0x0077;
                case 3: goto L_0x0070;
                case 4: goto L_0x0067;
                case 5: goto L_0x0060;
                case 6: goto L_0x0059;
                default: goto L_0x0058;
            }
        L_0x0058:
            goto L_0x008c
        L_0x0059:
            float r0 = r1.toFloat(r3)
            r1.mPercentY = r0
            goto L_0x008c
        L_0x0060:
            float r0 = r1.toFloat(r3)
            r1.mPercentX = r0
            goto L_0x008c
        L_0x0067:
            float r0 = r1.toFloat(r3)
            r1.mPercentWidth = r0
            r1.mPercentHeight = r0
            goto L_0x008c
        L_0x0070:
            float r0 = r1.toFloat(r3)
            r1.mPercentHeight = r0
            goto L_0x008c
        L_0x0077:
            float r0 = r1.toFloat(r3)
            r1.mPercentWidth = r0
            goto L_0x008c
        L_0x007e:
            int r0 = r1.toInt(r3)
            r1.mDrawPath = r0
            goto L_0x008c
        L_0x0085:
            java.lang.String r0 = r3.toString()
            r1.mTransitionEasing = r0
        L_0x008c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyPosition.setValue(java.lang.String, java.lang.Object):void");
    }

    public Key copy(Key src) {
        super.copy(src);
        KeyPosition k = (KeyPosition) src;
        this.mTransitionEasing = k.mTransitionEasing;
        this.mPathMotionArc = k.mPathMotionArc;
        this.mDrawPath = k.mDrawPath;
        this.mPercentWidth = k.mPercentWidth;
        this.mPercentHeight = Float.NaN;
        this.mPercentX = k.mPercentX;
        this.mPercentY = k.mPercentY;
        this.mAltPercentX = k.mAltPercentX;
        this.mAltPercentY = k.mAltPercentY;
        this.mCalculatedPositionX = k.mCalculatedPositionX;
        this.mCalculatedPositionY = k.mCalculatedPositionY;
        return this;
    }

    public Key clone() {
        return new KeyPosition().copy(this);
    }
}
