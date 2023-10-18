package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;
import org.slf4j.Marker;

@Metadata(mo112d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 )2\u00020\u0001:\u0001)B%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0010\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\bH\u0002J\u0013\u0010$\u001a\u00020\b2\b\u0010%\u001a\u0004\u0018\u00010&H\u0002J\b\u0010'\u001a\u00020\fH\u0016J\b\u0010(\u001a\u00020\u001eH\u0016J\f\u0010\"\u001a\u00020\u001e*\u00020\u0006H\u0002R\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00058VX\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u000b\u001a\u00020\f8\u0000X\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0007\u001a\u00020\b8VX\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0019R\u001e\u0010\n\u001a\u0004\u0018\u00010\u00018\u0000X\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001a\u0010\u0016\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\u00020\u001e*\u0006\u0012\u0002\b\u00030\u001f8BX\u0004¢\u0006\u0006\u001a\u0004\b \u0010!¨\u0006*"}, mo113d2 = {"Lkotlin/jvm/internal/TypeReference;", "Lkotlin/reflect/KType;", "classifier", "Lkotlin/reflect/KClassifier;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "isMarkedNullable", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Z)V", "platformTypeUpperBound", "flags", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Lkotlin/reflect/KType;I)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lkotlin/reflect/KClassifier;", "getFlags$kotlin_stdlib$annotations", "()V", "getFlags$kotlin_stdlib", "()I", "()Z", "getPlatformTypeUpperBound$kotlin_stdlib$annotations", "getPlatformTypeUpperBound$kotlin_stdlib", "()Lkotlin/reflect/KType;", "arrayClassName", "", "Ljava/lang/Class;", "getArrayClassName", "(Ljava/lang/Class;)Ljava/lang/String;", "asString", "convertPrimitiveToWrapper", "equals", "other", "", "hashCode", "toString", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: TypeReference.kt */
public final class TypeReference implements KType {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final int IS_MARKED_NULLABLE = 1;
    public static final int IS_MUTABLE_COLLECTION_TYPE = 2;
    public static final int IS_NOTHING_TYPE = 4;
    private final List<KTypeProjection> arguments;
    private final KClassifier classifier;
    private final int flags;
    private final KType platformTypeUpperBound;

