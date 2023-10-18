package com.itextpdf.signatures;

import com.itextpdf.p026io.codec.Base64;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.esf.OtherHashAlgAndValue;
import org.bouncycastle.asn1.esf.SigPolicyQualifierInfo;
import org.bouncycastle.asn1.esf.SignaturePolicyId;
import org.bouncycastle.asn1.esf.SignaturePolicyIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class SignaturePolicyInfo {
    private String policyDigestAlgorithm;
    private byte[] policyHash;
    private String policyIdentifier;
    private String policyUri;

    public SignaturePolicyInfo(String policyIdentifier2, byte[] policyHash2, String policyDigestAlgorithm2, String policyUri2) {
        if (policyIdentifier2 == null || policyIdentifier2.length() == 0) {
            throw new IllegalArgumentException("Policy identifier cannot be null");
        } else if (policyHash2 == null) {
            throw new IllegalArgumentException("Policy hash cannot be null");
        } else if (policyDigestAlgorithm2 == null || policyDigestAlgorithm2.length() == 0) {
            throw new IllegalArgumentException("Policy digest algorithm cannot be null");
        } else {
            this.policyIdentifier = policyIdentifier2;
            this.policyHash = policyHash2;
            this.policyDigestAlgorithm = policyDigestAlgorithm2;
            this.policyUri = policyUri2;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SignaturePolicyInfo(String policyIdentifier2, String policyHashBase64, String policyDigestAlgorithm2, String policyUri2) {
        this(policyIdentifier2, policyHashBase64 != null ? Base64.decode(policyHashBase64) : null, policyDigestAlgorithm2, policyUri2);
    }

    public String getPolicyIdentifier() {
        return this.policyIdentifier;
    }

    public byte[] getPolicyHash() {
        return this.policyHash;
    }

    public String getPolicyDigestAlgorithm() {
        return this.policyDigestAlgorithm;
    }

    public String getPolicyUri() {
        return this.policyUri;
    }

    /* access modifiers changed from: package-private */
    public SignaturePolicyIdentifier toSignaturePolicyIdentifier() {
        String algId = DigestAlgorithms.getAllowedDigest(this.policyDigestAlgorithm);
        if (algId == null || algId.length() == 0) {
            throw new IllegalArgumentException("Invalid policy hash algorithm");
        }
        SigPolicyQualifierInfo spqi = null;
        String str = this.policyUri;
        if (str != null && str.length() > 0) {
            spqi = new SigPolicyQualifierInfo(PKCSObjectIdentifiers.id_spq_ets_uri, new DERIA5String(this.policyUri));
        }
        return new SignaturePolicyIdentifier(new SignaturePolicyId(DERObjectIdentifier.getInstance(new DERObjectIdentifier(this.policyIdentifier.replace("urn:oid:", ""))), new OtherHashAlgAndValue(new AlgorithmIdentifier(new ASN1ObjectIdentifier(algId)), new DEROctetString(this.policyHash)), SignUtils.createSigPolicyQualifiers(spqi)));
    }
}
