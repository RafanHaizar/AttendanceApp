package com.itextpdf.signatures;

import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.cert.ocsp.SingleResp;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OcspClientBouncyCastle implements IOcspClient {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) OcspClientBouncyCastle.class);
    private final OCSPVerifier verifier;

    public OcspClientBouncyCastle(OCSPVerifier verifier2) {
        this.verifier = verifier2;
    }

    public BasicOCSPResp getBasicOCSPResp(X509Certificate checkCert, X509Certificate rootCert, String url) {
        try {
            OCSPResp ocspResponse = getOcspResponse(checkCert, rootCert, url);
            if (ocspResponse == null || ocspResponse.getStatus() != 0) {
                return null;
            }
            BasicOCSPResp basicResponse = (BasicOCSPResp) ocspResponse.getResponseObject();
            OCSPVerifier oCSPVerifier = this.verifier;
            if (oCSPVerifier != null) {
                oCSPVerifier.isValidResponse(basicResponse, rootCert);
            }
            return basicResponse;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

    public byte[] getEncoded(X509Certificate checkCert, X509Certificate rootCert, String url) {
        try {
            BasicOCSPResp basicResponse = getBasicOCSPResp(checkCert, rootCert, url);
            if (basicResponse == null) {
                return null;
            }
            SingleResp[] responses = basicResponse.getResponses();
            if (responses.length != 1) {
                return null;
            }
            Object status = responses[0].getCertStatus();
            if (status == CertificateStatus.GOOD) {
                return basicResponse.getEncoded();
            }
            if (status instanceof RevokedStatus) {
                throw new IOException(LogMessageConstant.OCSP_STATUS_IS_REVOKED);
            }
            throw new IOException(LogMessageConstant.OCSP_STATUS_IS_UNKNOWN);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

    private static OCSPReq generateOCSPRequest(X509Certificate issuerCert, BigInteger serialNumber) throws OCSPException, IOException, OperatorException, CertificateEncodingException {
        Security.addProvider(new BouncyCastleProvider());
        return SignUtils.generateOcspRequestWithNonce(SignUtils.generateCertificateId(issuerCert, serialNumber, CertificateID.HASH_SHA1));
    }

    private OCSPResp getOcspResponse(X509Certificate checkCert, X509Certificate rootCert, String url) throws GeneralSecurityException, OCSPException, IOException, OperatorException {
        if (checkCert == null || rootCert == null) {
            return null;
        }
        if (url == null) {
            url = CertificateUtil.getOCSPURL(checkCert);
        }
        if (url == null) {
            return null;
        }
        LOGGER.info("Getting OCSP from " + url);
        return new OCSPResp(StreamUtil.inputStreamToArray(SignUtils.getHttpResponseForOcspRequest(generateOCSPRequest(rootCert, checkCert.getSerialNumber()).getEncoded(), new URL(url))));
    }
}
