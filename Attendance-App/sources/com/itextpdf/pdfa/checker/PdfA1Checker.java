package com.itextpdf.pdfa.checker;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.PatternColor;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfTrueTypeFont;
import com.itextpdf.kernel.font.PdfType3Font;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfXrefTable;
import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.pdfa.PdfAConformanceException;
import com.itextpdf.pdfa.PdfAConformanceLogMessageConstant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.LoggerFactory;

public class PdfA1Checker extends PdfAChecker {
    private static final int MAX_NUMBER_OF_DEVICEN_COLOR_COMPONENTS = 8;
    protected static final Set<PdfName> allowedNamedActions = new HashSet(Arrays.asList(new PdfName[]{PdfName.NextPage, PdfName.PrevPage, PdfName.FirstPage, PdfName.LastPage}));
    protected static final Set<PdfName> allowedRenderingIntents = new HashSet(Arrays.asList(new PdfName[]{PdfName.RelativeColorimetric, PdfName.AbsoluteColorimetric, PdfName.Perceptual, PdfName.Saturation}));
    protected static final Set<PdfName> contentAnnotations = new HashSet(Arrays.asList(new PdfName[]{PdfName.Text, PdfName.FreeText, PdfName.Line, PdfName.Square, PdfName.Circle, PdfName.Stamp, PdfName.Ink, PdfName.Popup}));
    protected static final Set<PdfName> forbiddenActions = new HashSet(Arrays.asList(new PdfName[]{PdfName.Launch, PdfName.Sound, PdfName.Movie, PdfName.ResetForm, PdfName.ImportData, PdfName.JavaScript, PdfName.Hide}));
    protected static final Set<PdfName> forbiddenAnnotations = new HashSet(Arrays.asList(new PdfName[]{PdfName.Sound, PdfName.Movie, PdfName.FileAttachment}));
    private static final long serialVersionUID = 5103027349795298132L;

    public PdfA1Checker(PdfAConformanceLevel conformanceLevel) {
        super(conformanceLevel);
    }

    public void checkCanvasStack(char stackOperation) {
        if ('q' == stackOperation) {
            int i = this.gsStackDepth + 1;
            this.gsStackDepth = i;
            if (i > 28) {
                throw new PdfAConformanceException(PdfAConformanceException.GRAPHICS_STATE_STACK_DEPTH_IS_GREATER_THAN_28);
            }
        } else if ('Q' == stackOperation) {
            this.gsStackDepth--;
        }
    }

    public void checkInlineImage(PdfStream inlineImage, PdfDictionary currentColorSpaces) {
        PdfObject filter = inlineImage.get(PdfName.Filter);
        if (filter instanceof PdfName) {
            if (filter.equals(PdfName.LZWDecode)) {
                throw new PdfAConformanceException(PdfAConformanceException.LZWDECODE_FILTER_IS_NOT_PERMITTED);
            }
        } else if (filter instanceof PdfArray) {
            int i = 0;
            while (i < ((PdfArray) filter).size()) {
                if (!((PdfArray) filter).getAsName(i).equals(PdfName.LZWDecode)) {
                    i++;
                } else {
                    throw new PdfAConformanceException(PdfAConformanceException.LZWDECODE_FILTER_IS_NOT_PERMITTED);
                }
            }
        }
        checkImage(inlineImage, currentColorSpaces);
    }

