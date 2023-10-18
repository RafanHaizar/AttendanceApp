package kotlin.p002io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(mo112d1 = {"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\b\u001aB\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010+\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010.\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010/\u001a\u000200*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\b\u0002\u000f\n\u0005\b20\u0001\n\u0006\b\u0011(,0\u0001¨\u00061"}, mo113d2 = {"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Requires newer compiler version to be inlined correctly.", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/io/FilesKt")
/* renamed from: kotlin.io.FilesKt__FileReadWriteKt */
/* compiled from: FileReadWrite.kt */
class FilesKt__FileReadWriteKt extends FilesKt__FilePathComponentsKt {
    static /* synthetic */ InputStreamReader reader$default(File $this$reader_u24default, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter($this$reader_u24default, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new InputStreamReader(new FileInputStream($this$reader_u24default), charset);
    }

    private static final InputStreamReader reader(File $this$reader, Charset charset) {
        Intrinsics.checkNotNullParameter($this$reader, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new InputStreamReader(new FileInputStream($this$reader), charset);
    }

    static /* synthetic */ BufferedReader bufferedReader$default(File $this$bufferedReader_u24default, Charset charset, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i & 2) != 0) {
            bufferSize = 8192;
        }
        Intrinsics.checkNotNullParameter($this$bufferedReader_u24default, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Reader inputStreamReader = new InputStreamReader(new FileInputStream($this$bufferedReader_u24default), charset);
        return inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, bufferSize);
    }

    private static final BufferedReader bufferedReader(File $this$bufferedReader, Charset charset, int bufferSize) {
        Intrinsics.checkNotNullParameter($this$bufferedReader, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Reader inputStreamReader = new InputStreamReader(new FileInputStream($this$bufferedReader), charset);
        return inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, bufferSize);
    }

    static /* synthetic */ OutputStreamWriter writer$default(File $this$writer_u24default, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter($this$writer_u24default, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new OutputStreamWriter(new FileOutputStream($this$writer_u24default), charset);
    }

    private static final OutputStreamWriter writer(File $this$writer, Charset charset) {
        Intrinsics.checkNotNullParameter($this$writer, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new OutputStreamWriter(new FileOutputStream($this$writer), charset);
    }

    static /* synthetic */ BufferedWriter bufferedWriter$default(File $this$bufferedWriter_u24default, Charset charset, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i & 2) != 0) {
            bufferSize = 8192;
        }
        Intrinsics.checkNotNullParameter($this$bufferedWriter_u24default, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream($this$bufferedWriter_u24default), charset);
        return outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, bufferSize);
    }

    private static final BufferedWriter bufferedWriter(File $this$bufferedWriter, Charset charset, int bufferSize) {
        Intrinsics.checkNotNullParameter($this$bufferedWriter, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream($this$bufferedWriter), charset);
        return outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, bufferSize);
    }

    static /* synthetic */ PrintWriter printWriter$default(File $this$printWriter_u24default, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter($this$printWriter_u24default, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream($this$printWriter_u24default), charset);
        return new PrintWriter(outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192));
    }

    private static final PrintWriter printWriter(File $this$printWriter, Charset charset) {
        Intrinsics.checkNotNullParameter($this$printWriter, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream($this$printWriter), charset);
        return new PrintWriter(outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c3, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c4, code lost:
        kotlin.p002io.CloseableKt.closeFinally(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c8, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final byte[] readBytes(java.io.File r15) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r15, r0)
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r15)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = r0
            java.io.FileInputStream r1 = (java.io.FileInputStream) r1     // Catch:{ all -> 0x00c1 }
            r2 = 0
            r3 = 0
            long r4 = r15.length()     // Catch:{ all -> 0x00c1 }
            r6 = r4
            r8 = 0
            r9 = 2147483647(0x7fffffff, double:1.060997895E-314)
            java.lang.String r11 = "File "
            int r12 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r12 > 0) goto L_0x009a
            int r5 = (int) r4
            r4 = r5
            byte[] r5 = new byte[r4]     // Catch:{ all -> 0x00c1 }
        L_0x0026:
            if (r4 <= 0) goto L_0x0031
            int r6 = r1.read(r5, r3, r4)     // Catch:{ all -> 0x00c1 }
            if (r6 < 0) goto L_0x0031
            int r4 = r4 - r6
            int r3 = r3 + r6
            goto L_0x0026
        L_0x0031:
            java.lang.String r6 = "copyOf(this, newSize)"
            r7 = 0
            if (r4 <= 0) goto L_0x003f
            byte[] r8 = java.util.Arrays.copyOf(r5, r3)     // Catch:{ all -> 0x00c1 }
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, r6)     // Catch:{ all -> 0x00c1 }
            r5 = r8
            goto L_0x0079
        L_0x003f:
            int r8 = r1.read()     // Catch:{ all -> 0x00c1 }
            r9 = -1
            if (r8 != r9) goto L_0x0047
            goto L_0x0079
        L_0x0047:
            kotlin.io.ExposingBufferByteArrayOutputStream r9 = new kotlin.io.ExposingBufferByteArrayOutputStream     // Catch:{ all -> 0x00c1 }
            r10 = 8193(0x2001, float:1.1481E-41)
            r9.<init>(r10)     // Catch:{ all -> 0x00c1 }
            r9.write(r8)     // Catch:{ all -> 0x00c1 }
            r10 = r1
            java.io.InputStream r10 = (java.io.InputStream) r10     // Catch:{ all -> 0x00c1 }
            r12 = r9
            java.io.OutputStream r12 = (java.io.OutputStream) r12     // Catch:{ all -> 0x00c1 }
            r13 = 2
            r14 = 0
            kotlin.p002io.ByteStreamsKt.copyTo$default(r10, r12, r14, r13, r7)     // Catch:{ all -> 0x00c1 }
            int r10 = r5.length     // Catch:{ all -> 0x00c1 }
            int r12 = r9.size()     // Catch:{ all -> 0x00c1 }
            int r10 = r10 + r12
            if (r10 < 0) goto L_0x007d
            byte[] r11 = r9.getBuffer()     // Catch:{ all -> 0x00c1 }
            byte[] r12 = java.util.Arrays.copyOf(r5, r10)     // Catch:{ all -> 0x00c1 }
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r12, r6)     // Catch:{ all -> 0x00c1 }
            int r6 = r5.length     // Catch:{ all -> 0x00c1 }
            int r13 = r9.size()     // Catch:{ all -> 0x00c1 }
            byte[] r6 = kotlin.collections.ArraysKt.copyInto((byte[]) r11, (byte[]) r12, (int) r6, (int) r14, (int) r13)     // Catch:{ all -> 0x00c1 }
            r5 = r6
        L_0x0079:
            kotlin.p002io.CloseableKt.closeFinally(r0, r7)
            return r5
        L_0x007d:
            java.lang.OutOfMemoryError r6 = new java.lang.OutOfMemoryError     // Catch:{ all -> 0x00c1 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c1 }
            r7.<init>()     // Catch:{ all -> 0x00c1 }
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ all -> 0x00c1 }
            java.lang.StringBuilder r7 = r7.append(r15)     // Catch:{ all -> 0x00c1 }
            java.lang.String r11 = " is too big to fit in memory."
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ all -> 0x00c1 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00c1 }
            r6.<init>(r7)     // Catch:{ all -> 0x00c1 }
            throw r6     // Catch:{ all -> 0x00c1 }
        L_0x009a:
            java.lang.OutOfMemoryError r4 = new java.lang.OutOfMemoryError     // Catch:{ all -> 0x00c1 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c1 }
            r5.<init>()     // Catch:{ all -> 0x00c1 }
            java.lang.StringBuilder r5 = r5.append(r11)     // Catch:{ all -> 0x00c1 }
            java.lang.StringBuilder r5 = r5.append(r15)     // Catch:{ all -> 0x00c1 }
            java.lang.String r9 = " is too big ("
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch:{ all -> 0x00c1 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x00c1 }
            java.lang.String r9 = " bytes) to fit in memory."
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch:{ all -> 0x00c1 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00c1 }
            r4.<init>(r5)     // Catch:{ all -> 0x00c1 }
            throw r4     // Catch:{ all -> 0x00c1 }
        L_0x00c1:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x00c3 }
        L_0x00c3:
            r2 = move-exception
            kotlin.p002io.CloseableKt.closeFinally(r0, r1)
            goto L_0x00c9
        L_0x00c8:
            throw r2
        L_0x00c9:
            goto L_0x00c8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FilesKt__FileReadWriteKt.readBytes(java.io.File):byte[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0022, code lost:
        kotlin.p002io.CloseableKt.closeFinally(r0, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void writeBytes(java.io.File r3, byte[] r4) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            java.lang.String r0 = "array"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.io.FileOutputStream r0 = new java.io.FileOutputStream
            r0.<init>(r3)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = r0
            java.io.FileOutputStream r1 = (java.io.FileOutputStream) r1     // Catch:{ all -> 0x001f }
            r2 = 0
            r1.write(r4)     // Catch:{ all -> 0x001f }
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x001f }
            r1 = 0
            kotlin.p002io.CloseableKt.closeFinally(r0, r1)
            return
        L_0x001f:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0021 }
        L_0x0021:
            r2 = move-exception
            kotlin.p002io.CloseableKt.closeFinally(r0, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FilesKt__FileReadWriteKt.writeBytes(java.io.File, byte[]):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0022, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0023, code lost:
        kotlin.p002io.CloseableKt.closeFinally(r0, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void appendBytes(java.io.File r3, byte[] r4) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            java.lang.String r0 = "array"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.io.FileOutputStream r0 = new java.io.FileOutputStream
            r1 = 1
            r0.<init>(r3, r1)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = r0
            java.io.FileOutputStream r1 = (java.io.FileOutputStream) r1     // Catch:{ all -> 0x0020 }
            r2 = 0
            r1.write(r4)     // Catch:{ all -> 0x0020 }
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0020 }
            r1 = 0
            kotlin.p002io.CloseableKt.closeFinally(r0, r1)
            return
        L_0x0020:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0022 }
        L_0x0022:
            r2 = move-exception
            kotlin.p002io.CloseableKt.closeFinally(r0, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FilesKt__FileReadWriteKt.appendBytes(java.io.File, byte[]):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002e, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002a, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002b, code lost:
        kotlin.p002io.CloseableKt.closeFinally(r0, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String readText(java.io.File r4, java.nio.charset.Charset r5) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.lang.String r0 = "charset"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.io.InputStreamReader r0 = new java.io.InputStreamReader
            java.io.FileInputStream r1 = new java.io.FileInputStream
            r1.<init>(r4)
            java.io.InputStream r1 = (java.io.InputStream) r1
            r0.<init>(r1, r5)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = r0
            java.io.InputStreamReader r1 = (java.io.InputStreamReader) r1     // Catch:{ all -> 0x0028 }
            r2 = 0
            r3 = r1
            java.io.Reader r3 = (java.io.Reader) r3     // Catch:{ all -> 0x0028 }
            java.lang.String r3 = kotlin.p002io.TextStreamsKt.readText(r3)     // Catch:{ all -> 0x0028 }
            r1 = 0
            kotlin.p002io.CloseableKt.closeFinally(r0, r1)
            return r3
        L_0x0028:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x002a }
        L_0x002a:
            r2 = move-exception
            kotlin.p002io.CloseableKt.closeFinally(r0, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FilesKt__FileReadWriteKt.readText(java.io.File, java.nio.charset.Charset):java.lang.String");
    }

    public static /* synthetic */ String readText$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readText(file, charset);
    }

    public static final void writeText(File $this$writeText, String text, Charset charset) {
        Intrinsics.checkNotNullParameter($this$writeText, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        FilesKt.writeBytes($this$writeText, bytes);
    }

    public static /* synthetic */ void writeText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.writeText(file, str, charset);
    }

    public static final void appendText(File $this$appendText, String text, Charset charset) {
        Intrinsics.checkNotNullParameter($this$appendText, "<this>");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkNotNullExpressionValue(bytes, "this as java.lang.String).getBytes(charset)");
        FilesKt.appendBytes($this$appendText, bytes);
    }

    public static /* synthetic */ void appendText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.appendText(file, str, charset);
    }

    public static final void forEachBlock(File $this$forEachBlock, Function2<? super byte[], ? super Integer, Unit> action) {
        Intrinsics.checkNotNullParameter($this$forEachBlock, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        FilesKt.forEachBlock($this$forEachBlock, 4096, action);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0037, code lost:
        kotlin.p002io.CloseableKt.closeFinally(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003b, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void forEachBlock(java.io.File r6, int r7, kotlin.jvm.functions.Function2<? super byte[], ? super java.lang.Integer, kotlin.Unit> r8) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "action"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            r0 = 512(0x200, float:7.175E-43)
            int r0 = kotlin.ranges.RangesKt.coerceAtLeast((int) r7, (int) r0)
            byte[] r0 = new byte[r0]
            java.io.FileInputStream r1 = new java.io.FileInputStream
            r1.<init>(r6)
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = r1
            java.io.FileInputStream r2 = (java.io.FileInputStream) r2     // Catch:{ all -> 0x0034 }
            r3 = 0
        L_0x001d:
            int r4 = r2.read(r0)     // Catch:{ all -> 0x0034 }
            if (r4 > 0) goto L_0x002c
            kotlin.Unit r2 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0034 }
            r2 = 0
            kotlin.p002io.CloseableKt.closeFinally(r1, r2)
            return
        L_0x002c:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0034 }
            r8.invoke(r0, r5)     // Catch:{ all -> 0x0034 }
            goto L_0x001d
        L_0x0034:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0036 }
        L_0x0036:
            r3 = move-exception
            kotlin.p002io.CloseableKt.closeFinally(r1, r2)
            goto L_0x003c
        L_0x003b:
            throw r3
        L_0x003c:
            goto L_0x003b
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FilesKt__FileReadWriteKt.forEachBlock(java.io.File, int, kotlin.jvm.functions.Function2):void");
    }

    public static /* synthetic */ void forEachLine$default(File file, Charset charset, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.forEachLine(file, charset, function1);
    }

    public static final void forEachLine(File $this$forEachLine, Charset charset, Function1<? super String, Unit> action) {
        Intrinsics.checkNotNullParameter($this$forEachLine, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        Intrinsics.checkNotNullParameter(action, "action");
        TextStreamsKt.forEachLine(new BufferedReader(new InputStreamReader(new FileInputStream($this$forEachLine), charset)), action);
    }

    private static final FileInputStream inputStream(File $this$inputStream) {
        Intrinsics.checkNotNullParameter($this$inputStream, "<this>");
        return new FileInputStream($this$inputStream);
    }

    private static final FileOutputStream outputStream(File $this$outputStream) {
        Intrinsics.checkNotNullParameter($this$outputStream, "<this>");
        return new FileOutputStream($this$outputStream);
    }

    public static /* synthetic */ List readLines$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readLines(file, charset);
    }

    public static final List<String> readLines(File $this$readLines, Charset charset) {
        Intrinsics.checkNotNullParameter($this$readLines, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        ArrayList result = new ArrayList();
        FilesKt.forEachLine($this$readLines, charset, new FilesKt__FileReadWriteKt$readLines$1(result));
        return result;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0042, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
        kotlin.p002io.CloseableKt.closeFinally(r2, r3);
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004b, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ java.lang.Object useLines$default(java.io.File r6, java.nio.charset.Charset r7, kotlin.jvm.functions.Function1 r8, int r9, java.lang.Object r10) {
        /*
            r10 = 1
            r9 = r9 & r10
            if (r9 == 0) goto L_0x0006
            java.nio.charset.Charset r7 = kotlin.text.Charsets.UTF_8
        L_0x0006:
            r9 = r7
            r0 = r6
            r1 = 0
            java.io.InputStreamReader r2 = new java.io.InputStreamReader
            java.io.FileInputStream r3 = new java.io.FileInputStream
            r3.<init>(r0)
            java.io.InputStream r3 = (java.io.InputStream) r3
            r2.<init>(r3, r9)
            java.io.Reader r2 = (java.io.Reader) r2
            boolean r3 = r2 instanceof java.io.BufferedReader
            if (r3 == 0) goto L_0x001e
            java.io.BufferedReader r2 = (java.io.BufferedReader) r2
            goto L_0x0026
        L_0x001e:
            java.io.BufferedReader r3 = new java.io.BufferedReader
            r4 = 8192(0x2000, float:1.14794E-41)
            r3.<init>(r2, r4)
            r2 = r3
        L_0x0026:
            java.io.Closeable r2 = (java.io.Closeable) r2
            r3 = r2
            java.io.BufferedReader r3 = (java.io.BufferedReader) r3     // Catch:{ all -> 0x003f }
            r4 = 0
            kotlin.sequences.Sequence r5 = kotlin.p002io.TextStreamsKt.lineSequence(r3)     // Catch:{ all -> 0x003f }
            java.lang.Object r5 = r8.invoke(r5)     // Catch:{ all -> 0x003f }
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            r3 = 0
            kotlin.p002io.CloseableKt.closeFinally(r2, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            return r5
        L_0x003f:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0041 }
        L_0x0041:
            r4 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlin.p002io.CloseableKt.closeFinally(r2, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FilesKt__FileReadWriteKt.useLines$default(java.io.File, java.nio.charset.Charset, kotlin.jvm.functions.Function1, int, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0049, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004a, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
        kotlin.p002io.CloseableKt.closeFinally(r1, r3);
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0053, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> T useLines(java.io.File r6, java.nio.charset.Charset r7, kotlin.jvm.functions.Function1<? super kotlin.sequences.Sequence<java.lang.String>, ? extends T> r8) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "charset"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            java.lang.String r0 = "block"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            r0 = 0
            java.io.InputStreamReader r1 = new java.io.InputStreamReader
            java.io.FileInputStream r2 = new java.io.FileInputStream
            r2.<init>(r6)
            java.io.InputStream r2 = (java.io.InputStream) r2
            r1.<init>(r2, r7)
            java.io.Reader r1 = (java.io.Reader) r1
            boolean r2 = r1 instanceof java.io.BufferedReader
            if (r2 == 0) goto L_0x0025
            java.io.BufferedReader r1 = (java.io.BufferedReader) r1
            goto L_0x002d
        L_0x0025:
            java.io.BufferedReader r2 = new java.io.BufferedReader
            r3 = 8192(0x2000, float:1.14794E-41)
            r2.<init>(r1, r3)
            r1 = r2
        L_0x002d:
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = 1
            r3 = r1
            java.io.BufferedReader r3 = (java.io.BufferedReader) r3     // Catch:{ all -> 0x0047 }
            r4 = 0
            kotlin.sequences.Sequence r5 = kotlin.p002io.TextStreamsKt.lineSequence(r3)     // Catch:{ all -> 0x0047 }
            java.lang.Object r5 = r8.invoke(r5)     // Catch:{ all -> 0x0047 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r2)
            r3 = 0
            kotlin.p002io.CloseableKt.closeFinally(r1, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r2)
            return r5
        L_0x0047:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0049 }
        L_0x0049:
            r4 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r2)
            kotlin.p002io.CloseableKt.closeFinally(r1, r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r2)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FilesKt__FileReadWriteKt.useLines(java.io.File, java.nio.charset.Charset, kotlin.jvm.functions.Function1):java.lang.Object");
    }
}
