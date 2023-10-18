package com.itextpdf.kernel.crypto;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class AESCipherCBCnoPad {
    private BlockCipher cbc = new CBCBlockCipher(new AESFastEngine());

    public AESCipherCBCnoPad(boolean forEncryption, byte[] key) {
        this.cbc.init(forEncryption, new KeyParameter(key));
    }

    public AESCipherCBCnoPad(boolean forEncryption, byte[] key, byte[] initVector) {
        this.cbc.init(forEncryption, new ParametersWithIV(new KeyParameter(key), initVector));
    }

    public byte[] processBlock(byte[] inp, int inpOff, int inpLen) {
        if (inpLen % this.cbc.getBlockSize() == 0) {
            byte[] outp = new byte[inpLen];
            int baseOffset = 0;
            while (inpLen > 0) {
                this.cbc.processBlock(inp, inpOff, outp, baseOffset);
                inpLen -= this.cbc.getBlockSize();
                baseOffset += this.cbc.getBlockSize();
                inpOff += this.cbc.getBlockSize();
            }
            return outp;
        }
        throw new IllegalArgumentException("Not multiple of block: " + inpLen);
    }
}
