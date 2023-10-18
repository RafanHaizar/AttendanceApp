package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.TimeMark;

@Metadata(mo112d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH$R\u0014\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, mo113d2 = {"Lkotlin/time/AbstractLongTimeSource;", "Lkotlin/time/TimeSource;", "unit", "Lkotlin/time/DurationUnit;", "(Lkotlin/time/DurationUnit;)V", "getUnit", "()Lkotlin/time/DurationUnit;", "markNow", "Lkotlin/time/TimeMark;", "read", "", "LongTimeMark", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: TimeSources.kt */
public abstract class AbstractLongTimeSource implements TimeSource {
    private final DurationUnit unit;

    /* access modifiers changed from: protected */
    public abstract long read();

    public AbstractLongTimeSource(DurationUnit unit2) {
        Intrinsics.checkNotNullParameter(unit2, "unit");
        this.unit = unit2;
    }

    /* access modifiers changed from: protected */
    public final DurationUnit getUnit() {
        return this.unit;
    }

    @Metadata(mo112d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B \u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0015\u0010\n\u001a\u00020\u0007H\u0016ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u001b\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0007H\u0002ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0006\u001a\u00020\u0007X\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u0011"}, mo113d2 = {"Lkotlin/time/AbstractLongTimeSource$LongTimeMark;", "Lkotlin/time/TimeMark;", "startedAt", "", "timeSource", "Lkotlin/time/AbstractLongTimeSource;", "offset", "Lkotlin/time/Duration;", "(JLkotlin/time/AbstractLongTimeSource;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "J", "elapsedNow", "elapsedNow-UwyO8pc", "()J", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/TimeMark;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: TimeSources.kt */
    private static final class LongTimeMark implements TimeMark {
        private final long offset;
        private final long startedAt;
        private final AbstractLongTimeSource timeSource;

        public /* synthetic */ LongTimeMark(long j, AbstractLongTimeSource abstractLongTimeSource, long j2, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, abstractLongTimeSource, j2);
        }

        private LongTimeMark(long startedAt2, AbstractLongTimeSource timeSource2, long offset2) {
            this.startedAt = startedAt2;
            this.timeSource = timeSource2;
            this.offset = offset2;
        }

        public boolean hasNotPassedNow() {
            return TimeMark.DefaultImpls.hasNotPassedNow(this);
        }

        public boolean hasPassedNow() {
            return TimeMark.DefaultImpls.hasPassedNow(this);
        }

        /* renamed from: minus-LRDsOJo  reason: not valid java name */
        public TimeMark m1828minusLRDsOJo(long duration) {
            return TimeMark.DefaultImpls.m1845minusLRDsOJo(this, duration);
        }

        /* renamed from: elapsedNow-UwyO8pc  reason: not valid java name */
        public long m1827elapsedNowUwyO8pc() {
            return Duration.m1146minusLRDsOJo(DurationKt.toDuration(this.timeSource.read() - this.startedAt, this.timeSource.getUnit()), this.offset);
        }

        /* renamed from: plus-LRDsOJo  reason: not valid java name */
        public TimeMark m1829plusLRDsOJo(long duration) {
            return new LongTimeMark(this.startedAt, this.timeSource, Duration.m1147plusLRDsOJo(this.offset, duration), (DefaultConstructorMarker) null);
        }
    }

    public TimeMark markNow() {
        return new LongTimeMark(read(), this, Duration.Companion.m1213getZEROUwyO8pc(), (DefaultConstructorMarker) null);
    }
}
