package kotlinx.coroutines.debug.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, mo113d2 = {"Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "T", "Ljava/lang/ref/WeakReference;", "ref", "queue", "Ljava/lang/ref/ReferenceQueue;", "(Ljava/lang/Object;Ljava/lang/ref/ReferenceQueue;)V", "hash", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ConcurrentWeakMap.kt */
public final class HashedWeakRef<T> extends WeakReference<T> {
    public final int hash;

    public HashedWeakRef(T ref, ReferenceQueue<T> queue) {
        super(ref, queue);
        this.hash = ref == null ? 0 : ref.hashCode();
    }
}
