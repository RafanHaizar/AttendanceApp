package org.bouncycastle.jcajce.spec;

import java.security.spec.EncodedKeySpec;

public class OpenSSHPrivateKeySpec extends EncodedKeySpec {
    private final String format;

    public OpenSSHPrivateKeySpec(byte[] bArr) {
        super(bArr);
        String str;
        byte b = bArr[0];
        if (b == 48) {
            str = "ASN.1";
        } else if (b == 111) {
            str = "OpenSSH";
        } else {
            throw new IllegalArgumentException("unknown byte encoding");
        }
        this.format = str;
    }

    public String getFormat() {
        return this.format;
    }
}
