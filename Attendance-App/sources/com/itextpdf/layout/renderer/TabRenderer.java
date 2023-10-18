package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.CanvasArtifact;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import org.slf4j.LoggerFactory;

public class TabRenderer extends AbstractRenderer {
    public TabRenderer(Tab tab) {
        super((IElement) tab);
    }

    public LayoutResult layout(LayoutContext layoutContext) {
        LayoutArea area = layoutContext.getArea();
        this.occupiedArea = new LayoutArea(area.getPageNumber(), new Rectangle(area.getBBox().getX(), area.getBBox().getY() + area.getBBox().getHeight(), retrieveWidth(area.getBBox().getWidth()).floatValue(), ((UnitValue) getProperty(85)).getValue()));
        return new LayoutResult(1, this.occupiedArea, (IRenderer) null, (IRenderer) null);
    }

    public void draw(DrawContext drawContext) {
        if (this.occupiedArea == null) {
            LoggerFactory.getLogger((Class<?>) TabRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, "Drawing won't be performed."));
            return;
        }
        ILineDrawer leader = (ILineDrawer) getProperty(68);
        if (leader != null) {
            boolean isTagged = drawContext.isTaggingEnabled();
            if (isTagged) {
                drawContext.getCanvas().openTag((CanvasTag) new CanvasArtifact());
            }
            beginElementOpacityApplying(drawContext);
            leader.draw(drawContext.getCanvas(), this.occupiedArea.getBBox());
            endElementOpacityApplying(drawContext);
            if (isTagged) {
                drawContext.getCanvas().closeTag();
            }
        }
    }

    public IRenderer getNextRenderer() {
        return new TabRenderer((Tab) this.modelElement);
    }
}
