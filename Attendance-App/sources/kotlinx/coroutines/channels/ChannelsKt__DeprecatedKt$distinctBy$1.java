package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0004H@"}, mo113d2 = {"<anonymous>", "", "E", "K", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$distinctBy$1", mo130f = "Deprecated.kt", mo131i = {0, 0, 1, 1, 1, 2, 2, 2}, mo132l = {387, 388, 390}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "keys", "$this$produce", "keys", "e", "$this$produce", "keys", "k"}, mo135s = {"L$0", "L$1", "L$0", "L$1", "L$3", "L$0", "L$1", "L$3"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$distinctBy$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2<E, Continuation<? super K>, Object> $selector;
    final /* synthetic */ ReceiveChannel<E> $this_distinctBy;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$distinctBy$1(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super K>, ? extends Object> function2, Continuation<? super ChannelsKt__DeprecatedKt$distinctBy$1> continuation) {
        super(2, continuation);
        this.$this_distinctBy = receiveChannel;
        this.$selector = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$distinctBy$1 channelsKt__DeprecatedKt$distinctBy$1 = new ChannelsKt__DeprecatedKt$distinctBy$1(this.$this_distinctBy, this.$selector, continuation);
        channelsKt__DeprecatedKt$distinctBy$1.L$0 = obj;
        return channelsKt__DeprecatedKt$distinctBy$1;
    }

    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$distinctBy$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00cd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            switch(r1) {
                case 0: goto L_0x0057;
                case 1: goto L_0x0040;
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
            java.lang.Object r2 = r1.L$3
            java.lang.Object r3 = r1.L$2
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$1
            java.util.HashSet r4 = (java.util.HashSet) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x00d5
        L_0x0025:
            r1 = r10
            java.lang.Object r2 = r1.L$3
            java.lang.Object r3 = r1.L$2
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$1
            java.util.HashSet r4 = (java.util.HashSet) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r11
            goto L_0x00b1
        L_0x0040:
            r1 = r10
            java.lang.Object r2 = r1.L$2
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$1
            java.util.HashSet r3 = (java.util.HashSet) r3
            java.lang.Object r4 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r11)
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r11
            goto L_0x008b
        L_0x0057:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            kotlinx.coroutines.channels.ReceiveChannel<E> r4 = r1.$this_distinctBy
            kotlinx.coroutines.channels.ChannelIterator r4 = r4.iterator()
            r9 = r4
            r4 = r2
            r2 = r9
        L_0x006d:
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r4
            r1.L$1 = r3
            r1.L$2 = r2
            r6 = 0
            r1.L$3 = r6
            r6 = 1
            r1.label = r6
            java.lang.Object r5 = r2.hasNext(r5)
            if (r5 != r0) goto L_0x0083
            return r0
        L_0x0083:
            r9 = r0
            r0 = r11
            r11 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r9
        L_0x008b:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x00e7
            java.lang.Object r11 = r3.next()
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super K>, java.lang.Object> r6 = r2.$selector
            r2.L$0 = r5
            r2.L$1 = r4
            r2.L$2 = r3
            r2.L$3 = r11
            r7 = 2
            r2.label = r7
            java.lang.Object r6 = r6.invoke(r11, r2)
            if (r6 != r1) goto L_0x00ab
            return r1
        L_0x00ab:
            r9 = r3
            r3 = r11
            r11 = r6
            r6 = r5
            r5 = r4
            r4 = r9
        L_0x00b1:
            boolean r7 = r5.contains(r11)
            if (r7 != 0) goto L_0x00e0
            r7 = r2
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r2.L$0 = r6
            r2.L$1 = r5
            r2.L$2 = r4
            r2.L$3 = r11
            r8 = 3
            r2.label = r8
            java.lang.Object r3 = r6.send(r3, r7)
            if (r3 != r1) goto L_0x00cd
            return r1
        L_0x00cd:
            r3 = r4
            r4 = r5
            r5 = r6
            r9 = r2
            r2 = r11
            r11 = r0
            r0 = r1
            r1 = r9
        L_0x00d5:
            r6 = r4
            java.util.Collection r6 = (java.util.Collection) r6
            r6.add(r2)
            r2 = r3
            r3 = r4
            r4 = r5
            goto L_0x006d
        L_0x00e0:
            r11 = r0
            r0 = r1
            r1 = r2
            r2 = r4
            r3 = r5
            r4 = r6
            goto L_0x006d
        L_0x00e7:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$distinctBy$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
