package com.itextpdf.signatures;

import com.itextpdf.p026io.util.DateTimeUtil;
import java.security.KeyStore;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.tsp.TimeStampToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertificateVerification {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) CrlClientOnline.class);

    public static String verifyCertificate(X509Certificate cert, Collection<CRL> crls) {
        return verifyCertificate(cert, crls, DateTimeUtil.getCurrentTimeCalendar());
    }

    public static String verifyCertificate(X509Certificate cert, Collection<CRL> crls, Calendar calendar) {
        if (SignUtils.hasUnsupportedCriticalExtension(cert)) {
            return "Has unsupported critical extension";
        }
        try {
            cert.checkValidity(calendar.getTime());
            if (crls == null) {
                return null;
            }
            for (CRL crl : crls) {
                if (crl.isRevoked(cert)) {
                    return "Certificate revoked";
                }
            }
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore, Collection<CRL> crls) {
        return verifyCertificates(certs, keystore, crls, DateTimeUtil.getCurrentTimeCalendar());
    }

    public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore, Collection<CRL> crls, Calendar calendar) {
        List<VerificationException> result = new ArrayList<>();
        for (int k = 0; k < certs.length; k++) {
            X509Certificate cert = certs[k];
            String err = verifyCertificate(cert, crls, calendar);
            if (err != null) {
                result.add(new VerificationException(cert, err));
            }
            try {
                for (X509Certificate certStoreX509 : SignUtils.getCertificates(keystore)) {
                    try {
                        if (verifyCertificate(certStoreX509, crls, calendar) != null) {
                            continue;
                        } else {
                            try {
                                cert.verify(certStoreX509.getPublicKey());
                                return result;
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e2) {
                    }
                }
            } catch (Exception e3) {
            }
            int j = 0;
            while (j < certs.length) {
                if (j != k) {
                    try {
                        cert.verify(certs[j].getPublicKey());
                        break;
                    } catch (Exception e4) {
                    }
                }
                j++;
            }
            if (j == certs.length) {
                result.add(new VerificationException(cert, "Cannot be verified against the KeyStore or the certificate chain"));
            }
        }
        if (result.size() == 0) {
            Certificate certificate = null;
            result.add(new VerificationException((Certificate) null, "Invalid state. Possible circular certificate chain"));
        }
        return result;
    }

    public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore) {
        return verifyCertificates(certs, keystore, DateTimeUtil.getCurrentTimeCalendar());
    }

    public static List<VerificationException> verifyCertificates(Certificate[] certs, KeyStore keystore, Calendar calendar) {
        return verifyCertificates(certs, keystore, (Collection<CRL>) null, calendar);
    }

    public static boolean verifyOcspCertificates(BasicOCSPResp ocsp, KeyStore keystore, String provider) {
        List<Exception> exceptionsThrown = new ArrayList<>();
        try {
            for (X509Certificate certStoreX509 : SignUtils.getCertificates(keystore)) {
                try {
                    return SignUtils.isSignatureValid(ocsp, (Certificate) certStoreX509, provider);
                } catch (Exception ex) {
                    exceptionsThrown.add(ex);
                }
            }
        } catch (Exception e) {
            exceptionsThrown.add(e);
        }
        for (Exception ex2 : exceptionsThrown) {
            LOGGER.error(ex2.getMessage(), (Throwable) ex2);
        }
        return false;
    }

    public static boolean verifyTimestampCertificates(TimeStampToken ts, KeyStore keystore, String provider) {
        List<Exception> exceptionsThrown = new ArrayList<>();
        try {
            for (X509Certificate certStoreX509 : SignUtils.getCertificates(keystore)) {
                try {
                    SignUtils.isSignatureValid(ts, certStoreX509, provider);
                    return true;
                } catch (Exception ex) {
                    exceptionsThrown.add(ex);
                }
            }
        } catch (Exception e) {
            exceptionsThrown.add(e);
        }
        for (Exception ex2 : exceptionsThrown) {
            LOGGER.error(ex2.getMessage(), (Throwable) ex2);
        }
        return false;
    }
}
