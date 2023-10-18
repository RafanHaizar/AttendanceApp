package kotlinx.coroutines.internal;

import java.util.ArrayDeque;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CopyableThrowable;
import kotlinx.coroutines.DebugKt;

@Metadata(mo112d1 = {"\u0000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\u001a\u0014\u0010\u0006\u001a\u00060\u0007j\u0002`\b2\u0006\u0010\t\u001a\u00020\u0001H\u0007\u001a9\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u0002H\u000b2\u0006\u0010\u000e\u001a\u0002H\u000b2\u0010\u0010\u000f\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u0010H\u0002¢\u0006\u0002\u0010\u0011\u001a\u001e\u0010\u0012\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u00102\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0002\u001a1\u0010\u0016\u001a\u00020\u00172\u0010\u0010\u0018\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u00192\u0010\u0010\u000e\u001a\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u0010H\u0002¢\u0006\u0002\u0010\u001a\u001a\u0019\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\fHHø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a+\u0010\u001f\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000b2\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0002¢\u0006\u0002\u0010 \u001a\u001f\u0010!\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000bH\u0000¢\u0006\u0002\u0010\"\u001a,\u0010!\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000b2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030#H\b¢\u0006\u0002\u0010$\u001a!\u0010%\u001a\u0004\u0018\u0001H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000bH\u0002¢\u0006\u0002\u0010\"\u001a \u0010&\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000bH\b¢\u0006\u0002\u0010\"\u001a\u001f\u0010'\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\u001d\u001a\u0002H\u000bH\u0000¢\u0006\u0002\u0010\"\u001a1\u0010(\u001a\u0018\u0012\u0004\u0012\u0002H\u000b\u0012\u000e\u0012\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u00190)\"\b\b\u0000\u0010\u000b*\u00020\f*\u0002H\u000bH\u0002¢\u0006\u0002\u0010*\u001a\u001c\u0010+\u001a\u00020,*\u00060\u0007j\u0002`\b2\n\u0010-\u001a\u00060\u0007j\u0002`\bH\u0002\u001a#\u0010.\u001a\u00020/*\f\u0012\b\u0012\u00060\u0007j\u0002`\b0\u00192\u0006\u00100\u001a\u00020\u0001H\u0002¢\u0006\u0002\u00101\u001a\u0014\u00102\u001a\u00020\u0017*\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0000\u001a\u0010\u00103\u001a\u00020,*\u00060\u0007j\u0002`\bH\u0000\u001a\u001b\u00104\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f*\u0002H\u000bH\u0002¢\u0006\u0002\u0010\"\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0016\u0010\u0002\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0016\u0010\u0005\u001a\n \u0003*\u0004\u0018\u00010\u00010\u0001X\u0004¢\u0006\u0002\n\u0000*\f\b\u0000\u00105\"\u00020\u00142\u00020\u0014*\f\b\u0000\u00106\"\u00020\u00072\u00020\u0007\u0002\u0004\n\u0002\b\u0019¨\u00067"}, mo113d2 = {"baseContinuationImplClass", "", "baseContinuationImplClassName", "kotlin.jvm.PlatformType", "stackTraceRecoveryClass", "stackTraceRecoveryClassName", "artificialFrame", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "message", "createFinalException", "E", "", "cause", "result", "resultStackTrace", "Ljava/util/ArrayDeque;", "(Ljava/lang/Throwable;Ljava/lang/Throwable;Ljava/util/ArrayDeque;)Ljava/lang/Throwable;", "createStackTrace", "continuation", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "mergeRecoveredTraces", "", "recoveredStacktrace", "", "([Ljava/lang/StackTraceElement;Ljava/util/ArrayDeque;)V", "recoverAndThrow", "", "exception", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recoverFromStackFrame", "(Ljava/lang/Throwable;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Ljava/lang/Throwable;", "recoverStackTrace", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Throwable;", "tryCopyAndVerify", "unwrap", "unwrapImpl", "causeAndStacktrace", "Lkotlin/Pair;", "(Ljava/lang/Throwable;)Lkotlin/Pair;", "elementWiseEquals", "", "e", "frameIndex", "", "methodName", "([Ljava/lang/StackTraceElement;Ljava/lang/String;)I", "initCause", "isArtificial", "sanitizeStackTrace", "CoroutineStackFrame", "StackTraceElement", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: StackTraceRecovery.kt */
public final class StackTraceRecoveryKt {
    private static final String baseContinuationImplClass = "kotlin.coroutines.jvm.internal.BaseContinuationImpl";
    private static final String baseContinuationImplClassName;
    private static final String stackTraceRecoveryClass = "kotlinx.coroutines.internal.StackTraceRecoveryKt";
    private static final String stackTraceRecoveryClassName;

    public static /* synthetic */ void CoroutineStackFrame$annotations() {
    }

    public static /* synthetic */ void StackTraceElement$annotations() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v12, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v14, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.String r0 = "kotlinx.coroutines.internal.StackTraceRecoveryKt"
            java.lang.String r1 = "kotlin.coroutines.jvm.internal.BaseContinuationImpl"
            kotlin.Result$Companion r2 = kotlin.Result.Companion     // Catch:{ all -> 0x0014 }
            r2 = 0
            java.lang.Class r3 = java.lang.Class.forName(r1)     // Catch:{ all -> 0x0014 }
            java.lang.String r3 = r3.getCanonicalName()     // Catch:{ all -> 0x0014 }
            java.lang.Object r2 = kotlin.Result.m1345constructorimpl(r3)     // Catch:{ all -> 0x0014 }
            goto L_0x001f
        L_0x0014:
            r2 = move-exception
            kotlin.Result$Companion r3 = kotlin.Result.Companion
            java.lang.Object r2 = kotlin.ResultKt.createFailure(r2)
            java.lang.Object r2 = kotlin.Result.m1345constructorimpl(r2)
        L_0x001f:
            java.lang.Throwable r3 = kotlin.Result.m1348exceptionOrNullimpl(r2)
            if (r3 != 0) goto L_0x0027
            r1 = r2
            goto L_0x002a
        L_0x0027:
            r2 = r3
            r3 = 0
        L_0x002a:
            java.lang.String r1 = (java.lang.String) r1
            baseContinuationImplClassName = r1
            kotlin.Result$Companion r1 = kotlin.Result.Companion     // Catch:{ all -> 0x003f }
            r1 = 0
            java.lang.Class r2 = java.lang.Class.forName(r0)     // Catch:{ all -> 0x003f }
            java.lang.String r2 = r2.getCanonicalName()     // Catch:{ all -> 0x003f }
            java.lang.Object r1 = kotlin.Result.m1345constructorimpl(r2)     // Catch:{ all -> 0x003f }
            goto L_0x004a
        L_0x003f:
            r1 = move-exception
            kotlin.Result$Companion r2 = kotlin.Result.Companion
            java.lang.Object r1 = kotlin.ResultKt.createFailure(r1)
            java.lang.Object r1 = kotlin.Result.m1345constructorimpl(r1)
        L_0x004a:
            java.lang.Throwable r2 = kotlin.Result.m1348exceptionOrNullimpl(r1)
            if (r2 != 0) goto L_0x0052
            r0 = r1
            goto L_0x0055
        L_0x0052:
            r1 = r2
            r2 = 0
        L_0x0055:
            java.lang.String r0 = (java.lang.String) r0
            stackTraceRecoveryClassName = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.StackTraceRecoveryKt.<clinit>():void");
    }

    public static final <E extends Throwable> E recoverStackTrace(E exception) {
        Throwable copy;
        if (DebugKt.getRECOVER_STACK_TRACES() && (copy = tryCopyAndVerify(exception)) != null) {
            return sanitizeStackTrace(copy);
        }
        return exception;
    }

    private static final <E extends Throwable> E sanitizeStackTrace(E $this$sanitizeStackTrace) {
        StackTraceElement stackTraceElement;
        StackTraceElement[] stackTrace = $this$sanitizeStackTrace.getStackTrace();
        int size = stackTrace.length;
        int lastIntrinsic = frameIndex(stackTrace, stackTraceRecoveryClassName);
        int startIndex = lastIntrinsic + 1;
        int endIndex = frameIndex(stackTrace, baseContinuationImplClassName);
        int i = (size - lastIntrinsic) - (endIndex == -1 ? 0 : size - endIndex);
        StackTraceElement[] trace = new StackTraceElement[i];
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 == 0) {
                stackTraceElement = artificialFrame("Coroutine boundary");
            } else {
                stackTraceElement = stackTrace[(startIndex + i2) - 1];
            }
            trace[i2] = stackTraceElement;
        }
        $this$sanitizeStackTrace.setStackTrace(trace);
        return $this$sanitizeStackTrace;
    }

    public static final <E extends Throwable> E recoverStackTrace(E exception, Continuation<?> continuation) {
        if (!DebugKt.getRECOVER_STACK_TRACES() || !(continuation instanceof CoroutineStackFrame)) {
            return exception;
        }
        return recoverFromStackFrame(exception, (CoroutineStackFrame) continuation);
    }

    /* access modifiers changed from: private */
    public static final <E extends Throwable> E recoverFromStackFrame(E exception, CoroutineStackFrame continuation) {
        Pair causeAndStacktrace = causeAndStacktrace(exception);
        Throwable cause = (Throwable) causeAndStacktrace.component1();
        StackTraceElement[] recoveredStacktrace = (StackTraceElement[]) causeAndStacktrace.component2();
        Throwable newException = tryCopyAndVerify(cause);
        if (newException == null) {
            return exception;
        }
        ArrayDeque stacktrace = createStackTrace(continuation);
        if (stacktrace.isEmpty()) {
            return exception;
        }
        if (cause != exception) {
            mergeRecoveredTraces(recoveredStacktrace, stacktrace);
        }
        return createFinalException(cause, newException, stacktrace);
    }

    private static final <E extends Throwable> E tryCopyAndVerify(E exception) {
        Throwable newException = ExceptionsConstructorKt.tryCopyException(exception);
        if (newException == null) {
            return null;
        }
        if ((exception instanceof CopyableThrowable) || Intrinsics.areEqual((Object) newException.getMessage(), (Object) exception.getMessage())) {
            return newException;
        }
        return null;
    }

    private static final <E extends Throwable> E createFinalException(E cause, E result, ArrayDeque<StackTraceElement> resultStackTrace) {
        resultStackTrace.addFirst(artificialFrame("Coroutine boundary"));
        StackTraceElement[] causeTrace = cause.getStackTrace();
        int size = frameIndex(causeTrace, baseContinuationImplClassName);
        int i = 0;
        if (size == -1) {
            Object[] array = resultStackTrace.toArray(new StackTraceElement[0]);
            if (array != null) {
                result.setStackTrace((StackTraceElement[]) array);
                return result;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        StackTraceElement[] mergedStackTrace = new StackTraceElement[(resultStackTrace.size() + size)];
        int i2 = 0;
        while (i2 < size) {
            int i3 = i2;
            i2++;
            mergedStackTrace[i3] = causeTrace[i3];
        }
        Iterator<StackTraceElement> it = resultStackTrace.iterator();
        while (it.hasNext()) {
            int index = i;
            i++;
            mergedStackTrace[size + index] = it.next();
        }
        result.setStackTrace(mergedStackTrace);
        return result;
    }

    private static final <E extends Throwable> Pair<E, StackTraceElement[]> causeAndStacktrace(E $this$causeAndStacktrace) {
        boolean z;
        Throwable cause = $this$causeAndStacktrace.getCause();
        if (cause == null || !Intrinsics.areEqual((Object) cause.getClass(), (Object) $this$causeAndStacktrace.getClass())) {
            return TuplesKt.m281to($this$causeAndStacktrace, new StackTraceElement[0]);
        }
        StackTraceElement[] currentTrace = $this$causeAndStacktrace.getStackTrace();
        StackTraceElement[] stackTraceElementArr = currentTrace;
        int length = stackTraceElementArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            StackTraceElement it = stackTraceElementArr[i];
            i++;
            if (isArtificial(it)) {
                z = true;
                break;
            }
        }
        if (z) {
            return TuplesKt.m281to(cause, currentTrace);
        }
        return TuplesKt.m281to($this$causeAndStacktrace, new StackTraceElement[0]);
    }

    private static final void mergeRecoveredTraces(StackTraceElement[] recoveredStacktrace, ArrayDeque<StackTraceElement> result) {
        int index$iv;
        int i;
        StackTraceElement[] stackTraceElementArr = recoveredStacktrace;
        int length = stackTraceElementArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                index$iv = -1;
                break;
            }
            index$iv = i2;
            i2++;
            if (isArtificial(stackTraceElementArr[index$iv])) {
                break;
            }
        }
        int startIndex = index$iv + 1;
        int lastFrameIndex = recoveredStacktrace.length - 1;
        if (startIndex <= lastFrameIndex) {
            int i3 = lastFrameIndex;
            do {
                i = i3;
                i3--;
                if (elementWiseEquals(recoveredStacktrace[i], result.getLast())) {
                    result.removeLast();
                }
                result.addFirst(recoveredStacktrace[i]);
            } while (i != startIndex);
        }
    }

    public static final Object recoverAndThrow(Throwable exception, Continuation<?> $completion) {
        if (DebugKt.getRECOVER_STACK_TRACES()) {
            Continuation<?> continuation = $completion;
            if (!(continuation instanceof CoroutineStackFrame)) {
                throw exception;
            }
            throw recoverFromStackFrame(exception, (CoroutineStackFrame) continuation);
        }
        throw exception;
    }

    private static final Object recoverAndThrow$$forInline(Throwable exception, Continuation<?> $completion) {
        if (DebugKt.getRECOVER_STACK_TRACES()) {
            InlineMarker.mark(0);
            Continuation<?> continuation = $completion;
            if (!(continuation instanceof CoroutineStackFrame)) {
                throw exception;
            }
            throw recoverFromStackFrame(exception, (CoroutineStackFrame) continuation);
        }
        throw exception;
    }

    public static final <E extends Throwable> E unwrap(E exception) {
        return !DebugKt.getRECOVER_STACK_TRACES() ? exception : unwrapImpl(exception);
    }

    public static final <E extends Throwable> E unwrapImpl(E exception) {
        Throwable cause = exception.getCause();
        if (cause == null || !Intrinsics.areEqual((Object) cause.getClass(), (Object) exception.getClass())) {
            return exception;
        }
        StackTraceElement[] stackTrace = exception.getStackTrace();
        int length = stackTrace.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            StackTraceElement it = stackTrace[i];
            i++;
            if (isArtificial(it)) {
                z = true;
                break;
            }
        }
        if (z) {
            return cause;
        }
        return exception;
    }

    private static final ArrayDeque<StackTraceElement> createStackTrace(CoroutineStackFrame continuation) {
        ArrayDeque stack = new ArrayDeque();
        StackTraceElement it = continuation.getStackTraceElement();
        if (it != null) {
            stack.add(it);
        }
        CoroutineStackFrame last = continuation;
        while (true) {
            CoroutineStackFrame coroutineStackFrame = null;
            CoroutineStackFrame coroutineStackFrame2 = last instanceof CoroutineStackFrame ? last : null;
            if (coroutineStackFrame2 != null) {
                coroutineStackFrame = coroutineStackFrame2.getCallerFrame();
            }
            if (coroutineStackFrame == null) {
                return stack;
            }
            last = coroutineStackFrame;
            StackTraceElement it2 = last.getStackTraceElement();
            if (it2 != null) {
                stack.add(it2);
            }
        }
    }

    public static final StackTraceElement artificialFrame(String message) {
        return new StackTraceElement(Intrinsics.stringPlus("\b\b\b(", message), "\b", "\b", -1);
    }

    public static final boolean isArtificial(StackTraceElement $this$isArtificial) {
        return StringsKt.startsWith$default($this$isArtificial.getClassName(), "\b\b\b", false, 2, (Object) null);
    }

    private static final int frameIndex(StackTraceElement[] $this$frameIndex, String methodName) {
        StackTraceElement[] stackTraceElementArr = $this$frameIndex;
        int length = stackTraceElementArr.length;
        int i = 0;
        while (i < length) {
            int index$iv = i;
            i++;
            if (Intrinsics.areEqual((Object) methodName, (Object) stackTraceElementArr[index$iv].getClassName())) {
                return index$iv;
            }
        }
        return -1;
    }

    private static final boolean elementWiseEquals(StackTraceElement $this$elementWiseEquals, StackTraceElement e) {
        return $this$elementWiseEquals.getLineNumber() == e.getLineNumber() && Intrinsics.areEqual((Object) $this$elementWiseEquals.getMethodName(), (Object) e.getMethodName()) && Intrinsics.areEqual((Object) $this$elementWiseEquals.getFileName(), (Object) e.getFileName()) && Intrinsics.areEqual((Object) $this$elementWiseEquals.getClassName(), (Object) e.getClassName());
    }

    public static final void initCause(Throwable $this$initCause, Throwable cause) {
        $this$initCause.initCause(cause);
    }
}
