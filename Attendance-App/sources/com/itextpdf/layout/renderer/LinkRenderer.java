package com.itextpdf.layout.renderer;

import com.itextpdf.layout.element.Link;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import org.slf4j.LoggerFactory;

public class LinkRenderer extends TextRenderer {
    public LinkRenderer(Link link) {
        this(link, link.getText());
    }

    public LinkRenderer(Link linkElement, String text) {
        super(linkElement, text);
    }

    public void draw(DrawContext drawContext) {
        if (this.occupiedArea == null) {
            LoggerFactory.getLogger((Class<?>) LinkRenderer.class).error(MessageFormatUtil.format(LogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, "Drawing won't be performed."));
            return;
        }
        super.draw(drawContext);
        if (isRelativePosition()) {
            applyRelativePositioningTranslation(false);
        }
    }

    public IRenderer getNextRenderer() {
        return new LinkRenderer((Link) this.modelElement);
    }
}
