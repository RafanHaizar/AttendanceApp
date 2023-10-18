package androidx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface ChecksSdkIntAtLeast {
    int api() default -1;

    String codename() default "";

    int lambda() default -1;

    int parameter() default -1;
}
