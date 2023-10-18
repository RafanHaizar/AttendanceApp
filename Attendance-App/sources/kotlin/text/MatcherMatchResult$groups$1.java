package kotlin.text;

import com.itextpdf.forms.xfdf.XfdfConstants;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.AbstractCollection;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt;

@Metadata(mo112d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002J\u0013\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\t\u001a\u00020\u0005H\u0002J\u0013\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\u0011\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000fH\u0002R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, mo113d2 = {"kotlin/text/MatcherMatchResult$groups$1", "Lkotlin/text/MatchNamedGroupCollection;", "Lkotlin/collections/AbstractCollection;", "Lkotlin/text/MatchGroup;", "size", "", "getSize", "()I", "get", "index", "name", "", "isEmpty", "", "iterator", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Regex.kt */
public final class MatcherMatchResult$groups$1 extends AbstractCollection<MatchGroup> implements MatchNamedGroupCollection {
    final /* synthetic */ MatcherMatchResult this$0;

    MatcherMatchResult$groups$1(MatcherMatchResult $receiver) {
        this.this$0 = $receiver;
    }

    public final /* bridge */ boolean contains(Object element) {
        if (!(element == null ? true : element instanceof MatchGroup)) {
            return false;
        }
        return contains((MatchGroup) element);
    }

    public /* bridge */ boolean contains(MatchGroup element) {
        return super.contains(element);
    }

    public int getSize() {
        return this.this$0.getMatchResult().groupCount() + 1;
    }

    public boolean isEmpty() {
        return false;
    }

    public Iterator<MatchGroup> iterator() {
        return SequencesKt.map(CollectionsKt.asSequence(CollectionsKt.getIndices(this)), new MatcherMatchResult$groups$1$iterator$1(this)).iterator();
    }

    public MatchGroup get(int index) {
        IntRange range = RegexKt.range(this.this$0.getMatchResult(), index);
        if (range.getStart().intValue() < 0) {
            return null;
        }
        String group = this.this$0.getMatchResult().group(index);
        Intrinsics.checkNotNullExpressionValue(group, "matchResult.group(index)");
        return new MatchGroup(group, range);
    }

    public MatchGroup get(String name) {
        Intrinsics.checkNotNullParameter(name, XfdfConstants.NAME);
        return PlatformImplementationsKt.IMPLEMENTATIONS.getMatchResultNamedGroup(this.this$0.getMatchResult(), name);
    }
}
