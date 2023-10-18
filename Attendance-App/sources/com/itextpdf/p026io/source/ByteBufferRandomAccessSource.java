package com.itextpdf.p026io.source;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import kotlin.UByte;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.source.ByteBufferRandomAccessSource */
class ByteBufferRandomAccessSource implements IRandomAccessSource, Serializable {
    /* access modifiers changed from: private */
    public static final BufferCleaner CLEANER;
    public static final boolean UNMAP_SUPPORTED;
    private static final long serialVersionUID = -1477190062876186034L;
    private byte[] bufferMirror;
    private transient ByteBuffer byteBuffer;

    public ByteBufferRandomAccessSource(ByteBuffer byteBuffer2) {
        this.byteBuffer = byteBuffer2;
    }

    public int get(long position) throws IOException {
        if (position <= 2147483647L) {
            try {
                if (position >= ((long) this.byteBuffer.limit())) {
                    return -1;
                }
                return this.byteBuffer.get((int) position) & UByte.MAX_VALUE;
            } catch (BufferUnderflowException e) {
                return -1;
            }
        } else {
            throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");
        }
    }

    public int get(long position, byte[] bytes, int off, int len) throws IOException {
        if (position > 2147483647L) {
            throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");
        } else if (position >= ((long) this.byteBuffer.limit())) {
            return -1;
        } else {
            this.byteBuffer.position((int) position);
            int bytesFromThisBuffer = Math.min(len, this.byteBuffer.remaining());
            this.byteBuffer.get(bytes, off, bytesFromThisBuffer);
            return bytesFromThisBuffer;
        }
    }

    public long length() {
        return (long) this.byteBuffer.limit();
    }

    public void close() throws IOException {
        clean(this.byteBuffer);
    }

    static {
        Object hack = AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                return BufferCleaner.unmapHackImpl();
            }
        });
        if (hack instanceof BufferCleaner) {
            CLEANER = (BufferCleaner) hack;
            UNMAP_SUPPORTED = true;
            return;
        }
        CLEANER = null;
        UNMAP_SUPPORTED = false;
    }

    private static boolean clean(final ByteBuffer buffer) {
        if (buffer == null || !buffer.isDirect()) {
            return false;
        }
        return ((Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            public Boolean run() {
                Boolean success = Boolean.FALSE;
                try {
                    if (ByteBufferRandomAccessSource.UNMAP_SUPPORTED) {
                        ByteBufferRandomAccessSource.CLEANER.freeBuffer(buffer.toString(), buffer);
                    } else {
                        Class[] clsArr = null;
                        Method getCleanerMethod = buffer.getClass().getMethod("cleaner", (Class[]) null);
                        getCleanerMethod.setAccessible(true);
                        Object[] objArr = null;
                        Object cleaner = getCleanerMethod.invoke(buffer, (Object[]) null);
                        Class[] clsArr2 = null;
                        Object[] objArr2 = null;
                        cleaner.getClass().getMethod("clean", (Class[]) null).invoke(cleaner, (Object[]) null);
                    }
                    return Boolean.TRUE;
                } catch (Exception e) {
                    LoggerFactory.getLogger((Class<?>) ByteBufferRandomAccessSource.class).debug(e.getMessage());
                    return success;
                }
            }
        })).booleanValue();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        ByteBuffer byteBuffer2 = this.byteBuffer;
        if (byteBuffer2 == null || !byteBuffer2.hasArray()) {
            ByteBuffer byteBuffer3 = this.byteBuffer;
            if (byteBuffer3 != null) {
                this.bufferMirror = byteBuffer3.array();
            }
            out.defaultWriteObject();
            return;
        }
        throw new NotSerializableException(this.byteBuffer.getClass().toString());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        byte[] bArr = this.bufferMirror;
        if (bArr != null) {
            this.byteBuffer = ByteBuffer.wrap(bArr);
            this.bufferMirror = null;
        }
    }
}
