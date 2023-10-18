package kotlin.time;

import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, mo113d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(J)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: TimeSources.kt */
public final class TestTimeSource extends AbstractLongTimeSource {
    private long reading;

    public TestTimeSource() {
        super(DurationUnit.NANOSECONDS);
    }

    /* access modifiers changed from: protected */
    public long read() {
        return this.reading;
    }

    /* renamed from: plusAssign-LRDsOJo  reason: not valid java name */
    public final void m1841plusAssignLRDsOJo(long duration) {
        long newReading;
        long longDelta = Duration.m1157toLongimpl(duration, getUnit());
        if (longDelta == Long.MIN_VALUE || longDelta == Long.MAX_VALUE) {
            double delta = Duration.m1154toDoubleimpl(duration, getUnit());
            double d = (double) this.reading;
            Double.isNaN(d);
            double newReading2 = d + delta;
            if (newReading2 > 9.223372036854776E18d || newReading2 < -9.223372036854776E18d) {
                m1840overflowLRDsOJo(duration);
            }
            newReading = (long) newReading2;
        } else {
            long j = this.reading;
            newReading = j + longDelta;
            if ((j ^ longDelta) >= 0 && (j ^ newReading) < 0) {
                m1840overflowLRDsOJo(duration);
            }
        }
        this.reading = newReading;
    }

    /* renamed from: overflow-LRDsOJo  reason: not valid java name */
    private final void m1840overflowLRDsOJo(long duration) {
        throw new IllegalStateException("TestTimeSource will overflow if its reading " + this.reading + "ns is advanced by " + Duration.m1160toStringimpl(duration) + '.');
    }
}
