package kotlin;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0004\u001a\u001c\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\b\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a-\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a/\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\bH\b\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\t\u001a@\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\n\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\b\u001a\u001c\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\b\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a-\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a/\u0010\u000f\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\bH\b\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\t\u001a@\u0010\u000f\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\n\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0010"}, mo113d2 = {"check", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "checkNotNull", "T", "(Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "error", "", "message", "require", "requireNotNull", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/PreconditionsKt")
/* compiled from: Preconditions.kt */
class PreconditionsKt__PreconditionsKt extends PreconditionsKt__AssertionsJVMKt {
    private static final void require(boolean value) {
        if (!value) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    private static final void require(boolean value, Function0<? extends Object> lazyMessage) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        if (!value) {
            throw new IllegalArgumentException(lazyMessage.invoke().toString());
        }
    }

    private static final <T> T requireNotNull(T value) {
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException("Required value was null.".toString());
    }

    private static final <T> T requireNotNull(T value, Function0<? extends Object> lazyMessage) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException(lazyMessage.invoke().toString());
    }

    private static final void check(boolean value) {
        if (!value) {
            throw new IllegalStateException("Check failed.".toString());
        }
    }

    private static final void check(boolean value, Function0<? extends Object> lazyMessage) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        if (!value) {
            throw new IllegalStateException(lazyMessage.invoke().toString());
        }
    }

    private static final <T> T checkNotNull(T value) {
        if (value != null) {
            return value;
        }
        throw new IllegalStateException("Required value was null.".toString());
    }

    private static final <T> T checkNotNull(T value, Function0<? extends Object> lazyMessage) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        if (value != null) {
            return value;
        }
        throw new IllegalStateException(lazyMessage.invoke().toString());
    }

    private static final Void error(Object message) {
        Intrinsics.checkNotNullParameter(message, "message");
        throw new IllegalStateException(message.toString());
    }
}
