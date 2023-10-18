package org.bouncycastle.asn1.cryptopro;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface CryptoProObjectIdentifiers {
    public static final ASN1ObjectIdentifier GOST_id;
    public static final ASN1ObjectIdentifier gostR28147_gcfb;
    public static final ASN1ObjectIdentifier gostR3410_2001;
    public static final ASN1ObjectIdentifier gostR3410_2001DH;
    public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_A;
    public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_B;
    public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_C;
    public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_ESDH;
    public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_XchA;
    public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_XchB;
    public static final ASN1ObjectIdentifier gostR3410_94;
    public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_A;
    public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_B;
    public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_C;
    public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_D;
    public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_XchA;
    public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_XchB;
    public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_XchC;
    public static final ASN1ObjectIdentifier gostR3411;
    public static final ASN1ObjectIdentifier gostR3411Hmac;
    public static final ASN1ObjectIdentifier gostR3411_94_CryptoProParamSet;
    public static final ASN1ObjectIdentifier gostR3411_94_with_gostR3410_2001;
    public static final ASN1ObjectIdentifier gostR3411_94_with_gostR3410_94;
    public static final ASN1ObjectIdentifier gost_ElSgDH3410_1;
    public static final ASN1ObjectIdentifier gost_ElSgDH3410_default;
    public static final ASN1ObjectIdentifier id_Gost28147_89_CryptoPro_A_ParamSet;
    public static final ASN1ObjectIdentifier id_Gost28147_89_CryptoPro_B_ParamSet;
    public static final ASN1ObjectIdentifier id_Gost28147_89_CryptoPro_C_ParamSet;
    public static final ASN1ObjectIdentifier id_Gost28147_89_CryptoPro_D_ParamSet;
    public static final ASN1ObjectIdentifier id_Gost28147_89_CryptoPro_KeyWrap;
    public static final ASN1ObjectIdentifier id_Gost28147_89_CryptoPro_TestParamSet;
    public static final ASN1ObjectIdentifier id_Gost28147_89_None_KeyWrap;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.2.643.2.2");
        GOST_id = aSN1ObjectIdentifier;
        gostR3411 = aSN1ObjectIdentifier.branch("9");
        gostR3411Hmac = aSN1ObjectIdentifier.branch("10");
        id_Gost28147_89_None_KeyWrap = aSN1ObjectIdentifier.branch("13.0");
        id_Gost28147_89_CryptoPro_KeyWrap = aSN1ObjectIdentifier.branch("13.1");
        gostR28147_gcfb = aSN1ObjectIdentifier.branch("21");
        id_Gost28147_89_CryptoPro_TestParamSet = aSN1ObjectIdentifier.branch("31.0");
        id_Gost28147_89_CryptoPro_A_ParamSet = aSN1ObjectIdentifier.branch("31.1");
        id_Gost28147_89_CryptoPro_B_ParamSet = aSN1ObjectIdentifier.branch("31.2");
        id_Gost28147_89_CryptoPro_C_ParamSet = aSN1ObjectIdentifier.branch("31.3");
        id_Gost28147_89_CryptoPro_D_ParamSet = aSN1ObjectIdentifier.branch("31.4");
        gostR3410_94 = aSN1ObjectIdentifier.branch("20");
        gostR3410_2001 = aSN1ObjectIdentifier.branch("19");
        gostR3411_94_with_gostR3410_94 = aSN1ObjectIdentifier.branch("4");
        gostR3411_94_with_gostR3410_2001 = aSN1ObjectIdentifier.branch("3");
        gostR3411_94_CryptoProParamSet = aSN1ObjectIdentifier.branch("30.1");
        gostR3410_94_CryptoPro_A = aSN1ObjectIdentifier.branch("32.2");
        gostR3410_94_CryptoPro_B = aSN1ObjectIdentifier.branch("32.3");
        gostR3410_94_CryptoPro_C = aSN1ObjectIdentifier.branch("32.4");
        gostR3410_94_CryptoPro_D = aSN1ObjectIdentifier.branch("32.5");
        gostR3410_94_CryptoPro_XchA = aSN1ObjectIdentifier.branch("33.1");
        gostR3410_94_CryptoPro_XchB = aSN1ObjectIdentifier.branch("33.2");
        gostR3410_94_CryptoPro_XchC = aSN1ObjectIdentifier.branch("33.3");
        gostR3410_2001_CryptoPro_A = aSN1ObjectIdentifier.branch("35.1");
        gostR3410_2001_CryptoPro_B = aSN1ObjectIdentifier.branch("35.2");
        gostR3410_2001_CryptoPro_C = aSN1ObjectIdentifier.branch("35.3");
        gostR3410_2001_CryptoPro_XchA = aSN1ObjectIdentifier.branch("36.0");
        gostR3410_2001_CryptoPro_XchB = aSN1ObjectIdentifier.branch("36.1");
        gost_ElSgDH3410_default = aSN1ObjectIdentifier.branch("36.0");
        gost_ElSgDH3410_1 = aSN1ObjectIdentifier.branch("36.1");
        gostR3410_2001_CryptoPro_ESDH = aSN1ObjectIdentifier.branch("96");
        gostR3410_2001DH = aSN1ObjectIdentifier.branch("98");
    }
}
