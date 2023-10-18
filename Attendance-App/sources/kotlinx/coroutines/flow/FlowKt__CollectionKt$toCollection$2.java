package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendFunction;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0010\b\u0001\u0010\u0003*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0006\u0010\u0007"}, mo113d2 = {"<anonymous>", "", "T", "C", "", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Collection.kt */
final class FlowKt__CollectionKt$toCollection$2<T> implements FlowCollector, SuspendFunction {
    final /* synthetic */ C $destination;

    FlowKt__CollectionKt$toCollection$2(C c) {
        this.$destination = c;
    }

    public final Object emit(T value, Continuation<? super Unit> $completion) {
        this.$destination.add(value);
        return Unit.INSTANCE;
    }
}
