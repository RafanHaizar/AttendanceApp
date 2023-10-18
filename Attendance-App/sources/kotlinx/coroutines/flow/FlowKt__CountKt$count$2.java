package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "T", "it", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Count.kt */
final class FlowKt__CountKt$count$2<T> implements FlowCollector, SuspendFunction {

    /* renamed from: $i */
    final /* synthetic */ Ref.IntRef f6$i;

    FlowKt__CountKt$count$2(Ref.IntRef intRef) {
        this.f6$i = intRef;
    }

    public final Object emit(T it, Continuation<? super Unit> $completion) {
        this.f6$i.element++;
        int i = this.f6$i.element;
        return Unit.INSTANCE;
    }
}
