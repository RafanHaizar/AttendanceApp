package com.itextpdf.signatures;

public class BouncyCastleDigest implements IExternalDigest {
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.security.MessageDigest getMessageDigest(java.lang.String r3) throws java.security.GeneralSecurityException {
        /*
            r2 = this;
            java.lang.String r0 = com.itextpdf.signatures.DigestAlgorithms.getAllowedDigest(r3)
            int r1 = r0.hashCode()
            switch(r1) {
                case -2071451550: goto L_0x0075;
                case -2071451547: goto L_0x006b;
                case -1261045977: goto L_0x0060;
                case -1261045976: goto L_0x0056;
                case -1261045975: goto L_0x004b;
                case -1225949696: goto L_0x0041;
                case -1225949695: goto L_0x0037;
                case -1225949694: goto L_0x002d;
                case -1225949693: goto L_0x0023;
                case -308431282: goto L_0x0019;
                case 1044166351: goto L_0x000d;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x007f
        L_0x000d:
            java.lang.String r1 = "1.2.643.2.2.9"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 10
            goto L_0x0080
        L_0x0019:
            java.lang.String r1 = "1.3.14.3.2.26"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 2
            goto L_0x0080
        L_0x0023:
            java.lang.String r1 = "2.16.840.1.101.3.4.2.4"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 3
            goto L_0x0080
        L_0x002d:
            java.lang.String r1 = "2.16.840.1.101.3.4.2.3"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 6
            goto L_0x0080
        L_0x0037:
            java.lang.String r1 = "2.16.840.1.101.3.4.2.2"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 5
            goto L_0x0080
        L_0x0041:
            java.lang.String r1 = "2.16.840.1.101.3.4.2.1"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 4
            goto L_0x0080
        L_0x004b:
            java.lang.String r1 = "1.3.36.3.2.3"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 9
            goto L_0x0080
        L_0x0056:
            java.lang.String r1 = "1.3.36.3.2.2"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 7
            goto L_0x0080
        L_0x0060:
            java.lang.String r1 = "1.3.36.3.2.1"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 8
            goto L_0x0080
        L_0x006b:
            java.lang.String r1 = "1.2.840.113549.2.5"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 1
            goto L_0x0080
        L_0x0075:
            java.lang.String r1 = "1.2.840.113549.2.2"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x000b
            r1 = 0
            goto L_0x0080
        L_0x007f:
            r1 = -1
        L_0x0080:
            switch(r1) {
                case 0: goto L_0x00c5;
                case 1: goto L_0x00bf;
                case 2: goto L_0x00b9;
                case 3: goto L_0x00b3;
                case 4: goto L_0x00ad;
                case 5: goto L_0x00a7;
                case 6: goto L_0x00a1;
                case 7: goto L_0x009b;
                case 8: goto L_0x0095;
                case 9: goto L_0x008f;
                case 10: goto L_0x0089;
                default: goto L_0x0083;
            }
        L_0x0083:
            java.security.NoSuchAlgorithmException r1 = new java.security.NoSuchAlgorithmException
            r1.<init>(r3)
            throw r1
        L_0x0089:
            org.bouncycastle.jcajce.provider.digest.GOST3411$Digest r1 = new org.bouncycastle.jcajce.provider.digest.GOST3411$Digest
            r1.<init>()
            return r1
        L_0x008f:
            org.bouncycastle.jcajce.provider.digest.RIPEMD256$Digest r1 = new org.bouncycastle.jcajce.provider.digest.RIPEMD256$Digest
            r1.<init>()
            return r1
        L_0x0095:
            org.bouncycastle.jcajce.provider.digest.RIPEMD160$Digest r1 = new org.bouncycastle.jcajce.provider.digest.RIPEMD160$Digest
            r1.<init>()
            return r1
        L_0x009b:
            org.bouncycastle.jcajce.provider.digest.RIPEMD128$Digest r1 = new org.bouncycastle.jcajce.provider.digest.RIPEMD128$Digest
            r1.<init>()
            return r1
        L_0x00a1:
            org.bouncycastle.jcajce.provider.digest.SHA512$Digest r1 = new org.bouncycastle.jcajce.provider.digest.SHA512$Digest
            r1.<init>()
            return r1
        L_0x00a7:
            org.bouncycastle.jcajce.provider.digest.SHA384$Digest r1 = new org.bouncycastle.jcajce.provider.digest.SHA384$Digest
            r1.<init>()
            return r1
        L_0x00ad:
            org.bouncycastle.jcajce.provider.digest.SHA256$Digest r1 = new org.bouncycastle.jcajce.provider.digest.SHA256$Digest
            r1.<init>()
            return r1
        L_0x00b3:
            org.bouncycastle.jcajce.provider.digest.SHA224$Digest r1 = new org.bouncycastle.jcajce.provider.digest.SHA224$Digest
            r1.<init>()
            return r1
        L_0x00b9:
            org.bouncycastle.jcajce.provider.digest.SHA1$Digest r1 = new org.bouncycastle.jcajce.provider.digest.SHA1$Digest
            r1.<init>()
            return r1
        L_0x00bf:
            org.bouncycastle.jcajce.provider.digest.MD5$Digest r1 = new org.bouncycastle.jcajce.provider.digest.MD5$Digest
            r1.<init>()
            return r1
        L_0x00c5:
            org.bouncycastle.jcajce.provider.digest.MD2$Digest r1 = new org.bouncycastle.jcajce.provider.digest.MD2$Digest
            r1.<init>()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.signatures.BouncyCastleDigest.getMessageDigest(java.lang.String):java.security.MessageDigest");
    }
}
