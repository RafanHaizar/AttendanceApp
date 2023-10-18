package com.itextpdf.signatures;

import com.itextpdf.kernel.PdfException;
import java.security.cert.CRL;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CrlClientOffline implements ICrlClient {
    private List<byte[]> crls;

    public CrlClientOffline(byte[] crlEncoded) {
        ArrayList arrayList = new ArrayList();
        this.crls = arrayList;
        arrayList.add(crlEncoded);
    }

    public CrlClientOffline(CRL crl) {
        ArrayList arrayList = new ArrayList();
        this.crls = arrayList;
        try {
            arrayList.add(((X509CRL) crl).getEncoded());
        } catch (Exception ex) {
            throw new PdfException((Throwable) ex);
        }
    }

    public Collection<byte[]> getEncoded(X509Certificate checkCert, String url) {
        return this.crls;
    }
}
