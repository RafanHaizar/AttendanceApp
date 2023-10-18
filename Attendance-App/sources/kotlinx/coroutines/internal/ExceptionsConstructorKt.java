package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlinx.coroutines.CopyableThrowable;

@Metadata(mo112d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a2\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005j\u0002`\u0007\"\b\b\u0000\u0010\b*\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\nH\u0002\u001a*\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0005j\u0004\u0018\u0001`\u00072\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0002\u001a1\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005j\u0002`\u00072\u0014\b\u0004\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H\b\u001a!\u0010\u0010\u001a\u0004\u0018\u0001H\b\"\b\b\u0000\u0010\b*\u00020\u00062\u0006\u0010\u0011\u001a\u0002H\bH\u0000¢\u0006\u0002\u0010\u0012\u001a\u001b\u0010\u0013\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\n2\b\b\u0002\u0010\u0014\u001a\u00020\u0003H\u0010\u001a\u0018\u0010\u0015\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u0016\u001a\u00020\u0003H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000*(\b\u0002\u0010\u0017\"\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00052\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¨\u0006\u0018"}, mo113d2 = {"ctorCache", "Lkotlinx/coroutines/internal/CtorCache;", "throwableFields", "", "createConstructor", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/Ctor;", "E", "clz", "Ljava/lang/Class;", "createSafeConstructor", "constructor", "Ljava/lang/reflect/Constructor;", "safeCtor", "block", "tryCopyException", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "fieldsCount", "accumulator", "fieldsCountOrDefault", "defaultValue", "Ctor", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ExceptionsConstructor.kt */
public final class ExceptionsConstructorKt {
    private static final CtorCache ctorCache;
    private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);

    static {
        CtorCache ctorCache2;
        try {
            if (FastServiceLoaderKt.getANDROID_DETECTED()) {
                ctorCache2 = WeakMapCtorCache.INSTANCE;
            } else {
                ctorCache2 = ClassValueCtorCache.INSTANCE;
            }
        } catch (Throwable th) {
            ctorCache2 = WeakMapCtorCache.INSTANCE;
        }
        ctorCache = ctorCache2;
    }

    public static final <E extends Throwable> E tryCopyException(E exception) {
        E e;
        if (!(exception instanceof CopyableThrowable)) {
            return (Throwable) ctorCache.get(exception.getClass()).invoke(exception);
        }
        try {
            Result.Companion companion = Result.Companion;
            e = Result.m1345constructorimpl(((CopyableThrowable) exception).createCopy());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            e = Result.m1345constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1351isFailureimpl(e)) {
            e = null;
        }
        return (Throwable) e;
    }

    /* access modifiers changed from: private */
    public static final <E extends Throwable> Function1<Throwable, Throwable> createConstructor(Class<E> clz) {
        Function1 nullResult = ExceptionsConstructorKt$createConstructor$nullResult$1.INSTANCE;
        if (throwableFields != fieldsCountOrDefault(clz, 0)) {
            return nullResult;
        }
        for (Constructor constructor : ArraysKt.sortedWith((T[]) clz.getConstructors(), new C0096x1af7bbab())) {
            Function1 result = createSafeConstructor(constructor);
            if (result != null) {
                return result;
            }
        }
        return nullResult;
    }

    private static final Function1<Throwable, Throwable> createSafeConstructor(Constructor<?> constructor) {
        Class[] p = constructor.getParameterTypes();
        switch (p.length) {
            case 0:
                return new C0100x18164156(constructor);
            case 1:
                Class cls = p[0];
                if (Intrinsics.areEqual((Object) cls, (Object) Throwable.class)) {
                    return new C0098x18164154(constructor);
                }
                if (Intrinsics.areEqual((Object) cls, (Object) String.class)) {
                    return new C0099x18164155(constructor);
                }
                return null;
            case 2:
                if (!Intrinsics.areEqual((Object) p[0], (Object) String.class) || !Intrinsics.areEqual((Object) p[1], (Object) Throwable.class)) {
                    return null;
                }
                return new C0097x18164153(constructor);
            default:
                return null;
        }
    }

    private static final Function1<Throwable, Throwable> safeCtor(Function1<? super Throwable, ? extends Throwable> block) {
        return new ExceptionsConstructorKt$safeCtor$1(block);
    }

    private static final int fieldsCountOrDefault(Class<?> $this$fieldsCountOrDefault, int defaultValue) {
        Integer num;
        KClass<?> kotlinClass = JvmClassMappingKt.getKotlinClass($this$fieldsCountOrDefault);
        try {
            Result.Companion companion = Result.Companion;
            num = Result.m1345constructorimpl(Integer.valueOf(fieldsCount$default($this$fieldsCountOrDefault, 0, 1, (Object) null)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            num = Result.m1345constructorimpl(ResultKt.createFailure(th));
        }
        Integer valueOf = Integer.valueOf(defaultValue);
        if (Result.m1351isFailureimpl(num)) {
            num = valueOf;
        }
        return ((Number) num).intValue();
    }

    private static final int fieldsCount(Class<?> $this$fieldsCount, int accumulator) {
        Class<?> cls = $this$fieldsCount;
        int totalFields = accumulator;
        do {
            Field[] declaredFields = cls.getDeclaredFields();
            int count$iv = 0;
            int length = declaredFields.length;
            int i = 0;
            while (i < length) {
                Field it = declaredFields[i];
                i++;
                if (!Modifier.isStatic(it.getModifiers())) {
                    count$iv++;
                }
            }
            totalFields += count$iv;
            cls = cls.getSuperclass();
        } while (cls != null);
        return totalFields;
    }

    static /* synthetic */ int fieldsCount$default(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return fieldsCount(cls, i);
    }
}
