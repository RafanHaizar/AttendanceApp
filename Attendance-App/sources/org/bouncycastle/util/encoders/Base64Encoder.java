package org.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

public class Base64Encoder implements Encoder {
    protected final byte[] decodingTable = new byte[128];
    protected final byte[] encodingTable = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    protected byte padding = 61;

    public Base64Encoder() {
        initialiseDecodingTable();
    }

    private int decodeLastBlock(OutputStream outputStream, char c, char c2, char c3, char c4) throws IOException {
        byte b = this.padding;
        if (c3 == b) {
            if (c4 == b) {
                byte[] bArr = this.decodingTable;
                byte b2 = bArr[c];
                byte b3 = bArr[c2];
                if ((b2 | b3) >= 0) {
                    outputStream.write((b2 << 2) | (b3 >> 4));
                    return 1;
                }
                throw new IOException("invalid characters encountered at end of base64 data");
            }
            throw new IOException("invalid characters encountered at end of base64 data");
        } else if (c4 == b) {
            byte[] bArr2 = this.decodingTable;
            byte b4 = bArr2[c];
            byte b5 = bArr2[c2];
            byte b6 = bArr2[c3];
            if ((b4 | b5 | b6) >= 0) {
                outputStream.write((b4 << 2) | (b5 >> 4));
                outputStream.write((b5 << 4) | (b6 >> 2));
                return 2;
            }
            throw new IOException("invalid characters encountered at end of base64 data");
        } else {
            byte[] bArr3 = this.decodingTable;
            byte b7 = bArr3[c];
            byte b8 = bArr3[c2];
            byte b9 = bArr3[c3];
            byte b10 = bArr3[c4];
            if ((b7 | b8 | b9 | b10) >= 0) {
                outputStream.write((b7 << 2) | (b8 >> 4));
                outputStream.write((b8 << 4) | (b9 >> 2));
                outputStream.write((b9 << 6) | b10);
                return 3;
            }
            throw new IOException("invalid characters encountered at end of base64 data");
        }
    }

    private boolean ignore(char c) {
        return c == 10 || c == 13 || c == 9 || c == ' ';
    }

    private int nextI(String str, int i, int i2) {
        while (i < i2 && ignore(str.charAt(i))) {
            i++;
        }
        return i;
    }

    private int nextI(byte[] bArr, int i, int i2) {
        while (i < i2 && ignore((char) bArr[i])) {
            i++;
        }
        return i;
    }

    public int decode(String str, OutputStream outputStream) throws IOException {
        int length = str.length();
        while (length > 0 && ignore(str.charAt(length - 1))) {
            length--;
        }
        int i = 0;
        if (length == 0) {
            return 0;
        }
        int i2 = length;
        int i3 = 0;
        while (i2 > 0 && i3 != 4) {
            if (!ignore(str.charAt(i2 - 1))) {
                i3++;
            }
            i2--;
        }
        int nextI = nextI(str, 0, i2);
        while (nextI < i2) {
            int i4 = nextI + 1;
            byte b = this.decodingTable[str.charAt(nextI)];
            int nextI2 = nextI(str, i4, i2);
            int i5 = nextI2 + 1;
            byte b2 = this.decodingTable[str.charAt(nextI2)];
            int nextI3 = nextI(str, i5, i2);
            int i6 = nextI3 + 1;
            byte b3 = this.decodingTable[str.charAt(nextI3)];
            int nextI4 = nextI(str, i6, i2);
            int i7 = nextI4 + 1;
            byte b4 = this.decodingTable[str.charAt(nextI4)];
            if ((b | b2 | b3 | b4) >= 0) {
                outputStream.write((b << 2) | (b2 >> 4));
                outputStream.write((b2 << 4) | (b3 >> 2));
                outputStream.write((b3 << 6) | b4);
                i += 3;
                nextI = nextI(str, i7, i2);
            } else {
                throw new IOException("invalid characters encountered in base64 data");
            }
        }
        int nextI5 = nextI(str, nextI, length);
        int nextI6 = nextI(str, nextI5 + 1, length);
        int nextI7 = nextI(str, nextI6 + 1, length);
        return i + decodeLastBlock(outputStream, str.charAt(nextI5), str.charAt(nextI6), str.charAt(nextI7), str.charAt(nextI(str, nextI7 + 1, length)));
    }

