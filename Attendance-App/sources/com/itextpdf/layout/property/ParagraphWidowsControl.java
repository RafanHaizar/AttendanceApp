package com.itextpdf.layout.property;

import com.itextpdf.layout.renderer.ParagraphRenderer;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParagraphWidowsControl {
    private int maxLinesToMove;
    private int minWidows;
    private boolean overflowOnWidowsViolation;

    public ParagraphWidowsControl(int minWidows2, int maxLinesToMove2, boolean overflowParagraphOnViolation) {
        this.minWidows = minWidows2;
        this.maxLinesToMove = maxLinesToMove2;
        this.overflowOnWidowsViolation = overflowParagraphOnViolation;
    }

    public ParagraphWidowsControl setMinAllowedWidows(int minWidows2, int maxLinesToMove2, boolean overflowParagraphOnViolation) {
        this.minWidows = minWidows2;
        this.maxLinesToMove = maxLinesToMove2;
        this.overflowOnWidowsViolation = overflowParagraphOnViolation;
        return this;
    }

    public int getMinWidows() {
        return this.minWidows;
    }

    public int getMaxLinesToMove() {
        return this.maxLinesToMove;
    }

    public boolean isOverflowOnWidowsViolation() {
        return this.overflowOnWidowsViolation;
    }

    public void handleViolatedWidows(ParagraphRenderer widowsRenderer, String message) {
        Logger logger = LoggerFactory.getLogger((Class<?>) ParagraphWidowsControl.class);
        if (widowsRenderer.getOccupiedArea() == null || widowsRenderer.getLines() == null) {
            logger.warn(LogMessageConstant.PREMATURE_CALL_OF_HANDLE_VIOLATION_METHOD);
            return;
        }
        logger.warn(MessageFormatUtil.format(LogMessageConstant.WIDOWS_CONSTRAINT_VIOLATED, Integer.valueOf(widowsRenderer.getOccupiedArea().getPageNumber()), Integer.valueOf(this.minWidows), Integer.valueOf(widowsRenderer.getLines().size()), message));
    }
}
