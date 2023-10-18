package androidx.core.content;

import androidx.core.content.IntentSanitizer;
import androidx.core.util.Predicate;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class IntentSanitizer$Builder$$ExternalSyntheticLambda10 implements Predicate {
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
        return IntentSanitizer.Builder.lambda$allowExtra$12(obj);
    }
}
