package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.p026io.util.MessageFormatUtil;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class TextMarginFinder implements IEventListener {
    private Rectangle textRectangle = null;

    public void eventOccurred(IEventData data, EventType type) {
        if (type == EventType.RENDER_TEXT) {
            TextRenderInfo info = (TextRenderInfo) data;
            Rectangle rectangle = this.textRectangle;
            if (rectangle == null) {
                this.textRectangle = info.getDescentLine().getBoundingRectangle();
            } else {
                this.textRectangle = Rectangle.getCommonRectangle(rectangle, info.getDescentLine().getBoundingRectangle());
            }
            this.textRectangle = Rectangle.getCommonRectangle(this.textRectangle, info.getAscentLine().getBoundingRectangle());
            return;
        }
        throw new IllegalStateException(MessageFormatUtil.format("Event type not supported: {0}", type));
    }

    public Set<EventType> getSupportedEvents() {
        return new LinkedHashSet(Collections.singletonList(EventType.RENDER_TEXT));
    }

    public Rectangle getTextRectangle() {
        return this.textRectangle;
    }
}
