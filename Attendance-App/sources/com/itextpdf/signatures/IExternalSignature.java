package com.itextpdf.signatures;

import java.security.GeneralSecurityException;

public interface IExternalSignature {
    String getEncryptionAlgorithm();

    String getHashAlgorithm();

    byte[] sign(byte[] bArr) throws GeneralSecurityException;
}
