package kotlin.collections;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

@Metadata(mo112d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H@"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", ""}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlin.collections.SlidingWindowKt$windowedIterator$1", mo130f = "SlidingWindow.kt", mo131i = {0, 0, 0, 2, 2, 3, 3}, mo132l = {34, 40, 49, 55, 58}, mo133m = "invokeSuspend", mo134n = {"$this$iterator", "buffer", "gap", "$this$iterator", "buffer", "$this$iterator", "buffer"}, mo135s = {"L$0", "L$1", "I$0", "L$0", "L$1", "L$0", "L$1"})
/* compiled from: SlidingWindow.kt */
final class SlidingWindowKt$windowedIterator$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super List<? extends T>>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Iterator<T> $iterator;
    final /* synthetic */ boolean $partialWindows;
    final /* synthetic */ boolean $reuseBuffer;
    final /* synthetic */ int $size;
    final /* synthetic */ int $step;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SlidingWindowKt$windowedIterator$1(int i, int i2, Iterator<? extends T> it, boolean z, boolean z2, Continuation<? super SlidingWindowKt$windowedIterator$1> continuation) {
        super(2, continuation);
        this.$size = i;
        this.$step = i2;
        this.$iterator = it;
        this.$reuseBuffer = z;
        this.$partialWindows = z2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        SlidingWindowKt$windowedIterator$1 slidingWindowKt$windowedIterator$1 = new SlidingWindowKt$windowedIterator$1(this.$size, this.$step, this.$iterator, this.$reuseBuffer, this.$partialWindows, continuation);
        slidingWindowKt$windowedIterator$1.L$0 = obj;
        return slidingWindowKt$windowedIterator$1;
    }

    public final Object invoke(SequenceScope<? super List<? extends T>> sequenceScope, Continuation<? super Unit> continuation) {
        return ((SlidingWindowKt$windowedIterator$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0085, code lost:
        if (r5.hasNext() == false) goto L_0x00c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0087, code lost:
        r9 = r5.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x008b, code lost:
        if (r7 <= 0) goto L_0x0090;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x008d, code lost:
        r7 = r7 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0090, code lost:
        r6.add(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0099, code lost:
        if (r6.size() != r0.$size) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x009b, code lost:
        r0.L$0 = r8;
        r0.L$1 = r6;
        r0.L$2 = r5;
        r0.I$0 = r4;
        r0.label = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00ac, code lost:
        if (r8.yield(r6, r0) != r1) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ae, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b1, code lost:
        if (r0.$reuseBuffer == false) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b3, code lost:
        r6.clear();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b7, code lost:
        r6 = new java.util.ArrayList(r0.$size);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00be, code lost:
        r7 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00c8, code lost:
        if ((true ^ r6.isEmpty()) == false) goto L_0x019c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00cc, code lost:
        if (r0.$partialWindows != false) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00d4, code lost:
        if (r6.size() != r0.$size) goto L_0x019c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d6, code lost:
        r0.L$0 = null;
        r0.L$1 = null;
        r0.L$2 = null;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e6, code lost:
        if (r8.yield(r6, r0) != r1) goto L_0x019c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00e8, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00fa, code lost:
        if (r4.hasNext() == false) goto L_0x0141;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00fc, code lost:
        r5.add(r4.next());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0107, code lost:
        if (r5.isFull() == false) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0109, code lost:
        r7 = r5.size();
        r8 = r1.$size;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x010f, code lost:
        if (r7 >= r8) goto L_0x0116;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0111, code lost:
        r5 = r5.expanded(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0118, code lost:
        if (r1.$reuseBuffer == false) goto L_0x011e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x011a, code lost:
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x011e, code lost:
        r7 = new java.util.ArrayList(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0128, code lost:
        r1.L$0 = r6;
        r1.L$1 = r5;
        r1.L$2 = r4;
        r1.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0138, code lost:
        if (r6.yield(r7, r1) != r0) goto L_0x013b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x013a, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x013b, code lost:
        r5.removeFirst(r1.$step);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0143, code lost:
        if (r1.$partialWindows == false) goto L_0x019b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0145, code lost:
        r4 = r5;
        r5 = r6;
        r11 = r1;
        r1 = r0;
        r0 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0150, code lost:
        if (r4.size() <= r0.$step) goto L_0x017d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0154, code lost:
        if (r0.$reuseBuffer == false) goto L_0x015a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0156, code lost:
        r6 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x015a, code lost:
        r6 = new java.util.ArrayList(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0164, code lost:
        r0.L$0 = r5;
        r0.L$1 = r4;
        r0.L$2 = null;
        r0.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0174, code lost:
        if (r5.yield(r6, r0) != r1) goto L_0x0177;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0176, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0177, code lost:
        r4.removeFirst(r0.$step);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0185, code lost:
        if ((true ^ r4.isEmpty()) == false) goto L_0x019c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0187, code lost:
        r0.L$0 = null;
        r0.L$1 = null;
        r0.L$2 = null;
        r0.label = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0197, code lost:
        if (r5.yield(r4, r0) != r1) goto L_0x019c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0199, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x019b, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x019e, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            r13 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r13.label
            r2 = 1
            r3 = 0
            switch(r1) {
                case 0: goto L_0x0059;
                case 1: goto L_0x0042;
                case 2: goto L_0x003c;
                case 3: goto L_0x002a;
                case 4: goto L_0x0019;
                case 5: goto L_0x0013;
                default: goto L_0x000b;
            }
        L_0x000b:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r0)
            throw r14
        L_0x0013:
            r0 = r13
            kotlin.ResultKt.throwOnFailure(r14)
            goto L_0x019a
        L_0x0019:
            r1 = r13
            java.lang.Object r4 = r1.L$1
            kotlin.collections.RingBuffer r4 = (kotlin.collections.RingBuffer) r4
            java.lang.Object r5 = r1.L$0
            kotlin.sequences.SequenceScope r5 = (kotlin.sequences.SequenceScope) r5
            kotlin.ResultKt.throwOnFailure(r14)
            r11 = r1
            r1 = r0
            r0 = r11
            goto L_0x0177
        L_0x002a:
            r1 = r13
            java.lang.Object r4 = r1.L$2
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r1.L$1
            kotlin.collections.RingBuffer r5 = (kotlin.collections.RingBuffer) r5
            java.lang.Object r6 = r1.L$0
            kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
            kotlin.ResultKt.throwOnFailure(r14)
            goto L_0x013b
        L_0x003c:
            r0 = r13
            kotlin.ResultKt.throwOnFailure(r14)
            goto L_0x00e9
        L_0x0042:
            r1 = r13
            int r4 = r1.I$0
            java.lang.Object r5 = r1.L$2
            java.util.Iterator r5 = (java.util.Iterator) r5
            java.lang.Object r6 = r1.L$1
            java.util.ArrayList r6 = (java.util.ArrayList) r6
            java.lang.Object r7 = r1.L$0
            kotlin.sequences.SequenceScope r7 = (kotlin.sequences.SequenceScope) r7
            kotlin.ResultKt.throwOnFailure(r14)
            r8 = r7
            r11 = r1
            r1 = r0
            r0 = r11
            goto L_0x00af
        L_0x0059:
            kotlin.ResultKt.throwOnFailure(r14)
            r1 = r13
            java.lang.Object r4 = r1.L$0
            kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
            int r5 = r1.$size
            r6 = 1024(0x400, float:1.435E-42)
            int r5 = kotlin.ranges.RangesKt.coerceAtMost((int) r5, (int) r6)
            int r6 = r1.$step
            int r7 = r1.$size
            int r6 = r6 - r7
            if (r6 < 0) goto L_0x00eb
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>(r5)
            r5 = r7
            r7 = 0
            java.util.Iterator<T> r8 = r1.$iterator
            r11 = r1
            r1 = r0
            r0 = r11
            r12 = r8
            r8 = r4
            r4 = r6
            r6 = r5
            r5 = r12
        L_0x0081:
            boolean r9 = r5.hasNext()
            if (r9 == 0) goto L_0x00c0
            java.lang.Object r9 = r5.next()
            if (r7 <= 0) goto L_0x0090
            int r7 = r7 + -1
            goto L_0x0081
        L_0x0090:
            r6.add(r9)
            int r9 = r6.size()
            int r10 = r0.$size
            if (r9 != r10) goto L_0x0081
            r7 = r0
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r0.L$0 = r8
            r0.L$1 = r6
            r0.L$2 = r5
            r0.I$0 = r4
            r0.label = r2
            java.lang.Object r7 = r8.yield(r6, r7)
            if (r7 != r1) goto L_0x00af
            return r1
        L_0x00af:
            boolean r7 = r0.$reuseBuffer
            if (r7 == 0) goto L_0x00b7
            r6.clear()
            goto L_0x00be
        L_0x00b7:
            java.util.ArrayList r6 = new java.util.ArrayList
            int r7 = r0.$size
            r6.<init>(r7)
        L_0x00be:
            r7 = r4
            goto L_0x0081
        L_0x00c0:
            r4 = r6
            java.util.Collection r4 = (java.util.Collection) r4
            boolean r4 = r4.isEmpty()
            r2 = r2 ^ r4
            if (r2 == 0) goto L_0x019c
            boolean r2 = r0.$partialWindows
            if (r2 != 0) goto L_0x00d6
            int r2 = r6.size()
            int r4 = r0.$size
            if (r2 != r4) goto L_0x019c
        L_0x00d6:
            r2 = r0
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r0.L$0 = r3
            r0.L$1 = r3
            r0.L$2 = r3
            r3 = 2
            r0.label = r3
            java.lang.Object r2 = r8.yield(r6, r2)
            if (r2 != r1) goto L_0x00e9
            return r1
        L_0x00e9:
            goto L_0x019c
        L_0x00eb:
            kotlin.collections.RingBuffer r6 = new kotlin.collections.RingBuffer
            r6.<init>(r5)
            r5 = r6
            java.util.Iterator<T> r6 = r1.$iterator
            r11 = r6
            r6 = r4
            r4 = r11
        L_0x00f6:
            boolean r7 = r4.hasNext()
            if (r7 == 0) goto L_0x0141
            java.lang.Object r7 = r4.next()
            r5.add(r7)
            boolean r7 = r5.isFull()
            if (r7 == 0) goto L_0x00f6
            int r7 = r5.size()
            int r8 = r1.$size
            if (r7 >= r8) goto L_0x0116
            kotlin.collections.RingBuffer r5 = r5.expanded(r8)
            goto L_0x00f6
        L_0x0116:
            boolean r7 = r1.$reuseBuffer
            if (r7 == 0) goto L_0x011e
            r7 = r5
            java.util.List r7 = (java.util.List) r7
            goto L_0x0128
        L_0x011e:
            java.util.ArrayList r7 = new java.util.ArrayList
            r8 = r5
            java.util.Collection r8 = (java.util.Collection) r8
            r7.<init>(r8)
            java.util.List r7 = (java.util.List) r7
        L_0x0128:
            r8 = r1
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r1.L$0 = r6
            r1.L$1 = r5
            r1.L$2 = r4
            r9 = 3
            r1.label = r9
            java.lang.Object r7 = r6.yield(r7, r8)
            if (r7 != r0) goto L_0x013b
            return r0
        L_0x013b:
            int r7 = r1.$step
            r5.removeFirst(r7)
            goto L_0x00f6
        L_0x0141:
            boolean r4 = r1.$partialWindows
            if (r4 == 0) goto L_0x019b
            r4 = r5
            r5 = r6
            r11 = r1
            r1 = r0
            r0 = r11
        L_0x014a:
            int r6 = r4.size()
            int r7 = r0.$step
            if (r6 <= r7) goto L_0x017d
            boolean r6 = r0.$reuseBuffer
            if (r6 == 0) goto L_0x015a
            r6 = r4
            java.util.List r6 = (java.util.List) r6
            goto L_0x0164
        L_0x015a:
            java.util.ArrayList r6 = new java.util.ArrayList
            r7 = r4
            java.util.Collection r7 = (java.util.Collection) r7
            r6.<init>(r7)
            java.util.List r6 = (java.util.List) r6
        L_0x0164:
            r7 = r0
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r0.L$0 = r5
            r0.L$1 = r4
            r0.L$2 = r3
            r8 = 4
            r0.label = r8
            java.lang.Object r6 = r5.yield(r6, r7)
            if (r6 != r1) goto L_0x0177
            return r1
        L_0x0177:
            int r6 = r0.$step
            r4.removeFirst(r6)
            goto L_0x014a
        L_0x017d:
            r6 = r4
            java.util.Collection r6 = (java.util.Collection) r6
            boolean r6 = r6.isEmpty()
            r2 = r2 ^ r6
            if (r2 == 0) goto L_0x019c
            r2 = r0
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r0.L$0 = r3
            r0.L$1 = r3
            r0.L$2 = r3
            r3 = 5
            r0.label = r3
            java.lang.Object r2 = r5.yield(r4, r2)
            if (r2 != r1) goto L_0x019a
            return r1
        L_0x019a:
            goto L_0x019c
        L_0x019b:
            r0 = r1
        L_0x019c:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.SlidingWindowKt$windowedIterator$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
