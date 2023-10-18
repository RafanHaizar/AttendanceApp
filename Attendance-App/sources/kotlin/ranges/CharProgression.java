package kotlin.ranges;

import kotlin.Metadata;
import kotlin.collections.CharIterator;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(mo112d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0019B\u001f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0006H\u0016J\b\u0010\u0014\u001a\u00020\u0010H\u0016J\t\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\b\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, mo113d2 = {"Lkotlin/ranges/CharProgression;", "", "", "start", "endInclusive", "step", "", "(CCI)V", "first", "getFirst", "()C", "last", "getLast", "getStep", "()I", "equals", "", "other", "", "hashCode", "isEmpty", "iterator", "Lkotlin/collections/CharIterator;", "toString", "", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Progressions.kt */
public class CharProgression implements Iterable<Character>, KMappedMarker {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final char first;
    private final char last;
    private final int step;

    public CharProgression(char start, char endInclusive, int step2) {
        if (step2 == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        } else if (step2 != Integer.MIN_VALUE) {
            this.first = start;
            this.last = (char) ProgressionUtilKt.getProgressionLastElement((int) start, (int) endInclusive, step2);
            this.step = step2;
        } else {
            throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
        }
    }

    public final char getFirst() {
        return this.first;
    }

    public final char getLast() {
        return this.last;
    }

    public final int getStep() {
        return this.step;
    }

    public CharIterator iterator() {
        return new CharProgressionIterator(this.first, this.last, this.step);
    }

    public boolean isEmpty() {
        if (this.step > 0) {
            if (Intrinsics.compare((int) this.first, (int) this.last) > 0) {
                return true;
            }
        } else if (Intrinsics.compare((int) this.first, (int) this.last) < 0) {
            return true;
        }
        return false;
    }

    public boolean equals(Object other) {
        return (other instanceof CharProgression) && ((isEmpty() && ((CharProgression) other).isEmpty()) || (this.first == ((CharProgression) other).first && this.last == ((CharProgression) other).last && this.step == ((CharProgression) other).step));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (((this.first * 31) + this.last) * 31) + this.step;
    }

    public String toString() {
        int i;
        StringBuilder sb;
        if (this.step > 0) {
            sb = new StringBuilder().append(this.first).append("..").append(this.last).append(" step ");
            i = this.step;
        } else {
            sb = new StringBuilder().append(this.first).append(" downTo ").append(this.last).append(" step ");
            i = -this.step;
        }
        return sb.append(i).toString();
    }

    @Metadata(mo112d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, mo113d2 = {"Lkotlin/ranges/CharProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/CharProgression;", "rangeStart", "", "rangeEnd", "step", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: Progressions.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CharProgression fromClosedRange(char rangeStart, char rangeEnd, int step) {
            return new CharProgression(rangeStart, rangeEnd, step);
        }
    }
}
