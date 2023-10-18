package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\u0004H\u0016R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0001X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, mo113d2 = {"Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "stackTraceElement", "Ljava/lang/StackTraceElement;", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;Ljava/lang/StackTraceElement;)V", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getStackTraceElement", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: StackTraceFrame.kt */
public final class StackTraceFrame implements CoroutineStackFrame {
    private final CoroutineStackFrame callerFrame;
    private final StackTraceElement stackTraceElement;

    public StackTraceFrame(CoroutineStackFrame callerFrame2, StackTraceElement stackTraceElement2) {
        this.callerFrame = callerFrame2;
        this.stackTraceElement = stackTraceElement2;
    }

    public CoroutineStackFrame getCallerFrame() {
        return this.callerFrame;
    }

    public StackTraceElement getStackTraceElement() {
        return this.stackTraceElement;
    }
}
