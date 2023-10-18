package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.ListSymbolAlignment;
import com.itextpdf.layout.property.ListSymbolPosition;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.tagging.LayoutTaggingHelper;
import com.itextpdf.layout.tagging.TaggingDummyElement;
import com.itextpdf.layout.tagging.TaggingHintKey;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.Collections;
import org.slf4j.LoggerFactory;

public class ListItemRenderer extends DivRenderer {
    private boolean symbolAddedInside;
    protected float symbolAreaWidth;
    protected IRenderer symbolRenderer;

    public ListItemRenderer(ListItem modelElement) {
        super(modelElement);
    }

    public void addSymbolRenderer(IRenderer symbolRenderer2, float symbolAreaWidth2) {
        this.symbolRenderer = symbolRenderer2;
        this.symbolAreaWidth = symbolAreaWidth2;
    }

    public LayoutResult layout(LayoutContext layoutContext) {
        if (this.symbolRenderer != null && getProperty(27) == null && !isListSymbolEmpty(this.symbolRenderer)) {
            float[] ascenderDescender = calculateAscenderDescender();
            updateMinHeight(UnitValue.createPointValue(Math.max(this.symbolRenderer.getOccupiedArea().getBBox().getHeight(), ascenderDescender[0] - ascenderDescender[1])));
        }
        applyListSymbolPosition();
        LayoutResult result = super.layout(layoutContext);
        if (2 == result.getStatus()) {
            result.getOverflowRenderer().deleteOwnProperty(85);
        }
        return result;
    }

