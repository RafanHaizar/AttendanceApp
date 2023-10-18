package com.itextpdf.kernel.crypto.securityhandler;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.crypto.IDecryptor;
import com.itextpdf.kernel.crypto.OutputStreamEncryption;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;

public abstract class SecurityHandler implements Serializable {
    private static final long serialVersionUID = 7980424575363686173L;
    protected byte[] extra = new byte[5];
    protected transient MessageDigest md5;
    protected byte[] mkey = new byte[0];
    protected byte[] nextObjectKey;
    protected int nextObjectKeySize;

    public abstract IDecryptor getDecryptor();

    public abstract OutputStreamEncryption getEncryptionStream(OutputStream outputStream);

    protected SecurityHandler() {
        safeInitMessageDigest();
    }

    public void setHashKeyForNextObject(int objNumber, int objGeneration) {
        this.md5.reset();
        byte[] bArr = this.extra;
        bArr[0] = (byte) objNumber;
        bArr[1] = (byte) (objNumber >> 8);
        bArr[2] = (byte) (objNumber >> 16);
        bArr[3] = (byte) objGeneration;
        bArr[4] = (byte) (objGeneration >> 8);
        this.md5.update(this.mkey);
        this.md5.update(this.extra);
        this.nextObjectKey = this.md5.digest();
        int length = this.mkey.length + 5;
        this.nextObjectKeySize = length;
        if (length > 16) {
            this.nextObjectKeySize = 16;
        }
    }

    private void safeInitMessageDigest() {
        try {
            this.md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new PdfException(PdfException.PdfEncryption, (Throwable) e);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        safeInitMessageDigest();
    }
}
