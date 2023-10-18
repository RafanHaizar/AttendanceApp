package kotlin.sequences;

import kotlin.Metadata;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, mo113d2 = {"<anonymous>", "", "T", "it", "Lkotlin/collections/IndexedValue;", "invoke", "(Lkotlin/collections/IndexedValue;)Ljava/lang/Boolean;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$filterIndexed$1 extends Lambda implements Function1<IndexedValue<? extends T>, Boolean> {
    final /* synthetic */ Function2<Integer, T, Boolean> $predicate;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$filterIndexed$1(Function2<? super Integer, ? super T, Boolean> function2) {
        super(1);
        this.$predicate = function2;
    }

    public final Boolean invoke(IndexedValue<? extends T> it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return this.$predicate.invoke(Integer.valueOf(it.getIndex()), it.getValue());
    }
}
