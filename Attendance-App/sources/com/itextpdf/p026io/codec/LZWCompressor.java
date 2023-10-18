package com.itextpdf.p026io.codec;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: com.itextpdf.io.codec.LZWCompressor */
public class LZWCompressor {
    BitFile bf_;
    int clearCode_;
    int codeSize_;
    int endOfInfo_;
    int limit_;
    LZWStringTable lzss_;
    int numBits_;
    short prefix_;
    boolean tiffFudge_;

    public LZWCompressor(OutputStream outputStream, int codeSize, boolean TIFF) throws IOException {
        this.bf_ = new BitFile(outputStream, !TIFF);
        this.codeSize_ = codeSize;
        this.tiffFudge_ = TIFF;
        int i = 1 << codeSize;
        this.clearCode_ = i;
        this.endOfInfo_ = i + 1;
        int i2 = codeSize + 1;
        this.numBits_ = i2;
        int i3 = (1 << i2) - 1;
        this.limit_ = i3;
        if (TIFF) {
            this.limit_ = i3 - 1;
        }
        this.prefix_ = -1;
        LZWStringTable lZWStringTable = new LZWStringTable();
        this.lzss_ = lZWStringTable;
        lZWStringTable.ClearTable(this.codeSize_);
        this.bf_.writeBits(this.clearCode_, this.numBits_);
    }

    public void compress(byte[] buf, int offset, int length) throws IOException {
        int maxOffset = offset + length;
        for (int idx = offset; idx < maxOffset; idx++) {
            byte c = buf[idx];
            short FindCharString = this.lzss_.FindCharString(this.prefix_, c);
            short index = FindCharString;
            if (FindCharString != -1) {
                this.prefix_ = index;
            } else {
                this.bf_.writeBits(this.prefix_, this.numBits_);
                if (this.lzss_.AddCharString(this.prefix_, c) > this.limit_) {
                    int i = this.numBits_;
                    if (i == 12) {
                        this.bf_.writeBits(this.clearCode_, i);
                        this.lzss_.ClearTable(this.codeSize_);
                        this.numBits_ = this.codeSize_ + 1;
                    } else {
                        this.numBits_ = i + 1;
                    }
                    int i2 = (1 << this.numBits_) - 1;
                    this.limit_ = i2;
                    if (this.tiffFudge_) {
                        this.limit_ = i2 - 1;
                    }
                }
                this.prefix_ = (short) (((short) c) & 255);
            }
        }
    }

    public void flush() throws IOException {
        short s = this.prefix_;
        if (s != -1) {
            this.bf_.writeBits(s, this.numBits_);
        }
        this.bf_.writeBits(this.endOfInfo_, this.numBits_);
        this.bf_.flush();
    }
}
