package org.bouncycastle.cms;

import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public class CMSSignedDataGenerator extends CMSSignedGenerator {
    private List signerInfs = new ArrayList();

    public CMSSignedData generate(CMSTypedData cMSTypedData) throws CMSException {
        return generate(cMSTypedData, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00d5  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00de  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.cms.CMSSignedData generate(org.bouncycastle.cms.CMSTypedData r12, boolean r13) throws org.bouncycastle.cms.CMSException {
        /*
            r11 = this;
            java.util.List r0 = r11.signerInfs
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0107
            org.bouncycastle.asn1.ASN1EncodableVector r0 = new org.bouncycastle.asn1.ASN1EncodableVector
            r0.<init>()
            org.bouncycastle.asn1.ASN1EncodableVector r1 = new org.bouncycastle.asn1.ASN1EncodableVector
            r1.<init>()
            java.util.Map r2 = r11.digests
            r2.clear()
            java.util.List r2 = r11._signers
            java.util.Iterator r2 = r2.iterator()
        L_0x001d:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x003e
            java.lang.Object r3 = r2.next()
            org.bouncycastle.cms.SignerInformation r3 = (org.bouncycastle.cms.SignerInformation) r3
            org.bouncycastle.cms.CMSSignedHelper r4 = org.bouncycastle.cms.CMSSignedHelper.INSTANCE
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r5 = r3.getDigestAlgorithmID()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r4 = r4.fixAlgID(r5)
            r0.add(r4)
            org.bouncycastle.asn1.cms.SignerInfo r3 = r3.toASN1Structure()
            r1.add(r3)
            goto L_0x001d
        L_0x003e:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r12.getContentType()
            java.lang.Object r3 = r12.getContent()
            r4 = 0
            if (r3 == 0) goto L_0x008c
            if (r13 == 0) goto L_0x0051
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream
            r3.<init>()
            goto L_0x0052
        L_0x0051:
            r3 = r4
        L_0x0052:
            java.util.List r5 = r11.signerGens
            java.io.OutputStream r5 = org.bouncycastle.cms.CMSUtils.attachSignersToOutputStream(r5, r3)
            java.io.OutputStream r5 = org.bouncycastle.cms.CMSUtils.getSafeOutputStream(r5)
            r12.write(r5)     // Catch:{ IOException -> 0x006e }
            r5.close()     // Catch:{ IOException -> 0x006e }
            if (r13 == 0) goto L_0x008c
            org.bouncycastle.asn1.BEROctetString r13 = new org.bouncycastle.asn1.BEROctetString
            byte[] r3 = r3.toByteArray()
            r13.<init>((byte[]) r3)
            goto L_0x008d
        L_0x006e:
            r12 = move-exception
            org.bouncycastle.cms.CMSException r13 = new org.bouncycastle.cms.CMSException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "data processing exception: "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = r12.getMessage()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r13.<init>(r0, r12)
            throw r13
        L_0x008c:
            r13 = r4
        L_0x008d:
            java.util.List r3 = r11.signerGens
            java.util.Iterator r3 = r3.iterator()
        L_0x0093:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x00c5
            java.lang.Object r5 = r3.next()
            org.bouncycastle.cms.SignerInfoGenerator r5 = (org.bouncycastle.cms.SignerInfoGenerator) r5
            org.bouncycastle.asn1.cms.SignerInfo r6 = r5.generate(r2)
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r7 = r6.getDigestAlgorithm()
            r0.add(r7)
            r1.add(r6)
            byte[] r5 = r5.getCalculatedDigest()
            if (r5 == 0) goto L_0x0093
            java.util.Map r7 = r11.digests
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r6 = r6.getDigestAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r6 = r6.getAlgorithm()
            java.lang.String r6 = r6.getId()
            r7.put(r6, r5)
            goto L_0x0093
        L_0x00c5:
            java.util.List r3 = r11.certs
            int r3 = r3.size()
            if (r3 == 0) goto L_0x00d5
            java.util.List r3 = r11.certs
            org.bouncycastle.asn1.ASN1Set r3 = org.bouncycastle.cms.CMSUtils.createBerSetFromList(r3)
            r8 = r3
            goto L_0x00d6
        L_0x00d5:
            r8 = r4
        L_0x00d6:
            java.util.List r3 = r11.crls
            int r3 = r3.size()
            if (r3 == 0) goto L_0x00e4
            java.util.List r3 = r11.crls
            org.bouncycastle.asn1.ASN1Set r4 = org.bouncycastle.cms.CMSUtils.createBerSetFromList(r3)
        L_0x00e4:
            r9 = r4
            org.bouncycastle.asn1.cms.ContentInfo r7 = new org.bouncycastle.asn1.cms.ContentInfo
            r7.<init>(r2, r13)
            org.bouncycastle.asn1.cms.SignedData r13 = new org.bouncycastle.asn1.cms.SignedData
            org.bouncycastle.asn1.DERSet r6 = new org.bouncycastle.asn1.DERSet
            r6.<init>((org.bouncycastle.asn1.ASN1EncodableVector) r0)
            org.bouncycastle.asn1.DERSet r10 = new org.bouncycastle.asn1.DERSet
            r10.<init>((org.bouncycastle.asn1.ASN1EncodableVector) r1)
            r5 = r13
            r5.<init>(r6, r7, r8, r9, r10)
            org.bouncycastle.asn1.cms.ContentInfo r0 = new org.bouncycastle.asn1.cms.ContentInfo
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.cms.CMSObjectIdentifiers.signedData
            r0.<init>(r1, r13)
            org.bouncycastle.cms.CMSSignedData r13 = new org.bouncycastle.cms.CMSSignedData
            r13.<init>((org.bouncycastle.cms.CMSProcessable) r12, (org.bouncycastle.asn1.cms.ContentInfo) r0)
            return r13
        L_0x0107:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "this method can only be used with SignerInfoGenerator"
            r12.<init>(r13)
            goto L_0x0110
        L_0x010f:
            throw r12
        L_0x0110:
            goto L_0x010f
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.cms.CMSSignedDataGenerator.generate(org.bouncycastle.cms.CMSTypedData, boolean):org.bouncycastle.cms.CMSSignedData");
    }

    public SignerInformationStore generateCounterSigners(SignerInformation signerInformation) throws CMSException {
        return generate(new CMSProcessableByteArray((ASN1ObjectIdentifier) null, signerInformation.getSignature()), false).getSignerInfos();
    }
}
