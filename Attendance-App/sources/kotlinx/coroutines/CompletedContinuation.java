package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\b\u0018\u00002\u00020\u0001BZ\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012%\b\u0002\u0010\u0005\u001a\u001f\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\u000eJ\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0004HÆ\u0003J&\u0010\u0015\u001a\u001f\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0007HÆ\u0003J`\u0010\u0018\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042%\b\u0002\u0010\u0005\u001a\u001f\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\u001a\u0010\u001d\u001a\u00020\u000b2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001f2\u0006\u0010\n\u001a\u00020\u0007J\t\u0010 \u001a\u00020!HÖ\u0001R\u0012\u0010\r\u001a\u0004\u0018\u00010\u00078\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0012\u0010\f\u001a\u0004\u0018\u00010\u00018\u0006X\u0004¢\u0006\u0002\n\u0000R-\u0010\u0005\u001a\u001f\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00018\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, mo113d2 = {"Lkotlinx/coroutines/CompletedContinuation;", "", "result", "cancelHandler", "Lkotlinx/coroutines/CancelHandler;", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "idempotentResume", "cancelCause", "(Ljava/lang/Object;Lkotlinx/coroutines/CancelHandler;Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelled", "", "getCancelled", "()Z", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "invokeHandlers", "cont", "Lkotlinx/coroutines/CancellableContinuationImpl;", "toString", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CancellableContinuationImpl.kt */
final class CompletedContinuation {
    public final Throwable cancelCause;
    public final CancelHandler cancelHandler;
    public final Object idempotentResume;
    public final Function1<Throwable, Unit> onCancellation;
    public final Object result;

    public static /* synthetic */ CompletedContinuation copy$default(CompletedContinuation completedContinuation, Object obj, CancelHandler cancelHandler2, Function1<Throwable, Unit> function1, Object obj2, Throwable th, int i, Object obj3) {
        if ((i & 1) != 0) {
            obj = completedContinuation.result;
        }
        if ((i & 2) != 0) {
            cancelHandler2 = completedContinuation.cancelHandler;
        }
        CancelHandler cancelHandler3 = cancelHandler2;
        if ((i & 4) != 0) {
            function1 = completedContinuation.onCancellation;
        }
        Function1<Throwable, Unit> function12 = function1;
        if ((i & 8) != 0) {
            obj2 = completedContinuation.idempotentResume;
        }
        Object obj4 = obj2;
        if ((i & 16) != 0) {
            th = completedContinuation.cancelCause;
        }
        return completedContinuation.copy(obj, cancelHandler3, function12, obj4, th);
    }

    public final Object component1() {
        return this.result;
    }

    public final CancelHandler component2() {
        return this.cancelHandler;
    }

    public final Function1<Throwable, Unit> component3() {
        return this.onCancellation;
    }

    public final Object component4() {
        return this.idempotentResume;
    }

    public final Throwable component5() {
        return this.cancelCause;
    }

    public final CompletedContinuation copy(Object obj, CancelHandler cancelHandler2, Function1<? super Throwable, Unit> function1, Object obj2, Throwable th) {
        return new CompletedContinuation(obj, cancelHandler2, function1, obj2, th);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompletedContinuation)) {
            return false;
        }
        CompletedContinuation completedContinuation = (CompletedContinuation) obj;
        return Intrinsics.areEqual(this.result, completedContinuation.result) && Intrinsics.areEqual((Object) this.cancelHandler, (Object) completedContinuation.cancelHandler) && Intrinsics.areEqual((Object) this.onCancellation, (Object) completedContinuation.onCancellation) && Intrinsics.areEqual(this.idempotentResume, completedContinuation.idempotentResume) && Intrinsics.areEqual((Object) this.cancelCause, (Object) completedContinuation.cancelCause);
    }

    public int hashCode() {
        Object obj = this.result;
        int i = 0;
        int hashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        CancelHandler cancelHandler2 = this.cancelHandler;
        int hashCode2 = (hashCode + (cancelHandler2 == null ? 0 : cancelHandler2.hashCode())) * 31;
        Function1<Throwable, Unit> function1 = this.onCancellation;
        int hashCode3 = (hashCode2 + (function1 == null ? 0 : function1.hashCode())) * 31;
        Object obj2 = this.idempotentResume;
        int hashCode4 = (hashCode3 + (obj2 == null ? 0 : obj2.hashCode())) * 31;
        Throwable th = this.cancelCause;
        if (th != null) {
            i = th.hashCode();
        }
        return hashCode4 + i;
    }

    public String toString() {
        return "CompletedContinuation(result=" + this.result + ", cancelHandler=" + this.cancelHandler + ", onCancellation=" + this.onCancellation + ", idempotentResume=" + this.idempotentResume + ", cancelCause=" + this.cancelCause + ')';
    }

    public CompletedContinuation(Object result2, CancelHandler cancelHandler2, Function1<? super Throwable, Unit> onCancellation2, Object idempotentResume2, Throwable cancelCause2) {
        this.result = result2;
        this.cancelHandler = cancelHandler2;
        this.onCancellation = onCancellation2;
        this.idempotentResume = idempotentResume2;
        this.cancelCause = cancelCause2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ CompletedContinuation(java.lang.Object r8, kotlinx.coroutines.CancelHandler r9, kotlin.jvm.functions.Function1 r10, java.lang.Object r11, java.lang.Throwable r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r7 = this;
            r14 = r13 & 2
            r0 = 0
            if (r14 == 0) goto L_0x0007
            r3 = r0
            goto L_0x0008
        L_0x0007:
            r3 = r9
        L_0x0008:
            r9 = r13 & 4
            if (r9 == 0) goto L_0x000e
            r4 = r0
            goto L_0x000f
        L_0x000e:
            r4 = r10
        L_0x000f:
            r9 = r13 & 8
            if (r9 == 0) goto L_0x0015
            r5 = r0
            goto L_0x0016
        L_0x0015:
            r5 = r11
        L_0x0016:
            r9 = r13 & 16
            if (r9 == 0) goto L_0x001c
            r6 = r0
            goto L_0x001d
        L_0x001c:
            r6 = r12
        L_0x001d:
            r1 = r7
            r2 = r8
            r1.<init>(r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.CompletedContinuation.<init>(java.lang.Object, kotlinx.coroutines.CancelHandler, kotlin.jvm.functions.Function1, java.lang.Object, java.lang.Throwable, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final boolean getCancelled() {
        return this.cancelCause != null;
    }

    public final void invokeHandlers(CancellableContinuationImpl<?> cont, Throwable cause) {
        CancelHandler it = this.cancelHandler;
        if (it != null) {
            cont.callCancelHandler(it, cause);
        }
        Function1 it2 = this.onCancellation;
        if (it2 != null) {
            cont.callOnCancellation(it2, cause);
        }
    }
}
