package androidx.dynamicanimation.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;

public final class FlingAnimation extends DynamicAnimation<FlingAnimation> {
    private final DragForce mFlingForce;

    public FlingAnimation(FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        DragForce dragForce = new DragForce();
        this.mFlingForce = dragForce;
        dragForce.setValueThreshold(getValueThreshold());
    }

    public <K> FlingAnimation(K object, FloatPropertyCompat<K> property) {
        super(object, property);
        DragForce dragForce = new DragForce();
        this.mFlingForce = dragForce;
        dragForce.setValueThreshold(getValueThreshold());
    }

    public FlingAnimation setFriction(float friction) {
        if (friction > 0.0f) {
            this.mFlingForce.setFrictionScalar(friction);
            return this;
        }
        throw new IllegalArgumentException("Friction must be positive");
    }

    public float getFriction() {
        return this.mFlingForce.getFrictionScalar();
    }

    public FlingAnimation setMinValue(float minValue) {
        super.setMinValue(minValue);
        return this;
    }

    public FlingAnimation setMaxValue(float maxValue) {
        super.setMaxValue(maxValue);
        return this;
    }

    public FlingAnimation setStartVelocity(float startVelocity) {
        super.setStartVelocity(startVelocity);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean updateValueAndVelocity(long deltaT) {
        DynamicAnimation.MassState state = this.mFlingForce.updateValueAndVelocity(this.mValue, this.mVelocity, deltaT);
        this.mValue = state.mValue;
        this.mVelocity = state.mVelocity;
        if (this.mValue < this.mMinValue) {
            this.mValue = this.mMinValue;
            return true;
        } else if (this.mValue > this.mMaxValue) {
            this.mValue = this.mMaxValue;
            return true;
        } else if (isAtEquilibrium(this.mValue, this.mVelocity)) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public float getAcceleration(float value, float velocity) {
        return this.mFlingForce.getAcceleration(value, velocity);
    }

    /* access modifiers changed from: package-private */
    public boolean isAtEquilibrium(float value, float velocity) {
        return value >= this.mMaxValue || value <= this.mMinValue || this.mFlingForce.isAtEquilibrium(value, velocity);
    }

    /* access modifiers changed from: package-private */
    public void setValueThreshold(float threshold) {
        this.mFlingForce.setValueThreshold(threshold);
    }

    static final class DragForce implements Force {
        private static final float DEFAULT_FRICTION = -4.2f;
        private static final float VELOCITY_THRESHOLD_MULTIPLIER = 62.5f;
        private float mFriction = DEFAULT_FRICTION;
        private final DynamicAnimation.MassState mMassState = new DynamicAnimation.MassState();
        private float mVelocityThreshold;

        DragForce() {
        }

        /* access modifiers changed from: package-private */
        public void setFrictionScalar(float frictionScalar) {
            this.mFriction = DEFAULT_FRICTION * frictionScalar;
        }

        /* access modifiers changed from: package-private */
        public float getFrictionScalar() {
            return this.mFriction / DEFAULT_FRICTION;
        }

        /* access modifiers changed from: package-private */
        public DynamicAnimation.MassState updateValueAndVelocity(float value, float velocity, long deltaT) {
            DynamicAnimation.MassState massState = this.mMassState;
            double d = (double) velocity;
            double exp = Math.exp((double) ((((float) deltaT) / 1000.0f) * this.mFriction));
            Double.isNaN(d);
            massState.mVelocity = (float) (d * exp);
            DynamicAnimation.MassState massState2 = this.mMassState;
            float f = this.mFriction;
            double d2 = (double) (value - (velocity / f));
            double d3 = (double) (velocity / f);
            double exp2 = Math.exp((double) ((f * ((float) deltaT)) / 1000.0f));
            Double.isNaN(d3);
            Double.isNaN(d2);
            massState2.mValue = (float) (d2 + (d3 * exp2));
            if (isAtEquilibrium(this.mMassState.mValue, this.mMassState.mVelocity)) {
                this.mMassState.mVelocity = 0.0f;
            }
            return this.mMassState;
        }

        public float getAcceleration(float position, float velocity) {
            return this.mFriction * velocity;
        }

        public boolean isAtEquilibrium(float value, float velocity) {
            return Math.abs(velocity) < this.mVelocityThreshold;
        }

        /* access modifiers changed from: package-private */
        public void setValueThreshold(float threshold) {
            this.mVelocityThreshold = VELOCITY_THRESHOLD_MULTIPLIER * threshold;
        }
    }
}
