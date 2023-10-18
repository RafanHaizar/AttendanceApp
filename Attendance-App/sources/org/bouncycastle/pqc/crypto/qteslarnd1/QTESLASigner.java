package org.bouncycastle.pqc.crypto.qteslarnd1;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;

public class QTESLASigner implements MessageSigner {
    private QTESLAPrivateKeyParameters privateKey;
    private QTESLAPublicKeyParameters publicKey;
    private SecureRandom secureRandom;

    public byte[] generateSignature(byte[] bArr) {
        byte[] bArr2 = new byte[QTESLASecurityCategory.getSignatureSize(this.privateKey.getSecurityCategory())];
        switch (this.privateKey.getSecurityCategory()) {
            case 0:
                QTESLA.signingI(bArr2, bArr, 0, bArr.length, this.privateKey.getSecret(), this.secureRandom);
                break;
            case 1:
                QTESLA.signingIIISize(bArr2, bArr, 0, bArr.length, this.privateKey.getSecret(), this.secureRandom);
                break;
            case 2:
                QTESLA.signingIIISpeed(bArr2, bArr, 0, bArr.length, this.privateKey.getSecret(), this.secureRandom);
                break;
            case 3:
                QTESLA.signingIP(bArr2, bArr, 0, bArr.length, this.privateKey.getSecret(), this.secureRandom);
                break;
            case 4:
                QTESLA.signingIIIP(bArr2, bArr, 0, bArr.length, this.privateKey.getSecret(), this.secureRandom);
                break;
            default:
                throw new IllegalArgumentException("unknown security category: " + this.privateKey.getSecurityCategory());
        }
        return bArr2;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        int i;
        if (z) {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.secureRandom = parametersWithRandom.getRandom();
                this.privateKey = (QTESLAPrivateKeyParameters) parametersWithRandom.getParameters();
            } else {
                this.secureRandom = CryptoServicesRegistrar.getSecureRandom();
                this.privateKey = (QTESLAPrivateKeyParameters) cipherParameters;
            }
            this.publicKey = null;
            i = this.privateKey.getSecurityCategory();
        } else {
            this.privateKey = null;
            QTESLAPublicKeyParameters qTESLAPublicKeyParameters = (QTESLAPublicKeyParameters) cipherParameters;
            this.publicKey = qTESLAPublicKeyParameters;
            i = qTESLAPublicKeyParameters.getSecurityCategory();
        }
        QTESLASecurityCategory.validate(i);
    }

    public boolean verifySignature(byte[] bArr, byte[] bArr2) {
        int i;
        switch (this.publicKey.getSecurityCategory()) {
            case 0:
                i = QTESLA.verifyingI(bArr, bArr2, 0, bArr2.length, this.publicKey.getPublicData());
                break;
            case 1:
                i = QTESLA.verifyingIIISize(bArr, bArr2, 0, bArr2.length, this.publicKey.getPublicData());
                break;
            case 2:
                i = QTESLA.verifyingIIISpeed(bArr, bArr2, 0, bArr2.length, this.publicKey.getPublicData());
                break;
            case 3:
                i = QTESLA.verifyingPI(bArr, bArr2, 0, bArr2.length, this.publicKey.getPublicData());
                break;
            case 4:
                i = QTESLA.verifyingPIII(bArr, bArr2, 0, bArr2.length, this.publicKey.getPublicData());
                break;
            default:
                throw new IllegalArgumentException("unknown security category: " + this.publicKey.getSecurityCategory());
        }
        return i == 0;
    }
}
