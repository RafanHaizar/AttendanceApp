package com.itextpdf.p026io.source;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.svg.SvgConstants;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import kotlin.UByte;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.source.PdfTokenizer */
public class PdfTokenizer implements Closeable, Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* renamed from: F */
    public static final byte[] f1231F = ByteUtils.getIsoBytes(XfdfConstants.f1185F);
    public static final byte[] False = ByteUtils.getIsoBytes("false");

    /* renamed from: N */
    public static final byte[] f1232N = ByteUtils.getIsoBytes("n");
    public static final byte[] Null = ByteUtils.getIsoBytes("null");
    public static final byte[] Obj = ByteUtils.getIsoBytes("obj");

    /* renamed from: R */
    public static final byte[] f1233R = ByteUtils.getIsoBytes(SvgConstants.Attributes.PATH_DATA_CATMULL_CURVE);
    public static final byte[] Startxref = ByteUtils.getIsoBytes("startxref");
    public static final byte[] Stream = ByteUtils.getIsoBytes("stream");
    public static final byte[] Trailer = ByteUtils.getIsoBytes("trailer");
    public static final byte[] True = ByteUtils.getIsoBytes("true");
    public static final byte[] Xref = ByteUtils.getIsoBytes("xref");
    public static final boolean[] delims = {true, true, false, false, false, false, false, false, false, false, true, true, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, true, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static final long serialVersionUID = -2949864233416670521L;
    private boolean closeStream = true;
    private final RandomAccessFileOrArray file;
    protected int generation;
    protected boolean hexString;
    protected ByteBuffer outBuf;
    protected int reference;
    protected TokenType type;

    /* renamed from: com.itextpdf.io.source.PdfTokenizer$TokenType */
    public enum TokenType {
        Number,
        String,
        Name,
        Comment,
        StartArray,
        EndArray,
        StartDic,
        EndDic,
        Ref,
        Obj,
        EndObj,
        Other,
        EndOfFile
    }

    public PdfTokenizer(RandomAccessFileOrArray file2) {
        this.file = file2;
        this.outBuf = new ByteBuffer();
    }

    public void seek(long pos) throws IOException {
        this.file.seek(pos);
    }

    public void readFully(byte[] bytes) throws IOException {
        this.file.readFully(bytes);
    }

    public long getPosition() throws IOException {
        return this.file.getPosition();
    }

    public void close() throws IOException {
        if (this.closeStream) {
            this.file.close();
        }
    }

    public long length() throws IOException {
        return this.file.length();
    }

    public int read() throws IOException {
        return this.file.read();
    }

    public String readString(int ch) throws IOException {
        int ch2;
        StringBuilder buf = new StringBuilder();
        while (true) {
            int size = ch - 1;
            if (ch > 0 && (ch2 = read()) != -1) {
                buf.append((char) ch2);
                ch = size;
            }
        }
        return buf.toString();
    }

    public TokenType getTokenType() {
        return this.type;
    }

    public byte[] getByteContent() {
        return this.outBuf.toByteArray();
    }

    public String getStringValue() {
        return new String(this.outBuf.getInternalBuffer(), 0, this.outBuf.size());
    }

    public byte[] getDecodedStringContent() {
        return decodeStringContent(this.outBuf.getInternalBuffer(), 0, this.outBuf.size() - 1, isHexString());
    }

    public boolean tokenValueEqualsTo(byte[] cmp) {
        int size;
        if (cmp == null || this.outBuf.size() != (size = cmp.length)) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (cmp[i] != this.outBuf.getInternalBuffer()[i]) {
                return false;
            }
        }
        return true;
    }

    public int getObjNr() {
        return this.reference;
    }

    public int getGenNr() {
        return this.generation;
    }

    public void backOnePosition(int ch) {
        if (ch != -1) {
            this.file.pushBack((byte) ch);
        }
    }

    public int getHeaderOffset() throws IOException {
        String str = readString(1024);
        int idx = str.indexOf("%PDF-");
        if (idx >= 0 || (idx = str.indexOf("%FDF-")) >= 0) {
            return idx;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.PdfHeaderNotFound, (Object) this);
    }

    public String checkPdfHeader() throws IOException {
        this.file.seek(0);
        String str = readString(1024);
        int idx = str.indexOf("%PDF-");
        if (idx == 0) {
            return str.substring(idx + 1, idx + 8);
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.PdfHeaderNotFound, (Object) this);
    }

    public void checkFdfHeader() throws IOException {
        this.file.seek(0);
        if (readString(1024).indexOf("%FDF-") != 0) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.FdfStartxrefNotFound, (Object) this);
        }
    }

    public long getStartxref() throws IOException {
        long pos = this.file.length() - ((long) 1024);
        if (pos < 1) {
            pos = 1;
        }
        while (pos > 0) {
            this.file.seek(pos);
            int idx = readString(1024).lastIndexOf("startxref");
            if (idx >= 0) {
                return ((long) idx) + pos;
            }
            pos = (pos - ((long) 1024)) + 9;
        }
        throw new com.itextpdf.p026io.IOException("PDF startxref not found.", (Object) this);
    }

    public void nextValidToken() throws IOException {
        int level = 0;
        byte[] n1 = null;
        byte[] n2 = null;
        long ptr = 0;
        while (nextToken()) {
            if (this.type != TokenType.Comment) {
                switch (level) {
                    case 0:
                        if (this.type == TokenType.Number) {
                            ptr = this.file.getPosition();
                            n1 = getByteContent();
                            level++;
                            break;
                        } else {
                            return;
                        }
                    case 1:
                        if (this.type == TokenType.Number) {
                            n2 = getByteContent();
                            level++;
                            break;
                        } else {
                            this.file.seek(ptr);
                            this.type = TokenType.Number;
                            this.outBuf.reset().append(n1);
                            return;
                        }
                    case 2:
                        if (this.type == TokenType.Other) {
                            if (tokenValueEqualsTo(f1233R)) {
                                if (n2 != null) {
                                    this.type = TokenType.Ref;
                                    try {
                                        this.reference = Integer.parseInt(new String(n1));
                                        this.generation = Integer.parseInt(new String(n2));
                                        return;
                                    } catch (Exception e) {
                                        LoggerFactory.getLogger((Class<?>) PdfTokenizer.class).error(MessageFormatUtil.format(LogMessageConstant.INVALID_INDIRECT_REFERENCE, new String(n1), new String(n2)));
                                        this.reference = -1;
                                        this.generation = 0;
                                        return;
                                    }
                                } else {
                                    throw new AssertionError();
                                }
                            } else if (tokenValueEqualsTo(Obj)) {
                                if (n2 != null) {
                                    this.type = TokenType.Obj;
                                    this.reference = Integer.parseInt(new String(n1));
                                    this.generation = Integer.parseInt(new String(n2));
                                    return;
                                }
                                throw new AssertionError();
                            }
                        }
                        this.file.seek(ptr);
                        this.type = TokenType.Number;
                        this.outBuf.reset().append(n1);
                        return;
                }
            }
        }
        if (level == 1) {
            this.type = TokenType.Number;
            this.outBuf.reset().append(n1);
        }
    }

    public boolean nextToken() throws IOException {
        int ch;
        int ch2;
        int ch3;
        this.outBuf.reset();
        do {
            ch = this.file.read();
            if (ch == -1 || !isWhitespace(ch)) {
            }
            ch = this.file.read();
            break;
        } while (!isWhitespace(ch));
        if (ch == -1) {
            this.type = TokenType.EndOfFile;
            return false;
        }
        switch (ch) {
            case 37:
                this.type = TokenType.Comment;
                do {
                    ch2 = this.file.read();
                    if (ch2 == -1) {
                        break;
                    } else if (ch2 == 13) {
                        break;
                    }
                } while (ch2 == 10);
                break;
            case 40:
                this.type = TokenType.String;
                this.hexString = false;
                int nesting = 0;
                while (true) {
                    ch3 = this.file.read();
                    if (ch3 != -1) {
                        if (ch3 == 40) {
                            nesting++;
                        } else if (ch3 == 41) {
                            nesting--;
                            if (nesting == -1) {
                            }
                        } else if (ch3 == 92) {
                            this.outBuf.append(92);
                            ch3 = this.file.read();
                            if (ch3 < 0) {
                            }
                        } else {
                            continue;
                        }
                        this.outBuf.append(ch3);
                    }
                }
                if (ch3 == -1) {
                    throwError(com.itextpdf.p026io.IOException.ErrorReadingString, new Object[0]);
                    break;
                }
                break;
            case 47:
                this.type = TokenType.Name;
                while (true) {
                    int ch4 = this.file.read();
                    if (delims[ch4 + 1]) {
                        backOnePosition(ch4);
                        break;
                    } else {
                        this.outBuf.append(ch4);
                    }
                }
            case 60:
                int v1 = this.file.read();
                if (v1 != 60) {
                    this.type = TokenType.String;
                    this.hexString = true;
                    int v2 = 0;
                    while (true) {
                        if (isWhitespace(v1)) {
                            v1 = this.file.read();
                        } else if (v1 != 62) {
                            this.outBuf.append(v1);
                            v1 = ByteBuffer.getHex(v1);
                            if (v1 >= 0) {
                                v2 = this.file.read();
                                while (isWhitespace(v2)) {
                                    v2 = this.file.read();
                                }
                                if (v2 != 62) {
                                    this.outBuf.append(v2);
                                    v2 = ByteBuffer.getHex(v2);
                                    if (v2 >= 0) {
                                        v1 = this.file.read();
                                    }
                                }
                            }
                        }
                    }
                    if (v1 < 0 || v2 < 0) {
                        throwError(com.itextpdf.p026io.IOException.ErrorReadingString, new Object[0]);
                        break;
                    }
                } else {
                    this.type = TokenType.StartDic;
                    break;
                }
            case 62:
                if (this.file.read() != 62) {
                    throwError(com.itextpdf.p026io.IOException.GtNotExpected, new Object[0]);
                }
                this.type = TokenType.EndDic;
                break;
            case 91:
                this.type = TokenType.StartArray;
                break;
            case 93:
                this.type = TokenType.EndArray;
                break;
            default:
                if (ch == 45 || ch == 43 || ch == 46 || (ch >= 48 && ch <= 57)) {
                    this.type = TokenType.Number;
                    boolean isReal = false;
                    int numberOfMinuses = 0;
                    if (ch == 45) {
                        do {
                            numberOfMinuses++;
                            ch = this.file.read();
                        } while (ch == 45);
                        this.outBuf.append(45);
                    } else {
                        this.outBuf.append(ch);
                        ch = this.file.read();
                    }
                    while (ch >= 48 && ch <= 57) {
                        this.outBuf.append(ch);
                        ch = this.file.read();
                    }
                    if (ch == 46) {
                        isReal = true;
                        this.outBuf.append(ch);
                        int ch5 = this.file.read();
                        int numberOfMinusesAfterDot = 0;
                        if (ch5 == 45) {
                            numberOfMinusesAfterDot = 0 + 1;
                            ch5 = this.file.read();
                        }
                        while (ch >= 48 && ch <= 57) {
                            if (numberOfMinusesAfterDot == 0) {
                                this.outBuf.append(ch);
                            }
                            ch5 = this.file.read();
                        }
                    }
                    if (numberOfMinuses > 1 && !isReal) {
                        this.outBuf.reset();
                        this.outBuf.append(48);
                    }
                } else {
                    this.type = TokenType.Other;
                    do {
                        this.outBuf.append(ch);
                        ch = this.file.read();
                    } while (!delims[ch + 1]);
                }
                if (ch != -1) {
                    backOnePosition(ch);
                    break;
                }
                break;
        }
        return true;
    }

    public long getLongValue() {
        return Long.parseLong(getStringValue());
    }

    public int getIntValue() {
        return Integer.parseInt(getStringValue());
    }

    public boolean isHexString() {
        return this.hexString;
    }

    public boolean isCloseStream() {
        return this.closeStream;
    }

    public void setCloseStream(boolean closeStream2) {
        this.closeStream = closeStream2;
    }

    public RandomAccessFileOrArray getSafeFile() {
        return this.file.createView();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v10, types: [int, byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v5, types: [byte] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static byte[] decodeStringContent(byte[] r8, int r9, int r10, boolean r11) {
        /*
            com.itextpdf.io.source.ByteBuffer r0 = new com.itextpdf.io.source.ByteBuffer
            int r1 = r10 - r9
            int r1 = r1 + 1
            r0.<init>(r1)
            if (r11 == 0) goto L_0x0030
            r1 = r9
        L_0x000c:
            if (r1 > r10) goto L_0x002e
            int r2 = r1 + 1
            byte r1 = r8[r1]
            int r1 = com.itextpdf.p026io.source.ByteBuffer.getHex(r1)
            if (r2 <= r10) goto L_0x001e
            int r3 = r1 << 4
            r0.append((int) r3)
            goto L_0x002e
        L_0x001e:
            int r3 = r2 + 1
            byte r2 = r8[r2]
            int r2 = com.itextpdf.p026io.source.ByteBuffer.getHex(r2)
            int r4 = r1 << 4
            int r4 = r4 + r2
            r0.append((int) r4)
            r1 = r3
            goto L_0x000c
        L_0x002e:
            goto L_0x00b7
        L_0x0030:
            r1 = r9
        L_0x0031:
            if (r1 > r10) goto L_0x00b7
            int r2 = r1 + 1
            byte r1 = r8[r1]
            r3 = 92
            r4 = 10
            if (r1 != r3) goto L_0x009e
            r3 = 0
            int r5 = r2 + 1
            byte r1 = r8[r2]
            switch(r1) {
                case 10: goto L_0x006d;
                case 13: goto L_0x005e;
                case 40: goto L_0x005d;
                case 41: goto L_0x005d;
                case 92: goto L_0x005d;
                case 98: goto L_0x005a;
                case 102: goto L_0x0057;
                case 110: goto L_0x0054;
                case 114: goto L_0x0051;
                case 116: goto L_0x004e;
                default: goto L_0x0045;
            }
        L_0x0045:
            r2 = 48
            if (r1 < r2) goto L_0x0098
            r4 = 55
            if (r1 <= r4) goto L_0x006f
            goto L_0x0098
        L_0x004e:
            r1 = 9
            goto L_0x0098
        L_0x0051:
            r1 = 13
            goto L_0x0098
        L_0x0054:
            r1 = 10
            goto L_0x0098
        L_0x0057:
            r1 = 12
            goto L_0x0098
        L_0x005a:
            r1 = 8
            goto L_0x0098
        L_0x005d:
            goto L_0x0098
        L_0x005e:
            r3 = 1
            if (r5 > r10) goto L_0x0098
            int r2 = r5 + 1
            byte r5 = r8[r5]
            if (r5 == r4) goto L_0x006b
            int r2 = r2 + -1
            r5 = r2
            goto L_0x0098
        L_0x006b:
            r5 = r2
            goto L_0x0098
        L_0x006d:
            r3 = 1
            goto L_0x0098
        L_0x006f:
            int r6 = r1 + -48
            int r7 = r5 + 1
            byte r1 = r8[r5]
            if (r1 < r2) goto L_0x0094
            if (r1 <= r4) goto L_0x007a
            goto L_0x0094
        L_0x007a:
            int r5 = r6 << 3
            int r5 = r5 + r1
            int r5 = r5 - r2
            int r6 = r7 + 1
            byte r1 = r8[r7]
            if (r1 < r2) goto L_0x008f
            if (r1 <= r4) goto L_0x0087
            goto L_0x008f
        L_0x0087:
            int r4 = r5 << 3
            int r4 = r4 + r1
            int r4 = r4 - r2
            r1 = r4 & 255(0xff, float:3.57E-43)
            r5 = r6
            goto L_0x0098
        L_0x008f:
            int r6 = r6 + -1
            r1 = r5
            r5 = r6
            goto L_0x0098
        L_0x0094:
            int r7 = r7 + -1
            r1 = r6
            r5 = r7
        L_0x0098:
            if (r3 == 0) goto L_0x009c
            r1 = r5
            goto L_0x0031
        L_0x009c:
            r2 = r5
            goto L_0x00b1
        L_0x009e:
            r3 = 13
            if (r1 != r3) goto L_0x00b1
            r1 = 10
            if (r2 > r10) goto L_0x00b1
            int r3 = r2 + 1
            byte r2 = r8[r2]
            if (r2 == r4) goto L_0x00b0
            int r3 = r3 + -1
            r2 = r3
            goto L_0x00b1
        L_0x00b0:
            r2 = r3
        L_0x00b1:
            r0.append((int) r1)
            r1 = r2
            goto L_0x0031
        L_0x00b7:
            byte[] r1 = r0.toByteArray()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.source.PdfTokenizer.decodeStringContent(byte[], int, int, boolean):byte[]");
    }

    public static byte[] decodeStringContent(byte[] content, boolean hexWriting) {
        return decodeStringContent(content, 0, content.length - 1, hexWriting);
    }

    public static boolean isWhitespace(int ch) {
        return isWhitespace(ch, true);
    }

    protected static boolean isWhitespace(int ch, boolean isWhitespace) {
        return (isWhitespace && ch == 0) || ch == 9 || ch == 10 || ch == 12 || ch == 13 || ch == 32;
    }

    protected static boolean isDelimiter(int ch) {
        return ch == 40 || ch == 41 || ch == 60 || ch == 62 || ch == 91 || ch == 93 || ch == 47 || ch == 37;
    }

    protected static boolean isDelimiterWhitespace(int ch) {
        return delims[ch + 1];
    }

    public void throwError(String error, Object... messageParams) {
        try {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ErrorAtFilePointer1, (Throwable) new com.itextpdf.p026io.IOException(error).setMessageParams(messageParams)).setMessageParams(Long.valueOf(this.file.getPosition()));
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ErrorAtFilePointer1, (Throwable) new com.itextpdf.p026io.IOException(error).setMessageParams(messageParams)).setMessageParams(error, "no position");
        }
    }

    public static boolean checkTrailer(ByteBuffer line) {
        if (Trailer.length > line.size()) {
            return false;
        }
        int i = 0;
        while (true) {
            byte[] bArr = Trailer;
            if (i >= bArr.length) {
                return true;
            }
            if (bArr[i] != line.get(i)) {
                return false;
            }
            i++;
        }
    }

    public boolean readLineSegment(ByteBuffer buffer) throws IOException {
        return readLineSegment(buffer, true);
    }

    public boolean readLineSegment(ByteBuffer buffer, boolean isNullWhitespace) throws IOException {
        int read;
        int c;
        boolean eol = false;
        do {
            read = read();
            c = read;
        } while (isWhitespace(read, isNullWhitespace));
        boolean prevWasWhitespace = false;
        while (!eol) {
            switch (c) {
                case -1:
                case 10:
                    eol = true;
                    break;
                case 9:
                case 12:
                case 32:
                    if (!prevWasWhitespace) {
                        prevWasWhitespace = true;
                        buffer.append((byte) c);
                        break;
                    }
                    break;
                case 13:
                    eol = true;
                    long cur = getPosition();
                    if (read() != 10) {
                        seek(cur);
                        break;
                    }
                    break;
                default:
                    prevWasWhitespace = false;
                    buffer.append((byte) c);
                    break;
            }
            if (eol || buffer.size() == buffer.capacity()) {
                eol = true;
            } else {
                c = read();
            }
        }
        if (buffer.size() == buffer.capacity()) {
            boolean eol2 = false;
            while (!eol2) {
                int read2 = read();
                c = read2;
                switch (read2) {
                    case -1:
                    case 10:
                        eol2 = true;
                        break;
                    case 13:
                        eol2 = true;
                        long cur2 = getPosition();
                        if (read() == 10) {
                            break;
                        } else {
                            seek(cur2);
                            break;
                        }
                }
            }
        }
        return c != -1 || !buffer.isEmpty();
    }

    public static int[] checkObjectStart(PdfTokenizer lineTokenizer) {
        try {
            lineTokenizer.seek(0);
            if (lineTokenizer.nextToken()) {
                if (lineTokenizer.getTokenType() == TokenType.Number) {
                    int num = lineTokenizer.getIntValue();
                    if (lineTokenizer.nextToken()) {
                        if (lineTokenizer.getTokenType() == TokenType.Number) {
                            int gen = lineTokenizer.getIntValue();
                            if (!lineTokenizer.nextToken() || !Arrays.equals(Obj, lineTokenizer.getByteContent())) {
                                return null;
                            }
                            return new int[]{num, gen};
                        }
                    }
                    return null;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Deprecated
    /* renamed from: com.itextpdf.io.source.PdfTokenizer$ReusableRandomAccessSource */
    protected static class ReusableRandomAccessSource implements IRandomAccessSource {
        private ByteBuffer buffer;

        public ReusableRandomAccessSource(ByteBuffer buffer2) {
            if (buffer2 != null) {
                this.buffer = buffer2;
                return;
            }
            throw new IllegalArgumentException("Passed byte buffer can not be null.");
        }

        public int get(long offset) {
            if (offset >= ((long) this.buffer.size())) {
                return -1;
            }
            return this.buffer.getInternalBuffer()[(int) offset] & UByte.MAX_VALUE;
        }

        public int get(long offset, byte[] bytes, int off, int len) {
            ByteBuffer byteBuffer = this.buffer;
            if (byteBuffer == null) {
                throw new IllegalStateException("Already closed");
            } else if (offset >= ((long) byteBuffer.size())) {
                return -1;
            } else {
                if (((long) len) + offset > ((long) this.buffer.size())) {
                    len = (int) (((long) this.buffer.size()) - offset);
                }
                System.arraycopy(this.buffer.getInternalBuffer(), (int) offset, bytes, off, len);
                return len;
            }
        }

        public long length() {
            return (long) this.buffer.size();
        }

        public void close() throws IOException {
            this.buffer = null;
        }
    }
}
