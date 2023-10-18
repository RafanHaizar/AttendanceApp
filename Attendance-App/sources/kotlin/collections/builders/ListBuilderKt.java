package kotlin.collections.builders;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a!\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000¢\u0006\u0002\u0010\u0005\u001a+\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0001\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00012\u0006\u0010\b\u001a\u00020\u0004H\u0000¢\u0006\u0002\u0010\t\u001a%\u0010\n\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\f\u001a\u00020\u0004H\u0000¢\u0006\u0002\u0010\r\u001a-\u0010\u000e\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0000¢\u0006\u0002\u0010\u0011\u001a9\u0010\u0012\u001a\u00020\u0013\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0017H\u0002¢\u0006\u0002\u0010\u0018\u001a-\u0010\u0019\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010\u001a\u001a/\u0010\u001b\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010\u001d¨\u0006\u001e"}, mo113d2 = {"arrayOfUninitializedElements", "", "E", "size", "", "(I)[Ljava/lang/Object;", "copyOfUninitializedElements", "T", "newSize", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "resetAt", "", "index", "([Ljava/lang/Object;I)V", "resetRange", "fromIndex", "toIndex", "([Ljava/lang/Object;II)V", "subarrayContentEquals", "", "offset", "length", "other", "", "([Ljava/lang/Object;IILjava/util/List;)Z", "subarrayContentHashCode", "([Ljava/lang/Object;II)I", "subarrayContentToString", "", "([Ljava/lang/Object;II)Ljava/lang/String;", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ListBuilder.kt */
public final class ListBuilderKt {
    public static final <E> E[] arrayOfUninitializedElements(int size) {
        if (size >= 0) {
            return new Object[size];
        }
        throw new IllegalArgumentException("capacity must be non-negative.".toString());
    }

    /* access modifiers changed from: private */
    public static final <T> String subarrayContentToString(T[] $this$subarrayContentToString, int offset, int length) {
        StringBuilder sb = new StringBuilder((length * 3) + 2);
        sb.append("[");
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append($this$subarrayContentToString[offset + i]);
        }
        sb.append("]");
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "sb.toString()");
        return sb2;
    }

    /* access modifiers changed from: private */
    public static final <T> int subarrayContentHashCode(T[] $this$subarrayContentHashCode, int offset, int length) {
        int result = 1;
        for (int i = 0; i < length; i++) {
            Object nextElement = $this$subarrayContentHashCode[offset + i];
            result = (result * 31) + (nextElement != null ? nextElement.hashCode() : 0);
        }
        return result;
    }

    /* access modifiers changed from: private */
    public static final <T> boolean subarrayContentEquals(T[] $this$subarrayContentEquals, int offset, int length, List<?> other) {
        if (length != other.size()) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!Intrinsics.areEqual((Object) $this$subarrayContentEquals[offset + i], (Object) other.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static final <T> T[] copyOfUninitializedElements(T[] $this$copyOfUninitializedElements, int newSize) {
        Intrinsics.checkNotNullParameter($this$copyOfUninitializedElements, "<this>");
        T[] copyOf = Arrays.copyOf($this$copyOfUninitializedElements, newSize);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        Intrinsics.checkNotNull(copyOf, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.builders.ListBuilderKt.copyOfUninitializedElements>");
        return copyOf;
    }

    public static final <E> void resetAt(E[] $this$resetAt, int index) {
        Intrinsics.checkNotNullParameter($this$resetAt, "<this>");
        $this$resetAt[index] = null;
    }

    public static final <E> void resetRange(E[] $this$resetRange, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$resetRange, "<this>");
        for (int index = fromIndex; index < toIndex; index++) {
            resetAt($this$resetRange, index);
        }
    }
}
