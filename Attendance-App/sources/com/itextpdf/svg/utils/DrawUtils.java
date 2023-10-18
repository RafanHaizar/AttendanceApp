package com.itextpdf.svg.utils;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.util.List;

public class DrawUtils {
    private DrawUtils() {
    }

    @Deprecated
    public static void arc(float x1, float y1, float x2, float y2, float startAng, float extent, PdfCanvas cv) {
        arc((double) x1, (double) y1, (double) x2, (double) y2, (double) startAng, (double) extent, cv);
    }

    public static void arc(double x1, double y1, double x2, double y2, double startAng, double extent, PdfCanvas cv) {
        List<double[]> ar = PdfCanvas.bezierArc(x1, y1, x2, y2, startAng, extent);
        if (!ar.isEmpty()) {
            for (double[] pt : ar) {
                cv.curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);
            }
        }
    }
}
