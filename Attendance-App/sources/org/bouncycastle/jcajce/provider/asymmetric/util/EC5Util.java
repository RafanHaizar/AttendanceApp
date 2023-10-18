package org.bouncycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.p008x9.ECNamedCurveTable;
import org.bouncycastle.asn1.p008x9.X962Parameters;
import org.bouncycastle.asn1.p008x9.X9ECParameters;
import org.bouncycastle.crypto.p012ec.CustomNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.field.FiniteField;
import org.bouncycastle.math.field.Polynomial;
import org.bouncycastle.math.field.PolynomialExtensionField;
import org.bouncycastle.math.p018ec.ECAlgorithms;
import org.bouncycastle.math.p018ec.ECCurve;
import org.bouncycastle.util.Arrays;

public class EC5Util {
    private static Map customCurves = new HashMap();

    static {
        Enumeration names = CustomNamedCurves.getNames();
        while (names.hasMoreElements()) {
            String str = (String) names.nextElement();
            X9ECParameters byName = ECNamedCurveTable.getByName(str);
            if (byName != null) {
                customCurves.put(byName.getCurve(), CustomNamedCurves.getByName(str).getCurve());
            }
        }
        ECCurve curve = CustomNamedCurves.getByName("Curve25519").getCurve();
        customCurves.put(new ECCurve.C0346Fp(curve.getField().getCharacteristic(), curve.getA().toBigInteger(), curve.getB().toBigInteger(), curve.getOrder(), curve.getCofactor()), curve);
    }

