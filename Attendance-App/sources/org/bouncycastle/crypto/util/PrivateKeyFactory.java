package org.bouncycastle.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class PrivateKeyFactory {
    public static AsymmetricKeyParameter createKey(InputStream inputStream) throws IOException {
        return createKey(PrivateKeyInfo.getInstance(new ASN1InputStream(inputStream).readObject()));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v16, resolved type: org.bouncycastle.crypto.params.ECGOST3410Parameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v17, resolved type: org.bouncycastle.crypto.params.ECGOST3410Parameters} */
    /* JADX WARNING: type inference failed for: r2v2, types: [org.bouncycastle.crypto.params.ECDomainParameters] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r8v1, types: [org.bouncycastle.crypto.params.ECGOST3410Parameters] */
    /* JADX WARNING: type inference failed for: r9v3, types: [org.bouncycastle.crypto.params.ECGOST3410Parameters] */
    /* JADX WARNING: type inference failed for: r9v4, types: [org.bouncycastle.crypto.params.ECGOST3410Parameters] */
    /* JADX WARNING: type inference failed for: r8v4, types: [org.bouncycastle.crypto.params.ECDomainParameters] */
    /* JADX WARNING: type inference failed for: r1v49, types: [org.bouncycastle.crypto.params.ECDomainParameters] */
    /* JADX WARNING: type inference failed for: r1v50, types: [org.bouncycastle.crypto.params.ECNamedDomainParameters] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.bouncycastle.crypto.params.AsymmetricKeyParameter createKey(org.bouncycastle.asn1.pkcs.PrivateKeyInfo r11) throws java.io.IOException {
        /*
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r11.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r0.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption
            boolean r1 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            if (r1 != 0) goto L_0x0305
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS
            boolean r1 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            if (r1 != 0) goto L_0x0305
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_ea_rsa
            boolean r1 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            if (r1 == 0) goto L_0x0022
            goto L_0x0305
        L_0x0022:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement
            boolean r1 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            r3 = 0
            r4 = 0
            if (r1 == 0) goto L_0x005c
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.pkcs.DHParameter r0 = org.bouncycastle.asn1.pkcs.DHParameter.getInstance(r0)
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            org.bouncycastle.asn1.ASN1Integer r11 = (org.bouncycastle.asn1.ASN1Integer) r11
            java.math.BigInteger r1 = r0.getL()
            if (r1 != 0) goto L_0x0041
            goto L_0x0045
        L_0x0041:
            int r4 = r1.intValue()
        L_0x0045:
            org.bouncycastle.crypto.params.DHParameters r1 = new org.bouncycastle.crypto.params.DHParameters
            java.math.BigInteger r2 = r0.getP()
            java.math.BigInteger r0 = r0.getG()
            r1.<init>(r2, r0, r3, r4)
            org.bouncycastle.crypto.params.DHPrivateKeyParameters r0 = new org.bouncycastle.crypto.params.DHPrivateKeyParameters
            java.math.BigInteger r11 = r11.getValue()
            r0.<init>(r11, r1)
            return r0
        L_0x005c:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.elGamalAlgorithm
            boolean r1 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            if (r1 == 0) goto L_0x0089
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.oiw.ElGamalParameter r0 = org.bouncycastle.asn1.oiw.ElGamalParameter.getInstance(r0)
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            org.bouncycastle.asn1.ASN1Integer r11 = (org.bouncycastle.asn1.ASN1Integer) r11
            org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters r1 = new org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters
            java.math.BigInteger r11 = r11.getValue()
            org.bouncycastle.crypto.params.ElGamalParameters r2 = new org.bouncycastle.crypto.params.ElGamalParameters
            java.math.BigInteger r3 = r0.getP()
            java.math.BigInteger r0 = r0.getG()
            r2.<init>(r3, r0)
            r1.<init>(r11, r2)
            return r1
        L_0x0089:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.p008x9.X9ObjectIdentifiers.id_dsa
            boolean r1 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            if (r1 == 0) goto L_0x00c0
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            org.bouncycastle.asn1.ASN1Integer r11 = (org.bouncycastle.asn1.ASN1Integer) r11
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            if (r0 == 0) goto L_0x00b6
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.toASN1Primitive()
            org.bouncycastle.asn1.x509.DSAParameter r0 = org.bouncycastle.asn1.x509.DSAParameter.getInstance(r0)
            org.bouncycastle.crypto.params.DSAParameters r3 = new org.bouncycastle.crypto.params.DSAParameters
            java.math.BigInteger r1 = r0.getP()
            java.math.BigInteger r2 = r0.getQ()
            java.math.BigInteger r0 = r0.getG()
            r3.<init>(r1, r2, r0)
        L_0x00b6:
            org.bouncycastle.crypto.params.DSAPrivateKeyParameters r0 = new org.bouncycastle.crypto.params.DSAPrivateKeyParameters
            java.math.BigInteger r11 = r11.getValue()
            r0.<init>(r11, r3)
            return r0
        L_0x00c0:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.p008x9.X9ObjectIdentifiers.id_ecPublicKey
            boolean r1 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r1)
            if (r1 == 0) goto L_0x0132
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X962Parameters r0 = org.bouncycastle.asn1.p008x9.X962Parameters.getInstance(r0)
            boolean r1 = r0.isNamedCurve()
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            if (r1 == 0) goto L_0x0102
            r2 = r0
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r2
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.crypto.p012ec.CustomNamedCurves.getByOID(r2)
            if (r0 != 0) goto L_0x00e7
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.asn1.p008x9.ECNamedCurveTable.getByOID(r2)
        L_0x00e7:
            org.bouncycastle.crypto.params.ECNamedDomainParameters r8 = new org.bouncycastle.crypto.params.ECNamedDomainParameters
            org.bouncycastle.math.ec.ECCurve r3 = r0.getCurve()
            org.bouncycastle.math.ec.ECPoint r4 = r0.getG()
            java.math.BigInteger r5 = r0.getN()
            java.math.BigInteger r6 = r0.getH()
            byte[] r7 = r0.getSeed()
            r1 = r8
            r1.<init>(r2, r3, r4, r5, r6, r7)
            goto L_0x0120
        L_0x0102:
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.asn1.p008x9.X9ECParameters.getInstance(r0)
            org.bouncycastle.crypto.params.ECDomainParameters r8 = new org.bouncycastle.crypto.params.ECDomainParameters
            org.bouncycastle.math.ec.ECCurve r2 = r0.getCurve()
            org.bouncycastle.math.ec.ECPoint r3 = r0.getG()
            java.math.BigInteger r4 = r0.getN()
            java.math.BigInteger r5 = r0.getH()
            byte[] r6 = r0.getSeed()
            r1 = r8
            r1.<init>(r2, r3, r4, r5, r6)
        L_0x0120:
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            org.bouncycastle.asn1.sec.ECPrivateKey r11 = org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(r11)
            java.math.BigInteger r11 = r11.getKey()
            org.bouncycastle.crypto.params.ECPrivateKeyParameters r0 = new org.bouncycastle.crypto.params.ECPrivateKeyParameters
            r0.<init>(r11, r8)
            return r0
        L_0x0132:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.edec.EdECObjectIdentifiers.id_X25519
            boolean r0 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r0)
            r1 = 32
            if (r0 == 0) goto L_0x0146
            org.bouncycastle.crypto.params.X25519PrivateKeyParameters r0 = new org.bouncycastle.crypto.params.X25519PrivateKeyParameters
            byte[] r11 = getRawKey(r11, r1)
            r0.<init>(r11, r4)
            return r0
        L_0x0146:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.edec.EdECObjectIdentifiers.id_X448
            boolean r0 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r0)
            if (r0 == 0) goto L_0x015a
            org.bouncycastle.crypto.params.X448PrivateKeyParameters r0 = new org.bouncycastle.crypto.params.X448PrivateKeyParameters
            r1 = 56
            byte[] r11 = getRawKey(r11, r1)
            r0.<init>(r11, r4)
            return r0
        L_0x015a:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.edec.EdECObjectIdentifiers.id_Ed25519
            boolean r0 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r0)
            if (r0 == 0) goto L_0x016c
            org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters r0 = new org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
            byte[] r11 = getRawKey(r11, r1)
            r0.<init>(r11, r4)
            return r0
        L_0x016c:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.edec.EdECObjectIdentifiers.id_Ed448
            boolean r0 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r0)
            if (r0 == 0) goto L_0x0180
            org.bouncycastle.crypto.params.Ed448PrivateKeyParameters r0 = new org.bouncycastle.crypto.params.Ed448PrivateKeyParameters
            r1 = 57
            byte[] r11 = getRawKey(r11, r1)
            r0.<init>(r11, r4)
            return r0
        L_0x0180:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers.gostR3410_2001
            boolean r0 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r0)
            if (r0 != 0) goto L_0x01a1
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers.id_tc26_gost_3410_12_512
            boolean r0 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r0)
            if (r0 != 0) goto L_0x01a1
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers.id_tc26_gost_3410_12_256
            boolean r0 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r0)
            if (r0 == 0) goto L_0x0199
            goto L_0x01a1
        L_0x0199:
            java.lang.RuntimeException r11 = new java.lang.RuntimeException
            java.lang.String r0 = "algorithm identifier in private key not recognised"
            r11.<init>(r0)
            throw r11
        L_0x01a1:
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r11.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters r0 = org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters.getInstance(r0)
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r11.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getParameters()
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()
            boolean r4 = r1 instanceof org.bouncycastle.asn1.ASN1Sequence
            if (r4 == 0) goto L_0x021c
            org.bouncycastle.asn1.ASN1Sequence r4 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r1)
            int r4 = r4.size()
            r5 = 2
            if (r4 == r5) goto L_0x01d3
            org.bouncycastle.asn1.ASN1Sequence r1 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r1)
            int r1 = r1.size()
            r4 = 3
            if (r1 != r4) goto L_0x021c
        L_0x01d3:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getPublicKeyParamSet()
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getByOID(r1)
            org.bouncycastle.crypto.params.ECGOST3410Parameters r2 = new org.bouncycastle.crypto.params.ECGOST3410Parameters
            org.bouncycastle.crypto.params.ECNamedDomainParameters r3 = new org.bouncycastle.crypto.params.ECNamedDomainParameters
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r0.getPublicKeyParamSet()
            r3.<init>(r4, r1)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getPublicKeyParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r0.getDigestParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = r0.getEncryptionParamSet()
            r2.<init>(r3, r1, r4, r5)
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            boolean r1 = r11 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r1 == 0) goto L_0x0207
            org.bouncycastle.asn1.ASN1Integer r11 = org.bouncycastle.asn1.ASN1Integer.getInstance(r11)
            java.math.BigInteger r11 = r11.getPositiveValue()
            goto L_0x02ee
        L_0x0207:
            org.bouncycastle.asn1.ASN1OctetString r11 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r11)
            byte[] r11 = r11.getOctets()
            byte[] r11 = org.bouncycastle.util.Arrays.reverse((byte[]) r11)
            java.math.BigInteger r1 = new java.math.BigInteger
            r3 = 1
            r1.<init>(r3, r11)
            r11 = r1
            goto L_0x02ee
        L_0x021c:
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r11.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getParameters()
            org.bouncycastle.asn1.x9.X962Parameters r1 = org.bouncycastle.asn1.p008x9.X962Parameters.getInstance(r1)
            boolean r4 = r1.isNamedCurve()
            if (r4 == 0) goto L_0x0299
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.getParameters()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r1)
            org.bouncycastle.asn1.x9.X9ECParameters r1 = org.bouncycastle.asn1.p008x9.ECNamedCurveTable.getByOID(r3)
            if (r1 != 0) goto L_0x026c
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getByOID(r3)
            org.bouncycastle.crypto.params.ECGOST3410Parameters r9 = new org.bouncycastle.crypto.params.ECGOST3410Parameters
            org.bouncycastle.crypto.params.ECNamedDomainParameters r10 = new org.bouncycastle.crypto.params.ECNamedDomainParameters
            org.bouncycastle.math.ec.ECCurve r4 = r1.getCurve()
            org.bouncycastle.math.ec.ECPoint r5 = r1.getG()
            java.math.BigInteger r6 = r1.getN()
            java.math.BigInteger r7 = r1.getH()
            byte[] r8 = r1.getSeed()
            r2 = r10
            r2.<init>(r3, r4, r5, r6, r7, r8)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getPublicKeyParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r0.getDigestParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r0.getEncryptionParamSet()
            r9.<init>(r10, r1, r2, r3)
            goto L_0x0297
        L_0x026c:
            org.bouncycastle.crypto.params.ECGOST3410Parameters r9 = new org.bouncycastle.crypto.params.ECGOST3410Parameters
            org.bouncycastle.crypto.params.ECNamedDomainParameters r10 = new org.bouncycastle.crypto.params.ECNamedDomainParameters
            org.bouncycastle.math.ec.ECCurve r4 = r1.getCurve()
            org.bouncycastle.math.ec.ECPoint r5 = r1.getG()
            java.math.BigInteger r6 = r1.getN()
            java.math.BigInteger r7 = r1.getH()
            byte[] r8 = r1.getSeed()
            r2 = r10
            r2.<init>(r3, r4, r5, r6, r7, r8)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getPublicKeyParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r0.getDigestParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r0.getEncryptionParamSet()
            r9.<init>(r10, r1, r2, r3)
        L_0x0297:
            r3 = r9
            goto L_0x02d4
        L_0x0299:
            boolean r4 = r1.isImplicitlyCA()
            if (r4 == 0) goto L_0x02a0
            goto L_0x02d4
        L_0x02a0:
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.getParameters()
            org.bouncycastle.asn1.x9.X9ECParameters r1 = org.bouncycastle.asn1.p008x9.X9ECParameters.getInstance(r1)
            org.bouncycastle.crypto.params.ECGOST3410Parameters r8 = new org.bouncycastle.crypto.params.ECGOST3410Parameters
            org.bouncycastle.crypto.params.ECNamedDomainParameters r9 = new org.bouncycastle.crypto.params.ECNamedDomainParameters
            org.bouncycastle.math.ec.ECCurve r3 = r1.getCurve()
            org.bouncycastle.math.ec.ECPoint r4 = r1.getG()
            java.math.BigInteger r5 = r1.getN()
            java.math.BigInteger r6 = r1.getH()
            byte[] r7 = r1.getSeed()
            r1 = r9
            r1.<init>(r2, r3, r4, r5, r6, r7)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getPublicKeyParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r0.getDigestParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = r0.getEncryptionParamSet()
            r8.<init>(r9, r1, r2, r3)
            r3 = r8
        L_0x02d4:
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            boolean r1 = r11 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r1 == 0) goto L_0x02e5
            org.bouncycastle.asn1.ASN1Integer r11 = org.bouncycastle.asn1.ASN1Integer.getInstance(r11)
            java.math.BigInteger r11 = r11.getValue()
            goto L_0x02ed
        L_0x02e5:
            org.bouncycastle.asn1.sec.ECPrivateKey r11 = org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(r11)
            java.math.BigInteger r11 = r11.getKey()
        L_0x02ed:
            r2 = r3
        L_0x02ee:
            org.bouncycastle.crypto.params.ECPrivateKeyParameters r1 = new org.bouncycastle.crypto.params.ECPrivateKeyParameters
            org.bouncycastle.crypto.params.ECGOST3410Parameters r3 = new org.bouncycastle.crypto.params.ECGOST3410Parameters
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r0.getPublicKeyParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = r0.getDigestParamSet()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r0.getEncryptionParamSet()
            r3.<init>(r2, r4, r5, r0)
            r1.<init>(r11, r3)
            return r1
        L_0x0305:
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            org.bouncycastle.asn1.pkcs.RSAPrivateKey r11 = org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(r11)
            org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters r9 = new org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters
            java.math.BigInteger r1 = r11.getModulus()
            java.math.BigInteger r2 = r11.getPublicExponent()
            java.math.BigInteger r3 = r11.getPrivateExponent()
            java.math.BigInteger r4 = r11.getPrime1()
            java.math.BigInteger r5 = r11.getPrime2()
            java.math.BigInteger r6 = r11.getExponent1()
            java.math.BigInteger r7 = r11.getExponent2()
            java.math.BigInteger r8 = r11.getCoefficient()
            r0 = r9
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.util.PrivateKeyFactory.createKey(org.bouncycastle.asn1.pkcs.PrivateKeyInfo):org.bouncycastle.crypto.params.AsymmetricKeyParameter");
    }

    public static AsymmetricKeyParameter createKey(byte[] bArr) throws IOException {
        return createKey(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(bArr)));
    }

    private static byte[] getRawKey(PrivateKeyInfo privateKeyInfo, int i) throws IOException {
        byte[] octets = ASN1OctetString.getInstance(privateKeyInfo.parsePrivateKey()).getOctets();
        if (i == octets.length) {
            return octets;
        }
        throw new RuntimeException("private key encoding has incorrect length");
    }
}
