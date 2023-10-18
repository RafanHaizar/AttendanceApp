package kotlinx.coroutines.flow.internal;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo112d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\u001f\u0010\u0017\u001a\u00020\u000e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019H@ø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u001f\u0010\u001b\u001a\u00020\u000e2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\fH¤@ø\u0001\u0000¢\u0006\u0002\u0010\u001dJ&\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH$J\u0010\u0010\u001f\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010 H\u0016J&\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000 2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#2\u0006\u0010\u001c\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020\u0016H\u0016R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R9\u0010\n\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000b8@X\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00068@X\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u0002\u0004\n\u0002\b\u0019¨\u0006&"}, mo113d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlow;", "T", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "collectToFun", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "getCollectToFun$kotlinx_coroutines_core", "()Lkotlin/jvm/functions/Function2;", "produceCapacity", "getProduceCapacity$kotlinx_coroutines_core", "()I", "additionalToStringProps", "", "collect", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectTo", "scope", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "dropChannelOperators", "Lkotlinx/coroutines/flow/Flow;", "fuse", "produceImpl", "Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/CoroutineScope;", "toString", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ChannelFlow.kt */
public abstract class ChannelFlow<T> implements FusibleFlow<T> {
    public final int capacity;
    public final CoroutineContext context;
    public final BufferOverflow onBufferOverflow;

    public Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        return collect$suspendImpl(this, flowCollector, continuation);
    }

    /* access modifiers changed from: protected */
    public abstract Object collectTo(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation);

    /* access modifiers changed from: protected */
    public abstract ChannelFlow<T> create(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow);

    public ChannelFlow(CoroutineContext context2, int capacity2, BufferOverflow onBufferOverflow2) {
        this.context = context2;
        this.capacity = capacity2;
        this.onBufferOverflow = onBufferOverflow2;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(capacity2 != -1)) {
                throw new AssertionError();
            }
        }
    }

    public final Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> getCollectToFun$kotlinx_coroutines_core() {
        return new ChannelFlow$collectToFun$1(this, (Continuation<? super ChannelFlow$collectToFun$1>) null);
    }

    public final int getProduceCapacity$kotlinx_coroutines_core() {
        int i = this.capacity;
        if (i == -3) {
            return -2;
        }
        return i;
    }

    public Flow<T> dropChannelOperators() {
        return null;
    }

    public Flow<T> fuse(CoroutineContext context2, int capacity2, BufferOverflow onBufferOverflow2) {
        BufferOverflow newOverflow;
        int newCapacity;
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((capacity2 != -1 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        CoroutineContext newContext = context2.plus(this.context);
        if (onBufferOverflow2 != BufferOverflow.SUSPEND) {
            newCapacity = capacity2;
            newOverflow = onBufferOverflow2;
        } else {
            int sum = this.capacity;
            if (sum != -3) {
                if (capacity2 != -3) {
                    if (sum != -2) {
                        if (capacity2 != -2) {
                            if (DebugKt.getASSERTIONS_ENABLED()) {
                                if ((this.capacity >= 0 ? 1 : 0) == 0) {
                                    throw new AssertionError();
                                }
                            }
                            if (DebugKt.getASSERTIONS_ENABLED()) {
                                if (capacity2 < 0) {
                                    z = false;
                                }
                                if (!z) {
                                    throw new AssertionError();
                                }
                            }
                            sum = this.capacity + capacity2;
                            if (sum < 0) {
                                sum = Integer.MAX_VALUE;
                            }
                        }
                    }
                }
                newCapacity = sum;
                newOverflow = this.onBufferOverflow;
            }
            sum = capacity2;
            newCapacity = sum;
            newOverflow = this.onBufferOverflow;
        }
        if (Intrinsics.areEqual((Object) newContext, (Object) this.context) && newCapacity == this.capacity && newOverflow == this.onBufferOverflow) {
            return this;
        }
        return create(newContext, newCapacity, newOverflow);
    }

    public ReceiveChannel<T> produceImpl(CoroutineScope scope) {
        return ProduceKt.produce$default(scope, this.context, getProduceCapacity$kotlinx_coroutines_core(), this.onBufferOverflow, CoroutineStart.ATOMIC, (Function1) null, getCollectToFun$kotlinx_coroutines_core(), 16, (Object) null);
    }

    static /* synthetic */ Object collect$suspendImpl(ChannelFlow channelFlow, FlowCollector collector, Continuation $completion) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new ChannelFlow$collect$2(collector, channelFlow, (Continuation<? super ChannelFlow$collect$2>) null), $completion);
        return coroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? coroutineScope : Unit.INSTANCE;
    }

    /* access modifiers changed from: protected */
    public String additionalToStringProps() {
        return null;
    }

    public String toString() {
        ArrayList props = new ArrayList(4);
        String it = additionalToStringProps();
        if (it != null) {
            props.add(it);
        }
        if (this.context != EmptyCoroutineContext.INSTANCE) {
            props.add(Intrinsics.stringPlus("context=", this.context));
        }
        int i = this.capacity;
        if (i != -3) {
            props.add(Intrinsics.stringPlus("capacity=", Integer.valueOf(i)));
        }
        if (this.onBufferOverflow != BufferOverflow.SUSPEND) {
            props.add(Intrinsics.stringPlus("onBufferOverflow=", this.onBufferOverflow));
        }
        return DebugStringsKt.getClassSimpleName(this) + '[' + CollectionsKt.joinToString$default(props, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null) + ']';
    }
}
