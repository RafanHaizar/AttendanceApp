package kotlin.text;

import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b¨\u0006\u0003"}, mo113d2 = {"toRegex", "Lkotlin/text/Regex;", "Ljava/util/regex/Pattern;", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/text/StringsKt")
/* compiled from: RegexExtensionsJVM.kt */
class StringsKt__RegexExtensionsJVMKt extends StringsKt__IndentKt {
    private static final Regex toRegex(Pattern $this$toRegex) {
        Intrinsics.checkNotNullParameter($this$toRegex, "<this>");
        return new Regex($this$toRegex);
    }
}
