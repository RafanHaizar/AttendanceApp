package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.debug.internal.DebugProbesImpl;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, mo113d2 = {"<anonymous>", "R", "", "owner", "Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "invoke", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: DebugProbesImpl.kt */
final class DebugProbesImpl$dumpCoroutinesInfoImpl$1$3 extends Lambda implements Function1<DebugProbesImpl.CoroutineOwner<?>, R> {
    final /* synthetic */ Function2<DebugProbesImpl.CoroutineOwner<?>, CoroutineContext, R> $create;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DebugProbesImpl$dumpCoroutinesInfoImpl$1$3(Function2<? super DebugProbesImpl.CoroutineOwner<?>, ? super CoroutineContext, ? extends R> function2) {
        super(1);
        this.$create = function2;
    }

    public final R invoke(DebugProbesImpl.CoroutineOwner<?> owner) {
        CoroutineContext context;
        if (!DebugProbesImpl.INSTANCE.isFinished(owner) && (context = owner.info.getContext()) != null) {
            return this.$create.invoke(owner, context);
        }
        return null;
    }
}
