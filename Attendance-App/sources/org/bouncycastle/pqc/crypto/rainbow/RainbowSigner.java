package org.bouncycastle.pqc.crypto.rainbow;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.pqc.crypto.rainbow.util.ComputeInField;
import org.bouncycastle.pqc.crypto.rainbow.util.GF2Field;

public class RainbowSigner implements MessageSigner {
    private static final int MAXITS = 65536;

    /* renamed from: cf */
    private ComputeInField f937cf = new ComputeInField();
    RainbowKeyParameters key;
    private SecureRandom random;
    int signableDocumentLength;

    /* renamed from: x */
    private short[] f938x;

    private short[] initSign(Layer[] layerArr, short[] sArr) {
        short[] sArr2 = new short[sArr.length];
        short[] multiplyMatrix = this.f937cf.multiplyMatrix(((RainbowPrivateKeyParameters) this.key).getInvA1(), this.f937cf.addVect(((RainbowPrivateKeyParameters) this.key).getB1(), sArr));
        for (int i = 0; i < layerArr[0].getVi(); i++) {
            this.f938x[i] = (short) this.random.nextInt();
            short[] sArr3 = this.f938x;
            sArr3[i] = (short) (sArr3[i] & 255);
        }
        return multiplyMatrix;
    }

    private short[] makeMessageRepresentative(byte[] bArr) {
        int i = this.signableDocumentLength;
        short[] sArr = new short[i];
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length) {
            short s = (short) bArr[i3];
            sArr[i2] = s;
            sArr[i2] = (short) (s & 255);
            i3++;
            i2++;
            if (i2 >= i) {
                break;
            }
        }
        return sArr;
    }

    private short[] verifySignatureIntern(short[] sArr) {
        short[][] coeffQuadratic = ((RainbowPublicKeyParameters) this.key).getCoeffQuadratic();
        short[][] coeffSingular = ((RainbowPublicKeyParameters) this.key).getCoeffSingular();
        short[] coeffScalar = ((RainbowPublicKeyParameters) this.key).getCoeffScalar();
        short[] sArr2 = new short[coeffQuadratic.length];
        int length = coeffSingular[0].length;
        for (int i = 0; i < coeffQuadratic.length; i++) {
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                for (int i4 = i3; i4 < length; i4++) {
                    sArr2[i] = GF2Field.addElem(sArr2[i], GF2Field.multElem(coeffQuadratic[i][i2], GF2Field.multElem(sArr[i3], sArr[i4])));
                    i2++;
                }
                sArr2[i] = GF2Field.addElem(sArr2[i], GF2Field.multElem(coeffSingular[i][i3], sArr[i3]));
            }
            sArr2[i] = GF2Field.addElem(sArr2[i], coeffScalar[i]);
        }
        return sArr2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x00a0 A[Catch:{ Exception -> 0x00aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00b6 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:4:0x002e A[Catch:{ Exception -> 0x00aa }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] generateSignature(byte[] r15) {
        /*
            r14 = this;
            org.bouncycastle.pqc.crypto.rainbow.RainbowKeyParameters r0 = r14.key
            org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters r0 = (org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters) r0
            org.bouncycastle.pqc.crypto.rainbow.Layer[] r0 = r0.getLayers()
            int r1 = r0.length
            org.bouncycastle.pqc.crypto.rainbow.RainbowKeyParameters r2 = r14.key
            org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters r2 = (org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters) r2
            short[][] r2 = r2.getInvA2()
            int r2 = r2.length
            short[] r2 = new short[r2]
            r14.f938x = r2
            int r2 = r1 + -1
            r2 = r0[r2]
            int r2 = r2.getViNext()
            byte[] r3 = new byte[r2]
            short[] r15 = r14.makeMessageRepresentative(r15)
            r4 = 0
            r5 = 0
        L_0x0026:
            short[] r6 = r14.initSign(r0, r15)     // Catch:{ Exception -> 0x00aa }
            r7 = 0
            r8 = 0
        L_0x002c:
            if (r7 >= r1) goto L_0x007f
            r9 = r0[r7]     // Catch:{ Exception -> 0x00aa }
            int r9 = r9.getOi()     // Catch:{ Exception -> 0x00aa }
            short[] r9 = new short[r9]     // Catch:{ Exception -> 0x00aa }
            r10 = r0[r7]     // Catch:{ Exception -> 0x00aa }
            int r10 = r10.getOi()     // Catch:{ Exception -> 0x00aa }
            short[] r10 = new short[r10]     // Catch:{ Exception -> 0x00aa }
            r10 = 0
        L_0x003f:
            r11 = r0[r7]     // Catch:{ Exception -> 0x00aa }
            int r11 = r11.getOi()     // Catch:{ Exception -> 0x00aa }
            if (r10 >= r11) goto L_0x0050
            short r11 = r6[r8]     // Catch:{ Exception -> 0x00aa }
            r9[r10] = r11     // Catch:{ Exception -> 0x00aa }
            int r8 = r8 + 1
            int r10 = r10 + 1
            goto L_0x003f
        L_0x0050:
            org.bouncycastle.pqc.crypto.rainbow.util.ComputeInField r10 = r14.f937cf     // Catch:{ Exception -> 0x00aa }
            r11 = r0[r7]     // Catch:{ Exception -> 0x00aa }
            short[] r12 = r14.f938x     // Catch:{ Exception -> 0x00aa }
            short[][] r11 = r11.plugInVinegars(r12)     // Catch:{ Exception -> 0x00aa }
            short[] r9 = r10.solveEquation(r11, r9)     // Catch:{ Exception -> 0x00aa }
            if (r9 == 0) goto L_0x0077
            r10 = 0
        L_0x0061:
            int r11 = r9.length     // Catch:{ Exception -> 0x00aa }
            if (r10 >= r11) goto L_0x0074
            short[] r11 = r14.f938x     // Catch:{ Exception -> 0x00aa }
            r12 = r0[r7]     // Catch:{ Exception -> 0x00aa }
            int r12 = r12.getVi()     // Catch:{ Exception -> 0x00aa }
            int r12 = r12 + r10
            short r13 = r9[r10]     // Catch:{ Exception -> 0x00aa }
            r11[r12] = r13     // Catch:{ Exception -> 0x00aa }
            int r10 = r10 + 1
            goto L_0x0061
        L_0x0074:
            int r7 = r7 + 1
            goto L_0x002c
        L_0x0077:
            java.lang.Exception r6 = new java.lang.Exception     // Catch:{ Exception -> 0x00aa }
            java.lang.String r7 = "LES is not solveable!"
            r6.<init>(r7)     // Catch:{ Exception -> 0x00aa }
            throw r6     // Catch:{ Exception -> 0x00aa }
        L_0x007f:
            org.bouncycastle.pqc.crypto.rainbow.util.ComputeInField r6 = r14.f937cf     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.pqc.crypto.rainbow.RainbowKeyParameters r7 = r14.key     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters r7 = (org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters) r7     // Catch:{ Exception -> 0x00aa }
            short[] r7 = r7.getB2()     // Catch:{ Exception -> 0x00aa }
            short[] r8 = r14.f938x     // Catch:{ Exception -> 0x00aa }
            short[] r6 = r6.addVect(r7, r8)     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.pqc.crypto.rainbow.util.ComputeInField r7 = r14.f937cf     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.pqc.crypto.rainbow.RainbowKeyParameters r8 = r14.key     // Catch:{ Exception -> 0x00aa }
            org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters r8 = (org.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters) r8     // Catch:{ Exception -> 0x00aa }
            short[][] r8 = r8.getInvA2()     // Catch:{ Exception -> 0x00aa }
            short[] r6 = r7.multiplyMatrix((short[][]) r8, (short[]) r6)     // Catch:{ Exception -> 0x00aa }
            r7 = 0
        L_0x009e:
            if (r7 >= r2) goto L_0x00a8
            short r8 = r6[r7]     // Catch:{ Exception -> 0x00aa }
            byte r8 = (byte) r8     // Catch:{ Exception -> 0x00aa }
            r3[r7] = r8     // Catch:{ Exception -> 0x00aa }
            int r7 = r7 + 1
            goto L_0x009e
        L_0x00a8:
            r6 = 1
            goto L_0x00ac
        L_0x00aa:
            r6 = move-exception
            r6 = 0
        L_0x00ac:
            r7 = 65536(0x10000, float:9.18355E-41)
            if (r6 != 0) goto L_0x00b4
            int r5 = r5 + 1
            if (r5 < r7) goto L_0x0026
        L_0x00b4:
            if (r5 == r7) goto L_0x00b7
            return r3
        L_0x00b7:
            java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
            java.lang.String r0 = "unable to generate signature - LES not solvable"
            r15.<init>(r0)
            goto L_0x00c0
        L_0x00bf:
            throw r15
        L_0x00c0:
            goto L_0x00bf
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.pqc.crypto.rainbow.RainbowSigner.generateSignature(byte[]):byte[]");
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        RainbowKeyParameters rainbowKeyParameters;
        if (!z) {
            rainbowKeyParameters = (RainbowPublicKeyParameters) cipherParameters;
        } else if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.random = parametersWithRandom.getRandom();
            this.key = (RainbowPrivateKeyParameters) parametersWithRandom.getParameters();
            this.signableDocumentLength = this.key.getDocLength();
        } else {
            this.random = CryptoServicesRegistrar.getSecureRandom();
            rainbowKeyParameters = (RainbowPrivateKeyParameters) cipherParameters;
        }
        this.key = rainbowKeyParameters;
        this.signableDocumentLength = this.key.getDocLength();
    }

    public boolean verifySignature(byte[] bArr, byte[] bArr2) {
        short[] sArr = new short[bArr2.length];
        for (int i = 0; i < bArr2.length; i++) {
            sArr[i] = (short) (((short) bArr2[i]) & 255);
        }
        short[] makeMessageRepresentative = makeMessageRepresentative(bArr);
        short[] verifySignatureIntern = verifySignatureIntern(sArr);
        if (makeMessageRepresentative.length != verifySignatureIntern.length) {
            return false;
        }
        boolean z = true;
        for (int i2 = 0; i2 < makeMessageRepresentative.length; i2++) {
            z = z && makeMessageRepresentative[i2] == verifySignatureIntern[i2];
        }
        return z;
    }
}
