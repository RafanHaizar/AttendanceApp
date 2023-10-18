package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.BroadcastKt$broadcast$2", mo130f = "Broadcast.kt", mo131i = {0, 1}, mo132l = {53, 54}, mo133m = "invokeSuspend", mo134n = {"$this$broadcast", "$this$broadcast"}, mo135s = {"L$0", "L$0"})
/* compiled from: Broadcast.kt */
final class BroadcastKt$broadcast$2 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel<E> $this_broadcast;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    BroadcastKt$broadcast$2(ReceiveChannel<? extends E> receiveChannel, Continuation<? super BroadcastKt$broadcast$2> continuation) {
        super(2, continuation);
        this.$this_broadcast = receiveChannel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        BroadcastKt$broadcast$2 broadcastKt$broadcast$2 = new BroadcastKt$broadcast$2(this.$this_broadcast, continuation);
        broadcastKt$broadcast$2.L$0 = obj;
        return broadcastKt$broadcast$2;
    }

    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((BroadcastKt$broadcast$2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0053  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            switch(r1) {
                case 0: goto L_0x0034;
                case 1: goto L_0x0022;
                case 2: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x0011:
            r1 = r8
            java.lang.Object r2 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r9)
            r7 = r3
            r3 = r2
            r2 = r7
            goto L_0x007a
        L_0x0022:
            r1 = r8
            java.lang.Object r2 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r9)
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r9
            goto L_0x0059
        L_0x0034:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlinx.coroutines.channels.ReceiveChannel<E> r3 = r1.$this_broadcast
            kotlinx.coroutines.channels.ChannelIterator r3 = r3.iterator()
        L_0x0042:
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r1.L$0 = r2
            r1.L$1 = r3
            r5 = 1
            r1.label = r5
            java.lang.Object r4 = r3.hasNext(r4)
            if (r4 != r0) goto L_0x0053
            return r0
        L_0x0053:
            r7 = r0
            r0 = r9
            r9 = r4
            r4 = r2
            r2 = r1
            r1 = r7
        L_0x0059:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x007b
            java.lang.Object r9 = r3.next()
            r5 = r2
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r2.L$0 = r4
            r2.L$1 = r3
            r6 = 2
            r2.label = r6
            java.lang.Object r9 = r4.send(r9, r5)
            if (r9 != r1) goto L_0x0076
            return r1
        L_0x0076:
            r9 = r0
            r0 = r1
            r1 = r2
            r2 = r4
        L_0x007a:
            goto L_0x0042
        L_0x007b:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.BroadcastKt$broadcast$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
