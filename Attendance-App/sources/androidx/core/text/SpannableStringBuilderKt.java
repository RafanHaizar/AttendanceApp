package androidx.core.text;

import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0005\u001a%\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a3\u0010\u0007\u001a\u00020\u0004*\u00020\u00042\b\b\u0001\u0010\b\u001a\u00020\t2\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a)\u0010\n\u001a\u00020\u0004*\u00020\u00042\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a3\u0010\b\u001a\u00020\u0004*\u00020\u00042\b\b\u0001\u0010\b\u001a\u00020\t2\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a1\u0010\u000b\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001aB\u0010\u000b\u001a\u00020\u0004*\u00020\u00042\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0\u000f\"\u00020\r2\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a)\u0010\u0011\u001a\u00020\u0004*\u00020\u00042\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a1\u0010\u0012\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00142\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a)\u0010\u0015\u001a\u00020\u0004*\u00020\u00042\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a)\u0010\u0016\u001a\u00020\u0004*\u00020\u00042\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a)\u0010\u0017\u001a\u00020\u0004*\u00020\u00042\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u001a)\u0010\u0018\u001a\u00020\u0004*\u00020\u00042\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0019"}, mo113d2 = {"buildSpannedString", "Landroid/text/SpannedString;", "builderAction", "Lkotlin/Function1;", "Landroid/text/SpannableStringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "backgroundColor", "color", "", "bold", "inSpans", "span", "", "spans", "", "(Landroid/text/SpannableStringBuilder;[Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Landroid/text/SpannableStringBuilder;", "italic", "scale", "proportion", "", "strikeThrough", "subscript", "superscript", "underline", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: SpannableStringBuilder.kt */
public final class SpannableStringBuilderKt {
    public static final SpannedString buildSpannedString(Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builderAction.invoke(builder);
        return new SpannedString(builder);
    }

    public static final SpannableStringBuilder inSpans(SpannableStringBuilder $this$inSpans, Object[] spans, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$inSpans, "<this>");
        Intrinsics.checkNotNullParameter(spans, "spans");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        int start = $this$inSpans.length();
        builderAction.invoke($this$inSpans);
        for (Object span : spans) {
            $this$inSpans.setSpan(span, start, $this$inSpans.length(), 17);
        }
        return $this$inSpans;
    }

    public static final SpannableStringBuilder inSpans(SpannableStringBuilder $this$inSpans, Object span, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$inSpans, "<this>");
        Intrinsics.checkNotNullParameter(span, "span");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        int start = $this$inSpans.length();
        builderAction.invoke($this$inSpans);
        $this$inSpans.setSpan(span, start, $this$inSpans.length(), 17);
        return $this$inSpans;
    }

    public static final SpannableStringBuilder bold(SpannableStringBuilder $this$bold, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$bold, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Object span$iv = new StyleSpan(1);
        SpannableStringBuilder $this$inSpans$iv = $this$bold;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    public static final SpannableStringBuilder italic(SpannableStringBuilder $this$italic, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$italic, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Object span$iv = new StyleSpan(2);
        SpannableStringBuilder $this$inSpans$iv = $this$italic;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    public static final SpannableStringBuilder underline(SpannableStringBuilder $this$underline, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$underline, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Object span$iv = new UnderlineSpan();
        SpannableStringBuilder $this$inSpans$iv = $this$underline;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    public static final SpannableStringBuilder color(SpannableStringBuilder $this$color, int color, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$color, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Object span$iv = new ForegroundColorSpan(color);
        SpannableStringBuilder $this$inSpans$iv = $this$color;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    public static final SpannableStringBuilder backgroundColor(SpannableStringBuilder $this$backgroundColor, int color, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$backgroundColor, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Object span$iv = new BackgroundColorSpan(color);
        SpannableStringBuilder $this$inSpans$iv = $this$backgroundColor;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    public static final SpannableStringBuilder strikeThrough(SpannableStringBuilder $this$strikeThrough, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$strikeThrough, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Object span$iv = new StrikethroughSpan();
        SpannableStringBuilder $this$inSpans$iv = $this$strikeThrough;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    public static final SpannableStringBuilder scale(SpannableStringBuilder $this$scale, float proportion, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$scale, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Object span$iv = new RelativeSizeSpan(proportion);
        SpannableStringBuilder $this$inSpans$iv = $this$scale;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    public static final SpannableStringBuilder superscript(SpannableStringBuilder $this$superscript, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$superscript, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Object span$iv = new SuperscriptSpan();
        SpannableStringBuilder $this$inSpans$iv = $this$superscript;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    public static final SpannableStringBuilder subscript(SpannableStringBuilder $this$subscript, Function1<? super SpannableStringBuilder, Unit> builderAction) {
        Intrinsics.checkNotNullParameter($this$subscript, "<this>");
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        Object span$iv = new SubscriptSpan();
        SpannableStringBuilder $this$inSpans$iv = $this$subscript;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }
}
