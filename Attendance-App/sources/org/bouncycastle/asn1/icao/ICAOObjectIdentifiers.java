package org.bouncycastle.asn1.icao;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface ICAOObjectIdentifiers {
    public static final ASN1ObjectIdentifier id_icao;
    public static final ASN1ObjectIdentifier id_icao_aaProtocolObject;
    public static final ASN1ObjectIdentifier id_icao_cscaMasterList;
    public static final ASN1ObjectIdentifier id_icao_cscaMasterListSigningKey;
    public static final ASN1ObjectIdentifier id_icao_documentTypeList;
    public static final ASN1ObjectIdentifier id_icao_extensions;
    public static final ASN1ObjectIdentifier id_icao_extensions_namechangekeyrollover;
    public static final ASN1ObjectIdentifier id_icao_ldsSecurityObject;
    public static final ASN1ObjectIdentifier id_icao_mrtd;
    public static final ASN1ObjectIdentifier id_icao_mrtd_security;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.23.136");
        id_icao = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier branch = aSN1ObjectIdentifier.branch("1");
        id_icao_mrtd = branch;
        ASN1ObjectIdentifier branch2 = branch.branch("1");
        id_icao_mrtd_security = branch2;
        id_icao_ldsSecurityObject = branch2.branch("1");
        id_icao_cscaMasterList = branch2.branch("2");
        id_icao_cscaMasterListSigningKey = branch2.branch("3");
        id_icao_documentTypeList = branch2.branch("4");
        id_icao_aaProtocolObject = branch2.branch("5");
        ASN1ObjectIdentifier branch3 = branch2.branch("6");
        id_icao_extensions = branch3;
        id_icao_extensions_namechangekeyrollover = branch3.branch("1");
    }
}
