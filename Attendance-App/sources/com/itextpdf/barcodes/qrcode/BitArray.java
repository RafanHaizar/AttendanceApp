package com.itextpdf.barcodes.qrcode;

final class BitArray {
    private int[] bits;
    private final int size;

    public BitArray(int size2) {
        if (size2 >= 1) {
            this.size = size2;
            this.bits = makeArray(size2);
            return;
        }
        throw new IllegalArgumentException("size must be at least 1");
    }

    public int getSize() {
        return this.size;
    }

    public boolean get(int i) {
        return (this.bits[i >> 5] & (1 << (i & 31))) != 0;
    }

    public void set(int i) {
        int[] iArr = this.bits;
        int i2 = i >> 5;
        iArr[i2] = iArr[i2] | (1 << (i & 31));
    }

    public void flip(int i) {
        int[] iArr = this.bits;
        int i2 = i >> 5;
        iArr[i2] = iArr[i2] ^ (1 << (i & 31));
    }

    public void setBulk(int i, int newBits) {
        this.bits[i >> 5] = newBits;
    }

    public void clear() {
        int max = this.bits.length;
        for (int i = 0; i < max; i++) {
            this.bits[i] = 0;
        }
    }

    public boolean isRange(int start, int end, boolean value) {
        int mask;
        if (end < start) {
            throw new IllegalArgumentException();
        } else if (end == start) {
            return true;
        } else {
            int end2 = end - 1;
            int firstInt = start >> 5;
            int lastInt = end2 >> 5;
            int i = firstInt;
            while (i <= lastInt) {
                int firstBit = i > firstInt ? 0 : start & 31;
                int lastBit = i < lastInt ? 31 : end2 & 31;
                if (firstBit == 0 && lastBit == 31) {
                    mask = -1;
                } else {
                    mask = 0;
                    for (int j = firstBit; j <= lastBit; j++) {
                        mask |= 1 << j;
                    }
                }
                if ((this.bits[i] & mask) != (value ? mask : 0)) {
                    return false;
                }
                i++;
            }
            return true;
        }
    }

    public int[] getBitArray() {
        return this.bits;
    }

    public void reverse() {
        int[] newBits = new int[this.bits.length];
        int size2 = this.size;
        for (int i = 0; i < size2; i++) {
            if (get((size2 - i) - 1)) {
                int i2 = i >> 5;
                newBits[i2] = (1 << (i & 31)) | newBits[i2];
            }
        }
        this.bits = newBits;
    }

    private static int[] makeArray(int size2) {
        int arraySize = size2 >> 5;
        if ((size2 & 31) != 0) {
            arraySize++;
        }
        return new int[arraySize];
    }

    public String toString() {
        StringBuffer result = new StringBuffer(this.size);
        for (int i = 0; i < this.size; i++) {
            if ((i & 7) == 0) {
                result.append(' ');
            }
            result.append(get(i) ? 'X' : '.');
        }
        return result.toString();
    }
}
