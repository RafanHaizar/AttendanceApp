package com.itextpdf.kernel.pdf;

import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.source.ByteBuffer;
import com.itextpdf.p026io.source.PdfTokenizer;
import com.itextpdf.p026io.util.StreamUtil;
import java.nio.charset.StandardCharsets;
import kotlin.UByte;

public class PdfString extends PdfPrimitiveObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 390789504287887010L;
    private int decryptInfoGen;
    private int decryptInfoNum;
    private PdfEncryption decryption;
    protected String encoding;
    protected boolean hexWriting;
    protected String value;

    public PdfString(String value2, String encoding2) {
        this.hexWriting = false;
        if (value2 != null) {
            this.value = value2;
            this.encoding = encoding2;
            return;
        }
        throw new AssertionError();
    }

    public PdfString(String value2) {
        this(value2, (String) null);
    }

    public PdfString(byte[] content) {
        this.hexWriting = false;
        if (content == null || content.length <= 0) {
            this.value = "";
            return;
        }
        StringBuilder str = new StringBuilder(content.length);
        for (byte b : content) {
            str.append((char) (b & UByte.MAX_VALUE));
        }
        this.value = str.toString();
    }

    protected PdfString(byte[] content, boolean hexWriting2) {
        super(content);
        this.hexWriting = false;
        this.hexWriting = hexWriting2;
    }

    private PdfString() {
        this.hexWriting = false;
    }

    public byte getType() {
        return 10;
    }

    public boolean isHexWriting() {
        return this.hexWriting;
    }

    public PdfString setHexWriting(boolean hexWriting2) {
        if (this.value == null) {
            generateValue();
        }
        this.content = null;
        this.hexWriting = hexWriting2;
        return this;
    }

    public String getValue() {
        if (this.value == null) {
            generateValue();
        }
        return this.value;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public String toUnicodeString() {
        String str = this.encoding;
        if (str != null && str.length() != 0) {
            return getValue();
        }
        if (this.content == null) {
            generateContent();
        }
        byte[] b = decodeContent();
        if (b.length >= 2 && b[0] == -2 && b[1] == -1) {
            return PdfEncodings.convertToString(b, PdfEncodings.UNICODE_BIG);
        }
        if (b.length >= 3 && b[0] == -17 && b[1] == -69 && b[2] == -65) {
            return PdfEncodings.convertToString(b, PdfEncodings.UTF8);
        }
        return PdfEncodings.convertToString(b, PdfEncodings.PDF_DOC_ENCODING);
    }

    public byte[] getValueBytes() {
        if (this.value == null) {
            generateValue();
        }
        String str = this.encoding;
        if (str == null || !PdfEncodings.UNICODE_BIG.equals(str) || !PdfEncodings.isPdfDocEncoding(this.value)) {
            return PdfEncodings.convertToBytes(this.value, this.encoding);
        }
        return PdfEncodings.convertToBytes(this.value, PdfEncodings.PDF_DOC_ENCODING);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PdfString that = (PdfString) o;
        String v1 = getValue();
        String v2 = that.getValue();
        if (v1 != null && v1.equals(v2)) {
            String e1 = getEncoding();
            String e2 = that.getEncoding();
            if ((e1 != null || e2 != null) && (e1 == null || !e1.equals(e2))) {
                return false;
            }
            return true;
        }
        return false;
    }

    public String toString() {
        if (this.value == null) {
            return new String(decodeContent(), StandardCharsets.ISO_8859_1);
        }
        return getValue();
    }

    public int hashCode() {
        String v = getValue();
        String e = getEncoding();
        int i = 0;
        int hashCode = (v != null ? v.hashCode() : 0) * 31;
        if (e != null) {
            i = e.hashCode();
        }
        return hashCode + i;
    }

    public void markAsUnencryptedObject() {
        setState(512);
    }

    /* access modifiers changed from: package-private */
    public void setDecryption(int decryptInfoNum2, int decryptInfoGen2, PdfEncryption decryption2) {
        this.decryptInfoNum = decryptInfoNum2;
        this.decryptInfoGen = decryptInfoGen2;
        this.decryption = decryption2;
    }

    /* access modifiers changed from: protected */
    public void generateValue() {
        if (this.content != null) {
            this.value = PdfEncodings.convertToString(decodeContent(), (String) null);
            if (this.decryption != null) {
                this.decryption = null;
                this.content = null;
                return;
            }
            return;
        }
        throw new AssertionError("No byte[] content to generate value");
    }

    /* access modifiers changed from: protected */
    public void generateContent() {
        this.content = encodeBytes(getValueBytes());
    }

    /* access modifiers changed from: protected */
    public boolean encrypt(PdfEncryption encrypt) {
        PdfEncryption pdfEncryption;
        if (!checkState(512) && encrypt != (pdfEncryption = this.decryption)) {
            if (pdfEncryption != null) {
                generateValue();
            }
            if (encrypt != null && !encrypt.isEmbeddedFilesOnly()) {
                this.content = encodeBytes(encrypt.encryptByteArray(getValueBytes()));
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public byte[] decodeContent() {
        byte[] decodedBytes = PdfTokenizer.decodeStringContent(this.content, this.hexWriting);
        if (this.decryption == null || checkState(512)) {
            return decodedBytes;
        }
        this.decryption.setHashKeyForNextObject(this.decryptInfoNum, this.decryptInfoGen);
        return this.decryption.decryptByteArray(decodedBytes);
    }

    /* access modifiers changed from: protected */
    public byte[] encodeBytes(byte[] bytes) {
        if (this.hexWriting) {
            ByteBuffer buf = new ByteBuffer(bytes.length * 2);
            for (byte b : bytes) {
                buf.appendHex(b);
            }
            return buf.getInternalBuffer();
        }
        ByteBuffer buf2 = StreamUtil.createBufferedEscapedString(bytes);
        return buf2.toByteArray(1, buf2.size() - 2);
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return new PdfString();
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        PdfString string = (PdfString) from;
        this.value = string.value;
        this.hexWriting = string.hexWriting;
        this.decryption = string.decryption;
        this.decryptInfoNum = string.decryptInfoNum;
        this.decryptInfoGen = string.decryptInfoGen;
        this.encoding = string.encoding;
    }
}
