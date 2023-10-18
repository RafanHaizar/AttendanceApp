package com.itextpdf.barcodes;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.p026io.font.constants.FontWeights;
import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.util.HashMap;
import java.util.Map;

public class Barcode128 extends Barcode1D {
    private static final byte[][] BARS = {new byte[]{2, 1, 2, 2, 2, 2}, new byte[]{2, 2, 2, 1, 2, 2}, new byte[]{2, 2, 2, 2, 2, 1}, new byte[]{1, 2, 1, 2, 2, 3}, new byte[]{1, 2, 1, 3, 2, 2}, new byte[]{1, 3, 1, 2, 2, 2}, new byte[]{1, 2, 2, 2, 1, 3}, new byte[]{1, 2, 2, 3, 1, 2}, new byte[]{1, 3, 2, 2, 1, 2}, new byte[]{2, 2, 1, 2, 1, 3}, new byte[]{2, 2, 1, 3, 1, 2}, new byte[]{2, 3, 1, 2, 1, 2}, new byte[]{1, 1, 2, 2, 3, 2}, new byte[]{1, 2, 2, 1, 3, 2}, new byte[]{1, 2, 2, 2, 3, 1}, new byte[]{1, 1, 3, 2, 2, 2}, new byte[]{1, 2, 3, 1, 2, 2}, new byte[]{1, 2, 3, 2, 2, 1}, new byte[]{2, 2, 3, 2, 1, 1}, new byte[]{2, 2, 1, 1, 3, 2}, new byte[]{2, 2, 1, 2, 3, 1}, new byte[]{2, 1, 3, 2, 1, 2}, new byte[]{2, 2, 3, 1, 1, 2}, new byte[]{3, 1, 2, 1, 3, 1}, new byte[]{3, 1, 1, 2, 2, 2}, new byte[]{3, 2, 1, 1, 2, 2}, new byte[]{3, 2, 1, 2, 2, 1}, new byte[]{3, 1, 2, 2, 1, 2}, new byte[]{3, 2, 2, 1, 1, 2}, new byte[]{3, 2, 2, 2, 1, 1}, new byte[]{2, 1, 2, 1, 2, 3}, new byte[]{2, 1, 2, 3, 2, 1}, new byte[]{2, 3, 2, 1, 2, 1}, new byte[]{1, 1, 1, 3, 2, 3}, new byte[]{1, 3, 1, 1, 2, 3}, new byte[]{1, 3, 1, 3, 2, 1}, new byte[]{1, 1, 2, 3, 1, 3}, new byte[]{1, 3, 2, 1, 1, 3}, new byte[]{1, 3, 2, 3, 1, 1}, new byte[]{2, 1, 1, 3, 1, 3}, new byte[]{2, 3, 1, 1, 1, 3}, new byte[]{2, 3, 1, 3, 1, 1}, new byte[]{1, 1, 2, 1, 3, 3}, new byte[]{1, 1, 2, 3, 3, 1}, new byte[]{1, 3, 2, 1, 3, 1}, new byte[]{1, 1, 3, 1, 2, 3}, new byte[]{1, 1, 3, 3, 2, 1}, new byte[]{1, 3, 3, 1, 2, 1}, new byte[]{3, 1, 3, 1, 2, 1}, new byte[]{2, 1, 1, 3, 3, 1}, new byte[]{2, 3, 1, 1, 3, 1}, new byte[]{2, 1, 3, 1, 1, 3}, new byte[]{2, 1, 3, 3, 1, 1}, new byte[]{2, 1, 3, 1, 3, 1}, new byte[]{3, 1, 1, 1, 2, 3}, new byte[]{3, 1, 1, 3, 2, 1}, new byte[]{3, 3, 1, 1, 2, 1}, new byte[]{3, 1, 2, 1, 1, 3}, new byte[]{3, 1, 2, 3, 1, 1}, new byte[]{3, 3, 2, 1, 1, 1}, new byte[]{3, 1, 4, 1, 1, 1}, new byte[]{2, 2, 1, 4, 1, 1}, new byte[]{4, 3, 1, 1, 1, 1}, new byte[]{1, 1, 1, 2, 2, 4}, new byte[]{1, 1, 1, 4, 2, 2}, new byte[]{1, 2, 1, 1, 2, 4}, new byte[]{1, 2, 1, 4, 2, 1}, new byte[]{1, 4, 1, 1, 2, 2}, new byte[]{1, 4, 1, 2, 2, 1}, new byte[]{1, 1, 2, 2, 1, 4}, new byte[]{1, 1, 2, 4, 1, 2}, new byte[]{1, 2, 2, 1, 1, 4}, new byte[]{1, 2, 2, 4, 1, 1}, new byte[]{1, 4, 2, 1, 1, 2}, new byte[]{1, 4, 2, 2, 1, 1}, new byte[]{2, 4, 1, 2, 1, 1}, new byte[]{2, 2, 1, 1, 1, 4}, new byte[]{4, 1, 3, 1, 1, 1}, new byte[]{2, 4, 1, 1, 1, 2}, new byte[]{1, 3, 4, 1, 1, 1}, new byte[]{1, 1, 1, 2, 4, 2}, new byte[]{1, 2, 1, 1, 4, 2}, new byte[]{1, 2, 1, 2, 4, 1}, new byte[]{1, 1, 4, 2, 1, 2}, new byte[]{1, 2, 4, 1, 1, 2}, new byte[]{1, 2, 4, 2, 1, 1}, new byte[]{4, 1, 1, 2, 1, 2}, new byte[]{4, 2, 1, 1, 1, 2}, new byte[]{4, 2, 1, 2, 1, 1}, new byte[]{2, 1, 2, 1, 4, 1}, new byte[]{2, 1, 4, 1, 2, 1}, new byte[]{4, 1, 2, 1, 2, 1}, new byte[]{1, 1, 1, 1, 4, 3}, new byte[]{1, 1, 1, 3, 4, 1}, new byte[]{1, 3, 1, 1, 4, 1}, new byte[]{1, 1, 4, 1, 1, 3}, new byte[]{1, 1, 4, 3, 1, 1}, new byte[]{4, 1, 1, 1, 1, 3}, new byte[]{4, 1, 1, 3, 1, 1}, new byte[]{1, 1, 3, 1, 4, 1}, new byte[]{1, 1, 4, 1, 3, 1}, new byte[]{3, 1, 1, 1, 4, 1}, new byte[]{4, 1, 1, 1, 3, 1}, new byte[]{2, 1, 1, 4, 1, 2}, new byte[]{2, 1, 1, 2, 1, 4}, new byte[]{2, 1, 1, 2, 3, 2}};
    private static final byte[] BARS_STOP = {2, 3, 3, 1, 1, 1, 2};
    public static final int CODE128 = 1;
    public static final int CODE128_RAW = 3;
    public static final int CODE128_UCC = 2;
    public static final char CODE_A = 'È';
    public static final char CODE_AB_TO_C = 'c';
    public static final char CODE_AC_TO_B = 'd';
    public static final char CODE_BC_TO_A = 'e';
    public static final char CODE_C = 'Ç';
    public static final char DEL = 'Ã';
    public static final char FNC1 = 'Ê';
    public static final char FNC1_INDEX = 'f';
    public static final char FNC2 = 'Å';
    public static final char FNC3 = 'Ä';
    public static final char FNC4 = 'È';
    public static final char SHIFT = 'Æ';
    public static final char STARTA = 'Ë';
    public static final char STARTB = 'Ì';
    public static final char STARTC = 'Í';
    public static final char START_A = 'g';
    public static final char START_B = 'h';
    public static final char START_C = 'i';
    private static Map<Integer, Integer> ais;
    private Barcode128CodeSet codeSet;

