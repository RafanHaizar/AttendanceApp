package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfType0Font;
import com.itextpdf.kernel.font.PdfType1Font;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.font.FontCharacteristics;
import com.itextpdf.layout.font.FontFamilySplitter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSelectorStrategy;
import com.itextpdf.layout.font.FontSet;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.TextLayoutResult;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.FontKerning;
import com.itextpdf.layout.property.RenderingMode;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.Underline;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.font.FontMetrics;
import com.itextpdf.p026io.font.TrueTypeFont;
import com.itextpdf.p026io.font.otf.Glyph;
import com.itextpdf.p026io.font.otf.GlyphLine;
import com.itextpdf.p026io.util.EnumUtil;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.TextUtil;
import java.lang.Character;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.slf4j.LoggerFactory;

public class TextRenderer extends AbstractRenderer implements ILeafElementRenderer {
    private static final float BOLD_SIMULATION_STROKE_COEFF = 0.033333335f;
    private static final float ITALIC_ANGLE = 0.21256f;
    protected static final float TEXT_SPACE_COEFF = 1000.0f;
    static final float TYPO_ASCENDER_SCALE_COEFF = 1.2f;
    private PdfFont font;
    protected GlyphLine line;
    protected boolean otfFeaturesApplied;
    protected List<int[]> reversedRanges;
    protected GlyphLine savedWordBreakAtLineEnding;
    private int specialScriptFirstNotFittingIndex;
    private List<Integer> specialScriptsWordBreakPoints;
    protected String strToBeConverted;
    protected float tabAnchorCharacterPosition;
    protected GlyphLine text;
    protected float yLineOffset;

    public TextRenderer(Text textElement) {
        this(textElement, textElement.getText());
    }

    public TextRenderer(Text textElement, String text2) {
        super((IElement) textElement);
        this.otfFeaturesApplied = false;
        this.tabAnchorCharacterPosition = -1.0f;
        this.specialScriptFirstNotFittingIndex = -1;
        this.strToBeConverted = text2;
    }

