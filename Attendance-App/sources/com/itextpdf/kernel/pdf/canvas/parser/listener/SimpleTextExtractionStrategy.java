package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.LineSegment;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class SimpleTextExtractionStrategy implements ITextExtractionStrategy {
    private Vector lastEnd;
    private Vector lastStart;
    private final StringBuilder result = new StringBuilder();

    public void eventOccurred(IEventData data, EventType type) {
        if (type.equals(EventType.RENDER_TEXT)) {
            TextRenderInfo renderInfo = (TextRenderInfo) data;
            boolean firstRender = this.result.length() == 0;
            boolean hardReturn = false;
            LineSegment segment = renderInfo.getBaseline();
            Vector start = segment.getStartPoint();
            Vector end = segment.getEndPoint();
            if (!firstRender) {
                Vector x1 = this.lastStart;
                Vector x2 = this.lastEnd;
                if (x2.subtract(x1).cross(x1.subtract(start)).lengthSquared() / x2.subtract(x1).lengthSquared() > 1.0f) {
                    hardReturn = true;
                }
            }
            if (hardReturn) {
                appendTextChunk("\n");
            } else if (!firstRender) {
                StringBuilder sb = this.result;
                if (sb.charAt(sb.length() - 1) != ' ' && renderInfo.getText().length() > 0 && renderInfo.getText().charAt(0) != ' ' && this.lastEnd.subtract(start).length() > renderInfo.getSingleSpaceWidth() / 2.0f) {
                    appendTextChunk(" ");
                }
            }
            appendTextChunk(renderInfo.getText());
            this.lastStart = start;
            this.lastEnd = end;
        }
    }

    public Set<EventType> getSupportedEvents() {
        return Collections.unmodifiableSet(new LinkedHashSet(Collections.singletonList(EventType.RENDER_TEXT)));
    }

    public String getResultantText() {
        return this.result.toString();
    }

    /* access modifiers changed from: protected */
    public final void appendTextChunk(CharSequence text) {
        this.result.append(text);
    }
}
