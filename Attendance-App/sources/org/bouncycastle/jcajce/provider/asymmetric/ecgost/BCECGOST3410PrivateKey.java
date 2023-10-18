package org.bouncycastle.jcajce.provider.asymmetric.ecgost;

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

public class BCECGOST3410PrivateKey implements ECPrivateKey, org.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    static final long serialVersionUID = 7245981689601667138L;
    private String algorithm = "ECGOST3410";
    private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

    /* renamed from: d */
    private transient BigInteger f655d;
    private transient ECParameterSpec ecSpec;
    private transient ASN1Encodable gostParams;
    private transient DERBitString publicKey;
    private boolean withCompression;

    protected BCECGOST3410PrivateKey() {
    }

    public BCECGOST3410PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters) {
        this.algorithm = str;
        this.f655d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
    }

    public BCECGOST3410PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCECGOST3410PublicKey bCECGOST3410PublicKey, ECParameterSpec eCParameterSpec) {
        this.algorithm = str;
        this.f655d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
            eCParameterSpec = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        }
        this.ecSpec = eCParameterSpec;
        this.gostParams = bCECGOST3410PublicKey.getGostParams();
        this.publicKey = getPublicKeyDetails(bCECGOST3410PublicKey);
    }

    public BCECGOST3410PrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, BCECGOST3410PublicKey bCECGOST3410PublicKey, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        this.algorithm = str;
        this.f655d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), EC5Util.convertPoint(eCParameterSpec.getG()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
        }
        this.gostParams = bCECGOST3410PublicKey.getGostParams();
        this.publicKey = getPublicKeyDetails(bCECGOST3410PublicKey);
    }

    public BCECGOST3410PrivateKey(ECPrivateKey eCPrivateKey) {
        this.f655d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
    }

    public BCECGOST3410PrivateKey(ECPrivateKeySpec eCPrivateKeySpec) {
        this.f655d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
    }

    BCECGOST3410PrivateKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        populateFromPrivKeyInfo(privateKeyInfo);
    }

    public BCECGOST3410PrivateKey(BCECGOST3410PrivateKey bCECGOST3410PrivateKey) {
        this.f655d = bCECGOST3410PrivateKey.f655d;
        this.ecSpec = bCECGOST3410PrivateKey.ecSpec;
        this.withCompression = bCECGOST3410PrivateKey.withCompression;
        this.attrCarrier = bCECGOST3410PrivateKey.attrCarrier;
        this.publicKey = bCECGOST3410PrivateKey.publicKey;
        this.gostParams = bCECGOST3410PrivateKey.gostParams;
    }

    public BCECGOST3410PrivateKey(org.bouncycastle.jce.spec.ECPrivateKeySpec eCPrivateKeySpec) {
        this.f655d = eCPrivateKeySpec.getD();
        this.ecSpec = eCPrivateKeySpec.getParams() != null ? EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams()) : null;
    }

    private void extractBytes(byte[] bArr, int i, BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length < 32) {
            byte[] bArr2 = new byte[32];
            System.arraycopy(byteArray, 0, bArr2, 32 - byteArray.length, byteArray.length);
            byteArray = bArr2;
        }
        for (int i2 = 0; i2 != 32; i2++) {
            bArr[i + i2] = byteArray[(byteArray.length - 1) - i2];
        }
    }

    private DERBitString getPublicKeyDetails(BCECGOST3410PublicKey bCECGOST3410PublicKey) {
        try {
            return SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(bCECGOST3410PublicKey.getEncoded())).getPublicKeyData();
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0142  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x014d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo r12) throws java.io.IOException {
        /*
            r11 = this;
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r12.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1Primitive r1 = r0.toASN1Primitive()
            boolean r2 = r1 instanceof org.bouncycastle.asn1.ASN1Sequence
            r3 = 0
            if (r2 == 0) goto L_0x0097
            org.bouncycastle.asn1.ASN1Sequence r2 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r1)
            int r2 = r2.size()
            r4 = 2
            if (r2 == r4) goto L_0x0027
            org.bouncycastle.asn1.ASN1Sequence r1 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r1)
            int r1 = r1.size()
            r2 = 3
            if (r1 != r2) goto L_0x0097
        L_0x0027:
            org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters r0 = org.bouncycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters.getInstance(r0)
            r11.gostParams = r0
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r0.getPublicKeyParamSet()
            java.lang.String r1 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getName(r1)
            org.bouncycastle.jce.spec.ECNamedCurveParameterSpec r1 = org.bouncycastle.jce.ECGOST3410NamedCurveTable.getParameterSpec(r1)
            org.bouncycastle.math.ec.ECCurve r2 = r1.getCurve()
            byte[] r4 = r1.getSeed()
            java.security.spec.EllipticCurve r7 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r2, r4)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r2 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r0.getPublicKeyParamSet()
            java.lang.String r6 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getName(r0)
            org.bouncycastle.math.ec.ECPoint r0 = r1.getG()
            java.security.spec.ECPoint r8 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(r0)
            java.math.BigInteger r9 = r1.getN()
            java.math.BigInteger r10 = r1.getH()
            r5 = r2
            r5.<init>((java.lang.String) r6, (java.security.spec.EllipticCurve) r7, (java.security.spec.ECPoint) r8, (java.math.BigInteger) r9, (java.math.BigInteger) r10)
            r11.ecSpec = r2
            org.bouncycastle.asn1.ASN1Encodable r12 = r12.parsePrivateKey()
            boolean r0 = r12 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r0 == 0) goto L_0x0077
            org.bouncycastle.asn1.ASN1Integer r12 = org.bouncycastle.asn1.ASN1Integer.getInstance(r12)
            java.math.BigInteger r12 = r12.getPositiveValue()
            goto L_0x014a
        L_0x0077:
            org.bouncycastle.asn1.ASN1OctetString r12 = org.bouncycastle.asn1.ASN1OctetString.getInstance(r12)
            byte[] r12 = r12.getOctets()
            int r0 = r12.length
            byte[] r0 = new byte[r0]
        L_0x0082:
            int r1 = r12.length
            r2 = 1
            if (r3 == r1) goto L_0x0090
            int r1 = r12.length
            int r1 = r1 - r2
            int r1 = r1 - r3
            byte r1 = r12[r1]
            r0[r3] = r1
            int r3 = r3 + 1
            goto L_0x0082
        L_0x0090:
            java.math.BigInteger r12 = new java.math.BigInteger
            r12.<init>(r2, r0)
            goto L_0x014a
        L_0x0097:
            org.bouncycastle.asn1.x9.X962Parameters r0 = org.bouncycastle.asn1.p008x9.X962Parameters.getInstance(r0)
            boolean r1 = r0.isNamedCurve()
            if (r1 == 0) goto L_0x0101
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r0)
            org.bouncycastle.asn1.x9.X9ECParameters r1 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(r0)
            if (r1 != 0) goto L_0x00d9
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getByOID(r0)
            org.bouncycastle.asn1.x9.X9ECParameters r2 = new org.bouncycastle.asn1.x9.X9ECParameters
            org.bouncycastle.math.ec.ECCurve r5 = r1.getCurve()
            org.bouncycastle.asn1.x9.X9ECPoint r6 = new org.bouncycastle.asn1.x9.X9ECPoint
            org.bouncycastle.math.ec.ECPoint r4 = r1.getG()
            r6.<init>((org.bouncycastle.math.p018ec.ECPoint) r4, (boolean) r3)
            java.math.BigInteger r7 = r1.getN()
            java.math.BigInteger r8 = r1.getH()
            byte[] r9 = r1.getSeed()
            r4 = r2
            r4.<init>(r5, r6, r7, r8, r9)
            java.lang.String r0 = org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves.getName(r0)
            r3 = r0
            r1 = r2
            goto L_0x00de
        L_0x00d9:
            java.lang.String r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getCurveName(r0)
            r3 = r0
        L_0x00de:
            org.bouncycastle.math.ec.ECCurve r0 = r1.getCurve()
            byte[] r2 = r1.getSeed()
            java.security.spec.EllipticCurve r4 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r0, r2)
            org.bouncycastle.jce.spec.ECNamedCurveSpec r0 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            org.bouncycastle.math.ec.ECPoint r2 = r1.getG()
            java.security.spec.ECPoint r5 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(r2)
            java.math.BigInteger r6 = r1.getN()
            java.math.BigInteger r7 = r1.getH()
            r2 = r0
            r2.<init>((java.lang.String) r3, (java.security.spec.EllipticCurve) r4, (java.security.spec.ECPoint) r5, (java.math.BigInteger) r6, (java.math.BigInteger) r7)
            goto L_0x0108
        L_0x0101:
            boolean r1 = r0.isImplicitlyCA()
            if (r1 == 0) goto L_0x010b
            r0 = 0
        L_0x0108:
            r11.ecSpec = r0
            goto L_0x013a
        L_0x010b:
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
            r11.ecSpec = r2
        L_0x013a:
            org.bouncycastle.asn1.ASN1Encodable r12 = r12.parsePrivateKey()
            boolean r0 = r12 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r0 == 0) goto L_0x014d
            org.bouncycastle.asn1.ASN1Integer r12 = org.bouncycastle.asn1.ASN1Integer.getInstance(r12)
            java.math.BigInteger r12 = r12.getValue()
        L_0x014a:
            r11.f655d = r12
            goto L_0x015d
        L_0x014d:
            org.bouncycastle.asn1.sec.ECPrivateKey r12 = org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(r12)
            java.math.BigInteger r0 = r12.getKey()
            r11.f655d = r0
            org.bouncycastle.asn1.DERBitString r12 = r12.getPublicKey()
            r11.publicKey = r12
        L_0x015d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.ecgost.BCECGOST3410PrivateKey.populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo):void");
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
        if (!(obj instanceof BCECGOST3410PrivateKey)) {
            return false;
        }
        BCECGOST3410PrivateKey bCECGOST3410PrivateKey = (BCECGOST3410PrivateKey) obj;
        return getD().equals(bCECGOST3410PrivateKey.getD()) && engineGetSpec().equals(bCECGOST3410PrivateKey.engineGetSpec());
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
        return this.f655d;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00b9  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00c5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getEncoded() {
        /*
            r9 = this;
            org.bouncycastle.asn1.ASN1Encodable r0 = r9.gostParams
            java.lang.String r1 = "DER"
            r2 = 0
            if (r0 == 0) goto L_0x002d
            r0 = 32
            byte[] r0 = new byte[r0]
            r3 = 0
            java.math.BigInteger r4 = r9.getS()
            r9.extractBytes(r0, r3, r4)
            org.bouncycastle.asn1.pkcs.PrivateKeyInfo r3 = new org.bouncycastle.asn1.pkcs.PrivateKeyInfo     // Catch:{ IOException -> 0x002b }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r4 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier     // Catch:{ IOException -> 0x002b }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers.gostR3410_2001     // Catch:{ IOException -> 0x002b }
            org.bouncycastle.asn1.ASN1Encodable r6 = r9.gostParams     // Catch:{ IOException -> 0x002b }
            r4.<init>(r5, r6)     // Catch:{ IOException -> 0x002b }
            org.bouncycastle.asn1.DEROctetString r5 = new org.bouncycastle.asn1.DEROctetString     // Catch:{ IOException -> 0x002b }
            r5.<init>((byte[]) r0)     // Catch:{ IOException -> 0x002b }
            r3.<init>(r4, r5)     // Catch:{ IOException -> 0x002b }
            byte[] r0 = r3.getEncoded(r1)     // Catch:{ IOException -> 0x002b }
            return r0
        L_0x002b:
            r0 = move-exception
            return r2
        L_0x002d:
            java.security.spec.ECParameterSpec r0 = r9.ecSpec
            boolean r3 = r0 instanceof org.bouncycastle.jce.spec.ECNamedCurveSpec
            if (r3 == 0) goto L_0x0052
            org.bouncycastle.jce.spec.ECNamedCurveSpec r0 = (org.bouncycastle.jce.spec.ECNamedCurveSpec) r0
            java.lang.String r0 = r0.getName()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveOid((java.lang.String) r0)
            if (r0 != 0) goto L_0x004c
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = new org.bouncycastle.asn1.ASN1ObjectIdentifier
            java.security.spec.ECParameterSpec r3 = r9.ecSpec
            org.bouncycastle.jce.spec.ECNamedCurveSpec r3 = (org.bouncycastle.jce.spec.ECNamedCurveSpec) r3
            java.lang.String r3 = r3.getName()
            r0.<init>((java.lang.String) r3)
        L_0x004c:
            org.bouncycastle.asn1.x9.X962Parameters r3 = new org.bouncycastle.asn1.x9.X962Parameters
            r3.<init>((org.bouncycastle.asn1.ASN1ObjectIdentifier) r0)
            goto L_0x00a5
        L_0x0052:
            if (r0 != 0) goto L_0x0066
            org.bouncycastle.asn1.x9.X962Parameters r3 = new org.bouncycastle.asn1.x9.X962Parameters
            org.bouncycastle.asn1.DERNull r0 = org.bouncycastle.asn1.DERNull.INSTANCE
            r3.<init>((org.bouncycastle.asn1.ASN1Null) r0)
            org.bouncycastle.jcajce.provider.config.ProviderConfiguration r0 = org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION
            java.math.BigInteger r4 = r9.getS()
            int r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getOrderBitLength(r0, r2, r4)
            goto L_0x00b5
        L_0x0066:
            java.security.spec.EllipticCurve r0 = r0.getCurve()
            org.bouncycastle.math.ec.ECCurve r4 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r0)
            org.bouncycastle.asn1.x9.X9ECParameters r0 = new org.bouncycastle.asn1.x9.X9ECParameters
            org.bouncycastle.asn1.x9.X9ECPoint r5 = new org.bouncycastle.asn1.x9.X9ECPoint
            java.security.spec.ECParameterSpec r3 = r9.ecSpec
            java.security.spec.ECPoint r3 = r3.getGenerator()
            org.bouncycastle.math.ec.ECPoint r3 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint((org.bouncycastle.math.p018ec.ECCurve) r4, (java.security.spec.ECPoint) r3)
            boolean r6 = r9.withCompression
            r5.<init>((org.bouncycastle.math.p018ec.ECPoint) r3, (boolean) r6)
            java.security.spec.ECParameterSpec r3 = r9.ecSpec
            java.math.BigInteger r6 = r3.getOrder()
            java.security.spec.ECParameterSpec r3 = r9.ecSpec
            int r3 = r3.getCofactor()
            long r7 = (long) r3
            java.math.BigInteger r7 = java.math.BigInteger.valueOf(r7)
            java.security.spec.ECParameterSpec r3 = r9.ecSpec
            java.security.spec.EllipticCurve r3 = r3.getCurve()
            byte[] r8 = r3.getSeed()
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r8)
            org.bouncycastle.asn1.x9.X962Parameters r3 = new org.bouncycastle.asn1.x9.X962Parameters
            r3.<init>((org.bouncycastle.asn1.p008x9.X9ECParameters) r0)
        L_0x00a5:
            org.bouncycastle.jcajce.provider.config.ProviderConfiguration r0 = org.bouncycastle.jce.provider.BouncyCastleProvider.CONFIGURATION
            java.security.spec.ECParameterSpec r4 = r9.ecSpec
            java.math.BigInteger r4 = r4.getOrder()
            java.math.BigInteger r5 = r9.getS()
            int r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getOrderBitLength(r0, r4, r5)
        L_0x00b5:
            org.bouncycastle.asn1.DERBitString r4 = r9.publicKey
            if (r4 == 0) goto L_0x00c5
            org.bouncycastle.asn1.sec.ECPrivateKey r4 = new org.bouncycastle.asn1.sec.ECPrivateKey
            java.math.BigInteger r5 = r9.getS()
            org.bouncycastle.asn1.DERBitString r6 = r9.publicKey
            r4.<init>(r0, r5, r6, r3)
            goto L_0x00ce
        L_0x00c5:
            org.bouncycastle.asn1.sec.ECPrivateKey r4 = new org.bouncycastle.asn1.sec.ECPrivateKey
            java.math.BigInteger r5 = r9.getS()
            r4.<init>((int) r0, (java.math.BigInteger) r5, (org.bouncycastle.asn1.ASN1Encodable) r3)
        L_0x00ce:
            org.bouncycastle.asn1.pkcs.PrivateKeyInfo r0 = new org.bouncycastle.asn1.pkcs.PrivateKeyInfo     // Catch:{ IOException -> 0x00e7 }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r5 = new org.bouncycastle.asn1.x509.AlgorithmIdentifier     // Catch:{ IOException -> 0x00e7 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r6 = org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers.gostR3410_2001     // Catch:{ IOException -> 0x00e7 }
            org.bouncycastle.asn1.ASN1Primitive r3 = r3.toASN1Primitive()     // Catch:{ IOException -> 0x00e7 }
            r5.<init>(r6, r3)     // Catch:{ IOException -> 0x00e7 }
            org.bouncycastle.asn1.ASN1Primitive r3 = r4.toASN1Primitive()     // Catch:{ IOException -> 0x00e7 }
            r0.<init>(r5, r3)     // Catch:{ IOException -> 0x00e7 }
            byte[] r0 = r0.getEncoded(r1)     // Catch:{ IOException -> 0x00e7 }
            return r0
        L_0x00e7:
            r0 = move-exception
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.ecgost.BCECGOST3410PrivateKey.getEncoded():byte[]");
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
        return this.f655d;
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
        return ECUtil.privateKeyToString(this.algorithm, this.f655d, engineGetSpec());
    }
}
