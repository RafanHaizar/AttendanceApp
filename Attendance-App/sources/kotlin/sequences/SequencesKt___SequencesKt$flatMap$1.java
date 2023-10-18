package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: _Sequences.kt */
/* synthetic */ class SequencesKt___SequencesKt$flatMap$1 extends FunctionReferenceImpl implements Function1<Iterable<? extends R>, Iterator<? extends R>> {
    public static final SequencesKt___SequencesKt$flatMap$1 INSTANCE = new SequencesKt___SequencesKt$flatMap$1();

    SequencesKt___SequencesKt$flatMap$1() {
        super(1, Iterable.class, "iterator", "iterator()Ljava/util/Iterator;", 0);
    }

    public final Iterator<R> invoke(Iterable<? extends R> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return p0.iterator();
    }
}
