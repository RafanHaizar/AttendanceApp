package org.bouncycastle.mime;

import java.io.InputStream;

class LineReader {
    private int lastC = -1;
    private final InputStream src;

    LineReader(InputStream inputStream) {
        this.src = inputStream;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0036 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String readLine() throws java.io.IOException {
        /*
            r4 = this;
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            int r1 = r4.lastC
            r2 = 13
            r3 = -1
            if (r1 == r3) goto L_0x0014
            if (r1 != r2) goto L_0x0011
            java.lang.String r0 = ""
            return r0
        L_0x0011:
            r4.lastC = r3
            goto L_0x001a
        L_0x0014:
            java.io.InputStream r1 = r4.src
            int r1 = r1.read()
        L_0x001a:
            r3 = 10
            if (r1 < 0) goto L_0x0026
            if (r1 == r2) goto L_0x0026
            if (r1 == r3) goto L_0x0026
            r0.write(r1)
            goto L_0x0014
        L_0x0026:
            if (r1 != r2) goto L_0x0034
            java.io.InputStream r2 = r4.src
            int r2 = r2.read()
            if (r2 == r3) goto L_0x0034
            if (r2 < 0) goto L_0x0034
            r4.lastC = r2
        L_0x0034:
            if (r1 >= 0) goto L_0x0038
            r0 = 0
            return r0
        L_0x0038:
            byte[] r0 = r0.toByteArray()
            java.lang.String r0 = org.bouncycastle.util.Strings.fromUTF8ByteArray(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.mime.LineReader.readLine():java.lang.String");
    }
}
