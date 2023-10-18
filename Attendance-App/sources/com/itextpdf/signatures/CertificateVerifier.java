package com.itextpdf.signatures;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CertificateVerifier {
    protected boolean onlineCheckingAllowed = true;
    protected CertificateVerifier verifier;

    public CertificateVerifier(CertificateVerifier verifier2) {
        this.verifier = verifier2;
    }

    public void setOnlineCheckingAllowed(boolean onlineCheckingAllowed2) {
        this.onlineCheckingAllowed = onlineCheckingAllowed2;
    }

    public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
        if (signDate != null) {
            signCert.checkValidity(signDate);
        }
        if (issuerCert != null) {
            signCert.verify(issuerCert.getPublicKey());
        } else {
            signCert.verify(signCert.getPublicKey());
        }
        List<VerificationOK> result = new ArrayList<>();
        CertificateVerifier certificateVerifier = this.verifier;
        if (certificateVerifier != null) {
            result.addAll(certificateVerifier.verify(signCert, issuerCert, signDate));
        }
        return result;
    }
}
