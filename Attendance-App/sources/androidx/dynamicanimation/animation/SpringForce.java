package androidx.dynamicanimation.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;

public final class SpringForce implements Force {
    public static final float DAMPING_RATIO_HIGH_BOUNCY = 0.2f;
    public static final float DAMPING_RATIO_LOW_BOUNCY = 0.75f;
    public static final float DAMPING_RATIO_MEDIUM_BOUNCY = 0.5f;
    public static final float DAMPING_RATIO_NO_BOUNCY = 1.0f;
    public static final float STIFFNESS_HIGH = 10000.0f;
    public static final float STIFFNESS_LOW = 200.0f;
    public static final float STIFFNESS_MEDIUM = 1500.0f;
    public static final float STIFFNESS_VERY_LOW = 50.0f;
    private static final double UNSET = Double.MAX_VALUE;
    private static final double VELOCITY_THRESHOLD_MULTIPLIER = 62.5d;
    private double mDampedFreq;
    double mDampingRatio = 0.5d;
    private double mFinalPosition = Double.MAX_VALUE;
    private double mGammaMinus;
    private double mGammaPlus;
    private boolean mInitialized = false;
    private final DynamicAnimation.MassState mMassState = new DynamicAnimation.MassState();
    double mNaturalFreq = Math.sqrt(1500.0d);
    private double mValueThreshold;
    private double mVelocityThreshold;

    public SpringForce() {
    }

    public SpringForce(float finalPosition) {
        this.mFinalPosition = (double) finalPosition;
    }

    public SpringForce setStiffness(float stiffness) {
        if (stiffness > 0.0f) {
            this.mNaturalFreq = Math.sqrt((double) stiffness);
            this.mInitialized = false;
            return this;
        }
        throw new IllegalArgumentException("Spring stiffness constant must be positive.");
    }

    public float getStiffness() {
        double d = this.mNaturalFreq;
        return (float) (d * d);
    }

    public SpringForce setDampingRatio(float dampingRatio) {
        if (dampingRatio >= 0.0f) {
            this.mDampingRatio = (double) dampingRatio;
            this.mInitialized = false;
            return this;
        }
        throw new IllegalArgumentException("Damping ratio must be non-negative");
    }

    public float getDampingRatio() {
        return (float) this.mDampingRatio;
    }

    public SpringForce setFinalPosition(float finalPosition) {
        this.mFinalPosition = (double) finalPosition;
        return this;
    }

    public float getFinalPosition() {
        return (float) this.mFinalPosition;
    }

    public float getAcceleration(float lastDisplacement, float lastVelocity) {
        float lastDisplacement2 = lastDisplacement - getFinalPosition();
        double d = this.mNaturalFreq;
        double k = d * d;
        double c = d * 2.0d * this.mDampingRatio;
        double d2 = (double) lastDisplacement2;
        Double.isNaN(d2);
        double d3 = (-k) * d2;
        double d4 = (double) lastVelocity;
        Double.isNaN(d4);
        return (float) (d3 - (d4 * c));
    }

    public boolean isAtEquilibrium(float value, float velocity) {
        if (((double) Math.abs(velocity)) >= this.mVelocityThreshold || ((double) Math.abs(value - getFinalPosition())) >= this.mValueThreshold) {
            return false;
        }
        return true;
    }

    private void init() {
        if (!this.mInitialized) {
            if (this.mFinalPosition != Double.MAX_VALUE) {
                double d = this.mDampingRatio;
                if (d > 1.0d) {
                    double d2 = this.mNaturalFreq;
                    this.mGammaPlus = ((-d) * d2) + (d2 * Math.sqrt((d * d) - 1.0d));
                    double d3 = this.mDampingRatio;
                    double d4 = this.mNaturalFreq;
                    this.mGammaMinus = ((-d3) * d4) - (d4 * Math.sqrt((d3 * d3) - 1.0d));
                } else if (d >= 0.0d && d < 1.0d) {
                    this.mDampedFreq = this.mNaturalFreq * Math.sqrt(1.0d - (d * d));
                }
                this.mInitialized = true;
                return;
            }
            throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
        }
    }

