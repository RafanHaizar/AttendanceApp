package kotlinx.coroutines.channels;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J\u0011\u0010\u0003\u001a\u00020\u0004H¦Bø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\u0007J\u0013\u0010\b\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\t"}, mo113d2 = {"Lkotlinx/coroutines/channels/ChannelIterator;", "E", "", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "next", "()Ljava/lang/Object;", "next0", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Channel.kt */
public interface ChannelIterator<E> {
    Object hasNext(Continuation<? super Boolean> continuation);

    E next();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
    /* synthetic */ Object next(Continuation continuation);

    @Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Channel.kt */
    public static final class DefaultImpls {
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x004d  */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x0052  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
        @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static /* synthetic */ java.lang.Object next(kotlinx.coroutines.channels.ChannelIterator r3, kotlin.coroutines.Continuation r4) {
            /*
                boolean r0 = r4 instanceof kotlinx.coroutines.channels.ChannelIterator$next0$1
                if (r0 == 0) goto L_0x0014
                r0 = r4
                kotlinx.coroutines.channels.ChannelIterator$next0$1 r0 = (kotlinx.coroutines.channels.ChannelIterator$next0$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r1 = r1 & r2
                if (r1 == 0) goto L_0x0014
                int r4 = r0.label
                int r4 = r4 - r2
                r0.label = r4
                goto L_0x0019
            L_0x0014:
                kotlinx.coroutines.channels.ChannelIterator$next0$1 r0 = new kotlinx.coroutines.channels.ChannelIterator$next0$1
                r0.<init>(r4)
            L_0x0019:
                r4 = r0
                java.lang.Object r0 = r4.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r4.label
                switch(r2) {
                    case 0: goto L_0x0036;
                    case 1: goto L_0x002d;
                    default: goto L_0x0025;
                }
            L_0x0025:
                java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                r3.<init>(r4)
                throw r3
            L_0x002d:
                java.lang.Object r3 = r4.L$0
                kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
                kotlin.ResultKt.throwOnFailure(r0)
                r2 = r0
                goto L_0x0045
            L_0x0036:
                kotlin.ResultKt.throwOnFailure(r0)
                r4.L$0 = r3
                r2 = 1
                r4.label = r2
                java.lang.Object r2 = r3.hasNext(r4)
                if (r2 != r1) goto L_0x0045
                return r1
            L_0x0045:
                java.lang.Boolean r2 = (java.lang.Boolean) r2
                boolean r1 = r2.booleanValue()
                if (r1 == 0) goto L_0x0052
                java.lang.Object r1 = r3.next()
                return r1
            L_0x0052:
                kotlinx.coroutines.channels.ClosedReceiveChannelException r1 = new kotlinx.coroutines.channels.ClosedReceiveChannelException
                java.lang.String r2 = "Channel was closed"
                r1.<init>(r2)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelIterator.DefaultImpls.next(kotlinx.coroutines.channels.ChannelIterator, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }
}
