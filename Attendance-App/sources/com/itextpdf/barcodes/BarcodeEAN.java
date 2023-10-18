package com.itextpdf.barcodes;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.util.Arrays;

public class BarcodeEAN extends Barcode1D {
    private static final byte[][] BARS = {new byte[]{3, 2, 1, 1}, new byte[]{2, 2, 2, 1}, new byte[]{2, 1, 2, 2}, new byte[]{1, 4, 1, 1}, new byte[]{1, 1, 3, 2}, new byte[]{1, 2, 3, 1}, new byte[]{1, 1, 1, 4}, new byte[]{1, 3, 1, 2}, new byte[]{1, 2, 1, 3}, new byte[]{3, 1, 1, 2}};
    public static final int EAN13 = 1;
    public static final int EAN8 = 2;
    private static final int EVEN = 1;
    private static final int[] GUARD_EAN13 = {0, 2, 28, 30, 56, 58};
    private static final int[] GUARD_EAN8 = {0, 2, 20, 22, 40, 42};
    private static final int[] GUARD_EMPTY = new int[0];
    private static final int[] GUARD_UPCA = {0, 2, 4, 6, 28, 30, 52, 54, 56, 58};
    private static final int[] GUARD_UPCE = {0, 2, 28, 30, 32};
    private static final int ODD = 0;
    private static final byte[][] PARITY13 = {new byte[]{0, 0, 0, 0, 0, 0}, new byte[]{0, 0, 1, 0, 1, 1}, new byte[]{0, 0, 1, 1, 0, 1}, new byte[]{0, 0, 1, 1, 1, 0}, new byte[]{0, 1, 0, 0, 1, 1}, new byte[]{0, 1, 1, 0, 0, 1}, new byte[]{0, 1, 1, 1, 0, 0}, new byte[]{0, 1, 0, 1, 0, 1}, new byte[]{0, 1, 0, 1, 1, 0}, new byte[]{0, 1, 1, 0, 1, 0}};
    private static final byte[][] PARITY2 = {new byte[]{0, 0}, new byte[]{0, 1}, new byte[]{1, 0}, new byte[]{1, 1}};
    private static final byte[][] PARITY5 = {new byte[]{1, 1, 0, 0, 0}, new byte[]{1, 0, 1, 0, 0}, new byte[]{1, 0, 0, 1, 0}, new byte[]{1, 0, 0, 0, 1}, new byte[]{0, 1, 1, 0, 0}, new byte[]{0, 0, 1, 1, 0}, new byte[]{0, 0, 0, 1, 1}, new byte[]{0, 1, 0, 1, 0}, new byte[]{0, 1, 0, 0, 1}, new byte[]{0, 0, 1, 0, 1}};
    private static final byte[][] PARITYE = {new byte[]{1, 1, 1, 0, 0, 0}, new byte[]{1, 1, 0, 1, 0, 0}, new byte[]{1, 1, 0, 0, 1, 0}, new byte[]{1, 1, 0, 0, 0, 1}, new byte[]{1, 0, 1, 1, 0, 0}, new byte[]{1, 0, 0, 1, 1, 0}, new byte[]{1, 0, 0, 0, 1, 1}, new byte[]{1, 0, 1, 0, 1, 0}, new byte[]{1, 0, 1, 0, 0, 1}, new byte[]{1, 0, 0, 1, 0, 1}};
    public static final int SUPP2 = 5;
    public static final int SUPP5 = 6;
    private static final float[] TEXTPOS_EAN13 = {6.5f, 13.5f, 20.5f, 27.5f, 34.5f, 41.5f, 53.5f, 60.5f, 67.5f, 74.5f, 81.5f, 88.5f};
    private static final float[] TEXTPOS_EAN8 = {6.5f, 13.5f, 20.5f, 27.5f, 39.5f, 46.5f, 53.5f, 60.5f};
    private static final int TOTALBARS_EAN13 = 59;
    private static final int TOTALBARS_EAN8 = 43;
    private static final int TOTALBARS_SUPP2 = 13;
    private static final int TOTALBARS_SUPP5 = 31;
    private static final int TOTALBARS_UPCE = 33;
    public static final int UPCA = 3;
    public static final int UPCE = 4;

