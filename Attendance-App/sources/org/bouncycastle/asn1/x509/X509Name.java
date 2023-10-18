package org.bouncycastle.asn1.x509;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.svg.SvgConstants;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import kotlin.UByte;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERUniversalString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class X509Name extends ASN1Object {
    public static final ASN1ObjectIdentifier BUSINESS_CATEGORY;

    /* renamed from: C */
    public static final ASN1ObjectIdentifier f87C;

    /* renamed from: CN */
    public static final ASN1ObjectIdentifier f88CN;
    public static final ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP;
    public static final ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE;
    public static final ASN1ObjectIdentifier DATE_OF_BIRTH;

    /* renamed from: DC */
    public static final ASN1ObjectIdentifier f89DC;
    public static final ASN1ObjectIdentifier DMD_NAME = new ASN1ObjectIdentifier("2.5.4.54");
    public static final ASN1ObjectIdentifier DN_QUALIFIER;
    public static final Hashtable DefaultLookUp;
    public static boolean DefaultReverse = false;
    public static final Hashtable DefaultSymbols;

    /* renamed from: E */
    public static final ASN1ObjectIdentifier f90E;
    public static final ASN1ObjectIdentifier EmailAddress;
    private static final Boolean FALSE = new Boolean(false);
    public static final ASN1ObjectIdentifier GENDER;
    public static final ASN1ObjectIdentifier GENERATION;
    public static final ASN1ObjectIdentifier GIVENNAME;
    public static final ASN1ObjectIdentifier INITIALS;

    /* renamed from: L */
    public static final ASN1ObjectIdentifier f91L;
    public static final ASN1ObjectIdentifier NAME;
    public static final ASN1ObjectIdentifier NAME_AT_BIRTH;

    /* renamed from: O */
    public static final ASN1ObjectIdentifier f92O;
    public static final Hashtable OIDLookUp;

    /* renamed from: OU */
    public static final ASN1ObjectIdentifier f93OU;
    public static final ASN1ObjectIdentifier PLACE_OF_BIRTH;
    public static final ASN1ObjectIdentifier POSTAL_ADDRESS;
    public static final ASN1ObjectIdentifier POSTAL_CODE;
    public static final ASN1ObjectIdentifier PSEUDONYM;
    public static final Hashtable RFC1779Symbols;
    public static final Hashtable RFC2253Symbols;
    public static final ASN1ObjectIdentifier SERIALNUMBER;

    /* renamed from: SN */
    public static final ASN1ObjectIdentifier f94SN;

    /* renamed from: ST */
    public static final ASN1ObjectIdentifier f95ST;
    public static final ASN1ObjectIdentifier STREET;
    public static final ASN1ObjectIdentifier SURNAME;
    public static final Hashtable SymbolLookUp;

    /* renamed from: T */
    public static final ASN1ObjectIdentifier f96T;
    public static final ASN1ObjectIdentifier TELEPHONE_NUMBER;
    private static final Boolean TRUE = new Boolean(true);
    public static final ASN1ObjectIdentifier UID;
    public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER;
    public static final ASN1ObjectIdentifier UnstructuredAddress;
    public static final ASN1ObjectIdentifier UnstructuredName;
    private Vector added;
    private X509NameEntryConverter converter;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private Vector ordering;
    private ASN1Sequence seq;
    private Vector values;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.5.4.6");
        f87C = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = new ASN1ObjectIdentifier("2.5.4.10");
        f92O = aSN1ObjectIdentifier2;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = new ASN1ObjectIdentifier("2.5.4.11");
        f93OU = aSN1ObjectIdentifier3;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = new ASN1ObjectIdentifier("2.5.4.12");
        f96T = aSN1ObjectIdentifier4;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = new ASN1ObjectIdentifier("2.5.4.3");
        f88CN = aSN1ObjectIdentifier5;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = new ASN1ObjectIdentifier("2.5.4.5");
        f94SN = aSN1ObjectIdentifier6;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = new ASN1ObjectIdentifier("2.5.4.9");
        STREET = aSN1ObjectIdentifier7;
        SERIALNUMBER = aSN1ObjectIdentifier6;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = new ASN1ObjectIdentifier("2.5.4.7");
        f91L = aSN1ObjectIdentifier8;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = new ASN1ObjectIdentifier("2.5.4.8");
        f95ST = aSN1ObjectIdentifier9;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = new ASN1ObjectIdentifier("2.5.4.4");
        SURNAME = aSN1ObjectIdentifier10;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = new ASN1ObjectIdentifier("2.5.4.42");
        GIVENNAME = aSN1ObjectIdentifier11;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = new ASN1ObjectIdentifier("2.5.4.43");
        INITIALS = aSN1ObjectIdentifier12;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = new ASN1ObjectIdentifier("2.5.4.44");
        GENERATION = aSN1ObjectIdentifier13;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = new ASN1ObjectIdentifier("2.5.4.45");
        UNIQUE_IDENTIFIER = aSN1ObjectIdentifier14;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = new ASN1ObjectIdentifier("2.5.4.15");
        BUSINESS_CATEGORY = aSN1ObjectIdentifier15;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = aSN1ObjectIdentifier15;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = new ASN1ObjectIdentifier("2.5.4.17");
        POSTAL_CODE = aSN1ObjectIdentifier17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = aSN1ObjectIdentifier17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = new ASN1ObjectIdentifier("2.5.4.46");
        DN_QUALIFIER = aSN1ObjectIdentifier19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = aSN1ObjectIdentifier19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = new ASN1ObjectIdentifier("2.5.4.65");
        PSEUDONYM = aSN1ObjectIdentifier21;
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = aSN1ObjectIdentifier21;
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1");
        DATE_OF_BIRTH = aSN1ObjectIdentifier23;
        ASN1ObjectIdentifier aSN1ObjectIdentifier24 = aSN1ObjectIdentifier23;
        ASN1ObjectIdentifier aSN1ObjectIdentifier25 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2");
        PLACE_OF_BIRTH = aSN1ObjectIdentifier25;
        ASN1ObjectIdentifier aSN1ObjectIdentifier26 = aSN1ObjectIdentifier25;
        ASN1ObjectIdentifier aSN1ObjectIdentifier27 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3");
        GENDER = aSN1ObjectIdentifier27;
        ASN1ObjectIdentifier aSN1ObjectIdentifier28 = aSN1ObjectIdentifier27;
        ASN1ObjectIdentifier aSN1ObjectIdentifier29 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4");
        COUNTRY_OF_CITIZENSHIP = aSN1ObjectIdentifier29;
        ASN1ObjectIdentifier aSN1ObjectIdentifier30 = aSN1ObjectIdentifier29;
        ASN1ObjectIdentifier aSN1ObjectIdentifier31 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5");
        COUNTRY_OF_RESIDENCE = aSN1ObjectIdentifier31;
        ASN1ObjectIdentifier aSN1ObjectIdentifier32 = aSN1ObjectIdentifier31;
        ASN1ObjectIdentifier aSN1ObjectIdentifier33 = new ASN1ObjectIdentifier("1.3.36.8.3.14");
        NAME_AT_BIRTH = aSN1ObjectIdentifier33;
        ASN1ObjectIdentifier aSN1ObjectIdentifier34 = aSN1ObjectIdentifier33;
        ASN1ObjectIdentifier aSN1ObjectIdentifier35 = new ASN1ObjectIdentifier("2.5.4.16");
        POSTAL_ADDRESS = aSN1ObjectIdentifier35;
        ASN1ObjectIdentifier aSN1ObjectIdentifier36 = aSN1ObjectIdentifier35;
        ASN1ObjectIdentifier aSN1ObjectIdentifier37 = X509ObjectIdentifiers.id_at_telephoneNumber;
        TELEPHONE_NUMBER = aSN1ObjectIdentifier37;
        ASN1ObjectIdentifier aSN1ObjectIdentifier38 = X509ObjectIdentifiers.id_at_name;
        NAME = aSN1ObjectIdentifier38;
        ASN1ObjectIdentifier aSN1ObjectIdentifier39 = aSN1ObjectIdentifier38;
        ASN1ObjectIdentifier aSN1ObjectIdentifier40 = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
        EmailAddress = aSN1ObjectIdentifier40;
        ASN1ObjectIdentifier aSN1ObjectIdentifier41 = aSN1ObjectIdentifier37;
        ASN1ObjectIdentifier aSN1ObjectIdentifier42 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
        UnstructuredName = aSN1ObjectIdentifier42;
        ASN1ObjectIdentifier aSN1ObjectIdentifier43 = aSN1ObjectIdentifier14;
        ASN1ObjectIdentifier aSN1ObjectIdentifier44 = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
        UnstructuredAddress = aSN1ObjectIdentifier44;
        f90E = aSN1ObjectIdentifier40;
        ASN1ObjectIdentifier aSN1ObjectIdentifier45 = aSN1ObjectIdentifier42;
        ASN1ObjectIdentifier aSN1ObjectIdentifier46 = aSN1ObjectIdentifier44;
        ASN1ObjectIdentifier aSN1ObjectIdentifier47 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
        f89DC = aSN1ObjectIdentifier47;
        ASN1ObjectIdentifier aSN1ObjectIdentifier48 = aSN1ObjectIdentifier13;
        ASN1ObjectIdentifier aSN1ObjectIdentifier49 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
        UID = aSN1ObjectIdentifier49;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        ASN1ObjectIdentifier aSN1ObjectIdentifier50 = aSN1ObjectIdentifier12;
        Hashtable hashtable2 = new Hashtable();
        RFC2253Symbols = hashtable2;
        Hashtable hashtable3 = hashtable2;
        Hashtable hashtable4 = new Hashtable();
        RFC1779Symbols = hashtable4;
        Hashtable hashtable5 = hashtable4;
        Hashtable hashtable6 = new Hashtable();
        DefaultLookUp = hashtable6;
        OIDLookUp = hashtable;
        SymbolLookUp = hashtable6;
        ASN1ObjectIdentifier aSN1ObjectIdentifier51 = aSN1ObjectIdentifier11;
        hashtable.put(aSN1ObjectIdentifier, SvgConstants.Attributes.PATH_DATA_CURVE_TO);
        hashtable.put(aSN1ObjectIdentifier2, "O");
        hashtable.put(aSN1ObjectIdentifier4, SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO);
        hashtable.put(aSN1ObjectIdentifier3, "OU");
        ASN1ObjectIdentifier aSN1ObjectIdentifier52 = aSN1ObjectIdentifier4;
        hashtable.put(aSN1ObjectIdentifier5, "CN");
        hashtable.put(aSN1ObjectIdentifier8, "L");
        Object obj = "L";
        hashtable.put(aSN1ObjectIdentifier9, "ST");
        hashtable.put(aSN1ObjectIdentifier6, "SERIALNUMBER");
        hashtable.put(aSN1ObjectIdentifier40, "E");
        hashtable.put(aSN1ObjectIdentifier47, "DC");
        hashtable.put(aSN1ObjectIdentifier49, "UID");
        ASN1ObjectIdentifier aSN1ObjectIdentifier53 = aSN1ObjectIdentifier6;
        hashtable.put(aSN1ObjectIdentifier7, "STREET");
        ASN1ObjectIdentifier aSN1ObjectIdentifier54 = aSN1ObjectIdentifier49;
        hashtable.put(aSN1ObjectIdentifier10, "SURNAME");
        ASN1ObjectIdentifier aSN1ObjectIdentifier55 = aSN1ObjectIdentifier10;
        hashtable.put(aSN1ObjectIdentifier51, "GIVENNAME");
        hashtable.put(aSN1ObjectIdentifier50, "INITIALS");
        hashtable.put(aSN1ObjectIdentifier48, "GENERATION");
        hashtable.put(aSN1ObjectIdentifier46, "unstructuredAddress");
        hashtable.put(aSN1ObjectIdentifier45, "unstructuredName");
        hashtable.put(aSN1ObjectIdentifier43, "UniqueIdentifier");
        hashtable.put(aSN1ObjectIdentifier20, "DN");
        hashtable.put(aSN1ObjectIdentifier22, "Pseudonym");
        hashtable.put(aSN1ObjectIdentifier36, "PostalAddress");
        hashtable.put(aSN1ObjectIdentifier34, "NameAtBirth");
        hashtable.put(aSN1ObjectIdentifier30, "CountryOfCitizenship");
        hashtable.put(aSN1ObjectIdentifier32, "CountryOfResidence");
        hashtable.put(aSN1ObjectIdentifier28, "Gender");
        hashtable.put(aSN1ObjectIdentifier26, "PlaceOfBirth");
        hashtable.put(aSN1ObjectIdentifier24, "DateOfBirth");
        hashtable.put(aSN1ObjectIdentifier18, "PostalCode");
        hashtable.put(aSN1ObjectIdentifier16, "BusinessCategory");
        hashtable.put(aSN1ObjectIdentifier41, "TelephoneNumber");
        hashtable.put(aSN1ObjectIdentifier39, XfdfConstants.NAME_CAPITAL);
        Hashtable hashtable7 = hashtable3;
        hashtable7.put(aSN1ObjectIdentifier, SvgConstants.Attributes.PATH_DATA_CURVE_TO);
        ASN1ObjectIdentifier aSN1ObjectIdentifier56 = aSN1ObjectIdentifier2;
        hashtable7.put(aSN1ObjectIdentifier56, "O");
        hashtable7.put(aSN1ObjectIdentifier3, "OU");
        Object obj2 = "CN";
        hashtable7.put(aSN1ObjectIdentifier5, obj2);
        ASN1ObjectIdentifier aSN1ObjectIdentifier57 = aSN1ObjectIdentifier5;
        hashtable7.put(aSN1ObjectIdentifier8, obj);
        Object obj3 = "ST";
        hashtable7.put(aSN1ObjectIdentifier9, obj3);
        hashtable7.put(aSN1ObjectIdentifier7, "STREET");
        hashtable7.put(aSN1ObjectIdentifier47, "DC");
        ASN1ObjectIdentifier aSN1ObjectIdentifier58 = aSN1ObjectIdentifier54;
        hashtable7.put(aSN1ObjectIdentifier58, "UID");
        Hashtable hashtable8 = hashtable5;
        hashtable8.put(aSN1ObjectIdentifier, SvgConstants.Attributes.PATH_DATA_CURVE_TO);
        hashtable8.put(aSN1ObjectIdentifier56, "O");
        hashtable8.put(aSN1ObjectIdentifier3, "OU");
        ASN1ObjectIdentifier aSN1ObjectIdentifier59 = aSN1ObjectIdentifier57;
        hashtable8.put(aSN1ObjectIdentifier59, obj2);
        hashtable8.put(aSN1ObjectIdentifier8, obj);
        hashtable8.put(aSN1ObjectIdentifier9, obj3);
        hashtable8.put(aSN1ObjectIdentifier7, "STREET");
        Hashtable hashtable9 = hashtable6;
        hashtable9.put(SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO, aSN1ObjectIdentifier);
        hashtable9.put("o", aSN1ObjectIdentifier56);
        hashtable9.put(SvgConstants.Attributes.PATH_DATA_REL_SHORTHAND_CURVE_TO, aSN1ObjectIdentifier52);
        hashtable9.put("ou", aSN1ObjectIdentifier3);
        hashtable9.put("cn", aSN1ObjectIdentifier59);
        hashtable9.put(SvgConstants.Attributes.PATH_DATA_REL_LINE_TO, aSN1ObjectIdentifier8);
        hashtable9.put("st", aSN1ObjectIdentifier9);
        ASN1ObjectIdentifier aSN1ObjectIdentifier60 = aSN1ObjectIdentifier53;
        hashtable9.put("sn", aSN1ObjectIdentifier60);
        hashtable9.put("serialnumber", aSN1ObjectIdentifier60);
        hashtable9.put("street", aSN1ObjectIdentifier7);
        ASN1ObjectIdentifier aSN1ObjectIdentifier61 = aSN1ObjectIdentifier40;
        hashtable9.put("emailaddress", aSN1ObjectIdentifier61);
        hashtable9.put("dc", aSN1ObjectIdentifier47);
        hashtable9.put("e", aSN1ObjectIdentifier61);
        hashtable9.put("uid", aSN1ObjectIdentifier58);
        hashtable9.put("surname", aSN1ObjectIdentifier55);
        hashtable9.put("givenname", aSN1ObjectIdentifier51);
        hashtable9.put("initials", aSN1ObjectIdentifier50);
        hashtable9.put("generation", aSN1ObjectIdentifier48);
        hashtable9.put("unstructuredaddress", aSN1ObjectIdentifier46);
        hashtable9.put("unstructuredname", aSN1ObjectIdentifier45);
        hashtable9.put("uniqueidentifier", aSN1ObjectIdentifier43);
        hashtable9.put("dn", aSN1ObjectIdentifier20);
        hashtable9.put("pseudonym", aSN1ObjectIdentifier22);
        hashtable9.put("postaladdress", aSN1ObjectIdentifier36);
        hashtable9.put("nameofbirth", aSN1ObjectIdentifier34);
        hashtable9.put("countryofcitizenship", aSN1ObjectIdentifier30);
        hashtable9.put("countryofresidence", aSN1ObjectIdentifier32);
        hashtable9.put("gender", aSN1ObjectIdentifier28);
        hashtable9.put("placeofbirth", aSN1ObjectIdentifier26);
        hashtable9.put("dateofbirth", aSN1ObjectIdentifier24);
        hashtable9.put("postalcode", aSN1ObjectIdentifier18);
        hashtable9.put("businesscategory", aSN1ObjectIdentifier16);
        hashtable9.put("telephonenumber", aSN1ObjectIdentifier41);
        hashtable9.put(XfdfConstants.NAME, aSN1ObjectIdentifier39);
    }

    protected X509Name() {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
    }

    public X509Name(String str) {
        this(DefaultReverse, DefaultLookUp, str);
    }

    public X509Name(String str, X509NameEntryConverter x509NameEntryConverter) {
        this(DefaultReverse, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(Hashtable hashtable) {
        this((Vector) null, hashtable);
    }

    public X509Name(Vector vector, Hashtable hashtable) {
        this(vector, hashtable, (X509NameEntryConverter) new X509DefaultEntryConverter());
    }

    public X509Name(Vector vector, Hashtable hashtable, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = x509NameEntryConverter;
        int i = 0;
        if (vector != null) {
            for (int i2 = 0; i2 != vector.size(); i2++) {
                this.ordering.addElement(vector.elementAt(i2));
                this.added.addElement(FALSE);
            }
        } else {
            Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                this.ordering.addElement(keys.nextElement());
                this.added.addElement(FALSE);
            }
        }
        while (i != this.ordering.size()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) this.ordering.elementAt(i);
            if (hashtable.get(aSN1ObjectIdentifier) != null) {
                this.values.addElement(hashtable.get(aSN1ObjectIdentifier));
                i++;
            } else {
                throw new IllegalArgumentException("No attribute for object id - " + aSN1ObjectIdentifier.getId() + " - passed to distinguished name");
            }
        }
    }

    public X509Name(Vector vector, Vector vector2) {
        this(vector, vector2, (X509NameEntryConverter) new X509DefaultEntryConverter());
    }

    public X509Name(Vector vector, Vector vector2, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = x509NameEntryConverter;
        if (vector.size() == vector2.size()) {
            for (int i = 0; i < vector.size(); i++) {
                this.ordering.addElement(vector.elementAt(i));
                this.values.addElement(vector2.elementAt(i));
                this.added.addElement(FALSE);
            }
            return;
        }
        throw new IllegalArgumentException("oids vector must be same length as values.");
    }

    public X509Name(ASN1Sequence aSN1Sequence) {
        Vector vector;
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.seq = aSN1Sequence;
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1Set instance = ASN1Set.getInstance(((ASN1Encodable) objects.nextElement()).toASN1Primitive());
            int i = 0;
            while (true) {
                if (i < instance.size()) {
                    ASN1Sequence instance2 = ASN1Sequence.getInstance(instance.getObjectAt(i).toASN1Primitive());
                    if (instance2.size() == 2) {
                        this.ordering.addElement(ASN1ObjectIdentifier.getInstance(instance2.getObjectAt(0)));
                        ASN1Encodable objectAt = instance2.getObjectAt(1);
                        if (!(objectAt instanceof ASN1String) || (objectAt instanceof DERUniversalString)) {
                            try {
                                this.values.addElement("#" + bytesToString(Hex.encode(objectAt.toASN1Primitive().getEncoded(ASN1Encoding.DER))));
                            } catch (IOException e) {
                                throw new IllegalArgumentException("cannot encode value");
                            }
                        } else {
                            String string = ((ASN1String) objectAt).getString();
                            if (string.length() <= 0 || string.charAt(0) != '#') {
                                vector = this.values;
                            } else {
                                vector = this.values;
                                string = "\\" + string;
                            }
                            vector.addElement(string);
                        }
                        this.added.addElement(i != 0 ? TRUE : FALSE);
                        i++;
                    } else {
                        throw new IllegalArgumentException("badly sized pair");
                    }
                }
            }
        }
    }

    public X509Name(boolean z, String str) {
        this(z, DefaultLookUp, str);
    }

    public X509Name(boolean z, String str, X509NameEntryConverter x509NameEntryConverter) {
        this(z, DefaultLookUp, str, x509NameEntryConverter);
    }

    public X509Name(boolean z, Hashtable hashtable, String str) {
        this(z, hashtable, str, new X509DefaultEntryConverter());
    }

    public X509Name(boolean z, Hashtable hashtable, String str, X509NameEntryConverter x509NameEntryConverter) {
        this.converter = null;
        this.ordering = new Vector();
        this.values = new Vector();
        this.added = new Vector();
        this.converter = x509NameEntryConverter;
        X509NameTokenizer x509NameTokenizer = new X509NameTokenizer(str);
        while (x509NameTokenizer.hasMoreTokens()) {
            String nextToken = x509NameTokenizer.nextToken();
            if (nextToken.indexOf(43) > 0) {
                X509NameTokenizer x509NameTokenizer2 = new X509NameTokenizer(nextToken, '+');
                String nextToken2 = x509NameTokenizer2.nextToken();
                Boolean bool = FALSE;
                while (true) {
                    addEntry(hashtable, nextToken2, bool);
                    if (!x509NameTokenizer2.hasMoreTokens()) {
                        break;
                    }
                    nextToken2 = x509NameTokenizer2.nextToken();
                    bool = TRUE;
                }
            } else {
                addEntry(hashtable, nextToken, FALSE);
            }
        }
        if (z) {
            Vector vector = new Vector();
            Vector vector2 = new Vector();
            Vector vector3 = new Vector();
            int i = 1;
            for (int i2 = 0; i2 < this.ordering.size(); i2++) {
                if (((Boolean) this.added.elementAt(i2)).booleanValue()) {
                    vector.insertElementAt(this.ordering.elementAt(i2), i);
                    vector2.insertElementAt(this.values.elementAt(i2), i);
                    vector3.insertElementAt(this.added.elementAt(i2), i);
                    i++;
                } else {
                    vector.insertElementAt(this.ordering.elementAt(i2), 0);
                    vector2.insertElementAt(this.values.elementAt(i2), 0);
                    vector3.insertElementAt(this.added.elementAt(i2), 0);
                    i = 1;
                }
            }
            this.ordering = vector;
            this.values = vector2;
            this.added = vector3;
        }
    }

    private void addEntry(Hashtable hashtable, String str, Boolean bool) {
        X509NameTokenizer x509NameTokenizer = new X509NameTokenizer(str, '=');
        String nextToken = x509NameTokenizer.nextToken();
        if (x509NameTokenizer.hasMoreTokens()) {
            String nextToken2 = x509NameTokenizer.nextToken();
            this.ordering.addElement(decodeOID(nextToken, hashtable));
            this.values.addElement(unescape(nextToken2));
            this.added.addElement(bool);
            return;
        }
        throw new IllegalArgumentException("badly formatted directory string");
    }

    private void appendValue(StringBuffer stringBuffer, Hashtable hashtable, ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        int i;
        String str2 = (String) hashtable.get(aSN1ObjectIdentifier);
        if (str2 == null) {
            str2 = aSN1ObjectIdentifier.getId();
        }
        stringBuffer.append(str2);
        stringBuffer.append('=');
        int length = stringBuffer.length();
        stringBuffer.append(str);
        int length2 = stringBuffer.length();
        if (str.length() >= 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
            length += 2;
        }
        while (i < length2 && stringBuffer.charAt(i) == ' ') {
            stringBuffer.insert(i, "\\");
            length = i + 2;
            length2++;
        }
        while (true) {
            length2--;
            if (length2 > i && stringBuffer.charAt(length2) == ' ') {
                stringBuffer.insert(length2, '\\');
            }
        }
        while (i <= length2) {
            switch (stringBuffer.charAt(i)) {
                case '\"':
                case '+':
                case ',':
                case ';':
                case '<':
                case '=':
                case '>':
                case '\\':
                    stringBuffer.insert(i, "\\");
                    i += 2;
                    length2++;
                    break;
                default:
                    i++;
                    break;
            }
        }
    }

    private String bytesToString(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i = 0; i != length; i++) {
            cArr[i] = (char) (bArr[i] & UByte.MAX_VALUE);
        }
        return new String(cArr);
    }

    private String canonicalize(String str) {
        String lowerCase = Strings.toLowerCase(str.trim());
        if (lowerCase.length() <= 0 || lowerCase.charAt(0) != '#') {
            return lowerCase;
        }
        ASN1Primitive decodeObject = decodeObject(lowerCase);
        return decodeObject instanceof ASN1String ? Strings.toLowerCase(((ASN1String) decodeObject).getString().trim()) : lowerCase;
    }

    private ASN1ObjectIdentifier decodeOID(String str, Hashtable hashtable) {
        String trim = str.trim();
        if (Strings.toUpperCase(trim).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(trim.substring(4));
        }
        if (trim.charAt(0) >= '0' && trim.charAt(0) <= '9') {
            return new ASN1ObjectIdentifier(trim);
        }
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) hashtable.get(Strings.toLowerCase(trim));
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        throw new IllegalArgumentException("Unknown object id - " + trim + " - passed to distinguished name");
    }

    private ASN1Primitive decodeObject(String str) {
        try {
            return ASN1Primitive.fromByteArray(Hex.decodeStrict(str, 1, str.length() - 1));
        } catch (IOException e) {
            throw new IllegalStateException("unknown encoding in name: " + e);
        }
    }

    private boolean equivalentStrings(String str, String str2) {
        String canonicalize = canonicalize(str);
        String canonicalize2 = canonicalize(str2);
        return canonicalize.equals(canonicalize2) || stripInternalSpaces(canonicalize).equals(stripInternalSpaces(canonicalize2));
    }

    public static X509Name getInstance(Object obj) {
        if (obj == null || (obj instanceof X509Name)) {
            return (X509Name) obj;
        }
        if (obj instanceof X500Name) {
            return new X509Name(ASN1Sequence.getInstance(((X500Name) obj).toASN1Primitive()));
        }
        if (obj != null) {
            return new X509Name(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static X509Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    private String stripInternalSpaces(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() != 0) {
            char charAt = str.charAt(0);
            stringBuffer.append(charAt);
            int i = 1;
            while (i < str.length()) {
                char charAt2 = str.charAt(i);
                if (charAt != ' ' || charAt2 != ' ') {
                    stringBuffer.append(charAt2);
                }
                i++;
                charAt = charAt2;
            }
        }
        return stringBuffer.toString();
    }

    private String unescape(String str) {
        int i;
        if (str.length() == 0 || (str.indexOf(92) < 0 && str.indexOf(34) < 0)) {
            return str.trim();
        }
        char[] charArray = str.toCharArray();
        StringBuffer stringBuffer = new StringBuffer(str.length());
        if (charArray[0] == '\\' && charArray[1] == '#') {
            stringBuffer.append("\\#");
            i = 2;
        } else {
            i = 0;
        }
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i != charArray.length) {
            char c = charArray[i];
            if (c != ' ') {
                z3 = true;
            }
            if (c == '\"') {
                if (!z) {
                    z2 = !z2;
                    z = false;
                    i++;
                }
            } else if (c == '\\' && !z && !z2) {
                i2 = stringBuffer.length();
                z = true;
                i++;
            } else if (c == ' ' && !z && !z3) {
                i++;
            }
            stringBuffer.append(c);
            z = false;
            i++;
        }
        if (stringBuffer.length() > 0) {
            while (stringBuffer.charAt(stringBuffer.length() - 1) == ' ' && i2 != stringBuffer.length() - 1) {
                stringBuffer.setLength(stringBuffer.length() - 1);
            }
        }
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        int i;
        int i2;
        int i3;
        boolean z;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            X509Name instance = getInstance(obj);
            int size = this.ordering.size();
            if (size != instance.ordering.size()) {
                return false;
            }
            boolean[] zArr = new boolean[size];
            if (this.ordering.elementAt(0).equals(instance.ordering.elementAt(0))) {
                i2 = size;
                i3 = 0;
                i = 1;
            } else {
                i3 = size - 1;
                i2 = -1;
                i = -1;
            }
            while (i3 != i2) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) this.ordering.elementAt(i3);
                String str = (String) this.values.elementAt(i3);
                int i4 = 0;
                while (true) {
                    if (i4 >= size) {
                        z = false;
                        break;
                    } else if (!zArr[i4] && aSN1ObjectIdentifier.equals((ASN1Primitive) (ASN1ObjectIdentifier) instance.ordering.elementAt(i4)) && equivalentStrings(str, (String) instance.values.elementAt(i4))) {
                        zArr[i4] = true;
                        z = true;
                        break;
                    } else {
                        i4++;
                    }
                }
                if (!z) {
                    return false;
                }
                i3 += i;
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean equals(Object obj, boolean z) {
        if (!z) {
            return equals(obj);
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X509Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            X509Name instance = getInstance(obj);
            int size = this.ordering.size();
            if (size != instance.ordering.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!((ASN1ObjectIdentifier) this.ordering.elementAt(i)).equals((ASN1Primitive) (ASN1ObjectIdentifier) instance.ordering.elementAt(i)) || !equivalentStrings((String) this.values.elementAt(i), (String) instance.values.elementAt(i))) {
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Vector getOIDs() {
        Vector vector = new Vector();
        for (int i = 0; i != this.ordering.size(); i++) {
            vector.addElement(this.ordering.elementAt(i));
        }
        return vector;
    }

    public Vector getValues() {
        Vector vector = new Vector();
        for (int i = 0; i != this.values.size(); i++) {
            vector.addElement(this.values.elementAt(i));
        }
        return vector;
    }

    public Vector getValues(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Vector vector = new Vector();
        for (int i = 0; i != this.values.size(); i++) {
            if (this.ordering.elementAt(i).equals(aSN1ObjectIdentifier)) {
                String str = (String) this.values.elementAt(i);
                if (str.length() > 2 && str.charAt(0) == '\\' && str.charAt(1) == '#') {
                    str = str.substring(1);
                }
                vector.addElement(str);
            }
        }
        return vector;
    }

    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        for (int i = 0; i != this.ordering.size(); i++) {
            String stripInternalSpaces = stripInternalSpaces(canonicalize((String) this.values.elementAt(i)));
            int hashCode = this.hashCodeValue ^ this.ordering.elementAt(i).hashCode();
            this.hashCodeValue = hashCode;
            this.hashCodeValue = stripInternalSpaces.hashCode() ^ hashCode;
        }
        return this.hashCodeValue;
    }

    public ASN1Primitive toASN1Primitive() {
        DERSequence dERSequence;
        if (this.seq == null) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            ASN1ObjectIdentifier aSN1ObjectIdentifier = null;
            int i = 0;
            while (i != this.ordering.size()) {
                ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector(2);
                ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) this.ordering.elementAt(i);
                aSN1EncodableVector3.add(aSN1ObjectIdentifier2);
                aSN1EncodableVector3.add(this.converter.getConvertedValue(aSN1ObjectIdentifier2, (String) this.values.elementAt(i)));
                if (aSN1ObjectIdentifier == null || ((Boolean) this.added.elementAt(i)).booleanValue()) {
                    dERSequence = new DERSequence(aSN1EncodableVector3);
                } else {
                    aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
                    aSN1EncodableVector2 = new ASN1EncodableVector();
                    dERSequence = new DERSequence(aSN1EncodableVector3);
                }
                aSN1EncodableVector2.add(dERSequence);
                i++;
                aSN1ObjectIdentifier = aSN1ObjectIdentifier2;
            }
            aSN1EncodableVector.add(new DERSet(aSN1EncodableVector2));
            this.seq = new DERSequence(aSN1EncodableVector);
        }
        return this.seq;
    }

    public String toString() {
        return toString(DefaultReverse, DefaultSymbols);
    }

    public String toString(boolean z, Hashtable hashtable) {
        StringBuffer stringBuffer = new StringBuffer();
        Vector vector = new Vector();
        StringBuffer stringBuffer2 = null;
        for (int i = 0; i < this.ordering.size(); i++) {
            if (((Boolean) this.added.elementAt(i)).booleanValue()) {
                stringBuffer2.append('+');
                appendValue(stringBuffer2, hashtable, (ASN1ObjectIdentifier) this.ordering.elementAt(i), (String) this.values.elementAt(i));
            } else {
                stringBuffer2 = new StringBuffer();
                appendValue(stringBuffer2, hashtable, (ASN1ObjectIdentifier) this.ordering.elementAt(i), (String) this.values.elementAt(i));
                vector.addElement(stringBuffer2);
            }
        }
        boolean z2 = true;
        if (z) {
            for (int size = vector.size() - 1; size >= 0; size--) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(size).toString());
            }
        } else {
            for (int i2 = 0; i2 < vector.size(); i2++) {
                if (z2) {
                    z2 = false;
                } else {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(i2).toString());
            }
        }
        return stringBuffer.toString();
    }
}
