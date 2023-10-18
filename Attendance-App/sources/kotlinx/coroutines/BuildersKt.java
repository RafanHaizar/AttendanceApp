package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"kotlinx/coroutines/BuildersKt__BuildersKt", "kotlinx/coroutines/BuildersKt__Builders_commonKt"}, mo114k = 4, mo115mv = {1, 6, 0}, mo117xi = 48)
public final class BuildersKt {
    public static final <T> Deferred<T> async(CoroutineScope $this$async, CoroutineContext context, CoroutineStart start, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        return BuildersKt__Builders_commonKt.async($this$async, context, start, block);
    }

    public static final <T> Object invoke(CoroutineDispatcher $this$invoke, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        return BuildersKt__Builders_commonKt.invoke($this$invoke, block, $completion);
    }

    public static final Job launch(CoroutineScope $this$launch, CoroutineContext context, CoroutineStart start, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
        return BuildersKt__Builders_commonKt.launch($this$launch, context, start, block);
    }

    public static final <T> T runBlocking(CoroutineContext context, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) throws InterruptedException {
        return BuildersKt__BuildersKt.runBlocking(context, block);
    }

    public static final <T> Object withContext(CoroutineContext context, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        return BuildersKt__Builders_commonKt.withContext(context, block, $completion);
    }
}
