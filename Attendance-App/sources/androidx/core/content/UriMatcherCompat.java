package androidx.core.content;

import android.content.UriMatcher;
import android.net.Uri;
import androidx.core.util.Predicate;

public class UriMatcherCompat {
    private UriMatcherCompat() {
    }

    public static Predicate<Uri> asPredicate(UriMatcher matcher) {
        return new UriMatcherCompat$$ExternalSyntheticLambda0(matcher);
    }

    static /* synthetic */ boolean lambda$asPredicate$0(UriMatcher matcher, Uri v) {
        return matcher.match(v) != -1;
    }
}