    @Metadata(mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: TypeReference.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KVariance.values().length];
            iArr[KVariance.INVARIANT.ordinal()] = 1;
            iArr[KVariance.IN.ordinal()] = 2;
            iArr[KVariance.OUT.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ void getFlags$kotlin_stdlib$annotations() {
    }

    public static /* synthetic */ void getPlatformTypeUpperBound$kotlin_stdlib$annotations() {
    }

    public TypeReference(KClassifier classifier2, List<KTypeProjection> arguments2, KType platformTypeUpperBound2, int flags2) {
        Intrinsics.checkNotNullParameter(classifier2, "classifier");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        this.classifier = classifier2;
        this.arguments = arguments2;
        this.platformTypeUpperBound = platformTypeUpperBound2;
        this.flags = flags2;
    }

    public KClassifier getClassifier() {
        return this.classifier;
    }

    public List<KTypeProjection> getArguments() {
        return this.arguments;
    }

    public final KType getPlatformTypeUpperBound$kotlin_stdlib() {
        return this.platformTypeUpperBound;
    }

    public final int getFlags$kotlin_stdlib() {
        return this.flags;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TypeReference(KClassifier classifier2, List<KTypeProjection> arguments2, boolean isMarkedNullable) {
        this(classifier2, arguments2, (KType) null, isMarkedNullable);
        Intrinsics.checkNotNullParameter(classifier2, "classifier");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
    }

    public List<Annotation> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    public boolean isMarkedNullable() {
        return (this.flags & 1) != 0;
    }

    public boolean equals(Object other) {
        return (other instanceof TypeReference) && Intrinsics.areEqual((Object) getClassifier(), (Object) ((TypeReference) other).getClassifier()) && Intrinsics.areEqual((Object) getArguments(), (Object) ((TypeReference) other).getArguments()) && Intrinsics.areEqual((Object) this.platformTypeUpperBound, (Object) ((TypeReference) other).platformTypeUpperBound) && this.flags == ((TypeReference) other).flags;
    }

    public int hashCode() {
        return (((getClassifier().hashCode() * 31) + getArguments().hashCode()) * 31) + Integer.valueOf(this.flags).hashCode();
    }

    public String toString() {
        return asString(false) + " (Kotlin reflection is not available)";
    }

    private final String asString(boolean convertPrimitiveToWrapper) {
        String klass;
        String args;
        KClassifier classifier2 = getClassifier();
        Class cls = null;
        KClass kClass = classifier2 instanceof KClass ? (KClass) classifier2 : null;
        if (kClass != null) {
            cls = JvmClassMappingKt.getJavaClass(kClass);
        }
        Class javaClass = cls;
        if (javaClass == null) {
            klass = getClassifier().toString();
        } else if ((this.flags & 4) != 0) {
            klass = "kotlin.Nothing";
        } else if (javaClass.isArray()) {
            klass = getArrayClassName(javaClass);
        } else if (!convertPrimitiveToWrapper || !javaClass.isPrimitive()) {
            klass = javaClass.getName();
        } else {
            KClassifier classifier3 = getClassifier();
            Intrinsics.checkNotNull(classifier3, "null cannot be cast to non-null type kotlin.reflect.KClass<*>");
            klass = JvmClassMappingKt.getJavaObjectType((KClass) classifier3).getName();
        }
        String nullable = "";
        if (getArguments().isEmpty()) {
            args = nullable;
        } else {
            args = CollectionsKt.joinToString$default(getArguments(), ", ", "<", ">", 0, (CharSequence) null, new TypeReference$asString$args$1(this), 24, (Object) null);
        }
        if (isMarkedNullable()) {
            nullable = "?";
        }
        String result = klass + args + nullable;
        KType upper = this.platformTypeUpperBound;
        if (upper instanceof TypeReference) {
            String renderedUpper = ((TypeReference) upper).asString(true);
            if (!Intrinsics.areEqual((Object) renderedUpper, (Object) result)) {
                if (Intrinsics.areEqual((Object) renderedUpper, (Object) result + '?')) {
                    return result + '!';
                }
                return '(' + result + ".." + renderedUpper + ')';
            }
        }
        return result;
    }

    private final String getArrayClassName(Class<?> $this$arrayClassName) {
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) boolean[].class)) {
            return "kotlin.BooleanArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) char[].class)) {
            return "kotlin.CharArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) byte[].class)) {
            return "kotlin.ByteArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) short[].class)) {
            return "kotlin.ShortArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) int[].class)) {
            return "kotlin.IntArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) float[].class)) {
            return "kotlin.FloatArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) long[].class)) {
            return "kotlin.LongArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) double[].class)) {
            return "kotlin.DoubleArray";
        }
        return "kotlin.Array";
    }

    /* access modifiers changed from: private */
    public final String asString(KTypeProjection $this$asString) {
        String typeString;
        if ($this$asString.getVariance() == null) {
            return Marker.ANY_MARKER;
        }
        KType type = $this$asString.getType();
        TypeReference typeReference = type instanceof TypeReference ? (TypeReference) type : null;
        if (typeReference == null || (typeString = typeReference.asString(true)) == null) {
            typeString = String.valueOf($this$asString.getType());
        }
        switch (WhenMappings.$EnumSwitchMapping$0[$this$asString.getVariance().ordinal()]) {
            case 1:
                return typeString;
            case 2:
                return "in " + typeString;
            case 3:
                return "out " + typeString;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\u0007"}, mo113d2 = {"Lkotlin/jvm/internal/TypeReference$Companion;", "", "()V", "IS_MARKED_NULLABLE", "", "IS_MUTABLE_COLLECTION_TYPE", "IS_NOTHING_TYPE", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: TypeReference.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
