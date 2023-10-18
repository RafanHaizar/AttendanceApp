package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo112d1 = {"\u0000\b\n\u0002\b\u0004\n\u0002\u0018\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001\"\u0006\b\u0001\u0010\u0002 \u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H@"}, mo113d2 = {"<anonymous>", "R", "E", "it", "Lkotlinx/coroutines/channels/ChannelResult;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.channels.ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1", mo130f = "Channel.kt", mo131i = {}, mo132l = {375}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: Channel.kt */
final class ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1 extends SuspendLambda implements Function2<ChannelResult<? extends E>, Continuation<? super R>, Object> {
    final /* synthetic */ Function2<E, Continuation<? super R>, Object> $block;
    /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1(Function2<? super E, ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1> continuation) {
        super(2, continuation);
        this.$block = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1 receiveChannel$onReceiveOrNull$1$registerSelectClause1$1 = new ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1(this.$block, continuation);
        receiveChannel$onReceiveOrNull$1$registerSelectClause1$1.L$0 = obj;
        return receiveChannel$onReceiveOrNull$1$registerSelectClause1$1;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return m1277invokeWpGqRn0(((ChannelResult) obj).m1271unboximpl(), (Continuation) obj2);
    }

    /* renamed from: invoke-WpGqRn0  reason: not valid java name */
    public final Object m1277invokeWpGqRn0(Object obj, Continuation<? super R> continuation) {
        return ((ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1) create(ChannelResult.m1259boximpl(obj), continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                Object it = ((ChannelResult) this.L$0).m1271unboximpl();
                Throwable it2 = ChannelResult.m1263exceptionOrNullimpl(it);
                if (it2 == null) {
                    Function2<E, Continuation<? super R>, Object> function2 = this.$block;
                    Object r4 = ChannelResult.m1264getOrNullimpl(it);
                    this.label = 1;
                    Object it3 = function2.invoke(r4, this);
                    if (it3 == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return it3;
                }
                throw it2;
            case 1:
                ResultKt.throwOnFailure($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
