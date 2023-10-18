package com.itextpdf.barcodes;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class BarcodePostnet extends Barcode1D {
    private static final byte[][] BARS = {new byte[]{1, 1, 0, 0, 0}, new byte[]{0, 0, 0, 1, 1}, new byte[]{0, 0, 1, 0, 1}, new byte[]{0, 0, 1, 1, 0}, new byte[]{0, 1, 0, 0, 1}, new byte[]{0, 1, 0, 1, 0}, new byte[]{0, 1, 1, 0, 0}, new byte[]{1, 0, 0, 0, 1}, new byte[]{1, 0, 0, 1, 0}, new byte[]{1, 0, 1, 0, 0}};
    public static int TYPE_PLANET = 2;
    public static int TYPE_POSTNET = 1;

    public BarcodePostnet(PdfDocument document) {
        super(document);
        this.f1170n = 3.2727273f;
        this.f1171x = 1.4399999f;
        this.barHeight = 9.0f;
        this.size = 3.6000001f;
        this.codeType = TYPE_POSTNET;
    }

    public static byte[] getBarsPostnet(String text) {
        int total = 0;
        for (int k = text.length() - 1; k >= 0; k--) {
            total += text.charAt(k) - '0';
        }
        String text2 = text + ((char) (((10 - (total % 10)) % 10) + 48));
        byte[] bars = new byte[((text2.length() * 5) + 2)];
        bars[0] = 1;
        bars[bars.length - 1] = 1;
        for (int k2 = 0; k2 < text2.length(); k2++) {
            System.arraycopy(BARS[text2.charAt(k2) - '0'], 0, bars, (k2 * 5) + 1, 5);
        }
        return bars;
    }

    public Rectangle getBarcodeSize() {
        return new Rectangle((((float) (((this.code.length() + 1) * 5) + 1)) * this.f1170n) + this.f1171x, this.barHeight);
    }

    public void fitWidth(float width) {
        byte[] bars = getBarsPostnet(this.code);
        this.f1171x *= width / getBarcodeSize().getWidth();
        this.f1170n = (width - this.f1171x) / ((float) (bars.length - 1));
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
        if (barColor != null) {
            canvas.setFillColor(barColor);
        }
        byte[] bars = getBarsPostnet(this.code);
        byte flip = 1;
        if (this.codeType == TYPE_PLANET) {
            flip = 0;
            bars[0] = 0;
            bars[bars.length - 1] = 0;
        }
        float startX = 0.0f;
        for (int k = 0; k < bars.length; k++) {
            canvas.rectangle((double) startX, 0.0d, (double) (this.f1171x - this.inkSpreading), (double) (bars[k] == flip ? this.barHeight : this.size));
            startX += this.f1170n;
        }
        canvas.fill();
        return getBarcodeSize();
    }

    public Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        byte flip;
        int f = foreground == null ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
        int g = background == null ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
        Canvas canvas = new Canvas();
        int barWidth = (int) this.f1171x;
        if (barWidth <= 0) {
            barWidth = 1;
        }
        int barDistance = (int) this.f1170n;
        if (barDistance <= barWidth) {
            barDistance = barWidth + 1;
        }
        int barShort = (int) this.size;
        if (barShort <= 0) {
            barShort = 1;
        }
        int barTall = (int) this.barHeight;
        if (barTall <= barShort) {
            barTall = barShort + 1;
        }
        int width = ((((this.code.length() + 1) * 5) + 1) * barDistance) + barWidth;
        int[] pix = new int[(width * barTall)];
        byte[] bars = getBarsPostnet(this.code);
        if (this.codeType == TYPE_PLANET) {
            bars[0] = 0;
            bars[bars.length - 1] = 0;
            flip = 0;
        } else {
            flip = 1;
        }
        int idx = 0;
        for (int k = 0; k < bars.length; k++) {
            boolean dot = bars[k] == flip;
            int j = 0;
            while (j < barDistance) {
                pix[idx + j] = (!dot || j >= barWidth) ? g : f;
                j++;
            }
            idx += barDistance;
        }
        int limit = width * (barTall - barShort);
        for (int k2 = width; k2 < limit; k2 += width) {
            System.arraycopy(pix, 0, pix, k2, width);
        }
        int idx2 = limit;
        for (int k3 = 0; k3 < bars.length; k3++) {
            int j2 = 0;
            while (j2 < barDistance) {
                pix[idx2 + j2] = j2 < barWidth ? f : g;
                j2++;
            }
            idx2 += barDistance;
        }
        for (int k4 = limit + width; k4 < pix.length; k4 += width) {
            System.arraycopy(pix, limit, pix, k4, width);
        }
        MemoryImageSource memoryImageSource = r8;
        byte b = flip;
        int i = limit;
        byte[] bArr = bars;
        MemoryImageSource memoryImageSource2 = new MemoryImageSource(width, barTall, pix, 0, width);
        return canvas.createImage(memoryImageSource);
    }
}
