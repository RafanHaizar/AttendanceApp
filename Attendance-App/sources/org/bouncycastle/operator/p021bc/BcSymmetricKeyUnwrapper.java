package org.bouncycastle.operator.p021bc;

import java.security.SecureRandom;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.OperatorException;
import org.bouncycastle.operator.SymmetricKeyUnwrapper;

/* renamed from: org.bouncycastle.operator.bc.BcSymmetricKeyUnwrapper */
public class BcSymmetricKeyUnwrapper extends SymmetricKeyUnwrapper {
    private SecureRandom random;
    private Wrapper wrapper;
    private KeyParameter wrappingKey;

    public BcSymmetricKeyUnwrapper(AlgorithmIdentifier algorithmIdentifier, Wrapper wrapper2, KeyParameter keyParameter) {
        super(algorithmIdentifier);
        this.wrapper = wrapper2;
        this.wrappingKey = keyParameter;
    }

    public GenericKey generateUnwrappedKey(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) throws OperatorException {
        this.wrapper.init(false, this.wrappingKey);
        try {
            return new GenericKey(algorithmIdentifier, this.wrapper.unwrap(bArr, 0, bArr.length));
        } catch (InvalidCipherTextException e) {
            throw new OperatorException("unable to unwrap key: " + e.getMessage(), e);
        }
    }

    public BcSymmetricKeyUnwrapper setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }
}
