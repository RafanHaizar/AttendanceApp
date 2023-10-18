package org.bouncycastle.asn1.x509.qualified;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface ETSIQCObjectIdentifiers {
    public static final ASN1ObjectIdentifier id_etsi_qcs_LimiteValue = new ASN1ObjectIdentifier("0.4.0.1862.1.2");
    public static final ASN1ObjectIdentifier id_etsi_qcs_QcCompliance = new ASN1ObjectIdentifier("0.4.0.1862.1.1");
    public static final ASN1ObjectIdentifier id_etsi_qcs_QcPds = new ASN1ObjectIdentifier("0.4.0.1862.1.5");
    public static final ASN1ObjectIdentifier id_etsi_qcs_QcSSCD = new ASN1ObjectIdentifier("0.4.0.1862.1.4");
    public static final ASN1ObjectIdentifier id_etsi_qcs_QcType;
    public static final ASN1ObjectIdentifier id_etsi_qcs_RetentionPeriod = new ASN1ObjectIdentifier("0.4.0.1862.1.3");
    public static final ASN1ObjectIdentifier id_etsi_qct_eseal;
    public static final ASN1ObjectIdentifier id_etsi_qct_esign;
    public static final ASN1ObjectIdentifier id_etsi_qct_web;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("0.4.0.1862.1.6");
        id_etsi_qcs_QcType = aSN1ObjectIdentifier;
        id_etsi_qct_esign = aSN1ObjectIdentifier.branch("1");
        id_etsi_qct_eseal = aSN1ObjectIdentifier.branch("2");
        id_etsi_qct_web = aSN1ObjectIdentifier.branch("3");
    }
}
