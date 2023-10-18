package org.bouncycastle.asn1.x500.style;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.svg.SvgConstants;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameStyle;

public class RFC4519Style extends AbstractX500NameStyle {
    private static final Hashtable DefaultLookUp;
    private static final Hashtable DefaultSymbols;
    public static final X500NameStyle INSTANCE = new RFC4519Style();
    public static final ASN1ObjectIdentifier businessCategory;

    /* renamed from: c */
    public static final ASN1ObjectIdentifier f72c;

    /* renamed from: cn */
    public static final ASN1ObjectIdentifier f73cn;

    /* renamed from: dc */
    public static final ASN1ObjectIdentifier f74dc;
    public static final ASN1ObjectIdentifier description;
    public static final ASN1ObjectIdentifier destinationIndicator;
    public static final ASN1ObjectIdentifier distinguishedName;
    public static final ASN1ObjectIdentifier dnQualifier;
    public static final ASN1ObjectIdentifier enhancedSearchGuide;
    public static final ASN1ObjectIdentifier facsimileTelephoneNumber;
    public static final ASN1ObjectIdentifier generationQualifier;
    public static final ASN1ObjectIdentifier givenName;
    public static final ASN1ObjectIdentifier houseIdentifier;
    public static final ASN1ObjectIdentifier initials;
    public static final ASN1ObjectIdentifier internationalISDNNumber;

    /* renamed from: l */
    public static final ASN1ObjectIdentifier f75l;
    public static final ASN1ObjectIdentifier member;
    public static final ASN1ObjectIdentifier name;

    /* renamed from: o */
    public static final ASN1ObjectIdentifier f76o;

    /* renamed from: ou */
    public static final ASN1ObjectIdentifier f77ou;
    public static final ASN1ObjectIdentifier owner;
    public static final ASN1ObjectIdentifier physicalDeliveryOfficeName;
    public static final ASN1ObjectIdentifier postOfficeBox;
    public static final ASN1ObjectIdentifier postalAddress;
    public static final ASN1ObjectIdentifier postalCode;
    public static final ASN1ObjectIdentifier preferredDeliveryMethod;
    public static final ASN1ObjectIdentifier registeredAddress;
    public static final ASN1ObjectIdentifier roleOccupant;
    public static final ASN1ObjectIdentifier searchGuide;
    public static final ASN1ObjectIdentifier seeAlso;
    public static final ASN1ObjectIdentifier serialNumber;

    /* renamed from: sn */
    public static final ASN1ObjectIdentifier f78sn;

    /* renamed from: st */
    public static final ASN1ObjectIdentifier f79st;
    public static final ASN1ObjectIdentifier street;
    public static final ASN1ObjectIdentifier telephoneNumber;
    public static final ASN1ObjectIdentifier teletexTerminalIdentifier;
    public static final ASN1ObjectIdentifier telexNumber;
    public static final ASN1ObjectIdentifier title;
    public static final ASN1ObjectIdentifier uid;
    public static final ASN1ObjectIdentifier uniqueMember;
    public static final ASN1ObjectIdentifier userPassword;
    public static final ASN1ObjectIdentifier x121Address;
    public static final ASN1ObjectIdentifier x500UniqueIdentifier;
    protected final Hashtable defaultLookUp = copyHashTable(DefaultLookUp);
    protected final Hashtable defaultSymbols = copyHashTable(DefaultSymbols);

