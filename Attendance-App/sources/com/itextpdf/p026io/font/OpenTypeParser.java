package com.itextpdf.p026io.font;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.p026io.font.constants.FontStretches;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.util.IntHashtable;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* renamed from: com.itextpdf.io.font.OpenTypeParser */
class OpenTypeParser implements Serializable, Closeable {
    private static final int HEAD_LOCA_FORMAT_OFFSET = 51;
    private static final long serialVersionUID = 3399061674525229738L;
    protected Map<Integer, List<String[]>> allNameEntries;
    protected boolean cff = false;
    protected int cffLength;
    protected int cffOffset;
    protected CmapTable cmaps;
    protected int directoryOffset;
    protected String fileName;
    protected String fontName;
    private int[] glyphWidthsByIndex;
    protected HeaderTable head;
    protected HorizontalHeader hhea;
    protected WindowsMetrics os_2;
    protected PostTable post;
    protected RandomAccessFileOrArray raf;
    protected Map<String, int[]> tables;
    protected int ttcIndex = -1;

    /* renamed from: com.itextpdf.io.font.OpenTypeParser$HeaderTable */
    static class HeaderTable implements Serializable {
        private static final long serialVersionUID = 5849907401352439751L;
        int flags;
        int macStyle;
        int unitsPerEm;
        short xMax;
        short xMin;
        short yMax;
        short yMin;

        HeaderTable() {
        }
    }

    /* renamed from: com.itextpdf.io.font.OpenTypeParser$HorizontalHeader */
    static class HorizontalHeader implements Serializable {
        private static final long serialVersionUID = -6857266170153679811L;
        short Ascender;
        short Descender;
        short LineGap;
        int advanceWidthMax;
        short caretSlopeRise;
        short caretSlopeRun;
        short minLeftSideBearing;
        short minRightSideBearing;
        int numberOfHMetrics;
        short xMaxExtent;

        HorizontalHeader() {
        }
    }

    /* renamed from: com.itextpdf.io.font.OpenTypeParser$WindowsMetrics */
    static class WindowsMetrics implements Serializable {
        private static final long serialVersionUID = -9117114979326346658L;
        byte[] achVendID = new byte[4];
        int fsSelection;
        short fsType;
        byte[] panose = new byte[10];
        int sCapHeight;
        short sFamilyClass;
        short sTypoAscender;
        short sTypoDescender;
        short sTypoLineGap;
        int sxHeight;
        int ulCodePageRange1;
        int ulCodePageRange2;
        int usFirstCharIndex;
        int usLastCharIndex;
        int usWeightClass;
        int usWidthClass;
        int usWinAscent;
        int usWinDescent;
        short xAvgCharWidth;
        short yStrikeoutPosition;
        short yStrikeoutSize;
        short ySubscriptXOffset;
        short ySubscriptXSize;
        short ySubscriptYOffset;
        short ySubscriptYSize;
        short ySuperscriptXOffset;
        short ySuperscriptXSize;
        short ySuperscriptYOffset;
        short ySuperscriptYSize;

        WindowsMetrics() {
        }
    }

    /* renamed from: com.itextpdf.io.font.OpenTypeParser$PostTable */
    static class PostTable implements Serializable {
        private static final long serialVersionUID = 5735677308357646890L;
        boolean isFixedPitch;
        float italicAngle;
        int underlinePosition;
        int underlineThickness;

        PostTable() {
        }
    }

    /* renamed from: com.itextpdf.io.font.OpenTypeParser$CmapTable */
    static class CmapTable implements Serializable {
        private static final long serialVersionUID = 8923883989692194983L;
        Map<Integer, int[]> cmap10;
        Map<Integer, int[]> cmap31;
        Map<Integer, int[]> cmapExt;
        boolean fontSpecific = false;

        CmapTable() {
        }
    }

