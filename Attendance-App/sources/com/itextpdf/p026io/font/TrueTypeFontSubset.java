package com.itextpdf.p026io.font;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.UByte;

/* renamed from: com.itextpdf.io.font.TrueTypeFontSubset */
class TrueTypeFontSubset {
    private static final int ARG_1_AND_2_ARE_WORDS = 1;
    private static final int HEAD_LOCA_FORMAT_OFFSET = 51;
    private static final int MORE_COMPONENTS = 32;
    private static final int TABLE_CHECKSUM = 0;
    private static final int TABLE_LENGTH = 2;
    private static final String[] TABLE_NAMES = {"cvt ", "fpgm", "glyf", XfdfConstants.HEAD, "hhea", "hmtx", "loca", "maxp", "prep", "cmap", "OS/2", XfdfConstants.NAME, "post"};
    private static final String[] TABLE_NAMES_SUBSET = {"cvt ", "fpgm", "glyf", XfdfConstants.HEAD, "hhea", "hmtx", "loca", "maxp", "prep", "cmap", "OS/2"};
    private static final int TABLE_OFFSET = 1;
    private static final int WE_HAVE_AN_X_AND_Y_SCALE = 64;
    private static final int WE_HAVE_A_SCALE = 8;
    private static final int WE_HAVE_A_TWO_BY_TWO = 128;
    private static final int[] entrySelectors = {0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4};
    private int directoryOffset;
    private String fileName;
    private int fontPtr;
    private int glyfTableRealSize;
    private List<Integer> glyphsInList;
    private Set<Integer> glyphsUsed;
    private boolean locaShortTable;
    private int[] locaTable;
    private int locaTableRealSize;
    private byte[] newGlyfTable;
    private int[] newLocaTable;
    private byte[] newLocaTableOut;
    private byte[] outFont;

    /* renamed from: rf */
    protected RandomAccessFileOrArray f1213rf;
    private Map<String, int[]> tableDirectory;
    private int tableGlyphOffset;
    private final String[] tableNames;

    TrueTypeFontSubset(String fileName2, RandomAccessFileOrArray rf, Set<Integer> glyphsUsed2, int directoryOffset2, boolean subset) {
        this.fileName = fileName2;
        this.f1213rf = rf;
        this.glyphsUsed = new HashSet(glyphsUsed2);
        this.directoryOffset = directoryOffset2;
        if (subset) {
            this.tableNames = TABLE_NAMES_SUBSET;
        } else {
            this.tableNames = TABLE_NAMES;
        }
        this.glyphsInList = new ArrayList(glyphsUsed2);
    }