    public int decode(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        int i3 = i2 + i;
        while (i3 > i && ignore((char) bArr[i3 - 1])) {
            i3--;
        }
        int i4 = 0;
        if (i3 == 0) {
            return 0;
        }
        int i5 = i3;
        int i6 = 0;
        while (i5 > i && i6 != 4) {
            if (!ignore((char) bArr[i5 - 1])) {
                i6++;
            }
            i5--;
        }
        int nextI = nextI(bArr, i, i5);
        while (nextI < i5) {
            int i7 = nextI + 1;
            byte b = this.decodingTable[bArr[nextI]];
            int nextI2 = nextI(bArr, i7, i5);
            int i8 = nextI2 + 1;
            byte b2 = this.decodingTable[bArr[nextI2]];
            int nextI3 = nextI(bArr, i8, i5);
            int i9 = nextI3 + 1;
            byte b3 = this.decodingTable[bArr[nextI3]];
            int nextI4 = nextI(bArr, i9, i5);
            int i10 = nextI4 + 1;
            byte b4 = this.decodingTable[bArr[nextI4]];
            if ((b | b2 | b3 | b4) >= 0) {
                outputStream.write((b << 2) | (b2 >> 4));
                outputStream.write((b2 << 4) | (b3 >> 2));
                outputStream.write((b3 << 6) | b4);
                i4 += 3;
                nextI = nextI(bArr, i10, i5);
            } else {
                throw new IOException("invalid characters encountered in base64 data");
            }
        }
        int nextI5 = nextI(bArr, nextI, i3);
        int nextI6 = nextI(bArr, nextI5 + 1, i3);
        int nextI7 = nextI(bArr, nextI6 + 1, i3);
        return i4 + decodeLastBlock(outputStream, (char) bArr[nextI5], (char) bArr[nextI6], (char) bArr[nextI7], (char) bArr[nextI(bArr, nextI7 + 1, i3)]);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x00a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int encode(byte[] r9, int r10, int r11, java.io.OutputStream r12) throws java.io.IOException {
        /*
            r8 = this;
            int r0 = r11 % 3
            int r11 = r11 - r0
            r1 = r10
        L_0x0004:
            int r2 = r10 + r11
            r3 = 4
            if (r1 >= r2) goto L_0x004b
            byte r2 = r9[r1]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r4 = r1 + 1
            byte r4 = r9[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r5 = r1 + 2
            byte r5 = r9[r5]
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte[] r6 = r8.encodingTable
            int r7 = r2 >>> 2
            r7 = r7 & 63
            byte r6 = r6[r7]
            r12.write(r6)
            byte[] r6 = r8.encodingTable
            int r2 = r2 << r3
            int r3 = r4 >>> 4
            r2 = r2 | r3
            r2 = r2 & 63
            byte r2 = r6[r2]
            r12.write(r2)
            byte[] r2 = r8.encodingTable
            int r3 = r4 << 2
            int r4 = r5 >>> 6
            r3 = r3 | r4
            r3 = r3 & 63
            byte r2 = r2[r3]
            r12.write(r2)
            byte[] r2 = r8.encodingTable
            r3 = r5 & 63
            byte r2 = r2[r3]
            r12.write(r2)
            int r1 = r1 + 3
            goto L_0x0004
        L_0x004b:
            switch(r0) {
                case 0: goto L_0x009d;
                case 1: goto L_0x007a;
                case 2: goto L_0x004f;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x009d
        L_0x004f:
            byte r10 = r9[r2]
            r10 = r10 & 255(0xff, float:3.57E-43)
            int r2 = r2 + 1
            byte r9 = r9[r2]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r1 = r10 >>> 2
            r1 = r1 & 63
            int r10 = r10 << r3
            int r2 = r9 >>> 4
            r10 = r10 | r2
            r10 = r10 & 63
            int r9 = r9 << 2
            r9 = r9 & 63
            byte[] r2 = r8.encodingTable
            byte r1 = r2[r1]
            r12.write(r1)
            byte[] r1 = r8.encodingTable
            byte r10 = r1[r10]
            r12.write(r10)
            byte[] r10 = r8.encodingTable
            byte r9 = r10[r9]
            goto L_0x0095
        L_0x007a:
            byte r9 = r9[r2]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r10 = r9 >>> 2
            r10 = r10 & 63
            int r9 = r9 << r3
            r9 = r9 & 63
            byte[] r1 = r8.encodingTable
            byte r10 = r1[r10]
            r12.write(r10)
            byte[] r10 = r8.encodingTable
            byte r9 = r10[r9]
            r12.write(r9)
            byte r9 = r8.padding
        L_0x0095:
            r12.write(r9)
            byte r9 = r8.padding
            r12.write(r9)
        L_0x009d:
            int r11 = r11 / 3
            int r11 = r11 * 4
            if (r0 != 0) goto L_0x00a4
            r3 = 0
        L_0x00a4:
            int r11 = r11 + r3
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.util.encoders.Base64Encoder.encode(byte[], int, int, java.io.OutputStream):int");
    }

    /* access modifiers changed from: protected */
    public void initialiseDecodingTable() {
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.decodingTable;
            if (i2 >= bArr.length) {
                break;
            }
            bArr[i2] = -1;
            i2++;
        }
        while (true) {
            byte[] bArr2 = this.encodingTable;
            if (i < bArr2.length) {
                this.decodingTable[bArr2[i]] = (byte) i;
                i++;
            } else {
                return;
            }
        }
    }
}
