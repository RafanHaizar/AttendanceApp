package com.itextpdf.barcodes;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class BarcodeMSI extends Barcode1D {
    private static final byte[][] BARS = {new byte[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, new byte[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0}, new byte[]{1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0}, new byte[]{1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0}, new byte[]{1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0}, new byte[]{1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0}, new byte[]{1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0}, new byte[]{1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0}, new byte[]{1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0}, new byte[]{1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0}};
    private static final byte[] BARS_END = {1, 0, 0, 1};
    private static final int BARS_FOR_START = 3;
    private static final int BARS_FOR_STOP = 4;
    private static final int BARS_PER_CHARACTER = 12;
    private static final byte[] BARS_START = {1, 1, 0};
    private static final String CHARS = "0123456789";

    public BarcodeMSI(PdfDocument document) {
        this(document, document.getDefaultFont());
    }

    public BarcodeMSI(PdfDocument document, PdfFont font) {
        super(document);
        this.f1171x = 0.8f;
        this.f1170n = 2.0f;
        this.font = font;
        this.size = 8.0f;
        this.baseline = this.size;
        this.barHeight = this.size * 3.0f;
        this.generateChecksum = false;
        this.checksumText = false;
    }

    public Rectangle getBarcodeSize() {
        float fontX = 0.0f;
        float fontY = 0.0f;
        String fCode = this.code;
        if (this.font != null) {
            if (this.baseline > 0.0f) {
                fontY = this.baseline - getDescender();
            } else {
                fontY = (-this.baseline) + this.size;
            }
            fontX = this.font.getWidth(this.altText != null ? this.altText : this.code, this.size);
        }
        int len = fCode.length();
        if (this.generateChecksum) {
            len++;
        }
        return new Rectangle(Math.max(((float) ((len * 12) + 3 + 4)) * this.f1171x, fontX), this.barHeight + fontY);
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
        float fontX;
        String fullCode;
        String bCode;
        float textStartX;
        float barStartY;
        float textStartY;
        String bCode2;
        float textStartX2;
        float fontX2;
        byte[] bars;
        float barStartX;
        int k;
        float textStartX3;
        float fontX3;
        PdfCanvas pdfCanvas = canvas;
        Color color = textColor;
        String fullCode2 = this.code;
        if (this.checksumText) {
            fullCode2 = fullCode2 + Integer.toString(getChecksum(this.code));
        }
        if (this.font != null) {
            String var10001 = this.altText != null ? this.altText : fullCode2;
            fullCode = this.altText != null ? this.altText : fullCode2;
            fontX = this.font.getWidth(var10001, this.size);
        } else {
            fullCode = fullCode2;
            fontX = 0.0f;
        }
        String bCode3 = this.code;
        if (this.generateChecksum) {
            bCode = bCode3 + getChecksum(bCode3);
        } else {
            bCode = bCode3;
        }
        float fullWidth = ((float) ((bCode.length() * 12) + 3 + 4)) * this.f1171x;
        float barStartX2 = 0.0f;
        switch (this.textAlignment) {
            case 1:
                textStartX = 0.0f;
                break;
            case 2:
                if (fontX <= fullWidth) {
                    textStartX = fullWidth - fontX;
                    break;
                } else {
                    barStartX2 = fontX - fullWidth;
                    textStartX = 0.0f;
                    break;
                }
            default:
                if (fontX <= fullWidth) {
                    textStartX = (fullWidth - fontX) / 2.0f;
                    break;
                } else {
                    barStartX2 = (fontX - fullWidth) / 2.0f;
                    textStartX = 0.0f;
                    break;
                }
        }
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
        byte[] bars2 = getBarsMSI(bCode);
        if (barColor != null) {
            canvas.setFillColor(barColor);
        }
        float barStartX3 = barStartX2;
        int k2 = 0;
        while (k2 < bars2.length) {
            float w = ((float) bars2[k2]) * this.f1171x;
            if (bars2[k2] == 1) {
                barStartX = barStartX3;
                k = k2;
                fontX2 = fontX;
                fontX3 = textStartY;
                bars = bars2;
                bCode2 = bCode;
                textStartX3 = textStartX;
                textStartX2 = barStartY;
                canvas.rectangle((double) barStartX3, (double) barStartY, (double) (w - this.inkSpreading), (double) this.barHeight);
            } else {
                barStartX = barStartX3;
                k = k2;
                bars = bars2;
                textStartX2 = barStartY;
                fontX2 = fontX;
                bCode2 = bCode;
                fontX3 = textStartY;
                textStartX3 = textStartX;
            }
            barStartX3 = barStartX + this.f1171x;
            k2 = k + 1;
            textStartY = fontX3;
            textStartX = textStartX3;
            bars2 = bars;
            fontX = fontX2;
            barStartY = textStartX2;
            bCode = bCode2;
        }
        float f = barStartX3;
        int i = k2;
        byte[] bArr = bars2;
        float f2 = barStartY;
        float f3 = fontX;
        String str = bCode;
        float fontX4 = textStartY;
        float textStartX4 = textStartX;
        canvas.fill();
        if (this.font != null) {
            if (color != null) {
                pdfCanvas.setFillColor(color);
            }
            canvas.beginText();
            pdfCanvas.setFontAndSize(this.font, this.size);
            pdfCanvas.setTextMatrix(textStartX4, fontX4);
            pdfCanvas.showText(fullCode);
            canvas.endText();
        }
        return getBarcodeSize();
    }

    public Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        int foregroundColor = foreground == null ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
        int backgroundColor = background == null ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
        Canvas canvas = new Canvas();
        String bCode = this.code;
        if (this.generateChecksum) {
            bCode = bCode + Integer.toString(getChecksum(this.code));
        }
        byte[] bars = getBarsMSI(bCode);
        int fullWidth = bars.length;
        int fullHeight = (int) this.barHeight;
        int[] pix = new int[(fullWidth * fullHeight)];
        for (int x = 0; x < bars.length; x++) {
            int color = bars[x] == 1 ? foregroundColor : backgroundColor;
            for (int y = 0; y < fullHeight; y++) {
                pix[(y * fullWidth) + x] = color;
            }
        }
        return canvas.createImage(new MemoryImageSource(fullWidth, fullHeight, pix, 0, fullWidth));
    }

    public static byte[] getBarsMSI(String text) {
        if (text != null) {
            byte[] bars = new byte[((text.length() * 12) + 7)];
            System.arraycopy(BARS_START, 0, bars, 0, 3);
            int x = 0;
            while (x < text.length()) {
                int idx = CHARS.indexOf(text.charAt(x));
                if (idx >= 0) {
                    System.arraycopy(BARS[idx], 0, bars, (x * 12) + 3, 12);
                    x++;
                } else {
                    throw new IllegalArgumentException("The character " + text.charAt(x) + " is illegal in MSI bar codes.");
                }
            }
            System.arraycopy(BARS_END, 0, bars, bars.length - 4, 4);
            return bars;
        }
        throw new IllegalArgumentException("Valid code required to generate MSI barcode.");
    }

    public static int getChecksum(String text) {
        if (text != null) {
            int[] digits = new int[text.length()];
            for (int x = 0; x < text.length(); x++) {
                digits[x] = text.charAt(x) - '0';
                if (digits[x] < 0 || digits[x] > 9) {
                    throw new IllegalArgumentException("The character " + text.charAt(x) + " is illegal in MSI bar codes.");
                }
            }
            int sum = 0;
            int length = digits.length;
            for (int i = 0; i < length; i++) {
                int digit = digits[(length - i) - 1];
                if (i % 2 == 0) {
                    digit *= 2;
                }
                sum += digit > 9 ? digit - 9 : digit;
            }
            return (sum * 9) % 10;
        }
        throw new IllegalArgumentException("Valid code required to generate checksum for MSI barcode");
    }
}
