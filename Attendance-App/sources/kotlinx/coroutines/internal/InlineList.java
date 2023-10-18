package kotlinx.coroutines.internal;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;

@JvmInline
@Metadata(mo112d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\b@\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0016\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0002ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0002HÖ\u0003¢\u0006\u0004\b\t\u0010\nJ$\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000eH\b¢\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u0012HÖ\u0001¢\u0006\u0004\b\u0013\u0010\u0014J'\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0016\u001a\u00028\u0000H\u0002ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u0019\u001a\u00020\u001aHÖ\u0001¢\u0006\u0004\b\u001b\u0010\u001cR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0002X\u0004¢\u0006\u0002\n\u0000\u0001\u0003\u0001\u0004\u0018\u00010\u0002ø\u0001\u0000\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u001d"}, mo113d2 = {"Lkotlinx/coroutines/internal/InlineList;", "E", "", "holder", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "equals", "", "other", "equals-impl", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "forEachReversed", "", "action", "Lkotlin/Function1;", "forEachReversed-impl", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "hashCode", "", "hashCode-impl", "(Ljava/lang/Object;)I", "plus", "element", "plus-FjFbRPM", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "toString-impl", "(Ljava/lang/Object;)Ljava/lang/String;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: InlineList.kt */
public final class InlineList<E> {
    private final Object holder;

    /* renamed from: box-impl  reason: not valid java name */
    public static final /* synthetic */ InlineList m1286boximpl(Object obj) {
        return new InlineList(obj);
    }

    /* renamed from: constructor-impl  reason: not valid java name */
    public static <E> Object m1287constructorimpl(Object obj) {
        return obj;
    }

    /* renamed from: equals-impl  reason: not valid java name */
    public static boolean m1289equalsimpl(Object obj, Object obj2) {
        return (obj2 instanceof InlineList) && Intrinsics.areEqual(obj, ((InlineList) obj2).m1295unboximpl());
    }

    /* renamed from: equals-impl0  reason: not valid java name */
    public static final boolean m1290equalsimpl0(Object obj, Object obj2) {
        return Intrinsics.areEqual(obj, obj2);
    }

    /* renamed from: hashCode-impl  reason: not valid java name */
    public static int m1292hashCodeimpl(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    /* renamed from: toString-impl  reason: not valid java name */
    public static String m1294toStringimpl(Object obj) {
        return "InlineList(holder=" + obj + ')';
    }

    public boolean equals(Object obj) {
        return m1289equalsimpl(this.holder, obj);
    }

    public int hashCode() {
        return m1292hashCodeimpl(this.holder);
    }

    public String toString() {
        return m1294toStringimpl(this.holder);
    }

    /* renamed from: unbox-impl  reason: not valid java name */
    public final /* synthetic */ Object m1295unboximpl() {
        return this.holder;
    }

    private /* synthetic */ InlineList(Object holder2) {
        this.holder = holder2;
    }

    /* renamed from: constructor-impl$default  reason: not valid java name */
    public static /* synthetic */ Object m1288constructorimpl$default(Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            obj = null;
        }
        return m1287constructorimpl(obj);
    }

    /* renamed from: plus-FjFbRPM  reason: not valid java name */
    public static final Object m1293plusFjFbRPM(Object arg0, E element) {
        if (DebugKt.getASSERTIONS_ENABLED() && ((element instanceof List) ^ 1) == 0) {
            throw new AssertionError();
        } else if (arg0 == null) {
            return m1287constructorimpl(element);
        } else {
            if (!(arg0 instanceof ArrayList)) {
                ArrayList list = new ArrayList(4);
                list.add(arg0);
                list.add(element);
                return m1287constructorimpl(list);
            } else if (arg0 != null) {
                ((ArrayList) arg0).add(element);
                return m1287constructorimpl(arg0);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
            }
        }
    }

    /* renamed from: forEachReversed-impl  reason: not valid java name */
    public static final void m1291forEachReversedimpl(Object arg0, Function1<? super E, Unit> action) {
        if (arg0 != null) {
            if (!(arg0 instanceof ArrayList)) {
                action.invoke(arg0);
            } else if (arg0 != null) {
                ArrayList list = (ArrayList) arg0;
                int size = list.size() - 1;
                if (size >= 0) {
                    do {
                        int i = size;
                        size--;
                        action.invoke(list.get(i));
                    } while (size >= 0);
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }");
            }
        }
    }
}
