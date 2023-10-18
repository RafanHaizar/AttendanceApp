package androidx.constraintlayout.motion.utils;

import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ViewOscillator extends KeyCycleOscillator {
    private static final String TAG = "ViewOscillator";

    public abstract void setProperty(View view, float f);

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.constraintlayout.motion.utils.ViewOscillator makeSpline(java.lang.String r1) {
        /*
            java.lang.String r0 = "CUSTOM"
            boolean r0 = r1.startsWith(r0)
            if (r0 == 0) goto L_0x000e
            androidx.constraintlayout.motion.utils.ViewOscillator$CustomSet r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$CustomSet
            r0.<init>()
            return r0
        L_0x000e:
            int r0 = r1.hashCode()
            switch(r0) {
                case -1249320806: goto L_0x00ae;
                case -1249320805: goto L_0x00a3;
                case -1225497657: goto L_0x0097;
                case -1225497656: goto L_0x008b;
                case -1225497655: goto L_0x007f;
                case -1001078227: goto L_0x0073;
                case -908189618: goto L_0x0068;
                case -908189617: goto L_0x005d;
                case -797520672: goto L_0x0051;
                case -40300674: goto L_0x0046;
                case -4379043: goto L_0x003b;
                case 37232917: goto L_0x002f;
                case 92909918: goto L_0x0024;
                case 156108012: goto L_0x0017;
                default: goto L_0x0015;
            }
        L_0x0015:
            goto L_0x00b9
        L_0x0017:
            java.lang.String r0 = "waveOffset"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 8
            goto L_0x00ba
        L_0x0024:
            java.lang.String r0 = "alpha"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 0
            goto L_0x00ba
        L_0x002f:
            java.lang.String r0 = "transitionPathRotate"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 5
            goto L_0x00ba
        L_0x003b:
            java.lang.String r0 = "elevation"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 1
            goto L_0x00ba
        L_0x0046:
            java.lang.String r0 = "rotation"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 2
            goto L_0x00ba
        L_0x0051:
            java.lang.String r0 = "waveVariesBy"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 9
            goto L_0x00ba
        L_0x005d:
            java.lang.String r0 = "scaleY"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 7
            goto L_0x00ba
        L_0x0068:
            java.lang.String r0 = "scaleX"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 6
            goto L_0x00ba
        L_0x0073:
            java.lang.String r0 = "progress"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 13
            goto L_0x00ba
        L_0x007f:
            java.lang.String r0 = "translationZ"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 12
            goto L_0x00ba
        L_0x008b:
            java.lang.String r0 = "translationY"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 11
            goto L_0x00ba
        L_0x0097:
            java.lang.String r0 = "translationX"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 10
            goto L_0x00ba
        L_0x00a3:
            java.lang.String r0 = "rotationY"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 4
            goto L_0x00ba
        L_0x00ae:
            java.lang.String r0 = "rotationX"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0015
            r0 = 3
            goto L_0x00ba
        L_0x00b9:
            r0 = -1
        L_0x00ba:
            switch(r0) {
                case 0: goto L_0x010d;
                case 1: goto L_0x0107;
                case 2: goto L_0x0101;
                case 3: goto L_0x00fb;
                case 4: goto L_0x00f5;
                case 5: goto L_0x00ef;
                case 6: goto L_0x00e9;
                case 7: goto L_0x00e3;
                case 8: goto L_0x00dd;
                case 9: goto L_0x00d7;
                case 10: goto L_0x00d1;
                case 11: goto L_0x00cb;
                case 12: goto L_0x00c5;
                case 13: goto L_0x00bf;
                default: goto L_0x00bd;
            }
        L_0x00bd:
            r0 = 0
            return r0
        L_0x00bf:
            androidx.constraintlayout.motion.utils.ViewOscillator$ProgressSet r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$ProgressSet
            r0.<init>()
            return r0
        L_0x00c5:
            androidx.constraintlayout.motion.utils.ViewOscillator$TranslationZset r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$TranslationZset
            r0.<init>()
            return r0
        L_0x00cb:
            androidx.constraintlayout.motion.utils.ViewOscillator$TranslationYset r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$TranslationYset
            r0.<init>()
            return r0
        L_0x00d1:
            androidx.constraintlayout.motion.utils.ViewOscillator$TranslationXset r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$TranslationXset
            r0.<init>()
            return r0
        L_0x00d7:
            androidx.constraintlayout.motion.utils.ViewOscillator$AlphaSet r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$AlphaSet
            r0.<init>()
            return r0
        L_0x00dd:
            androidx.constraintlayout.motion.utils.ViewOscillator$AlphaSet r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$AlphaSet
            r0.<init>()
            return r0
        L_0x00e3:
            androidx.constraintlayout.motion.utils.ViewOscillator$ScaleYset r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$ScaleYset
            r0.<init>()
            return r0
        L_0x00e9:
            androidx.constraintlayout.motion.utils.ViewOscillator$ScaleXset r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$ScaleXset
            r0.<init>()
            return r0
        L_0x00ef:
            androidx.constraintlayout.motion.utils.ViewOscillator$PathRotateSet r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$PathRotateSet
            r0.<init>()
            return r0
        L_0x00f5:
            androidx.constraintlayout.motion.utils.ViewOscillator$RotationYset r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$RotationYset
            r0.<init>()
            return r0
        L_0x00fb:
            androidx.constraintlayout.motion.utils.ViewOscillator$RotationXset r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$RotationXset
            r0.<init>()
            return r0
        L_0x0101:
            androidx.constraintlayout.motion.utils.ViewOscillator$RotationSet r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$RotationSet
            r0.<init>()
            return r0
        L_0x0107:
            androidx.constraintlayout.motion.utils.ViewOscillator$ElevationSet r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$ElevationSet
            r0.<init>()
            return r0
        L_0x010d:
            androidx.constraintlayout.motion.utils.ViewOscillator$AlphaSet r0 = new androidx.constraintlayout.motion.utils.ViewOscillator$AlphaSet
            r0.<init>()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.utils.ViewOscillator.makeSpline(java.lang.String):androidx.constraintlayout.motion.utils.ViewOscillator");
    }

    static class ElevationSet extends ViewOscillator {
        ElevationSet() {
        }

        public void setProperty(View view, float t) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setElevation(get(t));
            }
        }
    }

    static class AlphaSet extends ViewOscillator {
        AlphaSet() {
        }

        public void setProperty(View view, float t) {
            view.setAlpha(get(t));
        }
    }

    static class RotationSet extends ViewOscillator {
        RotationSet() {
        }

        public void setProperty(View view, float t) {
            view.setRotation(get(t));
        }
    }

    static class RotationXset extends ViewOscillator {
        RotationXset() {
        }

        public void setProperty(View view, float t) {
            view.setRotationX(get(t));
        }
    }

    static class RotationYset extends ViewOscillator {
        RotationYset() {
        }

        public void setProperty(View view, float t) {
            view.setRotationY(get(t));
        }
    }

    public static class PathRotateSet extends ViewOscillator {
        public void setProperty(View view, float t) {
        }

        public void setPathRotate(View view, float t, double dx, double dy) {
            view.setRotation(get(t) + ((float) Math.toDegrees(Math.atan2(dy, dx))));
        }
    }

    static class ScaleXset extends ViewOscillator {
        ScaleXset() {
        }

        public void setProperty(View view, float t) {
            view.setScaleX(get(t));
        }
    }

    static class ScaleYset extends ViewOscillator {
        ScaleYset() {
        }

        public void setProperty(View view, float t) {
            view.setScaleY(get(t));
        }
    }

    static class TranslationXset extends ViewOscillator {
        TranslationXset() {
        }

        public void setProperty(View view, float t) {
            view.setTranslationX(get(t));
        }
    }

    static class TranslationYset extends ViewOscillator {
        TranslationYset() {
        }

        public void setProperty(View view, float t) {
            view.setTranslationY(get(t));
        }
    }

    static class TranslationZset extends ViewOscillator {
        TranslationZset() {
        }

        public void setProperty(View view, float t) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setTranslationZ(get(t));
            }
        }
    }

    static class CustomSet extends ViewOscillator {
        protected ConstraintAttribute mCustom;
        float[] value = new float[1];

        CustomSet() {
        }

        /* access modifiers changed from: protected */
        public void setCustom(Object custom) {
            this.mCustom = (ConstraintAttribute) custom;
        }

        public void setProperty(View view, float t) {
            this.value[0] = get(t);
            CustomSupport.setInterpolatedValue(this.mCustom, view, this.value);
        }
    }

    static class ProgressSet extends ViewOscillator {
        boolean mNoMethod = false;

        ProgressSet() {
        }

        public void setProperty(View view, float t) {
            if (view instanceof MotionLayout) {
                ((MotionLayout) view).setProgress(get(t));
            } else if (!this.mNoMethod) {
                Method method = null;
                try {
                    method = view.getClass().getMethod("setProgress", new Class[]{Float.TYPE});
                } catch (NoSuchMethodException e) {
                    this.mNoMethod = true;
                }
                if (method != null) {
                    try {
                        method.invoke(view, new Object[]{Float.valueOf(get(t))});
                    } catch (IllegalAccessException e2) {
                        Log.e(ViewOscillator.TAG, "unable to setProgress", e2);
                    } catch (InvocationTargetException e3) {
                        Log.e(ViewOscillator.TAG, "unable to setProgress", e3);
                    }
                }
            }
        }
    }
}
