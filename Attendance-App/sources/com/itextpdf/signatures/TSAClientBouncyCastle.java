package com.itextpdf.signatures;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.p026io.codec.Base64;
import com.itextpdf.p026io.util.SystemUtil;
import com.itextpdf.signatures.SignUtils;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.TimeStampTokenInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TSAClientBouncyCastle implements ITSAClient {
    public static final String DEFAULTHASHALGORITHM = "SHA-256";
    public static final int DEFAULTTOKENSIZE = 4096;
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) TSAClientBouncyCastle.class);
    protected String digestAlgorithm;
    protected int tokenSizeEstimate;
    protected ITSAInfoBouncyCastle tsaInfo;
    protected String tsaPassword;
    private String tsaReqPolicy;
    protected String tsaURL;
    protected String tsaUsername;

    public TSAClientBouncyCastle(String url) {
        this(url, (String) null, (String) null, 4096, "SHA-256");
    }

    public TSAClientBouncyCastle(String url, String username, String password) {
        this(url, username, password, 4096, "SHA-256");
    }

    public TSAClientBouncyCastle(String url, String username, String password, int tokSzEstimate, String digestAlgorithm2) {
        this.tsaURL = url;
        this.tsaUsername = username;
        this.tsaPassword = password;
        this.tokenSizeEstimate = tokSzEstimate;
        this.digestAlgorithm = digestAlgorithm2;
    }

    public void setTSAInfo(ITSAInfoBouncyCastle tsaInfo2) {
        this.tsaInfo = tsaInfo2;
    }

    public int getTokenSizeEstimate() {
        return this.tokenSizeEstimate;
    }

    public String getTSAReqPolicy() {
        return this.tsaReqPolicy;
    }

    public void setTSAReqPolicy(String tsaReqPolicy2) {
        this.tsaReqPolicy = tsaReqPolicy2;
    }

    public MessageDigest getMessageDigest() throws GeneralSecurityException {
        return SignUtils.getMessageDigest(this.digestAlgorithm);
    }

    public byte[] getTimeStampToken(byte[] imprint) throws IOException, TSPException {
        TimeStampRequestGenerator tsqGenerator = new TimeStampRequestGenerator();
        tsqGenerator.setCertReq(true);
        String str = this.tsaReqPolicy;
        if (str != null && str.length() > 0) {
            tsqGenerator.setReqPolicy(this.tsaReqPolicy);
        }
        TimeStampRequest request = tsqGenerator.generate(new ASN1ObjectIdentifier(DigestAlgorithms.getAllowedDigest(this.digestAlgorithm)), imprint, BigInteger.valueOf(SystemUtil.getTimeBasedSeed()));
        TimeStampResponse response = new TimeStampResponse(getTSAResponse(request.getEncoded()));
        response.validate(request);
        PKIFailureInfo failure = response.getFailInfo();
        int value = failure == null ? 0 : failure.intValue();
        if (value == 0) {
            TimeStampToken tsToken = response.getTimeStampToken();
            if (tsToken != null) {
                TimeStampTokenInfo tsTokenInfo = tsToken.getTimeStampInfo();
                byte[] encoded = tsToken.getEncoded();
                LOGGER.info("Timestamp generated: " + tsTokenInfo.getGenTime());
                ITSAInfoBouncyCastle iTSAInfoBouncyCastle = this.tsaInfo;
                if (iTSAInfoBouncyCastle != null) {
                    iTSAInfoBouncyCastle.inspectTimeStampTokenInfo(tsTokenInfo);
                }
                this.tokenSizeEstimate = encoded.length + 32;
                return encoded;
            }
            throw new PdfException(PdfException.Tsa1FailedToReturnTimeStampToken2).setMessageParams(this.tsaURL, response.getStatusString());
        }
        throw new PdfException(PdfException.InvalidTsa1ResponseCode2).setMessageParams(this.tsaURL, String.valueOf(value));
    }

    /* access modifiers changed from: protected */
    public byte[] getTSAResponse(byte[] requestBytes) throws IOException {
        SignUtils.TsaResponse response = SignUtils.getTsaResponseForUserRequest(this.tsaURL, requestBytes, this.tsaUsername, this.tsaPassword);
        InputStream inp = response.tsaResponseStream;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int read = inp.read(buffer, 0, buffer.length);
            int bytesRead = read;
            if (read < 0) {
                break;
            }
            baos.write(buffer, 0, bytesRead);
        }
        byte[] respBytes = baos.toByteArray();
        if (response.encoding == null || !response.encoding.toLowerCase().equals(ResourceResolver.BASE64IDENTIFIER.toLowerCase())) {
            return respBytes;
        }
        return Base64.decode(new String(respBytes, "US-ASCII"));
    }
}
