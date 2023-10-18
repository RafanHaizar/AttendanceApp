package kotlinx.coroutines.internal;

import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(mo112d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\b0\tj\u0002`\n2\u000e\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R4\u0010\u0005\u001a(\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\b0\tj\u0002`\n0\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo113d2 = {"Lkotlinx/coroutines/internal/WeakMapCtorCache;", "Lkotlinx/coroutines/internal/CtorCache;", "()V", "cacheLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "exceptionCtors", "Ljava/util/WeakHashMap;", "Ljava/lang/Class;", "", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/Ctor;", "get", "key", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ExceptionsConstructor.kt */
final class WeakMapCtorCache extends CtorCache {
    public static final WeakMapCtorCache INSTANCE = new WeakMapCtorCache();
    private static final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private static final WeakHashMap<Class<? extends Throwable>, Function1<Throwable, Throwable>> exceptionCtors = new WeakHashMap<>();

    private WeakMapCtorCache() {
    }

    /* JADX INFO: finally extract failed */
    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable> get(java.lang.Class<? extends java.lang.Throwable> r10) {
        /*
            r9 = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = cacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            r1.lock()
            r2 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r3 = exceptionCtors     // Catch:{ all -> 0x007d }
            java.lang.Object r3 = r3.get(r10)     // Catch:{ all -> 0x007d }
            kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3     // Catch:{ all -> 0x007d }
            if (r3 != 0) goto L_0x0076
            r1.unlock()
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L_0x0027
            int r2 = r0.getReadHoldCount()
            goto L_0x0028
        L_0x0027:
            r2 = 0
        L_0x0028:
            r4 = 0
        L_0x0029:
            if (r4 >= r2) goto L_0x0031
            int r4 = r4 + 1
            r1.unlock()
            goto L_0x0029
        L_0x0031:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            r4 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r5 = exceptionCtors     // Catch:{ all -> 0x0069 }
            java.lang.Object r6 = r5.get(r10)     // Catch:{ all -> 0x0069 }
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6     // Catch:{ all -> 0x0069 }
            if (r6 != 0) goto L_0x005a
            kotlin.jvm.functions.Function1 r6 = kotlinx.coroutines.internal.ExceptionsConstructorKt.createConstructor(r10)     // Catch:{ all -> 0x0069 }
            r7 = r6
            r8 = 0
            java.util.Map r5 = (java.util.Map) r5     // Catch:{ all -> 0x0069 }
            r5.put(r10, r7)     // Catch:{ all -> 0x0069 }
        L_0x004e:
            if (r3 >= r2) goto L_0x0056
            int r3 = r3 + 1
            r1.lock()
            goto L_0x004e
        L_0x0056:
            r0.unlock()
            return r6
        L_0x005a:
            r5 = r6
            r6 = 0
        L_0x005d:
            if (r3 >= r2) goto L_0x0065
            int r3 = r3 + 1
            r1.lock()
            goto L_0x005d
        L_0x0065:
            r0.unlock()
            return r5
        L_0x0069:
            r4 = move-exception
        L_0x006a:
            if (r3 >= r2) goto L_0x0072
            int r3 = r3 + 1
            r1.lock()
            goto L_0x006a
        L_0x0072:
            r0.unlock()
            throw r4
        L_0x0076:
            r0 = r3
            r3 = 0
            r1.unlock()
            return r0
        L_0x007d:
            r0 = move-exception
            r1.unlock()
            goto L_0x0083
        L_0x0082:
            throw r0
        L_0x0083:
            goto L_0x0082
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.WeakMapCtorCache.get(java.lang.Class):kotlin.jvm.functions.Function1");
    }
}
