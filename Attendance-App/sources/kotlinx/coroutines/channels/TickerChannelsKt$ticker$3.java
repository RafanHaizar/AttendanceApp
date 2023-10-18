package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H@"}, mo113d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.TickerChannelsKt$ticker$3", mo130f = "TickerChannels.kt", mo131i = {}, mo132l = {72, 73}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: TickerChannels.kt */
final class TickerChannelsKt$ticker$3 extends SuspendLambda implements Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $delayMillis;
    final /* synthetic */ long $initialDelayMillis;
    final /* synthetic */ TickerMode $mode;
    private /* synthetic */ Object L$0;
    int label;

    @Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: TickerChannels.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TickerMode.values().length];
            iArr[TickerMode.FIXED_PERIOD.ordinal()] = 1;
            iArr[TickerMode.FIXED_DELAY.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    TickerChannelsKt$ticker$3(TickerMode tickerMode, long j, long j2, Continuation<? super TickerChannelsKt$ticker$3> continuation) {
        super(2, continuation);
        this.$mode = tickerMode;
        this.$delayMillis = j;
        this.$initialDelayMillis = j2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        TickerChannelsKt$ticker$3 tickerChannelsKt$ticker$3 = new TickerChannelsKt$ticker$3(this.$mode, this.$delayMillis, this.$initialDelayMillis, continuation);
        tickerChannelsKt$ticker$3.L$0 = obj;
        return tickerChannelsKt$ticker$3;
    }

    public final Object invoke(ProducerScope<? super Unit> producerScope, Continuation<? super Unit> continuation) {
        return ((TickerChannelsKt$ticker$3) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0047, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005f, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0062, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            switch(r1) {
                case 0: goto L_0x001b;
                case 1: goto L_0x0016;
                case 2: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x0011:
            r0 = r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x0047
        L_0x0016:
            r0 = r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x005f
        L_0x001b:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlinx.coroutines.channels.TickerMode r3 = r1.$mode
            int[] r4 = kotlinx.coroutines.channels.TickerChannelsKt$ticker$3.WhenMappings.$EnumSwitchMapping$0
            int r3 = r3.ordinal()
            r3 = r4[r3]
            switch(r3) {
                case 1: goto L_0x0049;
                case 2: goto L_0x0031;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x0060
        L_0x0031:
            long r4 = r1.$delayMillis
            long r6 = r1.$initialDelayMillis
            kotlinx.coroutines.channels.SendChannel r8 = r2.getChannel()
            r9 = r1
            kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9
            r3 = 2
            r1.label = r3
            java.lang.Object r2 = kotlinx.coroutines.channels.TickerChannelsKt.fixedDelayTicker(r4, r6, r8, r9)
            if (r2 != r0) goto L_0x0046
            return r0
        L_0x0046:
            r0 = r1
        L_0x0047:
            r1 = r0
            goto L_0x0060
        L_0x0049:
            long r3 = r1.$delayMillis
            long r5 = r1.$initialDelayMillis
            kotlinx.coroutines.channels.SendChannel r7 = r2.getChannel()
            r8 = r1
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r9 = 1
            r1.label = r9
            java.lang.Object r2 = kotlinx.coroutines.channels.TickerChannelsKt.fixedPeriodTicker(r3, r5, r7, r8)
            if (r2 != r0) goto L_0x005e
            return r0
        L_0x005e:
            r0 = r1
        L_0x005f:
            r1 = r0
        L_0x0060:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt$ticker$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
