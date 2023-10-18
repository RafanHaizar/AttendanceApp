package kotlin;

import java.lang.annotation.Documented;
import java.lang.annotation.RetentionPolicy;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.MustBeDocumented;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@MustBeDocumented
@Target(allowedTargets = {})
@Retention(AnnotationRetention.BINARY)
@Documented
@java.lang.annotation.Target({})
@Metadata(mo112d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u001c\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0005\"\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0006R\u0017\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0005¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0007¨\u0006\b"}, mo113d2 = {"Lkotlin/ReplaceWith;", "", "expression", "", "imports", "", "()Ljava/lang/String;", "()[Ljava/lang/String;", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
/* compiled from: Annotations.kt */
public @interface ReplaceWith {
    String expression();

    String[] imports();
}