    public OpenTypeParser(byte[] ttf) throws IOException {
        this.raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(ttf));
        initializeSfntTables();
    }

    public OpenTypeParser(byte[] ttc, int ttcIndex2) throws IOException {
        this.ttcIndex = ttcIndex2;
        this.raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(ttc));
        initializeSfntTables();
    }

    public OpenTypeParser(String ttcPath, int ttcIndex2) throws IOException {
        this.ttcIndex = ttcIndex2;
        this.raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(ttcPath));
        initializeSfntTables();
    }

    public OpenTypeParser(String name) throws IOException {
        String ttcName = getTTCName(name);
        this.fileName = ttcName;
        if (ttcName.length() < name.length()) {
            this.ttcIndex = Integer.parseInt(name.substring(ttcName.length() + 1));
        }
        this.raf = new RandomAccessFileOrArray(new RandomAccessSourceFactory().createBestSource(this.fileName));
        initializeSfntTables();
    }

    public String getPsFontName() {
        if (this.fontName == null) {
            List<String[]> names = this.allNameEntries.get(6);
            if (names == null || names.size() <= 0) {
                this.fontName = new File(this.fileName).getName().replace(' ', '-');
            } else {
                this.fontName = names.get(0)[3];
            }
        }
        return this.fontName;
    }

    public Map<Integer, List<String[]>> getAllNameEntries() {
        return this.allNameEntries;
    }

    public PostTable getPostTable() {
        return this.post;
    }

    public WindowsMetrics getOs_2Table() {
        return this.os_2;
    }

    public HorizontalHeader getHheaTable() {
        return this.hhea;
    }

    public HeaderTable getHeadTable() {
        return this.head;
    }

    public CmapTable getCmapTable() {
        return this.cmaps;
    }

    public int[] getGlyphWidthsByIndex() {
        return this.glyphWidthsByIndex;
    }

    public FontNames getFontNames() {
        FontNames fontNames = new FontNames();
        fontNames.setAllNames(getAllNameEntries());
        fontNames.setFontName(getPsFontName());
        fontNames.setFullName(fontNames.getNames(4));
        String[][] otfFamilyName = fontNames.getNames(16);
        boolean z = true;
        if (otfFamilyName != null) {
            fontNames.setFamilyName(otfFamilyName);
        } else {
            fontNames.setFamilyName(fontNames.getNames(1));
        }
        String[][] subfamily = fontNames.getNames(2);
        if (subfamily != null) {
            fontNames.setStyle(subfamily[0][3]);
        }
        String[][] otfSubFamily = fontNames.getNames(17);
        if (otfFamilyName != null) {
            fontNames.setSubfamily(otfSubFamily);
        } else {
            fontNames.setSubfamily(subfamily);
        }
        String[][] cidName = fontNames.getNames(20);
        if (cidName != null) {
            fontNames.setCidFontName(cidName[0][3]);
        }
        fontNames.setFontWeight(this.os_2.usWeightClass);
        fontNames.setFontStretch(FontStretches.fromOpenTypeWidthClass(this.os_2.usWidthClass));
        fontNames.setMacStyle(this.head.macStyle);
        if (this.os_2.fsType == 2) {
            z = false;
        }
        fontNames.setAllowEmbedding(z);
        return fontNames;
    }

    public boolean isCff() {
        return this.cff;
    }

    public byte[] getFullFont() throws IOException {
        RandomAccessFileOrArray rf2 = null;
        try {
            rf2 = this.raf.createView();
            byte[] b = new byte[((int) rf2.length())];
            rf2.readFully(b);
            return b;
        } finally {
            if (rf2 != null) {
                try {
                    rf2.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public byte[] readCffFont() throws IOException {
        if (!isCff()) {
            return null;
        }
        RandomAccessFileOrArray rf2 = null;
        try {
            rf2 = this.raf.createView();
            rf2.seek((long) this.cffOffset);
            byte[] cff2 = new byte[this.cffLength];
            rf2.readFully(cff2);
            return cff2;
        } finally {
            if (rf2 != null) {
                try {
                    rf2.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] getSubset(Set<Integer> glyphs, boolean subset) throws IOException {
        return new TrueTypeFontSubset(this.fileName, this.raf.createView(), glyphs, this.directoryOffset, subset).process();
    }

    public void close() throws IOException {
        RandomAccessFileOrArray randomAccessFileOrArray = this.raf;
        if (randomAccessFileOrArray != null) {
            randomAccessFileOrArray.close();
        }
        this.raf = null;
    }

    private void initializeSfntTables() throws IOException {
        this.tables = new LinkedHashMap();
        if (this.ttcIndex >= 0) {
            int dirIdx = this.ttcIndex;
            if (dirIdx < 0) {
                if (this.fileName != null) {
                    throw new com.itextpdf.p026io.IOException("The font index for {0} must be positive.").setMessageParams(this.fileName);
                }
                throw new com.itextpdf.p026io.IOException("The font index must be positive.");
            } else if (readStandardString(4).equals("ttcf")) {
                this.raf.skipBytes(4);
                int dirCount = this.raf.readInt();
                if (dirIdx < dirCount) {
                    this.raf.skipBytes(dirIdx * 4);
                    this.directoryOffset = this.raf.readInt();
                } else if (this.fileName != null) {
                    throw new com.itextpdf.p026io.IOException("The font index for {0} must be between 0 and {1}. It is {2}.").setMessageParams(this.fileName, Integer.valueOf(dirCount - 1), Integer.valueOf(dirIdx));
                } else {
                    throw new com.itextpdf.p026io.IOException("The font index must be between 0 and {0}. It is {1}.").setMessageParams(Integer.valueOf(dirCount - 1), Integer.valueOf(dirIdx));
                }
            } else if (this.fileName != null) {
                throw new com.itextpdf.p026io.IOException("{0} is not a valid ttc file.").setMessageParams(this.fileName);
            } else {
                throw new com.itextpdf.p026io.IOException("Not a valid ttc file.");
            }
        }
        this.raf.seek((long) this.directoryOffset);
        int ttId = this.raf.readInt();
        if (ttId == 65536 || ttId == 1330926671) {
            int num_tables = this.raf.readUnsignedShort();
            this.raf.skipBytes(6);
            for (int k = 0; k < num_tables; k++) {
                String tag = readStandardString(4);
                this.raf.skipBytes(4);
                this.tables.put(tag, new int[]{this.raf.readInt(), this.raf.readInt()});
            }
        } else if (this.fileName != null) {
            throw new com.itextpdf.p026io.IOException("{0} is not a valid ttf or otf file.").setMessageParams(this.fileName);
        } else {
            throw new com.itextpdf.p026io.IOException("Not a valid ttf or otf file.");
        }
    }

    /* access modifiers changed from: protected */
    public void loadTables(boolean all) throws IOException {
        readNameTable();
        readHeadTable();
        readOs_2Table();
        readPostTable();
        if (all) {
            checkCff();
            readHheaTable();
            readGlyphWidths();
            readCmapTable();
        }
    }

    protected static String getTTCName(String name) {
        if (name == null) {
            return null;
        }
        int idx = name.toLowerCase().indexOf(".ttc,");
        if (idx < 0) {
            return name;
        }
        return name.substring(0, idx + 4);
    }

    /* access modifiers changed from: protected */
    public void checkCff() {
        int[] table_location = this.tables.get("CFF ");
        if (table_location != null) {
            this.cff = true;
            this.cffOffset = table_location[0];
            this.cffLength = table_location[1];
        }
    }

    /* access modifiers changed from: protected */
    public void readGlyphWidths() throws IOException {
        int numberOfHMetrics = this.hhea.numberOfHMetrics;
        int unitsPerEm = this.head.unitsPerEm;
        int[] table_location = this.tables.get("hmtx");
        if (table_location != null) {
            this.glyphWidthsByIndex = new int[readNumGlyphs()];
            this.raf.seek((long) table_location[0]);
            for (int k = 0; k < numberOfHMetrics; k++) {
                this.glyphWidthsByIndex[k] = (this.raf.readUnsignedShort() * 1000) / unitsPerEm;
                int readShort = (this.raf.readShort() * 1000) / unitsPerEm;
            }
            if (numberOfHMetrics > 0) {
                int k2 = numberOfHMetrics;
                while (true) {
                    int[] iArr = this.glyphWidthsByIndex;
                    if (k2 < iArr.length) {
                        iArr[k2] = iArr[numberOfHMetrics - 1];
                        k2++;
                    } else {
                        return;
                    }
                }
            }
        } else if (this.fileName != null) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams("hmtx", this.fileName);
        } else {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExist).setMessageParams("hmtx");
        }
    }

    /* access modifiers changed from: protected */
    public IntHashtable readKerning(int unitsPerEm) throws IOException {
        int[] table_location = this.tables.get("kern");
        IntHashtable kerning = new IntHashtable();
        if (table_location == null) {
            return kerning;
        }
        this.raf.seek((long) (table_location[0] + 2));
        int nTables = this.raf.readUnsignedShort();
        int checkpoint = table_location[0] + 4;
        int length = 0;
        for (int k = 0; k < nTables; k++) {
            checkpoint += length;
            this.raf.seek((long) checkpoint);
            this.raf.skipBytes(2);
            length = this.raf.readUnsignedShort();
            if ((65527 & this.raf.readUnsignedShort()) == 1) {
                int nPairs = this.raf.readUnsignedShort();
                this.raf.skipBytes(6);
                for (int j = 0; j < nPairs; j++) {
                    kerning.put(this.raf.readInt(), (this.raf.readShort() * 1000) / unitsPerEm);
                }
            }
        }
        return kerning;
    }

    /* access modifiers changed from: protected */
    public int[][] readBbox(int unitsPerEm) throws IOException {
        int[] locaTable;
        int[] tableLocation = this.tables.get(XfdfConstants.HEAD);
        if (tableLocation != null) {
            this.raf.seek((long) (tableLocation[0] + 51));
            boolean locaShortTable = this.raf.readUnsignedShort() == 0;
            int[] tableLocation2 = this.tables.get("loca");
            if (tableLocation2 == null) {
                int[][] iArr = null;
                return null;
            }
            this.raf.seek((long) tableLocation2[0]);
            if (locaShortTable) {
                int entries = tableLocation2[1] / 2;
                locaTable = new int[entries];
                for (int k = 0; k < entries; k++) {
                    locaTable[k] = this.raf.readUnsignedShort() * 2;
                }
            } else {
                int entries2 = tableLocation2[1] / 4;
                locaTable = new int[entries2];
                for (int k2 = 0; k2 < entries2; k2++) {
                    locaTable[k2] = this.raf.readInt();
                }
            }
            int[] tableLocation3 = this.tables.get("glyf");
            if (tableLocation3 != null) {
                int tableGlyphOffset = tableLocation3[0];
                int[][] bboxes = new int[(locaTable.length - 1)][];
                for (int glyph = 0; glyph < locaTable.length - 1; glyph++) {
                    int start = locaTable[glyph];
                    if (start != locaTable[glyph + 1]) {
                        this.raf.seek((long) (tableGlyphOffset + start + 2));
                        bboxes[glyph] = new int[]{(this.raf.readShort() * 1000) / unitsPerEm, (this.raf.readShort() * 1000) / unitsPerEm, (this.raf.readShort() * 1000) / unitsPerEm, (this.raf.readShort() * 1000) / unitsPerEm};
                    }
                }
                return bboxes;
            } else if (this.fileName != null) {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams("glyf", this.fileName);
            } else {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExist).setMessageParams("glyf");
            }
        } else if (this.fileName != null) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams(XfdfConstants.HEAD, this.fileName);
        } else {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExist).setMessageParams(XfdfConstants.HEAD);
        }
    }

    /* access modifiers changed from: protected */
    public int readNumGlyphs() throws IOException {
        int[] table_location = this.tables.get("maxp");
        if (table_location == null) {
            return 65536;
        }
        this.raf.seek((long) (table_location[0] + 4));
        return this.raf.readUnsignedShort();
    }

    private void readNameTable() throws IOException {
        List<String[]> names;
        String name;
        OpenTypeParser openTypeParser = this;
        int[] table_location = openTypeParser.tables.get(XfdfConstants.NAME);
        char c = 0;
        if (table_location != null) {
            openTypeParser.allNameEntries = new LinkedHashMap();
            openTypeParser.raf.seek((long) (table_location[0] + 2));
            int numRecords = openTypeParser.raf.readUnsignedShort();
            int startOfStorage = openTypeParser.raf.readUnsignedShort();
            int k = 0;
            while (k < numRecords) {
                int platformID = openTypeParser.raf.readUnsignedShort();
                int platformEncodingID = openTypeParser.raf.readUnsignedShort();
                int languageID = openTypeParser.raf.readUnsignedShort();
                int nameID = openTypeParser.raf.readUnsignedShort();
                int length = openTypeParser.raf.readUnsignedShort();
                int offset = openTypeParser.raf.readUnsignedShort();
                if (openTypeParser.allNameEntries.containsKey(Integer.valueOf(nameID))) {
                    names = openTypeParser.allNameEntries.get(Integer.valueOf(nameID));
                } else {
                    Map<Integer, List<String[]>> map = openTypeParser.allNameEntries;
                    Integer valueOf = Integer.valueOf(nameID);
                    List<String[]> names2 = new ArrayList<>();
                    map.put(valueOf, names2);
                    names = names2;
                }
                int pos = (int) openTypeParser.raf.getPosition();
                int startOfStorage2 = startOfStorage;
                openTypeParser.raf.seek((long) (table_location[c] + startOfStorage + offset));
                if (platformID == 0 || platformID == 3 || (platformID == 2 && platformEncodingID == 1)) {
                    name = openTypeParser.readUnicodeString(length);
                } else {
                    name = openTypeParser.readStandardString(length);
                }
                names.add(new String[]{Integer.toString(platformID), Integer.toString(platformEncodingID), Integer.toString(languageID), name});
                openTypeParser.raf.seek((long) pos);
                k++;
                openTypeParser = this;
                table_location = table_location;
                startOfStorage = startOfStorage2;
                c = 0;
            }
        } else if (openTypeParser.fileName != null) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams(XfdfConstants.NAME, openTypeParser.fileName);
        } else {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExist).setMessageParams(XfdfConstants.NAME);
        }
    }

    private void readHheaTable() throws IOException {
        int[] table_location = this.tables.get("hhea");
        if (table_location != null) {
            this.raf.seek((long) (table_location[0] + 4));
            HorizontalHeader horizontalHeader = new HorizontalHeader();
            this.hhea = horizontalHeader;
            horizontalHeader.Ascender = this.raf.readShort();
            this.hhea.Descender = this.raf.readShort();
            this.hhea.LineGap = this.raf.readShort();
            this.hhea.advanceWidthMax = this.raf.readUnsignedShort();
            this.hhea.minLeftSideBearing = this.raf.readShort();
            this.hhea.minRightSideBearing = this.raf.readShort();
            this.hhea.xMaxExtent = this.raf.readShort();
            this.hhea.caretSlopeRise = this.raf.readShort();
            this.hhea.caretSlopeRun = this.raf.readShort();
            this.raf.skipBytes(12);
            this.hhea.numberOfHMetrics = this.raf.readUnsignedShort();
        } else if (this.fileName != null) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams("hhea", this.fileName);
        } else {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExist).setMessageParams("hhea");
        }
    }

    private void readHeadTable() throws IOException {
        int[] table_location = this.tables.get(XfdfConstants.HEAD);
        if (table_location != null) {
            this.raf.seek((long) (table_location[0] + 16));
            HeaderTable headerTable = new HeaderTable();
            this.head = headerTable;
            headerTable.flags = this.raf.readUnsignedShort();
            this.head.unitsPerEm = this.raf.readUnsignedShort();
            this.raf.skipBytes(16);
            this.head.xMin = this.raf.readShort();
            this.head.yMin = this.raf.readShort();
            this.head.xMax = this.raf.readShort();
            this.head.yMax = this.raf.readShort();
            this.head.macStyle = this.raf.readUnsignedShort();
        } else if (this.fileName != null) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams(XfdfConstants.HEAD, this.fileName);
        } else {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExist).setMessageParams(XfdfConstants.HEAD);
        }
    }

    private void readOs_2Table() throws IOException {
        int[] table_location = this.tables.get("OS/2");
        if (table_location != null) {
            this.os_2 = new WindowsMetrics();
            this.raf.seek((long) table_location[0]);
            int version = this.raf.readUnsignedShort();
            this.os_2.xAvgCharWidth = this.raf.readShort();
            this.os_2.usWeightClass = this.raf.readUnsignedShort();
            this.os_2.usWidthClass = this.raf.readUnsignedShort();
            this.os_2.fsType = this.raf.readShort();
            this.os_2.ySubscriptXSize = this.raf.readShort();
            this.os_2.ySubscriptYSize = this.raf.readShort();
            this.os_2.ySubscriptXOffset = this.raf.readShort();
            this.os_2.ySubscriptYOffset = this.raf.readShort();
            this.os_2.ySuperscriptXSize = this.raf.readShort();
            this.os_2.ySuperscriptYSize = this.raf.readShort();
            this.os_2.ySuperscriptXOffset = this.raf.readShort();
            this.os_2.ySuperscriptYOffset = this.raf.readShort();
            this.os_2.yStrikeoutSize = this.raf.readShort();
            this.os_2.yStrikeoutPosition = this.raf.readShort();
            this.os_2.sFamilyClass = this.raf.readShort();
            this.raf.readFully(this.os_2.panose);
            this.raf.skipBytes(16);
            this.raf.readFully(this.os_2.achVendID);
            this.os_2.fsSelection = this.raf.readUnsignedShort();
            this.os_2.usFirstCharIndex = this.raf.readUnsignedShort();
            this.os_2.usLastCharIndex = this.raf.readUnsignedShort();
            this.os_2.sTypoAscender = this.raf.readShort();
            this.os_2.sTypoDescender = this.raf.readShort();
            if (this.os_2.sTypoDescender > 0) {
                WindowsMetrics windowsMetrics = this.os_2;
                windowsMetrics.sTypoDescender = (short) (-windowsMetrics.sTypoDescender);
            }
            this.os_2.sTypoLineGap = this.raf.readShort();
            this.os_2.usWinAscent = this.raf.readUnsignedShort();
            this.os_2.usWinDescent = this.raf.readUnsignedShort();
            if (this.os_2.usWinDescent > 0) {
                WindowsMetrics windowsMetrics2 = this.os_2;
                windowsMetrics2.usWinDescent = (short) (-windowsMetrics2.usWinDescent);
            }
            this.os_2.ulCodePageRange1 = 0;
            this.os_2.ulCodePageRange2 = 0;
            if (version > 0) {
                this.os_2.ulCodePageRange1 = this.raf.readInt();
                this.os_2.ulCodePageRange2 = this.raf.readInt();
            }
            if (version > 1) {
                this.raf.skipBytes(2);
                this.os_2.sCapHeight = this.raf.readShort();
                return;
            }
            WindowsMetrics windowsMetrics3 = this.os_2;
            double d = (double) this.head.unitsPerEm;
            Double.isNaN(d);
            windowsMetrics3.sCapHeight = (int) (d * 0.7d);
        } else if (this.fileName != null) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams("os/2", this.fileName);
        } else {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExist).setMessageParams("os/2");
        }
    }

    private void readPostTable() throws IOException {
        int[] table_location = this.tables.get("post");
        if (table_location != null) {
            boolean z = false;
            this.raf.seek((long) (table_location[0] + 4));
            short mantissa = this.raf.readShort();
            int fraction = this.raf.readUnsignedShort();
            PostTable postTable = new PostTable();
            this.post = postTable;
            double d = (double) mantissa;
            double d2 = (double) fraction;
            Double.isNaN(d2);
            Double.isNaN(d);
            postTable.italicAngle = (float) (d + (d2 / 16384.0d));
            this.post.underlinePosition = this.raf.readShort();
            this.post.underlineThickness = this.raf.readShort();
            PostTable postTable2 = this.post;
            if (this.raf.readInt() != 0) {
                z = true;
            }
            postTable2.isFixedPitch = z;
            return;
        }
        PostTable postTable3 = new PostTable();
        this.post = postTable3;
        postTable3.italicAngle = (float) (((-Math.atan2((double) this.hhea.caretSlopeRun, (double) this.hhea.caretSlopeRise)) * 180.0d) / 3.141592653589793d);
    }

    private void readCmapTable() throws IOException {
        int[] table_location = this.tables.get("cmap");
        if (table_location != null) {
            this.raf.seek((long) table_location[0]);
            this.raf.skipBytes(2);
            int num_tables = this.raf.readUnsignedShort();
            int map10 = 0;
            int map31 = 0;
            int map30 = 0;
            int mapExt = 0;
            this.cmaps = new CmapTable();
            for (int k = 0; k < num_tables; k++) {
                int platId = this.raf.readUnsignedShort();
                int platSpecId = this.raf.readUnsignedShort();
                int offset = this.raf.readInt();
                if (platId == 3 && platSpecId == 0) {
                    this.cmaps.fontSpecific = true;
                    map30 = offset;
                } else if (platId == 3 && platSpecId == 1) {
                    map31 = offset;
                } else if (platId == 3 && platSpecId == 10) {
                    mapExt = offset;
                } else if (platId == 1 && platSpecId == 0) {
                    map10 = offset;
                }
            }
            if (map10 > 0) {
                this.raf.seek((long) (table_location[0] + map10));
                switch (this.raf.readUnsignedShort()) {
                    case 0:
                        this.cmaps.cmap10 = readFormat0();
                        break;
                    case 4:
                        this.cmaps.cmap10 = readFormat4(false);
                        break;
                    case 6:
                        this.cmaps.cmap10 = readFormat6();
                        break;
                }
            }
            if (map31 > 0) {
                this.raf.seek((long) (table_location[0] + map31));
                if (this.raf.readUnsignedShort() == 4) {
                    this.cmaps.cmap31 = readFormat4(false);
                }
            }
            if (map30 > 0) {
                this.raf.seek((long) (table_location[0] + map30));
                if (this.raf.readUnsignedShort() == 4) {
                    CmapTable cmapTable = this.cmaps;
                    cmapTable.cmap10 = readFormat4(cmapTable.fontSpecific);
                } else {
                    this.cmaps.fontSpecific = false;
                }
            }
            if (mapExt > 0) {
                this.raf.seek((long) (table_location[0] + mapExt));
                switch (this.raf.readUnsignedShort()) {
                    case 0:
                        this.cmaps.cmapExt = readFormat0();
                        return;
                    case 4:
                        this.cmaps.cmapExt = readFormat4(false);
                        return;
                    case 6:
                        this.cmaps.cmapExt = readFormat6();
                        return;
                    case 12:
                        this.cmaps.cmapExt = readFormat12();
                        return;
                    default:
                        return;
                }
            }
        } else if (this.fileName != null) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams("cmap", this.fileName);
        } else {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExist).setMessageParams("cmap");
        }
    }

    private String readStandardString(int length) throws IOException {
        return this.raf.readString(length, "Cp1252");
    }

    private String readUnicodeString(int length) throws IOException {
        StringBuilder buf = new StringBuilder();
        int length2 = length / 2;
        for (int k = 0; k < length2; k++) {
            buf.append(this.raf.readChar());
        }
        return buf.toString();
    }

    /* access modifiers changed from: protected */
    public int getGlyphWidth(int glyph) {
        int[] iArr = this.glyphWidthsByIndex;
        if (glyph >= iArr.length) {
            glyph = iArr.length - 1;
        }
        return iArr[glyph];
    }

    private Map<Integer, int[]> readFormat0() throws IOException {
        Map<Integer, int[]> h = new LinkedHashMap<>();
        this.raf.skipBytes(4);
        for (int k = 0; k < 256; k++) {
            int[] r = new int[2];
            r[0] = this.raf.readUnsignedByte();
            r[1] = getGlyphWidth(r[0]);
            h.put(Integer.valueOf(k), r);
        }
        return h;
    }

    private Map<Integer, int[]> readFormat4(boolean fontSpecific) throws IOException {
        int glyph;
        Map<Integer, int[]> h = new LinkedHashMap<>();
        int table_lenght = this.raf.readUnsignedShort();
        this.raf.skipBytes(2);
        int segCount = this.raf.readUnsignedShort() / 2;
        this.raf.skipBytes(6);
        int[] endCount = new int[segCount];
        for (int k = 0; k < segCount; k++) {
            endCount[k] = this.raf.readUnsignedShort();
        }
        this.raf.skipBytes(2);
        int[] startCount = new int[segCount];
        for (int k2 = 0; k2 < segCount; k2++) {
            startCount[k2] = this.raf.readUnsignedShort();
        }
        int[] idDelta = new int[segCount];
        for (int k3 = 0; k3 < segCount; k3++) {
            idDelta[k3] = this.raf.readUnsignedShort();
        }
        int[] idRO = new int[segCount];
        for (int k4 = 0; k4 < segCount; k4++) {
            idRO[k4] = this.raf.readUnsignedShort();
        }
        int[] glyphId = new int[(((table_lenght / 2) - 8) - (segCount * 4))];
        for (int k5 = 0; k5 < glyphId.length; k5++) {
            glyphId[k5] = this.raf.readUnsignedShort();
        }
        for (int k6 = 0; k6 < segCount; k6++) {
            int j = startCount[k6];
            while (j <= endCount[k6] && j != 65535) {
                if (idRO[k6] == 0) {
                    glyph = 65535 & (idDelta[k6] + j);
                } else {
                    int idx = ((((idRO[k6] / 2) + k6) - segCount) + j) - startCount[k6];
                    if (idx >= glyphId.length) {
                        j++;
                    } else {
                        glyph = 65535 & (glyphId[idx] + idDelta[k6]);
                    }
                }
                int[] r = new int[2];
                r[0] = glyph;
                r[1] = getGlyphWidth(r[0]);
                if (fontSpecific && (65280 & j) == 61440) {
                    h.put(Integer.valueOf(j & 255), r);
                }
                h.put(Integer.valueOf(j), r);
                j++;
            }
        }
        return h;
    }

    private Map<Integer, int[]> readFormat6() throws IOException {
        Map<Integer, int[]> h = new LinkedHashMap<>();
        this.raf.skipBytes(4);
        int start_code = this.raf.readUnsignedShort();
        int code_count = this.raf.readUnsignedShort();
        for (int k = 0; k < code_count; k++) {
            int[] r = new int[2];
            r[0] = this.raf.readUnsignedShort();
            r[1] = getGlyphWidth(r[0]);
            h.put(Integer.valueOf(k + start_code), r);
        }
        return h;
    }

    private Map<Integer, int[]> readFormat12() throws IOException {
        Map<Integer, int[]> h = new LinkedHashMap<>();
        this.raf.skipBytes(2);
        int readInt = this.raf.readInt();
        this.raf.skipBytes(4);
        int nGroups = this.raf.readInt();
        for (int k = 0; k < nGroups; k++) {
            int startCharCode = this.raf.readInt();
            int endCharCode = this.raf.readInt();
            int startGlyphID = this.raf.readInt();
            for (int i = startCharCode; i <= endCharCode; i++) {
                int[] r = new int[2];
                r[0] = startGlyphID;
                r[1] = getGlyphWidth(r[0]);
                h.put(Integer.valueOf(i), r);
                startGlyphID++;
            }
        }
        return h;
    }
}
