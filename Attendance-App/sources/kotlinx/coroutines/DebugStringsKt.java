package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.DispatchedContinuation;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0007\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\bH\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0005\u001a\u00020\u0001*\u00020\u00028@X\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\t"}, mo113d2 = {"classSimpleName", "", "", "getClassSimpleName", "(Ljava/lang/Object;)Ljava/lang/String;", "hexAddress", "getHexAddress", "toDebugString", "Lkotlin/coroutines/Continuation;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: DebugStrings.kt */
public final class DebugStringsKt {
    public static final String getHexAddress(Object $this$hexAddress) {
        return Integer.toHexString(System.identityHashCode($this$hexAddress));
    }

    public static final String toDebugString(Continuation<?> $this$toDebugString) {
        String str;
        if ($this$toDebugString instanceof DispatchedContinuation) {
            return $this$toDebugString.toString();
        }
        try {
            Result.Companion companion = Result.Companion;
            Continuation $this$toDebugString_u24lambda_u2d0 = $this$toDebugString;
            str = Result.m1345constructorimpl($this$toDebugString_u24lambda_u2d0 + '@' + getHexAddress($this$toDebugString_u24lambda_u2d0));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            str = Result.m1345constructorimpl(ResultKt.createFailure(th));
        }
        Throwable r2 = Result.m1348exceptionOrNullimpl(str);
        if (r2 != null) {
            Throwable th2 = r2;
            str = $this$toDebugString.getClass().getName() + '@' + getHexAddress($this$toDebugString);
        }
        return (String) str;
    }

    public static final String getClassSimpleName(Object $this$classSimpleName) {
        return $this$classSimpleName.getClass().getSimpleName();
    }
}
