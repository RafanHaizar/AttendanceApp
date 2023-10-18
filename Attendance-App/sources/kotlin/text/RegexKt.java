package kotlin.text;

import com.itextpdf.svg.SvgConstants;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

@Metadata(mo112d1 = {"\u0000>\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\b\u001a\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0010H\u0002\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0002¨\u0006\u0014"}, mo113d2 = {"fromInt", "", "T", "Lkotlin/text/FlagEnum;", "", "value", "", "findNext", "Lkotlin/text/MatchResult;", "Ljava/util/regex/Matcher;", "from", "input", "", "matchEntire", "range", "Lkotlin/ranges/IntRange;", "Ljava/util/regex/MatchResult;", "groupIndex", "toInt", "", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Regex.kt */
public final class RegexKt {
    /* access modifiers changed from: private */
    public static final int toInt(Iterable<? extends FlagEnum> $this$toInt) {
        int accumulator$iv = 0;
        for (FlagEnum option : $this$toInt) {
            accumulator$iv |= option.getValue();
        }
        return accumulator$iv;
    }

    private static final /* synthetic */ <T extends Enum<T> & FlagEnum> Set<T> fromInt(int value) {
        Intrinsics.reifiedOperationMarker(4, SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO);
        Class<Enum> cls = Enum.class;
        Class cls2 = cls;
        EnumSet<E> allOf = EnumSet.allOf(cls);
        EnumSet $this$fromInt_u24lambda_u2d1 = allOf;
        Intrinsics.checkNotNullExpressionValue($this$fromInt_u24lambda_u2d1, "");
        Intrinsics.needClassReification();
        CollectionsKt.retainAll($this$fromInt_u24lambda_u2d1, new RegexKt$fromInt$1$1(value));
        Set<T> unmodifiableSet = Collections.unmodifiableSet(allOf);
        Intrinsics.checkNotNullExpressionValue(unmodifiableSet, "unmodifiableSet(EnumSet.…mask == it.value }\n    })");
        Set set = unmodifiableSet;
        return unmodifiableSet;
    }

    /* access modifiers changed from: private */
    public static final MatchResult findNext(Matcher $this$findNext, int from, CharSequence input) {
        if (!$this$findNext.find(from)) {
            return null;
        }
        return new MatcherMatchResult($this$findNext, input);
    }

    /* access modifiers changed from: private */
    public static final MatchResult matchEntire(Matcher $this$matchEntire, CharSequence input) {
        if (!$this$matchEntire.matches()) {
            return null;
        }
        return new MatcherMatchResult($this$matchEntire, input);
    }

    /* access modifiers changed from: private */
    public static final IntRange range(MatchResult $this$range) {
        return RangesKt.until($this$range.start(), $this$range.end());
    }

    /* access modifiers changed from: private */
    public static final IntRange range(MatchResult $this$range, int groupIndex) {
        return RangesKt.until($this$range.start(groupIndex), $this$range.end(groupIndex));
    }
}
