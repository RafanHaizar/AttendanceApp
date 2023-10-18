package androidx.core.p001os;

import android.os.PersistableBundle;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0007\u001a=\u0010\u0000\u001a\u00020\u00012.\u0010\u0002\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00040\u0003\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004H\u0007¢\u0006\u0002\u0010\u0007\u001a\u001a\u0010\b\u001a\u00020\u0001*\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\tH\u0007¨\u0006\n"}, mo113d2 = {"persistableBundleOf", "Landroid/os/PersistableBundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/PersistableBundle;", "toPersistableBundle", "", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: androidx.core.os.PersistableBundleKt */
/* compiled from: PersistableBundle.kt */
public final class PersistableBundleKt {
    public static final PersistableBundle persistableBundleOf(Pair<String, ? extends Object>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        PersistableBundle persistableBundle = PersistableBundleApi21ImplKt.createPersistableBundle(pairs.length);
        for (Pair<String, ? extends Object> pair : pairs) {
            PersistableBundleApi21ImplKt.putValue(persistableBundle, pair.component1(), pair.component2());
        }
        return persistableBundle;
    }

    public static final PersistableBundle persistableBundleOf() {
        return PersistableBundleApi21ImplKt.createPersistableBundle(0);
    }

    public static final PersistableBundle toPersistableBundle(Map<String, ? extends Object> $this$toPersistableBundle) {
        Intrinsics.checkNotNullParameter($this$toPersistableBundle, "<this>");
        PersistableBundle persistableBundle = PersistableBundleApi21ImplKt.createPersistableBundle($this$toPersistableBundle.size());
        for (Map.Entry next : $this$toPersistableBundle.entrySet()) {
            PersistableBundleApi21ImplKt.putValue(persistableBundle, (String) next.getKey(), next.getValue());
        }
        return persistableBundle;
    }
}
