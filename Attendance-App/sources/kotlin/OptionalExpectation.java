package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;

@Target({ElementType.ANNOTATION_TYPE})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.ANNOTATION_CLASS})
@Metadata(mo112d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0000ø\u0001\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0002"}, mo113d2 = {"Lkotlin/OptionalExpectation;", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
@Retention(AnnotationRetention.BINARY)
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
/* compiled from: Multiplatform.kt */
public @interface OptionalExpectation {
}