    public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill) {
        checkColorSpace(color.getColorSpace(), currentColorSpaces, true, fill);
    }

    public void checkColor(Color color, PdfDictionary currentColorSpaces, Boolean fill, PdfStream stream) {
        checkColorSpace(color.getColorSpace(), currentColorSpaces, true, fill);
        if (color instanceof PatternColor) {
            PdfPattern pattern = ((PatternColor) color).getPattern();
            if (pattern instanceof PdfPattern.Tiling) {
                checkContentStream((PdfStream) pattern.getPdfObject());
            }
        }
    }

    public void checkColorSpace(PdfColorSpace colorSpace, PdfDictionary currentColorSpaces, boolean checkAlternate, Boolean fill) {
        if (colorSpace instanceof PdfSpecialCs.Separation) {
            colorSpace = ((PdfSpecialCs.Separation) colorSpace).getBaseCs();
        } else if (colorSpace instanceof PdfSpecialCs.DeviceN) {
            PdfSpecialCs.DeviceN deviceNColorspace = (PdfSpecialCs.DeviceN) colorSpace;
            if (deviceNColorspace.getNumberOfComponents() <= 8) {
                colorSpace = deviceNColorspace.getBaseCs();
            } else {
                throw new PdfAConformanceException(PdfAConformanceException.f1587x736cbd, 8);
            }
        }
        if (colorSpace instanceof PdfDeviceCs.Rgb) {
            if (!this.cmykIsUsed) {
                this.rgbIsUsed = true;
                return;
            }
            throw new PdfAConformanceException(PdfAConformanceException.f1561xfe2f81a7);
        } else if (colorSpace instanceof PdfDeviceCs.Cmyk) {
            if (!this.rgbIsUsed) {
                this.cmykIsUsed = true;
                return;
            }
            throw new PdfAConformanceException(PdfAConformanceException.f1561xfe2f81a7);
        } else if (colorSpace instanceof PdfDeviceCs.Gray) {
            this.grayIsUsed = true;
        }
    }

    public void checkXrefTable(PdfXrefTable xrefTable) {
        if (((long) xrefTable.getCountOfIndirectObjects()) > getMaxNumberOfIndirectObjects()) {
            throw new PdfAConformanceException(PdfAConformanceException.MAXIMUM_NUMBER_OF_INDIRECT_OBJECTS_EXCEEDED);
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
    public long getMaxNumberOfIndirectObjects() {
        return 8388607;
    }

    /* access modifiers changed from: protected */
    public void checkColorsUsages() {
        if ((this.rgbIsUsed || this.cmykIsUsed || this.grayIsUsed) && this.pdfAOutputIntentColorSpace == null) {
            throw new PdfAConformanceException(PdfAConformanceException.f1568x59840b6b);
        } else if (this.rgbIsUsed && !PdfAChecker.ICC_COLOR_SPACE_RGB.equals(this.pdfAOutputIntentColorSpace)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1562x729bf01d);
        } else if (this.cmykIsUsed && !PdfAChecker.ICC_COLOR_SPACE_CMYK.equals(this.pdfAOutputIntentColorSpace)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1559x91de4871);
        }
    }

    public void checkExtGState(CanvasGraphicsState extGState) {
        checkExtGState(extGState, (PdfStream) null);
    }

    public void checkExtGState(CanvasGraphicsState extGState, PdfStream contentStream) {
        if (extGState.getTransferFunction() == null) {
            PdfObject transferFunction2 = extGState.getTransferFunction2();
            if (transferFunction2 == null || PdfName.Default.equals(transferFunction2)) {
                checkRenderingIntent(extGState.getRenderingIntent());
                PdfObject softMask = extGState.getSoftMask();
                if (softMask == null || PdfName.None.equals(softMask)) {
                    PdfObject bm = extGState.getBlendMode();
                    if (bm == null || PdfName.Normal.equals(bm) || PdfName.Compatible.equals(bm)) {
                        Float ca = Float.valueOf(extGState.getStrokeOpacity());
                        if (ca == null || ca.floatValue() == 1.0f) {
                            Float ca2 = Float.valueOf(extGState.getFillOpacity());
                            if (ca2 != null && ca2.floatValue() != 1.0f) {
                                throw new PdfAConformanceException(PdfAConformanceException.TRANSPARENCY_IS_NOT_ALLOWED_AND_CA_SHALL_BE_EQUAL_TO_1);
                            }
                            return;
                        }
                        throw new PdfAConformanceException(PdfAConformanceException.TRANSPARENCY_IS_NOT_ALLOWED_CA_SHALL_BE_EQUAL_TO_1);
                    }
                    throw new PdfAConformanceException(PdfAConformanceException.BLEND_MODE_SHALL_HAVE_VALUE_NORMAL_OR_COMPATIBLE);
                }
                throw new PdfAConformanceException(PdfAConformanceException.THE_SMASK_KEY_IS_NOT_ALLOWED_IN_EXTGSTATE);
            }
            throw new PdfAConformanceException(PdfAConformanceException.f1550x46add6e4);
        }
        throw new PdfAConformanceException(PdfAConformanceException.AN_EXTGSTATE_DICTIONARY_SHALL_NOT_CONTAIN_THE_TR_KEY);
    }

    public void checkRenderingIntent(PdfName intent) {
        if (intent != null && !allowedRenderingIntents.contains(intent)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1571x75174367);
        }
    }

    public void checkFont(PdfFont pdfFont) {
        if (pdfFont.isEmbedded()) {
            if (pdfFont instanceof PdfTrueTypeFont) {
                PdfTrueTypeFont trueTypeFont = (PdfTrueTypeFont) pdfFont;
                if (trueTypeFont.getFontEncoding().isFontSpecific()) {
                    checkSymbolicTrueTypeFont(trueTypeFont);
                } else {
                    checkNonSymbolicTrueTypeFont(trueTypeFont);
                }
            }
            if (pdfFont instanceof PdfType3Font) {
                PdfDictionary charProcs = ((PdfDictionary) pdfFont.getPdfObject()).getAsDictionary(PdfName.CharProcs);
                for (PdfName charName : charProcs.keySet()) {
                    checkContentStream(charProcs.getAsStream(charName));
                }
                return;
            }
            return;
        }
        throw new PdfAConformanceException(PdfAConformanceException.ALL_THE_FONTS_MUST_BE_EMBEDDED_THIS_ONE_IS_NOT_0).setMessageParams(pdfFont.getFontProgram().getFontNames().getFontName());
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0037 A[Catch:{ IOException -> 0x004e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void checkContentStream(com.itextpdf.kernel.pdf.PdfStream r8) {
        /*
            r7 = this;
            boolean r0 = r7.isFullCheckMode()
            if (r0 != 0) goto L_0x000c
            boolean r0 = r8.isModified()
            if (r0 == 0) goto L_0x004d
        L_0x000c:
            byte[] r0 = r8.getBytes()
            com.itextpdf.io.source.PdfTokenizer r1 = new com.itextpdf.io.source.PdfTokenizer
            com.itextpdf.io.source.RandomAccessFileOrArray r2 = new com.itextpdf.io.source.RandomAccessFileOrArray
            com.itextpdf.io.source.RandomAccessSourceFactory r3 = new com.itextpdf.io.source.RandomAccessSourceFactory
            r3.<init>()
            com.itextpdf.io.source.IRandomAccessSource r3 = r3.createSource((byte[]) r0)
            r2.<init>(r3)
            r1.<init>(r2)
            com.itextpdf.kernel.pdf.canvas.parser.util.PdfCanvasParser r2 = new com.itextpdf.kernel.pdf.canvas.parser.util.PdfCanvasParser
            r2.<init>(r1)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
        L_0x002d:
            java.util.List r4 = r2.parse(r3)     // Catch:{ IOException -> 0x004e }
            int r4 = r4.size()     // Catch:{ IOException -> 0x004e }
            if (r4 <= 0) goto L_0x004c
            java.util.Iterator r4 = r3.iterator()     // Catch:{ IOException -> 0x004e }
        L_0x003b:
            boolean r5 = r4.hasNext()     // Catch:{ IOException -> 0x004e }
            if (r5 == 0) goto L_0x004b
            java.lang.Object r5 = r4.next()     // Catch:{ IOException -> 0x004e }
            com.itextpdf.kernel.pdf.PdfObject r5 = (com.itextpdf.kernel.pdf.PdfObject) r5     // Catch:{ IOException -> 0x004e }
            r7.checkContentStreamObject(r5)     // Catch:{ IOException -> 0x004e }
            goto L_0x003b
        L_0x004b:
            goto L_0x002d
        L_0x004c:
        L_0x004d:
            return
        L_0x004e:
            r4 = move-exception
            com.itextpdf.kernel.PdfException r5 = new com.itextpdf.kernel.PdfException
            java.lang.String r6 = "Cannot parse content stream."
            r5.<init>((java.lang.String) r6, (java.lang.Throwable) r4)
            goto L_0x0058
        L_0x0057:
            throw r5
        L_0x0058:
            goto L_0x0057
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.pdfa.checker.PdfA1Checker.checkContentStream(com.itextpdf.kernel.pdf.PdfStream):void");
    }

    /* access modifiers changed from: protected */
    public void checkContentStreamObject(PdfObject object) {
        switch (object.getType()) {
            case 1:
                PdfArray array = (PdfArray) object;
                checkPdfArray(array);
                Iterator<PdfObject> it = array.iterator();
                while (it.hasNext()) {
                    checkContentStreamObject(it.next());
                }
                return;
            case 3:
                PdfDictionary dictionary = (PdfDictionary) object;
                checkPdfDictionary(dictionary);
                for (PdfName name : dictionary.keySet()) {
                    checkPdfName(name);
                    checkPdfObject(dictionary.get(name, false));
                }
                for (PdfObject obj : dictionary.values()) {
                    checkContentStreamObject(obj);
                }
                return;
            case 6:
                checkPdfName((PdfName) object);
                return;
            case 8:
                checkPdfNumber((PdfNumber) object);
                return;
            case 10:
                checkPdfString((PdfString) object);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void checkNonSymbolicTrueTypeFont(PdfTrueTypeFont trueTypeFont) {
        String encoding = trueTypeFont.getFontEncoding().getBaseEncoding();
        if ((!"Cp1252".equals(encoding) && !PdfEncodings.MACROMAN.equals(encoding)) || trueTypeFont.getFontEncoding().hasDifferences()) {
            throw new PdfAConformanceException(PdfAConformanceException.f1548xc9ab85b4, trueTypeFont);
        }
    }

    /* access modifiers changed from: protected */
    public void checkSymbolicTrueTypeFont(PdfTrueTypeFont trueTypeFont) {
        if (trueTypeFont.getFontEncoding().hasDifferences()) {
            throw new PdfAConformanceException(PdfAConformanceException.ALL_SYMBOLIC_TRUE_TYPE_FONTS_SHALL_NOT_SPECIFY_ENCODING);
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
            if (image.containsKey(PdfName.SMask) && !PdfName.None.equals(image.getAsName(PdfName.SMask))) {
                throw new PdfAConformanceException(PdfAConformanceException.THE_SMASK_KEY_IS_NOT_ALLOWED_IN_XOBJECTS);
            }
        } else {
            throw new PdfAConformanceException(PdfAConformanceException.THE_VALUE_OF_INTERPOLATE_KEY_SHALL_BE_FALSE);
        }
    }

    /* access modifiers changed from: protected */
    public void checkFormXObject(PdfStream form) {
        if (!isAlreadyChecked(form)) {
            if (form.containsKey(PdfName.OPI)) {
                throw new PdfAConformanceException(PdfAConformanceException.A_FORM_XOBJECT_DICTIONARY_SHALL_NOT_CONTAIN_OPI_KEY);
            } else if (form.containsKey(PdfName.f1372PS)) {
                throw new PdfAConformanceException(PdfAConformanceException.A_FORM_XOBJECT_DICTIONARY_SHALL_NOT_CONTAIN_PS_KEY);
            } else if (PdfName.f1372PS.equals(form.getAsName(PdfName.Subtype2))) {
                throw new PdfAConformanceException(PdfAConformanceException.f1555x71e4aedf);
            } else if (form.containsKey(PdfName.SMask) && !PdfName.None.equals(form.getAsName(PdfName.SMask))) {
                throw new PdfAConformanceException(PdfAConformanceException.THE_SMASK_KEY_IS_NOT_ALLOWED_IN_XOBJECTS);
            } else if (!isContainsTransparencyGroup(form)) {
                checkResources(form.getAsDictionary(PdfName.Resources));
                checkContentStream(form);
            } else {
                throw new PdfAConformanceException(PdfAConformanceException.f1556x1337f931);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkLogicalStructure(PdfDictionary catalog) {
        if (checkStructure(this.conformanceLevel)) {
            PdfDictionary markInfo = catalog.getAsDictionary(PdfName.MarkInfo);
            if (markInfo == null || markInfo.getAsBoolean(PdfName.Marked) == null || !markInfo.getAsBoolean(PdfName.Marked).getValue()) {
                throw new PdfAConformanceException(PdfAConformanceException.f1554xe4fc9e16);
            } else if (!catalog.containsKey(PdfName.Lang)) {
                LoggerFactory.getLogger((Class<?>) PdfAChecker.class).warn(PdfAConformanceLogMessageConstant.CATALOG_SHOULD_CONTAIN_LANG_ENTRY);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkMetaData(PdfDictionary catalog) {
        if (!catalog.containsKey(PdfName.Metadata)) {
            throw new PdfAConformanceException(PdfAConformanceException.A_CATALOG_DICTIONARY_SHALL_CONTAIN_METADATA_ENTRY);
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
        }
    }

    /* access modifiers changed from: protected */
    public void checkPdfNumber(PdfNumber number) {
        if (number.hasDecimalPoint()) {
            if (((double) Math.abs(number.longValue())) > getMaxRealValue()) {
                throw new PdfAConformanceException(PdfAConformanceException.REAL_NUMBER_IS_OUT_OF_RANGE);
            }
        } else if (number.longValue() > getMaxIntegerValue() || number.longValue() < getMinIntegerValue()) {
            throw new PdfAConformanceException(PdfAConformanceException.INTEGER_NUMBER_IS_OUT_OF_RANGE);
        }
    }

    /* access modifiers changed from: protected */
    public double getMaxRealValue() {
        return 32767.0d;
    }

    /* access modifiers changed from: protected */
    public long getMaxIntegerValue() {
        return 2147483647L;
    }

    /* access modifiers changed from: protected */
    public long getMinIntegerValue() {
        return -2147483648L;
    }

    /* access modifiers changed from: protected */
    public void checkPdfArray(PdfArray array) {
        if (array.size() > getMaxArrayCapacity()) {
            throw new PdfAConformanceException(PdfAConformanceException.MAXIMUM_ARRAY_CAPACITY_IS_EXCEEDED);
        }
    }

    /* access modifiers changed from: protected */
    public void checkPdfDictionary(PdfDictionary dictionary) {
        if (dictionary.size() > getMaxDictionaryCapacity()) {
            throw new PdfAConformanceException(PdfAConformanceException.MAXIMUM_DICTIONARY_CAPACITY_IS_EXCEEDED);
        }
    }

    /* access modifiers changed from: protected */
    public void checkPdfStream(PdfStream stream) {
        checkPdfDictionary(stream);
        if (stream.containsKey(PdfName.f1324F) || stream.containsKey(PdfName.FFilter) || stream.containsKey(PdfName.FDecodeParams)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1581x774c9db);
        }
        PdfObject filter = stream.get(PdfName.Filter);
        if (filter instanceof PdfName) {
            if (filter.equals(PdfName.LZWDecode)) {
                throw new PdfAConformanceException(PdfAConformanceException.LZWDECODE_FILTER_IS_NOT_PERMITTED);
            }
        } else if (filter instanceof PdfArray) {
            Iterator<PdfObject> it = ((PdfArray) filter).iterator();
            while (it.hasNext()) {
                if (it.next().equals(PdfName.LZWDecode)) {
                    throw new PdfAConformanceException(PdfAConformanceException.LZWDECODE_FILTER_IS_NOT_PERMITTED);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkPdfName(PdfName name) {
        if (name.getValue().length() > getMaxNameLength()) {
            throw new PdfAConformanceException(PdfAConformanceException.PDF_NAME_IS_TOO_LONG);
        }
    }

    /* access modifiers changed from: protected */
    public int getMaxNameLength() {
        return 127;
    }

    /* access modifiers changed from: protected */
    public void checkPdfString(PdfString string) {
        if (string.getValueBytes().length > getMaxStringLength()) {
            throw new PdfAConformanceException(PdfAConformanceException.PDF_STRING_IS_TOO_LONG);
        }
    }

    /* access modifiers changed from: protected */
    public int getMaxStringLength() {
        return 65535;
    }

    /* access modifiers changed from: protected */
    public void checkPageSize(PdfDictionary page) {
    }

    /* access modifiers changed from: protected */
    public void checkFileSpec(PdfDictionary fileSpec) {
        if (fileSpec.containsKey(PdfName.f1321EF)) {
            throw new PdfAConformanceException(PdfAConformanceException.FILE_SPECIFICATION_DICTIONARY_SHALL_NOT_CONTAIN_THE_EF_KEY);
        }
    }

    /* access modifiers changed from: protected */
    public void checkAnnotation(PdfDictionary annotDic) {
        PdfName subtype = annotDic.getAsName(PdfName.Subtype);
        if (subtype == null) {
            throw new PdfAConformanceException(PdfAConformanceException.ANNOTATION_TYPE_0_IS_NOT_PERMITTED).setMessageParams("null");
        } else if (!forbiddenAnnotations.contains(subtype)) {
            PdfNumber ca = annotDic.getAsNumber(PdfName.f1303CA);
            if (ca != null && ((double) ca.floatValue()) != 1.0d) {
                throw new PdfAConformanceException(PdfAConformanceException.f1549x1362ef29);
            } else if (annotDic.containsKey(PdfName.f1324F)) {
                int flags = annotDic.getAsInt(PdfName.f1324F).intValue();
                if (!checkFlag(flags, 4) || checkFlag(flags, 2) || checkFlag(flags, 1) || checkFlag(flags, 32)) {
                    throw new PdfAConformanceException(PdfAConformanceException.f1585x4ce094c8);
                } else if (subtype.equals(PdfName.Text) && (!checkFlag(flags, 8) || !checkFlag(flags, 16))) {
                    throw new PdfAConformanceException(PdfAConformanceLogMessageConstant.f1592xba373e7c);
                } else if ((annotDic.containsKey(PdfName.f1300C) || annotDic.containsKey(PdfName.f1340IC)) && !PdfAChecker.ICC_COLOR_SPACE_RGB.equals(this.pdfAOutputIntentColorSpace)) {
                    throw new PdfAConformanceException(PdfAConformanceException.f1558x93667b7);
                } else {
                    PdfDictionary ap = annotDic.getAsDictionary(PdfName.f1291AP);
                    if (ap != null) {
                        if (ap.containsKey(PdfName.f1312D) || ap.containsKey(PdfName.f1376R)) {
                            throw new PdfAConformanceException(PdfAConformanceException.f1552x5f64bc25);
                        }
                        if (!PdfName.Widget.equals(annotDic.getAsName(PdfName.Subtype)) || !PdfName.Btn.equals(annotDic.getAsName(PdfName.f1327FT))) {
                            if (ap.getAsStream(PdfName.f1357N) == null) {
                                throw new PdfAConformanceException(PdfAConformanceException.f1552x5f64bc25);
                            }
                        } else if (ap.getAsDictionary(PdfName.f1357N) == null) {
                            throw new PdfAConformanceException(PdfAConformanceException.N_KEY_SHALL_BE_APPEARANCE_SUBDICTIONARY);
                        }
                        checkResourcesOfAppearanceStreams(ap);
                    }
                    if (PdfName.Widget.equals(subtype) && (annotDic.containsKey(PdfName.f1288AA) || annotDic.containsKey(PdfName.f1287A))) {
                        throw new PdfAConformanceException(PdfAConformanceException.f1591x1eeb393f);
                    } else if (annotDic.containsKey(PdfName.f1288AA)) {
                        throw new PdfAConformanceException(PdfAConformanceException.AN_ANNOTATION_DICTIONARY_SHALL_NOT_CONTAIN_AA_KEY);
                    } else if (checkStructure(this.conformanceLevel) && contentAnnotations.contains(subtype) && !annotDic.containsKey(PdfName.Contents)) {
                        throw new PdfAConformanceException(PdfAConformanceException.ANNOTATION_OF_TYPE_0_SHOULD_HAVE_CONTENTS_KEY).setMessageParams(subtype.getValue());
                    }
                }
            } else {
                throw new PdfAConformanceException(PdfAConformanceException.AN_ANNOTATION_DICTIONARY_SHALL_CONTAIN_THE_F_KEY);
            }
        } else {
            throw new PdfAConformanceException(PdfAConformanceException.ANNOTATION_TYPE_0_IS_NOT_PERMITTED).setMessageParams(subtype.getValue());
        }
    }

    /* access modifiers changed from: protected */
    public void checkForm(PdfDictionary form) {
        if (form != null) {
            PdfBoolean needAppearances = form.getAsBoolean(PdfName.NeedAppearances);
            if (needAppearances == null || !needAppearances.getValue()) {
                checkResources(form.getAsDictionary(PdfName.f1315DR));
                PdfArray fields = form.getAsArray(PdfName.Fields);
                if (fields != null) {
                    Iterator<PdfObject> it = getFormFields(fields).iterator();
                    while (it.hasNext()) {
                        PdfDictionary fieldDic = (PdfDictionary) it.next();
                        if (fieldDic.containsKey(PdfName.f1287A) || fieldDic.containsKey(PdfName.f1288AA)) {
                            throw new PdfAConformanceException(PdfAConformanceException.f1591x1eeb393f);
                        }
                        checkResources(fieldDic.getAsDictionary(PdfName.f1315DR));
                    }
                    return;
                }
                return;
            }
            throw new PdfAConformanceException(PdfAConformanceException.f1574x9609c9f4);
        }
    }

    /* access modifiers changed from: protected */
    public void checkAction(PdfDictionary action) {
        PdfName n;
        if (!isAlreadyChecked(action)) {
            PdfName s = action.getAsName(PdfName.f1385S);
            if (getForbiddenActions().contains(s)) {
                throw new PdfAConformanceException(PdfAConformanceException._0_ACTIONS_ARE_NOT_ALLOWED).setMessageParams(s.getValue());
            } else if (s.equals(PdfName.Named) && (n = action.getAsName(PdfName.f1357N)) != null && !getAllowedNamedActions().contains(n)) {
                throw new PdfAConformanceException(PdfAConformanceException.NAMED_ACTION_TYPE_0_IS_NOT_ALLOWED).setMessageParams(n.getValue());
            } else if (s.equals(PdfName.SetState) || s.equals(PdfName.NoOp)) {
                throw new PdfAConformanceException(PdfAConformanceException.DEPRECATED_SETSTATE_AND_NOOP_ACTIONS_ARE_NOT_ALLOWED);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkCatalogValidEntries(PdfDictionary catalogDict) {
        if (catalogDict.containsKey(PdfName.f1288AA)) {
            throw new PdfAConformanceException(PdfAConformanceException.A_CATALOG_DICTIONARY_SHALL_NOT_CONTAIN_AA_ENTRY);
        } else if (catalogDict.containsKey(PdfName.OCProperties)) {
            throw new PdfAConformanceException(PdfAConformanceException.A_CATALOG_DICTIONARY_SHALL_NOT_CONTAIN_OCPROPERTIES_KEY);
        } else if (catalogDict.containsKey(PdfName.Names) && catalogDict.getAsDictionary(PdfName.Names).containsKey(PdfName.EmbeddedFiles)) {
            throw new PdfAConformanceException(PdfAConformanceException.A_NAME_DICTIONARY_SHALL_NOT_CONTAIN_THE_EMBEDDED_FILES_KEY);
        }
    }

    /* access modifiers changed from: protected */
    public void checkPageObject(PdfDictionary pageDict, PdfDictionary pageResources) {
        PdfDictionary actions = pageDict.getAsDictionary(PdfName.f1288AA);
        if (actions != null) {
            for (PdfName key : actions.keySet()) {
                checkAction(actions.getAsDictionary(key));
            }
        }
        if (isContainsTransparencyGroup(pageDict)) {
            throw new PdfAConformanceException(PdfAConformanceException.f1557x1a5b53fc);
        }
    }

    /* access modifiers changed from: protected */
    public void checkTrailer(PdfDictionary trailer) {
        if (trailer.containsKey(PdfName.Encrypt)) {
            throw new PdfAConformanceException(PdfAConformanceException.KEYWORD_ENCRYPT_SHALL_NOT_BE_USED_IN_THE_TRAILER_DICTIONARY);
        }
    }

    /* access modifiers changed from: protected */
    public PdfArray getFormFields(PdfArray array) {
        PdfArray fields = new PdfArray();
        Iterator<PdfObject> it = array.iterator();
        while (it.hasNext()) {
            PdfObject field = it.next();
            PdfArray kids = ((PdfDictionary) field).getAsArray(PdfName.Kids);
            fields.add(field);
            if (kids != null) {
                fields.addAll(getFormFields(kids));
            }
        }
        return fields;
    }

    private int getMaxArrayCapacity() {
        return 8191;
    }

    private int getMaxDictionaryCapacity() {
        return 4095;
    }
}
