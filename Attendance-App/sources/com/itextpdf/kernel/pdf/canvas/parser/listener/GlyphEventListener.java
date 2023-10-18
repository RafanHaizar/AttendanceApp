package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import java.util.Set;

public class GlyphEventListener implements IEventListener {
    protected final IEventListener delegate;

    public GlyphEventListener(IEventListener delegate2) {
        this.delegate = delegate2;
    }

    public void eventOccurred(IEventData data, EventType type) {
        if (type.equals(EventType.RENDER_TEXT)) {
            for (TextRenderInfo glyphRenderInfo : ((TextRenderInfo) data).getCharacterRenderInfos()) {
                this.delegate.eventOccurred(glyphRenderInfo, type);
            }
            return;
        }
        this.delegate.eventOccurred(data, type);
    }

    public Set<EventType> getSupportedEvents() {
        return this.delegate.getSupportedEvents();
    }
}
