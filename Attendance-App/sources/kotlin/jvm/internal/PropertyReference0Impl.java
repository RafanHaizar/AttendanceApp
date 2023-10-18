package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class PropertyReference0Impl extends PropertyReference0 {
    public PropertyReference0Impl(KDeclarationContainer owner, String name, String signature) {
        super(NO_RECEIVER, ((ClassBasedDeclarationContainer) owner).getJClass(), name, signature, (owner instanceof KClass) ^ true ? 1 : 0);
    }

    public PropertyReference0Impl(Class owner, String name, String signature, int flags) {
        super(NO_RECEIVER, owner, name, signature, flags);
    }

    public PropertyReference0Impl(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, flags);
    }

    public Object get() {
        return getGetter().call(new Object[0]);
    }
}
