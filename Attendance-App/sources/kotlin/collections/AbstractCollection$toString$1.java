package kotlin.collections;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\n\n\u0000\n\u0002\u0010\r\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002 \u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, mo113d2 = {"<anonymous>", "", "E", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/CharSequence;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: AbstractCollection.kt */
final class AbstractCollection$toString$1 extends Lambda implements Function1<E, CharSequence> {
    final /* synthetic */ AbstractCollection<E> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    AbstractCollection$toString$1(AbstractCollection<? extends E> abstractCollection) {
        super(1);
        this.this$0 = abstractCollection;
    }

    public final CharSequence invoke(E it) {
        return it == this.this$0 ? "(this Collection)" : String.valueOf(it);
    }
}
