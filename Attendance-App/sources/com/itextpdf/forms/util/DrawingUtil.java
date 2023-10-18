package com.itextpdf.forms.util;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.nio.charset.StandardCharsets;

public class DrawingUtil {
    protected static final String check = "0.8 0 0 0.8 0.3 0.5 cm 0 0 m\n0.066 -0.026 l\n0.137 -0.15 l\n0.259 0.081 0.46 0.391 0.553 0.461 c\n0.604 0.489 l\n0.703 0.492 l\n0.543 0.312 0.255 -0.205 0.154 -0.439 c\n0.069 -0.399 l\n0.035 -0.293 -0.039 -0.136 -0.091 -0.057 c\nh\nf\n";
    protected static final String circle = "1 0 0 1 0.86 0.5 cm 0 0 m\n0 0.204 -0.166 0.371 -0.371 0.371 c\n-0.575 0.371 -0.741 0.204 -0.741 0 c\n-0.741 -0.204 -0.575 -0.371 -0.371 -0.371 c\n-0.166 -0.371 0 -0.204 0 0 c\nf\n";
    protected static final String cross = "1 0 0 1 0.80 0.8 cm 0 0 m\n-0.172 -0.027 l\n-0.332 -0.184 l\n-0.443 -0.019 l\n-0.475 -0.009 l\n-0.568 -0.168 l\n-0.453 -0.324 l\n-0.58 -0.497 l\n-0.59 -0.641 l\n-0.549 -0.627 l\n-0.543 -0.612 -0.457 -0.519 -0.365 -0.419 c\n-0.163 -0.572 l\n-0.011 -0.536 l\n-0.004 -0.507 l\n-0.117 -0.441 l\n-0.246 -0.294 l\n-0.132 -0.181 l\n0.031 -0.04 l\nh\nf\n";
    protected static final String diamond = "1 0 0 1 0.5 0.12 cm 0 0 m\n0.376 0.376 l\n0 0.751 l\n-0.376 0.376 l\nh\nf\n";
    protected static final String square = "1 0 0 1 0.835 0.835 cm 0 0 -0.669 -0.67 re\nf\n";
    protected static final String star = "0.95 0 0 0.95 0.85 0.6 cm 0 0 m\n-0.291 0 l\n-0.381 0.277 l\n-0.47 0 l\n-0.761 0 l\n-0.526 -0.171 l\n-0.616 -0.448 l\n-0.381 -0.277 l\n-0.145 -0.448 l\n-0.236 -0.171 l\nh\nf\n";

    private static void drawPdfAAppearanceString(PdfCanvas canvas, float width, float height, float moveX, float moveY, String appearanceString) {
        canvas.saveState();
        canvas.resetFillColorRgb();
        canvas.concatMatrix((double) width, 0.0d, 0.0d, (double) height, (double) moveX, (double) moveY);
        canvas.getContentStream().getOutputStream().writeBytes(appearanceString.getBytes(StandardCharsets.ISO_8859_1));
        canvas.restoreState();
    }

    public static void drawPdfACheck(PdfCanvas canvas, float width, float height, float moveX, float moveY) {
        drawPdfAAppearanceString(canvas, width, height, moveX, moveY, check);
    }

    public static void drawPdfACheck(PdfCanvas canvas, float width, float height) {
        drawPdfAAppearanceString(canvas, width, height, 0.0f, 0.0f, check);
    }

    public static void drawPdfACircle(PdfCanvas canvas, float width, float height, float moveX, float moveY) {
        drawPdfAAppearanceString(canvas, width, height, moveX, moveY, circle);
    }

    public static void drawPdfACircle(PdfCanvas canvas, float width, float height) {
        drawPdfAAppearanceString(canvas, width, height, 0.0f, 0.0f, circle);
    }

    public static void drawPdfACross(PdfCanvas canvas, float width, float height, float moveX, float moveY) {
        drawPdfAAppearanceString(canvas, width, height, moveX, moveY, cross);
    }

    public static void drawPdfACross(PdfCanvas canvas, float width, float height) {
        drawPdfAAppearanceString(canvas, width, height, 0.0f, 0.0f, cross);
    }

    public static void drawPdfADiamond(PdfCanvas canvas, float width, float height, float moveX, float moveY) {
        drawPdfAAppearanceString(canvas, width, height, moveX, moveY, diamond);
    }

    public static void drawPdfADiamond(PdfCanvas canvas, float width, float height) {
        drawPdfAAppearanceString(canvas, width, height, 0.0f, 0.0f, diamond);
    }

    public static void drawPdfASquare(PdfCanvas canvas, float width, float height, float moveX, float moveY) {
        drawPdfAAppearanceString(canvas, width, height, moveX, moveY, square);
    }

    public static void drawPdfASquare(PdfCanvas canvas, float width, float height) {
        drawPdfAAppearanceString(canvas, width, height, 0.0f, 0.0f, square);
    }

    public static void drawPdfAStar(PdfCanvas canvas, float width, float height, float moveX, float moveY) {
        drawPdfAAppearanceString(canvas, width, height, moveX, moveY, star);
    }

    public static void drawPdfAStar(PdfCanvas canvas, float width, float height) {
        drawPdfAAppearanceString(canvas, width, height, 0.0f, 0.0f, star);
    }

    public static void drawCross(PdfCanvas canvas, float width, float height, float borderWidth) {
        float offset = borderWidth * 2.0f;
        canvas.moveTo((double) (((width - height) / 2.0f) + offset), (double) (height - offset)).lineTo((double) (((width + height) / 2.0f) - offset), (double) offset).moveTo((double) (((width + height) / 2.0f) - offset), (double) (height - offset)).lineTo((double) (((width - height) / 2.0f) + offset), (double) offset).stroke();
    }

    public static void drawCircle(PdfCanvas canvas, float centerX, float centerY, float radius) {
        canvas.circle((double) centerX, (double) centerY, (double) radius).fill();
    }
}
