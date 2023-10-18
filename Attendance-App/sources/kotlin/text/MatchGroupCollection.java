package kotlin.text;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(mo112d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001J\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0005H¦\u0002¨\u0006\u0006"}, mo113d2 = {"Lkotlin/text/MatchGroupCollection;", "", "Lkotlin/text/MatchGroup;", "get", "index", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: MatchResult.kt */
public interface MatchGroupCollection extends Collection<MatchGroup>, KMappedMarker {
    MatchGroup get(int i);
}
