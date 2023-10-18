package com.itextpdf.signatures;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RootStoreVerifier extends CertificateVerifier {
    protected KeyStore rootStore = null;

    public RootStoreVerifier(CertificateVerifier verifier) {
        super(verifier);
    }

    public void setRootStore(KeyStore keyStore) {
        this.rootStore = keyStore;
    }

    public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
        if (this.rootStore == null) {
            return super.verify(signCert, issuerCert, signDate);
        }
        try {
            List<VerificationOK> result = new ArrayList<>();
            for (X509Certificate anchor : SignUtils.getCertificates(this.rootStore)) {
                try {
                    signCert.verify(anchor.getPublicKey());
                    result.add(new VerificationOK(signCert, getClass(), "Certificate verified against root store."));
                    result.addAll(super.verify(signCert, issuerCert, signDate));
                    return result;
                } catch (GeneralSecurityException e) {
                }
            }
            result.addAll(super.verify(signCert, issuerCert, signDate));
            return result;
        } catch (GeneralSecurityException e2) {
            return super.verify(signCert, issuerCert, signDate);
        }
    }
}
