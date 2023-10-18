package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@"}, mo113d2 = {"<anonymous>", "", "T", "R", "Lkotlin/sequences/SequenceScope;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlin.sequences.SequencesKt___SequencesKt$runningFoldIndexed$1", mo130f = "_Sequences.kt", mo131i = {0, 1, 1, 1}, mo132l = {2314, 2319}, mo133m = "invokeSuspend", mo134n = {"$this$sequence", "$this$sequence", "accumulator", "index"}, mo135s = {"L$0", "L$0", "L$1", "I$0"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$runningFoldIndexed$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ R $initial;
    final /* synthetic */ Function3<Integer, R, T, R> $operation;
    final /* synthetic */ Sequence<T> $this_runningFoldIndexed;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$runningFoldIndexed$1(R r, Sequence<? extends T> sequence, Function3<? super Integer, ? super R, ? super T, ? extends R> function3, Continuation<? super SequencesKt___SequencesKt$runningFoldIndexed$1> continuation) {
        super(2, continuation);
        this.$initial = r;
        this.$this_runningFoldIndexed = sequence;
        this.$operation = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        SequencesKt___SequencesKt$runningFoldIndexed$1 sequencesKt___SequencesKt$runningFoldIndexed$1 = new SequencesKt___SequencesKt$runningFoldIndexed$1(this.$initial, this.$this_runningFoldIndexed, this.$operation, continuation);
        sequencesKt___SequencesKt$runningFoldIndexed$1.L$0 = obj;
        return sequencesKt___SequencesKt$runningFoldIndexed$1;
    }

    public final Object invoke(SequenceScope<? super R> sequenceScope, Continuation<? super Unit> continuation) {
        return ((SequencesKt___SequencesKt$runningFoldIndexed$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        SequenceScope $this$sequence;
        Object accumulator;
        Iterator<T> it;
        int index;
        SequencesKt___SequencesKt$runningFoldIndexed$1 sequencesKt___SequencesKt$runningFoldIndexed$1;
        SequenceScope $this$sequence2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                sequencesKt___SequencesKt$runningFoldIndexed$1 = this;
                $this$sequence2 = (SequenceScope) sequencesKt___SequencesKt$runningFoldIndexed$1.L$0;
                sequencesKt___SequencesKt$runningFoldIndexed$1.L$0 = $this$sequence2;
                sequencesKt___SequencesKt$runningFoldIndexed$1.label = 1;
                if ($this$sequence2.yield(sequencesKt___SequencesKt$runningFoldIndexed$1.$initial, sequencesKt___SequencesKt$runningFoldIndexed$1) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                sequencesKt___SequencesKt$runningFoldIndexed$1 = this;
                $this$sequence2 = (SequenceScope) sequencesKt___SequencesKt$runningFoldIndexed$1.L$0;
                ResultKt.throwOnFailure($result);
                break;
            case 2:
                sequencesKt___SequencesKt$runningFoldIndexed$1 = this;
                index = sequencesKt___SequencesKt$runningFoldIndexed$1.I$0;
                it = (Iterator) sequencesKt___SequencesKt$runningFoldIndexed$1.L$2;
                accumulator = sequencesKt___SequencesKt$runningFoldIndexed$1.L$1;
                $this$sequence = (SequenceScope) sequencesKt___SequencesKt$runningFoldIndexed$1.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        accumulator = sequencesKt___SequencesKt$runningFoldIndexed$1.$initial;
        $this$sequence = $this$sequence2;
        index = 0;
        it = sequencesKt___SequencesKt$runningFoldIndexed$1.$this_runningFoldIndexed.iterator();
        while (it.hasNext()) {
            Object element = it.next();
            Function3<Integer, R, T, R> function3 = sequencesKt___SequencesKt$runningFoldIndexed$1.$operation;
            int index2 = index + 1;
            if (index < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            accumulator = function3.invoke(Boxing.boxInt(index), accumulator, element);
            sequencesKt___SequencesKt$runningFoldIndexed$1.L$0 = $this$sequence;
            sequencesKt___SequencesKt$runningFoldIndexed$1.L$1 = accumulator;
            sequencesKt___SequencesKt$runningFoldIndexed$1.L$2 = it;
            sequencesKt___SequencesKt$runningFoldIndexed$1.I$0 = index2;
            sequencesKt___SequencesKt$runningFoldIndexed$1.label = 2;
            if ($this$sequence.yield(accumulator, sequencesKt___SequencesKt$runningFoldIndexed$1) == coroutine_suspended) {
                return coroutine_suspended;
            }
            index = index2;
        }
        return Unit.INSTANCE;
    }
}
