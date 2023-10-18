package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.reflect.KDeclarationContainer;

public class AdaptedFunctionReference implements FunctionBase, Serializable {
    private final int arity;
    private final int flags;
    private final boolean isTopLevel;
    private final String name;
    private final Class owner;
    protected final Object receiver;
    private final String signature;

    public AdaptedFunctionReference(int arity2, Class owner2, String name2, String signature2, int flags2) {
        this(arity2, CallableReference.NO_RECEIVER, owner2, name2, signature2, flags2);
    }

    public AdaptedFunctionReference(int arity2, Object receiver2, Class owner2, String name2, String signature2, int flags2) {
        this.receiver = receiver2;
        this.owner = owner2;
        this.name = name2;
        this.signature = signature2;
        this.isTopLevel = (flags2 & 1) != 1 ? false : true;
        this.arity = arity2;
        this.flags = flags2 >> 1;
    }

    public int getArity() {
        return this.arity;
    }

    public KDeclarationContainer getOwner() {
        Class cls = this.owner;
        if (cls == null) {
            return null;
        }
        return this.isTopLevel ? Reflection.getOrCreateKotlinPackage(cls) : Reflection.getOrCreateKotlinClass(cls);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdaptedFunctionReference)) {
            return false;
        }
        AdaptedFunctionReference other = (AdaptedFunctionReference) o;
        if (this.isTopLevel != other.isTopLevel || this.arity != other.arity || this.flags != other.flags || !Intrinsics.areEqual(this.receiver, other.receiver) || !Intrinsics.areEqual((Object) this.owner, (Object) other.owner) || !this.name.equals(other.name) || !this.signature.equals(other.signature)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        Object obj = this.receiver;
        int i = 0;
        int hashCode = (obj != null ? obj.hashCode() : 0) * 31;
        Class cls = this.owner;
        if (cls != null) {
            i = cls.hashCode();
        }
        return ((((((((((hashCode + i) * 31) + this.name.hashCode()) * 31) + this.signature.hashCode()) * 31) + (this.isTopLevel ? 1231 : 1237)) * 31) + this.arity) * 31) + this.flags;
    }

    public String toString() {
        return Reflection.renderLambdaToString((FunctionBase) this);
    }
}
