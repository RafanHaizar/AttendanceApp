package com.itextpdf.barcodes.qrcode;

final class BitMatrix {
    private final int[] bits;
    private final int height;
    private final int rowSize;
    private final int width;

    public BitMatrix(int dimension) {
        this(dimension, dimension);
    }

    public BitMatrix(int width2, int height2) {
        if (width2 < 1 || height2 < 1) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.width = width2;
        this.height = height2;
        int rowSize2 = width2 >> 5;
        rowSize2 = (width2 & 31) != 0 ? rowSize2 + 1 : rowSize2;
        this.rowSize = rowSize2;
        this.bits = new int[(rowSize2 * height2)];
    }

    public boolean get(int x, int y) {
        return ((this.bits[(this.rowSize * y) + (x >> 5)] >>> (x & 31)) & 1) != 0;
    }

    public void set(int x, int y) {
        int offset = (this.rowSize * y) + (x >> 5);
        int[] iArr = this.bits;
        iArr[offset] = iArr[offset] | (1 << (x & 31));
    }

    public void flip(int x, int y) {
        int offset = (this.rowSize * y) + (x >> 5);
        int[] iArr = this.bits;
        iArr[offset] = iArr[offset] ^ (1 << (x & 31));
    }

    public void clear() {
        int max = this.bits.length;
        for (int i = 0; i < max; i++) {
            this.bits[i] = 0;
        }
    }

    public void setRegion(int left, int top, int width2, int height2) {
        if (top < 0 || left < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        } else if (height2 < 1 || width2 < 1) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        } else {
            int right = left + width2;
            int bottom = top + height2;
            if (bottom > this.height || right > this.width) {
                throw new IllegalArgumentException("The region must fit inside the matrix");
            }
            for (int y = top; y < bottom; y++) {
                int offset = this.rowSize * y;
                for (int x = left; x < right; x++) {
                    int[] iArr = this.bits;
                    int i = (x >> 5) + offset;
                    iArr[i] = iArr[i] | (1 << (x & 31));
                }
            }
        }
    }

    public BitArray getRow(int y, BitArray row) {
        if (row == null || row.getSize() < this.width) {
            row = new BitArray(this.width);
        }
        int offset = this.rowSize * y;
        for (int x = 0; x < this.rowSize; x++) {
            row.setBulk(x << 5, this.bits[offset + x]);
        }
        return row;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDimension() {
        int i = this.width;
        if (i == this.height) {
            return i;
        }
        throw new RuntimeException("Can't call getDimension() on a non-square matrix");
    }

    public String toString() {
        StringBuffer result = new StringBuffer(this.height * (this.width + 1));
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                result.append(get(x, y) ? "X " : "  ");
            }
            result.append(10);
        }
        return result.toString();
    }
}
