package com.itextpdf.p026io.source;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;

/* renamed from: com.itextpdf.io.source.BufferCleaner */
class BufferCleaner {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    final Method method;
    final Object theUnsafe;
    Class<?> unmappableBufferClass;

    BufferCleaner(Class<?> unmappableBufferClass2, Method method2, Object theUnsafe2) {
        this.unmappableBufferClass = unmappableBufferClass2;
        this.method = method2;
        this.theUnsafe = theUnsafe2;
    }

    /* access modifiers changed from: package-private */
    public void freeBuffer(String resourceDescription, final ByteBuffer buffer) throws IOException {
        if (!Objects.equals(Void.TYPE, this.method.getReturnType())) {
            throw new AssertionError();
        } else if (this.method.getParameterTypes().length != 1) {
            throw new AssertionError();
        } else if (!Objects.equals(ByteBuffer.class, this.method.getParameterTypes()[0])) {
            throw new AssertionError();
        } else if (!buffer.isDirect()) {
            throw new IllegalArgumentException("unmapping only works with direct buffers");
        } else if (this.unmappableBufferClass.isInstance(buffer)) {
            Throwable error = (Throwable) AccessController.doPrivileged(new PrivilegedAction<Throwable>() {
                public Throwable run() {
                    try {
                        BufferCleaner.this.method.invoke(BufferCleaner.this.theUnsafe, new Object[]{buffer});
                        return null;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        return e;
                    }
                }
            });
            if (error != null) {
                throw new IOException("Unable to unmap the mapped buffer: " + resourceDescription, error);
            }
        } else {
            throw new IllegalArgumentException("buffer is not an instance of " + this.unmappableBufferClass.getName());
        }
    }

    static Object unmapHackImpl() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Method method2 = unsafeClass.getDeclaredMethod("invokeCleaner", new Class[]{ByteBuffer.class});
            Field f = unsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return new BufferCleaner(ByteBuffer.class, method2, f.get((Object) null));
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
