package kotlin.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a6\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\bø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0005\u001a6\u0010\u0006\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00072\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\bø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\b\u001a6\u0010\t\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\bø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0005\u0002\u0007\n\u0005\b20\u0001¨\u0006\n"}, mo113d2 = {"read", "T", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "action", "Lkotlin/Function0;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLock", "Ljava/util/concurrent/locks/Lock;", "(Ljava/util/concurrent/locks/Lock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "write", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Locks.kt */
public final class LocksKt {
    private static final <T> T withLock(Lock $this$withLock, Function0<? extends T> action) {
        Intrinsics.checkNotNullParameter($this$withLock, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        $this$withLock.lock();
        try {
            return action.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            $this$withLock.unlock();
            InlineMarker.finallyEnd(1);
        }
    }

    private static final <T> T read(ReentrantReadWriteLock $this$read, Function0<? extends T> action) {
        Intrinsics.checkNotNullParameter($this$read, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        ReentrantReadWriteLock.ReadLock rl = $this$read.readLock();
        rl.lock();
        try {
            return action.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            rl.unlock();
            InlineMarker.finallyEnd(1);
        }
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    private static final <T> T write(java.util.concurrent.locks.ReentrantReadWriteLock r8, kotlin.jvm.functions.Function0<? extends T> r9) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            java.lang.String r0 = "action"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r8.readLock()
            int r1 = r8.getWriteHoldCount()
            r2 = 0
            if (r1 != 0) goto L_0x001b
            int r1 = r8.getReadHoldCount()
            goto L_0x001c
        L_0x001b:
            r1 = 0
        L_0x001c:
            r3 = 0
        L_0x001d:
            if (r3 >= r1) goto L_0x0027
            r4 = r3
            r5 = 0
            r0.unlock()
            int r3 = r3 + 1
            goto L_0x001d
        L_0x0027:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r3 = r8.writeLock()
            r3.lock()
            r4 = 1
            java.lang.Object r5 = r9.invoke()     // Catch:{ all -> 0x0049 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
        L_0x0038:
            if (r2 >= r1) goto L_0x0042
            r6 = r2
            r7 = 0
            r0.lock()
            int r2 = r2 + 1
            goto L_0x0038
        L_0x0042:
            r3.unlock()
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            return r5
        L_0x0049:
            r5 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
        L_0x004d:
            if (r2 >= r1) goto L_0x0057
            r6 = r2
            r7 = 0
            r0.lock()
            int r2 = r2 + 1
            goto L_0x004d
        L_0x0057:
            r3.unlock()
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            goto L_0x005f
        L_0x005e:
            throw r5
        L_0x005f:
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.concurrent.LocksKt.write(java.util.concurrent.locks.ReentrantReadWriteLock, kotlin.jvm.functions.Function0):java.lang.Object");
    }
}