    protected TextRenderer(TextRenderer other) {
        super((AbstractRenderer) other);
        this.otfFeaturesApplied = false;
        this.tabAnchorCharacterPosition = -1.0f;
        this.specialScriptFirstNotFittingIndex = -1;
        this.text = other.text;
        this.line = other.line;
        this.font = other.font;
        this.yLineOffset = other.yLineOffset;
        this.strToBeConverted = other.strToBeConverted;
        this.otfFeaturesApplied = other.otfFeaturesApplied;
        this.tabAnchorCharacterPosition = other.tabAnchorCharacterPosition;
        this.reversedRanges = other.reversedRanges;
        this.specialScriptsWordBreakPoints = other.specialScriptsWordBreakPoints;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:134:0x04e4, code lost:
        r51 = r7;
        r10 = r2;
        r39 = r3;
        r61 = r4;
        r7 = r34;
        r4 = r52;
        r3 = r53;
        r2 = r54;
     */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x049c  */
    /* JADX WARNING: Removed duplicated region for block: B:275:0x04e0 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.layout.layout.LayoutResult layout(com.itextpdf.layout.layout.LayoutContext r81) {
        /*
            r80 = this;
            r6 = r80
            r80.updateFontAndText()
            com.itextpdf.layout.layout.LayoutArea r7 = r81.getArea()
            com.itextpdf.kernel.geom.Rectangle r0 = r7.getBBox()
            com.itextpdf.kernel.geom.Rectangle r8 = r0.clone()
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            com.itextpdf.layout.renderer.IRenderer r1 = r6.parent
            r2 = 118(0x76, float:1.65E-43)
            java.lang.Object r1 = r1.getOwnProperty(r2)
            boolean r9 = r0.equals(r1)
            com.itextpdf.layout.renderer.IRenderer r0 = r6.parent
            r1 = 103(0x67, float:1.44E-43)
            java.lang.Object r0 = r0.getProperty(r1)
            r10 = r0
            com.itextpdf.layout.property.OverflowPropertyValue r10 = (com.itextpdf.layout.property.OverflowPropertyValue) r10
            java.util.List r11 = r81.getFloatRendererAreas()
            r0 = 99
            java.lang.Object r0 = r6.getProperty(r0)
            r12 = r0
            com.itextpdf.layout.property.FloatPropertyValue r12 = (com.itextpdf.layout.property.FloatPropertyValue) r12
            boolean r0 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r6, r12)
            if (r0 == 0) goto L_0x0047
            r2 = 0
            r0 = r80
            r1 = r8
            r3 = r11
            r4 = r12
            r5 = r10
            com.itextpdf.layout.renderer.FloatingHelper.adjustFloatedBlockLayoutBox(r0, r1, r2, r3, r4, r5)
        L_0x0047:
            com.itextpdf.layout.property.UnitValue[] r13 = r80.getMargins()
            r14 = 0
            r6.applyMargins(r8, r13, r14)
            com.itextpdf.layout.borders.Border[] r15 = r80.getBorders()
            r6.applyBorderBox(r8, r15, r14)
            com.itextpdf.layout.property.UnitValue[] r5 = r80.getPaddings()
            r6.applyPaddings(r8, r5, r14)
            com.itextpdf.layout.minmaxwidth.MinMaxWidth r0 = new com.itextpdf.layout.minmaxwidth.MinMaxWidth
            com.itextpdf.kernel.geom.Rectangle r1 = r7.getBBox()
            float r1 = r1.getWidth()
            float r2 = r8.getWidth()
            float r1 = r1 - r2
            r0.<init>(r1)
            r4 = r0
            if (r9 == 0) goto L_0x0079
            com.itextpdf.layout.renderer.SumSumWidthHandler r0 = new com.itextpdf.layout.renderer.SumSumWidthHandler
            r0.<init>(r4)
            r3 = r0
            goto L_0x007f
        L_0x0079:
            com.itextpdf.layout.renderer.MaxSumWidthHandler r0 = new com.itextpdf.layout.renderer.MaxSumWidthHandler
            r0.<init>(r4)
            r3 = r0
        L_0x007f:
            com.itextpdf.layout.layout.LayoutArea r0 = new com.itextpdf.layout.layout.LayoutArea
            int r1 = r7.getPageNumber()
            com.itextpdf.kernel.geom.Rectangle r2 = new com.itextpdf.kernel.geom.Rectangle
            float r14 = r8.getX()
            float r17 = r8.getY()
            float r18 = r8.getHeight()
            r19 = r3
            float r3 = r17 + r18
            r17 = r11
            r11 = 0
            r2.<init>(r14, r3, r11, r11)
            r0.<init>(r1, r2)
            r6.occupiedArea = r0
            r0 = 0
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            int r1 = r1.start
            r2 = 24
            com.itextpdf.layout.property.UnitValue r14 = r6.getPropertyAsUnitValue(r2)
            boolean r3 = r14.isPointValue()
            r11 = 1
            if (r3 != 0) goto L_0x00d0
            java.lang.Class<com.itextpdf.layout.renderer.TextRenderer> r3 = com.itextpdf.layout.renderer.TextRenderer.class
            org.slf4j.Logger r3 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r3)
            r20 = r0
            java.lang.Object[] r0 = new java.lang.Object[r11]
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r16 = 0
            r0[r16] = r2
            java.lang.String r2 = "Property {0} in percents is not supported"
            java.lang.String r0 = com.itextpdf.p026io.util.MessageFormatUtil.format(r2, r0)
            r3.error(r0)
            goto L_0x00d2
        L_0x00d0:
            r20 = r0
        L_0x00d2:
            r0 = 72
            java.lang.Float r0 = r6.getPropertyAsFloat(r0)
            float r21 = r0.floatValue()
            r0 = 15
            java.lang.Float r22 = r6.getPropertyAsFloat(r0)
            r0 = 78
            java.lang.Float r23 = r6.getPropertyAsFloat(r0)
            r0 = 1065353216(0x3f800000, float:1.0)
            java.lang.Float r0 = java.lang.Float.valueOf(r0)
            r2 = 29
            java.lang.Object r0 = r6.getProperty(r2, r0)
            java.lang.Float r0 = (java.lang.Float) r0
            float r24 = r0.floatValue()
            r0 = 62
            java.lang.Object r0 = r6.getProperty(r0)
            r3 = r0
            com.itextpdf.layout.splitting.ISplitCharacters r3 = (com.itextpdf.layout.splitting.ISplitCharacters) r3
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r2 = 31
            java.lang.Boolean r2 = r6.getPropertyAsBoolean(r2)
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x011b
            r0 = 1046063444(0x3e59a954, float:0.21256)
            float r2 = r14.getValue()
            float r0 = r0 * r2
            goto L_0x011c
        L_0x011b:
            r0 = 0
        L_0x011c:
            r25 = r0
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r2 = 8
            java.lang.Boolean r2 = r6.getPropertyAsBoolean(r2)
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0136
            r0 = 1023969417(0x3d088889, float:0.033333335)
            float r2 = r14.getValue()
            float r0 = r0 * r2
            goto L_0x0137
        L_0x0136:
            r0 = 0
        L_0x0137:
            r26 = r0
            com.itextpdf.io.font.otf.GlyphLine r0 = new com.itextpdf.io.font.otf.GlyphLine
            com.itextpdf.io.font.otf.GlyphLine r2 = r6.text
            r0.<init>((com.itextpdf.p026io.font.otf.GlyphLine) r2)
            r6.line = r0
            r2 = -1
            r0.end = r2
            r0.start = r2
            r0 = 0
            r27 = 0
            r28 = 0
            r29 = 0
            r30 = 0
            r31 = r1
            r32 = 0
            r33 = -1
            r2 = 123(0x7b, float:1.72E-43)
            java.lang.Object r2 = r6.getProperty(r2)
            com.itextpdf.layout.property.RenderingMode r2 = (com.itextpdf.layout.property.RenderingMode) r2
            com.itextpdf.kernel.font.PdfFont r11 = r6.font
            float[] r11 = calculateAscenderDescender(r11, r2)
            r16 = 0
            r0 = r11[r16]
            r36 = r12
            r35 = 1
            r12 = r11[r35]
            r27 = r0
            com.itextpdf.layout.property.RenderingMode r0 = com.itextpdf.layout.property.RenderingMode.HTML_MODE
            boolean r0 = r0.equals(r2)
            r37 = 1148846080(0x447a0000, float:1000.0)
            if (r0 == 0) goto L_0x018a
            r28 = r11[r16]
            r29 = r11[r35]
            float r0 = r28 - r29
            float r38 = r14.getValue()
            float r0 = r0 * r38
            float r0 = r0 / r37
            float r30 = r0 + r21
        L_0x018a:
            r0 = 0
            r6.savedWordBreakAtLineEnding = r0
            r38 = 0
            r0 = 66
            java.lang.Object r0 = r6.getProperty(r0)
            java.lang.Character r0 = (java.lang.Character) r0
            r40 = 0
            boolean r41 = r81.isClippedHeight()
            if (r41 != 0) goto L_0x01aa
            com.itextpdf.layout.property.OverflowPropertyValue r41 = com.itextpdf.layout.property.OverflowPropertyValue.FIT
            r42 = r2
            r77 = r41
            r41 = r0
            r0 = r77
            goto L_0x01b8
        L_0x01aa:
            r41 = r0
            com.itextpdf.layout.renderer.IRenderer r0 = r6.parent
            r42 = r2
            r2 = 104(0x68, float:1.46E-43)
            java.lang.Object r0 = r0.getProperty(r2)
            com.itextpdf.layout.property.OverflowPropertyValue r0 = (com.itextpdf.layout.property.OverflowPropertyValue) r0
        L_0x01b8:
            r43 = r0
            r0 = 0
            r2 = 0
            r44 = 0
            r45 = 0
            r46 = r0
            r0 = 30
            java.lang.Object r0 = r6.getProperty(r0)
            com.itextpdf.layout.hyphenation.HyphenationConfig r0 = (com.itextpdf.layout.hyphenation.HyphenationConfig) r0
            r47 = r1
            r48 = r11
            r11 = r47
        L_0x01d0:
            r47 = r0
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.text
            int r0 = r0.end
            if (r11 >= r0) goto L_0x01e9
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.text
            com.itextpdf.io.font.otf.Glyph r0 = r0.get(r11)
            boolean r0 = noPrint(r0)
            if (r0 == 0) goto L_0x01e9
            int r11 = r11 + 1
            r0 = r47
            goto L_0x01d0
        L_0x01e9:
            r0 = r41
            r77 = r2
            r2 = r1
            r1 = r28
            r28 = r77
            r78 = r29
            r29 = r7
            r7 = r78
            r79 = r30
            r30 = r13
            r13 = r79
        L_0x01fe:
            r41 = r0
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.text
            int r0 = r0.end
            r49 = r15
            if (r2 >= r0) goto L_0x08c7
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.text
            com.itextpdf.io.font.otf.Glyph r0 = r0.get(r2)
            boolean r0 = noPrint(r0)
            if (r0 == 0) goto L_0x0236
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            int r0 = r0.start
            r15 = -1
            if (r0 != r15) goto L_0x021f
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            r0.start = r2
        L_0x021f:
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            int r15 = r0.end
            r50 = r1
            int r1 = r2 + 1
            int r1 = java.lang.Math.max(r15, r1)
            r0.end = r1
            int r2 = r2 + 1
            r0 = r41
            r15 = r49
            r1 = r50
            goto L_0x01fe
        L_0x0236:
            r50 = r1
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.text
            int r0 = r0.end
            r1 = 1
            int r51 = r0 + -1
            r0 = 0
            r1 = 0
            r52 = 0
            r53 = 0
            r54 = 0
            r55 = -1
            r56 = 0
            r57 = -1
            r58 = 0
            r59 = 0
            r60 = r2
            r15 = r55
            r77 = r33
            r33 = r0
            r0 = r41
            r41 = r38
            r38 = r1
            r1 = r77
            r78 = r60
            r60 = r7
            r7 = r78
            r79 = r57
            r57 = r13
            r13 = r79
        L_0x026d:
            r61 = r1
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            int r1 = r1.end
            if (r7 >= r1) goto L_0x04f4
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            com.itextpdf.io.font.otf.Glyph r1 = r1.get(r7)
            boolean r1 = com.itextpdf.p026io.util.TextUtil.isNewLine((com.itextpdf.p026io.font.otf.Glyph) r1)
            if (r1 == 0) goto L_0x02dd
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            com.itextpdf.io.font.otf.Glyph r1 = r1.get(r7)
            r41 = 1
            int r15 = r7 + 1
            if (r7 == r11) goto L_0x0290
            r44 = 1
            goto L_0x0292
        L_0x0290:
            r28 = 1
        L_0x0292:
            r62 = r1
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.line
            int r1 = r1.start
            r63 = r4
            r4 = -1
            if (r1 != r4) goto L_0x02a1
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.line
            r1.start = r2
        L_0x02a1:
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            boolean r1 = com.itextpdf.p026io.util.TextUtil.isCarriageReturnFollowedByLineFeed(r1, r2)
            if (r1 == 0) goto L_0x02ab
            int r2 = r2 + 1
        L_0x02ab:
            com.itextpdf.io.font.otf.GlyphLine r4 = r6.line
            r45 = r1
            int r1 = r4.end
            r46 = r2
            int r2 = r15 + -1
            int r1 = java.lang.Math.max(r1, r2)
            r4.end = r1
            r64 = r0
            r70 = r5
            r39 = r12
            r67 = r19
            r5 = r27
            r7 = r46
            r12 = r47
            r66 = r50
            r4 = r52
            r2 = r54
            r68 = r63
            r27 = r10
            r10 = r13
            r46 = r41
            r41 = r62
            r13 = r3
            r3 = r53
            goto L_0x051a
        L_0x02dd:
            r63 = r4
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            com.itextpdf.io.font.otf.Glyph r62 = r1.get(r7)
            boolean r1 = noPrint(r62)
            if (r1 == 0) goto L_0x0342
            int r1 = r7 + 1
            com.itextpdf.io.font.otf.GlyphLine r4 = r6.text
            int r4 = r4.end
            if (r1 == r4) goto L_0x0323
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            int r4 = r7 + 1
            boolean r1 = r3.isSplitCharacter(r1, r4)
            if (r1 == 0) goto L_0x030c
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            int r4 = r7 + 1
            com.itextpdf.io.font.otf.Glyph r1 = r1.get(r4)
            boolean r1 = com.itextpdf.p026io.util.TextUtil.isSpaceOrWhitespace(r1)
            if (r1 == 0) goto L_0x030c
            goto L_0x0323
        L_0x030c:
            r34 = r2
            r70 = r5
            r2 = r13
            r67 = r19
            r5 = r27
            r66 = r50
            r1 = r61
            r68 = r63
            r13 = r3
            r27 = r10
            r3 = r12
            r12 = r47
            goto L_0x04c6
        L_0x0323:
            r51 = r7
            r64 = r0
            r7 = r2
            r70 = r5
            r39 = r12
            r67 = r19
            r5 = r27
            r12 = r47
            r66 = r50
            r4 = r52
            r2 = r54
            r68 = r63
            r27 = r10
            r10 = r13
            r13 = r3
            r3 = r53
            goto L_0x051a
        L_0x0342:
            if (r0 == 0) goto L_0x035c
            char r1 = r0.charValue()
            com.itextpdf.io.font.otf.GlyphLine r4 = r6.text
            com.itextpdf.io.font.otf.Glyph r4 = r4.get(r7)
            int r4 = r4.getUnicode()
            if (r1 != r4) goto L_0x035c
            float r1 = r32 + r33
            r6.tabAnchorCharacterPosition = r1
            r0 = 0
            r64 = r0
            goto L_0x035e
        L_0x035c:
            r64 = r0
        L_0x035e:
            float r4 = r14.getValue()
            java.lang.Float r65 = java.lang.Float.valueOf(r24)
            r39 = r12
            r12 = r47
            r1 = 0
            r77 = r27
            r27 = r10
            r10 = r77
            r0 = r80
            r47 = r10
            r66 = r50
            r10 = r61
            r1 = r62
            r34 = r2
            r50 = r13
            r13 = -1
            r2 = r4
            r4 = r3
            r67 = r19
            r3 = r65
            r69 = r4
            r68 = r63
            r4 = r22
            r70 = r5
            r5 = r23
            float r0 = r0.getCharWidth(r1, r2, r3, r4, r5)
            float r0 = r0 / r37
            if (r10 == r13) goto L_0x03a4
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            com.itextpdf.io.font.otf.Glyph r1 = r1.get(r10)
            short r1 = r1.getXAdvance()
            float r1 = (float) r1
            goto L_0x03a5
        L_0x03a4:
            r1 = 0
        L_0x03a5:
            r2 = 0
            int r3 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r3 == 0) goto L_0x03b8
            float r2 = r14.getValue()
            java.lang.Float r3 = java.lang.Float.valueOf(r24)
            float r2 = r6.scaleXAdvance(r1, r2, r3)
            float r1 = r2 / r37
        L_0x03b8:
            if (r9 != 0) goto L_0x03cd
            float r2 = r33 + r0
            float r2 = r2 + r1
            float r2 = r2 + r25
            float r2 = r2 + r26
            float r3 = r8.getWidth()
            float r3 = r3 - r32
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x03cd
            if (r15 == r13) goto L_0x03d1
        L_0x03cd:
            int r2 = r6.specialScriptFirstNotFittingIndex
            if (r7 != r2) goto L_0x03f9
        L_0x03d1:
            r15 = r7
            com.itextpdf.io.font.otf.GlyphLine r2 = r6.text
            com.itextpdf.io.font.otf.Glyph r2 = r2.get(r7)
            boolean r2 = com.itextpdf.p026io.util.TextUtil.isSpaceOrWhitespace(r2)
            if (r2 == 0) goto L_0x03f9
            r41 = r62
            if (r7 != r11) goto L_0x03f9
            r2 = 1
            int r15 = r7 + 1
            r28 = r2
            r61 = r10
            r7 = r34
            r5 = r47
            r10 = r50
            r4 = r52
            r3 = r53
            r2 = r54
            r13 = r69
            goto L_0x051a
        L_0x03f9:
            if (r12 == 0) goto L_0x0417
            com.itextpdf.io.font.otf.GlyphLine r2 = r6.text
            boolean r2 = glyphBelongsToNonBreakingHyphenRelatedChunk(r2, r7)
            if (r2 == 0) goto L_0x0411
            r2 = r50
            if (r13 != r2) goto L_0x040c
            r58 = r52
            r59 = r53
            r2 = r7
        L_0x040c:
            float r3 = r0 + r1
            float r56 = r56 + r3
            goto L_0x0419
        L_0x0411:
            r2 = r50
            r2 = -1
            r56 = 0
            goto L_0x0419
        L_0x0417:
            r2 = r50
        L_0x0419:
            if (r15 != r13) goto L_0x041f
            float r3 = r0 + r1
            float r38 = r38 + r3
        L_0x041f:
            float r3 = r0 + r1
            float r33 = r33 + r3
            r5 = r47
            r3 = r52
            float r52 = java.lang.Math.max(r3, r5)
            r3 = r39
            r4 = r53
            float r53 = java.lang.Math.min(r4, r3)
            float r4 = r52 - r53
            float r19 = r14.getValue()
            float r4 = r4 * r19
            float r4 = r4 / r37
            float r54 = r4 + r21
            r4 = r7
            if (r9 != 0) goto L_0x047c
            float r10 = r33 + r25
            float r10 = r10 + r26
            float r19 = r8.getWidth()
            int r10 = (r10 > r19 ? 1 : (r10 == r19 ? 0 : -1))
            if (r10 <= 0) goto L_0x047c
            r10 = 0
            int r19 = (r10 > r56 ? 1 : (r10 == r56 ? 0 : -1))
            if (r19 == 0) goto L_0x0465
            int r10 = r7 + 1
            com.itextpdf.io.font.otf.GlyphLine r13 = r6.text
            int r13 = r13.end
            if (r10 == r13) goto L_0x0465
            com.itextpdf.io.font.otf.GlyphLine r10 = r6.text
            int r13 = r7 + 1
            boolean r10 = glyphBelongsToNonBreakingHyphenRelatedChunk(r10, r13)
            if (r10 != 0) goto L_0x047c
        L_0x0465:
            boolean r10 = isOverflowFit(r27)
            if (r10 == 0) goto L_0x047c
            r10 = r2
            r39 = r3
            r61 = r4
            r7 = r34
            r4 = r52
            r3 = r53
            r2 = r54
            r13 = r69
            goto L_0x051a
        L_0x047c:
            r10 = 1
            boolean r13 = r6.textContainsSpecialScriptGlyphs(r10)
            if (r13 == 0) goto L_0x0491
            java.util.List<java.lang.Integer> r13 = r6.specialScriptsWordBreakPoints
            r39 = r0
            int r0 = r7 + 1
            int r0 = findPossibleBreaksSplitPosition(r13, r0, r10)
            if (r0 < 0) goto L_0x0493
            r0 = 1
            goto L_0x0494
        L_0x0491:
            r39 = r0
        L_0x0493:
            r0 = 0
        L_0x0494:
            int r10 = r7 + 1
            com.itextpdf.io.font.otf.GlyphLine r13 = r6.text
            int r13 = r13.end
            if (r10 == r13) goto L_0x04e0
            com.itextpdf.io.font.otf.GlyphLine r10 = r6.text
            r13 = r69
            boolean r10 = r13.isSplitCharacter(r10, r7)
            if (r10 != 0) goto L_0x04dd
            com.itextpdf.io.font.otf.GlyphLine r10 = r6.text
            r47 = r1
            int r1 = r7 + 1
            boolean r1 = r13.isSplitCharacter(r10, r1)
            if (r1 == 0) goto L_0x04c0
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            int r10 = r7 + 1
            com.itextpdf.io.font.otf.Glyph r1 = r1.get(r10)
            boolean r1 = com.itextpdf.p026io.util.TextUtil.isSpaceOrWhitespace(r1)
            if (r1 != 0) goto L_0x04c2
        L_0x04c0:
            if (r0 == 0) goto L_0x04c3
        L_0x04c2:
            goto L_0x04e4
        L_0x04c3:
            r1 = r4
            r0 = r64
        L_0x04c6:
            int r7 = r7 + 1
            r47 = r12
            r10 = r27
            r50 = r66
            r19 = r67
            r4 = r68
            r12 = r3
            r27 = r5
            r3 = r13
            r5 = r70
            r13 = r2
            r2 = r34
            goto L_0x026d
        L_0x04dd:
            r47 = r1
            goto L_0x04e4
        L_0x04e0:
            r47 = r1
            r13 = r69
        L_0x04e4:
            r51 = r7
            r10 = r2
            r39 = r3
            r61 = r4
            r7 = r34
            r4 = r52
            r3 = r53
            r2 = r54
            goto L_0x051a
        L_0x04f4:
            r34 = r2
            r68 = r4
            r70 = r5
            r39 = r12
            r2 = r13
            r67 = r19
            r5 = r27
            r12 = r47
            r66 = r50
            r4 = r53
            r13 = r3
            r27 = r10
            r3 = r52
            r10 = r61
            r64 = r0
            r7 = r34
            r10 = r2
            r2 = r54
            r77 = r4
            r4 = r3
            r3 = r77
        L_0x051a:
            r0 = -1
            if (r15 != r0) goto L_0x0580
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.line
            int r1 = r1.start
            if (r1 != r0) goto L_0x0527
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            r0.start = r7
        L_0x0527:
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            int r1 = r0.end
            r47 = r5
            int r5 = r51 + 1
            int r1 = java.lang.Math.max(r1, r5)
            r0.end = r1
            r5 = r66
            float r1 = java.lang.Math.max(r5, r4)
            r0 = r60
            float r0 = java.lang.Math.min(r0, r3)
            r5 = r57
            float r5 = java.lang.Math.max(r5, r2)
            int r7 = r51 + 1
            float r32 = r32 + r33
            float r34 = r38 + r25
            r50 = r0
            float r0 = r34 + r26
            r34 = r1
            r1 = r67
            r1.updateMinChildWidth(r0)
            float r0 = r38 + r25
            float r0 = r0 + r26
            r1.updateMaxChildWidth(r0)
            r20 = 1
            r19 = r1
            r2 = r7
            r3 = r13
            r10 = r27
            r1 = r34
            r38 = r41
            r27 = r47
            r15 = r49
            r7 = r50
            r33 = r61
            r0 = r64
            r4 = r68
            r13 = r5
            r47 = r12
            r12 = r39
            r5 = r70
            goto L_0x01fe
        L_0x0580:
            r47 = r5
            r1 = r57
            r0 = r60
            r5 = r66
            float r34 = java.lang.Math.max(r1, r2)
            float r50 = r8.getHeight()
            int r34 = (r34 > r50 ? 1 : (r34 == r50 ? 0 : -1))
            if (r34 <= 0) goto L_0x0618
            boolean r34 = isOverflowFit(r43)
            if (r34 == 0) goto L_0x0618
            r60 = r0
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r57 = r1
            r34 = r2
            r1 = r70
            r2 = 1
            r6.applyPaddings(r0, r1, r2)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r1 = r49
            r6.applyBorderBox(r0, r1, r2)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r1 = r30
            r6.applyMargins(r0, r1, r2)
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            int r0 = r0.start
            r2 = -1
            if (r0 != r2) goto L_0x05cd
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            r0.start = r7
        L_0x05cd:
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            int r2 = r0.end
            int r2 = java.lang.Math.max(r2, r15)
            r0.end = r2
            r2 = r31
            com.itextpdf.layout.renderer.TextRenderer[] r18 = r6.split(r2)
            com.itextpdf.layout.layout.TextLayoutResult r19 = new com.itextpdf.layout.layout.TextLayoutResult
            r30 = 3
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            r16 = 0
            r16 = r18[r16]
            r31 = 1
            r31 = r18[r31]
            r35 = r0
            r50 = r9
            r9 = r60
            r0 = r19
            r52 = r11
            r69 = r13
            r53 = r49
            r11 = r57
            r13 = r67
            r49 = r1
            r1 = r30
            r30 = r2
            r71 = r34
            r2 = r35
            r72 = r3
            r3 = r16
            r73 = r4
            r4 = r31
            r31 = r47
            r13 = r5
            r5 = r80
            r0.<init>(r1, r2, r3, r4, r5)
            return r19
        L_0x0618:
            r71 = r2
            r72 = r3
            r73 = r4
            r50 = r9
            r52 = r11
            r69 = r13
            r53 = r49
            r9 = r0
            r11 = r1
            r13 = r5
            r49 = r30
            r30 = r31
            r31 = r47
            r34 = 0
            r47 = 0
            if (r12 == 0) goto L_0x07a6
            r0 = -1
            if (r0 != r10) goto L_0x077b
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.text
            int r1 = r0.end
            int r2 = r15 + -1
            int r2 = java.lang.Math.max(r7, r2)
            int[] r54 = r6.getWordBoundsForHyphenation(r0, r7, r1, r2)
            if (r54 == 0) goto L_0x0763
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.text
            r1 = 0
            r2 = r54[r1]
            r1 = 1
            r3 = r54[r1]
            java.lang.String r5 = r0.toUnicodeString(r2, r3)
            com.itextpdf.layout.hyphenation.Hyphenation r4 = r12.hyphenate(r5)
            if (r4 == 0) goto L_0x0754
            int r0 = r4.length()
            int r0 = r0 - r1
            r3 = r0
        L_0x0660:
            if (r3 < 0) goto L_0x0743
            java.lang.String r2 = r4.getPreHyphenText(r3)
            java.lang.String r57 = r4.getPostHyphenText(r3)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.text
            r60 = r3
            r16 = 0
            r3 = r54[r16]
            java.lang.String r1 = r1.toUnicodeString(r7, r3)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r2)
            char r1 = r12.getHyphenSymbol()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.convertToGlyphLine(r0)
            float r3 = r14.getValue()
            r0 = r80
            r62 = r2
            r2 = r3
            r3 = r24
            r63 = r4
            r4 = r22
            r65 = r5
            r5 = r23
            float r0 = r0.getGlyphLineWidth(r1, r2, r3, r4, r5)
            float r1 = r32 + r0
            float r1 = r1 + r25
            float r1 = r1 + r26
            float r2 = r8.getWidth()
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L_0x072c
            r1 = 1
            com.itextpdf.io.font.otf.GlyphLine r2 = r6.line
            int r2 = r2.start
            r3 = -1
            if (r2 != r3) goto L_0x06c4
            com.itextpdf.io.font.otf.GlyphLine r2 = r6.line
            r2.start = r7
        L_0x06c4:
            com.itextpdf.io.font.otf.GlyphLine r2 = r6.line
            int r3 = r2.end
            r4 = 0
            r5 = r54[r4]
            int r4 = r62.length()
            int r5 = r5 + r4
            int r3 = java.lang.Math.max(r3, r5)
            r2.end = r3
            com.itextpdf.io.font.otf.GlyphLine r2 = r6.line
            int r3 = r2.start
            com.itextpdf.io.font.otf.GlyphLine r4 = r6.line
            int r4 = r4.end
            com.itextpdf.io.font.otf.GlyphLine r2 = r2.copy(r3, r4)
            com.itextpdf.kernel.font.PdfFont r3 = r6.font
            char r4 = r12.getHyphenSymbol()
            com.itextpdf.io.font.otf.Glyph r3 = r3.getGlyph(r4)
            r2.add((com.itextpdf.p026io.font.otf.Glyph) r3)
            int r3 = r2.end
            r4 = 1
            int r3 = r3 + r4
            r2.end = r3
            r6.line = r2
            r3 = r73
            float r4 = java.lang.Math.max(r13, r3)
            r5 = r72
            float r9 = java.lang.Math.min(r9, r5)
            r13 = r71
            float r11 = java.lang.Math.max(r11, r13)
            float r32 = r32 + r0
            float r47 = r0 + r25
            r66 = r1
            float r1 = r47 + r26
            r71 = r7
            r7 = r67
            r7.updateMinChildWidth(r1)
            float r1 = r0 + r25
            float r1 = r1 + r26
            r7.updateMaxChildWidth(r1)
            r1 = 0
            r47 = r54[r1]
            int r1 = r62.length()
            int r47 = r47 + r1
            r1 = r4
            r4 = r13
            r13 = r11
            goto L_0x0773
        L_0x072c:
            r4 = r71
            r5 = r72
            r3 = r73
            r71 = r7
            r7 = r67
            int r0 = r60 + -1
            r5 = r65
            r7 = r71
            r3 = r0
            r71 = r4
            r4 = r63
            goto L_0x0660
        L_0x0743:
            r60 = r3
            r63 = r4
            r65 = r5
            r4 = r71
            r5 = r72
            r3 = r73
            r71 = r7
            r7 = r67
            goto L_0x076d
        L_0x0754:
            r63 = r4
            r65 = r5
            r4 = r71
            r5 = r72
            r3 = r73
            r71 = r7
            r7 = r67
            goto L_0x076d
        L_0x0763:
            r4 = r71
            r5 = r72
            r3 = r73
            r71 = r7
            r7 = r67
        L_0x076d:
            r1 = r13
            r66 = r47
            r47 = r71
            r13 = r11
        L_0x0773:
            r11 = r3
            r2 = r9
            r0 = r47
            r47 = r66
            r9 = r5
            goto L_0x07b7
        L_0x077b:
            r4 = r71
            r5 = r72
            r3 = r73
            r71 = r7
            r7 = r67
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.text
            int r0 = r0.start
            if (r0 != r10) goto L_0x0797
            r56 = 0
            int r15 = r61 + 1
            r2 = r9
            r1 = r13
            r0 = r71
            r9 = r5
            r13 = r11
            r11 = r3
            goto L_0x07b7
        L_0x0797:
            r15 = r10
            float r33 = r33 - r56
            r0 = r58
            r3 = r59
            r2 = r9
            r1 = r13
            r9 = r3
            r13 = r11
            r11 = r0
            r0 = r71
            goto L_0x07b7
        L_0x07a6:
            r4 = r71
            r5 = r72
            r3 = r73
            r71 = r7
            r7 = r67
            r2 = r9
            r1 = r13
            r0 = r71
            r9 = r5
            r13 = r11
            r11 = r3
        L_0x07b7:
            r3 = 1
            boolean r5 = r6.textContainsSpecialScriptGlyphs(r3)
            if (r5 == 0) goto L_0x07c2
            if (r46 != 0) goto L_0x07c2
            r3 = 1
            goto L_0x07c3
        L_0x07c2:
            r3 = 0
        L_0x07c3:
            r54 = r3
            float r3 = r8.getWidth()
            int r3 = (r33 > r3 ? 1 : (r33 == r3 ? 0 : -1))
            if (r3 <= 0) goto L_0x07d1
            if (r20 != 0) goto L_0x07d1
            if (r47 == 0) goto L_0x07e6
        L_0x07d1:
            if (r28 != 0) goto L_0x07e6
            r3 = -1
            if (r3 != r10) goto L_0x07e6
            if (r54 == 0) goto L_0x07d9
            goto L_0x07e6
        L_0x07d9:
            r62 = r0
            r19 = r1
            r57 = r2
            r60 = r10
            r5 = r34
            r10 = r4
            goto L_0x088b
        L_0x07e6:
            com.itextpdf.io.font.otf.GlyphLine r3 = r6.line
            int r3 = r3.start
            r5 = -1
            if (r3 != r5) goto L_0x07f1
            com.itextpdf.io.font.otf.GlyphLine r3 = r6.line
            r3.start = r0
        L_0x07f1:
            if (r45 != 0) goto L_0x0805
            if (r28 != 0) goto L_0x0801
            boolean r3 = isOverflowFit(r27)
            if (r3 != 0) goto L_0x0801
            if (r54 == 0) goto L_0x07fe
            goto L_0x0801
        L_0x07fe:
            int r3 = r51 + 1
            goto L_0x0802
        L_0x0801:
            r3 = r15
        L_0x0802:
            r0 = r3
            r5 = r0
            goto L_0x0806
        L_0x0805:
            r5 = r0
        L_0x0806:
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            int r3 = r0.end
            int r3 = java.lang.Math.max(r3, r5)
            r0.end = r3
            if (r28 != 0) goto L_0x081a
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.text
            int r0 = r0.end
            if (r0 == r5) goto L_0x081a
            r0 = 1
            goto L_0x081b
        L_0x081a:
            r0 = 0
        L_0x081b:
            r34 = r0
            if (r34 != 0) goto L_0x0863
            if (r28 != 0) goto L_0x082d
            boolean r0 = isOverflowFit(r27)
            if (r0 != 0) goto L_0x082d
            r62 = r5
            r60 = r10
            r10 = r4
            goto L_0x0868
        L_0x082d:
            r19 = r31
            r57 = r39
            float r0 = r19 - r57
            float r1 = r14.getValue()
            float r0 = r0 * r1
            float r0 = r0 / r37
            float r13 = r0 + r21
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            int r1 = r0.start
            com.itextpdf.io.font.otf.Glyph r1 = r0.get(r1)
            float r2 = r14.getValue()
            java.lang.Float r3 = java.lang.Float.valueOf(r24)
            r0 = r80
            r60 = r10
            r10 = r4
            r4 = r22
            r62 = r5
            r5 = r23
            float r0 = r0.getCharWidth(r1, r2, r3, r4, r5)
            float r0 = r0 / r37
            float r32 = r32 + r0
            r5 = r34
            goto L_0x088b
        L_0x0863:
            r62 = r5
            r60 = r10
            r10 = r4
        L_0x0868:
            float r0 = java.lang.Math.max(r1, r11)
            float r1 = java.lang.Math.min(r2, r9)
            float r2 = java.lang.Math.max(r13, r10)
            float r32 = r32 + r38
            float r3 = r38 + r25
            float r3 = r3 + r26
            r7.updateMinChildWidth(r3)
            float r3 = r38 + r25
            float r3 = r3 + r26
            r7.updateMaxChildWidth(r3)
            r19 = r0
            r57 = r1
            r13 = r2
            r5 = r34
        L_0x088b:
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            int r0 = r0.end
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.line
            int r1 = r1.start
            if (r0 > r1) goto L_0x08a8
            com.itextpdf.layout.layout.TextLayoutResult r16 = new com.itextpdf.layout.layout.TextLayoutResult
            r1 = 3
            com.itextpdf.layout.layout.LayoutArea r2 = r6.occupiedArea
            r3 = 0
            r0 = r16
            r4 = r80
            r67 = r7
            r7 = r5
            r5 = r80
            r0.<init>(r1, r2, r3, r4, r5)
            return r16
        L_0x08a8:
            r67 = r7
            r7 = r5
            com.itextpdf.layout.layout.TextLayoutResult r0 = new com.itextpdf.layout.layout.TextLayoutResult
            com.itextpdf.layout.layout.LayoutArea r1 = r6.occupiedArea
            r2 = 2
            r3 = 0
            r0.<init>(r2, r1, r3, r3)
            com.itextpdf.layout.layout.TextLayoutResult r40 = r0.setWordHasBeenSplit(r7)
            r10 = r32
            r15 = r40
            r11 = r41
            r5 = r46
            r9 = r57
            r33 = r61
            r7 = r62
            goto L_0x08f6
        L_0x08c7:
            r34 = r2
            r69 = r3
            r68 = r4
            r70 = r5
            r50 = r9
            r52 = r11
            r39 = r12
            r11 = r13
            r67 = r19
            r12 = r47
            r53 = r49
            r3 = 0
            r13 = r1
            r9 = r7
            r49 = r30
            r30 = r31
            r31 = r27
            r27 = r10
            r19 = r13
            r10 = r32
            r7 = r34
            r15 = r40
            r64 = r41
            r5 = r46
            r13 = r11
            r11 = r38
        L_0x08f6:
            r32 = 0
            float r0 = r8.getHeight()
            int r0 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x0962
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r1 = 26
            java.lang.Boolean r1 = r6.getPropertyAsBoolean(r1)
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0956
            boolean r0 = isOverflowFit(r43)
            if (r0 == 0) goto L_0x0956
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r4 = r70
            r1 = 1
            r6.applyPaddings(r0, r4, r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r3 = r53
            r6.applyBorderBox(r0, r3, r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r2 = r49
            r6.applyMargins(r0, r2, r1)
            com.itextpdf.layout.layout.TextLayoutResult r16 = new com.itextpdf.layout.layout.TextLayoutResult
            r1 = 3
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            r18 = 0
            r34 = r0
            r0 = r16
            r74 = r2
            r2 = r34
            r75 = r3
            r3 = r18
            r76 = r4
            r4 = r80
            r34 = r9
            r9 = r5
            r5 = r80
            r0.<init>(r1, r2, r3, r4, r5)
            return r16
        L_0x0956:
            r34 = r9
            r74 = r49
            r75 = r53
            r76 = r70
            r9 = r5
            r32 = 1
            goto L_0x096b
        L_0x0962:
            r34 = r9
            r74 = r49
            r75 = r53
            r76 = r70
            r9 = r5
        L_0x096b:
            float r0 = r14.getValue()
            float r0 = r0 * r19
            float r0 = r0 / r37
            r6.yLineOffset = r0
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r0.moveDown(r13)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            com.itextpdf.layout.layout.LayoutArea r1 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getHeight()
            float r1 = r1 + r13
            r0.setHeight(r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            com.itextpdf.layout.layout.LayoutArea r1 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getWidth()
            float r1 = java.lang.Math.max(r1, r10)
            r0.setWidth(r1)
            com.itextpdf.kernel.geom.Rectangle r0 = r29.getBBox()
            float r0 = r0.getHeight()
            float r0 = r0 - r13
            r8.setHeight(r0)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            com.itextpdf.layout.layout.LayoutArea r1 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getWidth()
            float r1 = r1 + r25
            float r1 = r1 + r26
            r0.setWidth(r1)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r1 = r76
            r2 = 1
            r6.applyPaddings(r0, r1, r2)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r4 = r75
            r6.applyBorderBox(r0, r4, r2)
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r0 = r0.getBBox()
            r5 = r74
            r6.applyMargins(r0, r5, r2)
            r6.increaseYLineOffset(r1, r4, r5)
            if (r15 != 0) goto L_0x0a10
            com.itextpdf.layout.layout.TextLayoutResult r0 = new com.itextpdf.layout.layout.TextLayoutResult
            r57 = 1
            com.itextpdf.layout.layout.LayoutArea r2 = r6.occupiedArea
            r59 = 0
            r60 = 0
            if (r32 == 0) goto L_0x0a02
            r61 = r6
            goto L_0x0a04
        L_0x0a02:
            r61 = r3
        L_0x0a04:
            r56 = r0
            r58 = r2
            r56.<init>(r57, r58, r59, r60, r61)
            r15 = r0
            r70 = r1
            r2 = 1
            goto L_0x0a48
        L_0x0a10:
            if (r44 != 0) goto L_0x0a1a
            if (r45 == 0) goto L_0x0a15
            goto L_0x0a1a
        L_0x0a15:
            com.itextpdf.layout.renderer.TextRenderer[] r0 = r6.split(r7)
            goto L_0x0a1e
        L_0x0a1a:
            com.itextpdf.layout.renderer.TextRenderer[] r0 = r6.splitIgnoreFirstNewLine(r7)
        L_0x0a1e:
            r15.setSplitForcedByNewline(r9)
            r2 = 0
            r3 = r0[r2]
            r15.setSplitRenderer(r3)
            if (r11 == 0) goto L_0x0a2e
            r2 = r0[r2]
            r2.saveWordBreakIfNotYetSaved(r11)
        L_0x0a2e:
            r2 = 1
            r3 = r0[r2]
            com.itextpdf.io.font.otf.GlyphLine r3 = r3.text
            int r3 = r3.start
            r70 = r1
            r1 = r0[r2]
            com.itextpdf.io.font.otf.GlyphLine r1 = r1.text
            int r1 = r1.end
            if (r3 == r1) goto L_0x0a45
            r1 = r0[r2]
            r15.setOverflowRenderer(r1)
            goto L_0x0a48
        L_0x0a45:
            r15.setStatus(r2)
        L_0x0a48:
            r0 = r36
            boolean r1 = com.itextpdf.layout.renderer.FloatingHelper.isRendererFloating(r6, r0)
            if (r1 == 0) goto L_0x0a8d
            int r1 = r15.getStatus()
            if (r1 != r2) goto L_0x0a74
            com.itextpdf.layout.layout.LayoutArea r1 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            float r1 = r1.getWidth()
            r2 = 0
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x0a71
            com.itextpdf.layout.layout.LayoutArea r1 = r6.occupiedArea
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r2 = r17
            r2.add(r1)
            goto L_0x0a8f
        L_0x0a71:
            r2 = r17
            goto L_0x0a8f
        L_0x0a74:
            r2 = r17
            int r1 = r15.getStatus()
            r3 = 2
            if (r1 != r3) goto L_0x0a8f
            com.itextpdf.layout.renderer.IRenderer r1 = r15.getSplitRenderer()
            com.itextpdf.layout.layout.LayoutArea r1 = r1.getOccupiedArea()
            com.itextpdf.kernel.geom.Rectangle r1 = r1.getBBox()
            r2.add(r1)
            goto L_0x0a8f
        L_0x0a8d:
            r2 = r17
        L_0x0a8f:
            r1 = r68
            r15.setMinMaxWidth(r1)
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.TextRenderer.layout(com.itextpdf.layout.layout.LayoutContext):com.itextpdf.layout.layout.LayoutResult");
    }

    private void increaseYLineOffset(UnitValue[] paddings, Border[] borders, UnitValue[] margins) {
        float f = 0.0f;
        float value = this.yLineOffset + (paddings[0] != null ? paddings[0].getValue() : 0.0f);
        this.yLineOffset = value;
        float width = value + (borders[0] != null ? borders[0].getWidth() : 0.0f);
        this.yLineOffset = width;
        if (margins[0] != null) {
            f = margins[0].getValue();
        }
        this.yLineOffset = width + f;
    }

    public void applyOtf() {
        updateFontAndText();
        Character.UnicodeScript script = (Character.UnicodeScript) getProperty(23);
        if (!this.otfFeaturesApplied && TypographyUtils.isPdfCalligraphAvailable() && this.text.start < this.text.end) {
            if (hasOtfFont()) {
                Object typographyConfig = getProperty(117);
                Collection<Character.UnicodeScript> supportedScripts = null;
                if (typographyConfig != null) {
                    supportedScripts = TypographyUtils.getSupportedScripts(typographyConfig);
                }
                if (supportedScripts == null) {
                    supportedScripts = TypographyUtils.getSupportedScripts();
                }
                List<ScriptRange> scriptsRanges = new ArrayList<>();
                if (script != null) {
                    scriptsRanges.add(new ScriptRange(script, this.text.end));
                } else {
                    ScriptRange currRange = new ScriptRange((Character.UnicodeScript) null, this.text.end);
                    scriptsRanges.add(currRange);
                    for (int i = this.text.start; i < this.text.end; i++) {
                        int unicode = this.text.get(i).getUnicode();
                        if (unicode > -1) {
                            Character.UnicodeScript glyphScript = Character.UnicodeScript.of(unicode);
                            if (!Character.UnicodeScript.COMMON.equals(glyphScript) && !Character.UnicodeScript.UNKNOWN.equals(glyphScript) && !Character.UnicodeScript.INHERITED.equals(glyphScript) && glyphScript != currRange.script) {
                                if (currRange.script == null) {
                                    currRange.script = glyphScript;
                                } else {
                                    currRange.rangeEnd = i;
                                    currRange = new ScriptRange(glyphScript, this.text.end);
                                    scriptsRanges.add(currRange);
                                }
                            }
                        }
                    }
                }
                int delta = 0;
                int origTextStart = this.text.start;
                int origTextEnd = this.text.end;
                int shapingRangeStart = this.text.start;
                for (ScriptRange scriptsRange : scriptsRanges) {
                    if (scriptsRange.script != null && supportedScripts.contains(EnumUtil.throwIfNull(scriptsRange.script))) {
                        scriptsRange.rangeEnd += delta;
                        this.text.start = shapingRangeStart;
                        this.text.end = scriptsRange.rangeEnd;
                        if ((scriptsRange.script == Character.UnicodeScript.ARABIC || scriptsRange.script == Character.UnicodeScript.HEBREW) && (this.parent instanceof LineRenderer)) {
                            setProperty(7, BaseDirection.DEFAULT_BIDI);
                        }
                        TypographyUtils.applyOtfScript(this.font.getFontProgram(), this.text, scriptsRange.script, typographyConfig);
                        delta += this.text.end - scriptsRange.rangeEnd;
                        int i2 = this.text.end;
                        shapingRangeStart = i2;
                        scriptsRange.rangeEnd = i2;
                    }
                }
                this.text.start = origTextStart;
                this.text.end = origTextEnd + delta;
            }
            if (((FontKerning) getProperty(22, FontKerning.NO)) == FontKerning.YES) {
                TypographyUtils.applyKerning(this.font.getFontProgram(), this.text);
            }
            this.otfFeaturesApplied = true;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: com.itextpdf.layout.tagging.LayoutTaggingHelper} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(com.itextpdf.layout.renderer.DrawContext r34) {
        /*
            r33 = this;
            r6 = r33
            com.itextpdf.layout.layout.LayoutArea r0 = r6.occupiedArea
            java.lang.Class<com.itextpdf.layout.renderer.TextRenderer> r1 = com.itextpdf.layout.renderer.TextRenderer.class
            r7 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001e
            org.slf4j.Logger r0 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r1)
            java.lang.Object[] r1 = new java.lang.Object[r2]
            java.lang.String r2 = "Drawing won't be performed."
            r1[r7] = r2
            java.lang.String r2 = "Occupied area has not been initialized. {0}"
            java.lang.String r1 = com.itextpdf.p026io.util.MessageFormatUtil.format(r2, r1)
            r0.error(r1)
            return
        L_0x001e:
            boolean r8 = r34.isTaggingEnabled()
            r0 = 0
            r3 = 0
            r4 = 0
            if (r8 == 0) goto L_0x005a
            r5 = 108(0x6c, float:1.51E-43)
            java.lang.Object r5 = r6.getProperty(r5)
            r0 = r5
            com.itextpdf.layout.tagging.LayoutTaggingHelper r0 = (com.itextpdf.layout.tagging.LayoutTaggingHelper) r0
            if (r0 != 0) goto L_0x0037
            r3 = 1
            r9 = r0
            r10 = r3
            r11 = r4
            goto L_0x005d
        L_0x0037:
            boolean r3 = r0.isArtifact(r6)
            if (r3 != 0) goto L_0x0056
            com.itextpdf.kernel.pdf.tagutils.TagTreePointer r4 = r0.useAutoTaggingPointerAndRememberItsPosition(r6)
            boolean r5 = r0.createTag((com.itextpdf.layout.renderer.IRenderer) r6, (com.itextpdf.kernel.pdf.tagutils.TagTreePointer) r4)
            if (r5 == 0) goto L_0x0052
            com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties r5 = r4.getProperties()
            com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes r9 = com.itextpdf.layout.renderer.AccessibleAttributesApplier.getLayoutAttributes(r6, r4)
            r5.addAttributes(r7, r9)
        L_0x0052:
            r9 = r0
            r10 = r3
            r11 = r4
            goto L_0x005d
        L_0x0056:
            r9 = r0
            r10 = r3
            r11 = r4
            goto L_0x005d
        L_0x005a:
            r9 = r0
            r10 = r3
            r11 = r4
        L_0x005d:
            super.draw(r34)
            boolean r12 = r33.isRelativePosition()
            if (r12 == 0) goto L_0x0069
            r6.applyRelativePositioningTranslation(r7)
        L_0x0069:
            com.itextpdf.kernel.geom.Rectangle r0 = r33.getInnerAreaBBox()
            float r5 = r0.getX()
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.line
            int r0 = r0.end
            com.itextpdf.io.font.otf.GlyphLine r3 = r6.line
            int r3 = r3.start
            if (r0 > r3) goto L_0x0085
            com.itextpdf.io.font.otf.GlyphLine r0 = r6.savedWordBreakAtLineEnding
            if (r0 == 0) goto L_0x0080
            goto L_0x0085
        L_0x0080:
            r28 = r5
            r15 = r8
            goto L_0x0381
        L_0x0085:
            r0 = 24
            com.itextpdf.layout.property.UnitValue r20 = r6.getPropertyAsUnitValue(r0)
            boolean r3 = r20.isPointValue()
            if (r3 != 0) goto L_0x00a6
            org.slf4j.Logger r1 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r1)
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r3[r7] = r0
            java.lang.String r0 = "Property {0} in percents is not supported"
            java.lang.String r0 = com.itextpdf.p026io.util.MessageFormatUtil.format(r0, r3)
            r1.error(r0)
        L_0x00a6:
            r0 = 21
            com.itextpdf.layout.property.TransparentColor r4 = r6.getPropertyAsTransparentColor(r0)
            r0 = 71
            java.lang.Object r0 = r6.getProperty(r0)
            java.lang.Integer r0 = (java.lang.Integer) r0
            r1 = 72
            java.lang.Float r21 = r6.getPropertyAsFloat(r1)
            r1 = 15
            java.lang.Float r22 = r6.getPropertyAsFloat(r1)
            r1 = 78
            java.lang.Float r23 = r6.getPropertyAsFloat(r1)
            r1 = 29
            java.lang.Object r1 = r6.getProperty(r1)
            r24 = r1
            java.lang.Float r24 = (java.lang.Float) r24
            r1 = 65
            java.lang.Object r1 = r6.getProperty(r1)
            r3 = r1
            float[] r3 = (float[]) r3
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r13 = 31
            java.lang.Boolean r13 = r6.getPropertyAsBoolean(r13)
            boolean r25 = r1.equals(r13)
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r13 = 8
            java.lang.Boolean r13 = r6.getPropertyAsBoolean(r13)
            boolean r26 = r1.equals(r13)
            r1 = 0
            r15 = 2
            if (r26 == 0) goto L_0x0107
            java.lang.Integer r0 = java.lang.Integer.valueOf(r15)
            float r13 = r20.getValue()
            r14 = 1106247680(0x41f00000, float:30.0)
            float r13 = r13 / r14
            java.lang.Float r1 = java.lang.Float.valueOf(r13)
            r27 = r0
            goto L_0x0109
        L_0x0107:
            r27 = r0
        L_0x0109:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r0 = r34.getCanvas()
            if (r8 == 0) goto L_0x0121
            if (r10 == 0) goto L_0x011a
            com.itextpdf.kernel.pdf.canvas.CanvasArtifact r13 = new com.itextpdf.kernel.pdf.canvas.CanvasArtifact
            r13.<init>()
            r0.openTag((com.itextpdf.kernel.pdf.canvas.CanvasTag) r13)
            goto L_0x0121
        L_0x011a:
            com.itextpdf.kernel.pdf.tagutils.TagReference r13 = r11.getTagReference()
            r0.openTag((com.itextpdf.kernel.pdf.tagutils.TagReference) r13)
        L_0x0121:
            r33.beginElementOpacityApplying(r34)
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r13 = r0.saveState()
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r13 = r13.beginText()
            com.itextpdf.kernel.font.PdfFont r14 = r6.font
            float r2 = r20.getValue()
            r13.setFontAndSize(r14, r2)
            if (r3 == 0) goto L_0x0151
            int r2 = r3.length
            if (r2 != r15) goto L_0x0151
            r14 = 1065353216(0x3f800000, float:1.0)
            r2 = r3[r7]
            r13 = 1
            r16 = r3[r13]
            r17 = 1065353216(0x3f800000, float:1.0)
            float r19 = r33.getYLine()
            r13 = r0
            r7 = 2
            r15 = r2
            r18 = r5
            r13.setTextMatrix(r14, r15, r16, r17, r18, r19)
            r15 = r8
            goto L_0x0172
        L_0x0151:
            r7 = 2
            if (r25 == 0) goto L_0x0168
            r14 = 1065353216(0x3f800000, float:1.0)
            r15 = 0
            r16 = 1046063444(0x3e59a954, float:0.21256)
            r17 = 1065353216(0x3f800000, float:1.0)
            float r19 = r33.getYLine()
            r13 = r0
            r18 = r5
            r13.setTextMatrix(r14, r15, r16, r17, r18, r19)
            r15 = r8
            goto L_0x0172
        L_0x0168:
            double r13 = (double) r5
            float r2 = r33.getYLine()
            r15 = r8
            double r7 = (double) r2
            r0.moveText(r13, r7)
        L_0x0172:
            int r2 = r27.intValue()
            if (r2 == 0) goto L_0x017f
            int r2 = r27.intValue()
            r0.setTextRenderingMode(r2)
        L_0x017f:
            int r2 = r27.intValue()
            r7 = 1065353216(0x3f800000, float:1.0)
            r8 = 1
            if (r2 == r8) goto L_0x0192
            int r2 = r27.intValue()
            r8 = 2
            if (r2 != r8) goto L_0x0190
            goto L_0x0192
        L_0x0190:
            r8 = r1
            goto L_0x01bf
        L_0x0192:
            if (r1 != 0) goto L_0x019a
            r2 = 64
            java.lang.Float r1 = r6.getPropertyAsFloat(r2)
        L_0x019a:
            if (r1 == 0) goto L_0x01ab
            float r2 = r1.floatValue()
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 == 0) goto L_0x01ab
            float r2 = r1.floatValue()
            r0.setLineWidth(r2)
        L_0x01ab:
            r2 = 63
            com.itextpdf.kernel.colors.Color r2 = r6.getPropertyAsColor(r2)
            if (r2 != 0) goto L_0x01b9
            if (r4 == 0) goto L_0x01b9
            com.itextpdf.kernel.colors.Color r2 = r4.getColor()
        L_0x01b9:
            if (r2 == 0) goto L_0x01be
            r0.setStrokeColor(r2)
        L_0x01be:
            r8 = r1
        L_0x01bf:
            if (r4 == 0) goto L_0x01cb
            com.itextpdf.kernel.colors.Color r1 = r4.getColor()
            r0.setFillColor(r1)
            r4.applyFillTransparency(r0)
        L_0x01cb:
            r13 = 0
            if (r21 == 0) goto L_0x01dd
            float r1 = r21.floatValue()
            int r1 = (r1 > r13 ? 1 : (r1 == r13 ? 0 : -1))
            if (r1 == 0) goto L_0x01dd
            float r1 = r21.floatValue()
            r0.setTextRise(r1)
        L_0x01dd:
            if (r22 == 0) goto L_0x01ee
            float r1 = r22.floatValue()
            int r1 = (r1 > r13 ? 1 : (r1 == r13 ? 0 : -1))
            if (r1 == 0) goto L_0x01ee
            float r1 = r22.floatValue()
            r0.setCharacterSpacing(r1)
        L_0x01ee:
            if (r23 == 0) goto L_0x0243
            float r1 = r23.floatValue()
            int r1 = (r1 > r13 ? 1 : (r1 == r13 ? 0 : -1))
            if (r1 == 0) goto L_0x0243
            com.itextpdf.kernel.font.PdfFont r1 = r6.font
            boolean r1 = r1 instanceof com.itextpdf.kernel.font.PdfType0Font
            if (r1 == 0) goto L_0x023c
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.line
            int r1 = r1.start
        L_0x0202:
            com.itextpdf.io.font.otf.GlyphLine r2 = r6.line
            int r2 = r2.end
            if (r1 >= r2) goto L_0x023b
            com.itextpdf.io.font.otf.GlyphLine r2 = r6.line
            com.itextpdf.io.font.otf.Glyph r2 = r2.get(r1)
            boolean r2 = com.itextpdf.p026io.util.TextUtil.isUni0020(r2)
            if (r2 == 0) goto L_0x0237
            r2 = 1148846080(0x447a0000, float:1000.0)
            float r14 = r23.floatValue()
            float r14 = r14 * r2
            float r2 = r20.getValue()
            float r14 = r14 / r2
            int r2 = (int) r14
            short r2 = (short) r2
            com.itextpdf.io.font.otf.Glyph r14 = new com.itextpdf.io.font.otf.Glyph
            com.itextpdf.io.font.otf.GlyphLine r13 = r6.line
            com.itextpdf.io.font.otf.Glyph r13 = r13.get(r1)
            r14.<init>(r13)
            r13 = r14
            r13.setXAdvance(r2)
            com.itextpdf.io.font.otf.GlyphLine r14 = r6.line
            r14.set(r1, r13)
        L_0x0237:
            int r1 = r1 + 1
            r13 = 0
            goto L_0x0202
        L_0x023b:
            goto L_0x0243
        L_0x023c:
            float r1 = r23.floatValue()
            r0.setWordSpacing(r1)
        L_0x0243:
            if (r24 == 0) goto L_0x0258
            float r1 = r24.floatValue()
            int r1 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r1 == 0) goto L_0x0258
            float r1 = r24.floatValue()
            r2 = 1120403456(0x42c80000, float:100.0)
            float r1 = r1 * r2
            r0.setHorizontalScaling(r1)
        L_0x0258:
            com.itextpdf.layout.renderer.TextRenderer$1 r1 = new com.itextpdf.layout.renderer.TextRenderer$1
            r1.<init>()
            r7 = r1
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r2 = 82
            java.lang.Boolean r2 = r6.getPropertyAsBoolean(r2)
            boolean r13 = r1.equals(r2)
            java.util.List r1 = r33.getReversedRanges()
            if (r1 == 0) goto L_0x02d5
            r1 = r13 ^ 1
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            com.itextpdf.io.font.otf.GlyphLine r14 = r6.line
            int r14 = r14.start
        L_0x027b:
            r17 = r3
            com.itextpdf.io.font.otf.GlyphLine r3 = r6.line
            int r3 = r3.end
            if (r14 >= r3) goto L_0x029b
            com.itextpdf.io.font.otf.GlyphLine r3 = r6.line
            com.itextpdf.io.font.otf.Glyph r3 = r3.get(r14)
            boolean r3 = r7.accept(r3)
            if (r3 != 0) goto L_0x0296
            java.lang.Integer r3 = java.lang.Integer.valueOf(r14)
            r2.add(r3)
        L_0x0296:
            int r14 = r14 + 1
            r3 = r17
            goto L_0x027b
        L_0x029b:
            java.util.List r3 = r33.getReversedRanges()
            java.util.Iterator r3 = r3.iterator()
        L_0x02a3:
            boolean r14 = r3.hasNext()
            if (r14 == 0) goto L_0x02b3
            java.lang.Object r14 = r3.next()
            int[] r14 = (int[]) r14
            updateRangeBasedOnRemovedCharacters(r2, r14)
            goto L_0x02a3
        L_0x02b3:
            com.itextpdf.io.font.otf.GlyphLine r3 = r6.line
            com.itextpdf.io.font.otf.GlyphLine r3 = r3.filter(r7)
            r6.line = r3
            if (r1 == 0) goto L_0x02cf
            com.itextpdf.layout.renderer.TextRenderer$ReversedCharsIterator r14 = new com.itextpdf.layout.renderer.TextRenderer$ReversedCharsIterator
            r18 = r1
            java.util.List<int[]> r1 = r6.reversedRanges
            r14.<init>(r1, r3)
            r1 = 1
            com.itextpdf.layout.renderer.TextRenderer$ReversedCharsIterator r1 = r14.setUseReversed(r1)
            r0.showText(r3, r1)
            goto L_0x02d4
        L_0x02cf:
            r18 = r1
            r0.showText((com.itextpdf.p026io.font.otf.GlyphLine) r3)
        L_0x02d4:
            goto L_0x02ee
        L_0x02d5:
            r17 = r3
            if (r13 == 0) goto L_0x02e5
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.line
            int r2 = r1.start
            com.itextpdf.io.font.otf.GlyphLine r3 = r6.line
            int r3 = r3.end
            r14 = 0
            r1.setActualText(r2, r3, r14)
        L_0x02e5:
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.line
            com.itextpdf.io.font.otf.GlyphLine r1 = r1.filter(r7)
            r0.showText((com.itextpdf.p026io.font.otf.GlyphLine) r1)
        L_0x02ee:
            com.itextpdf.io.font.otf.GlyphLine r1 = r6.savedWordBreakAtLineEnding
            if (r1 == 0) goto L_0x02f5
            r0.showText((com.itextpdf.p026io.font.otf.GlyphLine) r1)
        L_0x02f5:
            com.itextpdf.kernel.pdf.canvas.PdfCanvas r1 = r0.endText()
            r1.restoreState()
            r33.endElementOpacityApplying(r34)
            r1 = 74
            java.lang.Object r14 = r6.getProperty(r1)
            boolean r1 = r14 instanceof java.util.List
            r18 = 1046063444(0x3e59a954, float:0.21256)
            if (r1 == 0) goto L_0x035b
            r1 = r14
            java.util.List r1 = (java.util.List) r1
            java.util.Iterator r19 = r1.iterator()
        L_0x0313:
            boolean r1 = r19.hasNext()
            if (r1 == 0) goto L_0x0354
            java.lang.Object r3 = r19.next()
            boolean r1 = r3 instanceof com.itextpdf.layout.property.Underline
            if (r1 == 0) goto L_0x0345
            r1 = r3
            com.itextpdf.layout.property.Underline r1 = (com.itextpdf.layout.property.Underline) r1
            float r28 = r20.getValue()
            if (r25 == 0) goto L_0x032e
            r29 = 1046063444(0x3e59a954, float:0.21256)
            goto L_0x0330
        L_0x032e:
            r29 = 0
        L_0x0330:
            r30 = r0
            r0 = r33
            r2 = r4
            r31 = r3
            r3 = r30
            r32 = r4
            r4 = r28
            r28 = r5
            r5 = r29
            r0.drawSingleUnderline(r1, r2, r3, r4, r5)
            goto L_0x034d
        L_0x0345:
            r30 = r0
            r31 = r3
            r32 = r4
            r28 = r5
        L_0x034d:
            r5 = r28
            r0 = r30
            r4 = r32
            goto L_0x0313
        L_0x0354:
            r30 = r0
            r32 = r4
            r28 = r5
            goto L_0x037c
        L_0x035b:
            r30 = r0
            r32 = r4
            r28 = r5
            boolean r0 = r14 instanceof com.itextpdf.layout.property.Underline
            if (r0 == 0) goto L_0x037c
            r1 = r14
            com.itextpdf.layout.property.Underline r1 = (com.itextpdf.layout.property.Underline) r1
            float r4 = r20.getValue()
            if (r25 == 0) goto L_0x0372
            r5 = 1046063444(0x3e59a954, float:0.21256)
            goto L_0x0373
        L_0x0372:
            r5 = 0
        L_0x0373:
            r0 = r33
            r2 = r32
            r3 = r30
            r0.drawSingleUnderline(r1, r2, r3, r4, r5)
        L_0x037c:
            if (r15 == 0) goto L_0x0381
            r30.closeTag()
        L_0x0381:
            if (r12 == 0) goto L_0x0387
            r0 = 0
            r6.applyRelativePositioningTranslation(r0)
        L_0x0387:
            if (r15 == 0) goto L_0x0395
            if (r10 != 0) goto L_0x0395
            boolean r0 = r6.isLastRendererForModelElement
            if (r0 == 0) goto L_0x0392
            r9.finishTaggingHint(r6)
        L_0x0392:
            r9.restoreAutoTaggingPointerPosition(r6)
        L_0x0395:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.TextRenderer.draw(com.itextpdf.layout.renderer.DrawContext):void");
    }

    public void trimFirst() {
        updateFontAndText();
        if (this.text != null) {
            while (this.text.start < this.text.end) {
                GlyphLine glyphLine = this.text;
                Glyph glyph = glyphLine.get(glyphLine.start);
                Glyph glyph2 = glyph;
                if (!TextUtil.isWhitespace(glyph) || TextUtil.isNewLine(glyph2)) {
                    break;
                }
                this.text.start++;
            }
        }
        if (textContainsSpecialScriptGlyphs(true) && this.specialScriptsWordBreakPoints.get(0).intValue() == this.text.start) {
            if (this.specialScriptsWordBreakPoints.size() == 1) {
                this.specialScriptsWordBreakPoints.set(0, -1);
            } else {
                this.specialScriptsWordBreakPoints.remove(0);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public float trimLast() {
        float trimmedSpace = 0.0f;
        if (this.line.end <= 0) {
            return 0.0f;
        }
        UnitValue fontSize = getPropertyAsUnitValue(24);
        if (!fontSize.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) TextRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 24));
        }
        Float characterSpacing = getPropertyAsFloat(15);
        Float wordSpacing = getPropertyAsFloat(78);
        float hScale = getPropertyAsFloat(29, Float.valueOf(1.0f)).floatValue();
        int firstNonSpaceCharIndex = this.line.end - 1;
        while (firstNonSpaceCharIndex >= this.line.start) {
            Glyph currentGlyph = this.line.get(firstNonSpaceCharIndex);
            if (!TextUtil.isWhitespace(currentGlyph)) {
                break;
            }
            saveWordBreakIfNotYetSaved(currentGlyph);
            float currentCharWidth = getCharWidth(currentGlyph, fontSize.getValue(), Float.valueOf(hScale), characterSpacing, wordSpacing) / TEXT_SPACE_COEFF;
            trimmedSpace += currentCharWidth - (firstNonSpaceCharIndex > this.line.start ? scaleXAdvance((float) this.line.get(firstNonSpaceCharIndex - 1).getXAdvance(), fontSize.getValue(), Float.valueOf(hScale)) / TEXT_SPACE_COEFF : 0.0f);
            this.occupiedArea.getBBox().setWidth(this.occupiedArea.getBBox().getWidth() - currentCharWidth);
            firstNonSpaceCharIndex--;
        }
        this.line.end = firstNonSpaceCharIndex + 1;
        return trimmedSpace;
    }

    public float getAscent() {
        return this.yLineOffset;
    }

    public float getDescent() {
        return -((getOccupiedAreaBBox().getHeight() - this.yLineOffset) - getPropertyAsFloat(72).floatValue());
    }

    public float getYLine() {
        return ((this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight()) - this.yLineOffset) - getPropertyAsFloat(72).floatValue();
    }

    public void moveYLineTo(float y) {
        this.occupiedArea.getBBox().setY(this.occupiedArea.getBBox().getY() + (y - getYLine()));
    }

    public void setText(String text2) {
        this.strToBeConverted = text2;
        updateFontAndText();
    }

    @Deprecated
    public void setText(GlyphLine text2, int leftPos, int rightPos) {
        GlyphLine newText = new GlyphLine(text2);
        newText.start = leftPos;
        newText.end = rightPos;
        PdfFont pdfFont = this.font;
        if (pdfFont != null) {
            newText = TextPreprocessingUtil.replaceSpecialWhitespaceGlyphs(newText, pdfFont);
        }
        setProcessedGlyphLineAndFont(newText, this.font);
    }

    public void setText(GlyphLine text2, PdfFont font2) {
        setProcessedGlyphLineAndFont(TextPreprocessingUtil.replaceSpecialWhitespaceGlyphs(new GlyphLine(text2), font2), font2);
    }

    public GlyphLine getText() {
        updateFontAndText();
        return this.text;
    }

    public int length() {
        GlyphLine glyphLine = this.text;
        if (glyphLine == null) {
            return 0;
        }
        return glyphLine.end - this.text.start;
    }

    public String toString() {
        GlyphLine glyphLine = this.line;
        if (glyphLine != null) {
            return glyphLine.toString();
        }
        return null;
    }

    public int charAt(int pos) {
        GlyphLine glyphLine = this.text;
        return glyphLine.get(glyphLine.start + pos).getUnicode();
    }

    public float getTabAnchorCharacterPosition() {
        return this.tabAnchorCharacterPosition;
    }

    public IRenderer getNextRenderer() {
        return new TextRenderer((Text) this.modelElement);
    }

    public static float[] calculateAscenderDescender(PdfFont font2) {
        return calculateAscenderDescender(font2, RenderingMode.DEFAULT_LAYOUT_MODE);
    }

    public static float[] calculateAscenderDescender(PdfFont font2, RenderingMode mode) {
        float descender;
        float ascender;
        FontMetrics fontMetrics = font2.getFontProgram().getFontMetrics();
        float usedTypoAscenderScaleCoeff = TYPO_ASCENDER_SCALE_COEFF;
        if (RenderingMode.HTML_MODE.equals(mode) && !(font2 instanceof PdfType1Font)) {
            usedTypoAscenderScaleCoeff = 1.0f;
        }
        if (fontMetrics.getWinAscender() == 0 || fontMetrics.getWinDescender() == 0 || (fontMetrics.getTypoAscender() == fontMetrics.getWinAscender() && fontMetrics.getTypoDescender() == fontMetrics.getWinDescender())) {
            ascender = ((float) fontMetrics.getTypoAscender()) * usedTypoAscenderScaleCoeff;
            descender = ((float) fontMetrics.getTypoDescender()) * usedTypoAscenderScaleCoeff;
        } else {
            ascender = (float) fontMetrics.getWinAscender();
            descender = (float) fontMetrics.getWinDescender();
        }
        return new float[]{ascender, descender};
    }

    /* access modifiers changed from: package-private */
    public List<int[]> getReversedRanges() {
        return this.reversedRanges;
    }

    /* access modifiers changed from: package-private */
    public List<int[]> initReversedRanges() {
        if (this.reversedRanges == null) {
            this.reversedRanges = new ArrayList();
        }
        return this.reversedRanges;
    }

    /* access modifiers changed from: package-private */
    public TextRenderer removeReversedRanges() {
        this.reversedRanges = null;
        return this;
    }

    private TextRenderer[] splitIgnoreFirstNewLine(int currentTextPos) {
        if (TextUtil.isCarriageReturnFollowedByLineFeed(this.text, currentTextPos)) {
            return split(currentTextPos + 2);
        }
        return split(currentTextPos + 1);
    }

    private GlyphLine convertToGlyphLine(String text2) {
        return this.font.createGlyphLine(text2);
    }

    private boolean hasOtfFont() {
        PdfFont pdfFont = this.font;
        return (pdfFont instanceof PdfType0Font) && (pdfFont.getFontProgram() instanceof TrueTypeFont);
    }

    /* access modifiers changed from: package-private */
    public boolean textContainsSpecialScriptGlyphs(boolean analyzeSpecialScriptsWordBreakPointsOnly) {
        List<Integer> list = this.specialScriptsWordBreakPoints;
        if (list != null) {
            return !list.isEmpty();
        }
        if (analyzeSpecialScriptsWordBreakPointsOnly) {
            return false;
        }
        for (int i = this.text.start; i < this.text.end; i++) {
            int unicode = this.text.get(i).getUnicode();
            if (unicode <= -1) {
                char[] chars = this.text.get(i).getChars();
                if (chars != null) {
                    for (char ch : chars) {
                        if (codePointIsOfSpecialScript(ch)) {
                            return true;
                        }
                    }
                    continue;
                } else {
                    continue;
                }
            } else if (codePointIsOfSpecialScript(unicode)) {
                return true;
            }
        }
        this.specialScriptsWordBreakPoints = new ArrayList();
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setSpecialScriptsWordBreakPoints(List<Integer> specialScriptsWordBreakPoints2) {
        this.specialScriptsWordBreakPoints = specialScriptsWordBreakPoints2;
    }

    /* access modifiers changed from: package-private */
    public List<Integer> getSpecialScriptsWordBreakPoints() {
        return this.specialScriptsWordBreakPoints;
    }

    /* access modifiers changed from: package-private */
    public void setSpecialScriptFirstNotFittingIndex(int lastFittingIndex) {
        this.specialScriptFirstNotFittingIndex = lastFittingIndex;
    }

    /* access modifiers changed from: protected */
    public Rectangle getBackgroundArea(Rectangle occupiedAreaWithMargins) {
        float textRise = getPropertyAsFloat(72).floatValue();
        return occupiedAreaWithMargins.moveUp(textRise).decreaseHeight(textRise);
    }

    /* access modifiers changed from: protected */
    public Float getFirstYLineRecursively() {
        return Float.valueOf(getYLine());
    }

    /* access modifiers changed from: protected */
    public Float getLastYLineRecursively() {
        return Float.valueOf(getYLine());
    }

    /* access modifiers changed from: protected */
    public int lineLength() {
        if (this.line.end > 0) {
            return this.line.end - this.line.start;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int baseCharactersCount() {
        int count = 0;
        for (int i = this.line.start; i < this.line.end; i++) {
            if (!this.line.get(i).hasPlacement()) {
                count++;
            }
        }
        return count;
    }

    public MinMaxWidth getMinMaxWidth() {
        return ((TextLayoutResult) layout(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0f))))).getMinMaxWidth();
    }

    /* access modifiers changed from: protected */
    public int getNumberOfSpaces() {
        if (this.line.end <= 0) {
            return 0;
        }
        int spaces = 0;
        for (int i = this.line.start; i < this.line.end; i++) {
            if (this.line.get(i).getUnicode() == 32) {
                spaces++;
            }
        }
        return spaces;
    }

    /* access modifiers changed from: protected */
    public TextRenderer createSplitRenderer() {
        return (TextRenderer) getNextRenderer();
    }

    /* access modifiers changed from: protected */
    public TextRenderer createOverflowRenderer() {
        return (TextRenderer) getNextRenderer();
    }

    /* access modifiers changed from: protected */
    public TextRenderer[] split(int initialOverflowTextPos) {
        TextRenderer splitRenderer = createSplitRenderer();
        GlyphLine newText = new GlyphLine(this.text);
        newText.start = this.text.start;
        newText.end = initialOverflowTextPos;
        splitRenderer.setProcessedGlyphLineAndFont(newText, this.font);
        splitRenderer.line = this.line;
        splitRenderer.occupiedArea = this.occupiedArea.clone();
        splitRenderer.parent = this.parent;
        splitRenderer.yLineOffset = this.yLineOffset;
        splitRenderer.otfFeaturesApplied = this.otfFeaturesApplied;
        splitRenderer.isLastRendererForModelElement = false;
        splitRenderer.addAllProperties(getOwnProperties());
        TextRenderer overflowRenderer = createOverflowRenderer();
        GlyphLine newText2 = new GlyphLine(this.text);
        newText2.start = initialOverflowTextPos;
        newText2.end = this.text.end;
        overflowRenderer.setProcessedGlyphLineAndFont(newText2, this.font);
        overflowRenderer.otfFeaturesApplied = this.otfFeaturesApplied;
        overflowRenderer.parent = this.parent;
        overflowRenderer.addAllProperties(getOwnProperties());
        List<Integer> list = this.specialScriptsWordBreakPoints;
        if (list != null) {
            if (list.isEmpty()) {
                splitRenderer.setSpecialScriptsWordBreakPoints(new ArrayList());
                overflowRenderer.setSpecialScriptsWordBreakPoints(new ArrayList());
            } else if (this.specialScriptsWordBreakPoints.get(0).intValue() == -1) {
                List<Integer> split = new ArrayList<>(1);
                split.add(-1);
                splitRenderer.setSpecialScriptsWordBreakPoints(split);
                List<Integer> overflow = new ArrayList<>(1);
                overflow.add(-1);
                overflowRenderer.setSpecialScriptsWordBreakPoints(overflow);
            } else {
                int splitIndex = findPossibleBreaksSplitPosition(this.specialScriptsWordBreakPoints, initialOverflowTextPos, false);
                if (splitIndex > -1) {
                    splitRenderer.setSpecialScriptsWordBreakPoints(this.specialScriptsWordBreakPoints.subList(0, splitIndex + 1));
                } else {
                    List<Integer> split2 = new ArrayList<>(1);
                    split2.add(-1);
                    splitRenderer.setSpecialScriptsWordBreakPoints(split2);
                }
                if (splitIndex + 1 < this.specialScriptsWordBreakPoints.size()) {
                    List<Integer> list2 = this.specialScriptsWordBreakPoints;
                    overflowRenderer.setSpecialScriptsWordBreakPoints(list2.subList(splitIndex + 1, list2.size()));
                } else {
                    List<Integer> split3 = new ArrayList<>(1);
                    split3.add(-1);
                    overflowRenderer.setSpecialScriptsWordBreakPoints(split3);
                }
            }
        }
        return new TextRenderer[]{splitRenderer, overflowRenderer};
    }

    /* access modifiers changed from: protected */
    public void drawSingleUnderline(Underline underline, TransparentColor fontStrokeColor, PdfCanvas canvas, float fontSize, float italicAngleTan) {
        Underline underline2 = underline;
        PdfCanvas pdfCanvas = canvas;
        float f = fontSize;
        TransparentColor underlineColor = underline.getColor() != null ? new TransparentColor(underline.getColor(), underline.getOpacity()) : fontStrokeColor;
        canvas.saveState();
        if (underlineColor != null) {
            pdfCanvas.setStrokeColor(underlineColor.getColor());
            underlineColor.applyStrokeTransparency(pdfCanvas);
        }
        pdfCanvas.setLineCapStyle(underline.getLineCapStyle());
        float underlineThickness = underline.getThickness(f);
        if (underlineThickness != 0.0f) {
            pdfCanvas.setLineWidth(underlineThickness);
            float underlineYPosition = underline.getYPosition(f) + getYLine();
            Rectangle innerAreaBbox = getInnerAreaBBox();
            pdfCanvas.moveTo((double) innerAreaBbox.getX(), (double) underlineYPosition).lineTo((double) ((innerAreaBbox.getX() + innerAreaBbox.getWidth()) - ((0.5f * f) * italicAngleTan)), (double) underlineYPosition).stroke();
        }
        canvas.restoreState();
    }

    /* access modifiers changed from: protected */
    public float calculateLineWidth() {
        UnitValue fontSize = getPropertyAsUnitValue(24);
        if (!fontSize.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) TextRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 24));
        }
        return getGlyphLineWidth(this.line, fontSize.getValue(), getPropertyAsFloat(29, Float.valueOf(1.0f)).floatValue(), getPropertyAsFloat(15), getPropertyAsFloat(78));
    }

    /* access modifiers changed from: protected */
    public boolean resolveFonts(List<IRenderer> addTo) {
        Object font2 = getProperty(20);
        if (font2 instanceof PdfFont) {
            addTo.add(this);
            return false;
        } else if ((font2 instanceof String) || (font2 instanceof String[])) {
            boolean z = font2 instanceof String;
            Object font3 = font2;
            if (z) {
                LoggerFactory.getLogger((Class<?>) AbstractRenderer.class).warn(LogMessageConstant.f1190x1cf9924b);
                List<String> splitFontFamily = FontFamilySplitter.splitFontFamily((String) font2);
                font3 = splitFontFamily.toArray(new String[splitFontFamily.size()]);
            }
            FontProvider provider = (FontProvider) getProperty(91);
            FontSet fontSet = (FontSet) getProperty(98);
            if (!provider.getFontSet().isEmpty() || (fontSet != null && !fontSet.isEmpty())) {
                FontSelectorStrategy strategy = provider.getStrategy(this.strToBeConverted, Arrays.asList((String[]) font3), createFontCharacteristics(), fontSet);
                String str = this.strToBeConverted;
                if (str == null || str.isEmpty()) {
                    addTo.add(this);
                    return true;
                }
                while (!strategy.endOfText()) {
                    GlyphLine nextGlyphs = new GlyphLine(strategy.nextGlyphs());
                    PdfFont currentFont = strategy.getCurrentFont();
                    addTo.add(createCopy(TextPreprocessingUtil.replaceSpecialWhitespaceGlyphs(nextGlyphs, currentFont), currentFont));
                }
                return true;
            }
            throw new IllegalStateException(PdfException.FontProviderNotSetFontFamilyNotResolved);
        } else {
            throw new IllegalStateException("Invalid FONT property value type.");
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void setGlyphLineAndFont(GlyphLine gl, PdfFont font2) {
        setProcessedGlyphLineAndFont(gl, font2);
    }

    /* access modifiers changed from: protected */
    public void setProcessedGlyphLineAndFont(GlyphLine gl, PdfFont font2) {
        this.text = gl;
        this.font = font2;
        this.otfFeaturesApplied = false;
        this.strToBeConverted = null;
        this.specialScriptsWordBreakPoints = null;
        setProperty(20, font2);
    }

    /* access modifiers changed from: protected */
    public TextRenderer createCopy(GlyphLine gl, PdfFont font2) {
        TextRenderer copy = new TextRenderer(this);
        copy.setProcessedGlyphLineAndFont(gl, font2);
        return copy;
    }

    static void updateRangeBasedOnRemovedCharacters(ArrayList<Integer> removedIds, int[] range) {
        range[0] = range[0] - numberOfElementsLessThan(removedIds, range[0]);
        range[1] = range[1] - numberOfElementsLessThanOrEqual(removedIds, range[1]);
    }

    /* access modifiers changed from: package-private */
    public PdfFont resolveFirstPdfFont(String[] font2, FontProvider provider, FontCharacteristics fc, FontSet additionalFonts) {
        FontSelectorStrategy strategy = provider.getStrategy(this.strToBeConverted, Arrays.asList(font2), fc, additionalFonts);
        while (!strategy.endOfText()) {
            List<Glyph> resolvedGlyphs = strategy.nextGlyphs();
            PdfFont currentFont = strategy.getCurrentFont();
            Iterator<Glyph> it = resolvedGlyphs.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (currentFont.containsGlyph(it.next().getUnicode())) {
                        return currentFont;
                    }
                }
            }
        }
        return super.resolveFirstPdfFont(font2, provider, fc, additionalFonts);
    }

    private static int numberOfElementsLessThan(ArrayList<Integer> numbers, int n) {
        int x = Collections.binarySearch(numbers, Integer.valueOf(n));
        if (x >= 0) {
            return x;
        }
        return (-x) - 1;
    }

    private static int numberOfElementsLessThanOrEqual(ArrayList<Integer> numbers, int n) {
        int x = Collections.binarySearch(numbers, Integer.valueOf(n));
        if (x >= 0) {
            return x + 1;
        }
        return (-x) - 1;
    }

    /* access modifiers changed from: private */
    public static boolean noPrint(Glyph g) {
        if (!g.hasValidUnicode()) {
            return false;
        }
        return TextUtil.isNonPrintable(g.getUnicode());
    }

    private static boolean glyphBelongsToNonBreakingHyphenRelatedChunk(GlyphLine text2, int ind) {
        return TextUtil.isNonBreakingHyphen(text2.get(ind)) || (ind + 1 < text2.end && TextUtil.isNonBreakingHyphen(text2.get(ind + 1))) || (ind + -1 >= text2.start && TextUtil.isNonBreakingHyphen(text2.get(ind + -1)));
    }

    private float getCharWidth(Glyph g, float fontSize, Float hScale, Float characterSpacing, Float wordSpacing) {
        if (hScale == null) {
            hScale = Float.valueOf(1.0f);
        }
        float resultWidth = ((float) g.getWidth()) * fontSize * hScale.floatValue();
        if (characterSpacing != null) {
            resultWidth += characterSpacing.floatValue() * hScale.floatValue() * TEXT_SPACE_COEFF;
        }
        if (wordSpacing == null || g.getUnicode() != 32) {
            return resultWidth;
        }
        return resultWidth + (wordSpacing.floatValue() * hScale.floatValue() * TEXT_SPACE_COEFF);
    }

    private float scaleXAdvance(float xAdvance, float fontSize, Float hScale) {
        return xAdvance * fontSize * hScale.floatValue();
    }

    private float getGlyphLineWidth(GlyphLine glyphLine, float fontSize, float hScale, Float characterSpacing, Float wordSpacing) {
        float width = 0.0f;
        int i = glyphLine.start;
        while (i < glyphLine.end) {
            if (!noPrint(glyphLine.get(i))) {
                width = width + getCharWidth(glyphLine.get(i), fontSize, Float.valueOf(hScale), characterSpacing, wordSpacing) + (i != glyphLine.start ? scaleXAdvance((float) glyphLine.get(i - 1).getXAdvance(), fontSize, Float.valueOf(hScale)) : 0.0f);
            }
            i++;
        }
        return width / TEXT_SPACE_COEFF;
    }

    private int[] getWordBoundsForHyphenation(GlyphLine text2, int leftTextPos, int rightTextPos, int wordMiddleCharPos) {
        while (wordMiddleCharPos >= leftTextPos && !isGlyphPartOfWordForHyphenation(text2.get(wordMiddleCharPos)) && !TextUtil.isUni0020(text2.get(wordMiddleCharPos))) {
            wordMiddleCharPos--;
        }
        if (wordMiddleCharPos < leftTextPos) {
            return null;
        }
        int left = wordMiddleCharPos;
        while (left >= leftTextPos && isGlyphPartOfWordForHyphenation(text2.get(left))) {
            left--;
        }
        int right = wordMiddleCharPos;
        while (right < rightTextPos && isGlyphPartOfWordForHyphenation(text2.get(right))) {
            right++;
        }
        return new int[]{left + 1, right};
    }

    private boolean isGlyphPartOfWordForHyphenation(Glyph g) {
        return Character.isLetter((char) g.getUnicode()) || 173 == g.getUnicode();
    }

    private void updateFontAndText() {
        PdfFont newFont;
        if (this.strToBeConverted != null) {
            try {
                newFont = getPropertyAsFont(20);
            } catch (ClassCastException e) {
                PdfFont newFont2 = resolveFirstPdfFont();
                if (!this.strToBeConverted.isEmpty()) {
                    LoggerFactory.getLogger((Class<?>) TextRenderer.class).error(LogMessageConstant.FONT_PROPERTY_MUST_BE_PDF_FONT_OBJECT);
                }
                newFont = newFont2;
            }
            setProcessedGlyphLineAndFont(TextPreprocessingUtil.replaceSpecialWhitespaceGlyphs(newFont.createGlyphLine(this.strToBeConverted), newFont), newFont);
        }
    }

    private void saveWordBreakIfNotYetSaved(Glyph wordBreak) {
        if (this.savedWordBreakAtLineEnding == null) {
            if (TextUtil.isNewLine(wordBreak)) {
                wordBreak = this.font.getGlyph(32);
            }
            this.savedWordBreakAtLineEnding = new GlyphLine((List<Glyph>) Collections.singletonList(wordBreak));
        }
    }

    static int findPossibleBreaksSplitPosition(List<Integer> list, int textStartBasedInitialOverflowTextPos, boolean amongPresentOnly) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int middle = (low + high) >>> 1;
            if (list.get(middle).compareTo(Integer.valueOf(textStartBasedInitialOverflowTextPos)) < 0) {
                low = middle + 1;
            } else if (list.get(middle).compareTo(Integer.valueOf(textStartBasedInitialOverflowTextPos)) <= 0) {
                return middle;
            } else {
                high = middle - 1;
            }
        }
        if (amongPresentOnly || low <= 0) {
            return -1;
        }
        return low - 1;
    }

    static boolean codePointIsOfSpecialScript(int codePoint) {
        Character.UnicodeScript glyphScript = Character.UnicodeScript.of(codePoint);
        return Character.UnicodeScript.THAI == glyphScript || Character.UnicodeScript.KHMER == glyphScript || Character.UnicodeScript.LAO == glyphScript || Character.UnicodeScript.MYANMAR == glyphScript;
    }

    private static class ReversedCharsIterator implements Iterator<GlyphLine.GlyphLinePart> {
        private int currentInd = 0;
        private List<Integer> outEnd = new ArrayList();
        private List<Integer> outStart = new ArrayList();
        private List<Boolean> reversed = new ArrayList();
        private boolean useReversed;

        public ReversedCharsIterator(List<int[]> reversedRange, GlyphLine line) {
            if (reversedRange != null) {
                if (reversedRange.get(0)[0] > 0) {
                    this.outStart.add(0);
                    this.outEnd.add(Integer.valueOf(reversedRange.get(0)[0]));
                    this.reversed.add(false);
                }
                for (int i = 0; i < reversedRange.size(); i++) {
                    int[] range = reversedRange.get(i);
                    this.outStart.add(Integer.valueOf(range[0]));
                    this.outEnd.add(Integer.valueOf(range[1] + 1));
                    this.reversed.add(true);
                    if (i != reversedRange.size() - 1) {
                        this.outStart.add(Integer.valueOf(range[1] + 1));
                        this.outEnd.add(Integer.valueOf(reversedRange.get(i + 1)[0]));
                        this.reversed.add(false);
                    }
                }
                int lastIndex = reversedRange.get(reversedRange.size() - 1)[1];
                if (lastIndex < line.size() - 1) {
                    this.outStart.add(Integer.valueOf(lastIndex + 1));
                    this.outEnd.add(Integer.valueOf(line.size()));
                    this.reversed.add(false);
                    return;
                }
                return;
            }
            this.outStart.add(Integer.valueOf(line.start));
            this.outEnd.add(Integer.valueOf(line.end));
            this.reversed.add(false);
        }

        public ReversedCharsIterator setUseReversed(boolean useReversed2) {
            this.useReversed = useReversed2;
            return this;
        }

        public boolean hasNext() {
            return this.currentInd < this.outStart.size();
        }

        public GlyphLine.GlyphLinePart next() {
            GlyphLine.GlyphLinePart part = new GlyphLine.GlyphLinePart(this.outStart.get(this.currentInd).intValue(), this.outEnd.get(this.currentInd).intValue()).setReversed(this.useReversed && this.reversed.get(this.currentInd).booleanValue());
            this.currentInd++;
            return part;
        }

        public void remove() {
            throw new IllegalStateException("Operation not supported");
        }
    }

    private static class ScriptRange {
        int rangeEnd;
        Character.UnicodeScript script;

        ScriptRange(Character.UnicodeScript script2, int rangeEnd2) {
            this.script = script2;
            this.rangeEnd = rangeEnd2;
        }
    }
}
