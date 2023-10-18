package org.bouncycastle.crypto.signers;

import com.itextpdf.signatures.DigestAlgorithms;
import java.io.IOException;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.sphincs.SPHINCSKeyParameters;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.bouncycastle.util.Arrays;

public class RSADigestSigner implements Signer {
    private static final Hashtable oidMap;
    private final AlgorithmIdentifier algId;
    private final Digest digest;
    private boolean forSigning;
    private final AsymmetricBlockCipher rsaEngine;

    static {
        Hashtable hashtable = new Hashtable();
        oidMap = hashtable;
        hashtable.put("RIPEMD128", TeleTrusTObjectIdentifiers.ripemd128);
        hashtable.put(DigestAlgorithms.RIPEMD160, TeleTrusTObjectIdentifiers.ripemd160);
        hashtable.put("RIPEMD256", TeleTrusTObjectIdentifiers.ripemd256);
        hashtable.put("SHA-1", X509ObjectIdentifiers.id_SHA1);
        hashtable.put(McElieceCCA2KeyGenParameterSpec.SHA224, NISTObjectIdentifiers.id_sha224);
        hashtable.put("SHA-256", NISTObjectIdentifiers.id_sha256);
        hashtable.put("SHA-384", NISTObjectIdentifiers.id_sha384);
        hashtable.put("SHA-512", NISTObjectIdentifiers.id_sha512);
        hashtable.put("SHA-512/224", NISTObjectIdentifiers.id_sha512_224);
        hashtable.put(SPHINCSKeyParameters.SHA512_256, NISTObjectIdentifiers.id_sha512_256);
        hashtable.put("SHA3-224", NISTObjectIdentifiers.id_sha3_224);
        hashtable.put("SHA3-256", NISTObjectIdentifiers.id_sha3_256);
        hashtable.put("SHA3-384", NISTObjectIdentifiers.id_sha3_384);
        hashtable.put("SHA3-512", NISTObjectIdentifiers.id_sha3_512);
        hashtable.put("MD2", PKCSObjectIdentifiers.md2);
        hashtable.put("MD4", PKCSObjectIdentifiers.md4);
        hashtable.put("MD5", PKCSObjectIdentifiers.md5);
    }

    public RSADigestSigner(Digest digest2) {
        this(digest2, (ASN1ObjectIdentifier) oidMap.get(digest2.getAlgorithmName()));
    }

    public RSADigestSigner(Digest digest2, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.rsaEngine = new PKCS1Encoding(new RSABlindedEngine());
        this.digest = digest2;
        this.algId = aSN1ObjectIdentifier != null ? new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE) : null;
    }

    private byte[] derEncode(byte[] bArr) throws IOException {
        AlgorithmIdentifier algorithmIdentifier = this.algId;
        if (algorithmIdentifier != null) {
            return new DigestInfo(algorithmIdentifier, bArr).getEncoded(ASN1Encoding.DER);
        }
        try {
            DigestInfo.getInstance(bArr);
            return bArr;
        } catch (IllegalArgumentException e) {
            throw new IOException("malformed DigestInfo for NONEwithRSA hash: " + e.getMessage());
        }
    }

    public byte[] generateSignature() throws CryptoException, DataLengthException {
        if (this.forSigning) {
            byte[] bArr = new byte[this.digest.getDigestSize()];
            this.digest.doFinal(bArr, 0);
            try {
                byte[] derEncode = derEncode(bArr);
                return this.rsaEngine.processBlock(derEncode, 0, derEncode.length);
            } catch (IOException e) {
                throw new CryptoException("unable to encode signature: " + e.getMessage(), e);
            }
        } else {
            throw new IllegalStateException("RSADigestSigner not initialised for signature generation.");
        }
    }

    public String getAlgorithmName() {
        return this.digest.getAlgorithmName() + "withRSA";
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.forSigning = z;
        AsymmetricKeyParameter asymmetricKeyParameter = cipherParameters instanceof ParametersWithRandom ? (AsymmetricKeyParameter) ((ParametersWithRandom) cipherParameters).getParameters() : (AsymmetricKeyParameter) cipherParameters;
        if (z && !asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("signing requires private key");
        } else if (z || !asymmetricKeyParameter.isPrivate()) {
            reset();
            this.rsaEngine.init(z, cipherParameters);
        } else {
            throw new IllegalArgumentException("verification requires public key");
        }
    }

    public void reset() {
        this.digest.reset();
    }

    public void update(byte b) {
        this.digest.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.digest.update(bArr, i, i2);
    }

    public boolean verifySignature(byte[] bArr) {
        if (!this.forSigning) {
            int digestSize = this.digest.getDigestSize();
            byte[] bArr2 = new byte[digestSize];
            this.digest.doFinal(bArr2, 0);
            try {
                byte[] processBlock = this.rsaEngine.processBlock(bArr, 0, bArr.length);
                byte[] derEncode = derEncode(bArr2);
                if (processBlock.length == derEncode.length) {
                    return Arrays.constantTimeAreEqual(processBlock, derEncode);
                }
                if (processBlock.length == derEncode.length - 2) {
                    int length = (processBlock.length - digestSize) - 2;
                    int length2 = (derEncode.length - digestSize) - 2;
                    derEncode[1] = (byte) (derEncode[1] - 2);
                    derEncode[3] = (byte) (derEncode[3] - 2);
                    byte b = 0;
                    for (int i = 0; i < digestSize; i++) {
                        b |= processBlock[length + i] ^ derEncode[length2 + i];
                    }
                    for (int i2 = 0; i2 < length; i2++) {
                        b |= processBlock[i2] ^ derEncode[i2];
                    }
                    return b == 0;
                }
                Arrays.constantTimeAreEqual(derEncode, derEncode);
                return false;
            } catch (Exception e) {
                return false;
            }
        } else {
            throw new IllegalStateException("RSADigestSigner not initialised for verification");
        }
    }
}