    static {
        ASN1ObjectIdentifier intern = new ASN1ObjectIdentifier("2.5.4.15").intern();
        businessCategory = intern;
        ASN1ObjectIdentifier intern2 = new ASN1ObjectIdentifier("2.5.4.6").intern();
        f72c = intern2;
        ASN1ObjectIdentifier intern3 = new ASN1ObjectIdentifier("2.5.4.3").intern();
        f73cn = intern3;
        ASN1ObjectIdentifier intern4 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25").intern();
        f74dc = intern4;
        ASN1ObjectIdentifier intern5 = new ASN1ObjectIdentifier("2.5.4.13").intern();
        description = intern5;
        ASN1ObjectIdentifier intern6 = new ASN1ObjectIdentifier("2.5.4.27").intern();
        destinationIndicator = intern6;
        ASN1ObjectIdentifier intern7 = new ASN1ObjectIdentifier("2.5.4.49").intern();
        distinguishedName = intern7;
        ASN1ObjectIdentifier intern8 = new ASN1ObjectIdentifier("2.5.4.46").intern();
        dnQualifier = intern8;
        ASN1ObjectIdentifier intern9 = new ASN1ObjectIdentifier("2.5.4.47").intern();
        enhancedSearchGuide = intern9;
        ASN1ObjectIdentifier intern10 = new ASN1ObjectIdentifier("2.5.4.23").intern();
        facsimileTelephoneNumber = intern10;
        ASN1ObjectIdentifier intern11 = new ASN1ObjectIdentifier("2.5.4.44").intern();
        generationQualifier = intern11;
        ASN1ObjectIdentifier intern12 = new ASN1ObjectIdentifier("2.5.4.42").intern();
        givenName = intern12;
        ASN1ObjectIdentifier intern13 = new ASN1ObjectIdentifier("2.5.4.51").intern();
        houseIdentifier = intern13;
        ASN1ObjectIdentifier intern14 = new ASN1ObjectIdentifier("2.5.4.43").intern();
        initials = intern14;
        ASN1ObjectIdentifier intern15 = new ASN1ObjectIdentifier("2.5.4.25").intern();
        internationalISDNNumber = intern15;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = intern15;
        ASN1ObjectIdentifier intern16 = new ASN1ObjectIdentifier("2.5.4.7").intern();
        f75l = intern16;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = intern16;
        ASN1ObjectIdentifier intern17 = new ASN1ObjectIdentifier("2.5.4.31").intern();
        member = intern17;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = intern17;
        ASN1ObjectIdentifier intern18 = new ASN1ObjectIdentifier("2.5.4.41").intern();
        name = intern18;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = intern18;
        ASN1ObjectIdentifier intern19 = new ASN1ObjectIdentifier("2.5.4.10").intern();
        f76o = intern19;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = intern19;
        ASN1ObjectIdentifier intern20 = new ASN1ObjectIdentifier("2.5.4.11").intern();
        f77ou = intern20;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = intern20;
        ASN1ObjectIdentifier intern21 = new ASN1ObjectIdentifier("2.5.4.32").intern();
        owner = intern21;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = intern21;
        ASN1ObjectIdentifier intern22 = new ASN1ObjectIdentifier("2.5.4.19").intern();
        physicalDeliveryOfficeName = intern22;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = intern22;
        ASN1ObjectIdentifier intern23 = new ASN1ObjectIdentifier("2.5.4.16").intern();
        postalAddress = intern23;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = intern23;
        ASN1ObjectIdentifier intern24 = new ASN1ObjectIdentifier("2.5.4.17").intern();
        postalCode = intern24;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = intern24;
        ASN1ObjectIdentifier intern25 = new ASN1ObjectIdentifier("2.5.4.18").intern();
        postOfficeBox = intern25;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = intern25;
        ASN1ObjectIdentifier intern26 = new ASN1ObjectIdentifier("2.5.4.28").intern();
        preferredDeliveryMethod = intern26;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = intern26;
        ASN1ObjectIdentifier intern27 = new ASN1ObjectIdentifier("2.5.4.26").intern();
        registeredAddress = intern27;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = intern27;
        ASN1ObjectIdentifier intern28 = new ASN1ObjectIdentifier("2.5.4.33").intern();
        roleOccupant = intern28;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = intern28;
        ASN1ObjectIdentifier intern29 = new ASN1ObjectIdentifier("2.5.4.14").intern();
        searchGuide = intern29;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = intern29;
        ASN1ObjectIdentifier intern30 = new ASN1ObjectIdentifier("2.5.4.34").intern();
        seeAlso = intern30;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = intern30;
        ASN1ObjectIdentifier intern31 = new ASN1ObjectIdentifier("2.5.4.5").intern();
        serialNumber = intern31;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = intern31;
        ASN1ObjectIdentifier intern32 = new ASN1ObjectIdentifier("2.5.4.4").intern();
        f78sn = intern32;
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = intern32;
        ASN1ObjectIdentifier intern33 = new ASN1ObjectIdentifier("2.5.4.8").intern();
        f79st = intern33;
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = intern33;
        ASN1ObjectIdentifier intern34 = new ASN1ObjectIdentifier("2.5.4.9").intern();
        street = intern34;
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = intern34;
        ASN1ObjectIdentifier intern35 = new ASN1ObjectIdentifier("2.5.4.20").intern();
        telephoneNumber = intern35;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = intern35;
        ASN1ObjectIdentifier intern36 = new ASN1ObjectIdentifier("2.5.4.22").intern();
        teletexTerminalIdentifier = intern36;
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = intern36;
        ASN1ObjectIdentifier intern37 = new ASN1ObjectIdentifier("2.5.4.21").intern();
        telexNumber = intern37;
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = intern37;
        ASN1ObjectIdentifier intern38 = new ASN1ObjectIdentifier("2.5.4.12").intern();
        title = intern38;
        ASN1ObjectIdentifier aSN1ObjectIdentifier24 = intern38;
        ASN1ObjectIdentifier intern39 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1").intern();
        uid = intern39;
        ASN1ObjectIdentifier aSN1ObjectIdentifier25 = intern39;
        ASN1ObjectIdentifier intern40 = new ASN1ObjectIdentifier("2.5.4.50").intern();
        uniqueMember = intern40;
        ASN1ObjectIdentifier aSN1ObjectIdentifier26 = intern40;
        ASN1ObjectIdentifier intern41 = new ASN1ObjectIdentifier("2.5.4.35").intern();
        userPassword = intern41;
        ASN1ObjectIdentifier aSN1ObjectIdentifier27 = intern41;
        ASN1ObjectIdentifier intern42 = new ASN1ObjectIdentifier("2.5.4.24").intern();
        x121Address = intern42;
        ASN1ObjectIdentifier aSN1ObjectIdentifier28 = intern42;
        ASN1ObjectIdentifier intern43 = new ASN1ObjectIdentifier("2.5.4.45").intern();
        x500UniqueIdentifier = intern43;
        Hashtable hashtable = new Hashtable();
        DefaultSymbols = hashtable;
        ASN1ObjectIdentifier aSN1ObjectIdentifier29 = intern43;
        Hashtable hashtable2 = new Hashtable();
        DefaultLookUp = hashtable2;
        hashtable.put(intern, "businessCategory");
        hashtable.put(intern2, SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO);
        hashtable.put(intern3, "cn");
        hashtable.put(intern4, "dc");
        hashtable.put(intern5, PdfConst.Description);
        Object obj = PdfConst.Description;
        hashtable.put(intern6, "destinationIndicator");
        hashtable.put(intern7, "distinguishedName");
        hashtable.put(intern8, "dnQualifier");
        hashtable.put(intern9, "enhancedSearchGuide");
        hashtable.put(intern10, "facsimileTelephoneNumber");
        hashtable.put(intern11, "generationQualifier");
        hashtable.put(intern12, "givenName");
        hashtable.put(intern13, "houseIdentifier");
        hashtable.put(intern14, "initials");
        ASN1ObjectIdentifier aSN1ObjectIdentifier30 = intern14;
        hashtable.put(aSN1ObjectIdentifier, "internationalISDNNumber");
        ASN1ObjectIdentifier aSN1ObjectIdentifier31 = aSN1ObjectIdentifier2;
        hashtable.put(aSN1ObjectIdentifier31, SvgConstants.Attributes.PATH_DATA_REL_LINE_TO);
        Object obj2 = SvgConstants.Attributes.PATH_DATA_REL_LINE_TO;
        ASN1ObjectIdentifier aSN1ObjectIdentifier32 = aSN1ObjectIdentifier31;
        ASN1ObjectIdentifier aSN1ObjectIdentifier33 = aSN1ObjectIdentifier3;
        hashtable.put(aSN1ObjectIdentifier33, "member");
        ASN1ObjectIdentifier aSN1ObjectIdentifier34 = aSN1ObjectIdentifier33;
        ASN1ObjectIdentifier aSN1ObjectIdentifier35 = aSN1ObjectIdentifier4;
        hashtable.put(aSN1ObjectIdentifier35, XfdfConstants.NAME);
        Object obj3 = XfdfConstants.NAME;
        ASN1ObjectIdentifier aSN1ObjectIdentifier36 = aSN1ObjectIdentifier35;
        ASN1ObjectIdentifier aSN1ObjectIdentifier37 = aSN1ObjectIdentifier5;
        hashtable.put(aSN1ObjectIdentifier37, "o");
        ASN1ObjectIdentifier aSN1ObjectIdentifier38 = aSN1ObjectIdentifier37;
        ASN1ObjectIdentifier aSN1ObjectIdentifier39 = aSN1ObjectIdentifier6;
        hashtable.put(aSN1ObjectIdentifier39, "ou");
        ASN1ObjectIdentifier aSN1ObjectIdentifier40 = aSN1ObjectIdentifier39;
        ASN1ObjectIdentifier aSN1ObjectIdentifier41 = aSN1ObjectIdentifier7;
        hashtable.put(aSN1ObjectIdentifier41, "owner");
        ASN1ObjectIdentifier aSN1ObjectIdentifier42 = aSN1ObjectIdentifier41;
        hashtable.put(aSN1ObjectIdentifier8, "physicalDeliveryOfficeName");
        hashtable.put(aSN1ObjectIdentifier9, "postalAddress");
        hashtable.put(aSN1ObjectIdentifier10, "postalCode");
        hashtable.put(aSN1ObjectIdentifier11, "postOfficeBox");
        hashtable.put(aSN1ObjectIdentifier12, "preferredDeliveryMethod");
        hashtable.put(aSN1ObjectIdentifier13, "registeredAddress");
        hashtable.put(aSN1ObjectIdentifier14, "roleOccupant");
        hashtable.put(aSN1ObjectIdentifier15, "searchGuide");
        hashtable.put(aSN1ObjectIdentifier16, "seeAlso");
        hashtable.put(aSN1ObjectIdentifier17, "serialNumber");
        ASN1ObjectIdentifier aSN1ObjectIdentifier43 = aSN1ObjectIdentifier18;
        hashtable.put(aSN1ObjectIdentifier43, "sn");
        ASN1ObjectIdentifier aSN1ObjectIdentifier44 = aSN1ObjectIdentifier43;
        ASN1ObjectIdentifier aSN1ObjectIdentifier45 = aSN1ObjectIdentifier19;
        hashtable.put(aSN1ObjectIdentifier45, "st");
        ASN1ObjectIdentifier aSN1ObjectIdentifier46 = aSN1ObjectIdentifier45;
        ASN1ObjectIdentifier aSN1ObjectIdentifier47 = aSN1ObjectIdentifier20;
        hashtable.put(aSN1ObjectIdentifier47, "street");
        ASN1ObjectIdentifier aSN1ObjectIdentifier48 = aSN1ObjectIdentifier47;
        hashtable.put(aSN1ObjectIdentifier21, "telephoneNumber");
        hashtable.put(aSN1ObjectIdentifier22, "teletexTerminalIdentifier");
        hashtable.put(aSN1ObjectIdentifier23, "telexNumber");
        ASN1ObjectIdentifier aSN1ObjectIdentifier49 = aSN1ObjectIdentifier24;
        hashtable.put(aSN1ObjectIdentifier49, "title");
        ASN1ObjectIdentifier aSN1ObjectIdentifier50 = aSN1ObjectIdentifier49;
        ASN1ObjectIdentifier aSN1ObjectIdentifier51 = aSN1ObjectIdentifier25;
        hashtable.put(aSN1ObjectIdentifier51, "uid");
        ASN1ObjectIdentifier aSN1ObjectIdentifier52 = aSN1ObjectIdentifier51;
        hashtable.put(aSN1ObjectIdentifier26, "uniqueMember");
        hashtable.put(aSN1ObjectIdentifier27, "userPassword");
        hashtable.put(aSN1ObjectIdentifier28, "x121Address");
        ASN1ObjectIdentifier aSN1ObjectIdentifier53 = aSN1ObjectIdentifier29;
        hashtable.put(aSN1ObjectIdentifier53, "x500UniqueIdentifier");
        Hashtable hashtable3 = hashtable2;
        hashtable3.put("businesscategory", intern);
        hashtable3.put(SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO, intern2);
        hashtable3.put("cn", intern3);
        hashtable3.put("dc", intern4);
        hashtable3.put(obj, intern5);
        hashtable3.put("destinationindicator", intern6);
        hashtable3.put("distinguishedname", intern7);
        hashtable3.put("dnqualifier", intern8);
        hashtable3.put("enhancedsearchguide", intern9);
        hashtable3.put("facsimiletelephonenumber", intern10);
        hashtable3.put("generationqualifier", intern11);
        hashtable3.put("givenname", intern12);
        hashtable3.put("houseidentifier", intern13);
        hashtable3.put("initials", aSN1ObjectIdentifier30);
        hashtable3.put("internationalisdnnumber", aSN1ObjectIdentifier);
        hashtable3.put(obj2, aSN1ObjectIdentifier32);
        hashtable3.put("member", aSN1ObjectIdentifier34);
        hashtable3.put(obj3, aSN1ObjectIdentifier36);
        hashtable3.put("o", aSN1ObjectIdentifier38);
        hashtable3.put("ou", aSN1ObjectIdentifier40);
        hashtable3.put("owner", aSN1ObjectIdentifier42);
        hashtable3.put("physicaldeliveryofficename", aSN1ObjectIdentifier8);
        hashtable3.put("postaladdress", aSN1ObjectIdentifier9);
        hashtable3.put("postalcode", aSN1ObjectIdentifier10);
        hashtable3.put("postofficebox", aSN1ObjectIdentifier11);
        hashtable3.put("preferreddeliverymethod", aSN1ObjectIdentifier12);
        hashtable3.put("registeredaddress", aSN1ObjectIdentifier13);
        hashtable3.put("roleoccupant", aSN1ObjectIdentifier14);
        hashtable3.put("searchguide", aSN1ObjectIdentifier15);
        hashtable3.put("seealso", aSN1ObjectIdentifier16);
        hashtable3.put("serialnumber", aSN1ObjectIdentifier17);
        hashtable3.put("sn", aSN1ObjectIdentifier44);
        hashtable3.put("st", aSN1ObjectIdentifier46);
        hashtable3.put("street", aSN1ObjectIdentifier48);
        hashtable3.put("telephonenumber", aSN1ObjectIdentifier21);
        hashtable3.put("teletexterminalidentifier", aSN1ObjectIdentifier22);
        hashtable3.put("telexnumber", aSN1ObjectIdentifier23);
        hashtable3.put("title", aSN1ObjectIdentifier50);
        hashtable3.put("uid", aSN1ObjectIdentifier52);
        hashtable3.put("uniquemember", aSN1ObjectIdentifier26);
        hashtable3.put("userpassword", aSN1ObjectIdentifier27);
        hashtable3.put("x121address", aSN1ObjectIdentifier28);
        hashtable3.put("x500uniqueidentifier", aSN1ObjectIdentifier53);
    }

