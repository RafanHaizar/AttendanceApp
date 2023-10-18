package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.LongIterator;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\b\u001a\u00020\tH\u0002J\b\u0010\r\u001a\u00020\u0003H\u0016R\u000e\u0010\u0007\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000e"}, mo113d2 = {"Lkotlin/ranges/LongProgressionIterator;", "Lkotlin/collections/LongIterator;", "first", "", "last", "step", "(JJJ)V", "finalElement", "hasNext", "", "next", "getStep", "()J", "nextLong", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ProgressionIterators.kt */
public final class LongProgressionIterator extends LongIterator {
    private final long finalElement;
    private boolean hasNext;
    private long next;
    private final long step;

    public LongProgressionIterator(long first, long last, long step2) {
        this.step = step2;
        this.finalElement = last;
        boolean z = true;
        int i = (first > last ? 1 : (first == last ? 0 : -1));
        if (step2 <= 0 ? i < 0 : i > 0) {
            z = false;
        }
        this.hasNext = z;
        this.next = z ? first : last;
    }

    public final long getStep() {
        return this.step;
    }

    public boolean hasNext() {
        return this.hasNext;
    }

    public long nextLong() {
        long value = this.next;
        if (value != this.finalElement) {
            this.next += this.step;
        } else if (this.hasNext) {
            this.hasNext = false;
        } else {
            throw new NoSuchElementException();
        }
        return value;
    }
}
