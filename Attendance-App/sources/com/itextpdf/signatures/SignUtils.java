package com.itextpdf.signatures;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfEncryption;
import com.itextpdf.p026io.codec.Base64;
import com.itextpdf.signatures.OID;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.esf.SigPolicyQualifierInfo;
import org.bouncycastle.asn1.esf.SigPolicyQualifiers;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPReq;
import org.bouncycastle.cert.ocsp.OCSPReqBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.X509CertParser;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.operator.p021bc.BcDigestCalculatorProvider;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.x509.util.StreamParsingException;

final class SignUtils {
    SignUtils() {
    }

    static String getPrivateKeyAlgorithm(PrivateKey pk) {
        String algorithm = pk.getAlgorithm();
        if (algorithm.equals("EC")) {
            return "ECDSA";
        }
        return algorithm;
    }

    static CRL parseCrlFromStream(InputStream input) throws CertificateException, CRLException {
        return CertificateFactory.getInstance("X.509").generateCRL(input);
    }

    static byte[] getExtensionValueByOid(X509Certificate certificate, String oid) {
        return certificate.getExtensionValue(oid);
    }

    static MessageDigest getMessageDigest(String hashAlgorithm) throws GeneralSecurityException {
        return new BouncyCastleDigest().getMessageDigest(hashAlgorithm);
    }

    static MessageDigest getMessageDigest(String hashAlgorithm, IExternalDigest externalDigest) throws GeneralSecurityException {
        return externalDigest.getMessageDigest(hashAlgorithm);
    }

    static MessageDigest getMessageDigest(String hashAlgorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (provider == null || provider.startsWith("SunPKCS11") || provider.startsWith("SunMSCAPI")) {
            return MessageDigest.getInstance(DigestAlgorithms.normalizeDigestName(hashAlgorithm));
        }
        return MessageDigest.getInstance(hashAlgorithm, provider);
    }

    static InputStream getHttpResponse(URL urlt) throws IOException {
        HttpURLConnection con = (HttpURLConnection) urlt.openConnection();
        if (con.getResponseCode() / 100 == 2) {
            return (InputStream) con.getContent();
        }
        throw new PdfException(PdfException.InvalidHttpResponse1).setMessageParams(Integer.valueOf(con.getResponseCode()));
    }

    static CertificateID generateCertificateId(X509Certificate issuerCert, BigInteger serialNumber, AlgorithmIdentifier digestAlgorithmIdentifier) throws OperatorCreationException, CertificateEncodingException, OCSPException {
        return new CertificateID(new JcaDigestCalculatorProviderBuilder().build().get(digestAlgorithmIdentifier), new JcaX509CertificateHolder(issuerCert), serialNumber);
    }

    static CertificateID generateCertificateId(X509Certificate issuerCert, BigInteger serialNumber, ASN1ObjectIdentifier identifier) throws OperatorCreationException, CertificateEncodingException, OCSPException {
        return new CertificateID(new JcaDigestCalculatorProviderBuilder().build().get(new AlgorithmIdentifier(identifier, DERNull.INSTANCE)), new JcaX509CertificateHolder(issuerCert), serialNumber);
    }

    static OCSPReq generateOcspRequestWithNonce(CertificateID id) throws IOException, OCSPException {
        OCSPReqBuilder gen = new OCSPReqBuilder();
        gen.addRequest(id);
        gen.setRequestExtensions(new Extensions(new Extension[]{new Extension(OCSPObjectIdentifiers.id_pkix_ocsp_nonce, false, (ASN1OctetString) new DEROctetString(new DEROctetString(PdfEncryption.generateNewDocumentId()).getEncoded()))}));
        return gen.build();
    }

