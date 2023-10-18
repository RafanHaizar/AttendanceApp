package kotlinx.coroutines;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;

@Metadata(mo112d1 = {"\u0000*\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\u001a=\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u001e\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004\"\b\u0012\u0004\u0012\u0002H\u00020\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a%\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0004\"\u00020\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\fH@ø\u0001\u0000¢\u0006\u0002\u0010\r\u001a\u001b\u0010\u0007\u001a\u00020\b*\b\u0012\u0004\u0012\u00020\n0\fH@ø\u0001\u0000¢\u0006\u0002\u0010\r\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, mo113d2 = {"awaitAll", "", "T", "deferreds", "", "Lkotlinx/coroutines/Deferred;", "([Lkotlinx/coroutines/Deferred;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinAll", "", "jobs", "Lkotlinx/coroutines/Job;", "([Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "(Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Await.kt */
public final class AwaitKt {
    public static final <T> Object awaitAll(Deferred<? extends T>[] deferreds, Continuation<? super List<? extends T>> $completion) {
        return deferreds.length == 0 ? CollectionsKt.emptyList() : new AwaitAll(deferreds).await($completion);
    }

    public static final <T> Object awaitAll(Collection<? extends Deferred<? extends T>> $this$awaitAll, Continuation<? super List<? extends T>> $completion) {
        if ($this$awaitAll.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        Object[] array = $this$awaitAll.toArray(new Deferred[0]);
        if (array != null) {
            return new AwaitAll((Deferred[]) array).await($completion);
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object joinAll(kotlinx.coroutines.Job[] r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.AwaitKt$joinAll$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.AwaitKt$joinAll$1 r0 = (kotlinx.coroutines.AwaitKt$joinAll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.AwaitKt$joinAll$1 r0 = new kotlinx.coroutines.AwaitKt$joinAll$1
            r0.<init>(r9)
        L_0x0019:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002d:
            r8 = 0
            r2 = 0
            int r3 = r9.I$1
            int r4 = r9.I$0
            java.lang.Object r5 = r9.L$0
            kotlinx.coroutines.Job[] r5 = (kotlinx.coroutines.Job[]) r5
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0043
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = 0
            int r3 = r8.length
            r4 = 0
            r5 = r8
            r8 = r2
        L_0x0043:
            if (r4 >= r3) goto L_0x005c
            r2 = r5[r4]
            int r4 = r4 + 1
            r6 = 0
            r9.L$0 = r5
            r9.I$0 = r4
            r9.I$1 = r3
            r7 = 1
            r9.label = r7
            java.lang.Object r2 = r2.join(r9)
            if (r2 != r1) goto L_0x005a
            return r1
        L_0x005a:
            r2 = r6
            goto L_0x0043
        L_0x005c:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.AwaitKt.joinAll(kotlinx.coroutines.Job[], kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object joinAll(java.util.Collection<? extends kotlinx.coroutines.Job> r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.AwaitKt$joinAll$3
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.AwaitKt$joinAll$3 r0 = (kotlinx.coroutines.AwaitKt$joinAll$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.AwaitKt$joinAll$3 r0 = new kotlinx.coroutines.AwaitKt$joinAll$3
            r0.<init>(r7)
        L_0x0019:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L_0x0037;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002d:
            r6 = 0
            r2 = 0
            java.lang.Object r3 = r7.L$0
            java.util.Iterator r3 = (java.util.Iterator) r3
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0042
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r0)
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            r2 = 0
            java.util.Iterator r3 = r6.iterator()
            r6 = r2
        L_0x0042:
            boolean r2 = r3.hasNext()
            if (r2 == 0) goto L_0x005d
            java.lang.Object r2 = r3.next()
            kotlinx.coroutines.Job r2 = (kotlinx.coroutines.Job) r2
            r4 = 0
            r7.L$0 = r3
            r5 = 1
            r7.label = r5
            java.lang.Object r2 = r2.join(r7)
            if (r2 != r1) goto L_0x005b
            return r1
        L_0x005b:
            r2 = r4
            goto L_0x0042
        L_0x005d:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.AwaitKt.joinAll(java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
