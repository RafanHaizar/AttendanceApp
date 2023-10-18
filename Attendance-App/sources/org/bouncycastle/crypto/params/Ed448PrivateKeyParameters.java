package org.bouncycastle.crypto.params;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import org.bouncycastle.math.p018ec.rfc8032.Ed448;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.p023io.Streams;

public final class Ed448PrivateKeyParameters extends AsymmetricKeyParameter {
    public static final int KEY_SIZE = 57;
    public static final int SIGNATURE_SIZE = 114;
    private final byte[] data;

    public Ed448PrivateKeyParameters(InputStream inputStream) throws IOException {
        super(true);
        byte[] bArr = new byte[57];
        this.data = bArr;
        if (57 != Streams.readFully(inputStream, bArr)) {
            throw new EOFException("EOF encountered in middle of Ed448 private key");
        }
    }

    public Ed448PrivateKeyParameters(SecureRandom secureRandom) {
        super(true);
        byte[] bArr = new byte[57];
        this.data = bArr;
        Ed448.generatePrivateKey(secureRandom, bArr);
    }

    public Ed448PrivateKeyParameters(byte[] bArr, int i) {
        super(true);
        byte[] bArr2 = new byte[57];
        this.data = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 57);
    }

    public void encode(byte[] bArr, int i) {
        System.arraycopy(this.data, 0, bArr, i, 57);
    }

    public Ed448PublicKeyParameters generatePublicKey() {
        byte[] bArr = new byte[57];
        Ed448.generatePublicKey(this.data, 0, bArr, 0);
        return new Ed448PublicKeyParameters(bArr, 0);
    }

    public byte[] getEncoded() {
        return Arrays.clone(this.data);
    }

    public void sign(int i, Ed448PublicKeyParameters ed448PublicKeyParameters, byte[] bArr, byte[] bArr2, int i2, int i3, byte[] bArr3, int i4) {
        byte[] bArr4 = new byte[57];
        if (ed448PublicKeyParameters == null) {
            Ed448.generatePublicKey(this.data, 0, bArr4, 0);
        } else {
            ed448PublicKeyParameters.encode(bArr4, 0);
        }
        switch (i) {
            case 0:
                int i5 = i3;
                Ed448.sign(this.data, 0, bArr4, 0, bArr, bArr2, i2, i3, bArr3, i4);
                return;
            case 1:
                if (64 == i3) {
                    Ed448.signPrehash(this.data, 0, bArr4, 0, bArr, bArr2, i2, bArr3, i4);
                    return;
                }
                throw new IllegalArgumentException("msgLen");
            default:
                throw new IllegalArgumentException("algorithm");
        }
    }
}
