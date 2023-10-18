package com.itextpdf.p026io.font;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.p026io.IOException;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.svg.SvgConstants;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.UByte;
import kotlin.text.Typography;

/* renamed from: com.itextpdf.io.font.CFFFont */
public class CFFFont {
    static final String[] operatorNames = {SvgConstants.Attributes.VERSION, "Notice", "FullName", "FamilyName", "Weight", "FontBBox", "BlueValues", "OtherBlues", "FamilyBlues", "FamilyOtherBlues", "StdHW", "StdVW", "UNKNOWN_12", "UniqueID", "XUID", "charset", "Encoding", "CharStrings", StandardRoles.PRIVATE, "Subrs", "defaultWidthX", "nominalWidthX", "UNKNOWN_22", "UNKNOWN_23", "UNKNOWN_24", "UNKNOWN_25", "UNKNOWN_26", "UNKNOWN_27", "UNKNOWN_28", "UNKNOWN_29", "UNKNOWN_30", "UNKNOWN_31", "Copyright", "isFixedPitch", "ItalicAngle", "UnderlinePosition", "UnderlineThickness", "PaintType", "CharstringType", "FontMatrix", "StrokeWidth", "BlueScale", "BlueShift", "BlueFuzz", "StemSnapH", "StemSnapV", "ForceBold", "UNKNOWN_12_15", "UNKNOWN_12_16", "LanguageGroup", "ExpansionFactor", "initialRandomSeed", "SyntheticBase", "PostScript", "BaseFontName", "BaseFontBlend", "UNKNOWN_12_24", "UNKNOWN_12_25", "UNKNOWN_12_26", "UNKNOWN_12_27", "UNKNOWN_12_28", "UNKNOWN_12_29", "ROS", "CIDFontVersion", "CIDFontRevision", "CIDFontType", "CIDCount", "UIDBase", "FDArray", "FDSelect", "FontName"};
    static final String[] standardStrings = {FontEncoding.NOTDEF, CommonCssConstants.SPACE, "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quoteright", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", TypedValues.CycleType.S_WAVE_PERIOD, "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A, SvgConstants.Attributes.PATH_DATA_BEARING, SvgConstants.Attributes.PATH_DATA_CURVE_TO, "D", "E", "F", "G", "H", "I", "J", "K", "L", SvgConstants.Attributes.PATH_DATA_MOVE_TO, "N", "O", StandardRoles.f1511P, SvgConstants.Attributes.PATH_DATA_QUAD_CURVE_TO, SvgConstants.Attributes.PATH_DATA_CATMULL_CURVE, SvgConstants.Attributes.PATH_DATA_CURVE_TO_S, SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO, "U", SvgConstants.Attributes.PATH_DATA_LINE_TO_V, "W", "X", "Y", SvgConstants.Attributes.PATH_DATA_CLOSE_PATH, "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "quoteleft", "a", SvgConstants.Attributes.PATH_DATA_REL_BEARING, SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO, SvgConstants.Attributes.f1634D, "e", XfdfConstants.f1185F, SvgConstants.Tags.f1648G, SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_H, "i", "j", "k", SvgConstants.Attributes.PATH_DATA_REL_LINE_TO, SvgConstants.Attributes.PATH_DATA_REL_MOVE_TO, "n", "o", "p", "q", "r", SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S, SvgConstants.Attributes.PATH_DATA_REL_SHORTHAND_CURVE_TO, "u", SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_V, "w", SvgConstants.Attributes.f1641X, SvgConstants.Attributes.f1644Y, "z", "braceleft", "bar", "braceright", "asciitilde", "exclamdown", "cent", "sterling", "fraction", "yen", "florin", "section", "currency", "quotesingle", "quotedblleft", "guillemotleft", "guilsinglleft", "guilsinglright", "fi", "fl", "endash", "dagger", "daggerdbl", "periodcentered", "paragraph", "bullet", "quotesinglbase", "quotedblbase", "quotedblright", "guillemotright", "ellipsis", "perthousand", "questiondown", "grave", "acute", "circumflex", "tilde", "macron", "breve", "dotaccent", "dieresis", "ring", "cedilla", "hungarumlaut", "ogonek", "caron", "emdash", "AE", "ordfeminine", "Lslash", "Oslash", "OE", "ordmasculine", "ae", "dotlessi", "lslash", "oslash", "oe", "germandbls", "onesuperior", "logicalnot", "mu", "trademark", "Eth", "onehalf", "plusminus", "Thorn", "onequarter", "divide", "brokenbar", "degree", "thorn", "threequarters", "twosuperior", "registered", "minus", "eth", CommonCssConstants.MULTIPLY, "threesuperior", "copyright", "Aacute", "Acircumflex", "Adieresis", "Agrave", "Aring", "Atilde", "Ccedilla", "Eacute", "Ecircumflex", "Edieresis", "Egrave", "Iacute", "Icircumflex", "Idieresis", "Igrave", "Ntilde", "Oacute", "Ocircumflex", "Odieresis", "Ograve", "Otilde", "Scaron", "Uacute", "Ucircumflex", "Udieresis", "Ugrave", "Yacute", "Ydieresis", "Zcaron", "aacute", "acircumflex", "adieresis", "agrave", "aring", "atilde", "ccedilla", "eacute", "ecircumflex", "edieresis", "egrave", "iacute", "icircumflex", "idieresis", "igrave", "ntilde", "oacute", "ocircumflex", "odieresis", "ograve", "otilde", "scaron", "uacute", "ucircumflex", "udieresis", "ugrave", "yacute", "ydieresis", "zcaron", "exclamsmall", "Hungarumlautsmall", "dollaroldstyle", "dollarsuperior", "ampersandsmall", "Acutesmall", "parenleftsuperior", "parenrightsuperior", "twodotenleader", "onedotenleader", "zerooldstyle", "oneoldstyle", "twooldstyle", "threeoldstyle", "fouroldstyle", "fiveoldstyle", "sixoldstyle", "sevenoldstyle", "eightoldstyle", "nineoldstyle", "commasuperior", "threequartersemdash", "periodsuperior", "questionsmall", "asuperior", "bsuperior", "centsuperior", "dsuperior", "esuperior", "isuperior", "lsuperior", "msuperior", "nsuperior", "osuperior", "rsuperior", "ssuperior", "tsuperior", "ff", "ffi", "ffl", "parenleftinferior", "parenrightinferior", "Circumflexsmall", "hyphensuperior", "Gravesmall", "Asmall", "Bsmall", "Csmall", "Dsmall", "Esmall", "Fsmall", "Gsmall", "Hsmall", "Ismall", "Jsmall", "Ksmall", "Lsmall", "Msmall", "Nsmall", "Osmall", "Psmall", "Qsmall", "Rsmall", "Ssmall", "Tsmall", "Usmall", "Vsmall", "Wsmall", "Xsmall", "Ysmall", "Zsmall", "colonmonetary", "onefitted", "rupiah", "Tildesmall", "exclamdownsmall", "centoldstyle", "Lslashsmall", "Scaronsmall", "Zcaronsmall", "Dieresissmall", "Brevesmall", "Caronsmall", "Dotaccentsmall", "Macronsmall", "figuredash", "hypheninferior", "Ogoneksmall", "Ringsmall", "Cedillasmall", "questiondownsmall", "oneeighth", "threeeighths", "fiveeighths", "seveneighths", "onethird", "twothirds", "zerosuperior", "foursuperior", "fivesuperior", "sixsuperior", "sevensuperior", "eightsuperior", "ninesuperior", "zeroinferior", "oneinferior", "twoinferior", "threeinferior", "fourinferior", "fiveinferior", "sixinferior", "seveninferior", "eightinferior", "nineinferior", "centinferior", "dollarinferior", "periodinferior", "commainferior", "Agravesmall", "Aacutesmall", "Acircumflexsmall", "Atildesmall", "Adieresissmall", "Aringsmall", "AEsmall", "Ccedillasmall", "Egravesmall", "Eacutesmall", "Ecircumflexsmall", "Edieresissmall", "Igravesmall", "Iacutesmall", "Icircumflexsmall", "Idieresissmall", "Ethsmall", "Ntildesmall", "Ogravesmall", "Oacutesmall", "Ocircumflexsmall", "Otildesmall", "Odieresissmall", "OEsmall", "Oslashsmall", "Ugravesmall", "Uacutesmall", "Ucircumflexsmall", "Udieresissmall", "Yacutesmall", "Thornsmall", "Ydieresissmall", "001.000", "001.001", "001.002", "001.003", "Black", "Bold", "Book", "Light", "Medium", "Regular", "Roman", "Semibold"};
    protected int arg_count = 0;
    protected Object[] args = new Object[48];
    protected RandomAccessFileOrArray buf;
    protected Font[] fonts;
    protected int gsubrIndexOffset;
    protected int[] gsubrOffsets;
    protected String key;
    protected int nameIndexOffset;
    protected int[] nameOffsets;
    int nextIndexOffset;
    private int offSize;
    RandomAccessSourceFactory rasFactory = new RandomAccessSourceFactory();
    protected int stringIndexOffset;
    protected int[] stringOffsets;
    protected int topdictIndexOffset;
    protected int[] topdictOffsets;

    /* renamed from: com.itextpdf.io.font.CFFFont$IndexBaseItem */
    protected static final class IndexBaseItem extends Item {
    }

    public String getString(char sid) {
        String[] strArr = standardStrings;
        if (sid < strArr.length) {
            return strArr[sid];
        }
        if (sid >= (strArr.length + this.stringOffsets.length) - 1) {
            return null;
        }
        int j = sid - strArr.length;
        int p = getPosition();
        seek(this.stringOffsets[j]);
        StringBuffer s = new StringBuffer();
        for (int k = this.stringOffsets[j]; k < this.stringOffsets[j + 1]; k++) {
            s.append(getCard8());
        }
        seek(p);
        return s.toString();
    }

    /* access modifiers changed from: package-private */
    public char getCard8() {
        try {
            return (char) (this.buf.readByte() & UByte.MAX_VALUE);
        } catch (Exception e) {
            throw new IOException("I/O exception.", (Throwable) e);
        }
    }

    /* access modifiers changed from: package-private */
    public char getCard16() {
        try {
            return this.buf.readChar();
        } catch (java.io.IOException e) {
            throw new IOException("I/O exception.", (Throwable) e);
        }
    }

    /* access modifiers changed from: package-private */
    public int getOffset(int offSize2) {
        int offset = 0;
        for (int i = 0; i < offSize2; i++) {
            offset = (offset * 256) + getCard8();
        }
        return offset;
    }

    /* access modifiers changed from: package-private */
    public void seek(int offset) {
        try {
            this.buf.seek((long) offset);
        } catch (java.io.IOException e) {
            throw new IOException("I/O exception.", (Throwable) e);
        }
    }

    /* access modifiers changed from: package-private */
    public short getShort() {
        try {
            return this.buf.readShort();
        } catch (java.io.IOException e) {
            throw new IOException("I/O exception.", (Throwable) e);
        }
    }

    /* access modifiers changed from: package-private */
    public int getInt() {
        try {
            return this.buf.readInt();
        } catch (java.io.IOException e) {
            throw new IOException("I/O exception.", (Throwable) e);
        }
    }

    /* access modifiers changed from: package-private */
    public int getPosition() {
        try {
            return (int) this.buf.getPosition();
        } catch (java.io.IOException e) {
            throw new IOException("I/O exception.", (Throwable) e);
        }
    }

    /* access modifiers changed from: package-private */
    public int[] getIndex(int nextIndexOffset2) {
        seek(nextIndexOffset2);
        int count = getCard16();
        int[] offsets = new int[(count + 1)];
        if (count == 0) {
            offsets[0] = -1;
            int nextIndexOffset3 = nextIndexOffset2 + 2;
            return offsets;
        }
        int indexOffSize = getCard8();
        for (int j = 0; j <= count; j++) {
            offsets[j] = ((((nextIndexOffset2 + 2) + 1) + ((count + 1) * indexOffSize)) - 1) + getOffset(indexOffSize);
        }
        return offsets;
    }

    /* access modifiers changed from: protected */
    public void getDictItem() {
        for (int i = 0; i < this.arg_count; i++) {
            this.args[i] = null;
        }
        this.arg_count = 0;
        this.key = null;
        boolean gotKey = false;
        while (!gotKey) {
            char b0 = getCard8();
            if (b0 == 29) {
                this.args[this.arg_count] = Integer.valueOf(getInt());
                this.arg_count++;
            } else if (b0 == 28) {
                this.args[this.arg_count] = Integer.valueOf(getShort());
                this.arg_count++;
            } else if (b0 >= ' ' && b0 <= 246) {
                this.args[this.arg_count] = Integer.valueOf(b0 - 139);
                this.arg_count++;
            } else if (b0 >= 247 && b0 <= 250) {
                this.args[this.arg_count] = Integer.valueOf((short) (((b0 - 247) * 256) + getCard8() + 108));
                this.arg_count++;
            } else if (b0 >= 251 && b0 <= 254) {
                this.args[this.arg_count] = Integer.valueOf((short) ((((-(b0 - 251)) * 256) - getCard8()) - 108));
                this.arg_count++;
            } else if (b0 == 30) {
                StringBuilder item = new StringBuilder("");
                boolean done = false;
                char buffer = 0;
                byte avail = 0;
                int nibble = 0;
                while (!done) {
                    if (avail == 0) {
                        buffer = getCard8();
                        avail = 2;
                    }
                    if (avail == 1) {
                        nibble = buffer / 16;
                        avail = (byte) (avail - 1);
                    }
                    if (avail == 2) {
                        nibble = buffer % 16;
                        avail = (byte) (avail - 1);
                    }
                    switch (nibble) {
                        case 10:
                            item.append(".");
                            break;
                        case 11:
                            item.append("E");
                            break;
                        case 12:
                            item.append("E-");
                            break;
                        case 14:
                            item.append("-");
                            break;
                        case 15:
                            done = true;
                            break;
                        default:
                            if (nibble >= 0 && nibble <= 9) {
                                item.append(nibble);
                                break;
                            } else {
                                item.append("<NIBBLE ERROR: ").append(nibble).append(Typography.greater);
                                done = true;
                                break;
                            }
                            break;
                    }
                }
                this.args[this.arg_count] = item.toString();
                this.arg_count++;
            } else if (b0 <= 21) {
                gotKey = true;
                if (b0 != 12) {
                    this.key = operatorNames[b0];
                } else {
                    this.key = operatorNames[getCard8() + ' '];
                }
            }
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$Item */
    protected static abstract class Item {
        protected int myOffset = -1;

        protected Item() {
        }

        public void increment(int[] currentOffset) {
            this.myOffset = currentOffset[0];
        }

        public void emit(byte[] buffer) {
        }

        public void xref() {
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$OffsetItem */
    protected static abstract class OffsetItem extends Item {
        public int value;

        protected OffsetItem() {
        }

        public void set(int offset) {
            this.value = offset;
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$RangeItem */
    protected static final class RangeItem extends Item {
        private RandomAccessFileOrArray buf;
        public int length;
        public int offset;

        public RangeItem(RandomAccessFileOrArray buf2, int offset2, int length2) {
            this.offset = offset2;
            this.length = length2;
            this.buf = buf2;
        }

        public void increment(int[] currentOffset) {
            super.increment(currentOffset);
            currentOffset[0] = currentOffset[0] + this.length;
        }

        public void emit(byte[] buffer) {
            try {
                this.buf.seek((long) this.offset);
                for (int i = this.myOffset; i < this.myOffset + this.length; i++) {
                    buffer[i] = this.buf.readByte();
                }
            } catch (java.io.IOException e) {
                throw new IOException("I/O exception.", (Throwable) e);
            }
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$IndexOffsetItem */
    protected static final class IndexOffsetItem extends OffsetItem {
        public final int size;

        public IndexOffsetItem(int size2, int value) {
            this.size = size2;
            this.value = value;
        }

        public IndexOffsetItem(int size2) {
            this.size = size2;
        }

        public void increment(int[] currentOffset) {
            super.increment(currentOffset);
            currentOffset[0] = currentOffset[0] + this.size;
        }

        public void emit(byte[] buffer) {
            int i = this.size;
            if (i >= 1 && i <= 4) {
                for (int i2 = 0; i2 < this.size; i2++) {
                    buffer[this.myOffset + i2] = (byte) ((this.value >>> (((this.size - 1) - i2) << 3)) & 255);
                }
            }
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$IndexMarkerItem */
    protected static final class IndexMarkerItem extends Item {
        private IndexBaseItem indexBase;
        private OffsetItem offItem;

        public IndexMarkerItem(OffsetItem offItem2, IndexBaseItem indexBase2) {
            this.offItem = offItem2;
            this.indexBase = indexBase2;
        }

        public void xref() {
            this.offItem.set((this.myOffset - this.indexBase.myOffset) + 1);
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$SubrMarkerItem */
    protected static final class SubrMarkerItem extends Item {
        private IndexBaseItem indexBase;
        private OffsetItem offItem;

        public SubrMarkerItem(OffsetItem offItem2, IndexBaseItem indexBase2) {
            this.offItem = offItem2;
            this.indexBase = indexBase2;
        }

        public void xref() {
            this.offItem.set(this.myOffset - this.indexBase.myOffset);
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$DictOffsetItem */
    protected static final class DictOffsetItem extends OffsetItem {
        public final int size = 5;

        public void increment(int[] currentOffset) {
            super.increment(currentOffset);
            currentOffset[0] = currentOffset[0] + this.size;
        }

        public void emit(byte[] buffer) {
            if (this.size == 5) {
                buffer[this.myOffset] = 29;
                buffer[this.myOffset + 1] = (byte) ((this.value >>> 24) & 255);
                buffer[this.myOffset + 2] = (byte) ((this.value >>> 16) & 255);
                buffer[this.myOffset + 3] = (byte) ((this.value >>> 8) & 255);
                buffer[this.myOffset + 4] = (byte) ((this.value >>> 0) & 255);
            }
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$UInt24Item */
    protected static final class UInt24Item extends Item {
        public int value;

        public UInt24Item(int value2) {
            this.value = value2;
        }

        public void increment(int[] currentOffset) {
            super.increment(currentOffset);
            currentOffset[0] = currentOffset[0] + 3;
        }

        public void emit(byte[] buffer) {
            buffer[this.myOffset + 0] = (byte) ((this.value >>> 16) & 255);
            buffer[this.myOffset + 1] = (byte) ((this.value >>> 8) & 255);
            buffer[this.myOffset + 2] = (byte) ((this.value >>> 0) & 255);
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$UInt32Item */
    protected static final class UInt32Item extends Item {
        public int value;

        public UInt32Item(int value2) {
            this.value = value2;
        }

        public void increment(int[] currentOffset) {
            super.increment(currentOffset);
            currentOffset[0] = currentOffset[0] + 4;
        }

        public void emit(byte[] buffer) {
            buffer[this.myOffset + 0] = (byte) ((this.value >>> 24) & 255);
            buffer[this.myOffset + 1] = (byte) ((this.value >>> 16) & 255);
            buffer[this.myOffset + 2] = (byte) ((this.value >>> 8) & 255);
            buffer[this.myOffset + 3] = (byte) ((this.value >>> 0) & 255);
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$UInt16Item */
    protected static final class UInt16Item extends Item {
        public char value;

        public UInt16Item(char value2) {
            this.value = value2;
        }

        public void increment(int[] currentOffset) {
            super.increment(currentOffset);
            currentOffset[0] = currentOffset[0] + 2;
        }

        public void emit(byte[] buffer) {
            buffer[this.myOffset + 0] = (byte) ((this.value >> 8) & 255);
            buffer[this.myOffset + 1] = (byte) ((this.value >> 0) & 255);
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$UInt8Item */
    protected static final class UInt8Item extends Item {
        public char value;

        public UInt8Item(char value2) {
            this.value = value2;
        }

        public void increment(int[] currentOffset) {
            super.increment(currentOffset);
            currentOffset[0] = currentOffset[0] + 1;
        }

        public void emit(byte[] buffer) {
            buffer[this.myOffset + 0] = (byte) (this.value & 255);
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$StringItem */
    protected static final class StringItem extends Item {

        /* renamed from: s */
        public String f1210s;

        public StringItem(String s) {
            this.f1210s = s;
        }

        public void increment(int[] currentOffset) {
            super.increment(currentOffset);
            currentOffset[0] = currentOffset[0] + this.f1210s.length();
        }

        public void emit(byte[] buffer) {
            for (int i = 0; i < this.f1210s.length(); i++) {
                buffer[this.myOffset + i] = (byte) (this.f1210s.charAt(i) & 255);
            }
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$DictNumberItem */
    protected static final class DictNumberItem extends Item {
        public int size = 5;
        public final int value;

        public DictNumberItem(int value2) {
            this.value = value2;
        }

        public void increment(int[] currentOffset) {
            super.increment(currentOffset);
            currentOffset[0] = currentOffset[0] + this.size;
        }

        public void emit(byte[] buffer) {
            if (this.size == 5) {
                buffer[this.myOffset] = 29;
                buffer[this.myOffset + 1] = (byte) ((this.value >>> 24) & 255);
                buffer[this.myOffset + 2] = (byte) ((this.value >>> 16) & 255);
                buffer[this.myOffset + 3] = (byte) ((this.value >>> 8) & 255);
                buffer[this.myOffset + 4] = (byte) ((this.value >>> 0) & 255);
            }
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$MarkerItem */
    protected static final class MarkerItem extends Item {

        /* renamed from: p */
        OffsetItem f1209p;

        public MarkerItem(OffsetItem pointerToMarker) {
            this.f1209p = pointerToMarker;
        }

        public void xref() {
            this.f1209p.set(this.myOffset);
        }
    }

    /* access modifiers changed from: protected */
    public RangeItem getEntireIndexRange(int indexOffset) {
        seek(indexOffset);
        int count = getCard16();
        if (count == 0) {
            return new RangeItem(this.buf, indexOffset, 2);
        }
        int indexOffSize = getCard8();
        seek(indexOffset + 2 + 1 + (count * indexOffSize));
        return new RangeItem(this.buf, indexOffset, ((count + 1) * indexOffSize) + 3 + (getOffset(indexOffSize) - 1));
    }

    public byte[] getCID(String fontName) {
        byte stringsIndexOffSize;
        int offSize2;
        int j = 0;
        while (true) {
            Font[] fontArr = this.fonts;
            if (j >= fontArr.length) {
                String str = fontName;
                break;
            }
            if (fontName.equals(fontArr[j].name)) {
                break;
            }
            j++;
        }
        if (j == this.fonts.length) {
            return null;
        }
        LinkedList<Item> l = new LinkedList<>();
        seek(0);
        char card8 = getCard8();
        int minor = getCard8();
        int hdrSize = getCard8();
        int offSize3 = getCard8();
        this.nextIndexOffset = hdrSize;
        l.addLast(new RangeItem(this.buf, 0, hdrSize));
        int nglyphs = -1;
        int nstrings = -1;
        if (!this.fonts[j].isCID) {
            seek(this.fonts[j].charstringsOffset);
            nglyphs = getCard16();
            seek(this.stringIndexOffset);
            nstrings = getCard16() + standardStrings.length;
        }
        l.addLast(new UInt16Item(1));
        l.addLast(new UInt8Item(1));
        l.addLast(new UInt8Item(1));
        l.addLast(new UInt8Item((char) (this.fonts[j].name.length() + 1)));
        l.addLast(new StringItem(this.fonts[j].name));
        l.addLast(new UInt16Item(1));
        l.addLast(new UInt8Item(2));
        l.addLast(new UInt16Item(1));
        IndexOffsetItem indexOffsetItem = new IndexOffsetItem(2);
        l.addLast(indexOffsetItem);
        IndexBaseItem topdictBase = new IndexBaseItem();
        l.addLast(topdictBase);
        OffsetItem charsetRef = new DictOffsetItem();
        OffsetItem charstringsRef = new DictOffsetItem();
        OffsetItem fdarrayRef = new DictOffsetItem();
        OffsetItem fdselectRef = new DictOffsetItem();
        if (!this.fonts[j].isCID) {
            l.addLast(new DictNumberItem(nstrings));
            l.addLast(new DictNumberItem(nstrings + 1));
            l.addLast(new DictNumberItem(0));
            l.addLast(new UInt8Item(12));
            l.addLast(new UInt8Item(30));
            l.addLast(new DictNumberItem(nglyphs));
            l.addLast(new UInt8Item(12));
            l.addLast(new UInt8Item(Typography.quote));
        }
        OffsetItem fdarrayRef2 = fdarrayRef;
        l.addLast(fdarrayRef2);
        l.addLast(new UInt8Item(12));
        l.addLast(new UInt8Item(Typography.dollar));
        OffsetItem fdselectRef2 = fdselectRef;
        l.addLast(fdselectRef2);
        l.addLast(new UInt8Item(12));
        l.addLast(new UInt8Item('%'));
        l.addLast(charsetRef);
        l.addLast(new UInt8Item(15));
        OffsetItem charstringsRef2 = charstringsRef;
        l.addLast(charstringsRef2);
        char c = card8;
        l.addLast(new UInt8Item(17));
        seek(this.topdictOffsets[j]);
        while (getPosition() < this.topdictOffsets[j + 1]) {
            int p1 = getPosition();
            getDictItem();
            int p2 = getPosition();
            int minor2 = minor;
            int hdrSize2 = hdrSize;
            if ("Encoding".equals(this.key)) {
                offSize2 = offSize3;
            } else if (StandardRoles.PRIVATE.equals(this.key)) {
                offSize2 = offSize3;
            } else if ("FDSelect".equals(this.key)) {
                offSize2 = offSize3;
            } else if ("FDArray".equals(this.key)) {
                offSize2 = offSize3;
            } else if ("charset".equals(this.key)) {
                offSize2 = offSize3;
            } else if ("CharStrings".equals(this.key)) {
                offSize2 = offSize3;
            } else {
                offSize2 = offSize3;
                l.addLast(new RangeItem(this.buf, p1, p2 - p1));
            }
            minor = minor2;
            hdrSize = hdrSize2;
            offSize3 = offSize2;
        }
        int i = hdrSize;
        int i2 = offSize3;
        l.addLast(new IndexMarkerItem(indexOffsetItem, topdictBase));
        if (this.fonts[j].isCID) {
            l.addLast(getEntireIndexRange(this.stringIndexOffset));
            int i3 = nstrings;
            IndexOffsetItem indexOffsetItem2 = indexOffsetItem;
            IndexBaseItem indexBaseItem = topdictBase;
        } else {
            String fdFontName = this.fonts[j].name + "-OneRange";
            if (fdFontName.length() > 127) {
                fdFontName = fdFontName.substring(0, 127);
            }
            String extraStrings = "AdobeIdentity" + fdFontName;
            int[] iArr = this.stringOffsets;
            int i4 = iArr[iArr.length - 1];
            int i5 = iArr[0];
            int origStringsLen = i4 - i5;
            int stringsBaseOffset = i5 - 1;
            int i6 = nstrings;
            if (origStringsLen + extraStrings.length() <= 255) {
                stringsIndexOffSize = 1;
            } else if (extraStrings.length() + origStringsLen <= 65535) {
                stringsIndexOffSize = 2;
            } else if (extraStrings.length() + origStringsLen <= 16777215) {
                stringsIndexOffSize = 3;
            } else {
                stringsIndexOffSize = 4;
            }
            IndexOffsetItem indexOffsetItem3 = indexOffsetItem;
            l.addLast(new UInt16Item((char) ((this.stringOffsets.length - 1) + 3)));
            l.addLast(new UInt8Item((char) stringsIndexOffSize));
            int[] iArr2 = this.stringOffsets;
            IndexBaseItem indexBaseItem2 = topdictBase;
            int i7 = 0;
            for (int length = iArr2.length; i7 < length; length = length) {
                l.addLast(new IndexOffsetItem(stringsIndexOffSize, iArr2[i7] - stringsBaseOffset));
                i7++;
                iArr2 = iArr2;
            }
            int[] iArr3 = this.stringOffsets;
            int currentStringsOffset = (iArr3[iArr3.length - 1] - stringsBaseOffset) + "Adobe".length();
            l.addLast(new IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
            int currentStringsOffset2 = currentStringsOffset + "Identity".length();
            l.addLast(new IndexOffsetItem(stringsIndexOffSize, currentStringsOffset2));
            l.addLast(new IndexOffsetItem(stringsIndexOffSize, currentStringsOffset2 + fdFontName.length()));
            String str2 = fdFontName;
            l.addLast(new RangeItem(this.buf, this.stringOffsets[0], origStringsLen));
            l.addLast(new StringItem(extraStrings));
        }
        l.addLast(getEntireIndexRange(this.gsubrIndexOffset));
        if (!this.fonts[j].isCID) {
            l.addLast(new MarkerItem(fdselectRef2));
            l.addLast(new UInt8Item(3));
            l.addLast(new UInt16Item(1));
            l.addLast(new UInt16Item(0));
            l.addLast(new UInt8Item(0));
            l.addLast(new UInt16Item((char) nglyphs));
            l.addLast(new MarkerItem(charsetRef));
            l.addLast(new UInt8Item(2));
            l.addLast(new UInt16Item(1));
            l.addLast(new UInt16Item((char) (nglyphs - 1)));
            l.addLast(new MarkerItem(fdarrayRef2));
            l.addLast(new UInt16Item(1));
            l.addLast(new UInt8Item(1));
            l.addLast(new UInt8Item(1));
            OffsetItem privateIndex1Ref = new IndexOffsetItem(1);
            l.addLast(privateIndex1Ref);
            IndexBaseItem privateBase = new IndexBaseItem();
            l.addLast(privateBase);
            l.addLast(new DictNumberItem(this.fonts[j].privateLength));
            OffsetItem privateRef = new DictOffsetItem();
            l.addLast(privateRef);
            l.addLast(new UInt8Item(18));
            l.addLast(new IndexMarkerItem(privateIndex1Ref, privateBase));
            l.addLast(new MarkerItem(privateRef));
            l.addLast(new RangeItem(this.buf, this.fonts[j].privateOffset, this.fonts[j].privateLength));
            if (this.fonts[j].privateSubrs >= 0) {
                l.addLast(getEntireIndexRange(this.fonts[j].privateSubrs));
            }
        }
        l.addLast(new MarkerItem(charstringsRef2));
        l.addLast(getEntireIndexRange(this.fonts[j].charstringsOffset));
        int[] currentOffset = {0};
        Iterator it = l.iterator();
        while (it.hasNext()) {
            ((Item) it.next()).increment(currentOffset);
        }
        Iterator it2 = l.iterator();
        while (it2.hasNext()) {
            ((Item) it2.next()).xref();
        }
        byte[] b = new byte[currentOffset[0]];
        Iterator it3 = l.iterator();
        while (it3.hasNext()) {
            ((Item) it3.next()).emit(b);
        }
        return b;
    }

    public boolean isCID(String fontName) {
        int j = 0;
        while (true) {
            Font[] fontArr = this.fonts;
            if (j >= fontArr.length) {
                return false;
            }
            if (fontName.equals(fontArr[j].name)) {
                return this.fonts[j].isCID;
            }
            j++;
        }
    }

    public boolean exists(String fontName) {
        int j = 0;
        while (true) {
            Font[] fontArr = this.fonts;
            if (j >= fontArr.length) {
                return false;
            }
            if (fontName.equals(fontArr[j].name)) {
                return true;
            }
            j++;
        }
    }

    public String[] getNames() {
        String[] names = new String[this.fonts.length];
        int i = 0;
        while (true) {
            Font[] fontArr = this.fonts;
            if (i >= fontArr.length) {
                return names;
            }
            names[i] = fontArr[i].name;
            i++;
        }
    }

    /* renamed from: com.itextpdf.io.font.CFFFont$Font */
    protected final class Font {
        public int CharsetLength;
        public int CharstringType = 2;
        public int FDArrayCount;
        public int[] FDArrayOffsets;
        public int FDArrayOffsize;
        public int[] FDSelect;
        public int FDSelectFormat;
        public int FDSelectLength;
        public int[] PrivateSubrsOffset;
        public int[][] PrivateSubrsOffsetsArray;
        public int[] SubrsOffsets;
        public int[] charset;
        public int charsetOffset = -1;
        public int charstringsOffset = -1;
        public int[] charstringsOffsets;
        public int encodingOffset = -1;
        public int fdarrayOffset = -1;
        public int[] fdprivateLengths;
        public int[] fdprivateOffsets;
        public int[] fdprivateSubrs;
        public int fdselectOffset = -1;
        public String fullName;
        public boolean isCID = false;
        public String name;
        public int nglyphs;
        public int nstrings;
        public int privateLength = -1;
        public int privateOffset = -1;
        public int privateSubrs = -1;

        protected Font() {
        }
    }

    public CFFFont(byte[] cff) {
        this.buf = new RandomAccessFileOrArray(this.rasFactory.createSource(cff));
        seek(0);
        char card8 = getCard8();
        char card82 = getCard8();
        int hdrSize = getCard8();
        this.offSize = getCard8();
        this.nameIndexOffset = hdrSize;
        int[] index = getIndex(hdrSize);
        this.nameOffsets = index;
        int i = index[index.length - 1];
        this.topdictIndexOffset = i;
        int[] index2 = getIndex(i);
        this.topdictOffsets = index2;
        int i2 = index2[index2.length - 1];
        this.stringIndexOffset = i2;
        int[] index3 = getIndex(i2);
        this.stringOffsets = index3;
        int i3 = index3[index3.length - 1];
        this.gsubrIndexOffset = i3;
        this.gsubrOffsets = getIndex(i3);
        this.fonts = new Font[(this.nameOffsets.length - 1)];
        for (int j = 0; j < this.nameOffsets.length - 1; j++) {
            this.fonts[j] = new Font();
            seek(this.nameOffsets[j]);
            this.fonts[j].name = "";
            for (int k = this.nameOffsets[j]; k < this.nameOffsets[j + 1]; k++) {
                StringBuilder sb = new StringBuilder();
                Font font = this.fonts[j];
                font.name = sb.append(font.name).append(getCard8()).toString();
            }
        }
        int j2 = 0;
        while (true) {
            int[] iArr = this.topdictOffsets;
            if (j2 < iArr.length - 1) {
                seek(iArr[j2]);
                while (getPosition() < this.topdictOffsets[j2 + 1]) {
                    getDictItem();
                    String str = this.key;
                    if (str == "FullName") {
                        this.fonts[j2].fullName = getString((char) ((Integer) this.args[0]).intValue());
                    } else if (str == "ROS") {
                        this.fonts[j2].isCID = true;
                    } else if (str == StandardRoles.PRIVATE) {
                        this.fonts[j2].privateLength = ((Integer) this.args[0]).intValue();
                        this.fonts[j2].privateOffset = ((Integer) this.args[1]).intValue();
                    } else if (str == "charset") {
                        this.fonts[j2].charsetOffset = ((Integer) this.args[0]).intValue();
                    } else if (str == "CharStrings") {
                        this.fonts[j2].charstringsOffset = ((Integer) this.args[0]).intValue();
                        int p = getPosition();
                        Font font2 = this.fonts[j2];
                        font2.charstringsOffsets = getIndex(font2.charstringsOffset);
                        seek(p);
                    } else if (str == "FDArray") {
                        this.fonts[j2].fdarrayOffset = ((Integer) this.args[0]).intValue();
                    } else if (str == "FDSelect") {
                        this.fonts[j2].fdselectOffset = ((Integer) this.args[0]).intValue();
                    } else if (str == "CharstringType") {
                        this.fonts[j2].CharstringType = ((Integer) this.args[0]).intValue();
                    }
                }
                if (this.fonts[j2].privateOffset >= 0) {
                    seek(this.fonts[j2].privateOffset);
                    while (getPosition() < this.fonts[j2].privateOffset + this.fonts[j2].privateLength) {
                        getDictItem();
                        if (this.key == "Subrs") {
                            this.fonts[j2].privateSubrs = ((Integer) this.args[0]).intValue() + this.fonts[j2].privateOffset;
                        }
                    }
                }
                if (this.fonts[j2].fdarrayOffset >= 0) {
                    int[] fdarrayOffsets = getIndex(this.fonts[j2].fdarrayOffset);
                    this.fonts[j2].fdprivateOffsets = new int[(fdarrayOffsets.length - 1)];
                    this.fonts[j2].fdprivateLengths = new int[(fdarrayOffsets.length - 1)];
                    for (int k2 = 0; k2 < fdarrayOffsets.length - 1; k2++) {
                        seek(fdarrayOffsets[k2]);
                        while (getPosition() < fdarrayOffsets[k2 + 1]) {
                            getDictItem();
                            if (this.key == StandardRoles.PRIVATE) {
                                this.fonts[j2].fdprivateLengths[k2] = ((Integer) this.args[0]).intValue();
                                this.fonts[j2].fdprivateOffsets[k2] = ((Integer) this.args[1]).intValue();
                            }
                        }
                    }
                }
                j2++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ReadEncoding(int nextIndexOffset2) {
        seek(nextIndexOffset2);
        char card8 = getCard8();
    }
}
