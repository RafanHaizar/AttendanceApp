package kotlin.time;

import kotlin.Metadata;
import kotlin.time.TimeSource;

@Metadata(mo112d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\bÁ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\f\u001a\u00020\t2\u0006\u0010\u0007\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u0015\u0010\u000f\u001a\u00020\u0006H\u0016ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0004H\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u0015"}, mo113d2 = {"Lkotlin/time/MonotonicTimeSource;", "Lkotlin/time/TimeSource;", "()V", "zero", "", "adjustReading", "Lkotlin/time/TimeSource$Monotonic$ValueTimeMark;", "timeMark", "duration", "Lkotlin/time/Duration;", "adjustReading-6QKq23U", "(JJ)J", "elapsedFrom", "elapsedFrom-6eNON_k", "(J)J", "markNow", "markNow-z9LOYto", "()J", "read", "toString", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: MonoTimeSource.kt */
public final class MonotonicTimeSource implements TimeSource {
    public static final MonotonicTimeSource INSTANCE = new MonotonicTimeSource();
    private static final long zero = System.nanoTime();

    private MonotonicTimeSource() {
    }

    public /* bridge */ /* synthetic */ TimeMark markNow() {
        return TimeSource.Monotonic.ValueTimeMark.m1848boximpl(m1839markNowz9LOYto());
    }

    private final long read() {
        return System.nanoTime() - zero;
    }

    public String toString() {
        return "TimeSource(System.nanoTime())";
    }

    /* renamed from: markNow-z9LOYto  reason: not valid java name */
    public long m1839markNowz9LOYto() {
        return TimeSource.Monotonic.ValueTimeMark.m1849constructorimpl(read());
    }

    /* renamed from: elapsedFrom-6eNON_k  reason: not valid java name */
    public final long m1838elapsedFrom6eNON_k(long timeMark) {
        return LongSaturatedMathKt.saturatingDiff(read(), timeMark);
    }

    /* renamed from: adjustReading-6QKq23U  reason: not valid java name */
    public final long m1837adjustReading6QKq23U(long timeMark, long duration) {
        return TimeSource.Monotonic.ValueTimeMark.m1849constructorimpl(LongSaturatedMathKt.m1835saturatingAddpTJri5U(timeMark, duration));
    }
}
