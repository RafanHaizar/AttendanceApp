package androidx.core.p001os;

import android.os.Bundle;
import android.os.IBinder;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007¨\u0006\u000b"}, mo113d2 = {"Landroidx/core/os/BundleApi18ImplKt;", "", "()V", "putBinder", "", "bundle", "Landroid/os/Bundle;", "key", "", "value", "Landroid/os/IBinder;", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: androidx.core.os.BundleApi18ImplKt */
/* compiled from: Bundle.kt */
final class BundleApi18ImplKt {
    public static final BundleApi18ImplKt INSTANCE = new BundleApi18ImplKt();

    private BundleApi18ImplKt() {
    }

    @JvmStatic
    public static final void putBinder(Bundle bundle, String key, IBinder value) {
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        Intrinsics.checkNotNullParameter(key, "key");
        bundle.putBinder(key, value);
    }
}
