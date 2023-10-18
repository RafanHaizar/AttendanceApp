package androidx.constraintlayout.core.motion.utils;

public class Easing {
    private static final String ACCELERATE = "cubic(0.4, 0.05, 0.8, 0.7)";
    private static final String ACCELERATE_NAME = "accelerate";
    private static final String ANTICIPATE = "cubic(0.36, 0, 0.66, -0.56)";
    private static final String ANTICIPATE_NAME = "anticipate";
    private static final String DECELERATE = "cubic(0.0, 0.0, 0.2, 0.95)";
    private static final String DECELERATE_NAME = "decelerate";
    private static final String LINEAR = "cubic(1, 1, 0, 0)";
    private static final String LINEAR_NAME = "linear";
    public static String[] NAMED_EASING = {STANDARD_NAME, ACCELERATE_NAME, DECELERATE_NAME, LINEAR_NAME};
    private static final String OVERSHOOT = "cubic(0.34, 1.56, 0.64, 1)";
    private static final String OVERSHOOT_NAME = "overshoot";
    private static final String STANDARD = "cubic(0.4, 0.0, 0.2, 1)";
    private static final String STANDARD_NAME = "standard";
    static Easing sDefault = new Easing();
    String str = "identity";

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.constraintlayout.core.motion.utils.Easing getInterpolator(java.lang.String r3) {
        /*
            if (r3 != 0) goto L_0x0004
            r0 = 0
            return r0
        L_0x0004:
            java.lang.String r0 = "cubic"
            boolean r0 = r3.startsWith(r0)
            if (r0 == 0) goto L_0x0012
            androidx.constraintlayout.core.motion.utils.Easing$CubicEasing r0 = new androidx.constraintlayout.core.motion.utils.Easing$CubicEasing
            r0.<init>(r3)
            return r0
        L_0x0012:
            java.lang.String r0 = "spline"
            boolean r0 = r3.startsWith(r0)
            if (r0 == 0) goto L_0x0021
            androidx.constraintlayout.core.motion.utils.StepCurve r0 = new androidx.constraintlayout.core.motion.utils.StepCurve
            r0.<init>(r3)
            return r0
        L_0x0021:
            java.lang.String r0 = "Schlick"
            boolean r0 = r3.startsWith(r0)
            if (r0 == 0) goto L_0x002f
            androidx.constraintlayout.core.motion.utils.Schlick r0 = new androidx.constraintlayout.core.motion.utils.Schlick
            r0.<init>(r3)
            return r0
        L_0x002f:
            int r0 = r3.hashCode()
            switch(r0) {
                case -1354466595: goto L_0x006b;
                case -1263948740: goto L_0x0061;
                case -1197605014: goto L_0x0057;
                case -1102672091: goto L_0x004d;
                case -749065269: goto L_0x0042;
                case 1312628413: goto L_0x0037;
                default: goto L_0x0036;
            }
        L_0x0036:
            goto L_0x0075
        L_0x0037:
            java.lang.String r0 = "standard"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0036
            r0 = 0
            goto L_0x0076
        L_0x0042:
            java.lang.String r0 = "overshoot"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0036
            r0 = 5
            goto L_0x0076
        L_0x004d:
            java.lang.String r0 = "linear"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0036
            r0 = 3
            goto L_0x0076
        L_0x0057:
            java.lang.String r0 = "anticipate"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0036
            r0 = 4
            goto L_0x0076
        L_0x0061:
            java.lang.String r0 = "decelerate"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0036
            r0 = 2
            goto L_0x0076
        L_0x006b:
            java.lang.String r0 = "accelerate"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0036
            r0 = 1
            goto L_0x0076
        L_0x0075:
            r0 = -1
        L_0x0076:
            switch(r0) {
                case 0: goto L_0x00c3;
                case 1: goto L_0x00bb;
                case 2: goto L_0x00b3;
                case 3: goto L_0x00ab;
                case 4: goto L_0x00a3;
                case 5: goto L_0x009b;
                default: goto L_0x0079;
            }
        L_0x0079:
            java.io.PrintStream r0 = java.lang.System.err
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "transitionEasing syntax error syntax:transitionEasing=\"cubic(1.0,0.5,0.0,0.6)\" or "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String[] r2 = NAMED_EASING
            java.lang.String r2 = java.util.Arrays.toString(r2)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            androidx.constraintlayout.core.motion.utils.Easing r0 = sDefault
            return r0
        L_0x009b:
            androidx.constraintlayout.core.motion.utils.Easing$CubicEasing r0 = new androidx.constraintlayout.core.motion.utils.Easing$CubicEasing
            java.lang.String r1 = "cubic(0.34, 1.56, 0.64, 1)"
            r0.<init>(r1)
            return r0
        L_0x00a3:
            androidx.constraintlayout.core.motion.utils.Easing$CubicEasing r0 = new androidx.constraintlayout.core.motion.utils.Easing$CubicEasing
            java.lang.String r1 = "cubic(0.36, 0, 0.66, -0.56)"
            r0.<init>(r1)
            return r0
        L_0x00ab:
            androidx.constraintlayout.core.motion.utils.Easing$CubicEasing r0 = new androidx.constraintlayout.core.motion.utils.Easing$CubicEasing
            java.lang.String r1 = "cubic(1, 1, 0, 0)"
            r0.<init>(r1)
            return r0
        L_0x00b3:
            androidx.constraintlayout.core.motion.utils.Easing$CubicEasing r0 = new androidx.constraintlayout.core.motion.utils.Easing$CubicEasing
            java.lang.String r1 = "cubic(0.0, 0.0, 0.2, 0.95)"
            r0.<init>(r1)
            return r0
        L_0x00bb:
            androidx.constraintlayout.core.motion.utils.Easing$CubicEasing r0 = new androidx.constraintlayout.core.motion.utils.Easing$CubicEasing
            java.lang.String r1 = "cubic(0.4, 0.05, 0.8, 0.7)"
            r0.<init>(r1)
            return r0
        L_0x00c3:
            androidx.constraintlayout.core.motion.utils.Easing$CubicEasing r0 = new androidx.constraintlayout.core.motion.utils.Easing$CubicEasing
            java.lang.String r1 = "cubic(0.4, 0.0, 0.2, 1)"
            r0.<init>(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.Easing.getInterpolator(java.lang.String):androidx.constraintlayout.core.motion.utils.Easing");
    }

    public double get(double x) {
        return x;
    }

    public String toString() {
        return this.str;
    }

    public double getDiff(double x) {
        return 1.0d;
    }

    static class CubicEasing extends Easing {
        private static double d_error = 1.0E-4d;
        private static double error = 0.01d;

        /* renamed from: x1 */
        double f986x1;

        /* renamed from: x2 */
        double f987x2;

        /* renamed from: y1 */
        double f988y1;

        /* renamed from: y2 */
        double f989y2;

        CubicEasing(String configString) {
            this.str = configString;
            int start = configString.indexOf(40);
            int off1 = configString.indexOf(44, start);
            this.f986x1 = Double.parseDouble(configString.substring(start + 1, off1).trim());
            int off2 = configString.indexOf(44, off1 + 1);
            this.f988y1 = Double.parseDouble(configString.substring(off1 + 1, off2).trim());
            int off3 = configString.indexOf(44, off2 + 1);
            this.f987x2 = Double.parseDouble(configString.substring(off2 + 1, off3).trim());
            this.f989y2 = Double.parseDouble(configString.substring(off3 + 1, configString.indexOf(41, off3 + 1)).trim());
        }

        public CubicEasing(double x1, double y1, double x2, double y2) {
            setup(x1, y1, x2, y2);
        }

        /* access modifiers changed from: package-private */
        public void setup(double x1, double y1, double x2, double y2) {
            this.f986x1 = x1;
            this.f988y1 = y1;
            this.f987x2 = x2;
            this.f989y2 = y2;
        }

        private double getX(double t) {
            double t1 = 1.0d - t;
            return (this.f986x1 * t1 * 3.0d * t1 * t) + (this.f987x2 * 3.0d * t1 * t * t) + (t * t * t);
        }

        private double getY(double t) {
            double t1 = 1.0d - t;
            return (this.f988y1 * t1 * 3.0d * t1 * t) + (this.f989y2 * 3.0d * t1 * t * t) + (t * t * t);
        }

        private double getDiffX(double t) {
            double t1 = 1.0d - t;
            double d = this.f986x1;
            double d2 = this.f987x2;
            return (t1 * 3.0d * t1 * d) + (6.0d * t1 * t * (d2 - d)) + (3.0d * t * t * (1.0d - d2));
        }

        private double getDiffY(double t) {
            double t1 = 1.0d - t;
            double d = this.f988y1;
            double d2 = this.f989y2;
            return (t1 * 3.0d * t1 * d) + (6.0d * t1 * t * (d2 - d)) + (3.0d * t * t * (1.0d - d2));
        }

        public double getDiff(double x) {
            double t = 0.5d;
            double range = 0.5d;
            while (range > d_error) {
                range *= 0.5d;
                if (getX(t) < x) {
                    t += range;
                } else {
                    t -= range;
                }
            }
            double x1 = getX(t - range);
            double x2 = getX(t + range);
            return (getY(t + range) - getY(t - range)) / (x2 - x1);
        }

        public double get(double x) {
            if (x <= 0.0d) {
                return 0.0d;
            }
            if (x >= 1.0d) {
                return 1.0d;
            }
            double t = 0.5d;
            double range = 0.5d;
            while (range > error) {
                range *= 0.5d;
                if (getX(t) < x) {
                    t += range;
                } else {
                    t -= range;
                }
            }
            double x1 = getX(t - range);
            double x2 = getX(t + range);
            double y1 = getY(t - range);
            return (((getY(t + range) - y1) * (x - x1)) / (x2 - x1)) + y1;
        }
    }
}