    public void draw(DrawContext drawContext) {
        float x;
        LayoutTaggingHelper taggingHelper;
        Class<ListItemRenderer> cls = ListItemRenderer.class;
        if (this.occupiedArea == null) {
            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, "Drawing won't be performed."));
            return;
        }
        if (drawContext.isTaggingEnabled() && (taggingHelper = (LayoutTaggingHelper) getProperty(108)) != null) {
            IRenderer iRenderer = this.symbolRenderer;
            if (iRenderer != null) {
                LayoutTaggingHelper.addTreeHints(taggingHelper, iRenderer);
            }
            if (taggingHelper.isArtifact(this)) {
                taggingHelper.markArtifactHint((IPropertyContainer) this.symbolRenderer);
            } else {
                TaggingHintKey hintKey = LayoutTaggingHelper.getHintKey(this);
                TaggingHintKey parentHint = taggingHelper.getAccessibleParentHint(hintKey);
                if (parentHint != null && !StandardRoles.f1510LI.equals(parentHint.getAccessibleElement().getAccessibilityProperties().getRole())) {
                    TaggingDummyElement listItemIntermediate = new TaggingDummyElement(StandardRoles.f1510LI);
                    taggingHelper.replaceKidHint(hintKey, Collections.singletonList(LayoutTaggingHelper.getOrCreateHintKey(listItemIntermediate)));
                    IRenderer iRenderer2 = this.symbolRenderer;
                    if (iRenderer2 != null) {
                        taggingHelper.addKidsHint((IPropertyContainer) listItemIntermediate, (Iterable<? extends IPropertyContainer>) Collections.singletonList(iRenderer2));
                    }
                    taggingHelper.addKidsHint((IPropertyContainer) listItemIntermediate, (Iterable<? extends IPropertyContainer>) Collections.singletonList(this));
                }
            }
        }
        super.draw(drawContext);
        if (this.symbolRenderer != null && !this.symbolAddedInside) {
            boolean isRtl = BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7));
            this.symbolRenderer.setParent(this);
            Rectangle bBox = this.occupiedArea.getBBox();
            float x2 = isRtl ? bBox.getRight() : bBox.getLeft();
            ListSymbolPosition symbolPosition = (ListSymbolPosition) ListRenderer.getListItemOrListProperty(this, this.parent, 83);
            if (symbolPosition != ListSymbolPosition.DEFAULT) {
                Float symbolIndent = getPropertyAsFloat(39);
                if (isRtl) {
                    x = x2 + this.symbolAreaWidth + (symbolIndent == null ? 0.0f : symbolIndent.floatValue());
                } else {
                    x = x2 - (this.symbolAreaWidth + (symbolIndent == null ? 0.0f : symbolIndent.floatValue()));
                }
                if (symbolPosition == ListSymbolPosition.OUTSIDE) {
                    if (isRtl) {
                        UnitValue marginRightUV = getPropertyAsUnitValue(45);
                        if (!marginRightUV.isPointValue()) {
                            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 45));
                        }
                        x2 -= marginRightUV.getValue();
                    } else {
                        UnitValue marginLeftUV = getPropertyAsUnitValue(44);
                        if (!marginLeftUV.isPointValue()) {
                            LoggerFactory.getLogger((Class<?>) cls).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 44));
                        }
                        x2 += marginLeftUV.getValue();
                    }
                }
            }
            applyMargins(this.occupiedArea.getBBox(), false);
            applyBorderBox(this.occupiedArea.getBBox(), false);
            if (this.childRenderers.size() > 0) {
                Float yLine = null;
                int i = 0;
                while (i < this.childRenderers.size() && (((IRenderer) this.childRenderers.get(i)).getOccupiedArea().getBBox().getHeight() <= 0.0f || (yLine = ((AbstractRenderer) this.childRenderers.get(i)).getFirstYLineRecursively()) == null)) {
                    i++;
                }
                if (yLine != null) {
                    IRenderer iRenderer3 = this.symbolRenderer;
                    if (iRenderer3 instanceof LineRenderer) {
                        iRenderer3.move(0.0f, yLine.floatValue() - ((LineRenderer) this.symbolRenderer).getYLine());
                    } else {
                        iRenderer3.move(0.0f, yLine.floatValue() - this.symbolRenderer.getOccupiedArea().getBBox().getY());
                    }
                } else {
                    this.symbolRenderer.move(0.0f, (this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight()) - (this.symbolRenderer.getOccupiedArea().getBBox().getY() + this.symbolRenderer.getOccupiedArea().getBBox().getHeight()));
                }
            } else {
                IRenderer iRenderer4 = this.symbolRenderer;
                if (iRenderer4 instanceof TextRenderer) {
                    ((TextRenderer) iRenderer4).moveYLineTo((this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight()) - calculateAscenderDescender()[0]);
                } else {
                    iRenderer4.move(0.0f, ((this.occupiedArea.getBBox().getY() + this.occupiedArea.getBBox().getHeight()) - this.symbolRenderer.getOccupiedArea().getBBox().getHeight()) - this.symbolRenderer.getOccupiedArea().getBBox().getY());
                }
            }
            applyBorderBox(this.occupiedArea.getBBox(), true);
            applyMargins(this.occupiedArea.getBBox(), true);
            ListSymbolAlignment listSymbolAlignment = (ListSymbolAlignment) this.parent.getProperty(38, isRtl ? ListSymbolAlignment.LEFT : ListSymbolAlignment.RIGHT);
            float dxPosition = x2 - this.symbolRenderer.getOccupiedArea().getBBox().getX();
            if (listSymbolAlignment == ListSymbolAlignment.RIGHT) {
                if (!isRtl) {
                    dxPosition += this.symbolAreaWidth - this.symbolRenderer.getOccupiedArea().getBBox().getWidth();
                }
            } else if (listSymbolAlignment == ListSymbolAlignment.LEFT && isRtl) {
                dxPosition -= this.symbolAreaWidth - this.symbolRenderer.getOccupiedArea().getBBox().getWidth();
            }
            IRenderer iRenderer5 = this.symbolRenderer;
            if (!(iRenderer5 instanceof LineRenderer)) {
                iRenderer5.move(dxPosition, 0.0f);
            } else if (isRtl) {
                iRenderer5.move(dxPosition - iRenderer5.getOccupiedArea().getBBox().getWidth(), 0.0f);
            } else {
                iRenderer5.move(dxPosition, 0.0f);
            }
            if (this.symbolRenderer.getOccupiedArea().getBBox().getRight() > this.parent.getOccupiedArea().getBBox().getLeft()) {
                beginElementOpacityApplying(drawContext);
                this.symbolRenderer.draw(drawContext);
                endElementOpacityApplying(drawContext);
            }
        }
    }

    public IRenderer getNextRenderer() {
        return new ListItemRenderer((ListItem) this.modelElement);
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer createSplitRenderer(int layoutResult) {
        ListItemRenderer splitRenderer = (ListItemRenderer) getNextRenderer();
        splitRenderer.parent = this.parent;
        splitRenderer.modelElement = this.modelElement;
        splitRenderer.occupiedArea = this.occupiedArea;
        splitRenderer.isLastRendererForModelElement = false;
        if (layoutResult == 2) {
            splitRenderer.symbolRenderer = this.symbolRenderer;
            splitRenderer.symbolAreaWidth = this.symbolAreaWidth;
        }
        splitRenderer.addAllProperties(getOwnProperties());
        return splitRenderer;
    }

    /* access modifiers changed from: protected */
    public AbstractRenderer createOverflowRenderer(int layoutResult) {
        ListItemRenderer overflowRenderer = (ListItemRenderer) getNextRenderer();
        overflowRenderer.parent = this.parent;
        overflowRenderer.modelElement = this.modelElement;
        if (layoutResult == 3) {
            overflowRenderer.symbolRenderer = this.symbolRenderer;
            overflowRenderer.symbolAreaWidth = this.symbolAreaWidth;
        }
        overflowRenderer.addAllProperties(getOwnProperties());
        return overflowRenderer;
    }

    private void applyListSymbolPosition() {
        if (this.symbolRenderer != null && ((ListSymbolPosition) ListRenderer.getListItemOrListProperty(this, this.parent, 83)) == ListSymbolPosition.INSIDE) {
            boolean isRtl = BaseDirection.RIGHT_TO_LEFT.equals(getProperty(7));
            if (this.childRenderers.size() > 0 && (this.childRenderers.get(0) instanceof ParagraphRenderer)) {
                ParagraphRenderer paragraphRenderer = (ParagraphRenderer) this.childRenderers.get(0);
                Float symbolIndent = getPropertyAsFloat(39);
                IRenderer iRenderer = this.symbolRenderer;
                int i = 44;
                if (iRenderer instanceof LineRenderer) {
                    if (symbolIndent != null) {
                        IRenderer iRenderer2 = iRenderer.getChildRenderers().get(1);
                        if (!isRtl) {
                            i = 45;
                        }
                        iRenderer2.setProperty(i, UnitValue.createPointValue(symbolIndent.floatValue()));
                    }
                    for (IRenderer childRenderer : this.symbolRenderer.getChildRenderers()) {
                        paragraphRenderer.childRenderers.add(0, childRenderer);
                    }
                } else {
                    if (symbolIndent != null) {
                        if (!isRtl) {
                            i = 45;
                        }
                        iRenderer.setProperty(i, UnitValue.createPointValue(symbolIndent.floatValue()));
                    }
                    paragraphRenderer.childRenderers.add(0, this.symbolRenderer);
                }
                this.symbolAddedInside = true;
            } else if (this.childRenderers.size() > 0 && (this.childRenderers.get(0) instanceof ImageRenderer)) {
                Paragraph p = new Paragraph();
                p.getAccessibilityProperties().setRole((String) null);
                IRenderer paragraphRenderer2 = ((Paragraph) p.setMargin(0.0f)).createRendererSubTree();
                Float symbolIndent2 = getPropertyAsFloat(39);
                if (symbolIndent2 != null) {
                    this.symbolRenderer.setProperty(45, UnitValue.createPointValue(symbolIndent2.floatValue()));
                }
                paragraphRenderer2.addChild(this.symbolRenderer);
                paragraphRenderer2.addChild((IRenderer) this.childRenderers.get(0));
                this.childRenderers.set(0, paragraphRenderer2);
                this.symbolAddedInside = true;
            }
            if (!this.symbolAddedInside) {
                Paragraph p2 = new Paragraph();
                p2.getAccessibilityProperties().setRole((String) null);
                IRenderer paragraphRenderer3 = ((Paragraph) p2.setMargin(0.0f)).createRendererSubTree();
                Float symbolIndent3 = getPropertyAsFloat(39);
                if (symbolIndent3 != null) {
                    this.symbolRenderer.setProperty(45, UnitValue.createPointValue(symbolIndent3.floatValue()));
                }
                paragraphRenderer3.addChild(this.symbolRenderer);
                this.childRenderers.add(0, paragraphRenderer3);
                this.symbolAddedInside = true;
            }
        }
    }

    private boolean isListSymbolEmpty(IRenderer listSymbolRenderer) {
        if (listSymbolRenderer instanceof TextRenderer) {
            if (((TextRenderer) listSymbolRenderer).getText().toString().length() == 0) {
                return true;
            }
            return false;
        } else if (!(listSymbolRenderer instanceof LineRenderer) || ((TextRenderer) listSymbolRenderer.getChildRenderers().get(1)).getText().toString().length() != 0) {
            return false;
        } else {
            return true;
        }
    }

    private float[] calculateAscenderDescender() {
        PdfFont listItemFont = resolveFirstPdfFont();
        UnitValue fontSize = getPropertyAsUnitValue(24);
        if (listItemFont == null || fontSize == null) {
            return new float[]{0.0f, 0.0f};
        }
        if (!fontSize.isPointValue()) {
            LoggerFactory.getLogger((Class<?>) ListItemRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.PROPERTY_IN_PERCENTS_NOT_SUPPORTED, 24));
        }
        float[] ascenderDescender = TextRenderer.calculateAscenderDescender(listItemFont);
        return new float[]{(fontSize.getValue() * ascenderDescender[0]) / 1000.0f, (fontSize.getValue() * ascenderDescender[1]) / 1000.0f};
    }
}
