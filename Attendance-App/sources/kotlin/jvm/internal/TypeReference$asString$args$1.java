package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KTypeProjection;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo113d2 = {"<anonymous>", "", "it", "Lkotlin/reflect/KTypeProjection;", "invoke"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: TypeReference.kt */
final class TypeReference$asString$args$1 extends Lambda implements Function1<KTypeProjection, CharSequence> {
    final /* synthetic */ TypeReference this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    TypeReference$asString$args$1(TypeReference typeReference) {
        super(1);
        this.this$0 = typeReference;
    }

    public final CharSequence invoke(KTypeProjection it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return this.this$0.asString(it);
    }
}
