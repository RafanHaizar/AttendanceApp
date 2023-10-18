package org.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.TBSCertList;
import org.bouncycastle.util.Strings;

class X509CRLEntryObject extends X509CRLEntry {

    /* renamed from: c */
    private TBSCertList.CRLEntry f663c;
    private X500Name certificateIssuer;
    private volatile int hashValue;
    private volatile boolean hashValueSet;

    protected X509CRLEntryObject(TBSCertList.CRLEntry cRLEntry) {
        this.f663c = cRLEntry;
        this.certificateIssuer = null;
    }

    protected X509CRLEntryObject(TBSCertList.CRLEntry cRLEntry, boolean z, X500Name x500Name) {
        this.f663c = cRLEntry;
        this.certificateIssuer = loadCertificateIssuer(z, x500Name);
    }

    private Extension getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Extensions extensions = this.f663c.getExtensions();
        if (extensions != null) {
            return extensions.getExtension(aSN1ObjectIdentifier);
        }
        return null;
    }

    private Set getExtensionOIDs(boolean z) {
        Extensions extensions = this.f663c.getExtensions();
        if (extensions == null) {
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

    private X500Name loadCertificateIssuer(boolean z, X500Name x500Name) {
        if (!z) {
            return null;
        }
        Extension extension = getExtension(Extension.certificateIssuer);
        if (extension == null) {
            return x500Name;
        }
        try {
            GeneralName[] names = GeneralNames.getInstance(extension.getParsedValue()).getNames();
            for (int i = 0; i < names.length; i++) {
                if (names[i].getTagNo() == 4) {
                    return X500Name.getInstance(names[i].getName());
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509CRLEntryObject)) {
            return super.equals(this);
        }
        X509CRLEntryObject x509CRLEntryObject = (X509CRLEntryObject) obj;
        if (!this.hashValueSet || !x509CRLEntryObject.hashValueSet || this.hashValue == x509CRLEntryObject.hashValue) {
            return this.f663c.equals(x509CRLEntryObject.f663c);
        }
        return false;
    }

    public X500Principal getCertificateIssuer() {
        if (this.certificateIssuer == null) {
            return null;
        }
        try {
            return new X500Principal(this.certificateIssuer.getEncoded());
        } catch (IOException e) {
            return null;
        }
    }

    public Set getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    public byte[] getEncoded() throws CRLException {
        try {
            return this.f663c.getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new CRLException(e.toString());
        }
    }

    public byte[] getExtensionValue(String str) {
        Extension extension = getExtension(new ASN1ObjectIdentifier(str));
        if (extension == null) {
            return null;
        }
        try {
            return extension.getExtnValue().getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("Exception encoding: " + e.toString());
        }
    }

    public Set getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    public Date getRevocationDate() {
        return this.f663c.getRevocationDate().getDate();
    }

    public BigInteger getSerialNumber() {
        return this.f663c.getUserCertificate().getValue();
    }

    public boolean hasExtensions() {
        return this.f663c.getExtensions() != null;
    }

    public boolean hasUnsupportedCriticalExtension() {
        Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        return criticalExtensionOIDs != null && !criticalExtensionOIDs.isEmpty();
    }

    public int hashCode() {
        if (!this.hashValueSet) {
            this.hashValue = super.hashCode();
            this.hashValueSet = true;
        }
        return this.hashValue;
    }

    public String toString() {
        StringBuffer append;
        StringBuffer stringBuffer = new StringBuffer();
        String lineSeparator = Strings.lineSeparator();
        stringBuffer.append("      userCertificate: ").append(getSerialNumber()).append(lineSeparator);
        stringBuffer.append("       revocationDate: ").append(getRevocationDate()).append(lineSeparator);
        stringBuffer.append("       certificateIssuer: ").append(getCertificateIssuer()).append(lineSeparator);
        Extensions extensions = this.f663c.getExtensions();
        if (extensions != null) {
            Enumeration oids = extensions.oids();
            if (oids.hasMoreElements()) {
                StringBuffer append2 = stringBuffer.append("   crlEntryExtensions:");
                loop0:
                while (true) {
                    append2.append(lineSeparator);
                    while (oids.hasMoreElements()) {
                        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
                        Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
                        if (extension.getExtnValue() != null) {
                            ASN1InputStream aSN1InputStream = new ASN1InputStream(extension.getExtnValue().getOctets());
                            stringBuffer.append("                       critical(").append(extension.isCritical()).append(") ");
                            try {
                                if (aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.reasonCode)) {
                                    append = stringBuffer.append(CRLReason.getInstance(ASN1Enumerated.getInstance(aSN1InputStream.readObject())));
                                } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.certificateIssuer)) {
                                    append = stringBuffer.append("Certificate issuer: ").append(GeneralNames.getInstance(aSN1InputStream.readObject()));
                                } else {
                                    stringBuffer.append(aSN1ObjectIdentifier.getId());
                                    append = stringBuffer.append(" value = ").append(ASN1Dump.dumpAsString(aSN1InputStream.readObject()));
                                }
                                append.append(lineSeparator);
                            } catch (Exception e) {
                                stringBuffer.append(aSN1ObjectIdentifier.getId());
                                append2 = stringBuffer.append(" value = ").append("*****");
                            }
                        } else {
                            stringBuffer.append(lineSeparator);
                        }
                    }
                    break loop0;
                }
            }
        }
        return stringBuffer.toString();
    }
}
