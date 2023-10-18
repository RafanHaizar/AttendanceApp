package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(mo112d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000ø\u0001\u0001J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0005H\u0016J\u0014\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0003H\u0002ø\u0001\u0001J\u0014\u0010\t\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0003H\u0002ø\u0001\u0001\u0002\b\n\u0002\b!\n\u0002\b\u0019¨\u0006\n"}, mo113d2 = {"Lkotlin/time/TimeMark;", "", "elapsedNow", "Lkotlin/time/Duration;", "hasNotPassedNow", "", "hasPassedNow", "minus", "duration", "plus", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: TimeSource.kt */
public interface TimeMark {
    /* renamed from: elapsedNow-UwyO8pc  reason: not valid java name */
    long m1842elapsedNowUwyO8pc();

    boolean hasNotPassedNow();

    boolean hasPassedNow();

    /* renamed from: minus-LRDsOJo  reason: not valid java name */
    TimeMark m1843minusLRDsOJo(long j);

    /* renamed from: plus-LRDsOJo  reason: not valid java name */
    TimeMark m1844plusLRDsOJo(long j);

    @Metadata(mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: TimeSource.kt */
    public static final class DefaultImpls {
        /* renamed from: plus-LRDsOJo  reason: not valid java name */
        public static TimeMark m1846plusLRDsOJo(TimeMark $this, long duration) {
            return new AdjustedTimeMark($this, duration, (DefaultConstructorMarker) null);
        }

        /* renamed from: minus-LRDsOJo  reason: not valid java name */
        public static TimeMark m1845minusLRDsOJo(TimeMark $this, long duration) {
            return $this.m1844plusLRDsOJo(Duration.m1163unaryMinusUwyO8pc(duration));
        }

        public static boolean hasPassedNow(TimeMark $this) {
            return !Duration.m1144isNegativeimpl($this.m1842elapsedNowUwyO8pc());
        }

        public static boolean hasNotPassedNow(TimeMark $this) {
            return Duration.m1144isNegativeimpl($this.m1842elapsedNowUwyO8pc());
        }
    }
}
