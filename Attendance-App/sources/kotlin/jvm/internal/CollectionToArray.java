package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0007¢\u0006\u0004\b\t\u0010\n\u001a5\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001H\u0007¢\u0006\u0004\b\t\u0010\f\u001a~\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0014\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u000f2\u001a\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u00112(\u0010\u0012\u001a$\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u0013H\b¢\u0006\u0002\u0010\u0014\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000¨\u0006\u0015"}, mo113d2 = {"EMPTY", "", "", "[Ljava/lang/Object;", "MAX_SIZE", "", "collectionToArray", "collection", "", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", "a", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "toArrayImpl", "empty", "Lkotlin/Function0;", "alloc", "Lkotlin/Function1;", "trim", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)[Ljava/lang/Object;", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: CollectionToArray.kt */
public final class CollectionToArray {
    private static final Object[] EMPTY = new Object[0];
    private static final int MAX_SIZE = 2147483645;

    public static final Object[] toArray(Collection<?> collection) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        int size$iv = collection.size();
        if (size$iv == 0) {
            return EMPTY;
        }
        Iterator iter$iv = collection.iterator();
        if (!iter$iv.hasNext()) {
            return EMPTY;
        }
        Object[] result$iv = new Object[size$iv];
        int newSize$iv = 0;
        while (true) {
            int i$iv = newSize$iv + 1;
            result$iv[newSize$iv] = iter$iv.next();
            if (i$iv >= result$iv.length) {
                if (!iter$iv.hasNext()) {
                    return result$iv;
                }
                int newSize$iv2 = ((i$iv * 3) + 1) >>> 1;
                if (newSize$iv2 <= i$iv) {
                    if (i$iv < MAX_SIZE) {
                        newSize$iv2 = MAX_SIZE;
                    } else {
                        throw new OutOfMemoryError();
                    }
                }
                Object[] copyOf = Arrays.copyOf(result$iv, newSize$iv2);
                Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(result, newSize)");
                result$iv = copyOf;
                newSize$iv = i$iv;
            } else if (!iter$iv.hasNext()) {
                Object[] copyOf2 = Arrays.copyOf(result$iv, i$iv);
                Intrinsics.checkNotNullExpressionValue(copyOf2, "copyOf(result, size)");
                return copyOf2;
            } else {
                newSize$iv = i$iv;
            }
        }
    }

    public static final Object[] toArray(Collection<?> collection, Object[] a) {
        Object[] objArr;
        Object[] result$iv;
        Intrinsics.checkNotNullParameter(collection, "collection");
        if (a != null) {
            int size$iv = collection.size();
            if (size$iv != 0) {
                Iterator iter$iv = collection.iterator();
                if (iter$iv.hasNext()) {
                    int size = size$iv;
                    if (size <= a.length) {
                        objArr = a;
                    } else {
                        Object newInstance = Array.newInstance(a.getClass().getComponentType(), size);
                        Intrinsics.checkNotNull(newInstance, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                        objArr = (Object[]) newInstance;
                    }
                    Object[] result$iv2 = objArr;
                    int newSize$iv = 0;
                    while (true) {
                        int i$iv = newSize$iv + 1;
                        result$iv2[newSize$iv] = iter$iv.next();
                        if (i$iv >= result$iv2.length) {
                            if (!iter$iv.hasNext()) {
                                return result$iv2;
                            }
                            int newSize$iv2 = ((i$iv * 3) + 1) >>> 1;
                            if (newSize$iv2 <= i$iv) {
                                if (i$iv < MAX_SIZE) {
                                    newSize$iv2 = MAX_SIZE;
                                } else {
                                    throw new OutOfMemoryError();
                                }
                            }
                            Object[] copyOf = Arrays.copyOf(result$iv2, newSize$iv2);
                            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(result, newSize)");
                            result$iv2 = copyOf;
                            newSize$iv = i$iv;
                        } else if (!iter$iv.hasNext()) {
                            Object[] result = result$iv2;
                            int size2 = i$iv;
                            if (result == a) {
                                a[size2] = null;
                                result$iv = a;
                            } else {
                                result$iv = Arrays.copyOf(result, size2);
                                Intrinsics.checkNotNullExpressionValue(result$iv, "copyOf(result, size)");
                            }
                            return result$iv;
                        } else {
                            newSize$iv = i$iv;
                        }
                    }
                } else if (a.length > 0) {
                    a[0] = null;
                }
            } else if (a.length > 0) {
                a[0] = null;
            }
            return a;
        }
        throw new NullPointerException();
    }

    private static final Object[] toArrayImpl(Collection<?> collection, Function0<Object[]> empty, Function1<? super Integer, Object[]> alloc, Function2<? super Object[], ? super Integer, Object[]> trim) {
        int size = collection.size();
        if (size == 0) {
            return empty.invoke();
        }
        Iterator iter = collection.iterator();
        if (!iter.hasNext()) {
            return empty.invoke();
        }
        Object[] result = alloc.invoke(Integer.valueOf(size));
        int newSize = 0;
        while (true) {
            int i = newSize + 1;
            result[newSize] = iter.next();
            if (i >= result.length) {
                if (!iter.hasNext()) {
                    return result;
                }
                int newSize2 = ((i * 3) + 1) >>> 1;
                if (newSize2 <= i) {
                    if (i < MAX_SIZE) {
                        newSize2 = MAX_SIZE;
                    } else {
                        throw new OutOfMemoryError();
                    }
                }
                Object[] copyOf = Arrays.copyOf(result, newSize2);
                Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(result, newSize)");
                result = copyOf;
                newSize = i;
            } else if (!iter.hasNext()) {
                return trim.invoke(result, Integer.valueOf(i));
            } else {
                newSize = i;
            }
        }
    }
}
