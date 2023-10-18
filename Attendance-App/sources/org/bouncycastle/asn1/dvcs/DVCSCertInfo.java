package org.bouncycastle.asn1.dvcs;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cmp.PKIStatusInfo;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.PolicyInformation;

public class DVCSCertInfo extends ASN1Object {
    private static final int DEFAULT_VERSION = 1;
    private static final int TAG_CERTS = 3;
    private static final int TAG_DV_STATUS = 0;
    private static final int TAG_POLICY = 1;
    private static final int TAG_REQ_SIGNATURE = 2;
    private ASN1Sequence certs;
    private DVCSRequestInformation dvReqInfo;
    private PKIStatusInfo dvStatus;
    private Extensions extensions;
    private DigestInfo messageImprint;
    private PolicyInformation policy;
    private ASN1Set reqSignature;
    private DVCSTime responseTime;
    private ASN1Integer serialNumber;
    private int version = 1;

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0050  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private DVCSCertInfo(org.bouncycastle.asn1.ASN1Sequence r5) {
        /*
            r4 = this;
            r4.<init>()
            r0 = 1
            r4.version = r0
            r1 = 0
            org.bouncycastle.asn1.ASN1Encodable r2 = r5.getObjectAt(r1)
            org.bouncycastle.asn1.ASN1Integer r3 = org.bouncycastle.asn1.ASN1Integer.getInstance(r2)     // Catch:{ IllegalArgumentException -> 0x001e }
            int r3 = r3.intValueExact()     // Catch:{ IllegalArgumentException -> 0x001e }
            r4.version = r3     // Catch:{ IllegalArgumentException -> 0x001e }
            r3 = 2
            org.bouncycastle.asn1.ASN1Encodable r2 = r5.getObjectAt(r0)     // Catch:{ IllegalArgumentException -> 0x001b }
            goto L_0x0020
        L_0x001b:
            r0 = move-exception
            r0 = 2
            goto L_0x001f
        L_0x001e:
            r3 = move-exception
        L_0x001f:
            r3 = r0
        L_0x0020:
            org.bouncycastle.asn1.dvcs.DVCSRequestInformation r0 = org.bouncycastle.asn1.dvcs.DVCSRequestInformation.getInstance(r2)
            r4.dvReqInfo = r0
            int r0 = r3 + 1
            org.bouncycastle.asn1.ASN1Encodable r2 = r5.getObjectAt(r3)
            org.bouncycastle.asn1.x509.DigestInfo r2 = org.bouncycastle.asn1.x509.DigestInfo.getInstance(r2)
            r4.messageImprint = r2
            int r2 = r0 + 1
            org.bouncycastle.asn1.ASN1Encodable r0 = r5.getObjectAt(r0)
            org.bouncycastle.asn1.ASN1Integer r0 = org.bouncycastle.asn1.ASN1Integer.getInstance(r0)
            r4.serialNumber = r0
            int r0 = r2 + 1
            org.bouncycastle.asn1.ASN1Encodable r2 = r5.getObjectAt(r2)
            org.bouncycastle.asn1.dvcs.DVCSTime r2 = org.bouncycastle.asn1.dvcs.DVCSTime.getInstance(r2)
            r4.responseTime = r2
        L_0x004a:
            int r2 = r5.size()
            if (r0 >= r2) goto L_0x00a8
            int r2 = r0 + 1
            org.bouncycastle.asn1.ASN1Encodable r0 = r5.getObjectAt(r0)
            boolean r3 = r0 instanceof org.bouncycastle.asn1.ASN1TaggedObject
            if (r3 == 0) goto L_0x009e
            org.bouncycastle.asn1.ASN1TaggedObject r0 = org.bouncycastle.asn1.ASN1TaggedObject.getInstance(r0)
            int r3 = r0.getTagNo()
            switch(r3) {
                case 0: goto L_0x0097;
                case 1: goto L_0x008c;
                case 2: goto L_0x0085;
                case 3: goto L_0x007e;
                default: goto L_0x0065;
            }
        L_0x0065:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Unknown tag encountered: "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x007e:
            org.bouncycastle.asn1.ASN1Sequence r0 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r0, r1)
            r4.certs = r0
            goto L_0x00a6
        L_0x0085:
            org.bouncycastle.asn1.ASN1Set r0 = org.bouncycastle.asn1.ASN1Set.getInstance(r0, r1)
            r4.reqSignature = r0
            goto L_0x00a6
        L_0x008c:
            org.bouncycastle.asn1.ASN1Sequence r0 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r0, r1)
            org.bouncycastle.asn1.x509.PolicyInformation r0 = org.bouncycastle.asn1.x509.PolicyInformation.getInstance(r0)
            r4.policy = r0
            goto L_0x00a6
        L_0x0097:
            org.bouncycastle.asn1.cmp.PKIStatusInfo r0 = org.bouncycastle.asn1.cmp.PKIStatusInfo.getInstance(r0, r1)
            r4.dvStatus = r0
            goto L_0x00a6
        L_0x009e:
            org.bouncycastle.asn1.x509.Extensions r0 = org.bouncycastle.asn1.x509.Extensions.getInstance(r0)     // Catch:{ IllegalArgumentException -> 0x00a5 }
            r4.extensions = r0     // Catch:{ IllegalArgumentException -> 0x00a5 }
            goto L_0x00a6
        L_0x00a5:
            r0 = move-exception
        L_0x00a6:
            r0 = r2
            goto L_0x004a
        L_0x00a8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.dvcs.DVCSCertInfo.<init>(org.bouncycastle.asn1.ASN1Sequence):void");
    }

    public DVCSCertInfo(DVCSRequestInformation dVCSRequestInformation, DigestInfo digestInfo, ASN1Integer aSN1Integer, DVCSTime dVCSTime) {
        this.dvReqInfo = dVCSRequestInformation;
        this.messageImprint = digestInfo;
        this.serialNumber = aSN1Integer;
        this.responseTime = dVCSTime;
    }

    public static DVCSCertInfo getInstance(Object obj) {
        if (obj instanceof DVCSCertInfo) {
            return (DVCSCertInfo) obj;
        }
        if (obj != null) {
            return new DVCSCertInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static DVCSCertInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    private void setDvReqInfo(DVCSRequestInformation dVCSRequestInformation) {
        this.dvReqInfo = dVCSRequestInformation;
    }

    private void setMessageImprint(DigestInfo digestInfo) {
        this.messageImprint = digestInfo;
    }

    private void setVersion(int i) {
        this.version = i;
    }

    public TargetEtcChain[] getCerts() {
        ASN1Sequence aSN1Sequence = this.certs;
        if (aSN1Sequence != null) {
            return TargetEtcChain.arrayFromSequence(aSN1Sequence);
        }
        return null;
    }

    public DVCSRequestInformation getDvReqInfo() {
        return this.dvReqInfo;
    }

    public PKIStatusInfo getDvStatus() {
        return this.dvStatus;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public DigestInfo getMessageImprint() {
        return this.messageImprint;
    }

    public PolicyInformation getPolicy() {
        return this.policy;
    }

    public ASN1Set getReqSignature() {
        return this.reqSignature;
    }

    public DVCSTime getResponseTime() {
        return this.responseTime;
    }

    public ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public int getVersion() {
        return this.version;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(10);
        int i = this.version;
        if (i != 1) {
            aSN1EncodableVector.add(new ASN1Integer((long) i));
        }
        aSN1EncodableVector.add(this.dvReqInfo);
        aSN1EncodableVector.add(this.messageImprint);
        aSN1EncodableVector.add(this.serialNumber);
        aSN1EncodableVector.add(this.responseTime);
        PKIStatusInfo pKIStatusInfo = this.dvStatus;
        if (pKIStatusInfo != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, pKIStatusInfo));
        }
        PolicyInformation policyInformation = this.policy;
        if (policyInformation != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, policyInformation));
        }
        ASN1Set aSN1Set = this.reqSignature;
        if (aSN1Set != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, aSN1Set));
        }
        ASN1Sequence aSN1Sequence = this.certs;
        if (aSN1Sequence != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 3, aSN1Sequence));
        }
        Extensions extensions2 = this.extensions;
        if (extensions2 != null) {
            aSN1EncodableVector.add(extensions2);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DVCSCertInfo {\n");
        if (this.version != 1) {
            stringBuffer.append("version: " + this.version + "\n");
        }
        stringBuffer.append("dvReqInfo: " + this.dvReqInfo + "\n");
        stringBuffer.append("messageImprint: " + this.messageImprint + "\n");
        stringBuffer.append("serialNumber: " + this.serialNumber + "\n");
        stringBuffer.append("responseTime: " + this.responseTime + "\n");
        if (this.dvStatus != null) {
            stringBuffer.append("dvStatus: " + this.dvStatus + "\n");
        }
        if (this.policy != null) {
            stringBuffer.append("policy: " + this.policy + "\n");
        }
        if (this.reqSignature != null) {
            stringBuffer.append("reqSignature: " + this.reqSignature + "\n");
        }
        if (this.certs != null) {
            stringBuffer.append("certs: " + this.certs + "\n");
        }
        if (this.extensions != null) {
            stringBuffer.append("extensions: " + this.extensions + "\n");
        }
        stringBuffer.append("}\n");
        return stringBuffer.toString();
    }
}
