package org.bouncycastle.crypto.tls;

import java.io.IOException;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;

public class TlsAEADCipher implements TlsCipher {
    static final int NONCE_DRAFT_CHACHA20_POLY1305 = 2;
    public static final int NONCE_RFC5288 = 1;
    protected TlsContext context;
    protected AEADBlockCipher decryptCipher;
    protected byte[] decryptImplicitNonce;
    protected AEADBlockCipher encryptCipher;
    protected byte[] encryptImplicitNonce;
    protected int macSize;
    protected int nonceMode;
    protected int record_iv_length;

    public TlsAEADCipher(TlsContext tlsContext, AEADBlockCipher aEADBlockCipher, AEADBlockCipher aEADBlockCipher2, int i, int i2) throws IOException {
        this(tlsContext, aEADBlockCipher, aEADBlockCipher2, i, i2, 1);
    }

    TlsAEADCipher(TlsContext tlsContext, AEADBlockCipher aEADBlockCipher, AEADBlockCipher aEADBlockCipher2, int i, int i2, int i3) throws IOException {
        int i4;
        TlsContext tlsContext2 = tlsContext;
        AEADBlockCipher aEADBlockCipher3 = aEADBlockCipher;
        AEADBlockCipher aEADBlockCipher4 = aEADBlockCipher2;
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        if (TlsUtils.isTLSv12(tlsContext)) {
            this.nonceMode = i7;
            switch (i7) {
                case 1:
                    this.record_iv_length = 8;
                    i4 = 4;
                    break;
                case 2:
                    this.record_iv_length = 0;
                    i4 = 12;
                    break;
                default:
                    throw new TlsFatalAlert(80);
            }
            this.context = tlsContext2;
            this.macSize = i6;
            int i8 = (i5 * 2) + (i4 * 2);
            byte[] calculateKeyBlock = TlsUtils.calculateKeyBlock(tlsContext2, i8);
            KeyParameter keyParameter = new KeyParameter(calculateKeyBlock, 0, i5);
            int i9 = i5 + 0;
            KeyParameter keyParameter2 = new KeyParameter(calculateKeyBlock, i9, i5);
            int i10 = i9 + i5;
            int i11 = i10 + i4;
            byte[] copyOfRange = Arrays.copyOfRange(calculateKeyBlock, i10, i11);
            int i12 = i11 + i4;
            byte[] copyOfRange2 = Arrays.copyOfRange(calculateKeyBlock, i11, i12);
            if (i12 == i8) {
                if (tlsContext.isServer()) {
                    this.encryptCipher = aEADBlockCipher4;
                    this.decryptCipher = aEADBlockCipher3;
                    this.encryptImplicitNonce = copyOfRange2;
                    this.decryptImplicitNonce = copyOfRange;
                    KeyParameter keyParameter3 = keyParameter2;
                    keyParameter2 = keyParameter;
                    keyParameter = keyParameter3;
                } else {
                    this.encryptCipher = aEADBlockCipher3;
                    this.decryptCipher = aEADBlockCipher4;
                    this.encryptImplicitNonce = copyOfRange;
                    this.decryptImplicitNonce = copyOfRange2;
                }
                byte[] bArr = new byte[(i4 + this.record_iv_length)];
                int i13 = i6 * 8;
                this.encryptCipher.init(true, new AEADParameters(keyParameter, i13, bArr));
                this.decryptCipher.init(false, new AEADParameters(keyParameter2, i13, bArr));
                return;
            }
            throw new TlsFatalAlert(80);
        }
        throw new TlsFatalAlert(80);
    }

