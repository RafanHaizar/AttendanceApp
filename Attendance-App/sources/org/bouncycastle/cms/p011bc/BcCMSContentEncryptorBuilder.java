package org.bouncycastle.cms.p011bc;

import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.util.CipherFactory;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.OutputAEADEncryptor;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.util.Integers;

/* renamed from: org.bouncycastle.cms.bc.BcCMSContentEncryptorBuilder */
public class BcCMSContentEncryptorBuilder {
    private static Map keySizes;
    private final ASN1ObjectIdentifier encryptionOID;
    /* access modifiers changed from: private */
    public EnvelopedDataHelper helper;
    private final int keySize;
    private SecureRandom random;

    /* renamed from: org.bouncycastle.cms.bc.BcCMSContentEncryptorBuilder$AADStream */
    private static class AADStream extends OutputStream {
        private AEADBlockCipher cipher;

        public AADStream(AEADBlockCipher aEADBlockCipher) {
            this.cipher = aEADBlockCipher;
        }

        public void write(int i) throws IOException {
            this.cipher.processAADByte((byte) i);
        }

        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.cipher.processAADBytes(bArr, i, i2);
        }
    }

    /* renamed from: org.bouncycastle.cms.bc.BcCMSContentEncryptorBuilder$CMSAuthOutputEncryptor */
    private class CMSAuthOutputEncryptor extends CMSOutputEncryptor implements OutputAEADEncryptor {
        private AEADBlockCipher aeadCipher = getCipher();

        CMSAuthOutputEncryptor(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i, SecureRandom secureRandom) throws CMSException {
            super(aSN1ObjectIdentifier, i, secureRandom);
        }

        private AEADBlockCipher getCipher() {
            if (this.cipher instanceof AEADBlockCipher) {
                return (AEADBlockCipher) this.cipher;
            }
            throw new IllegalArgumentException("Unable to create Authenticated Output Encryptor without Authenticaed Data cipher!");
        }

        public OutputStream getAADStream() {
            return new AADStream(this.aeadCipher);
        }

        public byte[] getMAC() {
            return this.aeadCipher.getMac();
        }
    }

    /* renamed from: org.bouncycastle.cms.bc.BcCMSContentEncryptorBuilder$CMSOutputEncryptor */
    private class CMSOutputEncryptor implements OutputEncryptor {
        private AlgorithmIdentifier algorithmIdentifier;
        protected Object cipher;
        private KeyParameter encKey;

        CMSOutputEncryptor(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i, SecureRandom secureRandom) throws CMSException {
            secureRandom = secureRandom == null ? new SecureRandom() : secureRandom;
            this.encKey = new KeyParameter(BcCMSContentEncryptorBuilder.this.helper.createKeyGenerator(aSN1ObjectIdentifier, secureRandom).generateKey());
            AlgorithmIdentifier generateEncryptionAlgID = BcCMSContentEncryptorBuilder.this.helper.generateEncryptionAlgID(aSN1ObjectIdentifier, this.encKey, secureRandom);
            this.algorithmIdentifier = generateEncryptionAlgID;
            this.cipher = EnvelopedDataHelper.createContentCipher(true, this.encKey, generateEncryptionAlgID);
        }

        public AlgorithmIdentifier getAlgorithmIdentifier() {
            return this.algorithmIdentifier;
        }

        public GenericKey getKey() {
            return new GenericKey(this.algorithmIdentifier, this.encKey.getKey());
        }

        public OutputStream getOutputStream(OutputStream outputStream) {
            return CipherFactory.createOutputStream(outputStream, this.cipher);
        }
    }

    static {
        HashMap hashMap = new HashMap();
        keySizes = hashMap;
        hashMap.put(CMSAlgorithm.AES128_CBC, Integers.valueOf(128));
        keySizes.put(CMSAlgorithm.AES192_CBC, Integers.valueOf(192));
        keySizes.put(CMSAlgorithm.AES256_CBC, Integers.valueOf(256));
        keySizes.put(CMSAlgorithm.AES128_GCM, Integers.valueOf(128));
        keySizes.put(CMSAlgorithm.AES192_GCM, Integers.valueOf(192));
        keySizes.put(CMSAlgorithm.AES256_GCM, Integers.valueOf(256));
        keySizes.put(CMSAlgorithm.CAMELLIA128_CBC, Integers.valueOf(128));
        keySizes.put(CMSAlgorithm.CAMELLIA192_CBC, Integers.valueOf(192));
        keySizes.put(CMSAlgorithm.CAMELLIA256_CBC, Integers.valueOf(256));
    }

    public BcCMSContentEncryptorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this(aSN1ObjectIdentifier, getKeySize(aSN1ObjectIdentifier));
    }

    public BcCMSContentEncryptorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i) {
        this.helper = new EnvelopedDataHelper();
        this.encryptionOID = aSN1ObjectIdentifier;
        this.keySize = i;
    }

    private static int getKeySize(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Integer num = (Integer) keySizes.get(aSN1ObjectIdentifier);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public OutputEncryptor build() throws CMSException {
        return this.helper.isAuthEnveloped(this.encryptionOID) ? new CMSAuthOutputEncryptor(this.encryptionOID, this.keySize, this.random) : new CMSOutputEncryptor(this.encryptionOID, this.keySize, this.random);
    }

    public BcCMSContentEncryptorBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }
}
