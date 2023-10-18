package com.itextpdf.layout.property;

import com.itextpdf.layout.renderer.ParagraphRenderer;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParagraphOrphansControl {
    private int minOrphans;

    public ParagraphOrphansControl(int minOrphans2) {
        this.minOrphans = minOrphans2;
    }

    public ParagraphOrphansControl setMinAllowedOrphans(int minOrphans2) {
        this.minOrphans = minOrphans2;
        return this;
    }

    public int getMinOrphans() {
        return this.minOrphans;
    }

    public void handleViolatedOrphans(ParagraphRenderer renderer, String message) {
        Logger logger = LoggerFactory.getLogger((Class<?>) ParagraphOrphansControl.class);
        if (renderer.getOccupiedArea() == null || renderer.getLines() == null) {
            logger.warn(LogMessageConstant.PREMATURE_CALL_OF_HANDLE_VIOLATION_METHOD);
            return;
        }
        logger.warn(MessageFormatUtil.format(LogMessageConstant.ORPHANS_CONSTRAINT_VIOLATED, Integer.valueOf(renderer.getOccupiedArea().getPageNumber()), Integer.valueOf(this.minOrphans), Integer.valueOf(renderer.getLines().size()), message));
    }
}
