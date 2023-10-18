package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty1;

public abstract class PropertyReference1 extends PropertyReference implements KProperty1 {
    public PropertyReference1() {
    }

    public PropertyReference1(Object receiver) {
        super(receiver);
    }

    public PropertyReference1(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    /* access modifiers changed from: protected */
    public KCallable computeReflected() {
        return Reflection.property1(this);
    }

    public Object invoke(Object receiver) {
        return get(receiver);
    }

    public KProperty1.Getter getGetter() {
        return ((KProperty1) getReflected()).getGetter();
    }

    public Object getDelegate(Object receiver) {
        return ((KProperty1) getReflected()).getDelegate(receiver);
    }
}
