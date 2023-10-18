package org.bouncycastle.crypto.engines;

import org.bouncycastle.util.Memoable;

public final class Zuc256Engine extends Zuc256CoreEngine {
    public Zuc256Engine() {
    }

    public Zuc256Engine(int i) {
        super(i);
    }

    private Zuc256Engine(Zuc256Engine zuc256Engine) {
        super((Zuc256CoreEngine) zuc256Engine);
    }

    public Memoable copy() {
        return new Zuc256Engine(this);
    }
}
