package org.bouncycastle.jcajce.provider.symmetric.util;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.cms.GCMParameters;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.engines.DSTU7624Engine;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.AEADCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.CTSBlockCipher;
import org.bouncycastle.crypto.modes.EAXBlockCipher;
import org.bouncycastle.crypto.modes.GCFBBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.GOFBBlockCipher;
import org.bouncycastle.crypto.modes.KCCMBlockCipher;
import org.bouncycastle.crypto.modes.KCTRBlockCipher;
import org.bouncycastle.crypto.modes.KGCMBlockCipher;
import org.bouncycastle.crypto.modes.OCBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.modes.OpenPGPCFBBlockCipher;
import org.bouncycastle.crypto.modes.PGPCFBBlockCipher;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.ISO10126d2Padding;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.TBCPadding;
import org.bouncycastle.crypto.paddings.X923Padding;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jcajce.spec.GOST28147ParameterSpec;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class BaseBlockCipher extends BaseWrapCipher implements PBE {
    private static final int BUF_SIZE = 512;
    private static final Class gcmSpecClass = ClassUtil.loadClass(BaseBlockCipher.class, "javax.crypto.spec.GCMParameterSpec");
    private AEADParameters aeadParams;
    private Class[] availableSpecs;
    private BlockCipher baseEngine;
    private GenericBlockCipher cipher;
    private int digest;
    private BlockCipherProvider engineProvider;
    private boolean fixedIv;
    private int ivLength;
    private ParametersWithIV ivParam;
    private int keySizeInBits;
    private String modeName;
    private boolean padded;
    private String pbeAlgorithm;
    private PBEParameterSpec pbeSpec;
    private int scheme;

    private static class AEADGenericBlockCipher implements GenericBlockCipher {
        private static final Constructor aeadBadTagConstructor;
        /* access modifiers changed from: private */
        public AEADCipher cipher;

        static {
            Class loadClass = ClassUtil.loadClass(BaseBlockCipher.class, "javax.crypto.AEADBadTagException");
            aeadBadTagConstructor = loadClass != null ? findExceptionConstructor(loadClass) : null;
        }

        AEADGenericBlockCipher(AEADCipher aEADCipher) {
            this.cipher = aEADCipher;
        }

        private static Constructor findExceptionConstructor(Class cls) {
            try {
                return cls.getConstructor(new Class[]{String.class});
            } catch (Exception e) {
                return null;
            }
        }

        public int doFinal(byte[] bArr, int i) throws IllegalStateException, BadPaddingException {
            BadPaddingException badPaddingException;
            try {
                return this.cipher.doFinal(bArr, i);
            } catch (InvalidCipherTextException e) {
                Constructor constructor = aeadBadTagConstructor;
                if (constructor != null) {
                    try {
                        badPaddingException = (BadPaddingException) constructor.newInstance(new Object[]{e.getMessage()});
                    } catch (Exception e2) {
                        badPaddingException = null;
                    }
                    if (badPaddingException != null) {
                        throw badPaddingException;
                    }
                }
                throw new BadPaddingException(e.getMessage());
            }
        }

        public String getAlgorithmName() {
            AEADCipher aEADCipher = this.cipher;
            return aEADCipher instanceof AEADBlockCipher ? ((AEADBlockCipher) aEADCipher).getUnderlyingCipher().getAlgorithmName() : aEADCipher.getAlgorithmName();
        }

        public int getOutputSize(int i) {
            return this.cipher.getOutputSize(i);
        }

        public BlockCipher getUnderlyingCipher() {
            AEADCipher aEADCipher = this.cipher;
            if (aEADCipher instanceof AEADBlockCipher) {
                return ((AEADBlockCipher) aEADCipher).getUnderlyingCipher();
            }
            return null;
        }

        public int getUpdateOutputSize(int i) {
            return this.cipher.getUpdateOutputSize(i);
        }

        public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
            this.cipher.init(z, cipherParameters);
        }

        public int processByte(byte b, byte[] bArr, int i) throws DataLengthException {
            return this.cipher.processByte(b, bArr, i);
        }

        public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
            return this.cipher.processBytes(bArr, i, i2, bArr2, i3);
        }

        public void updateAAD(byte[] bArr, int i, int i2) {
            this.cipher.processAADBytes(bArr, i, i2);
        }

        public boolean wrapOnNoPadding() {
            return false;
        }
    }

    private static class BufferedGenericBlockCipher implements GenericBlockCipher {
        private BufferedBlockCipher cipher;

        BufferedGenericBlockCipher(BlockCipher blockCipher) {
            this.cipher = new PaddedBufferedBlockCipher(blockCipher);
        }

        BufferedGenericBlockCipher(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
            this.cipher = new PaddedBufferedBlockCipher(blockCipher, blockCipherPadding);
        }

        BufferedGenericBlockCipher(BufferedBlockCipher bufferedBlockCipher) {
            this.cipher = bufferedBlockCipher;
        }

        public int doFinal(byte[] bArr, int i) throws IllegalStateException, BadPaddingException {
            try {
                return this.cipher.doFinal(bArr, i);
            } catch (InvalidCipherTextException e) {
                throw new BadPaddingException(e.getMessage());
            }
        }

        public String getAlgorithmName() {
            return this.cipher.getUnderlyingCipher().getAlgorithmName();
        }

        public int getOutputSize(int i) {
            return this.cipher.getOutputSize(i);
        }

        public BlockCipher getUnderlyingCipher() {
            return this.cipher.getUnderlyingCipher();
        }

        public int getUpdateOutputSize(int i) {
            return this.cipher.getUpdateOutputSize(i);
        }

        public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
            this.cipher.init(z, cipherParameters);
        }

        public int processByte(byte b, byte[] bArr, int i) throws DataLengthException {
            return this.cipher.processByte(b, bArr, i);
        }

        public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
            return this.cipher.processBytes(bArr, i, i2, bArr2, i3);
        }

        public void updateAAD(byte[] bArr, int i, int i2) {
            throw new UnsupportedOperationException("AAD is not supported in the current mode.");
        }

        public boolean wrapOnNoPadding() {
            return !(this.cipher instanceof CTSBlockCipher);
        }
    }

    private interface GenericBlockCipher {
        int doFinal(byte[] bArr, int i) throws IllegalStateException, BadPaddingException;

        String getAlgorithmName();

        int getOutputSize(int i);

        BlockCipher getUnderlyingCipher();

        int getUpdateOutputSize(int i);

        void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException;

        int processByte(byte b, byte[] bArr, int i) throws DataLengthException;

        int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException;

        void updateAAD(byte[] bArr, int i, int i2);

        boolean wrapOnNoPadding();
    }

    protected BaseBlockCipher(BlockCipher blockCipher) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
    }

    protected BaseBlockCipher(BlockCipher blockCipher, int i) {
        this(blockCipher, true, i);
    }

    protected BaseBlockCipher(BlockCipher blockCipher, int i, int i2, int i3, int i4) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.scheme = i;
        this.digest = i2;
        this.keySizeInBits = i3;
        this.ivLength = i4;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
    }

    protected BaseBlockCipher(BlockCipher blockCipher, boolean z, int i) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.fixedIv = z;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
        this.ivLength = i / 8;
    }

    protected BaseBlockCipher(BufferedBlockCipher bufferedBlockCipher, int i) {
        this(bufferedBlockCipher, true, i);
    }

    protected BaseBlockCipher(BufferedBlockCipher bufferedBlockCipher, boolean z, int i) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = bufferedBlockCipher.getUnderlyingCipher();
        this.cipher = new BufferedGenericBlockCipher(bufferedBlockCipher);
        this.fixedIv = z;
        this.ivLength = i / 8;
    }

    protected BaseBlockCipher(AEADBlockCipher aEADBlockCipher) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        BlockCipher underlyingCipher = aEADBlockCipher.getUnderlyingCipher();
        this.baseEngine = underlyingCipher;
        this.ivLength = underlyingCipher.getBlockSize();
        this.cipher = new AEADGenericBlockCipher(aEADBlockCipher);
    }

    protected BaseBlockCipher(AEADBlockCipher aEADBlockCipher, boolean z, int i) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = aEADBlockCipher.getUnderlyingCipher();
        this.fixedIv = z;
        this.ivLength = i;
        this.cipher = new AEADGenericBlockCipher(aEADBlockCipher);
    }

    protected BaseBlockCipher(AEADCipher aEADCipher, boolean z, int i) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = null;
        this.fixedIv = z;
        this.ivLength = i;
        this.cipher = new AEADGenericBlockCipher(aEADCipher);
    }

    protected BaseBlockCipher(BlockCipherProvider blockCipherProvider) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, gcmSpecClass, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipherProvider.get();
        this.engineProvider = blockCipherProvider;
        this.cipher = new BufferedGenericBlockCipher(blockCipherProvider.get());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: org.bouncycastle.crypto.params.ParametersWithSBox} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: org.bouncycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: org.bouncycastle.crypto.params.ParametersWithSBox} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: org.bouncycastle.crypto.params.ParametersWithSBox} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: org.bouncycastle.crypto.params.ParametersWithSBox} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.bouncycastle.crypto.CipherParameters adjustParameters(java.security.spec.AlgorithmParameterSpec r4, org.bouncycastle.crypto.CipherParameters r5) {
        /*
            r3 = this;
            boolean r0 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r0 == 0) goto L_0x0042
            r0 = r5
            org.bouncycastle.crypto.params.ParametersWithIV r0 = (org.bouncycastle.crypto.params.ParametersWithIV) r0
            org.bouncycastle.crypto.CipherParameters r0 = r0.getParameters()
            boolean r1 = r4 instanceof javax.crypto.spec.IvParameterSpec
            if (r1 == 0) goto L_0x001d
            javax.crypto.spec.IvParameterSpec r4 = (javax.crypto.spec.IvParameterSpec) r4
            org.bouncycastle.crypto.params.ParametersWithIV r5 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r4 = r4.getIV()
            r5.<init>(r0, r4)
        L_0x001a:
            r3.ivParam = r5
            goto L_0x0077
        L_0x001d:
            boolean r1 = r4 instanceof org.bouncycastle.jcajce.spec.GOST28147ParameterSpec
            if (r1 == 0) goto L_0x0077
            org.bouncycastle.jcajce.spec.GOST28147ParameterSpec r4 = (org.bouncycastle.jcajce.spec.GOST28147ParameterSpec) r4
            org.bouncycastle.crypto.params.ParametersWithSBox r1 = new org.bouncycastle.crypto.params.ParametersWithSBox
            byte[] r2 = r4.getSbox()
            r1.<init>(r5, r2)
            byte[] r5 = r4.getIV()
            if (r5 == 0) goto L_0x0040
            int r5 = r3.ivLength
            if (r5 == 0) goto L_0x0040
            org.bouncycastle.crypto.params.ParametersWithIV r5 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r4 = r4.getIV()
            r5.<init>(r0, r4)
            goto L_0x001a
        L_0x0040:
            r5 = r1
            goto L_0x0077
        L_0x0042:
            boolean r0 = r4 instanceof javax.crypto.spec.IvParameterSpec
            if (r0 == 0) goto L_0x0055
            javax.crypto.spec.IvParameterSpec r4 = (javax.crypto.spec.IvParameterSpec) r4
            org.bouncycastle.crypto.params.ParametersWithIV r0 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r4 = r4.getIV()
            r0.<init>(r5, r4)
            r3.ivParam = r0
        L_0x0053:
            r5 = r0
            goto L_0x0077
        L_0x0055:
            boolean r0 = r4 instanceof org.bouncycastle.jcajce.spec.GOST28147ParameterSpec
            if (r0 == 0) goto L_0x0077
            org.bouncycastle.jcajce.spec.GOST28147ParameterSpec r4 = (org.bouncycastle.jcajce.spec.GOST28147ParameterSpec) r4
            org.bouncycastle.crypto.params.ParametersWithSBox r0 = new org.bouncycastle.crypto.params.ParametersWithSBox
            byte[] r1 = r4.getSbox()
            r0.<init>(r5, r1)
            byte[] r5 = r4.getIV()
            if (r5 == 0) goto L_0x0053
            int r5 = r3.ivLength
            if (r5 == 0) goto L_0x0053
            org.bouncycastle.crypto.params.ParametersWithIV r5 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r4 = r4.getIV()
            r5.<init>(r0, r4)
        L_0x0077:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.adjustParameters(java.security.spec.AlgorithmParameterSpec, org.bouncycastle.crypto.CipherParameters):org.bouncycastle.crypto.CipherParameters");
    }

    private boolean isAEADModeName(String str) {
        return "CCM".equals(str) || "EAX".equals(str) || "GCM".equals(str) || "OCB".equals(str);
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
        int i4;
        if (engineGetOutputSize(i2) + i3 <= bArr2.length) {
            if (i2 != 0) {
                try {
                    i4 = this.cipher.processBytes(bArr, i, i2, bArr2, i3);
                } catch (OutputLengthException e) {
                    throw new IllegalBlockSizeException(e.getMessage());
                } catch (DataLengthException e2) {
                    throw new IllegalBlockSizeException(e2.getMessage());
                }
            } else {
                i4 = 0;
            }
            return i4 + this.cipher.doFinal(bArr2, i3 + i4);
        }
        throw new ShortBufferException("output buffer too short for input.");
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) throws IllegalBlockSizeException, BadPaddingException {
        int engineGetOutputSize = engineGetOutputSize(i2);
        byte[] bArr2 = new byte[engineGetOutputSize];
        int processBytes = i2 != 0 ? this.cipher.processBytes(bArr, i, i2, bArr2, 0) : 0;
        try {
            int doFinal = processBytes + this.cipher.doFinal(bArr2, processBytes);
            if (doFinal == engineGetOutputSize) {
                return bArr2;
            }
            byte[] bArr3 = new byte[doFinal];
            System.arraycopy(bArr2, 0, bArr3, 0, doFinal);
            return bArr3;
        } catch (DataLengthException e) {
            throw new IllegalBlockSizeException(e.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        BlockCipher blockCipher = this.baseEngine;
        if (blockCipher == null) {
            return -1;
        }
        return blockCipher.getBlockSize();
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        AEADParameters aEADParameters = this.aeadParams;
        if (aEADParameters != null) {
            return aEADParameters.getNonce();
        }
        ParametersWithIV parametersWithIV = this.ivParam;
        if (parametersWithIV != null) {
            return parametersWithIV.getIV();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int i) {
        return this.cipher.getOutputSize(i);
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null) {
            if (this.pbeSpec != null) {
                try {
                    this.engineParams = createParametersInstance(this.pbeAlgorithm);
                    this.engineParams.init(this.pbeSpec);
                } catch (Exception e) {
                    return null;
                }
            } else if (this.aeadParams != null) {
                if (this.baseEngine == null) {
                    try {
                        this.engineParams = createParametersInstance(PKCSObjectIdentifiers.id_alg_AEADChaCha20Poly1305.getId());
                        this.engineParams.init(new DEROctetString(this.aeadParams.getNonce()).getEncoded());
                    } catch (Exception e2) {
                        throw new RuntimeException(e2.toString());
                    }
                } else {
                    try {
                        this.engineParams = createParametersInstance("GCM");
                        this.engineParams.init(new GCMParameters(this.aeadParams.getNonce(), this.aeadParams.getMacSize() / 8).getEncoded());
                    } catch (Exception e3) {
                        throw new RuntimeException(e3.toString());
                    }
                }
            } else if (this.ivParam != null) {
                String algorithmName = this.cipher.getUnderlyingCipher().getAlgorithmName();
                if (algorithmName.indexOf(47) >= 0) {
                    algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
                }
                try {
                    this.engineParams = createParametersInstance(algorithmName);
                    this.engineParams.init(new IvParameterSpec(this.ivParam.getIV()));
                } catch (Exception e4) {
                    throw new RuntimeException(e4.toString());
                }
            }
        }
        return this.engineParams;
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec algorithmParameterSpec = null;
        if (algorithmParameters != null) {
            int i2 = 0;
            while (true) {
                Class[] clsArr = this.availableSpecs;
                if (i2 == clsArr.length) {
                    break;
                }
                Class cls = clsArr[i2];
                if (cls != null) {
                    try {
                        algorithmParameterSpec = algorithmParameters.getParameterSpec(cls);
                        break;
                    } catch (Exception e) {
                    }
                }
                i2++;
            }
            if (algorithmParameterSpec == null) {
                throw new InvalidAlgorithmParameterException("can't handle parameter " + algorithmParameters.toString());
            }
        }
        engineInit(i, key, algorithmParameterSpec, secureRandom);
        this.engineParams = algorithmParameters;
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            AlgorithmParameterSpec algorithmParameterSpec = null;
            engineInit(i, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(e.getMessage());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: org.bouncycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: org.bouncycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: org.bouncycastle.crypto.params.ParametersWithRandom} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: org.bouncycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: org.bouncycastle.crypto.params.AEADParameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v18, resolved type: org.bouncycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v56, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v58, resolved type: org.bouncycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v59, resolved type: org.bouncycastle.crypto.params.ParametersWithRandom} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v60, resolved type: org.bouncycastle.crypto.params.ParametersWithRandom} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v61, resolved type: org.bouncycastle.crypto.params.ParametersWithRandom} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v62, resolved type: org.bouncycastle.crypto.CipherParameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v63, resolved type: org.bouncycastle.crypto.params.RC5Parameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v64, resolved type: org.bouncycastle.crypto.params.RC5Parameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v65, resolved type: org.bouncycastle.crypto.params.RC2Parameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v66, resolved type: org.bouncycastle.crypto.params.RC2Parameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v67, resolved type: org.bouncycastle.crypto.params.ParametersWithSBox} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v68, resolved type: org.bouncycastle.crypto.params.ParametersWithSBox} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v69, resolved type: org.bouncycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v70, resolved type: org.bouncycastle.crypto.params.AEADParameters} */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1, types: [org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r5v2, types: [org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r5v71 */
    /* JADX WARNING: type inference failed for: r5v74 */
    /* JADX WARNING: type inference failed for: r5v75 */
    /* JADX WARNING: type inference failed for: r5v76 */
    /* JADX WARNING: type inference failed for: r5v78 */
    /* JADX WARNING: type inference failed for: r5v83 */
    /* JADX WARNING: type inference failed for: r5v84 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ac, code lost:
        if (r7 != false) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f9, code lost:
        if (r7 != false) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0143, code lost:
        if (r7 != false) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01fc, code lost:
        if (r7 != false) goto L_0x00ae;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0205  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0244  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x047c  */
    /* JADX WARNING: Removed duplicated region for block: B:229:0x04ce A[SYNTHETIC, Splitter:B:229:0x04ce] */
    /* JADX WARNING: Removed duplicated region for block: B:231:0x04d1 A[Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }] */
    /* JADX WARNING: Removed duplicated region for block: B:232:0x04d7 A[Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }] */
    /* JADX WARNING: Removed duplicated region for block: B:237:0x04e6 A[Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }] */
    /* JADX WARNING: Removed duplicated region for block: B:253:? A[Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineInit(int r19, java.security.Key r20, java.security.spec.AlgorithmParameterSpec r21, java.security.SecureRandom r22) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        /*
            r18 = this;
            r1 = r18
            r0 = r19
            r2 = r20
            r3 = r21
            r4 = r22
            r5 = 0
            r1.pbeSpec = r5
            r1.pbeAlgorithm = r5
            r1.engineParams = r5
            r1.aeadParams = r5
            boolean r6 = r2 instanceof javax.crypto.SecretKey
            if (r6 != 0) goto L_0x003c
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Key for algorithm "
            java.lang.StringBuilder r3 = r3.append(r4)
            if (r2 == 0) goto L_0x002a
            java.lang.String r5 = r20.getAlgorithm()
        L_0x002a:
            java.lang.StringBuilder r2 = r3.append(r5)
            java.lang.String r3 = " not suitable for symmetric enryption."
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x003c:
            java.lang.String r6 = "RC5-64"
            if (r3 != 0) goto L_0x0057
            org.bouncycastle.crypto.BlockCipher r7 = r1.baseEngine
            if (r7 == 0) goto L_0x0057
            java.lang.String r7 = r7.getAlgorithmName()
            boolean r7 = r7.startsWith(r6)
            if (r7 != 0) goto L_0x004f
            goto L_0x0057
        L_0x004f:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "RC5 requires an RC5ParametersSpec to be passed in."
            r0.<init>(r2)
            throw r0
        L_0x0057:
            int r7 = r1.scheme
            r8 = 2
            java.lang.String r9 = "Algorithm requires a PBE key"
            r10 = 1
            if (r7 == r8) goto L_0x0166
            boolean r8 = r2 instanceof org.bouncycastle.jcajce.PKCS12Key
            if (r8 == 0) goto L_0x0065
            goto L_0x0166
        L_0x0065:
            boolean r8 = r2 instanceof org.bouncycastle.jcajce.PBKDF1Key
            if (r8 == 0) goto L_0x00b5
            r5 = r2
            org.bouncycastle.jcajce.PBKDF1Key r5 = (org.bouncycastle.jcajce.PBKDF1Key) r5
            boolean r7 = r3 instanceof javax.crypto.spec.PBEParameterSpec
            if (r7 == 0) goto L_0x0075
            r7 = r3
            javax.crypto.spec.PBEParameterSpec r7 = (javax.crypto.spec.PBEParameterSpec) r7
            r1.pbeSpec = r7
        L_0x0075:
            boolean r7 = r5 instanceof org.bouncycastle.jcajce.PBKDF1KeyWithParameters
            if (r7 == 0) goto L_0x008f
            javax.crypto.spec.PBEParameterSpec r7 = r1.pbeSpec
            if (r7 != 0) goto L_0x008f
            javax.crypto.spec.PBEParameterSpec r7 = new javax.crypto.spec.PBEParameterSpec
            r8 = r5
            org.bouncycastle.jcajce.PBKDF1KeyWithParameters r8 = (org.bouncycastle.jcajce.PBKDF1KeyWithParameters) r8
            byte[] r9 = r8.getSalt()
            int r8 = r8.getIterationCount()
            r7.<init>(r9, r8)
            r1.pbeSpec = r7
        L_0x008f:
            byte[] r11 = r5.getEncoded()
            r12 = 0
            int r13 = r1.digest
            int r14 = r1.keySizeInBits
            int r5 = r1.ivLength
            int r15 = r5 * 8
            javax.crypto.spec.PBEParameterSpec r5 = r1.pbeSpec
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r7 = r1.cipher
            java.lang.String r17 = r7.getAlgorithmName()
            r16 = r5
            org.bouncycastle.crypto.CipherParameters r5 = org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(r11, r12, r13, r14, r15, r16, r17)
            boolean r7 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r7 == 0) goto L_0x0200
        L_0x00ae:
            r7 = r5
            org.bouncycastle.crypto.params.ParametersWithIV r7 = (org.bouncycastle.crypto.params.ParametersWithIV) r7
            r1.ivParam = r7
            goto L_0x0200
        L_0x00b5:
            boolean r8 = r2 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey
            if (r8 == 0) goto L_0x0104
            r5 = r2
            org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey r5 = (org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) r5
            org.bouncycastle.asn1.ASN1ObjectIdentifier r7 = r5.getOID()
            if (r7 == 0) goto L_0x00cb
            org.bouncycastle.asn1.ASN1ObjectIdentifier r7 = r5.getOID()
            java.lang.String r7 = r7.getId()
            goto L_0x00cf
        L_0x00cb:
            java.lang.String r7 = r5.getAlgorithm()
        L_0x00cf:
            r1.pbeAlgorithm = r7
            org.bouncycastle.crypto.CipherParameters r7 = r5.getParam()
            if (r7 == 0) goto L_0x00e0
            org.bouncycastle.crypto.CipherParameters r5 = r5.getParam()
            org.bouncycastle.crypto.CipherParameters r5 = r1.adjustParameters(r3, r5)
            goto L_0x00f7
        L_0x00e0:
            boolean r7 = r3 instanceof javax.crypto.spec.PBEParameterSpec
            if (r7 == 0) goto L_0x00fc
            r7 = r3
            javax.crypto.spec.PBEParameterSpec r7 = (javax.crypto.spec.PBEParameterSpec) r7
            r1.pbeSpec = r7
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r7 = r1.cipher
            org.bouncycastle.crypto.BlockCipher r7 = r7.getUnderlyingCipher()
            java.lang.String r7 = r7.getAlgorithmName()
            org.bouncycastle.crypto.CipherParameters r5 = org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(r5, r3, r7)
        L_0x00f7:
            boolean r7 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r7 == 0) goto L_0x0200
            goto L_0x00ae
        L_0x00fc:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "PBE requires PBE parameters to be set."
            r0.<init>(r2)
            throw r0
        L_0x0104:
            boolean r8 = r2 instanceof javax.crypto.interfaces.PBEKey
            if (r8 == 0) goto L_0x0147
            r5 = r2
            javax.crypto.interfaces.PBEKey r5 = (javax.crypto.interfaces.PBEKey) r5
            r7 = r3
            javax.crypto.spec.PBEParameterSpec r7 = (javax.crypto.spec.PBEParameterSpec) r7
            r1.pbeSpec = r7
            boolean r8 = r5 instanceof org.bouncycastle.jcajce.PKCS12KeyWithParameters
            if (r8 == 0) goto L_0x0125
            if (r7 != 0) goto L_0x0125
            javax.crypto.spec.PBEParameterSpec r7 = new javax.crypto.spec.PBEParameterSpec
            byte[] r8 = r5.getSalt()
            int r9 = r5.getIterationCount()
            r7.<init>(r8, r9)
            r1.pbeSpec = r7
        L_0x0125:
            byte[] r11 = r5.getEncoded()
            int r12 = r1.scheme
            int r13 = r1.digest
            int r14 = r1.keySizeInBits
            int r5 = r1.ivLength
            int r15 = r5 * 8
            javax.crypto.spec.PBEParameterSpec r5 = r1.pbeSpec
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r7 = r1.cipher
            java.lang.String r17 = r7.getAlgorithmName()
            r16 = r5
            org.bouncycastle.crypto.CipherParameters r5 = org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(r11, r12, r13, r14, r15, r16, r17)
            boolean r7 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r7 == 0) goto L_0x0200
            goto L_0x00ae
        L_0x0147:
            boolean r8 = r2 instanceof org.bouncycastle.jcajce.spec.RepeatedSecretKeySpec
            if (r8 != 0) goto L_0x0200
            if (r7 == 0) goto L_0x0160
            r5 = 4
            if (r7 == r5) goto L_0x0160
            if (r7 == r10) goto L_0x0160
            r5 = 5
            if (r7 == r5) goto L_0x0160
            org.bouncycastle.crypto.params.KeyParameter r5 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r7 = r20.getEncoded()
            r5.<init>(r7)
            goto L_0x0200
        L_0x0160:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            r0.<init>(r9)
            throw r0
        L_0x0166:
            r5 = r2
            javax.crypto.SecretKey r5 = (javax.crypto.SecretKey) r5     // Catch:{ Exception -> 0x053e }
            boolean r7 = r3 instanceof javax.crypto.spec.PBEParameterSpec
            if (r7 == 0) goto L_0x0172
            r7 = r3
            javax.crypto.spec.PBEParameterSpec r7 = (javax.crypto.spec.PBEParameterSpec) r7
            r1.pbeSpec = r7
        L_0x0172:
            boolean r7 = r5 instanceof javax.crypto.interfaces.PBEKey
            if (r7 == 0) goto L_0x019b
            javax.crypto.spec.PBEParameterSpec r8 = r1.pbeSpec
            if (r8 != 0) goto L_0x019b
            r8 = r5
            javax.crypto.interfaces.PBEKey r8 = (javax.crypto.interfaces.PBEKey) r8
            byte[] r11 = r8.getSalt()
            if (r11 == 0) goto L_0x0193
            javax.crypto.spec.PBEParameterSpec r11 = new javax.crypto.spec.PBEParameterSpec
            byte[] r12 = r8.getSalt()
            int r8 = r8.getIterationCount()
            r11.<init>(r12, r8)
            r1.pbeSpec = r11
            goto L_0x019b
        L_0x0193:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "PBEKey requires parameters to specify salt"
            r0.<init>(r2)
            throw r0
        L_0x019b:
            javax.crypto.spec.PBEParameterSpec r8 = r1.pbeSpec
            if (r8 != 0) goto L_0x01a8
            if (r7 == 0) goto L_0x01a2
            goto L_0x01a8
        L_0x01a2:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            r0.<init>(r9)
            throw r0
        L_0x01a8:
            boolean r7 = r2 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey
            if (r7 == 0) goto L_0x01df
            r7 = r2
            org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey r7 = (org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) r7
            org.bouncycastle.crypto.CipherParameters r7 = r7.getParam()
            boolean r8 = r7 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r8 == 0) goto L_0x01b8
            goto L_0x01d5
        L_0x01b8:
            if (r7 != 0) goto L_0x01d7
            byte[] r11 = r5.getEncoded()
            r12 = 2
            int r13 = r1.digest
            int r14 = r1.keySizeInBits
            int r5 = r1.ivLength
            int r15 = r5 * 8
            javax.crypto.spec.PBEParameterSpec r5 = r1.pbeSpec
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r7 = r1.cipher
            java.lang.String r17 = r7.getAlgorithmName()
            r16 = r5
            org.bouncycastle.crypto.CipherParameters r7 = org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(r11, r12, r13, r14, r15, r16, r17)
        L_0x01d5:
            r5 = r7
            goto L_0x01fa
        L_0x01d7:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            java.lang.String r2 = "Algorithm requires a PBE key suitable for PKCS12"
            r0.<init>(r2)
            throw r0
        L_0x01df:
            byte[] r11 = r5.getEncoded()
            r12 = 2
            int r13 = r1.digest
            int r14 = r1.keySizeInBits
            int r5 = r1.ivLength
            int r15 = r5 * 8
            javax.crypto.spec.PBEParameterSpec r5 = r1.pbeSpec
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r7 = r1.cipher
            java.lang.String r17 = r7.getAlgorithmName()
            r16 = r5
            org.bouncycastle.crypto.CipherParameters r5 = org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(r11, r12, r13, r14, r15, r16, r17)
        L_0x01fa:
            boolean r7 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r7 == 0) goto L_0x0200
            goto L_0x00ae
        L_0x0200:
            boolean r7 = r3 instanceof org.bouncycastle.jcajce.spec.AEADParameterSpec
            r8 = 0
            if (r7 == 0) goto L_0x0244
            java.lang.String r2 = r1.modeName
            boolean r2 = r1.isAEADModeName(r2)
            if (r2 != 0) goto L_0x021c
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r2 = r1.cipher
            boolean r2 = r2 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher
            if (r2 == 0) goto L_0x0214
            goto L_0x021c
        L_0x0214:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "AEADParameterSpec can only be used with AEAD modes."
            r0.<init>(r2)
            throw r0
        L_0x021c:
            r2 = r3
            org.bouncycastle.jcajce.spec.AEADParameterSpec r2 = (org.bouncycastle.jcajce.spec.AEADParameterSpec) r2
            boolean r3 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r3 == 0) goto L_0x022c
            org.bouncycastle.crypto.params.ParametersWithIV r5 = (org.bouncycastle.crypto.params.ParametersWithIV) r5
            org.bouncycastle.crypto.CipherParameters r3 = r5.getParameters()
            org.bouncycastle.crypto.params.KeyParameter r3 = (org.bouncycastle.crypto.params.KeyParameter) r3
            goto L_0x022f
        L_0x022c:
            r3 = r5
            org.bouncycastle.crypto.params.KeyParameter r3 = (org.bouncycastle.crypto.params.KeyParameter) r3
        L_0x022f:
            org.bouncycastle.crypto.params.AEADParameters r5 = new org.bouncycastle.crypto.params.AEADParameters
            int r6 = r2.getMacSizeInBits()
            byte[] r7 = r2.getNonce()
            byte[] r2 = r2.getAssociatedData()
            r5.<init>(r3, r6, r7, r2)
            r1.aeadParams = r5
            goto L_0x0478
        L_0x0244:
            boolean r7 = r3 instanceof javax.crypto.spec.IvParameterSpec
            if (r7 == 0) goto L_0x02bf
            int r2 = r1.ivLength
            if (r2 == 0) goto L_0x02a9
            r2 = r3
            javax.crypto.spec.IvParameterSpec r2 = (javax.crypto.spec.IvParameterSpec) r2
            byte[] r3 = r2.getIV()
            int r3 = r3.length
            int r6 = r1.ivLength
            if (r3 == r6) goto L_0x0284
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r3 = r1.cipher
            boolean r3 = r3 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher
            if (r3 != 0) goto L_0x0284
            boolean r3 = r1.fixedIv
            if (r3 != 0) goto L_0x0263
            goto L_0x0284
        L_0x0263:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "IV must be "
            java.lang.StringBuilder r2 = r2.append(r3)
            int r3 = r1.ivLength
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " bytes long."
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x0284:
            boolean r3 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r3 == 0) goto L_0x0298
            org.bouncycastle.crypto.params.ParametersWithIV r3 = new org.bouncycastle.crypto.params.ParametersWithIV
            org.bouncycastle.crypto.params.ParametersWithIV r5 = (org.bouncycastle.crypto.params.ParametersWithIV) r5
            org.bouncycastle.crypto.CipherParameters r5 = r5.getParameters()
            byte[] r2 = r2.getIV()
            r3.<init>(r5, r2)
            goto L_0x02a1
        L_0x0298:
            org.bouncycastle.crypto.params.ParametersWithIV r3 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r2 = r2.getIV()
            r3.<init>(r5, r2)
        L_0x02a1:
            r5 = r3
            r2 = r5
            org.bouncycastle.crypto.params.ParametersWithIV r2 = (org.bouncycastle.crypto.params.ParametersWithIV) r2
            r1.ivParam = r5
            goto L_0x0478
        L_0x02a9:
            java.lang.String r2 = r1.modeName
            if (r2 == 0) goto L_0x0478
            java.lang.String r3 = "ECB"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x02b7
            goto L_0x0478
        L_0x02b7:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "ECB mode does not use an IV"
            r0.<init>(r2)
            throw r0
        L_0x02bf:
            boolean r7 = r3 instanceof org.bouncycastle.jcajce.spec.GOST28147ParameterSpec
            if (r7 == 0) goto L_0x0306
            org.bouncycastle.jcajce.spec.GOST28147ParameterSpec r3 = (org.bouncycastle.jcajce.spec.GOST28147ParameterSpec) r3
            org.bouncycastle.crypto.params.ParametersWithSBox r5 = new org.bouncycastle.crypto.params.ParametersWithSBox
            org.bouncycastle.crypto.params.KeyParameter r6 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r2 = r20.getEncoded()
            r6.<init>(r2)
            byte[] r2 = r3.getSbox()
            r5.<init>(r6, r2)
            byte[] r2 = r3.getIV()
            if (r2 == 0) goto L_0x0478
            int r2 = r1.ivLength
            if (r2 == 0) goto L_0x0478
            boolean r2 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r2 == 0) goto L_0x02f5
            org.bouncycastle.crypto.params.ParametersWithIV r2 = new org.bouncycastle.crypto.params.ParametersWithIV
            org.bouncycastle.crypto.params.ParametersWithIV r5 = (org.bouncycastle.crypto.params.ParametersWithIV) r5
            org.bouncycastle.crypto.CipherParameters r5 = r5.getParameters()
            byte[] r3 = r3.getIV()
            r2.<init>(r5, r3)
            goto L_0x02fe
        L_0x02f5:
            org.bouncycastle.crypto.params.ParametersWithIV r2 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r3 = r3.getIV()
            r2.<init>(r5, r3)
        L_0x02fe:
            r3 = r2
            org.bouncycastle.crypto.params.ParametersWithIV r3 = (org.bouncycastle.crypto.params.ParametersWithIV) r3
            r1.ivParam = r2
            r5 = r2
            goto L_0x0478
        L_0x0306:
            boolean r7 = r3 instanceof javax.crypto.spec.RC2ParameterSpec
            if (r7 == 0) goto L_0x0341
            javax.crypto.spec.RC2ParameterSpec r3 = (javax.crypto.spec.RC2ParameterSpec) r3
            org.bouncycastle.crypto.params.RC2Parameters r5 = new org.bouncycastle.crypto.params.RC2Parameters
            byte[] r2 = r20.getEncoded()
            int r6 = r3.getEffectiveKeyBits()
            r5.<init>(r2, r6)
            byte[] r2 = r3.getIV()
            if (r2 == 0) goto L_0x0478
            int r2 = r1.ivLength
            if (r2 == 0) goto L_0x0478
            boolean r2 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r2 == 0) goto L_0x0337
            org.bouncycastle.crypto.params.ParametersWithIV r2 = new org.bouncycastle.crypto.params.ParametersWithIV
            org.bouncycastle.crypto.params.ParametersWithIV r5 = (org.bouncycastle.crypto.params.ParametersWithIV) r5
            org.bouncycastle.crypto.CipherParameters r5 = r5.getParameters()
            byte[] r3 = r3.getIV()
            r2.<init>(r5, r3)
            goto L_0x02fe
        L_0x0337:
            org.bouncycastle.crypto.params.ParametersWithIV r2 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r3 = r3.getIV()
            r2.<init>(r5, r3)
            goto L_0x02fe
        L_0x0341:
            boolean r7 = r3 instanceof javax.crypto.spec.RC5ParameterSpec
            if (r7 == 0) goto L_0x0404
            javax.crypto.spec.RC5ParameterSpec r3 = (javax.crypto.spec.RC5ParameterSpec) r3
            org.bouncycastle.crypto.params.RC5Parameters r5 = new org.bouncycastle.crypto.params.RC5Parameters
            byte[] r2 = r20.getEncoded()
            int r7 = r3.getRounds()
            r5.<init>(r2, r7)
            org.bouncycastle.crypto.BlockCipher r2 = r1.baseEngine
            java.lang.String r2 = r2.getAlgorithmName()
            java.lang.String r7 = "RC5"
            boolean r2 = r2.startsWith(r7)
            if (r2 == 0) goto L_0x03fc
            org.bouncycastle.crypto.BlockCipher r2 = r1.baseEngine
            java.lang.String r2 = r2.getAlgorithmName()
            java.lang.String r7 = "RC5-32"
            boolean r2 = r2.equals(r7)
            java.lang.String r7 = "."
            if (r2 == 0) goto L_0x039c
            int r2 = r3.getWordSize()
            r6 = 32
            if (r2 != r6) goto L_0x037b
            goto L_0x03d2
        L_0x037b:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "RC5 already set up for a word size of 32 not "
            java.lang.StringBuilder r2 = r2.append(r4)
            int r3 = r3.getWordSize()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x039c:
            org.bouncycastle.crypto.BlockCipher r2 = r1.baseEngine
            java.lang.String r2 = r2.getAlgorithmName()
            boolean r2 = r2.equals(r6)
            if (r2 == 0) goto L_0x03d2
            int r2 = r3.getWordSize()
            r6 = 64
            if (r2 != r6) goto L_0x03b1
            goto L_0x03d2
        L_0x03b1:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "RC5 already set up for a word size of 64 not "
            java.lang.StringBuilder r2 = r2.append(r4)
            int r3 = r3.getWordSize()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r7)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x03d2:
            byte[] r2 = r3.getIV()
            if (r2 == 0) goto L_0x0478
            int r2 = r1.ivLength
            if (r2 == 0) goto L_0x0478
            boolean r2 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r2 == 0) goto L_0x03f1
            org.bouncycastle.crypto.params.ParametersWithIV r2 = new org.bouncycastle.crypto.params.ParametersWithIV
            org.bouncycastle.crypto.params.ParametersWithIV r5 = (org.bouncycastle.crypto.params.ParametersWithIV) r5
            org.bouncycastle.crypto.CipherParameters r5 = r5.getParameters()
            byte[] r3 = r3.getIV()
            r2.<init>(r5, r3)
            goto L_0x02fe
        L_0x03f1:
            org.bouncycastle.crypto.params.ParametersWithIV r2 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r3 = r3.getIV()
            r2.<init>(r5, r3)
            goto L_0x02fe
        L_0x03fc:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "RC5 parameters passed to a cipher that is not RC5."
            r0.<init>(r2)
            throw r0
        L_0x0404:
            java.lang.Class r2 = gcmSpecClass
            if (r2 == 0) goto L_0x0469
            boolean r6 = r2.isInstance(r3)
            if (r6 == 0) goto L_0x0469
            java.lang.String r6 = r1.modeName
            boolean r6 = r1.isAEADModeName(r6)
            if (r6 != 0) goto L_0x0425
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r6 = r1.cipher
            boolean r6 = r6 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher
            if (r6 == 0) goto L_0x041d
            goto L_0x0425
        L_0x041d:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "GCMParameterSpec can only be used with AEAD modes."
            r0.<init>(r2)
            throw r0
        L_0x0425:
            java.lang.String r6 = "getTLen"
            java.lang.Class[] r7 = new java.lang.Class[r8]     // Catch:{ Exception -> 0x0460 }
            java.lang.reflect.Method r6 = r2.getDeclaredMethod(r6, r7)     // Catch:{ Exception -> 0x0460 }
            java.lang.String r7 = "getIV"
            java.lang.Class[] r9 = new java.lang.Class[r8]     // Catch:{ Exception -> 0x0460 }
            java.lang.reflect.Method r2 = r2.getDeclaredMethod(r7, r9)     // Catch:{ Exception -> 0x0460 }
            boolean r7 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV     // Catch:{ Exception -> 0x0460 }
            if (r7 == 0) goto L_0x043f
            org.bouncycastle.crypto.params.ParametersWithIV r5 = (org.bouncycastle.crypto.params.ParametersWithIV) r5     // Catch:{ Exception -> 0x0460 }
            org.bouncycastle.crypto.CipherParameters r5 = r5.getParameters()     // Catch:{ Exception -> 0x0460 }
        L_0x043f:
            org.bouncycastle.crypto.params.KeyParameter r5 = (org.bouncycastle.crypto.params.KeyParameter) r5     // Catch:{ Exception -> 0x0460 }
            org.bouncycastle.crypto.params.AEADParameters r7 = new org.bouncycastle.crypto.params.AEADParameters     // Catch:{ Exception -> 0x0460 }
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0460 }
            java.lang.Object r6 = r6.invoke(r3, r9)     // Catch:{ Exception -> 0x0460 }
            java.lang.Integer r6 = (java.lang.Integer) r6     // Catch:{ Exception -> 0x0460 }
            int r6 = r6.intValue()     // Catch:{ Exception -> 0x0460 }
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0460 }
            java.lang.Object r2 = r2.invoke(r3, r9)     // Catch:{ Exception -> 0x0460 }
            byte[] r2 = (byte[]) r2     // Catch:{ Exception -> 0x0460 }
            byte[] r2 = (byte[]) r2     // Catch:{ Exception -> 0x0460 }
            r7.<init>(r5, r6, r2)     // Catch:{ Exception -> 0x0460 }
            r1.aeadParams = r7     // Catch:{ Exception -> 0x0460 }
            r5 = r7
            goto L_0x0478
        L_0x0460:
            r0 = move-exception
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "Cannot process GCMParameterSpec."
            r0.<init>(r2)
            throw r0
        L_0x0469:
            if (r3 == 0) goto L_0x0478
            boolean r2 = r3 instanceof javax.crypto.spec.PBEParameterSpec
            if (r2 == 0) goto L_0x0470
            goto L_0x0478
        L_0x0470:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "unknown parameter type."
            r0.<init>(r2)
            throw r0
        L_0x0478:
            int r2 = r1.ivLength
            if (r2 == 0) goto L_0x04bf
            boolean r2 = r5 instanceof org.bouncycastle.crypto.params.ParametersWithIV
            if (r2 != 0) goto L_0x04bf
            boolean r2 = r5 instanceof org.bouncycastle.crypto.params.AEADParameters
            if (r2 != 0) goto L_0x04bf
            if (r4 != 0) goto L_0x048b
            java.security.SecureRandom r2 = org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom()
            goto L_0x048c
        L_0x048b:
            r2 = r4
        L_0x048c:
            if (r0 == r10) goto L_0x04ad
            r3 = 3
            if (r0 != r3) goto L_0x0492
            goto L_0x04ad
        L_0x0492:
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r2 = r1.cipher
            org.bouncycastle.crypto.BlockCipher r2 = r2.getUnderlyingCipher()
            java.lang.String r2 = r2.getAlgorithmName()
            java.lang.String r3 = "PGPCFB"
            int r2 = r2.indexOf(r3)
            if (r2 < 0) goto L_0x04a5
            goto L_0x04bf
        L_0x04a5:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "no IV set when one expected"
            r0.<init>(r2)
            throw r0
        L_0x04ad:
            int r3 = r1.ivLength
            byte[] r3 = new byte[r3]
            r2.nextBytes(r3)
            org.bouncycastle.crypto.params.ParametersWithIV r2 = new org.bouncycastle.crypto.params.ParametersWithIV
            r2.<init>(r5, r3)
            r3 = r2
            org.bouncycastle.crypto.params.ParametersWithIV r3 = (org.bouncycastle.crypto.params.ParametersWithIV) r3
            r1.ivParam = r2
            r5 = r2
        L_0x04bf:
            if (r4 == 0) goto L_0x04cb
            boolean r2 = r1.padded
            if (r2 == 0) goto L_0x04cb
            org.bouncycastle.crypto.params.ParametersWithRandom r2 = new org.bouncycastle.crypto.params.ParametersWithRandom
            r2.<init>(r5, r4)
            r5 = r2
        L_0x04cb:
            switch(r0) {
                case 1: goto L_0x04d7;
                case 2: goto L_0x04d1;
                case 3: goto L_0x04d7;
                case 4: goto L_0x04d1;
                default: goto L_0x04ce;
            }
        L_0x04ce:
            java.security.InvalidParameterException r2 = new java.security.InvalidParameterException     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            goto L_0x050d
        L_0x04d1:
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r0 = r1.cipher     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            r0.init(r8, r5)     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            goto L_0x04dc
        L_0x04d7:
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r0 = r1.cipher     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            r0.init(r10, r5)     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
        L_0x04dc:
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r0 = r1.cipher     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            boolean r2 = r0 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            if (r2 == 0) goto L_0x0508
            org.bouncycastle.crypto.params.AEADParameters r2 = r1.aeadParams     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            if (r2 != 0) goto L_0x0508
            org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher$AEADGenericBlockCipher r0 = (org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher) r0     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            org.bouncycastle.crypto.modes.AEADCipher r0 = r0.cipher     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            org.bouncycastle.crypto.params.AEADParameters r2 = new org.bouncycastle.crypto.params.AEADParameters     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            org.bouncycastle.crypto.params.ParametersWithIV r3 = r1.ivParam     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            org.bouncycastle.crypto.CipherParameters r3 = r3.getParameters()     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            org.bouncycastle.crypto.params.KeyParameter r3 = (org.bouncycastle.crypto.params.KeyParameter) r3     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            byte[] r0 = r0.getMac()     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            int r0 = r0.length     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            int r0 = r0 * 8
            org.bouncycastle.crypto.params.ParametersWithIV r4 = r1.ivParam     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            byte[] r4 = r4.getIV()     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            r2.<init>(r3, r0, r4)     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            r1.aeadParams = r2     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
        L_0x0508:
            return
        L_0x0509:
            r0 = move-exception
            goto L_0x052a
        L_0x050b:
            r0 = move-exception
            goto L_0x0534
        L_0x050d:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            r3.<init>()     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            java.lang.String r4 = "unknown opmode "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            java.lang.String r3 = " passed"
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            java.lang.String r0 = r0.toString()     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            r2.<init>(r0)     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
            throw r2     // Catch:{ IllegalArgumentException -> 0x050b, Exception -> 0x0509 }
        L_0x052a:
            org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$InvalidKeyOrParametersException r2 = new org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$InvalidKeyOrParametersException
            java.lang.String r3 = r0.getMessage()
            r2.<init>(r3, r0)
            throw r2
        L_0x0534:
            java.security.InvalidAlgorithmParameterException r2 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r3 = r0.getMessage()
            r2.<init>(r3, r0)
            throw r2
        L_0x053e:
            r0 = move-exception
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            java.lang.String r2 = "PKCS12 requires a SecretKey/PBEKey"
            r0.<init>(r2)
            goto L_0x0548
        L_0x0547:
            throw r0
        L_0x0548:
            goto L_0x0547
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.engineInit(int, java.security.Key, java.security.spec.AlgorithmParameterSpec, java.security.SecureRandom):void");
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String str) throws NoSuchAlgorithmException {
        GenericBlockCipher aEADGenericBlockCipher;
        BufferedGenericBlockCipher bufferedGenericBlockCipher;
        if (this.baseEngine != null) {
            String upperCase = Strings.toUpperCase(str);
            this.modeName = upperCase;
            if (upperCase.equals("ECB")) {
                this.ivLength = 0;
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(this.baseEngine);
            } else if (this.modeName.equals("CBC")) {
                this.ivLength = this.baseEngine.getBlockSize();
                aEADGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new CBCBlockCipher(this.baseEngine));
            } else {
                if (this.modeName.startsWith("OFB")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    if (this.modeName.length() != 3) {
                        bufferedGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new OFBBlockCipher(this.baseEngine, Integer.parseInt(this.modeName.substring(3))));
                    } else {
                        BlockCipher blockCipher = this.baseEngine;
                        aEADGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new OFBBlockCipher(blockCipher, blockCipher.getBlockSize() * 8));
                    }
                } else if (this.modeName.startsWith("CFB")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    if (this.modeName.length() != 3) {
                        bufferedGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new CFBBlockCipher(this.baseEngine, Integer.parseInt(this.modeName.substring(3))));
                    } else {
                        BlockCipher blockCipher2 = this.baseEngine;
                        aEADGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new CFBBlockCipher(blockCipher2, blockCipher2.getBlockSize() * 8));
                    }
                } else if (this.modeName.startsWith("PGP")) {
                    boolean equalsIgnoreCase = this.modeName.equalsIgnoreCase("PGPCFBwithIV");
                    this.ivLength = this.baseEngine.getBlockSize();
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new PGPCFBBlockCipher(this.baseEngine, equalsIgnoreCase));
                } else if (this.modeName.equalsIgnoreCase("OpenPGPCFB")) {
                    this.ivLength = 0;
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher((BlockCipher) new OpenPGPCFBBlockCipher(this.baseEngine));
                } else if (this.modeName.startsWith("SIC")) {
                    int blockSize = this.baseEngine.getBlockSize();
                    this.ivLength = blockSize;
                    if (blockSize >= 16) {
                        this.fixedIv = false;
                        aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.baseEngine)));
                    } else {
                        throw new IllegalArgumentException("Warning: SIC-Mode can become a twotime-pad if the blocksize of the cipher is too small. Use a cipher with a block size of at least 128 bits (e.g. AES)");
                    }
                } else if (this.modeName.startsWith("CTR")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    this.fixedIv = false;
                    BlockCipher blockCipher3 = this.baseEngine;
                    bufferedGenericBlockCipher = blockCipher3 instanceof DSTU7624Engine ? new BufferedGenericBlockCipher(new BufferedBlockCipher(new KCTRBlockCipher(blockCipher3))) : new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(blockCipher3)));
                } else if (this.modeName.startsWith("GOFB")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GOFBBlockCipher(this.baseEngine)));
                } else if (this.modeName.startsWith("GCFB")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GCFBBlockCipher(this.baseEngine)));
                } else if (this.modeName.startsWith("CTS")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    aEADGenericBlockCipher = new BufferedGenericBlockCipher((BufferedBlockCipher) new CTSBlockCipher(new CBCBlockCipher(this.baseEngine)));
                } else if (this.modeName.startsWith("CCM")) {
                    this.ivLength = 12;
                    aEADGenericBlockCipher = this.baseEngine instanceof DSTU7624Engine ? new AEADGenericBlockCipher(new KCCMBlockCipher(this.baseEngine)) : new AEADGenericBlockCipher(new CCMBlockCipher(this.baseEngine));
                } else if (this.modeName.startsWith("OCB")) {
                    if (this.engineProvider != null) {
                        this.ivLength = 15;
                        aEADGenericBlockCipher = new AEADGenericBlockCipher(new OCBBlockCipher(this.baseEngine, this.engineProvider.get()));
                    } else {
                        throw new NoSuchAlgorithmException("can't support mode " + str);
                    }
                } else if (this.modeName.startsWith("EAX")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    aEADGenericBlockCipher = new AEADGenericBlockCipher(new EAXBlockCipher(this.baseEngine));
                } else if (this.modeName.startsWith("GCM")) {
                    this.ivLength = this.baseEngine.getBlockSize();
                    aEADGenericBlockCipher = this.baseEngine instanceof DSTU7624Engine ? new AEADGenericBlockCipher(new KGCMBlockCipher(this.baseEngine)) : new AEADGenericBlockCipher(new GCMBlockCipher(this.baseEngine));
                } else {
                    throw new NoSuchAlgorithmException("can't support mode " + str);
                }
                this.cipher = bufferedGenericBlockCipher;
                return;
            }
            this.cipher = aEADGenericBlockCipher;
            return;
        }
        throw new NoSuchAlgorithmException("no mode supported for this algorithm");
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        BufferedGenericBlockCipher bufferedGenericBlockCipher;
        if (this.baseEngine != null) {
            String upperCase = Strings.toUpperCase(str);
            if (upperCase.equals("NOPADDING")) {
                if (this.cipher.wrapOnNoPadding()) {
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(this.cipher.getUnderlyingCipher()));
                } else {
                    return;
                }
            } else if (upperCase.equals("WITHCTS") || upperCase.equals("CTSPADDING") || upperCase.equals("CS3PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher((BufferedBlockCipher) new CTSBlockCipher(this.cipher.getUnderlyingCipher()));
            } else {
                this.padded = true;
                if (isAEADModeName(this.modeName)) {
                    throw new NoSuchPaddingException("Only NoPadding can be used with AEAD modes.");
                } else if (upperCase.equals("PKCS5PADDING") || upperCase.equals("PKCS7PADDING")) {
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher());
                } else if (upperCase.equals("ZEROBYTEPADDING")) {
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ZeroBytePadding());
                } else if (upperCase.equals("ISO10126PADDING") || upperCase.equals("ISO10126-2PADDING")) {
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO10126d2Padding());
                } else if (upperCase.equals("X9.23PADDING") || upperCase.equals("X923PADDING")) {
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new X923Padding());
                } else if (upperCase.equals("ISO7816-4PADDING") || upperCase.equals("ISO9797-1PADDING")) {
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO7816d4Padding());
                } else if (upperCase.equals("TBCPADDING")) {
                    bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new TBCPadding());
                } else {
                    throw new NoSuchPaddingException("Padding " + str + " unknown.");
                }
            }
            this.cipher = bufferedGenericBlockCipher;
            return;
        }
        throw new NoSuchPaddingException("no padding supported for this algorithm");
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException {
        if (this.cipher.getUpdateOutputSize(i2) + i3 <= bArr2.length) {
            try {
                return this.cipher.processBytes(bArr, i, i2, bArr2, i3);
            } catch (DataLengthException e) {
                throw new IllegalStateException(e.toString());
            }
        } else {
            throw new ShortBufferException("output buffer too short for input.");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] bArr, int i, int i2) {
        int updateOutputSize = this.cipher.getUpdateOutputSize(i2);
        if (updateOutputSize > 0) {
            byte[] bArr2 = new byte[updateOutputSize];
            int processBytes = this.cipher.processBytes(bArr, i, i2, bArr2, 0);
            if (processBytes == 0) {
                return null;
            }
            if (processBytes == updateOutputSize) {
                return bArr2;
            }
            byte[] bArr3 = new byte[processBytes];
            System.arraycopy(bArr2, 0, bArr3, 0, processBytes);
            return bArr3;
        }
        this.cipher.processBytes(bArr, i, i2, (byte[]) null, 0);
        return null;
    }

    /* access modifiers changed from: protected */
    public void engineUpdateAAD(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        if (remaining >= 1) {
            if (byteBuffer.hasArray()) {
                engineUpdateAAD(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), remaining);
                byteBuffer.position(byteBuffer.limit());
            } else if (remaining <= 512) {
                byte[] bArr = new byte[remaining];
                byteBuffer.get(bArr);
                engineUpdateAAD(bArr, 0, remaining);
                Arrays.fill(bArr, (byte) 0);
            } else {
                byte[] bArr2 = new byte[512];
                do {
                    int min = Math.min(512, remaining);
                    byteBuffer.get(bArr2, 0, min);
                    engineUpdateAAD(bArr2, 0, min);
                    remaining -= min;
                } while (remaining > 0);
                Arrays.fill(bArr2, (byte) 0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void engineUpdateAAD(byte[] bArr, int i, int i2) {
        this.cipher.updateAAD(bArr, i, i2);
    }
}
