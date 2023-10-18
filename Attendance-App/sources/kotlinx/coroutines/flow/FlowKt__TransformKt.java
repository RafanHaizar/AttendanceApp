package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

@Metadata(mo112d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001aM\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012$\b\u0004\u0010\u0003\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004H\bø\u0001\u0000¢\u0006\u0002\u0010\b\u001a\u001f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\n0\u0001\"\u0006\b\u0000\u0010\n\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0001H\b\u001aM\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012$\b\u0004\u0010\u0003\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004H\bø\u0001\u0000¢\u0006\u0002\u0010\b\u001a\"\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0007*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001\u001ab\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\n0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u00020\u000123\b\u0004\u0010\u000e\u001a-\b\u0001\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004H\bø\u0001\u0000¢\u0006\u0002\u0010\b\u001ah\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\n0\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\n*\u00020\u0007*\b\u0012\u0004\u0012\u0002H\u00020\u000125\b\u0004\u0010\u000e\u001a/\b\u0001\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H\n0\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004H\bø\u0001\u0000¢\u0006\u0002\u0010\b\u001aH\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\"\u0010\u0014\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004ø\u0001\u0000¢\u0006\u0002\u0010\b\u001a|\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\n0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0017\u001a\u0002H\n2H\b\u0001\u0010\u0018\u001aB\b\u0001\u0012\u0013\u0012\u0011H\n¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u001a\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0019ø\u0001\u0000¢\u0006\u0002\u0010\u001b\u001al\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012F\u0010\u0018\u001aB\b\u0001\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u001a\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0019ø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a|\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\n0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0017\u001a\u0002H\n2H\b\u0001\u0010\u0018\u001aB\b\u0001\u0012\u0013\u0012\u0011H\n¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u001a\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0019ø\u0001\u0000¢\u0006\u0002\u0010\u001b\u001a\"\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020 0\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u0002\u0004\n\u0002\b\u0019¨\u0006!"}, mo113d2 = {"filter", "Lkotlinx/coroutines/flow/Flow;", "T", "predicate", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "filterIsInstance", "R", "filterNot", "filterNotNull", "map", "transform", "Lkotlin/ParameterName;", "name", "value", "mapNotNull", "onEach", "action", "", "runningFold", "initial", "operation", "Lkotlin/Function3;", "accumulator", "(Lkotlinx/coroutines/flow/Flow;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "runningReduce", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "scan", "withIndex", "Lkotlin/collections/IndexedValue;", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Transform.kt */
final /* synthetic */ class FlowKt__TransformKt {
    public static final <T> Flow<T> filter(Flow<? extends T> $this$filter, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return new FlowKt__TransformKt$filter$$inlined$unsafeTransform$1($this$filter, predicate);
    }

    public static final <T> Flow<T> filterNot(Flow<? extends T> $this$filterNot, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return new FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1($this$filterNot, predicate);
    }

    public static final <T> Flow<T> filterNotNull(Flow<? extends T> $this$filterNotNull) {
        return new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1($this$filterNotNull);
    }

    public static final <T, R> Flow<R> map(Flow<? extends T> $this$map, Function2<? super T, ? super Continuation<? super R>, ? extends Object> transform) {
        return new FlowKt__TransformKt$map$$inlined$unsafeTransform$1($this$map, transform);
    }

    public static final <T, R> Flow<R> mapNotNull(Flow<? extends T> $this$mapNotNull, Function2<? super T, ? super Continuation<? super R>, ? extends Object> transform) {
        return new FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1($this$mapNotNull, transform);
    }

    public static final <T> Flow<IndexedValue<T>> withIndex(Flow<? extends T> $this$withIndex) {
        return new FlowKt__TransformKt$withIndex$$inlined$unsafeFlow$1($this$withIndex);
    }

    public static final <T> Flow<T> onEach(Flow<? extends T> $this$onEach, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> action) {
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1($this$onEach, action);
    }

    public static final <T, R> Flow<R> scan(Flow<? extends T> $this$scan, R initial, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> operation) {
        return FlowKt.runningFold($this$scan, initial, operation);
    }

    public static final <T, R> Flow<R> runningFold(Flow<? extends T> $this$runningFold, R initial, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> operation) {
        return new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(initial, $this$runningFold, operation);
    }

    public static final <T> Flow<T> runningReduce(Flow<? extends T> $this$runningReduce, Function3<? super T, ? super T, ? super Continuation<? super T>, ? extends Object> operation) {
        return new FlowKt__TransformKt$runningReduce$$inlined$unsafeFlow$1($this$runningReduce, operation);
    }
}
