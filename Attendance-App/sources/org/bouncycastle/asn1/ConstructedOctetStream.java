package org.bouncycastle.asn1;

import java.io.InputStream;

class ConstructedOctetStream extends InputStream {
    private InputStream _currentStream;
    private boolean _first = true;
    private final ASN1StreamParser _parser;

    ConstructedOctetStream(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read() throws java.io.IOException {
        /*
            r4 = this;
            java.io.InputStream r0 = r4._currentStream
            java.lang.String r1 = "unknown object encountered: "
            r2 = -1
            if (r0 != 0) goto L_0x003a
            boolean r0 = r4._first
            if (r0 != 0) goto L_0x000c
            return r2
        L_0x000c:
            org.bouncycastle.asn1.ASN1StreamParser r0 = r4._parser
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.readObject()
            if (r0 != 0) goto L_0x0015
            return r2
        L_0x0015:
            boolean r3 = r0 instanceof org.bouncycastle.asn1.ASN1OctetStringParser
            if (r3 == 0) goto L_0x001f
            org.bouncycastle.asn1.ASN1OctetStringParser r0 = (org.bouncycastle.asn1.ASN1OctetStringParser) r0
            r3 = 0
            r4._first = r3
            goto L_0x0055
        L_0x001f:
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.Class r0 = r0.getClass()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            r2.<init>(r0)
            throw r2
        L_0x003a:
            java.io.InputStream r0 = r4._currentStream
            int r0 = r0.read()
            if (r0 < 0) goto L_0x0043
            return r0
        L_0x0043:
            org.bouncycastle.asn1.ASN1StreamParser r0 = r4._parser
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.readObject()
            if (r0 != 0) goto L_0x004f
            r0 = 0
            r4._currentStream = r0
            return r2
        L_0x004f:
            boolean r3 = r0 instanceof org.bouncycastle.asn1.ASN1OctetStringParser
            if (r3 == 0) goto L_0x005c
            org.bouncycastle.asn1.ASN1OctetStringParser r0 = (org.bouncycastle.asn1.ASN1OctetStringParser) r0
        L_0x0055:
            java.io.InputStream r0 = r0.getOctetStream()
            r4._currentStream = r0
            goto L_0x003a
        L_0x005c:
            java.io.IOException r2 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.Class r0 = r0.getClass()
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            r2.<init>(r0)
            goto L_0x0078
        L_0x0077:
            throw r2
        L_0x0078:
            goto L_0x0077
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.ConstructedOctetStream.read():int");
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0050 A[EDGE_INSN: B:29:0x0050->B:19:0x0050 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x004f A[SYNTHETIC] */
    public int read(byte[] r7, int r8, int r9) throws java.io.IOException {
        /*
            r6 = this;
            java.io.InputStream r0 = r6._currentStream
            java.lang.String r1 = "unknown object encountered: "
            r2 = 0
            r3 = -1
            if (r0 != 0) goto L_0x0040
            boolean r0 = r6._first
            if (r0 != 0) goto L_0x000d
            return r3
        L_0x000d:
            org.bouncycastle.asn1.ASN1StreamParser r0 = r6._parser
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.readObject()
            if (r0 != 0) goto L_0x0016
            return r3
        L_0x0016:
            boolean r4 = r0 instanceof org.bouncycastle.asn1.ASN1OctetStringParser
            if (r4 == 0) goto L_0x0025
            org.bouncycastle.asn1.ASN1OctetStringParser r0 = (org.bouncycastle.asn1.ASN1OctetStringParser) r0
            r6._first = r2
        L_0x001e:
            java.io.InputStream r0 = r0.getOctetStream()
            r6._currentStream = r0
            goto L_0x0040
        L_0x0025:
            java.io.IOException r7 = new java.io.IOException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r1)
            java.lang.Class r9 = r0.getClass()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            throw r7
        L_0x0040:
            java.io.InputStream r0 = r6._currentStream
            int r4 = r8 + r2
            int r5 = r9 - r2
            int r0 = r0.read(r7, r4, r5)
            if (r0 < 0) goto L_0x0050
            int r2 = r2 + r0
            if (r2 != r9) goto L_0x0040
            return r2
        L_0x0050:
            org.bouncycastle.asn1.ASN1StreamParser r0 = r6._parser
            org.bouncycastle.asn1.ASN1Encodable r0 = r0.readObject()
            if (r0 != 0) goto L_0x0061
            r7 = 0
            r6._currentStream = r7
            r7 = 1
            if (r2 >= r7) goto L_0x005f
            goto L_0x0060
        L_0x005f:
            r3 = r2
        L_0x0060:
            return r3
        L_0x0061:
            boolean r4 = r0 instanceof org.bouncycastle.asn1.ASN1OctetStringParser
            if (r4 == 0) goto L_0x0068
            org.bouncycastle.asn1.ASN1OctetStringParser r0 = (org.bouncycastle.asn1.ASN1OctetStringParser) r0
            goto L_0x001e
        L_0x0068:
            java.io.IOException r7 = new java.io.IOException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r1)
            java.lang.Class r9 = r0.getClass()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.<init>(r8)
            goto L_0x0084
        L_0x0083:
            throw r7
        L_0x0084:
            goto L_0x0083
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.ConstructedOctetStream.read(byte[], int, int):int");
    }
}
