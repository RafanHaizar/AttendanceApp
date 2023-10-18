package org.bouncycastle.crypto.tls;

import java.io.IOException;
import org.bouncycastle.crypto.params.DHParameters;

public class PSKTlsClient extends AbstractTlsClient {
    protected TlsDHVerifier dhVerifier;
    protected TlsPSKIdentity pskIdentity;

    public PSKTlsClient(TlsCipherFactory tlsCipherFactory, TlsDHVerifier tlsDHVerifier, TlsPSKIdentity tlsPSKIdentity) {
        super(tlsCipherFactory);
        this.dhVerifier = tlsDHVerifier;
        this.pskIdentity = tlsPSKIdentity;
    }

    public PSKTlsClient(TlsCipherFactory tlsCipherFactory, TlsPSKIdentity tlsPSKIdentity) {
        this(tlsCipherFactory, new DefaultTlsDHVerifier(), tlsPSKIdentity);
    }

    public PSKTlsClient(TlsPSKIdentity tlsPSKIdentity) {
        this(new DefaultTlsCipherFactory(), tlsPSKIdentity);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createPSKKeyExchange(int i) {
        return new TlsPSKKeyExchange(i, this.supportedSignatureAlgorithms, this.pskIdentity, (TlsPSKIdentityManager) null, this.dhVerifier, (DHParameters) null, this.namedCurves, this.clientECPointFormats, this.serverECPointFormats);
    }

    public TlsAuthentication getAuthentication() throws IOException {
        throw new TlsFatalAlert(80);
    }

    public int[] getCipherSuites() {
        return new int[]{CipherSuite.TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA};
    }

    public TlsKeyExchange getKeyExchange() throws IOException {
        int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(this.selectedCipherSuite);
        switch (keyExchangeAlgorithm) {
            case 13:
            case 14:
            case 15:
            case 24:
                return createPSKKeyExchange(keyExchangeAlgorithm);
            default:
                throw new TlsFatalAlert(80);
        }
    }
}
