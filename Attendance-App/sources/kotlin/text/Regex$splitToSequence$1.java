package kotlin.text;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@"}, mo113d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", ""}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlin.text.Regex$splitToSequence$1", mo130f = "Regex.kt", mo131i = {1, 1, 1}, mo132l = {276, 284, 288}, mo133m = "invokeSuspend", mo134n = {"$this$sequence", "matcher", "splitCount"}, mo135s = {"L$0", "L$1", "I$0"})
/* compiled from: Regex.kt */
final class Regex$splitToSequence$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super String>, Continuation<? super Unit>, Object> {
    final /* synthetic */ CharSequence $input;
    final /* synthetic */ int $limit;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ Regex this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    Regex$splitToSequence$1(Regex regex, CharSequence charSequence, int i, Continuation<? super Regex$splitToSequence$1> continuation) {
        super(2, continuation);
        this.this$0 = regex;
        this.$input = charSequence;
        this.$limit = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Regex$splitToSequence$1 regex$splitToSequence$1 = new Regex$splitToSequence$1(this.this$0, this.$input, this.$limit, continuation);
        regex$splitToSequence$1.L$0 = obj;
        return regex$splitToSequence$1;
    }

    public final Object invoke(SequenceScope<? super String> sequenceScope, Continuation<? super Unit> continuation) {
        return ((Regex$splitToSequence$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0052, code lost:
        r1.L$0 = r5;
        r1.L$1 = r4;
        r1.I$0 = r3;
        r1.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0070, code lost:
        if (r5.yield(r1.$input.subSequence(r6, r4.start()).toString(), r1) != r0) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0072, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0073, code lost:
        r6 = r4.end();
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x007b, code lost:
        if (r3 == (r1.$limit - 1)) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0081, code lost:
        if (r4.find() != false) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0083, code lost:
        r2 = r1.$input;
        r1.L$0 = null;
        r1.L$1 = null;
        r1.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a1, code lost:
        if (r5.yield(r2.subSequence(r6, r2.length()).toString(), r1) != r0) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a3, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a4, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a7, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00bd, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 1
            switch(r1) {
                case 0: goto L_0x002d;
                case 1: goto L_0x0027;
                case 2: goto L_0x0018;
                case 3: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x0012:
            r0 = r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x00a5
        L_0x0018:
            r1 = r10
            int r3 = r1.I$0
            java.lang.Object r4 = r1.L$1
            java.util.regex.Matcher r4 = (java.util.regex.Matcher) r4
            java.lang.Object r5 = r1.L$0
            kotlin.sequences.SequenceScope r5 = (kotlin.sequences.SequenceScope) r5
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x0073
        L_0x0027:
            r0 = r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x00bb
        L_0x002d:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            java.lang.Object r3 = r1.L$0
            kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
            kotlin.text.Regex r4 = r1.this$0
            java.util.regex.Pattern r4 = r4.nativePattern
            java.lang.CharSequence r5 = r1.$input
            java.util.regex.Matcher r4 = r4.matcher(r5)
            int r5 = r1.$limit
            if (r5 == r2) goto L_0x00a8
            boolean r5 = r4.find()
            if (r5 != 0) goto L_0x004c
            goto L_0x00a8
        L_0x004c:
            r5 = 0
            r6 = 0
            r9 = r5
            r5 = r3
            r3 = r6
            r6 = r9
        L_0x0052:
            java.lang.CharSequence r7 = r1.$input
            int r8 = r4.start()
            java.lang.CharSequence r7 = r7.subSequence(r6, r8)
            java.lang.String r6 = r7.toString()
            r7 = r1
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r1.L$0 = r5
            r1.L$1 = r4
            r1.I$0 = r3
            r8 = 2
            r1.label = r8
            java.lang.Object r6 = r5.yield(r6, r7)
            if (r6 != r0) goto L_0x0073
            return r0
        L_0x0073:
            int r6 = r4.end()
            int r3 = r3 + r2
            int r7 = r1.$limit
            int r7 = r7 - r2
            if (r3 == r7) goto L_0x0083
            boolean r7 = r4.find()
            if (r7 != 0) goto L_0x0052
        L_0x0083:
            java.lang.CharSequence r2 = r1.$input
            int r3 = r2.length()
            java.lang.CharSequence r2 = r2.subSequence(r6, r3)
            java.lang.String r2 = r2.toString()
            r3 = r1
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r4 = 0
            r1.L$0 = r4
            r1.L$1 = r4
            r4 = 3
            r1.label = r4
            java.lang.Object r2 = r5.yield(r2, r3)
            if (r2 != r0) goto L_0x00a4
            return r0
        L_0x00a4:
            r0 = r1
        L_0x00a5:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x00a8:
            java.lang.CharSequence r4 = r1.$input
            java.lang.String r4 = r4.toString()
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.label = r2
            java.lang.Object r2 = r3.yield(r4, r5)
            if (r2 != r0) goto L_0x00ba
            return r0
        L_0x00ba:
            r0 = r1
        L_0x00bb:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.Regex$splitToSequence$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
