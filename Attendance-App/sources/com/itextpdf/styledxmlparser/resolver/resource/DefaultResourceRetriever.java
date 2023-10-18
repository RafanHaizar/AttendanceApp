package com.itextpdf.styledxmlparser.resolver.resource;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResourceRetriever implements IResourceRetriever {
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) DefaultResourceRetriever.class);
    private long resourceSizeByteLimit = Long.MAX_VALUE;

    public long getResourceSizeByteLimit() {
        return this.resourceSizeByteLimit;
    }

    public IResourceRetriever setResourceSizeByteLimit(long resourceSizeByteLimit2) {
        this.resourceSizeByteLimit = resourceSizeByteLimit2;
        return this;
    }

    public InputStream getInputStreamByUrl(URL url) throws IOException {
        if (urlFilter(url)) {
            return new LimitedInputStream(url.openStream(), this.resourceSizeByteLimit);
        }
        logger.warn(MessageFormatUtil.format(LogMessageConstant.RESOURCE_WITH_GIVEN_URL_WAS_FILTERED_OUT, url));
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001b, code lost:
        if (r1 != null) goto L_0x001d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0025, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getByteArrayByUrl(java.net.URL r7) throws java.io.IOException {
        /*
            r6 = this;
            r0 = 0
            java.io.InputStream r1 = r6.getInputStreamByUrl(r7)     // Catch:{ ReadingByteLimitException -> 0x0026 }
            if (r1 != 0) goto L_0x000e
            if (r1 == 0) goto L_0x000d
            r1.close()     // Catch:{ ReadingByteLimitException -> 0x0026 }
        L_0x000d:
            return r0
        L_0x000e:
            byte[] r2 = com.itextpdf.p026io.util.StreamUtil.inputStreamToArray(r1)     // Catch:{ all -> 0x0018 }
            if (r1 == 0) goto L_0x0017
            r1.close()     // Catch:{ ReadingByteLimitException -> 0x0026 }
        L_0x0017:
            return r2
        L_0x0018:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x001a }
        L_0x001a:
            r3 = move-exception
            if (r1 == 0) goto L_0x0025
            r1.close()     // Catch:{ all -> 0x0021 }
            goto L_0x0025
        L_0x0021:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch:{ ReadingByteLimitException -> 0x0026 }
        L_0x0025:
            throw r3     // Catch:{ ReadingByteLimitException -> 0x0026 }
        L_0x0026:
            r1 = move-exception
            org.slf4j.Logger r2 = logger
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            r3[r4] = r7
            long r4 = r6.resourceSizeByteLimit
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r5 = 1
            r3[r5] = r4
            java.lang.String r4 = "Unable to retrieve resource with given URL ({0}) and resource size byte limit ({1})."
            java.lang.String r3 = com.itextpdf.p026io.util.MessageFormatUtil.format(r4, r3)
            r2.warn(r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.resolver.resource.DefaultResourceRetriever.getByteArrayByUrl(java.net.URL):byte[]");
    }

    /* access modifiers changed from: protected */
    public boolean urlFilter(URL url) {
        return true;
    }
}
