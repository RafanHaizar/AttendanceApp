package kotlin.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Repeatable;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;
import kotlin.jvm.internal.RepeatableContainer;

@Target(allowedTargets = {AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.TYPEALIAS})
@Repeatable
@java.lang.annotation.Repeatable(Container.class)
@Retention(AnnotationRetention.SOURCE)
@java.lang.annotation.Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Metadata(mo112d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B0\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\nR\u000f\u0010\t\u001a\u00020\n¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u000f\u0010\u0005\u001a\u00020\u0006¢\u0006\u0006\u001a\u0004\b\u0005\u0010\fR\u000f\u0010\u0004\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0004\u0010\rR\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\rR\u000f\u0010\u0007\u001a\u00020\b¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u000e¨\u0006\u000f"}, mo113d2 = {"Lkotlin/internal/RequireKotlin;", "", "version", "", "message", "level", "Lkotlin/DeprecationLevel;", "versionKind", "Lkotlin/internal/RequireKotlinVersionKind;", "errorCode", "", "()I", "()Lkotlin/DeprecationLevel;", "()Ljava/lang/String;", "()Lkotlin/internal/RequireKotlinVersionKind;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
@java.lang.annotation.Retention(RetentionPolicy.SOURCE)
/* compiled from: Annotations.kt */
public @interface RequireKotlin {

    @Target(allowedTargets = {AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.TYPEALIAS})
    @Retention(AnnotationRetention.SOURCE)
    @RepeatableContainer
    @java.lang.annotation.Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
    @Metadata(mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
    @java.lang.annotation.Retention(RetentionPolicy.SOURCE)
    /* compiled from: Annotations.kt */
    public @interface Container {
        RequireKotlin[] value();
    }

    int errorCode() default -1;

    DeprecationLevel level() default DeprecationLevel.ERROR;

    String message() default "";

    String version();

    RequireKotlinVersionKind versionKind() default RequireKotlinVersionKind.LANGUAGE_VERSION;
}
