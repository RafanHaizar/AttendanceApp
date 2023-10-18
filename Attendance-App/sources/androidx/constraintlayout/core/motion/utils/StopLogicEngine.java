package androidx.constraintlayout.core.motion.utils;

public class StopLogicEngine implements StopEngine {
    private static final float EPSILON = 1.0E-5f;
    private boolean mBackwards = false;
    private boolean mDone = false;
    private float mLastPosition;
    private int mNumberOfStages;
    private float mStage1Duration;
    private float mStage1EndPosition;
    private float mStage1Velocity;
    private float mStage2Duration;
    private float mStage2EndPosition;
    private float mStage2Velocity;
    private float mStage3Duration;
    private float mStage3EndPosition;
    private float mStage3Velocity;
    private float mStartPosition;
    private String mType;

    public String debug(String desc, float time) {
        String ret = ((desc + " ===== " + this.mType + "\n") + desc + (this.mBackwards ? "backwards" : "forward ") + " time = " + time + "  stages " + this.mNumberOfStages + "\n") + desc + " dur " + this.mStage1Duration + " vel " + this.mStage1Velocity + " pos " + this.mStage1EndPosition + "\n";
        if (this.mNumberOfStages > 1) {
            ret = ret + desc + " dur " + this.mStage2Duration + " vel " + this.mStage2Velocity + " pos " + this.mStage2EndPosition + "\n";
        }
        if (this.mNumberOfStages > 2) {
            ret = ret + desc + " dur " + this.mStage3Duration + " vel " + this.mStage3Velocity + " pos " + this.mStage3EndPosition + "\n";
        }
        float f = this.mStage1Duration;
        if (time <= f) {
            return ret + desc + "stage 0\n";
        }
        int i = this.mNumberOfStages;
        if (i == 1) {
            return ret + desc + "end stage 0\n";
        }
        float time2 = time - f;
        float f2 = this.mStage2Duration;
        if (time2 < f2) {
            return ret + desc + " stage 1\n";
        }
        if (i == 2) {
            return ret + desc + "end stage 1\n";
        }
        if (time2 - f2 < this.mStage3Duration) {
            return ret + desc + " stage 2\n";
        }
        return ret + desc + " end stage 2\n";
    }

    public float getVelocity(float x) {
        float f = this.mStage1Duration;
        if (x <= f) {
            float f2 = this.mStage1Velocity;
            return f2 + (((this.mStage2Velocity - f2) * x) / f);
        }
        int i = this.mNumberOfStages;
        if (i == 1) {
            return 0.0f;
        }
        float x2 = x - f;
        float f3 = this.mStage2Duration;
        if (x2 < f3) {
            float f4 = this.mStage2Velocity;
            return f4 + (((this.mStage3Velocity - f4) * x2) / f3);
        } else if (i == 2) {
            return this.mStage2EndPosition;
        } else {
            float x3 = x2 - f3;
            float f5 = this.mStage3Duration;
            if (x3 >= f5) {
                return this.mStage3EndPosition;
            }
            float f6 = this.mStage3Velocity;
            return f6 - ((f6 * x3) / f5);
        }
    }

    private float calcY(float time) {
        this.mDone = false;
        float f = this.mStage1Duration;
        if (time <= f) {
            float f2 = this.mStage1Velocity;
            return (f2 * time) + ((((this.mStage2Velocity - f2) * time) * time) / (f * 2.0f));
        }
        int i = this.mNumberOfStages;
        if (i == 1) {
            return this.mStage1EndPosition;
        }
        float time2 = time - f;
        float f3 = this.mStage2Duration;
        if (time2 < f3) {
            float f4 = this.mStage1EndPosition;
            float f5 = this.mStage2Velocity;
            return f4 + (f5 * time2) + ((((this.mStage3Velocity - f5) * time2) * time2) / (f3 * 2.0f));
        } else if (i == 2) {
            return this.mStage2EndPosition;
        } else {
            float time3 = time2 - f3;
            float f6 = this.mStage3Duration;
            if (time3 <= f6) {
                float f7 = this.mStage2EndPosition;
                float f8 = this.mStage3Velocity;
                return (f7 + (f8 * time3)) - (((f8 * time3) * time3) / (f6 * 2.0f));
            }
            this.mDone = true;
            return this.mStage3EndPosition;
        }
    }

    public void config(float currentPos, float destination, float currentVelocity, float maxTime, float maxAcceleration, float maxVelocity) {
        boolean z = false;
        this.mDone = false;
        this.mStartPosition = currentPos;
        if (currentPos > destination) {
            z = true;
        }
        this.mBackwards = z;
        if (z) {
            setup(-currentVelocity, currentPos - destination, maxAcceleration, maxVelocity, maxTime);
            return;
        }
        setup(currentVelocity, destination - currentPos, maxAcceleration, maxVelocity, maxTime);
    }

    public float getInterpolation(float v) {
        float y = calcY(v);
        this.mLastPosition = v;
        return this.mBackwards ? this.mStartPosition - y : this.mStartPosition + y;
    }

