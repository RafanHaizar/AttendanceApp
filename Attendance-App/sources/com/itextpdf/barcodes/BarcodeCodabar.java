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

public class BarcodeCodabar extends Barcode1D {
    private static final byte[][] BARS = {new byte[]{0, 0, 0, 0, 0, 1, 1}, new byte[]{0, 0, 0, 0, 1, 1, 0}, new byte[]{0, 0, 0, 1, 0, 0, 1}, new byte[]{1, 1, 0, 0, 0, 0, 0}, new byte[]{0, 0, 1, 0, 0, 1, 0}, new byte[]{1, 0, 0, 0, 0, 1, 0}, new byte[]{0, 1, 0, 0, 0, 0, 1}, new byte[]{0, 1, 0, 0, 1, 0, 0}, new byte[]{0, 1, 1, 0, 0, 0, 0}, new byte[]{1, 0, 0, 1, 0, 0, 0}, new byte[]{0, 0, 0, 1, 1, 0, 0}, new byte[]{0, 0, 1, 1, 0, 0, 0}, new byte[]{1, 0, 0, 0, 1, 0, 1}, new byte[]{1, 0, 1, 0, 0, 0, 1}, new byte[]{1, 0, 1, 0, 1, 0, 0}, new byte[]{0, 0, 1, 0, 1, 0, 1}, new byte[]{0, 0, 1, 1, 0, 1, 0}, new byte[]{0, 1, 0, 1, 0, 0, 1}, new byte[]{0, 0, 0, 1, 0, 1, 1}, new byte[]{0, 0, 0, 1, 1, 1, 0}};
    private static final String CHARS = "0123456789-$:/.+ABCD";
    private static final int START_STOP_IDX = 16;

    public BarcodeCodabar(PdfDocument document) {
        this(document, document.getDefaultFont());
    }

    public BarcodeCodabar(PdfDocument document, PdfFont font) {
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
        this.startStopText = false;
    }

    public static byte[] getBarsCodabar(String text) {
        String text2 = text.toUpperCase();
        int len = text2.length();
        if (len < 2) {
            throw new IllegalArgumentException(PdfException.CodabarMustHaveAtLeastStartAndStopCharacter);
        } else if (CHARS.indexOf(text2.charAt(0)) < 16 || CHARS.indexOf(text2.charAt(len - 1)) < 16) {
            throw new IllegalArgumentException(PdfException.CodabarMustHaveOneAbcdAsStartStopCharacter);
        } else {
            byte[] bars = new byte[((text2.length() * 8) - 1)];
            int k = 0;
            while (k < len) {
                int idx = CHARS.indexOf(text2.charAt(k));
                if (idx >= 16 && k > 0 && k < len - 1) {
                    throw new IllegalArgumentException(PdfException.InCodabarStartStopCharactersAreOnlyAllowedAtTheExtremes);
                } else if (idx >= 0) {
                    System.arraycopy(BARS[idx], 0, bars, k * 8, 7);
                    k++;
                } else {
                    throw new IllegalArgumentException(PdfException.IllegalCharacterInCodabarBarcode);
                }
            }
            return bars;
        }
    }

    public static String calculateChecksum(String code) {
        if (code.length() < 2) {
            return code;
        }
        String text = code.toUpperCase();
        int sum = 0;
        int len = text.length();
        for (int k = 0; k < len; k++) {
            sum += CHARS.indexOf(text.charAt(k));
        }
        return code.substring(0, len - 1) + CHARS.charAt((((sum + 15) / 16) * 16) - sum) + code.substring(len - 1);
    }

