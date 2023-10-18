package kotlinx.coroutines.sync;

import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016R\u0010\u0010\u0002\u001a\u00020\u00018\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, mo113d2 = {"Lkotlinx/coroutines/sync/Empty;", "", "locked", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Mutex.kt */
final class Empty {
    public final Object locked;

    public Empty(Object locked2) {
        this.locked = locked2;
    }

    public String toString() {
        return "Empty[" + this.locked + ']';
    }
}
