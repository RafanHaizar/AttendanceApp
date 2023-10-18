package com.itextpdf.p026io.codec;

import java.io.PrintStream;
import kotlin.UByte;
import kotlin.UShort;

/* renamed from: com.itextpdf.io.codec.LZWStringTable */
public class LZWStringTable {
    private static final short HASHSIZE = 9973;
    private static final short HASHSTEP = 2039;
    private static final short HASH_FREE = -1;
    private static final int MAXBITS = 12;
    private static final int MAXSTR = 4096;
    private static final short NEXT_FIRST = -1;
    private static final int RES_CODES = 2;
    short numStrings_;
    byte[] strChr_ = new byte[4096];
    short[] strHsh_ = new short[9973];
    int[] strLen_ = new int[4096];
    short[] strNxt_ = new short[4096];

    public int AddCharString(short index, byte b) {
        short[] sArr;
        if (this.numStrings_ >= 4096) {
            return 65535;
        }
        int hshidx = Hash(index, b);
        while (true) {
            sArr = this.strHsh_;
            if (sArr[hshidx] == -1) {
                break;
            }
            hshidx = (hshidx + 2039) % 9973;
        }
        short s = this.numStrings_;
        sArr[hshidx] = s;
        this.strChr_[s] = b;
        if (index == -1) {
            this.strNxt_[s] = -1;
            this.strLen_[s] = 1;
        } else {
            this.strNxt_[s] = index;
            int[] iArr = this.strLen_;
            iArr[s] = iArr[index] + 1;
        }
        this.numStrings_ = (short) (s + 1);
        return s;
    }

    public short FindCharString(short index, byte b) {
        if (index == -1) {
            return (short) (b & UByte.MAX_VALUE);
        }
        int hshidx = Hash(index, b);
        while (true) {
            short s = this.strHsh_[hshidx];
            int nxtidx = s;
            if (s == -1) {
                return -1;
            }
            if (this.strNxt_[nxtidx] == index && this.strChr_[nxtidx] == b) {
                return (short) nxtidx;
            }
            hshidx = (hshidx + 2039) % 9973;
        }
    }

    public void ClearTable(int codesize) {
        this.numStrings_ = 0;
        for (int q = 0; q < 9973; q++) {
            this.strHsh_[q] = -1;
        }
        int w = (1 << codesize) + 2;
        for (int q2 = 0; q2 < w; q2++) {
            AddCharString(-1, (byte) q2);
        }
    }

    public static int Hash(short index, byte lastbyte) {
        return ((((short) (lastbyte << 8)) ^ index) & UShort.MAX_VALUE) % HASHSIZE;
    }

    public int expandCode(byte[] buf, int offset, short code, int skipHead) {
        int i;
        int expandLen;
        if (offset == -2 && skipHead == 1) {
            skipHead = 0;
        }
        if (code == -1 || skipHead == (i = this.strLen_[code])) {
            return 0;
        }
        int codeLen = i - skipHead;
        int bufSpace = buf.length - offset;
        if (bufSpace > codeLen) {
            expandLen = codeLen;
        } else {
            expandLen = bufSpace;
        }
        int skipTail = codeLen - expandLen;
        int idx = offset + expandLen;
        while (idx > offset && code != -1) {
            skipTail--;
            if (skipTail < 0) {
                idx--;
                buf[idx] = this.strChr_[code];
            }
            code = this.strNxt_[code];
        }
        if (codeLen > expandLen) {
            return -expandLen;
        }
        return expandLen;
    }

    public void dump(PrintStream output) {
        for (int i = 258; i < this.numStrings_; i++) {
            output.println(" strNxt_[" + i + "] = " + this.strNxt_[i] + " strChr_ " + Integer.toHexString(this.strChr_[i] & UByte.MAX_VALUE) + " strLen_ " + Integer.toHexString(this.strLen_[i]));
        }
    }
}
