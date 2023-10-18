package kotlin.ranges;

import kotlin.Metadata;
import kotlin.collections.LongIterator;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(mo112d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u000eH\u0016J\t\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0019"}, mo113d2 = {"Lkotlin/ranges/LongProgression;", "", "", "start", "endInclusive", "step", "(JJJ)V", "first", "getFirst", "()J", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "", "isEmpty", "iterator", "Lkotlin/collections/LongIterator;", "toString", "", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Progressions.kt */
public class LongProgression implements Iterable<Long>, KMappedMarker {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final long first;
    private final long last;
    private final long step;

    public LongProgression(long start, long endInclusive, long step2) {
        if (step2 == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        } else if (step2 != Long.MIN_VALUE) {
            this.first = start;
            this.last = ProgressionUtilKt.getProgressionLastElement(start, endInclusive, step2);
            this.step = step2;
        } else {
            throw new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation.");
        }
    }

    public final long getFirst() {
        return this.first;
    }

    public final long getLast() {
        return this.last;
    }

    public final long getStep() {
        return this.step;
    }

    public LongIterator iterator() {
        return new LongProgressionIterator(this.first, this.last, this.step);
    }

    public boolean isEmpty() {
        int i = (this.step > 0 ? 1 : (this.step == 0 ? 0 : -1));
        long j = this.first;
        long j2 = this.last;
        if (i > 0) {
            if (j > j2) {
                return true;
            }
        } else if (j < j2) {
            return true;
        }
        return false;
    }

    public boolean equals(Object other) {
        return (other instanceof LongProgression) && ((isEmpty() && ((LongProgression) other).isEmpty()) || (this.first == ((LongProgression) other).first && this.last == ((LongProgression) other).last && this.step == ((LongProgression) other).step));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        long j = (long) 31;
        long j2 = this.first;
        long j3 = this.last;
        long j4 = j * (((j2 ^ (j2 >>> 32)) * j) + (j3 ^ (j3 >>> 32)));
        long j5 = this.step;
        return (int) (j4 + (j5 ^ (j5 >>> 32)));
    }

    public String toString() {
        StringBuilder sb;
        long j;
        StringBuilder sb2;
        if (this.step > 0) {
            sb2 = sb.append(this.first).append("..").append(this.last).append(" step ");
            j = this.step;
        } else {
            sb = new StringBuilder();
            sb2 = sb.append(this.first).append(" downTo ").append(this.last).append(" step ");
            j = -this.step;
        }
        return sb2.append(j).toString();
    }

    @Metadata(mo112d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006¨\u0006\t"}, mo113d2 = {"Lkotlin/ranges/LongProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/LongProgression;", "rangeStart", "", "rangeEnd", "step", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: Progressions.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LongProgression fromClosedRange(long rangeStart, long rangeEnd, long step) {
            return new LongProgression(rangeStart, rangeEnd, step);
        }
    }
}
