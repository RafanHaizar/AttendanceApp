package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPrivateKeySpec;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.p008x9.X962Parameters;
import org.bouncycastle.asn1.p008x9.X9ECParameters;
import org.bouncycastle.asn1.p008x9.X9ECPoint;
import org.bouncycastle.asn1.p008x9.X9ObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.ECPrivateKeyStructure;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.bouncycastle.jce.interfaces.ECPointEncoder;
import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.util.Strings;

public class JCEECPrivateKey implements ECPrivateKey, org.bouncycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder {
    private String algorithm = "EC";
    private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();

    /* renamed from: d */
    private BigInteger f683d;
    private ECParameterSpec ecSpec;
    private DERBitString publicKey;
    private boolean withCompression;

    protected JCEECPrivateKey() {
    }

    public JCEECPrivateKey(String str, ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = str;
        this.f683d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
    }

    public JCEECPrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters) {
        this.algorithm = str;
        this.f683d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
    }

    public JCEECPrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, JCEECPublicKey jCEECPublicKey, ECParameterSpec eCParameterSpec) {
        this.algorithm = str;
        this.f683d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
            eCParameterSpec = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        }
        this.ecSpec = eCParameterSpec;
        this.publicKey = getPublicKeyDetails(jCEECPublicKey);
    }

    public JCEECPrivateKey(String str, ECPrivateKeyParameters eCPrivateKeyParameters, JCEECPublicKey jCEECPublicKey, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        this.algorithm = str;
        this.f683d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), EC5Util.convertPoint(parameters.getG()), parameters.getN(), parameters.getH().intValue());
        } else {
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), EC5Util.convertPoint(eCParameterSpec.getG()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
        }
        this.publicKey = getPublicKeyDetails(jCEECPublicKey);
    }

    public JCEECPrivateKey(String str, JCEECPrivateKey jCEECPrivateKey) {
        this.algorithm = str;
        this.f683d = jCEECPrivateKey.f683d;
        this.ecSpec = jCEECPrivateKey.ecSpec;
        this.withCompression = jCEECPrivateKey.withCompression;
        this.attrCarrier = jCEECPrivateKey.attrCarrier;
        this.publicKey = jCEECPrivateKey.publicKey;
    }

    public JCEECPrivateKey(String str, org.bouncycastle.jce.spec.ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = str;
        this.f683d = eCPrivateKeySpec.getD();
        this.ecSpec = eCPrivateKeySpec.getParams() != null ? EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams()) : null;
    }

    public JCEECPrivateKey(ECPrivateKey eCPrivateKey) {
        this.f683d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
    }

    JCEECPrivateKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        populateFromPrivKeyInfo(privateKeyInfo);
    }

    private DERBitString getPublicKeyDetails(JCEECPublicKey jCEECPublicKey) {
        try {
            return SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(jCEECPublicKey.getEncoded())).getPublicKeyData();
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.security.spec.ECParameterSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x00be  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo r11) throws java.io.IOException {
        /*
            r10 = this;
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r0 = r11.getPrivateKeyAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getParameters()
            org.bouncycastle.asn1.x9.X962Parameters r0 = org.bouncycastle.asn1.p008x9.X962Parameters.getInstance(r0)
            boolean r1 = r0.isNamedCurve()
            if (r1 == 0) goto L_0x0072
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.getParameters()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r0)
            org.bouncycastle.asn1.x9.X9ECParameters r1 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(r0)
            if (r1 != 0) goto L_0x004b
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
            goto L_0x00a9
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
            goto L_0x00a9
        L_0x0072:
            boolean r1 = r0.isImplicitlyCA()
            if (r1 == 0) goto L_0x007c
            r0 = 0
            r10.ecSpec = r0
            goto L_0x00ab
        L_0x007c:
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
        L_0x00a9:
            r10.ecSpec = r2
        L_0x00ab:
            org.bouncycastle.asn1.ASN1Encodable r11 = r11.parsePrivateKey()
            boolean r0 = r11 instanceof org.bouncycastle.asn1.ASN1Integer
            if (r0 == 0) goto L_0x00be
            org.bouncycastle.asn1.ASN1Integer r11 = org.bouncycastle.asn1.ASN1Integer.getInstance(r11)
            java.math.BigInteger r11 = r11.getValue()
            r10.f683d = r11
            goto L_0x00d1
        L_0x00be:
            org.bouncycastle.asn1.sec.ECPrivateKeyStructure r0 = new org.bouncycastle.asn1.sec.ECPrivateKeyStructure
            org.bouncycastle.asn1.ASN1Sequence r11 = (org.bouncycastle.asn1.ASN1Sequence) r11
            r0.<init>((org.bouncycastle.asn1.ASN1Sequence) r11)
            java.math.BigInteger r11 = r0.getKey()
            r10.f683d = r11
            org.bouncycastle.asn1.DERBitString r11 = r0.getPublicKey()
            r10.publicKey = r11
        L_0x00d1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.JCEECPrivateKey.populateFromPrivKeyInfo(org.bouncycastle.asn1.pkcs.PrivateKeyInfo):void");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
        this.algorithm = (String) objectInputStream.readObject();
        this.withCompression = objectInputStream.readBoolean();
        PKCS12BagAttributeCarrierImpl pKCS12BagAttributeCarrierImpl = new PKCS12BagAttributeCarrierImpl();
        this.attrCarrier = pKCS12BagAttributeCarrierImpl;
        pKCS12BagAttributeCarrierImpl.readObject(objectInputStream);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(getEncoded());
        objectOutputStream.writeObject(this.algorithm);
        objectOutputStream.writeBoolean(this.withCompression);
        this.attrCarrier.writeObject(objectOutputStream);
    }

    /* access modifiers changed from: package-private */
    public org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        return eCParameterSpec != null ? EC5Util.convertSpec(eCParameterSpec) : BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof JCEECPrivateKey)) {
            return false;
        }
        JCEECPrivateKey jCEECPrivateKey = (JCEECPrivateKey) obj;
        return getD().equals(jCEECPrivateKey.getD()) && engineGetSpec().equals(jCEECPrivateKey.engineGetSpec());
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
        return this.f683d;
    }

    public byte[] getEncoded() {
        X962Parameters x962Parameters;
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec instanceof ECNamedCurveSpec) {
            ASN1ObjectIdentifier namedCurveOid = ECUtil.getNamedCurveOid(((ECNamedCurveSpec) eCParameterSpec).getName());
            if (namedCurveOid == null) {
                namedCurveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.ecSpec).getName());
            }
            x962Parameters = new X962Parameters(namedCurveOid);
        } else if (eCParameterSpec == null) {
            x962Parameters = new X962Parameters((ASN1Null) DERNull.INSTANCE);
        } else {
            ECCurve convertCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
            x962Parameters = new X962Parameters(new X9ECParameters(convertCurve, new X9ECPoint(EC5Util.convertPoint(convertCurve, this.ecSpec.getGenerator()), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long) this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
        }
        ECPrivateKeyStructure eCPrivateKeyStructure = this.publicKey != null ? new ECPrivateKeyStructure(getS(), this.publicKey, x962Parameters) : new ECPrivateKeyStructure(getS(), x962Parameters);
        try {
            return (this.algorithm.equals("ECGOST3410") ? new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, x962Parameters.toASN1Primitive()), eCPrivateKeyStructure.toASN1Primitive()) : new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, x962Parameters.toASN1Primitive()), eCPrivateKeyStructure.toASN1Primitive())).getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            return null;
        }
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
        return this.f683d;
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
        StringBuffer stringBuffer = new StringBuffer();
        String lineSeparator = Strings.lineSeparator();
        stringBuffer.append("EC Private Key").append(lineSeparator);
        stringBuffer.append("             S: ").append(this.f683d.toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }
}
