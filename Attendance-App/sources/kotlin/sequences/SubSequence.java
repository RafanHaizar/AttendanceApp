package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B#\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0016J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0002J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0016R\u0014\u0010\t\u001a\u00020\u00068BX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0007\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, mo113d2 = {"Lkotlin/sequences/SubSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "startIndex", "", "endIndex", "(Lkotlin/sequences/Sequence;II)V", "count", "getCount", "()I", "drop", "n", "iterator", "", "take", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Sequences.kt */
public final class SubSequence<T> implements Sequence<T>, DropTakeSequence<T> {
    /* access modifiers changed from: private */
    public final int endIndex;
    /* access modifiers changed from: private */
    public final Sequence<T> sequence;
    /* access modifiers changed from: private */
    public final int startIndex;

    public SubSequence(Sequence<? extends T> sequence2, int startIndex2, int endIndex2) {
        Intrinsics.checkNotNullParameter(sequence2, "sequence");
        this.sequence = sequence2;
        this.startIndex = startIndex2;
        this.endIndex = endIndex2;
        boolean z = true;
        if (startIndex2 >= 0) {
            if (endIndex2 >= 0) {
                if (!(endIndex2 < startIndex2 ? false : z)) {
                    throw new IllegalArgumentException(("endIndex should be not less than startIndex, but was " + endIndex2 + " < " + startIndex2).toString());
                }
                return;
            }
            throw new IllegalArgumentException(("endIndex should be non-negative, but is " + endIndex2).toString());
        }
        throw new IllegalArgumentException(("startIndex should be non-negative, but is " + startIndex2).toString());
    }

    private final int getCount() {
        return this.endIndex - this.startIndex;
    }

    public Sequence<T> drop(int n) {
        return n >= getCount() ? SequencesKt.emptySequence() : new SubSequence<>(this.sequence, this.startIndex + n, this.endIndex);
    }

    public Sequence<T> take(int n) {
        if (n >= getCount()) {
            return this;
        }
        Sequence<T> sequence2 = this.sequence;
        int i = this.startIndex;
        return new SubSequence<>(sequence2, i, i + n);
    }

    public Iterator<T> iterator() {
        return new SubSequence$iterator$1(this);
    }
}
