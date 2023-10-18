package org.bouncycastle.operator.p021bc;

import java.io.OutputStream;
import java.security.SecureRandom;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.RuntimeOperatorException;

/* renamed from: org.bouncycastle.operator.bc.BcContentSignerBuilder */
public abstract class BcContentSignerBuilder {
    private AlgorithmIdentifier digAlgId;
    protected BcDigestProvider digestProvider = BcDefaultDigestProvider.INSTANCE;
    private SecureRandom random;
    /* access modifiers changed from: private */
    public AlgorithmIdentifier sigAlgId;

    public BcContentSignerBuilder(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        this.sigAlgId = algorithmIdentifier;
        this.digAlgId = algorithmIdentifier2;
    }

    public ContentSigner build(AsymmetricKeyParameter asymmetricKeyParameter) throws OperatorCreationException {
        Signer createSigner = createSigner(this.sigAlgId, this.digAlgId);
        if (this.random != null) {
            createSigner.init(true, new ParametersWithRandom(asymmetricKeyParameter, this.random));
        } else {
            createSigner.init(true, asymmetricKeyParameter);
        }
        return new ContentSigner(createSigner) {
            private BcSignerOutputStream stream;
            final /* synthetic */ Signer val$sig;

            {
                this.val$sig = r2;
                this.stream = new BcSignerOutputStream(r2);
            }

            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return BcContentSignerBuilder.this.sigAlgId;
            }

            public OutputStream getOutputStream() {
                return this.stream;
            }

            public byte[] getSignature() {
                try {
                    return this.stream.getSignature();
                } catch (CryptoException e) {
                    throw new RuntimeOperatorException("exception obtaining signature: " + e.getMessage(), e);
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    public abstract Signer createSigner(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) throws OperatorCreationException;

    public BcContentSignerBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }
}
