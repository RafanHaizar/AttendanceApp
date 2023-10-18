package org.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.p007ua.DSTU4145Params;
import org.bouncycastle.asn1.p007ua.DSTU4145PointEncoder;
import org.bouncycastle.asn1.p007ua.UAObjectIdentifiers;
import org.bouncycastle.asn1.p008x9.X962Parameters;
import org.bouncycastle.asn1.p008x9.X9ECParameters;
import org.bouncycastle.asn1.p008x9.X9ECPoint;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.interfaces.ECPointEncoder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.math.p018ec.ECPoint;

public class BCDSTU4145PublicKey implements ECPublicKey, org.bouncycastle.jce.interfaces.ECPublicKey, ECPointEncoder {
    static final long serialVersionUID = 7026240464295649314L;
    private String algorithm = "DSTU4145";
    private transient DSTU4145Params dstuParams;
    private transient ECPublicKeyParameters ecPublicKey;
    private transient ECParameterSpec ecSpec;
    private boolean withCompression;

    public BCDSTU4145PublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters) {
        this.algorithm = str;
        this.ecPublicKey = eCPublicKeyParameters;
        this.ecSpec = null;
    }

    public BCDSTU4145PublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters, ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.algorithm = str;
        this.ecPublicKey = eCPublicKeyParameters;
        if (eCParameterSpec == null) {
            this.ecSpec = createSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters);
        } else {
            this.ecSpec = eCParameterSpec;
        }
    }

    public BCDSTU4145PublicKey(String str, ECPublicKeyParameters eCPublicKeyParameters, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        this.algorithm = str;
        this.ecSpec = eCParameterSpec == null ? createSpec(EC5Util.convertCurve(parameters.getCurve(), parameters.getSeed()), parameters) : EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        this.ecPublicKey = eCPublicKeyParameters;
    }

    public BCDSTU4145PublicKey(ECPublicKeySpec eCPublicKeySpec) {
        ECParameterSpec params = eCPublicKeySpec.getParams();
        this.ecSpec = params;
        this.ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(params, eCPublicKeySpec.getW()), EC5Util.getDomainParameters((ProviderConfiguration) null, this.ecSpec));
    }

    BCDSTU4145PublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        populateFromPubKeyInfo(subjectPublicKeyInfo);
    }

    public BCDSTU4145PublicKey(BCDSTU4145PublicKey bCDSTU4145PublicKey) {
        this.ecPublicKey = bCDSTU4145PublicKey.ecPublicKey;
        this.ecSpec = bCDSTU4145PublicKey.ecSpec;
        this.withCompression = bCDSTU4145PublicKey.withCompression;
        this.dstuParams = bCDSTU4145PublicKey.dstuParams;
    }

    public BCDSTU4145PublicKey(org.bouncycastle.jce.spec.ECPublicKeySpec eCPublicKeySpec, ProviderConfiguration providerConfiguration) {
        if (eCPublicKeySpec.getParams() != null) {
            EllipticCurve convertCurve = EC5Util.convertCurve(eCPublicKeySpec.getParams().getCurve(), eCPublicKeySpec.getParams().getSeed());
            this.ecPublicKey = new ECPublicKeyParameters(eCPublicKeySpec.getQ(), ECUtil.getDomainParameters(providerConfiguration, eCPublicKeySpec.getParams()));
            this.ecSpec = EC5Util.convertSpec(convertCurve, eCPublicKeySpec.getParams());
            return;
        }
        ECParameterSpec eCParameterSpec = null;
        this.ecPublicKey = new ECPublicKeyParameters(providerConfiguration.getEcImplicitlyCa().getCurve().createPoint(eCPublicKeySpec.getQ().getAffineXCoord().toBigInteger(), eCPublicKeySpec.getQ().getAffineYCoord().toBigInteger()), EC5Util.getDomainParameters(providerConfiguration, (ECParameterSpec) null));
        this.ecSpec = null;
    }

    private ECParameterSpec createSpec(EllipticCurve ellipticCurve, ECDomainParameters eCDomainParameters) {
        return new ECParameterSpec(ellipticCurve, EC5Util.convertPoint(eCDomainParameters.getG()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v4, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v6, resolved type: java.security.spec.ECParameterSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v10, resolved type: java.security.spec.ECParameterSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v22, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v23, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: org.bouncycastle.jce.spec.ECNamedCurveSpec} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void populateFromPubKeyInfo(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo r14) {
        /*
            r13 = this;
            org.bouncycastle.asn1.DERBitString r0 = r14.getPublicKeyData()
            java.lang.String r1 = "DSTU4145"
            r13.algorithm = r1
            byte[] r0 = r0.getBytes()     // Catch:{ IOException -> 0x0160 }
            org.bouncycastle.asn1.ASN1Primitive r0 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r0)     // Catch:{ IOException -> 0x0160 }
            org.bouncycastle.asn1.ASN1OctetString r0 = (org.bouncycastle.asn1.ASN1OctetString) r0     // Catch:{ IOException -> 0x0160 }
            byte[] r0 = r0.getOctets()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r14.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r1.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = org.bouncycastle.asn1.p007ua.UAObjectIdentifiers.dstu4145le
            boolean r1 = r1.equals((org.bouncycastle.asn1.ASN1Primitive) r2)
            if (r1 == 0) goto L_0x0029
            r13.reverseBytes(r0)
        L_0x0029:
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r14.getAlgorithm()
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getParameters()
            org.bouncycastle.asn1.ASN1Sequence r1 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r1)
            r2 = 0
            org.bouncycastle.asn1.ASN1Encodable r2 = r1.getObjectAt(r2)
            boolean r2 = r2 instanceof org.bouncycastle.asn1.ASN1Integer
            r3 = 0
            if (r2 == 0) goto L_0x005f
            org.bouncycastle.asn1.x9.X9ECParameters r14 = org.bouncycastle.asn1.p008x9.X9ECParameters.getInstance(r1)
            org.bouncycastle.jce.spec.ECParameterSpec r1 = new org.bouncycastle.jce.spec.ECParameterSpec
            org.bouncycastle.math.ec.ECCurve r5 = r14.getCurve()
            org.bouncycastle.math.ec.ECPoint r6 = r14.getG()
            java.math.BigInteger r7 = r14.getN()
            java.math.BigInteger r8 = r14.getH()
            byte[] r9 = r14.getSeed()
            r4 = r1
            r4.<init>(r5, r6, r7, r8, r9)
            goto L_0x00fd
        L_0x005f:
            org.bouncycastle.asn1.ua.DSTU4145Params r1 = org.bouncycastle.asn1.p007ua.DSTU4145Params.getInstance(r1)
            r13.dstuParams = r1
            boolean r1 = r1.isNamedCurve()
            if (r1 == 0) goto L_0x0096
            org.bouncycastle.asn1.ua.DSTU4145Params r14 = r13.dstuParams
            org.bouncycastle.asn1.ASN1ObjectIdentifier r14 = r14.getNamedCurve()
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.asn1.p007ua.DSTU4145NamedCurves.getByOID(r14)
            org.bouncycastle.jce.spec.ECNamedCurveParameterSpec r2 = new org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
            java.lang.String r5 = r14.getId()
            org.bouncycastle.math.ec.ECCurve r6 = r1.getCurve()
            org.bouncycastle.math.ec.ECPoint r7 = r1.getG()
            java.math.BigInteger r8 = r1.getN()
            java.math.BigInteger r9 = r1.getH()
            byte[] r10 = r1.getSeed()
            r4 = r2
            r4.<init>(r5, r6, r7, r8, r9, r10)
            r1 = r2
        L_0x0094:
            r14 = r3
            goto L_0x00fd
        L_0x0096:
            org.bouncycastle.asn1.ua.DSTU4145Params r1 = r13.dstuParams
            org.bouncycastle.asn1.ua.DSTU4145ECBinary r1 = r1.getECBinary()
            byte[] r2 = r1.getB()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r4 = r14.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = r4.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r5 = org.bouncycastle.asn1.p007ua.UAObjectIdentifiers.dstu4145le
            boolean r4 = r4.equals((org.bouncycastle.asn1.ASN1Primitive) r5)
            if (r4 == 0) goto L_0x00b3
            r13.reverseBytes(r2)
        L_0x00b3:
            org.bouncycastle.asn1.ua.DSTU4145BinaryField r4 = r1.getField()
            org.bouncycastle.math.ec.ECCurve$F2m r12 = new org.bouncycastle.math.ec.ECCurve$F2m
            int r6 = r4.getM()
            int r7 = r4.getK1()
            int r8 = r4.getK2()
            int r9 = r4.getK3()
            java.math.BigInteger r10 = r1.getA()
            java.math.BigInteger r11 = new java.math.BigInteger
            r4 = 1
            r11.<init>(r4, r2)
            r5 = r12
            r5.<init>((int) r6, (int) r7, (int) r8, (int) r9, (java.math.BigInteger) r10, (java.math.BigInteger) r11)
            byte[] r2 = r1.getG()
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r14 = r14.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r14 = r14.getAlgorithm()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = org.bouncycastle.asn1.p007ua.UAObjectIdentifiers.dstu4145le
            boolean r14 = r14.equals((org.bouncycastle.asn1.ASN1Primitive) r4)
            if (r14 == 0) goto L_0x00ee
            r13.reverseBytes(r2)
        L_0x00ee:
            org.bouncycastle.jce.spec.ECParameterSpec r14 = new org.bouncycastle.jce.spec.ECParameterSpec
            org.bouncycastle.math.ec.ECPoint r2 = org.bouncycastle.asn1.p007ua.DSTU4145PointEncoder.decodePoint(r12, r2)
            java.math.BigInteger r1 = r1.getN()
            r14.<init>(r12, r2, r1)
            r1 = r14
            goto L_0x0094
        L_0x00fd:
            org.bouncycastle.math.ec.ECCurve r2 = r1.getCurve()
            byte[] r4 = r1.getSeed()
            java.security.spec.EllipticCurve r7 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertCurve(r2, r4)
            org.bouncycastle.asn1.ua.DSTU4145Params r4 = r13.dstuParams
            if (r4 == 0) goto L_0x0148
            org.bouncycastle.math.ec.ECPoint r14 = r1.getG()
            java.security.spec.ECPoint r8 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(r14)
            org.bouncycastle.asn1.ua.DSTU4145Params r14 = r13.dstuParams
            boolean r14 = r14.isNamedCurve()
            if (r14 == 0) goto L_0x0136
            org.bouncycastle.asn1.ua.DSTU4145Params r14 = r13.dstuParams
            org.bouncycastle.asn1.ASN1ObjectIdentifier r14 = r14.getNamedCurve()
            java.lang.String r6 = r14.getId()
            org.bouncycastle.jce.spec.ECNamedCurveSpec r14 = new org.bouncycastle.jce.spec.ECNamedCurveSpec
            java.math.BigInteger r9 = r1.getN()
            java.math.BigInteger r10 = r1.getH()
            r5 = r14
            r5.<init>((java.lang.String) r6, (java.security.spec.EllipticCurve) r7, (java.security.spec.ECPoint) r8, (java.math.BigInteger) r9, (java.math.BigInteger) r10)
            goto L_0x014c
        L_0x0136:
            java.security.spec.ECParameterSpec r14 = new java.security.spec.ECParameterSpec
            java.math.BigInteger r4 = r1.getN()
            java.math.BigInteger r1 = r1.getH()
            int r1 = r1.intValue()
            r14.<init>(r7, r8, r4, r1)
            goto L_0x014c
        L_0x0148:
            java.security.spec.ECParameterSpec r14 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertToSpec((org.bouncycastle.asn1.p008x9.X9ECParameters) r14)
        L_0x014c:
            r13.ecSpec = r14
            org.bouncycastle.crypto.params.ECPublicKeyParameters r14 = new org.bouncycastle.crypto.params.ECPublicKeyParameters
            org.bouncycastle.math.ec.ECPoint r0 = org.bouncycastle.asn1.p007ua.DSTU4145PointEncoder.decodePoint(r2, r0)
            java.security.spec.ECParameterSpec r1 = r13.ecSpec
            org.bouncycastle.crypto.params.ECDomainParameters r1 = org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.getDomainParameters(r3, r1)
            r14.<init>(r0, r1)
            r13.ecPublicKey = r14
            return
        L_0x0160:
            r14 = move-exception
            java.lang.IllegalArgumentException r14 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "error recovering public key"
            r14.<init>(r0)
            goto L_0x016a
        L_0x0169:
            throw r14
        L_0x016a:
            goto L_0x0169
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.dstu.BCDSTU4145PublicKey.populateFromPubKeyInfo(org.bouncycastle.asn1.x509.SubjectPublicKeyInfo):void");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[]) objectInputStream.readObject())));
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
    public ECPublicKeyParameters engineGetKeyParameters() {
        return this.ecPublicKey;
    }

    /* access modifiers changed from: package-private */
    public org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        return eCParameterSpec != null ? EC5Util.convertSpec(eCParameterSpec) : BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BCDSTU4145PublicKey)) {
            return false;
        }
        BCDSTU4145PublicKey bCDSTU4145PublicKey = (BCDSTU4145PublicKey) obj;
        return this.ecPublicKey.getQ().equals(bCDSTU4145PublicKey.ecPublicKey.getQ()) && engineGetSpec().equals(bCDSTU4145PublicKey.engineGetSpec());
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public byte[] getEncoded() {
        ASN1Encodable aSN1Encodable = this.dstuParams;
        if (aSN1Encodable == null) {
            ECParameterSpec eCParameterSpec = this.ecSpec;
            if (eCParameterSpec instanceof ECNamedCurveSpec) {
                aSN1Encodable = new DSTU4145Params(new ASN1ObjectIdentifier(((ECNamedCurveSpec) this.ecSpec).getName()));
            } else {
                ECCurve convertCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
                aSN1Encodable = new X962Parameters(new X9ECParameters(convertCurve, new X9ECPoint(EC5Util.convertPoint(convertCurve, this.ecSpec.getGenerator()), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf((long) this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
            }
        }
        try {
            return KeyUtil.getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(new AlgorithmIdentifier(UAObjectIdentifiers.dstu4145be, aSN1Encodable), (ASN1Encodable) new DEROctetString(DSTU4145PointEncoder.encodePoint(this.ecPublicKey.getQ()))));
        } catch (IOException e) {
            return null;
        }
    }

    public String getFormat() {
        return "X.509";
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

    public ECPoint getQ() {
        ECPoint q = this.ecPublicKey.getQ();
        return this.ecSpec == null ? q.getDetachedPoint() : q;
    }

    public byte[] getSbox() {
        DSTU4145Params dSTU4145Params = this.dstuParams;
        return dSTU4145Params != null ? dSTU4145Params.getDKE() : DSTU4145Params.getDefaultDKE();
    }

    public java.security.spec.ECPoint getW() {
        return EC5Util.convertPoint(this.ecPublicKey.getQ());
    }

    public int hashCode() {
        return this.ecPublicKey.getQ().hashCode() ^ engineGetSpec().hashCode();
    }

    public void setPointFormat(String str) {
        this.withCompression = !"UNCOMPRESSED".equalsIgnoreCase(str);
    }

    public String toString() {
        return ECUtil.publicKeyToString(this.algorithm, this.ecPublicKey.getQ(), engineGetSpec());
    }
}
