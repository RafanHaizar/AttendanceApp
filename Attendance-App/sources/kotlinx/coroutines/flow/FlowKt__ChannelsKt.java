package kotlinx.coroutines.flow;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.ChannelFlowKt;

@Metadata(mo112d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a/\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a9\u0010\u000b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\f\u001a\u00020\rH@ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a&\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012H\u0007\u001a\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, mo113d2 = {"asFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "Lkotlinx/coroutines/channels/BroadcastChannel;", "consumeAsFlow", "Lkotlinx/coroutines/channels/ReceiveChannel;", "emitAll", "", "Lkotlinx/coroutines/flow/FlowCollector;", "channel", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitAllImpl", "consume", "", "emitAllImpl$FlowKt__ChannelsKt", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produceIn", "scope", "Lkotlinx/coroutines/CoroutineScope;", "receiveAsFlow", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Channels.kt */
final /* synthetic */ class FlowKt__ChannelsKt {
    public static final <T> Object emitAll(FlowCollector<? super T> $this$emitAll, ReceiveChannel<? extends T> channel, Continuation<? super Unit> $completion) {
        Object emitAllImpl$FlowKt__ChannelsKt = emitAllImpl$FlowKt__ChannelsKt($this$emitAll, channel, true, $completion);
        return emitAllImpl$FlowKt__ChannelsKt == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emitAllImpl$FlowKt__ChannelsKt : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r10.L$0 = r2;
        r10.L$1 = r9;
        r10.Z$0 = r7;
        r10.label = 1;
        r4 = r9.m1251receiveCatchingJP2dKIU(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007a, code lost:
        if (r4 != r1) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007c, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007d, code lost:
        r6 = r8;
        r8 = r7;
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0081, code lost:
        r7 = r3;
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0088, code lost:
        if (kotlinx.coroutines.channels.ChannelResult.m1267isClosedimpl(r3) == false) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008a, code lost:
        r1 = kotlinx.coroutines.channels.ChannelResult.m1263exceptionOrNullimpl(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x008e, code lost:
        if (r1 != null) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0091, code lost:
        if (r8 == false) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0093, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0099, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x009d, code lost:
        r4 = kotlinx.coroutines.channels.ChannelResult.m1265getOrThrowimpl(r3);
        r10.L$0 = r2;
        r10.L$1 = r9;
        r10.Z$0 = r8;
        r10.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ae, code lost:
        if (r2.emit(r4, r10) != r1) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b0, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b1, code lost:
        r6 = r8;
        r8 = r7;
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b5, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b6, code lost:
        r6 = r8;
        r8 = r7;
        r7 = r6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object emitAllImpl$FlowKt__ChannelsKt(kotlinx.coroutines.flow.FlowCollector<? super T> r7, kotlinx.coroutines.channels.ReceiveChannel<? extends T> r8, boolean r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1 r0 = (kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1 r0 = new kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1
            r0.<init>(r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            switch(r2) {
                case 0: goto L_0x005e;
                case 1: goto L_0x0040;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002d:
            boolean r7 = r10.Z$0
            r8 = 0
            java.lang.Object r9 = r10.L$1
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r2 = r10.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x003d }
            goto L_0x00b4
        L_0x003d:
            r1 = move-exception
            goto L_0x00b9
        L_0x0040:
            r7 = 0
            boolean r8 = r10.Z$0
            java.lang.Object r9 = r10.L$1
            kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
            java.lang.Object r2 = r10.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            r3 = 0
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0057 }
            r4 = r0
            kotlinx.coroutines.channels.ChannelResult r4 = (kotlinx.coroutines.channels.ChannelResult) r4     // Catch:{ all -> 0x0057 }
            java.lang.Object r4 = r4.m1271unboximpl()     // Catch:{ all -> 0x0057 }
            goto L_0x0081
        L_0x0057:
            r1 = move-exception
            r7 = r3
            r6 = r8
            r8 = r7
            r7 = r6
            goto L_0x00b9
        L_0x005e:
            kotlin.ResultKt.throwOnFailure(r0)
            kotlinx.coroutines.flow.FlowKt.ensureActive(r7)
            r2 = 0
            r6 = r2
            r2 = r7
            r7 = r9
            r9 = r8
            r8 = r6
        L_0x006a:
            r3 = 0
            r10.L$0 = r2     // Catch:{ all -> 0x003d }
            r10.L$1 = r9     // Catch:{ all -> 0x003d }
            r10.Z$0 = r7     // Catch:{ all -> 0x003d }
            r4 = 1
            r10.label = r4     // Catch:{ all -> 0x003d }
            java.lang.Object r4 = r9.m1251receiveCatchingJP2dKIU(r10)     // Catch:{ all -> 0x003d }
            if (r4 != r1) goto L_0x007d
            return r1
        L_0x007d:
            r6 = r8
            r8 = r7
            r7 = r3
            r3 = r6
        L_0x0081:
            r7 = r3
            r3 = r4
            boolean r4 = kotlinx.coroutines.channels.ChannelResult.m1267isClosedimpl(r3)     // Catch:{ all -> 0x00b5 }
            if (r4 == 0) goto L_0x009d
            java.lang.Throwable r1 = kotlinx.coroutines.channels.ChannelResult.m1263exceptionOrNullimpl(r3)     // Catch:{ all -> 0x00b5 }
            if (r1 != 0) goto L_0x009a
            if (r8 == 0) goto L_0x0096
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r7)
        L_0x0096:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x009a:
            r3 = 0
            throw r1     // Catch:{ all -> 0x00b5 }
        L_0x009d:
            java.lang.Object r4 = kotlinx.coroutines.channels.ChannelResult.m1265getOrThrowimpl(r3)     // Catch:{ all -> 0x00b5 }
            r10.L$0 = r2     // Catch:{ all -> 0x00b5 }
            r10.L$1 = r9     // Catch:{ all -> 0x00b5 }
            r10.Z$0 = r8     // Catch:{ all -> 0x00b5 }
            r5 = 2
            r10.label = r5     // Catch:{ all -> 0x00b5 }
            java.lang.Object r4 = r2.emit(r4, r10)     // Catch:{ all -> 0x00b5 }
            if (r4 != r1) goto L_0x00b1
            return r1
        L_0x00b1:
            r6 = r8
            r8 = r7
            r7 = r6
        L_0x00b4:
            goto L_0x006a
        L_0x00b5:
            r1 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
        L_0x00b9:
            r8 = r1
            throw r1     // Catch:{ all -> 0x00bc }
        L_0x00bc:
            r1 = move-exception
            if (r7 == 0) goto L_0x00c2
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r9, r8)
        L_0x00c2:
            goto L_0x00c4
        L_0x00c3:
            throw r1
        L_0x00c4:
            goto L_0x00c3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ChannelsKt.emitAllImpl$FlowKt__ChannelsKt(kotlinx.coroutines.flow.FlowCollector, kotlinx.coroutines.channels.ReceiveChannel, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <T> Flow<T> receiveAsFlow(ReceiveChannel<? extends T> $this$receiveAsFlow) {
        return new ChannelAsFlow<>($this$receiveAsFlow, false, (CoroutineContext) null, 0, (BufferOverflow) null, 28, (DefaultConstructorMarker) null);
    }

    public static final <T> Flow<T> consumeAsFlow(ReceiveChannel<? extends T> $this$consumeAsFlow) {
        return new ChannelAsFlow<>($this$consumeAsFlow, true, (CoroutineContext) null, 0, (BufferOverflow) null, 28, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "'BroadcastChannel' is obsolete and all corresponding operators are deprecated in the favour of StateFlow and SharedFlow")
    public static final <T> Flow<T> asFlow(BroadcastChannel<T> $this$asFlow) {
        return new FlowKt__ChannelsKt$asFlow$$inlined$unsafeFlow$1($this$asFlow);
    }

    public static final <T> ReceiveChannel<T> produceIn(Flow<? extends T> $this$produceIn, CoroutineScope scope) {
        return ChannelFlowKt.asChannelFlow($this$produceIn).produceImpl(scope);
    }
}
