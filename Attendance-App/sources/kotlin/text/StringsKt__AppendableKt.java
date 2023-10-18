package kotlin.text;

import com.itextpdf.forms.xfdf.XfdfConstants;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000:\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u001a5\u0010\u0000\u001a\u0002H\u0001\"\f\b\u0000\u0010\u0001*\u00060\u0002j\u0002`\u0003*\u0002H\u00012\u0016\u0010\u0004\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00060\u0005\"\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007\u001a9\u0010\b\u001a\u00020\t\"\u0004\b\u0000\u0010\u0001*\u00060\u0002j\u0002`\u00032\u0006\u0010\n\u001a\u0002H\u00012\u0014\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u0006\u0018\u00010\fH\u0000¢\u0006\u0002\u0010\r\u001a\u0015\u0010\u000e\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u0003H\b\u001a\u001d\u0010\u000e\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0004\u001a\u00020\u000fH\b\u001a\u001f\u0010\u000e\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0006H\b\u001a7\u0010\u0010\u001a\u0002H\u0001\"\f\b\u0000\u0010\u0001*\u00060\u0002j\u0002`\u0003*\u0002H\u00012\u0006\u0010\u0004\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0007¢\u0006\u0002\u0010\u0014¨\u0006\u0015"}, mo113d2 = {"append", "T", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "value", "", "", "(Ljava/lang/Appendable;[Ljava/lang/CharSequence;)Ljava/lang/Appendable;", "appendElement", "", "element", "transform", "Lkotlin/Function1;", "(Ljava/lang/Appendable;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "appendLine", "", "appendRange", "startIndex", "", "endIndex", "(Ljava/lang/Appendable;Ljava/lang/CharSequence;II)Ljava/lang/Appendable;", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/text/StringsKt")
/* compiled from: Appendable.kt */
class StringsKt__AppendableKt {
    public static final <T extends Appendable> T appendRange(T $this$appendRange, CharSequence value, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$appendRange, "<this>");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        T append = $this$appendRange.append(value, startIndex, endIndex);
        Intrinsics.checkNotNull(append, "null cannot be cast to non-null type T of kotlin.text.StringsKt__AppendableKt.appendRange");
        return append;
    }

    public static final <T extends Appendable> T append(T $this$append, CharSequence... value) {
        Intrinsics.checkNotNullParameter($this$append, "<this>");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        for (CharSequence item : value) {
            $this$append.append(item);
        }
        return $this$append;
    }

    private static final Appendable appendLine(Appendable $this$appendLine) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        Appendable append = $this$appendLine.append(10);
        Intrinsics.checkNotNullExpressionValue(append, "append('\\n')");
        return append;
    }

    private static final Appendable appendLine(Appendable $this$appendLine, CharSequence value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        Appendable append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        Appendable append2 = append.append(10);
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    private static final Appendable appendLine(Appendable $this$appendLine, char value) {
        Intrinsics.checkNotNullParameter($this$appendLine, "<this>");
        Appendable append = $this$appendLine.append(value);
        Intrinsics.checkNotNullExpressionValue(append, "append(value)");
        Appendable append2 = append.append(10);
        Intrinsics.checkNotNullExpressionValue(append2, "append('\\n')");
        return append2;
    }

    public static final <T> void appendElement(Appendable $this$appendElement, T element, Function1<? super T, ? extends CharSequence> transform) {
        Intrinsics.checkNotNullParameter($this$appendElement, "<this>");
        if (transform != null) {
            $this$appendElement.append((CharSequence) transform.invoke(element));
            return;
        }
        if (element == null ? true : element instanceof CharSequence) {
            $this$appendElement.append((CharSequence) element);
        } else if (element instanceof Character) {
            $this$appendElement.append(((Character) element).charValue());
        } else {
            $this$appendElement.append(String.valueOf(element));
        }
    }
}
