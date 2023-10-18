package org.bouncycastle.crypto.p013io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.modes.AEADBlockCipher;

/* renamed from: org.bouncycastle.crypto.io.CipherOutputStream */
public class CipherOutputStream extends FilterOutputStream {
    private AEADBlockCipher aeadBlockCipher;
    private byte[] buf;
    private BufferedBlockCipher bufferedBlockCipher;
    private final byte[] oneByte = new byte[1];
    private StreamCipher streamCipher;

    public CipherOutputStream(OutputStream outputStream, BufferedBlockCipher bufferedBlockCipher2) {
        super(outputStream);
        this.bufferedBlockCipher = bufferedBlockCipher2;
    }

    public CipherOutputStream(OutputStream outputStream, StreamCipher streamCipher2) {
        super(outputStream);
        this.streamCipher = streamCipher2;
    }

    public CipherOutputStream(OutputStream outputStream, AEADBlockCipher aEADBlockCipher) {
        super(outputStream);
        this.aeadBlockCipher = aEADBlockCipher;
    }

    private void ensureCapacity(int i, boolean z) {
        if (z) {
            BufferedBlockCipher bufferedBlockCipher2 = this.bufferedBlockCipher;
            if (bufferedBlockCipher2 != null) {
                i = bufferedBlockCipher2.getOutputSize(i);
            } else {
                AEADBlockCipher aEADBlockCipher = this.aeadBlockCipher;
                if (aEADBlockCipher != null) {
                    i = aEADBlockCipher.getOutputSize(i);
                }
            }
        } else {
            BufferedBlockCipher bufferedBlockCipher3 = this.bufferedBlockCipher;
            if (bufferedBlockCipher3 != null) {
                i = bufferedBlockCipher3.getUpdateOutputSize(i);
            } else {
                AEADBlockCipher aEADBlockCipher2 = this.aeadBlockCipher;
                if (aEADBlockCipher2 != null) {
                    i = aEADBlockCipher2.getUpdateOutputSize(i);
                }
            }
        }
        byte[] bArr = this.buf;
        if (bArr == null || bArr.length < i) {
            this.buf = new byte[i];
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0057 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0058  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() throws java.io.IOException {
        /*
            r4 = this;
            r0 = 1
            r1 = 0
            r4.ensureCapacity(r1, r0)
            org.bouncycastle.crypto.BufferedBlockCipher r0 = r4.bufferedBlockCipher     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            if (r0 == 0) goto L_0x0019
            byte[] r2 = r4.buf     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            int r0 = r0.doFinal(r2, r1)     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            if (r0 == 0) goto L_0x0034
            java.io.OutputStream r2 = r4.out     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            byte[] r3 = r4.buf     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            r2.write(r3, r1, r0)     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            goto L_0x0034
        L_0x0019:
            org.bouncycastle.crypto.modes.AEADBlockCipher r0 = r4.aeadBlockCipher     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            if (r0 == 0) goto L_0x002d
            byte[] r2 = r4.buf     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            int r0 = r0.doFinal(r2, r1)     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            if (r0 == 0) goto L_0x0034
            java.io.OutputStream r2 = r4.out     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            byte[] r3 = r4.buf     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            r2.write(r3, r1, r0)     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            goto L_0x0034
        L_0x002d:
            org.bouncycastle.crypto.StreamCipher r0 = r4.streamCipher     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
            if (r0 == 0) goto L_0x0034
            r0.reset()     // Catch:{ InvalidCipherTextException -> 0x003f, Exception -> 0x0036 }
        L_0x0034:
            r0 = 0
            goto L_0x0048
        L_0x0036:
            r0 = move-exception
            org.bouncycastle.crypto.io.CipherIOException r1 = new org.bouncycastle.crypto.io.CipherIOException
            java.lang.String r2 = "Error closing stream: "
            r1.<init>(r2, r0)
            goto L_0x0047
        L_0x003f:
            r0 = move-exception
            org.bouncycastle.crypto.io.InvalidCipherTextIOException r1 = new org.bouncycastle.crypto.io.InvalidCipherTextIOException
            java.lang.String r2 = "Error finalising cipher data"
            r1.<init>(r2, r0)
        L_0x0047:
            r0 = r1
        L_0x0048:
            r4.flush()     // Catch:{ IOException -> 0x0051 }
            java.io.OutputStream r1 = r4.out     // Catch:{ IOException -> 0x0051 }
            r1.close()     // Catch:{ IOException -> 0x0051 }
            goto L_0x0055
        L_0x0051:
            r1 = move-exception
            if (r0 != 0) goto L_0x0055
            r0 = r1
        L_0x0055:
            if (r0 != 0) goto L_0x0058
            return
        L_0x0058:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.p013io.CipherOutputStream.close():void");
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void write(int i) throws IOException {
        byte[] bArr = this.oneByte;
        byte b = (byte) i;
        bArr[0] = b;
        if (this.streamCipher != null) {
            this.out.write(this.streamCipher.returnByte(b));
        } else {
            write(bArr, 0, 1);
        }
    }

    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        ensureCapacity(i2, false);
        BufferedBlockCipher bufferedBlockCipher2 = this.bufferedBlockCipher;
        if (bufferedBlockCipher2 != null) {
            int processBytes = bufferedBlockCipher2.processBytes(bArr, i, i2, this.buf, 0);
            if (processBytes != 0) {
                this.out.write(this.buf, 0, processBytes);
                return;
            }
            return;
        }
        AEADBlockCipher aEADBlockCipher = this.aeadBlockCipher;
        if (aEADBlockCipher != null) {
            int processBytes2 = aEADBlockCipher.processBytes(bArr, i, i2, this.buf, 0);
            if (processBytes2 != 0) {
                this.out.write(this.buf, 0, processBytes2);
                return;
            }
            return;
        }
        this.streamCipher.processBytes(bArr, i, i2, this.buf, 0);
        this.out.write(this.buf, 0, i2);
    }
}
