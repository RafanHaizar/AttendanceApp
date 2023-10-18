package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.channels.ChannelResult;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H@"}, mo113d2 = {"<anonymous>", "", "T", "value", "Lkotlinx/coroutines/channels/ChannelResult;", ""}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$2", mo130f = "Delay.kt", mo131i = {0}, mo132l = {243}, mo133m = "invokeSuspend", mo134n = {"$this$onFailure$iv"}, mo135s = {"L$0"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$debounceInternal$1$3$2 extends SuspendLambda implements Function2<ChannelResult<? extends Object>, Continuation<? super Unit>, Object> {
    final /* synthetic */ FlowCollector<T> $downstream;
    final /* synthetic */ Ref.ObjectRef<Object> $lastValue;
    /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$debounceInternal$1$3$2(Ref.ObjectRef<Object> objectRef, FlowCollector<? super T> flowCollector, Continuation<? super FlowKt__DelayKt$debounceInternal$1$3$2> continuation) {
        super(2, continuation);
        this.$lastValue = objectRef;
        this.$downstream = flowCollector;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__DelayKt$debounceInternal$1$3$2 flowKt__DelayKt$debounceInternal$1$3$2 = new FlowKt__DelayKt$debounceInternal$1$3$2(this.$lastValue, this.$downstream, continuation);
        flowKt__DelayKt$debounceInternal$1$3$2.L$0 = obj;
        return flowKt__DelayKt$debounceInternal$1$3$2;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return m1281invokeWpGqRn0(((ChannelResult) obj).m1271unboximpl(), (Continuation) obj2);
    }

    /* renamed from: invoke-WpGqRn0  reason: not valid java name */
    public final Object m1281invokeWpGqRn0(Object obj, Continuation<? super Unit> continuation) {
        return ((FlowKt__DelayKt$debounceInternal$1$3$2) create(ChannelResult.m1259boximpl(obj), continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0068, code lost:
        r5 = r1;
        r7 = r2;
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006b, code lost:
        r3.element = kotlinx.coroutines.flow.internal.NullSurrogateKt.DONE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0076, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            switch(r1) {
                case 0: goto L_0x001e;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L_0x0011:
            r0 = r11
            r1 = 0
            r2 = 0
            java.lang.Object r3 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r3 = (kotlin.jvm.internal.Ref.ObjectRef) r3
            java.lang.Object r4 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r12)
            goto L_0x0068
        L_0x001e:
            kotlin.ResultKt.throwOnFailure(r12)
            r1 = r11
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ChannelResult r2 = (kotlinx.coroutines.channels.ChannelResult) r2
            java.lang.Object r2 = r2.m1271unboximpl()
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r3 = r1.$lastValue
            r4 = 0
            boolean r5 = r2 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
            if (r5 != 0) goto L_0x0036
            r5 = r2
            r6 = 0
            r3.element = r5
        L_0x0036:
            r4 = r2
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r3 = r1.$lastValue
            kotlinx.coroutines.flow.FlowCollector<T> r2 = r1.$downstream
            r5 = 0
            boolean r6 = r4 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
            if (r6 == 0) goto L_0x0073
            java.lang.Throwable r6 = kotlinx.coroutines.channels.ChannelResult.m1263exceptionOrNullimpl(r4)
            r7 = 0
            if (r6 != 0) goto L_0x0070
            T r6 = r3.element
            if (r6 == 0) goto L_0x006b
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            T r8 = r3.element
            r9 = 0
            if (r8 != r6) goto L_0x0057
            r10 = 0
            r8 = r10
        L_0x0057:
            r1.L$0 = r4
            r1.L$1 = r3
            r6 = 1
            r1.label = r6
            java.lang.Object r2 = r2.emit(r8, r1)
            if (r2 != r0) goto L_0x0065
            return r0
        L_0x0065:
            r0 = r1
            r1 = r5
            r2 = r7
        L_0x0068:
            r5 = r1
            r7 = r2
            r1 = r0
        L_0x006b:
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.flow.internal.NullSurrogateKt.DONE
            r3.element = r0
            goto L_0x0073
        L_0x0070:
            r0 = r6
            r2 = 0
            throw r0
        L_0x0073:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
