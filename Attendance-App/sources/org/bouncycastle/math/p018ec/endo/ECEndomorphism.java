package org.bouncycastle.math.p018ec.endo;

import org.bouncycastle.math.p018ec.ECPointMap;

/* renamed from: org.bouncycastle.math.ec.endo.ECEndomorphism */
public interface ECEndomorphism {
    ECPointMap getPointMap();

    boolean hasEfficientPointMap();
}
