package com.itextpdf.p026io.font;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;
import kotlin.UByte;

/* renamed from: com.itextpdf.io.font.WoffConverter */
class WoffConverter {
    private static final long woffSignature = 2001684038;

    WoffConverter() {
    }

    public static boolean isWoffFont(byte[] woffBytes) {
        return bytesToUInt(woffBytes, 0) == woffSignature;
    }

    public static byte[] convert(byte[] woffBytes) throws IOException {
        int outTableOffset;
        byte[] uncompressedData;
        byte[] bArr = woffBytes;
        if (bytesToUInt(bArr, 0) == woffSignature) {
            int srcPos = 0 + 4;
            byte[] flavor = new byte[4];
            System.arraycopy(bArr, srcPos, flavor, 0, 4);
            int srcPos2 = srcPos + 4;
            if (bytesToUInt(bArr, srcPos2) == ((long) bArr.length)) {
                int srcPos3 = srcPos2 + 4;
                byte[] numTables = new byte[2];
                System.arraycopy(bArr, srcPos3, numTables, 0, 2);
                int srcPos4 = srcPos3 + 2;
                if (bytesToUShort(bArr, srcPos4) == 0) {
                    int srcPos5 = srcPos4 + 2;
                    long totalSfntSize = bytesToUInt(bArr, srcPos5);
                    int srcPos6 = srcPos5 + 4 + 2 + 2 + 4 + 4 + 4 + 4 + 4;
                    byte[] otfBytes = new byte[((int) totalSfntSize)];
                    System.arraycopy(flavor, 0, otfBytes, 0, 4);
                    int destPos = 0 + 4;
                    System.arraycopy(numTables, 0, otfBytes, destPos, 2);
                    int destPos2 = destPos + 2;
                    int entrySelector = -1;
                    int searchRange = -1;
                    int numTablesVal = bytesToUShort(numTables, 0);
                    int i = 0;
                    while (true) {
                        if (i >= 17) {
                            break;
                        }
                        byte[] flavor2 = flavor;
                        int powOfTwo = (int) Math.pow(2.0d, (double) i);
                        if (powOfTwo > numTablesVal) {
                            entrySelector = i;
                            searchRange = powOfTwo * 16;
                            break;
                        }
                        i++;
                        flavor = flavor2;
                    }
                    if (entrySelector >= 0) {
                        otfBytes[destPos2] = (byte) (searchRange >> 8);
                        otfBytes[destPos2 + 1] = (byte) searchRange;
                        int destPos3 = destPos2 + 2;
                        otfBytes[destPos3] = (byte) (entrySelector >> 8);
                        otfBytes[destPos3 + 1] = (byte) entrySelector;
                        int destPos4 = destPos3 + 2;
                        int rangeShift = (numTablesVal * 16) - searchRange;
                        otfBytes[destPos4] = (byte) (rangeShift >> 8);
                        otfBytes[destPos4 + 1] = (byte) rangeShift;
                        int destPos5 = destPos4 + 2;
                        int outTableOffset2 = destPos5;
                        List<TableDirectory> tdList = new ArrayList<>(numTablesVal);
                        int i2 = 0;
                        while (i2 < numTablesVal) {
                            TableDirectory td = new TableDirectory();
                            int destPos6 = destPos5;
                            int rangeShift2 = rangeShift;
                            System.arraycopy(bArr, srcPos6, td.tag, 0, 4);
                            int srcPos7 = srcPos6 + 4;
                            int outTableOffset3 = outTableOffset;
                            td.offset = bytesToUInt(bArr, srcPos7);
                            int srcPos8 = srcPos7 + 4;
                            if (td.offset % 4 == 0) {
                                td.compLength = bytesToUInt(bArr, srcPos8);
                                int srcPos9 = srcPos8 + 4;
                                System.arraycopy(bArr, srcPos9, td.origLength, 0, 4);
                                td.origLengthVal = bytesToUInt(td.origLength, 0);
                                int srcPos10 = srcPos9 + 4;
                                System.arraycopy(bArr, srcPos10, td.origChecksum, 0, 4);
                                srcPos6 = srcPos10 + 4;
                                tdList.add(td);
                                outTableOffset2 = outTableOffset3 + 16;
                                i2++;
                                rangeShift = rangeShift2;
                                destPos5 = destPos6;
                                entrySelector = entrySelector;
                                searchRange = searchRange;
                            } else {
                                throw new IllegalArgumentException();
                            }
                        }
                        int destPos7 = destPos5;
                        int i3 = rangeShift;
                        int destPos8 = outTableOffset;
                        int i4 = entrySelector;
                        int i5 = searchRange;
                        int destPos9 = destPos7;
                        for (TableDirectory td2 : tdList) {
                            System.arraycopy(td2.tag, 0, otfBytes, destPos9, 4);
                            int destPos10 = destPos9 + 4;
                            System.arraycopy(td2.origChecksum, 0, otfBytes, destPos10, 4);
                            int destPos11 = destPos10 + 4;
                            otfBytes[destPos11] = (byte) (outTableOffset >> 24);
                            otfBytes[destPos11 + 1] = (byte) (outTableOffset >> 16);
                            otfBytes[destPos11 + 2] = (byte) (outTableOffset >> 8);
                            otfBytes[destPos11 + 3] = (byte) outTableOffset;
                            int destPos12 = destPos11 + 4;
                            System.arraycopy(td2.origLength, 0, otfBytes, destPos12, 4);
                            destPos9 = destPos12 + 4;
                            td2.outOffset = outTableOffset;
                            outTableOffset += (int) td2.origLengthVal;
                            if (outTableOffset % 4 != 0) {
                                outTableOffset += 4 - (outTableOffset % 4);
                            }
                        }
                        if (((long) outTableOffset) == totalSfntSize) {
                            for (TableDirectory td3 : tdList) {
                                byte[] compressedData = new byte[((int) td3.compLength)];
                                System.arraycopy(bArr, (int) td3.offset, compressedData, 0, (int) td3.compLength);
                                int expectedUncompressedLen = (int) td3.origLengthVal;
                                int srcPos11 = srcPos6;
                                if (td3.compLength <= td3.origLengthVal) {
                                    if (td3.compLength != td3.origLengthVal) {
                                        ByteArrayInputStream stream = new ByteArrayInputStream(compressedData);
                                        InflaterInputStream zip = new InflaterInputStream(stream);
                                        uncompressedData = new byte[expectedUncompressedLen];
                                        int bytesRead = 0;
                                        while (expectedUncompressedLen - bytesRead > 0) {
                                            ByteArrayInputStream stream2 = stream;
                                            int readRes = zip.read(uncompressedData, bytesRead, expectedUncompressedLen - bytesRead);
                                            if (readRes >= 0) {
                                                bytesRead += readRes;
                                                stream = stream2;
                                            } else {
                                                throw new IllegalArgumentException();
                                            }
                                        }
                                        if (zip.read() >= 0) {
                                            throw new IllegalArgumentException();
                                        }
                                    } else {
                                        uncompressedData = compressedData;
                                    }
                                    System.arraycopy(uncompressedData, 0, otfBytes, td3.outOffset, expectedUncompressedLen);
                                    bArr = woffBytes;
                                    srcPos6 = srcPos11;
                                } else {
                                    throw new IllegalArgumentException();
                                }
                            }
                            return otfBytes;
                        }
                        throw new IllegalArgumentException();
                    }
                    throw new IllegalArgumentException();
                }
                throw new IllegalArgumentException();
            }
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException();
    }

    private static long bytesToUInt(byte[] b, int start) {
        return ((((long) b[start]) & 255) << 24) | ((((long) b[start + 1]) & 255) << 16) | ((((long) b[start + 2]) & 255) << 8) | (255 & ((long) b[start + 3]));
    }

    private static int bytesToUShort(byte[] b, int start) {
        return ((b[start] & UByte.MAX_VALUE) << 8) | (b[start + 1] & UByte.MAX_VALUE);
    }

    /* renamed from: com.itextpdf.io.font.WoffConverter$TableDirectory */
    private static class TableDirectory {
        long compLength;
        long offset;
        byte[] origChecksum;
        byte[] origLength;
        long origLengthVal;
        int outOffset;
        byte[] tag;

        private TableDirectory() {
            this.tag = new byte[4];
            this.origLength = new byte[4];
            this.origChecksum = new byte[4];
        }
    }
}
