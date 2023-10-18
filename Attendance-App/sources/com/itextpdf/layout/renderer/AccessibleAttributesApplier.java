package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNull;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.tagging.PdfNamespace;
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.IRoleMappingResolver;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.IListSymbolFactory;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import org.slf4j.LoggerFactory;

public class AccessibleAttributesApplier {
    public static PdfStructureAttributes getLayoutAttributes(AbstractRenderer renderer, TagTreePointer taggingPointer) {
        IRoleMappingResolver resolvedMapping = resolveMappingToStandard(taggingPointer);
        if (resolvedMapping == null) {
            return null;
        }
        String role = resolvedMapping.getRole();
        int tagType = AccessibleTypes.identifyType(role);
        PdfDictionary attributes = new PdfDictionary();
        attributes.put(PdfName.f1361O, PdfName.Layout);
        applyCommonLayoutAttributes(renderer, attributes);
        if (tagType == AccessibleTypes.BlockLevel) {
            applyBlockLevelLayoutAttributes(role, renderer, attributes);
        }
        if (tagType == AccessibleTypes.InlineLevel) {
            applyInlineLevelLayoutAttributes(renderer, attributes);
        }
        if (tagType == AccessibleTypes.Illustration) {
            applyIllustrationLayoutAttributes(renderer, attributes);
        }
        if (attributes.size() > 1) {
            return new PdfStructureAttributes(attributes);
        }
        return null;
    }

    public static PdfStructureAttributes getListAttributes(AbstractRenderer renderer, TagTreePointer taggingPointer) {
        IRoleMappingResolver resolvedMapping = resolveMappingToStandard(taggingPointer);
        if (resolvedMapping == null || !"L".equals(resolvedMapping.getRole())) {
            return null;
        }
        PdfDictionary attributes = new PdfDictionary();
        attributes.put(PdfName.f1361O, PdfName.List);
        Object listSymbol = renderer.getProperty(37);
        boolean tagStructurePdf2 = isTagStructurePdf2(resolvedMapping.getNamespace());
        if (listSymbol instanceof ListNumberingType) {
            attributes.put(PdfName.ListNumbering, transformNumberingTypeToName((ListNumberingType) listSymbol, tagStructurePdf2));
        } else if (tagStructurePdf2) {
            if (listSymbol instanceof IListSymbolFactory) {
                attributes.put(PdfName.ListNumbering, PdfName.Ordered);
            } else {
                attributes.put(PdfName.ListNumbering, PdfName.Unordered);
            }
        }
        if (attributes.size() > 1) {
            return new PdfStructureAttributes(attributes);
        }
        return null;
    }

    public static PdfStructureAttributes getTableAttributes(AbstractRenderer renderer, TagTreePointer taggingPointer) {
        IRoleMappingResolver resolvedMapping = resolveMappingToStandard(taggingPointer);
        if (resolvedMapping == null || (!StandardRoles.f1515TD.equals(resolvedMapping.getRole()) && !StandardRoles.f1516TH.equals(resolvedMapping.getRole()))) {
            return null;
        }
        PdfDictionary attributes = new PdfDictionary();
        attributes.put(PdfName.f1361O, PdfName.Table);
        if (renderer.getModelElement() instanceof Cell) {
            Cell cell = (Cell) renderer.getModelElement();
            if (cell.getRowspan() != 1) {
                attributes.put(PdfName.RowSpan, new PdfNumber(cell.getRowspan()));
            }
            if (cell.getColspan() != 1) {
                attributes.put(PdfName.ColSpan, new PdfNumber(cell.getColspan()));
            }
        }
        if (attributes.size() > 1) {
            return new PdfStructureAttributes(attributes);
        }
        return null;
    }

    private static void applyCommonLayoutAttributes(AbstractRenderer renderer, PdfDictionary attributes) {
        Background background = (Background) renderer.getProperty(6);
        if (background != null && (background.getColor() instanceof DeviceRgb)) {
            attributes.put(PdfName.BackgroundColor, new PdfArray(background.getColor().getColorValue()));
        }
        if (!(renderer.getModelElement() instanceof Cell)) {
            applyBorderAttributes(renderer, attributes);
        }
        applyPaddingAttribute(renderer, attributes);
        TransparentColor transparentColor = renderer.getPropertyAsTransparentColor(21);
        if (transparentColor != null && (transparentColor.getColor() instanceof DeviceRgb)) {
            attributes.put(PdfName.Color, new PdfArray(transparentColor.getColor().getColorValue()));
        }
    }

