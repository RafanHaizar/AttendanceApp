package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.Symbol;

@Metadata(mo112d1 = {"\u0000\"\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0000\n\u0000\u001a\b\u0010\b\u001a\u00020\tH\u0002\u001a\u000e\u0010\n\u001a\u00020\u0003*\u0004\u0018\u00010\u000bH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, mo113d2 = {"MAGIC", "", "MARKED_NULL", "Lkotlinx/coroutines/debug/internal/Marked;", "MARKED_TRUE", "MIN_CAPACITY", "REHASH", "Lkotlinx/coroutines/internal/Symbol;", "noImpl", "", "mark", "", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ConcurrentWeakMap.kt */
public final class ConcurrentWeakMapKt {
    private static final int MAGIC = -1640531527;
    private static final Marked MARKED_NULL = new Marked((Object) null);
    private static final Marked MARKED_TRUE = new Marked(true);
    private static final int MIN_CAPACITY = 16;
    /* access modifiers changed from: private */
    public static final Symbol REHASH = new Symbol("REHASH");

    /* access modifiers changed from: private */
    public static final Marked mark(Object $this$mark) {
        if ($this$mark == null) {
            return MARKED_NULL;
        }
        if (Intrinsics.areEqual($this$mark, (Object) true)) {
            return MARKED_TRUE;
        }
        return new Marked($this$mark);
    }

    /* access modifiers changed from: private */
    public static final Void noImpl() {
        throw new UnsupportedOperationException("not implemented");
    }
}