    public static EllipticCurve convertCurve(ECCurve eCCurve, byte[] bArr) {
        return new EllipticCurve(convertField(eCCurve.getField()), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), (byte[]) null);
    }

    public static ECCurve convertCurve(EllipticCurve ellipticCurve) {
        ECField field = ellipticCurve.getField();
        BigInteger a = ellipticCurve.getA();
        BigInteger b = ellipticCurve.getB();
        if (field instanceof ECFieldFp) {
            ECCurve.C0346Fp fp = new ECCurve.C0346Fp(((ECFieldFp) field).getP(), a, b);
            return customCurves.containsKey(fp) ? (ECCurve) customCurves.get(fp) : fp;
        }
        ECFieldF2m eCFieldF2m = (ECFieldF2m) field;
        int m = eCFieldF2m.getM();
        int[] convertMidTerms = ECUtil.convertMidTerms(eCFieldF2m.getMidTermsOfReductionPolynomial());
        return new ECCurve.F2m(m, convertMidTerms[0], convertMidTerms[1], convertMidTerms[2], a, b);
    }

    public static ECField convertField(FiniteField finiteField) {
        if (ECAlgorithms.isFpField(finiteField)) {
            return new ECFieldFp(finiteField.getCharacteristic());
        }
        Polynomial minimalPolynomial = ((PolynomialExtensionField) finiteField).getMinimalPolynomial();
        int[] exponentsPresent = minimalPolynomial.getExponentsPresent();
        return new ECFieldF2m(minimalPolynomial.getDegree(), Arrays.reverse(Arrays.copyOfRange(exponentsPresent, 1, exponentsPresent.length - 1)));
    }

    public static ECPoint convertPoint(org.bouncycastle.math.p018ec.ECPoint eCPoint) {
        org.bouncycastle.math.p018ec.ECPoint normalize = eCPoint.normalize();
        return new ECPoint(normalize.getAffineXCoord().toBigInteger(), normalize.getAffineYCoord().toBigInteger());
    }

    public static org.bouncycastle.math.p018ec.ECPoint convertPoint(ECParameterSpec eCParameterSpec, ECPoint eCPoint) {
        return convertPoint(convertCurve(eCParameterSpec.getCurve()), eCPoint);
    }

    public static org.bouncycastle.math.p018ec.ECPoint convertPoint(ECCurve eCCurve, ECPoint eCPoint) {
        return eCCurve.createPoint(eCPoint.getAffineX(), eCPoint.getAffineY());
    }

    public static ECParameterSpec convertSpec(EllipticCurve ellipticCurve, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        ECPoint convertPoint = convertPoint(eCParameterSpec.getG());
        if (!(eCParameterSpec instanceof ECNamedCurveParameterSpec)) {
            return new ECParameterSpec(ellipticCurve, convertPoint, eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
        }
        return new ECNamedCurveSpec(((ECNamedCurveParameterSpec) eCParameterSpec).getName(), ellipticCurve, convertPoint, eCParameterSpec.getN(), eCParameterSpec.getH());
    }

    public static org.bouncycastle.jce.spec.ECParameterSpec convertSpec(ECParameterSpec eCParameterSpec) {
        ECCurve convertCurve = convertCurve(eCParameterSpec.getCurve());
        org.bouncycastle.math.p018ec.ECPoint convertPoint = convertPoint(convertCurve, eCParameterSpec.getGenerator());
        BigInteger order = eCParameterSpec.getOrder();
        BigInteger valueOf = BigInteger.valueOf((long) eCParameterSpec.getCofactor());
        byte[] seed = eCParameterSpec.getCurve().getSeed();
        return eCParameterSpec instanceof ECNamedCurveSpec ? new ECNamedCurveParameterSpec(((ECNamedCurveSpec) eCParameterSpec).getName(), convertCurve, convertPoint, order, valueOf, seed) : new org.bouncycastle.jce.spec.ECParameterSpec(convertCurve, convertPoint, order, valueOf, seed);
    }

    public static ECParameterSpec convertToSpec(X962Parameters x962Parameters, ECCurve eCCurve) {
        if (x962Parameters.isNamedCurve()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) x962Parameters.getParameters();
            X9ECParameters namedCurveByOid = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
            if (namedCurveByOid == null) {
                Map additionalECParameters = BouncyCastleProvider.CONFIGURATION.getAdditionalECParameters();
                if (!additionalECParameters.isEmpty()) {
                    namedCurveByOid = (X9ECParameters) additionalECParameters.get(aSN1ObjectIdentifier);
                }
            }
            return new ECNamedCurveSpec(ECUtil.getCurveName(aSN1ObjectIdentifier), convertCurve(eCCurve, namedCurveByOid.getSeed()), convertPoint(namedCurveByOid.getG()), namedCurveByOid.getN(), namedCurveByOid.getH());
        } else if (x962Parameters.isImplicitlyCA()) {
            return null;
        } else {
            X9ECParameters instance = X9ECParameters.getInstance(x962Parameters.getParameters());
            EllipticCurve convertCurve = convertCurve(eCCurve, instance.getSeed());
            return instance.getH() != null ? new ECParameterSpec(convertCurve, convertPoint(instance.getG()), instance.getN(), instance.getH().intValue()) : new ECParameterSpec(convertCurve, convertPoint(instance.getG()), instance.getN(), 1);
        }
    }

    public static ECParameterSpec convertToSpec(X9ECParameters x9ECParameters) {
        return new ECParameterSpec(convertCurve(x9ECParameters.getCurve(), (byte[]) null), convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH().intValue());
    }

    public static ECParameterSpec convertToSpec(ECDomainParameters eCDomainParameters) {
        return new ECParameterSpec(convertCurve(eCDomainParameters.getCurve(), (byte[]) null), convertPoint(eCDomainParameters.getG()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: org.bouncycastle.asn1.x9.X9ECParameters} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.bouncycastle.math.p018ec.ECCurve getCurve(org.bouncycastle.jcajce.provider.config.ProviderConfiguration r2, org.bouncycastle.asn1.p008x9.X962Parameters r3) {
        /*
            java.util.Set r0 = r2.getAcceptableNamedCurves()
            boolean r1 = r3.isNamedCurve()
            if (r1 == 0) goto L_0x003d
            org.bouncycastle.asn1.ASN1Primitive r3 = r3.getParameters()
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(r3)
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x0027
            boolean r0 = r0.contains(r3)
            if (r0 == 0) goto L_0x001f
            goto L_0x0027
        L_0x001f:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "named curve not acceptable"
            r2.<init>(r3)
            throw r2
        L_0x0027:
            org.bouncycastle.asn1.x9.X9ECParameters r0 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.getNamedCurveByOid(r3)
            if (r0 != 0) goto L_0x0038
            java.util.Map r2 = r2.getAdditionalECParameters()
            java.lang.Object r2 = r2.get(r3)
            r0 = r2
            org.bouncycastle.asn1.x9.X9ECParameters r0 = (org.bouncycastle.asn1.p008x9.X9ECParameters) r0
        L_0x0038:
            org.bouncycastle.math.ec.ECCurve r2 = r0.getCurve()
            goto L_0x005e
        L_0x003d:
            boolean r1 = r3.isImplicitlyCA()
            if (r1 == 0) goto L_0x004c
            org.bouncycastle.jce.spec.ECParameterSpec r2 = r2.getEcImplicitlyCa()
            org.bouncycastle.math.ec.ECCurve r2 = r2.getCurve()
            goto L_0x005e
        L_0x004c:
            boolean r2 = r0.isEmpty()
            if (r2 == 0) goto L_0x005f
            org.bouncycastle.asn1.ASN1Primitive r2 = r3.getParameters()
            org.bouncycastle.asn1.x9.X9ECParameters r2 = org.bouncycastle.asn1.p008x9.X9ECParameters.getInstance(r2)
            org.bouncycastle.math.ec.ECCurve r2 = r2.getCurve()
        L_0x005e:
            return r2
        L_0x005f:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "encoded parameters not acceptable"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.getCurve(org.bouncycastle.jcajce.provider.config.ProviderConfiguration, org.bouncycastle.asn1.x9.X962Parameters):org.bouncycastle.math.ec.ECCurve");
    }

    public static ECDomainParameters getDomainParameters(ProviderConfiguration providerConfiguration, ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec != null) {
            return ECUtil.getDomainParameters(providerConfiguration, convertSpec(eCParameterSpec));
        }
        org.bouncycastle.jce.spec.ECParameterSpec ecImplicitlyCa = providerConfiguration.getEcImplicitlyCa();
        return new ECDomainParameters(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
    }
}