    private static void applyBlockLevelLayoutAttributes(String role, AbstractRenderer renderer, PdfDictionary attributes) {
        String str = role;
        AbstractRenderer abstractRenderer = renderer;
        PdfDictionary pdfDictionary = attributes;
        UnitValue[] margins = {abstractRenderer.getPropertyAsUnitValue(46), abstractRenderer.getPropertyAsUnitValue(43), abstractRenderer.getPropertyAsUnitValue(44), abstractRenderer.getPropertyAsUnitValue(45)};
        int[] marginsOrder = {0, 1, 2, 3};
        UnitValue spaceBefore = margins[marginsOrder[0]];
        Class<AccessibleAttributesApplier> cls = AccessibleAttributesApplier.class;
        if (spaceBefore != null) {
            if (!spaceBefore.isPointValue()) {
                LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 46));
            }
            if (0.0f != spaceBefore.getValue()) {
                UnitValue unitValue = spaceBefore;
                pdfDictionary.put(PdfName.SpaceBefore, new PdfNumber((double) spaceBefore.getValue()));
            }
        }
        UnitValue spaceAfter = margins[marginsOrder[1]];
        if (spaceAfter != null) {
            if (!spaceAfter.isPointValue()) {
                LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 43));
            }
            if (0.0f != spaceAfter.getValue()) {
                pdfDictionary.put(PdfName.SpaceAfter, new PdfNumber((double) spaceAfter.getValue()));
            }
        }
        UnitValue startIndent = margins[marginsOrder[2]];
        if (startIndent != null) {
            if (!startIndent.isPointValue()) {
                LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
            }
            if (0.0f != startIndent.getValue()) {
                pdfDictionary.put(PdfName.StartIndent, new PdfNumber((double) startIndent.getValue()));
            }
        }
        UnitValue endIndent = margins[marginsOrder[3]];
        if (endIndent != null) {
            if (!endIndent.isPointValue()) {
                LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 45));
            }
            if (0.0f != endIndent.getValue()) {
                pdfDictionary.put(PdfName.EndIndent, new PdfNumber((double) endIndent.getValue()));
            }
        }
        Float firstLineIndent = abstractRenderer.getPropertyAsFloat(18);
        if (!(firstLineIndent == null || firstLineIndent.floatValue() == 0.0f)) {
            pdfDictionary.put(PdfName.TextIndent, new PdfNumber((double) firstLineIndent.floatValue()));
        }
        TextAlignment textAlignment = (TextAlignment) abstractRenderer.getProperty(70);
        if (textAlignment != null && !StandardRoles.f1516TH.equals(str) && !StandardRoles.f1515TD.equals(str)) {
            pdfDictionary.put(PdfName.TextAlign, transformTextAlignmentValueToName(textAlignment));
        }
        if (abstractRenderer.isLastRendererForModelElement) {
            pdfDictionary.put(PdfName.BBox, new PdfArray(renderer.getOccupiedArea().getBBox()));
        }
        if (StandardRoles.f1516TH.equals(str) || StandardRoles.f1515TD.equals(str) || StandardRoles.TABLE.equals(str)) {
            if (!(abstractRenderer instanceof TableRenderer) || ((Table) renderer.getModelElement()).isComplete()) {
                UnitValue width = (UnitValue) abstractRenderer.getProperty(77);
                if (width == null || !width.isPointValue()) {
                    UnitValue[] unitValueArr = margins;
                } else {
                    int[] iArr = marginsOrder;
                    UnitValue[] unitValueArr2 = margins;
                    pdfDictionary.put(PdfName.Width, new PdfNumber((double) width.getValue()));
                }
            } else {
                int[] iArr2 = marginsOrder;
                UnitValue[] unitValueArr3 = margins;
            }
            UnitValue height = (UnitValue) abstractRenderer.getProperty(27);
            if (height != null && height.isPointValue()) {
                pdfDictionary.put(PdfName.Height, new PdfNumber((double) height.getValue()));
            }
        } else {
            int[] iArr3 = marginsOrder;
            UnitValue[] unitValueArr4 = margins;
        }
        if (StandardRoles.f1516TH.equals(str) || StandardRoles.f1515TD.equals(str)) {
            HorizontalAlignment horizontalAlignment = (HorizontalAlignment) abstractRenderer.getProperty(28);
            if (horizontalAlignment != null) {
                pdfDictionary.put(PdfName.BlockAlign, transformBlockAlignToName(horizontalAlignment));
            }
            if (textAlignment != null && textAlignment != TextAlignment.JUSTIFIED && textAlignment != TextAlignment.JUSTIFIED_ALL) {
                pdfDictionary.put(PdfName.InlineAlign, transformTextAlignmentValueToName(textAlignment));
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: com.itextpdf.layout.property.Underline} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void applyInlineLevelLayoutAttributes(com.itextpdf.layout.renderer.AbstractRenderer r8, com.itextpdf.kernel.pdf.PdfDictionary r9) {
        /*
            r0 = 72
            java.lang.Float r0 = r8.getPropertyAsFloat(r0)
            r1 = 0
            if (r0 == 0) goto L_0x0020
            float r2 = r0.floatValue()
            int r2 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r2 == 0) goto L_0x0020
            com.itextpdf.kernel.pdf.PdfName r2 = com.itextpdf.kernel.pdf.PdfName.BaselineShift
            com.itextpdf.kernel.pdf.PdfNumber r3 = new com.itextpdf.kernel.pdf.PdfNumber
            float r4 = r0.floatValue()
            double r4 = (double) r4
            r3.<init>((double) r4)
            r9.put(r2, r3)
        L_0x0020:
            r2 = 74
            java.lang.Object r2 = r8.getProperty(r2)
            if (r2 == 0) goto L_0x00bd
            r3 = 24
            com.itextpdf.layout.property.UnitValue r4 = r8.getPropertyAsUnitValue(r3)
            boolean r5 = r4.isPointValue()
            r6 = 0
            if (r5 != 0) goto L_0x004d
            java.lang.Class<com.itextpdf.layout.renderer.AccessibleAttributesApplier> r5 = com.itextpdf.layout.renderer.AccessibleAttributesApplier.class
            org.slf4j.Logger r5 = org.slf4j.LoggerFactory.getLogger((java.lang.Class<?>) r5)
            r7 = 1
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r7[r6] = r3
            java.lang.String r3 = "Property {0} in percents is not supported"
            java.lang.String r3 = com.itextpdf.p026io.util.MessageFormatUtil.format(r3, r7)
            r5.error(r3)
        L_0x004d:
            r3 = 0
            boolean r5 = r2 instanceof java.util.List
            if (r5 == 0) goto L_0x0071
            r5 = r2
            java.util.List r5 = (java.util.List) r5
            int r5 = r5.size()
            if (r5 <= 0) goto L_0x0071
            r5 = r2
            java.util.List r5 = (java.util.List) r5
            java.lang.Object r5 = r5.get(r6)
            boolean r5 = r5 instanceof com.itextpdf.layout.property.Underline
            if (r5 == 0) goto L_0x0071
            r5 = r2
            java.util.List r5 = (java.util.List) r5
            java.lang.Object r5 = r5.get(r6)
            r3 = r5
            com.itextpdf.layout.property.Underline r3 = (com.itextpdf.layout.property.Underline) r3
            goto L_0x0078
        L_0x0071:
            boolean r5 = r2 instanceof com.itextpdf.layout.property.Underline
            if (r5 == 0) goto L_0x0078
            r3 = r2
            com.itextpdf.layout.property.Underline r3 = (com.itextpdf.layout.property.Underline) r3
        L_0x0078:
            if (r3 == 0) goto L_0x00bd
            com.itextpdf.kernel.pdf.PdfName r5 = com.itextpdf.kernel.pdf.PdfName.TextDecorationType
            float r6 = r4.getValue()
            float r6 = r3.getYPosition(r6)
            int r1 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x008b
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.LineThrough
            goto L_0x008d
        L_0x008b:
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.Underline
        L_0x008d:
            r9.put(r5, r1)
            com.itextpdf.kernel.colors.Color r1 = r3.getColor()
            boolean r1 = r1 instanceof com.itextpdf.kernel.colors.DeviceRgb
            if (r1 == 0) goto L_0x00aa
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.TextDecorationColor
            com.itextpdf.kernel.pdf.PdfArray r5 = new com.itextpdf.kernel.pdf.PdfArray
            com.itextpdf.kernel.colors.Color r6 = r3.getColor()
            float[] r6 = r6.getColorValue()
            r5.<init>((float[]) r6)
            r9.put(r1, r5)
        L_0x00aa:
            com.itextpdf.kernel.pdf.PdfName r1 = com.itextpdf.kernel.pdf.PdfName.TextDecorationThickness
            com.itextpdf.kernel.pdf.PdfNumber r5 = new com.itextpdf.kernel.pdf.PdfNumber
            float r6 = r4.getValue()
            float r6 = r3.getThickness(r6)
            double r6 = (double) r6
            r5.<init>((double) r6)
            r9.put(r1, r5)
        L_0x00bd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.layout.renderer.AccessibleAttributesApplier.applyInlineLevelLayoutAttributes(com.itextpdf.layout.renderer.AbstractRenderer, com.itextpdf.kernel.pdf.PdfDictionary):void");
    }

    private static void applyIllustrationLayoutAttributes(AbstractRenderer renderer, PdfDictionary attributes) {
        Rectangle bbox = renderer.getOccupiedArea().getBBox();
        attributes.put(PdfName.BBox, new PdfArray(bbox));
        UnitValue width = (UnitValue) renderer.getProperty(77);
        if (width == null || !width.isPointValue()) {
            attributes.put(PdfName.Width, new PdfNumber((double) bbox.getWidth()));
        } else {
            attributes.put(PdfName.Width, new PdfNumber((double) width.getValue()));
        }
        UnitValue height = (UnitValue) renderer.getProperty(27);
        if (height != null) {
            attributes.put(PdfName.Height, new PdfNumber((double) height.getValue()));
        } else {
            attributes.put(PdfName.Height, new PdfNumber((double) bbox.getHeight()));
        }
    }

    private static void applyPaddingAttribute(AbstractRenderer renderer, PdfDictionary attributes) {
        UnitValue[] paddingsUV = {renderer.getPropertyAsUnitValue(50), renderer.getPropertyAsUnitValue(49), renderer.getPropertyAsUnitValue(47), renderer.getPropertyAsUnitValue(48)};
        Class<AccessibleAttributesApplier> cls = AccessibleAttributesApplier.class;
        if (!paddingsUV[0].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 50));
        }
        if (!paddingsUV[1].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 49));
        }
        if (!paddingsUV[2].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 47));
        }
        if (!paddingsUV[3].isPointValue()) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 48));
        }
        float[] paddings = {paddingsUV[0].getValue(), paddingsUV[1].getValue(), paddingsUV[2].getValue(), paddingsUV[3].getValue()};
        PdfObject padding = null;
        if (paddings[0] != paddings[1] || paddings[0] != paddings[2] || paddings[0] != paddings[3]) {
            PdfArray paddingArray = new PdfArray();
            for (int i : new int[]{0, 1, 2, 3}) {
                paddingArray.add(new PdfNumber((double) paddings[i]));
            }
            padding = paddingArray;
        } else if (paddings[0] != 0.0f) {
            padding = new PdfNumber((double) paddings[0]);
        }
        if (padding != null) {
            attributes.put(PdfName.Padding, padding);
        }
    }

    private static void applyBorderAttributes(AbstractRenderer renderer, PdfDictionary attributes) {
        int i;
        boolean specificBorderProperties;
        boolean generalBorderProperties;
        AbstractRenderer abstractRenderer = renderer;
        PdfDictionary pdfDictionary = attributes;
        boolean generalBorderProperties2 = true;
        char c = 0;
        boolean specificBorderProperties2 = (abstractRenderer.getProperty(13) == null && abstractRenderer.getProperty(12) == null && abstractRenderer.getProperty(10) == null && abstractRenderer.getProperty(11) == null) ? false : true;
        if (specificBorderProperties2 || abstractRenderer.getProperty(9) == null) {
            generalBorderProperties2 = false;
        }
        if (generalBorderProperties2) {
            Border generalBorder = (Border) abstractRenderer.getProperty(9);
            Color generalBorderColor = generalBorder.getColor();
            int borderType = generalBorder.getType();
            float borderWidth = generalBorder.getWidth();
            if (generalBorderColor instanceof DeviceRgb) {
                pdfDictionary.put(PdfName.BorderColor, new PdfArray(generalBorderColor.getColorValue()));
                pdfDictionary.put(PdfName.BorderStyle, transformBorderTypeToName(borderType));
                pdfDictionary.put(PdfName.BorderThickness, new PdfNumber((double) borderWidth));
            }
        }
        if (specificBorderProperties2) {
            PdfArray borderColors = new PdfArray();
            PdfArray borderTypes = new PdfArray();
            PdfArray borderWidths = new PdfArray();
            boolean atLeastOneRgb = false;
            Border[] borders = renderer.getBorders();
            boolean allColorsEqual = true;
            boolean allTypesEqual = true;
            boolean allWidthsEqual = true;
            int i2 = 1;
            while (i2 < borders.length) {
                Border border = borders[i2];
                if (border != null) {
                    if (borders[c] == null || !border.getColor().equals(borders[c].getColor())) {
                        allColorsEqual = false;
                    }
                    if (borders[0] == null || border.getWidth() != borders[0].getWidth()) {
                        allWidthsEqual = false;
                    }
                    if (borders[0] == null || border.getType() != borders[0].getType()) {
                        allTypesEqual = false;
                    }
                }
                i2++;
                c = 0;
            }
            int[] borderOrder = {0, 1, 2, 3};
            int length = borderOrder.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = borderOrder[i3];
                if (borders[i4] != null) {
                    if (borders[i4].getColor() instanceof DeviceRgb) {
                        specificBorderProperties = specificBorderProperties2;
                        borderColors.add(new PdfArray(borders[i4].getColor().getColorValue()));
                        atLeastOneRgb = true;
                    } else {
                        specificBorderProperties = specificBorderProperties2;
                        borderColors.add(PdfNull.PDF_NULL);
                    }
                    borderTypes.add(transformBorderTypeToName(borders[i4].getType()));
                    generalBorderProperties = generalBorderProperties2;
                    borderWidths.add(new PdfNumber((double) borders[i4].getWidth()));
                } else {
                    specificBorderProperties = specificBorderProperties2;
                    generalBorderProperties = generalBorderProperties2;
                    borderColors.add(PdfNull.PDF_NULL);
                    borderTypes.add(PdfName.None);
                    borderWidths.add(PdfNull.PDF_NULL);
                }
                i3++;
                AbstractRenderer abstractRenderer2 = renderer;
                generalBorderProperties2 = generalBorderProperties;
                specificBorderProperties2 = specificBorderProperties;
            }
            boolean z = generalBorderProperties2;
            if (atLeastOneRgb) {
                if (allColorsEqual) {
                    pdfDictionary.put(PdfName.BorderColor, borderColors.get(0));
                } else {
                    pdfDictionary.put(PdfName.BorderColor, borderColors);
                }
            }
            if (allTypesEqual) {
                i = 0;
                pdfDictionary.put(PdfName.BorderStyle, borderTypes.get(0));
            } else {
                i = 0;
                pdfDictionary.put(PdfName.BorderStyle, borderTypes);
            }
            if (allWidthsEqual) {
                pdfDictionary.put(PdfName.BorderThickness, borderWidths.get(i));
            } else {
                pdfDictionary.put(PdfName.BorderThickness, borderWidths);
            }
        } else {
            boolean z2 = generalBorderProperties2;
        }
    }

    private static IRoleMappingResolver resolveMappingToStandard(TagTreePointer taggingPointer) {
        return taggingPointer.getDocument().getTagStructureContext().resolveMappingToStandardOrDomainSpecificRole(taggingPointer.getRole(), taggingPointer.getProperties().getNamespace());
    }

    private static boolean isTagStructurePdf2(PdfNamespace namespace) {
        return namespace != null && StandardNamespaces.PDF_2_0.equals(namespace.getNamespaceName());
    }

    private static PdfName transformTextAlignmentValueToName(TextAlignment textAlignment) {
        switch (C14691.$SwitchMap$com$itextpdf$layout$property$TextAlignment[textAlignment.ordinal()]) {
            case 1:
                if (1 != 0) {
                    return PdfName.Start;
                }
                return PdfName.End;
            case 2:
                return PdfName.Center;
            case 3:
                if (1 != 0) {
                    return PdfName.End;
                }
                return PdfName.Start;
            case 4:
            case 5:
                return PdfName.Justify;
            default:
                return PdfName.Start;
        }
    }

    private static PdfName transformBlockAlignToName(HorizontalAlignment horizontalAlignment) {
        switch (C14691.$SwitchMap$com$itextpdf$layout$property$HorizontalAlignment[horizontalAlignment.ordinal()]) {
            case 1:
                if (1 != 0) {
                    return PdfName.Before;
                }
                return PdfName.After;
            case 2:
                return PdfName.Middle;
            case 3:
                if (1 != 0) {
                    return PdfName.After;
                }
                return PdfName.Before;
            default:
                return PdfName.Before;
        }
    }

    private static PdfName transformBorderTypeToName(int borderType) {
        switch (borderType) {
            case 0:
                return PdfName.Solid;
            case 1:
                return PdfName.Dashed;
            case 2:
                return PdfName.Dotted;
            case 3:
                return PdfName.Double;
            case 4:
                return PdfName.Dotted;
            case 5:
                return PdfName.Groove;
            case 6:
                return PdfName.Inset;
            case 7:
                return PdfName.Outset;
            case 8:
                return PdfName.Ridge;
            default:
                return PdfName.Solid;
        }
    }

    /* renamed from: com.itextpdf.layout.renderer.AccessibleAttributesApplier$1 */
    static /* synthetic */ class C14691 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$HorizontalAlignment;
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$ListNumberingType;
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$TextAlignment;

        static {
            int[] iArr = new int[ListNumberingType.values().length];
            $SwitchMap$com$itextpdf$layout$property$ListNumberingType = iArr;
            try {
                iArr[ListNumberingType.DECIMAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.DECIMAL_LEADING_ZERO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ROMAN_UPPER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ROMAN_LOWER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ENGLISH_UPPER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.GREEK_UPPER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ENGLISH_LOWER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.GREEK_LOWER.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            int[] iArr2 = new int[HorizontalAlignment.values().length];
            $SwitchMap$com$itextpdf$layout$property$HorizontalAlignment = iArr2;
            try {
                iArr2[HorizontalAlignment.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$HorizontalAlignment[HorizontalAlignment.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$HorizontalAlignment[HorizontalAlignment.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e11) {
            }
            int[] iArr3 = new int[TextAlignment.values().length];
            $SwitchMap$com$itextpdf$layout$property$TextAlignment = iArr3;
            try {
                iArr3[TextAlignment.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$TextAlignment[TextAlignment.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$TextAlignment[TextAlignment.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$TextAlignment[TextAlignment.JUSTIFIED.ordinal()] = 4;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$TextAlignment[TextAlignment.JUSTIFIED_ALL.ordinal()] = 5;
            } catch (NoSuchFieldError e16) {
            }
        }
    }

    private static PdfName transformNumberingTypeToName(ListNumberingType numberingType, boolean isTagStructurePdf2) {
        switch (C14691.$SwitchMap$com$itextpdf$layout$property$ListNumberingType[numberingType.ordinal()]) {
            case 1:
            case 2:
                return PdfName.Decimal;
            case 3:
                return PdfName.UpperRoman;
            case 4:
                return PdfName.LowerRoman;
            case 5:
            case 6:
                return PdfName.UpperAlpha;
            case 7:
            case 8:
                return PdfName.LowerAlpha;
            default:
                if (isTagStructurePdf2) {
                    return PdfName.Ordered;
                }
                return PdfName.None;
        }
    }
}
