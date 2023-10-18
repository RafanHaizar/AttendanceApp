package com.itextpdf.kernel.pdf.filters;

import com.itextpdf.kernel.PdfException;
import java.io.IOException;
import java.io.OutputStream;
import kotlin.UByte;

public class LZWDecoder {
    int[] andTable = {511, 1023, 2047, 4095};
    int bitPointer;
    int bitsToGet = 9;
    int bytePointer;
    byte[] data = null;
    int nextBits = 0;
    int nextData = 0;
    byte[][] stringTable;
    int tableIndex;
    OutputStream uncompData;

    public void decode(byte[] data2, OutputStream uncompData2) {
        if (data2[0] == 0 && data2[1] == 1) {
            throw new PdfException(PdfException.LzwFlavourNotSupported);
        }
        initializeStringTable();
        this.data = data2;
        this.uncompData = uncompData2;
        this.bytePointer = 0;
        this.bitPointer = 0;
        this.nextData = 0;
        this.nextBits = 0;
        int oldCode = 0;
        while (true) {
            int nextCode = getNextCode();
            int code = nextCode;
            if (nextCode == 257) {
                return;
            }
            if (code == 256) {
                initializeStringTable();
                int code2 = getNextCode();
                if (code2 != 257) {
                    writeString(this.stringTable[code2]);
                    oldCode = code2;
                } else {
                    return;
                }
            } else if (code < this.tableIndex) {
                byte[] string = this.stringTable[code];
                writeString(string);
                addStringToTable(this.stringTable[oldCode], string[0]);
                oldCode = code;
            } else {
                byte[] string2 = this.stringTable[oldCode];
                byte[] string3 = composeString(string2, string2[0]);
                writeString(string3);
                addStringToTable(string3);
                oldCode = code;
            }
        }
    }

    public void initializeStringTable() {
        this.stringTable = new byte[8192][];
        for (int i = 0; i < 256; i++) {
            byte[] bArr = new byte[1];
            this.stringTable[i] = bArr;
            bArr[0] = (byte) i;
        }
        this.tableIndex = 258;
        this.bitsToGet = 9;
    }

    public void writeString(byte[] string) {
        try {
            this.uncompData.write(string);
        } catch (IOException e) {
            throw new PdfException(PdfException.LzwDecoderException, (Throwable) e);
        }
    }

    public void addStringToTable(byte[] oldString, byte newString) {
        int length = oldString.length;
        byte[] string = new byte[(length + 1)];
        System.arraycopy(oldString, 0, string, 0, length);
        string[length] = newString;
        byte[][] bArr = this.stringTable;
        int i = this.tableIndex;
        int i2 = i + 1;
        this.tableIndex = i2;
        bArr[i] = string;
        if (i2 == 511) {
            this.bitsToGet = 10;
        } else if (i2 == 1023) {
            this.bitsToGet = 11;
        } else if (i2 == 2047) {
            this.bitsToGet = 12;
        }
    }

    public void addStringToTable(byte[] string) {
        byte[][] bArr = this.stringTable;
        int i = this.tableIndex;
        int i2 = i + 1;
        this.tableIndex = i2;
        bArr[i] = string;
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
        byte[] string = new byte[(length + 1)];
        System.arraycopy(oldString, 0, string, 0, length);
        string[length] = newString;
        return string;
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
