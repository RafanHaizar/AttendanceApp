package com.itextpdf.kernel.crypto;

import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class AESCipher {

    /* renamed from: bp */
    private PaddedBufferedBlockCipher f1250bp = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));

    public AESCipher(boolean forEncryption, byte[] key, byte[] iv) {
        this.f1250bp.init(forEncryption, new ParametersWithIV(new KeyParameter(key), iv));
    }

    public byte[] update(byte[] inp, int inpOff, int inpLen) {
        byte[] outp;
        int neededLen = this.f1250bp.getUpdateOutputSize(inpLen);
        if (neededLen > 0) {
            outp = new byte[neededLen];
        } else {
            outp = new byte[0];
        }
        this.f1250bp.processBytes(inp, inpOff, inpLen, outp, 0);
        return outp;
    }

    public byte[] doFinal() {
        byte[] outp = new byte[this.f1250bp.getOutputSize(0)];
        try {
            int n = this.f1250bp.doFinal(outp, 0);
            if (n == outp.length) {
                return outp;
            }
            byte[] outp2 = new byte[n];
            System.arraycopy(outp, 0, outp2, 0, n);
            return outp2;
        } catch (Exception e) {
            return outp;
        }
    }
}
