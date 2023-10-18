package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CancellableContinuation;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0004"}, mo113d2 = {"<anonymous>", "", "it", "", "invoke"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Produce.kt */
final class ProduceKt$awaitClose$4$1 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ CancellableContinuation<Unit> $cont;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ProduceKt$awaitClose$4$1(CancellableContinuation<? super Unit> cancellableContinuation) {
        super(1);
        this.$cont = cancellableContinuation;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable it) {
        Result.Companion companion = Result.Companion;
        this.$cont.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
    }
}
