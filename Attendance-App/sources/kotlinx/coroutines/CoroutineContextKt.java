package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(mo112d1 = {"\u0000>\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a \u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u0002\u001a8\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\f0\u0012H\b¢\u0006\u0002\u0010\u0013\u001a4\u0010\u0014\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\u0006\u0010\u0015\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\f0\u0012H\b¢\u0006\u0002\u0010\u0016\u001a\f\u0010\u0017\u001a\u00020\n*\u00020\u0003H\u0002\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003H\u0007\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u0003H\u0007\u001a\u0013\u0010\u001b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001c*\u00020\u001dH\u0010\u001a(\u0010\u001e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001c*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0015\u001a\u00020\u00032\b\u0010\u001f\u001a\u0004\u0018\u00010\u0010H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00038@X\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006 "}, mo113d2 = {"DEBUG_THREAD_NAME_SEPARATOR", "", "coroutineName", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineName", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/String;", "foldCopies", "originalContext", "appendContext", "isNewCoroutine", "", "withContinuationContext", "T", "continuation", "Lkotlin/coroutines/Continuation;", "countOrElement", "", "block", "Lkotlin/Function0;", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withCoroutineContext", "context", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "hasCopyableElements", "newCoroutineContext", "addedContext", "Lkotlinx/coroutines/CoroutineScope;", "undispatchedCompletion", "Lkotlinx/coroutines/UndispatchedCoroutine;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "updateUndispatchedCompletion", "oldValue", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CoroutineContext.kt */
public final class CoroutineContextKt {
    private static final String DEBUG_THREAD_NAME_SEPARATOR = " @";

    public static final CoroutineContext newCoroutineContext(CoroutineScope $this$newCoroutineContext, CoroutineContext context) {
        CoroutineContext combined = foldCopies($this$newCoroutineContext.getCoroutineContext(), context, true);
        CoroutineContext debug = DebugKt.getDEBUG() ? combined.plus(new CoroutineId(DebugKt.getCOROUTINE_ID().incrementAndGet())) : combined;
        return (combined == Dispatchers.getDefault() || combined.get(ContinuationInterceptor.Key) != null) ? debug : debug.plus(Dispatchers.getDefault());
    }

    public static final CoroutineContext newCoroutineContext(CoroutineContext $this$newCoroutineContext, CoroutineContext addedContext) {
        if (!hasCopyableElements(addedContext)) {
            return $this$newCoroutineContext.plus(addedContext);
        }
        return foldCopies($this$newCoroutineContext, addedContext, false);
    }

    private static final boolean hasCopyableElements(CoroutineContext $this$hasCopyableElements) {
        return ((Boolean) $this$hasCopyableElements.fold(false, CoroutineContextKt$hasCopyableElements$1.INSTANCE)).booleanValue();
    }

    private static final CoroutineContext foldCopies(CoroutineContext originalContext, CoroutineContext appendContext, boolean isNewCoroutine) {
        boolean hasElementsLeft = hasCopyableElements(originalContext);
        boolean hasElementsRight = hasCopyableElements(appendContext);
        if (!hasElementsLeft && !hasElementsRight) {
            return originalContext.plus(appendContext);
        }
        Ref.ObjectRef leftoverContext = new Ref.ObjectRef();
        leftoverContext.element = appendContext;
        CoroutineContext folded = (CoroutineContext) originalContext.fold(EmptyCoroutineContext.INSTANCE, new CoroutineContextKt$foldCopies$folded$1(leftoverContext, isNewCoroutine));
        if (hasElementsRight) {
            leftoverContext.element = ((CoroutineContext) leftoverContext.element).fold(EmptyCoroutineContext.INSTANCE, CoroutineContextKt$foldCopies$1.INSTANCE);
        }
        return folded.plus((CoroutineContext) leftoverContext.element);
    }

    public static final <T> T withCoroutineContext(CoroutineContext context, Object countOrElement, Function0<? extends T> block) {
        Object oldValue = ThreadContextKt.updateThreadContext(context, countOrElement);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            ThreadContextKt.restoreThreadContext(context, oldValue);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final <T> T withContinuationContext(Continuation<?> continuation, Object countOrElement, Function0<? extends T> block) {
        UndispatchedCoroutine<?> undispatchedCoroutine;
        CoroutineContext context = continuation.getContext();
        Object oldValue = ThreadContextKt.updateThreadContext(context, countOrElement);
        if (oldValue != ThreadContextKt.NO_THREAD_ELEMENTS) {
            undispatchedCoroutine = updateUndispatchedCompletion(continuation, context, oldValue);
        } else {
            undispatchedCoroutine = null;
            UndispatchedCoroutine undispatchedCoroutine2 = undispatchedCoroutine;
        }
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            if (undispatchedCoroutine == null || undispatchedCoroutine.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, oldValue);
            }
            InlineMarker.finallyEnd(1);
        }
    }

    public static final UndispatchedCoroutine<?> updateUndispatchedCompletion(Continuation<?> $this$updateUndispatchedCompletion, CoroutineContext context, Object oldValue) {
        if (!($this$updateUndispatchedCompletion instanceof CoroutineStackFrame)) {
            return null;
        }
        if (!(context.get(UndispatchedMarker.INSTANCE) != null)) {
            return null;
        }
        UndispatchedCoroutine completion = undispatchedCompletion((CoroutineStackFrame) $this$updateUndispatchedCompletion);
        if (completion != null) {
            completion.saveThreadContext(context, oldValue);
        }
        return completion;
    }

    public static final UndispatchedCoroutine<?> undispatchedCompletion(CoroutineStackFrame $this$undispatchedCompletion) {
        CoroutineStackFrame completion = $this$undispatchedCompletion;
        while (!(completion instanceof DispatchedCoroutine) && (completion = completion.getCallerFrame()) != null) {
            if (completion instanceof UndispatchedCoroutine) {
                return (UndispatchedCoroutine) completion;
            }
        }
        return null;
    }

    public static final String getCoroutineName(CoroutineContext $this$coroutineName) {
        CoroutineId coroutineId;
        String name;
        if (!DebugKt.getDEBUG() || (coroutineId = (CoroutineId) $this$coroutineName.get(CoroutineId.Key)) == null) {
            return null;
        }
        CoroutineName coroutineName = (CoroutineName) $this$coroutineName.get(CoroutineName.Key);
        String coroutineName2 = "coroutine";
        if (!(coroutineName == null || (name = coroutineName.getName()) == null)) {
            coroutineName2 = name;
        }
        return coroutineName2 + '#' + coroutineId.getId();
    }
}
