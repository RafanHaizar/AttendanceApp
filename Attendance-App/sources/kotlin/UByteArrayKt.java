package kotlin;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010\u0007\u001a\u001f\u0010\b\u001a\u00020\u00012\n\u0010\t\u001a\u00020\u0001\"\u00020\u0006H\bø\u0001\u0001¢\u0006\u0004\b\n\u0010\u000b\u0002\u000b\n\u0005\b20\u0001\n\u0002\b\u0019¨\u0006\f"}, mo113d2 = {"UByteArray", "Lkotlin/UByteArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/UByte;", "(ILkotlin/jvm/functions/Function1;)[B", "ubyteArrayOf", "elements", "ubyteArrayOf-GBYM_sE", "([B)[B", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: UByteArray.kt */
public final class UByteArrayKt {
    private static final byte[] UByteArray(int size, Function1<? super Integer, UByte> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = init.invoke(Integer.valueOf(i)).m1412unboximpl();
        }
        return UByteArray.m1415constructorimpl(bArr);
    }

    /* renamed from: ubyteArrayOf-GBYM_sE  reason: not valid java name */
    private static final byte[] m1431ubyteArrayOfGBYM_sE(byte... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return elements;
    }
}
