package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H@"}, mo113d2 = {"<anonymous>", "", "T", "it", ""}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 176)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.LintKt$retry$1", mo130f = "Lint.kt", mo131i = {}, mo132l = {}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Lint.kt */
public final class LintKt$retry$1 extends SuspendLambda implements Function2<Throwable, Continuation<? super Boolean>, Object> {
    int label;

    public LintKt$retry$1(Continuation<? super LintKt$retry$1> continuation) {
        super(2, continuation);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LintKt$retry$1(continuation);
    }

    public final Object invoke(Throwable th, Continuation<? super Boolean> continuation) {
        return ((LintKt$retry$1) create(th, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                return Boxing.boxBoolean(true);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
