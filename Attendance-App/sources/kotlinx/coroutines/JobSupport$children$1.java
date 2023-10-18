package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@"}, mo113d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Lkotlinx/coroutines/Job;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.JobSupport$children$1", mo130f = "JobSupport.kt", mo131i = {1, 1, 1}, mo132l = {952, 954}, mo133m = "invokeSuspend", mo134n = {"$this$sequence", "this_$iv", "cur$iv"}, mo135s = {"L$0", "L$1", "L$2"})
/* compiled from: JobSupport.kt */
final class JobSupport$children$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super Job>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ JobSupport this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    JobSupport$children$1(JobSupport jobSupport, Continuation<? super JobSupport$children$1> continuation) {
        super(2, continuation);
        this.this$0 = jobSupport;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        JobSupport$children$1 jobSupport$children$1 = new JobSupport$children$1(this.this$0, continuation);
        jobSupport$children$1.L$0 = obj;
        return jobSupport$children$1;
    }

    public final Object invoke(SequenceScope<? super Job> sequenceScope, Continuation<? super Unit> continuation) {
        return ((JobSupport$children$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0067, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r1, (java.lang.Object) r2) != false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006b, code lost:
        if ((r1 instanceof kotlinx.coroutines.ChildHandleNode) == false) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006d, code lost:
        r4 = ((kotlinx.coroutines.ChildHandleNode) r1).childJob;
        r8.L$0 = r3;
        r8.L$1 = r2;
        r8.L$2 = r1;
        r8.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007f, code lost:
        if (r3.yield(r4, r8) != r0) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0081, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0083, code lost:
        r1 = r1.getNextNode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008d, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            switch(r1) {
                case 0: goto L_0x0026;
                case 1: goto L_0x0022;
                case 2: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x0011:
            java.lang.Object r1 = r7.L$2
            kotlinx.coroutines.internal.LockFreeLinkedListNode r1 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r1
            java.lang.Object r2 = r7.L$1
            kotlinx.coroutines.internal.LockFreeLinkedListHead r2 = (kotlinx.coroutines.internal.LockFreeLinkedListHead) r2
            java.lang.Object r3 = r7.L$0
            kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
            kotlin.ResultKt.throwOnFailure(r8)
            r8 = r7
            goto L_0x0082
        L_0x0022:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x0048
        L_0x0026:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlin.sequences.SequenceScope r8 = (kotlin.sequences.SequenceScope) r8
            kotlinx.coroutines.JobSupport r1 = r7.this$0
            java.lang.Object r1 = r1.getState$kotlinx_coroutines_core()
            boolean r2 = r1 instanceof kotlinx.coroutines.ChildHandleNode
            if (r2 == 0) goto L_0x0049
            kotlinx.coroutines.ChildHandleNode r1 = (kotlinx.coroutines.ChildHandleNode) r1
            kotlinx.coroutines.ChildJob r1 = r1.childJob
            r2 = r7
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r3 = 1
            r7.label = r3
            java.lang.Object r8 = r8.yield(r1, r2)
            if (r8 != r0) goto L_0x0048
            return r0
        L_0x0048:
            goto L_0x008b
        L_0x0049:
            boolean r2 = r1 instanceof kotlinx.coroutines.Incomplete
            if (r2 == 0) goto L_0x008a
            kotlinx.coroutines.Incomplete r1 = (kotlinx.coroutines.Incomplete) r1
            kotlinx.coroutines.NodeList r1 = r1.getList()
            if (r1 != 0) goto L_0x0056
            goto L_0x008a
        L_0x0056:
            kotlinx.coroutines.internal.LockFreeLinkedListHead r1 = (kotlinx.coroutines.internal.LockFreeLinkedListHead) r1
            java.lang.Object r2 = r1.getNext()
            kotlinx.coroutines.internal.LockFreeLinkedListNode r2 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r2
            r3 = r8
            r8 = r7
            r6 = r2
            r2 = r1
            r1 = r6
        L_0x0063:
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r1, (java.lang.Object) r2)
            if (r4 != 0) goto L_0x0088
            boolean r4 = r1 instanceof kotlinx.coroutines.ChildHandleNode
            if (r4 == 0) goto L_0x0083
            r4 = r1
            kotlinx.coroutines.ChildHandleNode r4 = (kotlinx.coroutines.ChildHandleNode) r4
            kotlinx.coroutines.ChildJob r4 = r4.childJob
            r8.L$0 = r3
            r8.L$1 = r2
            r8.L$2 = r1
            r5 = 2
            r8.label = r5
            java.lang.Object r4 = r3.yield(r4, r8)
            if (r4 != r0) goto L_0x0082
            return r0
        L_0x0082:
        L_0x0083:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r1 = r1.getNextNode()
            goto L_0x0063
        L_0x0088:
        L_0x008a:
        L_0x008b:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport$children$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
