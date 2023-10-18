package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo113d2 = {"<anonymous>", "Lkotlin/text/MatchGroup;", "it", "", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Regex.kt */
final class MatcherMatchResult$groups$1$iterator$1 extends Lambda implements Function1<Integer, MatchGroup> {
    final /* synthetic */ MatcherMatchResult$groups$1 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    MatcherMatchResult$groups$1$iterator$1(MatcherMatchResult$groups$1 matcherMatchResult$groups$1) {
        super(1);
        this.this$0 = matcherMatchResult$groups$1;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        return invoke(((Number) p1).intValue());
    }

    public final MatchGroup invoke(int it) {
        return this.this$0.get(it);
    }
}
