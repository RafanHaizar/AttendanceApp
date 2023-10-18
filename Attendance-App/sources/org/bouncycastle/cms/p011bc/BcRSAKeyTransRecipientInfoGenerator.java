package org.bouncycastle.cms.p011bc;

import java.io.IOException;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.operator.p021bc.BcAsymmetricKeyWrapper;
import org.bouncycastle.operator.p021bc.BcRSAAsymmetricKeyWrapper;

/* renamed from: org.bouncycastle.cms.bc.BcRSAKeyTransRecipientInfoGenerator */
public class BcRSAKeyTransRecipientInfoGenerator extends BcKeyTransRecipientInfoGenerator {
    public BcRSAKeyTransRecipientInfoGenerator(X509CertificateHolder x509CertificateHolder) throws IOException {
        super(x509CertificateHolder, (BcAsymmetricKeyWrapper) new BcRSAAsymmetricKeyWrapper(x509CertificateHolder.getSubjectPublicKeyInfo().getAlgorithm(), x509CertificateHolder.getSubjectPublicKeyInfo()));
    }

    public BcRSAKeyTransRecipientInfoGenerator(byte[] bArr, AlgorithmIdentifier algorithmIdentifier, AsymmetricKeyParameter asymmetricKeyParameter) {
        super(bArr, (BcAsymmetricKeyWrapper) new BcRSAAsymmetricKeyWrapper(algorithmIdentifier, asymmetricKeyParameter));
    }
}
