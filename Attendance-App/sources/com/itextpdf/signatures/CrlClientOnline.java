package com.itextpdf.signatures;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrlClientOnline implements ICrlClient {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) CrlClientOnline.class);
    protected List<URL> urls = new ArrayList();

    public CrlClientOnline() {
    }

    public CrlClientOnline(String... crls) {
        for (String url : crls) {
            addUrl(url);
        }
    }

    public CrlClientOnline(URL... crls) {
        for (URL url : crls) {
            addUrl(url);
        }
    }

    public CrlClientOnline(Certificate[] chain) {
        for (X509Certificate cert : chain) {
            LOGGER.info("Checking certificate: " + cert.getSubjectDN());
            try {
                String url = CertificateUtil.getCRLURL(cert);
                if (url != null) {
                    addUrl(url);
                }
            } catch (CertificateParsingException e) {
                LOGGER.info("Skipped CRL url (certificate could not be parsed)");
            }
        }
    }

    public Collection<byte[]> getEncoded(X509Certificate checkCert, String url) {
        if (checkCert == null) {
            return null;
        }
        List<URL> urllist = new ArrayList<>(this.urls);
        if (urllist.size() == 0) {
            Logger logger = LOGGER;
            logger.info("Looking for CRL for certificate " + checkCert.getSubjectDN());
            if (url == null) {
                try {
                    url = CertificateUtil.getCRLURL(checkCert);
                } catch (Exception e) {
                    LOGGER.info("Skipped CRL url: " + e.getMessage());
                }
            }
            if (url != null) {
                urllist.add(new URL(url));
                logger.info("Found CRL url: " + url);
            } else {
                throw new IllegalArgumentException("Passed url can not be null.");
            }
        }
        List<byte[]> ar = new ArrayList<>();
        for (URL urlt : urllist) {
            try {
                LOGGER.info("Checking CRL: " + urlt);
                InputStream inp = SignUtils.getHttpResponse(urlt);
                byte[] buf = new byte[1024];
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                while (true) {
                    int n = inp.read(buf, 0, buf.length);
                    if (n <= 0) {
                        break;
                    }
                    bout.write(buf, 0, n);
                }
                inp.close();
                ar.add(bout.toByteArray());
                LOGGER.info("Added CRL found at: " + urlt);
            } catch (Exception e2) {
                LOGGER.info("Skipped CRL: " + e2.getMessage() + " for " + urlt);
            }
        }
        return ar;
    }

    /* access modifiers changed from: protected */
    public void addUrl(String url) {
        try {
            addUrl(new URL(url));
        } catch (IOException e) {
            LOGGER.info("Skipped CRL url (malformed): " + url);
        }
    }

    /* access modifiers changed from: protected */
    public void addUrl(URL url) {
        if (this.urls.contains(url)) {
            LOGGER.info("Skipped CRL url (duplicate): " + url);
            return;
        }
        this.urls.add(url);
        LOGGER.info("Added CRL url: " + url);
    }

    public int getUrlsSize() {
        return this.urls.size();
    }
}
