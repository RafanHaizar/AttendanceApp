package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.debug.internal.DebugProbesImpl;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, mo113d2 = {"<anonymous>", "R", "", "owner", "Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "invoke", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)Ljava/lang/Object;", "kotlinx/coroutines/debug/internal/DebugProbesImpl$dumpCoroutinesInfoImpl$1$3"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* renamed from: kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpDebuggerInfo$$inlined$dumpCoroutinesInfoImpl$1 */
/* compiled from: DebugProbesImpl.kt */
final class C0010xee7de334 extends Lambda implements Function1<DebugProbesImpl.CoroutineOwner<?>, DebuggerInfo> {
    public C0010xee7de334() {
        super(1);
    }

    public final DebuggerInfo invoke(DebugProbesImpl.CoroutineOwner<?> owner) {
        CoroutineContext context;
        if (DebugProbesImpl.INSTANCE.isFinished(owner) || (context = owner.info.getContext()) == null) {
            return null;
        }
        return new DebuggerInfo(owner.info, context);
    }
}
