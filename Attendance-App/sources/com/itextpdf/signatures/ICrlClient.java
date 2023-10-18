package com.itextpdf.signatures;

import java.security.cert.X509Certificate;
import java.util.Collection;

public interface ICrlClient {
    Collection<byte[]> getEncoded(X509Certificate x509Certificate, String str);
}
