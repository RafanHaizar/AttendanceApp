package kotlin.text;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\r\n\u0002\b\u0002\u0010\u0000\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0002H\n¢\u0006\u0002\b\u0005"}, mo113d2 = {"<anonymous>", "Lkotlin/Pair;", "", "", "currentIndex", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Strings.kt */
final class StringsKt__StringsKt$rangesDelimitedBy$2 extends Lambda implements Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>> {
    final /* synthetic */ List<String> $delimitersList;
    final /* synthetic */ boolean $ignoreCase;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    StringsKt__StringsKt$rangesDelimitedBy$2(List<String> list, boolean z) {
        super(2);
        this.$delimitersList = list;
        this.$ignoreCase = z;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
        return invoke((CharSequence) p1, ((Number) p2).intValue());
    }

    public final Pair<Integer, Integer> invoke(CharSequence $this$$receiver, int currentIndex) {
        Intrinsics.checkNotNullParameter($this$$receiver, "$this$$receiver");
        Pair it = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt($this$$receiver, this.$delimitersList, currentIndex, this.$ignoreCase, false);
        if (it != null) {
            return TuplesKt.m281to(it.getFirst(), Integer.valueOf(((String) it.getSecond()).length()));
        }
        return null;
    }
}
