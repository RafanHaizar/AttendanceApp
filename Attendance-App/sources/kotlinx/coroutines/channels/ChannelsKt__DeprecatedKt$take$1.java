package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$take$1", mo130f = "Deprecated.kt", mo131i = {0, 0, 1, 1}, mo132l = {254, 255}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "remaining", "$this$produce", "remaining"}, mo135s = {"L$0", "I$0", "L$0", "I$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$take$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {

    /* renamed from: $n */
    final /* synthetic */ int f5$n;
    final /* synthetic */ ReceiveChannel<E> $this_take;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$take$1(int i, ReceiveChannel<? extends E> receiveChannel, Continuation<? super ChannelsKt__DeprecatedKt$take$1> continuation) {
        super(2, continuation);
        this.f5$n = i;
        this.$this_take = receiveChannel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$take$1 channelsKt__DeprecatedKt$take$1 = new ChannelsKt__DeprecatedKt$take$1(this.f5$n, this.$this_take, continuation);
        channelsKt__DeprecatedKt$take$1.L$0 = obj;
        return channelsKt__DeprecatedKt$take$1;
    }

    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$take$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x009a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 1
            switch(r1) {
                case 0: goto L_0x0037;
                case 1: goto L_0x0022;
                case 2: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x0012:
            r1 = r10
            int r3 = r1.I$0
            java.lang.Object r4 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x0092
        L_0x0022:
            r1 = r10
            int r3 = r1.I$0
            java.lang.Object r4 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r11
            goto L_0x006d
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            int r4 = r1.f5$n
            if (r4 != 0) goto L_0x0046
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0046:
            if (r4 < 0) goto L_0x004a
            r5 = 1
            goto L_0x004b
        L_0x004a:
            r5 = 0
        L_0x004b:
            if (r5 == 0) goto L_0x00a2
            int r4 = r1.f5$n
            kotlinx.coroutines.channels.ReceiveChannel<E> r5 = r1.$this_take
            kotlinx.coroutines.channels.ChannelIterator r5 = r5.iterator()
        L_0x0055:
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r1.L$0 = r3
            r1.L$1 = r5
            r1.I$0 = r4
            r1.label = r2
            java.lang.Object r6 = r5.hasNext(r6)
            if (r6 != r0) goto L_0x0067
            return r0
        L_0x0067:
            r9 = r0
            r0 = r11
            r11 = r6
            r6 = r3
            r3 = r1
            r1 = r9
        L_0x006d:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x009f
            java.lang.Object r11 = r5.next()
            r7 = r3
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r3.L$0 = r6
            r3.L$1 = r5
            r3.I$0 = r4
            r8 = 2
            r3.label = r8
            java.lang.Object r11 = r6.send(r11, r7)
            if (r11 != r1) goto L_0x008c
            return r1
        L_0x008c:
            r11 = r0
            r0 = r1
            r1 = r3
            r3 = r4
            r4 = r5
            r5 = r6
        L_0x0092:
            int r3 = r3 + -1
            if (r3 != 0) goto L_0x009a
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x009a:
            r9 = r4
            r4 = r3
            r3 = r5
            r5 = r9
            goto L_0x0055
        L_0x009f:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L_0x00a2:
            r0 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "Requested element count "
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r4 = " is less than zero."
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r0 = r2.toString()
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r0 = r0.toString()
            r2.<init>(r0)
            goto L_0x00c7
        L_0x00c6:
            throw r2
        L_0x00c7:
            goto L_0x00c6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$take$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
