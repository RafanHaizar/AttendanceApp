package com.itextpdf.signatures;

import com.itextpdf.p026io.util.DateTimeUtil;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.SingleResp;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OCSPVerifier extends RootStoreVerifier {
    protected static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) OCSPVerifier.class);
    protected static final String id_kp_OCSPSigning = "1.3.6.1.5.5.7.3.9";
    protected List<BasicOCSPResp> ocsps;

    public OCSPVerifier(CertificateVerifier verifier, List<BasicOCSPResp> ocsps2) {
        super(verifier);
        this.ocsps = ocsps2;
    }

    public List<VerificationOK> verify(X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
        List<VerificationOK> result = new ArrayList<>();
        int validOCSPsFound = 0;
        List<BasicOCSPResp> list = this.ocsps;
        if (list != null) {
            for (BasicOCSPResp ocspResp : list) {
                if (verify(ocspResp, signCert, issuerCert, signDate)) {
                    validOCSPsFound++;
                }
            }
        }
        boolean online = false;
        if (this.onlineCheckingAllowed && validOCSPsFound == 0 && verify(getOcspResponse(signCert, issuerCert), signCert, issuerCert, signDate)) {
            validOCSPsFound++;
            online = true;
        }
        LOGGER.info("Valid OCSPs found: " + validOCSPsFound);
        if (validOCSPsFound > 0) {
            result.add(new VerificationOK(signCert, getClass(), "Valid OCSPs Found: " + validOCSPsFound + (online ? " (online)" : "")));
        }
        if (this.verifier != null) {
            result.addAll(this.verifier.verify(signCert, issuerCert, signDate));
        }
        return result;
    }

    public boolean verify(BasicOCSPResp ocspResp, X509Certificate signCert, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException, IOException {
        if (ocspResp == null) {
            return false;
        }
        SingleResp[] resp = ocspResp.getResponses();
        for (int i = 0; i < resp.length; i++) {
            if (signCert.getSerialNumber().equals(resp[i].getCertID().getSerialNumber())) {
                if (issuerCert == null) {
                    issuerCert = signCert;
                }
                try {
                    if (!SignUtils.checkIfIssuersMatch(resp[i].getCertID(), issuerCert)) {
                        LOGGER.info("OCSP: Issuers doesn't match.");
                    } else {
                        if (resp[i].getNextUpdate() == null) {
                            Date nextUpdate = SignUtils.add180Sec(resp[i].getThisUpdate());
                            Logger logger = LOGGER;
                            logger.info(MessageFormatUtil.format("No 'next update' for OCSP Response; assuming {0}", nextUpdate));
                            if (signDate.after(nextUpdate)) {
                                logger.info(MessageFormatUtil.format("OCSP no longer valid: {0} after {1}", signDate, nextUpdate));
                            }
                        } else if (signDate.after(resp[i].getNextUpdate())) {
                            LOGGER.info(MessageFormatUtil.format("OCSP no longer valid: {0} after {1}", signDate, resp[i].getNextUpdate()));
                        }
                        if (resp[i].getCertStatus() == CertificateStatus.GOOD) {
                            isValidResponse(ocspResp, issuerCert, signDate);
                            return true;
                        }
                    }
                } catch (OCSPException e) {
                }
            }
        }
        return false;
    }

    @Deprecated
    public void isValidResponse(BasicOCSPResp ocspResp, X509Certificate issuerCert) throws GeneralSecurityException, IOException {
        isValidResponse(ocspResp, issuerCert, DateTimeUtil.getCurrentTimeDate());
    }

    public void isValidResponse(BasicOCSPResp ocspResp, X509Certificate issuerCert, Date signDate) throws GeneralSecurityException {
        CRL crl;
        X509Certificate responderCert = null;
        if (isSignatureValid(ocspResp, issuerCert)) {
            responderCert = issuerCert;
        }
        if (responderCert != null) {
            return;
        }
        if (ocspResp.getCerts() != null) {
            Iterator<X509Certificate> it = SignUtils.getCertsFromOcspResponse(ocspResp).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                X509Certificate cert = it.next();
                try {
                    List keyPurposes = cert.getExtendedKeyUsage();
                    if (keyPurposes != null && keyPurposes.contains(id_kp_OCSPSigning) && isSignatureValid(ocspResp, cert)) {
                        responderCert = cert;
                        break;
                    }
                } catch (CertificateParsingException e) {
                }
            }
            if (responderCert != null) {
                responderCert.verify(issuerCert.getPublicKey());
                responderCert.checkValidity(signDate);
                if (responderCert.getExtensionValue(OCSPObjectIdentifiers.id_pkix_ocsp_nocheck.getId()) == null) {
                    try {
                        crl = CertificateUtil.getCRL(responderCert);
                    } catch (Exception e2) {
                        crl = null;
                    }
                    if (crl == null || !(crl instanceof X509CRL)) {
                        LoggerFactory.getLogger((Class<?>) OCSPVerifier.class).error("Authorized OCSP responder certificate revocation status cannot be checked");
                        return;
                    }
                    CRLVerifier crlVerifier = new CRLVerifier((CertificateVerifier) null, (List<X509CRL>) null);
                    crlVerifier.setRootStore(this.rootStore);
                    crlVerifier.setOnlineCheckingAllowed(this.onlineCheckingAllowed);
                    if (!crlVerifier.verify(crl, responderCert, issuerCert, signDate)) {
                        throw new VerificationException(issuerCert, "Authorized OCSP responder certificate was revoked.");
                    }
                    return;
                }
                return;
            }
            throw new VerificationException(issuerCert, "OCSP response could not be verified");
        }
        if (this.rootStore != null) {
            try {
                Iterator<X509Certificate> it2 = SignUtils.getCertificates(this.rootStore).iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    X509Certificate anchor = it2.next();
                    if (isSignatureValid(ocspResp, anchor)) {
                        responderCert = anchor;
                        break;
                    }
                }
            } catch (Exception e3) {
                responderCert = null;
            }
        }
        if (responderCert == null) {
            throw new VerificationException(issuerCert, "OCSP response could not be verified: it does not contain certificate chain and response is not signed by issuer certificate or any from the root store.");
        }
    }

    public boolean isSignatureValid(BasicOCSPResp ocspResp, Certificate responderCert) {
        try {
            return SignUtils.isSignatureValid(ocspResp, responderCert, BouncyCastleProvider.PROVIDER_NAME);
        } catch (Exception e) {
            return false;
        }
    }

    public BasicOCSPResp getOcspResponse(X509Certificate signCert, X509Certificate issuerCert) {
        BasicOCSPResp ocspResp;
        if ((signCert == null && issuerCert == null) || (ocspResp = new OcspClientBouncyCastle((OCSPVerifier) null).getBasicOCSPResp(signCert, issuerCert, (String) null)) == null) {
            return null;
        }
        for (SingleResp resp : ocspResp.getResponses()) {
            if (resp.getCertStatus() == CertificateStatus.GOOD) {
                return ocspResp;
            }
        }
        return null;
    }
}
