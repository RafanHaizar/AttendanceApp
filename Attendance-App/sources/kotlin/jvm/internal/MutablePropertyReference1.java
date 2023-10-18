package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KProperty1;

public abstract class MutablePropertyReference1 extends MutablePropertyReference implements KMutableProperty1 {
    public MutablePropertyReference1() {
    }

    public MutablePropertyReference1(Object receiver) {
        super(receiver);
    }

    public MutablePropertyReference1(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    /* access modifiers changed from: protected */
    public KCallable computeReflected() {
        return Reflection.mutableProperty1(this);
    }

    public Object invoke(Object receiver) {
        return get(receiver);
    }

    public KProperty1.Getter getGetter() {
        return ((KMutableProperty1) getReflected()).getGetter();
    }

    public KMutableProperty1.Setter getSetter() {
        return ((KMutableProperty1) getReflected()).getSetter();
    }

    public Object getDelegate(Object receiver) {
        return ((KMutableProperty1) getReflected()).getDelegate(receiver);
    }
}
