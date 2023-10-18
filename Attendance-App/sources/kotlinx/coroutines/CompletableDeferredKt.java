package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;

@Metadata(mo112d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001f\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0004\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u001a,\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\nø\u0001\u0000¢\u0006\u0002\u0010\u000b\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, mo113d2 = {"CompletableDeferred", "Lkotlinx/coroutines/CompletableDeferred;", "T", "value", "(Ljava/lang/Object;)Lkotlinx/coroutines/CompletableDeferred;", "parent", "Lkotlinx/coroutines/Job;", "completeWith", "", "result", "Lkotlin/Result;", "(Lkotlinx/coroutines/CompletableDeferred;Ljava/lang/Object;)Z", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CompletableDeferred.kt */
public final class CompletableDeferredKt {
    public static final <T> boolean completeWith(CompletableDeferred<T> $this$completeWith, Object result) {
        Throwable it = Result.m1348exceptionOrNullimpl(result);
        return it == null ? $this$completeWith.complete(result) : $this$completeWith.completeExceptionally(it);
    }

    public static final <T> CompletableDeferred<T> CompletableDeferred(Job parent) {
        return new CompletableDeferredImpl<>(parent);
    }

    public static /* synthetic */ CompletableDeferred CompletableDeferred$default(Job job, int i, Object obj) {
        if ((i & 1) != 0) {
            job = null;
        }
        return CompletableDeferred(job);
    }

    public static final <T> CompletableDeferred<T> CompletableDeferred(T value) {
        CompletableDeferredImpl $this$CompletableDeferred_u24lambda_u2d2 = new CompletableDeferredImpl((Job) null);
        $this$CompletableDeferred_u24lambda_u2d2.complete(value);
        return $this$CompletableDeferred_u24lambda_u2d2;
    }
}
