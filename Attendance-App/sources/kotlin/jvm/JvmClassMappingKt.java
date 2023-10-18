package kotlin.jvm;

import com.itextpdf.svg.SvgConstants;
import java.lang.annotation.Annotation;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

@Metadata(mo112d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\b\n\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u001f\u001a\u00020 \"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0014*\u0006\u0012\u0002\b\u00030!¢\u0006\u0002\u0010\"\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"5\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u000e\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t*\u0002H\b8Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"-\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018G¢\u0006\f\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"&\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\u0002H\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0015\";\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00018Ç\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0016\u0010\u0010\u001a\u0004\b\u0017\u0010\u0012\"+\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0012\"-\u0010\u001a\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0012\"+\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e¨\u0006#"}, mo113d2 = {"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "declaringJavaClass", "Ljava/lang/Class;", "E", "", "getDeclaringJavaClass$annotations", "(Ljava/lang/Enum;)V", "getDeclaringJavaClass", "(Ljava/lang/Enum;)Ljava/lang/Class;", "java", "getJavaClass$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "getRuntimeClassOfKClassInstance$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: JvmClassMapping.kt */
public final class JvmClassMappingKt {
    public static /* synthetic */ void getDeclaringJavaClass$annotations(Enum enumR) {
    }

    public static /* synthetic */ void getJavaClass$annotations(KClass kClass) {
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.", replaceWith = @ReplaceWith(expression = "(this as Any).javaClass", imports = {}))
    public static /* synthetic */ void getRuntimeClassOfKClassInstance$annotations(KClass kClass) {
    }

    public static final <T> Class<T> getJavaClass(KClass<T> $this$java) {
        Intrinsics.checkNotNullParameter($this$java, "<this>");
        Class<?> jClass = ((ClassBasedDeclarationContainer) $this$java).getJClass();
        Intrinsics.checkNotNull(jClass, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-java>>");
        return jClass;
    }

    public static final <T> Class<T> getJavaPrimitiveType(KClass<T> $this$javaPrimitiveType) {
        Intrinsics.checkNotNullParameter($this$javaPrimitiveType, "<this>");
        Class thisJClass = ((ClassBasedDeclarationContainer) $this$javaPrimitiveType).getJClass();
        if (thisJClass.isPrimitive()) {
            Intrinsics.checkNotNull(thisJClass, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaPrimitiveType>>");
            return thisJClass;
        }
        String name = thisJClass.getName();
        if (name != null) {
            switch (name.hashCode()) {
                case -2056817302:
                    if (name.equals("java.lang.Integer")) {
                        return Integer.TYPE;
                    }
                    break;
                case -527879800:
                    if (name.equals("java.lang.Float")) {
                        return Float.TYPE;
                    }
                    break;
                case -515992664:
                    if (name.equals("java.lang.Short")) {
                        return Short.TYPE;
                    }
                    break;
                case 155276373:
                    if (name.equals("java.lang.Character")) {
                        return Character.TYPE;
                    }
                    break;
                case 344809556:
                    if (name.equals("java.lang.Boolean")) {
                        return Boolean.TYPE;
                    }
                    break;
                case 398507100:
                    if (name.equals("java.lang.Byte")) {
                        return Byte.TYPE;
                    }
                    break;
                case 398795216:
                    if (name.equals("java.lang.Long")) {
                        return Long.TYPE;
                    }
                    break;
                case 399092968:
                    if (name.equals("java.lang.Void")) {
                        return Void.TYPE;
                    }
                    break;
                case 761287205:
                    if (name.equals("java.lang.Double")) {
                        return Double.TYPE;
                    }
                    break;
            }
        }
        return null;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Class<T> getJavaObjectType(kotlin.reflect.KClass<T> r4) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            r0 = r4
            kotlin.jvm.internal.ClassBasedDeclarationContainer r0 = (kotlin.jvm.internal.ClassBasedDeclarationContainer) r0
            java.lang.Class r0 = r0.getJClass()
            boolean r1 = r0.isPrimitive()
            java.lang.String r2 = "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaObjectType>>"
            if (r1 != 0) goto L_0x0018
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0, r2)
            return r0
        L_0x0018:
            java.lang.String r1 = r0.getName()
            if (r1 == 0) goto L_0x0095
            int r3 = r1.hashCode()
            switch(r3) {
                case -1325958191: goto L_0x0089;
                case 104431: goto L_0x007d;
                case 3039496: goto L_0x0071;
                case 3052374: goto L_0x0065;
                case 3327612: goto L_0x0059;
                case 3625364: goto L_0x004d;
                case 64711720: goto L_0x0041;
                case 97526364: goto L_0x0035;
                case 109413500: goto L_0x0027;
                default: goto L_0x0025;
            }
        L_0x0025:
            goto L_0x0095
        L_0x0027:
            java.lang.String r3 = "short"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x0031
            goto L_0x0095
        L_0x0031:
            java.lang.Class<java.lang.Short> r1 = java.lang.Short.class
            goto L_0x0096
        L_0x0035:
            java.lang.String r3 = "float"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x003e
            goto L_0x0095
        L_0x003e:
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            goto L_0x0096
        L_0x0041:
            java.lang.String r3 = "boolean"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x004a
            goto L_0x0095
        L_0x004a:
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            goto L_0x0096
        L_0x004d:
            java.lang.String r3 = "void"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x0056
            goto L_0x0095
        L_0x0056:
            java.lang.Class<java.lang.Void> r1 = java.lang.Void.class
            goto L_0x0096
        L_0x0059:
            java.lang.String r3 = "long"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x0062
            goto L_0x0095
        L_0x0062:
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            goto L_0x0096
        L_0x0065:
            java.lang.String r3 = "char"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x006e
            goto L_0x0095
        L_0x006e:
            java.lang.Class<java.lang.Character> r1 = java.lang.Character.class
            goto L_0x0096
        L_0x0071:
            java.lang.String r3 = "byte"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x007a
            goto L_0x0095
        L_0x007a:
            java.lang.Class<java.lang.Byte> r1 = java.lang.Byte.class
            goto L_0x0096
        L_0x007d:
            java.lang.String r3 = "int"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x0086
            goto L_0x0095
        L_0x0086:
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            goto L_0x0096
        L_0x0089:
            java.lang.String r3 = "double"
            boolean r1 = r1.equals(r3)
            if (r1 != 0) goto L_0x0092
            goto L_0x0095
        L_0x0092:
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            goto L_0x0096
        L_0x0095:
            r1 = r0
        L_0x0096:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.jvm.JvmClassMappingKt.getJavaObjectType(kotlin.reflect.KClass):java.lang.Class");
    }

    public static final <T> KClass<T> getKotlinClass(Class<T> $this$kotlin) {
        Intrinsics.checkNotNullParameter($this$kotlin, "<this>");
        return Reflection.getOrCreateKotlinClass($this$kotlin);
    }

    public static final <T> Class<T> getJavaClass(T $this$javaClass) {
        Intrinsics.checkNotNullParameter($this$javaClass, "<this>");
        Class<?> cls = $this$javaClass.getClass();
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaClass>>");
        return cls;
    }

    public static final <T> Class<KClass<T>> getRuntimeClassOfKClassInstance(KClass<T> $this$javaClass) {
        Intrinsics.checkNotNullParameter($this$javaClass, "<this>");
        Class<?> cls = $this$javaClass.getClass();
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T of kotlin.jvm.JvmClassMappingKt.<get-javaClass>>>");
        return cls;
    }

    public static final /* synthetic */ boolean isArrayOf(Object[] $this$isArrayOf) {
        Intrinsics.checkNotNullParameter($this$isArrayOf, "<this>");
        Intrinsics.reifiedOperationMarker(4, SvgConstants.Attributes.PATH_DATA_SHORTHAND_CURVE_TO);
        Class<Object> cls = Object.class;
        Class cls2 = cls;
        Class<?> cls3 = $this$isArrayOf.getClass();
        Class cls4 = cls3;
        return cls.isAssignableFrom(cls3.getComponentType());
    }

    public static final <T extends Annotation> KClass<? extends T> getAnnotationClass(T $this$annotationClass) {
        Intrinsics.checkNotNullParameter($this$annotationClass, "<this>");
        Class<? extends Annotation> annotationType = $this$annotationClass.annotationType();
        Intrinsics.checkNotNullExpressionValue(annotationType, "this as java.lang.annota…otation).annotationType()");
        KClass<? extends T> kotlinClass = getKotlinClass(annotationType);
        Intrinsics.checkNotNull(kotlinClass, "null cannot be cast to non-null type kotlin.reflect.KClass<out T of kotlin.jvm.JvmClassMappingKt.<get-annotationClass>>");
        return kotlinClass;
    }

    private static final <E extends Enum<E>> Class<E> getDeclaringJavaClass(E $this$declaringJavaClass) {
        Intrinsics.checkNotNullParameter($this$declaringJavaClass, "<this>");
        Class<E> declaringClass = $this$declaringJavaClass.getDeclaringClass();
        Intrinsics.checkNotNullExpressionValue(declaringClass, "this as java.lang.Enum<E>).declaringClass");
        return declaringClass;
    }
}
