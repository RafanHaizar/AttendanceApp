package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo112d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u0003*\u00020\u0004H@"}, mo113d2 = {"<anonymous>", "Lkotlinx/coroutines/channels/ChannelResult;", "", "E", "Lkotlinx/coroutines/CoroutineScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ChannelsKt__ChannelsKt$trySendBlocking$2", mo130f = "Channels.kt", mo131i = {}, mo132l = {39}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Channels.kt */
final class ChannelsKt__ChannelsKt$trySendBlocking$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ChannelResult<? extends Unit>>, Object> {
    final /* synthetic */ E $element;
    final /* synthetic */ SendChannel<E> $this_trySendBlocking;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__ChannelsKt$trySendBlocking$2(SendChannel<? super E> sendChannel, E e, Continuation<? super ChannelsKt__ChannelsKt$trySendBlocking$2> continuation) {
        super(2, continuation);
        this.$this_trySendBlocking = sendChannel;
        this.$element = e;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__ChannelsKt$trySendBlocking$2 channelsKt__ChannelsKt$trySendBlocking$2 = new ChannelsKt__ChannelsKt$trySendBlocking$2(this.$this_trySendBlocking, this.$element, continuation);
        channelsKt__ChannelsKt$trySendBlocking$2.L$0 = obj;
        return channelsKt__ChannelsKt$trySendBlocking$2;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super ChannelResult<Unit>> continuation) {
        return ((ChannelsKt__ChannelsKt$trySendBlocking$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r1 = kotlin.Result.m1345constructorimpl(kotlin.Unit.INSTANCE);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            switch(r1) {
                case 0: goto L_0x0019;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x0011:
            r0 = r7
            r1 = 0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch:{ all -> 0x0017 }
            goto L_0x0034
        L_0x0017:
            r1 = move-exception
            goto L_0x003f
        L_0x0019:
            kotlin.ResultKt.throwOnFailure(r8)
            r1 = r7
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            kotlinx.coroutines.channels.SendChannel<E> r3 = r1.$this_trySendBlocking
            E r4 = r1.$element
            kotlin.Result$Companion r2 = kotlin.Result.Companion     // Catch:{ all -> 0x003b }
            r2 = 0
            r5 = 1
            r1.label = r5     // Catch:{ all -> 0x003b }
            java.lang.Object r3 = r3.send(r4, r1)     // Catch:{ all -> 0x003b }
            if (r3 != r0) goto L_0x0032
            return r0
        L_0x0032:
            r0 = r1
            r1 = r2
        L_0x0034:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0017 }
            java.lang.Object r1 = kotlin.Result.m1345constructorimpl(r1)     // Catch:{ all -> 0x0017 }
            goto L_0x0049
        L_0x003b:
            r0 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x003f:
            kotlin.Result$Companion r2 = kotlin.Result.Companion
            java.lang.Object r1 = kotlin.ResultKt.createFailure(r1)
            java.lang.Object r1 = kotlin.Result.m1345constructorimpl(r1)
        L_0x0049:
            boolean r2 = kotlin.Result.m1352isSuccessimpl(r1)
            if (r2 == 0) goto L_0x0058
            kotlinx.coroutines.channels.ChannelResult$Companion r2 = kotlinx.coroutines.channels.ChannelResult.Companion
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            java.lang.Object r2 = r2.m1274successJP2dKIU(r3)
            goto L_0x0062
        L_0x0058:
            kotlinx.coroutines.channels.ChannelResult$Companion r2 = kotlinx.coroutines.channels.ChannelResult.Companion
            java.lang.Throwable r3 = kotlin.Result.m1348exceptionOrNullimpl(r1)
            java.lang.Object r2 = r2.m1272closedJP2dKIU(r3)
        L_0x0062:
            kotlinx.coroutines.channels.ChannelResult r2 = kotlinx.coroutines.channels.ChannelResult.m1259boximpl(r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__ChannelsKt$trySendBlocking$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
