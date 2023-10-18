package org.bouncycastle.crypto.params;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.p023io.Streams;

public final class X25519PublicKeyParameters extends AsymmetricKeyParameter {
    public static final int KEY_SIZE = 32;
    private final byte[] data;

    public X25519PublicKeyParameters(InputStream inputStream) throws IOException {
        super(false);
        byte[] bArr = new byte[32];
        this.data = bArr;
        if (32 != Streams.readFully(inputStream, bArr)) {
            throw new EOFException("EOF encountered in middle of X25519 public key");
        }
    }

    public X25519PublicKeyParameters(byte[] bArr, int i) {
        super(false);
        byte[] bArr2 = new byte[32];
        this.data = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 32);
    }

    public void encode(byte[] bArr, int i) {
        System.arraycopy(this.data, 0, bArr, i, 32);
    }

    public byte[] getEncoded() {
        return Arrays.clone(this.data);
    }
}
