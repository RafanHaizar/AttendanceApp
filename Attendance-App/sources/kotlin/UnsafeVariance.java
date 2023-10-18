package kotlin;

import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@MustBeDocumented
@Target(allowedTargets = {AnnotationTarget.TYPE})
@Retention(AnnotationRetention.SOURCE)
@Documented
@java.lang.annotation.Target({})
@Metadata(mo112d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, mo113d2 = {"Lkotlin/UnsafeVariance;", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
@java.lang.annotation.Retention(RetentionPolicy.SOURCE)
/* compiled from: Annotations.kt */
public @interface UnsafeVariance {
}
