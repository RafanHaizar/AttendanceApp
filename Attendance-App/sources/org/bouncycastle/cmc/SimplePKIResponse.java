package org.bouncycastle.cmc;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.util.Encodable;
import org.bouncycastle.util.Store;

public class SimplePKIResponse implements Encodable {
    private final CMSSignedData certificateResponse;

    public SimplePKIResponse(ContentInfo contentInfo) throws CMCException {
        try {
            CMSSignedData cMSSignedData = new CMSSignedData(contentInfo);
            this.certificateResponse = cMSSignedData;
            if (cMSSignedData.getSignerInfos().size() != 0) {
                throw new CMCException("malformed response: SignerInfo structures found");
            } else if (cMSSignedData.getSignedContent() != null) {
                throw new CMCException("malformed response: Signed Content found");
            }
        } catch (CMSException e) {
            throw new CMCException("malformed response: " + e.getMessage(), e);
        }
    }

    public SimplePKIResponse(byte[] bArr) throws CMCException {
        this(parseBytes(bArr));
    }

    private static ContentInfo parseBytes(byte[] bArr) throws CMCException {
        try {
            return ContentInfo.getInstance(ASN1Primitive.fromByteArray(bArr));
        } catch (Exception e) {
            throw new CMCException("malformed data: " + e.getMessage(), e);
        }
    }

    public Store<X509CRLHolder> getCRLs() {
        return this.certificateResponse.getCRLs();
    }

    public Store<X509CertificateHolder> getCertificates() {
        return this.certificateResponse.getCertificates();
    }

    public byte[] getEncoded() throws IOException {
        return this.certificateResponse.getEncoded();
    }
}