    public BarcodeEAN(PdfDocument document) {
        this(document, document.getDefaultFont());
    }

    public BarcodeEAN(PdfDocument document, PdfFont font) {
        super(document);
        this.f1171x = 0.8f;
        this.font = font;
        this.size = 8.0f;
        this.baseline = this.size;
        this.barHeight = this.size * 3.0f;
        this.guardBars = true;
        this.codeType = 1;
        this.code = "";
    }

    public static int calculateEANParity(String code) {
        int mul = 3;
        int total = 0;
        for (int k = code.length() - 1; k >= 0; k--) {
            total += mul * (code.charAt(k) - '0');
            mul ^= 2;
        }
        return (10 - (total % 10)) % 10;
    }

    public static String convertUPCAtoUPCE(String text) {
        if (text.length() != 12 || (!text.startsWith("0") && !text.startsWith("1"))) {
            return null;
        }
        if (text.substring(3, 6).equals("000") || text.substring(3, 6).equals("100") || text.substring(3, 6).equals("200")) {
            if (text.substring(6, 8).equals("00")) {
                return text.substring(0, 1) + text.substring(1, 3) + text.substring(8, 11) + text.substring(3, 4) + text.substring(11);
            }
        } else if (text.substring(4, 6).equals("00")) {
            if (text.substring(6, 9).equals("000")) {
                return text.substring(0, 1) + text.substring(1, 4) + text.substring(9, 11) + "3" + text.substring(11);
            }
        } else if (text.substring(5, 6).equals("0")) {
            if (text.substring(6, 10).equals("0000")) {
                return text.substring(0, 1) + text.substring(1, 5) + text.substring(10, 11) + "4" + text.substring(11);
            }
        } else if (text.charAt(10) >= '5' && text.substring(6, 10).equals("0000")) {
            return text.substring(0, 1) + text.substring(1, 6) + text.substring(10, 11) + text.substring(11);
        }
        return null;
    }

    public static byte[] getBarsEAN13(String _code) {
        int[] code = new int[_code.length()];
        for (int k = 0; k < code.length; k++) {
            code[k] = _code.charAt(k) - '0';
        }
        byte[] bars = new byte[59];
        int pb = 0 + 1;
        bars[0] = 1;
        int pb2 = pb + 1;
        bars[pb] = 1;
        int pb3 = pb2 + 1;
        bars[pb2] = 1;
        byte[] sequence = PARITY13[code[0]];
        for (int k2 = 0; k2 < sequence.length; k2++) {
            byte[] stripes = BARS[code[k2 + 1]];
            if (sequence[k2] == 0) {
                int pb4 = pb3 + 1;
                bars[pb3] = stripes[0];
                int pb5 = pb4 + 1;
                bars[pb4] = stripes[1];
                int pb6 = pb5 + 1;
                bars[pb5] = stripes[2];
                pb3 = pb6 + 1;
                bars[pb6] = stripes[3];
            } else {
                int pb7 = pb3 + 1;
                bars[pb3] = stripes[3];
                int pb8 = pb7 + 1;
                bars[pb7] = stripes[2];
                int pb9 = pb8 + 1;
                bars[pb8] = stripes[1];
                pb3 = pb9 + 1;
                bars[pb9] = stripes[0];
            }
        }
        int pb10 = pb3 + 1;
        bars[pb3] = 1;
        int pb11 = pb10 + 1;
        bars[pb10] = 1;
        int pb12 = pb11 + 1;
        bars[pb11] = 1;
        int pb13 = pb12 + 1;
        bars[pb12] = 1;
        int pb14 = pb13 + 1;
        bars[pb13] = 1;
        for (int k3 = 7; k3 < 13; k3++) {
            byte[] stripes2 = BARS[code[k3]];
            int pb15 = pb14 + 1;
            bars[pb14] = stripes2[0];
            int pb16 = pb15 + 1;
            bars[pb15] = stripes2[1];
            int pb17 = pb16 + 1;
            bars[pb16] = stripes2[2];
            pb14 = pb17 + 1;
            bars[pb17] = stripes2[3];
        }
        int pb18 = pb14 + 1;
        bars[pb14] = 1;
        int pb19 = pb18 + 1;
        bars[pb18] = 1;
        int i = pb19 + 1;
        bars[pb19] = 1;
        return bars;
    }

