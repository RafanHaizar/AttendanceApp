package org.bouncycastle.est;

import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cmc.CMCException;
import org.bouncycastle.cmc.SimplePKIResponse;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.util.Selector;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;

public class ESTService {
    protected static final String CACERTS = "/cacerts";
    protected static final String CSRATTRS = "/csrattrs";
    protected static final String FULLCMC = "/fullcmc";
    protected static final String SERVERGEN = "/serverkeygen";
    protected static final String SIMPLE_ENROLL = "/simpleenroll";
    protected static final String SIMPLE_REENROLL = "/simplereenroll";
    protected static final Set<String> illegalParts;
    private static final Pattern pathInValid = Pattern.compile("^[0-9a-zA-Z_\\-.~!$&'()*+,;:=]+");
    private final ESTClientProvider clientProvider;
    private final String server;

    static {
        HashSet hashSet = new HashSet();
        illegalParts = hashSet;
        hashSet.add(CACERTS.substring(1));
        hashSet.add(SIMPLE_ENROLL.substring(1));
        hashSet.add(SIMPLE_REENROLL.substring(1));
        hashSet.add(FULLCMC.substring(1));
        hashSet.add(SERVERGEN.substring(1));
        hashSet.add(CSRATTRS.substring(1));
    }

    ESTService(String str, String str2, ESTClientProvider eSTClientProvider) {
        String str3;
        StringBuilder sb;
        String verifyServer = verifyServer(str);
        if (str2 != null) {
            str3 = verifyLabel(str2);
            sb = new StringBuilder().append("https://").append(verifyServer).append("/.well-known/est/");
        } else {
            sb = new StringBuilder().append("https://").append(verifyServer);
            str3 = "/.well-known/est";
        }
        this.server = sb.append(str3).toString();
        this.clientProvider = eSTClientProvider;
    }

    /* access modifiers changed from: private */
    public String annotateRequest(byte[] bArr) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        int i = 0;
        do {
            int i2 = i + 48;
            if (i2 < bArr.length) {
                printWriter.print(Base64.toBase64String(bArr, i, 48));
                i = i2;
            } else {
                printWriter.print(Base64.toBase64String(bArr, i, bArr.length - i));
                i = bArr.length;
            }
            printWriter.print(10);
        } while (i < bArr.length);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static X509CertificateHolder[] storeToArray(Store<X509CertificateHolder> store) {
        return storeToArray(store, (Selector<X509CertificateHolder>) null);
    }

    public static X509CertificateHolder[] storeToArray(Store<X509CertificateHolder> store, Selector<X509CertificateHolder> selector) {
        Collection<X509CertificateHolder> matches = store.getMatches(selector);
        return (X509CertificateHolder[]) matches.toArray(new X509CertificateHolder[matches.size()]);
    }

