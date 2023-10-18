package com.itextpdf.barcodes.qrcode;

import kotlin.UByte;

final class ByteArray {
    private static final int INITIAL_SIZE = 32;
    private byte[] bytes;
    private int size;

    public ByteArray() {
        this.bytes = null;
        this.size = 0;
    }

    public ByteArray(int size2) {
        this.bytes = new byte[size2];
        this.size = size2;
    }

    public ByteArray(byte[] byteArray) {
        this.bytes = byteArray;
        this.size = byteArray.length;
    }

    /* renamed from: at */
    public int mo25269at(int index) {
        return this.bytes[index] & UByte.MAX_VALUE;
    }

    public void set(int index, int value) {
        this.bytes[index] = (byte) value;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void appendByte(int value) {
        int i = this.size;
        if (i == 0 || i >= this.bytes.length) {
            reserve(Math.max(32, i << 1));
        }
        byte[] bArr = this.bytes;
        int i2 = this.size;
        bArr[i2] = (byte) value;
        this.size = i2 + 1;
    }

    public void reserve(int capacity) {
        byte[] bArr = this.bytes;
        if (bArr == null || bArr.length < capacity) {
            byte[] newArray = new byte[capacity];
            if (bArr != null) {
                System.arraycopy(bArr, 0, newArray, 0, bArr.length);
            }
            this.bytes = newArray;
        }
    }

    public void set(byte[] source, int offset, int count) {
        this.bytes = new byte[count];
        this.size = count;
        for (int x = 0; x < count; x++) {
            this.bytes[x] = source[offset + x];
        }
    }
}
