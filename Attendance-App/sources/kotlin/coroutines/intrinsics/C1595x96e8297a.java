package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0007H\u0014ø\u0001\u0000¢\u0006\u0002\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\t"}, mo113d2 = {"kotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$1", "Lkotlin/coroutines/jvm/internal/RestrictedContinuationImpl;", "label", "", "invokeSuspend", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$1 */
/* compiled from: IntrinsicsJvm.kt */
public final class C1595x96e8297a extends RestrictedContinuationImpl {
    final /* synthetic */ Function1<Continuation<? super T>, Object> $block;
    private int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1595x96e8297a(Continuation<? super T> $completion, Function1<? super Continuation<? super T>, ? extends Object> $block2) {
        super($completion);
        this.$block = $block2;
        Intrinsics.checkNotNull($completion, "null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
    }

    /* access modifiers changed from: protected */
    public Object invokeSuspend(Object result) {
        switch (this.label) {
            case 0:
                this.label = 1;
                ResultKt.throwOnFailure(result);
                return this.$block.invoke(this);
            case 1:
                this.label = 2;
                ResultKt.throwOnFailure(result);
                return result;
            default:
                throw new IllegalStateException("This coroutine had already completed".toString());
        }
    }
}
