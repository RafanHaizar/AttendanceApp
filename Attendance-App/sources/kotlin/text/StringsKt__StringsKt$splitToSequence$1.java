package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo113d2 = {"<anonymous>", "", "it", "Lkotlin/ranges/IntRange;", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Strings.kt */
final class StringsKt__StringsKt$splitToSequence$1 extends Lambda implements Function1<IntRange, String> {
    final /* synthetic */ CharSequence $this_splitToSequence;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    StringsKt__StringsKt$splitToSequence$1(CharSequence charSequence) {
        super(1);
        this.$this_splitToSequence = charSequence;
    }

    public final String invoke(IntRange it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return StringsKt.substring(this.$this_splitToSequence, it);
    }
}
