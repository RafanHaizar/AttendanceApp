package kotlin.text.jdk8;

import com.itextpdf.forms.xfdf.XfdfConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchNamedGroupCollection;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002¨\u0006\u0005"}, mo113d2 = {"get", "Lkotlin/text/MatchGroup;", "Lkotlin/text/MatchGroupCollection;", "name", "", "kotlin-stdlib-jdk8"}, mo114k = 2, mo115mv = {1, 6, 0}, mo116pn = "kotlin.text", mo117xi = 48)
/* compiled from: RegexExtensions.kt */
public final class RegexExtensionsJDK8Kt {
    public static final MatchGroup get(MatchGroupCollection $this$get, String name) {
        Intrinsics.checkNotNullParameter($this$get, "<this>");
        Intrinsics.checkNotNullParameter(name, XfdfConstants.NAME);
        MatchNamedGroupCollection namedGroups = $this$get instanceof MatchNamedGroupCollection ? (MatchNamedGroupCollection) $this$get : null;
        if (namedGroups != null) {
            return namedGroups.get(name);
        }
        throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
    }
}
