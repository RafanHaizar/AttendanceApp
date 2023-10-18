package org.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.CRLNumber;
import org.bouncycastle.asn1.x509.CertificateList;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.bouncycastle.asn1.x509.TBSCertList;
import org.bouncycastle.jcajce.p014io.OutputStreamFactory;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

abstract class X509CRLImpl extends X509CRL {
    protected JcaJceHelper bcHelper;

    /* renamed from: c */
    protected CertificateList f664c;
    protected boolean isIndirect;
    protected String sigAlgName;
    protected byte[] sigAlgParams;

    X509CRLImpl(JcaJceHelper jcaJceHelper, CertificateList certificateList, String str, byte[] bArr, boolean z) {
        this.bcHelper = jcaJceHelper;
        this.f664c = certificateList;
        this.sigAlgName = str;
        this.sigAlgParams = bArr;
        this.isIndirect = z;
    }

    private void doVerify(PublicKey publicKey, Signature signature) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (this.f664c.getSignatureAlgorithm().equals(this.f664c.getTBSCertList().getSignature())) {
            byte[] bArr = this.sigAlgParams;
            if (bArr != null) {
                try {
                    X509SignatureUtil.setSignatureParameters(signature, ASN1Primitive.fromByteArray(bArr));
                } catch (IOException e) {
                    throw new SignatureException("cannot decode signature parameters: " + e.getMessage());
                }
            }
            signature.initVerify(publicKey);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(OutputStreamFactory.createStream(signature), 512);
                this.f664c.getTBSCertList().encodeTo(bufferedOutputStream, ASN1Encoding.DER);
                bufferedOutputStream.close();
                if (!signature.verify(getSignature())) {
                    throw new SignatureException("CRL does not verify with supplied public key.");
                }
            } catch (IOException e2) {
                throw new CRLException(e2.toString());
            }
        } else {
            throw new CRLException("Signature algorithm on CertificateList does not match TBSCertList.");
        }
    }

    private Set getExtensionOIDs(boolean z) {
        Extensions extensions;
        if (getVersion() != 2 || (extensions = this.f664c.getTBSCertList().getExtensions()) == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Enumeration oids = extensions.oids();
        while (oids.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
            if (z == extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                hashSet.add(aSN1ObjectIdentifier.getId());
            }
        }
        return hashSet;
    }

    protected static byte[] getExtensionOctets(CertificateList certificateList, String str) {
        ASN1OctetString extensionValue = getExtensionValue(certificateList, str);
        if (extensionValue != null) {
            return extensionValue.getOctets();
        }
        return null;
    }

    protected static ASN1OctetString getExtensionValue(CertificateList certificateList, String str) {
        Extension extension;
        Extensions extensions = certificateList.getTBSCertList().getExtensions();
        if (extensions == null || (extension = extensions.getExtension(new ASN1ObjectIdentifier(str))) == null) {
            return null;
        }
        return extension.getExtnValue();
    }

    private Set loadCRLEntries() {
        Extension extension;
        HashSet hashSet = new HashSet();
        Enumeration revokedCertificateEnumeration = this.f664c.getRevokedCertificateEnumeration();
        X500Name x500Name = null;
        while (revokedCertificateEnumeration.hasMoreElements()) {
            TBSCertList.CRLEntry cRLEntry = (TBSCertList.CRLEntry) revokedCertificateEnumeration.nextElement();
            hashSet.add(new X509CRLEntryObject(cRLEntry, this.isIndirect, x500Name));
            if (this.isIndirect && cRLEntry.hasExtensions() && (extension = cRLEntry.getExtensions().getExtension(Extension.certificateIssuer)) != null) {
                x500Name = X500Name.getInstance(GeneralNames.getInstance(extension.getParsedValue()).getNames()[0].getName());
            }
        }
        return hashSet;
    }

    public Set getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    public byte[] getEncoded() throws CRLException {
        try {
            return this.f664c.getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new CRLException(e.toString());
        }
    }

    public byte[] getExtensionValue(String str) {
        ASN1OctetString extensionValue = getExtensionValue(this.f664c, str);
        if (extensionValue == null) {
            return null;
        }
        try {
            return extensionValue.getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("error parsing " + e.toString());
        }
    }

    public Principal getIssuerDN() {
        return new X509Principal(X500Name.getInstance(this.f664c.getIssuer().toASN1Primitive()));
    }

    public X500Principal getIssuerX500Principal() {
        try {
            return new X500Principal(this.f664c.getIssuer().getEncoded());
        } catch (IOException e) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    public Date getNextUpdate() {
        if (this.f664c.getNextUpdate() != null) {
            return this.f664c.getNextUpdate().getDate();
        }
        return null;
    }

    public Set getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    public X509CRLEntry getRevokedCertificate(BigInteger bigInteger) {
        Extension extension;
        Enumeration revokedCertificateEnumeration = this.f664c.getRevokedCertificateEnumeration();
        X500Name x500Name = null;
        while (revokedCertificateEnumeration.hasMoreElements()) {
            TBSCertList.CRLEntry cRLEntry = (TBSCertList.CRLEntry) revokedCertificateEnumeration.nextElement();
            if (cRLEntry.getUserCertificate().hasValue(bigInteger)) {
                return new X509CRLEntryObject(cRLEntry, this.isIndirect, x500Name);
            }
            if (this.isIndirect && cRLEntry.hasExtensions() && (extension = cRLEntry.getExtensions().getExtension(Extension.certificateIssuer)) != null) {
                x500Name = X500Name.getInstance(GeneralNames.getInstance(extension.getParsedValue()).getNames()[0].getName());
            }
        }
        return null;
    }

    public Set getRevokedCertificates() {
        Set loadCRLEntries = loadCRLEntries();
        if (!loadCRLEntries.isEmpty()) {
            return Collections.unmodifiableSet(loadCRLEntries);
        }
        return null;
    }

    public String getSigAlgName() {
        return this.sigAlgName;
    }

    public String getSigAlgOID() {
        return this.f664c.getSignatureAlgorithm().getAlgorithm().getId();
    }

    public byte[] getSigAlgParams() {
        return Arrays.clone(this.sigAlgParams);
    }

    public byte[] getSignature() {
        return this.f664c.getSignature().getOctets();
    }

    public byte[] getTBSCertList() throws CRLException {
        try {
            return this.f664c.getTBSCertList().getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new CRLException(e.toString());
        }
    }

    public Date getThisUpdate() {
        return this.f664c.getThisUpdate().getDate();
    }

    public int getVersion() {
        return this.f664c.getVersionNumber();
    }

    public boolean hasUnsupportedCriticalExtension() {
        Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        if (criticalExtensionOIDs == null) {
            return false;
        }
        criticalExtensionOIDs.remove(Extension.issuingDistributionPoint.getId());
        criticalExtensionOIDs.remove(Extension.deltaCRLIndicator.getId());
        return !criticalExtensionOIDs.isEmpty();
    }

    public boolean isRevoked(Certificate certificate) {
        X500Name x500Name;
        Extension extension;
        if (certificate.getType().equals("X.509")) {
            Enumeration revokedCertificateEnumeration = this.f664c.getRevokedCertificateEnumeration();
            X500Name issuer = this.f664c.getIssuer();
            if (revokedCertificateEnumeration.hasMoreElements()) {
                X509Certificate x509Certificate = (X509Certificate) certificate;
                BigInteger serialNumber = x509Certificate.getSerialNumber();
                while (revokedCertificateEnumeration.hasMoreElements()) {
                    TBSCertList.CRLEntry instance = TBSCertList.CRLEntry.getInstance(revokedCertificateEnumeration.nextElement());
                    if (this.isIndirect && instance.hasExtensions() && (extension = instance.getExtensions().getExtension(Extension.certificateIssuer)) != null) {
                        issuer = X500Name.getInstance(GeneralNames.getInstance(extension.getParsedValue()).getNames()[0].getName());
                    }
                    if (instance.getUserCertificate().hasValue(serialNumber)) {
                        if (certificate instanceof X509Certificate) {
                            x500Name = X500Name.getInstance(x509Certificate.getIssuerX500Principal().getEncoded());
                        } else {
                            try {
                                x500Name = org.bouncycastle.asn1.x509.Certificate.getInstance(certificate.getEncoded()).getIssuer();
                            } catch (CertificateEncodingException e) {
                                throw new IllegalArgumentException("Cannot process certificate: " + e.getMessage());
                            }
                        }
                        return issuer.equals(x500Name);
                    }
                }
            }
            return false;
        }
        throw new IllegalArgumentException("X.509 CRL used with non X.509 Cert");
    }

    public String toString() {
        StringBuffer append;
        StringBuffer append2;
        String str;
        StringBuffer stringBuffer;
        StringBuffer stringBuffer2 = new StringBuffer();
        String lineSeparator = Strings.lineSeparator();
        stringBuffer2.append("              Version: ").append(getVersion()).append(lineSeparator);
        stringBuffer2.append("             IssuerDN: ").append(getIssuerDN()).append(lineSeparator);
        stringBuffer2.append("          This update: ").append(getThisUpdate()).append(lineSeparator);
        stringBuffer2.append("          Next update: ").append(getNextUpdate()).append(lineSeparator);
        stringBuffer2.append("  Signature Algorithm: ").append(getSigAlgName()).append(lineSeparator);
        byte[] signature = getSignature();
        stringBuffer2.append("            Signature: ").append(new String(Hex.encode(signature, 0, 20))).append(lineSeparator);
        for (int i = 20; i < signature.length; i += 20) {
            if (i < signature.length - 20) {
                stringBuffer = stringBuffer2.append("                       ");
                str = new String(Hex.encode(signature, i, 20));
            } else {
                stringBuffer = stringBuffer2.append("                       ");
                str = new String(Hex.encode(signature, i, signature.length - i));
            }
            stringBuffer.append(str).append(lineSeparator);
        }
        Extensions extensions = this.f664c.getTBSCertList().getExtensions();
        if (extensions != null) {
            Enumeration oids = extensions.oids();
            if (oids.hasMoreElements()) {
                append = stringBuffer2.append("           Extensions: ");
                append.append(lineSeparator);
            }
            while (oids.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
                Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
                if (extension.getExtnValue() != null) {
                    ASN1InputStream aSN1InputStream = new ASN1InputStream(extension.getExtnValue().getOctets());
                    stringBuffer2.append("                       critical(").append(extension.isCritical()).append(") ");
                    try {
                    } catch (Exception e) {
                        stringBuffer2.append(aSN1ObjectIdentifier.getId());
                        append = stringBuffer2.append(" value = ").append("*****");
                    }
                    if (aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.cRLNumber)) {
                        append2 = stringBuffer2.append(new CRLNumber(ASN1Integer.getInstance(aSN1InputStream.readObject()).getPositiveValue()));
                    } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.deltaCRLIndicator)) {
                        append2 = stringBuffer2.append("Base CRL: " + new CRLNumber(ASN1Integer.getInstance(aSN1InputStream.readObject()).getPositiveValue()));
                    } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.issuingDistributionPoint)) {
                        append2 = stringBuffer2.append(IssuingDistributionPoint.getInstance(aSN1InputStream.readObject()));
                    } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.cRLDistributionPoints)) {
                        append2 = stringBuffer2.append(CRLDistPoint.getInstance(aSN1InputStream.readObject()));
                    } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.freshestCRL)) {
                        append2 = stringBuffer2.append(CRLDistPoint.getInstance(aSN1InputStream.readObject()));
                    } else {
                        stringBuffer2.append(aSN1ObjectIdentifier.getId());
                        append2 = stringBuffer2.append(" value = ").append(ASN1Dump.dumpAsString(aSN1InputStream.readObject()));
                    }
                    append2.append(lineSeparator);
                } else {
                    stringBuffer2.append(lineSeparator);
                }
            }
        }
        Set<Object> revokedCertificates = getRevokedCertificates();
        if (revokedCertificates != null) {
            for (Object append3 : revokedCertificates) {
                stringBuffer2.append(append3);
                stringBuffer2.append(lineSeparator);
            }
        }
        return stringBuffer2.toString();
    }

    public void verify(PublicKey publicKey) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        Signature signature;
        try {
            signature = this.bcHelper.createSignature(getSigAlgName());
        } catch (Exception e) {
            signature = Signature.getInstance(getSigAlgName());
        }
        doVerify(publicKey, signature);
    }

    public void verify(PublicKey publicKey, String str) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        doVerify(publicKey, str != null ? Signature.getInstance(getSigAlgName(), str) : Signature.getInstance(getSigAlgName()));
    }

    public void verify(PublicKey publicKey, Provider provider) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        doVerify(publicKey, provider != null ? Signature.getInstance(getSigAlgName(), provider) : Signature.getInstance(getSigAlgName()));
    }
}
