package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import org.slf4j.LoggerFactory;

public class PdfStream extends PdfDictionary {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -8259929152054328141L;
    protected int compressionLevel;
    private InputStream inputStream;
    private int length;
    private long offset;
    protected PdfOutputStream outputStream;

    public PdfStream(byte[] bytes, int compressionLevel2) {
        this.length = -1;
        setState(64);
        this.compressionLevel = compressionLevel2;
        if (bytes == null || bytes.length <= 0) {
            this.outputStream = new PdfOutputStream(new ByteArrayOutputStream());
            return;
        }
        PdfOutputStream pdfOutputStream = new PdfOutputStream(new ByteArrayOutputStream(bytes.length));
        this.outputStream = pdfOutputStream;
        pdfOutputStream.writeBytes(bytes);
    }

    public PdfStream(byte[] bytes) {
        this(bytes, Integer.MIN_VALUE);
    }

    public PdfStream(PdfDocument doc, InputStream inputStream2, int compressionLevel2) {
        this.length = -1;
        if (doc != null) {
            makeIndirect(doc);
            if (inputStream2 != null) {
                this.inputStream = inputStream2;
                this.compressionLevel = compressionLevel2;
                put(PdfName.Length, new PdfNumber(-1).makeIndirect(doc));
                return;
            }
            throw new IllegalArgumentException("The input stream in PdfStream constructor can not be null.");
        }
        throw new PdfException(PdfException.CannotCreatePdfStreamByInputStreamWithoutPdfDocument);
    }

    public PdfStream(PdfDocument doc, InputStream inputStream2) {
        this(doc, inputStream2, Integer.MIN_VALUE);
    }

    public PdfStream(int compressionLevel2) {
        this((byte[]) null, compressionLevel2);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public PdfStream() {
        this((byte[]) null);
        byte[] bArr = null;
    }

    protected PdfStream(OutputStream outputStream2) {
        this.length = -1;
        this.outputStream = new PdfOutputStream(outputStream2);
        this.compressionLevel = Integer.MIN_VALUE;
        setState(64);
    }

    PdfStream(long offset2, PdfDictionary keys) {
        this.length = -1;
        this.compressionLevel = Integer.MIN_VALUE;
        this.offset = offset2;
        putAll(keys);
        PdfNumber length2 = getAsNumber(PdfName.Length);
        if (length2 == null) {
            this.length = 0;
        } else {
            this.length = length2.intValue();
        }
    }

    public PdfOutputStream getOutputStream() {
        return this.outputStream;
    }

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public void setCompressionLevel(int compressionLevel2) {
        this.compressionLevel = compressionLevel2;
    }

    public byte getType() {
        return 9;
    }

    public int getLength() {
        return this.length;
    }

    public byte[] getBytes() {
        return getBytes(true);
    }

    public byte[] getBytes(boolean decoded) {
        PdfReader reader;
        if (isFlushed()) {
            throw new PdfException(PdfException.CannotOperateWithFlushedPdfStream);
        } else if (this.inputStream != null) {
            LoggerFactory.getLogger((Class<?>) PdfStream.class).warn("PdfStream was created by InputStream.getBytes() always returns null in this case");
            return null;
        } else {
            PdfOutputStream pdfOutputStream = this.outputStream;
            if (pdfOutputStream == null || pdfOutputStream.getOutputStream() == null) {
                if (getIndirectReference() == null || (reader = getIndirectReference().getReader()) == null) {
                    return null;
                }
                try {
                    return reader.readStreamBytes(this, decoded);
                } catch (IOException ioe) {
                    throw new PdfException(PdfException.CannotGetPdfStreamBytes, ioe, this);
                }
            } else if (this.outputStream.getOutputStream() instanceof ByteArrayOutputStream) {
                try {
                    this.outputStream.getOutputStream().flush();
                    byte[] bytes = ((ByteArrayOutputStream) this.outputStream.getOutputStream()).toByteArray();
                    if (!decoded || !containsKey(PdfName.Filter)) {
                        return bytes;
                    }
                    return PdfReader.decodeBytes(bytes, this);
                } catch (IOException ioe2) {
                    throw new PdfException(PdfException.CannotGetPdfStreamBytes, ioe2, this);
                }
            } else {
                throw new AssertionError("Invalid OutputStream: ByteArrayByteArrayOutputStream expected");
            }
        }
    }

    public void setData(byte[] bytes) {
        setData(bytes, false);
    }

    public void setData(byte[] bytes, boolean append) {
        if (isFlushed()) {
            throw new PdfException(PdfException.CannotOperateWithFlushedPdfStream);
        } else if (this.inputStream == null) {
            boolean outputStreamIsUninitialized = this.outputStream == null;
            if (outputStreamIsUninitialized) {
                this.outputStream = new PdfOutputStream(new ByteArrayOutputStream());
            }
            if (append) {
                if (!(!outputStreamIsUninitialized || getIndirectReference() == null || getIndirectReference().getReader() == null) || (!outputStreamIsUninitialized && containsKey(PdfName.Filter))) {
                    try {
                        byte[] oldBytes = getBytes();
                        this.outputStream.assignBytes(oldBytes, oldBytes.length);
                    } catch (PdfException ex) {
                        throw new PdfException(PdfException.CannotReadAStreamInOrderToAppendNewBytes, (Throwable) ex);
                    }
                }
                if (bytes != null) {
                    this.outputStream.writeBytes(bytes);
                }
            } else if (bytes != null) {
                this.outputStream.assignBytes(bytes, bytes.length);
            } else {
                this.outputStream.reset();
            }
            this.offset = 0;
            remove(PdfName.Filter);
            remove(PdfName.DecodeParms);
        } else {
            throw new PdfException("Cannot set data to PdfStream which was created by InputStream.");
        }
    }

    /* access modifiers changed from: protected */
    public PdfObject newInstance() {
        return new PdfStream();
    }

    /* access modifiers changed from: protected */
    public long getOffset() {
        return this.offset;
    }

    /* access modifiers changed from: protected */
    public void updateLength(int length2) {
        this.length = length2;
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        super.copyContent(from, document);
        PdfStream stream = (PdfStream) from;
        if (this.inputStream == null) {
            try {
                this.outputStream.write(stream.getBytes(false));
            } catch (IOException ioe) {
                throw new PdfException(PdfException.CannotCopyObjectContent, ioe, stream);
            }
        } else {
            throw new AssertionError("Try to copy the PdfStream that has been just created.");
        }
    }

    /* access modifiers changed from: protected */
    public void initOutputStream(OutputStream stream) {
        if (getOutputStream() == null && this.inputStream == null) {
            this.outputStream = new PdfOutputStream(stream != null ? stream : new ByteArrayOutputStream());
        }
    }

    /* access modifiers changed from: protected */
    public void releaseContent() {
        super.releaseContent();
        try {
            PdfOutputStream pdfOutputStream = this.outputStream;
            if (pdfOutputStream != null) {
                pdfOutputStream.close();
                this.outputStream = null;
            }
        } catch (IOException e) {
            throw new PdfException("I/O exception.", (Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public InputStream getInputStream() {
        return this.inputStream;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        InputStream inputStream2 = this.inputStream;
        if (inputStream2 == null || (inputStream2 instanceof Serializable)) {
            out.defaultWriteObject();
            return;
        }
        InputStream backup = this.inputStream;
        this.inputStream = null;
        LoggerFactory.getLogger(getClass()).warn(LogMessageConstant.INPUT_STREAM_CONTENT_IS_LOST_ON_PDFSTREAM_SERIALIZATION);
        this.inputStream = backup;
    }
}
