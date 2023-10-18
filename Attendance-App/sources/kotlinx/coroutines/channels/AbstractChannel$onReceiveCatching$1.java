package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(mo112d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0001JQ\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0001\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072(\u0010\b\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\tH\u0016ø\u0001\u0000ø\u0001\u0000¢\u0006\u0002\u0010\fø\u0001\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\r"}, mo113d2 = {"kotlinx/coroutines/channels/AbstractChannel$onReceiveCatching$1", "Lkotlinx/coroutines/selects/SelectClause1;", "Lkotlinx/coroutines/channels/ChannelResult;", "registerSelectClause1", "", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: AbstractChannel.kt */
public final class AbstractChannel$onReceiveCatching$1 implements SelectClause1<ChannelResult<? extends E>> {
    final /* synthetic */ AbstractChannel<E> this$0;

    AbstractChannel$onReceiveCatching$1(AbstractChannel<E> $receiver) {
        this.this$0 = $receiver;
    }

    public <R> void registerSelectClause1(SelectInstance<? super R> select, Function2<? super ChannelResult<? extends E>, ? super Continuation<? super R>, ? extends Object> block) {
        this.this$0.registerSelectReceiveMode(select, 1, block);
    }
}
