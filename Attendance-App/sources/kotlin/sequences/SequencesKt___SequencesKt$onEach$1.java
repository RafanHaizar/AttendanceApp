package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u0004\n\u0002\b\u0005\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, mo113d2 = {"<anonymous>", "T", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$onEach$1 extends Lambda implements Function1<T, T> {
    final /* synthetic */ Function1<T, Unit> $action;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$onEach$1(Function1<? super T, Unit> function1) {
        super(1);
        this.$action = function1;
    }

    public final T invoke(T it) {
        this.$action.invoke(it);
        return it;
    }
}
