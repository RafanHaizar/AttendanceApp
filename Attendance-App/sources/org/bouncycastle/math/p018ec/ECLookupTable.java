package org.bouncycastle.math.p018ec;

/* renamed from: org.bouncycastle.math.ec.ECLookupTable */
public interface ECLookupTable {
    int getSize();

    ECPoint lookup(int i);

    ECPoint lookupVar(int i);
}
