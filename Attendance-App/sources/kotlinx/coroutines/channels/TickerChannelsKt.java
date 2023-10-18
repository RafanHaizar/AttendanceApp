package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

@Metadata(mo112d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a/\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a/\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a4\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, mo113d2 = {"fixedDelayTicker", "", "delayMillis", "", "initialDelayMillis", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "(JJLkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fixedPeriodTicker", "ticker", "Lkotlinx/coroutines/channels/ReceiveChannel;", "context", "Lkotlin/coroutines/CoroutineContext;", "mode", "Lkotlinx/coroutines/channels/TickerMode;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: TickerChannels.kt */
public final class TickerChannelsKt {
    public static /* synthetic */ ReceiveChannel ticker$default(long j, long j2, CoroutineContext coroutineContext, TickerMode tickerMode, int i, Object obj) {
        if ((i & 2) != 0) {
            j2 = j;
        }
        if ((i & 4) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 8) != 0) {
            tickerMode = TickerMode.FIXED_PERIOD;
        }
        return ticker(j, j2, coroutineContext, tickerMode);
    }

    public static final ReceiveChannel<Unit> ticker(long delayMillis, long initialDelayMillis, CoroutineContext context, TickerMode mode) {
        long j = delayMillis;
        long j2 = initialDelayMillis;
        boolean z = true;
        if (j >= 0) {
            if (j2 < 0) {
                z = false;
            }
            if (z) {
                return ProduceKt.produce(GlobalScope.INSTANCE, Dispatchers.getUnconfined().plus(context), 0, new TickerChannelsKt$ticker$3(mode, delayMillis, initialDelayMillis, (Continuation<? super TickerChannelsKt$ticker$3>) null));
            }
            CoroutineContext coroutineContext = context;
            throw new IllegalArgumentException(("Expected non-negative initial delay, but has " + j2 + " ms").toString());
        }
        CoroutineContext coroutineContext2 = context;
        throw new IllegalArgumentException(("Expected non-negative delay, but has " + j + " ms").toString());
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a0, code lost:
        r9 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a4, code lost:
        r5 = r5 + r9;
        r7 = kotlin.Unit.INSTANCE;
        r0.L$0 = r3;
        r0.J$0 = r5;
        r0.J$1 = r9;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b5, code lost:
        if (r3.send(r7, r0) != r2) goto L_0x00b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b7, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b8, code lost:
        r7 = r5;
        r5 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ba, code lost:
        r9 = kotlinx.coroutines.AbstractTimeSourceKt.getTimeSource();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00be, code lost:
        if (r9 != null) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c0, code lost:
        r9 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c2, code lost:
        r9 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r9.nanoTime());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ca, code lost:
        if (r9 != null) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00cc, code lost:
        r9 = java.lang.System.nanoTime();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00d1, code lost:
        r9 = r9.longValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00d5, code lost:
        r11 = kotlin.ranges.RangesKt.coerceAtLeast(r7 - r9, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00df, code lost:
        if (r11 != 0) goto L_0x0105;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e3, code lost:
        if (r5 == 0) goto L_0x0105;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00e5, code lost:
        r7 = r5 - ((r9 - r7) % r5);
        r9 = r9 + r7;
        r11 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r7);
        r0.L$0 = r3;
        r0.J$0 = r9;
        r0.J$1 = r5;
        r0.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00fc, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r11, r0) != r2) goto L_0x00ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00fe, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ff, code lost:
        r16 = r5;
        r5 = r9;
        r9 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0105, code lost:
        r9 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r11);
        r0.L$0 = r3;
        r0.J$0 = r7;
        r0.J$1 = r5;
        r0.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0116, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r9, r0) != r2) goto L_0x0119;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0118, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0119, code lost:
        r9 = r5;
        r5 = r7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object fixedPeriodTicker(long r18, long r20, kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit> r22, kotlin.coroutines.Continuation<? super kotlin.Unit> r23) {
        /*
            r0 = r23
            boolean r1 = r0 instanceof kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1
            if (r1 == 0) goto L_0x0016
            r1 = r0
            kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1 r1 = (kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1 r1 = new kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1
            r1.<init>(r0)
        L_0x001b:
            r0 = r1
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0066;
                case 1: goto L_0x005a;
                case 2: goto L_0x004e;
                case 3: goto L_0x003f;
                case 4: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0030:
            long r5 = r0.J$1
            long r7 = r0.J$0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r3 = (kotlinx.coroutines.channels.SendChannel) r3
            kotlin.ResultKt.throwOnFailure(r1)
            r9 = r5
            r5 = r7
            goto L_0x011b
        L_0x003f:
            long r5 = r0.J$1
            long r7 = r0.J$0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r3 = (kotlinx.coroutines.channels.SendChannel) r3
            kotlin.ResultKt.throwOnFailure(r1)
            r9 = r5
            r5 = r7
            goto L_0x0104
        L_0x004e:
            long r5 = r0.J$1
            long r7 = r0.J$0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r3 = (kotlinx.coroutines.channels.SendChannel) r3
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x00ba
        L_0x005a:
            long r5 = r0.J$1
            long r7 = r0.J$0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r3 = (kotlinx.coroutines.channels.SendChannel) r3
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x00a0
        L_0x0066:
            kotlin.ResultKt.throwOnFailure(r1)
            r7 = r18
            r5 = r20
            r3 = r22
            kotlinx.coroutines.AbstractTimeSource r9 = kotlinx.coroutines.AbstractTimeSourceKt.getTimeSource()
            if (r9 != 0) goto L_0x0077
            r9 = r4
            goto L_0x007f
        L_0x0077:
            long r9 = r9.nanoTime()
            java.lang.Long r9 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r9)
        L_0x007f:
            if (r9 != 0) goto L_0x0086
            long r9 = java.lang.System.nanoTime()
            goto L_0x008a
        L_0x0086:
            long r9 = r9.longValue()
        L_0x008a:
            long r11 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r5)
            long r9 = r9 + r11
            r0.L$0 = r3
            r0.J$0 = r7
            r0.J$1 = r9
            r11 = 1
            r0.label = r11
            java.lang.Object r5 = kotlinx.coroutines.DelayKt.delay(r5, r0)
            if (r5 != r2) goto L_0x009f
            return r2
        L_0x009f:
            r5 = r9
        L_0x00a0:
            long r9 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r7)
        L_0x00a4:
            long r5 = r5 + r9
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            r0.L$0 = r3
            r0.J$0 = r5
            r0.J$1 = r9
            r8 = 2
            r0.label = r8
            java.lang.Object r7 = r3.send(r7, r0)
            if (r7 != r2) goto L_0x00b8
            return r2
        L_0x00b8:
            r7 = r5
            r5 = r9
        L_0x00ba:
            kotlinx.coroutines.AbstractTimeSource r9 = kotlinx.coroutines.AbstractTimeSourceKt.getTimeSource()
            if (r9 != 0) goto L_0x00c2
            r9 = r4
            goto L_0x00ca
        L_0x00c2:
            long r9 = r9.nanoTime()
            java.lang.Long r9 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r9)
        L_0x00ca:
            if (r9 != 0) goto L_0x00d1
            long r9 = java.lang.System.nanoTime()
            goto L_0x00d5
        L_0x00d1:
            long r9 = r9.longValue()
        L_0x00d5:
            long r11 = r7 - r9
            r13 = 0
            long r11 = kotlin.ranges.RangesKt.coerceAtLeast((long) r11, (long) r13)
            int r15 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r15 != 0) goto L_0x0105
            int r15 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
            if (r15 == 0) goto L_0x0105
            long r11 = r9 - r7
            long r11 = r11 % r5
            long r7 = r5 - r11
            long r9 = r9 + r7
            long r11 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r7)
            r0.L$0 = r3
            r0.J$0 = r9
            r0.J$1 = r5
            r13 = 3
            r0.label = r13
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r11, r0)
            if (r7 != r2) goto L_0x00ff
            return r2
        L_0x00ff:
            r16 = r5
            r5 = r9
            r9 = r16
        L_0x0104:
            goto L_0x00a4
        L_0x0105:
            long r9 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r11)
            r0.L$0 = r3
            r0.J$0 = r7
            r0.J$1 = r5
            r13 = 4
            r0.label = r13
            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r9, r0)
            if (r9 != r2) goto L_0x0119
            return r2
        L_0x0119:
            r9 = r5
            r5 = r7
        L_0x011b:
            goto L_0x00a4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.fixedPeriodTicker(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x007b A[RETURN] */
    public static final java.lang.Object fixedDelayTicker(long r3, long r5, kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit> r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1 r0 = (kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1 r0 = new kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1
            r0.<init>(r8)
        L_0x0019:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x004b;
                case 1: goto L_0x0041;
                case 2: goto L_0x0037;
                case 3: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            r3.<init>(r4)
            throw r3
        L_0x002d:
            long r3 = r8.J$0
            java.lang.Object r5 = r8.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x007c
        L_0x0037:
            long r3 = r8.J$0
            java.lang.Object r5 = r8.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x006e
        L_0x0041:
            long r3 = r8.J$0
            java.lang.Object r5 = r8.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x005d
        L_0x004b:
            kotlin.ResultKt.throwOnFailure(r0)
            r8.L$0 = r7
            r8.J$0 = r3
            r2 = 1
            r8.label = r2
            java.lang.Object r5 = kotlinx.coroutines.DelayKt.delay(r5, r8)
            if (r5 != r1) goto L_0x005c
            return r1
        L_0x005c:
            r5 = r7
        L_0x005d:
        L_0x005e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            r8.L$0 = r5
            r8.J$0 = r3
            r7 = 2
            r8.label = r7
            java.lang.Object r6 = r5.send(r6, r8)
            if (r6 != r1) goto L_0x006e
            return r1
        L_0x006e:
            r8.L$0 = r5
            r8.J$0 = r3
            r6 = 3
            r8.label = r6
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r8)
            if (r6 != r1) goto L_0x007c
            return r1
        L_0x007c:
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.fixedDelayTicker(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
