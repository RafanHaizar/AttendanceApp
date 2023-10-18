package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(mo112d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\n\u001aT\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\n26\u0010\f\u001a2\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00030\u0001\u001a6\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0012*\b\u0012\u0004\u0012\u0002H\u000b0\n2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00120\u0007\u001au\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\n\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\n2\u0014\u0010\u0013\u001a\u0010\u0012\u0004\u0012\u0002H\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00072:\u0010\f\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00030\u0001H\u0002¢\u0006\u0002\b\u0014\",\u0010\u0000\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0004\u0010\u0005\"&\u0010\u0006\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00078\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\b\u0010\u0005¨\u0006\u0015"}, mo113d2 = {"defaultAreEquivalent", "Lkotlin/Function2;", "", "", "getDefaultAreEquivalent$annotations$FlowKt__DistinctKt", "()V", "defaultKeySelector", "Lkotlin/Function1;", "getDefaultKeySelector$annotations$FlowKt__DistinctKt", "distinctUntilChanged", "Lkotlinx/coroutines/flow/Flow;", "T", "areEquivalent", "Lkotlin/ParameterName;", "name", "old", "new", "distinctUntilChangedBy", "K", "keySelector", "distinctUntilChangedBy$FlowKt__DistinctKt", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Distinct.kt */
final /* synthetic */ class FlowKt__DistinctKt {
    private static final Function2<Object, Object, Boolean> defaultAreEquivalent = FlowKt__DistinctKt$defaultAreEquivalent$1.INSTANCE;
    private static final Function1<Object, Object> defaultKeySelector = FlowKt__DistinctKt$defaultKeySelector$1.INSTANCE;

    private static /* synthetic */ void getDefaultAreEquivalent$annotations$FlowKt__DistinctKt() {
    }

    private static /* synthetic */ void getDefaultKeySelector$annotations$FlowKt__DistinctKt() {
    }

    public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> $this$distinctUntilChanged) {
        if ($this$distinctUntilChanged instanceof StateFlow) {
            return $this$distinctUntilChanged;
        }
        return distinctUntilChangedBy$FlowKt__DistinctKt($this$distinctUntilChanged, defaultKeySelector, defaultAreEquivalent);
    }

    public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> $this$distinctUntilChanged, Function2<? super T, ? super T, Boolean> areEquivalent) {
        return distinctUntilChangedBy$FlowKt__DistinctKt($this$distinctUntilChanged, defaultKeySelector, (Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(areEquivalent, 2));
    }

    public static final <T, K> Flow<T> distinctUntilChangedBy(Flow<? extends T> $this$distinctUntilChangedBy, Function1<? super T, ? extends K> keySelector) {
        return distinctUntilChangedBy$FlowKt__DistinctKt($this$distinctUntilChangedBy, keySelector, defaultAreEquivalent);
    }

    private static final <T> Flow<T> distinctUntilChangedBy$FlowKt__DistinctKt(Flow<? extends T> $this$distinctUntilChangedBy, Function1<? super T, ? extends Object> keySelector, Function2<Object, Object, Boolean> areEquivalent) {
        if (($this$distinctUntilChangedBy instanceof DistinctFlowImpl) && ((DistinctFlowImpl) $this$distinctUntilChangedBy).keySelector == keySelector && ((DistinctFlowImpl) $this$distinctUntilChangedBy).areEquivalent == areEquivalent) {
            return $this$distinctUntilChangedBy;
        }
        return new DistinctFlowImpl<>($this$distinctUntilChangedBy, keySelector, areEquivalent);
    }
}