    /* access modifiers changed from: package-private */
    public DynamicAnimation.MassState updateValues(double lastDisplacement, double lastVelocity, long timeElapsed) {
        double displacement;
        double cosCoeff;
        init();
        double d = (double) timeElapsed;
        Double.isNaN(d);
        double deltaT = d / 1000.0d;
        double lastDisplacement2 = lastDisplacement - this.mFinalPosition;
        double displacement2 = this.mDampingRatio;
        if (displacement2 > 1.0d) {
            double d2 = this.mGammaMinus;
            double d3 = this.mGammaPlus;
            double coeffA = lastDisplacement2 - (((d2 * lastDisplacement2) - lastVelocity) / (d2 - d3));
            double coeffB = ((d2 * lastDisplacement2) - lastVelocity) / (d2 - d3);
            displacement = (Math.pow(2.718281828459045d, d2 * deltaT) * coeffA) + (Math.pow(2.718281828459045d, this.mGammaPlus * deltaT) * coeffB);
            double d4 = this.mGammaMinus;
            double pow = coeffA * d4 * Math.pow(2.718281828459045d, d4 * deltaT);
            double d5 = this.mGammaPlus;
            double coeffB2 = lastDisplacement2;
            cosCoeff = pow + (coeffB * d5 * Math.pow(2.718281828459045d, d5 * deltaT));
        } else if (displacement2 == 1.0d) {
            double coeffA2 = lastDisplacement2;
            double d6 = this.mNaturalFreq;
            double coeffB3 = lastVelocity + (d6 * lastDisplacement2);
            double pow2 = ((coeffB3 * deltaT) + coeffA2) * Math.pow(2.718281828459045d, (-this.mNaturalFreq) * deltaT);
            double d7 = this.mNaturalFreq;
            double currentVelocity = (pow2 * (-d7)) + (Math.pow(2.718281828459045d, (-d7) * deltaT) * coeffB3);
            double d8 = lastDisplacement2;
            displacement = Math.pow(2.718281828459045d, (-d6) * deltaT) * ((coeffB3 * deltaT) + coeffA2);
            cosCoeff = currentVelocity;
        } else {
            double cosCoeff2 = lastDisplacement2;
            double d9 = 1.0d / this.mDampedFreq;
            double d10 = this.mNaturalFreq;
            double sinCoeff = d9 * ((displacement2 * d10 * lastDisplacement2) + lastVelocity);
            double displacement3 = Math.pow(2.718281828459045d, (-displacement2) * d10 * deltaT) * ((Math.cos(this.mDampedFreq * deltaT) * cosCoeff2) + (Math.sin(this.mDampedFreq * deltaT) * sinCoeff));
            double d11 = this.mNaturalFreq;
            double d12 = lastDisplacement2;
            double lastDisplacement3 = this.mDampingRatio;
            double d13 = (-d11) * displacement3 * lastDisplacement3;
            double pow3 = Math.pow(2.718281828459045d, (-lastDisplacement3) * d11 * deltaT);
            double d14 = this.mDampedFreq;
            double displacement4 = displacement3;
            double sin = (-d14) * cosCoeff2 * Math.sin(d14 * deltaT);
            double d15 = this.mDampedFreq;
            displacement = displacement4;
            cosCoeff = d13 + (pow3 * (sin + (d15 * sinCoeff * Math.cos(d15 * deltaT))));
        }
        this.mMassState.mValue = (float) (this.mFinalPosition + displacement);
        this.mMassState.mVelocity = (float) cosCoeff;
        return this.mMassState;
    }

    /* access modifiers changed from: package-private */
    public void setValueThreshold(double threshold) {
        double abs = Math.abs(threshold);
        this.mValueThreshold = abs;
        this.mVelocityThreshold = abs * VELOCITY_THRESHOLD_MULTIPLIER;
    }
}
