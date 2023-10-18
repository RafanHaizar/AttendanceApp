package org.bouncycastle.est.jcajce;

import com.itextpdf.p026io.font.PdfEncodings;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import org.bouncycastle.est.ESTClient;
import org.bouncycastle.est.ESTClientSourceProvider;
import org.bouncycastle.est.ESTException;
import org.bouncycastle.est.ESTRequest;
import org.bouncycastle.est.ESTRequestBuilder;
import org.bouncycastle.est.ESTResponse;

class DefaultESTClient implements ESTClient {
    private static byte[] CRLF = {13, 10};
    private static final Charset utf8 = Charset.forName(PdfEncodings.UTF8);
    private final ESTClientSourceProvider sslSocketProvider;

    private class PrintingOutputStream extends OutputStream {
        private final OutputStream tgt;

        public PrintingOutputStream(OutputStream outputStream) {
            this.tgt = outputStream;
        }

        public void write(int i) throws IOException {
            System.out.print(String.valueOf((char) i));
            this.tgt.write(i);
        }
    }

    public DefaultESTClient(ESTClientSourceProvider eSTClientSourceProvider) {
        this.sslSocketProvider = eSTClientSourceProvider;
    }

    private static void writeLine(OutputStream outputStream, String str) throws IOException {
        outputStream.write(str.getBytes());
        outputStream.write(CRLF);
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0015 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0016  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.est.ESTResponse doRequest(org.bouncycastle.est.ESTRequest r3) throws java.io.IOException {
        /*
            r2 = this;
            r0 = 15
        L_0x0002:
            org.bouncycastle.est.ESTResponse r3 = r2.performRequest(r3)
            org.bouncycastle.est.ESTRequest r1 = r2.redirectURL(r3)
            if (r1 == 0) goto L_0x0013
            int r0 = r0 + -1
            if (r0 > 0) goto L_0x0011
            goto L_0x0013
        L_0x0011:
            r3 = r1
            goto L_0x0002
        L_0x0013:
            if (r0 == 0) goto L_0x0016
            return r3
        L_0x0016:
            org.bouncycastle.est.ESTException r3 = new org.bouncycastle.est.ESTException
            java.lang.String r0 = "Too many redirects.."
            r3.<init>(r0)
            goto L_0x001f
        L_0x001e:
            throw r3
        L_0x001f:
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.est.jcajce.DefaultESTClient.doRequest(org.bouncycastle.est.ESTRequest):org.bouncycastle.est.ESTResponse");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0067 A[Catch:{ all -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0070 A[Catch:{ all -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0089 A[Catch:{ all -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00ba A[Catch:{ all -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00f9 A[Catch:{ all -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0144 A[Catch:{ all -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0154 A[SYNTHETIC, Splitter:B:41:0x0154] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.est.ESTResponse performRequest(org.bouncycastle.est.ESTRequest r10) throws java.io.IOException {
        /*
            r9 = this;
            java.lang.String r0 = "Connection"
            r1 = 0
            org.bouncycastle.est.ESTClientSourceProvider r2 = r9.sslSocketProvider     // Catch:{ all -> 0x015a }
            java.net.URL r3 = r10.getURL()     // Catch:{ all -> 0x015a }
            java.lang.String r3 = r3.getHost()     // Catch:{ all -> 0x015a }
            java.net.URL r4 = r10.getURL()     // Catch:{ all -> 0x015a }
            int r4 = r4.getPort()     // Catch:{ all -> 0x015a }
            org.bouncycastle.est.Source r1 = r2.makeSource(r3, r4)     // Catch:{ all -> 0x015a }
            org.bouncycastle.est.ESTSourceConnectionListener r2 = r10.getListener()     // Catch:{ all -> 0x015a }
            if (r2 == 0) goto L_0x0027
            org.bouncycastle.est.ESTSourceConnectionListener r2 = r10.getListener()     // Catch:{ all -> 0x015a }
            org.bouncycastle.est.ESTRequest r10 = r2.onConnection(r1, r10)     // Catch:{ all -> 0x015a }
        L_0x0027:
            java.lang.String r2 = "org.bouncycastle.debug.est"
            java.util.Set r2 = org.bouncycastle.util.Properties.asKeySet(r2)     // Catch:{ all -> 0x015a }
            java.lang.String r3 = "output"
            boolean r3 = r2.contains(r3)     // Catch:{ all -> 0x015a }
            if (r3 != 0) goto L_0x0043
            java.lang.String r3 = "all"
            boolean r2 = r2.contains(r3)     // Catch:{ all -> 0x015a }
            if (r2 == 0) goto L_0x003e
            goto L_0x0043
        L_0x003e:
            java.io.OutputStream r2 = r1.getOutputStream()     // Catch:{ all -> 0x015a }
            goto L_0x004c
        L_0x0043:
            org.bouncycastle.est.jcajce.DefaultESTClient$PrintingOutputStream r2 = new org.bouncycastle.est.jcajce.DefaultESTClient$PrintingOutputStream     // Catch:{ all -> 0x015a }
            java.io.OutputStream r3 = r1.getOutputStream()     // Catch:{ all -> 0x015a }
            r2.<init>(r3)     // Catch:{ all -> 0x015a }
        L_0x004c:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x015a }
            r3.<init>()     // Catch:{ all -> 0x015a }
            java.net.URL r4 = r10.getURL()     // Catch:{ all -> 0x015a }
            java.lang.String r4 = r4.getPath()     // Catch:{ all -> 0x015a }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x015a }
            java.net.URL r4 = r10.getURL()     // Catch:{ all -> 0x015a }
            java.lang.String r4 = r4.getQuery()     // Catch:{ all -> 0x015a }
            if (r4 == 0) goto L_0x0070
            java.net.URL r4 = r10.getURL()     // Catch:{ all -> 0x015a }
            java.lang.String r4 = r4.getQuery()     // Catch:{ all -> 0x015a }
            goto L_0x0072
        L_0x0070:
            java.lang.String r4 = ""
        L_0x0072:
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x015a }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x015a }
            org.bouncycastle.est.ESTRequestBuilder r4 = new org.bouncycastle.est.ESTRequestBuilder     // Catch:{ all -> 0x015a }
            r4.<init>(r10)     // Catch:{ all -> 0x015a }
            java.util.Map r5 = r10.getHeaders()     // Catch:{ all -> 0x015a }
            boolean r5 = r5.containsKey(r0)     // Catch:{ all -> 0x015a }
            if (r5 != 0) goto L_0x008e
            java.lang.String r5 = "close"
            r4.addHeader(r0, r5)     // Catch:{ all -> 0x015a }
        L_0x008e:
            java.net.URL r10 = r10.getURL()     // Catch:{ all -> 0x015a }
            int r0 = r10.getPort()     // Catch:{ all -> 0x015a }
            r5 = -1
            r6 = 0
            java.lang.String r7 = "Host"
            if (r0 <= r5) goto L_0x00ba
            java.lang.String r0 = "%s:%d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x015a }
            java.lang.String r8 = r10.getHost()     // Catch:{ all -> 0x015a }
            r5[r6] = r8     // Catch:{ all -> 0x015a }
            int r10 = r10.getPort()     // Catch:{ all -> 0x015a }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x015a }
            r8 = 1
            r5[r8] = r10     // Catch:{ all -> 0x015a }
            java.lang.String r10 = java.lang.String.format(r0, r5)     // Catch:{ all -> 0x015a }
        L_0x00b6:
            r4.setHeader(r7, r10)     // Catch:{ all -> 0x015a }
            goto L_0x00bf
        L_0x00ba:
            java.lang.String r10 = r10.getHost()     // Catch:{ all -> 0x015a }
            goto L_0x00b6
        L_0x00bf:
            org.bouncycastle.est.ESTRequest r10 = r4.build()     // Catch:{ all -> 0x015a }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x015a }
            r0.<init>()     // Catch:{ all -> 0x015a }
            java.lang.String r4 = r10.getMethod()     // Catch:{ all -> 0x015a }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x015a }
            java.lang.String r4 = " "
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x015a }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x015a }
            java.lang.String r3 = " HTTP/1.1"
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x015a }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x015a }
            writeLine(r2, r0)     // Catch:{ all -> 0x015a }
            java.util.Map r0 = r10.getHeaders()     // Catch:{ all -> 0x015a }
            java.util.Set r0 = r0.entrySet()     // Catch:{ all -> 0x015a }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x015a }
        L_0x00f3:
            boolean r3 = r0.hasNext()     // Catch:{ all -> 0x015a }
            if (r3 == 0) goto L_0x0130
            java.lang.Object r3 = r0.next()     // Catch:{ all -> 0x015a }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ all -> 0x015a }
            java.lang.Object r4 = r3.getValue()     // Catch:{ all -> 0x015a }
            java.lang.String[] r4 = (java.lang.String[]) r4     // Catch:{ all -> 0x015a }
            java.lang.String[] r4 = (java.lang.String[]) r4     // Catch:{ all -> 0x015a }
            r5 = 0
        L_0x0108:
            int r7 = r4.length     // Catch:{ all -> 0x015a }
            if (r5 == r7) goto L_0x00f3
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x015a }
            r7.<init>()     // Catch:{ all -> 0x015a }
            java.lang.Object r8 = r3.getKey()     // Catch:{ all -> 0x015a }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ all -> 0x015a }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x015a }
            java.lang.String r8 = ": "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x015a }
            r8 = r4[r5]     // Catch:{ all -> 0x015a }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x015a }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x015a }
            writeLine(r2, r7)     // Catch:{ all -> 0x015a }
            int r5 = r5 + 1
            goto L_0x0108
        L_0x0130:
            byte[] r0 = CRLF     // Catch:{ all -> 0x015a }
            r2.write(r0)     // Catch:{ all -> 0x015a }
            r2.flush()     // Catch:{ all -> 0x015a }
            r10.writeData(r2)     // Catch:{ all -> 0x015a }
            r2.flush()     // Catch:{ all -> 0x015a }
            org.bouncycastle.est.ESTHijacker r0 = r10.getHijacker()     // Catch:{ all -> 0x015a }
            if (r0 == 0) goto L_0x0154
            org.bouncycastle.est.ESTHijacker r0 = r10.getHijacker()     // Catch:{ all -> 0x015a }
            org.bouncycastle.est.ESTResponse r10 = r0.hijack(r10, r1)     // Catch:{ all -> 0x015a }
            if (r1 == 0) goto L_0x0153
            if (r10 != 0) goto L_0x0153
            r1.close()
        L_0x0153:
            return r10
        L_0x0154:
            org.bouncycastle.est.ESTResponse r0 = new org.bouncycastle.est.ESTResponse     // Catch:{ all -> 0x015a }
            r0.<init>(r10, r1)     // Catch:{ all -> 0x015a }
            return r0
        L_0x015a:
            r10 = move-exception
            if (r1 == 0) goto L_0x0160
            r1.close()
        L_0x0160:
            goto L_0x0162
        L_0x0161:
            throw r10
        L_0x0162:
            goto L_0x0161
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.est.jcajce.DefaultESTClient.performRequest(org.bouncycastle.est.ESTRequest):org.bouncycastle.est.ESTResponse");
    }

    /* access modifiers changed from: protected */
    public ESTRequest redirectURL(ESTResponse eSTResponse) throws IOException {
        ESTRequest eSTRequest;
        ESTRequestBuilder eSTRequestBuilder;
        if (eSTResponse.getStatusCode() < 300 || eSTResponse.getStatusCode() > 399) {
            eSTRequest = null;
        } else {
            switch (eSTResponse.getStatusCode()) {
                case 301:
                case 302:
                case 303:
                case 306:
                case 307:
                    String header = eSTResponse.getHeader("Location");
                    if (!"".equals(header)) {
                        ESTRequestBuilder eSTRequestBuilder2 = new ESTRequestBuilder(eSTResponse.getOriginalRequest());
                        if (header.startsWith("http")) {
                            eSTRequestBuilder = eSTRequestBuilder2.withURL(new URL(header));
                        } else {
                            URL url = eSTResponse.getOriginalRequest().getURL();
                            eSTRequestBuilder = eSTRequestBuilder2.withURL(new URL(url.getProtocol(), url.getHost(), url.getPort(), header));
                        }
                        eSTRequest = eSTRequestBuilder.build();
                        break;
                    } else {
                        throw new ESTException("Redirect status type: " + eSTResponse.getStatusCode() + " but no location header");
                    }
                default:
                    throw new ESTException("Client does not handle http status code: " + eSTResponse.getStatusCode());
            }
        }
        if (eSTRequest != null) {
            eSTResponse.close();
        }
        return eSTRequest;
    }
}
