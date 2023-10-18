package kotlin.ranges;

import kotlin.Metadata;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(mo112d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00172\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0017B\u0018\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0002ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u001a\u0010\u0005\u001a\u00020\u00038VX\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u0004\u001a\u00020\u00038VX\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\t\u0010\bø\u0001\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u0018"}, mo113d2 = {"Lkotlin/ranges/ULongRange;", "Lkotlin/ranges/ULongProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/ULong;", "start", "endInclusive", "(JJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive-s-VKNKU", "()J", "getStart-s-VKNKU", "contains", "", "value", "contains-VKZWuLQ", "(J)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ULongRange.kt */
public final class ULongRange extends ULongProgression implements ClosedRange<ULong> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final ULongRange EMPTY = new ULongRange(-1, 0, (DefaultConstructorMarker) null);

    public /* synthetic */ ULongRange(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public /* bridge */ /* synthetic */ boolean contains(Comparable value) {
        return m1785containsVKZWuLQ(((ULong) value).m1568unboximpl());
    }

    public /* bridge */ /* synthetic */ Comparable getEndInclusive() {
        return ULong.m1511boximpl(m1786getEndInclusivesVKNKU());
    }

    public /* bridge */ /* synthetic */ Comparable getStart() {
        return ULong.m1511boximpl(m1787getStartsVKNKU());
    }

    private ULongRange(long start, long endInclusive) {
        super(start, endInclusive, 1, (DefaultConstructorMarker) null);
    }

    /* renamed from: getStart-s-VKNKU  reason: not valid java name */
    public long m1787getStartsVKNKU() {
        return m1781getFirstsVKNKU();
    }

    /* renamed from: getEndInclusive-s-VKNKU  reason: not valid java name */
    public long m1786getEndInclusivesVKNKU() {
        return m1782getLastsVKNKU();
    }

    /* renamed from: contains-VKZWuLQ  reason: not valid java name */
    public boolean m1785containsVKZWuLQ(long value) {
        return UnsignedKt.ulongCompare(m1781getFirstsVKNKU(), value) <= 0 && UnsignedKt.ulongCompare(value, m1782getLastsVKNKU()) <= 0;
    }

    public boolean isEmpty() {
        return UnsignedKt.ulongCompare(m1781getFirstsVKNKU(), m1782getLastsVKNKU()) > 0;
    }

    public boolean equals(Object other) {
        return (other instanceof ULongRange) && ((isEmpty() && ((ULongRange) other).isEmpty()) || (m1781getFirstsVKNKU() == ((ULongRange) other).m1781getFirstsVKNKU() && m1782getLastsVKNKU() == ((ULongRange) other).m1782getLastsVKNKU()));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return ((int) ULong.m1517constructorimpl(m1782getLastsVKNKU() ^ ULong.m1517constructorimpl(m1782getLastsVKNKU() >>> 32))) + (((int) ULong.m1517constructorimpl(m1781getFirstsVKNKU() ^ ULong.m1517constructorimpl(m1781getFirstsVKNKU() >>> 32))) * 31);
    }

    public String toString() {
        return ULong.m1562toStringimpl(m1781getFirstsVKNKU()) + ".." + ULong.m1562toStringimpl(m1782getLastsVKNKU());
    }

    @Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, mo113d2 = {"Lkotlin/ranges/ULongRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/ULongRange;", "getEMPTY", "()Lkotlin/ranges/ULongRange;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: ULongRange.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ULongRange getEMPTY() {
            return ULongRange.EMPTY;
        }
    }
}
