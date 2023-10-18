package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.numbering.EnglishAlphabetNumbering;
import com.itextpdf.kernel.numbering.GreekAlphabetNumbering;
import com.itextpdf.kernel.numbering.RomanNumbering;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.IListSymbolFactory;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.ListSymbolPosition;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.tagging.LayoutTaggingHelper;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.TextUtil;
import java.io.IOException;
import java.util.ArrayList;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListRenderer extends BlockRenderer {
    public ListRenderer(List modelElement) {
        super(modelElement);
    }

    public LayoutResult layout(LayoutContext layoutContext) {
        LayoutResult errorResult = initializeListSymbols(layoutContext);
        if (errorResult != null) {
            return errorResult;
        }
        LayoutResult result = super.layout(layoutContext);
        if (!Boolean.TRUE.equals(getPropertyAsBoolean(26)) || result.getCauseOfNothing() == null) {
            return result;
        }
        if (1 == result.getStatus()) {
            return correctListSplitting(this, (IRenderer) null, result.getCauseOfNothing(), result.getOccupiedArea());
        }
        if (2 == result.getStatus()) {
            return correctListSplitting(result.getSplitRenderer(), result.getOverflowRenderer(), result.getCauseOfNothing(), result.getOccupiedArea());
        }
        return result;
    }

    public IRenderer getNextRenderer() {
        return new ListRenderer((List) this.modelElement);
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer createSplitRenderer(int layoutResult) {
        AbstractRenderer splitRenderer = super.createSplitRenderer(layoutResult);
        splitRenderer.addAllProperties(getOwnProperties());
        splitRenderer.setProperty(40, Boolean.TRUE);
        return splitRenderer;
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer createOverflowRenderer(int layoutResult) {
        AbstractRenderer overflowRenderer = super.createOverflowRenderer(layoutResult);
        overflowRenderer.addAllProperties(getOwnProperties());
        overflowRenderer.setProperty(40, Boolean.TRUE);
        return overflowRenderer;
    }

    public MinMaxWidth getMinMaxWidth() {
        if (initializeListSymbols(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getInfWidth(), 1000000.0f)))) != null) {
            return MinMaxWidthUtils.countDefaultMinMaxWidth(this);
        }
        return super.getMinMaxWidth();
    }

    /* access modifiers changed from: protected */
    public IRenderer makeListSymbolRenderer(int index, IRenderer renderer) {
        IRenderer symbolRenderer = createListSymbolRenderer(index, renderer);
        if (symbolRenderer != null) {
            symbolRenderer.setProperty(74, false);
        }
        return symbolRenderer;
    }

    static Object getListItemOrListProperty(IRenderer listItem, IRenderer list, int propertyId) {
        return listItem.hasProperty(propertyId) ? listItem.getProperty(propertyId) : list.getProperty(propertyId);
    }

    private IRenderer createListSymbolRenderer(int index, IRenderer renderer) {
        String numberText;
        IRenderer textRenderer;
        Object defaultListSymbol = getListItemOrListProperty(renderer, this, 37);
        if (defaultListSymbol instanceof Text) {
            return surroundTextBullet(new TextRenderer((Text) defaultListSymbol));
        }
        if (defaultListSymbol instanceof Image) {
            return new ImageRenderer((Image) defaultListSymbol);
        }
        if (defaultListSymbol instanceof ListNumberingType) {
            ListNumberingType numberingType = (ListNumberingType) defaultListSymbol;
            switch (C14732.$SwitchMap$com$itextpdf$layout$property$ListNumberingType[numberingType.ordinal()]) {
                case 1:
                    numberText = String.valueOf(index);
                    break;
                case 2:
                    numberText = (index < 10 ? "0" : "") + String.valueOf(index);
                    break;
                case 3:
                    numberText = RomanNumbering.toRomanLowerCase(index);
                    break;
                case 4:
                    numberText = RomanNumbering.toRomanUpperCase(index);
                    break;
                case 5:
                    numberText = EnglishAlphabetNumbering.toLatinAlphabetNumberLowerCase(index);
                    break;
                case 6:
                    numberText = EnglishAlphabetNumbering.toLatinAlphabetNumberUpperCase(index);
                    break;
                case 7:
                    numberText = GreekAlphabetNumbering.toGreekAlphabetNumber(index, false, true);
                    break;
                case 8:
                    numberText = GreekAlphabetNumbering.toGreekAlphabetNumber(index, true, true);
                    break;
                case 9:
                    numberText = TextUtil.charToString((char) (index + CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384));
                    break;
                case 10:
                    numberText = TextUtil.charToString((char) (index + CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384));
                    break;
                case 11:
                    numberText = TextUtil.charToString((char) (index + CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256));
                    break;
                case 12:
                    numberText = TextUtil.charToString((char) (index + XMPError.BADXML));
                    break;
                default:
                    throw new IllegalStateException();
            }
            Text textElement = new Text(getListItemOrListProperty(renderer, this, 41) + numberText + getListItemOrListProperty(renderer, this, 42));
            if (numberingType == ListNumberingType.GREEK_LOWER || numberingType == ListNumberingType.GREEK_UPPER || numberingType == ListNumberingType.ZAPF_DINGBATS_1 || numberingType == ListNumberingType.ZAPF_DINGBATS_2 || numberingType == ListNumberingType.ZAPF_DINGBATS_3 || numberingType == ListNumberingType.ZAPF_DINGBATS_4) {
                final String constantFont = (numberingType == ListNumberingType.GREEK_LOWER || numberingType == ListNumberingType.GREEK_UPPER) ? "Symbol" : "ZapfDingbats";
                IRenderer textRenderer2 = new TextRenderer(textElement) {
                    public void draw(DrawContext drawContext) {
                        try {
                            setProperty(20, PdfFontFactory.createFont(constantFont));
                        } catch (IOException e) {
                        }
                        super.draw(drawContext);
                    }
                };
                try {
                    textRenderer2.setProperty(20, PdfFontFactory.createFont(constantFont));
                } catch (IOException e) {
                }
                textRenderer = textRenderer2;
            } else {
                textRenderer = new TextRenderer(textElement);
            }
            return surroundTextBullet(textRenderer);
        } else if (defaultListSymbol instanceof IListSymbolFactory) {
            return surroundTextBullet(((IListSymbolFactory) defaultListSymbol).createSymbol(index, this, renderer).createRendererSubTree());
        } else {
            if (defaultListSymbol == null) {
                return null;
            }
            throw new IllegalStateException();
        }
    }

    /* renamed from: com.itextpdf.layout.renderer.ListRenderer$2 */
    static /* synthetic */ class C14732 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$ListNumberingType;

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
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ROMAN_LOWER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ROMAN_UPPER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ENGLISH_LOWER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ENGLISH_UPPER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.GREEK_LOWER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.GREEK_UPPER.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ZAPF_DINGBATS_1.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ZAPF_DINGBATS_2.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ZAPF_DINGBATS_3.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ListNumberingType[ListNumberingType.ZAPF_DINGBATS_4.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    private LineRenderer surroundTextBullet(IRenderer bulletRenderer) {
        LineRenderer lineRenderer = new LineRenderer();
        Text zeroWidthJoiner = new Text("‍");
        zeroWidthJoiner.getAccessibilityProperties().setRole(StandardRoles.ARTIFACT);
        TextRenderer zeroWidthJoinerRenderer = new TextRenderer(zeroWidthJoiner);
        lineRenderer.addChild(zeroWidthJoinerRenderer);
        lineRenderer.addChild(bulletRenderer);
        lineRenderer.addChild(zeroWidthJoinerRenderer);
        return lineRenderer;
    }

    private LayoutResult correctListSplitting(IRenderer splitRenderer, IRenderer overflowRenderer, IRenderer causeOfNothing, LayoutArea occupiedArea) {
        int firstNotRendered = splitRenderer.getChildRenderers().get(0).getChildRenderers().indexOf(causeOfNothing);
        if (-1 == firstNotRendered) {
            return new LayoutResult(overflowRenderer == null ? 1 : 2, occupiedArea, splitRenderer, overflowRenderer, this);
        }
        IRenderer firstListItemRenderer = splitRenderer.getChildRenderers().get(0);
        ListRenderer newOverflowRenderer = (ListRenderer) createOverflowRenderer(2);
        newOverflowRenderer.deleteOwnProperty(26);
        newOverflowRenderer.childRenderers.add(((ListItemRenderer) firstListItemRenderer).createOverflowRenderer(2));
        newOverflowRenderer.childRenderers.addAll(splitRenderer.getChildRenderers().subList(1, splitRenderer.getChildRenderers().size()));
        java.util.List<IRenderer> childrenStillRemainingToRender = new ArrayList<>(firstListItemRenderer.getChildRenderers().subList(firstNotRendered + 1, firstListItemRenderer.getChildRenderers().size()));
        splitRenderer.getChildRenderers().removeAll(splitRenderer.getChildRenderers().subList(1, splitRenderer.getChildRenderers().size()));
        if (childrenStillRemainingToRender.size() != 0) {
            newOverflowRenderer.getChildRenderers().get(0).getChildRenderers().addAll(childrenStillRemainingToRender);
            splitRenderer.getChildRenderers().get(0).getChildRenderers().removeAll(childrenStillRemainingToRender);
            newOverflowRenderer.getChildRenderers().get(0).setProperty(44, splitRenderer.getChildRenderers().get(0).getProperty(44));
        } else {
            newOverflowRenderer.childRenderers.remove(0);
        }
        if (overflowRenderer != null) {
            newOverflowRenderer.childRenderers.addAll(overflowRenderer.getChildRenderers());
        }
        if (newOverflowRenderer.childRenderers.size() != 0) {
            return new LayoutResult(2, occupiedArea, splitRenderer, newOverflowRenderer, this);
        }
        return new LayoutResult(1, occupiedArea, (IRenderer) null, (IRenderer) null, this);
    }

    private LayoutResult initializeListSymbols(LayoutContext layoutContext) {
        int i;
        LayoutTaggingHelper taggingHelper;
        LayoutResult listSymbolLayoutResult;
        int listItemNum;
        IRenderer currentSymbolRenderer;
        if (!hasOwnProperty(40)) {
            java.util.List<IRenderer> symbolRenderers = new ArrayList<>();
            int listItemNum2 = ((Integer) getProperty(36, 1)).intValue();
            int i2 = 0;
            while (true) {
                char c = 0;
                if (i2 < this.childRenderers.size()) {
                    ((IRenderer) this.childRenderers.get(i2)).setParent(this);
                    int listItemNum3 = ((IRenderer) this.childRenderers.get(i2)).getProperty(120) != null ? ((Integer) ((IRenderer) this.childRenderers.get(i2)).getProperty(120)).intValue() : listItemNum2;
                    IRenderer currentSymbolRenderer2 = makeListSymbolRenderer(listItemNum3, (IRenderer) this.childRenderers.get(i2));
                    if (BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7))) {
                        currentSymbolRenderer2.setProperty(7, BaseDirection.RIGHT_TO_LEFT);
                    }
                    if (currentSymbolRenderer2 != null) {
                        currentSymbolRenderer2.setParent((IRenderer) this.childRenderers.get(i2));
                        LayoutResult listSymbolLayoutResult2 = currentSymbolRenderer2.layout(layoutContext);
                        currentSymbolRenderer2.setParent((IRenderer) null);
                        listItemNum = listItemNum3 + 1;
                        listSymbolLayoutResult = listSymbolLayoutResult2;
                    } else {
                        LayoutContext layoutContext2 = layoutContext;
                        listItemNum = listItemNum3;
                        listSymbolLayoutResult = null;
                    }
                    ((IRenderer) this.childRenderers.get(i2)).setParent((IRenderer) null);
                    boolean isForcedPlacement = Boolean.TRUE.equals(getPropertyAsBoolean(26));
                    if (!(listSymbolLayoutResult == null || listSymbolLayoutResult.getStatus() == 1)) {
                        c = 1;
                    }
                    char c2 = c;
                    if (c2 == 0 || !isForcedPlacement) {
                        currentSymbolRenderer = currentSymbolRenderer2;
                    } else {
                        currentSymbolRenderer = null;
                    }
                    symbolRenderers.add(currentSymbolRenderer);
                    if (c2 == 0 || isForcedPlacement) {
                        i2++;
                        listItemNum2 = listItemNum;
                    } else {
                        return new LayoutResult(3, (LayoutArea) null, (IRenderer) null, this, listSymbolLayoutResult.getCauseOfNothing());
                    }
                } else {
                    LayoutContext layoutContext3 = layoutContext;
                    float maxSymbolWidth = 0.0f;
                    int i3 = 0;
                    while (true) {
                        i = 83;
                        if (i3 >= this.childRenderers.size()) {
                            break;
                        }
                        IRenderer symbolRenderer = symbolRenderers.get(i3);
                        if (!(symbolRenderer == null || ((ListSymbolPosition) getListItemOrListProperty((IRenderer) this.childRenderers.get(i3), this, 83)) == ListSymbolPosition.INSIDE)) {
                            maxSymbolWidth = Math.max(maxSymbolWidth, symbolRenderer.getOccupiedArea().getBBox().getWidth());
                        }
                        i3++;
                    }
                    Float symbolIndent = getPropertyAsFloat(39);
                    int listItemNum4 = 0;
                    for (IRenderer childRenderer : this.childRenderers) {
                        childRenderer.setParent(this);
                        childRenderer.deleteOwnProperty(44);
                        UnitValue marginLeftUV = (UnitValue) childRenderer.getProperty(44, UnitValue.createPointValue(0.0f));
                        if (!marginLeftUV.isPointValue()) {
                            Logger logger = LoggerFactory.getLogger((Class<?>) ListRenderer.class);
                            Object[] objArr = new Object[1];
                            objArr[c] = 44;
                            logger.error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, objArr));
                        }
                        float calculatedMargin = marginLeftUV.getValue();
                        if (((ListSymbolPosition) getListItemOrListProperty(childRenderer, this, i)) == ListSymbolPosition.DEFAULT) {
                            calculatedMargin += (symbolIndent != null ? symbolIndent.floatValue() : 0.0f) + maxSymbolWidth;
                        }
                        childRenderer.setProperty(44, UnitValue.createPointValue(calculatedMargin));
                        int listItemNum5 = listItemNum4 + 1;
                        IRenderer symbolRenderer2 = symbolRenderers.get(listItemNum4);
                        ((ListItemRenderer) childRenderer).addSymbolRenderer(symbolRenderer2, maxSymbolWidth);
                        if (!(symbolRenderer2 == null || (taggingHelper = (LayoutTaggingHelper) getProperty(108)) == null)) {
                            if (symbolRenderer2 instanceof LineRenderer) {
                                taggingHelper.setRoleHint(symbolRenderer2.getChildRenderers().get(1), StandardRoles.LBL);
                            } else {
                                taggingHelper.setRoleHint(symbolRenderer2, StandardRoles.LBL);
                            }
                        }
                        listItemNum4 = listItemNum5;
                        c = 0;
                        i = 83;
                    }
                }
            }
        } else {
            LayoutContext layoutContext4 = layoutContext;
        }
        return null;
    }
}
