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

public class BarcodeInter25 extends Barcode1D {
    private static final byte[][] BARS = {new byte[]{0, 0, 1, 1, 0}, new byte[]{1, 0, 0, 0, 1}, new byte[]{0, 1, 0, 0, 1}, new byte[]{1, 1, 0, 0, 0}, new byte[]{0, 0, 1, 0, 1}, new byte[]{1, 0, 1, 0, 0}, new byte[]{0, 1, 1, 0, 0}, new byte[]{0, 0, 0, 1, 1}, new byte[]{1, 0, 0, 1, 0}, new byte[]{0, 1, 0, 1, 0}};

    public BarcodeInter25(PdfDocument document) {
        this(document, document.getDefaultFont());
    }

    public BarcodeInter25(PdfDocument document, PdfFont font) {
        super(document);
        this.f1171x = 0.8f;
        this.f1170n = 2.0f;
        this.font = font;
        this.size = 8.0f;
        this.baseline = this.size;
        this.barHeight = this.size * 3.0f;
        this.textAlignment = 3;
        this.generateChecksum = false;
        this.checksumText = false;
    }

    public static String keepNumbers(String text) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < text.length(); k++) {
            char c = text.charAt(k);
            if (c >= '0' && c <= '9') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static char getChecksum(String text) {
        int mul = 3;
        int total = 0;
        for (int k = text.length() - 1; k >= 0; k--) {
            total += mul * (text.charAt(k) - '0');
            mul ^= 2;
        }
        return (char) (((10 - (total % 10)) % 10) + 48);
    }

    public static byte[] getBarsInter25(String text) {
        String text2 = keepNumbers(text);
        if ((text2.length() & 1) == 0) {
            byte[] bars = new byte[((text2.length() * 5) + 7)];
            int pb = 0 + 1;
            bars[0] = 0;
            int pb2 = pb + 1;
            bars[pb] = 0;
            int pb3 = pb2 + 1;
            bars[pb2] = 0;
            int pb4 = pb3 + 1;
            bars[pb3] = 0;
            int len = text2.length() / 2;
            for (int k = 0; k < len; k++) {
                byte[][] bArr = BARS;
                byte[] b1 = bArr[text2.charAt(k * 2) - '0'];
                byte[] b2 = bArr[text2.charAt((k * 2) + 1) - '0'];
                for (int j = 0; j < 5; j++) {
                    int pb5 = pb4 + 1;
                    bars[pb4] = b1[j];
                    pb4 = pb5 + 1;
                    bars[pb5] = b2[j];
                }
            }
            int pb6 = pb4 + 1;
            bars[pb4] = 1;
            int pb7 = pb6 + 1;
            bars[pb6] = 0;
            int i = pb7 + 1;
            bars[pb7] = 0;
            return bars;
        }
        throw new PdfException(PdfException.TextMustBeEven);
    }

    public Rectangle getBarcodeSize() {
        float fontX = 0.0f;
        float fontY = 0.0f;
        if (this.font != null) {
            if (this.baseline > 0.0f) {
                fontY = this.baseline - getDescender();
            } else {
                fontY = (-this.baseline) + this.size;
            }
            String fullCode = this.code;
            if (this.generateChecksum && this.checksumText) {
                fullCode = fullCode + getChecksum(fullCode);
            }
            fontX = this.font.getWidth(this.altText != null ? this.altText : fullCode, this.size);
        }
        int len = keepNumbers(this.code).length();
        if (this.generateChecksum) {
            len++;
        }
        return new Rectangle(Math.max((((float) len) * ((this.f1171x * 3.0f) + (this.f1171x * 2.0f * this.f1170n))) + ((this.f1170n + 6.0f) * this.f1171x), fontX), this.barHeight + fontY);
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
        if (this.font != null) {
            if (this.generateChecksum && this.checksumText) {
                fullCode2 = fullCode2 + getChecksum(fullCode2);
            }
            PdfFont pdfFont = this.font;
            String fullCode3 = this.altText != null ? this.altText : fullCode2;
            fullCode = fullCode3;
            fontX = pdfFont.getWidth(fullCode3, this.size);
        } else {
            fullCode = fullCode2;
            fontX = 0.0f;
        }
        String bCode3 = keepNumbers(this.code);
        if (this.generateChecksum) {
            bCode = bCode3 + getChecksum(bCode3);
        } else {
            bCode = bCode3;
        }
        float fullWidth = (((float) bCode.length()) * ((this.f1171x * 3.0f) + (this.f1171x * 2.0f * this.f1170n))) + ((this.f1170n + 6.0f) * this.f1171x);
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
        byte[] bars2 = getBarsInter25(bCode);
        if (barColor != null) {
            canvas.setFillColor(barColor);
        }
        float barStartX3 = barStartX2;
        boolean print = true;
        int k2 = 0;
        while (k2 < bars2.length) {
            float w = bars2[k2] == 0 ? this.f1171x : this.f1171x * this.f1170n;
            if (print) {
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
            print = !print;
            barStartX3 = barStartX + w;
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
        int f = foreground == null ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
        int g = background == null ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
        Canvas canvas = new Canvas();
        String bCode = keepNumbers(this.code);
        if (this.generateChecksum) {
            bCode = bCode + getChecksum(bCode);
        }
        int len = bCode.length();
        int nn = (int) this.f1170n;
        int fullWidth = (((nn * 2) + 3) * len) + nn + 6;
        byte[] bars = getBarsInter25(bCode);
        int height = (int) this.barHeight;
        int[] pix = new int[(fullWidth * height)];
        int k = 0;
        boolean print = true;
        int ptr = 0;
        while (true) {
            boolean z = false;
            if (k >= bars.length) {
                break;
            }
            int w = bars[k] == 0 ? 1 : nn;
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
        }
        for (int k2 = fullWidth; k2 < pix.length; k2 += fullWidth) {
            System.arraycopy(pix, 0, pix, k2, fullWidth);
        }
        MemoryImageSource memoryImageSource = r9;
        int[] iArr = pix;
        MemoryImageSource memoryImageSource2 = new MemoryImageSource(fullWidth, height, pix, 0, fullWidth);
        return canvas.createImage(memoryImageSource);
    }
}
