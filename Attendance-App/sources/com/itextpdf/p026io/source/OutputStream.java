package com.itextpdf.p026io.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/* renamed from: com.itextpdf.io.source.OutputStream */
public class OutputStream<T extends java.io.OutputStream> extends java.io.OutputStream implements Serializable {
    private static final long serialVersionUID = -5337390096148526418L;
    protected boolean closeStream = true;
    protected long currentPos = 0;
    private final ByteBuffer numBuffer = new ByteBuffer(32);
    protected java.io.OutputStream outputStream = null;

    public static boolean getHighPrecision() {
        return ByteUtils.HighPrecision;
    }

    public static void setHighPrecision(boolean value) {
        ByteUtils.HighPrecision = value;
    }

    public OutputStream(java.io.OutputStream outputStream2) {
        this.outputStream = outputStream2;
    }

    protected OutputStream() {
    }

    public void write(int b) throws IOException {
        this.outputStream.write(b);
        this.currentPos++;
    }

    public void write(byte[] b) throws IOException {
        this.outputStream.write(b);
        this.currentPos += (long) b.length;
    }

    public void write(byte[] b, int off, int len) throws IOException {
        this.outputStream.write(b, off, len);
        this.currentPos += (long) len;
    }

    public void writeByte(byte value) {
        try {
            write((int) value);
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotWriteByte, (Throwable) e);
        }
    }

    public void flush() throws IOException {
        this.outputStream.flush();
    }

    public void close() throws IOException {
        if (this.closeStream) {
            this.outputStream.close();
        }
    }

    public T writeLong(long value) {
        try {
            ByteUtils.getIsoBytes((double) value, this.numBuffer.reset());
            write(this.numBuffer.getInternalBuffer(), this.numBuffer.capacity() - this.numBuffer.size(), this.numBuffer.size());
            return this;
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotWriteIntNumber, (Throwable) e);
        }
    }

    public T writeInteger(int value) {
        try {
            ByteUtils.getIsoBytes(value, this.numBuffer.reset());
            write(this.numBuffer.getInternalBuffer(), this.numBuffer.capacity() - this.numBuffer.size(), this.numBuffer.size());
            return this;
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotWriteIntNumber, (Throwable) e);
        }
    }

    public T writeFloat(float value) {
        return writeFloat(value, ByteUtils.HighPrecision);
    }

    public T writeFloat(float value, boolean highPrecision) {
        return writeDouble((double) value, highPrecision);
    }

    public T writeFloats(float[] value) {
        for (int i = 0; i < value.length; i++) {
            writeFloat(value[i]);
            if (i < value.length - 1) {
                writeSpace();
            }
        }
        return this;
    }

    public T writeDouble(double value) {
        return writeDouble(value, ByteUtils.HighPrecision);
    }

    public T writeDouble(double value, boolean highPrecision) {
        try {
            ByteUtils.getIsoBytes(value, this.numBuffer.reset(), highPrecision);
            write(this.numBuffer.getInternalBuffer(), this.numBuffer.capacity() - this.numBuffer.size(), this.numBuffer.size());
            return this;
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotWriteFloatNumber, (Throwable) e);
        }
    }

    public T writeByte(int value) {
        try {
            write(value);
            return this;
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotWriteByte, (Throwable) e);
        }
    }

    public T writeSpace() {
        return writeByte(32);
    }

    public T writeNewLine() {
        return writeByte(10);
    }

    public T writeString(String value) {
        return writeBytes(ByteUtils.getIsoBytes(value));
    }

    public T writeBytes(byte[] b) {
        try {
            write(b);
            return this;
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotWriteBytes, (Throwable) e);
        }
    }

    public T writeBytes(byte[] b, int off, int len) {
        try {
            write(b, off, len);
            return this;
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotWriteBytes, (Throwable) e);
        }
    }

    public long getCurrentPos() {
        return this.currentPos;
    }

    public java.io.OutputStream getOutputStream() {
        return this.outputStream;
    }

    public boolean isCloseStream() {
        return this.closeStream;
    }

    public void setCloseStream(boolean closeStream2) {
        this.closeStream = closeStream2;
    }

    public void assignBytes(byte[] bytes, int count) {
        java.io.OutputStream outputStream2 = this.outputStream;
        if (outputStream2 instanceof ByteArrayOutputStream) {
            ((ByteArrayOutputStream) outputStream2).assignBytes(bytes, count);
            this.currentPos = (long) count;
            return;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.BytesCanBeAssignedToByteArrayOutputStreamOnly);
    }

    public void reset() {
        java.io.OutputStream outputStream2 = this.outputStream;
        if (outputStream2 instanceof ByteArrayOutputStream) {
            ((ByteArrayOutputStream) outputStream2).reset();
            this.currentPos = 0;
            return;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.BytesCanBeResetInByteArrayOutputStreamOnly);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        java.io.OutputStream tempOutputStream = this.outputStream;
        this.outputStream = null;
        out.defaultWriteObject();
        this.outputStream = tempOutputStream;
    }
}
