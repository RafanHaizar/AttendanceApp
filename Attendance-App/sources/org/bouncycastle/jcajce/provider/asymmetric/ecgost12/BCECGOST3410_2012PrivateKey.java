package org.bouncycastle.jcajce.provider.asymmetric.ecgost12;

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
import org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
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

public class BCECGOST3410_2012PrivateKey implements ECPrivateKey, org.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    static final long serialVersionUID = 7245981689601667138L;
    private String algorithm = "ECGOST3410-2012";
    private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

    /* renamed from: d */
    private transient BigInteger f656d;
    private transient ECParameterSpec ecSpec;
    private transient GOST3410PublicKeyAlgParameters gostParams;
    private transient DERBitString publicKey;
    private boolean withCompression;

    protected BCECGOST3410_2012PrivateKey() {
    }

    public BCECGOST3410_2012PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters) {
        this.algorithm = str;
        this.f656d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
    }

    public BCECGOST3410_2012PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCECGOST3410_2012PublicKey bCECGOST3410_2012PublicKey, ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.algorithm = str;
        this.f656d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.gostParams = bCECGOST3410_2012PublicKey.getGostParams();
        this.publicKey = getPublicKeyDetails(bCECGOST3410_2012PublicKey);
    }

    public BCECGOST3410_2012PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCECGOST3410_2012PublicKey bCECGOST3410_2012PublicKey, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        this.algorithm = str;
        this.f656d = eCPrivateKeyParameters.getD();
        this.ecSpec = eCParameterSpec == null ? new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue()) : new ECParameterSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), EC5Util.convertPoint(eCParameterSpec.getG()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
        this.gostParams = bCECGOST3410_2012PublicKey.getGostParams();
        this.publicKey = getPublicKeyDetails(bCECGOST3410_2012PublicKey);
    }

    public BCECGOST3410_2012PrivateKey(ECPrivateKey eCPrivateKey) {
        this.f656d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
    }

    public BCECGOST3410_2012PrivateKey(ECPrivateKeySpec eCPrivateKeySpec) {
        this.f656d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
    }

    BCECGOST3410_2012PrivateKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        populateFromPrivKeyInfo(privateKeyInfo);
    }

    public BCECGOST3410_2012PrivateKey(BCECGOST3410_2012PrivateKey bCECGOST3410_2012PrivateKey) {
        this.f656d = bCECGOST3410_2012PrivateKey.f656d;
        this.ecSpec = bCECGOST3410_2012PrivateKey.ecSpec;
        this.withCompression = bCECGOST3410_2012PrivateKey.withCompression;
        this.attrCarrier = bCECGOST3410_2012PrivateKey.attrCarrier;
        this.publicKey = bCECGOST3410_2012PrivateKey.publicKey;
        this.gostParams = bCECGOST3410_2012PrivateKey.gostParams;
    }

    public BCECGOST3410_2012PrivateKey(org.bouncycastle.jce.spec.ECPrivateKeySpec eCPrivateKeySpec) {
        this.f656d = eCPrivateKeySpec.getD();
        this.ecSpec = eCPrivateKeySpec.getParams() != null ? EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams()) : null;
    }

    private void extractBytes(byte[] bArr, int i, int i2, BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length < i) {
            byte[] bArr2 = new byte[i];
            System.arraycopy(byteArray, 0, bArr2, i - byteArray.length, byteArray.length);
            byteArray = bArr2;
        }
        for (int i3 = 0; i3 != i; i3++) {
            bArr[i2 + i3] = byteArray[(byteArray.length - 1) - i3];
        }
    }

    private DERBitString getPublicKeyDetails(BCECGOST3410_2012PublicKey bCECGOST3410_2012PublicKey) {
        return SubjectPublicKeyInfo.getInstance(bCECGOST3410_2012PublicKey.getEncoded()).getPublicKeyData();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.security.spec.ECParameterSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v17, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x015f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo r11) throws java.io.IOException {
        /*
            r10 = this;
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r11.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.toASN1Primitive()
            boolean r1 = r0 instanceof org.bouncycastle.asn1.ASN1Sequence
            if (r1 == 0) goto L_0x00a1
            org.bouncycastle.asn1.ASN1Sequence r1 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r0)
            int r1 = r1.size()
            r2 = 2
            if (r1 == r2) goto L_0x0026
            org.bouncycastle.asn1.ASN1Sequence r0 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r0)
            int r0 = r0.size()
            r1 = 3
            if (r0 != r1) goto L_0x00a1
        L_0x0026:
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r11.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters r0 = org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters.getInstance(r0)
            r10.gostParams = r0
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r0.getPublicKeyParamSet()
            java.lang.String r0 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getName(r0)
            org.bouncycastle.jce.spec.ECNamedCurveParameterSpec r0 = org.bouncycastle.jce.ECGOST3410NamedCurveTable.getParameterSpec(r0)
            org.bouncycastle.math.ec.ECCurve r1 = r0.getCurve()
            byte[] r2 = r0.getSeed()
            java.security.spec.EllipticCurve r5 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r1, r2)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r1 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters r2 = r10.gostParams
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r2.getPublicKeyParamSet()
            java.lang.String r4 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getName(r2)
            org.bouncycastle.math.ec.ECPoint r2 = r0.getG()
            java.security.spec.ECPoint r6 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(r2)
            java.math.BigInteger r7 = r0.getN()
            java.math.BigInteger r8 = r0.getH()
            r3 = r1
            r3.<init>((java.lang.String) r4, (java.security.spec.EllipticCurve) r5, (java.security.spec.ECPoint) r6, (java.math.BigInteger) r7, (java.math.BigInteger) r8)
            r10.ecSpec = r1
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            boolean r0 = r11 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r0 == 0) goto L_0x0080
            org.bouncycastle.asn1.ASN1Integer r11 = org.bouncycastle.asn1.ASN1Integer.getInstance(r11)
            java.math.BigInteger r11 = r11.getPositiveValue()
            goto L_0x015c
        L_0x0080:
            org.bouncycastle.asn1.ASN1OctetString r11 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r11)
            byte[] r11 = r11.getOctets()
            int r0 = r11.length
            byte[] r0 = new byte[r0]
            r1 = 0
        L_0x008c:
            int r2 = r11.length
            r3 = 1
            if (r1 == r2) goto L_0x009a
            int r2 = r11.length
            int r2 = r2 - r3
            int r2 = r2 - r1
            byte r2 = r11[r2]
            r0[r1] = r2
            int r1 = r1 + 1
            goto L_0x008c
        L_0x009a:
            java.math.BigInteger r11 = new java.math.BigInteger
            r11.<init>(r3, r0)
            goto L_0x015c
        L_0x00a1:
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r11.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X962Parameters r0 = org.bouncycastle.asn1.p008x9.X962Parameters.getInstance(r0)
            boolean r1 = r0.isNamedCurve()
            if (r1 == 0) goto L_0x0113
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r0)
            org.bouncycastle.asn1.x9.X9ECParameters r1 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(r0)
            if (r1 != 0) goto L_0x00ec
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getByOID(r0)
            org.bouncycastle.math.ec.ECCurve r2 = r1.getCurve()
            byte[] r3 = r1.getSeed()
            java.security.spec.EllipticCurve r6 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r2, r3)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r2 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            java.lang.String r5 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getName(r0)
            org.bouncycastle.math.ec.ECPoint r0 = r1.getG()
            java.security.spec.ECPoint r7 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(r0)
            java.math.BigInteger r8 = r1.getN()
            java.math.BigInteger r9 = r1.getH()
            r4 = r2
            r4.<init>((java.lang.String) r5, (java.security.spec.EllipticCurve) r6, (java.security.spec.ECPoint) r7, (java.math.BigInteger) r8, (java.math.BigInteger) r9)
            goto L_0x014a
        L_0x00ec:
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
            goto L_0x014a
        L_0x0113:
            boolean r1 = r0.isImplicitlyCA()
            if (r1 == 0) goto L_0x011d
            r0 = 0
            r10.ecSpec = r0
            goto L_0x014c
        L_0x011d:
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
        L_0x014a:
            r10.ecSpec = r2
        L_0x014c:
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            boolean r0 = r11 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r0 == 0) goto L_0x015f
            org.bouncycastle.asn1.ASN1Integer r11 = org.bouncycastle.asn1.ASN1Integer.getInstance(r11)
            java.math.BigInteger r11 = r11.getValue()
        L_0x015c:
            r10.f656d = r11
            goto L_0x016f
        L_0x015f:
            org.bouncycastle.asn1.sec.ECPrivateKey r11 = org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(r11)
            java.math.BigInteger r0 = r11.getKey()
            r10.f656d = r0
            org.bouncycastle.asn1.DERBitString r11 = r11.getPublicKey()
            r10.publicKey = r11
        L_0x016f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.ecgost12.BCECGOST3410_2012PrivateKey.populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo):void");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
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
        if (!(obj instanceof BCECGOST3410_2012PrivateKey)) {
            return false;
        }
        BCECGOST3410_2012PrivateKey bCECGOST3410_2012PrivateKey = (BCECGOST3410_2012PrivateKey) obj;
        return getD().equals(bCECGOST3410_2012PrivateKey.getD()) && engineGetSpec().equals(bCECGOST3410_2012PrivateKey.engineGetSpec());
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
        return this.f656d;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00dc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getEncoded() {
        /*
            r12 = this;
            java.math.BigInteger r0 = r12.f656d
            int r0 = r0.bitLength()
            r1 = 256(0x100, float:3.59E-43)
            r2 = 0
            if (r0 <= r1) goto L_0x000d
            r0 = 1
            goto L_0x000e
        L_0x000d:
            r0 = 0
        L_0x000e:
            if (r0 == 0) goto L_0x0013
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers.id_tc26_gost_3410_12_512
            goto L_0x0015
        L_0x0013:
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers.id_tc26_gost_3410_12_256
        L_0x0015:
            if (r0 == 0) goto L_0x001a
            r0 = 64
            goto L_0x001c
        L_0x001a:
            r0 = 32
        L_0x001c:
            org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters r3 = r12.gostParams
            java.lang.String r4 = "DER"
            r5 = 0
            if (r3 == 0) goto L_0x0044
            byte[] r3 = new byte[r0]
            java.math.BigInteger r6 = r12.getS()
            r12.extractBytes(r3, r0, r2, r6)
            org.bouncycastle.asn1.pkcs.PrivateKeyInfo r0 = new org.bouncycastle.asn1.pkcs.PrivateKeyInfo     // Catch:{ IOException -> 0x0042 }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r2 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier     // Catch:{ IOException -> 0x0042 }
            org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters r6 = r12.gostParams     // Catch:{ IOException -> 0x0042 }
            r2.<init>(r1, r6)     // Catch:{ IOException -> 0x0042 }
            org.bouncycastle.asn1.DEROctetString r1 = new org.bouncycastle.asn1.DEROctetString     // Catch:{ IOException -> 0x0042 }
            r1.<init>((byte[]) r3)     // Catch:{ IOException -> 0x0042 }
            r0.<init>(r2, r1)     // Catch:{ IOException -> 0x0042 }
            byte[] r0 = r0.getEncoded(r4)     // Catch:{ IOException -> 0x0042 }
            return r0
        L_0x0042:
            r0 = move-exception
            return r5
        L_0x0044:
            java.security.spec.ECParameterSpec r0 = r12.ecSpec
            boolean r2 = r0 instanceof org.bouncycastle.jce.spec.ECNamedCurveSpec
            if (r2 == 0) goto L_0x0069
            org.bouncycastle.jce.spec.ECNamedCurveSpec r0 = (org.bouncycastle.jce.spec.ECNamedCurveSpec) r0
            java.lang.String r0 = r0.getName()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveOid((java.lang.String) r0)
            if (r0 != 0) goto L_0x0063
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = new org.bouncycastle.asn1.ASN1ObjectIdentifier
            java.security.spec.ECParameterSpec r2 = r12.ecSpec
            org.bouncycastle.jce.spec.ECNamedCurveSpec r2 = (org.bouncycastle.jce.spec.ECNamedCurveSpec) r2
            java.lang.String r2 = r2.getName()
            r0.<init>((java.lang.String) r2)
        L_0x0063:
            org.bouncycastle.asn1.x9.X962Parameters r2 = new org.bouncycastle.asn1.x9.X962Parameters
            r2.<init>((org.bouncycastle.asn1.ASN1ObjectIdentifier) r0)
            goto L_0x00bc
        L_0x0069:
            if (r0 != 0) goto L_0x007d
            org.bouncycastle.asn1.x9.X962Parameters r2 = new org.bouncycastle.asn1.x9.X962Parameters
            org.bouncycastle.asn1.DERNull r0 = org.bouncycastle.asn1.DERNull.INSTANCE
            r2.<init>((org.bouncycastle.asn1.ASN1Null) r0)
            org.bouncycastle.jcajce.provider.config.ProviderConfiguration r0 = org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION
            java.math.BigInteger r3 = r12.getS()
            int r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getOrderBitLength(r0, r5, r3)
            goto L_0x00cc
        L_0x007d:
            java.security.spec.EllipticCurve r0 = r0.getCurve()
            org.bouncycastle.math.ec.ECCurve r7 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r0)
            org.bouncycastle.asn1.x9.X9ECParameters r0 = new org.bouncycastle.asn1.x9.X9ECParameters
            org.bouncycastle.asn1.x9.X9ECPoint r8 = new org.bouncycastle.asn1.x9.X9ECPoint
            java.security.spec.ECParameterSpec r2 = r12.ecSpec
            java.security.spec.ECPoint r2 = r2.getGenerator()
            org.bouncycastle.math.ec.ECPoint r2 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint((org.bouncycastle.math.p018ec.ECCurve) r7, (java.security.spec.ECPoint) r2)
            boolean r3 = r12.withCompression
            r8.<init>((org.bouncycastle.math.p018ec.ECPoint) r2, (boolean) r3)
            java.security.spec.ECParameterSpec r2 = r12.ecSpec
            java.math.BigInteger r9 = r2.getOrder()
            java.security.spec.ECParameterSpec r2 = r12.ecSpec
            int r2 = r2.getCofactor()
            long r2 = (long) r2
            java.math.BigInteger r10 = java.math.BigInteger.valueOf(r2)
            java.security.spec.ECParameterSpec r2 = r12.ecSpec
            java.security.spec.EllipticCurve r2 = r2.getCurve()
            byte[] r11 = r2.getSeed()
            r6 = r0
            r6.<init>(r7, r8, r9, r10, r11)
            org.bouncycastle.asn1.x9.X962Parameters r2 = new org.bouncycastle.asn1.x9.X962Parameters
            r2.<init>((org.bouncycastle.asn1.p008x9.X9ECParameters) r0)
        L_0x00bc:
            org.bouncycastle.jcajce.provider.config.ProviderConfiguration r0 = org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION
            java.security.spec.ECParameterSpec r3 = r12.ecSpec
            java.math.BigInteger r3 = r3.getOrder()
            java.math.BigInteger r6 = r12.getS()
            int r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getOrderBitLength(r0, r3, r6)
        L_0x00cc:
            org.bouncycastle.asn1.DERBitString r3 = r12.publicKey
            if (r3 == 0) goto L_0x00dc
            org.bouncycastle.asn1.sec.ECPrivateKey r3 = new org.bouncycastle.asn1.sec.ECPrivateKey
            java.math.BigInteger r6 = r12.getS()
            org.bouncycastle.asn1.DERBitString r7 = r12.publicKey
            r3.<init>(r0, r6, r7, r2)
            goto L_0x00e5
        L_0x00dc:
            org.bouncycastle.asn1.sec.ECPrivateKey r3 = new org.bouncycastle.asn1.sec.ECPrivateKey
            java.math.BigInteger r6 = r12.getS()
            r3.<init>((int) r0, (java.math.BigInteger) r6, (org.bouncycastle.asn1.ASN1Encodable) r2)
        L_0x00e5:
            org.bouncycastle.asn1.pkcs.PrivateKeyInfo r0 = new org.bouncycastle.asn1.pkcs.PrivateKeyInfo     // Catch:{ IOException -> 0x00fc }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r6 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier     // Catch:{ IOException -> 0x00fc }
            org.bouncycastle.asn1.ASN1Primitive r2 = r2.toASN1Primitive()     // Catch:{ IOException -> 0x00fc }
            r6.<init>(r1, r2)     // Catch:{ IOException -> 0x00fc }
            org.bouncycastle.asn1.ASN1Primitive r1 = r3.toASN1Primitive()     // Catch:{ IOException -> 0x00fc }
            r0.<init>(r6, r1)     // Catch:{ IOException -> 0x00fc }
            byte[] r0 = r0.getEncoded(r4)     // Catch:{ IOException -> 0x00fc }
            return r0
        L_0x00fc:
            r0 = move-exception
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.ecgost12.BCECGOST3410_2012PrivateKey.getEncoded():byte[]");
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
        return this.f656d;
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
        return ECUtil.privateKeyToString(this.algorithm, this.f656d, engineGetSpec());
    }
}