    /* access modifiers changed from: package-private */
    public byte[] process() throws IOException {
        try {
            createTableDirectory();
            readLoca();
            flatGlyphs();
            createNewGlyphTables();
            locaToBytes();
            assembleFont();
            return this.outFont;
        } finally {
            try {
                this.f1213rf.close();
            } catch (Exception e) {
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void assembleFont() throws java.io.IOException {
        /*
            r18 = this;
            r0 = r18
            r1 = 0
            r2 = 2
            java.lang.String[] r3 = r0.tableNames
            int r4 = r3.length
            r5 = 0
            r6 = 0
        L_0x0009:
            java.lang.String r7 = "loca"
            java.lang.String r8 = "glyf"
            r9 = 2
            if (r6 >= r4) goto L_0x0036
            r10 = r3[r6]
            boolean r8 = r10.equals(r8)
            if (r8 != 0) goto L_0x0033
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L_0x001f
            goto L_0x0033
        L_0x001f:
            java.util.Map<java.lang.String, int[]> r7 = r0.tableDirectory
            java.lang.Object r7 = r7.get(r10)
            int[] r7 = (int[]) r7
            if (r7 != 0) goto L_0x002a
            goto L_0x0033
        L_0x002a:
            int r2 = r2 + 1
            r8 = r7[r9]
            int r8 = r8 + 3
            r8 = r8 & -4
            int r1 = r1 + r8
        L_0x0033:
            int r6 = r6 + 1
            goto L_0x0009
        L_0x0036:
            byte[] r3 = r0.newLocaTableOut
            int r3 = r3.length
            int r1 = r1 + r3
            byte[] r3 = r0.newGlyfTable
            int r3 = r3.length
            int r1 = r1 + r3
            int r3 = r2 * 16
            int r3 = r3 + 12
            int r1 = r1 + r3
            byte[] r4 = new byte[r1]
            r0.outFont = r4
            r0.fontPtr = r5
            r4 = 65536(0x10000, float:9.18355E-41)
            r0.writeFontInt(r4)
            r0.writeFontShort(r2)
            int[] r4 = entrySelectors
            r4 = r4[r2]
            r6 = 1
            int r10 = r6 << r4
            int r10 = r10 * 16
            r0.writeFontShort(r10)
            r0.writeFontShort(r4)
            int r10 = r6 << r4
            int r10 = r2 - r10
            int r10 = r10 * 16
            r0.writeFontShort(r10)
            java.lang.String[] r10 = r0.tableNames
            int r11 = r10.length
            r12 = 0
        L_0x006d:
            if (r12 >= r11) goto L_0x00ca
            r14 = r10[r12]
            java.util.Map<java.lang.String, int[]> r15 = r0.tableDirectory
            java.lang.Object r15 = r15.get(r14)
            int[] r15 = (int[]) r15
            if (r15 != 0) goto L_0x007c
            goto L_0x00c7
        L_0x007c:
            r0.writeFontString(r14)
            int r16 = r14.hashCode()
            switch(r16) {
                case 3176114: goto L_0x008f;
                case 3327265: goto L_0x0087;
                default: goto L_0x0086;
            }
        L_0x0086:
            goto L_0x0097
        L_0x0087:
            boolean r16 = r14.equals(r7)
            if (r16 == 0) goto L_0x0086
            r13 = 1
            goto L_0x0098
        L_0x008f:
            boolean r16 = r14.equals(r8)
            if (r16 == 0) goto L_0x0086
            r13 = 0
            goto L_0x0098
        L_0x0097:
            r13 = -1
        L_0x0098:
            switch(r13) {
                case 0: goto L_0x00af;
                case 1: goto L_0x00a3;
                default: goto L_0x009b;
            }
        L_0x009b:
            r13 = r15[r5]
            r0.writeFontInt(r13)
            r13 = r15[r9]
            goto L_0x00bb
        L_0x00a3:
            byte[] r13 = r0.newLocaTableOut
            int r13 = r0.calculateChecksum(r13)
            r0.writeFontInt(r13)
            int r13 = r0.locaTableRealSize
            goto L_0x00bb
        L_0x00af:
            byte[] r13 = r0.newGlyfTable
            int r13 = r0.calculateChecksum(r13)
            r0.writeFontInt(r13)
            int r13 = r0.glyfTableRealSize
        L_0x00bb:
            r0.writeFontInt(r3)
            r0.writeFontInt(r13)
            int r16 = r13 + 3
            r16 = r16 & -4
            int r3 = r3 + r16
        L_0x00c7:
            int r12 = r12 + 1
            goto L_0x006d
        L_0x00ca:
            java.lang.String[] r10 = r0.tableNames
            int r11 = r10.length
            r12 = 0
        L_0x00ce:
            if (r12 >= r11) goto L_0x015b
            r14 = r10[r12]
            java.util.Map<java.lang.String, int[]> r15 = r0.tableDirectory
            java.lang.Object r15 = r15.get(r14)
            int[] r15 = (int[]) r15
            if (r15 != 0) goto L_0x00e0
            r17 = r1
            goto L_0x0153
        L_0x00e0:
            int r16 = r14.hashCode()
            switch(r16) {
                case 3176114: goto L_0x00f1;
                case 3327265: goto L_0x00e8;
                default: goto L_0x00e7;
            }
        L_0x00e7:
            goto L_0x00fa
        L_0x00e8:
            boolean r16 = r14.equals(r7)
            if (r16 == 0) goto L_0x00e7
            r16 = 1
            goto L_0x00fc
        L_0x00f1:
            boolean r16 = r14.equals(r8)
            if (r16 == 0) goto L_0x00e7
            r16 = 0
            goto L_0x00fc
        L_0x00fa:
            r16 = -1
        L_0x00fc:
            switch(r16) {
                case 0: goto L_0x013b;
                case 1: goto L_0x0122;
                default: goto L_0x00ff;
            }
        L_0x00ff:
            r17 = r1
            com.itextpdf.io.source.RandomAccessFileOrArray r1 = r0.f1213rf
            r6 = 1
            r9 = r15[r6]
            long r5 = (long) r9
            r1.seek(r5)
            com.itextpdf.io.source.RandomAccessFileOrArray r1 = r0.f1213rf
            byte[] r5 = r0.outFont
            int r6 = r0.fontPtr
            r9 = 2
            r13 = r15[r9]
            r1.readFully(r5, r6, r13)
            int r1 = r0.fontPtr
            r5 = r15[r9]
            int r5 = r5 + 3
            r5 = r5 & -4
            int r1 = r1 + r5
            r0.fontPtr = r1
            goto L_0x0153
        L_0x0122:
            byte[] r9 = r0.newLocaTableOut
            byte[] r6 = r0.outFont
            int r13 = r0.fontPtr
            r17 = r1
            int r1 = r9.length
            java.lang.System.arraycopy(r9, r5, r6, r13, r1)
            int r1 = r0.fontPtr
            byte[] r6 = r0.newLocaTableOut
            int r6 = r6.length
            int r1 = r1 + r6
            r0.fontPtr = r1
            r1 = 0
            r0.newLocaTableOut = r1
            r9 = 2
            goto L_0x0153
        L_0x013b:
            r17 = r1
            byte[] r1 = r0.newGlyfTable
            byte[] r6 = r0.outFont
            int r9 = r0.fontPtr
            int r13 = r1.length
            java.lang.System.arraycopy(r1, r5, r6, r9, r13)
            int r1 = r0.fontPtr
            byte[] r6 = r0.newGlyfTable
            int r6 = r6.length
            int r1 = r1 + r6
            r0.fontPtr = r1
            r1 = 0
            r0.newGlyfTable = r1
            r9 = 2
        L_0x0153:
            int r12 = r12 + 1
            r1 = r17
            r5 = 0
            r6 = 1
            goto L_0x00ce
        L_0x015b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.TrueTypeFontSubset.assembleFont():void");
    }

    private void createTableDirectory() throws IOException {
        this.tableDirectory = new HashMap();
        this.f1213rf.seek((long) this.directoryOffset);
        if (this.f1213rf.readInt() == 65536) {
            int num_tables = this.f1213rf.readUnsignedShort();
            this.f1213rf.skipBytes(6);
            for (int k = 0; k < num_tables; k++) {
                this.tableDirectory.put(readStandardString(4), new int[]{this.f1213rf.readInt(), this.f1213rf.readInt(), this.f1213rf.readInt()});
            }
            return;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.NotAtTrueTypeFile).setMessageParams(this.fileName);
    }

    private void readLoca() throws IOException {
        int[] tableLocation = this.tableDirectory.get(XfdfConstants.HEAD);
        if (tableLocation != null) {
            this.f1213rf.seek((long) (tableLocation[1] + 51));
            this.locaShortTable = this.f1213rf.readUnsignedShort() == 0;
            int[] tableLocation2 = this.tableDirectory.get("loca");
            if (tableLocation2 != null) {
                this.f1213rf.seek((long) tableLocation2[1]);
                if (this.locaShortTable) {
                    int entries = tableLocation2[2] / 2;
                    this.locaTable = new int[entries];
                    for (int k = 0; k < entries; k++) {
                        this.locaTable[k] = this.f1213rf.readUnsignedShort() * 2;
                    }
                    return;
                }
                int entries2 = tableLocation2[2] / 4;
                this.locaTable = new int[entries2];
                for (int k2 = 0; k2 < entries2; k2++) {
                    this.locaTable[k2] = this.f1213rf.readInt();
                }
                return;
            }
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams("loca", this.fileName);
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams(XfdfConstants.HEAD, this.fileName);
    }

    private void createNewGlyphTables() throws IOException {
        this.newLocaTable = new int[this.locaTable.length];
        int[] activeGlyphs = new int[this.glyphsInList.size()];
        for (int k = 0; k < activeGlyphs.length; k++) {
            activeGlyphs[k] = this.glyphsInList.get(k).intValue();
        }
        Arrays.sort(activeGlyphs);
        int glyfSize = 0;
        for (int glyph : activeGlyphs) {
            int[] iArr = this.locaTable;
            glyfSize += iArr[glyph + 1] - iArr[glyph];
        }
        this.glyfTableRealSize = glyfSize;
        this.newGlyfTable = new byte[((glyfSize + 3) & -4)];
        int glyfPtr = 0;
        int listGlyf = 0;
        int k2 = 0;
        while (true) {
            int[] iArr2 = this.newLocaTable;
            if (k2 < iArr2.length) {
                iArr2[k2] = glyfPtr;
                if (listGlyf < activeGlyphs.length && activeGlyphs[listGlyf] == k2) {
                    listGlyf++;
                    iArr2[k2] = glyfPtr;
                    int[] iArr3 = this.locaTable;
                    int start = iArr3[k2];
                    int len = iArr3[k2 + 1] - start;
                    if (len > 0) {
                        this.f1213rf.seek((long) (this.tableGlyphOffset + start));
                        this.f1213rf.readFully(this.newGlyfTable, glyfPtr, len);
                        glyfPtr += len;
                    }
                }
                k2++;
            } else {
                return;
            }
        }
    }

    private void locaToBytes() {
        if (this.locaShortTable) {
            this.locaTableRealSize = this.newLocaTable.length * 2;
        } else {
            this.locaTableRealSize = this.newLocaTable.length * 4;
        }
        byte[] bArr = new byte[((this.locaTableRealSize + 3) & -4)];
        this.newLocaTableOut = bArr;
        this.outFont = bArr;
        this.fontPtr = 0;
        for (int location : this.newLocaTable) {
            if (this.locaShortTable) {
                writeFontShort(location / 2);
            } else {
                writeFontInt(location);
            }
        }
    }

    private void flatGlyphs() throws IOException {
        int[] tableLocation = this.tableDirectory.get("glyf");
        if (tableLocation != null) {
            if (!this.glyphsUsed.contains(0)) {
                this.glyphsUsed.add(0);
                this.glyphsInList.add(0);
            }
            this.tableGlyphOffset = tableLocation[1];
            for (int i = 0; i < this.glyphsInList.size(); i++) {
                checkGlyphComposite(this.glyphsInList.get(i).intValue());
            }
            return;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.TableDoesNotExistsIn).setMessageParams("glyf", this.fileName);
    }

    private void checkGlyphComposite(int glyph) throws IOException {
        int skip;
        int[] iArr = this.locaTable;
        int start = iArr[glyph];
        if (start != iArr[glyph + 1]) {
            this.f1213rf.seek((long) (this.tableGlyphOffset + start));
            if (this.f1213rf.readShort() < 0) {
                this.f1213rf.skipBytes(8);
                while (true) {
                    int flags = this.f1213rf.readUnsignedShort();
                    int cGlyph = this.f1213rf.readUnsignedShort();
                    if (!this.glyphsUsed.contains(Integer.valueOf(cGlyph))) {
                        this.glyphsUsed.add(Integer.valueOf(cGlyph));
                        this.glyphsInList.add(Integer.valueOf(cGlyph));
                    }
                    if ((flags & 32) != 0) {
                        if ((flags & 1) != 0) {
                            skip = 4;
                        } else {
                            skip = 2;
                        }
                        if ((flags & 8) != 0) {
                            skip += 2;
                        } else if ((flags & 64) != 0) {
                            skip += 4;
                        }
                        if ((flags & 128) != 0) {
                            skip += 8;
                        }
                        this.f1213rf.skipBytes(skip);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private String readStandardString(int length) throws IOException {
        byte[] buf = new byte[length];
        this.f1213rf.readFully(buf);
        try {
            return new String(buf, "Cp1252");
        } catch (Exception e) {
            throw new com.itextpdf.p026io.IOException("TrueType font", (Throwable) e);
        }
    }

    private void writeFontShort(int n) {
        byte[] bArr = this.outFont;
        int i = this.fontPtr;
        int i2 = i + 1;
        this.fontPtr = i2;
        bArr[i] = (byte) (n >> 8);
        this.fontPtr = i2 + 1;
        bArr[i2] = (byte) n;
    }

    private void writeFontInt(int n) {
        byte[] bArr = this.outFont;
        int i = this.fontPtr;
        int i2 = i + 1;
        this.fontPtr = i2;
        bArr[i] = (byte) (n >> 24);
        int i3 = i2 + 1;
        this.fontPtr = i3;
        bArr[i2] = (byte) (n >> 16);
        int i4 = i3 + 1;
        this.fontPtr = i4;
        bArr[i3] = (byte) (n >> 8);
        this.fontPtr = i4 + 1;
        bArr[i4] = (byte) n;
    }

    private void writeFontString(String s) {
        byte[] b = PdfEncodings.convertToBytes(s, "Cp1252");
        System.arraycopy(b, 0, this.outFont, this.fontPtr, b.length);
        this.fontPtr += b.length;
    }

    private int calculateChecksum(byte[] b) {
        int len = b.length / 4;
        int v0 = 0;
        int v1 = 0;
        int v2 = 0;
        int v3 = 0;
        int ptr = 0;
        for (int k = 0; k < len; k++) {
            int ptr2 = ptr + 1;
            v3 += b[ptr] & UByte.MAX_VALUE;
            int ptr3 = ptr2 + 1;
            v2 += b[ptr2] & UByte.MAX_VALUE;
            int ptr4 = ptr3 + 1;
            v1 += b[ptr3] & UByte.MAX_VALUE;
            ptr = ptr4 + 1;
            v0 += b[ptr4] & UByte.MAX_VALUE;
        }
        return (v1 << 8) + v0 + (v2 << 16) + (v3 << 24);
    }
}
