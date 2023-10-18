package com.itextpdf.p026io.codec.brotli.dec;

/* renamed from: com.itextpdf.io.codec.brotli.dec.Huffman */
final class Huffman {
    static final int HUFFMAN_MAX_TABLE_SIZE = 1080;
    private static final int MAX_LENGTH = 15;

    Huffman() {
    }

    private static int getNextKey(int key, int len) {
        int step = 1 << (len - 1);
        while ((key & step) != 0) {
            step >>= 1;
        }
        return ((step - 1) & key) + step;
    }

    private static void replicateValue(int[] table, int offset, int step, int end, int item) {
        do {
            end -= step;
            table[offset + end] = item;
        } while (end > 0);
    }

    private static int nextTableBitSize(int[] count, int len, int rootBits) {
        int left;
        int left2 = 1 << (len - rootBits);
        while (len < 15 && (left = left2 - count[len]) > 0) {
            len++;
            left2 = left << 1;
        }
        return len - rootBits;
    }

    static void buildHuffmanTable(int[] rootTable, int tableOffset, int rootBits, int[] codeLengths, int codeLengthsSize) {
        int i;
        int[] iArr = rootTable;
        int i2 = rootBits;
        int i3 = codeLengthsSize;
        int[] sorted = new int[i3];
        int[] count = new int[16];
        int[] offset = new int[16];
        for (int symbol = 0; symbol < i3; symbol++) {
            int i4 = codeLengths[symbol];
            count[i4] = count[i4] + 1;
        }
        offset[1] = 0;
        int len = 1;
        while (true) {
            if (len >= 15) {
                break;
            }
            offset[len + 1] = offset[len] + count[len];
            len++;
        }
        for (int symbol2 = 0; symbol2 < i3; symbol2++) {
            if (codeLengths[symbol2] != 0) {
                int i5 = codeLengths[symbol2];
                int i6 = offset[i5];
                offset[i5] = i6 + 1;
                sorted[i6] = symbol2;
            }
        }
        int tableSize = 1 << rootBits;
        int totalSize = tableSize;
        if (offset[15] == 1) {
            for (int key = 0; key < totalSize; key++) {
                iArr[tableOffset + key] = sorted[0];
            }
            return;
        }
        int key2 = 0;
        int symbol3 = 0;
        int len2 = 1;
        int step = 2;
        while (len2 <= i2) {
            while (count[len2] > 0) {
                replicateValue(iArr, tableOffset + key2, step, tableSize, (len2 << 16) | sorted[symbol3]);
                key2 = getNextKey(key2, len2);
                count[len2] = count[len2] - 1;
                symbol3++;
            }
            len2++;
            step <<= 1;
        }
        int mask = totalSize - 1;
        int low = -1;
        int currentOffset = tableOffset;
        int len3 = i2 + 1;
        int step2 = 2;
        for (i = 15; len3 <= i; i = 15) {
            while (count[len3] > 0) {
                if ((key2 & mask) != low) {
                    currentOffset += tableSize;
                    int tableBits = nextTableBitSize(count, len3, i2);
                    tableSize = 1 << tableBits;
                    totalSize += tableSize;
                    low = key2 & mask;
                    iArr[tableOffset + low] = ((tableBits + i2) << 16) | ((currentOffset - tableOffset) - low);
                }
                replicateValue(iArr, (key2 >> i2) + currentOffset, step2, tableSize, ((len3 - i2) << 16) | sorted[symbol3]);
                key2 = getNextKey(key2, len3);
                count[len3] = count[len3] - 1;
                symbol3++;
            }
            len3++;
            step2 <<= 1;
        }
    }
}
