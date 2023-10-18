package kotlin.jvm.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Function;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.text.StringsKt;
import kotlin.text.Typography;

@Metadata(mo112d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 O2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001OB\u0011\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010F\u001a\u00020\u00122\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0002J\b\u0010H\u001a\u00020IH\u0002J\b\u0010J\u001a\u00020KH\u0016J\u0012\u0010L\u001a\u00020\u00122\b\u0010M\u001a\u0004\u0018\u00010\u0002H\u0017J\b\u0010N\u001a\u000201H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000e0\r8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u00128VX\u0004¢\u0006\f\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u00128VX\u0004¢\u0006\f\u0012\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u0015R\u001a\u0010\u0018\u001a\u00020\u00128VX\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u0018\u0010\u0015R\u001a\u0010\u001a\u001a\u00020\u00128VX\u0004¢\u0006\f\u0012\u0004\b\u001b\u0010\u0014\u001a\u0004\b\u001a\u0010\u0015R\u001a\u0010\u001c\u001a\u00020\u00128VX\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u0014\u001a\u0004\b\u001c\u0010\u0015R\u001a\u0010\u001e\u001a\u00020\u00128VX\u0004¢\u0006\f\u0012\u0004\b\u001f\u0010\u0014\u001a\u0004\b\u001e\u0010\u0015R\u001a\u0010 \u001a\u00020\u00128VX\u0004¢\u0006\f\u0012\u0004\b!\u0010\u0014\u001a\u0004\b \u0010\u0015R\u001a\u0010\"\u001a\u00020\u00128VX\u0004¢\u0006\f\u0012\u0004\b#\u0010\u0014\u001a\u0004\b\"\u0010\u0015R\u001a\u0010$\u001a\u00020\u00128VX\u0004¢\u0006\f\u0012\u0004\b%\u0010\u0014\u001a\u0004\b$\u0010\u0015R\u0018\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u001e\u0010(\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030)0\r8VX\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\u0010R\u001e\u0010+\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\r8VX\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u0010R\u0016\u0010-\u001a\u0004\u0018\u00010\u00028VX\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0016\u00100\u001a\u0004\u0018\u0001018VX\u0004¢\u0006\u0006\u001a\u0004\b2\u00103R(\u00104\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00010\b8VX\u0004¢\u0006\f\u0012\u0004\b5\u0010\u0014\u001a\u0004\b6\u0010\u000bR\u0016\u00107\u001a\u0004\u0018\u0001018VX\u0004¢\u0006\u0006\u001a\u0004\b8\u00103R \u00109\u001a\b\u0012\u0004\u0012\u00020:0\b8VX\u0004¢\u0006\f\u0012\u0004\b;\u0010\u0014\u001a\u0004\b<\u0010\u000bR \u0010=\u001a\b\u0012\u0004\u0012\u00020>0\b8VX\u0004¢\u0006\f\u0012\u0004\b?\u0010\u0014\u001a\u0004\b@\u0010\u000bR\u001c\u0010A\u001a\u0004\u0018\u00010B8VX\u0004¢\u0006\f\u0012\u0004\bC\u0010\u0014\u001a\u0004\bD\u0010E¨\u0006P"}, mo113d2 = {"Lkotlin/jvm/internal/ClassReference;", "Lkotlin/reflect/KClass;", "", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isCompanion", "isCompanion$annotations", "isData", "isData$annotations", "isFinal", "isFinal$annotations", "isFun", "isFun$annotations", "isInner", "isInner$annotations", "isOpen", "isOpen$annotations", "isSealed", "isSealed$annotations", "isValue", "isValue$annotations", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "getSealedSubclasses$annotations", "getSealedSubclasses", "simpleName", "getSimpleName", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes$annotations", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters$annotations", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "error", "", "hashCode", "", "isInstance", "value", "toString", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ClassReference.kt */
public final class ClassReference implements KClass<Object>, ClassBasedDeclarationContainer {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
    /* access modifiers changed from: private */
    public static final HashMap<String, String> classFqNames;
    private static final HashMap<String, String> primitiveFqNames;
    private static final HashMap<String, String> primitiveWrapperFqNames;
    /* access modifiers changed from: private */
    public static final Map<String, String> simpleNames;
    private final Class<?> jClass;

    public static /* synthetic */ void getSealedSubclasses$annotations() {
    }

    public static /* synthetic */ void getSupertypes$annotations() {
    }

    public static /* synthetic */ void getTypeParameters$annotations() {
    }

    public static /* synthetic */ void getVisibility$annotations() {
    }

    public static /* synthetic */ void isAbstract$annotations() {
    }

    public static /* synthetic */ void isCompanion$annotations() {
    }

    public static /* synthetic */ void isData$annotations() {
    }

    public static /* synthetic */ void isFinal$annotations() {
    }

    public static /* synthetic */ void isFun$annotations() {
    }

    public static /* synthetic */ void isInner$annotations() {
    }

    public static /* synthetic */ void isOpen$annotations() {
    }

    public static /* synthetic */ void isSealed$annotations() {
    }

    public static /* synthetic */ void isValue$annotations() {
    }

    public ClassReference(Class<?> jClass2) {
        Intrinsics.checkNotNullParameter(jClass2, "jClass");
        this.jClass = jClass2;
    }

    public Class<?> getJClass() {
        return this.jClass;
    }

    public String getSimpleName() {
        return Companion.getClassSimpleName(getJClass());
    }

    public String getQualifiedName() {
        return Companion.getClassQualifiedName(getJClass());
    }

    public Collection<KCallable<?>> getMembers() {
        error();
        throw new KotlinNothingValueException();
    }

    public Collection<KFunction<Object>> getConstructors() {
        error();
        throw new KotlinNothingValueException();
    }

    public Collection<KClass<?>> getNestedClasses() {
        error();
        throw new KotlinNothingValueException();
    }

    public List<Annotation> getAnnotations() {
        error();
        throw new KotlinNothingValueException();
    }

    public Object getObjectInstance() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isInstance(Object value) {
        return Companion.isInstance(value, getJClass());
    }

    public List<KTypeParameter> getTypeParameters() {
        error();
        throw new KotlinNothingValueException();
    }

    public List<KType> getSupertypes() {
        error();
        throw new KotlinNothingValueException();
    }

    public List<KClass<? extends Object>> getSealedSubclasses() {
        error();
        throw new KotlinNothingValueException();
    }

    public KVisibility getVisibility() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isFinal() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isOpen() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isAbstract() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isSealed() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isData() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isInner() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isCompanion() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isFun() {
        error();
        throw new KotlinNothingValueException();
    }

    public boolean isValue() {
        error();
        throw new KotlinNothingValueException();
    }

    private final Void error() {
        throw new KotlinReflectionNotSupportedError();
    }

    public boolean equals(Object other) {
        return (other instanceof ClassReference) && Intrinsics.areEqual((Object) JvmClassMappingKt.getJavaObjectType(this), (Object) JvmClassMappingKt.getJavaObjectType((KClass) other));
    }

    public int hashCode() {
        return JvmClassMappingKt.getJavaObjectType(this).hashCode();
    }

    public String toString() {
        return getJClass().toString() + " (Kotlin reflection is not available)";
    }

    @Metadata(mo112d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u001c\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00012\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005R&\u0010\u0003\u001a\u001a\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004X\u0004¢\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0004¢\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0004¢\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, mo113d2 = {"Lkotlin/jvm/internal/ClassReference$Companion;", "", "()V", "FUNCTION_CLASSES", "", "Ljava/lang/Class;", "Lkotlin/Function;", "", "classFqNames", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "primitiveFqNames", "primitiveWrapperFqNames", "simpleNames", "getClassQualifiedName", "jClass", "getClassSimpleName", "isInstance", "", "value", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: ClassReference.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getClassSimpleName(Class<?> jClass) {
            String str;
            Intrinsics.checkNotNullParameter(jClass, "jClass");
            String str2 = null;
            if (jClass.isAnonymousClass()) {
                return null;
            }
            if (jClass.isLocalClass()) {
                String name = jClass.getSimpleName();
                Method method = jClass.getEnclosingMethod();
                if (method != null) {
                    Intrinsics.checkNotNullExpressionValue(name, XfdfConstants.NAME);
                    String substringAfter$default = StringsKt.substringAfter$default(name, method.getName() + Typography.dollar, (String) null, 2, (Object) null);
                    if (substringAfter$default != null) {
                        return substringAfter$default;
                    }
                }
                Constructor constructor = jClass.getEnclosingConstructor();
                if (constructor != null) {
                    Intrinsics.checkNotNullExpressionValue(name, XfdfConstants.NAME);
                    return StringsKt.substringAfter$default(name, constructor.getName() + Typography.dollar, (String) null, 2, (Object) null);
                }
                Intrinsics.checkNotNullExpressionValue(name, XfdfConstants.NAME);
                return StringsKt.substringAfter$default(name, (char) Typography.dollar, (String) null, 2, (Object) null);
            } else if (jClass.isArray()) {
                Class componentType = jClass.getComponentType();
                if (componentType.isPrimitive() && (str = (String) ClassReference.simpleNames.get(componentType.getName())) != null) {
                    str2 = str + "Array";
                }
                if (str2 == null) {
                    return "Array";
                }
                return str2;
            } else {
                String str3 = (String) ClassReference.simpleNames.get(jClass.getName());
                if (str3 == null) {
                    return jClass.getSimpleName();
                }
                return str3;
            }
        }

        public final String getClassQualifiedName(Class<?> jClass) {
            String str;
            Intrinsics.checkNotNullParameter(jClass, "jClass");
            String str2 = null;
            if (jClass.isAnonymousClass() || jClass.isLocalClass()) {
                return null;
            }
            if (jClass.isArray()) {
                Class componentType = jClass.getComponentType();
                if (componentType.isPrimitive() && (str = (String) ClassReference.classFqNames.get(componentType.getName())) != null) {
                    str2 = str + "Array";
                }
                if (str2 == null) {
                    return "kotlin.Array";
                }
                return str2;
            }
            String str3 = (String) ClassReference.classFqNames.get(jClass.getName());
            if (str3 == null) {
                return jClass.getCanonicalName();
            }
            return str3;
        }

        public final boolean isInstance(Object value, Class<?> jClass) {
            Intrinsics.checkNotNullParameter(jClass, "jClass");
            Map access$getFUNCTION_CLASSES$cp = ClassReference.FUNCTION_CLASSES;
            Intrinsics.checkNotNull(access$getFUNCTION_CLASSES$cp, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.get, V of kotlin.collections.MapsKt__MapsKt.get>");
            Integer num = (Integer) access$getFUNCTION_CLASSES$cp.get(jClass);
            if (num != null) {
                return TypeIntrinsics.isFunctionOfArity(value, num.intValue());
            }
            return (jClass.isPrimitive() != 0 ? JvmClassMappingKt.getJavaObjectType(JvmClassMappingKt.getKotlinClass(jClass)) : jClass).isInstance(value);
        }
    }

    static {
        Iterable $this$mapIndexed$iv = CollectionsKt.listOf(Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexed$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            destination$iv$iv.add(TuplesKt.m281to((Class) item$iv$iv, Integer.valueOf(index$iv$iv)));
            index$iv$iv = index$iv$iv2;
        }
        FUNCTION_CLASSES = MapsKt.toMap((List) destination$iv$iv);
        HashMap hashMap = new HashMap();
        HashMap $this$primitiveFqNames_u24lambda_u2d1 = hashMap;
        $this$primitiveFqNames_u24lambda_u2d1.put(TypedValues.Custom.S_BOOLEAN, "kotlin.Boolean");
        $this$primitiveFqNames_u24lambda_u2d1.put("char", "kotlin.Char");
        $this$primitiveFqNames_u24lambda_u2d1.put("byte", "kotlin.Byte");
        $this$primitiveFqNames_u24lambda_u2d1.put("short", "kotlin.Short");
        $this$primitiveFqNames_u24lambda_u2d1.put("int", "kotlin.Int");
        $this$primitiveFqNames_u24lambda_u2d1.put("float", "kotlin.Float");
        $this$primitiveFqNames_u24lambda_u2d1.put("long", "kotlin.Long");
        $this$primitiveFqNames_u24lambda_u2d1.put(CommonCssConstants.DOUBLE, "kotlin.Double");
        primitiveFqNames = hashMap;
        HashMap hashMap2 = new HashMap();
        HashMap $this$primitiveWrapperFqNames_u24lambda_u2d2 = hashMap2;
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Boolean", "kotlin.Boolean");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Character", "kotlin.Char");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Byte", "kotlin.Byte");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Short", "kotlin.Short");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Integer", "kotlin.Int");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Float", "kotlin.Float");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Long", "kotlin.Long");
        $this$primitiveWrapperFqNames_u24lambda_u2d2.put("java.lang.Double", "kotlin.Double");
        primitiveWrapperFqNames = hashMap2;
        HashMap hashMap3 = new HashMap();
        HashMap $this$classFqNames_u24lambda_u2d4 = hashMap3;
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Object", "kotlin.Any");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.String", "kotlin.String");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.CharSequence", "kotlin.CharSequence");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Throwable", "kotlin.Throwable");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Cloneable", "kotlin.Cloneable");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Number", "kotlin.Number");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Comparable", "kotlin.Comparable");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Enum", "kotlin.Enum");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        $this$classFqNames_u24lambda_u2d4.put("java.lang.Iterable", "kotlin.collections.Iterable");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Iterator", "kotlin.collections.Iterator");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Collection", "kotlin.collections.Collection");
        $this$classFqNames_u24lambda_u2d4.put("java.util.List", "kotlin.collections.List");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Set", "kotlin.collections.Set");
        $this$classFqNames_u24lambda_u2d4.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Map", "kotlin.collections.Map");
        $this$classFqNames_u24lambda_u2d4.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        $this$classFqNames_u24lambda_u2d4.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        $this$classFqNames_u24lambda_u2d4.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        $this$classFqNames_u24lambda_u2d4.putAll(hashMap);
        $this$classFqNames_u24lambda_u2d4.putAll(hashMap2);
        Collection<String> $this$associateTo$iv = hashMap.values();
        Intrinsics.checkNotNullExpressionValue($this$associateTo$iv, "primitiveFqNames.values");
        for (String kotlinName : $this$associateTo$iv) {
            StringBuilder append = new StringBuilder().append("kotlin.jvm.internal.");
            Intrinsics.checkNotNullExpressionValue(kotlinName, "kotlinName");
            Pair pair = TuplesKt.m281to(append.append(StringsKt.substringAfterLast$default(kotlinName, '.', (String) null, 2, (Object) null)).append("CompanionObject").toString(), kotlinName + ".Companion");
            $this$classFqNames_u24lambda_u2d4.put(pair.getFirst(), pair.getSecond());
        }
        Map map = $this$classFqNames_u24lambda_u2d4;
        for (Map.Entry next : FUNCTION_CLASSES.entrySet()) {
            $this$classFqNames_u24lambda_u2d4.put(((Class) next.getKey()).getName(), "kotlin.Function" + ((Number) next.getValue()).intValue());
        }
        classFqNames = hashMap3;
        Map $this$mapValues$iv = hashMap3;
        Map destination$iv$iv2 = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
        for (Object element$iv$iv$iv : $this$mapValues$iv.entrySet()) {
            destination$iv$iv2.put(((Map.Entry) element$iv$iv$iv).getKey(), StringsKt.substringAfterLast$default((String) ((Map.Entry) element$iv$iv$iv).getValue(), '.', (String) null, 2, (Object) null));
        }
        simpleNames = destination$iv$iv2;
    }
}
