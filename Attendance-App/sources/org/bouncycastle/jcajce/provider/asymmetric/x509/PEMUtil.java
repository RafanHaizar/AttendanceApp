package org.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.util.encoders.Base64;

class PEMUtil {
    private final Boundaries[] _supportedBoundaries;

    private class Boundaries {
        private final String _footer;
        private final String _header;

        private Boundaries(String str) {
            this._header = "-----BEGIN " + str + "-----";
            this._footer = "-----END " + str + "-----";
        }

        public boolean isTheExpectedFooter(String str) {
            return str.startsWith(this._footer);
        }

        public boolean isTheExpectedHeader(String str) {
            return str.startsWith(this._header);
        }
    }

    PEMUtil(String str) {
        this._supportedBoundaries = new Boundaries[]{new Boundaries(str), new Boundaries("X509 " + str), new Boundaries("PKCS7")};
    }

    private Boundaries getBoundaries(String str) {
        Boundaries boundaries;
        int i = 0;
        while (true) {
            Boundaries[] boundariesArr = this._supportedBoundaries;
            if (i == boundariesArr.length) {
                return null;
            }
            boundaries = boundariesArr[i];
            if (boundaries.isTheExpectedHeader(str) || boundaries.isTheExpectedFooter(str)) {
                return boundaries;
            }
            i++;
        }
        return boundaries;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0022  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String readLine(java.io.InputStream r6) throws java.io.IOException {
        /*
            r5 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L_0x0005:
            int r1 = r6.read()
            r2 = 10
            r3 = 13
            if (r1 == r3) goto L_0x0018
            if (r1 == r2) goto L_0x0018
            if (r1 < 0) goto L_0x0018
            char r1 = (char) r1
            r0.append(r1)
            goto L_0x0005
        L_0x0018:
            if (r1 < 0) goto L_0x0020
            int r4 = r0.length()
            if (r4 == 0) goto L_0x0005
        L_0x0020:
            if (r1 >= 0) goto L_0x002f
            int r6 = r0.length()
            if (r6 != 0) goto L_0x002a
            r6 = 0
            return r6
        L_0x002a:
            java.lang.String r6 = r0.toString()
            return r6
        L_0x002f:
            if (r1 != r3) goto L_0x0043
            r1 = 1
            r6.mark(r1)
            int r3 = r6.read()
            if (r3 != r2) goto L_0x003e
            r6.mark(r1)
        L_0x003e:
            if (r3 <= 0) goto L_0x0043
            r6.reset()
        L_0x0043:
            java.lang.String r6 = r0.toString()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.readLine(java.io.InputStream):java.lang.String");
    }

    /* access modifiers changed from: package-private */
    public ASN1Sequence readPEMObject(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        Boundaries boundaries = null;
        while (boundaries == null) {
            String readLine = readLine(inputStream);
            if (readLine == null) {
                break;
            }
            boundaries = getBoundaries(readLine);
            if (boundaries != null && !boundaries.isTheExpectedHeader(readLine)) {
                throw new IOException("malformed PEM data: found footer where header was expected");
            }
        }
        if (boundaries != null) {
            Boundaries boundaries2 = null;
            while (boundaries2 == null) {
                String readLine2 = readLine(inputStream);
                if (readLine2 == null) {
                    break;
                }
                boundaries2 = getBoundaries(readLine2);
                if (boundaries2 == null) {
                    stringBuffer.append(readLine2);
                } else if (!boundaries.isTheExpectedFooter(readLine2)) {
                    throw new IOException("malformed PEM data: header/footer mismatch");
                }
            }
            if (boundaries2 == null) {
                throw new IOException("malformed PEM data: no footer found");
            } else if (stringBuffer.length() == 0) {
                return null;
            } else {
                try {
                    return ASN1Sequence.getInstance(Base64.decode(stringBuffer.toString()));
                } catch (Exception e) {
                    throw new IOException("malformed PEM data encountered");
                }
            }
        } else {
            throw new IOException("malformed PEM data: no header found");
        }
    }
}