    public Rectangle getBarcodeSize() {
        float fontX = 0.0f;
        float fontY = 0.0f;
        String text = this.code;
        if (this.generateChecksum && this.checksumText) {
            text = calculateChecksum(this.code);
        }
        if (!this.startStopText) {
            text = text.substring(1, text.length() - 1);
        }
        if (this.font != null) {
            if (this.baseline > 0.0f) {
                fontY = this.baseline - getDescender();
            } else {
                fontY = (-this.baseline) + this.size;
            }
            fontX = this.font.getWidth(this.altText != null ? this.altText : text, this.size);
        }
        String text2 = this.code;
        if (this.generateChecksum) {
            text2 = calculateChecksum(this.code);
        }
        byte[] bars = getBarsCodabar(text2);
        int wide = 0;
        for (byte b : bars) {
            wide += b;
        }
        return new Rectangle(Math.max(this.f1171x * (((float) (bars.length - wide)) + (((float) wide) * this.f1170n)), fontX), this.barHeight + fontY);
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
        float fontX;
        String fullCode;
        float textStartX;
        float barStartY;
        float textStartY;
        int k;
        int narrow;
        int wide;
        float barStartY2;
        float textStartY2;
        String fullCode2;
        float fontX2;
        float textStartY3;
        float barStartX;
        PdfCanvas pdfCanvas = canvas;
        Color color = textColor;
        String fullCode3 = this.code;
        if (this.generateChecksum && this.checksumText) {
            fullCode3 = calculateChecksum(this.code);
        }
        if (!this.startStopText) {
            fullCode3 = fullCode3.substring(1, fullCode3.length() - 1);
        }
        if (this.font != null) {
            PdfFont pdfFont = this.font;
            String fullCode4 = this.altText != null ? this.altText : fullCode3;
            fullCode = fullCode4;
            fontX = pdfFont.getWidth(fullCode4, this.size);
        } else {
            fullCode = fullCode3;
            fontX = 0.0f;
        }
        byte[] bars = getBarsCodabar(this.generateChecksum ? calculateChecksum(this.code) : this.code);
        int wide2 = 0;
        for (byte b : bars) {
            wide2 += b;
        }
        int narrow2 = bars.length - wide2;
        float fullWidth = this.f1171x * (((float) narrow2) + (((float) wide2) * this.f1170n));
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
            float textStartY4 = -getDescender();
            barStartY = textStartY4 + this.baseline;
            textStartY = textStartY4;
        }
        if (barColor != null) {
            canvas.setFillColor(barColor);
        }
        float barStartX3 = barStartX2;
        boolean print = true;
        int k2 = 0;
        while (k2 < bars.length) {
            float w = bars[k2] == 0 ? this.f1171x : this.f1171x * this.f1170n;
            if (print) {
                fullCode2 = fullCode;
                k = k2;
                textStartY3 = textStartY;
                barStartX = barStartX3;
                textStartY2 = fontX;
                fontX2 = textStartX;
                barStartY2 = barStartY;
                wide = wide2;
                narrow = narrow2;
                canvas.rectangle((double) barStartX3, (double) barStartY, (double) (w - this.inkSpreading), (double) this.barHeight);
            } else {
                k = k2;
                barStartX = barStartX3;
                barStartY2 = barStartY;
                wide = wide2;
                narrow = narrow2;
                fullCode2 = fullCode;
                textStartY2 = fontX;
                textStartY3 = textStartY;
                fontX2 = textStartX;
            }
            print = !print;
            barStartX3 = barStartX + w;
            k2 = k + 1;
            textStartY = textStartY3;
            textStartX = fontX2;
            fullCode = fullCode2;
            fontX = textStartY2;
            barStartY = barStartY2;
            wide2 = wide;
            narrow2 = narrow;
        }
        int i = k2;
        float f = barStartX3;
        float f2 = barStartY;
        int i2 = wide2;
        int i3 = narrow2;
        String fullCode5 = fullCode;
        float f3 = fontX;
        float textStartY5 = textStartY;
        float fontX3 = textStartX;
        canvas.fill();
        if (this.font != null) {
            if (color != null) {
                pdfCanvas.setFillColor(color);
            }
            canvas.beginText();
            pdfCanvas.setFontAndSize(this.font, this.size);
            pdfCanvas.setTextMatrix(fontX3, textStartY5);
            pdfCanvas.showText(fullCode5);
            canvas.endText();
        }
        return getBarcodeSize();
    }

    public Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        int f = foreground == null ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
        int g = background == null ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
        Canvas canvas = new Canvas();
        byte[] bars = getBarsCodabar(this.generateChecksum ? calculateChecksum(this.code) : this.code);
        int wide = 0;
        for (byte b : bars) {
            wide += b;
        }
        int fullWidth = (((int) this.f1170n) * wide) + (bars.length - wide);
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
            int w = bars[k] == 0 ? 1 : (int) this.f1170n;
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
        MemoryImageSource memoryImageSource = r8;
        MemoryImageSource memoryImageSource2 = new MemoryImageSource(fullWidth, height, pix, 0, fullWidth);
        return canvas.createImage(memoryImageSource);
    }
}
