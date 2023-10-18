package org.bouncycastle.openssl.p020bc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.util.Integers;

/* renamed from: org.bouncycastle.openssl.bc.PEMUtilities */
class PEMUtilities {
    private static final Map KEYSIZES;
    private static final Set PKCS5_SCHEME_1;
    private static final Set PKCS5_SCHEME_2;

    static {
        HashMap hashMap = new HashMap();
        KEYSIZES = hashMap;
        HashSet hashSet = new HashSet();
        PKCS5_SCHEME_1 = hashSet;
        HashSet hashSet2 = new HashSet();
        PKCS5_SCHEME_2 = hashSet2;
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD2AndRC2_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC);
        hashSet.add(PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC);
        hashSet2.add(PKCSObjectIdentifiers.id_PBES2);
        hashSet2.add(PKCSObjectIdentifiers.des_EDE3_CBC);
        hashSet2.add(NISTObjectIdentifiers.id_aes128_CBC);
        hashSet2.add(NISTObjectIdentifiers.id_aes192_CBC);
        hashSet2.add(NISTObjectIdentifiers.id_aes256_CBC);
        hashMap.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), Integers.valueOf(192));
        hashMap.put(NISTObjectIdentifiers.id_aes128_CBC.getId(), Integers.valueOf(128));
        hashMap.put(NISTObjectIdentifiers.id_aes192_CBC.getId(), Integers.valueOf(192));
        hashMap.put(NISTObjectIdentifiers.id_aes256_CBC.getId(), Integers.valueOf(256));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4.getId(), Integers.valueOf(128));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, Integers.valueOf(40));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, Integers.valueOf(128));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, Integers.valueOf(192));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC, Integers.valueOf(128));
        hashMap.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC, Integers.valueOf(40));
    }

    PEMUtilities() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x0132 A[SYNTHETIC, Splitter:B:59:0x0132] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0138 A[Catch:{ Exception -> 0x016c }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0140 A[Catch:{ Exception -> 0x016c }] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0144 A[Catch:{ Exception -> 0x016c }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0165 A[Catch:{ Exception -> 0x016c }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0166 A[Catch:{ Exception -> 0x016c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static byte[] crypt(boolean r16, byte[] r17, char[] r18, java.lang.String r19, byte[] r20) throws org.bouncycastle.openssl.PEMException {
        /*
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            org.bouncycastle.crypto.paddings.PKCS7Padding r5 = new org.bouncycastle.crypto.paddings.PKCS7Padding
            r5.<init>()
            java.lang.String r6 = "-CFB"
            boolean r6 = r3.endsWith(r6)
            java.lang.String r7 = "CFB"
            r8 = 0
            java.lang.String r9 = "CBC"
            if (r6 == 0) goto L_0x001f
            r6 = r7
            r5 = r8
            goto L_0x0020
        L_0x001f:
            r6 = r9
        L_0x0020:
            java.lang.String r10 = "-ECB"
            boolean r10 = r3.endsWith(r10)
            java.lang.String r11 = "DES-EDE3"
            java.lang.String r12 = "DES-EDE"
            if (r10 != 0) goto L_0x003b
            boolean r10 = r12.equals(r3)
            if (r10 != 0) goto L_0x003b
            boolean r10 = r11.equals(r3)
            if (r10 == 0) goto L_0x0039
            goto L_0x003b
        L_0x0039:
            r10 = r4
            goto L_0x003e
        L_0x003b:
            java.lang.String r6 = "ECB"
            r10 = r8
        L_0x003e:
            java.lang.String r13 = "-OFB"
            boolean r13 = r3.endsWith(r13)
            java.lang.String r14 = "OFB"
            if (r13 == 0) goto L_0x004a
            r6 = r14
            goto L_0x004b
        L_0x004a:
            r8 = r5
        L_0x004b:
            boolean r5 = r3.startsWith(r12)
            r12 = 0
            r13 = 8
            if (r5 == 0) goto L_0x0067
            boolean r3 = r3.startsWith(r11)
            r3 = r3 ^ 1
            r5 = 24
            org.bouncycastle.crypto.params.KeyParameter r2 = getKey(r2, r5, r4, r3)
            org.bouncycastle.crypto.engines.DESedeEngine r3 = new org.bouncycastle.crypto.engines.DESedeEngine
            r3.<init>()
            goto L_0x00ff
        L_0x0067:
            java.lang.String r5 = "DES-"
            boolean r5 = r3.startsWith(r5)
            if (r5 == 0) goto L_0x007a
            org.bouncycastle.crypto.params.KeyParameter r2 = getKey(r2, r13, r4)
            org.bouncycastle.crypto.engines.DESEngine r3 = new org.bouncycastle.crypto.engines.DESEngine
            r3.<init>()
            goto L_0x00ff
        L_0x007a:
            java.lang.String r5 = "BF-"
            boolean r5 = r3.startsWith(r5)
            if (r5 == 0) goto L_0x008f
            r3 = 16
            org.bouncycastle.crypto.params.KeyParameter r2 = getKey(r2, r3, r4)
            org.bouncycastle.crypto.engines.BlowfishEngine r3 = new org.bouncycastle.crypto.engines.BlowfishEngine
            r3.<init>()
            goto L_0x00ff
        L_0x008f:
            java.lang.String r5 = "RC2-"
            boolean r5 = r3.startsWith(r5)
            r11 = 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x00c6
            java.lang.String r5 = "RC2-40-"
            boolean r5 = r3.startsWith(r5)
            if (r5 == 0) goto L_0x00a4
            r11 = 40
            goto L_0x00ae
        L_0x00a4:
            java.lang.String r5 = "RC2-64-"
            boolean r3 = r3.startsWith(r5)
            if (r3 == 0) goto L_0x00ae
            r11 = 64
        L_0x00ae:
            org.bouncycastle.crypto.params.RC2Parameters r3 = new org.bouncycastle.crypto.params.RC2Parameters
            int r5 = r11 / 8
            org.bouncycastle.crypto.params.KeyParameter r2 = getKey(r2, r5, r4)
            byte[] r2 = r2.getKey()
            r3.<init>(r2, r11)
            org.bouncycastle.crypto.engines.RC2Engine r2 = new org.bouncycastle.crypto.engines.RC2Engine
            r2.<init>()
            r15 = r3
            r3 = r2
            r2 = r15
            goto L_0x00ff
        L_0x00c6:
            java.lang.String r5 = "AES-"
            boolean r5 = r3.startsWith(r5)
            if (r5 == 0) goto L_0x018e
            int r5 = r4.length
            if (r5 <= r13) goto L_0x00d7
            byte[] r5 = new byte[r13]
            java.lang.System.arraycopy(r4, r12, r5, r12, r13)
            r4 = r5
        L_0x00d7:
            java.lang.String r5 = "AES-128-"
            boolean r5 = r3.startsWith(r5)
            if (r5 == 0) goto L_0x00e0
            goto L_0x00f5
        L_0x00e0:
            java.lang.String r5 = "AES-192-"
            boolean r5 = r3.startsWith(r5)
            if (r5 == 0) goto L_0x00eb
            r11 = 192(0xc0, float:2.69E-43)
            goto L_0x00f5
        L_0x00eb:
            java.lang.String r5 = "AES-256-"
            boolean r5 = r3.startsWith(r5)
            if (r5 == 0) goto L_0x0175
            r11 = 256(0x100, float:3.59E-43)
        L_0x00f5:
            int r11 = r11 / r13
            org.bouncycastle.crypto.params.KeyParameter r2 = getKey(r2, r11, r4)
            org.bouncycastle.crypto.engines.AESEngine r3 = new org.bouncycastle.crypto.engines.AESEngine
            r3.<init>()
        L_0x00ff:
            boolean r4 = r6.equals(r9)
            if (r4 == 0) goto L_0x010c
            org.bouncycastle.crypto.modes.CBCBlockCipher r4 = new org.bouncycastle.crypto.modes.CBCBlockCipher
            r4.<init>(r3)
        L_0x010a:
            r3 = r4
            goto L_0x0130
        L_0x010c:
            boolean r4 = r6.equals(r7)
            if (r4 == 0) goto L_0x011e
            org.bouncycastle.crypto.modes.CFBBlockCipher r4 = new org.bouncycastle.crypto.modes.CFBBlockCipher
            int r5 = r3.getBlockSize()
            int r5 = r5 * 8
            r4.<init>(r3, r5)
            goto L_0x010a
        L_0x011e:
            boolean r4 = r6.equals(r14)
            if (r4 == 0) goto L_0x0130
            org.bouncycastle.crypto.modes.OFBBlockCipher r4 = new org.bouncycastle.crypto.modes.OFBBlockCipher
            int r5 = r3.getBlockSize()
            int r5 = r5 * 8
            r4.<init>(r3, r5)
            goto L_0x010a
        L_0x0130:
            if (r8 != 0) goto L_0x0138
            org.bouncycastle.crypto.BufferedBlockCipher r4 = new org.bouncycastle.crypto.BufferedBlockCipher     // Catch:{ Exception -> 0x016c }
            r4.<init>(r3)     // Catch:{ Exception -> 0x016c }
            goto L_0x013d
        L_0x0138:
            org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher r4 = new org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher     // Catch:{ Exception -> 0x016c }
            r4.<init>(r3, r8)     // Catch:{ Exception -> 0x016c }
        L_0x013d:
            r6 = r4
            if (r10 != 0) goto L_0x0144
            r6.init(r0, r2)     // Catch:{ Exception -> 0x016c }
            goto L_0x014c
        L_0x0144:
            org.bouncycastle.crypto.params.ParametersWithIV r3 = new org.bouncycastle.crypto.params.ParametersWithIV     // Catch:{ Exception -> 0x016c }
            r3.<init>(r2, r10)     // Catch:{ Exception -> 0x016c }
            r6.init(r0, r3)     // Catch:{ Exception -> 0x016c }
        L_0x014c:
            int r0 = r1.length     // Catch:{ Exception -> 0x016c }
            int r7 = r6.getOutputSize(r0)     // Catch:{ Exception -> 0x016c }
            byte[] r8 = new byte[r7]     // Catch:{ Exception -> 0x016c }
            r2 = 0
            int r3 = r1.length     // Catch:{ Exception -> 0x016c }
            r5 = 0
            r0 = r6
            r1 = r17
            r4 = r8
            int r0 = r0.processBytes(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x016c }
            int r1 = r6.doFinal(r8, r0)     // Catch:{ Exception -> 0x016c }
            int r0 = r0 + r1
            if (r0 != r7) goto L_0x0166
            return r8
        L_0x0166:
            byte[] r1 = new byte[r0]     // Catch:{ Exception -> 0x016c }
            java.lang.System.arraycopy(r8, r12, r1, r12, r0)     // Catch:{ Exception -> 0x016c }
            return r1
        L_0x016c:
            r0 = move-exception
            org.bouncycastle.openssl.EncryptionException r1 = new org.bouncycastle.openssl.EncryptionException
            java.lang.String r2 = "exception using cipher - please check password and data."
            r1.<init>(r2, r0)
            throw r1
        L_0x0175:
            org.bouncycastle.openssl.EncryptionException r0 = new org.bouncycastle.openssl.EncryptionException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "unknown AES encryption with private key: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x018e:
            org.bouncycastle.openssl.EncryptionException r0 = new org.bouncycastle.openssl.EncryptionException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "unknown encryption with private key: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            goto L_0x01a8
        L_0x01a7:
            throw r0
        L_0x01a8:
            goto L_0x01a7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.openssl.p020bc.PEMUtilities.crypt(boolean, byte[], char[], java.lang.String, byte[]):byte[]");
    }

    public static KeyParameter generateSecretKeyForPKCS5Scheme2(String str, char[] cArr, byte[] bArr, int i) {
        PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(new SHA1Digest());
        pKCS5S2ParametersGenerator.init(PBEParametersGenerator.PKCS5PasswordToBytes(cArr), bArr, i);
        return (KeyParameter) pKCS5S2ParametersGenerator.generateDerivedParameters(getKeySize(str));
    }

    private static KeyParameter getKey(char[] cArr, int i, byte[] bArr) throws PEMException {
        return getKey(cArr, i, bArr, false);
    }

    private static KeyParameter getKey(char[] cArr, int i, byte[] bArr, boolean z) throws PEMException {
        OpenSSLPBEParametersGenerator openSSLPBEParametersGenerator = new OpenSSLPBEParametersGenerator();
        openSSLPBEParametersGenerator.init(PBEParametersGenerator.PKCS5PasswordToBytes(cArr), bArr, 1);
        KeyParameter keyParameter = (KeyParameter) openSSLPBEParametersGenerator.generateDerivedParameters(i * 8);
        if (!z || keyParameter.getKey().length != 24) {
            return keyParameter;
        }
        byte[] key = keyParameter.getKey();
        System.arraycopy(key, 0, key, 16, 8);
        return new KeyParameter(key);
    }

    static int getKeySize(String str) {
        Map map = KEYSIZES;
        if (map.containsKey(str)) {
            return ((Integer) map.get(str)).intValue();
        }
        throw new IllegalStateException("no key size for algorithm: " + str);
    }

    public static boolean isPKCS12(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return aSN1ObjectIdentifier.getId().startsWith(PKCSObjectIdentifiers.pkcs_12PbeIds.getId());
    }

    static boolean isPKCS5Scheme1(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return PKCS5_SCHEME_1.contains(aSN1ObjectIdentifier);
    }

    static boolean isPKCS5Scheme2(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return PKCS5_SCHEME_2.contains(aSN1ObjectIdentifier);
    }
}
