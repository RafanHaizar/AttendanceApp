package kotlin;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010\u0007\u001a\u001f\u0010\b\u001a\u00020\u00012\n\u0010\t\u001a\u00020\u0001\"\u00020\u0006H\bø\u0001\u0001¢\u0006\u0004\b\n\u0010\u000b\u0002\u000b\n\u0005\b20\u0001\n\u0002\b\u0019¨\u0006\f"}, mo113d2 = {"ULongArray", "Lkotlin/ULongArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/ULong;", "(ILkotlin/jvm/functions/Function1;)[J", "ulongArrayOf", "elements", "ulongArrayOf-QwZRm1k", "([J)[J", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ULongArray.kt */
public final class ULongArrayKt {
    private static final long[] ULongArray(int size, Function1<? super Integer, ULong> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = init.invoke(Integer.valueOf(i)).m1568unboximpl();
        }
        return ULongArray.m1571constructorimpl(jArr);
    }

    /* renamed from: ulongArrayOf-QwZRm1k  reason: not valid java name */
    private static final long[] m1587ulongArrayOfQwZRm1k(long... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return elements;
    }
}
