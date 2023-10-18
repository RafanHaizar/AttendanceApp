package com.itextpdf.layout.hyphenation;

import java.io.Serializable;

public class CharVector implements Serializable {
    private static final int DEFAULT_BLOCK_SIZE = 2048;
    private static final long serialVersionUID = 4263472982169004048L;
    private char[] array;
    private int blockSize;

    /* renamed from: n */
    private int f1526n;

    public CharVector() {
        this(2048);
    }

    public CharVector(int capacity) {
        if (capacity > 0) {
            this.blockSize = capacity;
        } else {
            this.blockSize = 2048;
        }
        this.array = new char[this.blockSize];
        this.f1526n = 0;
    }

    public CharVector(char[] a) {
        this.blockSize = 2048;
        this.array = a;
        this.f1526n = a.length;
    }

    public CharVector(char[] a, int capacity) {
        if (capacity > 0) {
            this.blockSize = capacity;
        } else {
            this.blockSize = 2048;
        }
        this.array = a;
        this.f1526n = a.length;
    }

    public CharVector(CharVector cv) {
        this.array = (char[]) cv.array.clone();
        this.blockSize = cv.blockSize;
        this.f1526n = cv.f1526n;
    }

    public void clear() {
        this.f1526n = 0;
    }

    public char[] getArray() {
        return this.array;
    }

    public int length() {
        return this.f1526n;
    }

    public int capacity() {
        return this.array.length;
    }

    public void put(int index, char val) {
        this.array[index] = val;
    }

    public char get(int index) {
        return this.array[index];
    }

    public int alloc(int size) {
        int index = this.f1526n;
        char[] cArr = this.array;
        int len = cArr.length;
        if (this.f1526n + size >= len) {
            char[] aux = new char[(this.blockSize + len)];
            System.arraycopy(cArr, 0, aux, 0, len);
            this.array = aux;
        }
        this.f1526n += size;
        return index;
    }

    public void trimToSize() {
        int i = this.f1526n;
        char[] cArr = this.array;
        if (i < cArr.length) {
            char[] aux = new char[i];
            System.arraycopy(cArr, 0, aux, 0, i);
            this.array = aux;
        }
    }
}
