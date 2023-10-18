package androidx.core.util;

import androidx.core.util.Predicate;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class Predicate$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ Predicate f$0;
    public final /* synthetic */ Predicate f$1;

    public /* synthetic */ Predicate$$ExternalSyntheticLambda1(Predicate predicate, Predicate predicate2) {
        this.f$0 = predicate;
        this.f$1 = predicate2;
    }

    public /* synthetic */ Predicate and(Predicate predicate) {
        return Predicate.CC.$default$and(this, predicate);
    }

    public /* synthetic */ Predicate negate() {
        return Predicate.CC.$default$negate(this);
    }

    /* renamed from: or */
    public /* synthetic */ Predicate mo15473or(Predicate predicate) {
        return Predicate.CC.$default$or(this, predicate);
    }

    public final boolean test(Object obj) {
        return Predicate.CC.$private$lambda$or$2(this.f$0, this.f$1, obj);
    }
}
