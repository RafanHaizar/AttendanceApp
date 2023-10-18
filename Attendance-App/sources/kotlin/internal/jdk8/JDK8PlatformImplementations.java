package kotlin.internal.jdk8;

import com.itextpdf.forms.xfdf.XfdfConstants;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.internal.jdk7.JDK7PlatformImplementations;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.jdk8.PlatformThreadLocalRandom;
import kotlin.ranges.IntRange;
import kotlin.text.MatchGroup;

@Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0010\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, mo113d2 = {"Lkotlin/internal/jdk8/JDK8PlatformImplementations;", "Lkotlin/internal/jdk7/JDK7PlatformImplementations;", "()V", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "kotlin-stdlib-jdk8"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: JDK8PlatformImplementations.kt */
public class JDK8PlatformImplementations extends JDK7PlatformImplementations {
    public MatchGroup getMatchResultNamedGroup(MatchResult matchResult, String name) {
        Intrinsics.checkNotNullParameter(matchResult, "matchResult");
        Intrinsics.checkNotNullParameter(name, XfdfConstants.NAME);
        Matcher matcher = matchResult instanceof Matcher ? (Matcher) matchResult : null;
        if (matcher != null) {
            IntRange range = new IntRange(matcher.start(name), matcher.end(name) - 1);
            if (range.getStart().intValue() < 0) {
                return null;
            }
            String group = matcher.group(name);
            Intrinsics.checkNotNullExpressionValue(group, "matcher.group(name)");
            return new MatchGroup(group, range);
        }
        throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
    }

    public Random defaultPlatformRandom() {
        return new PlatformThreadLocalRandom();
    }
}
