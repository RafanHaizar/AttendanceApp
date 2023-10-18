package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$dropWhile$1", mo130f = "Deprecated.kt", mo131i = {0, 1, 1, 2, 3, 4}, mo132l = {181, 182, 183, 187, 188}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "$this$produce", "e", "$this$produce", "$this$produce", "$this$produce"}, mo135s = {"L$0", "L$0", "L$2", "L$0", "L$0", "L$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$dropWhile$1 extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2<E, Continuation<? super Boolean>, Object> $predicate;
    final /* synthetic */ ReceiveChannel<E> $this_dropWhile;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$dropWhile$1(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super ChannelsKt__DeprecatedKt$dropWhile$1> continuation) {
        super(2, continuation);
        this.$this_dropWhile = receiveChannel;
        this.$predicate = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$dropWhile$1 channelsKt__DeprecatedKt$dropWhile$1 = new ChannelsKt__DeprecatedKt$dropWhile$1(this.$this_dropWhile, this.$predicate, continuation);
        channelsKt__DeprecatedKt$dropWhile$1.L$0 = obj;
        return channelsKt__DeprecatedKt$dropWhile$1;
    }

    public final Object invoke(ProducerScope<? super E> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$dropWhile$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0076, code lost:
        r1.L$0 = r3;
        r1.L$1 = r4;
        r1.L$2 = null;
        r1.label = 1;
        r5 = r4.hasNext(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0086, code lost:
        if (r5 != r0) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0088, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0089, code lost:
        r8 = r0;
        r0 = r10;
        r10 = r5;
        r5 = r3;
        r3 = r1;
        r1 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0095, code lost:
        if (((java.lang.Boolean) r10).booleanValue() == false) goto L_0x00db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0097, code lost:
        r10 = r4.next();
        r6 = r3.$predicate;
        r3.L$0 = r5;
        r3.L$1 = r4;
        r3.L$2 = r10;
        r3.label = 2;
        r6 = r6.invoke(r10, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00aa, code lost:
        if (r6 != r1) goto L_0x00ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00ac, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00ad, code lost:
        r8 = r4;
        r4 = r10;
        r10 = r6;
        r6 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00b7, code lost:
        if (((java.lang.Boolean) r10).booleanValue() != false) goto L_0x00d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00b9, code lost:
        r3.L$0 = r5;
        r3.L$1 = null;
        r3.L$2 = null;
        r3.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00c9, code lost:
        if (r5.send(r4, r3) != r1) goto L_0x00cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00cb, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00cc, code lost:
        r10 = r0;
        r0 = r1;
        r1 = r3;
        r2 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00d0, code lost:
        r3 = r1;
        r5 = r2;
        r1 = r0;
        r0 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00d5, code lost:
        r10 = r0;
        r0 = r1;
        r1 = r3;
        r3 = r5;
        r4 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00db, code lost:
        r10 = r3.$this_dropWhile.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00e1, code lost:
        r3.L$0 = r5;
        r3.L$1 = r10;
        r3.label = 4;
        r2 = r10.hasNext(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ef, code lost:
        if (r2 != r1) goto L_0x00f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00f1, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00f2, code lost:
        r8 = r2;
        r2 = r10;
        r10 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00fb, code lost:
        if (((java.lang.Boolean) r10).booleanValue() == false) goto L_0x0114;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00fd, code lost:
        r3.L$0 = r5;
        r3.L$1 = r2;
        r3.label = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x010f, code lost:
        if (r5.send(r2.next(), r3) != r1) goto L_0x0112;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0111, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0112, code lost:
        r10 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0116, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 0
            switch(r1) {
                case 0: goto L_0x0068;
                case 1: goto L_0x0056;
                case 2: goto L_0x0041;
                case 3: goto L_0x0037;
                case 4: goto L_0x0025;
                case 5: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0012:
            r1 = r9
            java.lang.Object r2 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r10)
            r5 = r3
            r3 = r1
            r1 = r0
            r0 = r10
            r10 = r2
            goto L_0x0113
        L_0x0025:
            r1 = r9
            java.lang.Object r2 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r10)
            r5 = r3
            r3 = r1
            r1 = r0
            r0 = r10
            goto L_0x00f5
        L_0x0037:
            r1 = r9
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x00d0
        L_0x0041:
            r1 = r9
            java.lang.Object r3 = r1.L$2
            java.lang.Object r4 = r1.L$1
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            java.lang.Object r5 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
            kotlin.ResultKt.throwOnFailure(r10)
            r6 = r4
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r10
            goto L_0x00b1
        L_0x0056:
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
            goto L_0x008f
        L_0x0068:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlinx.coroutines.channels.ReceiveChannel<E> r4 = r1.$this_dropWhile
            kotlinx.coroutines.channels.ChannelIterator r4 = r4.iterator()
        L_0x0076:
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r3
            r1.L$1 = r4
            r1.L$2 = r2
            r6 = 1
            r1.label = r6
            java.lang.Object r5 = r4.hasNext(r5)
            if (r5 != r0) goto L_0x0089
            return r0
        L_0x0089:
            r8 = r0
            r0 = r10
            r10 = r5
            r5 = r3
            r3 = r1
            r1 = r8
        L_0x008f:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L_0x00db
            java.lang.Object r10 = r4.next()
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r6 = r3.$predicate
            r3.L$0 = r5
            r3.L$1 = r4
            r3.L$2 = r10
            r7 = 2
            r3.label = r7
            java.lang.Object r6 = r6.invoke(r10, r3)
            if (r6 != r1) goto L_0x00ad
            return r1
        L_0x00ad:
            r8 = r4
            r4 = r10
            r10 = r6
            r6 = r8
        L_0x00b1:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 != 0) goto L_0x00d5
            r10 = r3
            kotlin.coroutines.Continuation r10 = (kotlin.coroutines.Continuation) r10
            r3.L$0 = r5
            r3.L$1 = r2
            r3.L$2 = r2
            r2 = 3
            r3.label = r2
            java.lang.Object r10 = r5.send(r4, r10)
            if (r10 != r1) goto L_0x00cc
            return r1
        L_0x00cc:
            r10 = r0
            r0 = r1
            r1 = r3
            r2 = r5
        L_0x00d0:
            r3 = r1
            r5 = r2
            r1 = r0
            r0 = r10
            goto L_0x00db
        L_0x00d5:
            r10 = r0
            r0 = r1
            r1 = r3
            r3 = r5
            r4 = r6
            goto L_0x0076
        L_0x00db:
            kotlinx.coroutines.channels.ReceiveChannel<E> r10 = r3.$this_dropWhile
            kotlinx.coroutines.channels.ChannelIterator r10 = r10.iterator()
        L_0x00e1:
            r2 = r3
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r3.L$0 = r5
            r3.L$1 = r10
            r4 = 4
            r3.label = r4
            java.lang.Object r2 = r10.hasNext(r2)
            if (r2 != r1) goto L_0x00f2
            return r1
        L_0x00f2:
            r8 = r2
            r2 = r10
            r10 = r8
        L_0x00f5:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L_0x0114
            java.lang.Object r10 = r2.next()
            r4 = r3
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r3.L$0 = r5
            r3.L$1 = r2
            r6 = 5
            r3.label = r6
            java.lang.Object r10 = r5.send(r10, r4)
            if (r10 != r1) goto L_0x0112
            return r1
        L_0x0112:
            r10 = r2
        L_0x0113:
            goto L_0x00e1
        L_0x0114:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$dropWhile$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
