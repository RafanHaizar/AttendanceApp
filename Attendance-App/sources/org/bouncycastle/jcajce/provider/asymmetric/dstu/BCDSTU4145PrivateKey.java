package org.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.bouncycastle.jce.interfaces.ECPointEncoder;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BCDSTU4145PrivateKey implements ECPrivateKey, org.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    static final long serialVersionUID = 7245981689601667138L;
    private String algorithm = "DSTU4145";
    private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

    /* renamed from: d */
    private transient BigInteger f653d;
    private transient ECParameterSpec ecSpec;
    private transient DERBitString publicKey;
    private boolean withCompression;

    protected BCDSTU4145PrivateKey() {
    }

    public BCDSTU4145PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters) {
        this.algorithm = str;
        this.f653d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
    }

    public BCDSTU4145PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCDSTU4145PublicKey bCDSTU4145PublicKey, ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.algorithm = str;
        this.f653d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.publicKey = getPublicKeyDetails(bCDSTU4145PublicKey);
    }

    public BCDSTU4145PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCDSTU4145PublicKey bCDSTU4145PublicKey, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.algorithm = str;
        this.f653d = eCPrivateKeyParameters.getD();
        this.ecSpec = eCParameterSpec == null ? new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue()) : new ECParameterSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), EC5Util.convertPoint(eCParameterSpec.getG()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
        this.publicKey = getPublicKeyDetails(bCDSTU4145PublicKey);
    }

    public BCDSTU4145PrivateKey(ECPrivateKey eCPrivateKey) {
        this.f653d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
    }

    public BCDSTU4145PrivateKey(ECPrivateKeySpec eCPrivateKeySpec) {
        this.f653d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
    }

    BCDSTU4145PrivateKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        populateFromPrivKeyInfo(privateKeyInfo);
    }

    public BCDSTU4145PrivateKey(BCDSTU4145PrivateKey bCDSTU4145PrivateKey) {
        this.f653d = bCDSTU4145PrivateKey.f653d;
        this.ecSpec = bCDSTU4145PrivateKey.ecSpec;
        this.withCompression = bCDSTU4145PrivateKey.withCompression;
        this.attrCarrier = bCDSTU4145PrivateKey.attrCarrier;
        this.publicKey = bCDSTU4145PrivateKey.publicKey;
    }

    public BCDSTU4145PrivateKey(org.bouncycastle.jce.spec.ECPrivateKeySpec eCPrivateKeySpec) {
        this.f653d = eCPrivateKeySpec.getD();
        this.ecSpec = eCPrivateKeySpec.getParams() != null ? EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams()) : null;
    }

    private DERBitString getPublicKeyDetails(BCDSTU4145PublicKey bCDSTU4145PublicKey) {
        try {
            return SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(bCDSTU4145PublicKey.getEncoded())).getPublicKeyData();
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v0, resolved type: org.bouncycastle.jce.spec.ECNamedCurveParameterSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: org.bouncycastle.jce.spec.ECParameterSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v16, resolved type: java.security.spec.ECParameterSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: org.bouncycastle.jce.spec.ECNamedCurveParameterSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v20, resolved type: org.bouncycastle.jce.spec.ECNamedCurveParameterSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v21, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0182  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x018d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo r12) throws java.io.IOException {
        /*
            r11 = this;
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r12.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X962Parameters r0 = org.bouncycastle.asn1.p008x9.X962Parameters.getInstance(r0)
            boolean r1 = r0.isNamedCurve()
            if (r1 == 0) goto L_0x0072
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r0)
            org.bouncycastle.asn1.x9.X9ECParameters r1 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(r0)
            if (r1 != 0) goto L_0x004b
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.asn1.p007ua.DSTU4145NamedCurves.getByOID(r0)
            org.bouncycastle.math.ec.ECCurve r2 = r1.getCurve()
            byte[] r3 = r1.getSeed()
            java.security.spec.EllipticCurve r6 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r2, r3)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r2 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            java.lang.String r5 = r0.getId()
            org.bouncycastle.math.ec.ECPoint r0 = r1.getG()
            java.security.spec.ECPoint r7 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(r0)
            java.math.BigInteger r8 = r1.getN()
            java.math.BigInteger r9 = r1.getH()
            r4 = r2
            r4.<init>((java.lang.String) r5, (java.security.spec.EllipticCurve) r6, (java.security.spec.ECPoint) r7, (java.math.BigInteger) r8, (java.math.BigInteger) r9)
            goto L_0x00bb
        L_0x004b:
            org.bouncycastle.math.ec.ECCurve r2 = r1.getCurve()
            byte[] r3 = r1.getSeed()
            java.security.spec.EllipticCurve r6 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r2, r3)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r2 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            java.lang.String r5 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getCurveName(r0)
            org.bouncycastle.math.ec.ECPoint r0 = r1.getG()
            java.security.spec.ECPoint r7 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(r0)
            java.math.BigInteger r8 = r1.getN()
            java.math.BigInteger r9 = r1.getH()
            r4 = r2
            r4.<init>((java.lang.String) r5, (java.security.spec.EllipticCurve) r6, (java.security.spec.ECPoint) r7, (java.math.BigInteger) r8, (java.math.BigInteger) r9)
            goto L_0x00bb
        L_0x0072:
            boolean r1 = r0.isImplicitlyCA()
            if (r1 == 0) goto L_0x007d
            r0 = 0
            r11.ecSpec = r0
            goto L_0x017a
        L_0x007d:
            org.bouncycastle.asn1.ASN1Primitive r1 = r0.getParameters()
            org.bouncycastle.asn1.ASN1Sequence r1 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r1)
            r2 = 0
            org.bouncycastle.asn1.ASN1Encodable r2 = r1.getObjectAt(r2)
            boolean r2 = r2 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r2 == 0) goto L_0x00bf
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.asn1.p008x9.X9ECParameters.getInstance(r0)
            org.bouncycastle.math.ec.ECCurve r1 = r0.getCurve()
            byte[] r2 = r0.getSeed()
            java.security.spec.EllipticCurve r1 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r1, r2)
            java.security.spec.ECParameterSpec r2 = new java.security.spec.ECParameterSpec
            org.bouncycastle.math.ec.ECPoint r3 = r0.getG()
            java.security.spec.ECPoint r3 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(r3)
            java.math.BigInteger r4 = r0.getN()
            java.math.BigInteger r0 = r0.getH()
            int r0 = r0.intValue()
            r2.<init>(r1, r3, r4, r0)
        L_0x00bb:
            r11.ecSpec = r2
            goto L_0x017a
        L_0x00bf:
            org.bouncycastle.asn1.ua.DSTU4145Params r0 = org.bouncycastle.asn1.p007ua.DSTU4145Params.getInstance(r1)
            boolean r1 = r0.isNamedCurve()
            if (r1 == 0) goto L_0x00f0
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r0.getNamedCurve()
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.asn1.p007ua.DSTU4145NamedCurves.getByOID(r0)
            org.bouncycastle.jce.spec.ECNamedCurveParameterSpec r9 = new org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
            java.lang.String r3 = r0.getId()
            org.bouncycastle.math.ec.ECCurve r4 = r1.getCurve()
            org.bouncycastle.math.ec.ECPoint r5 = r1.getG()
            java.math.BigInteger r6 = r1.getN()
            java.math.BigInteger r7 = r1.getH()
            byte[] r8 = r1.getSeed()
            r2 = r9
            r2.<init>(r3, r4, r5, r6, r7, r8)
            goto L_0x0153
        L_0x00f0:
            org.bouncycastle.asn1.ua.DSTU4145ECBinary r0 = r0.getECBinary()
            byte[] r1 = r0.getB()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r2 = r12.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r2.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.p007ua.UAObjectIdentifiers.dstu4145le
            boolean r2 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r3)
            if (r2 == 0) goto L_0x010b
            r11.reverseBytes(r1)
        L_0x010b:
            org.bouncycastle.asn1.ua.DSTU4145BinaryField r2 = r0.getField()
            org.bouncycastle.math.ec.ECCurve$F2m r10 = new org.bouncycastle.math.ec.ECCurve$F2m
            int r4 = r2.getM()
            int r5 = r2.getK1()
            int r6 = r2.getK2()
            int r7 = r2.getK3()
            java.math.BigInteger r8 = r0.getA()
            java.math.BigInteger r9 = new java.math.BigInteger
            r2 = 1
            r9.<init>(r2, r1)
            r3 = r10
            r3.<init>((int) r4, (int) r5, (int) r6, (int) r7, (java.math.BigInteger) r8, (java.math.BigInteger) r9)
            byte[] r1 = r0.getG()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r2 = r12.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r2.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.p007ua.UAObjectIdentifiers.dstu4145le
            boolean r2 = r2.equals((org.bouncycastle.asn1.ASN1Primitive) r3)
            if (r2 == 0) goto L_0x0146
            r11.reverseBytes(r1)
        L_0x0146:
            org.bouncycastle.jce.spec.ECParameterSpec r9 = new org.bouncycastle.jce.spec.ECParameterSpec
            org.bouncycastle.math.ec.ECPoint r1 = org.bouncycastle.asn1.p007ua.DSTU4145PointEncoder.decodePoint(r10, r1)
            java.math.BigInteger r0 = r0.getN()
            r9.<init>(r10, r1, r0)
        L_0x0153:
            org.bouncycastle.math.ec.ECCurve r0 = r9.getCurve()
            byte[] r1 = r9.getSeed()
            java.security.spec.EllipticCurve r0 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r0, r1)
            java.security.spec.ECParameterSpec r1 = new java.security.spec.ECParameterSpec
            org.bouncycastle.math.ec.ECPoint r2 = r9.getG()
            java.security.spec.ECPoint r2 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(r2)
            java.math.BigInteger r3 = r9.getN()
            java.math.BigInteger r4 = r9.getH()
            int r4 = r4.intValue()
            r1.<init>(r0, r2, r3, r4)
            r11.ecSpec = r1
        L_0x017a:
            org.bouncycastle.asn1.ASN1Encodable r12 = r12.parsePrivateKey()
            boolean r0 = r12 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r0 == 0) goto L_0x018d
            org.bouncycastle.asn1.ASN1Integer r12 = org.bouncycastle.asn1.ASN1Integer.getInstance(r12)
            java.math.BigInteger r12 = r12.getValue()
            r11.f653d = r12
            goto L_0x019d
        L_0x018d:
            org.bouncycastle.asn1.sec.ECPrivateKey r12 = org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(r12)
            java.math.BigInteger r0 = r12.getKey()
            r11.f653d = r0
            org.bouncycastle.asn1.DERBitString r12 = r12.getPublicKey()
            r11.publicKey = r12
        L_0x019d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.dstu.BCDSTU4145PrivateKey.populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo):void");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private void reverseBytes(byte[] bArr) {
        for (int i = 0; i < bArr.length / 2; i++) {
            byte b = bArr[i];
            bArr[i] = bArr[(bArr.length - 1) - i];
            bArr[(bArr.length - 1) - i] = b;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getEncoded());
    }

    /* access modifiers changed from: package-private */
    public org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        return eCParameterSpec != null ? EC5Util.convertSpec(eCParameterSpec) : BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BCDSTU4145PrivateKey)) {
            return false;
        }
        BCDSTU4145PrivateKey bCDSTU4145PrivateKey = (BCDSTU4145PrivateKey) obj;
        return getD().equals(bCDSTU4145PrivateKey.getD()) && engineGetSpec().equals(bCDSTU4145PrivateKey.engineGetSpec());
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    public BigInteger getD() {
        return this.f653d;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00ac A[Catch:{ IOException -> 0x00dc }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00c1 A[Catch:{ IOException -> 0x00dc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getEncoded() {
        /*
            r9 = this;
            java.security.spec.ECParameterSpec r0 = r9.ecSpec
            boolean r1 = r0 instanceof org.bouncycastle.jce.spec.ECNamedCurveSpec
            r2 = 0
            if (r1 == 0) goto L_0x0026
            org.bouncycastle.jce.spec.ECNamedCurveSpec r0 = (org.bouncycastle.jce.spec.ECNamedCurveSpec) r0
            java.lang.String r0 = r0.getName()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveOid((java.lang.String) r0)
            if (r0 != 0) goto L_0x0020
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = new org.bouncycastle.asn1.ASN1ObjectIdentifier
            java.security.spec.ECParameterSpec r1 = r9.ecSpec
            org.bouncycastle.jce.spec.ECNamedCurveSpec r1 = (org.bouncycastle.jce.spec.ECNamedCurveSpec) r1
            java.lang.String r1 = r1.getName()
            r0.<init>((java.lang.String) r1)
        L_0x0020:
            org.bouncycastle.asn1.x9.X962Parameters r1 = new org.bouncycastle.asn1.x9.X962Parameters
            r1.<init>((org.bouncycastle.asn1.ASN1ObjectIdentifier) r0)
            goto L_0x0079
        L_0x0026:
            if (r0 != 0) goto L_0x003a
            org.bouncycastle.asn1.x9.X962Parameters r1 = new org.bouncycastle.asn1.x9.X962Parameters
            org.bouncycastle.asn1.DERNull r0 = org.bouncycastle.asn1.DERNull.INSTANCE
            r1.<init>((org.bouncycastle.asn1.ASN1Null) r0)
            org.bouncycastle.jcajce.provider.config.ProviderConfiguration r0 = org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION
            java.math.BigInteger r3 = r9.getS()
            int r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getOrderBitLength(r0, r2, r3)
            goto L_0x0089
        L_0x003a:
            java.security.spec.EllipticCurve r0 = r0.getCurve()
            org.bouncycastle.math.ec.ECCurve r4 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r0)
            org.bouncycastle.asn1.x9.X9ECParameters r0 = new org.bouncycastle.asn1.x9.X9ECParameters
            org.bouncycastle.asn1.x9.X9ECPoint r5 = new org.bouncycastle.asn1.x9.X9ECPoint
            java.security.spec.ECParameterSpec r1 = r9.ecSpec
            java.security.spec.ECPoint r1 = r1.getGenerator()
            org.bouncycastle.math.ec.ECPoint r1 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint((org.bouncycastle.math.p018ec.ECCurve) r4, (java.security.spec.ECPoint) r1)
            boolean r3 = r9.withCompression
            r5.<init>((org.bouncycastle.math.p018ec.ECPoint) r1, (boolean) r3)
            java.security.spec.ECParameterSpec r1 = r9.ecSpec
            java.math.BigInteger r6 = r1.getOrder()
            java.security.spec.ECParameterSpec r1 = r9.ecSpec
            int r1 = r1.getCofactor()
            long r7 = (long) r1
            java.math.BigInteger r7 = java.math.BigInteger.valueOf(r7)
            java.security.spec.ECParameterSpec r1 = r9.ecSpec
            java.security.spec.EllipticCurve r1 = r1.getCurve()
            byte[] r8 = r1.getSeed()
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r8)
            org.bouncycastle.asn1.x9.X962Parameters r1 = new org.bouncycastle.asn1.x9.X962Parameters
            r1.<init>((org.bouncycastle.asn1.p008x9.X9ECParameters) r0)
        L_0x0079:
            org.bouncycastle.jcajce.provider.config.ProviderConfiguration r0 = org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION
            java.security.spec.ECParameterSpec r3 = r9.ecSpec
            java.math.BigInteger r3 = r3.getOrder()
            java.math.BigInteger r4 = r9.getS()
            int r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getOrderBitLength(r0, r3, r4)
        L_0x0089:
            org.bouncycastle.asn1.DERBitString r3 = r9.publicKey
            if (r3 == 0) goto L_0x0099
            org.bouncycastle.asn1.sec.ECPrivateKey r3 = new org.bouncycastle.asn1.sec.ECPrivateKey
            java.math.BigInteger r4 = r9.getS()
            org.bouncycastle.asn1.DERBitString r5 = r9.publicKey
            r3.<init>(r0, r4, r5, r1)
            goto L_0x00a2
        L_0x0099:
            org.bouncycastle.asn1.sec.ECPrivateKey r3 = new org.bouncycastle.asn1.sec.ECPrivateKey
            java.math.BigInteger r4 = r9.getS()
            r3.<init>((int) r0, (java.math.BigInteger) r4, (org.bouncycastle.asn1.ASN1Encodable) r1)
        L_0x00a2:
            java.lang.String r0 = r9.algorithm     // Catch:{ IOException -> 0x00dc }
            java.lang.String r4 = "DSTU4145"
            boolean r0 = r0.equals(r4)     // Catch:{ IOException -> 0x00dc }
            if (r0 == 0) goto L_0x00c1
            org.bouncycastle.asn1.pkcs.PrivateKeyInfo r0 = new org.bouncycastle.asn1.pkcs.PrivateKeyInfo     // Catch:{ IOException -> 0x00dc }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r4 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier     // Catch:{ IOException -> 0x00dc }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = org.bouncycastle.asn1.p007ua.UAObjectIdentifiers.dstu4145be     // Catch:{ IOException -> 0x00dc }
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()     // Catch:{ IOException -> 0x00dc }
            r4.<init>(r5, r1)     // Catch:{ IOException -> 0x00dc }
            org.bouncycastle.asn1.ASN1Primitive r1 = r3.toASN1Primitive()     // Catch:{ IOException -> 0x00dc }
            r0.<init>(r4, r1)     // Catch:{ IOException -> 0x00dc }
            goto L_0x00d5
        L_0x00c1:
            org.bouncycastle.asn1.pkcs.PrivateKeyInfo r0 = new org.bouncycastle.asn1.pkcs.PrivateKeyInfo     // Catch:{ IOException -> 0x00dc }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r4 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier     // Catch:{ IOException -> 0x00dc }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = org.bouncycastle.asn1.p008x9.X9ObjectIdentifiers.id_ecPublicKey     // Catch:{ IOException -> 0x00dc }
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()     // Catch:{ IOException -> 0x00dc }
            r4.<init>(r5, r1)     // Catch:{ IOException -> 0x00dc }
            org.bouncycastle.asn1.ASN1Primitive r1 = r3.toASN1Primitive()     // Catch:{ IOException -> 0x00dc }
            r0.<init>(r4, r1)     // Catch:{ IOException -> 0x00dc }
        L_0x00d5:
            java.lang.String r1 = "DER"
            byte[] r0 = r0.getEncoded(r1)     // Catch:{ IOException -> 0x00dc }
            return r0
        L_0x00dc:
            r0 = move-exception
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.dstu.BCDSTU4145PrivateKey.getEncoded():byte[]");
    }

    public String getFormat() {
        return "PKCS#8";
    }

    public org.bouncycastle.jce.spec.ECParameterSpec getParameters() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec == null) {
            return null;
        }
        return EC5Util.convertSpec(eCParameterSpec);
    }

    public ECParameterSpec getParams() {
        return this.ecSpec;
    }

    public BigInteger getS() {
        return this.f653d;
    }

    public int hashCode() {
        return getD().hashCode() ^ engineGetSpec().hashCode();
    }

    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    public void setPointFormat(String str) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public String toString() {
        return ECUtil.privateKeyToString(this.algorithm, this.f653d, engineGetSpec());
    }
}
