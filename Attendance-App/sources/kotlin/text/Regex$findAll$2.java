package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Regex.kt */
/* synthetic */ class Regex$findAll$2 extends FunctionReferenceImpl implements Function1<MatchResult, MatchResult> {
    public static final Regex$findAll$2 INSTANCE = new Regex$findAll$2();

    Regex$findAll$2() {
        super(1, MatchResult.class, "next", "next()Lkotlin/text/MatchResult;", 0);
    }

    public final MatchResult invoke(MatchResult p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return p0.next();
    }
}
