package com.itextpdf.layout.hyphenation;

import java.io.Serializable;

public class ByteVector implements Serializable {
    private static final int DEFAULT_BLOCK_SIZE = 2048;
    private static final long serialVersionUID = 1554572867863466772L;
    private byte[] array;
    private int blockSize;

    /* renamed from: n */
    private int f1525n;

    public ByteVector() {
        this(2048);
    }

    public ByteVector(int capacity) {
        if (capacity > 0) {
            this.blockSize = capacity;
        } else {
            this.blockSize = 2048;
        }
        this.array = new byte[this.blockSize];
        this.f1525n = 0;
    }

    public ByteVector(byte[] a) {
        this.blockSize = 2048;
        this.array = a;
        this.f1525n = 0;
    }

    public ByteVector(byte[] a, int capacity) {
        if (capacity > 0) {
            this.blockSize = capacity;
        } else {
            this.blockSize = 2048;
        }
        this.array = a;
        this.f1525n = 0;
    }

    public byte[] getArray() {
        return this.array;
    }

    public int length() {
        return this.f1525n;
    }

    public int capacity() {
        return this.array.length;
    }

    public void put(int index, byte val) {
        this.array[index] = val;
    }

    public byte get(int index) {
        return this.array[index];
    }

    public int alloc(int size) {
        int index = this.f1525n;
        byte[] bArr = this.array;
        int len = bArr.length;
        if (this.f1525n + size >= len) {
            byte[] aux = new byte[(this.blockSize + len)];
            System.arraycopy(bArr, 0, aux, 0, len);
            this.array = aux;
        }
        this.f1525n += size;
        return index;
    }

    public void trimToSize() {
        int i = this.f1525n;
        byte[] bArr = this.array;
        if (i < bArr.length) {
            byte[] aux = new byte[i];
            System.arraycopy(bArr, 0, aux, 0, i);
            this.array = aux;
        }
    }
}
