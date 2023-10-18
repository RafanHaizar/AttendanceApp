package com.itextpdf.pdfa.checker;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.PatternColor;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfTrueTypeFont;
import com.itextpdf.kernel.font.PdfType3Font;
import com.itextpdf.kernel.font.Type3Glyph;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.p026io.colors.IccProfile;
import com.itextpdf.p026io.font.FontEncoding;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.image.ImageDataFactory;
import com.itextpdf.p026io.image.Jpeg2000ImageData;
import com.itextpdf.pdfa.PdfAConformanceException;
import com.itextpdf.pdfa.PdfAConformanceLogMessageConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class PdfA2Checker extends PdfA1Checker {
    private static final int MAX_NUMBER_OF_DEVICEN_COLOR_COMPONENTS = 32;
    static final int MAX_PAGE_SIZE = 14400;
    static final int MIN_PAGE_SIZE = 3;
    protected static final Set<PdfName> allowedBlendModes = new HashSet(Arrays.asList(new PdfName[]{PdfName.Normal, PdfName.Compatible, PdfName.Multiply, PdfName.Screen, PdfName.Overlay, PdfName.Darken, PdfName.Lighten, PdfName.ColorDodge, PdfName.ColorBurn, PdfName.HardLight, PdfName.SoftLight, PdfName.Difference, PdfName.Exclusion, PdfName.Hue, PdfName.Saturation, PdfName.Color, PdfName.Luminosity}));
    protected static final Set<PdfName> forbiddenActions = new HashSet(Arrays.asList(new PdfName[]{PdfName.Launch, PdfName.Sound, PdfName.Movie, PdfName.ResetForm, PdfName.ImportData, PdfName.JavaScript, PdfName.Hide, PdfName.SetOCGState, PdfName.Rendition, PdfName.Trans, PdfName.GoTo3DView}));
    protected static final Set<PdfName> forbiddenAnnotations = new HashSet(Arrays.asList(new PdfName[]{PdfName._3D, PdfName.Sound, PdfName.Screen, PdfName.Movie}));
    private static final long serialVersionUID = -5937712517954260687L;
    private boolean currentFillCsIsIccBasedCMYK = false;
    private boolean currentStrokeCsIsIccBasedCMYK = false;
    private Map<PdfName, PdfArray> separationColorSpaces = new HashMap();
    private Set<PdfObject> transparencyObjects = new HashSet();

    public PdfA2Checker(PdfAConformanceLevel conformanceLevel) {
        super(conformanceLevel);
    }

    public void checkInlineImage(PdfStream inlineImage, PdfDictionary currentColorSpaces) {
        PdfObject filter = inlineImage.get(PdfName.Filter);
        if (filter instanceof PdfName) {
            if (filter.equals(PdfName.LZWDecode)) {
                throw new PdfAConformanceException(PdfAConformanceException.LZWDECODE_FILTER_IS_NOT_PERMITTED);
            } else if (filter.equals(PdfName.Crypt)) {
                throw new PdfAConformanceException(PdfAConformanceException.CRYPT_FILTER_IS_NOT_PERMITTED_INLINE_IMAGE);
            }
        } else if (filter instanceof PdfArray) {
            int i = 0;
            while (i < ((PdfArray) filter).size()) {
                PdfName f = ((PdfArray) filter).getAsName(i);
                if (f.equals(PdfName.LZWDecode)) {
                    throw new PdfAConformanceException(PdfAConformanceException.LZWDECODE_FILTER_IS_NOT_PERMITTED);
                } else if (!f.equals(PdfName.Crypt)) {
                    i++;
                } else {
                    throw new PdfAConformanceException(PdfAConformanceException.CRYPT_FILTER_IS_NOT_PERMITTED_INLINE_IMAGE);
                }
            }
        }
        checkImage(inlineImage, currentColorSpaces);
    }

    public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill) {
        checkColor(color, currentColorSpaces, fill, (PdfStream) null);
    }

    public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill, PdfStream contentStream) {
        if (color instanceof PatternColor) {
            PdfPattern pattern = ((PatternColor) color).getPattern();
            if (pattern instanceof PdfPattern.Shading) {
                checkColorSpace(PdfColorSpace.makeColorSpace(((PdfPattern.Shading) pattern).getShading().get(PdfName.ColorSpace)), currentColorSpaces, true, true);
                checkExtGState(new CanvasGraphicsState(((PdfDictionary) pattern.getPdfObject()).getAsDictionary(PdfName.ExtGState)) {
                    final /* synthetic */ PdfDictionary val$extGStateDict;

                    {
                        this.val$extGStateDict = r3;
                        updateFromExtGState(new PdfExtGState(r3));
                    }
                }, contentStream);
            } else if (pattern instanceof PdfPattern.Tiling) {
                checkContentStream((PdfStream) pattern.getPdfObject());
            }
        }
        super.checkColor(color, currentColorSpaces, fill, contentStream);
    }

    public void checkColorSpace(PdfColorSpace colorSpace, PdfDictionary currentColorSpaces, boolean checkAlternate, Boolean fill) {
        if (fill != null) {
            if (fill.booleanValue()) {
                this.currentFillCsIsIccBasedCMYK = false;
            } else {
                this.currentStrokeCsIsIccBasedCMYK = false;
            }
        }
        if (colorSpace instanceof PdfSpecialCs.Separation) {
            PdfSpecialCs.Separation separation = (PdfSpecialCs.Separation) colorSpace;
            checkSeparationCS((PdfArray) separation.getPdfObject());
            if (checkAlternate) {
                checkColorSpace(separation.getBaseCs(), currentColorSpaces, false, fill);
            }
        } else if (colorSpace instanceof PdfSpecialCs.DeviceN) {
            PdfSpecialCs.DeviceN deviceN = (PdfSpecialCs.DeviceN) colorSpace;
            if (deviceN.getNumberOfComponents() <= 32) {
                PdfDictionary colorants = ((PdfArray) deviceN.getPdfObject()).getAsDictionary(4).getAsDictionary(PdfName.Colorants);
                if (colorants != null) {
                    for (Map.Entry<PdfName, PdfObject> entry : colorants.entrySet()) {
                        checkSeparationInsideDeviceN((PdfArray) entry.getValue(), ((PdfArray) deviceN.getPdfObject()).get(2), ((PdfArray) deviceN.getPdfObject()).get(3));
                    }
                }
                if (checkAlternate) {
                    checkColorSpace(deviceN.getBaseCs(), currentColorSpaces, false, fill);
                }
            } else {
                throw new PdfAConformanceException(PdfAConformanceException.f1587x736cbd, 32);
            }
        } else if (colorSpace instanceof PdfSpecialCs.Indexed) {
            if (checkAlternate) {
                checkColorSpace(((PdfSpecialCs.Indexed) colorSpace).getBaseCs(), currentColorSpaces, true, fill);
            }
        } else if (colorSpace instanceof PdfSpecialCs.UncoloredTilingPattern) {
            if (checkAlternate) {
                checkColorSpace(((PdfSpecialCs.UncoloredTilingPattern) colorSpace).getUnderlyingColorSpace(), currentColorSpaces, true, fill);
            }
        } else if (colorSpace instanceof PdfDeviceCs.Rgb) {
            if (!checkDefaultCS(currentColorSpaces, fill, PdfName.DefaultRGB, 3)) {
                this.rgbIsUsed = true;
            }
        } else if (colorSpace instanceof PdfDeviceCs.Cmyk) {
            if (!checkDefaultCS(currentColorSpaces, fill, PdfName.DefaultCMYK, 4)) {
                this.cmykIsUsed = true;
            }
        } else if ((colorSpace instanceof PdfDeviceCs.Gray) && !checkDefaultCS(currentColorSpaces, fill, PdfName.DefaultGray, 1)) {
            this.grayIsUsed = true;
        }
        if (fill != null && (colorSpace instanceof PdfCieBasedCs.IccBased) && PdfAChecker.ICC_COLOR_SPACE_CMYK.equals(IccProfile.getIccColorSpaceName(((PdfArray) colorSpace.getPdfObject()).getAsStream(1).getBytes()))) {
            if (fill.booleanValue()) {
                this.currentFillCsIsIccBasedCMYK = true;
            } else {
                this.currentStrokeCsIsIccBasedCMYK = true;
            }
        }
    }

    public void checkExtGState(CanvasGraphicsState extGState) {
        checkExtGState(extGState, (PdfStream) null);
    }

    public void checkExtGState(CanvasGraphicsState extGState, PdfStream contentStream) {
        Integer num = 1;
        if (num.equals(Integer.valueOf(extGState.getOverprintMode()))) {
            if (extGState.getFillOverprint() && this.currentFillCsIsIccBasedCMYK) {
                throw new PdfAConformanceException(PdfAConformanceException.f1578xb09c8137);
            } else if (extGState.getStrokeOverprint() && this.currentStrokeCsIsIccBasedCMYK) {
                throw new PdfAConformanceException(PdfAConformanceException.f1578xb09c8137);
            }
        }
        if (extGState.getTransferFunction() != null) {
            throw new PdfAConformanceException(PdfAConformanceException.AN_EXTGSTATE_DICTIONARY_SHALL_NOT_CONTAIN_THE_TR_KEY);
        } else if (extGState.getHTP() == null) {
            PdfObject transferFunction2 = extGState.getTransferFunction2();
            if (transferFunction2 == null || PdfName.Default.equals(transferFunction2)) {
                if (extGState.getHalftone() instanceof PdfDictionary) {
                    PdfDictionary halftoneDict = (PdfDictionary) extGState.getHalftone();
                    Integer halftoneType = halftoneDict.getAsInt(PdfName.HalftoneType);
                    if (halftoneType.intValue() != 1 && halftoneType.intValue() != 5) {
                        throw new PdfAConformanceException(PdfAConformanceException.ALL_HALFTONES_SHALL_HAVE_HALFTONETYPE_1_OR_5);
                    } else if (halftoneDict.containsKey(PdfName.HalftoneName)) {
                        throw new PdfAConformanceException(PdfAConformanceException.HALFTONES_SHALL_NOT_CONTAIN_HALFTONENAME);
                    }
                }
                checkRenderingIntent(extGState.getRenderingIntent());
                if (extGState.getSoftMask() != null && (extGState.getSoftMask() instanceof PdfDictionary)) {
                    this.transparencyObjects.add(contentStream);
                }
                if (extGState.getStrokeOpacity() < 1.0f) {
                    this.transparencyObjects.add(contentStream);
                }
                if (extGState.getFillOpacity() < 1.0f) {
                    this.transparencyObjects.add(contentStream);
                }
                PdfObject bm = extGState.getBlendMode();
                if (bm != null) {
                    if (!PdfName.Normal.equals(bm)) {
                        this.transparencyObjects.add(contentStream);
                    }
                    if (bm instanceof PdfArray) {
                        Iterator<PdfObject> it = ((PdfArray) bm).iterator();
                        while (it.hasNext()) {
                            checkBlendMode((PdfName) it.next());
                        }
                    } else if (bm instanceof PdfName) {
                        checkBlendMode((PdfName) bm);
                    }
                }
            } else {
                throw new PdfAConformanceException(PdfAConformanceException.f1550x46add6e4);
            }
        } else {
            throw new PdfAConformanceException(PdfAConformanceException.AN_EXTGSTATE_DICTIONARY_SHALL_NOT_CONTAIN_THE_HTP_KEY);
        }
    }

    /* access modifiers changed from: protected */
    public void checkNonSymbolicTrueTypeFont(PdfTrueTypeFont trueTypeFont) {
        String encoding = trueTypeFont.getFontEncoding().getBaseEncoding();
        if (!"Cp1252".equals(encoding) && !PdfEncodings.MACROMAN.equals(encoding)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1547x6222251a, trueTypeFont);
        }
    }

    /* access modifiers changed from: protected */
    public double getMaxRealValue() {
        return 3.4028234663852886E38d;
    }

    /* access modifiers changed from: protected */
    public int getMaxStringLength() {
        return 32767;
    }

    /* access modifiers changed from: protected */
    public void checkPdfArray(PdfArray array) {
    }

    /* access modifiers changed from: protected */
    public void checkPdfDictionary(PdfDictionary dictionary) {
    }

    /* access modifiers changed from: protected */
    public void checkAnnotation(PdfDictionary annotDic) {
        PdfName subtype = annotDic.getAsName(PdfName.Subtype);
        if (subtype == null) {
            throw new PdfAConformanceException(PdfAConformanceException.ANNOTATION_TYPE_0_IS_NOT_PERMITTED).setMessageParams("null");
        } else if (!forbiddenAnnotations.contains(subtype)) {
            if (!subtype.equals(PdfName.Popup)) {
                PdfNumber f = annotDic.getAsNumber(PdfName.f1324F);
                if (f != null) {
                    int flags = f.intValue();
                    if (!checkFlag(flags, 4) || checkFlag(flags, 2) || checkFlag(flags, 1) || checkFlag(flags, 32) || checkFlag(flags, 256)) {
                        throw new PdfAConformanceException(PdfAConformanceException.f1586x196d4a5b);
                    } else if (subtype.equals(PdfName.Text) && (!checkFlag(flags, 8) || !checkFlag(flags, 16))) {
                        throw new PdfAConformanceException(PdfAConformanceLogMessageConstant.f1592xba373e7c);
                    }
                } else {
                    throw new PdfAConformanceException(PdfAConformanceException.AN_ANNOTATION_DICTIONARY_SHALL_CONTAIN_THE_F_KEY);
                }
            }
            if (PdfName.Widget.equals(subtype) && (annotDic.containsKey(PdfName.f1288AA) || annotDic.containsKey(PdfName.f1287A))) {
                throw new PdfAConformanceException(PdfAConformanceException.f1591x1eeb393f);
            } else if (annotDic.containsKey(PdfName.f1288AA)) {
                throw new PdfAConformanceException(PdfAConformanceException.AN_ANNOTATION_DICTIONARY_SHALL_NOT_CONTAIN_AA_KEY);
            } else if (!checkStructure(this.conformanceLevel) || !contentAnnotations.contains(subtype) || annotDic.containsKey(PdfName.Contents)) {
                PdfDictionary ap = annotDic.getAsDictionary(PdfName.f1291AP);
                if (ap == null) {
                    boolean isCorrectRect = false;
                    PdfArray rect = annotDic.getAsArray(PdfName.Rect);
                    if (rect != null && rect.size() == 4) {
                        PdfNumber index0 = rect.getAsNumber(0);
                        PdfNumber index1 = rect.getAsNumber(1);
                        PdfNumber index2 = rect.getAsNumber(2);
                        PdfNumber index3 = rect.getAsNumber(3);
                        if (!(index0 == null || index1 == null || index2 == null || index3 == null || index0.floatValue() != index2.floatValue() || index1.floatValue() != index3.floatValue())) {
                            isCorrectRect = true;
                        }
                    }
                    if (!PdfName.Popup.equals(subtype) && !PdfName.Link.equals(subtype) && !isCorrectRect) {
                        throw new PdfAConformanceException(PdfAConformanceException.EVERY_ANNOTATION_SHALL_HAVE_AT_LEAST_ONE_APPEARANCE_DICTIONARY);
                    }
                } else if (ap.containsKey(PdfName.f1376R) || ap.containsKey(PdfName.f1312D)) {
                    throw new PdfAConformanceException(PdfAConformanceException.f1552x5f64bc25);
                } else {
                    PdfObject n = ap.get(PdfName.f1357N);
                    if (!PdfName.Widget.equals(subtype) || !PdfName.Btn.equals(annotDic.getAsName(PdfName.f1327FT))) {
                        if (n == null || !n.isStream()) {
                            throw new PdfAConformanceException(PdfAConformanceException.f1552x5f64bc25);
                        }
                    } else if (n == null || !n.isDictionary()) {
                        throw new PdfAConformanceException(PdfAConformanceException.f1551x6f23ed9c);
                    }
                    checkResourcesOfAppearanceStreams(ap);
                }
            } else {
                throw new PdfAConformanceException(PdfAConformanceException.ANNOTATION_OF_TYPE_0_SHOULD_HAVE_CONTENTS_KEY).setMessageParams(subtype.getValue());
            }
        } else {
            throw new PdfAConformanceException(PdfAConformanceException.ANNOTATION_TYPE_0_IS_NOT_PERMITTED).setMessageParams(subtype.getValue());
        }
    }

    /* access modifiers changed from: protected */
    public void checkAppearanceStream(PdfStream appearanceStream) {
        if (!isAlreadyChecked(appearanceStream)) {
            if (isContainsTransparencyGroup(appearanceStream)) {
                this.transparencyObjects.add(appearanceStream);
            }
            checkResources(appearanceStream.getAsDictionary(PdfName.Resources));
        }
    }

    /* access modifiers changed from: protected */
    public void checkForm(PdfDictionary form) {
        if (form != null) {
            PdfBoolean needAppearances = form.getAsBoolean(PdfName.NeedAppearances);
            if (needAppearances != null && needAppearances.getValue()) {
                throw new PdfAConformanceException(PdfAConformanceException.f1574x9609c9f4);
            } else if (!form.containsKey(PdfName.XFA)) {
                checkResources(form.getAsDictionary(PdfName.f1315DR));
                PdfArray fields = form.getAsArray(PdfName.Fields);
                if (fields != null) {
                    Iterator<PdfObject> it = getFormFields(fields).iterator();
                    while (it.hasNext()) {
                        checkResources(((PdfDictionary) it.next()).getAsDictionary(PdfName.f1315DR));
                    }
                }
            } else {
                throw new PdfAConformanceException(PdfAConformanceException.THE_INTERACTIVE_FORM_DICTIONARY_SHALL_NOT_CONTAIN_THE_XFA_KEY);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkCatalogValidEntries(PdfDictionary catalogDict) {
        PdfArray references;
        if (catalogDict.containsKey(PdfName.NeedsRendering)) {
            throw new PdfAConformanceException(PdfAConformanceException.THE_CATALOG_DICTIONARY_SHALL_NOT_CONTAIN_THE_NEEDSRENDERING_KEY);
        } else if (catalogDict.containsKey(PdfName.f1288AA)) {
            throw new PdfAConformanceException(PdfAConformanceException.A_CATALOG_DICTIONARY_SHALL_NOT_CONTAIN_AA_ENTRY);
        } else if (!catalogDict.containsKey(PdfName.Requirements)) {
            PdfDictionary permissions = catalogDict.getAsDictionary(PdfName.Perms);
            if (permissions != null) {
                for (PdfName dictKey : permissions.keySet()) {
                    if (PdfName.DocMDP.equals(dictKey)) {
                        PdfDictionary signatureDict = permissions.getAsDictionary(PdfName.DocMDP);
                        if (!(signatureDict == null || (references = signatureDict.getAsArray(PdfName.Reference)) == null)) {
                            for (int i = 0; i < references.size(); i++) {
                                PdfDictionary referenceDict = references.getAsDictionary(i);
                                if (referenceDict.containsKey(PdfName.DigestLocation) || referenceDict.containsKey(PdfName.DigestMethod) || referenceDict.containsKey(PdfName.DigestValue)) {
                                    throw new PdfAConformanceException(PdfAConformanceException.f1580x4e54c3f6);
                                }
                            }
                            continue;
                        }
                    } else if (!PdfName.UR3.equals(dictKey)) {
                        throw new PdfAConformanceException(PdfAConformanceException.f1575x2aa6303c);
                    }
                }
            }
            PdfDictionary namesDictionary = catalogDict.getAsDictionary(PdfName.Names);
            if (namesDictionary == null || !namesDictionary.containsKey(PdfName.AlternatePresentations)) {
                PdfDictionary oCProperties = catalogDict.getAsDictionary(PdfName.OCProperties);
                if (oCProperties != null) {
                    List<PdfDictionary> configList = new ArrayList<>();
                    PdfDictionary d = oCProperties.getAsDictionary(PdfName.f1312D);
                    if (d != null) {
                        configList.add(d);
                    }
                    PdfArray configs = oCProperties.getAsArray(PdfName.Configs);
                    if (configs != null) {
                        Iterator<PdfObject> it = configs.iterator();
                        while (it.hasNext()) {
                            configList.add((PdfDictionary) it.next());
                        }
                    }
                    HashSet<PdfObject> ocgs = new HashSet<>();
                    PdfArray ocgsArray = oCProperties.getAsArray(PdfName.OCGs);
                    if (ocgsArray != null) {
                        Iterator<PdfObject> it2 = ocgsArray.iterator();
                        while (it2.hasNext()) {
                            ocgs.add(it2.next());
                        }
                    }
                    HashSet<String> names = new HashSet<>();
                    for (PdfDictionary config : configList) {
                        checkCatalogConfig(config, ocgs, names);
                    }
                    return;
                }
                return;
            }
            throw new PdfAConformanceException(PdfAConformanceException.f1553x3b843e6a);
        } else {
            throw new PdfAConformanceException(PdfAConformanceException.A_CATALOG_DICTIONARY_SHALL_NOT_CONTAIN_REQUIREMENTS_ENTRY);
        }
    }

    /* access modifiers changed from: protected */
    public void checkPageSize(PdfDictionary page) {
        for (PdfName boxName : new PdfName[]{PdfName.MediaBox, PdfName.CropBox, PdfName.TrimBox, PdfName.ArtBox, PdfName.BleedBox}) {
            Rectangle box = page.getAsRectangle(boxName);
            if (box != null) {
                float width = box.getWidth();
                float height = box.getHeight();
                if (width < 3.0f || width > 14400.0f || height < 3.0f || height > 14400.0f) {
                    throw new PdfAConformanceException(PdfAConformanceException.THE_PAGE_LESS_3_UNITS_NO_GREATER_14400_IN_EITHER_DIRECTION);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkFileSpec(PdfDictionary fileSpec) {
        if (!fileSpec.containsKey(PdfName.f1321EF)) {
            return;
        }
        if (!fileSpec.containsKey(PdfName.f1324F) || !fileSpec.containsKey(PdfName.f1405UF)) {
            throw new PdfAConformanceException(PdfAConformanceException.FILE_SPECIFICATION_DICTIONARY_SHALL_CONTAIN_F_KEY_AND_UF_KEY);
        }
        if (!fileSpec.containsKey(PdfName.Desc)) {
            LoggerFactory.getLogger((Class<?>) PdfAChecker.class).warn(PdfAConformanceLogMessageConstant.FILE_SPECIFICATION_DICTIONARY_SHOULD_CONTAIN_DESC_KEY);
        }
        if (fileSpec.getAsDictionary(PdfName.f1321EF).getAsStream(PdfName.f1324F) != null) {
            LoggerFactory.getLogger((Class<?>) PdfAChecker.class).warn(PdfAConformanceLogMessageConstant.EMBEDDED_FILE_SHALL_BE_COMPLIANT_WITH_SPEC);
            return;
        }
        throw new PdfAConformanceException(PdfAConformanceException.f1565x7f5ec81);
    }

    /* access modifiers changed from: protected */
    public void checkPdfStream(PdfStream stream) {
        PdfArray decodeParams;
        PdfName cryptFilterName;
        PdfDictionary decodeParams2;
        PdfName cryptFilterName2;
        checkPdfDictionary(stream);
        if (stream.containsKey(PdfName.f1324F) || stream.containsKey(PdfName.FFilter) || stream.containsKey(PdfName.FDecodeParams)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1581x774c9db);
        }
        PdfObject filter = stream.get(PdfName.Filter);
        if (filter instanceof PdfName) {
            if (filter.equals(PdfName.LZWDecode)) {
                throw new PdfAConformanceException(PdfAConformanceException.LZWDECODE_FILTER_IS_NOT_PERMITTED);
            } else if (filter.equals(PdfName.Crypt) && (decodeParams2 = stream.getAsDictionary(PdfName.DecodeParms)) != null && (cryptFilterName2 = decodeParams2.getAsName(PdfName.Name)) != null && !cryptFilterName2.equals(PdfName.Identity)) {
                throw new PdfAConformanceException(PdfAConformanceException.NOT_IDENTITY_CRYPT_FILTER_IS_NOT_PERMITTED);
            }
        } else if (filter instanceof PdfArray) {
            int i = 0;
            while (i < ((PdfArray) filter).size()) {
                PdfName f = ((PdfArray) filter).getAsName(i);
                if (f.equals(PdfName.LZWDecode)) {
                    throw new PdfAConformanceException(PdfAConformanceException.LZWDECODE_FILTER_IS_NOT_PERMITTED);
                } else if (!f.equals(PdfName.Crypt) || (decodeParams = stream.getAsArray(PdfName.DecodeParms)) == null || i >= decodeParams.size() || (cryptFilterName = decodeParams.getAsDictionary(i).getAsName(PdfName.Name)) == null || cryptFilterName.equals(PdfName.Identity)) {
                    i++;
                } else {
                    throw new PdfAConformanceException(PdfAConformanceException.NOT_IDENTITY_CRYPT_FILTER_IS_NOT_PERMITTED);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkPageObject(PdfDictionary pageDict, PdfDictionary pageResources) {
        PdfObject cs;
        if (pageDict.containsKey(PdfName.f1288AA)) {
            throw new PdfAConformanceException(PdfAConformanceException.THE_PAGE_DICTIONARY_SHALL_NOT_CONTAIN_AA_ENTRY);
        } else if (pageDict.containsKey(PdfName.PresSteps)) {
            throw new PdfAConformanceException(PdfAConformanceException.THE_PAGE_DICTIONARY_SHALL_NOT_CONTAIN_PRESSTEPS_ENTRY);
        } else if (isContainsTransparencyGroup(pageDict) && (cs = pageDict.getAsDictionary(PdfName.Group).get(PdfName.f1309CS)) != null) {
            checkColorSpace(PdfColorSpace.makeColorSpace(cs), pageResources.getAsDictionary(PdfName.ColorSpace), true, (Boolean) null);
        }
    }

    /* access modifiers changed from: protected */
    public void checkPageTransparency(PdfDictionary pageDict, PdfDictionary pageResources) {
        if (this.pdfAOutputIntentColorSpace == null && this.transparencyObjects.size() > 0) {
            if (pageDict.getAsDictionary(PdfName.Group) != null && pageDict.getAsDictionary(PdfName.Group).get(PdfName.f1309CS) != null) {
                return;
            }
            if (!this.transparencyObjects.contains(pageDict)) {
                checkContentsForTransparency(pageDict);
                checkAnnotationsForTransparency(pageDict.getAsArray(PdfName.Annots));
                checkResourcesForTransparency(pageResources, new HashSet());
                return;
            }
            throw new PdfAConformanceException(PdfAConformanceException.f1584x323d23cf);
        }
    }

    /* access modifiers changed from: protected */
    public void checkOutputIntents(PdfDictionary catalog) {
        PdfArray outputIntents = catalog.getAsArray(PdfName.OutputIntents);
        if (outputIntents != null) {
            PdfObject destOutputProfile = null;
            int i = 0;
            while (i < outputIntents.size() && destOutputProfile == null) {
                destOutputProfile = outputIntents.getAsDictionary(i).get(PdfName.DestOutputProfile);
                i++;
            }
            while (i < outputIntents.size()) {
                PdfObject otherDestOutputProfile = outputIntents.getAsDictionary(i).get(PdfName.DestOutputProfile);
                if (otherDestOutputProfile == null || destOutputProfile == otherDestOutputProfile) {
                    i++;
                } else {
                    throw new PdfAConformanceException(PdfAConformanceException.f1570x58110a7);
                }
            }
            if (destOutputProfile != null) {
                String deviceClass = IccProfile.getIccDeviceClass(((PdfStream) destOutputProfile).getBytes());
                if (PdfAChecker.ICC_DEVICE_CLASS_OUTPUT_PROFILE.equals(deviceClass) || PdfAChecker.ICC_DEVICE_CLASS_MONITOR_PROFILE.equals(deviceClass)) {
                    String cs = IccProfile.getIccColorSpaceName(((PdfStream) destOutputProfile).getBytes());
                    if (!PdfAChecker.ICC_COLOR_SPACE_RGB.equals(cs) && !PdfAChecker.ICC_COLOR_SPACE_CMYK.equals(cs) && !PdfAChecker.ICC_COLOR_SPACE_GRAY.equals(cs)) {
                        throw new PdfAConformanceException(PdfAConformanceException.OUTPUT_INTENT_COLOR_SPACE_SHALL_BE_EITHER_GRAY_RGB_OR_CMYK);
                    }
                    return;
                }
                throw new PdfAConformanceException(PdfAConformanceException.f1579x647ed7c5);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Set<PdfName> getForbiddenActions() {
        return forbiddenActions;
    }

    /* access modifiers changed from: protected */
    public Set<PdfName> getAllowedNamedActions() {
        return allowedNamedActions;
    }

    /* access modifiers changed from: protected */
    public void checkColorsUsages() {
        if ((this.rgbIsUsed || this.cmykIsUsed || this.grayIsUsed) && this.pdfAOutputIntentColorSpace == null) {
            throw new PdfAConformanceException(PdfAConformanceException.f1569x27d7d48);
        } else if (this.rgbIsUsed && !PdfAChecker.ICC_COLOR_SPACE_RGB.equals(this.pdfAOutputIntentColorSpace)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1563x45cf5030);
        } else if (this.cmykIsUsed && !PdfAChecker.ICC_COLOR_SPACE_CMYK.equals(this.pdfAOutputIntentColorSpace)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1560x576021a7);
        }
    }

    /* access modifiers changed from: protected */
    public void checkImage(PdfStream image, PdfDictionary currentColorSpaces) {
        if (isAlreadyChecked(image)) {
            checkColorSpace((PdfColorSpace) this.checkedObjectsColorspace.get(image), currentColorSpaces, true, (Boolean) null);
            return;
        }
        PdfObject colorSpaceObj = image.get(PdfName.ColorSpace);
        if (colorSpaceObj != null) {
            PdfColorSpace colorSpace = PdfColorSpace.makeColorSpace(colorSpaceObj);
            checkColorSpace(colorSpace, currentColorSpaces, true, (Boolean) null);
            this.checkedObjectsColorspace.put(image, colorSpace);
        }
        if (image.containsKey(PdfName.Alternates)) {
            throw new PdfAConformanceException(PdfAConformanceException.AN_IMAGE_DICTIONARY_SHALL_NOT_CONTAIN_ALTERNATES_KEY);
        } else if (image.containsKey(PdfName.OPI)) {
            throw new PdfAConformanceException(PdfAConformanceException.AN_IMAGE_DICTIONARY_SHALL_NOT_CONTAIN_OPI_KEY);
        } else if (!image.containsKey(PdfName.Interpolate) || !image.getAsBool(PdfName.Interpolate).booleanValue()) {
            checkRenderingIntent(image.getAsName(PdfName.Intent));
            if (image.getAsStream(PdfName.SMask) != null) {
                this.transparencyObjects.add(image);
            }
            if (image.containsKey(PdfName.SMaskInData) && image.getAsInt(PdfName.SMaskInData).intValue() > 0) {
                this.transparencyObjects.add(image);
            }
            if (PdfName.JPXDecode.equals(image.get(PdfName.Filter))) {
                Jpeg2000ImageData jpgImage = (Jpeg2000ImageData) ImageDataFactory.createJpeg2000(image.getBytes(false));
                Jpeg2000ImageData.Parameters params = jpgImage.getParameters();
                if (!params.isJp2) {
                    throw new PdfAConformanceException(PdfAConformanceException.ONLY_JPX_BASELINE_SET_OF_FEATURES_SHALL_BE_USED);
                } else if (params.numOfComps == 1 || params.numOfComps == 3 || params.numOfComps == 4) {
                    if (params.colorSpecBoxes != null && params.colorSpecBoxes.size() > 1) {
                        int numOfApprox0x01 = 0;
                        for (Jpeg2000ImageData.ColorSpecBox colorSpecBox : params.colorSpecBoxes) {
                            if (colorSpecBox.getApprox() == 1) {
                                numOfApprox0x01++;
                                if (numOfApprox0x01 != 1 || colorSpecBox.getMeth() == 1 || colorSpecBox.getMeth() == 2 || colorSpecBox.getMeth() == 3) {
                                    if (image.get(PdfName.ColorSpace) == null) {
                                        switch (colorSpecBox.getEnumCs()) {
                                            case 1:
                                                PdfDeviceCs.Gray deviceGrayCs = new PdfDeviceCs.Gray();
                                                checkColorSpace(deviceGrayCs, currentColorSpaces, true, (Boolean) null);
                                                this.checkedObjectsColorspace.put(image, deviceGrayCs);
                                                break;
                                            case 3:
                                                PdfDeviceCs.Rgb deviceRgbCs = new PdfDeviceCs.Rgb();
                                                checkColorSpace(deviceRgbCs, currentColorSpaces, true, (Boolean) null);
                                                this.checkedObjectsColorspace.put(image, deviceRgbCs);
                                                break;
                                            case 12:
                                                PdfDeviceCs.Cmyk deviceCmykCs = new PdfDeviceCs.Cmyk();
                                                checkColorSpace(deviceCmykCs, currentColorSpaces, true, (Boolean) null);
                                                this.checkedObjectsColorspace.put(image, deviceCmykCs);
                                                break;
                                        }
                                    }
                                } else {
                                    throw new PdfAConformanceException(PdfAConformanceException.THE_VALUE_OF_THE_METH_ENTRY_IN_COLR_BOX_SHALL_BE_1_2_OR_3);
                                }
                            }
                            if (colorSpecBox.getEnumCs() == 19) {
                                throw new PdfAConformanceException(PdfAConformanceException.JPEG2000_ENUMERATED_COLOUR_SPACE_19_CIEJAB_SHALL_NOT_BE_USED);
                            }
                        }
                        if (numOfApprox0x01 != 1) {
                            throw new PdfAConformanceException(PdfAConformanceException.f1566xd65ad1eb);
                        }
                    }
                    if (jpgImage.getBpc() < 1 || jpgImage.getBpc() > 38) {
                        throw new PdfAConformanceException(PdfAConformanceException.f1583xfbbcd14b);
                    } else if (params.bpcBoxData != null) {
                        throw new PdfAConformanceException(PdfAConformanceException.f1546x5be78c20);
                    }
                } else {
                    throw new PdfAConformanceException(PdfAConformanceException.f1588x7df2e3d3);
                }
            }
        } else {
            throw new PdfAConformanceException(PdfAConformanceException.THE_VALUE_OF_INTERPOLATE_KEY_SHALL_BE_FALSE);
        }
    }

    public void checkFontGlyphs(PdfFont font, PdfStream contentStream) {
        if (font instanceof PdfType3Font) {
            checkType3FontGlyphs((PdfType3Font) font, contentStream);
        }
    }

    /* access modifiers changed from: protected */
    public void checkFormXObject(PdfStream form) {
        checkFormXObject(form, (PdfStream) null);
    }

    /* access modifiers changed from: protected */
    public void checkFormXObject(PdfStream form, PdfStream contentStream) {
        if (!isAlreadyChecked(form)) {
            if (form.containsKey(PdfName.OPI)) {
                throw new PdfAConformanceException(PdfAConformanceException.A_FORM_XOBJECT_DICTIONARY_SHALL_NOT_CONTAIN_OPI_KEY);
            } else if (form.containsKey(PdfName.f1372PS)) {
                throw new PdfAConformanceException(PdfAConformanceException.A_FORM_XOBJECT_DICTIONARY_SHALL_NOT_CONTAIN_PS_KEY);
            } else if (!PdfName.f1372PS.equals(form.getAsName(PdfName.Subtype2))) {
                if (isContainsTransparencyGroup(form)) {
                    if (contentStream != null) {
                        this.transparencyObjects.add(contentStream);
                    } else {
                        this.transparencyObjects.add(form);
                    }
                    PdfObject cs = form.getAsDictionary(PdfName.Group).get(PdfName.f1309CS);
                    PdfDictionary resources = form.getAsDictionary(PdfName.Resources);
                    if (!(cs == null || resources == null)) {
                        checkColorSpace(PdfColorSpace.makeColorSpace(cs), resources.getAsDictionary(PdfName.ColorSpace), true, (Boolean) null);
                    }
                }
                checkResources(form.getAsDictionary(PdfName.Resources));
                checkContentStream(form);
            } else {
                throw new PdfAConformanceException(PdfAConformanceException.f1555x71e4aedf);
            }
        }
    }

    private void checkContentsForTransparency(PdfDictionary pageDict) {
        PdfStream contentStream = pageDict.getAsStream(PdfName.Contents);
        if (contentStream == null || !this.transparencyObjects.contains(contentStream)) {
            PdfArray contentSteamArray = pageDict.getAsArray(PdfName.Contents);
            if (contentSteamArray != null) {
                int i = 0;
                while (i < contentSteamArray.size()) {
                    if (!this.transparencyObjects.contains(contentSteamArray.get(i))) {
                        i++;
                    } else {
                        throw new PdfAConformanceException(PdfAConformanceException.f1584x323d23cf);
                    }
                }
                return;
            }
            return;
        }
        throw new PdfAConformanceException(PdfAConformanceException.f1584x323d23cf);
    }

    private void checkAnnotationsForTransparency(PdfArray annotations) {
        if (annotations != null) {
            for (int i = 0; i < annotations.size(); i++) {
                PdfDictionary ap = annotations.getAsDictionary(i).getAsDictionary(PdfName.f1291AP);
                if (ap != null) {
                    checkAppearanceStreamForTransparency(ap, new HashSet());
                }
            }
        }
    }

    private void checkAppearanceStreamForTransparency(PdfDictionary ap, Set<PdfObject> checkedObjects) {
        if (!checkedObjects.contains(ap)) {
            checkedObjects.add(ap);
            for (PdfObject val : ap.values()) {
                if (this.transparencyObjects.contains(val)) {
                    throw new PdfAConformanceException(PdfAConformanceException.f1584x323d23cf);
                } else if (val.isDictionary()) {
                    checkAppearanceStreamForTransparency((PdfDictionary) val, checkedObjects);
                } else if (val.isStream()) {
                    checkObjectWithResourcesForTransparency(val, checkedObjects);
                }
            }
        }
    }

    private void checkObjectWithResourcesForTransparency(PdfObject objectWithResources, Set<PdfObject> checkedObjects) {
        if (!checkedObjects.contains(objectWithResources)) {
            checkedObjects.add(objectWithResources);
            if (this.transparencyObjects.contains(objectWithResources)) {
                throw new PdfAConformanceException(PdfAConformanceException.f1584x323d23cf);
            } else if (objectWithResources instanceof PdfDictionary) {
                checkResourcesForTransparency(((PdfDictionary) objectWithResources).getAsDictionary(PdfName.Resources), checkedObjects);
            }
        }
    }

    private void checkResourcesForTransparency(PdfDictionary resources, Set<PdfObject> checkedObjects) {
        if (resources != null) {
            checkSingleResourceTypeForTransparency(resources.getAsDictionary(PdfName.XObject), checkedObjects);
            checkSingleResourceTypeForTransparency(resources.getAsDictionary(PdfName.Pattern), checkedObjects);
        }
    }

    private void checkSingleResourceTypeForTransparency(PdfDictionary singleResourceDict, Set<PdfObject> checkedObjects) {
        if (singleResourceDict != null) {
            for (PdfObject resource : singleResourceDict.values()) {
                checkObjectWithResourcesForTransparency(resource, checkedObjects);
            }
        }
    }

    private void checkBlendMode(PdfName blendMode) {
        if (!allowedBlendModes.contains(blendMode)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1576xffb2bc34);
        }
    }

    private void checkSeparationInsideDeviceN(PdfArray separation, PdfObject deviceNColorSpace, PdfObject deviceNTintTransform) {
        if (!isAltCSIsTheSame(separation.get(2), deviceNColorSpace) || !deviceNTintTransform.equals(separation.get(3))) {
            LoggerFactory.getLogger((Class<?>) PdfAChecker.class).warn(PdfAConformanceLogMessageConstant.f1593x93fa878f);
        }
        checkSeparationCS(separation);
    }

    private void checkSeparationCS(PdfArray separation) {
        boolean tintTransformIsTheSame = false;
        if (this.separationColorSpaces.containsKey(separation.getAsName(0))) {
            PdfArray sameNameSeparation = this.separationColorSpaces.get(separation.getAsName(0));
            boolean altCSIsTheSame = isAltCSIsTheSame(separation.get(2), sameNameSeparation.get(2));
            PdfObject f1Obj = separation.get(3);
            PdfObject f2Obj = sameNameSeparation.get(3);
            if ((f1Obj.getType() == f2Obj.getType() && (f1Obj.isDictionary() || f1Obj.isStream())) && f1Obj.equals(f2Obj)) {
                tintTransformIsTheSame = true;
            }
            if (!altCSIsTheSame || !tintTransformIsTheSame) {
                throw new PdfAConformanceException(PdfAConformanceException.f1589xc1097c94);
            }
            return;
        }
        this.separationColorSpaces.put(separation.getAsName(0), separation);
    }

    private boolean isAltCSIsTheSame(PdfObject cs1, PdfObject cs2) {
        if (cs1 instanceof PdfName) {
            return cs1.equals(cs2);
        }
        if (!(cs1 instanceof PdfArray) || !(cs2 instanceof PdfArray)) {
            return false;
        }
        return ((PdfArray) cs1).get(0).equals(((PdfArray) cs1).get(0));
    }

    private void checkCatalogConfig(PdfDictionary config, HashSet<PdfObject> ocgs, HashSet<String> names) {
        PdfString name = config.getAsString(PdfName.Name);
        if (name == null) {
            throw new PdfAConformanceException(PdfAConformanceException.f1577xd4a16037);
        } else if (!names.add(name.toUnicodeString())) {
            throw new PdfAConformanceException(PdfAConformanceException.f1590x73565af5);
        } else if (!config.containsKey(PdfName.f1292AS)) {
            PdfArray orderArray = config.getAsArray(PdfName.Order);
            if (orderArray != null) {
                HashSet<PdfObject> order = new HashSet<>();
                fillOrderRecursively(orderArray, order);
                if (!order.equals(ocgs)) {
                    throw new PdfAConformanceException(PdfAConformanceException.ORDER_ARRAY_SHALL_CONTAIN_REFERENCES_TO_ALL_OCGS);
                }
            }
        } else {
            throw new PdfAConformanceException(PdfAConformanceException.f1582xb7d3b283);
        }
    }

    private void fillOrderRecursively(PdfArray orderArray, Set<PdfObject> order) {
        Iterator<PdfObject> it = orderArray.iterator();
        while (it.hasNext()) {
            PdfObject orderItem = it.next();
            if (!orderItem.isArray()) {
                order.add(orderItem);
            } else {
                fillOrderRecursively((PdfArray) orderItem, order);
            }
        }
    }

    private boolean checkDefaultCS(PdfDictionary currentColorSpaces, Boolean fill, PdfName defaultCsName, int numOfComponents) {
        if (currentColorSpaces == null || !currentColorSpaces.containsKey(defaultCsName)) {
            return false;
        }
        PdfColorSpace defaultCs = PdfColorSpace.makeColorSpace(currentColorSpaces.get(defaultCsName));
        if (defaultCs instanceof PdfDeviceCs) {
            throw new PdfAConformanceException(PdfAConformanceException.COLOR_SPACE_0_SHALL_BE_DEVICE_INDEPENDENT).setMessageParams(defaultCsName.toString());
        } else if (defaultCs.getNumberOfComponents() == numOfComponents) {
            checkColorSpace(defaultCs, currentColorSpaces, false, fill);
            return true;
        } else {
            throw new PdfAConformanceException(PdfAConformanceException.COLOR_SPACE_0_SHALL_HAVE_1_COMPONENTS).setMessageParams(defaultCsName.getValue(), Integer.valueOf(numOfComponents));
        }
    }

    private void checkType3FontGlyphs(PdfType3Font font, PdfStream contentStream) {
        Type3Glyph type3Glyph;
        for (int i = 0; i <= 255; i++) {
            FontEncoding fontEncoding = font.getFontEncoding();
            if (fontEncoding.canDecode(i) && (type3Glyph = font.getType3Glyph(fontEncoding.getUnicode(i))) != null) {
                checkFormXObject(type3Glyph.getContentStream(), contentStream);
            }
        }
    }
}
