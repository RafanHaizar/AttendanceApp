package kotlin;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001e\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\nR\u0014\u0010\u0002\u001a\u00020\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b¸\u0006\u0000"}, mo113d2 = {"kotlin/coroutines/ContinuationKt$Continuation$1", "Lkotlin/coroutines/Continuation;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: kotlin.DeepRecursiveScopeImpl$crossFunctionCompletion$$inlined$Continuation$1 */
/* compiled from: Continuation.kt */
public final class C1590x3ce6e4bb implements Continuation<Object> {
    final /* synthetic */ Continuation $cont$inlined;
    final /* synthetic */ CoroutineContext $context;
    final /* synthetic */ Function3 $currentFunction$inlined;
    final /* synthetic */ DeepRecursiveScopeImpl this$0;

    public C1590x3ce6e4bb(CoroutineContext $context2, DeepRecursiveScopeImpl deepRecursiveScopeImpl, Function3 function3, Continuation continuation) {
        this.$context = $context2;
        this.this$0 = deepRecursiveScopeImpl;
        this.$currentFunction$inlined = function3;
        this.$cont$inlined = continuation;
    }

    public CoroutineContext getContext() {
        return this.$context;
    }

    public void resumeWith(Object result) {
        this.this$0.function = this.$currentFunction$inlined;
        this.this$0.cont = this.$cont$inlined;
        this.this$0.result = result;
    }
}
