package androidx.annotation.experimental;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Target(allowedTargets = {AnnotationTarget.ANNOTATION_CLASS})
@Retention(AnnotationRetention.BINARY)
@java.lang.annotation.Target({ElementType.ANNOTATION_TYPE})
@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0005B\n\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0006"}, mo113d2 = {"Landroidx/annotation/experimental/Experimental;", "", "level", "Landroidx/annotation/experimental/Experimental$Level;", "()Landroidx/annotation/experimental/Experimental$Level;", "Level", "annotation-experimental_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
@Deprecated(message = "This annotation has been replaced by `@RequiresOptIn`", replaceWith = @ReplaceWith(expression = "RequiresOptIn", imports = {"androidx.annotation.RequiresOptIn"}))
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
/* compiled from: Experimental.kt */
public @interface Experimental {

    @Metadata(mo112d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, mo113d2 = {"Landroidx/annotation/experimental/Experimental$Level;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "annotation-experimental_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: Experimental.kt */
    public enum Level {
        WARNING,
        ERROR
    }

    Level level() default Level.ERROR;
}
