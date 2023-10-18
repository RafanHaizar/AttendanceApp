package org.bouncycastle.jcajce;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;

public class BCLoadStoreParameter implements KeyStore.LoadStoreParameter {

    /* renamed from: in */
    private final InputStream f644in;
    private final OutputStream out;
    private final KeyStore.ProtectionParameter protectionParameter;

    BCLoadStoreParameter(InputStream inputStream, OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter2) {
        this.f644in = inputStream;
        this.out = outputStream;
        this.protectionParameter = protectionParameter2;
    }

    public BCLoadStoreParameter(InputStream inputStream, KeyStore.ProtectionParameter protectionParameter2) {
        this(inputStream, (OutputStream) null, protectionParameter2);
    }

    public BCLoadStoreParameter(InputStream inputStream, char[] cArr) {
        this(inputStream, (KeyStore.ProtectionParameter) new KeyStore.PasswordProtection(cArr));
    }

    public BCLoadStoreParameter(OutputStream outputStream, KeyStore.ProtectionParameter protectionParameter2) {
        this((InputStream) null, outputStream, protectionParameter2);
    }

    public BCLoadStoreParameter(OutputStream outputStream, char[] cArr) {
        this(outputStream, (KeyStore.ProtectionParameter) new KeyStore.PasswordProtection(cArr));
    }

    public InputStream getInputStream() {
        if (this.out == null) {
            return this.f644in;
        }
        throw new UnsupportedOperationException("parameter configured for storage OutputStream present");
    }

    public OutputStream getOutputStream() {
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            return outputStream;
        }
        throw new UnsupportedOperationException("parameter not configured for storage - no OutputStream");
    }

    public KeyStore.ProtectionParameter getProtectionParameter() {
        return this.protectionParameter;
    }
}
