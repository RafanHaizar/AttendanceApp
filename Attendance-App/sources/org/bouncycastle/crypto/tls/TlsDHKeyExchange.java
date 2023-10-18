package org.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;

public class TlsDHKeyExchange extends AbstractTlsKeyExchange {
    protected TlsAgreementCredentials agreementCredentials;
    protected DHPrivateKeyParameters dhAgreePrivateKey;
    protected DHPublicKeyParameters dhAgreePublicKey;
    protected DHParameters dhParameters;
    protected TlsDHVerifier dhVerifier;
    protected AsymmetricKeyParameter serverPublicKey;
    protected TlsSigner tlsSigner;

    public TlsDHKeyExchange(int i, Vector vector, DHParameters dHParameters) {
        this(i, vector, new DefaultTlsDHVerifier(), dHParameters);
    }

    public TlsDHKeyExchange(int i, Vector vector, TlsDHVerifier tlsDHVerifier, DHParameters dHParameters) {
        super(i, vector);
        TlsSigner tlsSigner2;
        switch (i) {
            case 3:
                tlsSigner2 = new TlsDSSSigner();
                break;
            case 5:
                tlsSigner2 = new TlsRSASigner();
                break;
            case 7:
            case 9:
            case 11:
                tlsSigner2 = null;
                break;
            default:
                throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
        this.tlsSigner = tlsSigner2;
        this.dhVerifier = tlsDHVerifier;
        this.dhParameters = dHParameters;
    }

    public void generateClientKeyExchange(OutputStream outputStream) throws IOException {
        if (this.agreementCredentials == null) {
            this.dhAgreePrivateKey = TlsDHUtils.generateEphemeralClientKeyExchange(this.context.getSecureRandom(), this.dhParameters, outputStream);
        }
    }

    public byte[] generatePremasterSecret() throws IOException {
        TlsAgreementCredentials tlsAgreementCredentials = this.agreementCredentials;
        if (tlsAgreementCredentials != null) {
            return tlsAgreementCredentials.generateAgreement(this.dhAgreePublicKey);
        }
        DHPrivateKeyParameters dHPrivateKeyParameters = this.dhAgreePrivateKey;
        if (dHPrivateKeyParameters != null) {
            return TlsDHUtils.calculateDHBasicAgreement(this.dhAgreePublicKey, dHPrivateKeyParameters);
        }
        throw new TlsFatalAlert(80);
    }

    public byte[] generateServerKeyExchange() throws IOException {
        if (!requiresServerKeyExchange()) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.dhAgreePrivateKey = TlsDHUtils.generateEphemeralServerKeyExchange(this.context.getSecureRandom(), this.dhParameters, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void init(TlsContext tlsContext) {
        super.init(tlsContext);
        TlsSigner tlsSigner2 = this.tlsSigner;
        if (tlsSigner2 != null) {
            tlsSigner2.init(tlsContext);
        }
    }

    public void processClientCertificate(Certificate certificate) throws IOException {
        if (this.keyExchange == 11) {
            throw new TlsFatalAlert(10);
        }
    }

    public void processClientCredentials(TlsCredentials tlsCredentials) throws IOException {
        if (this.keyExchange == 11) {
            throw new TlsFatalAlert(80);
        } else if (tlsCredentials instanceof TlsAgreementCredentials) {
            this.agreementCredentials = (TlsAgreementCredentials) tlsCredentials;
        } else if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
        }
    }

    public void processClientKeyExchange(InputStream inputStream) throws IOException {
        if (this.dhAgreePublicKey == null) {
            this.dhAgreePublicKey = new DHPublicKeyParameters(TlsDHUtils.readDHParameter(inputStream), this.dhParameters);
        }
    }

    public void processServerCertificate(Certificate certificate) throws IOException {
        int i;
        if (this.keyExchange == 11) {
            throw new TlsFatalAlert(10);
        } else if (!certificate.isEmpty()) {
            Certificate certificateAt = certificate.getCertificateAt(0);
            try {
                AsymmetricKeyParameter createKey = PublicKeyFactory.createKey(certificateAt.getSubjectPublicKeyInfo());
                this.serverPublicKey = createKey;
                TlsSigner tlsSigner2 = this.tlsSigner;
                if (tlsSigner2 == null) {
                    try {
                        DHPublicKeyParameters dHPublicKeyParameters = (DHPublicKeyParameters) createKey;
                        this.dhAgreePublicKey = dHPublicKeyParameters;
                        this.dhParameters = dHPublicKeyParameters.getParameters();
                        i = 8;
                    } catch (ClassCastException e) {
                        throw new TlsFatalAlert(46, e);
                    }
                } else if (tlsSigner2.isValidPublicKey(createKey)) {
                    i = 128;
                } else {
                    throw new TlsFatalAlert(46);
                }
                TlsUtils.validateKeyUsage(certificateAt, i);
                super.processServerCertificate(certificate);
            } catch (RuntimeException e2) {
                throw new TlsFatalAlert(43, e2);
            }
        } else {
            throw new TlsFatalAlert(42);
        }
    }

    public void processServerKeyExchange(InputStream inputStream) throws IOException {
        if (requiresServerKeyExchange()) {
            this.dhParameters = TlsDHUtils.receiveDHParameters(this.dhVerifier, inputStream);
            this.dhAgreePublicKey = new DHPublicKeyParameters(TlsDHUtils.readDHParameter(inputStream), this.dhParameters);
            return;
        }
        throw new TlsFatalAlert(10);
    }

    public boolean requiresServerKeyExchange() {
        switch (this.keyExchange) {
            case 3:
            case 5:
            case 11:
                return true;
            default:
                return false;
        }
    }

    public void skipServerCredentials() throws IOException {
        if (this.keyExchange != 11) {
            throw new TlsFatalAlert(10);
        }
    }

    public void validateCertificateRequest(CertificateRequest certificateRequest) throws IOException {
        if (this.keyExchange != 11) {
            short[] certificateTypes = certificateRequest.getCertificateTypes();
            int i = 0;
            while (i < certificateTypes.length) {
                switch (certificateTypes[i]) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 64:
                        i++;
                    default:
                        throw new TlsFatalAlert(47);
                }
            }
            return;
        }
        throw new TlsFatalAlert(40);
    }
}
