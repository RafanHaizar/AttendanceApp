package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$drop$1", mo130f = "Deprecated.kt", mo131i = {0, 0, 1, 2}, mo132l = {164, 169, 170}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "remaining", "$this$produce", "$this$produce"}, mo135s = {"L$0", "I$0", "L$0", "L$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$drop$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {

    /* renamed from: $n */
    final /* synthetic */ int f4$n;
    final /* synthetic */ ReceiveChannel<E> $this_drop;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$drop$1(int i, ReceiveChannel<? extends E> receiveChannel, Continuation<? super ChannelsKt__DeprecatedKt$drop$1> continuation) {
        super(2, continuation);
        this.f4$n = i;
        this.$this_drop = receiveChannel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$drop$1 channelsKt__DeprecatedKt$drop$1 = new ChannelsKt__DeprecatedKt$drop$1(this.f4$n, this.$this_drop, continuation);
        channelsKt__DeprecatedKt$drop$1.L$0 = obj;
        return channelsKt__DeprecatedKt$drop$1;
    }

    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$drop$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0067, code lost:
        r1.L$0 = r5;
        r1.L$1 = r4;
        r1.I$0 = r3;
        r1.label = 1;
        r6 = r4.hasNext(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0076, code lost:
        if (r6 != r0) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0078, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0079, code lost:
        r7 = r0;
        r0 = r9;
        r9 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r1;
        r1 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0087, code lost:
        if (((java.lang.Boolean) r9).booleanValue() == false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0089, code lost:
        r5.next();
        r9 = r4 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x008f, code lost:
        if (r9 != 0) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0092, code lost:
        r4 = r5;
        r5 = r6;
        r7 = r3;
        r3 = r9;
        r9 = r0;
        r0 = r1;
        r1 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009a, code lost:
        r9 = r0;
        r0 = r1;
        r1 = r3;
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009e, code lost:
        r2 = r1.$this_drop.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a4, code lost:
        r1.L$0 = r3;
        r1.L$1 = r2;
        r1.label = 2;
        r4 = r2.hasNext(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b2, code lost:
        if (r4 != r0) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b4, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b5, code lost:
        r7 = r0;
        r0 = r9;
        r9 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r1;
        r1 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c2, code lost:
        if (((java.lang.Boolean) r9).booleanValue() == false) goto L_0x00df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c4, code lost:
        r2.L$0 = r4;
        r2.L$1 = r3;
        r2.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d6, code lost:
        if (r4.send(r3.next(), r2) != r1) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00d8, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d9, code lost:
        r9 = r0;
        r0 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00e1, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            r2 = 1
            switch(r1) {
                case 0: goto L_0x0048;
                case 1: goto L_0x0033;
                case 2: goto L_0x0020;
                case 3: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x0012:
            r1 = r8
            java.lang.Object r2 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x00de
        L_0x0020:
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
            goto L_0x00bc
        L_0x0033:
            r1 = r8
            int r3 = r1.I$0
            java.lang.Object r4 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r9)
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r9
            goto L_0x0081
        L_0x0048:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            int r4 = r1.f4$n
            if (r4 < 0) goto L_0x0056
            r5 = 1
            goto L_0x0057
        L_0x0056:
            r5 = 0
        L_0x0057:
            if (r5 == 0) goto L_0x00e2
            int r4 = r1.f4$n
            if (r4 <= 0) goto L_0x009e
            kotlinx.coroutines.channels.ReceiveChannel<E> r5 = r1.$this_drop
            kotlinx.coroutines.channels.ChannelIterator r5 = r5.iterator()
            r7 = r5
            r5 = r3
            r3 = r4
            r4 = r7
        L_0x0067:
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r1.L$0 = r5
            r1.L$1 = r4
            r1.I$0 = r3
            r1.label = r2
            java.lang.Object r6 = r4.hasNext(r6)
            if (r6 != r0) goto L_0x0079
            return r0
        L_0x0079:
            r7 = r0
            r0 = r9
            r9 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r1
            r1 = r7
        L_0x0081:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x009a
            r5.next()
            int r9 = r4 + -1
            if (r9 != 0) goto L_0x0092
            goto L_0x009a
        L_0x0092:
            r4 = r5
            r5 = r6
            r7 = r3
            r3 = r9
            r9 = r0
            r0 = r1
            r1 = r7
            goto L_0x0067
        L_0x009a:
            r9 = r0
            r0 = r1
            r1 = r3
            r3 = r6
        L_0x009e:
            kotlinx.coroutines.channels.ReceiveChannel<E> r2 = r1.$this_drop
            kotlinx.coroutines.channels.ChannelIterator r2 = r2.iterator()
        L_0x00a4:
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r1.L$0 = r3
            r1.L$1 = r2
            r5 = 2
            r1.label = r5
            java.lang.Object r4 = r2.hasNext(r4)
            if (r4 != r0) goto L_0x00b5
            return r0
        L_0x00b5:
            r7 = r0
            r0 = r9
            r9 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r7
        L_0x00bc:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x00df
            java.lang.Object r9 = r3.next()
            r5 = r2
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r2.L$0 = r4
            r2.L$1 = r3
            r6 = 3
            r2.label = r6
            java.lang.Object r9 = r4.send(r9, r5)
            if (r9 != r1) goto L_0x00d9
            return r1
        L_0x00d9:
            r9 = r0
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r4
        L_0x00de:
            goto L_0x00a4
        L_0x00df:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        L_0x00e2:
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
            goto L_0x0107
        L_0x0106:
            throw r2
        L_0x0107:
            goto L_0x0106
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$drop$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
