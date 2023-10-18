package kotlin.text;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010(\n\u0002\u0010\f\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, mo113d2 = {"<anonymous>", "", "", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: _Strings.kt */
final class StringsKt___StringsKt$withIndex$1 extends Lambda implements Function0<Iterator<? extends Character>> {
    final /* synthetic */ CharSequence $this_withIndex;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    StringsKt___StringsKt$withIndex$1(CharSequence charSequence) {
        super(0);
        this.$this_withIndex = charSequence;
    }

    public final Iterator<Character> invoke() {
        return StringsKt.iterator(this.$this_withIndex);
    }
}
