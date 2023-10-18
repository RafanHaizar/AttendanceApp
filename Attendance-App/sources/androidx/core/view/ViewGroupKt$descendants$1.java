package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@"}, mo113d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Landroid/view/View;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
@DebugMetadata(mo129c = "androidx.core.view.ViewGroupKt$descendants$1", mo130f = "ViewGroup.kt", mo131i = {0, 0, 0, 0, 1, 1, 1}, mo132l = {119, 121}, mo133m = "invokeSuspend", mo134n = {"$this$sequence", "$this$forEach$iv", "child", "index$iv", "$this$sequence", "$this$forEach$iv", "index$iv"}, mo135s = {"L$0", "L$1", "L$2", "I$0", "L$0", "L$1", "I$0"})
/* compiled from: ViewGroup.kt */
final class ViewGroupKt$descendants$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super View>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ViewGroup $this_descendants;
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ViewGroupKt$descendants$1(ViewGroup viewGroup, Continuation<? super ViewGroupKt$descendants$1> continuation) {
        super(2, continuation);
        this.$this_descendants = viewGroup;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ViewGroupKt$descendants$1 viewGroupKt$descendants$1 = new ViewGroupKt$descendants$1(this.$this_descendants, continuation);
        viewGroupKt$descendants$1.L$0 = obj;
        return viewGroupKt$descendants$1;
    }

    public final Object invoke(SequenceScope<? super View> sequenceScope, Continuation<? super Unit> continuation) {
        return ((ViewGroupKt$descendants$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x006b, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x006c, code lost:
        r9 = r3;
        r3 = r5;
        r5 = r7;
        r7 = r8;
        r8 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0075, code lost:
        if ((r7 instanceof android.view.ViewGroup) == false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0077, code lost:
        r10 = androidx.core.view.ViewGroupKt.getDescendants(r7);
        r1.L$0 = r9;
        r1.L$1 = r8;
        r1.L$2 = null;
        r1.I$0 = r6;
        r1.I$1 = r5;
        r1.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0090, code lost:
        if (r9.yieldAll(r10, (kotlin.coroutines.Continuation<? super kotlin.Unit>) r1) != r0) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0092, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0093, code lost:
        r7 = r8;
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0095, code lost:
        r4 = r7;
        r7 = r5;
        r5 = r3;
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x009a, code lost:
        r7 = r5;
        r4 = r8;
        r5 = r3;
        r3 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x009e, code lost:
        r6 = r6 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a3, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x004d, code lost:
        if (r6 >= r7) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x004f, code lost:
        r8 = r4.getChildAt(r6);
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, "getChildAt(index)");
        r1.L$0 = r3;
        r1.L$1 = r4;
        r1.L$2 = r8;
        r1.I$0 = r6;
        r1.I$1 = r7;
        r1.label = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0069, code lost:
        if (r3.yield(r8, r1) != r0) goto L_0x006c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            r13 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r13.label
            r2 = 1
            switch(r1) {
                case 0: goto L_0x003d;
                case 1: goto L_0x0026;
                case 2: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r0)
            throw r14
        L_0x0012:
            r1 = r13
            r3 = 0
            r4 = 0
            int r5 = r1.I$1
            int r6 = r1.I$0
            java.lang.Object r7 = r1.L$1
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            java.lang.Object r8 = r1.L$0
            kotlin.sequences.SequenceScope r8 = (kotlin.sequences.SequenceScope) r8
            kotlin.ResultKt.throwOnFailure(r14)
            goto L_0x0095
        L_0x0026:
            r1 = r13
            r3 = 0
            r4 = 0
            int r5 = r1.I$1
            int r6 = r1.I$0
            java.lang.Object r7 = r1.L$2
            android.view.View r7 = (android.view.View) r7
            java.lang.Object r8 = r1.L$1
            android.view.ViewGroup r8 = (android.view.ViewGroup) r8
            java.lang.Object r9 = r1.L$0
            kotlin.sequences.SequenceScope r9 = (kotlin.sequences.SequenceScope) r9
            kotlin.ResultKt.throwOnFailure(r14)
            goto L_0x0073
        L_0x003d:
            kotlin.ResultKt.throwOnFailure(r14)
            r1 = r13
            java.lang.Object r3 = r1.L$0
            kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
            android.view.ViewGroup r4 = r1.$this_descendants
            r5 = 0
            r6 = 0
            int r7 = r4.getChildCount()
        L_0x004d:
            if (r6 >= r7) goto L_0x00a0
            android.view.View r8 = r4.getChildAt(r6)
            java.lang.String r9 = "getChildAt(index)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, r9)
            r9 = 0
            r1.L$0 = r3
            r1.L$1 = r4
            r1.L$2 = r8
            r1.I$0 = r6
            r1.I$1 = r7
            r1.label = r2
            java.lang.Object r10 = r3.yield(r8, r1)
            if (r10 != r0) goto L_0x006c
            return r0
        L_0x006c:
            r12 = r9
            r9 = r3
            r3 = r5
            r5 = r7
            r7 = r8
            r8 = r4
            r4 = r12
        L_0x0073:
            boolean r10 = r7 instanceof android.view.ViewGroup
            if (r10 == 0) goto L_0x009a
            r10 = r7
            android.view.ViewGroup r10 = (android.view.ViewGroup) r10
            kotlin.sequences.Sequence r10 = androidx.core.view.ViewGroupKt.getDescendants(r10)
            r1.L$0 = r9
            r1.L$1 = r8
            r11 = 0
            r1.L$2 = r11
            r1.I$0 = r6
            r1.I$1 = r5
            r11 = 2
            r1.label = r11
            java.lang.Object r7 = r9.yieldAll(r10, (kotlin.coroutines.Continuation<? super kotlin.Unit>) r1)
            if (r7 != r0) goto L_0x0093
            return r0
        L_0x0093:
            r7 = r8
            r8 = r9
        L_0x0095:
            r4 = r7
            r7 = r5
            r5 = r3
            r3 = r8
            goto L_0x009e
        L_0x009a:
            r7 = r5
            r4 = r8
            r5 = r3
            r3 = r9
        L_0x009e:
            int r6 = r6 + r2
            goto L_0x004d
        L_0x00a0:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.ViewGroupKt$descendants$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
