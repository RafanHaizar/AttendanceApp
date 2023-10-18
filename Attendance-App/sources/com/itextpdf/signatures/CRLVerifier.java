package com.itextpdf.signatures;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CRLVerifier extends RootStoreVerifier {
    protected static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) CRLVerifier.class);
    List<X509CRL> crls;

    public CRLVerifier(CertificateVerifier verifier, List<X509CRL> crls2) {
        super(verifier);
        this.crls = crls2;
    }

    public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
        List<VerificationOK> result = new ArrayList<>();
        int validCrlsFound = 0;
        List<X509CRL> list = this.crls;
        if (list != null) {
            for (X509CRL crl : list) {
                if (verify(crl, signCert, issuerCert, signDate)) {
                    validCrlsFound++;
                }
            }
        }
        boolean online = false;
        if (this.onlineCheckingAllowed && validCrlsFound == 0 && verify(getCRL(signCert, issuerCert), signCert, issuerCert, signDate)) {
            validCrlsFound++;
            online = true;
        }
        LOGGER.info("Valid CRLs found: " + validCrlsFound);
        if (validCrlsFound > 0) {
            result.add(new VerificationOK(signCert, getClass(), "Valid CRLs found: " + validCrlsFound + (online ? " (online)" : "")));
        }
        if (this.verifier != null) {
            result.addAll(this.verifier.verify(signCert, issuerCert, signDate));
        }
        return result;
    }

    public boolean verify(X509CRL crl, X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException {
        if (crl == null || signDate == TimestampConstants.UNDEFINED_TIMESTAMP_DATE || !crl.getIssuerX500Principal().equals(signCert.getIssuerX500Principal()) || !signDate.before(crl.getNextUpdate())) {
            return false;
        }
        if (!isSignatureValid(crl, issuerCert) || !crl.isRevoked(signCert)) {
            return true;
        }
        throw new VerificationException(signCert, "The certificate has been revoked.");
    }

    public X509CRL getCRL(X509Certificate signCert, X509Certificate issuerCert) {
        if (issuerCert == null) {
            X509Certificate issuerCert2 = signCert;
        }
        try {
            String crlurl = CertificateUtil.getCRLURL(signCert);
            if (crlurl == null) {
                return null;
            }
            LOGGER.info("Getting CRL from " + crlurl);
            return (X509CRL) SignUtils.parseCrlFromStream(new URL(crlurl).openStream());
        } catch (IOException e) {
            return null;
        } catch (GeneralSecurityException e2) {
            return null;
        }
    }

    public boolean isSignatureValid(X509CRL crl, X509Certificate crlIssuer) {
        if (crlIssuer != null) {
            try {
                crl.verify(crlIssuer.getPublicKey());
                return true;
            } catch (GeneralSecurityException e) {
                LOGGER.warn("CRL not issued by the same authority as the certificate that is being checked");
            }
        }
        if (this.rootStore == null) {
            return false;
        }
        try {
            for (X509Certificate anchor : SignUtils.getCertificates(this.rootStore)) {
                try {
                    crl.verify(anchor.getPublicKey());
                    return true;
                } catch (GeneralSecurityException e2) {
                }
            }
            return false;
        } catch (GeneralSecurityException e3) {
            return false;
        }
    }
}
