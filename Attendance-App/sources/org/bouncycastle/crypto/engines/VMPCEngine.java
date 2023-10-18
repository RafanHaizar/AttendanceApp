package org.bouncycastle.crypto.engines;

import kotlin.UByte;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class VMPCEngine implements StreamCipher {

    /* renamed from: P */
    protected byte[] f433P = null;

    /* renamed from: n */
    protected byte f434n = 0;

    /* renamed from: s */
    protected byte f435s = 0;
    protected byte[] workingIV;
    protected byte[] workingKey;

    public String getAlgorithmName() {
        return "VMPC";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            if (parametersWithIV.getParameters() instanceof KeyParameter) {
                KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
                byte[] iv = parametersWithIV.getIV();
                this.workingIV = iv;
                if (iv == null || iv.length < 1 || iv.length > 768) {
                    throw new IllegalArgumentException("VMPC requires 1 to 768 bytes of IV");
                }
                byte[] key = keyParameter.getKey();
                this.workingKey = key;
                initKey(key, this.workingIV);
                return;
            }
            throw new IllegalArgumentException("VMPC init parameters must include a key");
        }
        throw new IllegalArgumentException("VMPC init parameters must include an IV");
    }

    /* access modifiers changed from: protected */
    public void initKey(byte[] bArr, byte[] bArr2) {
        this.f435s = 0;
        this.f433P = new byte[256];
        for (int i = 0; i < 256; i++) {
            this.f433P[i] = (byte) i;
        }
        for (int i2 = 0; i2 < 768; i2++) {
            byte[] bArr3 = this.f433P;
            byte b = this.f435s;
            int i3 = i2 & 255;
            byte b2 = bArr3[i3];
            byte b3 = bArr3[(b + b2 + bArr[i2 % bArr.length]) & 255];
            this.f435s = b3;
            bArr3[i3] = bArr3[b3 & UByte.MAX_VALUE];
            bArr3[b3 & UByte.MAX_VALUE] = b2;
        }
        for (int i4 = 0; i4 < 768; i4++) {
            byte[] bArr4 = this.f433P;
            byte b4 = this.f435s;
            int i5 = i4 & 255;
            byte b5 = bArr4[i5];
            byte b6 = bArr4[(b4 + b5 + bArr2[i4 % bArr2.length]) & 255];
            this.f435s = b6;
            bArr4[i5] = bArr4[b6 & UByte.MAX_VALUE];
            bArr4[b6 & UByte.MAX_VALUE] = b5;
        }
        this.f434n = 0;
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i3 + i2 <= bArr2.length) {
            for (int i4 = 0; i4 < i2; i4++) {
                byte[] bArr3 = this.f433P;
                byte b = this.f435s;
                byte b2 = this.f434n;
                byte b3 = bArr3[(b + bArr3[b2 & UByte.MAX_VALUE]) & 255];
                this.f435s = b3;
                byte b4 = bArr3[(bArr3[bArr3[b3 & UByte.MAX_VALUE] & UByte.MAX_VALUE] + 1) & 255];
                byte b5 = bArr3[b2 & UByte.MAX_VALUE];
                bArr3[b2 & UByte.MAX_VALUE] = bArr3[b3 & UByte.MAX_VALUE];
                bArr3[b3 & UByte.MAX_VALUE] = b5;
                this.f434n = (byte) ((b2 + 1) & 255);
                bArr2[i4 + i3] = (byte) (bArr[i4 + i] ^ b4);
            }
            return i2;
        } else {
            throw new OutputLengthException("output buffer too short");
        }
    }

    public void reset() {
        initKey(this.workingKey, this.workingIV);
    }

    public byte returnByte(byte b) {
        byte[] bArr = this.f433P;
        byte b2 = this.f435s;
        byte b3 = this.f434n;
        byte b4 = bArr[(b2 + bArr[b3 & UByte.MAX_VALUE]) & 255];
        this.f435s = b4;
        byte b5 = bArr[(bArr[bArr[b4 & UByte.MAX_VALUE] & UByte.MAX_VALUE] + 1) & 255];
        byte b6 = bArr[b3 & UByte.MAX_VALUE];
        bArr[b3 & UByte.MAX_VALUE] = bArr[b4 & UByte.MAX_VALUE];
        bArr[b4 & UByte.MAX_VALUE] = b6;
        this.f434n = (byte) ((b3 + 1) & 255);
        return (byte) (b ^ b5);
    }
}
