package com.itextpdf.signatures;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.svg.SvgConstants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class CertificateInfo {

    public static class X500Name {

        /* renamed from: C */
        public static final ASN1ObjectIdentifier f1594C;

        /* renamed from: CN */
        public static final ASN1ObjectIdentifier f1595CN;

        /* renamed from: DC */
        public static final ASN1ObjectIdentifier f1596DC;
        public static final Map<ASN1ObjectIdentifier, String> DefaultSymbols;

        /* renamed from: E */
        public static final ASN1ObjectIdentifier f1597E;
        public static final ASN1ObjectIdentifier EmailAddress;
        public static final ASN1ObjectIdentifier GENERATION;
        public static final ASN1ObjectIdentifier GIVENNAME;
        public static final ASN1ObjectIdentifier INITIALS;

        /* renamed from: L */
        public static final ASN1ObjectIdentifier f1598L;

        /* renamed from: O */
        public static final ASN1ObjectIdentifier f1599O;

        /* renamed from: OU */
        public static final ASN1ObjectIdentifier f1600OU;

        /* renamed from: SN */
        public static final ASN1ObjectIdentifier f1601SN;

        /* renamed from: ST */
        public static final ASN1ObjectIdentifier f1602ST;
        public static final ASN1ObjectIdentifier SURNAME;

        /* renamed from: T */
        public static final ASN1ObjectIdentifier f1603T;
        public static final ASN1ObjectIdentifier UID;
        public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER = new ASN1ObjectIdentifier("2.5.4.45");
        public Map<String, List<String>> values = new HashMap();

        static {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.5.4.6");
            f1594C = aSN1ObjectIdentifier;
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = new ASN1ObjectIdentifier("2.5.4.10");
            f1599O = aSN1ObjectIdentifier2;
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = new ASN1ObjectIdentifier("2.5.4.11");
            f1600OU = aSN1ObjectIdentifier3;
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = new ASN1ObjectIdentifier("2.5.4.12");
            f1603T = aSN1ObjectIdentifier4;
            ASN1ObjectIdentifier aSN1ObjectIdentifier5 = new ASN1ObjectIdentifier("2.5.4.3");
            f1595CN = aSN1ObjectIdentifier5;
            ASN1ObjectIdentifier aSN1ObjectIdentifier6 = new ASN1ObjectIdentifier("2.5.4.5");
            f1601SN = aSN1ObjectIdentifier6;
            ASN1ObjectIdentifier aSN1ObjectIdentifier7 = new ASN1ObjectIdentifier("2.5.4.7");
            f1598L = aSN1ObjectIdentifier7;
            ASN1ObjectIdentifier aSN1ObjectIdentifier8 = new ASN1ObjectIdentifier("2.5.4.8");
            f1602ST = aSN1ObjectIdentifier8;
            ASN1ObjectIdentifier aSN1ObjectIdentifier9 = new ASN1ObjectIdentifier("2.5.4.4");
            SURNAME = aSN1ObjectIdentifier9;
            ASN1ObjectIdentifier aSN1ObjectIdentifier10 = new ASN1ObjectIdentifier("2.5.4.42");
            GIVENNAME = aSN1ObjectIdentifier10;
            ASN1ObjectIdentifier aSN1ObjectIdentifier11 = new ASN1ObjectIdentifier("2.5.4.43");
            INITIALS = aSN1ObjectIdentifier11;
            ASN1ObjectIdentifier aSN1ObjectIdentifier12 = new ASN1ObjectIdentifier("2.5.4.44");
            GENERATION = aSN1ObjectIdentifier12;
            ASN1ObjectIdentifier aSN1ObjectIdentifier13 = new ASN1ObjectIdentifier("1.2.840.113549.1.9.1");
            EmailAddress = aSN1ObjectIdentifier13;
            f1597E = aSN1ObjectIdentifier13;
            ASN1ObjectIdentifier aSN1ObjectIdentifier14 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
            f1596DC = aSN1ObjectIdentifier14;
            ASN1ObjectIdentifier aSN1ObjectIdentifier15 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
            UID = aSN1ObjectIdentifier15;
            HashMap hashMap = new HashMap();
            DefaultSymbols = hashMap;
            hashMap.put(aSN1ObjectIdentifier, SvgConstants.Attributes.PATH_DATA_CURVE_TO);
            hashMap.put(aSN1ObjectIdentifier2, "O");
            hashMap.put(aSN1ObjectIdentifier4, SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO);
            hashMap.put(aSN1ObjectIdentifier3, "OU");
            hashMap.put(aSN1ObjectIdentifier5, "CN");
            hashMap.put(aSN1ObjectIdentifier7, "L");
            hashMap.put(aSN1ObjectIdentifier8, "ST");
            hashMap.put(aSN1ObjectIdentifier6, "SN");
            hashMap.put(aSN1ObjectIdentifier13, "E");
            hashMap.put(aSN1ObjectIdentifier14, "DC");
            hashMap.put(aSN1ObjectIdentifier15, "UID");
            hashMap.put(aSN1ObjectIdentifier9, "SURNAME");
            hashMap.put(aSN1ObjectIdentifier10, "GIVENNAME");
            hashMap.put(aSN1ObjectIdentifier11, "INITIALS");
            hashMap.put(aSN1ObjectIdentifier12, "GENERATION");
        }

        public X500Name(ASN1Sequence seq) {
            Enumeration e = seq.getObjects();
            while (e.hasMoreElements()) {
                ASN1Set set = (ASN1Set) e.nextElement();
                for (int i = 0; i < set.size(); i++) {
                    ASN1Sequence s = (ASN1Sequence) set.getObjectAt(i);
                    String id = DefaultSymbols.get((ASN1ObjectIdentifier) s.getObjectAt(0));
                    if (id != null) {
                        List<String> vs = this.values.get(id);
                        if (vs == null) {
                            vs = new ArrayList<>();
                            this.values.put(id, vs);
                        }
                        vs.add(((ASN1String) s.getObjectAt(1)).getString());
                    }
                }
            }
        }

        public X500Name(String dirName) {
            X509NameTokenizer nTok = new X509NameTokenizer(dirName);
            while (nTok.hasMoreTokens()) {
                String token = nTok.nextToken();
                int index = token.indexOf(61);
                if (index != -1) {
                    String id = token.substring(0, index).toUpperCase();
                    String value = token.substring(index + 1);
                    List<String> vs = this.values.get(id);
                    if (vs == null) {
                        vs = new ArrayList<>();
                        this.values.put(id, vs);
                    }
                    vs.add(value);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }

        public String getField(String name) {
            List<String> vs = this.values.get(name);
            if (vs == null) {
                return null;
            }
            return vs.get(0);
        }

        public List<String> getFieldArray(String name) {
            return this.values.get(name);
        }

        public Map<String, List<String>> getFields() {
            return this.values;
        }

        public String toString() {
            return this.values.toString();
        }
    }

    public static class X509NameTokenizer {
        private StringBuffer buf = new StringBuffer();
        private int index;
        private String oid;

        public X509NameTokenizer(String oid2) {
            this.oid = oid2;
            this.index = -1;
        }

        public boolean hasMoreTokens() {
            return this.index != this.oid.length();
        }

        public String nextToken() {
            if (this.index == this.oid.length()) {
                return null;
            }
            int end = this.index + 1;
            boolean quoted = false;
            boolean escaped = false;
            this.buf.setLength(0);
            while (end != this.oid.length()) {
                char c = this.oid.charAt(end);
                if (c == '\"') {
                    if (!escaped) {
                        quoted = !quoted;
                    } else {
                        this.buf.append(c);
                    }
                    escaped = false;
                } else if (escaped || quoted) {
                    this.buf.append(c);
                    escaped = false;
                } else if (c == '\\') {
                    escaped = true;
                } else if (c == ',') {
                    break;
                } else {
                    this.buf.append(c);
                }
                end++;
            }
            this.index = end;
            return this.buf.toString().trim();
        }
    }

    public static X500Name getIssuerFields(X509Certificate cert) {
        try {
            return new X500Name((ASN1Sequence) getIssuer(cert.getTBSCertificate()));
        } catch (Exception e) {
            throw new PdfException((Throwable) e);
        }
    }

    public static ASN1Primitive getIssuer(byte[] enc) {
        try {
            ASN1Sequence seq = (ASN1Sequence) new ASN1InputStream((InputStream) new ByteArrayInputStream(enc)).readObject();
            return (ASN1Primitive) seq.getObjectAt(seq.getObjectAt(0) instanceof ASN1TaggedObject ? 3 : 2);
        } catch (IOException e) {
            throw new PdfException((Throwable) e);
        }
    }

    public static X500Name getSubjectFields(X509Certificate cert) {
        if (cert == null) {
            return null;
        }
        try {
            return new X500Name((ASN1Sequence) getSubject(cert.getTBSCertificate()));
        } catch (Exception e) {
            throw new PdfException((Throwable) e);
        }
    }

    public static ASN1Primitive getSubject(byte[] enc) {
        try {
            ASN1Sequence seq = (ASN1Sequence) new ASN1InputStream((InputStream) new ByteArrayInputStream(enc)).readObject();
            return (ASN1Primitive) seq.getObjectAt(seq.getObjectAt(0) instanceof ASN1TaggedObject ? 5 : 4);
        } catch (IOException e) {
            throw new PdfException((Throwable) e);
        }
    }
}
