package org.bouncycastle.jcajce.provider.symmetric.util;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.jcajce.spec.GOST28147WrapParameterSpec;
import org.bouncycastle.jcajce.util.BCJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;

public abstract class BaseWrapCipher extends CipherSpi implements PBE {
    private Class[] availableSpecs;
    protected AlgorithmParameters engineParams;
    private boolean forWrapping;
    private final JcaJceHelper helper;

    /* renamed from: iv */
    private byte[] f673iv;
    private int ivSize;
    protected int pbeHash;
    protected int pbeIvSize;
    protected int pbeKeySize;
    protected int pbeType;
    protected Wrapper wrapEngine;
    private ErasableOutputStream wrapStream;

    protected static final class ErasableOutputStream extends ByteArrayOutputStream {
        public void erase() {
            Arrays.fill(this.buf, (byte) 0);
            reset();
        }

        public byte[] getBuf() {
            return this.buf;
        }
    }

    protected static class InvalidKeyOrParametersException extends InvalidKeyException {
        private final Throwable cause;

        InvalidKeyOrParametersException(String str, Throwable th) {
            super(str);
            this.cause = th;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }

    protected BaseWrapCipher() {
        this.availableSpecs = new Class[]{GOST28147WrapParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.engineParams = null;
        this.wrapEngine = null;
        this.wrapStream = null;
        this.helper = new BCJcaJceHelper();
    }

    protected BaseWrapCipher(Wrapper wrapper) {
        this(wrapper, 0);
    }

    protected BaseWrapCipher(Wrapper wrapper, int i) {
        this.availableSpecs = new Class[]{GOST28147WrapParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.engineParams = null;
        this.wrapEngine = null;
        this.wrapStream = null;
        this.helper = new BCJcaJceHelper();
        this.wrapEngine = wrapper;
        this.ivSize = i;
    }

    /* access modifiers changed from: protected */
    public final AlgorithmParameters createParametersInstance(String str) throws NoSuchAlgorithmException, NoSuchProviderException {
        return this.helper.createAlgorithmParameters(str);
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
        ErasableOutputStream erasableOutputStream = this.wrapStream;
        if (erasableOutputStream != null) {
            erasableOutputStream.write(bArr, i, i2);
            try {
                byte[] wrap = this.forWrapping ? this.wrapEngine.wrap(this.wrapStream.getBuf(), 0, this.wrapStream.size()) : this.wrapEngine.unwrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                if (wrap.length + i3 <= bArr2.length) {
                    System.arraycopy(wrap, 0, bArr2, i3, wrap.length);
                    int length = wrap.length;
                    this.wrapStream.erase();
                    return length;
                }
                throw new ShortBufferException("output buffer too short for input.");
            } catch (InvalidCipherTextException e) {
                throw new BadPaddingException(e.getMessage());
            } catch (Exception e2) {
                throw new IllegalBlockSizeException(e2.getMessage());
            } catch (Throwable th) {
                this.wrapStream.erase();
                throw th;
            }
        } else {
            throw new IllegalStateException("not supported in a wrapping mode");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) throws IllegalBlockSizeException, BadPaddingException {
        ErasableOutputStream erasableOutputStream = this.wrapStream;
        if (erasableOutputStream != null) {
            if (bArr != null) {
                erasableOutputStream.write(bArr, i, i2);
            }
            try {
                byte[] wrap = this.forWrapping ? this.wrapEngine.wrap(this.wrapStream.getBuf(), 0, this.wrapStream.size()) : this.wrapEngine.unwrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                this.wrapStream.erase();
                return wrap;
            } catch (InvalidCipherTextException e) {
                throw new BadPaddingException(e.getMessage());
            } catch (Exception e2) {
                throw new IllegalBlockSizeException(e2.getMessage());
            } catch (Throwable th) {
                this.wrapStream.erase();
                throw th;
            }
        } else {
            throw new IllegalStateException("not supported in a wrapping mode");
        }
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        return Arrays.clone(this.f673iv);
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int i) {
        return -1;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null && this.f673iv != null) {
            String algorithmName = this.wrapEngine.getAlgorithmName();
            if (algorithmName.indexOf(47) >= 0) {
                algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
            }
            try {
                AlgorithmParameters createParametersInstance = createParametersInstance(algorithmName);
                this.engineParams = createParametersInstance;
                createParametersInstance.init(new IvParameterSpec(this.f673iv));
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
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
                try {
                    algorithmParameterSpec = algorithmParameters.getParameterSpec(clsArr[i2]);
                    break;
                } catch (Exception e) {
                    i2++;
                }
            }
            if (algorithmParameterSpec == null) {
                throw new InvalidAlgorithmParameterException("can't handle parameter " + algorithmParameters.toString());
            }
        }
        this.engineParams = algorithmParameters;
        engineInit(i, key, algorithmParameterSpec, secureRandom);
    }

    /* access modifiers changed from: protected */
    public void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            AlgorithmParameterSpec algorithmParameterSpec = null;
            engineInit(i, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyOrParametersException(e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0095, code lost:
        r2.forWrapping = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009f, code lost:
        r2.forWrapping = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineInit(int r3, java.security.Key r4, java.security.spec.AlgorithmParameterSpec r5, java.security.SecureRandom r6) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        /*
            r2 = this;
            boolean r0 = r4 instanceof org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey
            if (r0 == 0) goto L_0x0028
            org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey r4 = (org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) r4
            boolean r0 = r5 instanceof javax.crypto.spec.PBEParameterSpec
            if (r0 == 0) goto L_0x0015
            org.bouncycastle.crypto.Wrapper r0 = r2.wrapEngine
            java.lang.String r0 = r0.getAlgorithmName()
            org.bouncycastle.crypto.CipherParameters r4 = org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(r4, r5, r0)
            goto L_0x0032
        L_0x0015:
            org.bouncycastle.crypto.CipherParameters r0 = r4.getParam()
            if (r0 == 0) goto L_0x0020
            org.bouncycastle.crypto.CipherParameters r4 = r4.getParam()
            goto L_0x0032
        L_0x0020:
            java.security.InvalidAlgorithmParameterException r3 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r4 = "PBE requires PBE parameters to be set."
            r3.<init>(r4)
            throw r3
        L_0x0028:
            org.bouncycastle.crypto.params.KeyParameter r0 = new org.bouncycastle.crypto.params.KeyParameter
            byte[] r4 = r4.getEncoded()
            r0.<init>(r4)
            r4 = r0
        L_0x0032:
            boolean r0 = r5 instanceof javax.crypto.spec.IvParameterSpec
            if (r0 == 0) goto L_0x0045
            r0 = r5
            javax.crypto.spec.IvParameterSpec r0 = (javax.crypto.spec.IvParameterSpec) r0
            byte[] r0 = r0.getIV()
            r2.f673iv = r0
            org.bouncycastle.crypto.params.ParametersWithIV r1 = new org.bouncycastle.crypto.params.ParametersWithIV
            r1.<init>(r4, r0)
            r4 = r1
        L_0x0045:
            boolean r0 = r5 instanceof org.bouncycastle.jcajce.spec.GOST28147WrapParameterSpec
            if (r0 == 0) goto L_0x0061
            org.bouncycastle.jcajce.spec.GOST28147WrapParameterSpec r5 = (org.bouncycastle.jcajce.spec.GOST28147WrapParameterSpec) r5
            byte[] r0 = r5.getSBox()
            if (r0 == 0) goto L_0x0057
            org.bouncycastle.crypto.params.ParametersWithSBox r1 = new org.bouncycastle.crypto.params.ParametersWithSBox
            r1.<init>(r4, r0)
            r4 = r1
        L_0x0057:
            org.bouncycastle.crypto.params.ParametersWithUKM r0 = new org.bouncycastle.crypto.params.ParametersWithUKM
            byte[] r5 = r5.getUKM()
            r0.<init>(r4, r5)
            r4 = r0
        L_0x0061:
            boolean r5 = r4 instanceof org.bouncycastle.crypto.params.KeyParameter
            r0 = 1
            if (r5 == 0) goto L_0x007e
            int r5 = r2.ivSize
            if (r5 == 0) goto L_0x007e
            r1 = 3
            if (r3 == r1) goto L_0x006f
            if (r3 != r0) goto L_0x007e
        L_0x006f:
            byte[] r5 = new byte[r5]
            r2.f673iv = r5
            r6.nextBytes(r5)
            org.bouncycastle.crypto.params.ParametersWithIV r5 = new org.bouncycastle.crypto.params.ParametersWithIV
            byte[] r1 = r2.f673iv
            r5.<init>(r4, r1)
            r4 = r5
        L_0x007e:
            if (r6 == 0) goto L_0x0086
            org.bouncycastle.crypto.params.ParametersWithRandom r5 = new org.bouncycastle.crypto.params.ParametersWithRandom
            r5.<init>(r4, r6)
            r4 = r5
        L_0x0086:
            r5 = 0
            r6 = 0
            switch(r3) {
                case 1: goto L_0x00af;
                case 2: goto L_0x00a2;
                case 3: goto L_0x0098;
                case 4: goto L_0x008e;
                default: goto L_0x008b;
            }
        L_0x008b:
            java.security.InvalidParameterException r3 = new java.security.InvalidParameterException     // Catch:{ Exception -> 0x00bd }
            goto L_0x00bf
        L_0x008e:
            org.bouncycastle.crypto.Wrapper r3 = r2.wrapEngine     // Catch:{ Exception -> 0x00bd }
            r3.init(r6, r4)     // Catch:{ Exception -> 0x00bd }
            r2.wrapStream = r5     // Catch:{ Exception -> 0x00bd }
        L_0x0095:
            r2.forWrapping = r6     // Catch:{ Exception -> 0x00bd }
            goto L_0x00bc
        L_0x0098:
            org.bouncycastle.crypto.Wrapper r3 = r2.wrapEngine     // Catch:{ Exception -> 0x00bd }
            r3.init(r0, r4)     // Catch:{ Exception -> 0x00bd }
            r2.wrapStream = r5     // Catch:{ Exception -> 0x00bd }
        L_0x009f:
            r2.forWrapping = r0     // Catch:{ Exception -> 0x00bd }
            goto L_0x00bc
        L_0x00a2:
            org.bouncycastle.crypto.Wrapper r3 = r2.wrapEngine     // Catch:{ Exception -> 0x00bd }
            r3.init(r6, r4)     // Catch:{ Exception -> 0x00bd }
            org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r3 = new org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream     // Catch:{ Exception -> 0x00bd }
            r3.<init>()     // Catch:{ Exception -> 0x00bd }
            r2.wrapStream = r3     // Catch:{ Exception -> 0x00bd }
            goto L_0x0095
        L_0x00af:
            org.bouncycastle.crypto.Wrapper r3 = r2.wrapEngine     // Catch:{ Exception -> 0x00bd }
            r3.init(r0, r4)     // Catch:{ Exception -> 0x00bd }
            org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r3 = new org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream     // Catch:{ Exception -> 0x00bd }
            r3.<init>()     // Catch:{ Exception -> 0x00bd }
            r2.wrapStream = r3     // Catch:{ Exception -> 0x00bd }
            goto L_0x009f
        L_0x00bc:
            return
        L_0x00bd:
            r3 = move-exception
            goto L_0x00c5
        L_0x00bf:
            java.lang.String r4 = "Unknown mode parameter passed to init."
            r3.<init>(r4)     // Catch:{ Exception -> 0x00bd }
            throw r3     // Catch:{ Exception -> 0x00bd }
        L_0x00c5:
            org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$InvalidKeyOrParametersException r4 = new org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$InvalidKeyOrParametersException
            java.lang.String r5 = r3.getMessage()
            r4.<init>(r5, r3)
            goto L_0x00d0
        L_0x00cf:
            throw r4
        L_0x00d0:
            goto L_0x00cf
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher.engineInit(int, java.security.Key, java.security.spec.AlgorithmParameterSpec, java.security.SecureRandom):void");
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String str) throws NoSuchAlgorithmException {
        throw new NoSuchAlgorithmException("can't support mode " + str);
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        throw new NoSuchPaddingException("Padding " + str + " unknown.");
    }

    /* access modifiers changed from: protected */
    public Key engineUnwrap(byte[] bArr, String str, int i) throws InvalidKeyException, NoSuchAlgorithmException {
        try {
            Wrapper wrapper = this.wrapEngine;
            byte[] engineDoFinal = wrapper == null ? engineDoFinal(bArr, 0, bArr.length) : wrapper.unwrap(bArr, 0, bArr.length);
            if (i == 3) {
                return new SecretKeySpec(engineDoFinal, str);
            }
            if (!str.equals("") || i != 2) {
                try {
                    KeyFactory createKeyFactory = this.helper.createKeyFactory(str);
                    if (i == 1) {
                        return createKeyFactory.generatePublic(new X509EncodedKeySpec(engineDoFinal));
                    }
                    if (i == 2) {
                        return createKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(engineDoFinal));
                    }
                    throw new InvalidKeyException("Unknown key type " + i);
                } catch (NoSuchProviderException e) {
                    throw new InvalidKeyException("Unknown key type " + e.getMessage());
                } catch (InvalidKeySpecException e2) {
                    throw new InvalidKeyException("Unknown key type " + e2.getMessage());
                }
            } else {
                try {
                    PrivateKeyInfo instance = PrivateKeyInfo.getInstance(engineDoFinal);
                    PrivateKey privateKey = BouncyCastleProvider.getPrivateKey(instance);
                    if (privateKey != null) {
                        return privateKey;
                    }
                    throw new InvalidKeyException("algorithm " + instance.getPrivateKeyAlgorithm().getAlgorithm() + " not supported");
                } catch (Exception e3) {
                    throw new InvalidKeyException("Invalid key encoding.");
                }
            }
        } catch (InvalidCipherTextException e4) {
            throw new InvalidKeyException(e4.getMessage());
        } catch (BadPaddingException e5) {
            throw new InvalidKeyException(e5.getMessage());
        } catch (IllegalBlockSizeException e6) {
            throw new InvalidKeyException(e6.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException {
        ErasableOutputStream erasableOutputStream = this.wrapStream;
        if (erasableOutputStream != null) {
            erasableOutputStream.write(bArr, i, i2);
            return 0;
        }
        throw new IllegalStateException("not supported in a wrapping mode");
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] bArr, int i, int i2) {
        ErasableOutputStream erasableOutputStream = this.wrapStream;
        if (erasableOutputStream != null) {
            erasableOutputStream.write(bArr, i, i2);
            return null;
        }
        throw new IllegalStateException("not supported in a wrapping mode");
    }

    /* access modifiers changed from: protected */
    public byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        byte[] encoded = key.getEncoded();
        if (encoded != null) {
            try {
                Wrapper wrapper = this.wrapEngine;
                return wrapper == null ? engineDoFinal(encoded, 0, encoded.length) : wrapper.wrap(encoded, 0, encoded.length);
            } catch (BadPaddingException e) {
                throw new IllegalBlockSizeException(e.getMessage());
            }
        } else {
            throw new InvalidKeyException("Cannot wrap key, null encoding.");
        }
    }
}
