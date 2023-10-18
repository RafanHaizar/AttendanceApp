package kotlin;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;

@MustBeDocumented
@Documented
@Target({})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.TYPE})
@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\b\u0012\u0006\u0010\u0002\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0005"}, mo113d2 = {"Lkotlin/ContextFunctionTypeParams;", "", "count", "", "()I", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
@Retention(RetentionPolicy.RUNTIME)
/* compiled from: Annotations.kt */
public @interface ContextFunctionTypeParams {
    int count();
}
