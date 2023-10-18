package kotlin.p002io;

import java.io.Closeable;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0001\u001aK\u0010\u0005\u001a\u0002H\u0006\"\n\b\u0000\u0010\u0007*\u0004\u0018\u00010\u0002\"\u0004\b\u0001\u0010\u0006*\u0002H\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\tH\bø\u0001\u0000ø\u0001\u0001\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\n\u0002\u000f\n\u0005\b20\u0001\n\u0006\b\u0011(\u000b0\u0001¨\u0006\f"}, mo113d2 = {"closeFinally", "", "Ljava/io/Closeable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "(Ljava/io/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Requires newer compiler version to be inlined correctly.", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: kotlin.io.CloseableKt */
/* compiled from: Closeable.kt */
public final class CloseableKt {
    private static final <T extends Closeable, R> R use(T $this$use, Function1<? super T, ? extends R> block) {
        Throwable exception;
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            R invoke = block.invoke($this$use);
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                closeFinally($this$use, (Throwable) null);
            } else if ($this$use != null) {
                $this$use.close();
            }
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Throwable e) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                closeFinally($this$use, exception);
            } else if ($this$use != null) {
                try {
                    $this$use.close();
                } catch (Throwable th) {
                }
            }
            InlineMarker.finallyEnd(1);
            throw e;
        }
    }

    public static final void closeFinally(Closeable $this$closeFinally, Throwable cause) {
        if ($this$closeFinally == null) {
            return;
        }
        if (cause == null) {
            $this$closeFinally.close();
            return;
        }
        try {
            $this$closeFinally.close();
        } catch (Throwable closeException) {
            ExceptionsKt.addSuppressed(cause, closeException);
        }
    }
}
