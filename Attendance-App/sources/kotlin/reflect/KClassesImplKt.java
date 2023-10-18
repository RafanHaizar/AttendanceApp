package kotlin.reflect;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00028À\u0002X\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, mo113d2 = {"qualifiedOrSimpleName", "", "Lkotlin/reflect/KClass;", "getQualifiedOrSimpleName", "(Lkotlin/reflect/KClass;)Ljava/lang/String;", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: KClassesImpl.kt */
public final class KClassesImplKt {
    public static final String getQualifiedOrSimpleName(KClass<?> $this$qualifiedOrSimpleName) {
        Intrinsics.checkNotNullParameter($this$qualifiedOrSimpleName, "<this>");
        return $this$qualifiedOrSimpleName.getQualifiedName();
    }
}
