package org.bouncycastle.asn1.ocsp;

import com.itextpdf.signatures.OID;
import com.itextpdf.signatures.SecurityIDs;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface OCSPObjectIdentifiers {
    public static final ASN1ObjectIdentifier id_pkix_ocsp;
    public static final ASN1ObjectIdentifier id_pkix_ocsp_archive_cutoff = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.6");
    public static final ASN1ObjectIdentifier id_pkix_ocsp_basic = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.1");
    public static final ASN1ObjectIdentifier id_pkix_ocsp_crl = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.3");
    public static final ASN1ObjectIdentifier id_pkix_ocsp_extended_revoke;
    public static final ASN1ObjectIdentifier id_pkix_ocsp_nocheck = new ASN1ObjectIdentifier(OID.X509Extensions.ID_PKIX_OCSP_NOCHECK);
    public static final ASN1ObjectIdentifier id_pkix_ocsp_nonce = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.2");
    public static final ASN1ObjectIdentifier id_pkix_ocsp_pref_sig_algs;
    public static final ASN1ObjectIdentifier id_pkix_ocsp_response = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.4");
    public static final ASN1ObjectIdentifier id_pkix_ocsp_service_locator = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.7");

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier(SecurityIDs.ID_OCSP);
        id_pkix_ocsp = aSN1ObjectIdentifier;
        id_pkix_ocsp_pref_sig_algs = aSN1ObjectIdentifier.branch("8");
        id_pkix_ocsp_extended_revoke = aSN1ObjectIdentifier.branch("9");
    }
}
