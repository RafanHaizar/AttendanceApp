package androidx.core.util;

import android.util.SparseArray;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000@\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010(\n\u0000\u001a!\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u0001H\n\u001a!\u0010\t\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u0001H\b\u001a&\u0010\n\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u000b\u001a\u0002H\u0002H\b¢\u0006\u0002\u0010\f\u001aT\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000326\u0010\u000f\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\b\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u000e0\u0010H\bø\u0001\u0000\u001a.\u0010\u0013\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u0002H\u0002H\b¢\u0006\u0002\u0010\u0015\u001a7\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0017H\bø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001a\u0019\u0010\u0019\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\b\u001a\u0019\u0010\u001a\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\b\u001a\u0016\u0010\u001b\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a-\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0002\u001a$\u0010\u001f\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a+\u0010 \u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u0002H\u0002¢\u0006\u0002\u0010!\u001a.\u0010\"\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u0002H\u0002H\n¢\u0006\u0002\u0010#\u001a\u001c\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00020%\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\"\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0002\u0007\n\u0005\b20\u0001¨\u0006&"}, mo113d2 = {"size", "", "T", "Landroid/util/SparseArray;", "getSize", "(Landroid/util/SparseArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "(Landroid/util/SparseArray;Ljava/lang/Object;)Z", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "(Landroid/util/SparseArray;ILjava/lang/Object;)Ljava/lang/Object;", "getOrElse", "Lkotlin/Function0;", "(Landroid/util/SparseArray;ILkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "(Landroid/util/SparseArray;ILjava/lang/Object;)Z", "set", "(Landroid/util/SparseArray;ILjava/lang/Object;)V", "valueIterator", "", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: SparseArray.kt */
public final class SparseArrayKt {
    public static final <T> int getSize(SparseArray<T> $this$size) {
        Intrinsics.checkNotNullParameter($this$size, "<this>");
        return $this$size.size();
    }

    public static final <T> boolean contains(SparseArray<T> $this$contains, int key) {
        Intrinsics.checkNotNullParameter($this$contains, "<this>");
        return $this$contains.indexOfKey(key) >= 0;
    }

    public static final <T> void set(SparseArray<T> $this$set, int key, T value) {
        Intrinsics.checkNotNullParameter($this$set, "<this>");
        $this$set.put(key, value);
    }

    public static final <T> SparseArray<T> plus(SparseArray<T> $this$plus, SparseArray<T> other) {
        Intrinsics.checkNotNullParameter($this$plus, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        SparseArray sparseArray = new SparseArray($this$plus.size() + other.size());
        putAll(sparseArray, $this$plus);
        putAll(sparseArray, other);
        return sparseArray;
    }

    public static final <T> boolean containsKey(SparseArray<T> $this$containsKey, int key) {
        Intrinsics.checkNotNullParameter($this$containsKey, "<this>");
        return $this$containsKey.indexOfKey(key) >= 0;
    }

    public static final <T> boolean containsValue(SparseArray<T> $this$containsValue, T value) {
        Intrinsics.checkNotNullParameter($this$containsValue, "<this>");
        return $this$containsValue.indexOfValue(value) >= 0;
    }

    public static final <T> T getOrDefault(SparseArray<T> $this$getOrDefault, int key, T defaultValue) {
        Intrinsics.checkNotNullParameter($this$getOrDefault, "<this>");
        T t = $this$getOrDefault.get(key);
        return t == null ? defaultValue : t;
    }

    public static final <T> T getOrElse(SparseArray<T> $this$getOrElse, int key, Function0<? extends T> defaultValue) {
        Intrinsics.checkNotNullParameter($this$getOrElse, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        T t = $this$getOrElse.get(key);
        return t == null ? defaultValue.invoke() : t;
    }

    public static final <T> boolean isEmpty(SparseArray<T> $this$isEmpty) {
        Intrinsics.checkNotNullParameter($this$isEmpty, "<this>");
        return $this$isEmpty.size() == 0;
    }

    public static final <T> boolean isNotEmpty(SparseArray<T> $this$isNotEmpty) {
        Intrinsics.checkNotNullParameter($this$isNotEmpty, "<this>");
        return $this$isNotEmpty.size() != 0;
    }

    public static final <T> boolean remove(SparseArray<T> $this$remove, int key, T value) {
        Intrinsics.checkNotNullParameter($this$remove, "<this>");
        int index = $this$remove.indexOfKey(key);
        if (index < 0 || !Intrinsics.areEqual((Object) value, (Object) $this$remove.valueAt(index))) {
            return false;
        }
        $this$remove.removeAt(index);
        return true;
    }

    public static final <T> void putAll(SparseArray<T> $this$putAll, SparseArray<T> other) {
        Intrinsics.checkNotNullParameter($this$putAll, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        SparseArray $this$forEach$iv = other;
        int size = $this$forEach$iv.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            $this$putAll.put($this$forEach$iv.keyAt(index$iv), $this$forEach$iv.valueAt(index$iv));
        }
    }

    public static final <T> void forEach(SparseArray<T> $this$forEach, Function2<? super Integer, ? super T, Unit> action) {
        Intrinsics.checkNotNullParameter($this$forEach, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int size = $this$forEach.size();
        for (int index = 0; index < size; index++) {
            action.invoke(Integer.valueOf($this$forEach.keyAt(index)), $this$forEach.valueAt(index));
        }
    }

    public static final <T> IntIterator keyIterator(SparseArray<T> $this$keyIterator) {
        Intrinsics.checkNotNullParameter($this$keyIterator, "<this>");
        return new SparseArrayKt$keyIterator$1($this$keyIterator);
    }

    public static final <T> Iterator<T> valueIterator(SparseArray<T> $this$valueIterator) {
        Intrinsics.checkNotNullParameter($this$valueIterator, "<this>");
        return new SparseArrayKt$valueIterator$1($this$valueIterator);
    }
}
