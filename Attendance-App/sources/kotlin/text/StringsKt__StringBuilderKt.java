package kotlin.text;

import com.itextpdf.forms.xfdf.XfdfConstants;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000F\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\r\n\u0000\u001a>\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\b\tH\bø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a6\u0010\u0000\u001a\u00020\u00012\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\b\tH\bø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\u001f\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\b\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\f0\u000e\"\u0004\u0018\u00010\f¢\u0006\u0002\u0010\u000f\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000e\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0010\u001a\u0015\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u0007H\b\u001a\u001f\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\r\u001a\u0004\u0018\u00010\fH\b\u001a\u001d\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\r\u001a\u00020\u0012H\b\u001a\u001d\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\r\u001a\u00020\u0013H\b\u001a\u001d\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\r\u001a\u00020\u0014H\b\u001a\u001f\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\r\u001a\u0004\u0018\u00010\u0015H\b\u001a\u001f\u0010\u0011\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\b\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0016"}, mo113d2 = {"buildString", "", "capacity", "", "builderAction", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "append", "obj", "", "value", "", "(Ljava/lang/StringBuilder;[Ljava/lang/Object;)Ljava/lang/StringBuilder;", "(Ljava/lang/StringBuilder;[Ljava/lang/String;)Ljava/lang/StringBuilder;", "appendLine", "", "", "", "", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/text/StringsKt")
/* compiled from: StringBuilder.kt */
class StringsKt__StringBuilderKt extends StringsKt__StringBuilderJVMKt {
    @Deprecated(level = DeprecationLevel.WARNING, message = "Use append(value: Any?) instead", replaceWith = @ReplaceWith(expression = "append(value = obj)", imports = {}))
    private static final StringBuilder append(StringBuilder $this$append, Object obj) {
        Intrinsics.checkNotNullParameter($this$append, "<this>");
        StringBuilder append = $this$append.append(obj);
        Intrinsics.checkNotNullExpressionValue(append, "this.append(obj)");
        return append;
    }

    private static final String buildString(Function1<? super StringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        StringBuilder sb = new StringBuilder();
        builderAction.invoke(sb);
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private static final String buildString(int capacity, Function1<? super StringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        StringBuilder sb = new StringBuilder(capacity);
        builderAction.invoke(sb);
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    public static final StringBuilder append(StringBuilder $this$append, String... value) {
        Intrinsics.checkNotNullParameter($this$append, "<this>");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        for (String item : value) {
            $this$append.append(item);
        }
        return $this$append;
    }

    public static final StringBuilder append(StringBuilder $this$append, Object... value) {
        Intrinsics.checkNotNullParameter($this$append, "<this>");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        for (Object item : value) {
            $this$append.append(item);
        }
        return $this$append;
    }

    private static final StringBuilder appendLine(StringBuilder $this$appendLine) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        StringBuilder append = $this$appendLine.append(10);
        Intrinsics.checkNotNullExpressionValue(append, "append('\\n')");
        return append;
    }

    private static final StringBuilder appendLine(StringBuilder $this$appendLine, CharSequence value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        StringBuilder append2 = append.append(10);
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    private static final StringBuilder appendLine(StringBuilder $this$appendLine, String value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        StringBuilder append2 = append.append(10);
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    private static final StringBuilder appendLine(StringBuilder $this$appendLine, Object value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        StringBuilder append2 = append.append(10);
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    private static final StringBuilder appendLine(StringBuilder $this$appendLine, char[] value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        StringBuilder append2 = append.append(10);
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    private static final StringBuilder appendLine(StringBuilder $this$appendLine, char value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        StringBuilder append2 = append.append(10);
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    private static final StringBuilder appendLine(StringBuilder $this$appendLine, boolean value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        StringBuilder append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        StringBuilder append2 = append.append(10);
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }
}
