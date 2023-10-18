package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@"}, mo113d2 = {"<anonymous>", "", "E", "R", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$mapIndexed$1", mo130f = "Deprecated.kt", mo131i = {0, 0, 1, 1, 2, 2}, mo132l = {344, 345, 345}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "index", "$this$produce", "index", "$this$produce", "index"}, mo135s = {"L$0", "I$0", "L$0", "I$0", "L$0", "I$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$mapIndexed$1 extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel<E> $this_mapIndexed;
    final /* synthetic */ Function3<Integer, E, Continuation<? super R>, Object> $transform;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$mapIndexed$1(ReceiveChannel<? extends E> receiveChannel, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super ChannelsKt__DeprecatedKt$mapIndexed$1> continuation) {
        super(2, continuation);
        this.$this_mapIndexed = receiveChannel;
        this.$transform = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$mapIndexed$1 channelsKt__DeprecatedKt$mapIndexed$1 = new ChannelsKt__DeprecatedKt$mapIndexed$1(this.$this_mapIndexed, this.$transform, continuation);
        channelsKt__DeprecatedKt$mapIndexed$1.L$0 = obj;
        return channelsKt__DeprecatedKt$mapIndexed$1;
    }

    public final Object invoke(ProducerScope<? super R> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$mapIndexed$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00b9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            switch(r1) {
                case 0: goto L_0x0051;
                case 1: goto L_0x003d;
                case 2: goto L_0x0025;
                case 3: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x0011:
            r1 = r10
            int r2 = r1.I$0
            java.lang.Object r3 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r11)
            r9 = r3
            r3 = r2
            r2 = r4
            r4 = r9
            goto L_0x00be
        L_0x0025:
            r1 = r10
            int r2 = r1.I$0
            java.lang.Object r3 = r1.L$2
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            java.lang.Object r4 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            r7 = r2
            r2 = r1
            r1 = r0
            r0 = r11
            goto L_0x00a3
        L_0x003d:
            r1 = r10
            int r2 = r1.I$0
            java.lang.Object r3 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r11)
            r5 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r11
            goto L_0x007a
        L_0x0051:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            r3 = 0
            kotlinx.coroutines.channels.ReceiveChannel<E> r4 = r1.$this_mapIndexed
            kotlinx.coroutines.channels.ChannelIterator r4 = r4.iterator()
        L_0x0060:
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r2
            r1.L$1 = r4
            r1.I$0 = r3
            r6 = 1
            r1.label = r6
            java.lang.Object r5 = r4.hasNext(r5)
            if (r5 != r0) goto L_0x0073
            return r0
        L_0x0073:
            r9 = r0
            r0 = r11
            r11 = r5
            r5 = r4
            r4 = r2
            r2 = r1
            r1 = r9
        L_0x007a:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x00bf
            java.lang.Object r11 = r5.next()
            kotlin.jvm.functions.Function3<java.lang.Integer, E, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r6 = r2.$transform
            int r7 = r3 + 1
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r3)
            r2.L$0 = r4
            r2.L$1 = r5
            r2.L$2 = r4
            r2.I$0 = r7
            r8 = 2
            r2.label = r8
            java.lang.Object r11 = r6.invoke(r3, r11, r2)
            if (r11 != r1) goto L_0x00a0
            return r1
        L_0x00a0:
            r3 = r4
            r4 = r5
            r5 = r3
        L_0x00a3:
            r6 = r2
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r2.L$0 = r5
            r2.L$1 = r4
            r8 = 0
            r2.L$2 = r8
            r2.I$0 = r7
            r8 = 3
            r2.label = r8
            java.lang.Object r11 = r3.send(r11, r6)
            if (r11 != r1) goto L_0x00b9
            return r1
        L_0x00b9:
            r11 = r0
            r0 = r1
            r1 = r2
            r2 = r5
            r3 = r7
        L_0x00be:
            goto L_0x0060
        L_0x00bf:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$mapIndexed$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
