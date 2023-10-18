package kotlinx.coroutines.flow;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.FusibleFlow;

@Metadata(mo112d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000f\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0012\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\b\u0012\u0004\u0012\u0002H\u00010\u0006:\u0001hB\u001d\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0019\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0003H@ø\u0001\u0000¢\u0006\u0002\u0010.J\u0010\u0010/\u001a\u00020,2\u0006\u00100\u001a\u000201H\u0002J\b\u00102\u001a\u00020,H\u0002J\u001f\u00103\u001a\u0002042\f\u00105\u001a\b\u0012\u0004\u0012\u00028\u000006H@ø\u0001\u0000¢\u0006\u0002\u00107J\u0010\u00108\u001a\u00020,2\u0006\u00109\u001a\u00020\u0012H\u0002J\b\u0010:\u001a\u00020\u0003H\u0014J\u001d\u0010;\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000e2\u0006\u0010<\u001a\u00020\bH\u0014¢\u0006\u0002\u0010=J\b\u0010>\u001a\u00020,H\u0002J\u0019\u0010?\u001a\u00020,2\u0006\u0010@\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010AJ\u0019\u0010B\u001a\u00020,2\u0006\u0010@\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010AJ\u0012\u0010C\u001a\u00020,2\b\u0010D\u001a\u0004\u0018\u00010\u000fH\u0002J1\u0010E\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020,\u0018\u00010F0\u000e2\u0014\u0010G\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020,\u0018\u00010F0\u000eH\u0002¢\u0006\u0002\u0010HJ&\u0010I\u001a\b\u0012\u0004\u0012\u00028\u00000J2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010N\u001a\u0004\u0018\u00010\u000f2\u0006\u0010O\u001a\u00020\u0012H\u0002J7\u0010P\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e2\u0010\u0010Q\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u000e2\u0006\u0010R\u001a\u00020\b2\u0006\u0010S\u001a\u00020\bH\u0002¢\u0006\u0002\u0010TJ\b\u0010U\u001a\u00020,H\u0016J\u0015\u0010V\u001a\u00020W2\u0006\u0010@\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010XJ\u0015\u0010Y\u001a\u00020W2\u0006\u0010@\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010XJ\u0015\u0010Z\u001a\u00020W2\u0006\u0010@\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010XJ\u0010\u0010[\u001a\u00020\u00122\u0006\u0010-\u001a\u00020\u0003H\u0002J\u0012\u0010\\\u001a\u0004\u0018\u00010\u000f2\u0006\u0010-\u001a\u00020\u0003H\u0002J(\u0010]\u001a\u00020,2\u0006\u0010^\u001a\u00020\u00122\u0006\u0010_\u001a\u00020\u00122\u0006\u0010`\u001a\u00020\u00122\u0006\u0010a\u001a\u00020\u0012H\u0002J%\u0010b\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020,\u0018\u00010F0\u000e2\u0006\u0010c\u001a\u00020\u0012H\u0000¢\u0006\u0004\bd\u0010eJ\r\u0010f\u001a\u00020\u0012H\u0000¢\u0006\u0002\bgR\u001a\u0010\r\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u000eX\u000e¢\u0006\u0004\n\u0002\u0010\u0010R\u000e\u0010\t\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00128BX\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0014R\u001a\u0010\u0018\u001a\u00028\u00008DX\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0012X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u00128BX\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0014R\u000e\u0010 \u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\"8VX\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020\u0012X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\u00020\b8BX\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020\b8BX\u0004¢\u0006\u0006\u001a\u0004\b*\u0010(\u0002\u0004\n\u0002\b\u0019¨\u0006i"}, mo113d2 = {"Lkotlinx/coroutines/flow/SharedFlowImpl;", "T", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "Lkotlinx/coroutines/flow/SharedFlowSlot;", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "replay", "", "bufferCapacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(IILkotlinx/coroutines/channels/BufferOverflow;)V", "buffer", "", "", "[Ljava/lang/Object;", "bufferEndIndex", "", "getBufferEndIndex", "()J", "bufferSize", "head", "getHead", "lastReplayedLocked", "getLastReplayedLocked$annotations", "()V", "getLastReplayedLocked", "()Ljava/lang/Object;", "minCollectorIndex", "queueEndIndex", "getQueueEndIndex", "queueSize", "replayCache", "", "getReplayCache", "()Ljava/util/List;", "replayIndex", "replaySize", "getReplaySize", "()I", "totalSize", "getTotalSize", "awaitValue", "", "slot", "(Lkotlinx/coroutines/flow/SharedFlowSlot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelEmitter", "emitter", "Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "cleanupTailLocked", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "correctCollectorIndexesOnDropOldest", "newHead", "createSlot", "createSlotArray", "size", "(I)[Lkotlinx/coroutines/flow/SharedFlowSlot;", "dropOldestLocked", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitSuspend", "enqueueLocked", "item", "findSlotsToResumeLocked", "Lkotlin/coroutines/Continuation;", "resumesIn", "([Lkotlin/coroutines/Continuation;)[Lkotlin/coroutines/Continuation;", "fuse", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "getPeekedValueLockedAt", "index", "growBuffer", "curBuffer", "curSize", "newSize", "([Ljava/lang/Object;II)[Ljava/lang/Object;", "resetReplayCache", "tryEmit", "", "(Ljava/lang/Object;)Z", "tryEmitLocked", "tryEmitNoCollectorsLocked", "tryPeekLocked", "tryTakeValue", "updateBufferLocked", "newReplayIndex", "newMinCollectorIndex", "newBufferEndIndex", "newQueueEndIndex", "updateCollectorIndexLocked", "oldIndex", "updateCollectorIndexLocked$kotlinx_coroutines_core", "(J)[Lkotlin/coroutines/Continuation;", "updateNewCollectorIndexLocked", "updateNewCollectorIndexLocked$kotlinx_coroutines_core", "Emitter", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SharedFlow.kt */
public class SharedFlowImpl<T> extends AbstractSharedFlow<SharedFlowSlot> implements MutableSharedFlow<T>, CancellableFlow<T>, FusibleFlow<T> {
    private Object[] buffer;
    /* access modifiers changed from: private */
    public final int bufferCapacity;
    private int bufferSize;
    private long minCollectorIndex;
    private final BufferOverflow onBufferOverflow;
    /* access modifiers changed from: private */
    public int queueSize;
    private final int replay;
    private long replayIndex;

    @Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: SharedFlow.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BufferOverflow.values().length];
            iArr[BufferOverflow.SUSPEND.ordinal()] = 1;
            iArr[BufferOverflow.DROP_LATEST.ordinal()] = 2;
            iArr[BufferOverflow.DROP_OLDEST.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected static /* synthetic */ void getLastReplayedLocked$annotations() {
    }

    public Object collect(FlowCollector<? super T> flowCollector, Continuation<?> continuation) {
        return collect$suspendImpl(this, flowCollector, continuation);
    }

    public Object emit(T t, Continuation<? super Unit> continuation) {
        return emit$suspendImpl(this, t, continuation);
    }

    public SharedFlowImpl(int replay2, int bufferCapacity2, BufferOverflow onBufferOverflow2) {
        this.replay = replay2;
        this.bufferCapacity = bufferCapacity2;
        this.onBufferOverflow = onBufferOverflow2;
    }

    /* access modifiers changed from: private */
    public final long getHead() {
        return Math.min(this.minCollectorIndex, this.replayIndex);
    }

    private final int getReplaySize() {
        return (int) ((getHead() + ((long) this.bufferSize)) - this.replayIndex);
    }

    /* access modifiers changed from: private */
    public final int getTotalSize() {
        return this.bufferSize + this.queueSize;
    }

    private final long getBufferEndIndex() {
        return getHead() + ((long) this.bufferSize);
    }

    private final long getQueueEndIndex() {
        return getHead() + ((long) this.bufferSize) + ((long) this.queueSize);
    }

    public List<T> getReplayCache() {
        synchronized (this) {
            int replaySize = getReplaySize();
            if (replaySize == 0) {
                List<T> emptyList = CollectionsKt.emptyList();
                return emptyList;
            }
            ArrayList result = new ArrayList(replaySize);
            Object[] buffer2 = this.buffer;
            Intrinsics.checkNotNull(buffer2);
            int i = 0;
            while (i < replaySize) {
                int i2 = i;
                i++;
                result.add(SharedFlowKt.getBufferAt(buffer2, this.replayIndex + ((long) i2)));
            }
            return result;
        }
    }

    /* access modifiers changed from: protected */
    public final T getLastReplayedLocked() {
        Object[] objArr = this.buffer;
        Intrinsics.checkNotNull(objArr);
        return SharedFlowKt.getBufferAt(objArr, (this.replayIndex + ((long) getReplaySize())) - 1);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: kotlinx.coroutines.flow.SharedFlowSlot} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: kotlinx.coroutines.flow.SharedFlowImpl} */
    /* JADX WARNING: type inference failed for: r6v9, types: [kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot] */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x008f, code lost:
        r6 = (kotlinx.coroutines.Job) r8.getContext().get(kotlinx.coroutines.Job.Key);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x009f, code lost:
        r4 = r3.tryTakeValue(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a6, code lost:
        if (r4 == kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a8, code lost:
        if (r6 != null) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ab, code lost:
        kotlinx.coroutines.JobKt.ensureActive(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ae, code lost:
        r8.L$0 = r3;
        r8.L$1 = r2;
        r8.L$2 = r7;
        r8.L$3 = r6;
        r8.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00bd, code lost:
        if (r2.emit(r4, r8) != r1) goto L_0x00c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bf, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c1, code lost:
        r8.L$0 = r3;
        r8.L$1 = r2;
        r8.L$2 = r7;
        r8.L$3 = r6;
        r8.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00d0, code lost:
        if (r3.awaitValue(r7, r8) != r1) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00d2, code lost:
        return r1;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object collect$suspendImpl(kotlinx.coroutines.flow.SharedFlowImpl r6, kotlinx.coroutines.flow.FlowCollector r7, kotlin.coroutines.Continuation r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.SharedFlowImpl$collect$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.flow.SharedFlowImpl$collect$1 r0 = (kotlinx.coroutines.flow.SharedFlowImpl$collect$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.SharedFlowImpl$collect$1 r0 = new kotlinx.coroutines.flow.SharedFlowImpl$collect$1
            r0.<init>(r6, r8)
        L_0x0019:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x006a;
                case 1: goto L_0x0057;
                case 2: goto L_0x0042;
                case 3: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002d:
            java.lang.Object r6 = r8.L$3
            kotlinx.coroutines.Job r6 = (kotlinx.coroutines.Job) r6
            java.lang.Object r7 = r8.L$2
            kotlinx.coroutines.flow.SharedFlowSlot r7 = (kotlinx.coroutines.flow.SharedFlowSlot) r7
            java.lang.Object r2 = r8.L$1
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            java.lang.Object r3 = r8.L$0
            kotlinx.coroutines.flow.SharedFlowImpl r3 = (kotlinx.coroutines.flow.SharedFlowImpl) r3
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x00d4 }
            goto L_0x00c0
        L_0x0042:
            java.lang.Object r6 = r8.L$3
            kotlinx.coroutines.Job r6 = (kotlinx.coroutines.Job) r6
            java.lang.Object r7 = r8.L$2
            kotlinx.coroutines.flow.SharedFlowSlot r7 = (kotlinx.coroutines.flow.SharedFlowSlot) r7
            java.lang.Object r2 = r8.L$1
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            java.lang.Object r3 = r8.L$0
            kotlinx.coroutines.flow.SharedFlowImpl r3 = (kotlinx.coroutines.flow.SharedFlowImpl) r3
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x00d4 }
            goto L_0x00d3
        L_0x0057:
            java.lang.Object r6 = r8.L$2
            r7 = r6
            kotlinx.coroutines.flow.SharedFlowSlot r7 = (kotlinx.coroutines.flow.SharedFlowSlot) r7
            java.lang.Object r6 = r8.L$1
            r2 = r6
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            java.lang.Object r6 = r8.L$0
            r3 = r6
            kotlinx.coroutines.flow.SharedFlowImpl r3 = (kotlinx.coroutines.flow.SharedFlowImpl) r3
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x00d4 }
            goto L_0x008e
        L_0x006a:
            kotlin.ResultKt.throwOnFailure(r0)
            r3 = r6
            r2 = r7
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot r6 = r3.allocateSlot()
            r7 = r6
            kotlinx.coroutines.flow.SharedFlowSlot r7 = (kotlinx.coroutines.flow.SharedFlowSlot) r7
            boolean r6 = r2 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector     // Catch:{ all -> 0x00d4 }
            if (r6 == 0) goto L_0x008f
            r6 = r2
            kotlinx.coroutines.flow.SubscribedFlowCollector r6 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r6     // Catch:{ all -> 0x00d4 }
            r8.L$0 = r3     // Catch:{ all -> 0x00d4 }
            r8.L$1 = r2     // Catch:{ all -> 0x00d4 }
            r8.L$2 = r7     // Catch:{ all -> 0x00d4 }
            r4 = 1
            r8.label = r4     // Catch:{ all -> 0x00d4 }
            java.lang.Object r6 = r6.onSubscription(r8)     // Catch:{ all -> 0x00d4 }
            if (r6 != r1) goto L_0x008e
            return r1
        L_0x008e:
        L_0x008f:
            r6 = 0
            kotlin.coroutines.CoroutineContext r4 = r8.getContext()     // Catch:{ all -> 0x00d4 }
            kotlinx.coroutines.Job$Key r6 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x00d4 }
            kotlin.coroutines.CoroutineContext$Key r6 = (kotlin.coroutines.CoroutineContext.Key) r6     // Catch:{ all -> 0x00d4 }
            kotlin.coroutines.CoroutineContext$Element r6 = r4.get(r6)     // Catch:{ all -> 0x00d4 }
            kotlinx.coroutines.Job r6 = (kotlinx.coroutines.Job) r6     // Catch:{ all -> 0x00d4 }
        L_0x009e:
        L_0x009f:
            java.lang.Object r4 = r3.tryTakeValue(r7)     // Catch:{ all -> 0x00d4 }
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE     // Catch:{ all -> 0x00d4 }
            if (r4 == r5) goto L_0x00c1
            if (r6 != 0) goto L_0x00ab
            goto L_0x00ae
        L_0x00ab:
            kotlinx.coroutines.JobKt.ensureActive((kotlinx.coroutines.Job) r6)     // Catch:{ all -> 0x00d4 }
        L_0x00ae:
            r8.L$0 = r3     // Catch:{ all -> 0x00d4 }
            r8.L$1 = r2     // Catch:{ all -> 0x00d4 }
            r8.L$2 = r7     // Catch:{ all -> 0x00d4 }
            r8.L$3 = r6     // Catch:{ all -> 0x00d4 }
            r5 = 3
            r8.label = r5     // Catch:{ all -> 0x00d4 }
            java.lang.Object r5 = r2.emit(r4, r8)     // Catch:{ all -> 0x00d4 }
            if (r5 != r1) goto L_0x00c0
            return r1
        L_0x00c0:
            goto L_0x009e
        L_0x00c1:
            r8.L$0 = r3     // Catch:{ all -> 0x00d4 }
            r8.L$1 = r2     // Catch:{ all -> 0x00d4 }
            r8.L$2 = r7     // Catch:{ all -> 0x00d4 }
            r8.L$3 = r6     // Catch:{ all -> 0x00d4 }
            r4 = 2
            r8.label = r4     // Catch:{ all -> 0x00d4 }
            java.lang.Object r4 = r3.awaitValue(r7, r8)     // Catch:{ all -> 0x00d4 }
            if (r4 != r1) goto L_0x00d3
            return r1
        L_0x00d3:
            goto L_0x009f
        L_0x00d4:
            r6 = move-exception
            r1 = r7
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot r1 = (kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot) r1
            r3.freeSlot(r1)
            goto L_0x00dd
        L_0x00dc:
            throw r6
        L_0x00dd:
            goto L_0x00dc
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.collect$suspendImpl(kotlinx.coroutines.flow.SharedFlowImpl, kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public boolean tryEmit(T value) {
        int i;
        boolean z;
        Continuation[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            i = 0;
            if (tryEmitLocked(value)) {
                continuationArr = findSlotsToResumeLocked(continuationArr);
                z = true;
            } else {
                z = false;
            }
        }
        boolean emitted = z;
        int length = continuationArr.length;
        while (i < length) {
            Continuation cont = continuationArr[i];
            i++;
            if (cont != null) {
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
            }
        }
        return emitted;
    }

    static /* synthetic */ Object emit$suspendImpl(SharedFlowImpl sharedFlowImpl, Object value, Continuation $completion) {
        if (sharedFlowImpl.tryEmit(value)) {
            return Unit.INSTANCE;
        }
        Object emitSuspend = sharedFlowImpl.emitSuspend(value, $completion);
        return emitSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emitSuspend : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final boolean tryEmitLocked(T value) {
        if (getNCollectors() == 0) {
            return tryEmitNoCollectorsLocked(value);
        }
        if (this.bufferSize >= this.bufferCapacity && this.minCollectorIndex <= this.replayIndex) {
            switch (WhenMappings.$EnumSwitchMapping$0[this.onBufferOverflow.ordinal()]) {
                case 1:
                    return false;
                case 2:
                    return true;
            }
        }
        enqueueLocked(value);
        int i = this.bufferSize + 1;
        this.bufferSize = i;
        if (i > this.bufferCapacity) {
            dropOldestLocked();
        }
        if (getReplaySize() > this.replay) {
            updateBufferLocked(this.replayIndex + 1, this.minCollectorIndex, getBufferEndIndex(), getQueueEndIndex());
        }
        return true;
    }

    private final boolean tryEmitNoCollectorsLocked(T value) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getNCollectors() == 0)) {
                throw new AssertionError();
            }
        }
        if (this.replay == 0) {
            return true;
        }
        enqueueLocked(value);
        int i = this.bufferSize + 1;
        this.bufferSize = i;
        if (i > this.replay) {
            dropOldestLocked();
        }
        this.minCollectorIndex = getHead() + ((long) this.bufferSize);
        return true;
    }

    private final void dropOldestLocked() {
        Object[] objArr = this.buffer;
        Intrinsics.checkNotNull(objArr);
        SharedFlowKt.setBufferAt(objArr, getHead(), (Object) null);
        this.bufferSize--;
        long newHead = getHead() + 1;
        if (this.replayIndex < newHead) {
            this.replayIndex = newHead;
        }
        if (this.minCollectorIndex < newHead) {
            correctCollectorIndexesOnDropOldest(newHead);
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getHead() == newHead)) {
                throw new AssertionError();
            }
        }
    }

    private final void correctCollectorIndexesOnDropOldest(long newHead) {
        AbstractSharedFlowSlot[] $this$forEach$iv$iv;
        long j = newHead;
        AbstractSharedFlow this_$iv = this;
        if (!(this_$iv.nCollectors == 0 || ($this$forEach$iv$iv = this_$iv.slots) == null)) {
            int length = $this$forEach$iv$iv.length;
            int i = 0;
            while (i < length) {
                AbstractSharedFlowSlot element$iv$iv = $this$forEach$iv$iv[i];
                i++;
                AbstractSharedFlowSlot slot$iv = element$iv$iv;
                if (slot$iv != null) {
                    SharedFlowSlot slot = (SharedFlowSlot) slot$iv;
                    if (slot.index >= 0 && slot.index < j) {
                        slot.index = j;
                    }
                }
            }
        }
        this.minCollectorIndex = j;
    }

    /* access modifiers changed from: private */
    public final void enqueueLocked(Object item) {
        int curSize = getTotalSize();
        Object[] curBuffer = this.buffer;
        if (curBuffer == null) {
            curBuffer = growBuffer((Object[]) null, 0, 2);
        } else if (curSize >= curBuffer.length) {
            curBuffer = growBuffer(curBuffer, curSize, curBuffer.length * 2);
        }
        SharedFlowKt.setBufferAt(curBuffer, getHead() + ((long) curSize), item);
    }

    private final Object[] growBuffer(Object[] curBuffer, int curSize, int newSize) {
        int i = 0;
        if (newSize > 0) {
            Object[] newBuffer = new Object[newSize];
            this.buffer = newBuffer;
            if (curBuffer == null) {
                return newBuffer;
            }
            long head = getHead();
            while (i < curSize) {
                int i2 = i;
                i++;
                SharedFlowKt.setBufferAt(newBuffer, ((long) i2) + head, SharedFlowKt.getBufferAt(curBuffer, ((long) i2) + head));
            }
            return newBuffer;
        }
        throw new IllegalStateException("Buffer size overflow".toString());
    }

    /* access modifiers changed from: private */
    public final Object emitSuspend(T value, Continuation<? super Unit> $completion) {
        Emitter emitter;
        Continuation[] continuationArr;
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        Continuation[] continuationArr2 = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            if (tryEmitLocked(value)) {
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
                continuationArr = findSlotsToResumeLocked(continuationArr2);
                emitter = null;
            } else {
                Emitter it = new Emitter(this, ((long) getTotalSize()) + getHead(), value, cont);
                enqueueLocked(it);
                this.queueSize = this.queueSize + 1;
                if (this.bufferCapacity == 0) {
                    continuationArr2 = findSlotsToResumeLocked(continuationArr2);
                }
                continuationArr = continuationArr2;
                emitter = it;
            }
        }
        Emitter emitter2 = emitter;
        if (emitter2 != null) {
            CancellableContinuationKt.disposeOnCancellation(cont, emitter2);
        }
        int length = continuationArr.length;
        int i = 0;
        while (i < length) {
            Continuation r = continuationArr[i];
            i++;
            if (r != null) {
                Result.Companion companion2 = Result.Companion;
                r.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
            }
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final void cancelEmitter(Emitter emitter) {
        synchronized (this) {
            if (emitter.index >= getHead()) {
                Object[] buffer2 = this.buffer;
                Intrinsics.checkNotNull(buffer2);
                if (SharedFlowKt.getBufferAt(buffer2, emitter.index) == emitter) {
                    SharedFlowKt.setBufferAt(buffer2, emitter.index, SharedFlowKt.NO_VALUE);
                    cleanupTailLocked();
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
    }

    public final long updateNewCollectorIndexLocked$kotlinx_coroutines_core() {
        long index = this.replayIndex;
        if (index < this.minCollectorIndex) {
            this.minCollectorIndex = index;
        }
        return index;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x016a  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x016d  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0174  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.coroutines.Continuation<kotlin.Unit>[] updateCollectorIndexLocked$kotlinx_coroutines_core(long r27) {
        /*
            r26 = this;
            r9 = r26
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x001b
            r0 = 0
            long r1 = r9.minCollectorIndex
            int r3 = (r27 > r1 ? 1 : (r27 == r1 ? 0 : -1))
            if (r3 < 0) goto L_0x0011
            r0 = 1
            goto L_0x0012
        L_0x0011:
            r0 = 0
        L_0x0012:
            if (r0 == 0) goto L_0x0015
            goto L_0x001b
        L_0x0015:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x001b:
            long r0 = r9.minCollectorIndex
            int r2 = (r27 > r0 ? 1 : (r27 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x0024
            kotlin.coroutines.Continuation<kotlin.Unit>[] r0 = kotlinx.coroutines.flow.internal.AbstractSharedFlowKt.EMPTY_RESUMES
            return r0
        L_0x0024:
            long r12 = r26.getHead()
            r0 = 0
            int r2 = r9.bufferSize
            long r2 = (long) r2
            long r2 = r2 + r12
            int r0 = r9.bufferCapacity
            r4 = 1
            if (r0 != 0) goto L_0x0039
            int r0 = r9.queueSize
            if (r0 <= 0) goto L_0x0039
            long r2 = r2 + r4
        L_0x0039:
            r0 = r9
            kotlinx.coroutines.flow.internal.AbstractSharedFlow r0 = (kotlinx.coroutines.flow.internal.AbstractSharedFlow) r0
            r1 = 0
            int r6 = r0.nCollectors
            if (r6 != 0) goto L_0x0046
            r20 = r12
            goto L_0x007f
        L_0x0046:
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot[] r6 = r0.slots
            if (r6 != 0) goto L_0x004f
            r20 = r12
            goto L_0x007e
        L_0x004f:
            r7 = 0
            int r8 = r6.length
            r14 = 0
        L_0x0052:
            if (r14 >= r8) goto L_0x007c
            r15 = r6[r14]
            int r14 = r14 + 1
            r16 = r15
            r17 = 0
            if (r16 == 0) goto L_0x0077
            r10 = r16
            kotlinx.coroutines.flow.SharedFlowSlot r10 = (kotlinx.coroutines.flow.SharedFlowSlot) r10
            r19 = 0
            r20 = r12
            long r11 = r10.index
            r22 = 0
            int r13 = (r11 > r22 ? 1 : (r11 == r22 ? 0 : -1))
            if (r13 < 0) goto L_0x0076
            long r11 = r10.index
            int r13 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r13 >= 0) goto L_0x0076
            long r2 = r10.index
        L_0x0076:
            goto L_0x0079
        L_0x0077:
            r20 = r12
        L_0x0079:
            r12 = r20
            goto L_0x0052
        L_0x007c:
            r20 = r12
        L_0x007e:
        L_0x007f:
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x0098
            r0 = 0
            long r6 = r9.minCollectorIndex
            int r1 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r1 < 0) goto L_0x008e
            r0 = 1
            goto L_0x008f
        L_0x008e:
            r0 = 0
        L_0x008f:
            if (r0 == 0) goto L_0x0092
            goto L_0x0098
        L_0x0092:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x0098:
            long r0 = r9.minCollectorIndex
            int r6 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r6 > 0) goto L_0x00a1
            kotlin.coroutines.Continuation<kotlin.Unit>[] r0 = kotlinx.coroutines.flow.internal.AbstractSharedFlowKt.EMPTY_RESUMES
            return r0
        L_0x00a1:
            long r0 = r26.getBufferEndIndex()
            int r6 = r26.getNCollectors()
            if (r6 <= 0) goto L_0x00b8
            long r6 = r0 - r2
            int r7 = (int) r6
            int r6 = r9.queueSize
            int r8 = r9.bufferCapacity
            int r8 = r8 - r7
            int r6 = java.lang.Math.min(r6, r8)
            goto L_0x00ba
        L_0x00b8:
            int r6 = r9.queueSize
        L_0x00ba:
            r10 = r6
            kotlin.coroutines.Continuation<kotlin.Unit>[] r6 = kotlinx.coroutines.flow.internal.AbstractSharedFlowKt.EMPTY_RESUMES
            int r7 = r9.queueSize
            long r7 = (long) r7
            long r11 = r0 + r7
            if (r10 <= 0) goto L_0x0118
            kotlin.coroutines.Continuation[] r6 = new kotlin.coroutines.Continuation[r10]
            r7 = 0
            java.lang.Object[] r8 = r9.buffer
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8)
            r13 = r0
        L_0x00cd:
            int r15 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r15 >= 0) goto L_0x0115
            r15 = r0
            long r0 = r0 + r4
            r4 = r15
            java.lang.Object r15 = kotlinx.coroutines.flow.SharedFlowKt.getBufferAt(r8, r4)
            r16 = r0
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE
            if (r15 == r0) goto L_0x0110
            if (r15 == 0) goto L_0x0108
            r0 = r15
            kotlinx.coroutines.flow.SharedFlowImpl$Emitter r0 = (kotlinx.coroutines.flow.SharedFlowImpl.Emitter) r0
            int r0 = r7 + 1
            r1 = r15
            kotlinx.coroutines.flow.SharedFlowImpl$Emitter r1 = (kotlinx.coroutines.flow.SharedFlowImpl.Emitter) r1
            kotlin.coroutines.Continuation<kotlin.Unit> r1 = r1.cont
            r6[r7] = r1
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE
            kotlinx.coroutines.flow.SharedFlowKt.setBufferAt(r8, r4, r1)
            r1 = r15
            kotlinx.coroutines.flow.SharedFlowImpl$Emitter r1 = (kotlinx.coroutines.flow.SharedFlowImpl.Emitter) r1
            java.lang.Object r1 = r1.value
            kotlinx.coroutines.flow.SharedFlowKt.setBufferAt(r8, r13, r1)
            r22 = 1
            long r13 = r13 + r22
            if (r0 < r10) goto L_0x0102
            r0 = r13
            r13 = r6
            goto L_0x0119
        L_0x0102:
            r7 = r0
            r0 = r16
            r4 = 1
            goto L_0x00cd
        L_0x0108:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "null cannot be cast to non-null type kotlinx.coroutines.flow.SharedFlowImpl.Emitter"
            r0.<init>(r1)
            throw r0
        L_0x0110:
            r0 = r16
            r4 = 1
            goto L_0x00cd
        L_0x0115:
            r0 = r13
            r13 = r6
            goto L_0x0119
        L_0x0118:
            r13 = r6
        L_0x0119:
            long r4 = r0 - r20
            int r14 = (int) r4
            int r4 = r26.getNCollectors()
            if (r4 != 0) goto L_0x0123
            r2 = r0
        L_0x0123:
            r15 = r2
            long r2 = r9.replayIndex
            int r4 = r9.replay
            int r4 = java.lang.Math.min(r4, r14)
            long r4 = (long) r4
            long r4 = r0 - r4
            long r2 = java.lang.Math.max(r2, r4)
            int r4 = r9.bufferCapacity
            if (r4 != 0) goto L_0x0155
            int r4 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r4 >= 0) goto L_0x0155
            java.lang.Object[] r4 = r9.buffer
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            java.lang.Object r4 = kotlinx.coroutines.flow.SharedFlowKt.getBufferAt(r4, r2)
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r4, (java.lang.Object) r5)
            if (r4 == 0) goto L_0x0155
            r4 = 1
            long r0 = r0 + r4
            long r2 = r2 + r4
            r22 = r0
            r24 = r2
            goto L_0x0159
        L_0x0155:
            r22 = r0
            r24 = r2
        L_0x0159:
            r0 = r26
            r1 = r24
            r3 = r15
            r5 = r22
            r7 = r11
            r0.updateBufferLocked(r1, r3, r5, r7)
            r26.cleanupTailLocked()
            int r0 = r13.length
            if (r0 != 0) goto L_0x016d
            r18 = 1
            goto L_0x016f
        L_0x016d:
            r18 = 0
        L_0x016f:
            r0 = 1
            r0 = r18 ^ 1
            if (r0 == 0) goto L_0x0178
            kotlin.coroutines.Continuation[] r13 = r9.findSlotsToResumeLocked(r13)
        L_0x0178:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.updateCollectorIndexLocked$kotlinx_coroutines_core(long):kotlin.coroutines.Continuation[]");
    }

    private final void updateBufferLocked(long newReplayIndex, long newMinCollectorIndex, long newBufferEndIndex, long newQueueEndIndex) {
        long j = newReplayIndex;
        long j2 = newMinCollectorIndex;
        long newHead = Math.min(j2, j);
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((newHead >= getHead() ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        long head = getHead();
        while (head < newHead) {
            long index = head;
            head++;
            Object[] objArr = this.buffer;
            Intrinsics.checkNotNull(objArr);
            SharedFlowKt.setBufferAt(objArr, index, (Object) null);
        }
        this.replayIndex = j;
        this.minCollectorIndex = j2;
        this.bufferSize = (int) (newBufferEndIndex - newHead);
        this.queueSize = (int) (newQueueEndIndex - newBufferEndIndex);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((this.bufferSize >= 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((this.queueSize >= 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (this.replayIndex > getHead() + ((long) this.bufferSize)) {
                z = false;
            }
            if (!z) {
                throw new AssertionError();
            }
        }
    }

    private final void cleanupTailLocked() {
        if (this.bufferCapacity != 0 || this.queueSize > 1) {
            Object[] buffer2 = this.buffer;
            Intrinsics.checkNotNull(buffer2);
            while (this.queueSize > 0 && SharedFlowKt.getBufferAt(buffer2, (getHead() + ((long) getTotalSize())) - 1) == SharedFlowKt.NO_VALUE) {
                this.queueSize--;
                SharedFlowKt.setBufferAt(buffer2, getHead() + ((long) getTotalSize()), (Object) null);
            }
        }
    }

    private final Object tryTakeValue(SharedFlowSlot slot) {
        Object obj;
        Continuation[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            long index = tryPeekLocked(slot);
            if (index < 0) {
                obj = SharedFlowKt.NO_VALUE;
            } else {
                long oldIndex = slot.index;
                Object newValue = getPeekedValueLockedAt(index);
                slot.index = 1 + index;
                continuationArr = updateCollectorIndexLocked$kotlinx_coroutines_core(oldIndex);
                obj = newValue;
            }
        }
        Object value = obj;
        int length = continuationArr.length;
        int i = 0;
        while (i < length) {
            Continuation resume = continuationArr[i];
            i++;
            if (resume != null) {
                Result.Companion companion = Result.Companion;
                resume.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
            }
        }
        return value;
    }

    /* access modifiers changed from: private */
    public final long tryPeekLocked(SharedFlowSlot slot) {
        long index = slot.index;
        if (index < getBufferEndIndex()) {
            return index;
        }
        if (this.bufferCapacity <= 0 && index <= getHead() && this.queueSize != 0) {
            return index;
        }
        return -1;
    }

    private final Object getPeekedValueLockedAt(long index) {
        Object[] objArr = this.buffer;
        Intrinsics.checkNotNull(objArr);
        Object item = SharedFlowKt.getBufferAt(objArr, index);
        if (item instanceof Emitter) {
            return ((Emitter) item).value;
        }
        return item;
    }

    /* access modifiers changed from: private */
    public final Object awaitValue(SharedFlowSlot slot, Continuation<? super Unit> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        synchronized (this) {
            if (tryPeekLocked(slot) < 0) {
                slot.cont = cont;
                slot.cont = cont;
            } else {
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m1345constructorimpl(Unit.INSTANCE));
            }
            Unit unit = Unit.INSTANCE;
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX WARNING: type inference failed for: r0v13, types: [java.lang.Object[], java.lang.Object] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.coroutines.Continuation<kotlin.Unit>[] findSlotsToResumeLocked(kotlin.coroutines.Continuation<kotlin.Unit>[] r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = 0
            r1 = r22
            r2 = 0
            r3 = r22
            int r2 = r3.length
            r4 = r0
            kotlinx.coroutines.flow.internal.AbstractSharedFlow r4 = (kotlinx.coroutines.flow.internal.AbstractSharedFlow) r4
            r5 = 0
            int r6 = r4.nCollectors
            if (r6 != 0) goto L_0x0015
            goto L_0x007d
        L_0x0015:
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot[] r6 = r4.slots
            if (r6 != 0) goto L_0x001d
            goto L_0x007c
        L_0x001d:
            r7 = 0
            int r8 = r6.length
            r9 = 0
        L_0x0020:
            if (r9 >= r8) goto L_0x007a
            r10 = r6[r9]
            int r9 = r9 + 1
            r11 = r10
            r12 = 0
            if (r11 == 0) goto L_0x0071
            r13 = r11
            kotlinx.coroutines.flow.SharedFlowSlot r13 = (kotlinx.coroutines.flow.SharedFlowSlot) r13
            r14 = 0
            kotlin.coroutines.Continuation<? super kotlin.Unit> r15 = r13.cont
            if (r15 != 0) goto L_0x0035
            r16 = r1
            goto L_0x0073
        L_0x0035:
            long r16 = r0.tryPeekLocked(r13)
            r18 = 0
            int r20 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r20 >= 0) goto L_0x0042
            r16 = r1
            goto L_0x0073
        L_0x0042:
            r0 = r1
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            int r0 = r0.length
            if (r2 < r0) goto L_0x0063
            r0 = r1
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r3 = r1
            java.lang.Object[] r3 = (java.lang.Object[]) r3
            int r3 = r3.length
            r16 = r1
            r1 = 2
            int r3 = r3 * 2
            int r1 = java.lang.Math.max(r1, r3)
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r0, r1)
            java.lang.String r1 = "copyOf(this, newSize)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            r1 = r0
            goto L_0x0065
        L_0x0063:
            r16 = r1
        L_0x0065:
            r0 = r1
            kotlin.coroutines.Continuation[] r0 = (kotlin.coroutines.Continuation[]) r0
            int r3 = r2 + 1
            r0[r2] = r15
            r0 = 0
            r13.cont = r0
            r2 = r3
            goto L_0x0075
        L_0x0071:
            r16 = r1
        L_0x0073:
            r1 = r16
        L_0x0075:
            r0 = r21
            r3 = r22
            goto L_0x0020
        L_0x007a:
            r16 = r1
        L_0x007c:
        L_0x007d:
            r0 = r1
            kotlin.coroutines.Continuation[] r0 = (kotlin.coroutines.Continuation[]) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.findSlotsToResumeLocked(kotlin.coroutines.Continuation[]):kotlin.coroutines.Continuation[]");
    }

    /* access modifiers changed from: protected */
    public SharedFlowSlot createSlot() {
        return new SharedFlowSlot();
    }

    /* access modifiers changed from: protected */
    public SharedFlowSlot[] createSlotArray(int size) {
        return new SharedFlowSlot[size];
    }

    public void resetReplayCache() {
        synchronized (this) {
            updateBufferLocked(getBufferEndIndex(), this.minCollectorIndex, getBufferEndIndex(), getQueueEndIndex());
            Unit unit = Unit.INSTANCE;
        }
    }

    public Flow<T> fuse(CoroutineContext context, int capacity, BufferOverflow onBufferOverflow2) {
        return SharedFlowKt.fuseSharedFlow(this, context, capacity, onBufferOverflow2);
    }

    @Metadata(mo112d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B1\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\nH\u0016R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00038\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo113d2 = {"Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "Lkotlinx/coroutines/DisposableHandle;", "flow", "Lkotlinx/coroutines/flow/SharedFlowImpl;", "index", "", "value", "", "cont", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/SharedFlowImpl;JLjava/lang/Object;Lkotlin/coroutines/Continuation;)V", "dispose", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: SharedFlow.kt */
    private static final class Emitter implements DisposableHandle {
        public final Continuation<Unit> cont;
        public final SharedFlowImpl<?> flow;
        public long index;
        public final Object value;

        public Emitter(SharedFlowImpl<?> flow2, long index2, Object value2, Continuation<? super Unit> cont2) {
            this.flow = flow2;
            this.index = index2;
            this.value = value2;
            this.cont = cont2;
        }

        public void dispose() {
            this.flow.cancelEmitter(this);
        }
    }
}