    public static byte[] getBarsEAN8(String _code) {
        int[] code = new int[_code.length()];
        for (int k = 0; k < code.length; k++) {
            code[k] = _code.charAt(k) - '0';
        }
        byte[] bars = new byte[43];
        int pb = 0 + 1;
        bars[0] = 1;
        int pb2 = pb + 1;
        bars[pb] = 1;
        int pb3 = pb2 + 1;
        bars[pb2] = 1;
        for (int k2 = 0; k2 < 4; k2++) {
            byte[] stripes = BARS[code[k2]];
            int pb4 = pb3 + 1;
            bars[pb3] = stripes[0];
            int pb5 = pb4 + 1;
            bars[pb4] = stripes[1];
            int pb6 = pb5 + 1;
            bars[pb5] = stripes[2];
            pb3 = pb6 + 1;
            bars[pb6] = stripes[3];
        }
        int pb7 = pb3 + 1;
        bars[pb3] = 1;
        int pb8 = pb7 + 1;
        bars[pb7] = 1;
        int pb9 = pb8 + 1;
        bars[pb8] = 1;
        int pb10 = pb9 + 1;
        bars[pb9] = 1;
        int pb11 = pb10 + 1;
        bars[pb10] = 1;
        for (int k3 = 4; k3 < 8; k3++) {
            byte[] stripes2 = BARS[code[k3]];
            int pb12 = pb11 + 1;
            bars[pb11] = stripes2[0];
            int pb13 = pb12 + 1;
            bars[pb12] = stripes2[1];
            int pb14 = pb13 + 1;
            bars[pb13] = stripes2[2];
            pb11 = pb14 + 1;
            bars[pb14] = stripes2[3];
        }
        int pb15 = pb11 + 1;
        bars[pb11] = 1;
        int pb16 = pb15 + 1;
        bars[pb15] = 1;
        int i = pb16 + 1;
        bars[pb16] = 1;
        return bars;
    }

    public static byte[] getBarsUPCE(String _code) {
        int[] code = new int[_code.length()];
        for (int k = 0; k < code.length; k++) {
            code[k] = _code.charAt(k) - '0';
        }
        byte[] bars = new byte[33];
        boolean flip = code[0] != 0;
        int pb = 0 + 1;
        bars[0] = 1;
        int pb2 = pb + 1;
        bars[pb] = 1;
        int pb3 = pb2 + 1;
        bars[pb2] = 1;
        byte[] sequence = PARITYE[code[code.length - 1]];
        for (int k2 = 1; k2 < code.length - 1; k2++) {
            byte[] stripes = BARS[code[k2]];
            if (sequence[k2 - 1] == (flip ? (byte) 1 : 0)) {
                int pb4 = pb3 + 1;
                bars[pb3] = stripes[0];
                int pb5 = pb4 + 1;
                bars[pb4] = stripes[1];
                int pb6 = pb5 + 1;
                bars[pb5] = stripes[2];
                pb3 = pb6 + 1;
                bars[pb6] = stripes[3];
            } else {
                int pb7 = pb3 + 1;
                bars[pb3] = stripes[3];
                int pb8 = pb7 + 1;
                bars[pb7] = stripes[2];
                int pb9 = pb8 + 1;
                bars[pb8] = stripes[1];
                pb3 = pb9 + 1;
                bars[pb9] = stripes[0];
            }
        }
        int pb10 = pb3 + 1;
        bars[pb3] = 1;
        int pb11 = pb10 + 1;
        bars[pb10] = 1;
        int pb12 = pb11 + 1;
        bars[pb11] = 1;
        int pb13 = pb12 + 1;
        bars[pb12] = 1;
        int pb14 = pb13 + 1;
        bars[pb13] = 1;
        int i = pb14 + 1;
        bars[pb14] = 1;
        return bars;
    }

