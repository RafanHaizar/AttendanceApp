package androidx.core.p001os;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

@Metadata(mo112d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo113d2 = {"<anonymous>", "", "run"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 176)
/* renamed from: androidx.core.os.HandlerKt$postAtTime$runnable$1 */
/* compiled from: Handler.kt */
public final class HandlerKt$postAtTime$runnable$1 implements Runnable {
    final /* synthetic */ Function0<Unit> $action;

    public HandlerKt$postAtTime$runnable$1(Function0<Unit> function0) {
        this.$action = function0;
    }

    public final void run() {
        this.$action.invoke();
    }
}