    public float getVelocity() {
        return this.mBackwards ? -getVelocity(this.mLastPosition) : getVelocity(this.mLastPosition);
    }

    public boolean isStopped() {
        return getVelocity() < EPSILON && Math.abs(this.mStage3EndPosition - this.mLastPosition) < EPSILON;
    }

    private void setup(float velocity, float distance, float maxAcceleration, float maxVelocity, float maxTime) {
        float velocity2;
        float f = distance;
        float f2 = maxVelocity;
        this.mDone = false;
        if (velocity == 0.0f) {
            velocity2 = 1.0E-4f;
        } else {
            velocity2 = velocity;
        }
        this.mStage1Velocity = velocity2;
        float min_time_to_stop = velocity2 / maxAcceleration;
        float stopDistance = (min_time_to_stop * velocity2) / 2.0f;
        if (velocity2 < 0.0f) {
            float peak_v = (float) Math.sqrt((double) (maxAcceleration * (f - ((((-velocity2) / maxAcceleration) * velocity2) / 2.0f))));
            if (peak_v < f2) {
                this.mType = "backward accelerate, decelerate";
                this.mNumberOfStages = 2;
                this.mStage1Velocity = velocity2;
                this.mStage2Velocity = peak_v;
                this.mStage3Velocity = 0.0f;
                float f3 = (peak_v - velocity2) / maxAcceleration;
                this.mStage1Duration = f3;
                this.mStage2Duration = peak_v / maxAcceleration;
                this.mStage1EndPosition = ((velocity2 + peak_v) * f3) / 2.0f;
                this.mStage2EndPosition = f;
                this.mStage3EndPosition = f;
                return;
            }
            this.mType = "backward accelerate cruse decelerate";
            this.mNumberOfStages = 3;
            this.mStage1Velocity = velocity2;
            this.mStage2Velocity = f2;
            this.mStage3Velocity = f2;
            float f4 = (f2 - velocity2) / maxAcceleration;
            this.mStage1Duration = f4;
            float f5 = f2 / maxAcceleration;
            this.mStage3Duration = f5;
            float accDist = ((velocity2 + f2) * f4) / 2.0f;
            float decDist = (f2 * f5) / 2.0f;
            this.mStage2Duration = ((f - accDist) - decDist) / f2;
            this.mStage1EndPosition = accDist;
            this.mStage2EndPosition = f - decDist;
            this.mStage3EndPosition = f;
        } else if (stopDistance >= f) {
            this.mType = "hard stop";
            this.mNumberOfStages = 1;
            this.mStage1Velocity = velocity2;
            this.mStage2Velocity = 0.0f;
            this.mStage1EndPosition = f;
            this.mStage1Duration = (2.0f * f) / velocity2;
        } else {
            float distance_before_break = f - stopDistance;
            float cruseTime = distance_before_break / velocity2;
            if (cruseTime + min_time_to_stop < maxTime) {
                this.mType = "cruse decelerate";
                this.mNumberOfStages = 2;
                this.mStage1Velocity = velocity2;
                this.mStage2Velocity = velocity2;
                this.mStage3Velocity = 0.0f;
                this.mStage1EndPosition = distance_before_break;
                this.mStage2EndPosition = f;
                this.mStage1Duration = cruseTime;
                this.mStage2Duration = velocity2 / maxAcceleration;
                return;
            }
            float peak_v2 = (float) Math.sqrt((double) ((maxAcceleration * f) + ((velocity2 * velocity2) / 2.0f)));
            this.mStage1Duration = (peak_v2 - velocity2) / maxAcceleration;
            this.mStage2Duration = peak_v2 / maxAcceleration;
            if (peak_v2 < f2) {
                this.mType = "accelerate decelerate";
                this.mNumberOfStages = 2;
                this.mStage1Velocity = velocity2;
                this.mStage2Velocity = peak_v2;
                this.mStage3Velocity = 0.0f;
                float f6 = (peak_v2 - velocity2) / maxAcceleration;
                this.mStage1Duration = f6;
                this.mStage2Duration = peak_v2 / maxAcceleration;
                this.mStage1EndPosition = ((velocity2 + peak_v2) * f6) / 2.0f;
                this.mStage2EndPosition = f;
                return;
            }
            this.mType = "accelerate cruse decelerate";
            this.mNumberOfStages = 3;
            this.mStage1Velocity = velocity2;
            this.mStage2Velocity = f2;
            this.mStage3Velocity = f2;
            float f7 = (f2 - velocity2) / maxAcceleration;
            this.mStage1Duration = f7;
            float f8 = f2 / maxAcceleration;
            this.mStage3Duration = f8;
            float accDist2 = ((velocity2 + f2) * f7) / 2.0f;
            float decDist2 = (f2 * f8) / 2.0f;
            this.mStage2Duration = ((f - accDist2) - decDist2) / f2;
            this.mStage1EndPosition = accDist2;
            this.mStage2EndPosition = f - decDist2;
            this.mStage3EndPosition = f;
        }
    }
}
