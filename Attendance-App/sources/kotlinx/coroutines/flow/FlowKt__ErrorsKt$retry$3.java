package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;

@Metadata(mo112d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\t\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H@"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "cause", "", "attempt", ""}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__ErrorsKt$retry$3", mo130f = "Errors.kt", mo131i = {}, mo132l = {95}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Errors.kt */
final class FlowKt__ErrorsKt$retry$3 extends SuspendLambda implements Function4<FlowCollector<? super T>, Throwable, Long, Continuation<? super Boolean>, Object> {
    final /* synthetic */ Function2<Throwable, Continuation<? super Boolean>, Object> $predicate;
    final /* synthetic */ long $retries;
    /* synthetic */ long J$0;
    /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__ErrorsKt$retry$3(long j, Function2<? super Throwable, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super FlowKt__ErrorsKt$retry$3> continuation) {
        super(4, continuation);
        this.$retries = j;
        this.$predicate = function2;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        return invoke((FlowCollector) obj, (Throwable) obj2, ((Number) obj3).longValue(), (Continuation<? super Boolean>) (Continuation) obj4);
    }

    public final Object invoke(FlowCollector<? super T> flowCollector, Throwable th, long j, Continuation<? super Boolean> continuation) {
        FlowKt__ErrorsKt$retry$3 flowKt__ErrorsKt$retry$3 = new FlowKt__ErrorsKt$retry$3(this.$retries, this.$predicate, continuation);
        flowKt__ErrorsKt$retry$3.L$0 = th;
        flowKt__ErrorsKt$retry$3.J$0 = j;
        return flowKt__ErrorsKt$retry$3.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0040, code lost:
        r2 = false;
        r0 = r10;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 1
            switch(r1) {
                case 0: goto L_0x0019;
                case 1: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0012:
            r0 = r9
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r0
            r0 = r10
            goto L_0x0036
        L_0x0019:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            java.lang.Object r3 = r1.L$0
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            long r4 = r1.J$0
            long r6 = r1.$retries
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x0040
            kotlin.jvm.functions.Function2<java.lang.Throwable, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r6 = r1.$predicate
            r1.label = r2
            java.lang.Object r3 = r6.invoke(r3, r1)
            if (r3 != r0) goto L_0x0034
            return r0
        L_0x0034:
            r0 = r10
            r10 = r3
        L_0x0036:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L_0x003f
            goto L_0x0042
        L_0x003f:
            r10 = r0
        L_0x0040:
            r2 = 0
            r0 = r10
        L_0x0042:
            java.lang.Boolean r10 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r2)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ErrorsKt$retry$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