    public static byte[] getBarsSupplemental2(String _code) {
        int[] code = new int[2];
        for (int k = 0; k < code.length; k++) {
            code[k] = _code.charAt(k) - '0';
        }
        byte[] bars = new byte[13];
        int pb = 0 + 1;
        bars[0] = 1;
        int pb2 = pb + 1;
        bars[pb] = 1;
        int pb3 = pb2 + 1;
        bars[pb2] = 2;
        byte[] sequence = PARITY2[((code[0] * 10) + code[1]) % 4];
        for (int k2 = 0; k2 < sequence.length; k2++) {
            if (k2 == 1) {
                int pb4 = pb3 + 1;
                bars[pb3] = 1;
                pb3 = pb4 + 1;
                bars[pb4] = 1;
            }
            byte[] stripes = BARS[code[k2]];
            if (sequence[k2] == 0) {
                int pb5 = pb3 + 1;
                bars[pb3] = stripes[0];
                int pb6 = pb5 + 1;
                bars[pb5] = stripes[1];
                int pb7 = pb6 + 1;
                bars[pb6] = stripes[2];
                pb3 = pb7 + 1;
                bars[pb7] = stripes[3];
            } else {
                int pb8 = pb3 + 1;
                bars[pb3] = stripes[3];
                int pb9 = pb8 + 1;
                bars[pb8] = stripes[2];
                int pb10 = pb9 + 1;
                bars[pb9] = stripes[1];
                pb3 = pb10 + 1;
                bars[pb10] = stripes[0];
            }
        }
        return bars;
    }

    public static byte[] getBarsSupplemental5(String _code) {
        int[] code = new int[5];
        for (int k = 0; k < code.length; k++) {
            code[k] = _code.charAt(k) - '0';
        }
        byte[] bars = new byte[31];
        int pb = 0 + 1;
        bars[0] = 1;
        int pb2 = pb + 1;
        bars[pb] = 1;
        int pb3 = pb2 + 1;
        bars[pb2] = 2;
        byte[] sequence = PARITY5[((((code[0] + code[2]) + code[4]) * 3) + ((code[1] + code[3]) * 9)) % 10];
        for (int k2 = 0; k2 < sequence.length; k2++) {
            if (k2 != 0) {
                int pb4 = pb3 + 1;
                bars[pb3] = 1;
                pb3 = pb4 + 1;
                bars[pb4] = 1;
            }
            byte[] stripes = BARS[code[k2]];
            if (sequence[k2] == 0) {
                int pb5 = pb3 + 1;
                bars[pb3] = stripes[0];
                int pb6 = pb5 + 1;
                bars[pb5] = stripes[1];
                int pb7 = pb6 + 1;
                bars[pb6] = stripes[2];
                pb3 = pb7 + 1;
                bars[pb7] = stripes[3];
            } else {
                int pb8 = pb3 + 1;
                bars[pb3] = stripes[3];
                int pb9 = pb8 + 1;
                bars[pb8] = stripes[2];
                int pb10 = pb9 + 1;
                bars[pb9] = stripes[1];
                pb3 = pb10 + 1;
                bars[pb10] = stripes[0];
            }
        }
        return bars;
    }

