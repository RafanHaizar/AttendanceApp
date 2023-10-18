package org.bouncycastle.crypto.params;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import org.bouncycastle.math.p018ec.rfc8032.Ed25519;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.p023io.Streams;

public final class Ed25519PrivateKeyParameters extends AsymmetricKeyParameter {
    public static final int KEY_SIZE = 32;
    public static final int SIGNATURE_SIZE = 64;
    private final byte[] data;

    public Ed25519PrivateKeyParameters(InputStream inputStream) throws IOException {
        super(true);
        byte[] bArr = new byte[32];
        this.data = bArr;
        if (32 != Streams.readFully(inputStream, bArr)) {
            throw new EOFException("EOF encountered in middle of Ed25519 private key");
        }
    }

    public Ed25519PrivateKeyParameters(SecureRandom secureRandom) {
        super(true);
        byte[] bArr = new byte[32];
        this.data = bArr;
        Ed25519.generatePrivateKey(secureRandom, bArr);
    }

    public Ed25519PrivateKeyParameters(byte[] bArr, int i) {
        super(true);
        byte[] bArr2 = new byte[32];
        this.data = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 32);
    }

    public void encode(byte[] bArr, int i) {
        System.arraycopy(this.data, 0, bArr, i, 32);
    }

    public Ed25519PublicKeyParameters generatePublicKey() {
        byte[] bArr = new byte[32];
        Ed25519.generatePublicKey(this.data, 0, bArr, 0);
        return new Ed25519PublicKeyParameters(bArr, 0);
    }

    public byte[] getEncoded() {
        return Arrays.clone(this.data);
    }

    public void sign(int i, Ed25519PublicKeyParameters ed25519PublicKeyParameters, byte[] bArr, byte[] bArr2, int i2, int i3, byte[] bArr3, int i4) {
        byte[] bArr4 = new byte[32];
        if (ed25519PublicKeyParameters == null) {
            Ed25519.generatePublicKey(this.data, 0, bArr4, 0);
        } else {
            ed25519PublicKeyParameters.encode(bArr4, 0);
        }
        switch (i) {
            case 0:
                int i5 = i3;
                if (bArr == null) {
                    Ed25519.sign(this.data, 0, bArr4, 0, bArr2, i2, i3, bArr3, i4);
                    return;
                }
                throw new IllegalArgumentException("ctx");
            case 1:
                int i6 = i3;
                Ed25519.sign(this.data, 0, bArr4, 0, bArr, bArr2, i2, i3, bArr3, i4);
                return;
            case 2:
                if (64 == i3) {
                    Ed25519.signPrehash(this.data, 0, bArr4, 0, bArr, bArr2, i2, bArr3, i4);
                    return;
                }
                throw new IllegalArgumentException("msgLen");
            default:
                throw new IllegalArgumentException("algorithm");
        }
    }
}
