package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.margincollapse.MarginsCollapseInfo;
import com.itextpdf.layout.property.ParagraphOrphansControl;
import com.itextpdf.layout.property.ParagraphWidowsControl;
import java.util.ArrayList;

class OrphansWidowsLayoutHelper {
    private OrphansWidowsLayoutHelper() {
    }

    static LayoutResult orphansWidowsAwareLayout(ParagraphRenderer renderer, LayoutContext context, ParagraphOrphansControl orphansControl, ParagraphWidowsControl widowsControl) {
        ParagraphRenderer paragraphRenderer = renderer;
        LayoutContext layoutContext = context;
        ParagraphOrphansControl paragraphOrphansControl = orphansControl;
        ParagraphWidowsControl paragraphWidowsControl = widowsControl;
        OrphansWidowsLayoutAttempt layoutAttempt = attemptLayout(paragraphRenderer, layoutContext, context.getArea().clone());
        if (context.isClippedHeight() || renderer.isPositioned() || layoutAttempt.attemptResult.getStatus() != 2 || layoutAttempt.attemptResult.getSplitRenderer() == null) {
            return handleAttemptAsSuccessful(layoutAttempt, layoutContext);
        }
        ParagraphRenderer splitRenderer = (ParagraphRenderer) layoutAttempt.attemptResult.getSplitRenderer();
        boolean orphansViolation = paragraphOrphansControl != null && splitRenderer != null && splitRenderer.getLines().size() < orphansControl.getMinOrphans() && !renderer.isFirstOnRootArea();
        boolean forcedPlacement = Boolean.TRUE.equals(paragraphRenderer.getPropertyAsBoolean(26));
        if (orphansViolation && forcedPlacement) {
            paragraphOrphansControl.handleViolatedOrphans(splitRenderer, "Ignored orphans constraint due to forced placement.");
        }
        if (orphansViolation && !forcedPlacement) {
            layoutAttempt = null;
            ParagraphRenderer paragraphRenderer2 = splitRenderer;
        } else if (paragraphWidowsControl == null || splitRenderer == null || layoutAttempt.attemptResult.getOverflowRenderer() == null) {
        } else {
            ParagraphRenderer overflowRenderer = (ParagraphRenderer) layoutAttempt.attemptResult.getOverflowRenderer();
            if (overflowRenderer.directLayout(new LayoutContext(new LayoutArea(context.getArea().getPageNumber(), context.getArea().getBBox().clone().setHeight((float) 3500)))).getStatus() == 1) {
                int extraWidows = widowsControl.getMinWidows() - overflowRenderer.getLines().size();
                if (extraWidows > 0) {
                    int extraLinesToMove = paragraphOrphansControl != null ? Math.max(orphansControl.getMinOrphans(), 1) : 1;
                    if (extraWidows > widowsControl.getMaxLinesToMove() || splitRenderer.getLines().size() - extraWidows < extraLinesToMove) {
                        if (!forcedPlacement && !renderer.isFirstOnRootArea() && widowsControl.isOverflowOnWidowsViolation()) {
                            layoutAttempt = null;
                        } else if (forcedPlacement) {
                            paragraphWidowsControl.handleViolatedWidows(overflowRenderer, "forced placement");
                        } else {
                            paragraphWidowsControl.handleViolatedWidows(overflowRenderer, "inability to fix it");
                        }
                    } else {
                        LineRenderer lastLine = splitRenderer.getLines().get(splitRenderer.getLines().size() - 1);
                        ParagraphRenderer paragraphRenderer3 = splitRenderer;
                        LineRenderer lastLineToLeave = splitRenderer.getLines().get((splitRenderer.getLines().size() - extraWidows) - 1);
                        float d = (lastLineToLeave.getOccupiedArea().getBBox().getY() - lastLine.getOccupiedArea().getBBox().getY()) - 1.0E-4f;
                        LineRenderer lineRenderer = lastLine;
                        LineRenderer lineRenderer2 = lastLineToLeave;
                        Rectangle smallerBBox = new Rectangle(context.getArea().getBBox());
                        smallerBBox.decreaseHeight(d);
                        smallerBBox.moveUp(d);
                        float f = d;
                        layoutAttempt = attemptLayout(paragraphRenderer, layoutContext, new LayoutArea(context.getArea().getPageNumber(), smallerBBox));
                    }
                }
            }
        }
        if (layoutAttempt != null) {
            return handleAttemptAsSuccessful(layoutAttempt, layoutContext);
        }
        return new LayoutResult(3, (LayoutArea) null, (IRenderer) null, paragraphRenderer);
    }

    private static OrphansWidowsLayoutAttempt attemptLayout(ParagraphRenderer renderer, LayoutContext originalContext, LayoutArea attemptArea) {
        OrphansWidowsLayoutAttempt attemptResult = new OrphansWidowsLayoutAttempt();
        MarginsCollapseInfo copiedMarginsCollapseInfo = null;
        if (originalContext.getMarginsCollapseInfo() != null) {
            copiedMarginsCollapseInfo = MarginsCollapseInfo.createDeepCopy(originalContext.getMarginsCollapseInfo());
        }
        LayoutContext attemptContext = new LayoutContext(attemptArea, copiedMarginsCollapseInfo, new ArrayList<>(originalContext.getFloatRendererAreas()), originalContext.isClippedHeight());
        attemptResult.attemptContext = attemptContext;
        attemptResult.attemptResult = renderer.directLayout(attemptContext);
        return attemptResult;
    }

    private static LayoutResult handleAttemptAsSuccessful(OrphansWidowsLayoutAttempt attemptResult, LayoutContext originalContext) {
        originalContext.getFloatRendererAreas().clear();
        originalContext.getFloatRendererAreas().addAll(attemptResult.attemptContext.getFloatRendererAreas());
        if (originalContext.getMarginsCollapseInfo() != null) {
            MarginsCollapseInfo.updateFromCopy(originalContext.getMarginsCollapseInfo(), attemptResult.attemptContext.getMarginsCollapseInfo());
        }
        return attemptResult.attemptResult;
    }

    private static class OrphansWidowsLayoutAttempt {
        LayoutContext attemptContext;
        LayoutResult attemptResult;

        private OrphansWidowsLayoutAttempt() {
        }
    }
}
