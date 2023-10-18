package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a2\u0010\u0000\u001a\u00020\u00012\u001f\b\u0004\u0010\u0002\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\b\u0006HHø\u0001\u0000¢\u0006\u0002\u0010\u0007\u0002\u0004\n\u0002\b\u0019¨\u0006\b"}, mo113d2 = {"whileSelect", "", "builder", "Lkotlin/Function1;", "Lkotlinx/coroutines/selects/SelectBuilder;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: WhileSelect.kt */
public final class WhileSelectKt {
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x006c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object whileSelect(kotlin.jvm.functions.Function1<? super kotlinx.coroutines.selects.SelectBuilder<? super java.lang.Boolean>, kotlin.Unit> r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.selects.WhileSelectKt$whileSelect$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.selects.WhileSelectKt$whileSelect$1 r0 = (kotlinx.coroutines.selects.WhileSelectKt$whileSelect$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.selects.WhileSelectKt$whileSelect$1 r0 = new kotlinx.coroutines.selects.WhileSelectKt$whileSelect$1
            r0.<init>(r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            switch(r2) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002d:
            r9 = 0
            r2 = 0
            java.lang.Object r3 = r10.L$0
            kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x0073
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = 0
            r3 = r9
            r9 = r2
        L_0x0041:
            r2 = 0
            r10.L$0 = r3
            r4 = 1
            r10.label = r4
            r4 = r10
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 0
            kotlinx.coroutines.selects.SelectBuilderImpl r6 = new kotlinx.coroutines.selects.SelectBuilderImpl
            r6.<init>(r4)
            r3.invoke(r6)     // Catch:{ all -> 0x0056 }
            goto L_0x005a
        L_0x0056:
            r7 = move-exception
            r6.handleBuilderException(r7)
        L_0x005a:
            java.lang.Object r4 = r6.getResult()
            java.lang.Object r5 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r4 != r5) goto L_0x006a
            r5 = r10
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r5)
        L_0x006a:
            if (r4 != r1) goto L_0x006d
            return r1
        L_0x006d:
            r8 = r1
            r1 = r0
            r0 = r4
            r4 = r3
            r3 = r2
            r2 = r8
        L_0x0073:
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0080
            r0 = r1
            r1 = r2
            r3 = r4
            goto L_0x0041
        L_0x0080:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.selects.WhileSelectKt.whileSelect(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final Object whileSelect$$forInline(Function1<? super SelectBuilder<? super Boolean>, Unit> builder, Continuation<? super Unit> $completion) {
        Object result;
        do {
            InlineMarker.mark(0);
            SelectBuilderImpl scope$iv = new SelectBuilderImpl($completion);
            try {
                builder.invoke(scope$iv);
            } catch (Throwable e$iv) {
                scope$iv.handleBuilderException(e$iv);
            }
            result = scope$iv.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            InlineMarker.mark(1);
        } while (((Boolean) result).booleanValue());
        return Unit.INSTANCE;
    }
}
