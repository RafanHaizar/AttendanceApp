package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;

public abstract class PropertyReference extends CallableReference implements KProperty {
    public PropertyReference() {
    }

    public PropertyReference(Object receiver) {
        super(receiver);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PropertyReference(Object receiver, Class owner, String name, String signature, int flags) {
        super(receiver, owner, name, signature, (flags & 1) == 1);
    }

    /* access modifiers changed from: protected */
    public KProperty getReflected() {
        return (KProperty) super.getReflected();
    }

    public boolean isLateinit() {
        return getReflected().isLateinit();
    }

    public boolean isConst() {
        return getReflected().isConst();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PropertyReference) {
            PropertyReference other = (PropertyReference) obj;
            if (!getOwner().equals(other.getOwner()) || !getName().equals(other.getName()) || !getSignature().equals(other.getSignature()) || !Intrinsics.areEqual(getBoundReceiver(), other.getBoundReceiver())) {
                return false;
            }
            return true;
        } else if (obj instanceof KProperty) {
            return obj.equals(compute());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (((getOwner().hashCode() * 31) + getName().hashCode()) * 31) + getSignature().hashCode();
    }

    public String toString() {
        KCallable reflected = compute();
        if (reflected != this) {
            return reflected.toString();
        }
        return "property " + getName() + " (Kotlin reflection is not available)";
    }
}
