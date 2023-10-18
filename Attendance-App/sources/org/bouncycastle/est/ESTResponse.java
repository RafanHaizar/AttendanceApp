package org.bouncycastle.est;

import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import org.bouncycastle.est.HttpUtil;
import org.bouncycastle.util.Properties;
import org.bouncycastle.util.Strings;

public class ESTResponse {
    private static final Long ZERO = 0L;
    private String HttpVersion;
    private Long absoluteReadLimit;
    /* access modifiers changed from: private */
    public Long contentLength;
    private final HttpUtil.Headers headers;
    private InputStream inputStream;
    private final byte[] lineBuffer;
    private final ESTRequest originalRequest;
    /* access modifiers changed from: private */
    public long read = 0;
    private final Source source;
    private int statusCode;
    private String statusMessage;

    private class PrintingInputStream extends InputStream {
        private final InputStream src;

        private PrintingInputStream(InputStream inputStream) {
            this.src = inputStream;
        }

        public int available() throws IOException {
            return this.src.available();
        }

        public void close() throws IOException {
            this.src.close();
        }

        public int read() throws IOException {
            int read = this.src.read();
            System.out.print(String.valueOf((char) read));
            return read;
        }
    }

    public ESTResponse(ESTRequest eSTRequest, Source source2) throws IOException {
        this.originalRequest = eSTRequest;
        this.source = source2;
        if (source2 instanceof LimitedSource) {
            this.absoluteReadLimit = ((LimitedSource) source2).getAbsoluteReadLimit();
        }
        Set<String> asKeySet = Properties.asKeySet("org.bouncycastle.debug.est");
        this.inputStream = (asKeySet.contains("input") || asKeySet.contains("all")) ? new PrintingInputStream(source2.getInputStream()) : source2.getInputStream();
        this.headers = new HttpUtil.Headers();
        this.lineBuffer = new byte[1024];
        process();
    }

    static /* synthetic */ long access$108(ESTResponse eSTResponse) {
        long j = eSTResponse.read;
        eSTResponse.read = 1 + j;
        return j;
    }

    private void process() throws IOException {
        this.HttpVersion = readStringIncluding(' ');
        this.statusCode = Integer.parseInt(readStringIncluding(' '));
        this.statusMessage = readStringIncluding(10);
        while (true) {
            String readStringIncluding = readStringIncluding(10);
            if (readStringIncluding.length() <= 0) {
                break;
            }
            int indexOf = readStringIncluding.indexOf(58);
            if (indexOf > -1) {
                this.headers.add(Strings.toLowerCase(readStringIncluding.substring(0, indexOf).trim()), readStringIncluding.substring(indexOf + 1).trim());
            }
        }
        Long contentLength2 = getContentLength();
        this.contentLength = contentLength2;
        int i = this.statusCode;
        if (i == 204 || i == 202) {
            if (contentLength2 == null) {
                this.contentLength = 0L;
            } else if (i == 204 && contentLength2.longValue() > 0) {
                throw new IOException("Got HTTP status 204 but Content-length > 0.");
            }
        }
        Long l = this.contentLength;
        if (l != null) {
            if (l.equals(ZERO)) {
                this.inputStream = new InputStream() {
                    public int read() throws IOException {
                        return -1;
                    }
                };
            }
            Long l2 = this.contentLength;
            if (l2 != null) {
                if (l2.longValue() < 0) {
                    throw new IOException("Server returned negative content length: " + this.absoluteReadLimit);
                } else if (this.absoluteReadLimit != null && this.contentLength.longValue() >= this.absoluteReadLimit.longValue()) {
                    throw new IOException("Content length longer than absolute read limit: " + this.absoluteReadLimit + " Content-Length: " + this.contentLength);
                }
            }
            this.inputStream = wrapWithCounter(this.inputStream, this.absoluteReadLimit);
            if (ResourceResolver.BASE64IDENTIFIER.equalsIgnoreCase(getHeader("content-transfer-encoding"))) {
                this.inputStream = new CTEBase64InputStream(this.inputStream, getContentLength());
                return;
            }
            return;
        }
        throw new IOException("No Content-length header.");
    }

    public void close() throws IOException {
        InputStream inputStream2 = this.inputStream;
        if (inputStream2 != null) {
            inputStream2.close();
        }
        this.source.close();
    }

    public Long getContentLength() {
        String firstValue = this.headers.getFirstValue("Content-Length");
        if (firstValue == null) {
            return null;
        }
        try {
            return Long.valueOf(Long.parseLong(firstValue));
        } catch (RuntimeException e) {
            throw new RuntimeException("Content Length: '" + firstValue + "' invalid. " + e.getMessage());
        }
    }

    public String getHeader(String str) {
        return this.headers.getFirstValue(str);
    }

    public HttpUtil.Headers getHeaders() {
        return this.headers;
    }

    public String getHttpVersion() {
        return this.HttpVersion;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public ESTRequest getOriginalRequest() {
        return this.originalRequest;
    }

    public Source getSource() {
        return this.source;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String readStringIncluding(char r7) throws java.io.IOException {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
        L_0x0002:
            java.io.InputStream r2 = r6.inputStream
            int r2 = r2.read()
            byte[] r3 = r6.lineBuffer
            int r4 = r1 + 1
            byte r5 = (byte) r2
            r3[r1] = r5
            int r1 = r3.length
            if (r4 >= r1) goto L_0x002c
            r1 = -1
            if (r2 == r7) goto L_0x001a
            if (r2 > r1) goto L_0x0018
            goto L_0x001a
        L_0x0018:
            r1 = r4
            goto L_0x0002
        L_0x001a:
            if (r2 == r1) goto L_0x0026
            java.lang.String r7 = new java.lang.String
            r7.<init>(r3, r0, r4)
            java.lang.String r7 = r7.trim()
            return r7
        L_0x0026:
            java.io.EOFException r7 = new java.io.EOFException
            r7.<init>()
            throw r7
        L_0x002c:
            java.io.IOException r7 = new java.io.IOException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Server sent line > "
            java.lang.StringBuilder r0 = r0.append(r1)
            byte[] r1 = r6.lineBuffer
            int r1 = r1.length
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0)
            goto L_0x0049
        L_0x0048:
            throw r7
        L_0x0049:
            goto L_0x0048
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.est.ESTResponse.readStringIncluding(char):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public InputStream wrapWithCounter(final InputStream inputStream2, final Long l) {
        return new InputStream() {
            public void close() throws IOException {
                if (ESTResponse.this.contentLength != null && ESTResponse.this.contentLength.longValue() - 1 > ESTResponse.this.read) {
                    throw new IOException("Stream closed before limit fully read, Read: " + ESTResponse.this.read + " ContentLength: " + ESTResponse.this.contentLength);
                } else if (inputStream2.available() <= 0) {
                    inputStream2.close();
                } else {
                    throw new IOException("Stream closed with extra content in pipe that exceeds content length.");
                }
            }

            public int read() throws IOException {
                int read = inputStream2.read();
                if (read > -1) {
                    ESTResponse.access$108(ESTResponse.this);
                    if (l != null && ESTResponse.this.read >= l.longValue()) {
                        throw new IOException("Absolute Read Limit exceeded: " + l);
                    }
                }
                return read;
            }
        };
    }
}
