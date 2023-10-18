package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H@"}, mo113d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/collections/IndexedValue;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$withIndex$1", mo130f = "Deprecated.kt", mo131i = {0, 0, 1, 1}, mo132l = {370, 371}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "index", "$this$produce", "index"}, mo135s = {"L$0", "I$0", "L$0", "I$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$withIndex$1 extends SuspendLambda implements Function2<ProducerScope<? super IndexedValue<? extends E>>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel<E> $this_withIndex;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$withIndex$1(ReceiveChannel<? extends E> receiveChannel, Continuation<? super ChannelsKt__DeprecatedKt$withIndex$1> continuation) {
        super(2, continuation);
        this.$this_withIndex = receiveChannel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$withIndex$1 channelsKt__DeprecatedKt$withIndex$1 = new ChannelsKt__DeprecatedKt$withIndex$1(this.$this_withIndex, continuation);
        channelsKt__DeprecatedKt$withIndex$1.L$0 = obj;
        return channelsKt__DeprecatedKt$withIndex$1;
    }

    public final Object invoke(ProducerScope<? super IndexedValue<? extends E>> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$withIndex$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x005c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            switch(r1) {
                case 0: goto L_0x003a;
                case 1: goto L_0x0025;
                case 2: goto L_0x0011;
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
            goto L_0x008d
        L_0x0025:
            r1 = r10
            int r2 = r1.I$0
            java.lang.Object r3 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            java.lang.Object r4 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r11)
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            r0 = r11
            goto L_0x0062
        L_0x003a:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            r3 = 0
            kotlinx.coroutines.channels.ReceiveChannel<E> r4 = r1.$this_withIndex
            kotlinx.coroutines.channels.ChannelIterator r4 = r4.iterator()
        L_0x0049:
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r2
            r1.L$1 = r4
            r1.I$0 = r3
            r6 = 1
            r1.label = r6
            java.lang.Object r5 = r4.hasNext(r5)
            if (r5 != r0) goto L_0x005c
            return r0
        L_0x005c:
            r9 = r0
            r0 = r11
            r11 = r5
            r5 = r2
            r2 = r1
            r1 = r9
        L_0x0062:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x008e
            java.lang.Object r11 = r4.next()
            kotlin.collections.IndexedValue r6 = new kotlin.collections.IndexedValue
            int r7 = r3 + 1
            r6.<init>(r3, r11)
            r3 = r2
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r2.L$0 = r5
            r2.L$1 = r4
            r2.I$0 = r7
            r8 = 2
            r2.label = r8
            java.lang.Object r11 = r5.send(r6, r3)
            if (r11 != r1) goto L_0x0088
            return r1
        L_0x0088:
            r11 = r0
            r0 = r1
            r1 = r2
            r2 = r5
            r3 = r7
        L_0x008d:
            goto L_0x0049
        L_0x008e:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$withIndex$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
