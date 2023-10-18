package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@"}, mo113d2 = {"<anonymous>", "", "E", "R", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$flatMap$1", mo130f = "Deprecated.kt", mo131i = {0, 1, 2}, mo132l = {321, 322, 322}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "$this$produce", "$this$produce"}, mo135s = {"L$0", "L$0", "L$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$flatMap$1 extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel<E> $this_flatMap;
    final /* synthetic */ Function2<E, Continuation<? super ReceiveChannel<? extends R>>, Object> $transform;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$flatMap$1(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super ReceiveChannel<? extends R>>, ? extends Object> function2, Continuation<? super ChannelsKt__DeprecatedKt$flatMap$1> continuation) {
        super(2, continuation);
        this.$this_flatMap = receiveChannel;
        this.$transform = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$flatMap$1 channelsKt__DeprecatedKt$flatMap$1 = new ChannelsKt__DeprecatedKt$flatMap$1(this.$this_flatMap, this.$transform, continuation);
        channelsKt__DeprecatedKt$flatMap$1.L$0 = obj;
        return channelsKt__DeprecatedKt$flatMap$1;
    }

    public final Object invoke(ProducerScope<? super R> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$flatMap$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x009f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            switch(r1) {
                case 0: goto L_0x0044;
                case 1: goto L_0x0032;
                case 2: goto L_0x001f;
                case 3: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0011:
            r1 = r9
            java.lang.Object r2 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x00a4
        L_0x001f:
            r1 = r9
            java.lang.Object r2 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r10)
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r10
            goto L_0x0089
        L_0x0032:
            r1 = r9
            java.lang.Object r2 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r10)
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r10
            goto L_0x006d
        L_0x0044:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlinx.coroutines.channels.ReceiveChannel<E> r3 = r1.$this_flatMap
            kotlinx.coroutines.channels.ChannelIterator r3 = r3.iterator()
            r8 = r3
            r3 = r2
            r2 = r8
        L_0x0055:
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r1.L$0 = r3
            r1.L$1 = r2
            r5 = 1
            r1.label = r5
            java.lang.Object r4 = r2.hasNext(r4)
            if (r4 != r0) goto L_0x0066
            return r0
        L_0x0066:
            r8 = r0
            r0 = r10
            r10 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r8
        L_0x006d:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L_0x00a5
            java.lang.Object r10 = r3.next()
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ReceiveChannel<? extends R>>, java.lang.Object> r5 = r2.$transform
            r2.L$0 = r4
            r2.L$1 = r3
            r6 = 2
            r2.label = r6
            java.lang.Object r10 = r5.invoke(r10, r2)
            if (r10 != r1) goto L_0x0089
            return r1
        L_0x0089:
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            r5 = r4
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            r6 = r2
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r2.L$0 = r4
            r2.L$1 = r3
            r7 = 3
            r2.label = r7
            java.lang.Object r10 = kotlinx.coroutines.channels.ChannelsKt.toChannel(r10, r5, r6)
            if (r10 != r1) goto L_0x009f
            return r1
        L_0x009f:
            r10 = r0
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r4
        L_0x00a4:
            goto L_0x0055
        L_0x00a5:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$flatMap$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
