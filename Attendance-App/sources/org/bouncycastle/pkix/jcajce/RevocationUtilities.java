package org.bouncycastle.pkix.jcajce;

import androidx.core.p001os.EnvironmentCompat;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jcajce.PKIXCRLStore;
import org.bouncycastle.jcajce.PKIXCRLStoreSelector;
import org.bouncycastle.jcajce.PKIXCertStore;
import org.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.StoreException;

class RevocationUtilities {
    protected static final String ANY_POLICY = "2.5.29.32.0";
    protected static final String AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();
    protected static final String BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
    protected static final String CERTIFICATE_POLICIES = Extension.certificatePolicies.getId();
    protected static final String CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
    protected static final String CRL_NUMBER = Extension.cRLNumber.getId();
    protected static final int CRL_SIGN = 6;
    protected static final PKIXCRLUtil CRL_UTIL = new PKIXCRLUtil();
    protected static final String DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
    protected static final String FRESHEST_CRL = Extension.freshestCRL.getId();
    protected static final String INHIBIT_ANY_POLICY = Extension.inhibitAnyPolicy.getId();
    protected static final String ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
    protected static final int KEY_CERT_SIGN = 5;
    protected static final String KEY_USAGE = Extension.keyUsage.getId();
    protected static final String NAME_CONSTRAINTS = Extension.nameConstraints.getId();
    protected static final String POLICY_CONSTRAINTS = Extension.policyConstraints.getId();
    protected static final String POLICY_MAPPINGS = Extension.policyMappings.getId();
    protected static final String SUBJECT_ALTERNATIVE_NAME = Extension.subjectAlternativeName.getId();
    protected static final String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", EnvironmentCompat.MEDIA_UNKNOWN, "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    RevocationUtilities() {
    }

    static void checkCRLsNotEmpty(Set set, Object obj) throws CRLNotFoundException {
        if (set.isEmpty()) {
            throw new CRLNotFoundException("No CRLs found for issuer \"" + RFC4519Style.INSTANCE.toString(getIssuer((X509Certificate) obj)) + "\"");
        }
    }

