package com.itextpdf.barcodes;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import org.slf4j.Marker;

public class Barcode39 extends Barcode1D {
    private static final byte[][] BARS = {new byte[]{0, 0, 0, 1, 1, 0, 1, 0, 0}, new byte[]{1, 0, 0, 1, 0, 0, 0, 0, 1}, new byte[]{0, 0, 1, 1, 0, 0, 0, 0, 1}, new byte[]{1, 0, 1, 1, 0, 0, 0, 0, 0}, new byte[]{0, 0, 0, 1, 1, 0, 0, 0, 1}, new byte[]{1, 0, 0, 1, 1, 0, 0, 0, 0}, new byte[]{0, 0, 1, 1, 1, 0, 0, 0, 0}, new byte[]{0, 0, 0, 1, 0, 0, 1, 0, 1}, new byte[]{1, 0, 0, 1, 0, 0, 1, 0, 0}, new byte[]{0, 0, 1, 1, 0, 0, 1, 0, 0}, new byte[]{1, 0, 0, 0, 0, 1, 0, 0, 1}, new byte[]{0, 0, 1, 0, 0, 1, 0, 0, 1}, new byte[]{1, 0, 1, 0, 0, 1, 0, 0, 0}, new byte[]{0, 0, 0, 0, 1, 1, 0, 0, 1}, new byte[]{1, 0, 0, 0, 1, 1, 0, 0, 0}, new byte[]{0, 0, 1, 0, 1, 1, 0, 0, 0}, new byte[]{0, 0, 0, 0, 0, 1, 1, 0, 1}, new byte[]{1, 0, 0, 0, 0, 1, 1, 0, 0}, new byte[]{0, 0, 1, 0, 0, 1, 1, 0, 0}, new byte[]{0, 0, 0, 0, 1, 1, 1, 0, 0}, new byte[]{1, 0, 0, 0, 0, 0, 0, 1, 1}, new byte[]{0, 0, 1, 0, 0, 0, 0, 1, 1}, new byte[]{1, 0, 1, 0, 0, 0, 0, 1, 0}, new byte[]{0, 0, 0, 0, 1, 0, 0, 1, 1}, new byte[]{1, 0, 0, 0, 1, 0, 0, 1, 0}, new byte[]{0, 0, 1, 0, 1, 0, 0, 1, 0}, new byte[]{0, 0, 0, 0, 0, 0, 1, 1, 1}, new byte[]{1, 0, 0, 0, 0, 0, 1, 1, 0}, new byte[]{0, 0, 1, 0, 0, 0, 1, 1, 0}, new byte[]{0, 0, 0, 0, 1, 0, 1, 1, 0}, new byte[]{1, 1, 0, 0, 0, 0, 0, 0, 1}, new byte[]{0, 1, 1, 0, 0, 0, 0, 0, 1}, new byte[]{1, 1, 1, 0, 0, 0, 0, 0, 0}, new byte[]{0, 1, 0, 0, 1, 0, 0, 0, 1}, new byte[]{1, 1, 0, 0, 1, 0, 0, 0, 0}, new byte[]{0, 1, 1, 0, 1, 0, 0, 0, 0}, new byte[]{0, 1, 0, 0, 0, 0, 1, 0, 1}, new byte[]{1, 1, 0, 0, 0, 0, 1, 0, 0}, new byte[]{0, 1, 1, 0, 0, 0, 1, 0, 0}, new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0}, new byte[]{0, 1, 0, 1, 0, 0, 0, 1, 0}, new byte[]{0, 1, 0, 0, 0, 1, 0, 1, 0}, new byte[]{0, 0, 0, 1, 0, 1, 0, 1, 0}, new byte[]{0, 1, 0, 0, 1, 0, 1, 0, 0}};
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*";
    private static final String EXTENDED = "%U$A$B$C$D$E$F$G$H$I$J$K$L$M$N$O$P$Q$R$S$T$U$V$W$X$Y$Z%A%B%C%D%E  /A/B/C/D/E/F/G/H/I/J/K/L - ./O 0 1 2 3 4 5 6 7 8 9/Z%F%G%H%I%J%V A B C D E F G H I J K L M N O P Q R S T U V W X Y Z%K%L%M%N%O%W+A+B+C+D+E+F+G+H+I+J+K+L+M+N+O+P+Q+R+S+T+U+V+W+X+Y+Z%P%Q%R%S%T";

    public Barcode39(PdfDocument document) {
        this(document, document.getDefaultFont());
    }

    public Barcode39(PdfDocument document, PdfFont font) {
        super(document);
        this.f1171x = 0.8f;
        this.f1170n = 2.0f;
        this.font = font;
        this.size = 8.0f;
        this.baseline = this.size;
        this.barHeight = this.size * 3.0f;
        this.generateChecksum = false;
        this.checksumText = false;
        this.startStopText = true;
        this.extended = false;
    }

    public static byte[] getBarsCode39(String text) {
        String text2 = Marker.ANY_MARKER + text + Marker.ANY_MARKER;
        byte[] bars = new byte[((text2.length() * 10) - 1)];
        int k = 0;
        while (k < text2.length()) {
            char ch = text2.charAt(k);
            int idx = CHARS.indexOf(ch);
            if (ch == '*' && k != 0 && k != text2.length() - 1) {
                throw new IllegalArgumentException("The character " + ch + " is illegal in code 39");
            } else if (idx >= 0) {
                System.arraycopy(BARS[idx], 0, bars, k * 10, 9);
                k++;
            } else {
                throw new IllegalArgumentException("The character " + text2.charAt(k) + " is illegal in code 39");
            }
        }
        return bars;
    }

    public static String getCode39Ex(String text) {
        StringBuilder out = new StringBuilder("");
        int k = 0;
        while (k < text.length()) {
            char c = text.charAt(k);
            if (c <= 127) {
                char c1 = EXTENDED.charAt(c * 2);
                char c2 = EXTENDED.charAt((c * 2) + 1);
                if (c1 != ' ') {
                    out.append(c1);
                }
                out.append(c2);
                k++;
            } else {
                throw new IllegalArgumentException("The character " + c + " is illegal in code 39");
            }
        }
        return out.toString();
    }

    static char getChecksum(String text) {
        int chk = 0;
        int k = 0;
        while (k < text.length()) {
            int idx = CHARS.indexOf(text.charAt(k));
            char ch = text.charAt(k);
            if (ch == '*' && k != 0 && k != text.length() - 1) {
                throw new IllegalArgumentException("The character " + ch + " is illegal in code 39");
            } else if (idx >= 0) {
                chk += idx;
                k++;
            } else {
                throw new IllegalArgumentException("The character " + text.charAt(k) + " is illegal in code 39");
            }
        }
        return CHARS.charAt(chk % 43);
    }

    public Rectangle getBarcodeSize() {
        float fontX = 0.0f;
        float fontY = 0.0f;
        String fCode = this.code;
        if (this.extended) {
            fCode = getCode39Ex(this.code);
        }
        if (this.font != null) {
            if (this.baseline > 0.0f) {
                fontY = this.baseline - getDescender();
            } else {
                fontY = (-this.baseline) + this.size;
            }
            String fullCode = this.code;
            if (this.generateChecksum && this.checksumText) {
                fullCode = fullCode + getChecksum(fCode);
            }
            if (this.startStopText) {
                fullCode = Marker.ANY_MARKER + fullCode + Marker.ANY_MARKER;
            }
            fontX = this.font.getWidth(this.altText != null ? this.altText : fullCode, this.size);
        }
        int len = fCode.length() + 2;
        if (this.generateChecksum) {
            len++;
        }
        return new Rectangle(Math.max((((float) len) * ((this.f1171x * 6.0f) + (this.f1171x * 3.0f * this.f1170n))) + (((float) (len - 1)) * this.f1171x), fontX), this.barHeight + fontY);
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
        byte[] bars;
        float textStartY;
        float barStartY;
        float textStartX;
        float fullWidth;
        int len;
        String bCode;
        float fontX;
        Color color = textColor;
        String fullCode = this.code;
        float fontX2 = 0.0f;
        String bCode2 = this.code;
        if (this.extended) {
            bCode2 = getCode39Ex(this.code);
        }
        if (this.font != null) {
            if (this.generateChecksum && this.checksumText) {
                fullCode = fullCode + getChecksum(bCode2);
            }
            if (this.startStopText) {
                fullCode = Marker.ANY_MARKER + fullCode + Marker.ANY_MARKER;
            }
            PdfFont pdfFont = this.font;
            String str = this.altText != null ? this.altText : fullCode;
            fullCode = str;
            fontX2 = pdfFont.getWidth(str, this.size);
        }
        if (this.generateChecksum) {
            bCode2 = bCode2 + getChecksum(bCode2);
        }
        int len2 = bCode2.length() + 2;
        float fullWidth2 = (((float) len2) * ((this.f1171x * 6.0f) + (this.f1171x * 3.0f * this.f1170n))) + (((float) (len2 - 1)) * this.f1171x);
        float barStartX = 0.0f;
        float textStartX2 = 0.0f;
        switch (this.textAlignment) {
            case 1:
                break;
            case 2:
                if (fontX2 <= fullWidth2) {
                    textStartX2 = fullWidth2 - fontX2;
                    break;
                } else {
                    barStartX = fontX2 - fullWidth2;
                    break;
                }
            default:
                if (fontX2 <= fullWidth2) {
                    textStartX2 = (fullWidth2 - fontX2) / 2.0f;
                    break;
                } else {
                    barStartX = (fontX2 - fullWidth2) / 2.0f;
                    break;
                }
        }
        float barStartY2 = 0.0f;
        float textStartY2 = 0.0f;
        if (this.font != null) {
            if (this.baseline <= 0.0f) {
                textStartY2 = this.barHeight - this.baseline;
            } else {
                textStartY2 = -getDescender();
                barStartY2 = textStartY2 + this.baseline;
            }
        }
        byte[] bars2 = getBarsCode39(bCode2);
        boolean print = true;
        if (barColor != null) {
            canvas.setFillColor(barColor);
        }
        int k = 0;
        while (k < bars2.length) {
            float w = bars2[k] == 0 ? this.f1171x : this.f1171x * this.f1170n;
            if (print) {
                fontX = fontX2;
                bCode = bCode2;
                len = len2;
                fullWidth = fullWidth2;
                textStartX = textStartX2;
                barStartY = barStartY2;
                textStartY = textStartY2;
                bars = bars2;
                canvas.rectangle((double) barStartX, (double) barStartY2, (double) (w - this.inkSpreading), (double) this.barHeight);
            } else {
                fontX = fontX2;
                bCode = bCode2;
                len = len2;
                fullWidth = fullWidth2;
                textStartX = textStartX2;
                barStartY = barStartY2;
                textStartY = textStartY2;
                bars = bars2;
            }
            print = !print;
            barStartX += w;
            k++;
            fontX2 = fontX;
            bCode2 = bCode;
            len2 = len;
            fullWidth2 = fullWidth;
            textStartX2 = textStartX;
            barStartY2 = barStartY;
            textStartY2 = textStartY;
            bars2 = bars;
        }
        String str2 = bCode2;
        int i = len2;
        float f = fullWidth2;
        float textStartX3 = textStartX2;
        float f2 = barStartY2;
        float textStartY3 = textStartY2;
        byte[] bArr = bars2;
        canvas.fill();
        if (this.font != null) {
            if (color != null) {
                canvas.setFillColor(color);
            } else {
                PdfCanvas pdfCanvas = canvas;
            }
            canvas.beginText().setFontAndSize(this.font, this.size).setTextMatrix(textStartX3, textStartY3).showText(fullCode).endText();
        } else {
            PdfCanvas pdfCanvas2 = canvas;
            float f3 = textStartX3;
            float f4 = textStartY3;
        }
        return getBarcodeSize();
    }

    public Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        int f = foreground == null ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
        int g = background == null ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
        Canvas canvas = new Canvas();
        String bCode = this.code;
        if (this.extended) {
            bCode = getCode39Ex(this.code);
        }
        if (this.generateChecksum) {
            bCode = bCode + getChecksum(bCode);
        }
        int len = bCode.length() + 2;
        int nn = (int) this.f1170n;
        int fullWidth = (((nn * 3) + 6) * len) + (len - 1);
        byte[] bars = getBarsCode39(bCode);
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
