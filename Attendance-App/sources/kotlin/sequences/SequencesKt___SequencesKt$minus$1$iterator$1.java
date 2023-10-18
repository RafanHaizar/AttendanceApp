package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "T", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Boolean;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$minus$1$iterator$1 extends Lambda implements Function1<T, Boolean> {
    final /* synthetic */ T $element;
    final /* synthetic */ Ref.BooleanRef $removed;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$minus$1$iterator$1(Ref.BooleanRef booleanRef, T t) {
        super(1);
        this.$removed = booleanRef;
        this.$element = t;
    }

    public final Boolean invoke(T it) {
        boolean z = true;
        if (!this.$removed.element && Intrinsics.areEqual((Object) it, (Object) this.$element)) {
            this.$removed.element = true;
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