    protected RFC4519Style() {
    }

    public ASN1ObjectIdentifier attrNameToOID(String str) {
        return IETFUtils.decodeAttrName(str, this.defaultLookUp);
    }

    /* access modifiers changed from: protected */
    public ASN1Encodable encodeStringValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        return aSN1ObjectIdentifier.equals((ASN1Primitive) f74dc) ? new DERIA5String(str) : (aSN1ObjectIdentifier.equals((ASN1Primitive) f72c) || aSN1ObjectIdentifier.equals((ASN1Primitive) serialNumber) || aSN1ObjectIdentifier.equals((ASN1Primitive) dnQualifier) || aSN1ObjectIdentifier.equals((ASN1Primitive) telephoneNumber)) ? new DERPrintableString(str) : super.encodeStringValue(aSN1ObjectIdentifier, str);
    }

    public RDN[] fromString(String str) {
        RDN[] rDNsFromString = IETFUtils.rDNsFromString(str, this);
        int length = rDNsFromString.length;
        RDN[] rdnArr = new RDN[length];
        for (int i = 0; i != rDNsFromString.length; i++) {
            rdnArr[(length - i) - 1] = rDNsFromString[i];
        }
        return rdnArr;
    }

    public String[] oidToAttrNames(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return IETFUtils.findAttrNamesForOID(aSN1ObjectIdentifier, this.defaultLookUp);
    }

    public String oidToDisplayName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String) DefaultSymbols.get(aSN1ObjectIdentifier);
    }

    public String toString(X500Name x500Name) {
        StringBuffer stringBuffer = new StringBuffer();
        RDN[] rDNs = x500Name.getRDNs();
        boolean z = true;
        for (int length = rDNs.length - 1; length >= 0; length--) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(',');
            }
            IETFUtils.appendRDN(stringBuffer, rDNs[length], this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}
