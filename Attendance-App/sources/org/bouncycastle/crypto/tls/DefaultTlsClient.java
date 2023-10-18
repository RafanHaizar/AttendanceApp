package org.bouncycastle.crypto.tls;

import java.io.IOException;
import org.bouncycastle.crypto.params.DHParameters;

public abstract class DefaultTlsClient extends AbstractTlsClient {
    protected TlsDHVerifier dhVerifier;

    public DefaultTlsClient() {
        this(new DefaultTlsCipherFactory());
    }

    public DefaultTlsClient(TlsCipherFactory tlsCipherFactory) {
        this(tlsCipherFactory, new DefaultTlsDHVerifier());
    }

    public DefaultTlsClient(TlsCipherFactory tlsCipherFactory, TlsDHVerifier tlsDHVerifier) {
        super(tlsCipherFactory);
        this.dhVerifier = tlsDHVerifier;
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createDHEKeyExchange(int i) {
        return new TlsDHEKeyExchange(i, this.supportedSignatureAlgorithms, this.dhVerifier, (DHParameters) null);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createDHKeyExchange(int i) {
        return new TlsDHKeyExchange(i, this.supportedSignatureAlgorithms, this.dhVerifier, (DHParameters) null);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createECDHEKeyExchange(int i) {
        return new TlsECDHEKeyExchange(i, this.supportedSignatureAlgorithms, this.namedCurves, this.clientECPointFormats, this.serverECPointFormats);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createECDHKeyExchange(int i) {
        return new TlsECDHKeyExchange(i, this.supportedSignatureAlgorithms, this.namedCurves, this.clientECPointFormats, this.serverECPointFormats);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createRSAKeyExchange() {
        return new TlsRSAKeyExchange(this.supportedSignatureAlgorithms);
    }

    public int[] getCipherSuites() {
        return new int[]{CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, 60, 47};
    }

    public TlsKeyExchange getKeyExchange() throws IOException {
        int keyExchangeAlgorithm = TlsUtils.getKeyExchangeAlgorithm(this.selectedCipherSuite);
        switch (keyExchangeAlgorithm) {
            case 1:
                return createRSAKeyExchange();
            case 3:
            case 5:
                return createDHEKeyExchange(keyExchangeAlgorithm);
            case 7:
            case 9:
            case 11:
                return createDHKeyExchange(keyExchangeAlgorithm);
            case 16:
            case 18:
            case 20:
                return createECDHKeyExchange(keyExchangeAlgorithm);
            case 17:
            case 19:
                return createECDHEKeyExchange(keyExchangeAlgorithm);
            default:
                throw new TlsFatalAlert(80);
        }
    }
}
