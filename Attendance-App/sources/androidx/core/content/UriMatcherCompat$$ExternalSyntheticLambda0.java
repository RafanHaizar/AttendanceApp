package androidx.core.content;

import android.content.UriMatcher;
import android.net.Uri;
import androidx.core.util.Predicate;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class UriMatcherCompat$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ UriMatcher f$0;

    public /* synthetic */ UriMatcherCompat$$ExternalSyntheticLambda0(UriMatcher uriMatcher) {
        this.f$0 = uriMatcher;
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
        return UriMatcherCompat.lambda$asPredicate$0(this.f$0, (Uri) obj);
    }
}
