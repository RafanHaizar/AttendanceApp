package com.itextpdf.p026io.codec;

import com.itextpdf.p026io.IOException;
import kotlin.UByte;

/* renamed from: com.itextpdf.io.codec.TIFFLZWDecoder */
public class TIFFLZWDecoder {
    int[] andTable = {511, 1023, 2047, 4095};
    int bitPointer;
    int bitsToGet = 9;
    int bytePointer;
    byte[] data = null;
    int dstIndex;

    /* renamed from: h */
    int f1204h;
    int nextBits = 0;
    int nextData = 0;
    int predictor;
    int samplesPerPixel;
    byte[][] stringTable;
    int tableIndex;
    byte[] uncompData;

    /* renamed from: w */
    int f1205w;

    public TIFFLZWDecoder(int w, int predictor2, int samplesPerPixel2) {
        this.f1205w = w;
        this.predictor = predictor2;
        this.samplesPerPixel = samplesPerPixel2;
    }

    public byte[] decode(byte[] data2, byte[] uncompData2, int h) {
        if (data2[0] == 0 && data2[1] == 1) {
            throw new IOException(IOException.Tiff50StyleLzwCodesAreNotSupported);
        }
        initializeStringTable();
        this.data = data2;
        this.f1204h = h;
        this.uncompData = uncompData2;
        this.bytePointer = 0;
        this.bitPointer = 0;
        this.dstIndex = 0;
        this.nextData = 0;
        this.nextBits = 0;
        int oldCode = 0;
        while (true) {
            int nextCode = getNextCode();
            int code = nextCode;
            if (nextCode == 257 || this.dstIndex >= uncompData2.length) {
                break;
            } else if (code == 256) {
                initializeStringTable();
                int code2 = getNextCode();
                if (code2 == 257) {
                    break;
                }
                writeString(this.stringTable[code2]);
                oldCode = code2;
            } else if (code < this.tableIndex) {
                byte[] str = this.stringTable[code];
                writeString(str);
                addStringToTable(this.stringTable[oldCode], str[0]);
                oldCode = code;
            } else {
                byte[] str2 = this.stringTable[oldCode];
                byte[] str3 = composeString(str2, str2[0]);
                writeString(str3);
                addStringToTable(str3);
                oldCode = code;
            }
        }
        if (this.predictor == 2) {
            for (int j = 0; j < h; j++) {
                int count = this.samplesPerPixel * ((this.f1205w * j) + 1);
                int i = this.samplesPerPixel;
                while (true) {
                    int i2 = this.f1205w;
                    int i3 = this.samplesPerPixel;
                    if (i >= i2 * i3) {
                        break;
                    }
                    uncompData2[count] = (byte) (uncompData2[count] + uncompData2[count - i3]);
                    count++;
                    i++;
                }
            }
        }
        return uncompData2;
    }

    public void initializeStringTable() {
        this.stringTable = new byte[4096][];
        for (int i = 0; i < 256; i++) {
            byte[] bArr = new byte[1];
            this.stringTable[i] = bArr;
            bArr[0] = (byte) i;
        }
        this.tableIndex = 258;
        this.bitsToGet = 9;
    }

    public void writeString(byte[] str) {
        byte[] bArr = this.uncompData;
        int length = bArr.length;
        int i = this.dstIndex;
        int max = length - i;
        if (str.length < max) {
            max = str.length;
        }
        System.arraycopy(str, 0, bArr, i, max);
        this.dstIndex += max;
    }

    public void addStringToTable(byte[] oldString, byte newString) {
        int length = oldString.length;
        byte[] str = new byte[(length + 1)];
        System.arraycopy(oldString, 0, str, 0, length);
        str[length] = newString;
        byte[][] bArr = this.stringTable;
        int i = this.tableIndex;
        int i2 = i + 1;
        this.tableIndex = i2;
        bArr[i] = str;
        if (i2 == 511) {
            this.bitsToGet = 10;
        } else if (i2 == 1023) {
            this.bitsToGet = 11;
        } else if (i2 == 2047) {
            this.bitsToGet = 12;
        }
    }

    public void addStringToTable(byte[] str) {
        byte[][] bArr = this.stringTable;
        int i = this.tableIndex;
        int i2 = i + 1;
        this.tableIndex = i2;
        bArr[i] = str;
        if (i2 == 511) {
            this.bitsToGet = 10;
        } else if (i2 == 1023) {
            this.bitsToGet = 11;
        } else if (i2 == 2047) {
            this.bitsToGet = 12;
        }
    }

    public byte[] composeString(byte[] oldString, byte newString) {
        int length = oldString.length;
        byte[] str = new byte[(length + 1)];
        System.arraycopy(oldString, 0, str, 0, length);
        str[length] = newString;
        return str;
    }

    public int getNextCode() {
        try {
            byte[] bArr = this.data;
            int i = this.bytePointer;
            int i2 = i + 1;
            this.bytePointer = i2;
            byte b = (this.nextData << 8) | (bArr[i] & UByte.MAX_VALUE);
            this.nextData = b;
            int i3 = this.nextBits + 8;
            this.nextBits = i3;
            int i4 = this.bitsToGet;
            if (i3 < i4) {
                this.bytePointer = i2 + 1;
                this.nextData = (b << 8) | (bArr[i2] & UByte.MAX_VALUE);
                this.nextBits = i3 + 8;
            }
            int i5 = this.nextData;
            int i6 = this.nextBits;
            int code = (i5 >> (i6 - i4)) & this.andTable[i4 - 9];
            this.nextBits = i6 - i4;
            return code;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 257;
        }
    }
}
