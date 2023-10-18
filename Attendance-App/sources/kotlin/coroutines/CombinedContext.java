package kotlin.coroutines;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

@Metadata(mo112d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0001\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001!B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0000H\u0002J\u0013\u0010\f\u001a\u00020\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J5\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u00102\u0006\u0010\u0011\u001a\u0002H\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u00100\u0013H\u0016¢\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0018H\u0002¢\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00020\u00012\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, mo113d2 = {"Lkotlin/coroutines/CombinedContext;", "Lkotlin/coroutines/CoroutineContext;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "left", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext$Element;)V", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "writeReplace", "Serialized", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: CoroutineContextImpl.kt */
public final class CombinedContext implements CoroutineContext, Serializable {
    private final CoroutineContext.Element element;
    private final CoroutineContext left;

    public CombinedContext(CoroutineContext left2, CoroutineContext.Element element2) {
        Intrinsics.checkNotNullParameter(left2, "left");
        Intrinsics.checkNotNullParameter(element2, "element");
        this.left = left2;
        this.element = element2;
    }

    public CoroutineContext plus(CoroutineContext context) {
        return CoroutineContext.DefaultImpls.plus(this, context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: kotlin.coroutines.CoroutineContext} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: kotlin.coroutines.CombinedContext} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <E extends kotlin.coroutines.CoroutineContext.Element> E get(kotlin.coroutines.CoroutineContext.Key<E> r4) {
        /*
            r3 = this;
            java.lang.String r0 = "key"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            r0 = r3
        L_0x0006:
            kotlin.coroutines.CoroutineContext$Element r1 = r0.element
            kotlin.coroutines.CoroutineContext$Element r1 = r1.get(r4)
            if (r1 == 0) goto L_0x0011
            r2 = 0
            return r1
        L_0x0011:
            kotlin.coroutines.CoroutineContext r1 = r0.left
            boolean r2 = r1 instanceof kotlin.coroutines.CombinedContext
            if (r2 == 0) goto L_0x001b
            r0 = r1
            kotlin.coroutines.CombinedContext r0 = (kotlin.coroutines.CombinedContext) r0
            goto L_0x0006
        L_0x001b:
            kotlin.coroutines.CoroutineContext$Element r2 = r1.get(r4)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.coroutines.CombinedContext.get(kotlin.coroutines.CoroutineContext$Key):kotlin.coroutines.CoroutineContext$Element");
    }

    public <R> R fold(R initial, Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
        Intrinsics.checkNotNullParameter(operation, "operation");
        return operation.invoke(this.left.fold(initial, operation), this.element);
    }

    public CoroutineContext minusKey(CoroutineContext.Key<?> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (this.element.get(key) != null) {
            return this.left;
        }
        CoroutineContext newLeft = this.left.minusKey(key);
        if (newLeft == this.left) {
            return this;
        }
        if (newLeft == EmptyCoroutineContext.INSTANCE) {
            return this.element;
        }
        return new CombinedContext(newLeft, this.element);
    }

    private final int size() {
        CombinedContext cur = this;
        int size = 2;
        while (true) {
            CoroutineContext coroutineContext = cur.left;
            CombinedContext combinedContext = coroutineContext instanceof CombinedContext ? (CombinedContext) coroutineContext : null;
            if (combinedContext == null) {
                return size;
            }
            cur = combinedContext;
            size++;
        }
    }

    private final boolean contains(CoroutineContext.Element element2) {
        return Intrinsics.areEqual((Object) get(element2.getKey()), (Object) element2);
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object, kotlin.coroutines.CoroutineContext] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean containsAll(kotlin.coroutines.CombinedContext r4) {
        /*
            r3 = this;
            r0 = r4
        L_0x0001:
            kotlin.coroutines.CoroutineContext$Element r1 = r0.element
            boolean r1 = r3.contains(r1)
            if (r1 != 0) goto L_0x000c
            r1 = 0
            return r1
        L_0x000c:
            kotlin.coroutines.CoroutineContext r1 = r0.left
            boolean r2 = r1 instanceof kotlin.coroutines.CombinedContext
            if (r2 == 0) goto L_0x0016
            r0 = r1
            kotlin.coroutines.CombinedContext r0 = (kotlin.coroutines.CombinedContext) r0
            goto L_0x0001
        L_0x0016:
            java.lang.String r2 = "null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1, r2)
            r2 = r1
            kotlin.coroutines.CoroutineContext$Element r2 = (kotlin.coroutines.CoroutineContext.Element) r2
            boolean r2 = r3.contains(r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.coroutines.CombinedContext.containsAll(kotlin.coroutines.CombinedContext):boolean");
    }

    public boolean equals(Object other) {
        return this == other || ((other instanceof CombinedContext) && ((CombinedContext) other).size() == size() && ((CombinedContext) other).containsAll(this));
    }

    public int hashCode() {
        return this.left.hashCode() + this.element.hashCode();
    }

    public String toString() {
        return '[' + ((String) fold("", CombinedContext$toString$1.INSTANCE)) + ']';
    }

    private final Object writeReplace() {
        int n = size();
        CoroutineContext[] elements = new CoroutineContext[n];
        Ref.IntRef index = new Ref.IntRef();
        fold(Unit.INSTANCE, new CombinedContext$writeReplace$1(elements, index));
        if (index.element == n) {
            return new Serialized(elements);
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    @Metadata(mo112d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \f2\u00060\u0001j\u0002`\u0002:\u0001\fB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, mo113d2 = {"Lkotlin/coroutines/CombinedContext$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "elements", "", "Lkotlin/coroutines/CoroutineContext;", "([Lkotlin/coroutines/CoroutineContext;)V", "getElements", "()[Lkotlin/coroutines/CoroutineContext;", "[Lkotlin/coroutines/CoroutineContext;", "readResolve", "", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: CoroutineContextImpl.kt */
    private static final class Serialized implements Serializable {
        public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
        private static final long serialVersionUID = 0;
        private final CoroutineContext[] elements;

        @Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\u0005"}, mo113d2 = {"Lkotlin/coroutines/CombinedContext$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
        /* compiled from: CoroutineContextImpl.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public Serialized(CoroutineContext[] elements2) {
            Intrinsics.checkNotNullParameter(elements2, "elements");
            this.elements = elements2;
        }

        public final CoroutineContext[] getElements() {
            return this.elements;
        }

        private final Object readResolve() {
            CoroutineContext[] coroutineContextArr = this.elements;
            Object accumulator$iv = EmptyCoroutineContext.INSTANCE;
            for (CoroutineContext p1 : coroutineContextArr) {
                accumulator$iv = ((CoroutineContext) accumulator$iv).plus(p1);
            }
            return accumulator$iv;
        }
    }
}
