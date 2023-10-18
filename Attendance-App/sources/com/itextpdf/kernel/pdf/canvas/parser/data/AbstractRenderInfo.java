package com.itextpdf.kernel.pdf.canvas.parser.data;

import com.itextpdf.kernel.pdf.canvas.CanvasGraphicsState;
import com.itextpdf.p026io.LogMessageConstant;

public class AbstractRenderInfo implements IEventData {
    private boolean graphicsStateIsPreserved;

    /* renamed from: gs */
    protected CanvasGraphicsState f1495gs;

    public AbstractRenderInfo(CanvasGraphicsState gs) {
        this.f1495gs = gs;
    }

    public CanvasGraphicsState getGraphicsState() {
        checkGraphicsState();
        return this.graphicsStateIsPreserved ? this.f1495gs : new CanvasGraphicsState(this.f1495gs);
    }

    public boolean isGraphicsStatePreserved() {
        return this.graphicsStateIsPreserved;
    }

    public void preserveGraphicsState() {
        checkGraphicsState();
        this.graphicsStateIsPreserved = true;
        this.f1495gs = new CanvasGraphicsState(this.f1495gs);
    }

    public void releaseGraphicsState() {
        if (!this.graphicsStateIsPreserved) {
            this.f1495gs = null;
        }
    }

    /* access modifiers changed from: protected */
    public void checkGraphicsState() {
        if (this.f1495gs == null) {
            throw new IllegalStateException(LogMessageConstant.GRAPHICS_STATE_WAS_DELETED);
        }
    }
}
