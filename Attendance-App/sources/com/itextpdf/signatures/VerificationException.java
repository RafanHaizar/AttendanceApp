package com.itextpdf.signatures;

import java.security.GeneralSecurityException;

public class VerificationException extends GeneralSecurityException {
    private static final long serialVersionUID = 2978604513926438256L;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public VerificationException(java.security.cert.Certificate r4, java.lang.String r5) {
        /*
            r3 = this;
            r0 = 2
            java.lang.Object[] r0 = new java.lang.Object[r0]
            if (r4 != 0) goto L_0x0008
            java.lang.String r1 = "Unknown"
            goto L_0x0013
        L_0x0008:
            r1 = r4
            java.security.cert.X509Certificate r1 = (java.security.cert.X509Certificate) r1
            java.security.Principal r1 = r1.getSubjectDN()
            java.lang.String r1 = r1.getName()
        L_0x0013:
            r2 = 0
            r0[r2] = r1
            r1 = 1
            r0[r1] = r5
            java.lang.String r1 = "Certificate {0} failed: {1}"
            java.lang.String r0 = com.itextpdf.p026io.util.MessageFormatUtil.format(r1, r0)
            r3.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.signatures.VerificationException.<init>(java.security.cert.Certificate, java.lang.String):void");
    }
}