    public Rectangle getBarcodeSize() {
        float width;
        float height = this.barHeight;
        if (this.font != null) {
            if (this.baseline <= 0.0f) {
                height += (-this.baseline) + this.size;
            } else {
                height += this.baseline - getDescender();
            }
        }
        switch (this.codeType) {
            case 1:
                width = this.f1171x * 95.0f;
                if (this.font != null) {
                    width += this.font.getWidth((int) this.code.charAt(0), this.size);
                    break;
                }
                break;
            case 2:
                width = this.f1171x * 67.0f;
                break;
            case 3:
                width = this.f1171x * 95.0f;
                if (this.font != null) {
                    width += this.font.getWidth((int) this.code.charAt(0), this.size) + this.font.getWidth((int) this.code.charAt(11), this.size);
                    break;
                }
                break;
            case 4:
                width = this.f1171x * 51.0f;
                if (this.font != null) {
                    width += this.font.getWidth((int) this.code.charAt(0), this.size) + this.font.getWidth((int) this.code.charAt(7), this.size);
                    break;
                }
                break;
            case 5:
                width = this.f1171x * 20.0f;
                break;
            case 6:
                width = this.f1171x * 47.0f;
                break;
            default:
                throw new PdfException("Invalid code type");
        }
        return new Rectangle(width, height);
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
        float textStartY;
        float barStartY;
        int[] guard;
        byte[] bars;
        float gd;
        Rectangle rect;
        int[] guard2;
        int k;
        byte[] bars2;
        float barStartY2;
        float barStartX;
        PdfCanvas pdfCanvas = canvas;
        Color color = textColor;
        Rectangle rect2 = getBarcodeSize();
        float barStartX2 = 0.0f;
        if (this.font == null) {
            barStartY = 0.0f;
            textStartY = 0.0f;
        } else if (this.baseline <= 0.0f) {
            barStartY = 0.0f;
            textStartY = this.barHeight - this.baseline;
        } else {
            float textStartY2 = -getDescender();
            barStartY = textStartY2 + this.baseline;
            textStartY = textStartY2;
        }
        switch (this.codeType) {
            case Float.MIN_VALUE:
            case 4.2E-45f:
            case 5.6E-45f:
                if (this.font != null) {
                    barStartX2 = 0.0f + this.font.getWidth((int) this.code.charAt(0), this.size);
                    break;
                }
                break;
        }
        int[] guard3 = GUARD_EMPTY;
        switch (this.codeType) {
            case 1:
                byte[] bars3 = getBarsEAN13(this.code);
                guard = GUARD_EAN13;
                bars = bars3;
                break;
            case 2:
                byte[] bars4 = getBarsEAN8(this.code);
                guard = GUARD_EAN8;
                bars = bars4;
                break;
            case 3:
                byte[] bars5 = getBarsEAN13("0" + this.code);
                guard = GUARD_UPCA;
                bars = bars5;
                break;
            case 4:
                byte[] bars6 = getBarsUPCE(this.code);
                guard = GUARD_UPCE;
                bars = bars6;
                break;
            case 5:
                guard = guard3;
                bars = getBarsSupplemental2(this.code);
                break;
            case 6:
                guard = guard3;
                bars = getBarsSupplemental5(this.code);
                break;
            default:
                throw new PdfException("Invalid code type");
        }
        float keepBarX = barStartX2;
        if (this.font == null || this.baseline <= 0.0f || !this.guardBars) {
            gd = 0.0f;
        } else {
            gd = this.baseline / 2.0f;
        }
        if (barColor != null) {
            canvas.setFillColor(barColor);
        }
        float barStartX3 = barStartX2;
        boolean print = true;
        int k2 = 0;
        while (k2 < bars.length) {
            float w = ((float) bars[k2]) * this.f1171x;
            if (!print) {
                k = k2;
                bars2 = bars;
                guard2 = guard;
                rect = rect2;
                barStartY2 = barStartY;
                barStartX = barStartX3;
            } else if (Arrays.binarySearch(guard, k2) >= 0) {
                bars2 = bars;
                k = k2;
                rect = rect2;
                barStartX = barStartX3;
                guard2 = guard;
                canvas.rectangle((double) barStartX3, (double) (barStartY - gd), (double) (w - this.inkSpreading), (double) (this.barHeight + gd));
                barStartY2 = barStartY;
            } else {
                k = k2;
                bars2 = bars;
                guard2 = guard;
                rect = rect2;
                barStartX = barStartX3;
                barStartY2 = barStartY;
                canvas.rectangle((double) barStartX, (double) barStartY2, (double) (w - this.inkSpreading), (double) this.barHeight);
            }
            print = !print;
            barStartX3 = barStartX + w;
            k2 = k + 1;
            barStartY = barStartY2;
            bars = bars2;
            guard = guard2;
            rect2 = rect;
        }
        int i = k2;
        byte[] bArr = bars;
        int[] iArr = guard;
        Rectangle rect3 = rect2;
        float f = barStartY;
        float f2 = barStartX3;
        canvas.fill();
        if (this.font != null) {
            if (color != null) {
                pdfCanvas.setFillColor(color);
            }
            canvas.beginText();
            pdfCanvas.setFontAndSize(this.font, this.size);
            switch (this.codeType) {
                case 1:
                    pdfCanvas.setTextMatrix(0.0f, textStartY);
                    pdfCanvas.showText(this.code.substring(0, 1));
                    for (int k3 = 1; k3 < 13; k3++) {
                        String c = this.code.substring(k3, k3 + 1);
                        pdfCanvas.setTextMatrix((keepBarX + (TEXTPOS_EAN13[k3 - 1] * this.f1171x)) - (this.font.getWidth(c, this.size) / 2.0f), textStartY);
                        pdfCanvas.showText(c);
                    }
                    break;
                case 2:
                    for (int k4 = 0; k4 < 8; k4++) {
                        String c2 = this.code.substring(k4, k4 + 1);
                        pdfCanvas.setTextMatrix((TEXTPOS_EAN8[k4] * this.f1171x) - (this.font.getWidth(c2, this.size) / 2.0f), textStartY);
                        pdfCanvas.showText(c2);
                    }
                    break;
                case 3:
                    pdfCanvas.setTextMatrix(0.0f, textStartY);
                    pdfCanvas.showText(this.code.substring(0, 1));
                    for (int k5 = 1; k5 < 11; k5++) {
                        String c3 = this.code.substring(k5, k5 + 1);
                        pdfCanvas.setTextMatrix((keepBarX + (TEXTPOS_EAN13[k5] * this.f1171x)) - (this.font.getWidth(c3, this.size) / 2.0f), textStartY);
                        pdfCanvas.showText(c3);
                    }
                    pdfCanvas.setTextMatrix(keepBarX + (this.f1171x * 95.0f), textStartY);
                    pdfCanvas.showText(this.code.substring(11, 12));
                    break;
                case 4:
                    pdfCanvas.setTextMatrix(0.0f, textStartY);
                    pdfCanvas.showText(this.code.substring(0, 1));
                    for (int k6 = 1; k6 < 7; k6++) {
                        String c4 = this.code.substring(k6, k6 + 1);
                        pdfCanvas.setTextMatrix((keepBarX + (TEXTPOS_EAN13[k6 - 1] * this.f1171x)) - (this.font.getWidth(c4, this.size) / 2.0f), textStartY);
                        pdfCanvas.showText(c4);
                    }
                    pdfCanvas.setTextMatrix(keepBarX + (this.f1171x * 51.0f), textStartY);
                    pdfCanvas.showText(this.code.substring(7, 8));
                    break;
                case 5:
                case 6:
                    for (int k7 = 0; k7 < this.code.length(); k7++) {
                        String c5 = this.code.substring(k7, k7 + 1);
                        pdfCanvas.setTextMatrix(((((float) (k7 * 9)) + 7.5f) * this.f1171x) - (this.font.getWidth(c5, this.size) / 2.0f), textStartY);
                        pdfCanvas.showText(c5);
                    }
                    break;
            }
            canvas.endText();
        }
        return rect3;
    }

