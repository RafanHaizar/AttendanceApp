package kotlin.ranges;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.internal.UProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(mo112d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0017\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\"\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u000eH\u0016J\u0012\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0002ø\u0001\u0000J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0013\u0010\b\u001a\u00020\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u0013\u0010\n\u001a\u00020\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fø\u0001\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u0019"}, mo113d2 = {"Lkotlin/ranges/ULongProgression;", "", "Lkotlin/ULong;", "start", "endInclusive", "step", "", "(JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "J", "last", "getStep", "()J", "equals", "", "other", "", "hashCode", "", "isEmpty", "iterator", "", "toString", "", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ULongRange.kt */
public class ULongProgression implements Iterable<ULong>, KMappedMarker {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final long first;
    private final long last;
    private final long step;

    public /* synthetic */ ULongProgression(long j, long j2, long j3, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3);
    }

    private ULongProgression(long start, long endInclusive, long step2) {
        if (step2 == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        } else if (step2 != Long.MIN_VALUE) {
            this.first = start;
            this.last = UProgressionUtilKt.m1759getProgressionLastElement7ftBX0g(start, endInclusive, step2);
            this.step = step2;
        } else {
            throw new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation.");
        }
    }

    /* renamed from: getFirst-s-VKNKU  reason: not valid java name */
    public final long m1781getFirstsVKNKU() {
        return this.first;
    }

    /* renamed from: getLast-s-VKNKU  reason: not valid java name */
    public final long m1782getLastsVKNKU() {
        return this.last;
    }

    public final long getStep() {
        return this.step;
    }

    public final Iterator<ULong> iterator() {
        return new ULongProgressionIterator(this.first, this.last, this.step, (DefaultConstructorMarker) null);
    }

    public boolean isEmpty() {
        int i = (this.step > 0 ? 1 : (this.step == 0 ? 0 : -1));
        int ulongCompare = UnsignedKt.ulongCompare(this.first, this.last);
        if (i > 0) {
            if (ulongCompare > 0) {
                return true;
            }
        } else if (ulongCompare < 0) {
            return true;
        }
        return false;
    }

    public boolean equals(Object other) {
        return (other instanceof ULongProgression) && ((isEmpty() && ((ULongProgression) other).isEmpty()) || (this.first == ((ULongProgression) other).first && this.last == ((ULongProgression) other).last && this.step == ((ULongProgression) other).step));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        long j = this.first;
        long j2 = this.last;
        long j3 = this.step;
        return ((int) (j3 ^ (j3 >>> 32))) + (((((int) ULong.m1517constructorimpl(j ^ ULong.m1517constructorimpl(j >>> 32))) * 31) + ((int) ULong.m1517constructorimpl(j2 ^ ULong.m1517constructorimpl(j2 >>> 32)))) * 31);
    }

    public String toString() {
        StringBuilder sb;
        long j;
        StringBuilder sb2;
        if (this.step > 0) {
            sb2 = sb.append(ULong.m1562toStringimpl(this.first)).append("..").append(ULong.m1562toStringimpl(this.last)).append(" step ");
            j = this.step;
        } else {
            sb = new StringBuilder();
            sb2 = sb.append(ULong.m1562toStringimpl(this.first)).append(" downTo ").append(ULong.m1562toStringimpl(this.last)).append(" step ");
            j = -this.step;
        }
        return sb2.append(j).toString();
    }

    @Metadata(mo112d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, mo113d2 = {"Lkotlin/ranges/ULongProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/ULongProgression;", "rangeStart", "Lkotlin/ULong;", "rangeEnd", "step", "", "fromClosedRange-7ftBX0g", "(JJJ)Lkotlin/ranges/ULongProgression;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: ULongRange.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* renamed from: fromClosedRange-7ftBX0g  reason: not valid java name */
        public final ULongProgression m1783fromClosedRange7ftBX0g(long rangeStart, long rangeEnd, long step) {
            return new ULongProgression(rangeStart, rangeEnd, step, (DefaultConstructorMarker) null);
        }
    }
}
