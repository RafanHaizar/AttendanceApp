package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(mo112d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0002¨\u0006\u0004"}, mo113d2 = {"kotlin/sequences/SequencesKt__SequencesKt$Sequence$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 176)
/* compiled from: Sequences.kt */
public final class SequencesKt__SequencesKt$Sequence$1 implements Sequence<T> {
    final /* synthetic */ Function0<Iterator<T>> $iterator;

    public SequencesKt__SequencesKt$Sequence$1(Function0<? extends Iterator<? extends T>> $iterator2) {
        this.$iterator = $iterator2;
    }

    public Iterator<T> iterator() {
        return this.$iterator.invoke();
    }
}
