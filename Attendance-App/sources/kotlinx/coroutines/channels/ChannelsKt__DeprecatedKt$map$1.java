package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@"}, mo113d2 = {"<anonymous>", "", "E", "R", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$map$1", mo130f = "Deprecated.kt", mo131i = {0, 0, 1, 1, 2, 2}, mo132l = {487, 333, 333}, mo133m = "invokeSuspend", mo134n = {"$this$produce", "$this$consume$iv$iv", "$this$produce", "$this$consume$iv$iv", "$this$produce", "$this$consume$iv$iv"}, mo135s = {"L$0", "L$2", "L$0", "L$2", "L$0", "L$2"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$map$1 extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel<E> $this_map;
    final /* synthetic */ Function2<E, Continuation<? super R>, Object> $transform;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$map$1(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super ChannelsKt__DeprecatedKt$map$1> continuation) {
        super(2, continuation);
        this.$this_map = receiveChannel;
        this.$transform = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$map$1 channelsKt__DeprecatedKt$map$1 = new ChannelsKt__DeprecatedKt$map$1(this.$this_map, this.$transform, continuation);
        channelsKt__DeprecatedKt$map$1.L$0 = obj;
        return channelsKt__DeprecatedKt$map$1;
    }

    public final Object invoke(ProducerScope<? super R> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$map$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: kotlinx.coroutines.channels.ReceiveChannel<E>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r2.L$0 = r12;
        r2.L$1 = r8;
        r2.L$2 = r10;
        r2.L$3 = r7;
        r2.label = 1;
        r11 = r7.hasNext(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00bb, code lost:
        if (r11 != r0) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00bd, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00be, code lost:
        r16 = r4;
        r4 = r3;
        r3 = r11;
        r11 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00d0, code lost:
        if (((java.lang.Boolean) r3).booleanValue() == false) goto L_0x011b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d2, code lost:
        r3 = r8.next();
        r13 = 0;
        r2.L$0 = r12;
        r2.L$1 = r9;
        r2.L$2 = r11;
        r2.L$3 = r8;
        r2.L$4 = r12;
        r2.label = 2;
        r14 = r9.invoke(r3, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00e8, code lost:
        if (r14 != r0) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ea, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00eb, code lost:
        r3 = r14;
        r14 = r12;
        r12 = r9;
        r9 = r8;
        r8 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r2.L$0 = r14;
        r2.L$1 = r12;
        r2.L$2 = r11;
        r2.L$3 = r9;
        r2.L$4 = null;
        r2.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0102, code lost:
        if (r8.send(r3, r2) != r0) goto L_0x0105;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0104, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0105, code lost:
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r8 = r9;
        r9 = r10;
        r10 = r11;
        r11 = r12;
        r7 = r13;
        r12 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x010f, code lost:
        r7 = r8;
        r8 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0112, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0113, code lost:
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r9 = r10;
        r10 = r11;
        r12 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x011d, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0124, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0125, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0126, code lost:
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r9 = r10;
        r10 = r11;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            r17 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r17
            int r2 = r1.label
            switch(r2) {
                case 0: goto L_0x0088;
                case 1: goto L_0x005f;
                case 2: goto L_0x0031;
                case 3: goto L_0x0013;
                default: goto L_0x000b;
            }
        L_0x000b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0013:
            r2 = r17
            r3 = r18
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.Object r8 = r2.L$3
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            r9 = 0
            java.lang.Object r10 = r2.L$2
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r11 = r2.L$1
            kotlin.jvm.functions.Function2 r11 = (kotlin.jvm.functions.Function2) r11
            java.lang.Object r12 = r2.L$0
            kotlinx.coroutines.channels.ProducerScope r12 = (kotlinx.coroutines.channels.ProducerScope) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0085 }
            goto L_0x010f
        L_0x0031:
            r2 = r17
            r3 = r18
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.Object r8 = r2.L$4
            kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
            java.lang.Object r9 = r2.L$3
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            r10 = 0
            java.lang.Object r11 = r2.L$2
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r12 = r2.L$1
            kotlin.jvm.functions.Function2 r12 = (kotlin.jvm.functions.Function2) r12
            java.lang.Object r13 = r2.L$0
            kotlinx.coroutines.channels.ProducerScope r13 = (kotlinx.coroutines.channels.ProducerScope) r13
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0059 }
            r14 = r13
            r13 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            goto L_0x00f0
        L_0x0059:
            r0 = move-exception
            r9 = r10
            r10 = r11
            r12 = r13
            goto L_0x012f
        L_0x005f:
            r2 = r17
            r3 = r18
            r4 = 0
            r5 = 0
            r6 = 0
            java.lang.Object r7 = r2.L$3
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            r9 = 0
            java.lang.Object r8 = r2.L$2
            r10 = r8
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r8 = r2.L$1
            kotlin.jvm.functions.Function2 r8 = (kotlin.jvm.functions.Function2) r8
            java.lang.Object r11 = r2.L$0
            r12 = r11
            kotlinx.coroutines.channels.ProducerScope r12 = (kotlinx.coroutines.channels.ProducerScope) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0085 }
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            goto L_0x00ca
        L_0x0085:
            r0 = move-exception
            goto L_0x012f
        L_0x0088:
            kotlin.ResultKt.throwOnFailure(r18)
            r2 = r17
            r3 = r18
            java.lang.Object r4 = r2.L$0
            r12 = r4
            kotlinx.coroutines.channels.ProducerScope r12 = (kotlinx.coroutines.channels.ProducerScope) r12
            kotlinx.coroutines.channels.ReceiveChannel<E> r4 = r2.$this_map
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r5 = r2.$transform
            r6 = 0
            r10 = r4
            r4 = 0
            r9 = 0
            r7 = r10
            r8 = 0
            kotlinx.coroutines.channels.ChannelIterator r11 = r7.iterator()     // Catch:{ all -> 0x012c }
            r7 = r11
            r16 = r5
            r5 = r4
            r4 = r6
            r6 = r8
            r8 = r16
        L_0x00ac:
            r2.L$0 = r12     // Catch:{ all -> 0x0085 }
            r2.L$1 = r8     // Catch:{ all -> 0x0085 }
            r2.L$2 = r10     // Catch:{ all -> 0x0085 }
            r2.L$3 = r7     // Catch:{ all -> 0x0085 }
            r11 = 1
            r2.label = r11     // Catch:{ all -> 0x0085 }
            java.lang.Object r11 = r7.hasNext(r2)     // Catch:{ all -> 0x0085 }
            if (r11 != r0) goto L_0x00be
            return r0
        L_0x00be:
            r16 = r4
            r4 = r3
            r3 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r16
        L_0x00ca:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0125 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0125 }
            if (r3 == 0) goto L_0x011a
            java.lang.Object r3 = r8.next()     // Catch:{ all -> 0x0125 }
            r13 = 0
            r2.L$0 = r12     // Catch:{ all -> 0x0125 }
            r2.L$1 = r9     // Catch:{ all -> 0x0125 }
            r2.L$2 = r11     // Catch:{ all -> 0x0125 }
            r2.L$3 = r8     // Catch:{ all -> 0x0125 }
            r2.L$4 = r12     // Catch:{ all -> 0x0125 }
            r14 = 2
            r2.label = r14     // Catch:{ all -> 0x0125 }
            java.lang.Object r14 = r9.invoke(r3, r2)     // Catch:{ all -> 0x0125 }
            if (r14 != r0) goto L_0x00eb
            return r0
        L_0x00eb:
            r3 = r14
            r14 = r12
            r12 = r9
            r9 = r8
            r8 = r14
        L_0x00f0:
            r2.L$0 = r14     // Catch:{ all -> 0x0112 }
            r2.L$1 = r12     // Catch:{ all -> 0x0112 }
            r2.L$2 = r11     // Catch:{ all -> 0x0112 }
            r2.L$3 = r9     // Catch:{ all -> 0x0112 }
            r15 = 0
            r2.L$4 = r15     // Catch:{ all -> 0x0112 }
            r15 = 3
            r2.label = r15     // Catch:{ all -> 0x0112 }
            java.lang.Object r3 = r8.send(r3, r2)     // Catch:{ all -> 0x0112 }
            if (r3 != r0) goto L_0x0105
            return r0
        L_0x0105:
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r7 = r13
            r12 = r14
        L_0x010f:
            r7 = r8
            r8 = r11
            goto L_0x00ac
        L_0x0112:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r9 = r10
            r10 = r11
            r12 = r14
            goto L_0x012f
        L_0x011a:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0125 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r10)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0125:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r9 = r10
            r10 = r11
            goto L_0x012f
        L_0x012c:
            r0 = move-exception
            r5 = r4
            r4 = r6
        L_0x012f:
            r6 = r0
            throw r0     // Catch:{ all -> 0x0132 }
        L_0x0132:
            r0 = move-exception
            r7 = r0
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r6)
            goto L_0x0139
        L_0x0138:
            throw r7
        L_0x0139:
            goto L_0x0138
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$map$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
