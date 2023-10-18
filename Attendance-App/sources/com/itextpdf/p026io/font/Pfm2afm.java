package com.itextpdf.p026io.font;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.util.FileUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.svg.SvgConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.i18n.LocalizedMessage;

/* renamed from: com.itextpdf.io.font.Pfm2afm */
public final class Pfm2afm {
    private int[] Win2PSStd = {0, 0, 0, 0, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, 198, 199, 0, XMPError.BADRDF, 0, 205, 206, 207, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 33, 34, 35, 36, 37, 38, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 0, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256, CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384, 188, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256, 0, CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256, 234, 0, 0, 0, 0, 96, 0, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_PSK_WITH_NULL_SHA384, 208, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256, 0, 0, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 0, 0, 0, 0, CipherSuite.TLS_DH_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 168, CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384, 0, CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384, 200, 0, 227, CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384, 0, 0, 0, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, 0, 0, 0, 0, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA256, 0, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256, 180, XMPError.BADXMP, 0, 235, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256, 0, 0, 0, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 0, 0, 0, 0, 0, 0, 225, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 233, 0, 0, 0, 0, 0, 0, 251, 0, 0, 0, 0, 0, 0, 241, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 0, 0, 0, 0, 0, 0, 0};
    private String[] WinChars = {"W00", "W01", "W02", "W03", "macron", "breve", "dotaccent", "W07", "ring", "W09", "W0a", "W0b", "W0c", "W0d", "W0e", "W0f", "hungarumlaut", "ogonek", "caron", "W13", "W14", "W15", "W16", "W17", "W18", "W19", "W1a", "W1b", "W1c", "W1d", "W1e", "W1f", CommonCssConstants.SPACE, "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quotesingle", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", TypedValues.CycleType.S_WAVE_PERIOD, "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A, SvgConstants.Attributes.PATH_DATA_BEARING, SvgConstants.Attributes.PATH_DATA_CURVE_TO, "D", "E", "F", "G", "H", "I", "J", "K", "L", SvgConstants.Attributes.PATH_DATA_MOVE_TO, "N", "O", StandardRoles.f1511P, SvgConstants.Attributes.PATH_DATA_QUAD_CURVE_TO, SvgConstants.Attributes.PATH_DATA_CATMULL_CURVE, SvgConstants.Attributes.PATH_DATA_CURVE_TO_S, SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO, "U", SvgConstants.Attributes.PATH_DATA_LINE_TO_V, "W", "X", "Y", SvgConstants.Attributes.PATH_DATA_CLOSE_PATH, "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "grave", "a", SvgConstants.Attributes.PATH_DATA_REL_BEARING, SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO, SvgConstants.Attributes.f1634D, "e", XfdfConstants.f1185F, SvgConstants.Tags.f1648G, SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_H, "i", "j", "k", SvgConstants.Attributes.PATH_DATA_REL_LINE_TO, SvgConstants.Attributes.PATH_DATA_REL_MOVE_TO, "n", "o", "p", "q", "r", SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S, SvgConstants.Attributes.PATH_DATA_REL_SHORTHAND_CURVE_TO, "u", SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_V, "w", SvgConstants.Attributes.f1641X, SvgConstants.Attributes.f1644Y, "z", "braceleft", "bar", "braceright", "asciitilde", "W7f", "euro", "W81", "quotesinglbase", "florin", "quotedblbase", "ellipsis", "dagger", "daggerdbl", "circumflex", "perthousand", "Scaron", "guilsinglleft", "OE", "W8d", "Zcaron", "W8f", "W90", "quoteleft", "quoteright", "quotedblleft", "quotedblright", "bullet", "endash", "emdash", "tilde", "trademark", "scaron", "guilsinglright", "oe", "W9d", "zcaron", "Ydieresis", "reqspace", "exclamdown", "cent", "sterling", "currency", "yen", "brokenbar", "section", "dieresis", "copyright", "ordfeminine", "guillemotleft", "logicalnot", "syllable", "registered", "macron", "degree", "plusminus", "twosuperior", "threesuperior", "acute", "mu", "paragraph", "periodcentered", "cedilla", "onesuperior", "ordmasculine", "guillemotright", "onequarter", "onehalf", "threequarters", "questiondown", "Agrave", "Aacute", "Acircumflex", "Atilde", "Adieresis", "Aring", "AE", "Ccedilla", "Egrave", "Eacute", "Ecircumflex", "Edieresis", "Igrave", "Iacute", "Icircumflex", "Idieresis", "Eth", "Ntilde", "Ograve", "Oacute", "Ocircumflex", "Otilde", "Odieresis", CommonCssConstants.MULTIPLY, "Oslash", "Ugrave", "Uacute", "Ucircumflex", "Udieresis", "Yacute", "Thorn", "germandbls", "agrave", "aacute", "acircumflex", "atilde", "adieresis", "aring", "ae", "ccedilla", "egrave", "eacute", "ecircumflex", "edieresis", "igrave", "iacute", "icircumflex", "idieresis", "eth", "ntilde", "ograve", "oacute", "ocircumflex", "otilde", "odieresis", "divide", "oslash", "ugrave", "uacute", "ucircumflex", "udieresis", "yacute", "thorn", "ydieresis"};
    private short ascender;
    private short ascent;
    private short avgwidth;
    private int bitoff;
    private int bits;
    private byte brkchar;
    private short capheight;
    private byte charset;
    private int chartab;
    private String copyright;
    private byte defchar;
    private short descender;
    private int device;
    private short extleading;
    private short extlen;
    private int face;
    private int firstchar;
    private int fontname;
    private int h_len;
    private short horres;
    private RandomAccessFileOrArray input;
    private short intleading;
    private boolean isMono;
    private byte italic;
    private int kernpairs;
    private byte kind;
    private int lastchar;
    private short maxwidth;
    private PrintWriter output;
    private byte overs;
    private short pixheight;
    private short pixwidth;
    private short points;
    private int psext;
    private int res1;
    private int res2;
    private short type;
    private byte uline;
    private short verres;
    private short vers;
    private short weight;
    private short widthby;
    private short xheight;

    private Pfm2afm(RandomAccessFileOrArray input2, OutputStream output2) throws IOException {
        this.input = input2;
        this.output = FileUtil.createPrintWriter(output2, LocalizedMessage.DEFAULT_ENCODING);
    }

    public static void convert(RandomAccessFileOrArray input2, OutputStream output2) throws IOException {
        Pfm2afm p = new Pfm2afm(input2, output2);
        p.openpfm();
        p.putheader();
        p.putchartab();
        p.putkerntab();
        p.puttrailer();
        p.output.flush();
    }

    private String readString(int n) throws IOException {
        byte[] b = new byte[n];
        this.input.readFully(b);
        int k = 0;
        while (k < b.length && b[k] != 0) {
            k++;
        }
        return new String(b, 0, k, LocalizedMessage.DEFAULT_ENCODING);
    }

    private String readString() throws IOException {
        StringBuilder buf = new StringBuilder();
        while (true) {
            int c = this.input.read();
            if (c <= 0) {
                return buf.toString();
            }
            buf.append((char) c);
        }
    }

    private void outval(int n) {
        this.output.print(' ');
        this.output.print(n);
    }

    private void outchar(int code, int width, String name) {
        this.output.print("C ");
        outval(code);
        this.output.print(" ; WX ");
        outval(width);
        if (name != null) {
            this.output.print(" ; N ");
            this.output.print(name);
        }
        this.output.print(" ;\n");
    }

    private void openpfm() throws IOException {
        int i;
        this.input.seek(0);
        this.vers = this.input.readShortLE();
        this.h_len = this.input.readIntLE();
        this.copyright = readString(60);
        this.type = this.input.readShortLE();
        this.points = this.input.readShortLE();
        this.verres = this.input.readShortLE();
        this.horres = this.input.readShortLE();
        this.ascent = this.input.readShortLE();
        this.intleading = this.input.readShortLE();
        this.extleading = this.input.readShortLE();
        this.italic = (byte) this.input.read();
        this.uline = (byte) this.input.read();
        this.overs = (byte) this.input.read();
        this.weight = this.input.readShortLE();
        this.charset = (byte) this.input.read();
        this.pixwidth = this.input.readShortLE();
        this.pixheight = this.input.readShortLE();
        this.kind = (byte) this.input.read();
        this.avgwidth = this.input.readShortLE();
        this.maxwidth = this.input.readShortLE();
        this.firstchar = this.input.read();
        this.lastchar = this.input.read();
        this.defchar = (byte) this.input.read();
        this.brkchar = (byte) this.input.read();
        this.widthby = this.input.readShortLE();
        this.device = this.input.readIntLE();
        this.face = this.input.readIntLE();
        this.bits = this.input.readIntLE();
        this.bitoff = this.input.readIntLE();
        this.extlen = this.input.readShortLE();
        this.psext = this.input.readIntLE();
        this.chartab = this.input.readIntLE();
        this.res1 = this.input.readIntLE();
        this.kernpairs = this.input.readIntLE();
        this.res2 = this.input.readIntLE();
        this.fontname = this.input.readIntLE();
        if (((long) this.h_len) != this.input.length() || this.extlen != 30 || (i = this.fontname) < 75 || i > 512) {
            throw new IOException("not.a.valid.pfm.file");
        }
        this.input.seek((long) (this.psext + 14));
        this.capheight = this.input.readShortLE();
        this.xheight = this.input.readShortLE();
        this.ascender = this.input.readShortLE();
        this.descender = this.input.readShortLE();
    }

    private void putheader() throws IOException {
        this.output.print("StartFontMetrics 2.0\n");
        if (this.copyright.length() > 0) {
            this.output.print("Comment " + this.copyright + 10);
        }
        this.output.print("FontName ");
        this.input.seek((long) this.fontname);
        String fname = readString();
        this.output.print(fname);
        this.output.print("\nEncodingScheme ");
        if (this.charset != 0) {
            this.output.print("FontSpecific\n");
        } else {
            this.output.print("AdobeStandardEncoding\n");
        }
        this.output.print("FullName " + fname.replace('-', ' '));
        int i = this.face;
        if (i != 0) {
            this.input.seek((long) i);
            this.output.print("\nFamilyName " + readString());
        }
        this.output.print("\nWeight ");
        if (this.weight > 475 || fname.toLowerCase().contains("bold")) {
            this.output.print("Bold");
        } else {
            short s = this.weight;
            if ((s < 325 && s != 0) || fname.toLowerCase().contains("light")) {
                this.output.print("Light");
            } else if (fname.toLowerCase().contains("black")) {
                this.output.print("Black");
            } else {
                this.output.print("Medium");
            }
        }
        this.output.print("\nItalicAngle ");
        if (this.italic != 0 || fname.toLowerCase().contains("italic")) {
            this.output.print("-12.00");
        } else {
            this.output.print("0");
        }
        this.output.print("\nIsFixedPitch ");
        if ((this.kind & 1) == 0 || this.avgwidth == this.maxwidth) {
            this.output.print("true");
            this.isMono = true;
        } else {
            this.output.print("false");
            this.isMono = false;
        }
        this.output.print("\nFontBBox");
        if (this.isMono) {
            outval(-20);
        } else {
            outval(-100);
        }
        outval(-(this.descender + 5));
        outval(this.maxwidth + 10);
        outval(this.ascent + 5);
        this.output.print("\nCapHeight");
        outval(this.capheight);
        this.output.print("\nXHeight");
        outval(this.xheight);
        this.output.print("\nDescender");
        outval(-this.descender);
        this.output.print("\nAscender");
        outval(this.ascender);
        this.output.print(10);
    }

    private void putchartab() throws IOException {
        int count = (this.lastchar - this.firstchar) + 1;
        int[] ctabs = new int[count];
        this.input.seek((long) this.chartab);
        for (int k = 0; k < count; k++) {
            ctabs[k] = this.input.readUnsignedShortLE();
        }
        int[] back = new int[256];
        if (this.charset == 0) {
            for (int i = this.firstchar; i <= this.lastchar; i++) {
                int i2 = this.Win2PSStd[i];
                if (i2 != 0) {
                    back[i2] = i;
                }
            }
        }
        this.output.print("StartCharMetrics");
        outval(count);
        this.output.print(10);
        if (this.charset != 0) {
            for (int i3 = this.firstchar; i3 <= this.lastchar; i3++) {
                int i4 = this.firstchar;
                if (ctabs[i3 - i4] != 0) {
                    outchar(i3, ctabs[i3 - i4], (String) null);
                }
            }
        } else {
            for (int i5 = 0; i5 < 256; i5++) {
                int j = back[i5];
                if (j != 0) {
                    outchar(i5, ctabs[j - this.firstchar], this.WinChars[j]);
                    ctabs[j - this.firstchar] = 0;
                }
            }
            for (int i6 = this.firstchar; i6 <= this.lastchar; i6++) {
                int i7 = this.firstchar;
                if (ctabs[i6 - i7] != 0) {
                    outchar(-1, ctabs[i6 - i7], this.WinChars[i6]);
                }
            }
        }
        this.output.print("EndCharMetrics\n");
    }

    private void putkerntab() throws IOException {
        int i = this.kernpairs;
        if (i != 0) {
            this.input.seek((long) i);
            int nzero = 0;
            int[] kerns = new int[(this.input.readUnsignedShortLE() * 3)];
            int k = 0;
            while (k < kerns.length) {
                int k2 = k + 1;
                kerns[k] = this.input.read();
                int k3 = k2 + 1;
                kerns[k2] = this.input.read();
                int k4 = k3 + 1;
                int readShortLE = this.input.readShortLE();
                kerns[k3] = readShortLE;
                if (readShortLE != 0) {
                    nzero++;
                    k = k4;
                } else {
                    k = k4;
                }
            }
            if (nzero != 0) {
                this.output.print("StartKernData\nStartKernPairs");
                outval(nzero);
                this.output.print(10);
                for (int k5 = 0; k5 < kerns.length; k5 += 3) {
                    if (kerns[k5 + 2] != 0) {
                        this.output.print("KPX ");
                        this.output.print(this.WinChars[kerns[k5]]);
                        this.output.print(' ');
                        this.output.print(this.WinChars[kerns[k5 + 1]]);
                        outval(kerns[k5 + 2]);
                        this.output.print(10);
                    }
                }
                this.output.print("EndKernPairs\nEndKernData\n");
            }
        }
    }

    private void puttrailer() {
        this.output.print("EndFontMetrics\n");
    }
}
