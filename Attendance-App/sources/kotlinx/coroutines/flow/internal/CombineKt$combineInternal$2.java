package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u0004H@"}, mo113d2 = {"<anonymous>", "", "R", "T", "Lkotlinx/coroutines/CoroutineScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2", mo130f = "Combine.kt", mo131i = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2}, mo132l = {57, 79, 82}, mo133m = "invokeSuspend", mo134n = {"latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues", "currentEpoch", "latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues", "currentEpoch", "latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues", "currentEpoch"}, mo135s = {"L$0", "L$1", "L$2", "I$0", "I$1", "L$0", "L$1", "L$2", "I$0", "I$1", "L$0", "L$1", "L$2", "I$0", "I$1"})
/* compiled from: Combine.kt */
final class CombineKt$combineInternal$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function0<T[]> $arrayFactory;
    final /* synthetic */ Flow<T>[] $flows;
    final /* synthetic */ FlowCollector<R> $this_combineInternal;
    final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$combineInternal$2(Flow<? extends T>[] flowArr, Function0<T[]> function0, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, FlowCollector<? super R> flowCollector, Continuation<? super CombineKt$combineInternal$2> continuation) {
        super(2, continuation);
        this.$flows = flowArr;
        this.$arrayFactory = function0;
        this.$transform = function3;
        this.$this_combineInternal = flowCollector;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CombineKt$combineInternal$2 combineKt$combineInternal$2 = new CombineKt$combineInternal$2(this.$flows, this.$arrayFactory, this.$transform, this.$this_combineInternal, continuation);
        combineKt$combineInternal$2.L$0 = obj;
        return combineKt$combineInternal$2;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CombineKt$combineInternal$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v14, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v18, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00e8 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x011e  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0169  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r24) {
        /*
            r23 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r23
            int r2 = r1.label
            switch(r2) {
                case 0: goto L_0x0079;
                case 1: goto L_0x0053;
                case 2: goto L_0x0033;
                case 3: goto L_0x0013;
                default: goto L_0x000b;
            }
        L_0x000b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0013:
            r2 = r23
            r3 = r24
            int r4 = r2.I$1
            int r5 = r2.I$0
            java.lang.Object r6 = r2.L$2
            byte[] r6 = (byte[]) r6
            java.lang.Object r7 = r2.L$1
            kotlinx.coroutines.channels.Channel r7 = (kotlinx.coroutines.channels.Channel) r7
            java.lang.Object r8 = r2.L$0
            java.lang.Object[] r8 = (java.lang.Object[]) r8
            kotlin.ResultKt.throwOnFailure(r3)
            r12 = r8
            r22 = r5
            r5 = r4
            r4 = r6
            r6 = r22
            goto L_0x0167
        L_0x0033:
            r2 = r23
            r3 = r24
            int r4 = r2.I$1
            int r5 = r2.I$0
            java.lang.Object r6 = r2.L$2
            byte[] r6 = (byte[]) r6
            java.lang.Object r7 = r2.L$1
            kotlinx.coroutines.channels.Channel r7 = (kotlinx.coroutines.channels.Channel) r7
            java.lang.Object r8 = r2.L$0
            java.lang.Object[] r8 = (java.lang.Object[]) r8
            kotlin.ResultKt.throwOnFailure(r3)
            r12 = r8
            r22 = r5
            r5 = r4
            r4 = r6
            r6 = r22
            goto L_0x0141
        L_0x0053:
            r2 = r23
            r3 = r24
            int r4 = r2.I$1
            int r5 = r2.I$0
            java.lang.Object r6 = r2.L$2
            byte[] r6 = (byte[]) r6
            java.lang.Object r7 = r2.L$1
            kotlinx.coroutines.channels.Channel r7 = (kotlinx.coroutines.channels.Channel) r7
            java.lang.Object r8 = r2.L$0
            java.lang.Object[] r8 = (java.lang.Object[]) r8
            kotlin.ResultKt.throwOnFailure(r3)
            r9 = r3
            kotlinx.coroutines.channels.ChannelResult r9 = (kotlinx.coroutines.channels.ChannelResult) r9
            java.lang.Object r9 = r9.m1271unboximpl()
            r22 = r5
            r5 = r4
            r4 = r6
            r6 = r22
            goto L_0x00ea
        L_0x0079:
            kotlin.ResultKt.throwOnFailure(r24)
            r2 = r23
            r3 = r24
            java.lang.Object r4 = r2.L$0
            kotlinx.coroutines.CoroutineScope r4 = (kotlinx.coroutines.CoroutineScope) r4
            kotlinx.coroutines.flow.Flow<T>[] r5 = r2.$flows
            int r11 = r5.length
            if (r11 != 0) goto L_0x008c
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x008c:
            java.lang.Object[] r12 = new java.lang.Object[r11]
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.flow.internal.NullSurrogateKt.UNINITIALIZED
            r7 = 0
            r8 = 0
            r9 = 6
            r10 = 0
            r5 = r12
            kotlin.collections.ArraysKt.fill$default((java.lang.Object[]) r5, (java.lang.Object) r6, (int) r7, (int) r8, (int) r9, (java.lang.Object) r10)
            r5 = 6
            r6 = 0
            kotlinx.coroutines.channels.Channel r19 = kotlinx.coroutines.channels.ChannelKt.Channel$default(r11, r6, r6, r5, r6)
            java.util.concurrent.atomic.AtomicInteger r5 = new java.util.concurrent.atomic.AtomicInteger
            r5.<init>(r11)
            r16 = r5
            r20 = r11
            r5 = 0
        L_0x00a8:
            if (r5 >= r11) goto L_0x00c7
            r15 = r5
            int r21 = r5 + 1
            r6 = 0
            r7 = 0
            kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1 r5 = new kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1
            kotlinx.coroutines.flow.Flow<T>[] r14 = r2.$flows
            r18 = 0
            r13 = r5
            r17 = r19
            r13.<init>(r14, r15, r16, r17, r18)
            r8 = r5
            kotlin.jvm.functions.Function2 r8 = (kotlin.jvm.functions.Function2) r8
            r9 = 3
            r10 = 0
            r5 = r4
            kotlinx.coroutines.Job unused = kotlinx.coroutines.BuildersKt__Builders_commonKt.launch$default(r5, r6, r7, r8, r9, r10)
            r5 = r21
            goto L_0x00a8
        L_0x00c7:
            byte[] r4 = new byte[r11]
            r5 = 0
            r7 = r19
            r6 = r20
        L_0x00ce:
            int r8 = r5 + 1
            byte r5 = (byte) r8
            r8 = r2
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r2.L$0 = r12
            r2.L$1 = r7
            r2.L$2 = r4
            r2.I$0 = r6
            r2.I$1 = r5
            r9 = 1
            r2.label = r9
            java.lang.Object r9 = r7.m1251receiveCatchingJP2dKIU(r8)
            if (r9 != r0) goto L_0x00e9
            return r0
        L_0x00e9:
            r8 = r12
        L_0x00ea:
            java.lang.Object r9 = kotlinx.coroutines.channels.ChannelResult.m1264getOrNullimpl(r9)
            kotlin.collections.IndexedValue r9 = (kotlin.collections.IndexedValue) r9
            if (r9 != 0) goto L_0x00f5
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x00f5:
            int r10 = r9.getIndex()
            r11 = r8[r10]
            java.lang.Object r12 = r9.getValue()
            r8[r10] = r12
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.flow.internal.NullSurrogateKt.UNINITIALIZED
            if (r11 != r9) goto L_0x0108
            int r6 = r6 + -1
        L_0x0108:
            byte r9 = r4[r10]
            if (r9 != r5) goto L_0x010d
            goto L_0x011c
        L_0x010d:
            byte r9 = (byte) r5
            r4[r10] = r9
            java.lang.Object r9 = r7.m1252tryReceivePtdJZtk()
            java.lang.Object r9 = kotlinx.coroutines.channels.ChannelResult.m1264getOrNullimpl(r9)
            kotlin.collections.IndexedValue r9 = (kotlin.collections.IndexedValue) r9
            if (r9 != 0) goto L_0x016c
        L_0x011c:
            if (r6 != 0) goto L_0x0169
            kotlin.jvm.functions.Function0<T[]> r9 = r2.$arrayFactory
            java.lang.Object r9 = r9.invoke()
            java.lang.Object[] r9 = (java.lang.Object[]) r9
            if (r9 != 0) goto L_0x0142
            kotlin.jvm.functions.Function3<kotlinx.coroutines.flow.FlowCollector<? super R>, T[], kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r9 = r2.$transform
            kotlinx.coroutines.flow.FlowCollector<R> r10 = r2.$this_combineInternal
            r2.L$0 = r8
            r2.L$1 = r7
            r2.L$2 = r4
            r2.I$0 = r6
            r2.I$1 = r5
            r11 = 2
            r2.label = r11
            java.lang.Object r9 = r9.invoke(r10, r8, r2)
            if (r9 != r0) goto L_0x0140
            return r0
        L_0x0140:
            r12 = r8
        L_0x0141:
            goto L_0x00ce
        L_0x0142:
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 14
            r16 = 0
            r10 = r8
            r11 = r9
            kotlin.collections.ArraysKt.copyInto$default((java.lang.Object[]) r10, (java.lang.Object[]) r11, (int) r12, (int) r13, (int) r14, (int) r15, (java.lang.Object) r16)
            kotlin.jvm.functions.Function3<kotlinx.coroutines.flow.FlowCollector<? super R>, T[], kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r10 = r2.$transform
            kotlinx.coroutines.flow.FlowCollector<R> r11 = r2.$this_combineInternal
            r2.L$0 = r8
            r2.L$1 = r7
            r2.L$2 = r4
            r2.I$0 = r6
            r2.I$1 = r5
            r12 = 3
            r2.label = r12
            java.lang.Object r9 = r10.invoke(r11, r9, r2)
            if (r9 != r0) goto L_0x0166
            return r0
        L_0x0166:
            r12 = r8
        L_0x0167:
            goto L_0x00ce
        L_0x0169:
            r12 = r8
            goto L_0x00ce
        L_0x016c:
            goto L_0x00f5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
