package com.itextpdf.kernel.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class CryptoUtil {
    public static Certificate readPublicCertificate(InputStream is) throws CertificateException {
        return CertificateFactory.getInstance("X.509").generateCertificate(is);
    }

    public static PrivateKey readPrivateKeyFromPKCS12KeyStore(InputStream keyStore, String pkAlias, char[] pkPassword) throws GeneralSecurityException, IOException {
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(keyStore, pkPassword);
        return (PrivateKey) keystore.getKey(pkAlias, pkPassword);
    }
}
