package org.bouncycastle.operator.jcajce;

import java.security.PrivateKey;
import java.security.Provider;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.AsymmetricKeyUnwrapper;

public class JceAsymmetricKeyUnwrapper extends AsymmetricKeyUnwrapper {
    private Map extraMappings = new HashMap();
    private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
    private PrivateKey privKey;
    private boolean unwrappedKeyMustBeEncodable;

    public JceAsymmetricKeyUnwrapper(AlgorithmIdentifier algorithmIdentifier, PrivateKey privateKey) {
        super(algorithmIdentifier);
        this.privKey = privateKey;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0043, code lost:
        if (r2.length != 0) goto L_0x0046;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005b A[SYNTHETIC, Splitter:B:21:0x005b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.operator.GenericKey generateUnwrappedKey(org.bouncycastle.asn1.x509.AlgorithmIdentifier r6, byte[] r7) throws org.bouncycastle.operator.OperatorException {
        /*
            r5 = this;
            org.bouncycastle.operator.jcajce.OperatorHelper r0 = r5.helper     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r1 = r5.getAlgorithmIdentifier()     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r1.getAlgorithm()     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            java.util.Map r2 = r5.extraMappings     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            javax.crypto.Cipher r0 = r0.createAsymmetricWrapper(r1, r2)     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            org.bouncycastle.operator.jcajce.OperatorHelper r1 = r5.helper     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r2 = r5.getAlgorithmIdentifier()     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            java.security.AlgorithmParameters r1 = r1.createAlgorithmParameters(r2)     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            r2 = 4
            r3 = 0
            if (r1 == 0) goto L_0x0024
            java.security.PrivateKey r4 = r5.privKey     // Catch:{ GeneralSecurityException -> 0x0058, IllegalStateException -> 0x0056, UnsupportedOperationException -> 0x0054, ProviderException -> 0x0052 }
            r0.init(r2, r4, r1)     // Catch:{ GeneralSecurityException -> 0x0058, IllegalStateException -> 0x0056, UnsupportedOperationException -> 0x0054, ProviderException -> 0x0052 }
            goto L_0x0029
        L_0x0024:
            java.security.PrivateKey r1 = r5.privKey     // Catch:{ GeneralSecurityException -> 0x0058, IllegalStateException -> 0x0056, UnsupportedOperationException -> 0x0054, ProviderException -> 0x0052 }
            r0.init(r2, r1)     // Catch:{ GeneralSecurityException -> 0x0058, IllegalStateException -> 0x0056, UnsupportedOperationException -> 0x0054, ProviderException -> 0x0052 }
        L_0x0029:
            org.bouncycastle.operator.jcajce.OperatorHelper r1 = r5.helper     // Catch:{ GeneralSecurityException -> 0x0058, IllegalStateException -> 0x0056, UnsupportedOperationException -> 0x0054, ProviderException -> 0x0052 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = r6.getAlgorithm()     // Catch:{ GeneralSecurityException -> 0x0058, IllegalStateException -> 0x0056, UnsupportedOperationException -> 0x0054, ProviderException -> 0x0052 }
            java.lang.String r1 = r1.getKeyAlgorithmName(r2)     // Catch:{ GeneralSecurityException -> 0x0058, IllegalStateException -> 0x0056, UnsupportedOperationException -> 0x0054, ProviderException -> 0x0052 }
            r2 = 3
            java.security.Key r1 = r0.unwrap(r7, r1, r2)     // Catch:{ GeneralSecurityException -> 0x0058, IllegalStateException -> 0x0056, UnsupportedOperationException -> 0x0054, ProviderException -> 0x0052 }
            boolean r2 = r5.unwrappedKeyMustBeEncodable     // Catch:{ GeneralSecurityException -> 0x0050, IllegalStateException -> 0x004e, UnsupportedOperationException -> 0x004c, ProviderException -> 0x004a }
            if (r2 == 0) goto L_0x0046
            byte[] r2 = r1.getEncoded()     // Catch:{ Exception -> 0x0048 }
            if (r2 == 0) goto L_0x0059
            int r2 = r2.length     // Catch:{ Exception -> 0x0048 }
            if (r2 != 0) goto L_0x0046
            goto L_0x0059
        L_0x0046:
            r3 = r1
            goto L_0x0059
        L_0x0048:
            r1 = move-exception
            goto L_0x0059
        L_0x004a:
            r2 = move-exception
            goto L_0x0046
        L_0x004c:
            r2 = move-exception
            goto L_0x0046
        L_0x004e:
            r2 = move-exception
            goto L_0x0046
        L_0x0050:
            r2 = move-exception
            goto L_0x0046
        L_0x0052:
            r1 = move-exception
            goto L_0x0059
        L_0x0054:
            r1 = move-exception
            goto L_0x0059
        L_0x0056:
            r1 = move-exception
            goto L_0x0059
        L_0x0058:
            r1 = move-exception
        L_0x0059:
            if (r3 != 0) goto L_0x0072
            java.security.PrivateKey r1 = r5.privKey     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            r2 = 2
            r0.init(r2, r1)     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            javax.crypto.spec.SecretKeySpec r3 = new javax.crypto.spec.SecretKeySpec     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            byte[] r7 = r0.doFinal(r7)     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r6.getAlgorithm()     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            java.lang.String r0 = r0.getId()     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            r3.<init>(r7, r0)     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
        L_0x0072:
            org.bouncycastle.operator.jcajce.JceGenericKey r7 = new org.bouncycastle.operator.jcajce.JceGenericKey     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            r7.<init>(r6, r3)     // Catch:{ InvalidKeyException -> 0x00b4, IllegalBlockSizeException -> 0x0096, BadPaddingException -> 0x0078 }
            return r7
        L_0x0078:
            r6 = move-exception
            org.bouncycastle.operator.OperatorException r7 = new org.bouncycastle.operator.OperatorException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "bad padding: "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = r6.getMessage()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0, r6)
            throw r7
        L_0x0096:
            r6 = move-exception
            org.bouncycastle.operator.OperatorException r7 = new org.bouncycastle.operator.OperatorException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "illegal blocksize: "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = r6.getMessage()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0, r6)
            throw r7
        L_0x00b4:
            r6 = move-exception
            org.bouncycastle.operator.OperatorException r7 = new org.bouncycastle.operator.OperatorException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "key invalid: "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = r6.getMessage()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0, r6)
            goto L_0x00d3
        L_0x00d2:
            throw r7
        L_0x00d3:
            goto L_0x00d2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.operator.jcajce.JceAsymmetricKeyUnwrapper.generateUnwrappedKey(org.bouncycastle.asn1.x509.AlgorithmIdentifier, byte[]):org.bouncycastle.operator.GenericKey");
    }

    public JceAsymmetricKeyUnwrapper setAlgorithmMapping(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        this.extraMappings.put(aSN1ObjectIdentifier, str);
        return this;
    }

    public JceAsymmetricKeyUnwrapper setMustProduceEncodableUnwrappedKey(boolean z) {
        this.unwrappedKeyMustBeEncodable = z;
        return this;
    }

    public JceAsymmetricKeyUnwrapper setProvider(String str) {
        this.helper = new OperatorHelper(new NamedJcaJceHelper(str));
        return this;
    }

    public JceAsymmetricKeyUnwrapper setProvider(Provider provider) {
        this.helper = new OperatorHelper(new ProviderJcaJceHelper(provider));
        return this;
    }
}
