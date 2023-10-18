package kotlin.p002io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0004H\u0002J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0010H\u0002J\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\u0010\u0010#\u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013X\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, mo113d2 = {"Lkotlin/io/LineReader;", "", "()V", "BUFFER_SIZE", "", "byteBuf", "Ljava/nio/ByteBuffer;", "bytes", "", "charBuf", "Ljava/nio/CharBuffer;", "chars", "", "decoder", "Ljava/nio/charset/CharsetDecoder;", "directEOL", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "compactBytes", "decode", "endOfInput", "decodeEndOfInput", "nBytes", "nChars", "readLine", "", "inputStream", "Ljava/io/InputStream;", "charset", "Ljava/nio/charset/Charset;", "resetAll", "", "trimStringBuilder", "updateCharset", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: kotlin.io.LineReader */
/* compiled from: Console.kt */
public final class LineReader {
    private static final int BUFFER_SIZE = 32;
    public static final LineReader INSTANCE = new LineReader();
    private static final ByteBuffer byteBuf;
    private static final byte[] bytes;
    private static final CharBuffer charBuf;
    private static final char[] chars;
    private static CharsetDecoder decoder;
    private static boolean directEOL;

    /* renamed from: sb */
    private static final StringBuilder f1664sb = new StringBuilder();

    private LineReader() {
    }

