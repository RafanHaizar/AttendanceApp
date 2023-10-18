package com.itextpdf.signatures;

import com.itextpdf.kernel.PdfException;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

public class KeyStoreUtil {
    public static KeyStore loadCacertsKeyStore(String provider) {
        KeyStore k;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(new File(new File(new File(System.getProperty("java.home"), "lib"), "security"), "cacerts"));
            if (provider == null) {
                k = KeyStore.getInstance("JKS");
            } else {
                k = KeyStore.getInstance("JKS", provider);
            }
            k.load(fin, (char[]) null);
            try {
                fin.close();
            } catch (Exception e) {
            }
            return k;
        } catch (Exception e2) {
            throw new PdfException((Throwable) e2);
        } catch (Throwable th) {
            if (fin != null) {
                try {
                    fin.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
    }

    public static KeyStore loadCacertsKeyStore() {
        return loadCacertsKeyStore((String) null);
    }
}
