package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.TimeMark;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u0003\u0018\u00002\u00020\u0001B\u0018\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u0015\u0010\u000b\u001a\u00020\u0004H\u0016ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\f\u0010\u0007J\u001b\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0004H\u0002ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u0011"}, mo113d2 = {"Lkotlin/time/AdjustedTimeMark;", "Lkotlin/time/TimeMark;", "mark", "adjustment", "Lkotlin/time/Duration;", "(Lkotlin/time/TimeMark;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAdjustment-UwyO8pc", "()J", "J", "getMark", "()Lkotlin/time/TimeMark;", "elapsedNow", "elapsedNow-UwyO8pc", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/TimeMark;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: TimeSource.kt */
final class AdjustedTimeMark implements TimeMark {
    private final long adjustment;
    private final TimeMark mark;

    public /* synthetic */ AdjustedTimeMark(TimeMark timeMark, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(timeMark, j);
    }

    private AdjustedTimeMark(TimeMark mark2, long adjustment2) {
        this.mark = mark2;
        this.adjustment = adjustment2;
    }

    public boolean hasNotPassedNow() {
        return TimeMark.DefaultImpls.hasNotPassedNow(this);
    }

    public boolean hasPassedNow() {
        return TimeMark.DefaultImpls.hasPassedNow(this);
    }

    /* renamed from: minus-LRDsOJo  reason: not valid java name */
    public TimeMark m1832minusLRDsOJo(long duration) {
        return TimeMark.DefaultImpls.m1845minusLRDsOJo(this, duration);
    }

    /* renamed from: getAdjustment-UwyO8pc  reason: not valid java name */
    public final long m1831getAdjustmentUwyO8pc() {
        return this.adjustment;
    }

    public final TimeMark getMark() {
        return this.mark;
    }

    /* renamed from: elapsedNow-UwyO8pc  reason: not valid java name */
    public long m1830elapsedNowUwyO8pc() {
        return Duration.m1146minusLRDsOJo(this.mark.m1842elapsedNowUwyO8pc(), this.adjustment);
    }

    /* renamed from: plus-LRDsOJo  reason: not valid java name */
    public TimeMark m1833plusLRDsOJo(long duration) {
        return new AdjustedTimeMark(this.mark, Duration.m1147plusLRDsOJo(this.adjustment, duration), (DefaultConstructorMarker) null);
    }
}
