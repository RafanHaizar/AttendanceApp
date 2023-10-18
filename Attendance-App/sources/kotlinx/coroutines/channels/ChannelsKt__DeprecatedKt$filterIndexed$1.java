package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterIndexed$1", mo130f = "Deprecated.kt", mo131i = {0, 0, 1, 1, 1, 2, 2}, mo132l = {211, 212, 212}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "index", "$this$produce", "e", "index", "$this$produce", "index"}, mo135s = {"L$0", "I$0", "L$0", "L$2", "I$0", "L$0", "I$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$filterIndexed$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function3<Integer, E, Continuation<? super Boolean>, Object> $predicate;
    final /* synthetic */ ReceiveChannel<E> $this_filterIndexed;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$filterIndexed$1(ReceiveChannel<? extends E> receiveChannel, Function3<? super Integer, ? super E, ? super Continuation<? super Boolean>, ? extends Object> function3, Continuation<? super ChannelsKt__DeprecatedKt$filterIndexed$1> continuation) {
        super(2, continuation);
        this.$this_filterIndexed = receiveChannel;
        this.$predicate = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$filterIndexed$1 channelsKt__DeprecatedKt$filterIndexed$1 = new ChannelsKt__DeprecatedKt$filterIndexed$1(this.$this_filterIndexed, this.$predicate, continuation);
        channelsKt__DeprecatedKt$filterIndexed$1.L$0 = obj;
        return channelsKt__DeprecatedKt$filterIndexed$1;
    }

    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$filterIndexed$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00ca  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            r2 = 0
            switch(r1) {
                case 0: goto L_0x004d;
                case 1: goto L_0x0038;
                case 2: goto L_0x0022;
                case 3: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L_0x0012:
            r1 = r11
            int r3 = r1.I$0
            java.lang.Object r4 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r12)
            goto L_0x00c9
        L_0x0022:
            r1 = r11
            int r3 = r1.I$0
            java.lang.Object r4 = r1.L$2
            java.lang.Object r5 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
            kotlin.ResultKt.throwOnFailure(r12)
            r8 = r3
            r3 = r1
            r1 = r0
            r0 = r12
            goto L_0x00a6
        L_0x0038:
            r1 = r11
            int r3 = r1.I$0
            java.lang.Object r4 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r12)
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r12
            goto L_0x007d
        L_0x004d:
            kotlin.ResultKt.throwOnFailure(r12)
            r1 = r11
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            r4 = 0
            kotlinx.coroutines.channels.ReceiveChannel<E> r5 = r1.$this_filterIndexed
            kotlinx.coroutines.channels.ChannelIterator r5 = r5.iterator()
            r10 = r5
            r5 = r3
            r3 = r4
            r4 = r10
        L_0x0060:
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r1.L$0 = r5
            r1.L$1 = r4
            r1.L$2 = r2
            r1.I$0 = r3
            r7 = 1
            r1.label = r7
            java.lang.Object r6 = r4.hasNext(r6)
            if (r6 != r0) goto L_0x0075
            return r0
        L_0x0075:
            r10 = r0
            r0 = r12
            r12 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r10
        L_0x007d:
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x00d1
            java.lang.Object r12 = r5.next()
            kotlin.jvm.functions.Function3<java.lang.Integer, E, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r7 = r3.$predicate
            int r8 = r4 + 1
            java.lang.Integer r4 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r4)
            r3.L$0 = r6
            r3.L$1 = r5
            r3.L$2 = r12
            r3.I$0 = r8
            r9 = 2
            r3.label = r9
            java.lang.Object r4 = r7.invoke(r4, r12, r3)
            if (r4 != r1) goto L_0x00a3
            return r1
        L_0x00a3:
            r10 = r4
            r4 = r12
            r12 = r10
        L_0x00a6:
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L_0x00ca
            r12 = r3
            kotlin.coroutines.Continuation r12 = (kotlin.coroutines.Continuation) r12
            r3.L$0 = r6
            r3.L$1 = r5
            r3.L$2 = r2
            r3.I$0 = r8
            r7 = 3
            r3.label = r7
            java.lang.Object r12 = r6.send(r4, r12)
            if (r12 != r1) goto L_0x00c3
            return r1
        L_0x00c3:
            r12 = r0
            r0 = r1
            r1 = r3
            r4 = r5
            r5 = r6
            r3 = r8
        L_0x00c9:
            goto L_0x0060
        L_0x00ca:
            r12 = r0
            r0 = r1
            r1 = r3
            r4 = r5
            r5 = r6
            r3 = r8
            goto L_0x0060
        L_0x00d1:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterIndexed$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
