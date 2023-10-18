package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0010\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0002H\u0000\u001as\u0010\u0003\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00022D\u0010\u0005\u001a@\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0002\u0012\u0015\u0012\u0013\u0018\u00010\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006¢\u0006\u0002\b\r2\b\u0010\n\u001a\u0004\u0018\u00010\u0007H@ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001aj\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00112D\u0010\u0005\u001a@\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0002\u0012\u0015\u0012\u0013\u0018\u00010\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aS\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00112-\u0010\u0005\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0014¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001aS\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00112-\u0010\u0005\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0014¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001as\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0011\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00040\u00112D\b\u0005\u0010\u0017\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u0002\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006¢\u0006\u0002\b\rH\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001as\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0011\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00040\u00112D\b\u0005\u0010\u0017\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u0002\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006¢\u0006\u0002\b\rH\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, mo113d2 = {"ensureActive", "", "Lkotlinx/coroutines/flow/FlowCollector;", "invokeSafely", "T", "action", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", "cause", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "invokeSafely$FlowKt__EmittersKt", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/jvm/functions/Function3;Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCompletion", "Lkotlinx/coroutines/flow/Flow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "onEmpty", "Lkotlin/Function2;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "onStart", "transform", "R", "value", "unsafeTransform", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Emitters.kt */
final /* synthetic */ class FlowKt__EmittersKt {
    public static final <T, R> Flow<R> transform(Flow<? extends T> $this$transform, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return FlowKt.flow(new FlowKt__EmittersKt$transform$1($this$transform, transform, (Continuation<? super FlowKt__EmittersKt$transform$1>) null));
    }

    public static final <T, R> Flow<R> unsafeTransform(Flow<? extends T> $this$unsafeTransform, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return new FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1($this$unsafeTransform, transform);
    }

    public static final <T> Flow<T> onStart(Flow<? extends T> $this$onStart, Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> action) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(action, $this$onStart);
    }

    public static final <T> Flow<T> onCompletion(Flow<? extends T> $this$onCompletion, Function3<? super FlowCollector<? super T>, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> action) {
        return new FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1($this$onCompletion, action);
    }

    public static final <T> Flow<T> onEmpty(Flow<? extends T> $this$onEmpty, Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> action) {
        return new FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1($this$onEmpty, action);
    }

    public static final void ensureActive(FlowCollector<?> $this$ensureActive) {
        if ($this$ensureActive instanceof ThrowingCollector) {
            throw ((ThrowingCollector) $this$ensureActive).f8e;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004b, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object invokeSafely$FlowKt__EmittersKt(kotlinx.coroutines.flow.FlowCollector<? super T> r3, kotlin.jvm.functions.Function3<? super kotlinx.coroutines.flow.FlowCollector<? super T>, ? super java.lang.Throwable, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> r4, java.lang.Throwable r5, kotlin.coroutines.Continuation<? super kotlin.Unit> r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$invokeSafely$1
            if (r0 == 0) goto L_0x0014
            r0 = r6
            kotlinx.coroutines.flow.FlowKt__EmittersKt$invokeSafely$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$invokeSafely$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$invokeSafely$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$invokeSafely$1
            r0.<init>(r6)
        L_0x0019:
            r6 = r0
            java.lang.Object r0 = r6.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r6.label
            switch(r2) {
                case 0: goto L_0x0037;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            r3.<init>(r4)
            throw r3
        L_0x002d:
            java.lang.Object r3 = r6.L$0
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0035 }
            goto L_0x0048
        L_0x0035:
            r4 = move-exception
            goto L_0x004e
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r0)
            r6.L$0 = r5     // Catch:{ all -> 0x004c }
            r2 = 1
            r6.label = r2     // Catch:{ all -> 0x004c }
            java.lang.Object r2 = r4.invoke(r3, r5, r6)     // Catch:{ all -> 0x004c }
            if (r2 != r1) goto L_0x0047
            return r1
        L_0x0047:
            r3 = r5
        L_0x0048:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L_0x004c:
            r4 = move-exception
            r3 = r5
        L_0x004e:
            if (r3 == 0) goto L_0x0057
            if (r3 == r4) goto L_0x0057
            r5 = r4
            r1 = 0
            kotlin.ExceptionsKt.addSuppressed(r5, r3)
        L_0x0057:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt.invokeSafely$FlowKt__EmittersKt(kotlinx.coroutines.flow.FlowCollector, kotlin.jvm.functions.Function3, java.lang.Throwable, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
