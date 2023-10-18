package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.TargetInformation;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jcajce.PKIXCertStoreSelector;
import org.bouncycastle.jcajce.PKIXExtendedBuilderParameters;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.x509.PKIXAttrCertChecker;
import org.bouncycastle.x509.X509AttributeCertificate;
import org.bouncycastle.x509.X509CertStoreSelector;

class RFC3281CertPathUtilities {
    private static final String AUTHORITY_INFO_ACCESS = Extension.authorityInfoAccess.getId();
    private static final String CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
    private static final String NO_REV_AVAIL = Extension.noRevAvail.getId();
    private static final String TARGET_INFORMATION = Extension.targetInformation.getId();

    RFC3281CertPathUtilities() {
    }

    protected static void additionalChecks(X509AttributeCertificate x509AttributeCertificate, Set set, Set set2) throws CertPathValidatorException {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (x509AttributeCertificate.getAttributes(str) != null) {
                throw new CertPathValidatorException("Attribute certificate contains prohibited attribute: " + str + ".");
            }
        }
        Iterator it2 = set2.iterator();
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            if (x509AttributeCertificate.getAttributes(str2) == null) {
                throw new CertPathValidatorException("Attribute certificate does not contain necessary attribute: " + str2 + ".");
            }
        }
    }

    private static void checkCRL(DistributionPoint distributionPoint, X509AttributeCertificate x509AttributeCertificate, PKIXExtendedParameters pKIXExtendedParameters, Date date, X509Certificate x509Certificate, CertStatus certStatus, ReasonsMask reasonsMask, List list, JcaJceHelper jcaJceHelper) throws AnnotatedException {
        Iterator it;
        DistributionPoint distributionPoint2 = distributionPoint;
        X509AttributeCertificate x509AttributeCertificate2 = x509AttributeCertificate;
        PKIXExtendedParameters pKIXExtendedParameters2 = pKIXExtendedParameters;
        Date date2 = date;
        CertStatus certStatus2 = certStatus;
        ReasonsMask reasonsMask2 = reasonsMask;
        if (x509AttributeCertificate2.getExtensionValue(X509Extensions.NoRevAvail.getId()) == null) {
            Date date3 = new Date(System.currentTimeMillis());
            if (date.getTime() <= date3.getTime()) {
                Iterator it2 = CertPathValidatorUtilities.getCompleteCRLs(distributionPoint2, x509AttributeCertificate2, date3, pKIXExtendedParameters2).iterator();
                e = null;
                boolean z = false;
                while (it2.hasNext() && certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
                    try {
                        X509CRL x509crl = (X509CRL) it2.next();
                        ReasonsMask processCRLD = RFC3280CertPathUtilities.processCRLD(x509crl, distributionPoint2);
                        if (!processCRLD.hasNewReasons(reasonsMask2)) {
                            continue;
                        } else {
                            ReasonsMask reasonsMask3 = processCRLD;
                            it = it2;
                            try {
                                X509CRL x509crl2 = x509crl;
                                X509CRL processCRLH = pKIXExtendedParameters.isUseDeltasEnabled() ? RFC3280CertPathUtilities.processCRLH(CertPathValidatorUtilities.getDeltaCRLs(date3, x509crl2, pKIXExtendedParameters.getCertStores(), pKIXExtendedParameters.getCRLStores()), RFC3280CertPathUtilities.processCRLG(x509crl2, RFC3280CertPathUtilities.processCRLF(x509crl, x509AttributeCertificate, (X509Certificate) null, (PublicKey) null, pKIXExtendedParameters, list, jcaJceHelper))) : null;
                                if (pKIXExtendedParameters.getValidityModel() != 1) {
                                    if (x509AttributeCertificate.getNotAfter().getTime() < x509crl2.getThisUpdate().getTime()) {
                                        throw new AnnotatedException("No valid CRL for current time found.");
                                    }
                                }
                                RFC3280CertPathUtilities.processCRLB1(distributionPoint2, x509AttributeCertificate2, x509crl2);
                                RFC3280CertPathUtilities.processCRLB2(distributionPoint2, x509AttributeCertificate2, x509crl2);
                                RFC3280CertPathUtilities.processCRLC(processCRLH, x509crl2, pKIXExtendedParameters2);
                                RFC3280CertPathUtilities.processCRLI(date2, processCRLH, x509AttributeCertificate2, certStatus2, pKIXExtendedParameters2);
                                RFC3280CertPathUtilities.processCRLJ(date2, x509crl2, x509AttributeCertificate2, certStatus2);
                                if (certStatus.getCertStatus() == 8) {
                                    certStatus2.setCertStatus(11);
                                }
                                reasonsMask2.addReasons(reasonsMask3);
                                it2 = it;
                                z = true;
                            } catch (AnnotatedException e) {
                                e = e;
                                it2 = it;
                            }
                        }
                    } catch (AnnotatedException e2) {
                        e = e2;
                        it = it2;
                        it2 = it;
                    }
                }
                if (!z) {
                    throw e;
                }
                return;
            }
            throw new AnnotatedException("Validation time is in future.");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x011d  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x017f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void checkCRLs(org.bouncycastle.x509.X509AttributeCertificate r18, org.bouncycastle.jcajce.PKIXExtendedParameters r19, java.security.cert.X509Certificate r20, java.util.Date r21, java.util.List r22, org.bouncycastle.jcajce.util.JcaJceHelper r23) throws java.security.cert.CertPathValidatorException {
        /*
            r10 = r18
            boolean r0 = r19.isRevocationEnabled()
            if (r0 == 0) goto L_0x01b2
            java.lang.String r0 = NO_REV_AVAIL
            byte[] r0 = r10.getExtensionValue(r0)
            if (r0 != 0) goto L_0x0199
            java.lang.String r0 = CRL_DISTRIBUTION_POINTS     // Catch:{ AnnotatedException -> 0x0190 }
            org.bouncycastle.asn1.ASN1Primitive r0 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r10, r0)     // Catch:{ AnnotatedException -> 0x0190 }
            org.bouncycastle.asn1.x509.CRLDistPoint r0 = org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(r0)     // Catch:{ AnnotatedException -> 0x0190 }
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.Map r2 = r19.getNamedCRLStoreMap()     // Catch:{ AnnotatedException -> 0x0187 }
            java.util.List r2 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.getAdditionalStoresFromCRLDistributionPoint(r0, r2)     // Catch:{ AnnotatedException -> 0x0187 }
            r1.addAll(r2)     // Catch:{ AnnotatedException -> 0x0187 }
            org.bouncycastle.jcajce.PKIXExtendedParameters$Builder r2 = new org.bouncycastle.jcajce.PKIXExtendedParameters$Builder
            r3 = r19
            r2.<init>((org.bouncycastle.jcajce.PKIXExtendedParameters) r3)
            java.util.Iterator r3 = r1.iterator()
        L_0x0035:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0042
            r4 = r1
            org.bouncycastle.jcajce.PKIXCRLStore r4 = (org.bouncycastle.jcajce.PKIXCRLStore) r4
            r2.addCRLStore(r4)
            goto L_0x0035
        L_0x0042:
            org.bouncycastle.jcajce.PKIXExtendedParameters r11 = r2.build()
            org.bouncycastle.jce.provider.CertStatus r12 = new org.bouncycastle.jce.provider.CertStatus
            r12.<init>()
            org.bouncycastle.jce.provider.ReasonsMask r13 = new org.bouncycastle.jce.provider.ReasonsMask
            r13.<init>()
            java.lang.String r14 = "No valid CRL for distribution point found."
            r9 = 0
            r8 = 11
            r7 = 0
            if (r0 == 0) goto L_0x00ae
            org.bouncycastle.asn1.x509.DistributionPoint[] r0 = r0.getDistributionPoints()     // Catch:{ Exception -> 0x00a4 }
            r6 = 0
            r16 = 0
        L_0x005f:
            int r1 = r0.length     // Catch:{ AnnotatedException -> 0x009a }
            if (r6 >= r1) goto L_0x0096
            int r1 = r12.getCertStatus()     // Catch:{ AnnotatedException -> 0x009a }
            if (r1 != r8) goto L_0x0096
            boolean r1 = r13.isAllReasons()     // Catch:{ AnnotatedException -> 0x009a }
            if (r1 != 0) goto L_0x0096
            java.lang.Object r1 = r11.clone()     // Catch:{ AnnotatedException -> 0x009a }
            r3 = r1
            org.bouncycastle.jcajce.PKIXExtendedParameters r3 = (org.bouncycastle.jcajce.PKIXExtendedParameters) r3     // Catch:{ AnnotatedException -> 0x009a }
            r1 = r0[r6]     // Catch:{ AnnotatedException -> 0x009a }
            r2 = r18
            r4 = r21
            r5 = r20
            r17 = r6
            r6 = r12
            r15 = 0
            r7 = r13
            r15 = 11
            r8 = r22
            r9 = r23
            checkCRL(r1, r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ AnnotatedException -> 0x0094 }
            int r6 = r17 + 1
            r7 = 0
            r8 = 11
            r9 = 0
            r16 = 1
            goto L_0x005f
        L_0x0094:
            r0 = move-exception
            goto L_0x009d
        L_0x0096:
            r15 = 11
            r0 = 0
            goto L_0x00b3
        L_0x009a:
            r0 = move-exception
            r15 = 11
        L_0x009d:
            org.bouncycastle.jce.provider.AnnotatedException r9 = new org.bouncycastle.jce.provider.AnnotatedException
            r9.<init>(r14, r0)
            r0 = r9
            goto L_0x00b3
        L_0x00a4:
            r0 = move-exception
            r1 = r0
            org.bouncycastle.jce.exception.ExtCertPathValidatorException r0 = new org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r2 = "Distribution points could not be read."
            r0.<init>(r2, r1)
            throw r0
        L_0x00ae:
            r15 = 11
            r0 = 0
            r16 = 0
        L_0x00b3:
            int r1 = r12.getCertStatus()
            if (r1 != r15) goto L_0x011b
            boolean r1 = r13.isAllReasons()
            if (r1 != 0) goto L_0x011b
            org.bouncycastle.asn1.ASN1InputStream r1 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ Exception -> 0x010c }
            org.bouncycastle.x509.AttributeCertificateIssuer r2 = r18.getIssuer()     // Catch:{ Exception -> 0x010c }
            java.security.Principal[] r2 = r2.getPrincipals()     // Catch:{ Exception -> 0x010c }
            r3 = 0
            r2 = r2[r3]     // Catch:{ Exception -> 0x010c }
            javax.security.auth.x500.X500Principal r2 = (javax.security.auth.x500.X500Principal) r2     // Catch:{ Exception -> 0x010c }
            byte[] r2 = r2.getEncoded()     // Catch:{ Exception -> 0x010c }
            r1.<init>((byte[]) r2)     // Catch:{ Exception -> 0x010c }
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.readObject()     // Catch:{ Exception -> 0x010c }
            org.bouncycastle.asn1.x509.DistributionPoint r2 = new org.bouncycastle.asn1.x509.DistributionPoint     // Catch:{ AnnotatedException -> 0x010a }
            org.bouncycastle.asn1.x509.DistributionPointName r3 = new org.bouncycastle.asn1.x509.DistributionPointName     // Catch:{ AnnotatedException -> 0x010a }
            org.bouncycastle.asn1.x509.GeneralNames r4 = new org.bouncycastle.asn1.x509.GeneralNames     // Catch:{ AnnotatedException -> 0x010a }
            org.bouncycastle.asn1.x509.GeneralName r5 = new org.bouncycastle.asn1.x509.GeneralName     // Catch:{ AnnotatedException -> 0x010a }
            r6 = 4
            r5.<init>((int) r6, (org.bouncycastle.asn1.ASN1Encodable) r1)     // Catch:{ AnnotatedException -> 0x010a }
            r4.<init>((org.bouncycastle.asn1.x509.GeneralName) r5)     // Catch:{ AnnotatedException -> 0x010a }
            r1 = 0
            r3.<init>(r1, r4)     // Catch:{ AnnotatedException -> 0x010a }
            r1 = 0
            r2.<init>(r3, r1, r1)     // Catch:{ AnnotatedException -> 0x010a }
            java.lang.Object r1 = r11.clone()     // Catch:{ AnnotatedException -> 0x010a }
            r3 = r1
            org.bouncycastle.jcajce.PKIXExtendedParameters r3 = (org.bouncycastle.jcajce.PKIXExtendedParameters) r3     // Catch:{ AnnotatedException -> 0x010a }
            r1 = r2
            r2 = r18
            r4 = r21
            r5 = r20
            r6 = r12
            r7 = r13
            r8 = r22
            r9 = r23
            checkCRL(r1, r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ AnnotatedException -> 0x010a }
            r16 = 1
            goto L_0x011b
        L_0x010a:
            r0 = move-exception
            goto L_0x0115
        L_0x010c:
            r0 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r1 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch:{ AnnotatedException -> 0x010a }
            java.lang.String r2 = "Issuer from certificate for CRL could not be reencoded."
            r1.<init>(r2, r0)     // Catch:{ AnnotatedException -> 0x010a }
            throw r1     // Catch:{ AnnotatedException -> 0x010a }
        L_0x0115:
            org.bouncycastle.jce.provider.AnnotatedException r1 = new org.bouncycastle.jce.provider.AnnotatedException
            r1.<init>(r14, r0)
            r0 = r1
        L_0x011b:
            if (r16 == 0) goto L_0x017f
            int r0 = r12.getCertStatus()
            if (r0 != r15) goto L_0x0143
            boolean r0 = r13.isAllReasons()
            r1 = 12
            if (r0 != 0) goto L_0x0134
            int r0 = r12.getCertStatus()
            if (r0 != r15) goto L_0x0134
            r12.setCertStatus(r1)
        L_0x0134:
            int r0 = r12.getCertStatus()
            if (r0 == r1) goto L_0x013b
            goto L_0x01b2
        L_0x013b:
            java.security.cert.CertPathValidatorException r0 = new java.security.cert.CertPathValidatorException
            java.lang.String r1 = "Attribute certificate status could not be determined."
            r0.<init>(r1)
            throw r0
        L_0x0143:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Attribute certificate revocation after "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.util.Date r1 = r12.getRevocationDate()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = ", reason: "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String[] r1 = org.bouncycastle.jce.provider.RFC3280CertPathUtilities.crlReasons
            int r2 = r12.getCertStatus()
            r1 = r1[r2]
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.security.cert.CertPathValidatorException r1 = new java.security.cert.CertPathValidatorException
            r1.<init>(r0)
            throw r1
        L_0x017f:
            org.bouncycastle.jce.exception.ExtCertPathValidatorException r1 = new org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r2 = "No valid CRL found."
            r1.<init>(r2, r0)
            throw r1
        L_0x0187:
            r0 = move-exception
            java.security.cert.CertPathValidatorException r1 = new java.security.cert.CertPathValidatorException
            java.lang.String r2 = "No additional CRL locations could be decoded from CRL distribution point extension."
            r1.<init>(r2, r0)
            throw r1
        L_0x0190:
            r0 = move-exception
            java.security.cert.CertPathValidatorException r1 = new java.security.cert.CertPathValidatorException
            java.lang.String r2 = "CRL distribution point extension could not be read."
            r1.<init>(r2, r0)
            throw r1
        L_0x0199:
            java.lang.String r0 = CRL_DISTRIBUTION_POINTS
            byte[] r0 = r10.getExtensionValue(r0)
            if (r0 != 0) goto L_0x01aa
            java.lang.String r0 = AUTHORITY_INFO_ACCESS
            byte[] r0 = r10.getExtensionValue(r0)
            if (r0 != 0) goto L_0x01aa
            goto L_0x01b2
        L_0x01aa:
            java.security.cert.CertPathValidatorException r0 = new java.security.cert.CertPathValidatorException
            java.lang.String r1 = "No rev avail extension is set, but also an AC revocation pointer."
            r0.<init>(r1)
            throw r0
        L_0x01b2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.RFC3281CertPathUtilities.checkCRLs(org.bouncycastle.x509.X509AttributeCertificate, org.bouncycastle.jcajce.PKIXExtendedParameters, java.security.cert.X509Certificate, java.util.Date, java.util.List, org.bouncycastle.jcajce.util.JcaJceHelper):void");
    }

    protected static CertPath processAttrCert1(X509AttributeCertificate x509AttributeCertificate, PKIXExtendedParameters pKIXExtendedParameters) throws CertPathValidatorException {
        HashSet<X509Certificate> hashSet = new HashSet<>();
        int i = 0;
        if (x509AttributeCertificate.getHolder().getIssuer() != null) {
            X509CertSelector x509CertSelector = new X509CertSelector();
            x509CertSelector.setSerialNumber(x509AttributeCertificate.getHolder().getSerialNumber());
            Principal[] issuer = x509AttributeCertificate.getHolder().getIssuer();
            int i2 = 0;
            while (i2 < issuer.length) {
                try {
                    Principal principal = issuer[i2];
                    if (principal instanceof X500Principal) {
                        x509CertSelector.setIssuer(((X500Principal) principal).getEncoded());
                    }
                    hashSet.addAll(CertPathValidatorUtilities.findCertificates(new PKIXCertStoreSelector.Builder(x509CertSelector).build(), pKIXExtendedParameters.getCertStores()));
                    i2++;
                } catch (AnnotatedException e) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e);
                } catch (IOException e2) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e2);
                }
            }
            if (hashSet.isEmpty()) {
                throw new CertPathValidatorException("Public key certificate specified in base certificate ID for attribute certificate cannot be found.");
            }
        }
        if (x509AttributeCertificate.getHolder().getEntityNames() != null) {
            X509CertStoreSelector x509CertStoreSelector = new X509CertStoreSelector();
            Principal[] entityNames = x509AttributeCertificate.getHolder().getEntityNames();
            while (i < entityNames.length) {
                try {
                    Principal principal2 = entityNames[i];
                    if (principal2 instanceof X500Principal) {
                        x509CertStoreSelector.setIssuer(((X500Principal) principal2).getEncoded());
                    }
                    hashSet.addAll(CertPathValidatorUtilities.findCertificates(new PKIXCertStoreSelector.Builder(x509CertStoreSelector).build(), pKIXExtendedParameters.getCertStores()));
                    i++;
                } catch (AnnotatedException e3) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e3);
                } catch (IOException e4) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e4);
                }
            }
            if (hashSet.isEmpty()) {
                throw new CertPathValidatorException("Public key certificate specified in entity name for attribute certificate cannot be found.");
            }
        }
        PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder(pKIXExtendedParameters);
        ExtCertPathValidatorException extCertPathValidatorException = null;
        CertPathBuilderResult certPathBuilderResult = null;
        for (X509Certificate certificate : hashSet) {
            X509CertStoreSelector x509CertStoreSelector2 = new X509CertStoreSelector();
            x509CertStoreSelector2.setCertificate(certificate);
            builder.setTargetConstraints(new PKIXCertStoreSelector.Builder(x509CertStoreSelector2).build());
            try {
                try {
                    certPathBuilderResult = CertPathBuilder.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).build(new PKIXExtendedBuilderParameters.Builder(builder.build()).build());
                } catch (CertPathBuilderException e5) {
                    extCertPathValidatorException = new ExtCertPathValidatorException("Certification path for public key certificate of attribute certificate could not be build.", e5);
                } catch (InvalidAlgorithmParameterException e6) {
                    throw new RuntimeException(e6.getMessage());
                }
            } catch (NoSuchProviderException e7) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e7);
            } catch (NoSuchAlgorithmException e8) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e8);
            }
        }
        if (extCertPathValidatorException == null) {
            return certPathBuilderResult.getCertPath();
        }
        throw extCertPathValidatorException;
    }

    protected static CertPathValidatorResult processAttrCert2(CertPath certPath, PKIXExtendedParameters pKIXExtendedParameters) throws CertPathValidatorException {
        try {
            try {
                return CertPathValidator.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).validate(certPath, pKIXExtendedParameters);
            } catch (CertPathValidatorException e) {
                throw new ExtCertPathValidatorException("Certification path for issuer certificate of attribute certificate could not be validated.", e);
            } catch (InvalidAlgorithmParameterException e2) {
                throw new RuntimeException(e2.getMessage());
            }
        } catch (NoSuchProviderException e3) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e3);
        } catch (NoSuchAlgorithmException e4) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e4);
        }
    }

    protected static void processAttrCert3(X509Certificate x509Certificate, PKIXExtendedParameters pKIXExtendedParameters) throws CertPathValidatorException {
        if (x509Certificate.getKeyUsage() != null && !x509Certificate.getKeyUsage()[0] && !x509Certificate.getKeyUsage()[1]) {
            throw new CertPathValidatorException("Attribute certificate issuer public key cannot be used to validate digital signatures.");
        } else if (x509Certificate.getBasicConstraints() != -1) {
            throw new CertPathValidatorException("Attribute certificate issuer is also a public key certificate issuer.");
        }
    }

    protected static void processAttrCert4(X509Certificate x509Certificate, Set set) throws CertPathValidatorException {
        Iterator it = set.iterator();
        boolean z = false;
        while (it.hasNext()) {
            TrustAnchor trustAnchor = (TrustAnchor) it.next();
            if (x509Certificate.getSubjectX500Principal().getName("RFC2253").equals(trustAnchor.getCAName()) || x509Certificate.equals(trustAnchor.getTrustedCert())) {
                z = true;
            }
        }
        if (!z) {
            throw new CertPathValidatorException("Attribute certificate issuer is not directly trusted.");
        }
    }

    protected static void processAttrCert5(X509AttributeCertificate x509AttributeCertificate, PKIXExtendedParameters pKIXExtendedParameters) throws CertPathValidatorException {
        try {
            x509AttributeCertificate.checkValidity(CertPathValidatorUtilities.getValidDate(pKIXExtendedParameters));
        } catch (CertificateExpiredException e) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e);
        } catch (CertificateNotYetValidException e2) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e2);
        }
    }

    protected static void processAttrCert7(X509AttributeCertificate x509AttributeCertificate, CertPath certPath, CertPath certPath2, PKIXExtendedParameters pKIXExtendedParameters, Set set) throws CertPathValidatorException {
        Set criticalExtensionOIDs = x509AttributeCertificate.getCriticalExtensionOIDs();
        String str = TARGET_INFORMATION;
        if (criticalExtensionOIDs.contains(str)) {
            try {
                TargetInformation.getInstance(CertPathValidatorUtilities.getExtensionValue(x509AttributeCertificate, str));
            } catch (AnnotatedException e) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e);
            } catch (IllegalArgumentException e2) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e2);
            }
        }
        criticalExtensionOIDs.remove(str);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((PKIXAttrCertChecker) it.next()).check(x509AttributeCertificate, certPath, certPath2, criticalExtensionOIDs);
        }
        if (!criticalExtensionOIDs.isEmpty()) {
            throw new CertPathValidatorException("Attribute certificate contains unsupported critical extensions: " + criticalExtensionOIDs);
        }
    }
}
