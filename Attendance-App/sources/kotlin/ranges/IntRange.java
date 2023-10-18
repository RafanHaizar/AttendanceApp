package kotlin.ranges;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(mo112d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0014B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0002J\u0013\u0010\r\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0003H\u0016J\b\u0010\u0011\u001a\u00020\u000bH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0014\u0010\u0005\u001a\u00020\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\b¨\u0006\u0015"}, mo113d2 = {"Lkotlin/ranges/IntRange;", "Lkotlin/ranges/IntProgression;", "Lkotlin/ranges/ClosedRange;", "", "start", "endInclusive", "(II)V", "getEndInclusive", "()Ljava/lang/Integer;", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Ranges.kt */
public final class IntRange extends IntProgression implements ClosedRange<Integer> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final IntRange EMPTY = new IntRange(1, 0);

    public IntRange(int start, int endInclusive) {
        super(start, endInclusive, 1);
    }

    public /* bridge */ /* synthetic */ boolean contains(Comparable value) {
        return contains(((Number) value).intValue());
    }

    public Integer getStart() {
        return Integer.valueOf(getFirst());
    }

    public Integer getEndInclusive() {
        return Integer.valueOf(getLast());
    }

    public boolean contains(int value) {
        return getFirst() <= value && value <= getLast();
    }

    public boolean isEmpty() {
        return getFirst() > getLast();
    }

    public boolean equals(Object other) {
        return (other instanceof IntRange) && ((isEmpty() && ((IntRange) other).isEmpty()) || (getFirst() == ((IntRange) other).getFirst() && getLast() == ((IntRange) other).getLast()));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (getFirst() * 31) + getLast();
    }

    public String toString() {
        return getFirst() + ".." + getLast();
    }

    @Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, mo113d2 = {"Lkotlin/ranges/IntRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/IntRange;", "getEMPTY", "()Lkotlin/ranges/IntRange;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: Ranges.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IntRange getEMPTY() {
            return IntRange.EMPTY;
        }
    }
}
