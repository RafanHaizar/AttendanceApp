package org.bouncycastle.crypto.tls;

import org.bouncycastle.util.Arrays;

class TlsSessionImpl implements TlsSession {
    boolean resumable;
    final byte[] sessionID;
    final SessionParameters sessionParameters;

    TlsSessionImpl(byte[] bArr, SessionParameters sessionParameters2) {
        if (bArr == null) {
            throw new IllegalArgumentException("'sessionID' cannot be null");
        } else if (bArr.length <= 32) {
            this.sessionID = Arrays.clone(bArr);
            this.sessionParameters = sessionParameters2;
            this.resumable = bArr.length > 0 && sessionParameters2 != null && sessionParameters2.isExtendedMasterSecret();
        } else {
            throw new IllegalArgumentException("'sessionID' cannot be longer than 32 bytes");
        }
    }

    public synchronized SessionParameters exportSessionParameters() {
        SessionParameters sessionParameters2;
        sessionParameters2 = this.sessionParameters;
        return sessionParameters2 == null ? null : sessionParameters2.copy();
    }

    public synchronized byte[] getSessionID() {
        return this.sessionID;
    }

    public synchronized void invalidate() {
        this.resumable = false;
    }

    public synchronized boolean isResumable() {
        return this.resumable;
    }
}
