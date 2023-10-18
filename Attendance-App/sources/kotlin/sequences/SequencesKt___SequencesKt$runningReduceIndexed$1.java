package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u0002H\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0004H@"}, mo113d2 = {"<anonymous>", "", "S", "T", "Lkotlin/sequences/SequenceScope;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlin.sequences.SequencesKt___SequencesKt$runningReduceIndexed$1", mo130f = "_Sequences.kt", mo131i = {0, 0, 0, 1, 1, 1, 1}, mo132l = {2373, 2377}, mo133m = "invokeSuspend", mo134n = {"$this$sequence", "iterator", "accumulator", "$this$sequence", "iterator", "accumulator", "index"}, mo135s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "I$0"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$runningReduceIndexed$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super S>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function3<Integer, S, T, S> $operation;
    final /* synthetic */ Sequence<T> $this_runningReduceIndexed;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$runningReduceIndexed$1(Sequence<? extends T> sequence, Function3<? super Integer, ? super S, ? super T, ? extends S> function3, Continuation<? super SequencesKt___SequencesKt$runningReduceIndexed$1> continuation) {
        super(2, continuation);
        this.$this_runningReduceIndexed = sequence;
        this.$operation = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        SequencesKt___SequencesKt$runningReduceIndexed$1 sequencesKt___SequencesKt$runningReduceIndexed$1 = new SequencesKt___SequencesKt$runningReduceIndexed$1(this.$this_runningReduceIndexed, this.$operation, continuation);
        sequencesKt___SequencesKt$runningReduceIndexed$1.L$0 = obj;
        return sequencesKt___SequencesKt$runningReduceIndexed$1;
    }

    public final Object invoke(SequenceScope<? super S> sequenceScope, Continuation<? super Unit> continuation) {
        return ((SequencesKt___SequencesKt$runningReduceIndexed$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0061, code lost:
        r9 = r4;
        r4 = r3;
        r3 = 1;
        r5 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006a, code lost:
        if (r4.hasNext() == false) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x006c, code lost:
        r6 = r1.$operation;
        r7 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0070, code lost:
        if (r3 >= 0) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0072, code lost:
        kotlin.collections.CollectionsKt.throwIndexOverflow();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0075, code lost:
        r3 = r6.invoke(kotlin.coroutines.jvm.internal.Boxing.boxInt(r3), r2, r4.next());
        r1.L$0 = r5;
        r1.L$1 = r4;
        r1.L$2 = r3;
        r1.I$0 = r7;
        r1.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0093, code lost:
        if (r5.yield(r3, r1) != r0) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0095, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0096, code lost:
        r2 = r3;
        r3 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x009b, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            switch(r1) {
                case 0: goto L_0x0035;
                case 1: goto L_0x0026;
                case 2: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x0011:
            r1 = r10
            int r2 = r1.I$0
            java.lang.Object r3 = r1.L$2
            java.lang.Object r4 = r1.L$1
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r1.L$0
            kotlin.sequences.SequenceScope r5 = (kotlin.sequences.SequenceScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            r9 = r3
            r3 = r2
            r2 = r9
            goto L_0x0098
        L_0x0026:
            r1 = r10
            java.lang.Object r2 = r1.L$2
            java.lang.Object r3 = r1.L$1
            java.util.Iterator r3 = (java.util.Iterator) r3
            java.lang.Object r4 = r1.L$0
            kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x0061
        L_0x0035:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            java.lang.Object r2 = r1.L$0
            r4 = r2
            kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
            kotlin.sequences.Sequence<T> r2 = r1.$this_runningReduceIndexed
            java.util.Iterator r3 = r2.iterator()
            boolean r2 = r3.hasNext()
            if (r2 == 0) goto L_0x0099
            java.lang.Object r2 = r3.next()
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r4
            r1.L$1 = r3
            r1.L$2 = r2
            r6 = 1
            r1.label = r6
            java.lang.Object r5 = r4.yield(r2, r5)
            if (r5 != r0) goto L_0x0061
            return r0
        L_0x0061:
            r5 = 1
            r9 = r4
            r4 = r3
            r3 = r5
            r5 = r9
        L_0x0066:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L_0x0099
            kotlin.jvm.functions.Function3<java.lang.Integer, S, T, S> r6 = r1.$operation
            int r7 = r3 + 1
            if (r3 >= 0) goto L_0x0075
            kotlin.collections.CollectionsKt.throwIndexOverflow()
        L_0x0075:
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r3)
            java.lang.Object r8 = r4.next()
            java.lang.Object r3 = r6.invoke(r3, r2, r8)
            r2 = r1
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r1.L$0 = r5
            r1.L$1 = r4
            r1.L$2 = r3
            r1.I$0 = r7
            r6 = 2
            r1.label = r6
            java.lang.Object r2 = r5.yield(r3, r2)
            if (r2 != r0) goto L_0x0096
            return r0
        L_0x0096:
            r2 = r3
            r3 = r7
        L_0x0098:
            goto L_0x0066
        L_0x0099:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$runningReduceIndexed$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
