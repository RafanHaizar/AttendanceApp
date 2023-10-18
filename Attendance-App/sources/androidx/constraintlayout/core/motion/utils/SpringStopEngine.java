package androidx.constraintlayout.core.motion.utils;

public class SpringStopEngine implements StopEngine {
    private static final double UNSET = Double.MAX_VALUE;
    private int mBoundaryMode = 0;
    double mDamping = 0.5d;
    private boolean mInitialized = false;
    private float mLastTime;
    private double mLastVelocity;
    private float mMass;
    private float mPos;
    private double mStiffness;
    private float mStopThreshold;
    private double mTargetPos;

    /* renamed from: mV */
    private float f1000mV;

    public String debug(String desc, float time) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public void log(String str) {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        System.out.println((".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName() + "() ") + str);
    }

    public void springConfig(float currentPos, float target, float currentVelocity, float mass, float stiffness, float damping, float stopThreshold, int boundaryMode) {
        this.mTargetPos = (double) target;
        this.mDamping = (double) damping;
        this.mInitialized = false;
        this.mPos = currentPos;
        this.mLastVelocity = (double) currentVelocity;
        this.mStiffness = (double) stiffness;
        this.mMass = mass;
        this.mStopThreshold = stopThreshold;
        this.mBoundaryMode = boundaryMode;
        this.mLastTime = 0.0f;
    }

    public float getVelocity(float t) {
        return this.f1000mV;
    }

    public float getInterpolation(float time) {
        compute((double) (time - this.mLastTime));
        this.mLastTime = time;
        return this.mPos;
    }

    public float getAcceleration() {
        double k = this.mStiffness;
        double c = this.mDamping;
        double d = (double) this.mPos;
        double d2 = this.mTargetPos;
        Double.isNaN(d);
        double x = d - d2;
        double d3 = (double) this.f1000mV;
        Double.isNaN(d3);
        return ((float) (((-k) * x) - (d3 * c))) / this.mMass;
    }

    public float getVelocity() {
        return 0.0f;
    }

    public boolean isStopped() {
        double d = (double) this.mPos;
        double d2 = this.mTargetPos;
        Double.isNaN(d);
        double x = d - d2;
        double k = this.mStiffness;
        double v = (double) this.f1000mV;
        double m = (double) this.mMass;
        Double.isNaN(v);
        Double.isNaN(v);
        Double.isNaN(m);
        return Math.sqrt((((v * v) * m) + ((k * x) * x)) / k) <= ((double) this.mStopThreshold);
    }

    private void compute(double dt) {
        double k = this.mStiffness;
        double c = this.mDamping;
        double d = this.mStiffness;
        double d2 = (double) this.mMass;
        Double.isNaN(d2);
        int overSample = (int) ((9.0d / ((Math.sqrt(d / d2) * dt) * 4.0d)) + 1.0d);
        double d3 = (double) overSample;
        Double.isNaN(d3);
        double dt2 = dt / d3;
        int i = 0;
        while (i < overSample) {
            float f = this.mPos;
            double d4 = (double) f;
            double d5 = this.mTargetPos;
            Double.isNaN(d4);
            double x = d4 - d5;
            double d6 = (-k) * x;
            int overSample2 = overSample;
            float f2 = this.f1000mV;
            double d7 = x;
            double x2 = (double) f2;
            Double.isNaN(x2);
            double d8 = d6 - (x2 * c);
            float f3 = this.mMass;
            double c2 = c;
            double c3 = (double) f3;
            Double.isNaN(c3);
            double a = d8 / c3;
            double d9 = (double) f2;
            Double.isNaN(d9);
            double avgV = d9 + ((a * dt2) / 2.0d);
            double d10 = a;
            double a2 = (double) f;
            Double.isNaN(a2);
            double d11 = ((-((a2 + ((dt2 * avgV) / 2.0d)) - d5)) * k) - (avgV * c2);
            double k2 = k;
            double k3 = (double) f3;
            Double.isNaN(k3);
            double a3 = d11 / k3;
            double dv = a3 * dt2;
            double d12 = avgV;
            double avgV2 = (double) f2;
            Double.isNaN(avgV2);
            double avgV3 = a3;
            double d13 = (double) f2;
            Double.isNaN(d13);
            float f4 = (float) (d13 + dv);
            this.f1000mV = f4;
            double d14 = (double) f;
            Double.isNaN(d14);
            float f5 = (float) (d14 + ((avgV2 + (dv / 2.0d)) * dt2));
            this.mPos = f5;
            int i2 = this.mBoundaryMode;
            if (i2 > 0) {
                if (f5 < 0.0f && (i2 & 1) == 1) {
                    this.mPos = -f5;
                    this.f1000mV = -f4;
                }
                float f6 = this.mPos;
                if (f6 > 1.0f && (i2 & 2) == 2) {
                    this.mPos = 2.0f - f6;
                    this.f1000mV = -this.f1000mV;
                }
            }
            i++;
            overSample = overSample2;
            c = c2;
            k = k2;
        }
    }
}
