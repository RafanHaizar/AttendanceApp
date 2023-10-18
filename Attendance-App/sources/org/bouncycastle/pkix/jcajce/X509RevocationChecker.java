package org.bouncycastle.pkix.jcajce;

import androidx.core.p001os.EnvironmentCompat;
import java.lang.ref.WeakReference;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.jcajce.PKIXCRLStore;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Iterable;
import org.bouncycastle.util.Selector;
import org.bouncycastle.util.Store;

public class X509RevocationChecker extends PKIXCertPathChecker {
    public static final int CHAIN_VALIDITY_MODEL = 1;
    private static Logger LOG = Logger.getLogger(X509RevocationChecker.class.getName());
    public static final int PKIX_VALIDITY_MODEL = 0;
    private static final Map<GeneralName, WeakReference<X509CRL>> crlCache = Collections.synchronizedMap(new WeakHashMap());
    protected static final String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", EnvironmentCompat.MEDIA_UNKNOWN, "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
    private final boolean canSoftFail;
    private final List<CertStore> crlCertStores;
    private final List<Store<CRL>> crls;
    private final long failHardMaxTime;
    private final long failLogMaxTime;
    private final Map<X500Principal, Long> failures;
    private final JcaJceHelper helper;
    private final boolean isCheckEEOnly;
    private X509Certificate signingCert;
    private final Set<TrustAnchor> trustAnchors;
    private X500Principal workingIssuerName;
    private PublicKey workingPublicKey;

    public static class Builder {
        /* access modifiers changed from: private */
        public boolean canSoftFail;
        /* access modifiers changed from: private */
        public List<CertStore> crlCertStores;
        /* access modifiers changed from: private */
        public List<Store<CRL>> crls;
        /* access modifiers changed from: private */
        public long failHardMaxTime;
        /* access modifiers changed from: private */
        public long failLogMaxTime;
        /* access modifiers changed from: private */
        public boolean isCheckEEOnly;
        /* access modifiers changed from: private */
        public Provider provider;
        /* access modifiers changed from: private */
        public String providerName;
        /* access modifiers changed from: private */
        public Set<TrustAnchor> trustAnchors;
        private int validityModel;

        public Builder(KeyStore keyStore) throws KeyStoreException {
            this.crlCertStores = new ArrayList();
            this.crls = new ArrayList();
            this.validityModel = 0;
            this.trustAnchors = new HashSet();
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String nextElement = aliases.nextElement();
                if (keyStore.isCertificateEntry(nextElement)) {
                    this.trustAnchors.add(new TrustAnchor((X509Certificate) keyStore.getCertificate(nextElement), (byte[]) null));
                }
            }
        }

        public Builder(TrustAnchor trustAnchor) {
            this.crlCertStores = new ArrayList();
            this.crls = new ArrayList();
            this.validityModel = 0;
            this.trustAnchors = Collections.singleton(trustAnchor);
        }

        public Builder(Set<TrustAnchor> set) {
            this.crlCertStores = new ArrayList();
            this.crls = new ArrayList();
            this.validityModel = 0;
            this.trustAnchors = new HashSet(set);
        }

        public Builder addCrls(CertStore certStore) {
            this.crlCertStores.add(certStore);
            return this;
        }

        public Builder addCrls(Store<CRL> store) {
            this.crls.add(store);
            return this;
        }

        public X509RevocationChecker build() {
            return new X509RevocationChecker(this);
        }

        public Builder setCheckEndEntityOnly(boolean z) {
            this.isCheckEEOnly = z;
            return this;
        }

        public Builder setSoftFail(boolean z, long j) {
            this.canSoftFail = z;
            this.failLogMaxTime = j;
            this.failHardMaxTime = -1;
            return this;
        }

        public Builder setSoftFailHardLimit(boolean z, long j) {
            this.canSoftFail = z;
            this.failLogMaxTime = (3 * j) / 4;
            this.failHardMaxTime = j;
            return this;
        }

        public Builder usingProvider(String str) {
            this.providerName = str;
            return this;
        }

