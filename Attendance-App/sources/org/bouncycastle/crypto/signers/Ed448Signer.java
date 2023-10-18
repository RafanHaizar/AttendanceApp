package org.bouncycastle.crypto.signers;

import java.io.ByteArrayOutputStream;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.Ed448PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed448PublicKeyParameters;
import org.bouncycastle.math.p018ec.rfc8032.Ed448;
import org.bouncycastle.util.Arrays;

public class Ed448Signer implements Signer {
    private final Buffer buffer = new Buffer();
    private final byte[] context;
    private boolean forSigning;
    private Ed448PrivateKeyParameters privateKey;
    private Ed448PublicKeyParameters publicKey;

    private static class Buffer extends ByteArrayOutputStream {
        private Buffer() {
        }

        /* access modifiers changed from: package-private */
        public synchronized byte[] generateSignature(Ed448PrivateKeyParameters ed448PrivateKeyParameters, Ed448PublicKeyParameters ed448PublicKeyParameters, byte[] bArr) {
            byte[] bArr2;
            bArr2 = new byte[114];
            ed448PrivateKeyParameters.sign(0, ed448PublicKeyParameters, bArr, this.buf, 0, this.count, bArr2, 0);
            reset();
            return bArr2;
        }

        public synchronized void reset() {
            Arrays.fill(this.buf, 0, this.count, (byte) 0);
            this.count = 0;
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean verifySignature(Ed448PublicKeyParameters ed448PublicKeyParameters, byte[] bArr, byte[] bArr2) {
            if (114 != bArr2.length) {
                return false;
            }
            boolean verify = Ed448.verify(bArr2, 0, ed448PublicKeyParameters.getEncoded(), 0, bArr, this.buf, 0, this.count);
            reset();
            return verify;
        }
    }

    public Ed448Signer(byte[] bArr) {
        this.context = Arrays.clone(bArr);
    }

    public byte[] generateSignature() {
        Ed448PrivateKeyParameters ed448PrivateKeyParameters;
        if (this.forSigning && (ed448PrivateKeyParameters = this.privateKey) != null) {
            return this.buffer.generateSignature(ed448PrivateKeyParameters, this.publicKey, this.context);
        }
        throw new IllegalStateException("Ed448Signer not initialised for signature generation.");
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.forSigning = z;
        if (z) {
            Ed448PrivateKeyParameters ed448PrivateKeyParameters = (Ed448PrivateKeyParameters) cipherParameters;
            this.privateKey = ed448PrivateKeyParameters;
            this.publicKey = ed448PrivateKeyParameters.generatePublicKey();
        } else {
            this.privateKey = null;
            this.publicKey = (Ed448PublicKeyParameters) cipherParameters;
        }
        reset();
    }

    public void reset() {
        this.buffer.reset();
    }

    public void update(byte b) {
        this.buffer.write(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.buffer.write(bArr, i, i2);
    }

    public boolean verifySignature(byte[] bArr) {
        Ed448PublicKeyParameters ed448PublicKeyParameters;
        if (!this.forSigning && (ed448PublicKeyParameters = this.publicKey) != null) {
            return this.buffer.verifySignature(ed448PublicKeyParameters, this.context, bArr);
        }
        throw new IllegalStateException("Ed448Signer not initialised for verification");
    }
}
