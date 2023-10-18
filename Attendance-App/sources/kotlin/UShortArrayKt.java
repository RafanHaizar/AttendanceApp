package kotlin;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010\u0007\u001a\u001f\u0010\b\u001a\u00020\u00012\n\u0010\t\u001a\u00020\u0001\"\u00020\u0006H\bø\u0001\u0001¢\u0006\u0004\b\n\u0010\u000b\u0002\u000b\n\u0005\b20\u0001\n\u0002\b\u0019¨\u0006\f"}, mo113d2 = {"UShortArray", "Lkotlin/UShortArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/UShort;", "(ILkotlin/jvm/functions/Function1;)[S", "ushortArrayOf", "elements", "ushortArrayOf-rL5Bavg", "([S)[S", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: UShortArray.kt */
public final class UShortArrayKt {
    private static final short[] UShortArray(int size, Function1<? super Integer, UShort> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        short[] sArr = new short[size];
        for (int i = 0; i < size; i++) {
            sArr[i] = init.invoke(Integer.valueOf(i)).m1672unboximpl();
        }
        return UShortArray.m1675constructorimpl(sArr);
    }

    /* renamed from: ushortArrayOf-rL5Bavg  reason: not valid java name */
    private static final short[] m1691ushortArrayOfrL5Bavg(short... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return elements;
    }
}
