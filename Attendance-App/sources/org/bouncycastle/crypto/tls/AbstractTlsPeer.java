package org.bouncycastle.crypto.tls;

import java.io.IOException;

public abstract class AbstractTlsPeer implements TlsPeer {
    private volatile TlsCloseable closeHandle;

    public void cancel() throws IOException {
        TlsCloseable tlsCloseable = this.closeHandle;
        if (tlsCloseable != null) {
            tlsCloseable.close();
        }
    }

    public void notifyAlertRaised(short s, short s2, String str, Throwable th) {
    }

    public void notifyAlertReceived(short s, short s2) {
    }

    public void notifyCloseHandle(TlsCloseable tlsCloseable) {
        this.closeHandle = tlsCloseable;
    }

    public void notifyHandshakeComplete() throws IOException {
    }

    public void notifySecureRenegotiation(boolean z) throws IOException {
        if (!z) {
            throw new TlsFatalAlert(40);
        }
    }

    public boolean requiresExtendedMasterSecret() {
        return false;
    }

    public boolean shouldUseGMTUnixTime() {
        return false;
    }
}
