package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.reflect.KFunction;

public class FunInterfaceConstructorReference extends FunctionReference implements Serializable {
    private final Class funInterface;

    public FunInterfaceConstructorReference(Class funInterface2) {
        super(1);
        this.funInterface = funInterface2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FunInterfaceConstructorReference)) {
            return false;
        }
        return this.funInterface.equals(((FunInterfaceConstructorReference) o).funInterface);
    }

    public int hashCode() {
        return this.funInterface.hashCode();
    }

    public String toString() {
        return "fun interface " + this.funInterface.getName();
    }

    /* access modifiers changed from: protected */
    public KFunction getReflected() {
        throw new UnsupportedOperationException("Functional interface constructor does not support reflection");
    }
}
