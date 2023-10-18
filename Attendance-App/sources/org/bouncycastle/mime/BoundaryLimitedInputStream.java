package org.bouncycastle.mime;

import java.io.InputStream;
import org.bouncycastle.util.Strings;

public class BoundaryLimitedInputStream extends InputStream {
    private final byte[] boundary;
    private final byte[] buf;
    private int bufOff = 0;
    private boolean ended = false;
    private int index = 0;
    private int lastI;
    private final InputStream src;

    public BoundaryLimitedInputStream(InputStream inputStream, String str) {
        this.src = inputStream;
        this.boundary = Strings.toByteArray(str);
        this.buf = new byte[(str.length() + 3)];
        this.bufOff = 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read() throws java.io.IOException {
        /*
            r8 = this;
            boolean r0 = r8.ended
            r1 = -1
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            int r0 = r8.index
            int r2 = r8.bufOff
            r3 = 0
            if (r0 >= r2) goto L_0x001f
            byte[] r4 = r8.buf
            int r5 = r0 + 1
            r8.index = r5
            byte r0 = r4[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            if (r5 >= r2) goto L_0x001a
            return r0
        L_0x001a:
            r8.bufOff = r3
            r8.index = r3
            goto L_0x0025
        L_0x001f:
            java.io.InputStream r0 = r8.src
            int r0 = r0.read()
        L_0x0025:
            r8.lastI = r0
            if (r0 >= 0) goto L_0x002a
            return r1
        L_0x002a:
            r2 = 13
            r4 = 10
            if (r0 == r2) goto L_0x0032
            if (r0 != r4) goto L_0x00af
        L_0x0032:
            r8.index = r3
            if (r0 != r2) goto L_0x0048
            java.io.InputStream r2 = r8.src
            int r2 = r2.read()
            if (r2 != r4) goto L_0x004e
            byte[] r2 = r8.buf
            int r3 = r8.bufOff
            int r5 = r3 + 1
            r8.bufOff = r5
            r2[r3] = r4
        L_0x0048:
            java.io.InputStream r2 = r8.src
            int r2 = r2.read()
        L_0x004e:
            r3 = 45
            if (r2 != r3) goto L_0x0062
            byte[] r2 = r8.buf
            int r4 = r8.bufOff
            int r5 = r4 + 1
            r8.bufOff = r5
            r2[r4] = r3
            java.io.InputStream r2 = r8.src
            int r2 = r2.read()
        L_0x0062:
            if (r2 != r3) goto L_0x00a2
            byte[] r2 = r8.buf
            int r4 = r8.bufOff
            int r5 = r4 + 1
            r8.bufOff = r5
            r2[r4] = r3
        L_0x006e:
            int r2 = r8.bufOff
            int r2 = r2 - r5
            byte[] r3 = r8.boundary
            int r3 = r3.length
            r4 = 1
            if (r2 == r3) goto L_0x0097
            java.io.InputStream r2 = r8.src
            int r2 = r2.read()
            if (r2 < 0) goto L_0x0097
            byte[] r3 = r8.buf
            int r6 = r8.bufOff
            byte r2 = (byte) r2
            r3[r6] = r2
            byte[] r3 = r8.boundary
            int r7 = r6 - r5
            byte r3 = r3[r7]
            if (r2 == r3) goto L_0x0092
            int r6 = r6 + r4
            r8.bufOff = r6
            goto L_0x0097
        L_0x0092:
            int r6 = r6 + 1
            r8.bufOff = r6
            goto L_0x006e
        L_0x0097:
            int r2 = r8.bufOff
            int r2 = r2 - r5
            byte[] r3 = r8.boundary
            int r3 = r3.length
            if (r2 != r3) goto L_0x00af
            r8.ended = r4
            return r1
        L_0x00a2:
            if (r2 < 0) goto L_0x00af
            byte[] r1 = r8.buf
            int r3 = r8.bufOff
            int r4 = r3 + 1
            r8.bufOff = r4
            byte r2 = (byte) r2
            r1[r3] = r2
        L_0x00af:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.mime.BoundaryLimitedInputStream.read():int");
    }
}
