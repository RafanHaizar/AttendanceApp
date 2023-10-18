package org.bouncycastle.asn1.x509;

import com.itextpdf.signatures.OID;
import java.io.IOException;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;

public class X509Extension {
    public static final ASN1ObjectIdentifier auditIdentity = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4");
    public static final ASN1ObjectIdentifier authorityInfoAccess = new ASN1ObjectIdentifier(OID.X509Extensions.AUTHORITY_INFO_ACCESS);
    public static final ASN1ObjectIdentifier authorityKeyIdentifier = new ASN1ObjectIdentifier(OID.X509Extensions.AUTHORITY_KEY_IDENTIFIER);
    public static final ASN1ObjectIdentifier basicConstraints = new ASN1ObjectIdentifier(OID.X509Extensions.BASIC_CONSTRAINTS);
    public static final ASN1ObjectIdentifier biometricInfo = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2");
    public static final ASN1ObjectIdentifier cRLDistributionPoints = new ASN1ObjectIdentifier(OID.X509Extensions.CRL_DISTRIBUTION_POINTS);
    public static final ASN1ObjectIdentifier cRLNumber = new ASN1ObjectIdentifier("2.5.29.20");
    public static final ASN1ObjectIdentifier certificateIssuer = new ASN1ObjectIdentifier("2.5.29.29");
    public static final ASN1ObjectIdentifier certificatePolicies = new ASN1ObjectIdentifier(OID.X509Extensions.CERTIFICATE_POLICIES);
    public static final ASN1ObjectIdentifier deltaCRLIndicator = new ASN1ObjectIdentifier("2.5.29.27");
    public static final ASN1ObjectIdentifier extendedKeyUsage = new ASN1ObjectIdentifier(OID.X509Extensions.EXTENDED_KEY_USAGE);
    public static final ASN1ObjectIdentifier freshestCRL = new ASN1ObjectIdentifier(OID.X509Extensions.FRESHEST_CRL);
    public static final ASN1ObjectIdentifier inhibitAnyPolicy = new ASN1ObjectIdentifier(OID.X509Extensions.INHIBIT_ANY_POLICY);
    public static final ASN1ObjectIdentifier instructionCode = new ASN1ObjectIdentifier("2.5.29.23");
    public static final ASN1ObjectIdentifier invalidityDate = new ASN1ObjectIdentifier("2.5.29.24");
    public static final ASN1ObjectIdentifier issuerAlternativeName = new ASN1ObjectIdentifier(OID.X509Extensions.ISSUER_ALTERNATIVE_NAME);
    public static final ASN1ObjectIdentifier issuingDistributionPoint = new ASN1ObjectIdentifier("2.5.29.28");
    public static final ASN1ObjectIdentifier keyUsage = new ASN1ObjectIdentifier(OID.X509Extensions.KEY_USAGE);
    public static final ASN1ObjectIdentifier logoType = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12");
    public static final ASN1ObjectIdentifier nameConstraints = new ASN1ObjectIdentifier(OID.X509Extensions.NAME_CONSTRAINTS);
    public static final ASN1ObjectIdentifier noRevAvail = new ASN1ObjectIdentifier("2.5.29.56");
    public static final ASN1ObjectIdentifier policyConstraints = new ASN1ObjectIdentifier(OID.X509Extensions.POLICY_CONSTRAINTS);
    public static final ASN1ObjectIdentifier policyMappings = new ASN1ObjectIdentifier(OID.X509Extensions.POLICY_MAPPINGS);
    public static final ASN1ObjectIdentifier privateKeyUsagePeriod = new ASN1ObjectIdentifier("2.5.29.16");
    public static final ASN1ObjectIdentifier qCStatements = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3");
    public static final ASN1ObjectIdentifier reasonCode = new ASN1ObjectIdentifier("2.5.29.21");
    public static final ASN1ObjectIdentifier subjectAlternativeName = new ASN1ObjectIdentifier(OID.X509Extensions.SUBJECT_ALTERNATIVE_NAME);
    public static final ASN1ObjectIdentifier subjectDirectoryAttributes = new ASN1ObjectIdentifier(OID.X509Extensions.SUBJECT_DIRECTORY_ATTRIBUTES);
    public static final ASN1ObjectIdentifier subjectInfoAccess = new ASN1ObjectIdentifier(OID.X509Extensions.SUBJECT_INFO_ACCESS);
    public static final ASN1ObjectIdentifier subjectKeyIdentifier = new ASN1ObjectIdentifier(OID.X509Extensions.SUBJECT_KEY_IDENTIFIER);
    public static final ASN1ObjectIdentifier targetInformation = new ASN1ObjectIdentifier("2.5.29.55");
    boolean critical;
    ASN1OctetString value;

    public X509Extension(ASN1Boolean aSN1Boolean, ASN1OctetString aSN1OctetString) {
        this.critical = aSN1Boolean.isTrue();
        this.value = aSN1OctetString;
    }

    public X509Extension(boolean z, ASN1OctetString aSN1OctetString) {
        this.critical = z;
        this.value = aSN1OctetString;
    }

    public static ASN1Primitive convertValueToObject(X509Extension x509Extension) throws IllegalArgumentException {
        try {
            return ASN1Primitive.fromByteArray(x509Extension.getValue().getOctets());
        } catch (IOException e) {
            throw new IllegalArgumentException("can't convert extension: " + e);
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof X509Extension)) {
            return false;
        }
        X509Extension x509Extension = (X509Extension) obj;
        return x509Extension.getValue().equals((ASN1Primitive) getValue()) && x509Extension.isCritical() == isCritical();
    }

    public ASN1Encodable getParsedValue() {
        return convertValueToObject(this);
    }

    public ASN1OctetString getValue() {
        return this.value;
    }

    public int hashCode() {
        return isCritical() ? getValue().hashCode() : getValue().hashCode() ^ -1;
    }

    public boolean isCritical() {
        return this.critical;
    }
}
