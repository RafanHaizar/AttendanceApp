package org.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.interfaces.BCX509Certificate;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.x509.ExtendedPKIXParameters;

public class PKIXCertPathValidatorSpi extends CertPathValidatorSpi {
    private final JcaJceHelper helper;
    private final boolean isForCRLCheck;

    public PKIXCertPathValidatorSpi() {
        this(false);
    }

    public PKIXCertPathValidatorSpi(boolean z) {
        this.helper = new BCJcaJceHelper();
        this.isForCRLCheck = z;
    }

    static void checkCertificate(X509Certificate x509Certificate) throws AnnotatedException {
        if (x509Certificate instanceof BCX509Certificate) {
            try {
                if (((BCX509Certificate) x509Certificate).getTBSCertificateNative() == null) {
                    e = null;
                    throw new AnnotatedException("unable to process TBSCertificate", e);
                }
            } catch (RuntimeException e) {
                e = e;
            }
        } else {
            try {
                TBSCertificate.getInstance(x509Certificate.getTBSCertificate());
            } catch (CertificateEncodingException e2) {
                throw new AnnotatedException("unable to process TBSCertificate", e2);
            } catch (IllegalArgumentException e3) {
                throw new AnnotatedException(e3.getMessage());
            }
        }
    }

    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters certPathParameters) throws CertPathValidatorException, InvalidAlgorithmParameterException {
        PKIXExtendedParameters pKIXExtendedParameters;
        List<? extends Certificate> list;
        PublicKey publicKey;
        X500Name x500Name;
        HashSet hashSet;
        List list2;
        ArrayList[] arrayListArr;
        HashSet hashSet2;
        CertPath certPath2 = certPath;
        CertPathParameters certPathParameters2 = certPathParameters;
        if (certPathParameters2 instanceof PKIXParameters) {
            PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder((PKIXParameters) certPathParameters2);
            if (certPathParameters2 instanceof ExtendedPKIXParameters) {
                ExtendedPKIXParameters extendedPKIXParameters = (ExtendedPKIXParameters) certPathParameters2;
                builder.setUseDeltasEnabled(extendedPKIXParameters.isUseDeltasEnabled());
                builder.setValidityModel(extendedPKIXParameters.getValidityModel());
            }
            pKIXExtendedParameters = builder.build();
        } else if (certPathParameters2 instanceof PKIXExtendedBuilderParameters) {
            pKIXExtendedParameters = ((PKIXExtendedBuilderParameters) certPathParameters2).getBaseParameters();
        } else if (certPathParameters2 instanceof PKIXExtendedParameters) {
            pKIXExtendedParameters = (PKIXExtendedParameters) certPathParameters2;
        } else {
            throw new InvalidAlgorithmParameterException("Parameters must be a " + PKIXParameters.class.getName() + " instance.");
        }
        if (pKIXExtendedParameters.getTrustAnchors() != null) {
            List<? extends Certificate> certificates = certPath.getCertificates();
            int size = certificates.size();
            if (!certificates.isEmpty()) {
                Set initialPolicies = pKIXExtendedParameters.getInitialPolicies();
                try {
                    TrustAnchor findTrustAnchor = CertPathValidatorUtilities.findTrustAnchor((X509Certificate) certificates.get(certificates.size() - 1), pKIXExtendedParameters.getTrustAnchors(), pKIXExtendedParameters.getSigProvider());
                    if (findTrustAnchor != null) {
                        checkCertificate(findTrustAnchor.getTrustedCert());
                        PKIXExtendedParameters build = new PKIXExtendedParameters.Builder(pKIXExtendedParameters).setTrustAnchor(findTrustAnchor).build();
                        int i = size + 1;
                        ArrayList[] arrayListArr2 = new ArrayList[i];
                        for (int i2 = 0; i2 < i; i2++) {
                            arrayListArr2[i2] = new ArrayList();
                        }
                        HashSet hashSet3 = new HashSet();
                        hashSet3.add(RFC3280CertPathUtilities.ANY_POLICY);
                        PKIXPolicyNode pKIXPolicyNode = new PKIXPolicyNode(new ArrayList(), 0, hashSet3, (PolicyNode) null, new HashSet(), RFC3280CertPathUtilities.ANY_POLICY, false);
                        arrayListArr2[0].add(pKIXPolicyNode);
                        PKIXNameConstraintValidator pKIXNameConstraintValidator = new PKIXNameConstraintValidator();
                        HashSet hashSet4 = new HashSet();
                        int i3 = build.isExplicitPolicyRequired() ? 0 : i;
                        int i4 = build.isAnyPolicyInhibited() ? 0 : i;
                        if (build.isPolicyMappingInhibited()) {
                            i = 0;
                        }
                        X509Certificate trustedCert = findTrustAnchor.getTrustedCert();
                        if (trustedCert != null) {
                            try {
                                x500Name = PrincipalUtils.getSubjectPrincipal(trustedCert);
                                publicKey = trustedCert.getPublicKey();
                            } catch (RuntimeException e) {
                                throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", e, certPath2, -1);
                            }
                        } else {
                            x500Name = PrincipalUtils.getCA(findTrustAnchor);
                            publicKey = findTrustAnchor.getCAPublicKey();
                        }
                        try {
                            AlgorithmIdentifier algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey);
                            algorithmIdentifier.getAlgorithm();
                            algorithmIdentifier.getParameters();
                            if (build.getTargetConstraints() == null || build.getTargetConstraints().match((Certificate) (X509Certificate) certificates.get(0))) {
                                List<PKIXCertPathChecker> certPathCheckers = build.getCertPathCheckers();
                                for (PKIXCertPathChecker init : certPathCheckers) {
                                    init.init(false);
                                }
                                int i5 = size;
                                X509Certificate x509Certificate = null;
                                int i6 = i;
                                int i7 = i4;
                                PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
                                int i8 = i3;
                                int size2 = certificates.size() - 1;
                                int i9 = i8;
                                while (size2 >= 0) {
                                    int i10 = size - size2;
                                    Set set = initialPolicies;
                                    X509Certificate x509Certificate2 = (X509Certificate) certificates.get(size2);
                                    boolean z = size2 == certificates.size() + -1;
                                    try {
                                        checkCertificate(x509Certificate2);
                                        int i11 = i7;
                                        List<? extends Certificate> list3 = certificates;
                                        int i12 = i9;
                                        PKIXExtendedParameters pKIXExtendedParameters2 = build;
                                        PKIXExtendedParameters pKIXExtendedParameters3 = build;
                                        int i13 = i6;
                                        List list4 = certPathCheckers;
                                        PKIXNameConstraintValidator pKIXNameConstraintValidator2 = pKIXNameConstraintValidator;
                                        ArrayList[] arrayListArr3 = arrayListArr2;
                                        TrustAnchor trustAnchor = findTrustAnchor;
                                        RFC3280CertPathUtilities.processCertA(certPath, pKIXExtendedParameters2, size2, publicKey, z, x500Name, trustedCert, this.helper);
                                        int i14 = size2;
                                        RFC3280CertPathUtilities.processCertBC(certPath2, i14, pKIXNameConstraintValidator2, this.isForCRLCheck);
                                        PKIXPolicyNode processCertE = RFC3280CertPathUtilities.processCertE(certPath2, i14, RFC3280CertPathUtilities.processCertD(certPath, i14, hashSet4, pKIXPolicyNode2, arrayListArr3, i11, this.isForCRLCheck));
                                        RFC3280CertPathUtilities.processCertF(certPath2, i14, processCertE, i12);
                                        if (i10 != size) {
                                            if (x509Certificate2 == null || x509Certificate2.getVersion() != 1) {
                                                RFC3280CertPathUtilities.prepareNextCertA(certPath2, i14);
                                                arrayListArr = arrayListArr3;
                                                PKIXPolicyNode prepareCertB = RFC3280CertPathUtilities.prepareCertB(certPath2, i14, arrayListArr, processCertE, i13);
                                                RFC3280CertPathUtilities.prepareNextCertG(certPath2, i14, pKIXNameConstraintValidator2);
                                                int prepareNextCertH1 = RFC3280CertPathUtilities.prepareNextCertH1(certPath2, i14, i12);
                                                int prepareNextCertH2 = RFC3280CertPathUtilities.prepareNextCertH2(certPath2, i14, i13);
                                                int prepareNextCertH3 = RFC3280CertPathUtilities.prepareNextCertH3(certPath2, i14, i11);
                                                i9 = RFC3280CertPathUtilities.prepareNextCertI1(certPath2, i14, prepareNextCertH1);
                                                i13 = RFC3280CertPathUtilities.prepareNextCertI2(certPath2, i14, prepareNextCertH2);
                                                int prepareNextCertJ = RFC3280CertPathUtilities.prepareNextCertJ(certPath2, i14, prepareNextCertH3);
                                                RFC3280CertPathUtilities.prepareNextCertK(certPath2, i14);
                                                i5 = RFC3280CertPathUtilities.prepareNextCertM(certPath2, i14, RFC3280CertPathUtilities.prepareNextCertL(certPath2, i14, i5));
                                                RFC3280CertPathUtilities.prepareNextCertN(certPath2, i14);
                                                Set criticalExtensionOIDs = x509Certificate2.getCriticalExtensionOIDs();
                                                if (criticalExtensionOIDs != null) {
                                                    hashSet2.remove(RFC3280CertPathUtilities.KEY_USAGE);
                                                    hashSet2.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                                                    hashSet2.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                                                    hashSet2.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                                                    hashSet2.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                                                    hashSet2.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                                                    hashSet2.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                                                    hashSet2.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                                                    hashSet2.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                                                    hashSet2.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                                                } else {
                                                    hashSet2 = new HashSet();
                                                }
                                                list2 = list4;
                                                RFC3280CertPathUtilities.prepareNextCertO(certPath2, i14, hashSet2, list2);
                                                X500Name subjectPrincipal = PrincipalUtils.getSubjectPrincipal(x509Certificate2);
                                                try {
                                                    PublicKey nextWorkingKey = CertPathValidatorUtilities.getNextWorkingKey(certPath.getCertificates(), i14, this.helper);
                                                    AlgorithmIdentifier algorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier(nextWorkingKey);
                                                    algorithmIdentifier2.getAlgorithm();
                                                    algorithmIdentifier2.getParameters();
                                                    pKIXPolicyNode2 = prepareCertB;
                                                    i7 = prepareNextCertJ;
                                                    x500Name = subjectPrincipal;
                                                    publicKey = nextWorkingKey;
                                                    trustedCert = x509Certificate2;
                                                    i6 = i13;
                                                    size2 = i14 - 1;
                                                    x509Certificate = x509Certificate2;
                                                    initialPolicies = set;
                                                    certificates = list3;
                                                    build = pKIXExtendedParameters3;
                                                    findTrustAnchor = trustAnchor;
                                                    List list5 = list2;
                                                    arrayListArr2 = arrayListArr;
                                                    pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                                                    certPathCheckers = list5;
                                                } catch (CertPathValidatorException e2) {
                                                    throw new CertPathValidatorException("Next working key could not be retrieved.", e2, certPath2, i14);
                                                }
                                            } else if (i10 != 1 || !x509Certificate2.equals(trustAnchor.getTrustedCert())) {
                                                throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", (Throwable) null, certPath2, i14);
                                            }
                                        }
                                        list2 = list4;
                                        arrayListArr = arrayListArr3;
                                        pKIXPolicyNode2 = processCertE;
                                        i7 = i11;
                                        i5 = i5;
                                        i9 = i12;
                                        i6 = i13;
                                        size2 = i14 - 1;
                                        x509Certificate = x509Certificate2;
                                        initialPolicies = set;
                                        certificates = list3;
                                        build = pKIXExtendedParameters3;
                                        findTrustAnchor = trustAnchor;
                                        List list52 = list2;
                                        arrayListArr2 = arrayListArr;
                                        pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                                        certPathCheckers = list52;
                                    } catch (AnnotatedException e3) {
                                        AnnotatedException annotatedException = e3;
                                        throw new CertPathValidatorException(annotatedException.getMessage(), annotatedException.getUnderlyingException(), certPath2, size2);
                                    }
                                }
                                PKIXExtendedParameters pKIXExtendedParameters4 = build;
                                ArrayList[] arrayListArr4 = arrayListArr2;
                                TrustAnchor trustAnchor2 = findTrustAnchor;
                                Set set2 = initialPolicies;
                                List list6 = certPathCheckers;
                                X509Certificate x509Certificate3 = x509Certificate;
                                int i15 = size2;
                                int i16 = i15 + 1;
                                int wrapupCertB = RFC3280CertPathUtilities.wrapupCertB(certPath2, i16, RFC3280CertPathUtilities.wrapupCertA(i9, x509Certificate3));
                                Set criticalExtensionOIDs2 = x509Certificate3.getCriticalExtensionOIDs();
                                if (criticalExtensionOIDs2 != null) {
                                    hashSet.remove(RFC3280CertPathUtilities.KEY_USAGE);
                                    hashSet.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                                    hashSet.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                                    hashSet.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                                    hashSet.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                                    hashSet.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                                    hashSet.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                                    hashSet.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                                    hashSet.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                                    hashSet.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                                    hashSet.remove(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
                                    hashSet.remove(Extension.extendedKeyUsage.getId());
                                } else {
                                    hashSet = new HashSet();
                                }
                                RFC3280CertPathUtilities.wrapupCertF(certPath2, i16, list6, hashSet);
                                PKIXPolicyNode wrapupCertG = RFC3280CertPathUtilities.wrapupCertG(certPath, pKIXExtendedParameters4, set2, i16, arrayListArr4, pKIXPolicyNode2, hashSet4);
                                if (wrapupCertB > 0 || wrapupCertG != null) {
                                    return new PKIXCertPathValidatorResult(trustAnchor2, wrapupCertG, x509Certificate3.getPublicKey());
                                }
                                throw new CertPathValidatorException("Path processing failed on policy.", (Throwable) null, certPath2, i15);
                            }
                            throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", (Throwable) null, certPath2, 0);
                        } catch (CertPathValidatorException e4) {
                            throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", e4, certPath2, -1);
                        }
                    } else {
                        list = certificates;
                        try {
                            throw new CertPathValidatorException("Trust anchor for certification path not found.", (Throwable) null, certPath2, -1);
                        } catch (AnnotatedException e5) {
                            e = e5;
                            throw new CertPathValidatorException(e.getMessage(), e.getUnderlyingException(), certPath2, list.size() - 1);
                        }
                    }
                } catch (AnnotatedException e6) {
                    e = e6;
                    list = certificates;
                    throw new CertPathValidatorException(e.getMessage(), e.getUnderlyingException(), certPath2, list.size() - 1);
                }
            } else {
                throw new CertPathValidatorException("Certification path is empty.", (Throwable) null, certPath2, -1);
            }
        } else {
            throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
        }
    }
}
