package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u00020\u00060\u0005H@"}, mo113d2 = {"<anonymous>", "", "T1", "T2", "R", "Lkotlinx/coroutines/channels/ProducerScope;", ""}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1", mo130f = "Combine.kt", mo131i = {}, mo132l = {92}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Combine.kt */
final class CombineKt$zipImpl$1$1$second$1 extends SuspendLambda implements Function2<ProducerScope<? super Object>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T2> $flow2;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$zipImpl$1$1$second$1(Flow<? extends T2> flow, Continuation<? super CombineKt$zipImpl$1$1$second$1> continuation) {
        super(2, continuation);
        this.$flow2 = flow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CombineKt$zipImpl$1$1$second$1 combineKt$zipImpl$1$1$second$1 = new CombineKt$zipImpl$1$1$second$1(this.$flow2, continuation);
        combineKt$zipImpl$1$1$second$1.L$0 = obj;
        return combineKt$zipImpl$1$1$second$1;
    }

    public final Object invoke(ProducerScope<Object> producerScope, Continuation<? super Unit> continuation) {
        return ((CombineKt$zipImpl$1$1$second$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                final ProducerScope $this$produce = (ProducerScope) this.L$0;
                this.label = 1;
                if (this.$flow2.collect(new Object() {
                    /* JADX WARNING: Incorrect type for immutable var: ssa=T2, code=java.lang.Object, for r6v0, types: [T2] */
                    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
                    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
                    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
                        /*
                            r5 = this;
                            boolean r0 = r7 instanceof kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1$1$emit$1
                            if (r0 == 0) goto L_0x0014
                            r0 = r7
                            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1$1$emit$1 r0 = (kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1$1$emit$1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r1 = r1 & r2
                            if (r1 == 0) goto L_0x0014
                            int r7 = r0.label
                            int r7 = r7 - r2
                            r0.label = r7
                            goto L_0x0019
                        L_0x0014:
                            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1$1$emit$1 r0 = new kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1$1$emit$1
                            r0.<init>(r5, r7)
                        L_0x0019:
                            r7 = r0
                            java.lang.Object r0 = r7.result
                            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                            int r2 = r7.label
                            switch(r2) {
                                case 0: goto L_0x0031;
                                case 1: goto L_0x002d;
                                default: goto L_0x0025;
                            }
                        L_0x0025:
                            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                            r6.<init>(r7)
                            throw r6
                        L_0x002d:
                            kotlin.ResultKt.throwOnFailure(r0)
                            goto L_0x004a
                        L_0x0031:
                            kotlin.ResultKt.throwOnFailure(r0)
                            r2 = r5
                            kotlinx.coroutines.channels.ProducerScope<java.lang.Object> r3 = r2
                            kotlinx.coroutines.channels.SendChannel r3 = r3.getChannel()
                            if (r6 != 0) goto L_0x0040
                            kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
                            r6 = r4
                        L_0x0040:
                            r2 = 1
                            r7.label = r2
                            java.lang.Object r6 = r3.send(r6, r7)
                            if (r6 != r1) goto L_0x004a
                            return r1
                        L_0x004a:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1.C00951.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }, this) != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
