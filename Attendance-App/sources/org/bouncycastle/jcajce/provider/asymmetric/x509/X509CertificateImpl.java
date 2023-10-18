package org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.itextpdf.signatures.OID;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.misc.NetscapeCertType;
import org.bouncycastle.asn1.misc.NetscapeRevocationURL;
import org.bouncycastle.asn1.misc.VerisignCzagExtension;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.jcajce.interfaces.BCX509Certificate;
import org.bouncycastle.jcajce.p014io.OutputStreamFactory;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

abstract class X509CertificateImpl extends X509Certificate implements BCX509Certificate {
    protected BasicConstraints basicConstraints;
    protected JcaJceHelper bcHelper;

    /* renamed from: c */
    protected Certificate f665c;
    protected boolean[] keyUsage;

    X509CertificateImpl(JcaJceHelper jcaJceHelper, Certificate certificate, BasicConstraints basicConstraints2, boolean[] zArr) {
        this.bcHelper = jcaJceHelper;
        this.f665c = certificate;
        this.basicConstraints = basicConstraints2;
        this.keyUsage = zArr;
    }

    private void checkSignature(PublicKey publicKey, Signature signature) throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (isAlgIdEqual(this.f665c.getSignatureAlgorithm(), this.f665c.getTBSCertificate().getSignature())) {
            X509SignatureUtil.setSignatureParameters(signature, this.f665c.getSignatureAlgorithm().getParameters());
            signature.initVerify(publicKey);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(OutputStreamFactory.createStream(signature), 512);
                this.f665c.getTBSCertificate().encodeTo(bufferedOutputStream, ASN1Encoding.DER);
                bufferedOutputStream.close();
                if (!signature.verify(getSignature())) {
                    throw new SignatureException("certificate does not verify with supplied key");
                }
            } catch (IOException e) {
                throw new CertificateEncodingException(e.toString());
            }
        } else {
            throw new CertificateException("signature algorithm in TBS cert not same as outer cert");
        }
    }

    private static Collection getAlternativeNames(Certificate certificate, String str) throws CertificateParsingException {
        String string;
        byte[] extensionOctets = getExtensionOctets(certificate, str);
        if (extensionOctets == null) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            Enumeration objects = ASN1Sequence.getInstance(extensionOctets).getObjects();
            while (objects.hasMoreElements()) {
                GeneralName instance = GeneralName.getInstance(objects.nextElement());
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(Integers.valueOf(instance.getTagNo()));
                switch (instance.getTagNo()) {
                    case 0:
                    case 3:
                    case 5:
                        arrayList2.add(instance.getEncoded());
                        break;
                    case 1:
                    case 2:
                    case 6:
                        string = ((ASN1String) instance.getName()).getString();
                        break;
                    case 4:
                        string = X500Name.getInstance(RFC4519Style.INSTANCE, (Object) instance.getName()).toString();
                        break;
                    case 7:
                        try {
                            string = InetAddress.getByAddress(DEROctetString.getInstance(instance.getName()).getOctets()).getHostAddress();
                            break;
                        } catch (UnknownHostException e) {
                            break;
                        }
                    case 8:
                        string = ASN1ObjectIdentifier.getInstance(instance.getName()).getId();
                        break;
                    default:
                        throw new IOException("Bad tag number: " + instance.getTagNo());
                }
                arrayList2.add(string);
                arrayList.add(Collections.unmodifiableList(arrayList2));
            }
            if (arrayList.size() == 0) {
                return null;
            }
            return Collections.unmodifiableCollection(arrayList);
        } catch (Exception e2) {
            throw new CertificateParsingException(e2.getMessage());
        }
    }

    protected static byte[] getExtensionOctets(Certificate certificate, String str) {
        ASN1OctetString extensionValue = getExtensionValue(certificate, str);
        if (extensionValue != null) {
            return extensionValue.getOctets();
        }
        return null;
    }

    protected static ASN1OctetString getExtensionValue(Certificate certificate, String str) {
        Extension extension;
        Extensions extensions = certificate.getTBSCertificate().getExtensions();
        if (extensions == null || (extension = extensions.getExtension(new ASN1ObjectIdentifier(str))) == null) {
            return null;
        }
        return extension.getExtnValue();
    }

    private boolean isAlgIdEqual(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        if (!algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) algorithmIdentifier2.getAlgorithm())) {
            return false;
        }
        return algorithmIdentifier.getParameters() == null ? algorithmIdentifier2.getParameters() == null || algorithmIdentifier2.getParameters().equals(DERNull.INSTANCE) : algorithmIdentifier2.getParameters() == null ? algorithmIdentifier.getParameters() == null || algorithmIdentifier.getParameters().equals(DERNull.INSTANCE) : algorithmIdentifier.getParameters().equals(algorithmIdentifier2.getParameters());
    }

    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        checkValidity(new Date());
    }

    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        if (date.getTime() > getNotAfter().getTime()) {
            throw new CertificateExpiredException("certificate expired on " + this.f665c.getEndDate().getTime());
        } else if (date.getTime() < getNotBefore().getTime()) {
            throw new CertificateNotYetValidException("certificate not valid till " + this.f665c.getStartDate().getTime());
        }
    }

    public int getBasicConstraints() {
        BasicConstraints basicConstraints2 = this.basicConstraints;
        if (basicConstraints2 == null || !basicConstraints2.isCA()) {
            return -1;
        }
        if (this.basicConstraints.getPathLenConstraint() == null) {
            return Integer.MAX_VALUE;
        }
        return this.basicConstraints.getPathLenConstraint().intValue();
    }

    public Set getCriticalExtensionOIDs() {
        if (getVersion() != 3) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Extensions extensions = this.f665c.getTBSCertificate().getExtensions();
        if (extensions == null) {
            return null;
        }
        Enumeration oids = extensions.oids();
        while (oids.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
            if (extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                hashSet.add(aSN1ObjectIdentifier.getId());
            }
        }
        return hashSet;
    }

    public byte[] getEncoded() throws CertificateEncodingException {
        try {
            return this.f665c.getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new CertificateEncodingException(e.toString());
        }
    }

    public List getExtendedKeyUsage() throws CertificateParsingException {
        byte[] extensionOctets = getExtensionOctets(this.f665c, OID.X509Extensions.EXTENDED_KEY_USAGE);
        if (extensionOctets == null) {
            return null;
        }
        try {
            ASN1Sequence instance = ASN1Sequence.getInstance(ASN1Primitive.fromByteArray(extensionOctets));
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i != instance.size(); i++) {
                arrayList.add(((ASN1ObjectIdentifier) instance.getObjectAt(i)).getId());
            }
            return Collections.unmodifiableList(arrayList);
        } catch (Exception e) {
            throw new CertificateParsingException("error processing extended key usage extension");
        }
    }

    public byte[] getExtensionValue(String str) {
        ASN1OctetString extensionValue = getExtensionValue(this.f665c, str);
        if (extensionValue == null) {
            return null;
        }
        try {
            return extensionValue.getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("error parsing " + e.toString());
        }
    }

    public Collection getIssuerAlternativeNames() throws CertificateParsingException {
        return getAlternativeNames(this.f665c, Extension.issuerAlternativeName.getId());
    }

    public Principal getIssuerDN() {
        return new X509Principal(this.f665c.getIssuer());
    }

    public boolean[] getIssuerUniqueID() {
        DERBitString issuerUniqueId = this.f665c.getTBSCertificate().getIssuerUniqueId();
        if (issuerUniqueId == null) {
            return null;
        }
        byte[] bytes = issuerUniqueId.getBytes();
        int length = (bytes.length * 8) - issuerUniqueId.getPadBits();
        boolean[] zArr = new boolean[length];
        for (int i = 0; i != length; i++) {
            zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
        }
        return zArr;
    }

    public X500Name getIssuerX500Name() {
        return this.f665c.getIssuer();
    }

    public X500Principal getIssuerX500Principal() {
        try {
            return new X500Principal(this.f665c.getIssuer().getEncoded(ASN1Encoding.DER));
        } catch (IOException e) {
            throw new IllegalStateException("can't encode issuer DN");
        }
    }

    public boolean[] getKeyUsage() {
        return Arrays.clone(this.keyUsage);
    }

    public Set getNonCriticalExtensionOIDs() {
        if (getVersion() != 3) {
            return null;
        }
        HashSet hashSet = new HashSet();
        Extensions extensions = this.f665c.getTBSCertificate().getExtensions();
        if (extensions == null) {
            return null;
        }
        Enumeration oids = extensions.oids();
        while (oids.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
            if (!extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                hashSet.add(aSN1ObjectIdentifier.getId());
            }
        }
        return hashSet;
    }

    public Date getNotAfter() {
        return this.f665c.getEndDate().getDate();
    }

    public Date getNotBefore() {
        return this.f665c.getStartDate().getDate();
    }

    public PublicKey getPublicKey() {
        try {
            return BouncyCastleProvider.getPublicKey(this.f665c.getSubjectPublicKeyInfo());
        } catch (IOException e) {
            return null;
        }
    }

    public BigInteger getSerialNumber() {
        return this.f665c.getSerialNumber().getValue();
    }

    public String getSigAlgName() {
        return X509SignatureUtil.getSignatureName(this.f665c.getSignatureAlgorithm());
    }

    public String getSigAlgOID() {
        return this.f665c.getSignatureAlgorithm().getAlgorithm().getId();
    }

    public byte[] getSigAlgParams() {
        if (this.f665c.getSignatureAlgorithm().getParameters() != null) {
            try {
                return this.f665c.getSignatureAlgorithm().getParameters().toASN1Primitive().getEncoded(ASN1Encoding.DER);
            } catch (IOException e) {
            }
        }
        return null;
    }

    public byte[] getSignature() {
        return this.f665c.getSignature().getOctets();
    }

    public Collection getSubjectAlternativeNames() throws CertificateParsingException {
        return getAlternativeNames(this.f665c, Extension.subjectAlternativeName.getId());
    }

    public Principal getSubjectDN() {
        return new X509Principal(this.f665c.getSubject());
    }

    public boolean[] getSubjectUniqueID() {
        DERBitString subjectUniqueId = this.f665c.getTBSCertificate().getSubjectUniqueId();
        if (subjectUniqueId == null) {
            return null;
        }
        byte[] bytes = subjectUniqueId.getBytes();
        int length = (bytes.length * 8) - subjectUniqueId.getPadBits();
        boolean[] zArr = new boolean[length];
        for (int i = 0; i != length; i++) {
            zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
        }
        return zArr;
    }

    public X500Name getSubjectX500Name() {
        return this.f665c.getSubject();
    }

    public X500Principal getSubjectX500Principal() {
        try {
            return new X500Principal(this.f665c.getSubject().getEncoded(ASN1Encoding.DER));
        } catch (IOException e) {
            throw new IllegalStateException("can't encode subject DN");
        }
    }

    public byte[] getTBSCertificate() throws CertificateEncodingException {
        try {
            return this.f665c.getTBSCertificate().getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new CertificateEncodingException(e.toString());
        }
    }

    public TBSCertificate getTBSCertificateNative() {
        return this.f665c.getTBSCertificate();
    }

    public int getVersion() {
        return this.f665c.getVersionNumber();
    }

    public boolean hasUnsupportedCriticalExtension() {
        Extensions extensions;
        if (getVersion() != 3 || (extensions = this.f665c.getTBSCertificate().getExtensions()) == null) {
            return false;
        }
        Enumeration oids = extensions.oids();
        while (oids.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
            if (!aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.keyUsage) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.certificatePolicies) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.policyMappings) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.inhibitAnyPolicy) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.cRLDistributionPoints) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.issuingDistributionPoint) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.deltaCRLIndicator) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.policyConstraints) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.basicConstraints) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.subjectAlternativeName) && !aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.nameConstraints) && extensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuffer append;
        String str;
        StringBuffer stringBuffer;
        StringBuffer stringBuffer2 = new StringBuffer();
        String lineSeparator = Strings.lineSeparator();
        stringBuffer2.append("  [0]         Version: ").append(getVersion()).append(lineSeparator);
        stringBuffer2.append("         SerialNumber: ").append(getSerialNumber()).append(lineSeparator);
        stringBuffer2.append("             IssuerDN: ").append(getIssuerDN()).append(lineSeparator);
        stringBuffer2.append("           Start Date: ").append(getNotBefore()).append(lineSeparator);
        stringBuffer2.append("           Final Date: ").append(getNotAfter()).append(lineSeparator);
        stringBuffer2.append("            SubjectDN: ").append(getSubjectDN()).append(lineSeparator);
        stringBuffer2.append("           Public Key: ").append(getPublicKey()).append(lineSeparator);
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
        Extensions extensions = this.f665c.getTBSCertificate().getExtensions();
        if (extensions != null) {
            Enumeration oids = extensions.oids();
            if (oids.hasMoreElements()) {
                stringBuffer2.append("       Extensions: \n");
            }
            while (oids.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) oids.nextElement();
                Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
                if (extension.getExtnValue() != null) {
                    ASN1InputStream aSN1InputStream = new ASN1InputStream(extension.getExtnValue().getOctets());
                    stringBuffer2.append("                       critical(").append(extension.isCritical()).append(") ");
                    try {
                        if (aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.basicConstraints)) {
                            append = stringBuffer2.append(BasicConstraints.getInstance(aSN1InputStream.readObject()));
                        } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) Extension.keyUsage)) {
                            append = stringBuffer2.append(KeyUsage.getInstance(aSN1InputStream.readObject()));
                        } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) MiscObjectIdentifiers.netscapeCertType)) {
                            append = stringBuffer2.append(new NetscapeCertType(DERBitString.getInstance(aSN1InputStream.readObject())));
                        } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) MiscObjectIdentifiers.netscapeRevocationURL)) {
                            append = stringBuffer2.append(new NetscapeRevocationURL(DERIA5String.getInstance(aSN1InputStream.readObject())));
                        } else if (aSN1ObjectIdentifier.equals((ASN1Primitive) MiscObjectIdentifiers.verisignCzagExtension)) {
                            append = stringBuffer2.append(new VerisignCzagExtension(DERIA5String.getInstance(aSN1InputStream.readObject())));
                        } else {
                            stringBuffer2.append(aSN1ObjectIdentifier.getId());
                            append = stringBuffer2.append(" value = ").append(ASN1Dump.dumpAsString(aSN1InputStream.readObject()));
                        }
                        append.append(lineSeparator);
                    } catch (Exception e) {
                        stringBuffer2.append(aSN1ObjectIdentifier.getId());
                        stringBuffer2.append(" value = ").append("*****").append(lineSeparator);
                    }
                } else {
                    stringBuffer2.append(lineSeparator);
                }
            }
        }
        return stringBuffer2.toString();
    }

    public final void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        Signature signature;
        String signatureName = X509SignatureUtil.getSignatureName(this.f665c.getSignatureAlgorithm());
        try {
            signature = this.bcHelper.createSignature(signatureName);
        } catch (Exception e) {
            signature = Signature.getInstance(signatureName);
        }
        checkSignature(publicKey, signature);
    }

    public final void verify(PublicKey publicKey, String str) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        String signatureName = X509SignatureUtil.getSignatureName(this.f665c.getSignatureAlgorithm());
        checkSignature(publicKey, str != null ? Signature.getInstance(signatureName, str) : Signature.getInstance(signatureName));
    }

    public final void verify(PublicKey publicKey, Provider provider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String signatureName = X509SignatureUtil.getSignatureName(this.f665c.getSignatureAlgorithm());
        checkSignature(publicKey, provider != null ? Signature.getInstance(signatureName, provider) : Signature.getInstance(signatureName));
    }
}
