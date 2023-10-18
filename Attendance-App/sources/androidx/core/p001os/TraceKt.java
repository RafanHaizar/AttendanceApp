package androidx.core.p001os;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a-\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0005H\bø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0007"}, mo113d2 = {"trace", "T", "sectionName", "", "block", "Lkotlin/Function0;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: androidx.core.os.TraceKt */
/* compiled from: Trace.kt */
public final class TraceKt {
    @Deprecated(message = "Use androidx.tracing.Trace instead", replaceWith = @ReplaceWith(expression = "trace(sectionName)", imports = {"androidx.tracing.trace"}))
    public static final <T> T trace(String sectionName, Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(sectionName, "sectionName");
        Intrinsics.checkNotNullParameter(block, "block");
        TraceCompat.beginSection(sectionName);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            TraceCompat.endSection();
            InlineMarker.finallyEnd(1);
        }
    }
}
