package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, mo113d2 = {"<anonymous>", "", "E", "<anonymous parameter 0>", "", "invoke"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: OnUndeliveredElement.kt */
final class OnUndeliveredElementKt$bindCancellationFun$1 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ CoroutineContext $context;
    final /* synthetic */ E $element;
    final /* synthetic */ Function1<E, Unit> $this_bindCancellationFun;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    OnUndeliveredElementKt$bindCancellationFun$1(Function1<? super E, Unit> function1, E e, CoroutineContext coroutineContext) {
        super(1);
        this.$this_bindCancellationFun = function1;
        this.$element = e;
        this.$context = coroutineContext;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable $noName_0) {
        OnUndeliveredElementKt.callUndeliveredElement(this.$this_bindCancellationFun, this.$element, this.$context);
    }
}
