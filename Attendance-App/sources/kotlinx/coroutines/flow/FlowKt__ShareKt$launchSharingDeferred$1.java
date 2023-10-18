package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1", mo130f = "Share.kt", mo131i = {}, mo132l = {340}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Share.kt */
final class FlowKt__ShareKt$launchSharingDeferred$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ CompletableDeferred<StateFlow<T>> $result;
    final /* synthetic */ Flow<T> $upstream;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__ShareKt$launchSharingDeferred$1(Flow<? extends T> flow, CompletableDeferred<StateFlow<T>> completableDeferred, Continuation<? super FlowKt__ShareKt$launchSharingDeferred$1> continuation) {
        super(2, continuation);
        this.$upstream = flow;
        this.$result = completableDeferred;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__ShareKt$launchSharingDeferred$1 flowKt__ShareKt$launchSharingDeferred$1 = new FlowKt__ShareKt$launchSharingDeferred$1(this.$upstream, this.$result, continuation);
        flowKt__ShareKt$launchSharingDeferred$1.L$0 = obj;
        return flowKt__ShareKt$launchSharingDeferred$1;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((FlowKt__ShareKt$launchSharingDeferred$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            switch(r1) {
                case 0: goto L_0x0018;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0011:
            r0 = r9
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x0016 }
            goto L_0x003f
        L_0x0016:
            r1 = move-exception
            goto L_0x0047
        L_0x0018:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            kotlin.jvm.internal.Ref$ObjectRef r3 = new kotlin.jvm.internal.Ref$ObjectRef     // Catch:{ all -> 0x0043 }
            r3.<init>()     // Catch:{ all -> 0x0043 }
            kotlinx.coroutines.flow.Flow<T> r4 = r1.$upstream     // Catch:{ all -> 0x0043 }
            kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1$1 r5 = new kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1$1     // Catch:{ all -> 0x0043 }
            kotlinx.coroutines.CompletableDeferred<kotlinx.coroutines.flow.StateFlow<T>> r6 = r1.$result     // Catch:{ all -> 0x0043 }
            r5.<init>(r3, r2, r6)     // Catch:{ all -> 0x0043 }
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5     // Catch:{ all -> 0x0043 }
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6     // Catch:{ all -> 0x0043 }
            r7 = 1
            r1.label = r7     // Catch:{ all -> 0x0043 }
            java.lang.Object r4 = r4.collect(r5, r6)     // Catch:{ all -> 0x0043 }
            if (r4 != r0) goto L_0x003e
            return r0
        L_0x003e:
            r0 = r1
        L_0x003f:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x0043:
            r0 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
        L_0x0047:
            kotlinx.coroutines.CompletableDeferred<kotlinx.coroutines.flow.StateFlow<T>> r2 = r0.$result
            r2.completeExceptionally(r1)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