    static InputStream getHttpResponseForOcspRequest(byte[] request, URL urlt) throws IOException {
        HttpURLConnection con = (HttpURLConnection) urlt.openConnection();
        con.setRequestProperty("Content-Type", "application/ocsp-request");
        con.setRequestProperty("Accept", "application/ocsp-response");
        con.setDoOutput(true);
        DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(con.getOutputStream()));
        dataOut.write(request);
        dataOut.flush();
        dataOut.close();
        if (con.getResponseCode() / 100 == 2) {
            return (InputStream) con.getContent();
        }
        throw new PdfException(PdfException.InvalidHttpResponse1).setMessageParams(Integer.valueOf(con.getResponseCode()));
    }

    static boolean isSignatureValid(BasicOCSPResp validator, Certificate certStoreX509, String provider) throws OperatorCreationException, OCSPException {
        if (provider == null) {
            provider = BouncyCastleProvider.PROVIDER_NAME;
        }
        return validator.isSignatureValid(new JcaContentVerifierProviderBuilder().setProvider(provider).build(certStoreX509.getPublicKey()));
    }

    static void isSignatureValid(TimeStampToken validator, X509Certificate certStoreX509, String provider) throws OperatorCreationException, TSPException {
        if (provider == null) {
            provider = BouncyCastleProvider.PROVIDER_NAME;
        }
        validator.validate(new JcaSimpleSignerInfoVerifierBuilder().setProvider(provider).build(certStoreX509));
    }

    static boolean checkIfIssuersMatch(CertificateID certID, X509Certificate issuerCert) throws CertificateEncodingException, IOException, OCSPException {
        return certID.matchesIssuer(new X509CertificateHolder(issuerCert.getEncoded()), new BcDigestCalculatorProvider());
    }

    static Date add180Sec(Date date) {
        return new Date(date.getTime() + 180000);
    }

    static Iterable<X509Certificate> getCertsFromOcspResponse(BasicOCSPResp ocspResp) {
        List<X509Certificate> certs = new ArrayList<>();
        X509CertificateHolder[] certHolders = ocspResp.getCerts();
        JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
        for (X509CertificateHolder certHolder : certHolders) {
            try {
                certs.add(converter.getCertificate(certHolder));
            } catch (Exception e) {
            }
        }
        return certs;
    }

    static Collection<Certificate> readAllCerts(byte[] contentsKey) throws StreamParsingException {
        X509CertParser cr = new X509CertParser();
        cr.engineInit(new ByteArrayInputStream(contentsKey));
        return cr.engineReadAll();
    }

    static <T> T getFirstElement(Iterable<T> iterable) {
        return iterable.iterator().next();
    }

    static X509Principal getIssuerX509Name(ASN1Sequence issuerAndSerialNumber) throws IOException {
        return new X509Principal(issuerAndSerialNumber.getObjectAt(0).toASN1Primitive().getEncoded());
    }

    public static String dateToString(Calendar signDate) {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").format(signDate.getTime());
    }

    static class TsaResponse {
        String encoding;
        InputStream tsaResponseStream;

        TsaResponse() {
        }
    }

    static TsaResponse getTsaResponseForUserRequest(String tsaUrl, byte[] requestBytes, String tsaUsername, String tsaPassword) throws IOException {
        try {
            URLConnection tsaConnection = new URL(tsaUrl).openConnection();
            tsaConnection.setDoInput(true);
            tsaConnection.setDoOutput(true);
            tsaConnection.setUseCaches(false);
            tsaConnection.setRequestProperty("Content-Type", "application/timestamp-query");
            tsaConnection.setRequestProperty("Content-Transfer-Encoding", "binary");
            if (tsaUsername != null && !tsaUsername.equals("")) {
                tsaConnection.setRequestProperty("Authorization", "Basic " + Base64.encodeBytes((tsaUsername + ":" + tsaPassword).getBytes(StandardCharsets.UTF_8), 8));
            }
            OutputStream out = tsaConnection.getOutputStream();
            out.write(requestBytes);
            out.close();
            TsaResponse response = new TsaResponse();
            response.tsaResponseStream = tsaConnection.getInputStream();
            response.encoding = tsaConnection.getContentEncoding();
            return response;
        } catch (IOException e) {
            throw new PdfException(PdfException.FailedToGetTsaResponseFrom1).setMessageParams(tsaUrl);
        }
    }

    @Deprecated
    static boolean hasUnsupportedCriticalExtension(X509Certificate cert) {
        if (cert == null) {
            throw new IllegalArgumentException("X509Certificate can't be null.");
        } else if (!cert.hasUnsupportedCriticalExtension()) {
            return false;
        } else {
            for (String oid : cert.getCriticalExtensionOIDs()) {
                if (!OID.X509Extensions.SUPPORTED_CRITICAL_EXTENSIONS.contains(oid)) {
                    return true;
                }
            }
            return false;
        }
    }

    static Calendar getTimeStampDate(TimeStampToken timeStampToken) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(timeStampToken.getTimeStampInfo().getGenTime());
        return calendar;
    }

    static Signature getSignatureHelper(String algorithm, String provider) throws NoSuchProviderException, NoSuchAlgorithmException {
        return provider == null ? Signature.getInstance(algorithm) : Signature.getInstance(algorithm, provider);
    }

    static boolean verifyCertificateSignature(X509Certificate certificate, PublicKey issuerPublicKey, String provider) {
        if (provider == null) {
            try {
                certificate.verify(issuerPublicKey);
            } catch (Exception e) {
                return false;
            }
        } else {
            certificate.verify(issuerPublicKey, provider);
        }
        return true;
    }

    static SigPolicyQualifiers createSigPolicyQualifiers(SigPolicyQualifierInfo... sigPolicyQualifierInfo) {
        return new SigPolicyQualifiers(sigPolicyQualifierInfo);
    }

    static Iterable<X509Certificate> getCertificates(final KeyStore keyStore) throws KeyStoreException {
        final Enumeration<String> keyStoreAliases = keyStore.aliases();
        return new Iterable<X509Certificate>() {
            public Iterator<X509Certificate> iterator() {
                return new Iterator<X509Certificate>() {
                    private X509Certificate nextCert;

                    public boolean hasNext() {
                        if (this.nextCert == null) {
                            tryToGetNextCertificate();
                        }
                        return this.nextCert != null;
                    }

                    public X509Certificate next() {
                        if (hasNext()) {
                            X509Certificate cert = this.nextCert;
                            this.nextCert = null;
                            return cert;
                        }
                        throw new NoSuchElementException();
                    }

                    private void tryToGetNextCertificate() {
                        while (keyStoreAliases.hasMoreElements()) {
                            try {
                                String alias = (String) keyStoreAliases.nextElement();
                                if (!keyStore.isCertificateEntry(alias)) {
                                    if (keyStore.isKeyEntry(alias)) {
                                    }
                                }
                                this.nextCert = (X509Certificate) keyStore.getCertificate(alias);
                                return;
                            } catch (KeyStoreException e) {
                            }
                        }
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("remove");
                    }
                };
            }
        };
    }
}
