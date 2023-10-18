package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.StartedLazily$command$1;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.StartedLazily$command$1$1", mo130f = "SharingStarted.kt", mo131i = {}, mo132l = {158}, mo133m = "emit", mo134n = {}, mo135s = {})
/* compiled from: SharingStarted.kt */
final class StartedLazily$command$1$1$emit$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ StartedLazily$command$1.C00851<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    StartedLazily$command$1$1$emit$1(StartedLazily$command$1.C00851<? super T> r1, Continuation<? super StartedLazily$command$1$1$emit$1> continuation) {
        super(continuation);
        this.this$0 = r1;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(0, (Continuation<? super Unit>) this);
    }
}