    static {
        byte[] bArr = new byte[32];
        bytes = bArr;
        char[] cArr = new char[32];
        chars = cArr;
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        Intrinsics.checkNotNullExpressionValue(wrap, "wrap(bytes)");
        byteBuf = wrap;
        CharBuffer wrap2 = CharBuffer.wrap(cArr);
        Intrinsics.checkNotNullExpressionValue(wrap2, "wrap(chars)");
        charBuf = wrap2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003d, code lost:
        if (f1664sb.length() != 0) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003f, code lost:
        r6 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0041, code lost:
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0042, code lost:
        if (r6 == false) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
        if (r0 != 0) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0046, code lost:
        if (r2 != 0) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r1 = decodeEndOfInput(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0020, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r0.charset(), (java.lang.Object) r13) == false) goto L_0x0022;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized java.lang.String readLine(java.io.InputStream r12, java.nio.charset.Charset r13) {
        /*
            r11 = this;
            monitor-enter(r11)
            java.lang.String r0 = "inputStream"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r12, r0)     // Catch:{ all -> 0x00d2 }
            java.lang.String r0 = "charset"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r13, r0)     // Catch:{ all -> 0x00d2 }
            java.nio.charset.CharsetDecoder r0 = decoder     // Catch:{ all -> 0x00d2 }
            r1 = 0
            if (r0 == 0) goto L_0x0022
            if (r0 != 0) goto L_0x0018
            java.lang.String r0 = "decoder"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)     // Catch:{ all -> 0x00d2 }
            r0 = r1
        L_0x0018:
            java.nio.charset.Charset r0 = r0.charset()     // Catch:{ all -> 0x00d2 }
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r0, (java.lang.Object) r13)     // Catch:{ all -> 0x00d2 }
            if (r0 != 0) goto L_0x0025
        L_0x0022:
            r11.updateCharset(r13)     // Catch:{ all -> 0x00d2 }
        L_0x0025:
            r0 = 0
            r2 = 0
        L_0x0027:
            int r3 = r12.read()     // Catch:{ all -> 0x00d2 }
            r4 = 32
            r5 = 10
            r6 = -1
            r7 = 1
            r8 = 0
            if (r3 != r6) goto L_0x004f
            java.lang.StringBuilder r6 = f1664sb     // Catch:{ all -> 0x00d2 }
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6     // Catch:{ all -> 0x00d2 }
            int r6 = r6.length()     // Catch:{ all -> 0x00d2 }
            if (r6 != 0) goto L_0x0041
            r6 = 1
            goto L_0x0042
        L_0x0041:
            r6 = 0
        L_0x0042:
            if (r6 == 0) goto L_0x004a
            if (r0 != 0) goto L_0x004a
            if (r2 != 0) goto L_0x004a
            monitor-exit(r11)
            return r1
        L_0x004a:
            int r1 = r11.decodeEndOfInput(r0, r2)     // Catch:{ all -> 0x00d2 }
            goto L_0x007f
        L_0x004f:
            byte[] r6 = bytes     // Catch:{ all -> 0x00d2 }
            int r9 = r0 + 1
            byte r10 = (byte) r3     // Catch:{ all -> 0x00d2 }
            r6[r0] = r10     // Catch:{ all -> 0x00d2 }
            if (r3 == r5) goto L_0x0061
            if (r9 == r4) goto L_0x0061
            boolean r0 = directEOL     // Catch:{ all -> 0x00d2 }
            if (r0 != 0) goto L_0x005f
            goto L_0x0061
        L_0x005f:
            r0 = r9
            goto L_0x0027
        L_0x0061:
            java.nio.ByteBuffer r0 = byteBuf     // Catch:{ all -> 0x00d2 }
            r0.limit(r9)     // Catch:{ all -> 0x00d2 }
            java.nio.CharBuffer r6 = charBuf     // Catch:{ all -> 0x00d2 }
            r6.position(r2)     // Catch:{ all -> 0x00d2 }
            int r6 = r11.decode(r8)     // Catch:{ all -> 0x00d2 }
            r2 = r6
            if (r2 <= 0) goto L_0x00cc
            char[] r6 = chars     // Catch:{ all -> 0x00d2 }
            int r10 = r2 + -1
            char r6 = r6[r10]     // Catch:{ all -> 0x00d2 }
            if (r6 != r5) goto L_0x00cc
            r0.position(r8)     // Catch:{ all -> 0x00d2 }
            r1 = r2
            r0 = r9
        L_0x007f:
            if (r1 <= 0) goto L_0x0097
            char[] r2 = chars     // Catch:{ all -> 0x00d2 }
            int r3 = r1 + -1
            char r3 = r2[r3]     // Catch:{ all -> 0x00d2 }
            if (r3 != r5) goto L_0x0097
            int r1 = r1 + -1
            if (r1 <= 0) goto L_0x0097
            int r3 = r1 + -1
            char r2 = r2[r3]     // Catch:{ all -> 0x00d2 }
            r3 = 13
            if (r2 != r3) goto L_0x0097
            int r1 = r1 + -1
        L_0x0097:
            java.lang.StringBuilder r2 = f1664sb     // Catch:{ all -> 0x00d2 }
            r3 = r2
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ all -> 0x00d2 }
            int r3 = r3.length()     // Catch:{ all -> 0x00d2 }
            if (r3 != 0) goto L_0x00a3
            goto L_0x00a4
        L_0x00a3:
            r7 = 0
        L_0x00a4:
            if (r7 == 0) goto L_0x00af
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x00d2 }
            char[] r3 = chars     // Catch:{ all -> 0x00d2 }
            r2.<init>(r3, r8, r1)     // Catch:{ all -> 0x00d2 }
            monitor-exit(r11)
            return r2
        L_0x00af:
            char[] r3 = chars     // Catch:{ all -> 0x00d2 }
            r2.append(r3, r8, r1)     // Catch:{ all -> 0x00d2 }
            java.lang.String r3 = r2.toString()     // Catch:{ all -> 0x00d2 }
            java.lang.String r5 = "sb.toString()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)     // Catch:{ all -> 0x00d2 }
            int r5 = r2.length()     // Catch:{ all -> 0x00d2 }
            if (r5 <= r4) goto L_0x00c7
            r11.trimStringBuilder()     // Catch:{ all -> 0x00d2 }
        L_0x00c7:
            r2.setLength(r8)     // Catch:{ all -> 0x00d2 }
            monitor-exit(r11)
            return r3
        L_0x00cc:
            int r0 = r11.compactBytes()     // Catch:{ all -> 0x00d2 }
            goto L_0x0027
        L_0x00d2:
            r12 = move-exception
            monitor-exit(r11)
            goto L_0x00d6
        L_0x00d5:
            throw r12
        L_0x00d6:
            goto L_0x00d5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.LineReader.readLine(java.io.InputStream, java.nio.charset.Charset):java.lang.String");
    }

    private final int decode(boolean endOfInput) {
        while (true) {
            CharsetDecoder charsetDecoder = decoder;
            if (charsetDecoder == null) {
                Intrinsics.throwUninitializedPropertyAccessException("decoder");
                charsetDecoder = null;
            }
            ByteBuffer byteBuffer = byteBuf;
            CharBuffer charBuffer = charBuf;
            CoderResult coderResult = charsetDecoder.decode(byteBuffer, charBuffer, endOfInput);
            Intrinsics.checkNotNullExpressionValue(coderResult, "decoder.decode(byteBuf, charBuf, endOfInput)");
            if (coderResult.isError()) {
                resetAll();
                coderResult.throwException();
            }
            int nChars = charBuffer.position();
            if (!coderResult.isOverflow()) {
                return nChars;
            }
            StringBuilder sb = f1664sb;
            char[] cArr = chars;
            sb.append(cArr, 0, nChars - 1);
            charBuffer.position(0);
            charBuffer.limit(32);
            charBuffer.put(cArr[nChars - 1]);
        }
    }

    private final int compactBytes() {
        ByteBuffer $this$compactBytes_u24lambda_u2d1 = byteBuf;
        $this$compactBytes_u24lambda_u2d1.compact();
        int position = $this$compactBytes_u24lambda_u2d1.position();
        int i = position;
        $this$compactBytes_u24lambda_u2d1.position(0);
        return position;
    }

    private final int decodeEndOfInput(int nBytes, int nChars) {
        ByteBuffer byteBuffer = byteBuf;
        byteBuffer.limit(nBytes);
        charBuf.position(nChars);
        int decode = decode(true);
        int i = decode;
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            charsetDecoder = null;
        }
        charsetDecoder.reset();
        byteBuffer.position(0);
        return decode;
    }

    private final void updateCharset(Charset charset) {
        CharsetDecoder newDecoder = charset.newDecoder();
        Intrinsics.checkNotNullExpressionValue(newDecoder, "charset.newDecoder()");
        decoder = newDecoder;
        ByteBuffer byteBuffer = byteBuf;
        byteBuffer.clear();
        CharBuffer charBuffer = charBuf;
        charBuffer.clear();
        byteBuffer.put((byte) 10);
        byteBuffer.flip();
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            charsetDecoder = null;
        }
        boolean z = false;
        charsetDecoder.decode(byteBuffer, charBuffer, false);
        if (charBuffer.position() == 1 && charBuffer.get(0) == 10) {
            z = true;
        }
        directEOL = z;
        resetAll();
    }

    private final void resetAll() {
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            charsetDecoder = null;
        }
        charsetDecoder.reset();
        byteBuf.position(0);
        f1664sb.setLength(0);
    }

    private final void trimStringBuilder() {
        StringBuilder sb = f1664sb;
        sb.setLength(32);
        sb.trimToSize();
    }
}