    public Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        int width;
        byte[] bars;
        int f = foreground == null ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
        int g = background == null ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
        Canvas canvas = new Canvas();
        switch (this.codeType) {
            case 1:
                bars = getBarsEAN13(this.code);
                width = 95;
                break;
            case 2:
                bars = getBarsEAN8(this.code);
                width = 67;
                break;
            case 3:
                bars = getBarsEAN13("0" + this.code);
                width = 95;
                break;
            case 4:
                bars = getBarsUPCE(this.code);
                width = 51;
                break;
            case 5:
                bars = getBarsSupplemental2(this.code);
                width = 20;
                break;
            case 6:
                bars = getBarsSupplemental5(this.code);
                width = 47;
                break;
            default:
                throw new PdfException("Invalid code type");
        }
        int height = (int) this.barHeight;
        int[] pix = new int[(width * height)];
        int k = 0;
        boolean print = true;
        int ptr = 0;
        while (true) {
            boolean z = false;
            if (k < bars.length) {
                byte w = bars[k];
                int c = g;
                if (print) {
                    c = f;
                }
                if (!print) {
                    z = true;
                }
                print = z;
                int j = 0;
                while (j < w) {
                    pix[ptr] = c;
                    j++;
                    ptr++;
                }
                k++;
            } else {
                for (int k2 = width; k2 < pix.length; k2 += width) {
                    System.arraycopy(pix, 0, pix, k2, width);
                }
                MemoryImageSource memoryImageSource = r5;
                MemoryImageSource memoryImageSource2 = new MemoryImageSource(width, height, pix, 0, width);
                return canvas.createImage(memoryImageSource);
            }
        }
    }
}