        public Builder usingProvider(Provider provider2) {
            this.provider = provider2;
            return this;
        }
    }

    private class LocalCRLStore<T extends CRL> implements PKIXCRLStore, Iterable<CRL> {
        private Collection<CRL> _local;

        public LocalCRLStore(Store<CRL> store) {
            this._local = new ArrayList(store.getMatches((Selector<CRL>) null));
        }

        public Collection getMatches(Selector selector) {
            if (selector == null) {
                return new ArrayList(this._local);
            }
            ArrayList arrayList = new ArrayList();
            for (CRL next : this._local) {
                if (selector.match(next)) {
                    arrayList.add(next);
                }
            }
            return arrayList;
        }

        public Iterator<CRL> iterator() {
            return getMatches((Selector) null).iterator();
        }
    }

    private X509RevocationChecker(Builder builder) {
        JcaJceHelper namedJcaJceHelper;
        this.failures = new HashMap();
        this.crls = new ArrayList(builder.crls);
        this.crlCertStores = new ArrayList(builder.crlCertStores);
        this.isCheckEEOnly = builder.isCheckEEOnly;
        this.trustAnchors = builder.trustAnchors;
        this.canSoftFail = builder.canSoftFail;
        this.failLogMaxTime = builder.failLogMaxTime;
        this.failHardMaxTime = builder.failHardMaxTime;
        if (builder.provider != null) {
            namedJcaJceHelper = new ProviderJcaJceHelper(builder.provider);
        } else if (builder.providerName != null) {
            namedJcaJceHelper = new NamedJcaJceHelper(builder.providerName);
        } else {
            this.helper = new DefaultJcaJceHelper();
            return;
        }
        this.helper = namedJcaJceHelper;
    }

    private void addIssuers(final List<X500Principal> list, CertStore certStore) throws CertStoreException {
        certStore.getCRLs(new X509CRLSelector() {
            public boolean match(CRL crl) {
                if (!(crl instanceof X509CRL)) {
                    return false;
                }
                list.add(((X509CRL) crl).getIssuerX500Principal());
                return false;
            }
        });
    }

    private void addIssuers(final List<X500Principal> list, Store<CRL> store) {
        store.getMatches(new Selector<CRL>() {
            public Object clone() {
                return this;
            }

            public boolean match(CRL crl) {
                if (!(crl instanceof X509CRL)) {
                    return false;
                }
                list.add(((X509CRL) crl).getIssuerX500Principal());
                return false;
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ff  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.security.cert.CRL downloadCRLs(javax.security.auth.x500.X500Principal r17, java.util.Date r18, org.bouncycastle.asn1.ASN1Primitive r19, org.bouncycastle.jcajce.util.JcaJceHelper r20) {
        /*
            r16 = this;
            r1 = r18
            org.bouncycastle.asn1.x509.CRLDistPoint r0 = org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(r19)
            org.bouncycastle.asn1.x509.DistributionPoint[] r2 = r0.getDistributionPoints()
            r4 = 0
        L_0x000b:
            int r0 = r2.length
            r5 = 0
            if (r4 == r0) goto L_0x0134
            r0 = r2[r4]
            org.bouncycastle.asn1.x509.DistributionPointName r0 = r0.getDistributionPoint()
            int r6 = r0.getType()
            if (r6 != 0) goto L_0x012c
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.getName()
            org.bouncycastle.asn1.x509.GeneralNames r0 = org.bouncycastle.asn1.x509.GeneralNames.getInstance(r0)
            org.bouncycastle.asn1.x509.GeneralName[] r6 = r0.getNames()
            r7 = 0
        L_0x0028:
            int r0 = r6.length
            if (r7 == r0) goto L_0x012c
            r0 = r6[r7]
            int r8 = r0.getTagNo()
            r9 = 6
            if (r8 != r9) goto L_0x0124
            java.util.Map<org.bouncycastle.asn1.x509.GeneralName, java.lang.ref.WeakReference<java.security.cert.X509CRL>> r8 = crlCache
            java.lang.Object r9 = r8.get(r0)
            java.lang.ref.WeakReference r9 = (java.lang.ref.WeakReference) r9
            if (r9 == 0) goto L_0x005e
            java.lang.Object r9 = r9.get()
            java.security.cert.X509CRL r9 = (java.security.cert.X509CRL) r9
            if (r9 == 0) goto L_0x005b
            java.util.Date r10 = r9.getThisUpdate()
            boolean r10 = r1.before(r10)
            if (r10 != 0) goto L_0x005b
            java.util.Date r10 = r9.getNextUpdate()
            boolean r10 = r1.after(r10)
            if (r10 != 0) goto L_0x005b
            return r9
        L_0x005b:
            r8.remove(r0)
        L_0x005e:
            java.net.URL r9 = new java.net.URL     // Catch:{ Exception -> 0x00c6 }
            org.bouncycastle.asn1.ASN1Encodable r10 = r0.getName()     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00c6 }
            r9.<init>(r10)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r10 = "X.509"
            r11 = r20
            java.security.cert.CertificateFactory r10 = r11.createCertificateFactory(r10)     // Catch:{ Exception -> 0x00bc }
            java.io.InputStream r12 = r9.openStream()     // Catch:{ Exception -> 0x00bc }
            java.io.BufferedInputStream r13 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x00bc }
            r13.<init>(r12)     // Catch:{ Exception -> 0x00bc }
            java.security.cert.CRL r10 = r10.generateCRL(r13)     // Catch:{ Exception -> 0x00bc }
            java.security.cert.X509CRL r10 = (java.security.cert.X509CRL) r10     // Catch:{ Exception -> 0x00bc }
            r12.close()     // Catch:{ Exception -> 0x00bc }
            java.util.logging.Logger r12 = LOG     // Catch:{ Exception -> 0x00bc }
            java.util.logging.Level r13 = java.util.logging.Level.INFO     // Catch:{ Exception -> 0x00bc }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00bc }
            r14.<init>()     // Catch:{ Exception -> 0x00bc }
            java.lang.String r15 = "downloaded CRL from CrlDP "
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ Exception -> 0x00bc }
            java.lang.StringBuilder r14 = r14.append(r9)     // Catch:{ Exception -> 0x00bc }
            java.lang.String r15 = " for issuer \""
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ Exception -> 0x00bc }
            r15 = r17
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ Exception -> 0x00ba }
            java.lang.String r3 = "\""
            java.lang.StringBuilder r3 = r14.append(r3)     // Catch:{ Exception -> 0x00ba }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00ba }
            r12.log(r13, r3)     // Catch:{ Exception -> 0x00ba }
            java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ Exception -> 0x00ba }
            r3.<init>(r10)     // Catch:{ Exception -> 0x00ba }
            r8.put(r0, r3)     // Catch:{ Exception -> 0x00ba }
            return r10
        L_0x00ba:
            r0 = move-exception
            goto L_0x00cc
        L_0x00bc:
            r0 = move-exception
            r15 = r17
            goto L_0x00cc
        L_0x00c0:
            r0 = move-exception
            r15 = r17
            r11 = r20
            goto L_0x00cc
        L_0x00c6:
            r0 = move-exception
            r15 = r17
            r11 = r20
            r9 = r5
        L_0x00cc:
            java.util.logging.Logger r3 = LOG
            java.util.logging.Level r8 = java.util.logging.Level.FINE
            boolean r3 = r3.isLoggable(r8)
            java.lang.String r8 = " ignored: "
            java.lang.String r10 = "CrlDP "
            if (r3 == 0) goto L_0x00ff
            java.util.logging.Logger r3 = LOG
            java.util.logging.Level r12 = java.util.logging.Level.FINE
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.StringBuilder r10 = r13.append(r10)
            java.lang.StringBuilder r9 = r10.append(r9)
            java.lang.StringBuilder r8 = r9.append(r8)
            java.lang.String r9 = r0.getMessage()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r3.log(r12, r8, r0)
            goto L_0x0128
        L_0x00ff:
            java.util.logging.Logger r3 = LOG
            java.util.logging.Level r12 = java.util.logging.Level.INFO
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.StringBuilder r10 = r13.append(r10)
            java.lang.StringBuilder r9 = r10.append(r9)
            java.lang.StringBuilder r8 = r9.append(r8)
            java.lang.String r0 = r0.getMessage()
            java.lang.StringBuilder r0 = r8.append(r0)
            java.lang.String r0 = r0.toString()
            r3.log(r12, r0)
            goto L_0x0128
        L_0x0124:
            r15 = r17
            r11 = r20
        L_0x0128:
            int r7 = r7 + 1
            goto L_0x0028
        L_0x012c:
            r15 = r17
            r11 = r20
            int r4 = r4 + 1
            goto L_0x000b
        L_0x0134:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.pkix.jcajce.X509RevocationChecker.downloadCRLs(javax.security.auth.x500.X500Principal, java.util.Date, org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.jcajce.util.JcaJceHelper):java.security.cert.CRL");
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
            throw new AnnotatedException("could not read distribution points could not be read", e);
        }
    }

    public void check(Certificate certificate, Collection<String> collection) throws CertPathValidatorException {
        StringBuilder sb;
        Level level;
        Logger logger;
        X509Certificate x509Certificate = (X509Certificate) certificate;
        if (!this.isCheckEEOnly || x509Certificate.getBasicConstraints() == -1) {
            if (this.workingIssuerName == null) {
                this.workingIssuerName = x509Certificate.getIssuerX500Principal();
                TrustAnchor trustAnchor = null;
                for (TrustAnchor next : this.trustAnchors) {
                    if (this.workingIssuerName.equals(next.getCA()) || this.workingIssuerName.equals(next.getTrustedCert().getSubjectX500Principal())) {
                        trustAnchor = next;
                    }
                }
                if (trustAnchor != null) {
                    X509Certificate trustedCert = trustAnchor.getTrustedCert();
                    this.signingCert = trustedCert;
                    this.workingPublicKey = trustedCert.getPublicKey();
                } else {
                    throw new CertPathValidatorException("no trust anchor found for " + this.workingIssuerName);
                }
            }
            ArrayList arrayList = new ArrayList();
            try {
                PKIXParameters pKIXParameters = new PKIXParameters(this.trustAnchors);
                pKIXParameters.setRevocationEnabled(false);
                pKIXParameters.setDate(new Date());
                for (int i = 0; i != this.crlCertStores.size(); i++) {
                    if (LOG.isLoggable(Level.INFO)) {
                        addIssuers((List<X500Principal>) arrayList, this.crlCertStores.get(i));
                    }
                    pKIXParameters.addCertStore(this.crlCertStores.get(i));
                }
                PKIXExtendedParameters.Builder builder = new PKIXExtendedParameters.Builder(pKIXParameters);
                for (int i2 = 0; i2 != this.crls.size(); i2++) {
                    if (LOG.isLoggable(Level.INFO)) {
                        addIssuers((List<X500Principal>) arrayList, this.crls.get(i2));
                    }
                    builder.addCRLStore(new LocalCRLStore(this.crls.get(i2)));
                }
                if (arrayList.isEmpty()) {
                    LOG.log(Level.INFO, "configured with 0 pre-loaded CRLs");
                } else if (LOG.isLoggable(Level.FINE)) {
                    for (int i3 = 0; i3 != arrayList.size(); i3++) {
                        LOG.log(Level.FINE, "configuring with CRL for issuer \"" + arrayList.get(i3) + "\"");
                    }
                } else {
                    LOG.log(Level.INFO, "configured with " + arrayList.size() + " pre-loaded CRLs");
                }
                try {
                    checkCRLs(builder.build(), x509Certificate, pKIXParameters.getDate(), this.signingCert, this.workingPublicKey, new ArrayList(), this.helper);
                } catch (AnnotatedException e) {
                    throw new CertPathValidatorException(e.getMessage(), e.getCause());
                } catch (CRLNotFoundException e2) {
                    if (x509Certificate.getExtensionValue(Extension.cRLDistributionPoints.getId()) != null) {
                        try {
                            CRL downloadCRLs = downloadCRLs(x509Certificate.getIssuerX500Principal(), pKIXParameters.getDate(), RevocationUtilities.getExtensionValue(x509Certificate, Extension.cRLDistributionPoints), this.helper);
                            if (downloadCRLs != null) {
                                try {
                                    builder.addCRLStore(new LocalCRLStore(new CollectionStore(Collections.singleton(downloadCRLs))));
                                    checkCRLs(builder.build(), x509Certificate, new Date(), this.signingCert, this.workingPublicKey, new ArrayList(), this.helper);
                                } catch (AnnotatedException e3) {
                                    throw new CertPathValidatorException(e2.getMessage(), e2.getCause());
                                }
                            } else if (this.canSoftFail) {
                                X500Principal issuerX500Principal = x509Certificate.getIssuerX500Principal();
                                Long l = this.failures.get(issuerX500Principal);
                                if (l != null) {
                                    long currentTimeMillis = System.currentTimeMillis() - l.longValue();
                                    long j = this.failHardMaxTime;
                                    if (j == -1 || j >= currentTimeMillis) {
                                        if (currentTimeMillis < this.failLogMaxTime) {
                                            logger = LOG;
                                            level = Level.WARNING;
                                            sb = new StringBuilder();
                                        } else {
                                            logger = LOG;
                                            level = Level.SEVERE;
                                            sb = new StringBuilder();
                                        }
                                        logger.log(level, sb.append("soft failing for issuer: \"").append(issuerX500Principal).append("\"").toString());
                                    } else {
                                        throw e2;
                                    }
                                } else {
                                    this.failures.put(issuerX500Principal, Long.valueOf(System.currentTimeMillis()));
                                }
                            } else {
                                throw e2;
                            }
                        } catch (AnnotatedException e4) {
                            throw new CertPathValidatorException(e2.getMessage(), e2.getCause());
                        }
                    } else {
                        throw e2;
                    }
                }
                this.signingCert = x509Certificate;
                this.workingPublicKey = x509Certificate.getPublicKey();
                this.workingIssuerName = x509Certificate.getSubjectX500Principal();
            } catch (GeneralSecurityException e5) {
                throw new RuntimeException("error setting up baseParams: " + e5.getMessage());
            }
        } else {
            this.workingIssuerName = x509Certificate.getSubjectX500Principal();
            this.workingPublicKey = x509Certificate.getPublicKey();
            this.signingCert = x509Certificate;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0103  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void checkCRLs(org.bouncycastle.jcajce.PKIXExtendedParameters r21, java.security.cert.X509Certificate r22, java.util.Date r23, java.security.cert.X509Certificate r24, java.security.PublicKey r25, java.util.List r26, org.bouncycastle.jcajce.util.JcaJceHelper r27) throws org.bouncycastle.pkix.jcajce.AnnotatedException, java.security.cert.CertPathValidatorException {
        /*
            r20 = this;
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = org.bouncycastle.asn1.x509.Extension.cRLDistributionPoints     // Catch:{ Exception -> 0x01ac }
            r11 = r22
            org.bouncycastle.asn1.ASN1Primitive r0 = org.bouncycastle.pkix.jcajce.RevocationUtilities.getExtensionValue(r11, r0)     // Catch:{ Exception -> 0x01ac }
            org.bouncycastle.asn1.x509.CRLDistPoint r0 = org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(r0)     // Catch:{ Exception -> 0x01ac }
            org.bouncycastle.jcajce.PKIXExtendedParameters$Builder r1 = new org.bouncycastle.jcajce.PKIXExtendedParameters$Builder
            r12 = r21
            r1.<init>((org.bouncycastle.jcajce.PKIXExtendedParameters) r12)
            java.util.Map r2 = r21.getNamedCRLStoreMap()     // Catch:{ AnnotatedException -> 0x01a3 }
            java.util.List r2 = getAdditionalStoresFromCRLDistributionPoint(r0, r2)     // Catch:{ AnnotatedException -> 0x01a3 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ AnnotatedException -> 0x01a3 }
        L_0x001f:
            boolean r3 = r2.hasNext()     // Catch:{ AnnotatedException -> 0x01a3 }
            if (r3 == 0) goto L_0x002f
            java.lang.Object r3 = r2.next()     // Catch:{ AnnotatedException -> 0x01a3 }
            org.bouncycastle.jcajce.PKIXCRLStore r3 = (org.bouncycastle.jcajce.PKIXCRLStore) r3     // Catch:{ AnnotatedException -> 0x01a3 }
            r1.addCRLStore(r3)     // Catch:{ AnnotatedException -> 0x01a3 }
            goto L_0x001f
        L_0x002f:
            org.bouncycastle.pkix.jcajce.CertStatus r13 = new org.bouncycastle.pkix.jcajce.CertStatus
            r13.<init>()
            org.bouncycastle.pkix.jcajce.ReasonsMask r14 = new org.bouncycastle.pkix.jcajce.ReasonsMask
            r14.<init>()
            org.bouncycastle.jcajce.PKIXExtendedParameters r15 = r1.build()
            r16 = 1
            r10 = 0
            r9 = 11
            r8 = 0
            if (r0 == 0) goto L_0x009d
            org.bouncycastle.asn1.x509.DistributionPoint[] r7 = r0.getDistributionPoints()     // Catch:{ Exception -> 0x0093 }
            if (r7 == 0) goto L_0x009d
            r0 = r8
            r6 = 0
            r17 = 0
        L_0x004f:
            int r1 = r7.length
            if (r6 >= r1) goto L_0x0090
            int r1 = r13.getCertStatus()
            if (r1 != r9) goto L_0x0090
            boolean r1 = r14.isAllReasons()
            if (r1 != 0) goto L_0x0090
            r1 = r7[r6]     // Catch:{ AnnotatedException -> 0x007e }
            r2 = r15
            r3 = r22
            r4 = r23
            r5 = r24
            r18 = r6
            r6 = r25
            r19 = r7
            r7 = r13
            r11 = r8
            r8 = r14
            r11 = 11
            r9 = r26
            r10 = r27
            org.bouncycastle.pkix.jcajce.RFC3280CertPathUtilities.checkCRL(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ AnnotatedException -> 0x007c }
            r17 = 1
            goto L_0x0085
        L_0x007c:
            r0 = move-exception
            goto L_0x0085
        L_0x007e:
            r0 = move-exception
            r18 = r6
            r19 = r7
            r11 = 11
        L_0x0085:
            int r6 = r18 + 1
            r11 = r22
            r7 = r19
            r8 = 0
            r9 = 11
            r10 = 0
            goto L_0x004f
        L_0x0090:
            r11 = 11
            goto L_0x00a2
        L_0x0093:
            r0 = move-exception
            r1 = r0
            org.bouncycastle.pkix.jcajce.AnnotatedException r0 = new org.bouncycastle.pkix.jcajce.AnnotatedException
            java.lang.String r2 = "cannot read distribution points"
            r0.<init>(r2, r1)
            throw r0
        L_0x009d:
            r11 = 11
            r0 = 0
            r17 = 0
        L_0x00a2:
            int r1 = r13.getCertStatus()
            if (r1 != r11) goto L_0x00ed
            boolean r1 = r14.isAllReasons()
            if (r1 != 0) goto L_0x00ed
            javax.security.auth.x500.X500Principal r1 = r22.getIssuerX500Principal()     // Catch:{ AnnotatedException -> 0x00ec }
            org.bouncycastle.asn1.x509.DistributionPoint r2 = new org.bouncycastle.asn1.x509.DistributionPoint     // Catch:{ AnnotatedException -> 0x00ec }
            org.bouncycastle.asn1.x509.DistributionPointName r3 = new org.bouncycastle.asn1.x509.DistributionPointName     // Catch:{ AnnotatedException -> 0x00ec }
            org.bouncycastle.asn1.x509.GeneralNames r4 = new org.bouncycastle.asn1.x509.GeneralNames     // Catch:{ AnnotatedException -> 0x00ec }
            org.bouncycastle.asn1.x509.GeneralName r5 = new org.bouncycastle.asn1.x509.GeneralName     // Catch:{ AnnotatedException -> 0x00ec }
            byte[] r1 = r1.getEncoded()     // Catch:{ AnnotatedException -> 0x00ec }
            org.bouncycastle.asn1.x500.X500Name r1 = org.bouncycastle.asn1.x500.X500Name.getInstance(r1)     // Catch:{ AnnotatedException -> 0x00ec }
            r6 = 4
            r5.<init>((int) r6, (org.bouncycastle.asn1.ASN1Encodable) r1)     // Catch:{ AnnotatedException -> 0x00ec }
            r4.<init>((org.bouncycastle.asn1.x509.GeneralName) r5)     // Catch:{ AnnotatedException -> 0x00ec }
            r1 = 0
            r3.<init>(r1, r4)     // Catch:{ AnnotatedException -> 0x00ec }
            r1 = 0
            r2.<init>(r3, r1, r1)     // Catch:{ AnnotatedException -> 0x00ec }
            java.lang.Object r1 = r21.clone()     // Catch:{ AnnotatedException -> 0x00ec }
            r3 = r1
            org.bouncycastle.jcajce.PKIXExtendedParameters r3 = (org.bouncycastle.jcajce.PKIXExtendedParameters) r3     // Catch:{ AnnotatedException -> 0x00ec }
            r1 = r2
            r2 = r3
            r3 = r22
            r4 = r23
            r5 = r24
            r6 = r25
            r7 = r13
            r8 = r14
            r9 = r26
            r10 = r27
            org.bouncycastle.pkix.jcajce.RFC3280CertPathUtilities.checkCRL(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ AnnotatedException -> 0x00ec }
            goto L_0x00ef
        L_0x00ec:
            r0 = move-exception
        L_0x00ed:
            r16 = r17
        L_0x00ef:
            if (r16 != 0) goto L_0x0103
            boolean r1 = r0 instanceof org.bouncycastle.pkix.jcajce.AnnotatedException
            java.lang.String r2 = "no valid CRL found"
            if (r1 == 0) goto L_0x00fd
            org.bouncycastle.pkix.jcajce.CRLNotFoundException r1 = new org.bouncycastle.pkix.jcajce.CRLNotFoundException
            r1.<init>(r2, r0)
            throw r1
        L_0x00fd:
            org.bouncycastle.pkix.jcajce.CRLNotFoundException r0 = new org.bouncycastle.pkix.jcajce.CRLNotFoundException
            r0.<init>(r2)
            throw r0
        L_0x0103:
            int r0 = r13.getCertStatus()
            if (r0 != r11) goto L_0x0129
            boolean r0 = r14.isAllReasons()
            r1 = 12
            if (r0 != 0) goto L_0x011a
            int r0 = r13.getCertStatus()
            if (r0 != r11) goto L_0x011a
            r13.setCertStatus(r1)
        L_0x011a:
            int r0 = r13.getCertStatus()
            if (r0 == r1) goto L_0x0121
            return
        L_0x0121:
            org.bouncycastle.pkix.jcajce.AnnotatedException r0 = new org.bouncycastle.pkix.jcajce.AnnotatedException
            java.lang.String r1 = "certificate status could not be determined"
            r0.<init>(r1)
            throw r0
        L_0x0129:
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.lang.String r1 = "yyyy-MM-dd HH:mm:ss Z"
            r0.<init>(r1)
            java.lang.String r1 = "UTC"
            java.util.TimeZone r1 = java.util.TimeZone.getTimeZone(r1)
            r0.setTimeZone(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "certificate [issuer=\""
            java.lang.StringBuilder r1 = r1.append(r2)
            javax.security.auth.x500.X500Principal r2 = r22.getIssuerX500Principal()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "\",serialNumber="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.math.BigInteger r2 = r22.getSerialNumber()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ",subject=\""
            java.lang.StringBuilder r1 = r1.append(r2)
            javax.security.auth.x500.X500Principal r2 = r22.getSubjectX500Principal()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "\"] revoked after "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.util.Date r2 = r13.getRevocationDate()
            java.lang.String r0 = r0.format(r2)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r1 = ", reason: "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String[] r1 = crlReasons
            int r2 = r13.getCertStatus()
            r1 = r1[r2]
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            org.bouncycastle.pkix.jcajce.AnnotatedException r1 = new org.bouncycastle.pkix.jcajce.AnnotatedException
            r1.<init>(r0)
            throw r1
        L_0x01a3:
            r0 = move-exception
            org.bouncycastle.pkix.jcajce.AnnotatedException r1 = new org.bouncycastle.pkix.jcajce.AnnotatedException
            java.lang.String r2 = "no additional CRL locations could be decoded from CRL distribution point extension"
            r1.<init>(r2, r0)
            throw r1
        L_0x01ac:
            r0 = move-exception
            org.bouncycastle.pkix.jcajce.AnnotatedException r1 = new org.bouncycastle.pkix.jcajce.AnnotatedException
            java.lang.String r2 = "cannot read CRL distribution point extension"
            r1.<init>(r2, r0)
            goto L_0x01b6
        L_0x01b5:
            throw r1
        L_0x01b6:
            goto L_0x01b5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.pkix.jcajce.X509RevocationChecker.checkCRLs(org.bouncycastle.jcajce.PKIXExtendedParameters, java.security.cert.X509Certificate, java.util.Date, java.security.cert.X509Certificate, java.security.PublicKey, java.util.List, org.bouncycastle.jcajce.util.JcaJceHelper):void");
    }

    public Object clone() {
        return this;
    }

    public Set<String> getSupportedExtensions() {
        return null;
    }

    public void init(boolean z) throws CertPathValidatorException {
        if (!z) {
            this.workingIssuerName = null;
            return;
        }
        throw new IllegalArgumentException("forward processing not supported");
    }

    public boolean isForwardCheckingSupported() {
        return false;
    }
}
