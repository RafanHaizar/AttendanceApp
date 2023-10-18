package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(mo112d1 = {"\u0000\u0015\n\u0000\n\u0002\u0010(\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\b\u0010\t\u001a\u00020\nH\u0002J\t\u0010\u000b\u001a\u00020\nH\u0002J\u000e\u0010\f\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\rR\"\u0010\u0002\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0001X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0004¨\u0006\u000e"}, mo113d2 = {"kotlin/sequences/FlatteningSequence$iterator$1", "", "itemIterator", "getItemIterator", "()Ljava/util/Iterator;", "setItemIterator", "(Ljava/util/Iterator;)V", "iterator", "getIterator", "ensureItemIterator", "", "hasNext", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Sequences.kt */
public final class FlatteningSequence$iterator$1 implements Iterator<E>, KMappedMarker {
    private Iterator<? extends E> itemIterator;
    private final Iterator<T> iterator;
    final /* synthetic */ FlatteningSequence<T, R, E> this$0;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    FlatteningSequence$iterator$1(FlatteningSequence<T, R, E> $receiver) {
        this.this$0 = $receiver;
        this.iterator = $receiver.sequence.iterator();
    }

    public final Iterator<T> getIterator() {
        return this.iterator;
    }

    public final Iterator<E> getItemIterator() {
        return this.itemIterator;
    }

    public final void setItemIterator(Iterator<? extends E> it) {
        this.itemIterator = it;
    }

    public E next() {
        if (ensureItemIterator()) {
            Iterator<? extends E> it = this.itemIterator;
            Intrinsics.checkNotNull(it);
            return it.next();
        }
        throw new NoSuchElementException();
    }

    public boolean hasNext() {
        return ensureItemIterator();
    }

    private final boolean ensureItemIterator() {
        Iterator<? extends E> it = this.itemIterator;
        if (it != null && !it.hasNext()) {
            this.itemIterator = null;
        }
        while (this.itemIterator == null) {
            if (!this.iterator.hasNext()) {
                return false;
            }
            Iterator nextItemIterator = (Iterator) this.this$0.iterator.invoke(this.this$0.transformer.invoke(this.iterator.next()));
            if (nextItemIterator.hasNext()) {
                this.itemIterator = nextItemIterator;
                return true;
            }
        }
        return true;
    }
}
