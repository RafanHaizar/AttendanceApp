package p004pl.droidsonroids.gif.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
/* renamed from: pl.droidsonroids.gif.annotations.Beta */
public @interface Beta {
}
