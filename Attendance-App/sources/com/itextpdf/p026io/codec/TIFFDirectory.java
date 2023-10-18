package com.itextpdf.p026io.codec;

import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.itextpdf.io.codec.TIFFDirectory */
public class TIFFDirectory implements Serializable {
    private static final long serialVersionUID = -168636766193675380L;
    private static final int[] sizeOfType = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    long IFDOffset = 8;
    Map<Integer, Integer> fieldIndex = new HashMap();
    TIFFField[] fields;
    boolean isBigEndian;
    long nextIFDOffset = 0;
    int numEntries;

    TIFFDirectory() {
    }

    private static boolean isValidEndianTag(int endian) {
        return endian == 18761 || endian == 19789;
    }

    public TIFFDirectory(RandomAccessFileOrArray stream, int directory) throws IOException {
        long global_save_offset = stream.getPosition();
        stream.seek(0);
        int endian = stream.readUnsignedShort();
        if (isValidEndianTag(endian)) {
            this.isBigEndian = endian == 19789;
            if (readUnsignedShort(stream) == 42) {
                long ifd_offset = readUnsignedInt(stream);
                int i = 0;
                while (i < directory) {
                    if (ifd_offset != 0) {
                        stream.seek(ifd_offset);
                        stream.skip((long) (readUnsignedShort(stream) * 12));
                        ifd_offset = readUnsignedInt(stream);
                        i++;
                    } else {
                        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.DirectoryNumberIsTooLarge);
                    }
                }
                stream.seek(ifd_offset);
                initialize(stream);
                stream.seek(global_save_offset);
                return;
            }
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.BadMagicNumberShouldBe42);
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.BadEndiannessTag0x4949Or0x4d4d);
    }

    public TIFFDirectory(RandomAccessFileOrArray stream, long ifd_offset, int directory) throws IOException {
        long global_save_offset = stream.getPosition();
        stream.seek(0);
        int endian = stream.readUnsignedShort();
        if (isValidEndianTag(endian)) {
            this.isBigEndian = endian == 19789;
            stream.seek(ifd_offset);
            for (int dirNum = 0; dirNum < directory; dirNum++) {
                stream.seek(((long) (readUnsignedShort(stream) * 12)) + ifd_offset);
                ifd_offset = readUnsignedInt(stream);
                stream.seek(ifd_offset);
            }
            initialize(stream);
            stream.seek(global_save_offset);
            return;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.BadEndiannessTag0x4949Or0x4d4d);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: char[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v11, resolved type: long[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: long[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v3, resolved type: long[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v16, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v17, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: float[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: double[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: byte[]} */
    /* JADX WARNING: type inference failed for: r5v5, types: [java.lang.String[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initialize(com.itextpdf.p026io.source.RandomAccessFileOrArray r21) throws java.io.IOException {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            r3 = 0
            long r5 = r21.length()
            long r7 = r21.getPosition()
            r1.IFDOffset = r7
            int r0 = r20.readUnsignedShort(r21)
            r1.numEntries = r0
            com.itextpdf.io.codec.TIFFField[] r0 = new com.itextpdf.p026io.codec.TIFFField[r0]
            r1.fields = r0
            r0 = 0
            r7 = r3
            r3 = r0
        L_0x001d:
            int r0 = r1.numEntries
            if (r3 >= r0) goto L_0x0196
            int r0 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0196
            int r4 = r20.readUnsignedShort(r21)
            int r9 = r20.readUnsignedShort(r21)
            long r10 = r20.readUnsignedInt(r21)
            int r11 = (int) r10
            r10 = 1
            long r12 = r21.getPosition()
            r14 = 4
            long r7 = r12 + r14
            int[] r0 = sizeOfType     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0052 }
            r0 = r0[r9]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0052 }
            int r0 = r0 * r11
            r12 = 4
            if (r0 <= r12) goto L_0x0051
            long r12 = r20.readUnsignedInt(r21)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0052 }
            int r0 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0050
            r2.seek(r12)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0052 }
            goto L_0x0051
        L_0x0050:
            r10 = 0
        L_0x0051:
            goto L_0x0054
        L_0x0052:
            r0 = move-exception
            r10 = 0
        L_0x0054:
            if (r10 == 0) goto L_0x018b
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = r1.fieldIndex
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)
            java.lang.Integer r13 = java.lang.Integer.valueOf(r3)
            r0.put(r12, r13)
            r0 = 0
            r12 = 2
            switch(r9) {
                case 1: goto L_0x012d;
                case 2: goto L_0x012d;
                case 3: goto L_0x011a;
                case 4: goto L_0x0107;
                case 5: goto L_0x00e2;
                case 6: goto L_0x012d;
                case 7: goto L_0x012d;
                case 8: goto L_0x00cf;
                case 9: goto L_0x00bc;
                case 10: goto L_0x0094;
                case 11: goto L_0x0081;
                case 12: goto L_0x006e;
                default: goto L_0x0068;
            }
        L_0x0068:
            r16 = r0
            r17 = r5
            goto L_0x0181
        L_0x006e:
            double[] r12 = new double[r11]
            r13 = 0
        L_0x0071:
            if (r13 >= r11) goto L_0x007c
            double r14 = r20.readDouble(r21)
            r12[r13] = r14
            int r13 = r13 + 1
            goto L_0x0071
        L_0x007c:
            r0 = r12
            r17 = r5
            goto L_0x0181
        L_0x0081:
            float[] r12 = new float[r11]
            r13 = 0
        L_0x0084:
            if (r13 >= r11) goto L_0x008f
            float r14 = r20.readFloat(r21)
            r12[r13] = r14
            int r13 = r13 + 1
            goto L_0x0084
        L_0x008f:
            r0 = r12
            r17 = r5
            goto L_0x0181
        L_0x0094:
            int[][] r15 = new int[r11][]
            r16 = 0
            r14 = r16
        L_0x009a:
            if (r14 >= r11) goto L_0x00b7
            int[] r13 = new int[r12]
            r15[r14] = r13
            r13 = r15[r14]
            int r18 = r20.readInt(r21)
            r16 = 0
            r13[r16] = r18
            r13 = r15[r14]
            int r18 = r20.readInt(r21)
            r17 = 1
            r13[r17] = r18
            int r14 = r14 + 1
            goto L_0x009a
        L_0x00b7:
            r0 = r15
            r17 = r5
            goto L_0x0181
        L_0x00bc:
            int[] r12 = new int[r11]
            r13 = 0
        L_0x00bf:
            if (r13 >= r11) goto L_0x00ca
            int r14 = r20.readInt(r21)
            r12[r13] = r14
            int r13 = r13 + 1
            goto L_0x00bf
        L_0x00ca:
            r0 = r12
            r17 = r5
            goto L_0x0181
        L_0x00cf:
            short[] r12 = new short[r11]
            r13 = 0
        L_0x00d2:
            if (r13 >= r11) goto L_0x00dd
            short r14 = r20.readShort(r21)
            r12[r13] = r14
            int r13 = r13 + 1
            goto L_0x00d2
        L_0x00dd:
            r0 = r12
            r17 = r5
            goto L_0x0181
        L_0x00e2:
            long[][] r13 = new long[r11][]
            r14 = 0
        L_0x00e5:
            if (r14 >= r11) goto L_0x0102
            long[] r15 = new long[r12]
            r13[r14] = r15
            r15 = r13[r14]
            long r18 = r20.readUnsignedInt(r21)
            r16 = 0
            r15[r16] = r18
            r15 = r13[r14]
            long r18 = r20.readUnsignedInt(r21)
            r17 = 1
            r15[r17] = r18
            int r14 = r14 + 1
            goto L_0x00e5
        L_0x0102:
            r0 = r13
            r17 = r5
            goto L_0x0181
        L_0x0107:
            long[] r12 = new long[r11]
            r13 = 0
        L_0x010a:
            if (r13 >= r11) goto L_0x0115
            long r14 = r20.readUnsignedInt(r21)
            r12[r13] = r14
            int r13 = r13 + 1
            goto L_0x010a
        L_0x0115:
            r0 = r12
            r17 = r5
            goto L_0x0181
        L_0x011a:
            char[] r12 = new char[r11]
            r13 = 0
        L_0x011d:
            if (r13 >= r11) goto L_0x0129
            int r14 = r20.readUnsignedShort(r21)
            char r14 = (char) r14
            r12[r13] = r14
            int r13 = r13 + 1
            goto L_0x011d
        L_0x0129:
            r0 = r12
            r17 = r5
            goto L_0x0181
        L_0x012d:
            byte[] r13 = new byte[r11]
            r14 = 0
            r2.readFully(r13, r14, r11)
            if (r9 != r12) goto L_0x017b
            r12 = 0
            r14 = 0
            java.util.ArrayList r15 = new java.util.ArrayList
            r15.<init>()
        L_0x013c:
            if (r12 >= r11) goto L_0x015f
        L_0x013e:
            if (r12 >= r11) goto L_0x014b
            int r16 = r12 + 1
            byte r12 = r13[r12]
            if (r12 == 0) goto L_0x0149
            r12 = r16
            goto L_0x013e
        L_0x0149:
            r12 = r16
        L_0x014b:
            r16 = r0
            java.lang.String r0 = new java.lang.String
            r17 = r5
            int r5 = r12 - r14
            r0.<init>(r13, r14, r5)
            r15.add(r0)
            r14 = r12
            r0 = r16
            r5 = r17
            goto L_0x013c
        L_0x015f:
            r16 = r0
            r17 = r5
            int r0 = r15.size()
            java.lang.String[] r5 = new java.lang.String[r0]
            r6 = 0
        L_0x016a:
            if (r6 >= r0) goto L_0x0177
            java.lang.Object r11 = r15.get(r6)
            java.lang.String r11 = (java.lang.String) r11
            r5[r6] = r11
            int r6 = r6 + 1
            goto L_0x016a
        L_0x0177:
            r11 = r0
            r0 = r5
            goto L_0x0181
        L_0x017b:
            r16 = r0
            r17 = r5
            r0 = r13
        L_0x0181:
            com.itextpdf.io.codec.TIFFField[] r5 = r1.fields
            com.itextpdf.io.codec.TIFFField r6 = new com.itextpdf.io.codec.TIFFField
            r6.<init>(r4, r9, r11, r0)
            r5[r3] = r6
            goto L_0x018d
        L_0x018b:
            r17 = r5
        L_0x018d:
            r2.seek(r7)
            int r3 = r3 + 1
            r5 = r17
            goto L_0x001d
        L_0x0196:
            r17 = r5
            long r4 = r20.readUnsignedInt(r21)     // Catch:{ Exception -> 0x019f }
            r1.nextIFDOffset = r4     // Catch:{ Exception -> 0x019f }
            goto L_0x01a4
        L_0x019f:
            r0 = move-exception
            r4 = 0
            r1.nextIFDOffset = r4
        L_0x01a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.codec.TIFFDirectory.initialize(com.itextpdf.io.source.RandomAccessFileOrArray):void");
    }

    public int getNumEntries() {
        return this.numEntries;
    }

    public TIFFField getField(int tag) {
        Integer i = this.fieldIndex.get(Integer.valueOf(tag));
        if (i == null) {
            return null;
        }
        return this.fields[i.intValue()];
    }

    public boolean isTagPresent(int tag) {
        return this.fieldIndex.containsKey(Integer.valueOf(tag));
    }

    public int[] getTags() {
        int[] tags = new int[this.fieldIndex.size()];
        int i = 0;
        for (Integer integer : this.fieldIndex.keySet()) {
            tags[i] = integer.intValue();
            i++;
        }
        return tags;
    }

    public TIFFField[] getFields() {
        return this.fields;
    }

    public byte getFieldAsByte(int tag, int index) {
        return this.fields[this.fieldIndex.get(Integer.valueOf(tag)).intValue()].getAsBytes()[index];
    }

    public byte getFieldAsByte(int tag) {
        return getFieldAsByte(tag, 0);
    }

    public long getFieldAsLong(int tag, int index) {
        return this.fields[this.fieldIndex.get(Integer.valueOf(tag)).intValue()].getAsLong(index);
    }

    public long getFieldAsLong(int tag) {
        return getFieldAsLong(tag, 0);
    }

    public float getFieldAsFloat(int tag, int index) {
        return this.fields[this.fieldIndex.get(Integer.valueOf(tag)).intValue()].getAsFloat(index);
    }

    public float getFieldAsFloat(int tag) {
        return getFieldAsFloat(tag, 0);
    }

    public double getFieldAsDouble(int tag, int index) {
        return this.fields[this.fieldIndex.get(Integer.valueOf(tag)).intValue()].getAsDouble(index);
    }

    public double getFieldAsDouble(int tag) {
        return getFieldAsDouble(tag, 0);
    }

    private short readShort(RandomAccessFileOrArray stream) throws IOException {
        if (this.isBigEndian) {
            return stream.readShort();
        }
        return stream.readShortLE();
    }

    private int readUnsignedShort(RandomAccessFileOrArray stream) throws IOException {
        if (this.isBigEndian) {
            return stream.readUnsignedShort();
        }
        return stream.readUnsignedShortLE();
    }

    private int readInt(RandomAccessFileOrArray stream) throws IOException {
        if (this.isBigEndian) {
            return stream.readInt();
        }
        return stream.readIntLE();
    }

    private long readUnsignedInt(RandomAccessFileOrArray stream) throws IOException {
        if (this.isBigEndian) {
            return stream.readUnsignedInt();
        }
        return stream.readUnsignedIntLE();
    }

    private long readLong(RandomAccessFileOrArray stream) throws IOException {
        if (this.isBigEndian) {
            return stream.readLong();
        }
        return stream.readLongLE();
    }

    private float readFloat(RandomAccessFileOrArray stream) throws IOException {
        if (this.isBigEndian) {
            return stream.readFloat();
        }
        return stream.readFloatLE();
    }

    private double readDouble(RandomAccessFileOrArray stream) throws IOException {
        if (this.isBigEndian) {
            return stream.readDouble();
        }
        return stream.readDoubleLE();
    }

    private static int readUnsignedShort(RandomAccessFileOrArray stream, boolean isBigEndian2) throws IOException {
        if (isBigEndian2) {
            return stream.readUnsignedShort();
        }
        return stream.readUnsignedShortLE();
    }

    private static long readUnsignedInt(RandomAccessFileOrArray stream, boolean isBigEndian2) throws IOException {
        if (isBigEndian2) {
            return stream.readUnsignedInt();
        }
        return stream.readUnsignedIntLE();
    }

    public static int getNumDirectories(RandomAccessFileOrArray stream) throws IOException {
        long pointer = stream.getPosition();
        stream.seek(0);
        int endian = stream.readUnsignedShort();
        if (isValidEndianTag(endian)) {
            boolean isBigEndian2 = endian == 19789;
            if (readUnsignedShort(stream, isBigEndian2) == 42) {
                stream.seek(4);
                long offset = readUnsignedInt(stream, isBigEndian2);
                int numDirectories = 0;
                while (offset != 0) {
                    numDirectories++;
                    try {
                        stream.seek(offset);
                        stream.skip((long) (readUnsignedShort(stream, isBigEndian2) * 12));
                        offset = readUnsignedInt(stream, isBigEndian2);
                    } catch (EOFException e) {
                        numDirectories--;
                    }
                }
                stream.seek(pointer);
                return numDirectories;
            }
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.BadMagicNumberShouldBe42);
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.BadEndiannessTag0x4949Or0x4d4d);
    }

    public boolean isBigEndian() {
        return this.isBigEndian;
    }

    public long getIFDOffset() {
        return this.IFDOffset;
    }

    public long getNextIFDOffset() {
        return this.nextIFDOffset;
    }
}
