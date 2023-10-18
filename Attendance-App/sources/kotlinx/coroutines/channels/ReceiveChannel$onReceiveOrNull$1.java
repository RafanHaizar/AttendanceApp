package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(mo112d1 = {"\u0000)\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0001JJ\u0010\u0002\u001a\u00020\u0003\"\u0004\b\u0001\u0010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00062$\u0010\u0007\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\t\u0012\u0006\u0012\u0004\u0018\u00010\n0\bH\u0017ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, mo113d2 = {"kotlinx/coroutines/channels/ReceiveChannel$onReceiveOrNull$1", "Lkotlinx/coroutines/selects/SelectClause1;", "registerSelectClause1", "", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Channel.kt */
public final class ReceiveChannel$onReceiveOrNull$1 implements SelectClause1<E> {
    final /* synthetic */ ReceiveChannel<E> this$0;

    ReceiveChannel$onReceiveOrNull$1(ReceiveChannel<? extends E> $receiver) {
        this.this$0 = $receiver;
    }

    public <R> void registerSelectClause1(SelectInstance<? super R> select, Function2<? super E, ? super Continuation<? super R>, ? extends Object> block) {
        this.this$0.getOnReceiveCatching().registerSelectClause1(select, new ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1(block, (Continuation<? super ReceiveChannel$onReceiveOrNull$1$registerSelectClause1$1>) null));
    }
}
