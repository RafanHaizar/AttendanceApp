package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class CramerShoupCiphertext {

    /* renamed from: e */
    BigInteger f368e;

    /* renamed from: u1 */
    BigInteger f369u1;

    /* renamed from: u2 */
    BigInteger f370u2;

    /* renamed from: v */
    BigInteger f371v;

    public CramerShoupCiphertext() {
    }

    public CramerShoupCiphertext(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.f369u1 = bigInteger;
        this.f370u2 = bigInteger2;
        this.f368e = bigInteger3;
        this.f371v = bigInteger4;
    }

    public CramerShoupCiphertext(byte[] bArr) {
        int bigEndianToInt = Pack.bigEndianToInt(bArr, 0) + 4;
        this.f369u1 = new BigInteger(Arrays.copyOfRange(bArr, 4, bigEndianToInt));
        int bigEndianToInt2 = Pack.bigEndianToInt(bArr, bigEndianToInt);
        int i = bigEndianToInt + 4;
        int i2 = bigEndianToInt2 + i;
        this.f370u2 = new BigInteger(Arrays.copyOfRange(bArr, i, i2));
        int bigEndianToInt3 = Pack.bigEndianToInt(bArr, i2);
        int i3 = i2 + 4;
        int i4 = bigEndianToInt3 + i3;
        this.f368e = new BigInteger(Arrays.copyOfRange(bArr, i3, i4));
        int bigEndianToInt4 = Pack.bigEndianToInt(bArr, i4);
        int i5 = i4 + 4;
        this.f371v = new BigInteger(Arrays.copyOfRange(bArr, i5, bigEndianToInt4 + i5));
    }

    public BigInteger getE() {
        return this.f368e;
    }

    public BigInteger getU1() {
        return this.f369u1;
    }

    public BigInteger getU2() {
        return this.f370u2;
    }

    public BigInteger getV() {
        return this.f371v;
    }

    public void setE(BigInteger bigInteger) {
        this.f368e = bigInteger;
    }

    public void setU1(BigInteger bigInteger) {
        this.f369u1 = bigInteger;
    }

    public void setU2(BigInteger bigInteger) {
        this.f370u2 = bigInteger;
    }

    public void setV(BigInteger bigInteger) {
        this.f371v = bigInteger;
    }

    public byte[] toByteArray() {
        byte[] byteArray = this.f369u1.toByteArray();
        int length = byteArray.length;
        byte[] byteArray2 = this.f370u2.toByteArray();
        int length2 = byteArray2.length;
        byte[] byteArray3 = this.f368e.toByteArray();
        int length3 = byteArray3.length;
        byte[] byteArray4 = this.f371v.toByteArray();
        int length4 = byteArray4.length;
        byte[] bArr = new byte[(length + length2 + length3 + length4 + 16)];
        Pack.intToBigEndian(length, bArr, 0);
        System.arraycopy(byteArray, 0, bArr, 4, length);
        int i = length + 4;
        Pack.intToBigEndian(length2, bArr, i);
        int i2 = i + 4;
        System.arraycopy(byteArray2, 0, bArr, i2, length2);
        int i3 = i2 + length2;
        Pack.intToBigEndian(length3, bArr, i3);
        int i4 = i3 + 4;
        System.arraycopy(byteArray3, 0, bArr, i4, length3);
        int i5 = i4 + length3;
        Pack.intToBigEndian(length4, bArr, i5);
        System.arraycopy(byteArray4, 0, bArr, i5 + 4, length4);
        return bArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("u1: " + this.f369u1.toString());
        stringBuffer.append("\nu2: " + this.f370u2.toString());
        stringBuffer.append("\ne: " + this.f368e.toString());
        stringBuffer.append("\nv: " + this.f371v.toString());
        return stringBuffer.toString();
    }
}
