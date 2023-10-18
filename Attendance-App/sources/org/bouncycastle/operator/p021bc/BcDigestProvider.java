package org.bouncycastle.operator.p021bc;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.operator.OperatorCreationException;

/* renamed from: org.bouncycastle.operator.bc.BcDigestProvider */
public interface BcDigestProvider {
    ExtendedDigest get(AlgorithmIdentifier algorithmIdentifier) throws OperatorCreationException;
}
