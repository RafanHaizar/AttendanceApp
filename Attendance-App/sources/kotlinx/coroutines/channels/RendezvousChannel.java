package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012 \u0010\u0003\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0014\u0010\u000b\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\f\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\t8DX\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\n¨\u0006\u000e"}, mo113d2 = {"Lkotlinx/coroutines/channels/RendezvousChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: RendezvousChannel.kt */
public class RendezvousChannel<E> extends AbstractChannel<E> {
    public RendezvousChannel(Function1<? super E, Unit> onUndeliveredElement) {
        super(onUndeliveredElement);
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferAlwaysEmpty() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferEmpty() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferAlwaysFull() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferFull() {
        return true;
    }
}
