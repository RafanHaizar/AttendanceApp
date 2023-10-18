package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.Metadata;
import kotlin.ranges.RangesKt;

@Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0006\u0010\b\u001a\u00020\u0004J\u0018\u0010\t\u001a\u0004\u0018\u00018\u00002\u0006\u0010\n\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010\u000bJ\u001d\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u000fR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, mo113d2 = {"Lkotlinx/coroutines/internal/ResizableAtomicArray;", "T", "", "initialLength", "", "(I)V", "array", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "currentLength", "get", "index", "(I)Ljava/lang/Object;", "setSynchronized", "", "value", "(ILjava/lang/Object;)V", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ResizableAtomicArray.kt */
public final class ResizableAtomicArray<T> {
    private volatile AtomicReferenceArray<T> array;

    public ResizableAtomicArray(int initialLength) {
        this.array = new AtomicReferenceArray<>(initialLength);
    }

    public final int currentLength() {
        return this.array.length();
    }

    public final T get(int index) {
        AtomicReferenceArray array2 = this.array;
        if (index < array2.length()) {
            return array2.get(index);
        }
        return null;
    }

    public final void setSynchronized(int index, T value) {
        AtomicReferenceArray curArray = this.array;
        int curLen = curArray.length();
        if (index < curLen) {
            curArray.set(index, value);
            return;
        }
        AtomicReferenceArray newArray = new AtomicReferenceArray(RangesKt.coerceAtLeast(index + 1, curLen * 2));
        int i = 0;
        while (i < curLen) {
            int i2 = i;
            i++;
            newArray.set(i2, curArray.get(i2));
        }
        newArray.set(index, value);
        this.array = newArray;
    }
}
