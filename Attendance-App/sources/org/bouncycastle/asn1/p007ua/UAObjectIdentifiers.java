package org.bouncycastle.asn1.p007ua;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

/* renamed from: org.bouncycastle.asn1.ua.UAObjectIdentifiers */
public interface UAObjectIdentifiers {
    public static final ASN1ObjectIdentifier UaOid;
    public static final ASN1ObjectIdentifier dstu4145be;
    public static final ASN1ObjectIdentifier dstu4145le;
    public static final ASN1ObjectIdentifier dstu7564digest_256;
    public static final ASN1ObjectIdentifier dstu7564digest_384;
    public static final ASN1ObjectIdentifier dstu7564digest_512;
    public static final ASN1ObjectIdentifier dstu7564mac_256;
    public static final ASN1ObjectIdentifier dstu7564mac_384;
    public static final ASN1ObjectIdentifier dstu7564mac_512;
    public static final ASN1ObjectIdentifier dstu7624cbc_128;
    public static final ASN1ObjectIdentifier dstu7624cbc_256;
    public static final ASN1ObjectIdentifier dstu7624cbc_512;
    public static final ASN1ObjectIdentifier dstu7624ccm_128;
    public static final ASN1ObjectIdentifier dstu7624ccm_256;
    public static final ASN1ObjectIdentifier dstu7624ccm_512;
    public static final ASN1ObjectIdentifier dstu7624cfb_128;
    public static final ASN1ObjectIdentifier dstu7624cfb_256;
    public static final ASN1ObjectIdentifier dstu7624cfb_512;
    public static final ASN1ObjectIdentifier dstu7624cmac_128;
    public static final ASN1ObjectIdentifier dstu7624cmac_256;
    public static final ASN1ObjectIdentifier dstu7624cmac_512;
    public static final ASN1ObjectIdentifier dstu7624ctr_128;
    public static final ASN1ObjectIdentifier dstu7624ctr_256;
    public static final ASN1ObjectIdentifier dstu7624ctr_512;
    public static final ASN1ObjectIdentifier dstu7624ecb_128;
    public static final ASN1ObjectIdentifier dstu7624ecb_256;
    public static final ASN1ObjectIdentifier dstu7624ecb_512;
    public static final ASN1ObjectIdentifier dstu7624gmac_128;
    public static final ASN1ObjectIdentifier dstu7624gmac_256;
    public static final ASN1ObjectIdentifier dstu7624gmac_512;
    public static final ASN1ObjectIdentifier dstu7624kw_128;
    public static final ASN1ObjectIdentifier dstu7624kw_256;
    public static final ASN1ObjectIdentifier dstu7624kw_512;
    public static final ASN1ObjectIdentifier dstu7624ofb_128;
    public static final ASN1ObjectIdentifier dstu7624ofb_256;
    public static final ASN1ObjectIdentifier dstu7624ofb_512;
    public static final ASN1ObjectIdentifier dstu7624xts_128;
    public static final ASN1ObjectIdentifier dstu7624xts_256;
    public static final ASN1ObjectIdentifier dstu7624xts_512;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.2.804.2.1.1.1");
        UaOid = aSN1ObjectIdentifier;
        dstu4145le = aSN1ObjectIdentifier.branch("1.3.1.1");
        dstu4145be = aSN1ObjectIdentifier.branch("1.3.1.1.1.1");
        dstu7564digest_256 = aSN1ObjectIdentifier.branch("1.2.2.1");
        dstu7564digest_384 = aSN1ObjectIdentifier.branch("1.2.2.2");
        dstu7564digest_512 = aSN1ObjectIdentifier.branch("1.2.2.3");
        dstu7564mac_256 = aSN1ObjectIdentifier.branch("1.2.2.4");
        dstu7564mac_384 = aSN1ObjectIdentifier.branch("1.2.2.5");
        dstu7564mac_512 = aSN1ObjectIdentifier.branch("1.2.2.6");
        dstu7624ecb_128 = aSN1ObjectIdentifier.branch("1.1.3.1.1");
        dstu7624ecb_256 = aSN1ObjectIdentifier.branch("1.1.3.1.2");
        dstu7624ecb_512 = aSN1ObjectIdentifier.branch("1.1.3.1.3");
        dstu7624ctr_128 = aSN1ObjectIdentifier.branch("1.1.3.2.1");
        dstu7624ctr_256 = aSN1ObjectIdentifier.branch("1.1.3.2.2");
        dstu7624ctr_512 = aSN1ObjectIdentifier.branch("1.1.3.2.3");
        dstu7624cfb_128 = aSN1ObjectIdentifier.branch("1.1.3.3.1");
        dstu7624cfb_256 = aSN1ObjectIdentifier.branch("1.1.3.3.2");
        dstu7624cfb_512 = aSN1ObjectIdentifier.branch("1.1.3.3.3");
        dstu7624cmac_128 = aSN1ObjectIdentifier.branch("1.1.3.4.1");
        dstu7624cmac_256 = aSN1ObjectIdentifier.branch("1.1.3.4.2");
        dstu7624cmac_512 = aSN1ObjectIdentifier.branch("1.1.3.4.3");
        dstu7624cbc_128 = aSN1ObjectIdentifier.branch("1.1.3.5.1");
        dstu7624cbc_256 = aSN1ObjectIdentifier.branch("1.1.3.5.2");
        dstu7624cbc_512 = aSN1ObjectIdentifier.branch("1.1.3.5.3");
        dstu7624ofb_128 = aSN1ObjectIdentifier.branch("1.1.3.6.1");
        dstu7624ofb_256 = aSN1ObjectIdentifier.branch("1.1.3.6.2");
        dstu7624ofb_512 = aSN1ObjectIdentifier.branch("1.1.3.6.3");
        dstu7624gmac_128 = aSN1ObjectIdentifier.branch("1.1.3.7.1");
        dstu7624gmac_256 = aSN1ObjectIdentifier.branch("1.1.3.7.2");
        dstu7624gmac_512 = aSN1ObjectIdentifier.branch("1.1.3.7.3");
        dstu7624ccm_128 = aSN1ObjectIdentifier.branch("1.1.3.8.1");
        dstu7624ccm_256 = aSN1ObjectIdentifier.branch("1.1.3.8.2");
        dstu7624ccm_512 = aSN1ObjectIdentifier.branch("1.1.3.8.3");
        dstu7624xts_128 = aSN1ObjectIdentifier.branch("1.1.3.9.1");
        dstu7624xts_256 = aSN1ObjectIdentifier.branch("1.1.3.9.2");
        dstu7624xts_512 = aSN1ObjectIdentifier.branch("1.1.3.9.3");
        dstu7624kw_128 = aSN1ObjectIdentifier.branch("1.1.3.10.1");
        dstu7624kw_256 = aSN1ObjectIdentifier.branch("1.1.3.10.2");
        dstu7624kw_512 = aSN1ObjectIdentifier.branch("1.1.3.10.3");
    }
}
