package com.itextpdf.signatures;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public class ProviderDigest implements IExternalDigest {
    private String provider;

    public ProviderDigest(String provider2) {
        this.provider = provider2;
    }

    public MessageDigest getMessageDigest(String hashAlgorithm) throws GeneralSecurityException {
        return DigestAlgorithms.getMessageDigest(hashAlgorithm, this.provider);
    }
}
