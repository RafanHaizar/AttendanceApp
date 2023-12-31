package org.bouncycastle.crypto.tls;

import java.io.IOException;

class DTLSEpoch {
    private final TlsCipher cipher;
    private final int epoch;
    private final DTLSReplayWindow replayWindow = new DTLSReplayWindow();
    private long sequenceNumber = 0;

    DTLSEpoch(int i, TlsCipher tlsCipher) {
        if (i < 0) {
            throw new IllegalArgumentException("'epoch' must be >= 0");
        } else if (tlsCipher != null) {
            this.epoch = i;
            this.cipher = tlsCipher;
        } else {
            throw new IllegalArgumentException("'cipher' cannot be null");
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized long allocateSequenceNumber() throws IOException {
        long j;
        j = this.sequenceNumber;
        if (j < 281474976710656L) {
            this.sequenceNumber = 1 + j;
        } else {
            throw new TlsFatalAlert(80);
        }
        return j;
    }

    /* access modifiers changed from: package-private */
    public TlsCipher getCipher() {
        return this.cipher;
    }

    /* access modifiers changed from: package-private */
    public int getEpoch() {
        return this.epoch;
    }

    /* access modifiers changed from: package-private */
    public DTLSReplayWindow getReplayWindow() {
        return this.replayWindow;
    }

    /* access modifiers changed from: package-private */
    public synchronized long getSequenceNumber() {
        return this.sequenceNumber;
    }
}
