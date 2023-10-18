package kotlin.collections;

import com.itextpdf.svg.SvgConstants;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(mo112d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b'\u0018\u0000 \u000b*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u000bB\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"}, mo113d2 = {"Lkotlin/collections/AbstractSet;", "E", "Lkotlin/collections/AbstractCollection;", "", "()V", "equals", "", "other", "", "hashCode", "", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: AbstractSet.kt */
public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E>, KMappedMarker {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);

    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    protected AbstractSet() {
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Set)) {
            return false;
        }
        return Companion.setEquals$kotlin_stdlib(this, (Set) other);
    }

    public int hashCode() {
        return Companion.unorderedHashCode$kotlin_stdlib(this);
    }

    @Metadata(mo112d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0010\u001e\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\bJ\u0019\u0010\t\u001a\u00020\n2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0000¢\u0006\u0002\b\f¨\u0006\r"}, mo113d2 = {"Lkotlin/collections/AbstractSet$Companion;", "", "()V", "setEquals", "", "c", "", "other", "setEquals$kotlin_stdlib", "unorderedHashCode", "", "", "unorderedHashCode$kotlin_stdlib", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: AbstractSet.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int unorderedHashCode$kotlin_stdlib(Collection<?> c) {
            Intrinsics.checkNotNullParameter(c, SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO);
            int hashCode = 0;
            Iterator<?> it = c.iterator();
            while (it.hasNext()) {
                Object element = it.next();
                hashCode += element != null ? element.hashCode() : 0;
            }
            return hashCode;
        }

        public final boolean setEquals$kotlin_stdlib(Set<?> c, Set<?> other) {
            Intrinsics.checkNotNullParameter(c, SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO);
            Intrinsics.checkNotNullParameter(other, "other");
            if (c.size() != other.size()) {
                return false;
            }
            return c.containsAll(other);
        }
    }
}
