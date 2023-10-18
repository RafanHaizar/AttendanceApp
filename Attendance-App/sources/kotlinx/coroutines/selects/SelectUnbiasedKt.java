package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;

@Metadata(mo112d1 = {"\u0000\u0018\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a8\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u001f\b\u0004\u0010\u0002\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006HHø\u0001\u0000¢\u0006\u0002\u0010\u0007\u0002\u0004\n\u0002\b\u0019¨\u0006\b"}, mo113d2 = {"selectUnbiased", "R", "builder", "Lkotlin/Function1;", "Lkotlinx/coroutines/selects/SelectBuilder;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SelectUnbiased.kt */
public final class SelectUnbiasedKt {
    public static final <R> Object selectUnbiased(Function1<? super SelectBuilder<? super R>, Unit> builder, Continuation<? super R> $completion) {
        UnbiasedSelectBuilderImpl scope = new UnbiasedSelectBuilderImpl($completion);
        try {
            builder.invoke(scope);
        } catch (Throwable e) {
            scope.handleBuilderException(e);
        }
        Object initSelectResult = scope.initSelectResult();
        if (initSelectResult == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return initSelectResult;
    }

    private static final <R> Object selectUnbiased$$forInline(Function1<? super SelectBuilder<? super R>, Unit> builder, Continuation<? super R> $completion) {
        InlineMarker.mark(0);
        UnbiasedSelectBuilderImpl scope = new UnbiasedSelectBuilderImpl($completion);
        try {
            builder.invoke(scope);
        } catch (Throwable e) {
            scope.handleBuilderException(e);
        }
        Object initSelectResult = scope.initSelectResult();
        if (initSelectResult == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        InlineMarker.mark(1);
        return initSelectResult;
    }
}
