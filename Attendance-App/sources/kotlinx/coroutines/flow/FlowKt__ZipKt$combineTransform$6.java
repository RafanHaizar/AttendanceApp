package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.internal.CombineKt;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@"}, mo113d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 176)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$6", mo130f = "Zip.kt", mo131i = {}, mo132l = {251}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Zip.kt */
public final class FlowKt__ZipKt$combineTransform$6 extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T>[] $flows;
    final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlowKt__ZipKt$combineTransform$6(Flow<? extends T>[] flowArr, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, Continuation<? super FlowKt__ZipKt$combineTransform$6> continuation) {
        super(2, continuation);
        this.$flows = flowArr;
        this.$transform = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__ZipKt$combineTransform$6 flowKt__ZipKt$combineTransform$6 = new FlowKt__ZipKt$combineTransform$6(this.$flows, this.$transform, continuation);
        flowKt__ZipKt$combineTransform$6.L$0 = obj;
        return flowKt__ZipKt$combineTransform$6;
    }

    public final Object invoke(FlowCollector<? super R> flowCollector, Continuation<? super Unit> continuation) {
        return ((FlowKt__ZipKt$combineTransform$6) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Metadata(mo112d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H@"}, mo113d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", ""}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 176)
    @DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$6$2", mo130f = "Zip.kt", mo131i = {}, mo132l = {251}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$6$2 */
    /* compiled from: Zip.kt */
    public static final class C00812 extends SuspendLambda implements Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public final Object invoke(FlowCollector<? super R> flowCollector, T[] tArr, Continuation<? super Unit> continuation) {
            C00812 r0 = new C00812(r4, continuation);
            r0.L$0 = flowCollector;
            r0.L$1 = tArr;
            return r0.invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> function3 = r4;
                    this.L$0 = null;
                    this.label = 1;
                    if (function3.invoke((FlowCollector) this.L$0, (Object[]) this.L$1, this) != coroutine_suspended) {
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

        public final Object invokeSuspend$$forInline(Object $result) {
            r4.invoke((FlowCollector) this.L$0, (Object[]) this.L$1, this);
            return Unit.INSTANCE;
        }
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Flow<T>[] flowArr = this.$flows;
                Intrinsics.needClassReification();
                final Flow<T>[] flowArr2 = this.$flows;
                Intrinsics.needClassReification();
                final Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> function3 = this.$transform;
                this.label = 1;
                if (CombineKt.combineInternal((FlowCollector) this.L$0, flowArr, new Function0<T[]>() {
                    public final T[] invoke() {
                        int length = r3.length;
                        Intrinsics.reifiedOperationMarker(0, "T?");
                        return new Object[length];
                    }
                }, new C00812((Continuation<? super C00812>) null), this) != coroutine_suspended) {
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

    public final Object invokeSuspend$$forInline(Object $result) {
        Flow<T>[] flowArr = this.$flows;
        Intrinsics.needClassReification();
        final Flow<T>[] flowArr2 = this.$flows;
        Intrinsics.needClassReification();
        final Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> function3 = this.$transform;
        InlineMarker.mark(0);
        CombineKt.combineInternal((FlowCollector) this.L$0, flowArr, new Function0<T[]>() {
            public final T[] invoke() {
                int length = flowArr2.length;
                Intrinsics.reifiedOperationMarker(0, "T?");
                return new Object[length];
            }
        }, new C00812((Continuation<? super C00812>) null), this);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}
