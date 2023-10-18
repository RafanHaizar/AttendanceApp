package kotlin;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Target(allowedTargets = {AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.EXPRESSION, AnnotationTarget.FILE, AnnotationTarget.TYPEALIAS})
@Retention(AnnotationRetention.SOURCE)
@java.lang.annotation.Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE})
@DeprecatedSinceKotlin(errorSince = "1.6", warningSince = "1.4")
@Metadata(mo112d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B$\u0012\"\u0010\u0002\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u00040\u0003\"\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0004R\u001f\u0010\u0002\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u00040\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005ø\u0001\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0006"}, mo113d2 = {"Lkotlin/UseExperimental;", "", "markerClass", "", "Lkotlin/reflect/KClass;", "()[Ljava/lang/Class;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
@Deprecated(message = "Please use OptIn instead.", replaceWith = @ReplaceWith(expression = "OptIn(*markerClass)", imports = {"kotlin.OptIn"}))
@java.lang.annotation.Retention(RetentionPolicy.SOURCE)
/* compiled from: Experimental.kt */
public @interface UseExperimental {
    Class<? extends Annotation>[] markerClass();
}