    public enum Barcode128CodeSet {
        A,
        B,
        C,
        AUTO
    }

    static {
        HashMap hashMap = new HashMap();
        ais = hashMap;
        hashMap.put(0, 20);
        ais.put(1, 16);
        ais.put(2, 16);
        ais.put(10, -1);
        ais.put(11, 9);
        ais.put(12, 8);
        ais.put(13, 8);
        ais.put(15, 8);
        ais.put(17, 8);
        ais.put(20, 4);
        ais.put(21, -1);
        ais.put(22, -1);
        ais.put(23, -1);
        ais.put(240, -1);
        ais.put(241, -1);
        ais.put(Integer.valueOf(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION), -1);
        ais.put(251, -1);
        ais.put(252, -1);
        ais.put(30, -1);
        for (int k = 3100; k < 3700; k++) {
            ais.put(Integer.valueOf(k), 10);
        }
        ais.put(37, -1);
        for (int k2 = 3900; k2 < 3940; k2++) {
            ais.put(Integer.valueOf(k2), -1);
        }
        ais.put(Integer.valueOf(FontWeights.NORMAL), -1);
        ais.put(Integer.valueOf(TypedValues.CycleType.TYPE_CURVE_FIT), -1);
        ais.put(Integer.valueOf(TypedValues.CycleType.TYPE_VISIBILITY), 20);
        ais.put(Integer.valueOf(TypedValues.CycleType.TYPE_ALPHA), -1);
        for (int k3 = 410; k3 < 416; k3++) {
            ais.put(Integer.valueOf(k3), 16);
        }
        ais.put(Integer.valueOf(TypedValues.CycleType.TYPE_EASING), -1);
        ais.put(Integer.valueOf(TypedValues.CycleType.TYPE_WAVE_SHAPE), -1);
        ais.put(Integer.valueOf(TypedValues.CycleType.TYPE_CUSTOM_WAVE_SHAPE), 6);
        ais.put(Integer.valueOf(TypedValues.CycleType.TYPE_WAVE_PERIOD), -1);
        ais.put(Integer.valueOf(TypedValues.CycleType.TYPE_WAVE_OFFSET), 6);
        ais.put(Integer.valueOf(TypedValues.CycleType.TYPE_WAVE_PHASE), 6);
        ais.put(426, 6);
        ais.put(7001, 17);
        ais.put(7002, -1);
        for (int k4 = 7030; k4 < 7040; k4++) {
            ais.put(Integer.valueOf(k4), -1);
        }
        ais.put(8001, 18);
        ais.put(8002, -1);
        ais.put(8003, -1);
        ais.put(8004, -1);
        ais.put(8005, 10);
        ais.put(8006, 22);
        ais.put(8007, -1);
        ais.put(8008, -1);
        ais.put(8018, 22);
        ais.put(8020, -1);
        ais.put(8100, 10);
        ais.put(8101, 14);
        ais.put(8102, 6);
        for (int k5 = 90; k5 < 100; k5++) {
            ais.put(Integer.valueOf(k5), -1);
        }
    }

