package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filter$1", mo130f = "Deprecated.kt", mo131i = {0, 1, 1, 2}, mo132l = {198, 199, 199}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "$this$produce", "e", "$this$produce"}, mo135s = {"L$0", "L$0", "L$2", "L$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$filter$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2<E, Continuation<? super Boolean>, Object> $predicate;
    final /* synthetic */ ReceiveChannel<E> $this_filter;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$filter$1(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super ChannelsKt__DeprecatedKt$filter$1> continuation) {
        super(2, continuation);
        this.$this_filter = receiveChannel;
        this.$predicate = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$filter$1 channelsKt__DeprecatedKt$filter$1 = new ChannelsKt__DeprecatedKt$filter$1(this.$this_filter, this.$predicate, continuation);
        channelsKt__DeprecatedKt$filter$1.L$0 = obj;
        return channelsKt__DeprecatedKt$filter$1;
    }

    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$filter$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00b7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 0
            switch(r1) {
                case 0: goto L_0x0048;
                case 1: goto L_0x0036;
                case 2: goto L_0x0020;
                case 3: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0012:
            r1 = r9
            java.lang.Object r3 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x00b6
        L_0x0020:
            r1 = r9
            java.lang.Object r3 = r1.L$2
            java.lang.Object r4 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r10)
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r10
            goto L_0x0096
        L_0x0036:
            r1 = r9
            java.lang.Object r3 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r10)
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r10
            goto L_0x0073
        L_0x0048:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlinx.coroutines.channels.ReceiveChannel<E> r4 = r1.$this_filter
            kotlinx.coroutines.channels.ChannelIterator r4 = r4.iterator()
            r8 = r4
            r4 = r3
            r3 = r8
        L_0x0059:
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r4
            r1.L$1 = r3
            r1.L$2 = r2
            r6 = 1
            r1.label = r6
            java.lang.Object r5 = r3.hasNext(r5)
            if (r5 != r0) goto L_0x006c
            return r0
        L_0x006c:
            r8 = r0
            r0 = r10
            r10 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r8
        L_0x0073:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L_0x00bd
            java.lang.Object r10 = r4.next()
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r6 = r3.$predicate
            r3.L$0 = r5
            r3.L$1 = r4
            r3.L$2 = r10
            r7 = 2
            r3.label = r7
            java.lang.Object r6 = r6.invoke(r10, r3)
            if (r6 != r1) goto L_0x0091
            return r1
        L_0x0091:
            r8 = r4
            r4 = r10
            r10 = r6
            r6 = r5
            r5 = r8
        L_0x0096:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L_0x00b7
            r10 = r3
            kotlin.coroutines.Continuation r10 = (kotlin.coroutines.Continuation) r10
            r3.L$0 = r6
            r3.L$1 = r5
            r3.L$2 = r2
            r7 = 3
            r3.label = r7
            java.lang.Object r10 = r6.send(r4, r10)
            if (r10 != r1) goto L_0x00b1
            return r1
        L_0x00b1:
            r10 = r0
            r0 = r1
            r1 = r3
            r3 = r5
            r4 = r6
        L_0x00b6:
            goto L_0x0059
        L_0x00b7:
            r10 = r0
            r0 = r1
            r1 = r3
            r3 = r5
            r4 = r6
            goto L_0x0059
        L_0x00bd:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filter$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
