package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface X509AttributeIdentifiers {
    public static final ASN1ObjectIdentifier RoleSyntax = new ASN1ObjectIdentifier("2.5.4.72");
    public static final ASN1ObjectIdentifier id_aca;
    public static final ASN1ObjectIdentifier id_aca_accessIdentity;
    public static final ASN1ObjectIdentifier id_aca_authenticationInfo;
    public static final ASN1ObjectIdentifier id_aca_chargingIdentity;
    public static final ASN1ObjectIdentifier id_aca_encAttrs;
    public static final ASN1ObjectIdentifier id_aca_group;
    public static final ASN1ObjectIdentifier id_at_clearance = new ASN1ObjectIdentifier("2.5.1.5.55");
    public static final ASN1ObjectIdentifier id_at_role = new ASN1ObjectIdentifier("2.5.4.72");
    public static final ASN1ObjectIdentifier id_ce_targetInformation = X509ObjectIdentifiers.id_ce.branch("55");
    public static final ASN1ObjectIdentifier id_pe_aaControls = X509ObjectIdentifiers.id_pe.branch("6");
    public static final ASN1ObjectIdentifier id_pe_ac_auditIdentity = X509ObjectIdentifiers.id_pe.branch("4");
    public static final ASN1ObjectIdentifier id_pe_ac_proxying = X509ObjectIdentifiers.id_pe.branch("10");

    static {
        ASN1ObjectIdentifier branch = X509ObjectIdentifiers.id_pkix.branch("10");
        id_aca = branch;
        id_aca_authenticationInfo = branch.branch("1");
        id_aca_accessIdentity = branch.branch("2");
        id_aca_chargingIdentity = branch.branch("3");
        id_aca_group = branch.branch("4");
        id_aca_encAttrs = branch.branch("6");
    }
}
