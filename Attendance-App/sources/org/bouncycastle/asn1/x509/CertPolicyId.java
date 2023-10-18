package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;

public class CertPolicyId extends ASN1Object {

    /* renamed from: id */
    private ASN1ObjectIdentifier f81id;

    private CertPolicyId(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.f81id = aSN1ObjectIdentifier;
    }

    public static CertPolicyId getInstance(Object obj) {
        if (obj instanceof CertPolicyId) {
            return (CertPolicyId) obj;
        }
        if (obj != null) {
            return new CertPolicyId(ASN1ObjectIdentifier.getInstance(obj));
        }
        return null;
    }

    public String getId() {
        return this.f81id.getId();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.f81id;
    }
}