    public byte[] decodeCiphertext(long j, short s, byte[] bArr, int i, int i2) throws IOException {
        long j2 = j;
        int i3 = i;
        int i4 = i2;
        if (getPlaintextLimit(i4) >= 0) {
            byte[] bArr2 = this.decryptImplicitNonce;
            int length = bArr2.length + this.record_iv_length;
            byte[] bArr3 = new byte[length];
            switch (this.nonceMode) {
                case 1:
                    System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
                    int i5 = this.record_iv_length;
                    System.arraycopy(bArr, i3, bArr3, length - i5, i5);
                    break;
                case 2:
                    TlsUtils.writeUint64(j2, bArr3, length - 8);
                    int i6 = 0;
                    while (true) {
                        byte[] bArr4 = this.decryptImplicitNonce;
                        if (i6 >= bArr4.length) {
                            byte[] bArr5 = bArr;
                            break;
                        } else {
                            bArr3[i6] = (byte) (bArr4[i6] ^ bArr3[i6]);
                            i6++;
                        }
                    }
                default:
                    throw new TlsFatalAlert(80);
            }
            int i7 = this.record_iv_length;
            int i8 = i3 + i7;
            int i9 = i4 - i7;
            int outputSize = this.decryptCipher.getOutputSize(i9);
            byte[] bArr6 = new byte[outputSize];
            try {
                this.decryptCipher.init(false, new AEADParameters((KeyParameter) null, this.macSize * 8, bArr3, getAdditionalData(j2, s, outputSize)));
                int processBytes = this.decryptCipher.processBytes(bArr, i8, i9, bArr6, 0) + 0;
                if (processBytes + this.decryptCipher.doFinal(bArr6, processBytes) == outputSize) {
                    return bArr6;
                }
                throw new TlsFatalAlert(80);
            } catch (Exception e) {
                throw new TlsFatalAlert(20, e);
            }
        } else {
            throw new TlsFatalAlert(50);
        }
    }

    public byte[] encodePlaintext(long j, short s, byte[] bArr, int i, int i2) throws IOException {
        long j2 = j;
        int i3 = i2;
        byte[] bArr2 = this.encryptImplicitNonce;
        int length = bArr2.length + this.record_iv_length;
        byte[] bArr3 = new byte[length];
        switch (this.nonceMode) {
            case 1:
                System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
                TlsUtils.writeUint64(j, bArr3, this.encryptImplicitNonce.length);
                break;
            case 2:
                TlsUtils.writeUint64(j, bArr3, length - 8);
                int i4 = 0;
                while (true) {
                    byte[] bArr4 = this.encryptImplicitNonce;
                    if (i4 >= bArr4.length) {
                        break;
                    } else {
                        bArr3[i4] = (byte) (bArr4[i4] ^ bArr3[i4]);
                        i4++;
                    }
                }
            default:
                throw new TlsFatalAlert(80);
        }
        int outputSize = this.encryptCipher.getOutputSize(i3);
        int i5 = this.record_iv_length;
        int i6 = i5 + outputSize;
        byte[] bArr5 = new byte[i6];
        if (i5 != 0) {
            System.arraycopy(bArr3, length - i5, bArr5, 0, i5);
        }
        int i7 = this.record_iv_length;
        short s2 = s;
        try {
            this.encryptCipher.init(true, new AEADParameters((KeyParameter) null, this.macSize * 8, bArr3, getAdditionalData(j, s, i3)));
            int processBytes = i7 + this.encryptCipher.processBytes(bArr, i, i2, bArr5, i7);
            if (processBytes + this.encryptCipher.doFinal(bArr5, processBytes) == i6) {
                return bArr5;
            }
            throw new TlsFatalAlert(80);
        } catch (Exception e) {
            throw new TlsFatalAlert(80, e);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] getAdditionalData(long j, short s, int i) throws IOException {
        byte[] bArr = new byte[13];
        TlsUtils.writeUint64(j, bArr, 0);
        TlsUtils.writeUint8(s, bArr, 8);
        TlsUtils.writeVersion(this.context.getServerVersion(), bArr, 9);
        TlsUtils.writeUint16(i, bArr, 11);
        return bArr;
    }

    public int getPlaintextLimit(int i) {
        return (i - this.macSize) - this.record_iv_length;
    }
}
