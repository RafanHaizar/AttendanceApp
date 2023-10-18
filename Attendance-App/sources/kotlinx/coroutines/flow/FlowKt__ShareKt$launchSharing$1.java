package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1", mo130f = "Share.kt", mo131i = {}, mo132l = {214, 218, 219, 225}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Share.kt */
final class FlowKt__ShareKt$launchSharing$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ T $initialValue;
    final /* synthetic */ MutableSharedFlow<T> $shared;
    final /* synthetic */ SharingStarted $started;
    final /* synthetic */ Flow<T> $upstream;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__ShareKt$launchSharing$1(SharingStarted sharingStarted, Flow<? extends T> flow, MutableSharedFlow<T> mutableSharedFlow, T t, Continuation<? super FlowKt__ShareKt$launchSharing$1> continuation) {
        super(2, continuation);
        this.$started = sharingStarted;
        this.$upstream = flow;
        this.$shared = mutableSharedFlow;
        this.$initialValue = t;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FlowKt__ShareKt$launchSharing$1(this.$started, this.$upstream, this.$shared, this.$initialValue, continuation);
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((FlowKt__ShareKt$launchSharing$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b5, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0084 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0085  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            switch(r1) {
                case 0: goto L_0x0027;
                case 1: goto L_0x0022;
                case 2: goto L_0x001d;
                case 3: goto L_0x0017;
                case 4: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x0011:
            r0 = r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x00b2
        L_0x0017:
            r0 = r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x0086
        L_0x001d:
            r1 = r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x0072
        L_0x0022:
            r0 = r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x004a
        L_0x0027:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            kotlinx.coroutines.flow.SharingStarted r2 = r1.$started
            kotlinx.coroutines.flow.SharingStarted$Companion r3 = kotlinx.coroutines.flow.SharingStarted.Companion
            kotlinx.coroutines.flow.SharingStarted r3 = r3.getEagerly()
            if (r2 != r3) goto L_0x004b
            kotlinx.coroutines.flow.Flow<T> r2 = r1.$upstream
            kotlinx.coroutines.flow.MutableSharedFlow<T> r3 = r1.$shared
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 1
            r1.label = r5
            java.lang.Object r2 = r2.collect(r3, r4)
            if (r2 != r0) goto L_0x0049
            return r0
        L_0x0049:
            r0 = r1
        L_0x004a:
            goto L_0x00b3
        L_0x004b:
            kotlinx.coroutines.flow.SharingStarted r2 = r1.$started
            kotlinx.coroutines.flow.SharingStarted$Companion r3 = kotlinx.coroutines.flow.SharingStarted.Companion
            kotlinx.coroutines.flow.SharingStarted r3 = r3.getLazily()
            r4 = 0
            if (r2 != r3) goto L_0x0087
            kotlinx.coroutines.flow.MutableSharedFlow<T> r2 = r1.$shared
            kotlinx.coroutines.flow.StateFlow r2 = r2.getSubscriptionCount()
            kotlinx.coroutines.flow.Flow r2 = (kotlinx.coroutines.flow.Flow) r2
            kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$1 r3 = new kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$1
            r3.<init>(r4)
            kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 2
            r1.label = r5
            java.lang.Object r2 = kotlinx.coroutines.flow.FlowKt.first(r2, r3, r4)
            if (r2 != r0) goto L_0x0072
            return r0
        L_0x0072:
            kotlinx.coroutines.flow.Flow<T> r2 = r1.$upstream
            kotlinx.coroutines.flow.MutableSharedFlow<T> r3 = r1.$shared
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 3
            r1.label = r5
            java.lang.Object r2 = r2.collect(r3, r4)
            if (r2 != r0) goto L_0x0085
            return r0
        L_0x0085:
            r0 = r1
        L_0x0086:
            goto L_0x00b3
        L_0x0087:
            kotlinx.coroutines.flow.SharingStarted r2 = r1.$started
            kotlinx.coroutines.flow.MutableSharedFlow<T> r3 = r1.$shared
            kotlinx.coroutines.flow.StateFlow r3 = r3.getSubscriptionCount()
            kotlinx.coroutines.flow.Flow r2 = r2.command(r3)
            kotlinx.coroutines.flow.Flow r2 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r2)
            kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2 r3 = new kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2
            kotlinx.coroutines.flow.Flow<T> r5 = r1.$upstream
            kotlinx.coroutines.flow.MutableSharedFlow<T> r6 = r1.$shared
            T r7 = r1.$initialValue
            r3.<init>(r5, r6, r7, r4)
            kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 4
            r1.label = r5
            java.lang.Object r2 = kotlinx.coroutines.flow.FlowKt.collectLatest(r2, r3, r4)
            if (r2 != r0) goto L_0x00b1
            return r0
        L_0x00b1:
            r0 = r1
        L_0x00b2:
        L_0x00b3:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
