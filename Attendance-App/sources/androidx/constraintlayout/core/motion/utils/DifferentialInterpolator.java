package androidx.constraintlayout.core.motion.utils;

public interface DifferentialInterpolator {
    float getInterpolation(float f);

    float getVelocity();
}
