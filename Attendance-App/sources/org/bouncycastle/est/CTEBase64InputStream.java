package org.bouncycastle.est;

import com.google.android.material.internal.ViewUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.UByte;

class CTEBase64InputStream extends InputStream {
    protected final byte[] data = new byte[ViewUtils.EDGE_TO_EDGE_FLAGS];
    protected final OutputStream dataOutputStream;
    protected boolean end;
    protected final Long max;
    protected final byte[] rawBuf = new byte[1024];
    protected long read;

    /* renamed from: rp */
    protected int f639rp;
    protected final InputStream src;

    /* renamed from: wp */
    protected int f640wp;

    public CTEBase64InputStream(InputStream inputStream, Long l) {
        this.src = inputStream;
        this.dataOutputStream = new OutputStream() {
            public void write(int i) throws IOException {
                byte[] bArr = CTEBase64InputStream.this.data;
                CTEBase64InputStream cTEBase64InputStream = CTEBase64InputStream.this;
                int i2 = cTEBase64InputStream.f640wp;
                cTEBase64InputStream.f640wp = i2 + 1;
                bArr[i2] = (byte) i;
            }
        };
        this.max = l;
    }

    public void close() throws IOException {
        this.src.close();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0054 A[SYNTHETIC, Splitter:B:21:0x0054] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x007c A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int pullFromSrc() throws java.io.IOException {
        /*
            r11 = this;
            long r0 = r11.read
            java.lang.Long r2 = r11.max
            long r2 = r2.longValue()
            r4 = -1
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 < 0) goto L_0x000e
            return r4
        L_0x000e:
            r0 = 0
            r1 = 0
        L_0x0010:
            java.io.InputStream r2 = r11.src
            int r2 = r2.read()
            r3 = 33
            r5 = 10
            r6 = 1
            if (r2 >= r3) goto L_0x002d
            r3 = 13
            if (r2 == r3) goto L_0x002d
            if (r2 != r5) goto L_0x0025
            goto L_0x002d
        L_0x0025:
            if (r2 < 0) goto L_0x003d
            long r8 = r11.read
            long r8 = r8 + r6
            r11.read = r8
            goto L_0x003d
        L_0x002d:
            byte[] r3 = r11.rawBuf
            int r8 = r3.length
            if (r1 >= r8) goto L_0x007c
            int r8 = r1 + 1
            byte r9 = (byte) r2
            r3[r1] = r9
            long r9 = r11.read
            long r9 = r9 + r6
            r11.read = r9
            r1 = r8
        L_0x003d:
            if (r2 <= r4) goto L_0x0052
            byte[] r3 = r11.rawBuf
            int r3 = r3.length
            if (r1 >= r3) goto L_0x0052
            if (r2 == r5) goto L_0x0052
            long r5 = r11.read
            java.lang.Long r3 = r11.max
            long r7 = r3.longValue()
            int r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r3 < 0) goto L_0x0010
        L_0x0052:
            if (r1 <= 0) goto L_0x0076
            byte[] r2 = r11.rawBuf     // Catch:{ Exception -> 0x005c }
            java.io.OutputStream r3 = r11.dataOutputStream     // Catch:{ Exception -> 0x005c }
            org.bouncycastle.util.encoders.Base64.decode(r2, r0, r1, r3)     // Catch:{ Exception -> 0x005c }
            goto L_0x0079
        L_0x005c:
            r0 = move-exception
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Decode Base64 Content-Transfer-Encoding: "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L_0x0076:
            if (r2 != r4) goto L_0x0079
            return r4
        L_0x0079:
            int r0 = r11.f640wp
            return r0
        L_0x007c:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Content Transfer Encoding, base64 line length > 1024"
            r0.<init>(r1)
            goto L_0x0085
        L_0x0084:
            throw r0
        L_0x0085:
            goto L_0x0084
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.est.CTEBase64InputStream.pullFromSrc():int");
    }

    public int read() throws IOException {
        if (this.f639rp == this.f640wp) {
            this.f639rp = 0;
            this.f640wp = 0;
            int pullFromSrc = pullFromSrc();
            if (pullFromSrc == -1) {
                return pullFromSrc;
            }
        }
        byte[] bArr = this.data;
        int i = this.f639rp;
        this.f639rp = i + 1;
        return bArr[i] & UByte.MAX_VALUE;
    }
}
