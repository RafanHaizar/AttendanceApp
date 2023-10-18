package kotlin.jvm.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;

@Target({ElementType.TYPE})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.CLASS})
@Metadata(mo112d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0010\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0007¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, mo113d2 = {"Lkotlin/jvm/internal/SerializedIr;", "", "bytes", "", "", "b", "()[Ljava/lang/String;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
@Retention(AnnotationRetention.BINARY)
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
/* compiled from: SerializedIr.kt */
public @interface SerializedIr {
    /* renamed from: b */
    String[] mo167b() default {};
}
