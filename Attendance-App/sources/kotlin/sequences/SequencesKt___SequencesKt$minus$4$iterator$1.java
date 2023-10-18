package kotlin.sequences;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "T", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Boolean;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$minus$4$iterator$1 extends Lambda implements Function1<T, Boolean> {
    final /* synthetic */ Collection<T> $other;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$minus$4$iterator$1(Collection<? extends T> collection) {
        super(1);
        this.$other = collection;
    }

    public final Boolean invoke(T it) {
        return Boolean.valueOf(this.$other.contains(it));
    }
}
