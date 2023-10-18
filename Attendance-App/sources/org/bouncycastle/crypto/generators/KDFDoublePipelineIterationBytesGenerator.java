package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.MacDerivationFunction;
import org.bouncycastle.crypto.params.KDFDoublePipelineIterationParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class KDFDoublePipelineIterationBytesGenerator implements MacDerivationFunction {
    private static final BigInteger INTEGER_MAX = BigInteger.valueOf(2147483647L);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    /* renamed from: a */
    private byte[] f452a;
    private byte[] fixedInputData;
    private int generatedBytes;

    /* renamed from: h */
    private final int f453h;
    private byte[] ios;

    /* renamed from: k */
    private byte[] f454k;
    private int maxSizeExcl;
    private final Mac prf;
    private boolean useCounter;

    public KDFDoublePipelineIterationBytesGenerator(Mac mac) {
        this.prf = mac;
        int macSize = mac.getMacSize();
        this.f453h = macSize;
        this.f452a = new byte[macSize];
        this.f454k = new byte[macSize];
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004a, code lost:
        r2[r2.length - 3] = (byte) (r0 >>> 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0052, code lost:
        r2[r2.length - 2] = (byte) (r0 >>> 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x005a, code lost:
        r2[r2.length - 1] = (byte) r0;
        r5.prf.update(r2, 0, r2.length);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void generateNext() {
        /*
            r5 = this;
            int r0 = r5.generatedBytes
            r1 = 0
            if (r0 != 0) goto L_0x0015
            org.bouncycastle.crypto.Mac r0 = r5.prf
            byte[] r2 = r5.fixedInputData
            int r3 = r2.length
            r0.update(r2, r1, r3)
            org.bouncycastle.crypto.Mac r0 = r5.prf
            byte[] r2 = r5.f452a
            r0.doFinal(r2, r1)
            goto L_0x0024
        L_0x0015:
            org.bouncycastle.crypto.Mac r0 = r5.prf
            byte[] r2 = r5.f452a
            int r3 = r2.length
            r0.update(r2, r1, r3)
            org.bouncycastle.crypto.Mac r0 = r5.prf
            byte[] r2 = r5.f452a
            r0.doFinal(r2, r1)
        L_0x0024:
            org.bouncycastle.crypto.Mac r0 = r5.prf
            byte[] r2 = r5.f452a
            int r3 = r2.length
            r0.update(r2, r1, r3)
            boolean r0 = r5.useCounter
            if (r0 == 0) goto L_0x0066
            int r0 = r5.generatedBytes
            int r2 = r5.f453h
            int r0 = r0 / r2
            int r0 = r0 + 1
            byte[] r2 = r5.ios
            int r3 = r2.length
            switch(r3) {
                case 1: goto L_0x005a;
                case 2: goto L_0x0052;
                case 3: goto L_0x004a;
                case 4: goto L_0x0045;
                default: goto L_0x003d;
            }
        L_0x003d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Unsupported size of counter i"
            r0.<init>(r1)
            throw r0
        L_0x0045:
            int r3 = r0 >>> 24
            byte r3 = (byte) r3
            r2[r1] = r3
        L_0x004a:
            int r3 = r2.length
            int r3 = r3 + -3
            int r4 = r0 >>> 16
            byte r4 = (byte) r4
            r2[r3] = r4
        L_0x0052:
            int r3 = r2.length
            int r3 = r3 + -2
            int r4 = r0 >>> 8
            byte r4 = (byte) r4
            r2[r3] = r4
        L_0x005a:
            int r3 = r2.length
            int r3 = r3 + -1
            byte r0 = (byte) r0
            r2[r3] = r0
            org.bouncycastle.crypto.Mac r0 = r5.prf
            int r3 = r2.length
            r0.update(r2, r1, r3)
        L_0x0066:
            org.bouncycastle.crypto.Mac r0 = r5.prf
            byte[] r2 = r5.fixedInputData
            int r3 = r2.length
            r0.update(r2, r1, r3)
            org.bouncycastle.crypto.Mac r0 = r5.prf
            byte[] r2 = r5.f454k
            r0.doFinal(r2, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.generators.KDFDoublePipelineIterationBytesGenerator.generateNext():void");
    }

    public int generateBytes(byte[] bArr, int i, int i2) throws DataLengthException, IllegalArgumentException {
        int i3 = this.generatedBytes;
        int i4 = i3 + i2;
        if (i4 < 0 || i4 >= this.maxSizeExcl) {
            throw new DataLengthException("Current KDFCTR may only be used for " + this.maxSizeExcl + " bytes");
        }
        if (i3 % this.f453h == 0) {
            generateNext();
        }
        int i5 = this.generatedBytes;
        int i6 = this.f453h;
        int i7 = i5 % i6;
        int min = Math.min(i6 - (i5 % i6), i2);
        System.arraycopy(this.f454k, i7, bArr, i, min);
        this.generatedBytes += min;
        int i8 = i2 - min;
        while (true) {
            i += min;
            if (i8 <= 0) {
                return i2;
            }
            generateNext();
            min = Math.min(this.f453h, i8);
            System.arraycopy(this.f454k, 0, bArr, i, min);
            this.generatedBytes += min;
            i8 -= min;
        }
    }

    public Mac getMac() {
        return this.prf;
    }

    public void init(DerivationParameters derivationParameters) {
        if (derivationParameters instanceof KDFDoublePipelineIterationParameters) {
            KDFDoublePipelineIterationParameters kDFDoublePipelineIterationParameters = (KDFDoublePipelineIterationParameters) derivationParameters;
            this.prf.init(new KeyParameter(kDFDoublePipelineIterationParameters.getKI()));
            this.fixedInputData = kDFDoublePipelineIterationParameters.getFixedInputData();
            int r = kDFDoublePipelineIterationParameters.getR();
            this.ios = new byte[(r / 8)];
            int i = Integer.MAX_VALUE;
            if (kDFDoublePipelineIterationParameters.useCounter()) {
                BigInteger multiply = TWO.pow(r).multiply(BigInteger.valueOf((long) this.f453h));
                if (multiply.compareTo(INTEGER_MAX) != 1) {
                    i = multiply.intValue();
                }
            }
            this.maxSizeExcl = i;
            this.useCounter = kDFDoublePipelineIterationParameters.useCounter();
            this.generatedBytes = 0;
            return;
        }
        throw new IllegalArgumentException("Wrong type of arguments given");
    }
}
