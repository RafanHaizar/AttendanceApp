package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$takeWhile$1", mo130f = "Deprecated.kt", mo131i = {0, 1, 1, 2}, mo132l = {269, 270, 271}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "$this$produce", "e", "$this$produce"}, mo135s = {"L$0", "L$0", "L$2", "L$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$takeWhile$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2<E, Continuation<? super Boolean>, Object> $predicate;
    final /* synthetic */ ReceiveChannel<E> $this_takeWhile;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$takeWhile$1(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super ChannelsKt__DeprecatedKt$takeWhile$1> continuation) {
        super(2, continuation);
        this.$this_takeWhile = receiveChannel;
        this.$predicate = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$takeWhile$1 channelsKt__DeprecatedKt$takeWhile$1 = new ChannelsKt__DeprecatedKt$takeWhile$1(this.$this_takeWhile, this.$predicate, continuation);
        channelsKt__DeprecatedKt$takeWhile$1.L$0 = obj;
        return channelsKt__DeprecatedKt$takeWhile$1;
    }

    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$takeWhile$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x009e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            switch(r1) {
                case 0: goto L_0x0047;
                case 1: goto L_0x0035;
                case 2: goto L_0x001f;
                case 3: goto L_0x0011;
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
            goto L_0x00b7
        L_0x001f:
            r1 = r8
            java.lang.Object r2 = r1.L$2
            java.lang.Object r3 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r9)
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r9
            goto L_0x0093
        L_0x0035:
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
            goto L_0x0070
        L_0x0047:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlinx.coroutines.channels.ReceiveChannel<E> r3 = r1.$this_takeWhile
            kotlinx.coroutines.channels.ChannelIterator r3 = r3.iterator()
            r7 = r3
            r3 = r2
            r2 = r7
        L_0x0058:
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r1.L$0 = r3
            r1.L$1 = r2
            r5 = 1
            r1.label = r5
            java.lang.Object r4 = r2.hasNext(r4)
            if (r4 != r0) goto L_0x0069
            return r0
        L_0x0069:
            r7 = r0
            r0 = r9
            r9 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r7
        L_0x0070:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x00b8
            java.lang.Object r9 = r3.next()
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r5 = r2.$predicate
            r2.L$0 = r4
            r2.L$1 = r3
            r2.L$2 = r9
            r6 = 2
            r2.label = r6
            java.lang.Object r5 = r5.invoke(r9, r2)
            if (r5 != r1) goto L_0x008e
            return r1
        L_0x008e:
            r7 = r3
            r3 = r9
            r9 = r5
            r5 = r4
            r4 = r7
        L_0x0093:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L_0x009e
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L_0x009e:
            r9 = r2
            kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9
            r2.L$0 = r5
            r2.L$1 = r4
            r6 = 0
            r2.L$2 = r6
            r6 = 3
            r2.label = r6
            java.lang.Object r9 = r5.send(r3, r9)
            if (r9 != r1) goto L_0x00b2
            return r1
        L_0x00b2:
            r9 = r0
            r0 = r1
            r1 = r2
            r2 = r4
            r3 = r5
        L_0x00b7:
            goto L_0x0058
        L_0x00b8:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$takeWhile$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
