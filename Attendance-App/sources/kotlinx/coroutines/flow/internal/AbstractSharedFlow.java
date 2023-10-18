package kotlinx.coroutines.flow.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;

@Metadata(mo112d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b \u0018\u0000*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u00060\u0003j\u0002`\u0004B\u0005¢\u0006\u0002\u0010\u0005J\r\u0010\u0018\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u0019J\r\u0010\u001a\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000e2\u0006\u0010\u001c\u001a\u00020\tH$¢\u0006\u0002\u0010\u001dJ\u001d\u0010\u001e\u001a\u00020\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u001f0!H\bJ\u0015\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010$R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\tX\u000e¢\u0006\u0002\n\u0000R:\u0010\u000f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u000e2\u0010\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u000e@BX\u000e¢\u0006\u0010\n\u0002\u0010\u0013\u0012\u0004\b\u0010\u0010\u0005\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006%"}, mo113d2 = {"Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "S", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "()V", "_subscriptionCount", "Lkotlinx/coroutines/flow/internal/SubscriptionCountStateFlow;", "<set-?>", "", "nCollectors", "getNCollectors", "()I", "nextIndex", "", "slots", "getSlots$annotations", "getSlots", "()[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "subscriptionCount", "Lkotlinx/coroutines/flow/StateFlow;", "getSubscriptionCount", "()Lkotlinx/coroutines/flow/StateFlow;", "allocateSlot", "()Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "createSlot", "createSlotArray", "size", "(I)[Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "forEachSlotLocked", "", "block", "Lkotlin/Function1;", "freeSlot", "slot", "(Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;)V", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: AbstractSharedFlow.kt */
public abstract class AbstractSharedFlow<S extends AbstractSharedFlowSlot<?>> {
    private SubscriptionCountStateFlow _subscriptionCount;
    /* access modifiers changed from: private */
    public int nCollectors;
    private int nextIndex;
    /* access modifiers changed from: private */
    public S[] slots;

    protected static /* synthetic */ void getSlots$annotations() {
    }

    /* access modifiers changed from: protected */
    public abstract S createSlot();

    /* access modifiers changed from: protected */
    public abstract S[] createSlotArray(int i);

    /* access modifiers changed from: protected */
    public final S[] getSlots() {
        return this.slots;
    }

    /* access modifiers changed from: protected */
    public final int getNCollectors() {
        return this.nCollectors;
    }

    public final StateFlow<Integer> getSubscriptionCount() {
        SubscriptionCountStateFlow it;
        synchronized (this) {
            it = this._subscriptionCount;
            if (it == null) {
                it = new SubscriptionCountStateFlow(getNCollectors());
                this._subscriptionCount = it;
            }
        }
        return it;
    }

    /* access modifiers changed from: protected */
    public final S allocateSlot() {
        AbstractSharedFlowSlot slot;
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        synchronized (this) {
            AbstractSharedFlowSlot[] curSlots = getSlots();
            if (curSlots == null) {
                AbstractSharedFlowSlot[] it = createSlotArray(2);
                this.slots = it;
                curSlots = it;
            } else if (getNCollectors() >= curSlots.length) {
                S[] it2 = Arrays.copyOf(curSlots, curSlots.length * 2);
                Intrinsics.checkNotNullExpressionValue(it2, "copyOf(this, newSize)");
                this.slots = it2;
                curSlots = (AbstractSharedFlowSlot[]) it2;
            }
            int index = this.nextIndex;
            do {
                AbstractSharedFlowSlot it3 = curSlots[index];
                if (it3 == null) {
                    it3 = createSlot();
                    curSlots[index] = it3;
                }
                slot = it3;
                index++;
                if (index >= curSlots.length) {
                    index = 0;
                }
            } while (!slot.allocateLocked(this));
            this.nextIndex = index;
            this.nCollectors = getNCollectors() + 1;
            subscriptionCountStateFlow = this._subscriptionCount;
        }
        AbstractSharedFlowSlot slot2 = slot;
        if (subscriptionCountStateFlow != null) {
            subscriptionCountStateFlow.increment(1);
        }
        return slot2;
    }

    /* access modifiers changed from: protected */
    public final void freeSlot(S slot) {
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        int i;
        Continuation[] freeLocked;
        synchronized (this) {
            this.nCollectors = getNCollectors() - 1;
            subscriptionCountStateFlow = this._subscriptionCount;
            i = 0;
            if (getNCollectors() == 0) {
                this.nextIndex = 0;
            }
            freeLocked = slot.freeLocked(this);
        }
        Continuation[] resumes = freeLocked;
        int length = resumes.length;
        while (i < length) {
            Continuation cont = resumes[i];
            i++;
            if (cont != null) {
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
            }
        }
        if (subscriptionCountStateFlow != null) {
            subscriptionCountStateFlow.increment(-1);
        }
    }

    /* access modifiers changed from: protected */
    public final void forEachSlotLocked(Function1<? super S, Unit> block) {
        AbstractSharedFlowSlot[] $this$forEach$iv;
        if (this.nCollectors != 0 && ($this$forEach$iv = this.slots) != null) {
            int length = $this$forEach$iv.length;
            int i = 0;
            while (i < length) {
                AbstractSharedFlowSlot element$iv = $this$forEach$iv[i];
                i++;
                AbstractSharedFlowSlot slot = element$iv;
                if (slot != null) {
                    block.invoke(slot);
                }
            }
        }
    }
}
