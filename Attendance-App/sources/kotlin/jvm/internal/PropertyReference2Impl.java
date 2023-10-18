package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class PropertyReference2Impl extends PropertyReference2 {
    public PropertyReference2Impl(KDeclarationContainer owner, String name, String signature) {
        super(((ClassBasedDeclarationContainer) owner).getJClass(), name, signature, (owner instanceof KClass) ^ true ? 1 : 0);
    }

    public PropertyReference2Impl(Class owner, String name, String signature, int flags) {
        super(owner, name, signature, flags);
    }

    public Object get(Object receiver1, Object receiver2) {
        return getGetter().call(receiver1, receiver2);
    }
}
