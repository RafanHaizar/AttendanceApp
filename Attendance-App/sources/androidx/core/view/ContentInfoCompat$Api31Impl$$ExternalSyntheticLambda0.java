package androidx.core.view;

import android.content.ClipData;
import androidx.core.util.Predicate;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ContentInfoCompat$Api31Impl$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ java.util.function.Predicate f$0;

    public /* synthetic */ ContentInfoCompat$Api31Impl$$ExternalSyntheticLambda0(java.util.function.Predicate predicate) {
        this.f$0 = predicate;
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
        return this.f$0.test((ClipData.Item) obj);
    }
}
