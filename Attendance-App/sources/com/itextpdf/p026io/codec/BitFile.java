package com.itextpdf.p026io.codec;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: com.itextpdf.io.codec.BitFile */
class BitFile {
    int bitsLeft;
    boolean blocks = false;
    byte[] buffer;
    int index;
    OutputStream output;

    public BitFile(OutputStream output2, boolean blocks2) {
        this.output = output2;
        this.blocks = blocks2;
        this.buffer = new byte[256];
        this.index = 0;
        this.bitsLeft = 8;
    }

    public void flush() throws IOException {
        int numBytes = this.index + (this.bitsLeft == 8 ? 0 : 1);
        if (numBytes > 0) {
            if (this.blocks) {
                this.output.write(numBytes);
            }
            this.output.write(this.buffer, 0, numBytes);
            this.buffer[0] = 0;
            this.index = 0;
            this.bitsLeft = 8;
        }
    }

    public void writeBits(int bits, int numbits) throws IOException {
        int bitsWritten = 0;
        do {
            int i = this.index;
            if ((i == 254 && this.bitsLeft == 0) || i > 254) {
                if (this.blocks) {
                    this.output.write(255);
                }
                this.output.write(this.buffer, 0, 255);
                this.buffer[0] = 0;
                this.index = 0;
                this.bitsLeft = 8;
            }
            int i2 = this.bitsLeft;
            if (numbits <= i2) {
                if (this.blocks) {
                    byte[] bArr = this.buffer;
                    int i3 = this.index;
                    bArr[i3] = (byte) (((byte) ((bits & ((1 << numbits) - 1)) << (8 - i2))) | bArr[i3]);
                    bitsWritten += numbits;
                    this.bitsLeft = i2 - numbits;
                    numbits = 0;
                    continue;
                } else {
                    byte[] bArr2 = this.buffer;
                    int i4 = this.index;
                    bArr2[i4] = (byte) (((byte) ((bits & ((1 << numbits) - 1)) << (i2 - numbits))) | bArr2[i4]);
                    bitsWritten += numbits;
                    this.bitsLeft = i2 - numbits;
                    numbits = 0;
                    continue;
                }
            } else if (this.blocks) {
                byte[] bArr3 = this.buffer;
                int i5 = this.index;
                bArr3[i5] = (byte) (bArr3[i5] | ((byte) ((((1 << i2) - 1) & bits) << (8 - i2))));
                bitsWritten += i2;
                bits >>= i2;
                numbits -= i2;
                int i6 = i5 + 1;
                this.index = i6;
                bArr3[i6] = 0;
                this.bitsLeft = 8;
                continue;
            } else {
                byte[] bArr4 = this.buffer;
                int i7 = this.index;
                bArr4[i7] = (byte) (bArr4[i7] | ((byte) ((bits >>> (numbits - i2)) & ((1 << i2) - 1))));
                numbits -= i2;
                bitsWritten += i2;
                int i8 = i7 + 1;
                this.index = i8;
                bArr4[i8] = 0;
                this.bitsLeft = 8;
                continue;
            }
        } while (numbits != 0);
    }
}
