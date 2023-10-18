package com.itextpdf.kernel.crypto.securityhandler;

import java.io.Serializable;
import java.security.cert.Certificate;

public class PublicKeyRecipient implements Serializable {
    private static final long serialVersionUID = -6985649182567287907L;
    private Certificate certificate = null;
    protected byte[] cms = null;
    private int permission = 0;

    public PublicKeyRecipient(Certificate certificate2, int permission2) {
        this.certificate = certificate2;
        this.permission = permission2;
    }

    public Certificate getCertificate() {
        return this.certificate;
    }

    public int getPermission() {
        return this.permission;
    }

    /* access modifiers changed from: protected */
    public void setCms(byte[] cms2) {
        this.cms = cms2;
    }

    /* access modifiers changed from: protected */
    public byte[] getCms() {
        return this.cms;
    }
}