    protected static Collection findCertificates(PKIXCertStoreSelector pKIXCertStoreSelector, List list) throws AnnotatedException {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Object next : list) {
            if (next instanceof Store) {
                try {
                    linkedHashSet.addAll(((Store) next).getMatches(pKIXCertStoreSelector));
                } catch (StoreException e) {
                    throw new AnnotatedException("Problem while picking certificates from X.509 store.", e);
                }
            } else {
                try {
                    linkedHashSet.addAll(PKIXCertStoreSelector.getCertificates(pKIXCertStoreSelector, (CertStore) next));
                } catch (CertStoreException e2) {
                    throw new AnnotatedException("Problem while picking certificates from certificate store.", e2);
                }
            }
        }
        return linkedHashSet;
    }

    static Collection findIssuerCerts(X509Certificate x509Certificate, List<CertStore> list, List<PKIXCertStore> list2) throws AnnotatedException {
        byte[] keyIdentifier;
        X509CertSelector x509CertSelector = new X509CertSelector();
        try {
            x509CertSelector.setSubject(x509Certificate.getIssuerX500Principal().getEncoded());
            try {
                byte[] extensionValue = x509Certificate.getExtensionValue(AUTHORITY_KEY_IDENTIFIER);
                if (!(extensionValue == null || (keyIdentifier = AuthorityKeyIdentifier.getInstance(ASN1OctetString.getInstance(extensionValue).getOctets()).getKeyIdentifier()) == null)) {
                    x509CertSelector.setSubjectKeyIdentifier(new DEROctetString(keyIdentifier).getEncoded());
                }
            } catch (Exception e) {
            }
            PKIXCertStoreSelector<? extends Certificate> build = new PKIXCertStoreSelector.Builder(x509CertSelector).build();
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            try {
                ArrayList<X509Certificate> arrayList = new ArrayList<>();
                arrayList.addAll(findCertificates(build, list));
                arrayList.addAll(findCertificates(build, list2));
                for (X509Certificate add : arrayList) {
                    linkedHashSet.add(add);
                }
                return linkedHashSet;
            } catch (AnnotatedException e2) {
                throw new AnnotatedException("Issuer certificate cannot be searched.", e2);
            }
        } catch (IOException e3) {
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate could not be set.", e3);
        }
    }

    protected static TrustAnchor findTrustAnchor(X509Certificate x509Certificate, Set set) throws AnnotatedException {
        return findTrustAnchor(x509Certificate, set, (String) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0062 A[SYNTHETIC, Splitter:B:23:0x0062] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0018 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.security.cert.TrustAnchor findTrustAnchor(java.security.cert.X509Certificate r7, java.util.Set r8, java.lang.String r9) throws org.bouncycastle.pkix.jcajce.AnnotatedException {
        /*
            java.security.cert.X509CertSelector r0 = new java.security.cert.X509CertSelector
            r0.<init>()
            org.bouncycastle.asn1.x500.X500Name r1 = getIssuer((java.security.cert.X509Certificate) r7)
            byte[] r2 = r1.getEncoded()     // Catch:{ IOException -> 0x0078 }
            r0.setSubject(r2)     // Catch:{ IOException -> 0x0078 }
            java.util.Iterator r8 = r8.iterator()
            r2 = 0
            r3 = r2
            r4 = r3
            r5 = r4
        L_0x0018:
            boolean r6 = r8.hasNext()
            if (r6 == 0) goto L_0x006a
            if (r3 != 0) goto L_0x006a
            java.lang.Object r3 = r8.next()
            java.security.cert.TrustAnchor r3 = (java.security.cert.TrustAnchor) r3
            java.security.cert.X509Certificate r6 = r3.getTrustedCert()
            if (r6 == 0) goto L_0x003f
            java.security.cert.X509Certificate r6 = r3.getTrustedCert()
            boolean r6 = r0.match(r6)
            if (r6 == 0) goto L_0x005f
            java.security.cert.X509Certificate r5 = r3.getTrustedCert()
            java.security.PublicKey r5 = r5.getPublicKey()
            goto L_0x0060
        L_0x003f:
            java.lang.String r6 = r3.getCAName()
            if (r6 == 0) goto L_0x005f
            java.security.PublicKey r6 = r3.getCAPublicKey()
            if (r6 == 0) goto L_0x005f
            javax.security.auth.x500.X500Principal r6 = r3.getCA()     // Catch:{ IllegalArgumentException -> 0x005e }
            org.bouncycastle.asn1.x500.X500Name r6 = getX500Name(r6)     // Catch:{ IllegalArgumentException -> 0x005e }
            boolean r6 = r1.equals(r6)     // Catch:{ IllegalArgumentException -> 0x005e }
            if (r6 == 0) goto L_0x005f
            java.security.PublicKey r5 = r3.getCAPublicKey()     // Catch:{ IllegalArgumentException -> 0x005e }
            goto L_0x0060
        L_0x005e:
            r3 = move-exception
        L_0x005f:
            r3 = r2
        L_0x0060:
            if (r5 == 0) goto L_0x0018
            verifyX509Certificate(r7, r5, r9)     // Catch:{ Exception -> 0x0066 }
            goto L_0x0018
        L_0x0066:
            r4 = move-exception
            r3 = r2
            r5 = r3
            goto L_0x0018
        L_0x006a:
            if (r3 != 0) goto L_0x0077
            if (r4 != 0) goto L_0x006f
            goto L_0x0077
        L_0x006f:
            org.bouncycastle.pkix.jcajce.AnnotatedException r7 = new org.bouncycastle.pkix.jcajce.AnnotatedException
            java.lang.String r8 = "TrustAnchor found but certificate validation failed."
            r7.<init>(r8, r4)
            throw r7
        L_0x0077:
            return r3
        L_0x0078:
            r7 = move-exception
            org.bouncycastle.pkix.jcajce.AnnotatedException r8 = new org.bouncycastle.pkix.jcajce.AnnotatedException
            java.lang.String r9 = "Cannot set subject search criteria for trust anchor."
            r8.<init>(r9, r7)
            goto L_0x0082
        L_0x0081:
            throw r8
        L_0x0082:
            goto L_0x0081
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.pkix.jcajce.RevocationUtilities.findTrustAnchor(java.security.cert.X509Certificate, java.util.Set, java.lang.String):java.security.cert.TrustAnchor");
    }

    static List<PKIXCertStore> getAdditionalStoresFromAltNames(byte[] bArr, Map<GeneralName, PKIXCertStore> map) throws CertificateParsingException {
        if (bArr == null) {
            return Collections.EMPTY_LIST;
        }
        GeneralName[] names = GeneralNames.getInstance(ASN1OctetString.getInstance(bArr).getOctets()).getNames();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i != names.length; i++) {
            PKIXCertStore pKIXCertStore = map.get(names[i]);
            if (pKIXCertStore != null) {
                arrayList.add(pKIXCertStore);
            }
        }
        return arrayList;
    }

    static List<PKIXCRLStore> getAdditionalStoresFromCRLDistributionPoint(CRLDistPoint cRLDistPoint, Map<GeneralName, PKIXCRLStore> map) throws AnnotatedException {
        if (cRLDistPoint == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
            ArrayList arrayList = new ArrayList();
            for (DistributionPoint distributionPoint : distributionPoints) {
                DistributionPointName distributionPoint2 = distributionPoint.getDistributionPoint();
                if (distributionPoint2 != null && distributionPoint2.getType() == 0) {
                    GeneralName[] names = GeneralNames.getInstance(distributionPoint2.getName()).getNames();
                    for (GeneralName generalName : names) {
                        PKIXCRLStore pKIXCRLStore = map.get(generalName);
                        if (pKIXCRLStore != null) {
                            arrayList.add(pKIXCRLStore);
                        }
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            throw new AnnotatedException("Distribution points could not be read.", e);
        }
    }

    protected static AlgorithmIdentifier getAlgorithmIdentifier(PublicKey publicKey) throws CertPathValidatorException {
        try {
            return SubjectPublicKeyInfo.getInstance(new ASN1InputStream(publicKey.getEncoded()).readObject()).getAlgorithm();
        } catch (Exception e) {
            throw new CertPathValidatorException("subject public key cannot be decoded", e);
        }
    }

    protected static void getCRLIssuersFromDistributionPoint(DistributionPoint distributionPoint, Collection collection, X509CRLSelector x509CRLSelector) throws AnnotatedException {
        ArrayList<X500Name> arrayList = new ArrayList<>();
        if (distributionPoint.getCRLIssuer() != null) {
            GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
            for (int i = 0; i < names.length; i++) {
                if (names[i].getTagNo() == 4) {
                    try {
                        arrayList.add(X500Name.getInstance(names[i].getName()));
                    } catch (IllegalArgumentException e) {
                        throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e);
                    }
                }
            }
        } else if (distributionPoint.getDistributionPoint() != null) {
            for (Object add : collection) {
                arrayList.add(add);
            }
        } else {
            throw new AnnotatedException("CRL issuer is omitted from distribution point but no distributionPoint field present.");
        }
        for (X500Name encoded : arrayList) {
            try {
                x509CRLSelector.addIssuerName(encoded.getEncoded());
            } catch (IOException e2) {
                throw new AnnotatedException("Cannot decode CRL issuer information.", e2);
            }
        }
    }

    protected static void getCertStatus(Date date, X509CRL x509crl, Object obj, CertStatus certStatus) throws AnnotatedException {
        X509CRLEntry revokedCertificate;
        try {
            boolean isIndirectCRL = isIndirectCRL(x509crl);
            X509Certificate x509Certificate = (X509Certificate) obj;
            X500Name issuer = getIssuer(x509Certificate);
            if ((isIndirectCRL || issuer.equals(getIssuer(x509crl))) && (revokedCertificate = x509crl.getRevokedCertificate(x509Certificate.getSerialNumber())) != null) {
                if (isIndirectCRL) {
                    X500Principal certificateIssuer = revokedCertificate.getCertificateIssuer();
                    if (!issuer.equals(certificateIssuer == null ? getIssuer(x509crl) : getX500Name(certificateIssuer))) {
                        return;
                    }
                }
                int i = 0;
                if (revokedCertificate.hasExtensions()) {
                    try {
                        ASN1Enumerated instance = ASN1Enumerated.getInstance(getExtensionValue(revokedCertificate, Extension.reasonCode));
                        if (instance != null) {
                            i = instance.intValueExact();
                        }
                    } catch (Exception e) {
                        throw new AnnotatedException("Reason code CRL entry extension could not be decoded.", e);
                    }
                }
                Date revocationDate = revokedCertificate.getRevocationDate();
                if (date.before(revocationDate)) {
                    switch (i) {
                        case 0:
                        case 1:
                        case 2:
                        case 10:
                            break;
                        default:
                            return;
                    }
                }
                certStatus.setCertStatus(i);
                certStatus.setRevocationDate(revocationDate);
            }
        } catch (CRLException e2) {
            throw new AnnotatedException("Failed check for indirect CRL.", e2);
        }
    }

    protected static Set getCompleteCRLs(DistributionPoint distributionPoint, Object obj, Date date, List list, List list2) throws AnnotatedException, CRLNotFoundException {
        X509CRLSelector x509CRLSelector = new X509CRLSelector();
        try {
            HashSet hashSet = new HashSet();
            hashSet.add(getIssuer((X509Certificate) obj));
            getCRLIssuersFromDistributionPoint(distributionPoint, hashSet, x509CRLSelector);
            if (obj instanceof X509Certificate) {
                x509CRLSelector.setCertificateChecking((X509Certificate) obj);
            }
            Set findCRLs = CRL_UTIL.findCRLs(new PKIXCRLStoreSelector.Builder(x509CRLSelector).setCompleteCRLEnabled(true).build(), date, list, list2);
            checkCRLsNotEmpty(findCRLs, obj);
            return findCRLs;
        } catch (AnnotatedException e) {
            throw new AnnotatedException("Could not get issuer information from distribution point.", e);
        }
    }

    protected static Set getDeltaCRLs(Date date, X509CRL x509crl, List<CertStore> list, List<PKIXCRLStore> list2) throws AnnotatedException {
        X509CRLSelector x509CRLSelector = new X509CRLSelector();
        try {
            x509CRLSelector.addIssuerName(x509crl.getIssuerX500Principal().getEncoded());
            try {
                ASN1Primitive extensionValue = getExtensionValue(x509crl, Extension.cRLNumber);
                BigInteger bigInteger = null;
                BigInteger positiveValue = extensionValue != null ? ASN1Integer.getInstance(extensionValue).getPositiveValue() : null;
                try {
                    byte[] extensionValue2 = x509crl.getExtensionValue(ISSUING_DISTRIBUTION_POINT);
                    if (positiveValue != null) {
                        bigInteger = positiveValue.add(BigInteger.valueOf(1));
                    }
                    x509CRLSelector.setMinCRLNumber(bigInteger);
                    PKIXCRLStoreSelector.Builder builder = new PKIXCRLStoreSelector.Builder(x509CRLSelector);
                    builder.setIssuingDistributionPoint(extensionValue2);
                    builder.setIssuingDistributionPointEnabled(true);
                    builder.setMaxBaseCRLNumber(positiveValue);
                    Set<X509CRL> findCRLs = CRL_UTIL.findCRLs(builder.build(), date, list, list2);
                    HashSet hashSet = new HashSet();
                    for (X509CRL x509crl2 : findCRLs) {
                        if (isDeltaCRL(x509crl2)) {
                            hashSet.add(x509crl2);
                        }
                    }
                    return hashSet;
                } catch (Exception e) {
                    throw new AnnotatedException("issuing distribution point extension value could not be read", e);
                }
            } catch (Exception e2) {
                throw new AnnotatedException("cannot extract CRL number extension from CRL", e2);
            }
        } catch (IOException e3) {
            throw new AnnotatedException("cannot extract issuer from CRL.", e3);
        }
    }

    protected static ASN1Primitive getExtensionValue(X509Extension x509Extension, ASN1ObjectIdentifier aSN1ObjectIdentifier) throws AnnotatedException {
        byte[] extensionValue = x509Extension.getExtensionValue(aSN1ObjectIdentifier.getId());
        if (extensionValue == null) {
            return null;
        }
        return getObject(aSN1ObjectIdentifier, extensionValue);
    }

    private static X500Name getIssuer(X509CRL x509crl) {
        return getX500Name(x509crl.getIssuerX500Principal());
    }

    private static X500Name getIssuer(X509Certificate x509Certificate) {
        return getX500Name(x509Certificate.getIssuerX500Principal());
    }

    protected static PublicKey getNextWorkingKey(List list, int i, JcaJceHelper jcaJceHelper) throws CertPathValidatorException {
        DSAPublicKey dSAPublicKey;
        PublicKey publicKey = ((Certificate) list.get(i)).getPublicKey();
        if (!(publicKey instanceof DSAPublicKey)) {
            return publicKey;
        }
        DSAPublicKey dSAPublicKey2 = (DSAPublicKey) publicKey;
        if (dSAPublicKey2.getParams() != null) {
            return dSAPublicKey2;
        }
        do {
            i++;
            if (i < list.size()) {
                PublicKey publicKey2 = ((X509Certificate) list.get(i)).getPublicKey();
                if (publicKey2 instanceof DSAPublicKey) {
                    dSAPublicKey = (DSAPublicKey) publicKey2;
                } else {
                    throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
                }
            } else {
                throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
            }
        } while (dSAPublicKey.getParams() == null);
        DSAParams params = dSAPublicKey.getParams();
        try {
            return jcaJceHelper.createKeyFactory("DSA").generatePublic(new DSAPublicKeySpec(dSAPublicKey2.getY(), params.getP(), params.getQ(), params.getG()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static ASN1Primitive getObject(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) throws AnnotatedException {
        try {
            return ASN1Primitive.fromByteArray(ASN1OctetString.getInstance(bArr).getOctets());
        } catch (Exception e) {
            throw new AnnotatedException("exception processing extension " + aSN1ObjectIdentifier, e);
        }
    }

    protected static Date getValidCertDateFromValidityModel(PKIXExtendedParameters pKIXExtendedParameters, CertPath certPath, int i) throws AnnotatedException {
        if (pKIXExtendedParameters.getValidityModel() != 1) {
            return getValidDate(pKIXExtendedParameters);
        }
        if (i <= 0) {
            return getValidDate(pKIXExtendedParameters);
        }
        int i2 = i - 1;
        if (i2 == 0) {
            try {
                byte[] extensionValue = ((X509Certificate) certPath.getCertificates().get(i2)).getExtensionValue(ISISMTTObjectIdentifiers.id_isismtt_at_dateOfCertGen.getId());
                ASN1GeneralizedTime instance = extensionValue != null ? ASN1GeneralizedTime.getInstance(ASN1Primitive.fromByteArray(extensionValue)) : null;
                if (instance != null) {
                    try {
                        return instance.getDate();
                    } catch (ParseException e) {
                        throw new AnnotatedException("Date from date of cert gen extension could not be parsed.", e);
                    }
                }
            } catch (IOException e2) {
                throw new AnnotatedException("Date of cert gen extension could not be read.");
            } catch (IllegalArgumentException e3) {
                throw new AnnotatedException("Date of cert gen extension could not be read.");
            }
        }
        return ((X509Certificate) certPath.getCertificates().get(i2)).getNotBefore();
    }

    protected static Date getValidDate(PKIXExtendedParameters pKIXExtendedParameters) {
        Date date = pKIXExtendedParameters.getDate();
        return date == null ? new Date() : date;
    }

    private static X500Name getX500Name(X500Principal x500Principal) {
        return X500Name.getInstance(x500Principal.getEncoded());
    }

    private static boolean isDeltaCRL(X509CRL x509crl) {
        Set criticalExtensionOIDs = x509crl.getCriticalExtensionOIDs();
        if (criticalExtensionOIDs == null) {
            return false;
        }
        return criticalExtensionOIDs.contains(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
    }

    public static boolean isIndirectCRL(X509CRL x509crl) throws CRLException {
        try {
            byte[] extensionValue = x509crl.getExtensionValue(Extension.issuingDistributionPoint.getId());
            return extensionValue != null && IssuingDistributionPoint.getInstance(ASN1OctetString.getInstance(extensionValue).getOctets()).isIndirectCRL();
        } catch (Exception e) {
            throw new CRLException("exception reading IssuingDistributionPoint", e);
        }
    }

    static boolean isIssuerTrustAnchor(X509Certificate x509Certificate, Set set, String str) throws AnnotatedException {
        try {
            return findTrustAnchor(x509Certificate, set, str) != null;
        } catch (Exception e) {
            return false;
        }
    }

    protected static boolean isSelfIssued(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectDN().equals(x509Certificate.getIssuerDN());
    }

    protected static void verifyX509Certificate(X509Certificate x509Certificate, PublicKey publicKey, String str) throws GeneralSecurityException {
        if (str == null) {
            x509Certificate.verify(publicKey);
        } else {
            x509Certificate.verify(publicKey, str);
        }
    }
}
