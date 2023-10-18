package kotlin;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0002H\u00022\u0006\u0010\u0004\u001a\u0002H\u0003H\u0004¢\u0006\u0002\u0010\u0005\u001a\"\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\b0\u0001\u001a(\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u0014\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\b0\t¨\u0006\n"}, mo113d2 = {"to", "Lkotlin/Pair;", "A", "B", "that", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;", "toList", "", "T", "Lkotlin/Triple;", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Tuples.kt */
public final class TuplesKt {
    /* renamed from: to */
    public static final <A, B> Pair<A, B> m281to(A $this$to, B that) {
        return new Pair<>($this$to, that);
    }

    public static final <T> List<T> toList(Pair<? extends T, ? extends T> $this$toList) {
        Intrinsics.checkNotNullParameter($this$toList, "<this>");
        return CollectionsKt.listOf($this$toList.getFirst(), $this$toList.getSecond());
    }

    public static final <T> List<T> toList(Triple<? extends T, ? extends T, ? extends T> $this$toList) {
        Intrinsics.checkNotNullParameter($this$toList, "<this>");
        return CollectionsKt.listOf($this$toList.getFirst(), $this$toList.getSecond(), $this$toList.getThird());
    }
}
