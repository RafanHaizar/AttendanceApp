package kotlin.jvm;

import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@MustBeDocumented
@Target(allowedTargets = {AnnotationTarget.FILE})
@Retention(AnnotationRetention.SOURCE)
@Documented
@java.lang.annotation.Target({})
@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\b\u0012\u0006\u0010\u0002\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0005"}, mo113d2 = {"Lkotlin/jvm/JvmPackageName;", "", "name", "", "()Ljava/lang/String;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
@java.lang.annotation.Retention(RetentionPolicy.SOURCE)
/* compiled from: JvmPlatformAnnotations.kt */
public @interface JvmPackageName {
    String name();
}
