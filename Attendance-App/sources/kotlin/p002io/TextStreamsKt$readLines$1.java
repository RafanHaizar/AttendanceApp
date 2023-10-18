package kotlin.p002io;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo113d2 = {"<anonymous>", "", "it", "", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: kotlin.io.TextStreamsKt$readLines$1 */
/* compiled from: ReadWrite.kt */
final class TextStreamsKt$readLines$1 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ ArrayList<String> $result;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    TextStreamsKt$readLines$1(ArrayList<String> arrayList) {
        super(1);
        this.$result = arrayList;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((String) p1);
        return Unit.INSTANCE;
    }

    public final void invoke(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this.$result.add(it);
    }
}
