package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\u0010\u0000\u001a\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0002\u0018\u00010\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "T", "R", "invoke", "()[Ljava/lang/Object;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 176)
/* compiled from: Zip.kt */
final class FlowKt__ZipKt$combine$5$1 extends Lambda implements Function0<T[]> {
    final /* synthetic */ Flow<T>[] $flows;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlowKt__ZipKt$combine$5$1(Flow<? extends T>[] flowArr) {
        super(0);
        this.$flows = flowArr;
    }

    public final T[] invoke() {
        int length = this.$flows.length;
        Intrinsics.reifiedOperationMarker(0, "T?");
        return new Object[length];
    }
}
