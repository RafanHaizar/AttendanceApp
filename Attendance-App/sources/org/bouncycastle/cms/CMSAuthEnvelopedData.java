package org.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.AuthEnvelopedData;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.Arrays;

public class CMSAuthEnvelopedData {
    /* access modifiers changed from: private */
    public ASN1Set authAttrs;
    private AlgorithmIdentifier authEncAlg;
    ContentInfo contentInfo;
    private byte[] mac;
    private OriginatorInformation originatorInfo;
    RecipientInformationStore recipientInfoStore;
    private ASN1Set unauthAttrs;

    public CMSAuthEnvelopedData(InputStream inputStream) throws CMSException {
        this(CMSUtils.readContentInfo(inputStream));
    }

    public CMSAuthEnvelopedData(ContentInfo contentInfo2) throws CMSException {
        this.contentInfo = contentInfo2;
        AuthEnvelopedData instance = AuthEnvelopedData.getInstance(contentInfo2.getContent());
        if (instance.getOriginatorInfo() != null) {
            this.originatorInfo = new OriginatorInformation(instance.getOriginatorInfo());
        }
        ASN1Set recipientInfos = instance.getRecipientInfos();
        final EncryptedContentInfo authEncryptedContentInfo = instance.getAuthEncryptedContentInfo();
        this.authEncAlg = authEncryptedContentInfo.getContentEncryptionAlgorithm();
        C01891 r2 = new CMSSecureReadable() {
            public InputStream getInputStream() throws IOException, CMSException {
                return new ByteArrayInputStream(authEncryptedContentInfo.getEncryptedContent().getOctets());
            }
        };
        this.authAttrs = instance.getAuthAttrs();
        this.mac = instance.getMac().getOctets();
        this.unauthAttrs = instance.getUnauthAttrs();
        this.recipientInfoStore = this.authAttrs != null ? CMSEnvelopedHelper.buildRecipientInformationStore(recipientInfos, this.authEncAlg, r2, new AuthAttributesProvider() {
            public ASN1Set getAuthAttributes() {
                return CMSAuthEnvelopedData.this.authAttrs;
            }

            public boolean isAead() {
                return true;
            }
        }) : CMSEnvelopedHelper.buildRecipientInformationStore(recipientInfos, this.authEncAlg, r2);
    }

    public CMSAuthEnvelopedData(byte[] bArr) throws CMSException {
        this(CMSUtils.readContentInfo(bArr));
    }

    public AttributeTable getAuthAttrs() {
        if (this.authAttrs == null) {
            return null;
        }
        return new AttributeTable(this.authAttrs);
    }

    public byte[] getMac() {
        return Arrays.clone(this.mac);
    }

    public OriginatorInformation getOriginatorInfo() {
        return this.originatorInfo;
    }

    public RecipientInformationStore getRecipientInfos() {
        return this.recipientInfoStore;
    }

    public AttributeTable getUnauthAttrs() {
        if (this.unauthAttrs == null) {
            return null;
        }
        return new AttributeTable(this.unauthAttrs);
    }
}
