package org.bouncycastle.crypto.agreement;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.RawAgreement;
import org.bouncycastle.crypto.params.XDHUPrivateParameters;
import org.bouncycastle.crypto.params.XDHUPublicParameters;

public class XDHUnifiedAgreement implements RawAgreement {
    private XDHUPrivateParameters privParams;
    private final RawAgreement xAgreement;

    public XDHUnifiedAgreement(RawAgreement rawAgreement) {
        this.xAgreement = rawAgreement;
    }

    public void calculateAgreement(CipherParameters cipherParameters, byte[] bArr, int i) {
        XDHUPublicParameters xDHUPublicParameters = (XDHUPublicParameters) cipherParameters;
        this.xAgreement.init(this.privParams.getEphemeralPrivateKey());
        this.xAgreement.calculateAgreement(xDHUPublicParameters.getEphemeralPublicKey(), bArr, i);
        this.xAgreement.init(this.privParams.getStaticPrivateKey());
        this.xAgreement.calculateAgreement(xDHUPublicParameters.getStaticPublicKey(), bArr, i + this.xAgreement.getAgreementSize());
    }

    public int getAgreementSize() {
        return this.xAgreement.getAgreementSize() * 2;
    }

    public void init(CipherParameters cipherParameters) {
        this.privParams = (XDHUPrivateParameters) cipherParameters;
    }
}
