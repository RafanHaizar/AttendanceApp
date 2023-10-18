package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.layout.RootLayoutArea;
import com.itextpdf.layout.property.Transform;
import org.slf4j.LoggerFactory;

public class CanvasRenderer extends RootRenderer {
    protected Canvas canvas;

    public CanvasRenderer(Canvas canvas2) {
        this(canvas2, true);
    }

    public CanvasRenderer(Canvas canvas2, boolean immediateFlush) {
        this.canvas = canvas2;
        this.modelElement = canvas2;
        this.immediateFlush = immediateFlush;
    }

    public void addChild(IRenderer renderer) {
        if (Boolean.TRUE.equals(getPropertyAsBoolean(25))) {
            LoggerFactory.getLogger((Class<?>) CanvasRenderer.class).warn("Canvas is already full. Element will be skipped.");
        } else {
            super.addChild(renderer);
        }
    }

    /* access modifiers changed from: protected */
    public void flushSingleRenderer(IRenderer resultRenderer) {
        Transform transformProp = (Transform) resultRenderer.getProperty(53);
        if (!this.waitingDrawingElements.contains(resultRenderer)) {
            processWaitingDrawing(resultRenderer, transformProp, this.waitingDrawingElements);
            if (FloatingHelper.isRendererFloating(resultRenderer) || transformProp != null) {
                return;
            }
        }
        if (!resultRenderer.isFlushed()) {
            boolean toTag = this.canvas.getPdfDocument().isTagged() && this.canvas.isAutoTaggingEnabled();
            TagTreePointer tagPointer = null;
            if (toTag) {
                tagPointer = this.canvas.getPdfDocument().getTagStructureContext().getAutoTaggingPointer();
                tagPointer.setPageForTagging(this.canvas.getPage());
                boolean pageStream = false;
                int i = this.canvas.getPage().getContentStreamCount() - 1;
                while (true) {
                    if (i < 0) {
                        break;
                    } else if (this.canvas.getPage().getContentStream(i).equals(this.canvas.getPdfCanvas().getContentStream())) {
                        pageStream = true;
                        break;
                    } else {
                        i--;
                    }
                }
                if (!pageStream) {
                    tagPointer.setContentStreamForTagging(this.canvas.getPdfCanvas().getContentStream());
                }
            }
            resultRenderer.draw(new DrawContext(this.canvas.getPdfDocument(), this.canvas.getPdfCanvas(), toTag));
            if (toTag) {
                tagPointer.setContentStreamForTagging((PdfStream) null);
            }
        }
    }

    /* access modifiers changed from: protected */
    public LayoutArea updateCurrentArea(LayoutResult overflowResult) {
        if (this.currentArea == null) {
            this.currentArea = new RootLayoutArea(this.canvas.isCanvasOfPage() ? this.canvas.getPdfDocument().getPageNumber(this.canvas.getPage()) : 0, this.canvas.getRootArea().clone());
        } else {
            setProperty(25, true);
            this.currentArea = null;
        }
        return this.currentArea;
    }

    public IRenderer getNextRenderer() {
        return new CanvasRenderer(this.canvas, this.immediateFlush);
    }
}
