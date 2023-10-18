package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.Job;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, mo113d2 = {"<anonymous>", "", "count", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke", "(ILkotlin/coroutines/CoroutineContext$Element;)Ljava/lang/Integer;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
final class SafeCollector_commonKt$checkContext$result$1 extends Lambda implements Function2<Integer, CoroutineContext.Element, Integer> {
    final /* synthetic */ SafeCollector<?> $this_checkContext;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SafeCollector_commonKt$checkContext$result$1(SafeCollector<?> safeCollector) {
        super(2);
        this.$this_checkContext = safeCollector;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
        return invoke(((Number) p1).intValue(), (CoroutineContext.Element) p2);
    }

    public final Integer invoke(int count, CoroutineContext.Element element) {
        int i;
        CoroutineContext.Key key = element.getKey();
        CoroutineContext.Element collectElement = this.$this_checkContext.collectContext.get(key);
        if (key != Job.Key) {
            if (element != collectElement) {
                i = Integer.MIN_VALUE;
            } else {
                i = count + 1;
            }
            return Integer.valueOf(i);
        }
        Job collectJob = (Job) collectElement;
        Job emissionParentJob = SafeCollector_commonKt.transitiveCoroutineParent((Job) element, collectJob);
        if (emissionParentJob == collectJob) {
            return Integer.valueOf(collectJob == null ? count : count + 1);
        }
        throw new IllegalStateException(("Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n\t\tChild of " + emissionParentJob + ", expected child of " + collectJob + ".\n\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'").toString());
    }
}
