package org.bouncycastle.cms.p011bc;

import java.io.InputStream;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.RecipientOperator;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.p013io.CipherInputStream;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.operator.InputDecryptor;
import org.bouncycastle.operator.p021bc.BcSymmetricKeyUnwrapper;

/* renamed from: org.bouncycastle.cms.bc.BcKEKEnvelopedRecipient */
public class BcKEKEnvelopedRecipient extends BcKEKRecipient {
    public BcKEKEnvelopedRecipient(BcSymmetricKeyUnwrapper bcSymmetricKeyUnwrapper) {
        super(bcSymmetricKeyUnwrapper);
    }

    public RecipientOperator getRecipientOperator(AlgorithmIdentifier algorithmIdentifier, final AlgorithmIdentifier algorithmIdentifier2, byte[] bArr) throws CMSException {
        final Object createContentCipher = EnvelopedDataHelper.createContentCipher(false, (KeyParameter) extractSecretKey(algorithmIdentifier, algorithmIdentifier2, bArr), algorithmIdentifier2);
        return new RecipientOperator((InputDecryptor) new InputDecryptor() {
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return algorithmIdentifier2;
            }

            public InputStream getInputStream(InputStream inputStream) {
                return createContentCipher instanceof BufferedBlockCipher ? new CipherInputStream(inputStream, (BufferedBlockCipher) createContentCipher) : new CipherInputStream(inputStream, (StreamCipher) createContentCipher);
            }
        });
    }
}
