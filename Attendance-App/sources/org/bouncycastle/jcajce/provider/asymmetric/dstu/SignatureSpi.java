package org.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.p007ua.DSTU4145Params;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DSAExt;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.signers.DSTU4145Signer;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;

public class SignatureSpi extends java.security.SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private Digest digest;
    private DSAExt signer = new DSTU4145Signer();

    /* access modifiers changed from: protected */
    public Object engineGetParameter(String str) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineInitSign(java.security.PrivateKey r5) throws java.security.InvalidKeyException {
        /*
            r4 = this;
            boolean r0 = r5 instanceof org.bouncycastle.jcajce.provider.asymmetric.dstu.BCDSTU4145PrivateKey
            if (r0 == 0) goto L_0x0018
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r5 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(r5)
            org.bouncycastle.crypto.digests.GOST3411Digest r0 = new org.bouncycastle.crypto.digests.GOST3411Digest
            byte[] r1 = org.bouncycastle.asn1.p007ua.DSTU4145Params.getDefaultDKE()
            byte[] r1 = r4.expandSbox(r1)
            r0.<init>((byte[]) r1)
        L_0x0015:
            r4.digest = r0
            goto L_0x002f
        L_0x0018:
            boolean r0 = r5 instanceof org.bouncycastle.jce.interfaces.ECKey
            if (r0 == 0) goto L_0x002e
            org.bouncycastle.crypto.params.AsymmetricKeyParameter r5 = org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(r5)
            org.bouncycastle.crypto.digests.GOST3411Digest r0 = new org.bouncycastle.crypto.digests.GOST3411Digest
            byte[] r1 = org.bouncycastle.asn1.p007ua.DSTU4145Params.getDefaultDKE()
            byte[] r1 = r4.expandSbox(r1)
            r0.<init>((byte[]) r1)
            goto L_0x0015
        L_0x002e:
            r5 = 0
        L_0x002f:
            java.security.SecureRandom r0 = r4.appRandom
            r1 = 1
            if (r0 == 0) goto L_0x0041
            org.bouncycastle.crypto.DSAExt r0 = r4.signer
            org.bouncycastle.crypto.params.ParametersWithRandom r2 = new org.bouncycastle.crypto.params.ParametersWithRandom
            java.security.SecureRandom r3 = r4.appRandom
            r2.<init>(r5, r3)
            r0.init(r1, r2)
            goto L_0x0046
        L_0x0041:
            org.bouncycastle.crypto.DSAExt r0 = r4.signer
            r0.init(r1, r5)
        L_0x0046:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.dstu.SignatureSpi.engineInitSign(java.security.PrivateKey):void");
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        CipherParameters cipherParameters;
        if (publicKey instanceof BCDSTU4145PublicKey) {
            BCDSTU4145PublicKey bCDSTU4145PublicKey = (BCDSTU4145PublicKey) publicKey;
            cipherParameters = bCDSTU4145PublicKey.engineGetKeyParameters();
            this.digest = new GOST3411Digest(expandSbox(bCDSTU4145PublicKey.getSbox()));
        } else {
            cipherParameters = ECUtil.generatePublicKeyParameter(publicKey);
            this.digest = new GOST3411Digest(expandSbox(DSTU4145Params.getDefaultDKE()));
        }
        this.signer.init(false, cipherParameters);
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(String str, Object obj) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public byte[] engineSign() throws SignatureException {
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        try {
            BigInteger[] generateSignature = this.signer.generateSignature(bArr);
            byte[] byteArray = generateSignature[0].toByteArray();
            byte[] byteArray2 = generateSignature[1].toByteArray();
            int length = (byteArray.length > byteArray2.length ? byteArray.length : byteArray2.length) * 2;
            byte[] bArr2 = new byte[length];
            System.arraycopy(byteArray2, 0, bArr2, (length / 2) - byteArray2.length, byteArray2.length);
            System.arraycopy(byteArray, 0, bArr2, length - byteArray.length, byteArray.length);
            return new DEROctetString(bArr2).getEncoded();
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte b) throws SignatureException {
        this.digest.update(b);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] bArr, int i, int i2) throws SignatureException {
        this.digest.update(bArr, i, i2);
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] bArr) throws SignatureException {
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr2, 0);
        try {
            byte[] octets = ((ASN1OctetString) ASN1OctetString.fromByteArray(bArr)).getOctets();
            byte[] bArr3 = new byte[(octets.length / 2)];
            byte[] bArr4 = new byte[(octets.length / 2)];
            System.arraycopy(octets, 0, bArr4, 0, octets.length / 2);
            System.arraycopy(octets, octets.length / 2, bArr3, 0, octets.length / 2);
            BigInteger bigInteger = new BigInteger(1, bArr4);
            return this.signer.verifySignature(bArr2, new BigInteger[]{new BigInteger(1, bArr3), bigInteger}[0], bigInteger);
        } catch (Exception e) {
            throw new SignatureException("error decoding signature bytes.");
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] expandSbox(byte[] bArr) {
        byte[] bArr2 = new byte[128];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            bArr2[i2] = (byte) ((bArr[i] >> 4) & 15);
            bArr2[i2 + 1] = (byte) (bArr[i] & 15);
        }
        return bArr2;
    }
}
