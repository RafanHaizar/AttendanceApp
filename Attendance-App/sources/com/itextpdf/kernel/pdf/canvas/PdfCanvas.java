package com.itextpdf.kernel.pdf.canvas;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.PatternColor;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfType0Font;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.IsoKey;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfOutputStream;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.canvas.wmf.WmfImageHelper;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.colorspace.PdfShading;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.layer.IPdfOCG;
import com.itextpdf.kernel.pdf.layer.PdfLayer;
import com.itextpdf.kernel.pdf.layer.PdfLayerMembership;
import com.itextpdf.kernel.pdf.tagutils.TagReference;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.p026io.codec.TIFFConstants;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.font.otf.ActualTextIterator;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.font.otf.GlyphLine;
import com.itextpdf.p026io.image.ImageData;
import com.itextpdf.p026io.image.ImageType;
import com.itextpdf.p026io.source.ByteUtils;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PdfCanvas implements Serializable {

    /* renamed from: B */
    private static final byte[] f1429B = ByteUtils.getIsoBytes("B\n");
    private static final byte[] BDC = ByteUtils.getIsoBytes("BDC\n");

    /* renamed from: BI */
    private static final byte[] f1430BI = ByteUtils.getIsoBytes("BI\n");
    private static final byte[] BMC = ByteUtils.getIsoBytes("BMC\n");
    private static final byte[] BStar = ByteUtils.getIsoBytes("B*\n");

    /* renamed from: BT */
    private static final byte[] f1431BT = ByteUtils.getIsoBytes("BT\n");

    /* renamed from: CS */
    private static final byte[] f1432CS = ByteUtils.getIsoBytes("CS\n");

    /* renamed from: Do */
    private static final byte[] f1433Do = ByteUtils.getIsoBytes("Do\n");

    /* renamed from: EI */
    private static final byte[] f1434EI = ByteUtils.getIsoBytes("EI\n");
    private static final byte[] EMC = ByteUtils.getIsoBytes("EMC\n");

    /* renamed from: ET */
    private static final byte[] f1435ET = ByteUtils.getIsoBytes("ET\n");

    /* renamed from: G */
    private static final byte[] f1436G = ByteUtils.getIsoBytes("G\n");

    /* renamed from: ID */
    private static final byte[] f1437ID = ByteUtils.getIsoBytes("ID\n");
    private static final float IDENTITY_MATRIX_EPS = 1.0E-4f;

    /* renamed from: J */
    private static final byte[] f1438J = ByteUtils.getIsoBytes("J\n");

    /* renamed from: K */
    private static final byte[] f1439K = ByteUtils.getIsoBytes("K\n");

    /* renamed from: M */
    private static final byte[] f1440M = ByteUtils.getIsoBytes("M\n");

    /* renamed from: Q */
    private static final byte[] f1441Q = ByteUtils.getIsoBytes("Q\n");

    /* renamed from: RG */
    private static final byte[] f1442RG = ByteUtils.getIsoBytes("RG\n");

    /* renamed from: S */
    private static final byte[] f1443S = ByteUtils.getIsoBytes("S\n");
    private static final byte[] SCN = ByteUtils.getIsoBytes("SCN\n");

    /* renamed from: TD */
    private static final byte[] f1444TD = ByteUtils.getIsoBytes("TD\n");

    /* renamed from: TJ */
    private static final byte[] f1445TJ = ByteUtils.getIsoBytes("TJ\n");

    /* renamed from: TL */
    private static final byte[] f1446TL = ByteUtils.getIsoBytes("TL\n");
    private static final byte[] TStar = ByteUtils.getIsoBytes("T*\n");

    /* renamed from: Tc */
    private static final byte[] f1447Tc = ByteUtils.getIsoBytes("Tc\n");

    /* renamed from: Td */
    private static final byte[] f1448Td = ByteUtils.getIsoBytes("Td\n");

    /* renamed from: Tf */
    private static final byte[] f1449Tf = ByteUtils.getIsoBytes("Tf\n");

    /* renamed from: Tj */
    private static final byte[] f1450Tj = ByteUtils.getIsoBytes("Tj\n");

    /* renamed from: Tm */
    private static final byte[] f1451Tm = ByteUtils.getIsoBytes("Tm\n");

    /* renamed from: Tr */
    private static final byte[] f1452Tr = ByteUtils.getIsoBytes("Tr\n");

    /* renamed from: Ts */
    private static final byte[] f1453Ts = ByteUtils.getIsoBytes("Ts\n");

    /* renamed from: Tw */
    private static final byte[] f1454Tw = ByteUtils.getIsoBytes("Tw\n");

    /* renamed from: Tz */
    private static final byte[] f1455Tz = ByteUtils.getIsoBytes("Tz\n");

    /* renamed from: W */
    private static final byte[] f1456W = ByteUtils.getIsoBytes("W\n");
    private static final byte[] WStar = ByteUtils.getIsoBytes("W*\n");

    /* renamed from: b */
    private static final byte[] f1457b = ByteUtils.getIsoBytes("b\n");
    private static final byte[] bStar = ByteUtils.getIsoBytes("b*\n");

    /* renamed from: c */
    private static final byte[] f1458c = ByteUtils.getIsoBytes("c\n");

    /* renamed from: cm */
    private static final byte[] f1459cm = ByteUtils.getIsoBytes("cm\n");
    private static final PdfDeviceCs.Cmyk cmyk = new PdfDeviceCs.Cmyk();

    /* renamed from: cs */
    private static final byte[] f1460cs = ByteUtils.getIsoBytes("cs\n");

    /* renamed from: d */
    private static final byte[] f1461d = ByteUtils.getIsoBytes("d\n");

    /* renamed from: f */
    private static final byte[] f1462f = ByteUtils.getIsoBytes("f\n");
    private static final byte[] fStar = ByteUtils.getIsoBytes("f*\n");

    /* renamed from: g */
    private static final byte[] f1463g = ByteUtils.getIsoBytes("g\n");
    private static final PdfDeviceCs.Gray gray = new PdfDeviceCs.Gray();

    /* renamed from: gs */
    private static final byte[] f1464gs = ByteUtils.getIsoBytes("gs\n");

    /* renamed from: h */
    private static final byte[] f1465h = ByteUtils.getIsoBytes("h\n");

    /* renamed from: i */
    private static final byte[] f1466i = ByteUtils.getIsoBytes("i\n");

    /* renamed from: j */
    private static final byte[] f1467j = ByteUtils.getIsoBytes("j\n");

    /* renamed from: k */
    private static final byte[] f1468k = ByteUtils.getIsoBytes("k\n");

    /* renamed from: l */
    private static final byte[] f1469l = ByteUtils.getIsoBytes("l\n");

    /* renamed from: m */
    private static final byte[] f1470m = ByteUtils.getIsoBytes("m\n");

    /* renamed from: n */
    private static final byte[] f1471n = ByteUtils.getIsoBytes("n\n");
    private static final PdfSpecialCs.Pattern pattern = new PdfSpecialCs.Pattern();

    /* renamed from: q */
    private static final byte[] f1472q = ByteUtils.getIsoBytes("q\n");

    /* renamed from: re */
    private static final byte[] f1473re = ByteUtils.getIsoBytes("re\n");

    /* renamed from: rg */
    private static final byte[] f1474rg = ByteUtils.getIsoBytes("rg\n");
    private static final PdfDeviceCs.Rgb rgb = new PdfDeviceCs.Rgb();

    /* renamed from: ri */
    private static final byte[] f1475ri = ByteUtils.getIsoBytes("ri\n");

    /* renamed from: s */
    private static final byte[] f1476s = ByteUtils.getIsoBytes("s\n");
    private static final byte[] scn = ByteUtils.getIsoBytes("scn\n");
    private static final long serialVersionUID = -4706222391732334562L;

    /* renamed from: sh */
    private static final byte[] f1477sh = ByteUtils.getIsoBytes("sh\n");

    /* renamed from: v */
    private static final byte[] f1478v = ByteUtils.getIsoBytes("v\n");

    /* renamed from: w */
    private static final byte[] f1479w = ByteUtils.getIsoBytes("w\n");

    /* renamed from: y */
    private static final byte[] f1480y = ByteUtils.getIsoBytes("y\n");
    protected PdfStream contentStream;
    protected CanvasGraphicsState currentGs;
    protected PdfDocument document;
    protected Stack<CanvasGraphicsState> gsStack;
    protected List<Integer> layerDepth;
    protected int mcDepth;
    protected PdfResources resources;

    public PdfCanvas(PdfStream contentStream2, PdfResources resources2, PdfDocument document2) {
        this.gsStack = new Stack<>();
        this.currentGs = new CanvasGraphicsState();
        this.contentStream = ensureStreamDataIsReadyToBeProcessed(contentStream2);
        this.resources = resources2;
        this.document = document2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public PdfCanvas(PdfPage page) {
        this(page, (page.getDocument().getReader() != null && page.getDocument().getWriter() != null && page.getContentStreamCount() > 0 && page.getLastContentStream().getLength() > 0) || (page.getRotation() != 0 && page.isIgnorePageRotationForContent()));
    }

    public PdfCanvas(PdfPage page, boolean wrapOldContent) {
        this(getPageStream(page), page.getResources(), page.getDocument());
        if (wrapOldContent) {
            page.newContentStreamBefore().getOutputStream().writeBytes(ByteUtils.getIsoBytes("q\n"));
            this.contentStream.getOutputStream().writeBytes(ByteUtils.getIsoBytes("Q\n"));
        }
        if (page.getRotation() != 0 && page.isIgnorePageRotationForContent()) {
            if (wrapOldContent || !page.isPageRotationInverseMatrixWritten()) {
                applyRotation(page);
                page.setPageRotationInverseMatrixWritten();
            }
        }
    }

    public PdfCanvas(PdfFormXObject xObj, PdfDocument document2) {
        this((PdfStream) xObj.getPdfObject(), xObj.getResources(), document2);
    }

    public PdfCanvas(PdfDocument doc, int pageNum) {
        this(doc.getPage(pageNum));
    }

    public PdfResources getResources() {
        return this.resources;
    }

    public PdfDocument getDocument() {
        return this.document;
    }

    public void attachContentStream(PdfStream contentStream2) {
        this.contentStream = contentStream2;
    }

    public CanvasGraphicsState getGraphicsState() {
        return this.currentGs;
    }

    public void release() {
        this.gsStack = null;
        this.currentGs = null;
        this.contentStream = null;
        this.resources = null;
    }

    public PdfCanvas saveState() {
        this.document.checkIsoConformance('q', IsoKey.CANVAS_STACK);
        this.gsStack.push(this.currentGs);
        this.currentGs = new CanvasGraphicsState(this.currentGs);
        this.contentStream.getOutputStream().writeBytes(f1472q);
        return this;
    }

    public PdfCanvas restoreState() {
        this.document.checkIsoConformance('Q', IsoKey.CANVAS_STACK);
        if (!this.gsStack.isEmpty()) {
            this.currentGs = this.gsStack.pop();
            this.contentStream.getOutputStream().writeBytes(f1441Q);
            return this;
        }
        throw new PdfException(PdfException.UnbalancedSaveRestoreStateOperators);
    }

    public PdfCanvas concatMatrix(double a, double b, double c, double d, double e, double f) {
        double d2 = a;
        double d3 = c;
        double d4 = e;
        double d5 = f;
        this.currentGs.updateCtm((float) d2, (float) b, (float) d3, (float) d, (float) d4, (float) d5);
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeDouble(d2)).writeSpace()).writeDouble(b)).writeSpace()).writeDouble(d3)).writeSpace()).writeDouble(d)).writeSpace()).writeDouble(d4)).writeSpace()).writeDouble(d5)).writeSpace()).writeBytes(f1459cm);
        return this;
    }

    public PdfCanvas concatMatrix(PdfArray array) {
        if (array.size() != 6) {
            return this;
        }
        for (int i = 0; i < array.size(); i++) {
            if (!array.get(i).isNumber()) {
                return this;
            }
        }
        return concatMatrix(array.getAsNumber(0).doubleValue(), array.getAsNumber(1).doubleValue(), array.getAsNumber(2).doubleValue(), array.getAsNumber(3).doubleValue(), array.getAsNumber(4).doubleValue(), array.getAsNumber(5).doubleValue());
    }

    public PdfCanvas concatMatrix(AffineTransform transform) {
        float[] matrix = new float[6];
        transform.getMatrix(matrix);
        return concatMatrix((double) matrix[0], (double) matrix[1], (double) matrix[2], (double) matrix[3], (double) matrix[4], (double) matrix[5]);
    }

    public PdfCanvas beginText() {
        this.contentStream.getOutputStream().writeBytes(f1431BT);
        return this;
    }

    public PdfCanvas endText() {
        this.contentStream.getOutputStream().writeBytes(f1435ET);
        return this;
    }

    public PdfCanvas beginVariableText() {
        return beginMarkedContent(PdfName.f1402Tx);
    }

    public PdfCanvas endVariableText() {
        return endMarkedContent();
    }

    public PdfCanvas setFontAndSize(PdfFont font, float size) {
        this.currentGs.setFontSize(size);
        PdfName fontName = this.resources.addFont(this.document, font);
        this.currentGs.setFont(font);
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) fontName).writeSpace()).writeFloat(size)).writeSpace()).writeBytes(f1449Tf);
        return this;
    }

    public PdfCanvas moveText(double x, double y) {
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeDouble(x)).writeSpace()).writeDouble(y)).writeSpace()).writeBytes(f1448Td);
        return this;
    }

    public PdfCanvas setLeading(float leading) {
        this.currentGs.setLeading(leading);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(leading)).writeSpace()).writeBytes(f1446TL);
        return this;
    }

    public PdfCanvas moveTextWithLeading(float x, float y) {
        this.currentGs.setLeading(-y);
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(x)).writeSpace()).writeFloat(y)).writeSpace()).writeBytes(f1444TD);
        return this;
    }

    public PdfCanvas newlineText() {
        this.contentStream.getOutputStream().writeBytes(TStar);
        return this;
    }

    public PdfCanvas newlineShowText(String text) {
        showTextInt(text);
        ((PdfOutputStream) this.contentStream.getOutputStream().writeByte(39)).writeNewLine();
        return this;
    }

    public PdfCanvas newlineShowText(float wordSpacing, float charSpacing, String text) {
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(wordSpacing)).writeSpace()).writeFloat(charSpacing);
        showTextInt(text);
        ((PdfOutputStream) this.contentStream.getOutputStream().writeByte(34)).writeNewLine();
        this.currentGs.setCharSpacing(charSpacing);
        this.currentGs.setWordSpacing(wordSpacing);
        return this;
    }

    public PdfCanvas setTextRenderingMode(int textRenderingMode) {
        this.currentGs.setTextRenderingMode(textRenderingMode);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeInteger(textRenderingMode)).writeSpace()).writeBytes(f1452Tr);
        return this;
    }

    public PdfCanvas setTextRise(float textRise) {
        this.currentGs.setTextRise(textRise);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(textRise)).writeSpace()).writeBytes(f1453Ts);
        return this;
    }

    public PdfCanvas setWordSpacing(float wordSpacing) {
        this.currentGs.setWordSpacing(wordSpacing);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(wordSpacing)).writeSpace()).writeBytes(f1454Tw);
        return this;
    }

    public PdfCanvas setCharacterSpacing(float charSpacing) {
        this.currentGs.setCharSpacing(charSpacing);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(charSpacing)).writeSpace()).writeBytes(f1447Tc);
        return this;
    }

    public PdfCanvas setHorizontalScaling(float scale) {
        this.currentGs.setHorizontalScaling(scale);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(scale)).writeSpace()).writeBytes(f1455Tz);
        return this;
    }

    public PdfCanvas setTextMatrix(float a, float b, float c, float d, float x, float y) {
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(a)).writeSpace()).writeFloat(b)).writeSpace()).writeFloat(c)).writeSpace()).writeFloat(d)).writeSpace()).writeFloat(x)).writeSpace()).writeFloat(y)).writeSpace()).writeBytes(f1451Tm);
        return this;
    }

    public PdfCanvas setTextMatrix(AffineTransform transform) {
        float[] matrix = new float[6];
        transform.getMatrix(matrix);
        return setTextMatrix(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
    }

    public PdfCanvas setTextMatrix(float x, float y) {
        return setTextMatrix(1.0f, 0.0f, 0.0f, 1.0f, x, y);
    }

    public PdfCanvas showText(String text) {
        showTextInt(text);
        this.contentStream.getOutputStream().writeBytes(f1450Tj);
        return this;
    }

    public PdfCanvas showText(GlyphLine text) {
        return showText(text, new ActualTextIterator(text));
    }

    public PdfCanvas showText(GlyphLine text, Iterator<GlyphLine.GlyphLinePart> iterator) {
        GlyphLine glyphLine = text;
        this.document.checkIsoConformance(this.currentGs, IsoKey.FONT_GLYPHS, (PdfResources) null, this.contentStream);
        PdfFont font = this.currentGs.getFont();
        PdfFont font2 = font;
        if (font != null) {
            float fontSize = this.currentGs.getFontSize() / 1000.0f;
            float charSpacing = this.currentGs.getCharSpacing();
            float scaling = this.currentGs.getHorizontalScaling() / 100.0f;
            List<GlyphLine.GlyphLinePart> glyphLineParts = iteratorToList(iterator);
            for (int partIndex = 0; partIndex < glyphLineParts.size(); partIndex++) {
                GlyphLine.GlyphLinePart glyphLinePart = glyphLineParts.get(partIndex);
                boolean z = true;
                if (glyphLinePart.actualText != null) {
                    PdfDictionary properties = new PdfDictionary();
                    properties.put(PdfName.ActualText, new PdfString(glyphLinePart.actualText, PdfEncodings.UNICODE_BIG).setHexWriting(true));
                    beginMarkedContent(PdfName.Span, properties);
                } else if (glyphLinePart.reversed) {
                    beginMarkedContent(PdfName.ReversedChars);
                }
                int sub = glyphLinePart.start;
                int i = glyphLinePart.start;
                while (i < glyphLinePart.end) {
                    Glyph glyph = glyphLine.get(i);
                    if (glyph.hasOffsets()) {
                        if ((i - 1) - sub >= 0) {
                            font2.writeText(glyphLine, sub, i - 1, this.contentStream.getOutputStream());
                            this.contentStream.getOutputStream().writeBytes(f1450Tj);
                            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(getSubrangeWidth(glyphLine, sub, i - 1), z)).writeSpace()).writeFloat(0.0f)).writeSpace()).writeBytes(f1448Td);
                        }
                        float xPlacement = Float.NaN;
                        float yPlacement = Float.NaN;
                        if (glyph.hasPlacement()) {
                            float xPlacementAddition = 0.0f;
                            Glyph currentGlyph = glyphLine.get(i);
                            int currentGlyphIndex = i;
                            while (true) {
                                if (currentGlyph == null || currentGlyph.getAnchorDelta() == 0) {
                                } else {
                                    float xPlacement2 = xPlacement;
                                    xPlacementAddition += (float) currentGlyph.getXPlacement();
                                    if (currentGlyph.getAnchorDelta() == 0) {
                                        break;
                                    }
                                    currentGlyphIndex += currentGlyph.getAnchorDelta();
                                    currentGlyph = glyphLine.get(currentGlyphIndex);
                                    xPlacement = xPlacement2;
                                }
                            }
                            xPlacement = (-getSubrangeWidth(glyphLine, currentGlyphIndex, i)) + (xPlacementAddition * fontSize * scaling);
                            float yPlacementAddition = 0.0f;
                            int currentGlyphIndex2 = i;
                            Glyph currentGlyph2 = glyphLine.get(i);
                            while (true) {
                                if (currentGlyph2 == null || currentGlyph2.getYPlacement() == 0) {
                                } else {
                                    float yPlacement2 = yPlacement;
                                    yPlacementAddition += (float) currentGlyph2.getYPlacement();
                                    if (currentGlyph2.getAnchorDelta() == 0) {
                                        break;
                                    }
                                    currentGlyphIndex2 += currentGlyph2.getAnchorDelta();
                                    currentGlyph2 = glyphLine.get(currentGlyphIndex2);
                                    yPlacement = yPlacement2;
                                }
                            }
                            yPlacement = (-getSubrangeYDelta(glyphLine, currentGlyphIndex2, i)) + (yPlacementAddition * fontSize);
                            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(xPlacement, true)).writeSpace()).writeFloat(yPlacement, true)).writeSpace()).writeBytes(f1448Td);
                        }
                        font2.writeText(glyphLine, i, i, this.contentStream.getOutputStream());
                        this.contentStream.getOutputStream().writeBytes(f1450Tj);
                        if (!Float.isNaN(xPlacement)) {
                            float f = xPlacement;
                            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(-xPlacement, true)).writeSpace()).writeFloat(-yPlacement, true)).writeSpace()).writeBytes(f1448Td);
                        }
                        if (glyph.hasAdvance()) {
                            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(((((float) ((glyph.hasPlacement() ? 0 : glyph.getWidth()) + glyph.getXAdvance())) * fontSize) + charSpacing + getWordSpacingAddition(glyph)) * scaling, true)).writeSpace()).writeFloat(((float) glyph.getYAdvance()) * fontSize, true)).writeSpace()).writeBytes(f1448Td);
                        }
                        sub = i + 1;
                    }
                    i++;
                    z = true;
                }
                if (glyphLinePart.end - sub > 0) {
                    font2.writeText(glyphLine, sub, glyphLinePart.end - 1, this.contentStream.getOutputStream());
                    this.contentStream.getOutputStream().writeBytes(f1450Tj);
                }
                if (glyphLinePart.actualText != null) {
                    endMarkedContent();
                } else if (glyphLinePart.reversed) {
                    endMarkedContent();
                }
                if (glyphLinePart.end > sub && partIndex + 1 < glyphLineParts.size()) {
                    ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(getSubrangeWidth(glyphLine, sub, glyphLinePart.end - 1), true)).writeSpace()).writeFloat(0.0f)).writeSpace()).writeBytes(f1448Td);
                }
            }
            return this;
        }
        throw new PdfException(PdfException.FontAndSizeMustBeSetBeforeWritingAnyText, (Object) this.currentGs);
    }

    private float getSubrangeWidth(GlyphLine text, int from, int to) {
        float fontSize = this.currentGs.getFontSize() / 1000.0f;
        float charSpacing = this.currentGs.getCharSpacing();
        float scaling = this.currentGs.getHorizontalScaling() / 100.0f;
        float width = 0.0f;
        for (int iter = from; iter <= to; iter++) {
            Glyph glyph = text.get(iter);
            if (!glyph.hasPlacement()) {
                width += ((((float) glyph.getWidth()) * fontSize) + charSpacing + getWordSpacingAddition(glyph)) * scaling;
            }
            if (iter > from) {
                width += ((float) text.get(iter - 1).getXAdvance()) * fontSize * scaling;
            }
        }
        return width;
    }

    private float getSubrangeYDelta(GlyphLine text, int from, int to) {
        float fontSize = this.currentGs.getFontSize() / 1000.0f;
        float yDelta = 0.0f;
        for (int iter = from; iter < to; iter++) {
            yDelta += ((float) text.get(iter).getYAdvance()) * fontSize;
        }
        return yDelta;
    }

    private float getWordSpacingAddition(Glyph glyph) {
        if ((this.currentGs.getFont() instanceof PdfType0Font) || !glyph.hasValidUnicode() || glyph.getCode() != 32) {
            return 0.0f;
        }
        return this.currentGs.getWordSpacing();
    }

    public PdfCanvas showText(PdfArray textArray) {
        this.document.checkIsoConformance(this.currentGs, IsoKey.FONT_GLYPHS, (PdfResources) null, this.contentStream);
        if (this.currentGs.getFont() != null) {
            this.contentStream.getOutputStream().writeBytes(ByteUtils.getIsoBytes("["));
            Iterator<PdfObject> it = textArray.iterator();
            while (it.hasNext()) {
                PdfObject obj = it.next();
                if (obj.isString()) {
                    StreamUtil.writeEscapedString(this.contentStream.getOutputStream(), ((PdfString) obj).getValueBytes());
                } else if (obj.isNumber()) {
                    this.contentStream.getOutputStream().writeFloat(((PdfNumber) obj).floatValue());
                }
            }
            this.contentStream.getOutputStream().writeBytes(ByteUtils.getIsoBytes("]"));
            this.contentStream.getOutputStream().writeBytes(f1445TJ);
            return this;
        }
        throw new PdfException(PdfException.FontAndSizeMustBeSetBeforeWritingAnyText, (Object) this.currentGs);
    }

    public PdfCanvas moveTo(double x, double y) {
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeDouble(x)).writeSpace()).writeDouble(y)).writeSpace()).writeBytes(f1470m);
        return this;
    }

    public PdfCanvas lineTo(double x, double y) {
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeDouble(x)).writeSpace()).writeDouble(y)).writeSpace()).writeBytes(f1469l);
        return this;
    }

    public PdfCanvas curveTo(double x1, double y1, double x2, double y2, double x3, double y3) {
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeDouble(x1)).writeSpace()).writeDouble(y1)).writeSpace()).writeDouble(x2)).writeSpace()).writeDouble(y2)).writeSpace()).writeDouble(x3)).writeSpace()).writeDouble(y3)).writeSpace()).writeBytes(f1458c);
        return this;
    }

    public PdfCanvas curveTo(double x2, double y2, double x3, double y3) {
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeDouble(x2)).writeSpace()).writeDouble(y2)).writeSpace()).writeDouble(x3)).writeSpace()).writeDouble(y3)).writeSpace()).writeBytes(f1478v);
        return this;
    }

    public PdfCanvas curveFromTo(double x1, double y1, double x3, double y3) {
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeDouble(x1)).writeSpace()).writeDouble(y1)).writeSpace()).writeDouble(x3)).writeSpace()).writeDouble(y3)).writeSpace()).writeBytes(f1480y);
        return this;
    }

    public PdfCanvas arc(double x1, double y1, double x2, double y2, double startAng, double extent) {
        List<double[]> ar = bezierArc(x1, y1, x2, y2, startAng, extent);
        if (ar.isEmpty()) {
            return this;
        }
        double[] pt = ar.get(0);
        moveTo(pt[0], pt[1]);
        int i = 0;
        while (i < ar.size()) {
            double[] pt2 = ar.get(i);
            curveTo(pt2[2], pt2[3], pt2[4], pt2[5], pt2[6], pt2[7]);
            i++;
            double[] dArr = pt2;
        }
        return this;
    }

    public PdfCanvas ellipse(double x1, double y1, double x2, double y2) {
        return arc(x1, y1, x2, y2, 0.0d, 360.0d);
    }

    public static List<double[]> bezierArc(double x1, double y1, double x2, double y2, double startAng, double extent) {
        double x22;
        double x12;
        double y22;
        double y12;
        int Nfrag;
        double fragAngle;
        double fragAngle2;
        List<double[]> pointList;
        if (x1 > x2) {
            x12 = x2;
            x22 = x1;
        } else {
            x12 = x1;
            x22 = x2;
        }
        if (y2 > y1) {
            y12 = y2;
            y22 = y1;
        } else {
            y12 = y1;
            y22 = y2;
        }
        if (Math.abs(extent) <= 90.0d) {
            fragAngle = extent;
            Nfrag = 1;
        } else {
            Nfrag = (int) Math.ceil(Math.abs(extent) / 90.0d);
            double d = (double) Nfrag;
            Double.isNaN(d);
            fragAngle = extent / d;
        }
        double x_cen = (x12 + x22) / 2.0d;
        double y_cen = (y12 + y22) / 2.0d;
        double rx = (x22 - x12) / 2.0d;
        double ry = (y22 - y12) / 2.0d;
        double halfAng = (fragAngle * 3.141592653589793d) / 360.0d;
        double kappa = Math.abs(((1.0d - Math.cos(halfAng)) * 1.3333333333333333d) / Math.sin(halfAng));
        List<double[]> pointList2 = new ArrayList<>();
        int iter = 0;
        while (iter < Nfrag) {
            double x13 = x12;
            double x14 = (double) iter;
            Double.isNaN(x14);
            double theta0 = ((startAng + (x14 * fragAngle)) * 3.141592653589793d) / 180.0d;
            double x23 = x22;
            double x24 = (double) (iter + 1);
            Double.isNaN(x24);
            double theta1 = ((startAng + (x24 * fragAngle)) * 3.141592653589793d) / 180.0d;
            double cos0 = Math.cos(theta0);
            double cos1 = Math.cos(theta1);
            double sin0 = Math.sin(theta0);
            double sin1 = Math.sin(theta1);
            if (fragAngle > 0.0d) {
                fragAngle2 = fragAngle;
                pointList = pointList2;
                pointList.add(new double[]{x_cen + (rx * cos0), y_cen - (ry * sin0), x_cen + ((cos0 - (kappa * sin0)) * rx), y_cen - ((sin0 + (kappa * cos0)) * ry), x_cen + ((cos1 + (kappa * sin1)) * rx), y_cen - ((sin1 - (kappa * cos1)) * ry), x_cen + (rx * cos1), y_cen - (ry * sin1)});
            } else {
                fragAngle2 = fragAngle;
                pointList = pointList2;
                pointList.add(new double[]{x_cen + (rx * cos0), y_cen - (ry * sin0), x_cen + ((cos0 + (kappa * sin0)) * rx), y_cen - ((sin0 - (kappa * cos0)) * ry), x_cen + ((cos1 - (kappa * sin1)) * rx), y_cen - ((sin1 + (kappa * cos1)) * ry), x_cen + (rx * cos1), y_cen - (ry * sin1)});
            }
            iter++;
            x12 = x13;
            pointList2 = pointList;
            x22 = x23;
            fragAngle = fragAngle2;
        }
        return pointList2;
    }

    public PdfCanvas rectangle(double x, double y, double width, double height) {
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeDouble(x)).writeSpace()).writeDouble(y)).writeSpace()).writeDouble(width)).writeSpace()).writeDouble(height)).writeSpace()).writeBytes(f1473re);
        return this;
    }

    public PdfCanvas rectangle(Rectangle rectangle) {
        return rectangle((double) rectangle.getX(), (double) rectangle.getY(), (double) rectangle.getWidth(), (double) rectangle.getHeight());
    }

    public PdfCanvas roundRectangle(double x, double y, double width, double height, double radius) {
        double width2;
        double x2;
        double height2;
        double y2;
        double radius2;
        double d = width;
        double d2 = height;
        double d3 = radius;
        if (d < 0.0d) {
            width2 = -d;
            x2 = x + d;
        } else {
            x2 = x;
            width2 = d;
        }
        if (d2 < 0.0d) {
            y2 = y + d2;
            height2 = -d2;
        } else {
            y2 = y;
            height2 = d2;
        }
        if (d3 < 0.0d) {
            radius2 = -d3;
        } else {
            radius2 = d3;
        }
        moveTo(x2 + radius2, y2);
        lineTo((x2 + width2) - radius2, y2);
        double y3 = y2;
        curveTo((x2 + width2) - (radius2 * 0.44769999384880066d), y2, x2 + width2, y2 + (radius2 * 0.44769999384880066d), x2 + width2, y2 + radius2);
        lineTo(x2 + width2, (y3 + height2) - radius2);
        curveTo(x2 + width2, (y3 + height2) - (radius2 * 0.44769999384880066d), (x2 + width2) - (radius2 * 0.44769999384880066d), y3 + height2, (x2 + width2) - radius2, y3 + height2);
        lineTo(x2 + radius2, y3 + height2);
        curveTo(x2 + (radius2 * 0.44769999384880066d), y3 + height2, x2, (y3 + height2) - (radius2 * 0.44769999384880066d), x2, (y3 + height2) - radius2);
        lineTo(x2, y3 + radius2);
        curveTo(x2, y3 + (radius2 * 0.44769999384880066d), x2 + (0.44769999384880066d * radius2), y3, x2 + radius2, y3);
        return this;
    }

    public PdfCanvas circle(double x, double y, double r) {
        double d = y;
        moveTo(x + r, d);
        curveTo(x + r, (r * 0.552299976348877d) + d, x + (r * 0.552299976348877d), d + r, x, d + r);
        curveTo(x - (r * 0.552299976348877d), d + r, x - r, (r * 0.552299976348877d) + d, x - r, y);
        curveTo(x - r, d - (r * 0.552299976348877d), x - (r * 0.552299976348877d), d - r, x, d - r);
        curveTo(x + (r * 0.552299976348877d), d - r, x + r, d - (r * 0.552299976348877d), x + r, y);
        return this;
    }

    public PdfCanvas paintShading(PdfShading shading) {
        ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) this.resources.addShading(shading)).writeSpace()).writeBytes(f1477sh);
        return this;
    }

    public PdfCanvas closePath() {
        this.contentStream.getOutputStream().writeBytes(f1465h);
        return this;
    }

    public PdfCanvas closePathEoFillStroke() {
        this.contentStream.getOutputStream().writeBytes(bStar);
        return this;
    }

    public PdfCanvas closePathFillStroke() {
        this.contentStream.getOutputStream().writeBytes(f1457b);
        return this;
    }

    @Deprecated
    public PdfCanvas newPath() {
        return endPath();
    }

    public PdfCanvas endPath() {
        this.contentStream.getOutputStream().writeBytes(f1471n);
        return this;
    }

    public PdfCanvas stroke() {
        this.contentStream.getOutputStream().writeBytes(f1443S);
        return this;
    }

    public PdfCanvas clip() {
        this.contentStream.getOutputStream().writeBytes(f1456W);
        return this;
    }

    public PdfCanvas eoClip() {
        this.contentStream.getOutputStream().writeBytes(WStar);
        return this;
    }

    public PdfCanvas closePathStroke() {
        this.contentStream.getOutputStream().writeBytes(f1476s);
        return this;
    }

    public PdfCanvas fill() {
        this.contentStream.getOutputStream().writeBytes(f1462f);
        return this;
    }

    public PdfCanvas fillStroke() {
        this.contentStream.getOutputStream().writeBytes(f1429B);
        return this;
    }

    public PdfCanvas eoFill() {
        this.contentStream.getOutputStream().writeBytes(fStar);
        return this;
    }

    public PdfCanvas eoFillStroke() {
        this.contentStream.getOutputStream().writeBytes(BStar);
        return this;
    }

    public PdfCanvas setLineWidth(float lineWidth) {
        if (this.currentGs.getLineWidth() == lineWidth) {
            return this;
        }
        this.currentGs.setLineWidth(lineWidth);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(lineWidth)).writeSpace()).writeBytes(f1479w);
        return this;
    }

    public PdfCanvas setLineCapStyle(int lineCapStyle) {
        if (this.currentGs.getLineCapStyle() == lineCapStyle) {
            return this;
        }
        this.currentGs.setLineCapStyle(lineCapStyle);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeInteger(lineCapStyle)).writeSpace()).writeBytes(f1438J);
        return this;
    }

    public PdfCanvas setLineJoinStyle(int lineJoinStyle) {
        if (this.currentGs.getLineJoinStyle() == lineJoinStyle) {
            return this;
        }
        this.currentGs.setLineJoinStyle(lineJoinStyle);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeInteger(lineJoinStyle)).writeSpace()).writeBytes(f1467j);
        return this;
    }

    public PdfCanvas setMiterLimit(float miterLimit) {
        if (this.currentGs.getMiterLimit() == miterLimit) {
            return this;
        }
        this.currentGs.setMiterLimit(miterLimit);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(miterLimit)).writeSpace()).writeBytes(f1440M);
        return this;
    }

    public PdfCanvas setLineDash(float phase) {
        this.currentGs.setDashPattern(getDashPatternArray(phase));
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeByte(91)).writeByte(93)).writeSpace()).writeFloat(phase)).writeSpace()).writeBytes(f1461d);
        return this;
    }

    public PdfCanvas setLineDash(float unitsOn, float phase) {
        this.currentGs.setDashPattern(getDashPatternArray(new float[]{unitsOn}, phase));
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeByte(91)).writeFloat(unitsOn)).writeByte(93)).writeSpace()).writeFloat(phase)).writeSpace()).writeBytes(f1461d);
        return this;
    }

    public PdfCanvas setLineDash(float unitsOn, float unitsOff, float phase) {
        this.currentGs.setDashPattern(getDashPatternArray(new float[]{unitsOn, unitsOff}, phase));
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeByte(91)).writeFloat(unitsOn)).writeSpace()).writeFloat(unitsOff)).writeByte(93)).writeSpace()).writeFloat(phase)).writeSpace()).writeBytes(f1461d);
        return this;
    }

    public PdfCanvas setLineDash(float[] array, float phase) {
        this.currentGs.setDashPattern(getDashPatternArray(array, phase));
        PdfOutputStream out = this.contentStream.getOutputStream();
        out.writeByte(91);
        for (int iter = 0; iter < array.length; iter++) {
            out.writeFloat(array[iter]);
            if (iter < array.length - 1) {
                out.writeSpace();
            }
        }
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) out.writeByte(93)).writeSpace()).writeFloat(phase)).writeSpace()).writeBytes(f1461d);
        return this;
    }

    public PdfCanvas setRenderingIntent(PdfName renderingIntent) {
        this.document.checkIsoConformance(renderingIntent, IsoKey.RENDERING_INTENT);
        if (renderingIntent.equals(this.currentGs.getRenderingIntent())) {
            return this;
        }
        this.currentGs.setRenderingIntent(renderingIntent);
        ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) renderingIntent).writeSpace()).writeBytes(f1475ri);
        return this;
    }

    public PdfCanvas setFlatnessTolerance(float flatnessTolerance) {
        if (this.currentGs.getFlatnessTolerance() == flatnessTolerance) {
            return this;
        }
        this.currentGs.setFlatnessTolerance(flatnessTolerance);
        ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloat(flatnessTolerance)).writeSpace()).writeBytes(f1466i);
        return this;
    }

    public PdfCanvas setFillColor(Color color) {
        return setColor(color, true);
    }

    public PdfCanvas setStrokeColor(Color color) {
        return setColor(color, false);
    }

    public PdfCanvas setColor(Color color, boolean fill) {
        if (color instanceof PatternColor) {
            return setColor(color.getColorSpace(), color.getColorValue(), ((PatternColor) color).getPattern(), fill);
        }
        return setColor(color.getColorSpace(), color.getColorValue(), fill);
    }

    public PdfCanvas setColor(PdfColorSpace colorSpace, float[] colorValue, boolean fill) {
        return setColor(colorSpace, colorValue, (PdfPattern) null, fill);
    }

    public PdfCanvas setColor(PdfColorSpace colorSpace, float[] colorValue, PdfPattern pattern2, boolean fill) {
        boolean setColorValueOnly = false;
        CanvasGraphicsState canvasGraphicsState = this.currentGs;
        Color oldColor = fill ? canvasGraphicsState.getFillColor() : canvasGraphicsState.getStrokeColor();
        Color newColor = createColor(colorSpace, colorValue, pattern2);
        if (oldColor.equals(newColor)) {
            return this;
        }
        if (fill) {
            this.currentGs.setFillColor(newColor);
        } else {
            this.currentGs.setStrokeColor(newColor);
        }
        if (oldColor.getColorSpace().getPdfObject().equals(colorSpace.getPdfObject())) {
            setColorValueOnly = true;
        }
        if (colorSpace instanceof PdfDeviceCs.Gray) {
            ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloats(colorValue)).writeSpace()).writeBytes(fill ? f1463g : f1436G);
        } else if (colorSpace instanceof PdfDeviceCs.Rgb) {
            ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloats(colorValue)).writeSpace()).writeBytes(fill ? f1474rg : f1442RG);
        } else if (colorSpace instanceof PdfDeviceCs.Cmyk) {
            ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloats(colorValue)).writeSpace()).writeBytes(fill ? f1468k : f1439K);
        } else if (colorSpace instanceof PdfSpecialCs.UncoloredTilingPattern) {
            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) this.resources.addColorSpace(colorSpace)).writeSpace()).writeBytes(fill ? f1460cs : f1432CS)).writeNewLine()).writeFloats(colorValue)).writeSpace()).write((PdfObject) this.resources.addPattern(pattern2)).writeSpace()).writeBytes(fill ? scn : SCN);
        } else if (colorSpace instanceof PdfSpecialCs.Pattern) {
            ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) PdfName.Pattern).writeSpace()).writeBytes(fill ? f1460cs : f1432CS)).writeNewLine()).write((PdfObject) this.resources.addPattern(pattern2)).writeSpace()).writeBytes(fill ? scn : SCN);
        } else if (colorSpace.getPdfObject().isIndirect()) {
            if (!setColorValueOnly) {
                ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) this.resources.addColorSpace(colorSpace)).writeSpace()).writeBytes(fill ? f1460cs : f1432CS);
            }
            ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().writeFloats(colorValue)).writeSpace()).writeBytes(fill ? scn : SCN);
        }
        this.document.checkIsoConformance(this.currentGs, fill ? IsoKey.FILL_COLOR : IsoKey.STROKE_COLOR, this.resources, this.contentStream);
        return this;
    }

    public PdfCanvas setFillColorGray(float g) {
        return setColor(gray, new float[]{g}, true);
    }

    public PdfCanvas setStrokeColorGray(float g) {
        return setColor(gray, new float[]{g}, false);
    }

    public PdfCanvas resetFillColorGray() {
        return setFillColorGray(0.0f);
    }

    public PdfCanvas resetStrokeColorGray() {
        return setStrokeColorGray(0.0f);
    }

    public PdfCanvas setFillColorRgb(float r, float g, float b) {
        return setColor(rgb, new float[]{r, g, b}, true);
    }

    public PdfCanvas setStrokeColorRgb(float r, float g, float b) {
        return setColor(rgb, new float[]{r, g, b}, false);
    }

    public PdfCanvas setFillColorShading(PdfPattern.Shading shading) {
        return setColor(pattern, (float[]) null, shading, true);
    }

    public PdfCanvas setStrokeColorShading(PdfPattern.Shading shading) {
        return setColor(pattern, (float[]) null, shading, false);
    }

    public PdfCanvas resetFillColorRgb() {
        return resetFillColorGray();
    }

    public PdfCanvas resetStrokeColorRgb() {
        return resetStrokeColorGray();
    }

    public PdfCanvas setFillColorCmyk(float c, float m, float y, float k) {
        return setColor(cmyk, new float[]{c, m, y, k}, true);
    }

    public PdfCanvas setStrokeColorCmyk(float c, float m, float y, float k) {
        return setColor(cmyk, new float[]{c, m, y, k}, false);
    }

    public PdfCanvas resetFillColorCmyk() {
        return setFillColorCmyk(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public PdfCanvas resetStrokeColorCmyk() {
        return setStrokeColorCmyk(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public PdfCanvas beginLayer(IPdfOCG layer) {
        if (!(layer instanceof PdfLayer) || ((PdfLayer) layer).getTitle() == null) {
            if (this.layerDepth == null) {
                this.layerDepth = new ArrayList();
            }
            if (layer instanceof PdfLayerMembership) {
                this.layerDepth.add(1);
                addToPropertiesAndBeginLayer(layer);
            } else if (layer instanceof PdfLayer) {
                int num = 0;
                for (PdfLayer la = (PdfLayer) layer; la != null; la = la.getParent()) {
                    if (la.getTitle() == null) {
                        addToPropertiesAndBeginLayer(la);
                        num++;
                    }
                }
                this.layerDepth.add(Integer.valueOf(num));
            } else {
                throw new UnsupportedOperationException("Unsupported type for operand: layer");
            }
            return this;
        }
        throw new IllegalArgumentException("Illegal layer argument.");
    }

    public PdfCanvas endLayer() {
        List<Integer> list = this.layerDepth;
        if (list == null || list.isEmpty()) {
            throw new PdfException(PdfException.UnbalancedLayerOperators);
        }
        List<Integer> list2 = this.layerDepth;
        int num = list2.get(list2.size() - 1).intValue();
        List<Integer> list3 = this.layerDepth;
        list3.remove(list3.size() - 1);
        while (true) {
            int num2 = num - 1;
            if (num <= 0) {
                return this;
            }
            ((PdfOutputStream) this.contentStream.getOutputStream().writeBytes(EMC)).writeNewLine();
            num = num2;
        }
    }

    @Deprecated
    public PdfXObject addImage(ImageData image, float a, float b, float c, float d, float e, float f) {
        return addImageWithTransformationMatrix(image, a, b, c, d, e, f);
    }

    public PdfXObject addImageWithTransformationMatrix(ImageData image, float a, float b, float c, float d, float e, float f) {
        return addImageWithTransformationMatrix(image, a, b, c, d, e, f, false);
    }

    @Deprecated
    public PdfXObject addImage(ImageData image, float a, float b, float c, float d, float e, float f, boolean asInline) {
        return addImageWithTransformationMatrix(image, a, b, c, d, e, f, asInline);
    }

    public PdfXObject addImageWithTransformationMatrix(ImageData image, float a, float b, float c, float d, float e, float f, boolean asInline) {
        ImageData imageData = image;
        if (image.getOriginalType() == ImageType.WMF) {
            PdfXObject xObject = new WmfImageHelper(image).createFormXObject(this.document);
            addXObject(xObject, a, b, c, d, e, f);
            return xObject;
        }
        PdfImageXObject imageXObject = new PdfImageXObject(image);
        if (!asInline || !image.canImageBeInline()) {
            addImageWithTransformationMatrix((PdfXObject) imageXObject, a, b, c, d, e, f);
            return imageXObject;
        }
        addInlineImage(imageXObject, a, b, c, d, e, f);
        return null;
    }

    @Deprecated
    public PdfXObject addImage(ImageData image, Rectangle rect, boolean asInline) {
        return addImageFittedIntoRectangle(image, rect, asInline);
    }

    public PdfXObject addImageFittedIntoRectangle(ImageData image, Rectangle rect, boolean asInline) {
        return addImageWithTransformationMatrix(image, rect.getWidth(), 0.0f, 0.0f, rect.getHeight(), rect.getX(), rect.getY(), asInline);
    }

    @Deprecated
    public PdfXObject addImage(ImageData image, float x, float y, boolean asInline) {
        return addImageAt(image, x, y, asInline);
    }

    public PdfXObject addImageAt(ImageData image, float x, float y, boolean asInline) {
        if (image.getOriginalType() == ImageType.WMF) {
            PdfXObject xObject = new WmfImageHelper(image).createFormXObject(this.document);
            addXObject(xObject, image.getWidth(), 0.0f, 0.0f, image.getHeight(), x, y);
            return xObject;
        }
        PdfImageXObject imageXObject = new PdfImageXObject(image);
        if (!asInline || !image.canImageBeInline()) {
            addImageWithTransformationMatrix((PdfXObject) imageXObject, image.getWidth(), 0.0f, 0.0f, image.getHeight(), x, y);
            return imageXObject;
        }
        addInlineImage(imageXObject, image.getWidth(), 0.0f, 0.0f, image.getHeight(), x, y);
        return null;
    }

    @Deprecated
    public PdfXObject addImage(ImageData image, float x, float y, float width, boolean asInline) {
        if (image.getOriginalType() == ImageType.WMF) {
            PdfXObject xObject = new WmfImageHelper(image).createFormXObject(this.document);
            addImageWithTransformationMatrix(xObject, width, 0.0f, 0.0f, width, x, y);
            return xObject;
        }
        PdfImageXObject imageXObject = new PdfImageXObject(image);
        if (!asInline || !image.canImageBeInline()) {
            addImageWithTransformationMatrix((PdfXObject) imageXObject, width, 0.0f, 0.0f, (width / image.getWidth()) * image.getHeight(), x, y);
            return imageXObject;
        }
        addInlineImage(imageXObject, width, 0.0f, 0.0f, (width / image.getWidth()) * image.getHeight(), x, y);
        return null;
    }

    @Deprecated
    public PdfXObject addImage(ImageData image, float x, float y, float height, boolean asInline, boolean dummy) {
        return addImageWithTransformationMatrix(image, (height / image.getHeight()) * image.getWidth(), 0.0f, 0.0f, height, x, y, asInline);
    }

    public PdfCanvas addXObjectWithTransformationMatrix(PdfXObject xObject, float a, float b, float c, float d, float e, float f) {
        PdfXObject pdfXObject = xObject;
        if (pdfXObject instanceof PdfFormXObject) {
            return addFormWithTransformationMatrix((PdfFormXObject) pdfXObject, a, b, c, d, e, f, true);
        } else if (pdfXObject instanceof PdfImageXObject) {
            return addImageWithTransformationMatrix(xObject, a, b, c, d, e, f);
        } else {
            throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
        }
    }

    @Deprecated
    public PdfCanvas addXObject(PdfXObject xObject, float a, float b, float c, float d, float e, float f) {
        return addXObjectWithTransformationMatrix(xObject, a, b, c, d, e, f);
    }

    public PdfCanvas addXObjectAt(PdfXObject xObject, float x, float y) {
        if (xObject instanceof PdfFormXObject) {
            return addFormAt((PdfFormXObject) xObject, x, y);
        }
        if (xObject instanceof PdfImageXObject) {
            return addImageAt((PdfImageXObject) xObject, x, y);
        }
        throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
    }

    @Deprecated
    public PdfCanvas addXObject(PdfXObject xObject, float x, float y) {
        if (xObject instanceof PdfFormXObject) {
            return addForm((PdfFormXObject) xObject, x, y);
        }
        if (xObject instanceof PdfImageXObject) {
            return addImageAt((PdfImageXObject) xObject, x, y);
        }
        throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
    }

    public PdfCanvas addXObjectFittedIntoRectangle(PdfXObject xObject, Rectangle rect) {
        if (xObject instanceof PdfFormXObject) {
            return addFormFittedIntoRectangle((PdfFormXObject) xObject, rect);
        }
        if (xObject instanceof PdfImageXObject) {
            return addImageFittedIntoRectangle((PdfImageXObject) xObject, rect);
        }
        throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
    }

    @Deprecated
    public PdfCanvas addXObject(PdfXObject xObject, Rectangle rect) {
        if (xObject instanceof PdfFormXObject) {
            return addForm((PdfFormXObject) xObject, rect);
        }
        if (xObject instanceof PdfImageXObject) {
            return addImageFittedIntoRectangle((PdfImageXObject) xObject, rect);
        }
        throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
    }

    @Deprecated
    public PdfCanvas addXObject(PdfXObject xObject, float x, float y, float width) {
        if (xObject instanceof PdfFormXObject) {
            return addForm((PdfFormXObject) xObject, x, y, width);
        }
        if (xObject instanceof PdfImageXObject) {
            return addXObject(xObject, PdfXObject.calculateProportionallyFitRectangleWithWidth(xObject, x, y, width));
        }
        throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
    }

    @Deprecated
    public PdfCanvas addXObject(PdfXObject xObject, float x, float y, float height, boolean dummy) {
        if (xObject instanceof PdfFormXObject) {
            return addForm((PdfFormXObject) xObject, x, y, height, dummy);
        } else if (xObject instanceof PdfImageXObject) {
            return addXObject(xObject, PdfXObject.calculateProportionallyFitRectangleWithHeight(xObject, x, y, height));
        } else {
            throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
        }
    }

    public PdfCanvas addXObject(PdfXObject xObject) {
        if (xObject instanceof PdfFormXObject) {
            return addFormWithTransformationMatrix((PdfFormXObject) xObject, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, false);
        } else if (xObject instanceof PdfImageXObject) {
            return addImageAt((PdfImageXObject) xObject, 0.0f, 0.0f);
        } else {
            throw new IllegalArgumentException("PdfFormXObject or PdfImageXObject expected.");
        }
    }

    public PdfCanvas setExtGState(PdfExtGState extGState) {
        if (!extGState.isFlushed()) {
            this.currentGs.updateFromExtGState(extGState, this.document);
        }
        ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) this.resources.addExtGState(extGState)).writeSpace()).writeBytes(f1464gs);
        this.document.checkIsoConformance(this.currentGs, IsoKey.EXTENDED_GRAPHICS_STATE, (PdfResources) null, this.contentStream);
        return this;
    }

    public PdfExtGState setExtGState(PdfDictionary extGState) {
        PdfExtGState egs = new PdfExtGState(extGState);
        setExtGState(egs);
        return egs;
    }

    public PdfCanvas beginMarkedContent(PdfName tag) {
        return beginMarkedContent(tag, (PdfDictionary) null);
    }

    public PdfCanvas beginMarkedContent(PdfName tag, PdfDictionary properties) {
        this.mcDepth++;
        PdfOutputStream out = (PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) tag).writeSpace();
        if (properties == null) {
            out.writeBytes(BMC);
        } else if (properties.getIndirectReference() == null) {
            ((PdfOutputStream) out.write((PdfObject) properties).writeSpace()).writeBytes(BDC);
        } else {
            ((PdfOutputStream) out.write((PdfObject) this.resources.addProperties(properties)).writeSpace()).writeBytes(BDC);
        }
        return this;
    }

    public PdfCanvas endMarkedContent() {
        int i = this.mcDepth - 1;
        this.mcDepth = i;
        if (i >= 0) {
            this.contentStream.getOutputStream().writeBytes(EMC);
            return this;
        }
        throw new PdfException(PdfException.UnbalancedBeginEndMarkedContentOperators);
    }

    public PdfCanvas openTag(CanvasTag tag) {
        if (tag.getRole() == null) {
            return this;
        }
        return beginMarkedContent(tag.getRole(), tag.getProperties());
    }

    public PdfCanvas openTag(TagReference tagReference) {
        if (tagReference.getRole() == null) {
            return this;
        }
        CanvasTag tag = new CanvasTag(tagReference.getRole());
        tag.setProperties(tagReference.getProperties()).addProperty(PdfName.MCID, new PdfNumber(tagReference.createNextMcid()));
        return openTag(tag);
    }

    public PdfCanvas closeTag() {
        return endMarkedContent();
    }

    public PdfCanvas writeLiteral(String s) {
        this.contentStream.getOutputStream().writeString(s);
        return this;
    }

    public PdfCanvas writeLiteral(char c) {
        this.contentStream.getOutputStream().writeInteger(c);
        return this;
    }

    public PdfCanvas writeLiteral(float n) {
        this.contentStream.getOutputStream().writeFloat(n);
        return this;
    }

    public PdfStream getContentStream() {
        return this.contentStream;
    }

    /* access modifiers changed from: protected */
    public void addInlineImage(PdfImageXObject imageXObject, float a, float b, float c, float d, float e, float f) {
        this.document.checkIsoConformance(imageXObject.getPdfObject(), IsoKey.INLINE_IMAGE, this.resources, this.contentStream);
        saveState();
        concatMatrix((double) a, (double) b, (double) c, (double) d, (double) e, (double) f);
        PdfOutputStream os = this.contentStream.getOutputStream();
        os.writeBytes(f1430BI);
        byte[] imageBytes = ((PdfStream) imageXObject.getPdfObject()).getBytes(false);
        for (Map.Entry<PdfName, PdfObject> entry : ((PdfStream) imageXObject.getPdfObject()).entrySet()) {
            PdfName key = entry.getKey();
            if (!PdfName.Type.equals(key) && !PdfName.Subtype.equals(key) && !PdfName.Length.equals(key)) {
                os.write((PdfObject) entry.getKey()).writeSpace();
                os.write(entry.getValue()).writeNewLine();
            }
        }
        if (this.document.getPdfVersion().compareTo(PdfVersion.PDF_2_0) >= 0) {
            os.write((PdfObject) PdfName.Length).writeSpace();
            os.write((PdfObject) new PdfNumber(imageBytes.length)).writeNewLine();
        }
        os.writeBytes(f1437ID);
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) os.writeBytes(imageBytes)).writeNewLine()).writeBytes(f1434EI)).writeNewLine();
        restoreState();
    }

    private PdfCanvas addFormWithTransformationMatrix(PdfFormXObject form, float a, float b, float c, float d, float e, float f, boolean writeIdentityMatrix) {
        saveState();
        if (writeIdentityMatrix || !isIdentityMatrix(a, b, c, d, e, f)) {
            concatMatrix((double) a, (double) b, (double) c, (double) d, (double) e, (double) f);
        }
        ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) this.resources.addForm(form)).writeSpace()).writeBytes(f1433Do);
        restoreState();
        return this;
    }

    @Deprecated
    private PdfCanvas addForm(PdfFormXObject form, float a, float b, float c, float d, float e, float f) {
        saveState();
        concatMatrix((double) a, (double) b, (double) c, (double) d, (double) e, (double) f);
        ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) this.resources.addForm(form)).writeSpace()).writeBytes(f1433Do);
        restoreState();
        return this;
    }

    private PdfCanvas addFormAt(PdfFormXObject form, float x, float y) {
        float f = x;
        float f2 = y;
        Rectangle bBox = PdfFormXObject.calculateBBoxMultipliedByMatrix(form);
        Vector bBoxMin = new Vector(bBox.getLeft(), bBox.getBottom(), 1.0f);
        Vector bBoxMax = new Vector(bBox.getRight(), bBox.getTop(), 1.0f);
        float[] result = calculateTransformationMatrix(new Vector(f, f2, 1.0f), new Vector((bBoxMax.get(0) + f) - bBoxMin.get(0), (bBoxMax.get(1) + f2) - bBoxMin.get(1), 1.0f), bBoxMin, bBoxMax);
        return addFormWithTransformationMatrix(form, result[0], result[1], result[2], result[3], result[4], result[5], false);
    }

    @Deprecated
    private PdfCanvas addForm(PdfFormXObject form, float x, float y) {
        return addForm(form, 1.0f, 0.0f, 0.0f, 1.0f, x, y);
    }

    @Deprecated
    private PdfCanvas addForm(PdfFormXObject form, float x, float y, float width) {
        PdfArray bbox = ((PdfStream) form.getPdfObject()).getAsArray(PdfName.BBox);
        if (bbox != null) {
            return addForm(form, width, 0.0f, 0.0f, (width / Math.abs(bbox.getAsNumber(2).floatValue() - bbox.getAsNumber(0).floatValue())) * Math.abs(bbox.getAsNumber(3).floatValue() - bbox.getAsNumber(1).floatValue()), x, y);
        }
        throw new PdfException(PdfException.PdfFormXobjectHasInvalidBbox);
    }

    @Deprecated
    private PdfCanvas addForm(PdfFormXObject form, float x, float y, float height, boolean dummy) {
        PdfArray bbox = ((PdfStream) form.getPdfObject()).getAsArray(PdfName.BBox);
        if (bbox != null) {
            return addForm(form, (height / Math.abs(bbox.getAsNumber(3).floatValue() - bbox.getAsNumber(1).floatValue())) * Math.abs(bbox.getAsNumber(2).floatValue() - bbox.getAsNumber(0).floatValue()), 0.0f, 0.0f, height, x, y);
        }
        throw new PdfException(PdfException.PdfFormXobjectHasInvalidBbox);
    }

    private PdfCanvas addFormFittedIntoRectangle(PdfFormXObject form, Rectangle rect) {
        Rectangle bBox = PdfFormXObject.calculateBBoxMultipliedByMatrix(form);
        float[] result = calculateTransformationMatrix(new Vector(rect.getLeft(), rect.getBottom(), 1.0f), new Vector(rect.getRight(), rect.getTop(), 1.0f), new Vector(bBox.getLeft(), bBox.getBottom(), 1.0f), new Vector(bBox.getRight(), bBox.getTop(), 1.0f));
        return addFormWithTransformationMatrix(form, result[0], result[1], result[2], result[3], result[4], result[5], false);
    }

    @Deprecated
    private PdfCanvas addForm(PdfFormXObject form, Rectangle rect) {
        return addForm(form, rect.getWidth(), 0.0f, 0.0f, rect.getHeight(), rect.getX(), rect.getY());
    }

    private PdfCanvas addImageWithTransformationMatrix(PdfXObject xObject, float a, float b, float c, float d, float e, float f) {
        PdfName name;
        PdfCanvas pdfCanvas;
        PdfXObject pdfXObject = xObject;
        saveState();
        concatMatrix((double) a, (double) b, (double) c, (double) d, (double) e, (double) f);
        PdfXObject pdfXObject2 = xObject;
        if (pdfXObject2 instanceof PdfImageXObject) {
            pdfCanvas = this;
            name = pdfCanvas.resources.addImage((PdfImageXObject) pdfXObject2);
        } else {
            pdfCanvas = this;
            name = pdfCanvas.resources.addImage((PdfStream) xObject.getPdfObject());
        }
        ((PdfOutputStream) pdfCanvas.contentStream.getOutputStream().write((PdfObject) name).writeSpace()).writeBytes(f1433Do);
        restoreState();
        return pdfCanvas;
    }

    private PdfCanvas addImageAt(PdfImageXObject image, float x, float y) {
        return addImageWithTransformationMatrix((PdfXObject) image, image.getWidth(), 0.0f, 0.0f, image.getHeight(), x, y);
    }

    private PdfCanvas addImageFittedIntoRectangle(PdfImageXObject image, Rectangle rect) {
        return addImageWithTransformationMatrix((PdfXObject) image, rect.getWidth(), 0.0f, 0.0f, rect.getHeight(), rect.getX(), rect.getY());
    }

    private PdfStream ensureStreamDataIsReadyToBeProcessed(PdfStream stream) {
        if (!stream.isFlushed() && (stream.getOutputStream() == null || stream.containsKey(PdfName.Filter))) {
            try {
                stream.setData(stream.getBytes());
            } catch (Exception e) {
            }
        }
        return stream;
    }

    private void showTextInt(String text) {
        this.document.checkIsoConformance(this.currentGs, IsoKey.FONT_GLYPHS, (PdfResources) null, this.contentStream);
        if (this.currentGs.getFont() != null) {
            this.currentGs.getFont().writeText(text, this.contentStream.getOutputStream());
            return;
        }
        throw new PdfException(PdfException.FontAndSizeMustBeSetBeforeWritingAnyText, (Object) this.currentGs);
    }

    private void addToPropertiesAndBeginLayer(IPdfOCG layer) {
        ((PdfOutputStream) ((PdfOutputStream) ((PdfOutputStream) this.contentStream.getOutputStream().write((PdfObject) PdfName.f1362OC).writeSpace()).write((PdfObject) this.resources.addProperties(layer.getPdfObject())).writeSpace()).writeBytes(BDC)).writeNewLine();
    }

    private Color createColor(PdfColorSpace colorSpace, float[] colorValue, PdfPattern pattern2) {
        if (colorSpace instanceof PdfSpecialCs.UncoloredTilingPattern) {
            return new PatternColor((PdfPattern.Tiling) pattern2, ((PdfSpecialCs.UncoloredTilingPattern) colorSpace).getUnderlyingColorSpace(), colorValue);
        }
        if (colorSpace instanceof PdfSpecialCs.Pattern) {
            return new PatternColor(pattern2);
        }
        return Color.makeColor(colorSpace, colorValue);
    }

    private PdfArray getDashPatternArray(float phase) {
        return getDashPatternArray((float[]) null, phase);
    }

    private PdfArray getDashPatternArray(float[] dashArray, float phase) {
        PdfArray dashPatternArray = new PdfArray();
        PdfArray dArray = new PdfArray();
        if (dashArray != null) {
            for (float fl : dashArray) {
                dArray.add(new PdfNumber((double) fl));
            }
        }
        dashPatternArray.add(dArray);
        dashPatternArray.add(new PdfNumber((double) phase));
        return dashPatternArray;
    }

    private void applyRotation(PdfPage page) {
        Rectangle rectangle = page.getPageSizeWithRotation();
        switch (page.getRotation()) {
            case 90:
                concatMatrix(0.0d, 1.0d, -1.0d, 0.0d, (double) rectangle.getTop(), 0.0d);
                return;
            case 180:
                concatMatrix(-1.0d, 0.0d, 0.0d, -1.0d, (double) rectangle.getRight(), (double) rectangle.getTop());
                return;
            case TIFFConstants.TIFFTAG_IMAGEDESCRIPTION:
                concatMatrix(0.0d, -1.0d, 1.0d, 0.0d, 0.0d, (double) rectangle.getRight());
                return;
            default:
                return;
        }
    }

    private static PdfStream getPageStream(PdfPage page) {
        PdfStream stream = page.getLastContentStream();
        return (stream == null || stream.getOutputStream() == null || stream.containsKey(PdfName.Filter)) ? page.newContentStreamAfter() : stream;
    }

    private static <T> List<T> iteratorToList(Iterator<T> iterator) {
        List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    private static float[] calculateTransformationMatrix(Vector expectedMin, Vector expectedMax, Vector actualMin, Vector actualMax) {
        float[] result = new float[6];
        result[0] = (expectedMin.get(0) - expectedMax.get(0)) / (actualMin.get(0) - actualMax.get(0));
        result[1] = 0.0f;
        result[2] = 0.0f;
        result[3] = (expectedMin.get(1) - expectedMax.get(1)) / (actualMin.get(1) - actualMax.get(1));
        result[4] = expectedMin.get(0) - (actualMin.get(0) * result[0]);
        result[5] = expectedMin.get(1) - (actualMin.get(1) * result[3]);
        return result;
    }

    private static boolean isIdentityMatrix(float a, float b, float c, float d, float e, float f) {
        return Math.abs(1.0f - a) < 1.0E-4f && Math.abs(b) < 1.0E-4f && Math.abs(c) < 1.0E-4f && Math.abs(1.0f - d) < 1.0E-4f && Math.abs(e) < 1.0E-4f && Math.abs(f) < 1.0E-4f;
    }
}
