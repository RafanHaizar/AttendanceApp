package kotlin.collections;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010&\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0006\b\u0001\u0010\u0003 \u00012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005H\n¢\u0006\u0002\b\u0006"}, mo113d2 = {"<anonymous>", "", "K", "V", "it", "", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: AbstractMap.kt */
final class AbstractMap$toString$1 extends Lambda implements Function1<Map.Entry<? extends K, ? extends V>, CharSequence> {
    final /* synthetic */ AbstractMap<K, V> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    AbstractMap$toString$1(AbstractMap<K, ? extends V> abstractMap) {
        super(1);
        this.this$0 = abstractMap;
    }

    public final CharSequence invoke(Map.Entry<? extends K, ? extends V> it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return this.this$0.toString(it);
    }
}
