package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.internal.CombineKt;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\b"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__ZipKt$combineUnsafe$$inlined$unsafeFlow$1"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 implements Flow<R> {
    final /* synthetic */ Flow[] $flows$inlined;
    final /* synthetic */ Function4 $transform$inlined$1;

    public FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1(Flow[] flowArr, Function4 function4) {
        this.$flows$inlined = flowArr;
        this.$transform$inlined$1 = function4;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        Continuation continuation = $completion;
        Flow[] flowArr = this.$flows$inlined;
        Function0 access$nullArrayFactory = FlowKt__ZipKt.nullArrayFactory$FlowKt__ZipKt();
        final Function4 function4 = this.$transform$inlined$1;
        Object combineInternal = CombineKt.combineInternal(collector, flowArr, access$nullArrayFactory, new C00642((Continuation) null), $completion);
        if (combineInternal == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return combineInternal;
        }
        return Unit.INSTANCE;
    }

    @Metadata(mo112d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¨\u0006\u0007"}, mo113d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "kotlinx/coroutines/flow/FlowKt__ZipKt$combineUnsafe$1$1"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
    @DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1$2", mo130f = "Zip.kt", mo131i = {}, mo132l = {333, 333}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1$2 */
    /* compiled from: Zip.kt */
    public static final class C00642 extends SuspendLambda implements Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public final Object invoke(FlowCollector<? super R> flowCollector, Object[] objArr, Continuation<? super Unit> continuation) {
            C00642 r0 = new C00642(continuation, function4);
            r0.L$0 = flowCollector;
            r0.L$1 = objArr;
            return r0.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: kotlinx.coroutines.flow.FlowCollector} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(java.lang.Object r13) {
            /*
                r12 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r12.label
                r2 = 2
                switch(r1) {
                    case 0: goto L_0x0024;
                    case 1: goto L_0x0017;
                    case 2: goto L_0x0012;
                    default: goto L_0x000a;
                }
            L_0x000a:
                java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r13.<init>(r0)
                throw r13
            L_0x0012:
                r0 = r12
                kotlin.ResultKt.throwOnFailure(r13)
                goto L_0x0068
            L_0x0017:
                r1 = r12
                r3 = 0
                java.lang.Object r4 = r1.L$0
                kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
                kotlin.ResultKt.throwOnFailure(r13)
                r5 = r3
                r3 = r1
                r1 = r13
                goto L_0x0056
            L_0x0024:
                kotlin.ResultKt.throwOnFailure(r13)
                r1 = r12
                java.lang.Object r3 = r1.L$0
                r4 = r3
                kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
                java.lang.Object r3 = r1.L$1
                java.lang.Object[] r3 = (java.lang.Object[]) r3
                r5 = r1
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r5 = 0
                kotlin.jvm.functions.Function4 r6 = r7
                r7 = 0
                r7 = r3[r7]
                r8 = 1
                r9 = r3[r8]
                r10 = r3[r2]
                r1.L$0 = r4
                r1.label = r8
                r3 = 6
                kotlin.jvm.internal.InlineMarker.mark((int) r3)
                java.lang.Object r3 = r6.invoke(r7, r9, r10, r1)
                r6 = 7
                kotlin.jvm.internal.InlineMarker.mark((int) r6)
                if (r3 != r0) goto L_0x0052
                return r0
            L_0x0052:
                r11 = r1
                r1 = r13
                r13 = r3
                r3 = r11
            L_0x0056:
                r5 = r3
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r6 = 0
                r3.L$0 = r6
                r3.label = r2
                java.lang.Object r13 = r4.emit(r13, r5)
                if (r13 != r0) goto L_0x0066
                return r0
            L_0x0066:
                r13 = r1
                r0 = r3
            L_0x0068:
                kotlin.Unit r1 = kotlin.Unit.INSTANCE
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1.C00642.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