    public Barcode128(PdfDocument document) {
        this(document, document.getDefaultFont());
    }

    public Barcode128(PdfDocument document, PdfFont font) {
        super(document);
        this.codeSet = Barcode128CodeSet.AUTO;
        this.f1171x = 0.8f;
        this.font = font;
        this.size = 8.0f;
        this.baseline = this.size;
        this.barHeight = this.size * 3.0f;
        this.textAlignment = 3;
        this.codeType = 1;
    }

    public void setCodeSet(Barcode128CodeSet codeSet2) {
        this.codeSet = codeSet2;
    }

    public Barcode128CodeSet getCodeSet() {
        return this.codeSet;
    }

    public static String removeFNC1(String code) {
        int len = code.length();
        StringBuilder buf = new StringBuilder(len);
        for (int k = 0; k < len; k++) {
            char c = code.charAt(k);
            if (c >= ' ' && c <= '~') {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    public static String getHumanReadableUCCEAN(String code) {
        StringBuilder buf = new StringBuilder();
        String fnc1 = new String(new char[]{FNC1});
        while (true) {
            if (code.startsWith(fnc1)) {
                code = code.substring(1);
            } else {
                int n = 0;
                int idlen = 0;
                int k = 2;
                while (true) {
                    if (k >= 5 || code.length() < k) {
                        break;
                    }
                    int subcode = Integer.parseInt(code.substring(0, k));
                    n = ais.containsKey(Integer.valueOf(subcode)) ? ais.get(Integer.valueOf(subcode)).intValue() : 0;
                    if (n != 0) {
                        idlen = k;
                        break;
                    }
                    k++;
                }
                if (idlen == 0) {
                    break;
                }
                buf.append('(').append(code.substring(0, idlen)).append(')');
                code = code.substring(idlen);
                if (n > 0) {
                    int n2 = n - idlen;
                    if (code.length() <= n2) {
                        break;
                    }
                    buf.append(removeFNC1(code.substring(0, n2)));
                    code = code.substring(n2);
                } else {
                    int idx = code.indexOf(XMPError.BADRDF);
                    if (idx < 0) {
                        break;
                    }
                    buf.append(code.substring(0, idx));
                    code = code.substring(idx + 1);
                }
            }
        }
        buf.append(removeFNC1(code));
        return buf.toString();
    }

    public static String getRawText(String text, boolean ucc, Barcode128CodeSet codeSet2) {
        int index;
        String out;
        String str = text;
        Barcode128CodeSet barcode128CodeSet = codeSet2;
        int tLen = text.length();
        if (tLen == 0) {
            String out2 = "" + getStartSymbol(codeSet2);
            if (ucc) {
                return out2 + FNC1_INDEX;
            }
            return out2;
        }
        int k = 0;
        while (k < tLen) {
            int c = str.charAt(k);
            if (c <= 127 || c == 202) {
                k++;
            } else {
                throw new PdfException(PdfException.ThereAreIllegalCharactersForBarcode128In1);
            }
        }
        int c2 = str.charAt(0);
        char currentCode = getStartSymbol(codeSet2);
        int i = 2;
        if ((barcode128CodeSet == Barcode128CodeSet.AUTO || barcode128CodeSet == Barcode128CodeSet.C) && isNextDigits(str, 0, 2)) {
            currentCode = START_C;
            String out3 = "" + START_C;
            if (ucc) {
                out3 = out3 + FNC1_INDEX;
            }
            String out22 = getPackedRawDigits(str, 0, 2);
            index = 0 + out22.charAt(0);
            out = out3 + out22.substring(1);
        } else if (c2 < 32) {
            currentCode = START_A;
            String out4 = "" + START_A;
            if (ucc) {
                out4 = out4 + FNC1_INDEX;
            }
            out = out4 + ((char) (c2 + 64));
            index = 0 + 1;
        } else {
            String out5 = "" + currentCode;
            if (ucc) {
                out5 = out5 + FNC1_INDEX;
            }
            if (c2 == 202) {
                out = out5 + FNC1_INDEX;
            } else {
                out = out5 + ((char) (c2 - 32));
            }
            index = 0 + 1;
        }
        if (barcode128CodeSet == Barcode128CodeSet.AUTO || currentCode == getStartSymbol(codeSet2)) {
            while (index < tLen) {
                switch (currentCode) {
                    case 'g':
                        if (barcode128CodeSet == Barcode128CodeSet.AUTO && isNextDigits(str, index, 4)) {
                            String out23 = getPackedRawDigits(str, index, 4);
                            index += out23.charAt(0);
                            out = (out + CODE_AB_TO_C) + out23.substring(1);
                            currentCode = 'i';
                            break;
                        } else {
                            int index2 = index + 1;
                            int c3 = str.charAt(index);
                            if (c3 != 202) {
                                if (c3 <= 95) {
                                    if (c3 >= 32) {
                                        out = out + ((char) (c3 - 32));
                                        index = index2;
                                        break;
                                    } else {
                                        out = out + ((char) (c3 + 64));
                                        index = index2;
                                        break;
                                    }
                                } else {
                                    currentCode = START_B;
                                    out = (out + CODE_AC_TO_B) + ((char) (c3 - 32));
                                    index = index2;
                                    break;
                                }
                            } else {
                                out = out + FNC1_INDEX;
                                index = index2;
                                break;
                            }
                        }
                        break;
                    case 'h':
                        if (barcode128CodeSet == Barcode128CodeSet.AUTO && isNextDigits(str, index, 4)) {
                            String out24 = getPackedRawDigits(str, index, 4);
                            index += out24.charAt(0);
                            out = (out + CODE_AB_TO_C) + out24.substring(1);
                            currentCode = 'i';
                            break;
                        } else {
                            int index3 = index + 1;
                            int c4 = str.charAt(index);
                            if (c4 != 202) {
                                if (c4 >= 32) {
                                    out = out + ((char) (c4 - 32));
                                    index = index3;
                                    break;
                                } else {
                                    currentCode = START_A;
                                    out = (out + CODE_BC_TO_A) + ((char) (c4 + 64));
                                    index = index3;
                                    break;
                                }
                            } else {
                                out = out + FNC1_INDEX;
                                index = index3;
                                break;
                            }
                        }
                    case 'i':
                        if (!isNextDigits(str, index, i)) {
                            int index4 = index + 1;
                            int c5 = str.charAt(index);
                            if (c5 != 202) {
                                if (c5 >= 32) {
                                    out = (out + CODE_AC_TO_B) + ((char) (c5 - 32));
                                    currentCode = 'h';
                                    index = index4;
                                    break;
                                } else {
                                    currentCode = START_A;
                                    out = (out + CODE_BC_TO_A) + ((char) (c5 + 64));
                                    index = index4;
                                    break;
                                }
                            } else {
                                out = out + FNC1_INDEX;
                                index = index4;
                                break;
                            }
                        } else {
                            String out25 = getPackedRawDigits(str, index, i);
                            index += out25.charAt(0);
                            out = out + out25.substring(1);
                            break;
                        }
                }
                if (barcode128CodeSet == Barcode128CodeSet.AUTO || currentCode == getStartSymbol(codeSet2)) {
                    i = 2;
                } else {
                    throw new PdfException(PdfException.ThereAreIllegalCharactersForBarcode128In1);
                }
            }
            return out;
        }
        throw new PdfException(PdfException.ThereAreIllegalCharactersForBarcode128In1);
    }

    public static String getRawText(String text, boolean ucc) {
        return getRawText(text, ucc, Barcode128CodeSet.AUTO);
    }

    public static byte[] getBarsCode128Raw(String text) {
        int idx = text.indexOf(65535);
        if (idx >= 0) {
            text = text.substring(0, idx);
        }
        int chk = text.charAt(0);
        for (int k = 1; k < text.length(); k++) {
            chk += text.charAt(k) * k;
        }
        String text2 = text + ((char) (chk % 103));
        byte[] bars = new byte[(((text2.length() + 1) * 6) + 7)];
        int k2 = 0;
        while (k2 < text2.length()) {
            System.arraycopy(BARS[text2.charAt(k2)], 0, bars, k2 * 6, 6);
            k2++;
        }
        System.arraycopy(BARS_STOP, 0, bars, k2 * 6, 7);
        return bars;
    }

    public Rectangle getBarcodeSize() {
        String fullCode;
        String fullCode2;
        float fontX = 0.0f;
        float fontY = 0.0f;
        if (this.font != null) {
            if (this.baseline > 0.0f) {
                fontY = this.baseline - getDescender();
            } else {
                fontY = (-this.baseline) + this.size;
            }
            if (this.codeType == 3) {
                int idx = this.code.indexOf(65535);
                if (idx < 0) {
                    fullCode2 = "";
                } else {
                    fullCode2 = this.code.substring(idx + 1);
                }
            } else if (this.codeType == 2) {
                fullCode2 = getHumanReadableUCCEAN(this.code);
            } else {
                fullCode2 = removeFNC1(this.code);
            }
            fontX = this.font.getWidth(this.altText != null ? this.altText : fullCode2, this.size);
        }
        boolean z = false;
        if (this.codeType == 3) {
            int idx2 = this.code.indexOf(65535);
            if (idx2 >= 0) {
                fullCode = this.code.substring(0, idx2);
            } else {
                fullCode = this.code;
            }
        } else {
            String str = this.code;
            if (this.codeType == 2) {
                z = true;
            }
            fullCode = getRawText(str, z, this.codeSet);
        }
        return new Rectangle(Math.max((((float) ((fullCode.length() + 2) * 11)) * this.f1171x) + (this.f1171x * 2.0f), fontX), this.barHeight + fontY);
    }

    public Rectangle placeBarcode(PdfCanvas canvas, Color barColor, Color textColor) {
        String fullCode;
        String fullCode2;
        float fontX;
        String bCode;
        float textStartX;
        float barStartY;
        float textStartY;
        float barStartX;
        int k;
        float barStartY2;
        float textStartY2;
        float textStartY3;
        byte[] bars;
        float fontX2;
        String bCode2;
        PdfCanvas pdfCanvas = canvas;
        Color color = textColor;
        if (this.codeType == 3) {
            int idx = this.code.indexOf(65535);
            if (idx < 0) {
                fullCode = "";
            } else {
                fullCode = this.code.substring(idx + 1);
            }
        } else if (this.codeType == 2) {
            fullCode = getHumanReadableUCCEAN(this.code);
        } else {
            fullCode = removeFNC1(this.code);
        }
        if (this.font != null) {
            PdfFont pdfFont = this.font;
            String fullCode3 = this.altText != null ? this.altText : fullCode;
            fontX = pdfFont.getWidth(fullCode3, this.size);
            fullCode2 = fullCode3;
        } else {
            fontX = 0.0f;
            fullCode2 = fullCode;
        }
        if (this.codeType == 3) {
            int idx2 = this.code.indexOf(65535);
            if (idx2 >= 0) {
                bCode2 = this.code.substring(0, idx2);
            } else {
                bCode2 = this.code;
            }
            bCode = bCode2;
        } else {
            bCode = getRawText(this.code, this.codeType == 2, this.codeSet);
        }
        float fullWidth = (((float) ((bCode.length() + 2) * 11)) * this.f1171x) + (this.f1171x * 2.0f);
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
        byte[] bars2 = getBarsCode128Raw(bCode);
        if (barColor != null) {
            canvas.setFillColor(barColor);
        }
        float barStartX3 = barStartX2;
        boolean print = true;
        int k2 = 0;
        while (k2 < bars2.length) {
            float w = ((float) bars2[k2]) * this.f1171x;
            if (print) {
                barStartX = barStartX3;
                k = k2;
                textStartY3 = textStartY;
                bars = bars2;
                textStartY2 = fontX;
                fontX2 = textStartX;
                barStartY2 = barStartY;
                canvas.rectangle((double) barStartX3, (double) barStartY, (double) (w - this.inkSpreading), (double) this.barHeight);
            } else {
                barStartX = barStartX3;
                k = k2;
                textStartY3 = textStartY;
                bars = bars2;
                barStartY2 = barStartY;
                textStartY2 = fontX;
                fontX2 = textStartX;
            }
            print = !print;
            barStartX3 = barStartX + w;
            k2 = k + 1;
            textStartX = fontX2;
            bars2 = bars;
            textStartY = textStartY3;
            fontX = textStartY2;
            barStartY = barStartY2;
        }
        float f = barStartX3;
        int i = k2;
        float textStartY5 = textStartY;
        byte[] bArr = bars2;
        float f2 = barStartY;
        float f3 = fontX;
        float fontX3 = textStartX;
        canvas.fill();
        if (this.font != null) {
            if (color != null) {
                pdfCanvas.setFillColor(color);
            }
            canvas.beginText();
            pdfCanvas.setFontAndSize(this.font, this.size);
            pdfCanvas.setTextMatrix(fontX3, textStartY5);
            pdfCanvas.showText(fullCode2);
            canvas.endText();
        }
        return getBarcodeSize();
    }

    public void setCode(String code) {
        if (getCodeType() != 2 || !code.startsWith("(")) {
            super.setCode(code);
            return;
        }
        int idx = 0;
        StringBuilder ret = new StringBuilder("");
        while (idx >= 0) {
            int end = code.indexOf(41, idx);
            if (end >= 0) {
                String sai = code.substring(idx + 1, end);
                if (sai.length() >= 2) {
                    int ai = Integer.parseInt(sai);
                    int len = ais.get(Integer.valueOf(ai)).intValue();
                    if (len != 0) {
                        String sai2 = Integer.valueOf(ai).toString();
                        if (sai2.length() == 1) {
                            sai2 = "0" + sai2;
                        }
                        idx = code.indexOf(40, end);
                        int next = idx < 0 ? code.length() : idx;
                        ret.append(sai2).append(code.substring(end + 1, next));
                        if (len < 0) {
                            if (idx >= 0) {
                                ret.append(FNC1);
                            }
                        } else if (((next - end) - 1) + sai2.length() != len) {
                            throw new IllegalArgumentException("Invalid AI length");
                        }
                    } else {
                        throw new IllegalArgumentException("AI not found");
                    }
                } else {
                    throw new IllegalArgumentException("AI is too short");
                }
            } else {
                throw new IllegalArgumentException("Badly formed ucc string");
            }
        }
        super.setCode(ret.toString());
    }

    public Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        String bCode;
        int f = foreground == null ? this.DEFAULT_BAR_FOREGROUND_COLOR.getRGB() : foreground.getRGB();
        int g = background == null ? this.DEFAULT_BAR_BACKGROUND_COLOR.getRGB() : background.getRGB();
        Canvas canvas = new Canvas();
        if (this.codeType == 3) {
            int idx = this.code.indexOf(65535);
            if (idx >= 0) {
                bCode = this.code.substring(0, idx);
            } else {
                bCode = this.code;
            }
        } else {
            bCode = getRawText(this.code, this.codeType == 2);
        }
        int fullWidth = ((bCode.length() + 2) * 11) + 2;
        byte[] bars = getBarsCode128Raw(bCode);
        int height = (int) this.barHeight;
        int[] pix = new int[(fullWidth * height)];
        boolean print = true;
        int ptr = 0;
        for (byte w : bars) {
            int c = g;
            if (print) {
                c = f;
            }
            print = !print;
            int j = 0;
            while (j < w) {
                pix[ptr] = c;
                j++;
                ptr++;
            }
        }
        for (int k = fullWidth; k < pix.length; k += fullWidth) {
            System.arraycopy(pix, 0, pix, k, fullWidth);
        }
        int[] iArr = pix;
        int i = height;
        return canvas.createImage(new MemoryImageSource(fullWidth, height, pix, 0, fullWidth));
    }

    /* renamed from: com.itextpdf.barcodes.Barcode128$1 */
    static /* synthetic */ class C14051 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$barcodes$Barcode128$Barcode128CodeSet;

        static {
            int[] iArr = new int[Barcode128CodeSet.values().length];
            $SwitchMap$com$itextpdf$barcodes$Barcode128$Barcode128CodeSet = iArr;
            try {
                iArr[Barcode128CodeSet.A.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$barcodes$Barcode128$Barcode128CodeSet[Barcode128CodeSet.B.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$barcodes$Barcode128$Barcode128CodeSet[Barcode128CodeSet.C.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private static char getStartSymbol(Barcode128CodeSet codeSet2) {
        switch (C14051.$SwitchMap$com$itextpdf$barcodes$Barcode128$Barcode128CodeSet[codeSet2.ordinal()]) {
            case 1:
                return START_A;
            case 2:
                return START_B;
            case 3:
                return START_C;
            default:
                return START_B;
        }
    }

    static boolean isNextDigits(String text, int textIndex, int numDigits) {
        int len = text.length();
        loop0:
        while (textIndex < len && numDigits > 0) {
            if (text.charAt(textIndex) == 202) {
                textIndex++;
            } else {
                int textIndex2 = Math.min(2, numDigits);
                if (textIndex + textIndex2 > len) {
                    return false;
                }
                while (true) {
                    int n = textIndex2 - 1;
                    if (textIndex2 <= 0) {
                        continue;
                        break;
                    }
                    int textIndex3 = textIndex + 1;
                    int textIndex4 = text.charAt(textIndex);
                    if (textIndex4 < 48 || textIndex4 > 57) {
                        return false;
                    }
                    numDigits--;
                    textIndex = textIndex3;
                    textIndex2 = n;
                }
                return false;
            }
        }
        if (numDigits == 0) {
            return true;
        }
        return false;
    }

    static String getPackedRawDigits(String text, int textIndex, int numDigits) {
        StringBuilder out = new StringBuilder("");
        int start = textIndex;
        while (numDigits > 0) {
            if (text.charAt(textIndex) == 202) {
                out.append(FNC1_INDEX);
                textIndex++;
            } else {
                numDigits -= 2;
                int textIndex2 = textIndex + 1;
                out.append((char) (((text.charAt(textIndex) - 48) * 10) + (text.charAt(textIndex2) - 48)));
                textIndex = textIndex2 + 1;
            }
        }
        return ((char) (textIndex - start)) + out.toString();
    }
}
