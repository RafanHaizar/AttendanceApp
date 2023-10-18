package com.itextpdf.kernel.pdf;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.font.constants.FontStretches;
import com.itextpdf.p026io.image.PngImageHelperConstants;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.source.ByteUtils;
import com.itextpdf.styledxmlparser.css.media.MediaRuleConstants;
import com.itextpdf.svg.SvgConstants;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class PdfName extends PdfPrimitiveObject implements Comparable<PdfName> {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* renamed from: A */
    public static final PdfName f1287A = createDirectName(SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A);
    public static final PdfName A85 = createDirectName("A85");

    /* renamed from: AA */
    public static final PdfName f1288AA = createDirectName("AA");
    public static final PdfName ADBE = createDirectName("ADBE");
    public static final PdfName AESV2 = createDirectName("AESV2");
    public static final PdfName AESV3 = createDirectName("AESV3");

    /* renamed from: AF */
    public static final PdfName f1289AF = createDirectName("AF");
    public static final PdfName AFRelationship = createDirectName("AFRelationship");
    public static final PdfName AHx = createDirectName("AHx");
    public static final PdfName AIS = createDirectName("AIS");

    /* renamed from: AN */
    public static final PdfName f1290AN = createDirectName("AN");

    /* renamed from: AP */
    public static final PdfName f1291AP = createDirectName("AP");

    /* renamed from: AS */
    public static final PdfName f1292AS = createDirectName("AS");
    public static final PdfName ASCII85Decode = createDirectName("ASCII85Decode");
    public static final PdfName ASCIIHexDecode = createDirectName("ASCIIHexDecode");
    public static final PdfName AbsoluteColorimetric = createDirectName("AbsoluteColorimetric");
    public static final PdfName AcroForm = createDirectName("AcroForm");
    public static final PdfName Action = createDirectName(XfdfConstants.ACTION);
    public static final PdfName ActualText = createDirectName("ActualText");
    public static final PdfName Adbe_pkcs7_detached = createDirectName("adbe.pkcs7.detached");
    public static final PdfName Adbe_pkcs7_s4 = createDirectName("adbe.pkcs7.s4");
    public static final PdfName Adbe_pkcs7_s5 = createDirectName("adbe.pkcs7.s5");
    public static final PdfName Adbe_pkcs7_sha1 = createDirectName("adbe.pkcs7.sha1");
    public static final PdfName Adbe_x509_rsa_sha1 = createDirectName("adbe.x509.rsa_sha1");
    public static final PdfName Adobe_PPKLite = createDirectName("Adobe.PPKLite");
    public static final PdfName Adobe_PPKMS = createDirectName("Adobe.PPKMS");
    public static final PdfName Adobe_PubSec = createDirectName("Adobe.PubSec");
    public static final PdfName After = createDirectName("After");
    public static final PdfName Alaw = createDirectName("ALaw");
    public static final PdfName All = createDirectName("All");
    public static final PdfName AllOff = createDirectName("AllOff");
    public static final PdfName AllOn = createDirectName("AllOn");
    public static final PdfName Alt = createDirectName("Alt");
    public static final PdfName Alternate = createDirectName("Alternate");
    public static final PdfName AlternatePresentations = createDirectName("AlternatePresentations");
    public static final PdfName Alternates = createDirectName("Alternates");
    public static final PdfName Alternative = createDirectName("Alternative");
    public static final PdfName And = createDirectName("And");
    public static final PdfName Annot = createDirectName(StandardRoles.ANNOT);
    public static final PdfName AnnotStates = createDirectName("AnnotStates");
    public static final PdfName Annotation = createDirectName("Annotation");
    public static final PdfName Annots = createDirectName("Annots");
    public static final PdfName AnyOff = createDirectName("AnyOff");
    public static final PdfName AnyOn = createDirectName("AnyOn");
    public static final PdfName App = createDirectName("App");
    public static final PdfName AppDefault = createDirectName("AppDefault");
    public static final PdfName ApplicationOctetStream = createDirectName("application/octet-stream");
    public static final PdfName ApplicationPdf = createDirectName("application/pdf");
    public static final PdfName ApplicationXml = createDirectName("application/xml");
    public static final PdfName Approved = createDirectName("Approved");
    public static final PdfName Art = createDirectName(StandardRoles.ART);
    public static final PdfName ArtBox = createDirectName("ArtBox");
    public static final PdfName Artifact = createDirectName(StandardRoles.ARTIFACT);
    public static final PdfName AsIs = createDirectName("AsIs");
    public static final PdfName Ascent = createDirectName("Ascent");
    public static final PdfName Aside = createDirectName(StandardRoles.ASIDE);
    public static final PdfName AuthEvent = createDirectName("AuthEvent");
    public static final PdfName Author = createDirectName("Author");

    /* renamed from: B */
    public static final PdfName f1293B = createDirectName(SvgConstants.Attributes.PATH_DATA_BEARING);
    public static final PdfName BBox = createDirectName("BBox");

    /* renamed from: BC */
    public static final PdfName f1294BC = createDirectName(BouncyCastleProvider.PROVIDER_NAME);

    /* renamed from: BE */
    public static final PdfName f1295BE = createDirectName("BE");

    /* renamed from: BG */
    public static final PdfName f1296BG = createDirectName("BG");
    public static final PdfName BG2 = createDirectName("BG2");

    /* renamed from: BM */
    public static final PdfName f1297BM = createDirectName("BM");

    /* renamed from: BS */
    public static final PdfName f1298BS = createDirectName("BS");
    public static final PdfName BackgroundColor = createDirectName("BackgroundColor");
    public static final PdfName BaseEncoding = createDirectName("BaseEncoding");
    public static final PdfName BaseFont = createDirectName("BaseFont");
    public static final PdfName BaseState = createDirectName("BaseState");
    public static final PdfName BaseVersion = createDirectName("BaseVersion");
    public static final PdfName BaselineShift = createDirectName("BaselineShift");
    public static final PdfName Bates = createDirectName("Bates");
    public static final PdfName Before = createDirectName("Before");
    public static final PdfName BibEntry = createDirectName(StandardRoles.BIBENTRY);
    public static final PdfName BitsPerComponent = createDirectName(PngImageHelperConstants.BITS_PER_COMPONENT);
    public static final PdfName BitsPerCoordinate = createDirectName("BitsPerCoordinate");
    public static final PdfName BitsPerFlag = createDirectName("BitsPerFlag");
    public static final PdfName BitsPerSample = createDirectName("BitsPerSample");

    /* renamed from: Bl */
    public static final PdfName f1299Bl = createDirectName("Bl");
    public static final PdfName BlackIs1 = createDirectName("BlackIs1");
    public static final PdfName BlackPoint = createDirectName("BlackPoint");
    public static final PdfName BleedBox = createDirectName("BleedBox");
    public static final PdfName Block = createDirectName("Block");
    public static final PdfName BlockAlign = createDirectName("BlockAlign");
    public static final PdfName BlockQuote = createDirectName(StandardRoles.BLOCKQUOTE);
    public static final PdfName Book = createDirectName("Book");
    public static final PdfName Border = createDirectName("Border");
    public static final PdfName BorderColor = createDirectName("BorderColor");
    public static final PdfName BorderStyle = createDirectName("BorderStyle");
    public static final PdfName BorderThickness = createDirectName("BorderThickness");
    public static final PdfName Both = createDirectName("Both");
    public static final PdfName Bounds = createDirectName("Bounds");
    public static final PdfName Btn = createDirectName("Btn");
    public static final PdfName Butt = createDirectName("Butt");
    public static final PdfName ByteRange = createDirectName("ByteRange");

    /* renamed from: C */
    public static final PdfName f1300C = createDirectName(SvgConstants.Attributes.PATH_DATA_CURVE_TO);

    /* renamed from: C0 */
    public static final PdfName f1301C0 = createDirectName("C0");

    /* renamed from: C1 */
    public static final PdfName f1302C1 = createDirectName("C1");

    /* renamed from: CA */
    public static final PdfName f1303CA = createDirectName("CA");
    public static final PdfName CCITTFaxDecode = createDirectName("CCITTFaxDecode");

    /* renamed from: CF */
    public static final PdfName f1304CF = createDirectName("CF");
    public static final PdfName CFM = createDirectName("CFM");

    /* renamed from: CI */
    public static final PdfName f1305CI = createDirectName("CI");
    public static final PdfName CIDFontType0 = createDirectName("CIDFontType0");
    public static final PdfName CIDFontType2 = createDirectName("CIDFontType2");
    public static final PdfName CIDSet = createDirectName("CIDSet");
    public static final PdfName CIDSystemInfo = createDirectName("CIDSystemInfo");
    public static final PdfName CIDToGIDMap = createDirectName("CIDToGIDMap");

    /* renamed from: CL */
    public static final PdfName f1306CL = createDirectName("CL");
    public static final PdfName CMapName = createDirectName("CMapName");

    /* renamed from: CO */
    public static final PdfName f1307CO = createDirectName("CO");

    /* renamed from: CP */
    public static final PdfName f1308CP = createDirectName("CP");
    public static final PdfName CRL = createDirectName("CRL");
    public static final PdfName CRLs = createDirectName("CRLs");

    /* renamed from: CS */
    public static final PdfName f1309CS = createDirectName("CS");

    /* renamed from: CT */
    public static final PdfName f1310CT = createDirectName("CT");
    public static final PdfName CalGray = createDirectName("CalGray");
    public static final PdfName CalRGB = createDirectName("CalRGB");
    public static final PdfName Cap = createDirectName("Cap");
    public static final PdfName CapHeight = createDirectName("CapHeight");
    public static final PdfName Caption = createDirectName(StandardRoles.CAPTION);
    public static final PdfName Caret = createDirectName("Caret");
    public static final PdfName Catalog = createDirectName("Catalog");
    public static final PdfName Category = createDirectName("Category");
    public static final PdfName Center = createDirectName("Center");
    public static final PdfName CenterWindow = createDirectName("CenterWindow");
    public static final PdfName Cert = createDirectName("Cert");
    public static final PdfName Certs = createDirectName("Certs");

    /* renamed from: Ch */
    public static final PdfName f1311Ch = createDirectName("Ch");
    public static final PdfName CharProcs = createDirectName("CharProcs");
    public static final PdfName Circle = createDirectName("Circle");
    public static final PdfName ClosedArrow = createDirectName("ClosedArrow");
    public static final PdfName Code = createDirectName(StandardRoles.CODE);
    public static final PdfName ColSpan = createDirectName("ColSpan");
    public static final PdfName Collection = createDirectName("Collection");
    public static final PdfName Color = createDirectName("Color");
    public static final PdfName ColorBurn = createDirectName("ColorBurn");
    public static final PdfName ColorDodge = createDirectName("ColorDodge");
    public static final PdfName ColorSpace = createDirectName("ColorSpace");
    public static final PdfName ColorTransform = createDirectName("ColorTransform");
    public static final PdfName Colorants = createDirectName("Colorants");
    public static final PdfName Colors = createDirectName(PngImageHelperConstants.COLORS);
    public static final PdfName Column = createDirectName("Column");
    public static final PdfName ColumnCount = createDirectName("ColumnCount");
    public static final PdfName ColumnGap = createDirectName("ColumnGap");
    public static final PdfName ColumnWidths = createDirectName("ColumnWidths");
    public static final PdfName Columns = createDirectName(PngImageHelperConstants.COLUMNS);
    public static final PdfName Compatible = createDirectName("Compatible");
    public static final PdfName Confidential = createDirectName("Confidential");
    public static final PdfName Configs = createDirectName("Configs");
    public static final PdfName ContactInfo = createDirectName("ContactInfo");
    public static final PdfName Contents = createDirectName("Contents");
    public static final PdfName Coords = createDirectName("Coords");
    public static final PdfName Count = createDirectName("Count");
    public static final PdfName CreationDate = createDirectName("CreationDate");
    public static final PdfName Creator = createDirectName("Creator");
    public static final PdfName CreatorInfo = createDirectName("CreatorInfo");
    public static final PdfName CropBox = createDirectName("CropBox");
    public static final PdfName Crypt = createDirectName("Crypt");

    /* renamed from: D */
    public static final PdfName f1312D = createDirectName("D");

    /* renamed from: DA */
    public static final PdfName f1313DA = createDirectName("DA");
    public static final PdfName DCTDecode = createDirectName("DCTDecode");

    /* renamed from: DP */
    public static final PdfName f1314DP = createDirectName("DP");
    public static final PdfName DPart = createDirectName("DPart");

    /* renamed from: DR */
    public static final PdfName f1315DR = createDirectName("DR");

    /* renamed from: DS */
    public static final PdfName f1316DS = createDirectName("DS");
    public static final PdfName DSS = createDirectName("DSS");

    /* renamed from: DV */
    public static final PdfName f1317DV = createDirectName("DV");

    /* renamed from: DW */
    public static final PdfName f1318DW = createDirectName("DW");
    public static final PdfName Darken = createDirectName("Darken");
    public static final PdfName Dashed = createDirectName("Dashed");
    public static final PdfName Data = createDirectName("Data");
    public static final PdfName Decimal = createDirectName("Decimal");
    public static final PdfName Decode = createDirectName("Decode");
    public static final PdfName DecodeParms = createDirectName("DecodeParms");
    public static final PdfName Default = createDirectName("Default");
    public static final PdfName DefaultCMYK = createDirectName("DefaultCMYK");
    public static final PdfName DefaultCryptFilter = createDirectName("DefaultCryptFilter");
    public static final PdfName DefaultGray = createDirectName("DefaultGray");
    public static final PdfName DefaultRGB = createDirectName("DefaultRGB");
    public static final PdfName Departmental = createDirectName("Departmental");
    public static final PdfName Desc = createDirectName("Desc");
    public static final PdfName DescendantFonts = createDirectName("DescendantFonts");
    public static final PdfName Descent = createDirectName("Descent");
    public static final PdfName Design = createDirectName("Design");
    public static final PdfName Dest = createDirectName(XfdfConstants.DEST);
    public static final PdfName DestOutputProfile = createDirectName("DestOutputProfile");
    public static final PdfName Dests = createDirectName("Dests");
    public static final PdfName DeviceCMY = createDirectName("DeviceCMY");
    public static final PdfName DeviceCMYK = createDirectName("DeviceCMYK");
    public static final PdfName DeviceGray = createDirectName("DeviceGray");
    public static final PdfName DeviceN = createDirectName("DeviceN");
    public static final PdfName DeviceRGB = createDirectName("DeviceRGB");
    public static final PdfName DeviceRGBK = createDirectName("DeviceRGBK");
    public static final PdfName Diamond = createDirectName("Diamond");
    public static final PdfName Difference = createDirectName("Difference");
    public static final PdfName Differences = createDirectName("Differences");
    public static final PdfName DigestLocation = createDirectName("DigestLocation");
    public static final PdfName DigestMethod = createDirectName("DigestMethod");
    public static final PdfName DigestValue = createDirectName("DigestValue");
    public static final PdfName Direction = createDirectName("Direction");
    public static final PdfName Disc = createDirectName("Disc");
    public static final PdfName DisplayDocTitle = createDirectName("DisplayDocTitle");
    public static final PdfName Div = createDirectName(StandardRoles.DIV);
    public static final PdfName DocMDP = createDirectName("DocMDP");
    public static final PdfName DocOpen = createDirectName("DocOpen");
    public static final PdfName DocTimeStamp = createDirectName("DocTimeStamp");
    public static final PdfName Document = createDirectName(StandardRoles.DOCUMENT);
    public static final PdfName DocumentFragment = createDirectName(StandardRoles.DOCUMENTFRAGMENT);
    public static final PdfName Domain = createDirectName("Domain");
    public static final PdfName Dotted = createDirectName("Dotted");
    public static final PdfName Double = createDirectName("Double");

    /* renamed from: Dp */
    public static final PdfName f1319Dp = createDirectName("Dp");
    public static final PdfName Draft = createDirectName("Draft");
    public static final PdfName Duplex = createDirectName("Duplex");
    public static final PdfName DuplexFlipLongEdge = createDirectName("DuplexFlipLongEdge");
    public static final PdfName DuplexFlipShortEdge = createDirectName("DuplexFlipShortEdge");

    /* renamed from: E */
    public static final PdfName f1320E = createDirectName("E");

    /* renamed from: EF */
    public static final PdfName f1321EF = createDirectName("EF");
    public static final PdfName EFF = createDirectName("EFF");
    public static final PdfName EFOpen = createDirectName("EFOpen");

    /* renamed from: EP */
    public static final PdfName f1322EP = createDirectName("EP");
    public static final PdfName ESIC = createDirectName("ESIC");
    public static final PdfName ETSI_CAdES_DETACHED = createDirectName("ETSI.CAdES.detached");
    public static final PdfName ETSI_RFC3161 = createDirectName("ETSI.RFC3161");

    /* renamed from: Em */
    public static final PdfName f1323Em = createDirectName(StandardRoles.f1501EM);
    public static final PdfName EmbeddedFile = createDirectName("EmbeddedFile");
    public static final PdfName EmbeddedFiles = createDirectName("EmbeddedFiles");
    public static final PdfName Encode = createDirectName("Encode");
    public static final PdfName EncodedByteAlign = createDirectName("EncodedByteAlign");
    public static final PdfName Encoding = createDirectName("Encoding");
    public static final PdfName Encrypt = createDirectName("Encrypt");
    public static final PdfName EncryptMetadata = createDirectName("EncryptMetadata");
    public static final PdfName EncryptedPayload = createDirectName("EncryptedPayload");
    public static final PdfName End = createDirectName("End");
    public static final PdfName EndIndent = createDirectName("EndIndent");
    public static final PdfName EndOfBlock = createDirectName("EndOfBlock");
    public static final PdfName EndOfLine = createDirectName("EndOfLine");
    public static final PdfName Enforce = createDirectName("Enforce");
    public static final PdfName Event = createDirectName("Event");
    public static final PdfName ExData = createDirectName("ExData");
    public static final PdfName Exclude = createDirectName("Exclude");
    public static final PdfName Exclusion = createDirectName("Exclusion");
    public static final PdfName Experimental = createDirectName("Experimental");
    public static final PdfName Expired = createDirectName("Expired");
    public static final PdfName Export = createDirectName("Export");
    public static final PdfName ExportState = createDirectName("ExportState");
    public static final PdfName ExtGState = createDirectName("ExtGState");
    public static final PdfName Extend = createDirectName("Extend");
    public static final PdfName Extends = createDirectName("Extends");
    public static final PdfName ExtensionLevel = createDirectName("ExtensionLevel");
    public static final PdfName Extensions = createDirectName("Extensions");

    /* renamed from: F */
    public static final PdfName f1324F = createDirectName("F");
    public static final PdfName FDecodeParams = createDirectName("FDecodeParams");
    public static final PdfName FENote = createDirectName(StandardRoles.FENOTE);
    public static final PdfName FFilter = createDirectName("FFilter");

    /* renamed from: FL */
    public static final PdfName f1325FL = createDirectName("FL");

    /* renamed from: FS */
    public static final PdfName f1326FS = createDirectName("FS");

    /* renamed from: FT */
    public static final PdfName f1327FT = createDirectName("FT");
    public static final PdfName False = createDirectName("false");

    /* renamed from: Ff */
    public static final PdfName f1328Ff = createDirectName("Ff");
    public static final PdfName FieldMDP = createDirectName("FieldMDP");
    public static final PdfName Fields = createDirectName("Fields");
    public static final PdfName Figure = createDirectName(StandardRoles.FIGURE);
    public static final PdfName FileAttachment = createDirectName("FileAttachment");
    public static final PdfName Filespec = createDirectName("Filespec");
    public static final PdfName Filter = createDirectName("Filter");
    public static final PdfName Final = createDirectName("Final");
    public static final PdfName First = createDirectName("First");
    public static final PdfName FirstChar = createDirectName("FirstChar");
    public static final PdfName FirstPage = createDirectName("FirstPage");
    public static final PdfName Fit = createDirectName(XfdfConstants.FIT);
    public static final PdfName FitB = createDirectName(XfdfConstants.FIT_B);
    public static final PdfName FitBH = createDirectName(XfdfConstants.FIT_BH);
    public static final PdfName FitBV = createDirectName(XfdfConstants.FIT_BV);
    public static final PdfName FitH = createDirectName(XfdfConstants.FIT_H);
    public static final PdfName FitR = createDirectName(XfdfConstants.FIT_R);
    public static final PdfName FitV = createDirectName(XfdfConstants.FIT_V);
    public static final PdfName FitWindow = createDirectName("FitWindow");
    public static final PdfName FixedPrint = createDirectName("FixedPrint");

    /* renamed from: Fl */
    public static final PdfName f1329Fl = createDirectName("Fl");
    public static final PdfName Flags = createDirectName("Flags");
    public static final PdfName FlateDecode = createDirectName("FlateDecode");

    /* renamed from: Fo */
    public static final PdfName f1330Fo = createDirectName("Fo");
    public static final PdfName Font = createDirectName("Font");
    public static final PdfName FontBBox = createDirectName("FontBBox");
    public static final PdfName FontDescriptor = createDirectName("FontDescriptor");
    public static final PdfName FontFamily = createDirectName("FontFamily");
    public static final PdfName FontFauxing = createDirectName("FontFauxing");
    public static final PdfName FontFile = createDirectName("FontFile");
    public static final PdfName FontFile2 = createDirectName("FontFile2");
    public static final PdfName FontFile3 = createDirectName("FontFile3");
    public static final PdfName FontMatrix = createDirectName("FontMatrix");
    public static final PdfName FontName = createDirectName("FontName");
    public static final PdfName FontStretch = createDirectName("FontStretch");
    public static final PdfName FontWeight = createDirectName("FontWeight");
    public static final PdfName Footer = createDirectName("Footer");
    public static final PdfName ForComment = createDirectName("ForComment");
    public static final PdfName ForPublicRelease = createDirectName("ForPublicRelease");
    public static final PdfName Form = createDirectName(StandardRoles.FORM);
    public static final PdfName FormData = createDirectName("FormData");
    public static final PdfName FormType = createDirectName("FormType");
    public static final PdfName Formula = createDirectName(StandardRoles.FORMULA);
    public static final PdfName FreeText = createDirectName("FreeText");
    public static final PdfName FreeTextCallout = createDirectName("FreeTextCallout");
    public static final PdfName FreeTextTypeWriter = createDirectName("FreeTextTypeWriter");
    public static final PdfName FullScreen = createDirectName("FullScreen");
    public static final PdfName Function = createDirectName("Function");
    public static final PdfName FunctionType = createDirectName("FunctionType");
    public static final PdfName Functions = createDirectName("Functions");
    public static final PdfName GTS_PDFA1 = createDirectName("GTS_PDFA1");
    public static final PdfName Gamma = createDirectName("Gamma");
    public static final PdfName GlyphOrientationVertical = createDirectName("GlyphOrientationVertical");
    public static final PdfName GoTo = createDirectName(XfdfConstants.GO_TO);
    public static final PdfName GoTo3DView = createDirectName("GoTo3DView");
    public static final PdfName GoToDp = createDirectName("GoToDp");
    public static final PdfName GoToE = createDirectName("GoToE");
    public static final PdfName GoToR = createDirectName(XfdfConstants.GO_TO_R);
    public static final PdfName Graph = createDirectName("Graph");
    public static final PdfName Groove = createDirectName("Groove");
    public static final PdfName Group = createDirectName("Group");

    /* renamed from: H */
    public static final PdfName f1331H = createDirectName("H");

    /* renamed from: H1 */
    public static final PdfName f1332H1 = createDirectName(StandardRoles.f1503H1);

    /* renamed from: H2 */
    public static final PdfName f1333H2 = createDirectName(StandardRoles.f1504H2);

    /* renamed from: H3 */
    public static final PdfName f1334H3 = createDirectName(StandardRoles.f1505H3);

    /* renamed from: H4 */
    public static final PdfName f1335H4 = createDirectName(StandardRoles.f1506H4);

    /* renamed from: H5 */
    public static final PdfName f1336H5 = createDirectName(StandardRoles.f1507H5);

    /* renamed from: H6 */
    public static final PdfName f1337H6 = createDirectName(StandardRoles.f1508H6);

    /* renamed from: HT */
    public static final PdfName f1338HT = createDirectName("HT");
    public static final PdfName HTO = createDirectName("HTO");
    public static final PdfName HTP = createDirectName("HTP");
    public static final PdfName HalftoneName = createDirectName("HalftoneName");
    public static final PdfName HalftoneType = createDirectName("HalftoneType");
    public static final PdfName HardLight = createDirectName("HardLight");
    public static final PdfName Header = createDirectName("Header");
    public static final PdfName Headers = createDirectName("Headers");
    public static final PdfName Height = createDirectName("Height");
    public static final PdfName Hidden = createDirectName("Hidden");
    public static final PdfName Hide = createDirectName("Hide");
    public static final PdfName HideMenubar = createDirectName("HideMenubar");
    public static final PdfName HideToolbar = createDirectName("HideToolbar");
    public static final PdfName HideWindowUI = createDirectName("HideWindowUI");
    public static final PdfName Highlight = createDirectName("Highlight");
    public static final PdfName Hue = createDirectName("Hue");

    /* renamed from: I */
    public static final PdfName f1339I = createDirectName("I");

    /* renamed from: IC */
    public static final PdfName f1340IC = createDirectName("IC");
    public static final PdfName ICCBased = createDirectName("ICCBased");

    /* renamed from: ID */
    public static final PdfName f1341ID = createDirectName("ID");
    public static final PdfName IDS = createDirectName("IDS");
    public static final PdfName IRT = createDirectName("IRT");

    /* renamed from: IT */
    public static final PdfName f1342IT = createDirectName("IT");
    public static final PdfName Identity = createDirectName("Identity");
    public static final PdfName IdentityH = createDirectName(PdfEncodings.IDENTITY_H);
    public static final PdfName Image = createDirectName("Image");
    public static final PdfName ImageMask = createDirectName("ImageMask");
    public static final PdfName ImportData = createDirectName("ImportData");
    public static final PdfName Include = createDirectName("Include");
    public static final PdfName Index = createDirectName(StandardRoles.INDEX);
    public static final PdfName Indexed = createDirectName("Indexed");
    public static final PdfName Info = createDirectName("Info");
    public static final PdfName Ink = createDirectName("Ink");
    public static final PdfName InkList = createDirectName("InkList");
    public static final PdfName Inline = createDirectName("Inline");
    public static final PdfName InlineAlign = createDirectName("InlineAlign");
    public static final PdfName Inset = createDirectName("Inset");
    public static final PdfName Intent = createDirectName(PngImageHelperConstants.INTENT);
    public static final PdfName Interpolate = createDirectName("Interpolate");
    public static final PdfName IsMap = createDirectName(XfdfConstants.IS_MAP);
    public static final PdfName ItalicAngle = createDirectName("ItalicAngle");
    public static final PdfName JBIG2Decode = createDirectName("JBIG2Decode");
    public static final PdfName JBIG2Globals = createDirectName("JBIG2Globals");
    public static final PdfName JPXDecode = createDirectName("JPXDecode");

    /* renamed from: JS */
    public static final PdfName f1343JS = createDirectName("JS");
    public static final PdfName JavaScript = createDirectName("JavaScript");
    public static final PdfName Justify = createDirectName("Justify");

    /* renamed from: K */
    public static final PdfName f1344K = createDirectName("K");
    public static final PdfName Keywords = createDirectName(PdfConst.Keywords);
    public static final PdfName Kids = createDirectName("Kids");

    /* renamed from: L */
    public static final PdfName f1345L = createDirectName("L");
    public static final PdfName L2R = createDirectName("L2R");
    public static final PdfName LBody = createDirectName(StandardRoles.LBODY);

    /* renamed from: LC */
    public static final PdfName f1346LC = createDirectName("LC");

    /* renamed from: LE */
    public static final PdfName f1347LE = createDirectName("LE");

    /* renamed from: LI */
    public static final PdfName f1348LI = createDirectName(StandardRoles.f1510LI);

    /* renamed from: LJ */
    public static final PdfName f1349LJ = createDirectName("LJ");

    /* renamed from: LL */
    public static final PdfName f1350LL = createDirectName("LL");
    public static final PdfName LLE = createDirectName("LLE");
    public static final PdfName LLO = createDirectName("LLO");

    /* renamed from: LW */
    public static final PdfName f1351LW = createDirectName("LW");
    public static final PdfName LZWDecode = createDirectName("LZWDecode");
    public static final PdfName Lab = createDirectName("Lab");
    public static final PdfName Lang = createDirectName("Lang");
    public static final PdfName Language = createDirectName("Language");
    public static final PdfName Last = createDirectName("Last");
    public static final PdfName LastChar = createDirectName("LastChar");
    public static final PdfName LastModified = createDirectName("LastModified");
    public static final PdfName LastPage = createDirectName("LastPage");
    public static final PdfName Launch = createDirectName(XfdfConstants.LAUNCH);
    public static final PdfName Layout = createDirectName("Layout");
    public static final PdfName Lbl = createDirectName(StandardRoles.LBL);
    public static final PdfName Leading = createDirectName("Leading");
    public static final PdfName Length = createDirectName("Length");
    public static final PdfName Length1 = createDirectName("Length1");
    public static final PdfName Lighten = createDirectName("Lighten");
    public static final PdfName Limits = createDirectName("Limits");
    public static final PdfName Line = createDirectName("Line");
    public static final PdfName LineArrow = createDirectName("LineArrow");
    public static final PdfName LineHeight = createDirectName("LineHeight");
    public static final PdfName LineNum = createDirectName("LineNum");
    public static final PdfName LineThrough = createDirectName("LineThrough");
    public static final PdfName Link = createDirectName(StandardRoles.LINK);
    public static final PdfName List = createDirectName("List");
    public static final PdfName ListMode = createDirectName("ListMode");
    public static final PdfName ListNumbering = createDirectName("ListNumbering");
    public static final PdfName Location = createDirectName("Location");
    public static final PdfName Lock = createDirectName("Lock");
    public static final PdfName Locked = createDirectName("Locked");
    public static final PdfName LowerAlpha = createDirectName("LowerAlpha");
    public static final PdfName LowerRoman = createDirectName("LowerRoman");
    public static final PdfName Luminosity = createDirectName("Luminosity");

    /* renamed from: M */
    public static final PdfName f1352M = createDirectName(SvgConstants.Attributes.PATH_DATA_MOVE_TO);
    public static final PdfName MCD = createDirectName("MCD");
    public static final PdfName MCID = createDirectName("MCID");
    public static final PdfName MCR = createDirectName("MCR");
    public static final PdfName MD5 = createDirectName("MD5");

    /* renamed from: MK */
    public static final PdfName f1353MK = createDirectName("MK");

    /* renamed from: ML */
    public static final PdfName f1354ML = createDirectName("ML");
    public static final PdfName MMType1 = createDirectName("MMType1");

    /* renamed from: MN */
    public static final PdfName f1355MN = createDirectName("ML");

    /* renamed from: MR */
    public static final PdfName f1356MR = createDirectName("MR");
    public static final PdfName MacExpertEncoding = createDirectName("MacExpertEncoding");
    public static final PdfName MacRomanEncoding = createDirectName("MacRomanEncoding");
    public static final PdfName MarkInfo = createDirectName("MarkInfo");
    public static final PdfName MarkStyle = createDirectName("MarkStyle");
    public static final PdfName Marked = createDirectName("Marked");
    public static final PdfName Markup = createDirectName("Markup");
    public static final PdfName Markup3D = createDirectName("Markup3D");
    public static final PdfName Mask = createDirectName(PngImageHelperConstants.MASK);
    public static final PdfName Matrix = createDirectName("Matrix");
    public static final PdfName MaxLen = createDirectName("MaxLen");
    public static final PdfName Measure = createDirectName("Measure");
    public static final PdfName MediaBox = createDirectName("MediaBox");
    public static final PdfName MediaClip = createDirectName("MediaClip");
    public static final PdfName Metadata = createDirectName("Metadata");
    public static final PdfName Middle = createDirectName("Middle");
    public static final PdfName MissingWidth = createDirectName("MissingWidth");
    public static final PdfName Mix = createDirectName("Mix");
    public static final PdfName ModDate = createDirectName("ModDate");
    public static final PdfName Movie = createDirectName("Movie");
    public static final PdfName MuLaw = createDirectName("muLaw");
    public static final PdfName Multiply = createDirectName("Multiply");

    /* renamed from: N */
    public static final PdfName f1357N = createDirectName("N");

    /* renamed from: NA */
    public static final PdfName f1358NA = createDirectName("NA");

    /* renamed from: NM */
    public static final PdfName f1359NM = createDirectName("NM");

    /* renamed from: NS */
    public static final PdfName f1360NS = createDirectName("NS");
    public static final PdfName NSO = createDirectName("NSO");
    public static final PdfName Name = createDirectName(XfdfConstants.NAME_CAPITAL);
    public static final PdfName Named = createDirectName(XfdfConstants.NAMED);
    public static final PdfName Names = createDirectName("Names");
    public static final PdfName Namespace = createDirectName("Namespace");
    public static final PdfName Namespaces = createDirectName("Namespaces");
    public static final PdfName NeedAppearances = createDirectName("NeedAppearances");
    public static final PdfName NeedsRendering = createDirectName("NeedsRendering");
    public static final PdfName NewWindow = createDirectName(XfdfConstants.NEW_WINDOW);
    public static final PdfName Next = createDirectName("Next");
    public static final PdfName NextPage = createDirectName("NextPage");
    public static final PdfName NoOp = createDirectName("NoOp");
    public static final PdfName NonFullScreenPageMode = createDirectName("NonFullScreenPageMode");
    public static final PdfName NonStruct = createDirectName(StandardRoles.NONSTRUCT);
    public static final PdfName None = createDirectName("None");
    public static final PdfName Normal = createDirectName(FontStretches.NORMAL);
    public static final PdfName Not = createDirectName("Not");
    public static final PdfName NotApproved = createDirectName("NotApproved");
    public static final PdfName NotForPublicRelease = createDirectName("NotForPublicRelease");
    public static final PdfName Note = createDirectName(StandardRoles.NOTE);
    public static final PdfName NumCopies = createDirectName("NumCopies");
    public static final PdfName Nums = createDirectName("Nums");

    /* renamed from: O */
    public static final PdfName f1361O = createDirectName("O");
    public static final PdfName OBJR = createDirectName("OBJR");

    /* renamed from: OC */
    public static final PdfName f1362OC = createDirectName("OC");
    public static final PdfName OCG = createDirectName("OCG");
    public static final PdfName OCGs = createDirectName("OCGs");
    public static final PdfName OCMD = createDirectName("OCMD");
    public static final PdfName OCProperties = createDirectName("OCProperties");
    public static final PdfName OCSP = createDirectName("OCSP");
    public static final PdfName OCSPs = createDirectName("OCSPs");

    /* renamed from: OE */
    public static final PdfName f1363OE = createDirectName("OE");
    public static final PdfName OFF = createDirectName("OFF");

    /* renamed from: ON */
    public static final PdfName f1364ON = createDirectName("ON");

    /* renamed from: OP */
    public static final PdfName f1365OP = createDirectName("OP");
    public static final PdfName OPI = createDirectName("OPI");
    public static final PdfName OPM = createDirectName("OPM");
    public static final PdfName Obj = createDirectName("Obj");
    public static final PdfName ObjStm = createDirectName("ObjStm");
    public static final PdfName OneColumn = createDirectName("OneColumn");
    public static final PdfName Open = createDirectName("Open");
    public static final PdfName OpenAction = createDirectName("OpenAction");
    public static final PdfName OpenArrow = createDirectName("OpenArrow");
    public static final PdfName Operation = createDirectName("Operation");
    public static final PdfName Opt = createDirectName("Opt");

    /* renamed from: Or */
    public static final PdfName f1366Or = createDirectName("Or");
    public static final PdfName Order = createDirectName("Order");
    public static final PdfName Ordered = createDirectName("Ordered");
    public static final PdfName Ordering = createDirectName("Ordering");
    public static final PdfName Outlines = createDirectName("Outlines");
    public static final PdfName OutputCondition = createDirectName("OutputCondition");
    public static final PdfName OutputConditionIdentifier = createDirectName("OutputConditionIdentifier");
    public static final PdfName OutputIntent = createDirectName("OutputIntent");
    public static final PdfName OutputIntents = createDirectName("OutputIntents");
    public static final PdfName Outset = createDirectName("Outset");
    public static final PdfName Overlay = createDirectName("Overlay");
    public static final PdfName OverlayText = createDirectName("OverlayText");

    /* renamed from: P */
    public static final PdfName f1367P = createDirectName(StandardRoles.f1511P);

    /* renamed from: PA */
    public static final PdfName f1368PA = createDirectName("PA");

    /* renamed from: PC */
    public static final PdfName f1369PC = createDirectName("PC");
    public static final PdfName PCM = createDirectName("PCM");

    /* renamed from: PI */
    public static final PdfName f1370PI = createDirectName("PI");

    /* renamed from: PO */
    public static final PdfName f1371PO = createDirectName("PO");

    /* renamed from: PS */
    public static final PdfName f1372PS = createDirectName("PS");

    /* renamed from: PV */
    public static final PdfName f1373PV = createDirectName("PV");
    public static final PdfName Padding = createDirectName("Padding");
    public static final PdfName Page = createDirectName(XfdfConstants.PAGE_CAPITAL);
    public static final PdfName PageElement = createDirectName("PageElement");
    public static final PdfName PageLabels = createDirectName("PageLabels");
    public static final PdfName PageLayout = createDirectName("PageLayout");
    public static final PdfName PageMode = createDirectName("PageMode");
    public static final PdfName PageNum = createDirectName("PageNum");
    public static final PdfName Pages = createDirectName("Pages");
    public static final PdfName Pagination = createDirectName("Pagination");
    public static final PdfName PaintType = createDirectName("PaintType");
    public static final PdfName Panose = createDirectName("Panose");
    public static final PdfName Paperclip = createDirectName("Paperclip");
    public static final PdfName Params = createDirectName("Params");
    public static final PdfName Parent = createDirectName("Parent");
    public static final PdfName ParentTree = createDirectName("ParentTree");
    public static final PdfName ParentTreeNextKey = createDirectName("ParentTreeNextKey");
    public static final PdfName Part = createDirectName(StandardRoles.PART);
    public static final PdfName Path = createDirectName("Path");
    public static final PdfName Pattern = createDirectName("Pattern");
    public static final PdfName PatternType = createDirectName("PatternType");
    public static final PdfName Pause = createDirectName("Pause");
    public static final PdfName Pdf_Version_1_2 = createDirectName("1.2");
    public static final PdfName Pdf_Version_1_3 = createDirectName("1.3");
    public static final PdfName Pdf_Version_1_4 = createDirectName("1.4");
    public static final PdfName Pdf_Version_1_5 = createDirectName("1.5");
    public static final PdfName Pdf_Version_1_6 = createDirectName("1.6");
    public static final PdfName Pdf_Version_1_7 = createDirectName("1.7");
    public static final PdfName Perceptual = createDirectName("Perceptual");
    public static final PdfName Perms = createDirectName("Perms");

    /* renamed from: Pg */
    public static final PdfName f1374Pg = createDirectName("Pg");
    public static final PdfName Phoneme = createDirectName("Phoneme");
    public static final PdfName PhoneticAlphabet = createDirectName("PhoneticAlphabet");
    public static final PdfName PickTrayByPDFSize = createDirectName("PickTrayByPDFSize");
    public static final PdfName Placement = createDirectName("Placement");
    public static final PdfName Play = createDirectName("Play");
    public static final PdfName PolyLine = createDirectName("PolyLine");
    public static final PdfName Polygon = createDirectName("Polygon");
    public static final PdfName Popup = createDirectName("Popup");
    public static final PdfName Predictor = createDirectName(PngImageHelperConstants.PREDICTOR);
    public static final PdfName Preferred = createDirectName("Preferred");
    public static final PdfName PresSteps = createDirectName("PresSteps");
    public static final PdfName PreserveRB = createDirectName("PreserveRB");
    public static final PdfName Prev = createDirectName("Prev");
    public static final PdfName PrevPage = createDirectName("PrevPage");
    public static final PdfName Print = createDirectName("Print");
    public static final PdfName PrintArea = createDirectName("PrintArea");
    public static final PdfName PrintClip = createDirectName("PrintClip");
    public static final PdfName PrintPageRange = createDirectName("PrintPageRange");
    public static final PdfName PrintScaling = createDirectName("PrintScaling");
    public static final PdfName PrintState = createDirectName("PrintState");
    public static final PdfName PrinterMark = createDirectName("PrinterMark");
    public static final PdfName Private = createDirectName(StandardRoles.PRIVATE);
    public static final PdfName ProcSet = createDirectName("ProcSet");
    public static final PdfName Producer = createDirectName(PdfConst.Producer);
    public static final PdfName PronunciationLexicon = createDirectName("PronunciationLexicon");
    public static final PdfName Prop_Build = createDirectName("Prop_Build");
    public static final PdfName Properties = createDirectName("Properties");
    public static final PdfName Pushpin = createDirectName("PushPin");

    /* renamed from: Q */
    public static final PdfName f1375Q = createDirectName(SvgConstants.Attributes.PATH_DATA_QUAD_CURVE_TO);
    public static final PdfName QuadPoints = createDirectName("QuadPoints");
    public static final PdfName Quote = createDirectName(StandardRoles.QUOTE);

    /* renamed from: R */
    public static final PdfName f1376R = createDirectName(SvgConstants.Attributes.PATH_DATA_CATMULL_CURVE);
    public static final PdfName R2L = createDirectName("R2L");

    /* renamed from: RB */
    public static final PdfName f1377RB = createDirectName(StandardRoles.f1512RB);
    public static final PdfName RBGroups = createDirectName("RBGroups");

    /* renamed from: RC */
    public static final PdfName f1378RC = createDirectName("RC");
    public static final PdfName RClosedArrow = createDirectName("RClosedArrow");

    /* renamed from: RD */
    public static final PdfName f1379RD = createDirectName("RD");

    /* renamed from: RI */
    public static final PdfName f1380RI = createDirectName("RI");

    /* renamed from: RO */
    public static final PdfName f1381RO = createDirectName("RO");
    public static final PdfName ROpenArrow = createDirectName("ROpenArrow");

    /* renamed from: RP */
    public static final PdfName f1382RP = createDirectName(StandardRoles.f1513RP);

    /* renamed from: RT */
    public static final PdfName f1383RT = createDirectName(StandardRoles.f1514RT);

    /* renamed from: RV */
    public static final PdfName f1384RV = createDirectName("RV");
    public static final PdfName Range = createDirectName("Range");
    public static final PdfName Raw = createDirectName("Raw");
    public static final PdfName Reason = createDirectName("Reason");
    public static final PdfName Recipients = createDirectName("Recipients");
    public static final PdfName Rect = createDirectName("Rect");
    public static final PdfName Redact = createDirectName("Redact");
    public static final PdfName Redaction = createDirectName("Redaction");
    public static final PdfName Ref = createDirectName("Ref");
    public static final PdfName Reference = createDirectName(StandardRoles.REFERENCE);
    public static final PdfName Registry = createDirectName("Registry");
    public static final PdfName RegistryName = createDirectName("RegistryName");
    public static final PdfName RelativeColorimetric = createDirectName("RelativeColorimetric");
    public static final PdfName Rendition = createDirectName("Rendition");
    public static final PdfName Renditions = createDirectName("Renditions");
    public static final PdfName Repeat = createDirectName("Repeat");
    public static final PdfName Requirement = createDirectName("Requirement");
    public static final PdfName Requirements = createDirectName("Requirements");
    public static final PdfName ResetForm = createDirectName("ResetForm");
    public static final PdfName Resources = createDirectName("Resources");
    public static final PdfName Resume = createDirectName("Resume");
    public static final PdfName ReversedChars = createDirectName("ReversedChars");
    public static final PdfName RichMedia = createDirectName("RichMedia");
    public static final PdfName Ridge = createDirectName("Ridge");
    public static final PdfName RoleMap = createDirectName("RoleMap");
    public static final PdfName RoleMapNS = createDirectName("RoleMapNS");
    public static final PdfName Root = createDirectName("Root");
    public static final PdfName Rotate = createDirectName("Rotate");
    public static final PdfName Row = createDirectName("Row");
    public static final PdfName RowSpan = createDirectName("RowSpan");
    public static final PdfName Rows = createDirectName("Rows");
    public static final PdfName Ruby = createDirectName(StandardRoles.RUBY);
    public static final PdfName RubyAlign = createDirectName("RubyAlign");
    public static final PdfName RubyPosition = createDirectName("RubyPosition");
    public static final PdfName RunLengthDecode = createDirectName("RunLengthDecode");

    /* renamed from: S */
    public static final PdfName f1385S = createDirectName(SvgConstants.Attributes.PATH_DATA_CURVE_TO_S);

    /* renamed from: SA */
    public static final PdfName f1386SA = createDirectName("SA");

    /* renamed from: SD */
    public static final PdfName f1387SD = createDirectName("SD");

    /* renamed from: SM */
    public static final PdfName f1388SM = createDirectName("SM");
    public static final PdfName SMask = createDirectName("SMask");
    public static final PdfName SMaskInData = createDirectName("SMaskInData");
    public static final PdfName Saturation = createDirectName("Saturation");
    public static final PdfName Schema = createDirectName("Schema");
    public static final PdfName Scope = createDirectName("Scope");
    public static final PdfName Screen = createDirectName("Screen");
    public static final PdfName Sect = createDirectName(StandardRoles.SECT);
    public static final PdfName Separation = createDirectName("Separation");
    public static final PdfName SeparationColorNames = createDirectName("SeparationColorNames");
    public static final PdfName SeparationInfo = createDirectName("SeparationInfo");
    public static final PdfName SetOCGState = createDirectName("SetOCGState");
    public static final PdfName SetState = createDirectName("SetState");
    public static final PdfName Shading = createDirectName("Shading");
    public static final PdfName ShadingType = createDirectName("ShadingType");
    public static final PdfName Short = createDirectName("Short");
    public static final PdfName Sig = createDirectName("Sig");
    public static final PdfName SigFieldLock = createDirectName("SigFieldLock");
    public static final PdfName SigFlags = createDirectName("SigFlags");
    public static final PdfName SigRef = createDirectName("SigRef");
    public static final PdfName Signed = createDirectName("Signed");
    public static final PdfName Simplex = createDirectName("Simplex");
    public static final PdfName SinglePage = createDirectName("SinglePage");
    public static final PdfName Size = createDirectName("Size");
    public static final PdfName Slash = createDirectName("Slash");
    public static final PdfName SoftLight = createDirectName("SoftLight");
    public static final PdfName Sold = createDirectName("Sold");
    public static final PdfName Solid = createDirectName("Solid");
    public static final PdfName Sort = createDirectName("Sort");
    public static final PdfName Sound = createDirectName("Sound");
    public static final PdfName Source = createDirectName("Source");
    public static final PdfName SpaceAfter = createDirectName("SpaceAfter");
    public static final PdfName SpaceBefore = createDirectName("SpaceBefore");
    public static final PdfName Span = createDirectName(StandardRoles.SPAN);
    public static final PdfName Square = createDirectName("Square");
    public static final PdfName Squiggly = createDirectName("Squiggly");

    /* renamed from: St */
    public static final PdfName f1389St = createDirectName("St");
    public static final PdfName Stamp = createDirectName("Stamp");
    public static final PdfName StampImage = createDirectName("StampImage");
    public static final PdfName StampSnapshot = createDirectName("StampSnapshot");
    public static final PdfName Standard = createDirectName("Standard");
    public static final PdfName Start = createDirectName("Start");
    public static final PdfName StartIndent = createDirectName("StartIndent");
    public static final PdfName State = createDirectName("State");
    public static final PdfName StateModel = createDirectName("StateModel");
    public static final PdfName StdCF = createDirectName("StdCF");
    public static final PdfName StemH = createDirectName("StemH");
    public static final PdfName StemV = createDirectName("StemV");
    public static final PdfName Stm = createDirectName("Stm");
    public static final PdfName StmF = createDirectName("StmF");
    public static final PdfName Stop = createDirectName("Stop");
    public static final PdfName StrF = createDirectName("StrF");
    public static final PdfName Stream = createDirectName("Stream");
    public static final PdfName StrikeOut = createDirectName("StrikeOut");
    public static final PdfName Strong = createDirectName(StandardRoles.STRONG);
    public static final PdfName StructElem = createDirectName("StructElem");
    public static final PdfName StructParent = createDirectName("StructParent");
    public static final PdfName StructParents = createDirectName("StructParents");
    public static final PdfName StructTreeRoot = createDirectName("StructTreeRoot");
    public static final PdfName Style = createDirectName("Style");
    public static final PdfName Sub = createDirectName(StandardRoles.SUB);
    public static final PdfName SubFilter = createDirectName("SubFilter");
    public static final PdfName Subj = createDirectName("Subj");
    public static final PdfName Subject = createDirectName("Subject");
    public static final PdfName SubmitForm = createDirectName("SubmitForm");
    public static final PdfName Subtype = createDirectName("Subtype");
    public static final PdfName Subtype2 = createDirectName("Subtype2");
    public static final PdfName Supplement = createDirectName("Supplement");

    /* renamed from: Sy */
    public static final PdfName f1390Sy = createDirectName("Sy");
    public static final PdfName Symbol = createDirectName("Symbol");
    public static final PdfName Synchronous = createDirectName("Synchronous");

    /* renamed from: T */
    public static final PdfName f1391T = createDirectName(SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO);

    /* renamed from: TA */
    public static final PdfName f1392TA = createDirectName("TA");
    public static final PdfName TBody = createDirectName(StandardRoles.TBODY);
    public static final PdfName TBorderStyle = createDirectName("TBorderStyle");

    /* renamed from: TD */
    public static final PdfName f1393TD = createDirectName(StandardRoles.f1515TD);

    /* renamed from: TF */
    public static final PdfName f1394TF = createDirectName("TF");
    public static final PdfName TFoot = createDirectName(StandardRoles.TFOOT);

    /* renamed from: TH */
    public static final PdfName f1395TH = createDirectName(StandardRoles.f1516TH);
    public static final PdfName THead = createDirectName(StandardRoles.THEAD);

    /* renamed from: TI */
    public static final PdfName f1396TI = createDirectName("TI");

    /* renamed from: TK */
    public static final PdfName f1397TK = createDirectName("TK");

    /* renamed from: TM */
    public static final PdfName f1398TM = createDirectName("TM");
    public static final PdfName TOC = createDirectName(StandardRoles.TOC);
    public static final PdfName TOCI = createDirectName(StandardRoles.TOCI);

    /* renamed from: TP */
    public static final PdfName f1399TP = createDirectName("TP");
    public static final PdfName TPadding = createDirectName("TPadding");

    /* renamed from: TR */
    public static final PdfName f1400TR = createDirectName(StandardRoles.f1517TR);
    public static final PdfName TR2 = createDirectName("TR2");

    /* renamed from: TU */
    public static final PdfName f1401TU = createDirectName("TU");
    public static final PdfName Table = createDirectName(StandardRoles.TABLE);
    public static final PdfName Tabs = createDirectName("Tabs");
    public static final PdfName Tag = createDirectName("Tag");
    public static final PdfName Templates = createDirectName("Templates");
    public static final PdfName Text = createDirectName("Text");
    public static final PdfName TextAlign = createDirectName("TextAlign");
    public static final PdfName TextDecorationColor = createDirectName("TextDecorationColor");
    public static final PdfName TextDecorationThickness = createDirectName("TextDecorationThickness");
    public static final PdfName TextDecorationType = createDirectName("TextDecorationType");
    public static final PdfName TextIndent = createDirectName("TextIndent");
    public static final PdfName Thumb = createDirectName("Thumb");
    public static final PdfName TilingType = createDirectName("TilingType");
    public static final PdfName Title = createDirectName(StandardRoles.TITLE);
    public static final PdfName ToUnicode = createDirectName("ToUnicode");
    public static final PdfName Toggle = createDirectName("Toggle");
    public static final PdfName Top = createDirectName(XfdfConstants.TOP);
    public static final PdfName TopSecret = createDirectName("TopSecret");
    public static final PdfName Trans = createDirectName("Trans");
    public static final PdfName TransformMethod = createDirectName("TransformMethod");
    public static final PdfName TransformParams = createDirectName("TransformParams");
    public static final PdfName Transparency = createDirectName("Transparency");
    public static final PdfName TrapNet = createDirectName("TrapNet");
    public static final PdfName TrapRegions = createDirectName("TrapRegions");
    public static final PdfName TrapStyles = createDirectName("TrapStyles");
    public static final PdfName Trapped = createDirectName(PdfConst.Trapped);
    public static final PdfName TrimBox = createDirectName("TrimBox");
    public static final PdfName True = createDirectName("true");
    public static final PdfName TrueType = createDirectName("TrueType");
    public static final PdfName TwoColumnLeft = createDirectName("TwoColumnLeft");
    public static final PdfName TwoColumnRight = createDirectName("TwoColumnRight");
    public static final PdfName TwoPageLeft = createDirectName("TwoPageLeft");
    public static final PdfName TwoPageRight = createDirectName("TwoPageRight");

    /* renamed from: Tx */
    public static final PdfName f1402Tx = createDirectName("Tx");
    public static final PdfName Type = createDirectName("Type");
    public static final PdfName Type0 = createDirectName("Type0");
    public static final PdfName Type1 = createDirectName("Type1");
    public static final PdfName Type3 = createDirectName("Type3");

    /* renamed from: U */
    public static final PdfName f1403U = createDirectName("U");
    public static final PdfName UCR = createDirectName("UCR");
    public static final PdfName UCR2 = createDirectName("UCR2");

    /* renamed from: UE */
    public static final PdfName f1404UE = createDirectName("UE");

    /* renamed from: UF */
    public static final PdfName f1405UF = createDirectName("UF");
    public static final PdfName UR3 = createDirectName("UR3");
    public static final PdfName URI = createDirectName(XfdfConstants.URI);
    public static final PdfName URL = createDirectName("URL");
    public static final PdfName URLS = createDirectName("URLS");
    public static final PdfName Underline = createDirectName("Underline");
    public static final PdfName Unordered = createDirectName("Unordered");
    public static final PdfName Unspecified = createDirectName("Unspecified");
    public static final PdfName UpperAlpha = createDirectName("UpperAlpha");
    public static final PdfName UpperRoman = createDirectName("UpperRoman");
    public static final PdfName Usage = createDirectName("Usage");
    public static final PdfName UseAttachments = createDirectName("UseAttachments");
    public static final PdfName UseBlackPtComp = createDirectName("UseBlackPtComp");
    public static final PdfName UseNone = createDirectName("UseNone");
    public static final PdfName UseOC = createDirectName("UseOC");
    public static final PdfName UseOutlines = createDirectName("UseOutlines");
    public static final PdfName UseThumbs = createDirectName("UseThumbs");
    public static final PdfName User = createDirectName("User");
    public static final PdfName UserProperties = createDirectName("UserProperties");
    public static final PdfName UserUnit = createDirectName("UserUnit");

    /* renamed from: V */
    public static final PdfName f1406V = createDirectName(SvgConstants.Attributes.PATH_DATA_LINE_TO_V);

    /* renamed from: V2 */
    public static final PdfName f1407V2 = createDirectName("V2");

    /* renamed from: VE */
    public static final PdfName f1408VE = createDirectName("VE");
    public static final PdfName VRI = createDirectName("VRI");
    public static final PdfName Version = createDirectName("Version");
    public static final PdfName Vertices = createDirectName("Vertices");
    public static final PdfName VerticesPerRow = createDirectName("VerticesPerRow");
    public static final PdfName View = createDirectName("View");
    public static final PdfName ViewArea = createDirectName("ViewArea");
    public static final PdfName ViewClip = createDirectName("ViewClip");
    public static final PdfName ViewState = createDirectName("ViewState");
    public static final PdfName ViewerPreferences = createDirectName("ViewerPreferences");
    public static final PdfName VisiblePages = createDirectName("VisiblePages");
    public static final PdfName Volatile = createDirectName("Volatile");
    public static final PdfName Volume = createDirectName("Volume");

    /* renamed from: W */
    public static final PdfName f1409W = createDirectName("W");

    /* renamed from: W2 */
    public static final PdfName f1410W2 = createDirectName("W2");

    /* renamed from: WC */
    public static final PdfName f1411WC = createDirectName("WC");

    /* renamed from: WP */
    public static final PdfName f1412WP = createDirectName(StandardRoles.f1518WP);

    /* renamed from: WS */
    public static final PdfName f1413WS = createDirectName("WS");

    /* renamed from: WT */
    public static final PdfName f1414WT = createDirectName(StandardRoles.f1519WT);
    public static final PdfName Warichu = createDirectName(StandardRoles.WARICHU);
    public static final PdfName Watermark = createDirectName("Watermark");
    public static final PdfName WhitePoint = createDirectName("WhitePoint");
    public static final PdfName Widget = createDirectName("Widget");
    public static final PdfName Width = createDirectName(XfdfConstants.WIDTH_CAPITAL);
    public static final PdfName Widths = createDirectName("Widths");
    public static final PdfName Win = createDirectName("Win");
    public static final PdfName WinAnsiEncoding = createDirectName("WinAnsiEncoding");
    public static final PdfName WritingMode = createDirectName("WritingMode");

    /* renamed from: X */
    public static final PdfName f1415X = createDirectName("X");
    public static final PdfName XFA = createDirectName("XFA");
    public static final PdfName XHeight = createDirectName("XHeight");
    public static final PdfName XML = createDirectName("XML");
    public static final PdfName XObject = createDirectName("XObject");
    public static final PdfName XRef = createDirectName("XRef");
    public static final PdfName XRefStm = createDirectName("XRefStm");
    public static final PdfName XStep = createDirectName("XStep");
    public static final PdfName XYZ = createDirectName(XfdfConstants.XYZ_CAPITAL);
    public static final PdfName YStep = createDirectName("YStep");
    public static final PdfName ZapfDingbats = createDirectName("ZapfDingbats");
    public static final PdfName Zoom = createDirectName("Zoom");
    public static final PdfName _3D = createDirectName("3D");
    public static final PdfName _3DA = createDirectName("3DA");
    public static final PdfName _3DB = createDirectName("3DB");
    public static final PdfName _3DCrossSection = createDirectName("3DCrossSection");
    public static final PdfName _3DD = createDirectName("3DD");
    public static final PdfName _3DI = createDirectName("3DI");
    public static final PdfName _3DV = createDirectName("3DV");
    public static final PdfName _3DView = createDirectName("3DView");

    /* renamed from: a */
    public static final PdfName f1416a = createDirectName("a");

    /* renamed from: ca */
    public static final PdfName f1417ca = createDirectName("ca");
    private static final byte[] greaterThan = ByteUtils.getIsoBytes("#3e");
    public static final PdfName ipa = createDirectName("ipa");
    private static final byte[] leftCurlyBracket = ByteUtils.getIsoBytes("#7b");
    private static final byte[] leftParenthesis = ByteUtils.getIsoBytes("#28");
    private static final byte[] leftSquare = ByteUtils.getIsoBytes("#5b");
    private static final byte[] lessThan = ByteUtils.getIsoBytes("#3c");
    public static final PdfName max = createDirectName(MediaRuleConstants.MAX);
    public static final PdfName min = createDirectName(MediaRuleConstants.MIN);
    private static final byte[] numberSign = ByteUtils.getIsoBytes("#23");

    /* renamed from: op */
    public static final PdfName f1418op = createDirectName("op");
    private static final byte[] percent = ByteUtils.getIsoBytes("#25");

    /* renamed from: r */
    public static final PdfName f1419r = createDirectName("r");
    private static final byte[] rightCurlyBracket = ByteUtils.getIsoBytes("#7d");
    private static final byte[] rightParenthesis = ByteUtils.getIsoBytes("#29");
    private static final byte[] rightSquare = ByteUtils.getIsoBytes("#5d");
    private static final long serialVersionUID = 7493154668111961953L;
    private static final byte[] solidus = ByteUtils.getIsoBytes("#2f");
    private static final byte[] space = ByteUtils.getIsoBytes("#20");
    public static Map<String, PdfName> staticNames = PdfNameLoader.loadNames();
    public static final PdfName x_sampa = createDirectName("x-sampa");
    public static final PdfName zh_Latn_pinyin = createDirectName("zh-Latn-pinyin");
    public static final PdfName zh_Latn_wadegile = createDirectName("zh-Latn-wadegile");
    protected String value = null;

    private static PdfName createDirectName(String name) {
        return new PdfName(name, true);
    }

    public PdfName(String value2) {
        if (value2 != null) {
            this.value = value2;
            return;
        }
        throw new AssertionError();
    }

    private PdfName(String value2, boolean directOnly) {
        super(directOnly);
        this.value = value2;
    }

    public PdfName(byte[] content) {
        super(content);
    }

    private PdfName() {
    }

    public byte getType() {
        return 6;
    }

    public String getValue() {
        if (this.value == null) {
            generateValue();
        }
        return this.value;
    }

    public int compareTo(PdfName o) {
        return getValue().compareTo(o.getValue());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && getClass() == o.getClass() && compareTo((PdfName) o) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return getValue().hashCode();
    }

    /* access modifiers changed from: protected */
    public void generateValue() {
        StringBuilder buf = new StringBuilder();
        int k = 0;
        while (k < this.content.length) {
            try {
                char c = (char) this.content[k];
                if (c == '#') {
                    c = (char) ((ByteBuffer.getHex(this.content[k + 1]) << 4) + ByteBuffer.getHex(this.content[k + 2]));
                    k += 2;
                }
                buf.append(c);
                k++;
            } catch (IndexOutOfBoundsException e) {
            }
        }
        this.value = buf.toString();
    }

    /* access modifiers changed from: protected */
    public void generateContent() {
        int length = this.value.length();
        ByteBuffer buf = new ByteBuffer(length + 20);
        char[] chars = this.value.toCharArray();
        for (int k = 0; k < length; k++) {
            char c = (char) (chars[k] & 255);
            switch (c) {
                case ' ':
                    buf.append(space);
                    break;
                case '#':
                    buf.append(numberSign);
                    break;
                case '%':
                    buf.append(percent);
                    break;
                case '(':
                    buf.append(leftParenthesis);
                    break;
                case ')':
                    buf.append(rightParenthesis);
                    break;
                case '/':
                    buf.append(solidus);
                    break;
                case '<':
                    buf.append(lessThan);
                    break;
                case '>':
                    buf.append(greaterThan);
                    break;
                case '[':
                    buf.append(leftSquare);
                    break;
                case ']':
                    buf.append(rightSquare);
                    break;
                case '{':
                    buf.append(leftCurlyBracket);
                    break;
                case '}':
                    buf.append(rightCurlyBracket);
                    break;
                default:
                    if (c >= ' ' && c <= '~') {
                        buf.append((int) c);
                        break;
                    } else {
                        buf.append(35);
                        if (c < 16) {
                            buf.append(48);
                        }
                        buf.append(Integer.toHexString(c));
                        break;
                    }
                    break;
            }
        }
        this.content = buf.toByteArray();
    }

    public String toString() {
        if (this.content != null) {
            return "/" + new String(this.content, StandardCharsets.ISO_8859_1);
        }
        return "/" + getValue();
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return new PdfName();
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        this.value = ((PdfName) from).value;
    }
}
