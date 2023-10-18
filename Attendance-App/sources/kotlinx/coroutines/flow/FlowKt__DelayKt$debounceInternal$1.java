package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H@"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;", "downstream", "Lkotlinx/coroutines/flow/FlowCollector;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1", mo130f = "Delay.kt", mo131i = {0, 0, 0, 0, 1, 1, 1, 1}, mo132l = {222, 355}, mo133m = "invokeSuspend", mo134n = {"downstream", "values", "lastValue", "timeoutMillis", "downstream", "values", "lastValue", "timeoutMillis"}, mo135s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$debounceInternal$1 extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T> $this_debounceInternal;
    final /* synthetic */ Function1<T, Long> $timeoutMillisSelector;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$debounceInternal$1(Function1<? super T, Long> function1, Flow<? extends T> flow, Continuation<? super FlowKt__DelayKt$debounceInternal$1> continuation) {
        super(3, continuation);
        this.$timeoutMillisSelector = function1;
        this.$this_debounceInternal = flow;
    }

    public final Object invoke(CoroutineScope coroutineScope, FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        FlowKt__DelayKt$debounceInternal$1 flowKt__DelayKt$debounceInternal$1 = new FlowKt__DelayKt$debounceInternal$1(this.$timeoutMillisSelector, this.$this_debounceInternal, continuation);
        flowKt__DelayKt$debounceInternal$1.L$0 = coroutineScope;
        flowKt__DelayKt$debounceInternal$1.L$1 = flowCollector;
        return flowKt__DelayKt$debounceInternal$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v3, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Can't wrap try/catch for region: R(14:9|(7:11|(1:13)|14|(1:16)(1:17)|(1:28)(2:19|(16:21|(1:23)|24|(1:26)|27|31|(3:33|(1:38)(1:37)|(2:40|41))|42|43|44|(1:46)|47|50|(1:52)|(1:54)(4:55|56|7|(1:57)(0))|54))|28|29)|30|31|(0)|42|43|44|(0)|47|50|(0)|(0)|54) */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0147, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0148, code lost:
        r3.handleBuilderException(r0);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ee  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x012b A[Catch:{ all -> 0x0147 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0155  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x015e  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x007c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r19) {
        /*
            r18 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r18
            int r2 = r1.label
            r4 = 0
            r6 = 1
            r7 = 0
            switch(r2) {
                case 0: goto L_0x004a;
                case 1: goto L_0x0031;
                case 2: goto L_0x0017;
                default: goto L_0x000f;
            }
        L_0x000f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0017:
            r2 = r18
            r8 = r19
            r9 = 0
            java.lang.Object r10 = r2.L$3
            kotlin.jvm.internal.Ref$LongRef r10 = (kotlin.jvm.internal.Ref.LongRef) r10
            java.lang.Object r10 = r2.L$2
            kotlin.jvm.internal.Ref$ObjectRef r10 = (kotlin.jvm.internal.Ref.ObjectRef) r10
            java.lang.Object r11 = r2.L$1
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r12 = r2.L$0
            kotlinx.coroutines.flow.FlowCollector r12 = (kotlinx.coroutines.flow.FlowCollector) r12
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x0165
        L_0x0031:
            r2 = r18
            r8 = r19
            java.lang.Object r9 = r2.L$3
            kotlin.jvm.internal.Ref$LongRef r9 = (kotlin.jvm.internal.Ref.LongRef) r9
            java.lang.Object r10 = r2.L$2
            kotlin.jvm.internal.Ref$ObjectRef r10 = (kotlin.jvm.internal.Ref.ObjectRef) r10
            java.lang.Object r11 = r2.L$1
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r12 = r2.L$0
            kotlinx.coroutines.flow.FlowCollector r12 = (kotlinx.coroutines.flow.FlowCollector) r12
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x00c9
        L_0x004a:
            kotlin.ResultKt.throwOnFailure(r19)
            r2 = r18
            r8 = r19
            java.lang.Object r9 = r2.L$0
            kotlinx.coroutines.CoroutineScope r9 = (kotlinx.coroutines.CoroutineScope) r9
            java.lang.Object r10 = r2.L$1
            r16 = r10
            kotlinx.coroutines.flow.FlowCollector r16 = (kotlinx.coroutines.flow.FlowCollector) r16
            r11 = 0
            r12 = 0
            kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$values$1 r10 = new kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$values$1
            kotlinx.coroutines.flow.Flow<T> r13 = r2.$this_debounceInternal
            r10.<init>(r13, r7)
            r13 = r10
            kotlin.jvm.functions.Function2 r13 = (kotlin.jvm.functions.Function2) r13
            r14 = 3
            r15 = 0
            r10 = r9
            kotlinx.coroutines.channels.ReceiveChannel r9 = kotlinx.coroutines.channels.ProduceKt.produce$default(r10, r11, r12, r13, r14, r15)
            kotlin.jvm.internal.Ref$ObjectRef r10 = new kotlin.jvm.internal.Ref$ObjectRef
            r10.<init>()
            r11 = r9
            r12 = r16
        L_0x0076:
            T r9 = r10.element
            kotlinx.coroutines.internal.Symbol r13 = kotlinx.coroutines.flow.internal.NullSurrogateKt.DONE
            if (r9 == r13) goto L_0x016a
            kotlin.jvm.internal.Ref$LongRef r9 = new kotlin.jvm.internal.Ref$LongRef
            r9.<init>()
            T r13 = r10.element
            if (r13 == 0) goto L_0x00e1
            kotlin.jvm.functions.Function1<T, java.lang.Long> r13 = r2.$timeoutMillisSelector
            kotlinx.coroutines.internal.Symbol r14 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            T r15 = r10.element
            r16 = 0
            if (r15 != r14) goto L_0x0090
            r15 = r7
        L_0x0090:
            java.lang.Object r13 = r13.invoke(r15)
            java.lang.Number r13 = (java.lang.Number) r13
            long r13 = r13.longValue()
            r9.element = r13
            long r13 = r9.element
            int r15 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r15 < 0) goto L_0x00a4
            r13 = 1
            goto L_0x00a5
        L_0x00a4:
            r13 = 0
        L_0x00a5:
            if (r13 == 0) goto L_0x00d3
            long r13 = r9.element
            int r15 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r15 != 0) goto L_0x00e1
            kotlinx.coroutines.internal.Symbol r13 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            T r14 = r10.element
            r15 = 0
            if (r14 != r13) goto L_0x00b5
            r14 = r7
        L_0x00b5:
            r13 = r2
            kotlin.coroutines.Continuation r13 = (kotlin.coroutines.Continuation) r13
            r2.L$0 = r12
            r2.L$1 = r11
            r2.L$2 = r10
            r2.L$3 = r9
            r2.label = r6
            java.lang.Object r13 = r12.emit(r14, r13)
            if (r13 != r0) goto L_0x00c9
            return r0
        L_0x00c9:
            r10.element = r7
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r2
            r2 = r0
            goto L_0x00e8
        L_0x00d3:
            r0 = 0
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Debounce timeout should not be negative"
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x00e1:
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r2
            r2 = r0
        L_0x00e8:
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x0106
            r0 = 0
            T r14 = r11.element
            if (r14 == 0) goto L_0x00fc
            long r14 = r10.element
            int r16 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r16 <= 0) goto L_0x00fa
            goto L_0x00fc
        L_0x00fa:
            r0 = 0
            goto L_0x00fd
        L_0x00fc:
            r0 = 1
        L_0x00fd:
            if (r0 == 0) goto L_0x0100
            goto L_0x0106
        L_0x0100:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x0106:
            r14 = 0
            r8.L$0 = r13
            r8.L$1 = r12
            r8.L$2 = r11
            r8.L$3 = r10
            r0 = 2
            r8.label = r0
            r15 = r8
            kotlin.coroutines.Continuation r15 = (kotlin.coroutines.Continuation) r15
            r16 = 0
            kotlinx.coroutines.selects.SelectBuilderImpl r0 = new kotlinx.coroutines.selects.SelectBuilderImpl
            r0.<init>(r15)
            r19 = r0
            r3 = r19
            r0 = r3
            kotlinx.coroutines.selects.SelectBuilder r0 = (kotlinx.coroutines.selects.SelectBuilder) r0     // Catch:{ all -> 0x0147 }
            r17 = 0
            T r4 = r11.element     // Catch:{ all -> 0x0147 }
            if (r4 == 0) goto L_0x0137
            long r4 = r10.element     // Catch:{ all -> 0x0147 }
            kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$1 r6 = new kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$1     // Catch:{ all -> 0x0147 }
            r6.<init>(r13, r11, r7)     // Catch:{ all -> 0x0147 }
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6     // Catch:{ all -> 0x0147 }
            r0.onTimeout(r4, r6)     // Catch:{ all -> 0x0147 }
        L_0x0137:
            kotlinx.coroutines.selects.SelectClause1 r4 = r12.getOnReceiveCatching()     // Catch:{ all -> 0x0147 }
            kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$2 r5 = new kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$2     // Catch:{ all -> 0x0147 }
            r5.<init>(r11, r13, r7)     // Catch:{ all -> 0x0147 }
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5     // Catch:{ all -> 0x0147 }
            r0.invoke(r4, r5)     // Catch:{ all -> 0x0147 }
            goto L_0x014b
        L_0x0147:
            r0 = move-exception
            r3.handleBuilderException(r0)
        L_0x014b:
            java.lang.Object r0 = r3.getResult()
            java.lang.Object r3 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r0 != r3) goto L_0x015b
            r3 = r8
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r3)
        L_0x015b:
            if (r0 != r2) goto L_0x015e
            return r2
        L_0x015e:
            r0 = r2
            r2 = r8
            r8 = r9
            r10 = r11
            r11 = r12
            r12 = r13
            r9 = r14
        L_0x0165:
            r4 = 0
            r6 = 1
            goto L_0x0076
        L_0x016a:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
