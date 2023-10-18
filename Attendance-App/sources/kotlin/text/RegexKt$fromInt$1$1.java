package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u000e\u0010\u0005\u001a\n \u0006*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0007\u0010\b"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlin/text/FlagEnum;", "", "it", "kotlin.jvm.PlatformType", "invoke", "(Ljava/lang/Enum;)Ljava/lang/Boolean;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Regex.kt */
final class RegexKt$fromInt$1$1 extends Lambda implements Function1<T, Boolean> {
    final /* synthetic */ int $value;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RegexKt$fromInt$1$1(int i) {
        super(1);
        this.$value = i;
    }

    public final Boolean invoke(T it) {
        return Boolean.valueOf((this.$value & ((FlagEnum) it).getMask()) == ((FlagEnum) it).getValue());
    }
}