    private String verifyLabel(String str) {
        while (str.endsWith("/") && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        while (str.startsWith("/") && str.length() > 0) {
            str = str.substring(1);
        }
        if (str.length() == 0) {
            throw new IllegalArgumentException("Label set but after trimming '/' is not zero length string.");
        } else if (!pathInValid.matcher(str).matches()) {
            throw new IllegalArgumentException("Server path " + str + " contains invalid characters");
        } else if (!illegalParts.contains(str)) {
            return str;
        } else {
            throw new IllegalArgumentException("Label " + str + " is a reserved path segment.");
        }
    }

    private String verifyServer(String str) {
        while (str.endsWith("/") && str.length() > 0) {
            try {
                str = str.substring(0, str.length() - 1);
            } catch (Exception e) {
                if (e instanceof IllegalArgumentException) {
                    throw ((IllegalArgumentException) e);
                }
                throw new IllegalArgumentException("Scheme and host is invalid: " + e.getMessage(), e);
            }
        }
        if (!str.contains("://")) {
            URL url = new URL("https://" + str);
            if (url.getPath().length() != 0) {
                if (!url.getPath().equals("/")) {
                    throw new IllegalArgumentException("Server contains path, must only be <dnsname/ipaddress>:port, a path of '/.well-known/est/<label>' will be added arbitrarily.");
                }
            }
            return str;
        }
        throw new IllegalArgumentException("Server contains scheme, must only be <dnsname/ipaddress>:port, https:// will be added arbitrarily.");
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x018a A[Catch:{ all -> 0x0197 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x018d A[Catch:{ all -> 0x0197 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.est.CACertsResponse getCACerts() throws java.lang.Exception {
        /*
            r12 = this;
            java.lang.String r0 = "Content-Type"
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch:{ all -> 0x0185 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0185 }
            r3.<init>()     // Catch:{ all -> 0x0185 }
            java.lang.String r4 = r12.server     // Catch:{ all -> 0x0185 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0185 }
            java.lang.String r4 = "/cacerts"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0185 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0185 }
            r2.<init>(r3)     // Catch:{ all -> 0x0185 }
            org.bouncycastle.est.ESTClientProvider r3 = r12.clientProvider     // Catch:{ all -> 0x0185 }
            org.bouncycastle.est.ESTClient r3 = r3.makeClient()     // Catch:{ all -> 0x0185 }
            org.bouncycastle.est.ESTRequestBuilder r4 = new org.bouncycastle.est.ESTRequestBuilder     // Catch:{ all -> 0x0185 }
            java.lang.String r5 = "GET"
            r4.<init>(r5, r2)     // Catch:{ all -> 0x0185 }
            org.bouncycastle.est.ESTRequestBuilder r4 = r4.withClient(r3)     // Catch:{ all -> 0x0185 }
            org.bouncycastle.est.ESTRequest r8 = r4.build()     // Catch:{ all -> 0x0185 }
            org.bouncycastle.est.ESTResponse r3 = r3.doRequest(r8)     // Catch:{ all -> 0x0185 }
            int r4 = r3.getStatusCode()     // Catch:{ all -> 0x0182 }
            r5 = 200(0xc8, float:2.8E-43)
            java.lang.String r11 = "Get CACerts: "
            if (r4 != r5) goto L_0x0115
            java.lang.String r4 = "application/pkcs7-mime"
            org.bouncycastle.est.HttpUtil$Headers r5 = r3.getHeaders()     // Catch:{ all -> 0x0182 }
            java.lang.String r5 = r5.getFirstValue(r0)     // Catch:{ all -> 0x0182 }
            boolean r4 = r4.equals(r5)     // Catch:{ all -> 0x0182 }
            if (r4 != 0) goto L_0x00a7
            org.bouncycastle.est.HttpUtil$Headers r4 = r3.getHeaders()     // Catch:{ all -> 0x0182 }
            java.lang.String r4 = r4.getFirstValue(r0)     // Catch:{ all -> 0x0182 }
            if (r4 == 0) goto L_0x0076
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0182 }
            r4.<init>()     // Catch:{ all -> 0x0182 }
            java.lang.String r5 = " got "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0182 }
            org.bouncycastle.est.HttpUtil$Headers r5 = r3.getHeaders()     // Catch:{ all -> 0x0182 }
            java.lang.String r0 = r5.getFirstValue(r0)     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ all -> 0x0182 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0182 }
            goto L_0x0078
        L_0x0076:
            java.lang.String r0 = " but was not present."
        L_0x0078:
            org.bouncycastle.est.ESTException r4 = new org.bouncycastle.est.ESTException     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0182 }
            r5.<init>()     // Catch:{ all -> 0x0182 }
            java.lang.String r6 = "Response : "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0182 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r2 = r5.append(r2)     // Catch:{ all -> 0x0182 }
            java.lang.String r5 = "Expecting application/pkcs7-mime "
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0182 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0182 }
            int r2 = r3.getStatusCode()     // Catch:{ all -> 0x0182 }
            java.io.InputStream r5 = r3.getInputStream()     // Catch:{ all -> 0x0182 }
            r4.<init>(r0, r1, r2, r5)     // Catch:{ all -> 0x0182 }
            throw r4     // Catch:{ all -> 0x0182 }
        L_0x00a7:
            java.lang.Long r0 = r3.getContentLength()     // Catch:{ all -> 0x00e1 }
            if (r0 == 0) goto L_0x00dc
            java.lang.Long r0 = r3.getContentLength()     // Catch:{ all -> 0x00e1 }
            long r4 = r0.longValue()     // Catch:{ all -> 0x00e1 }
            r6 = 0
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 <= 0) goto L_0x00dc
            org.bouncycastle.asn1.ASN1InputStream r0 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ all -> 0x00e1 }
            java.io.InputStream r4 = r3.getInputStream()     // Catch:{ all -> 0x00e1 }
            r0.<init>((java.io.InputStream) r4)     // Catch:{ all -> 0x00e1 }
            org.bouncycastle.cmc.SimplePKIResponse r4 = new org.bouncycastle.cmc.SimplePKIResponse     // Catch:{ all -> 0x00e1 }
            org.bouncycastle.asn1.ASN1Primitive r0 = r0.readObject()     // Catch:{ all -> 0x00e1 }
            org.bouncycastle.asn1.ASN1Sequence r0 = (org.bouncycastle.asn1.ASN1Sequence) r0     // Catch:{ all -> 0x00e1 }
            org.bouncycastle.asn1.cms.ContentInfo r0 = org.bouncycastle.asn1.cms.ContentInfo.getInstance(r0)     // Catch:{ all -> 0x00e1 }
            r4.<init>((org.bouncycastle.asn1.cms.ContentInfo) r0)     // Catch:{ all -> 0x00e1 }
            org.bouncycastle.util.Store r0 = r4.getCertificates()     // Catch:{ all -> 0x00e1 }
            org.bouncycastle.util.Store r4 = r4.getCRLs()     // Catch:{ all -> 0x00e1 }
            goto L_0x00de
        L_0x00dc:
            r0 = r1
            r4 = r0
        L_0x00de:
            r6 = r0
            r7 = r4
            goto L_0x011f
        L_0x00e1:
            r0 = move-exception
            org.bouncycastle.est.ESTException r1 = new org.bouncycastle.est.ESTException     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0182 }
            r4.<init>()     // Catch:{ all -> 0x0182 }
            java.lang.String r5 = "Decoding CACerts: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0182 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x0182 }
            java.lang.String r4 = " "
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x0182 }
            java.lang.String r4 = r0.getMessage()     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x0182 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0182 }
            int r4 = r3.getStatusCode()     // Catch:{ all -> 0x0182 }
            java.io.InputStream r5 = r3.getInputStream()     // Catch:{ all -> 0x0182 }
            r1.<init>(r2, r0, r4, r5)     // Catch:{ all -> 0x0182 }
            throw r1     // Catch:{ all -> 0x0182 }
        L_0x0115:
            int r0 = r3.getStatusCode()     // Catch:{ all -> 0x0182 }
            r4 = 204(0xcc, float:2.86E-43)
            if (r0 != r4) goto L_0x015f
            r6 = r1
            r7 = r6
        L_0x011f:
            org.bouncycastle.est.CACertsResponse r0 = new org.bouncycastle.est.CACertsResponse     // Catch:{ all -> 0x0182 }
            org.bouncycastle.est.Source r9 = r3.getSource()     // Catch:{ all -> 0x0182 }
            org.bouncycastle.est.ESTClientProvider r4 = r12.clientProvider     // Catch:{ all -> 0x0182 }
            boolean r10 = r4.isTrusted()     // Catch:{ all -> 0x0182 }
            r5 = r0
            r5.<init>(r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0182 }
            if (r3 == 0) goto L_0x0137
            r3.close()     // Catch:{ Exception -> 0x0135 }
            goto L_0x0137
        L_0x0135:
            r4 = move-exception
            goto L_0x0138
        L_0x0137:
            r4 = r1
        L_0x0138:
            if (r4 == 0) goto L_0x015e
            boolean r0 = r4 instanceof org.bouncycastle.est.ESTException
            if (r0 == 0) goto L_0x013f
            throw r4
        L_0x013f:
            org.bouncycastle.est.ESTException r0 = new org.bouncycastle.est.ESTException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r11)
            java.lang.String r2 = r2.toString()
            java.lang.StringBuilder r2 = r5.append(r2)
            java.lang.String r2 = r2.toString()
            int r3 = r3.getStatusCode()
            r0.<init>(r2, r4, r3, r1)
            throw r0
        L_0x015e:
            return r0
        L_0x015f:
            org.bouncycastle.est.ESTException r0 = new org.bouncycastle.est.ESTException     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0182 }
            r4.<init>()     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r4 = r4.append(r11)     // Catch:{ all -> 0x0182 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0182 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ all -> 0x0182 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0182 }
            int r4 = r3.getStatusCode()     // Catch:{ all -> 0x0182 }
            java.io.InputStream r5 = r3.getInputStream()     // Catch:{ all -> 0x0182 }
            r0.<init>(r2, r1, r4, r5)     // Catch:{ all -> 0x0182 }
            throw r0     // Catch:{ all -> 0x0182 }
        L_0x0182:
            r0 = move-exception
            r1 = r3
            goto L_0x0186
        L_0x0185:
            r0 = move-exception
        L_0x0186:
            boolean r2 = r0 instanceof org.bouncycastle.est.ESTException     // Catch:{ all -> 0x0197 }
            if (r2 == 0) goto L_0x018d
            org.bouncycastle.est.ESTException r0 = (org.bouncycastle.est.ESTException) r0     // Catch:{ all -> 0x0197 }
            throw r0     // Catch:{ all -> 0x0197 }
        L_0x018d:
            org.bouncycastle.est.ESTException r2 = new org.bouncycastle.est.ESTException     // Catch:{ all -> 0x0197 }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x0197 }
            r2.<init>(r3, r0)     // Catch:{ all -> 0x0197 }
            throw r2     // Catch:{ all -> 0x0197 }
        L_0x0197:
            r0 = move-exception
            if (r1 == 0) goto L_0x019f
            r1.close()     // Catch:{ Exception -> 0x019e }
            goto L_0x019f
        L_0x019e:
            r1 = move-exception
        L_0x019f:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.est.ESTService.getCACerts():org.bouncycastle.est.CACertsResponse");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x0106 A[Catch:{ all -> 0x0113 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0109 A[Catch:{ all -> 0x0113 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.est.CSRRequestResponse getCSRAttributes() throws org.bouncycastle.est.ESTException {
        /*
            r8 = this;
            org.bouncycastle.est.ESTClientProvider r0 = r8.clientProvider
            boolean r0 = r0.isTrusted()
            if (r0 == 0) goto L_0x011c
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ all -> 0x00fe }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fe }
            r2.<init>()     // Catch:{ all -> 0x00fe }
            java.lang.String r3 = r8.server     // Catch:{ all -> 0x00fe }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00fe }
            java.lang.String r3 = "/csrattrs"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00fe }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00fe }
            r1.<init>(r2)     // Catch:{ all -> 0x00fe }
            org.bouncycastle.est.ESTClientProvider r2 = r8.clientProvider     // Catch:{ all -> 0x00fe }
            org.bouncycastle.est.ESTClient r2 = r2.makeClient()     // Catch:{ all -> 0x00fe }
            org.bouncycastle.est.ESTRequestBuilder r3 = new org.bouncycastle.est.ESTRequestBuilder     // Catch:{ all -> 0x00fe }
            java.lang.String r4 = "GET"
            r3.<init>(r4, r1)     // Catch:{ all -> 0x00fe }
            org.bouncycastle.est.ESTRequestBuilder r3 = r3.withClient(r2)     // Catch:{ all -> 0x00fe }
            org.bouncycastle.est.ESTRequest r3 = r3.build()     // Catch:{ all -> 0x00fe }
            org.bouncycastle.est.ESTResponse r2 = r2.doRequest(r3)     // Catch:{ all -> 0x00fe }
            int r4 = r2.getStatusCode()     // Catch:{ all -> 0x00fc }
            switch(r4) {
                case 200: goto L_0x0049;
                case 204: goto L_0x0047;
                case 404: goto L_0x0047;
                default: goto L_0x0043;
            }     // Catch:{ all -> 0x00fc }
        L_0x0043:
            org.bouncycastle.est.ESTException r1 = new org.bouncycastle.est.ESTException     // Catch:{ all -> 0x00fc }
            goto L_0x00d5
        L_0x0047:
            r4 = r0
            goto L_0x0077
        L_0x0049:
            java.lang.Long r3 = r2.getContentLength()     // Catch:{ all -> 0x00a1 }
            if (r3 == 0) goto L_0x0047
            java.lang.Long r3 = r2.getContentLength()     // Catch:{ all -> 0x00a1 }
            long r3 = r3.longValue()     // Catch:{ all -> 0x00a1 }
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x0047
            org.bouncycastle.asn1.ASN1InputStream r3 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ all -> 0x00a1 }
            java.io.InputStream r4 = r2.getInputStream()     // Catch:{ all -> 0x00a1 }
            r3.<init>((java.io.InputStream) r4)     // Catch:{ all -> 0x00a1 }
            org.bouncycastle.asn1.ASN1Primitive r3 = r3.readObject()     // Catch:{ all -> 0x00a1 }
            org.bouncycastle.asn1.ASN1Sequence r3 = org.bouncycastle.asn1.ASN1Sequence.getInstance(r3)     // Catch:{ all -> 0x00a1 }
            org.bouncycastle.est.CSRAttributesResponse r4 = new org.bouncycastle.est.CSRAttributesResponse     // Catch:{ all -> 0x00a1 }
            org.bouncycastle.asn1.est.CsrAttrs r3 = org.bouncycastle.asn1.est.CsrAttrs.getInstance(r3)     // Catch:{ all -> 0x00a1 }
            r4.<init>((org.bouncycastle.asn1.est.CsrAttrs) r3)     // Catch:{ all -> 0x00a1 }
        L_0x0077:
            if (r2 == 0) goto L_0x007f
            r2.close()     // Catch:{ Exception -> 0x007d }
            goto L_0x007f
        L_0x007d:
            r1 = move-exception
            goto L_0x0080
        L_0x007f:
            r1 = r0
        L_0x0080:
            if (r1 == 0) goto L_0x0097
            boolean r3 = r1 instanceof org.bouncycastle.est.ESTException
            if (r3 == 0) goto L_0x0089
            org.bouncycastle.est.ESTException r1 = (org.bouncycastle.est.ESTException) r1
            throw r1
        L_0x0089:
            org.bouncycastle.est.ESTException r3 = new org.bouncycastle.est.ESTException
            java.lang.String r4 = r1.getMessage()
            int r2 = r2.getStatusCode()
            r3.<init>(r4, r1, r2, r0)
            throw r3
        L_0x0097:
            org.bouncycastle.est.CSRRequestResponse r0 = new org.bouncycastle.est.CSRRequestResponse
            org.bouncycastle.est.Source r1 = r2.getSource()
            r0.<init>(r4, r1)
            return r0
        L_0x00a1:
            r0 = move-exception
            org.bouncycastle.est.ESTException r3 = new org.bouncycastle.est.ESTException     // Catch:{ all -> 0x00fc }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fc }
            r4.<init>()     // Catch:{ all -> 0x00fc }
            java.lang.String r5 = "Decoding CACerts: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00fc }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00fc }
            java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ all -> 0x00fc }
            java.lang.String r4 = " "
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ all -> 0x00fc }
            java.lang.String r4 = r0.getMessage()     // Catch:{ all -> 0x00fc }
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ all -> 0x00fc }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00fc }
            int r4 = r2.getStatusCode()     // Catch:{ all -> 0x00fc }
            java.io.InputStream r5 = r2.getInputStream()     // Catch:{ all -> 0x00fc }
            r3.<init>(r1, r0, r4, r5)     // Catch:{ all -> 0x00fc }
            throw r3     // Catch:{ all -> 0x00fc }
        L_0x00d5:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fc }
            r4.<init>()     // Catch:{ all -> 0x00fc }
            java.lang.String r5 = "CSR Attribute request: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x00fc }
            java.net.URL r3 = r3.getURL()     // Catch:{ all -> 0x00fc }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00fc }
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ all -> 0x00fc }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00fc }
            int r4 = r2.getStatusCode()     // Catch:{ all -> 0x00fc }
            java.io.InputStream r5 = r2.getInputStream()     // Catch:{ all -> 0x00fc }
            r1.<init>(r3, r0, r4, r5)     // Catch:{ all -> 0x00fc }
            throw r1     // Catch:{ all -> 0x00fc }
        L_0x00fc:
            r0 = move-exception
            goto L_0x0101
        L_0x00fe:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0101:
            boolean r1 = r0 instanceof org.bouncycastle.est.ESTException     // Catch:{ all -> 0x0113 }
            if (r1 == 0) goto L_0x0109
            org.bouncycastle.est.ESTException r0 = (org.bouncycastle.est.ESTException) r0     // Catch:{ all -> 0x0113 }
            throw r0     // Catch:{ all -> 0x0113 }
        L_0x0109:
            org.bouncycastle.est.ESTException r1 = new org.bouncycastle.est.ESTException     // Catch:{ all -> 0x0113 }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x0113 }
            r1.<init>(r3, r0)     // Catch:{ all -> 0x0113 }
            throw r1     // Catch:{ all -> 0x0113 }
        L_0x0113:
            r0 = move-exception
            if (r2 == 0) goto L_0x011b
            r2.close()     // Catch:{ Exception -> 0x011a }
            goto L_0x011b
        L_0x011a:
            r1 = move-exception
        L_0x011b:
            throw r0
        L_0x011c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "No trust anchors."
            r0.<init>(r1)
            goto L_0x0125
        L_0x0124:
            throw r0
        L_0x0125:
            goto L_0x0124
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.est.ESTService.getCSRAttributes():org.bouncycastle.est.CSRRequestResponse");
    }

    /* access modifiers changed from: protected */
    public EnrollmentResponse handleEnrollResponse(ESTResponse eSTResponse) throws IOException {
        long j;
        ESTRequest originalRequest = eSTResponse.getOriginalRequest();
        if (eSTResponse.getStatusCode() == 202) {
            String header = eSTResponse.getHeader("Retry-After");
            if (header != null) {
                try {
                    j = System.currentTimeMillis() + (Long.parseLong(header) * 1000);
                } catch (NumberFormatException e) {
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                        j = simpleDateFormat.parse(header).getTime();
                    } catch (Exception e2) {
                        throw new ESTException("Unable to parse Retry-After header:" + originalRequest.getURL().toString() + " " + e2.getMessage(), (Throwable) null, eSTResponse.getStatusCode(), eSTResponse.getInputStream());
                    }
                }
                return new EnrollmentResponse((Store<X509CertificateHolder>) null, j, originalRequest, eSTResponse.getSource());
            }
            throw new ESTException("Got Status 202 but not Retry-After header from: " + originalRequest.getURL().toString());
        } else if (eSTResponse.getStatusCode() == 200) {
            try {
                return new EnrollmentResponse(new SimplePKIResponse(ContentInfo.getInstance(new ASN1InputStream(eSTResponse.getInputStream()).readObject())).getCertificates(), -1, (ESTRequest) null, eSTResponse.getSource());
            } catch (CMCException e3) {
                throw new ESTException(e3.getMessage(), e3.getCause());
            }
        } else {
            throw new ESTException("Simple Enroll: " + originalRequest.getURL().toString(), (Throwable) null, eSTResponse.getStatusCode(), eSTResponse.getInputStream());
        }
    }

    public EnrollmentResponse simpleEnroll(EnrollmentResponse enrollmentResponse) throws Exception {
        if (this.clientProvider.isTrusted()) {
            ESTResponse eSTResponse = null;
            try {
                ESTClient makeClient = this.clientProvider.makeClient();
                ESTResponse doRequest = makeClient.doRequest(new ESTRequestBuilder(enrollmentResponse.getRequestToRetry()).withClient(makeClient).build());
                EnrollmentResponse handleEnrollResponse = handleEnrollResponse(doRequest);
                if (doRequest != null) {
                    doRequest.close();
                }
                return handleEnrollResponse;
            } catch (Throwable th) {
                if (eSTResponse != null) {
                    eSTResponse.close();
                }
                throw th;
            }
        } else {
            throw new IllegalStateException("No trust anchors.");
        }
    }

    public EnrollmentResponse simpleEnroll(boolean z, PKCS10CertificationRequest pKCS10CertificationRequest, ESTAuth eSTAuth) throws IOException {
        if (this.clientProvider.isTrusted()) {
            ESTResponse eSTResponse = null;
            try {
                byte[] bytes = annotateRequest(pKCS10CertificationRequest.getEncoded()).getBytes();
                URL url = new URL(this.server + (z ? SIMPLE_REENROLL : SIMPLE_ENROLL));
                ESTClient makeClient = this.clientProvider.makeClient();
                ESTRequestBuilder withClient = new ESTRequestBuilder("POST", url).withData(bytes).withClient(makeClient);
                withClient.addHeader("Content-Type", "application/pkcs10");
                withClient.addHeader("Content-Length", "" + bytes.length);
                withClient.addHeader("Content-Transfer-Encoding", ResourceResolver.BASE64IDENTIFIER);
                if (eSTAuth != null) {
                    eSTAuth.applyAuth(withClient);
                }
                ESTResponse doRequest = makeClient.doRequest(withClient.build());
                EnrollmentResponse handleEnrollResponse = handleEnrollResponse(doRequest);
                if (doRequest != null) {
                    doRequest.close();
                }
                return handleEnrollResponse;
            } catch (Throwable th) {
                if (eSTResponse != null) {
                    eSTResponse.close();
                }
                throw th;
            }
        } else {
            throw new IllegalStateException("No trust anchors.");
        }
    }

    public EnrollmentResponse simpleEnrollPoP(boolean z, final PKCS10CertificationRequestBuilder pKCS10CertificationRequestBuilder, final ContentSigner contentSigner, ESTAuth eSTAuth) throws IOException {
        if (this.clientProvider.isTrusted()) {
            ESTResponse eSTResponse = null;
            try {
                URL url = new URL(this.server + (z ? SIMPLE_REENROLL : SIMPLE_ENROLL));
                ESTClient makeClient = this.clientProvider.makeClient();
                ESTRequestBuilder withConnectionListener = new ESTRequestBuilder("POST", url).withClient(makeClient).withConnectionListener(new ESTSourceConnectionListener() {
                    public ESTRequest onConnection(Source source, ESTRequest eSTRequest) throws IOException {
                        if (source instanceof TLSUniqueProvider) {
                            TLSUniqueProvider tLSUniqueProvider = (TLSUniqueProvider) source;
                            if (tLSUniqueProvider.isTLSUniqueAvailable()) {
                                PKCS10CertificationRequestBuilder pKCS10CertificationRequestBuilder = new PKCS10CertificationRequestBuilder(pKCS10CertificationRequestBuilder);
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                pKCS10CertificationRequestBuilder.setAttribute(PKCSObjectIdentifiers.pkcs_9_at_challengePassword, (ASN1Encodable) new DERPrintableString(Base64.toBase64String(tLSUniqueProvider.getTLSUnique())));
                                byteArrayOutputStream.write(ESTService.this.annotateRequest(pKCS10CertificationRequestBuilder.build(contentSigner).getEncoded()).getBytes());
                                byteArrayOutputStream.flush();
                                ESTRequestBuilder withData = new ESTRequestBuilder(eSTRequest).withData(byteArrayOutputStream.toByteArray());
                                withData.setHeader("Content-Type", "application/pkcs10");
                                withData.setHeader("Content-Transfer-Encoding", ResourceResolver.BASE64IDENTIFIER);
                                withData.setHeader("Content-Length", Long.toString((long) byteArrayOutputStream.size()));
                                return withData.build();
                            }
                        }
                        throw new IOException("Source does not supply TLS unique.");
                    }
                });
                if (eSTAuth != null) {
                    eSTAuth.applyAuth(withConnectionListener);
                }
                ESTResponse doRequest = makeClient.doRequest(withConnectionListener.build());
                EnrollmentResponse handleEnrollResponse = handleEnrollResponse(doRequest);
                if (doRequest != null) {
                    doRequest.close();
                }
                return handleEnrollResponse;
            } catch (Throwable th) {
                if (eSTResponse != null) {
                    eSTResponse.close();
                }
                throw th;
            }
        } else {
            throw new IllegalStateException("No trust anchors.");
        }
    }
}
