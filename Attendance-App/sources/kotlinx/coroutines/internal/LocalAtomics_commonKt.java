package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\".\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0000\u001a\u00020\u00018À\u0002@À\u0002X\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\b"}, mo113d2 = {"value", "", "Ljava/util/concurrent/atomic/AtomicInteger;", "Lkotlinx/coroutines/internal/LocalAtomicInt;", "getValue", "(Ljava/util/concurrent/atomic/AtomicInteger;)I", "setValue", "(Ljava/util/concurrent/atomic/AtomicInteger;I)V", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: LocalAtomics.common.kt */
public final class LocalAtomics_commonKt {
    public static final int getValue(AtomicInteger $this$value) {
        return $this$value.get();
    }

    public static final void setValue(AtomicInteger $this$value, int value) {
        $this$value.set(value);
    }
}
