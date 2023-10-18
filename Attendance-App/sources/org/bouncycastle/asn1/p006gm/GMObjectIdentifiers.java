package org.bouncycastle.asn1.p006gm;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

/* renamed from: org.bouncycastle.asn1.gm.GMObjectIdentifiers */
public interface GMObjectIdentifiers {
    public static final ASN1ObjectIdentifier hmac_sm3;
    public static final ASN1ObjectIdentifier id_sm9PublicKey;
    public static final ASN1ObjectIdentifier sm1_cbc;
    public static final ASN1ObjectIdentifier sm1_cfb1;
    public static final ASN1ObjectIdentifier sm1_cfb128;
    public static final ASN1ObjectIdentifier sm1_cfb8;
    public static final ASN1ObjectIdentifier sm1_ecb;
    public static final ASN1ObjectIdentifier sm1_ofb128;
    public static final ASN1ObjectIdentifier sm2encrypt;
    public static final ASN1ObjectIdentifier sm2encrypt_recommendedParameters;
    public static final ASN1ObjectIdentifier sm2encrypt_specifiedParameters;
    public static final ASN1ObjectIdentifier sm2encrypt_with_blake2b512;
    public static final ASN1ObjectIdentifier sm2encrypt_with_blake2s256;
    public static final ASN1ObjectIdentifier sm2encrypt_with_md5;
    public static final ASN1ObjectIdentifier sm2encrypt_with_rmd160;
    public static final ASN1ObjectIdentifier sm2encrypt_with_sha1;
    public static final ASN1ObjectIdentifier sm2encrypt_with_sha224;
    public static final ASN1ObjectIdentifier sm2encrypt_with_sha256;
    public static final ASN1ObjectIdentifier sm2encrypt_with_sha384;
    public static final ASN1ObjectIdentifier sm2encrypt_with_sha512;
    public static final ASN1ObjectIdentifier sm2encrypt_with_sm3;
    public static final ASN1ObjectIdentifier sm2encrypt_with_whirlpool;
    public static final ASN1ObjectIdentifier sm2exchange;
    public static final ASN1ObjectIdentifier sm2p256v1;
    public static final ASN1ObjectIdentifier sm2sign;
    public static final ASN1ObjectIdentifier sm2sign_with_blake2b512;
    public static final ASN1ObjectIdentifier sm2sign_with_blake2s256;
    public static final ASN1ObjectIdentifier sm2sign_with_rmd160;
    public static final ASN1ObjectIdentifier sm2sign_with_sha1;
    public static final ASN1ObjectIdentifier sm2sign_with_sha224;
    public static final ASN1ObjectIdentifier sm2sign_with_sha256;
    public static final ASN1ObjectIdentifier sm2sign_with_sha384;
    public static final ASN1ObjectIdentifier sm2sign_with_sha512;
    public static final ASN1ObjectIdentifier sm2sign_with_sm3;
    public static final ASN1ObjectIdentifier sm2sign_with_whirlpool;
    public static final ASN1ObjectIdentifier sm3;
    public static final ASN1ObjectIdentifier sm5;
    public static final ASN1ObjectIdentifier sm6_cbc;
    public static final ASN1ObjectIdentifier sm6_cfb128;
    public static final ASN1ObjectIdentifier sm6_ecb;
    public static final ASN1ObjectIdentifier sm6_ofb128;
    public static final ASN1ObjectIdentifier sm9encrypt;
    public static final ASN1ObjectIdentifier sm9keyagreement;
    public static final ASN1ObjectIdentifier sm9sign;
    public static final ASN1ObjectIdentifier sm_scheme;
    public static final ASN1ObjectIdentifier sms4_cbc;
    public static final ASN1ObjectIdentifier sms4_ccm;
    public static final ASN1ObjectIdentifier sms4_cfb1;
    public static final ASN1ObjectIdentifier sms4_cfb128;
    public static final ASN1ObjectIdentifier sms4_cfb8;
    public static final ASN1ObjectIdentifier sms4_ctr;
    public static final ASN1ObjectIdentifier sms4_ecb;
    public static final ASN1ObjectIdentifier sms4_gcm;
    public static final ASN1ObjectIdentifier sms4_ocb;
    public static final ASN1ObjectIdentifier sms4_ofb128;
    public static final ASN1ObjectIdentifier sms4_wrap;
    public static final ASN1ObjectIdentifier sms4_wrap_pad;
    public static final ASN1ObjectIdentifier sms4_xts;
    public static final ASN1ObjectIdentifier ssf33_cbc;
    public static final ASN1ObjectIdentifier ssf33_cfb1;
    public static final ASN1ObjectIdentifier ssf33_cfb128;
    public static final ASN1ObjectIdentifier ssf33_cfb8;
    public static final ASN1ObjectIdentifier ssf33_ecb;
    public static final ASN1ObjectIdentifier ssf33_ofb128;
    public static final ASN1ObjectIdentifier wapip192v1;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.2.156.10197.1");
        sm_scheme = aSN1ObjectIdentifier;
        sm6_ecb = aSN1ObjectIdentifier.branch("101.1");
        sm6_cbc = aSN1ObjectIdentifier.branch("101.2");
        sm6_ofb128 = aSN1ObjectIdentifier.branch("101.3");
        sm6_cfb128 = aSN1ObjectIdentifier.branch("101.4");
        sm1_ecb = aSN1ObjectIdentifier.branch("102.1");
        sm1_cbc = aSN1ObjectIdentifier.branch("102.2");
        sm1_ofb128 = aSN1ObjectIdentifier.branch("102.3");
        sm1_cfb128 = aSN1ObjectIdentifier.branch("102.4");
        sm1_cfb1 = aSN1ObjectIdentifier.branch("102.5");
        sm1_cfb8 = aSN1ObjectIdentifier.branch("102.6");
        ssf33_ecb = aSN1ObjectIdentifier.branch("103.1");
        ssf33_cbc = aSN1ObjectIdentifier.branch("103.2");
        ssf33_ofb128 = aSN1ObjectIdentifier.branch("103.3");
        ssf33_cfb128 = aSN1ObjectIdentifier.branch("103.4");
        ssf33_cfb1 = aSN1ObjectIdentifier.branch("103.5");
        ssf33_cfb8 = aSN1ObjectIdentifier.branch("103.6");
        sms4_ecb = aSN1ObjectIdentifier.branch("104.1");
        sms4_cbc = aSN1ObjectIdentifier.branch("104.2");
        sms4_ofb128 = aSN1ObjectIdentifier.branch("104.3");
        sms4_cfb128 = aSN1ObjectIdentifier.branch("104.4");
        sms4_cfb1 = aSN1ObjectIdentifier.branch("104.5");
        sms4_cfb8 = aSN1ObjectIdentifier.branch("104.6");
        sms4_ctr = aSN1ObjectIdentifier.branch("104.7");
        sms4_gcm = aSN1ObjectIdentifier.branch("104.8");
        sms4_ccm = aSN1ObjectIdentifier.branch("104.9");
        sms4_xts = aSN1ObjectIdentifier.branch("104.10");
        sms4_wrap = aSN1ObjectIdentifier.branch("104.11");
        sms4_wrap_pad = aSN1ObjectIdentifier.branch("104.12");
        sms4_ocb = aSN1ObjectIdentifier.branch("104.100");
        sm5 = aSN1ObjectIdentifier.branch("201");
        sm2p256v1 = aSN1ObjectIdentifier.branch("301");
        sm2sign = aSN1ObjectIdentifier.branch("301.1");
        sm2exchange = aSN1ObjectIdentifier.branch("301.2");
        ASN1ObjectIdentifier branch = aSN1ObjectIdentifier.branch("301.3");
        sm2encrypt = branch;
        wapip192v1 = aSN1ObjectIdentifier.branch("301.101");
        sm2encrypt_recommendedParameters = branch.branch("1");
        sm2encrypt_specifiedParameters = branch.branch("2");
        sm2encrypt_with_sm3 = branch.branch("2.1");
        sm2encrypt_with_sha1 = branch.branch("2.2");
        sm2encrypt_with_sha224 = branch.branch("2.3");
        sm2encrypt_with_sha256 = branch.branch("2.4");
        sm2encrypt_with_sha384 = branch.branch("2.5");
        sm2encrypt_with_sha512 = branch.branch("2.6");
        sm2encrypt_with_rmd160 = branch.branch("2.7");
        sm2encrypt_with_whirlpool = branch.branch("2.8");
        sm2encrypt_with_blake2b512 = branch.branch("2.9");
        sm2encrypt_with_blake2s256 = branch.branch("2.10");
        sm2encrypt_with_md5 = branch.branch("2.11");
        id_sm9PublicKey = aSN1ObjectIdentifier.branch("302");
        sm9sign = aSN1ObjectIdentifier.branch("302.1");
        sm9keyagreement = aSN1ObjectIdentifier.branch("302.2");
        sm9encrypt = aSN1ObjectIdentifier.branch("302.3");
        ASN1ObjectIdentifier branch2 = aSN1ObjectIdentifier.branch("401");
        sm3 = branch2;
        hmac_sm3 = branch2.branch("2");
        sm2sign_with_sm3 = aSN1ObjectIdentifier.branch("501");
        sm2sign_with_sha1 = aSN1ObjectIdentifier.branch("502");
        sm2sign_with_sha256 = aSN1ObjectIdentifier.branch("503");
        sm2sign_with_sha512 = aSN1ObjectIdentifier.branch("504");
        sm2sign_with_sha224 = aSN1ObjectIdentifier.branch("505");
        sm2sign_with_sha384 = aSN1ObjectIdentifier.branch("506");
        sm2sign_with_rmd160 = aSN1ObjectIdentifier.branch("507");
        sm2sign_with_whirlpool = aSN1ObjectIdentifier.branch("520");
        sm2sign_with_blake2b512 = aSN1ObjectIdentifier.branch("521");
        sm2sign_with_blake2s256 = aSN1ObjectIdentifier.branch("522");
    }
}
