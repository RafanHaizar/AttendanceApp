package kotlin.collections.unsigned;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, mo113d2 = {"<anonymous>", "", "Lkotlin/ULong;", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: _UArrays.kt */
final class UArraysKt___UArraysKt$withIndex$2 extends Lambda implements Function0<Iterator<? extends ULong>> {
    final /* synthetic */ long[] $this_withIndex;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    UArraysKt___UArraysKt$withIndex$2(long[] jArr) {
        super(0);
        this.$this_withIndex = jArr;
    }

    public final Iterator<ULong> invoke() {
        return ULongArray.m1580iteratorimpl(this.$this_withIndex);
    }
}
