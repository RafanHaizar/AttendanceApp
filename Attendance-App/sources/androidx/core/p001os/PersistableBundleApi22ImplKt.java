package androidx.core.p001os;

import android.os.PersistableBundle;
import com.itextpdf.forms.xfdf.XfdfConstants;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0018\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\"\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\fH\u0007¨\u0006\r"}, mo113d2 = {"Landroidx/core/os/PersistableBundleApi22ImplKt;", "", "()V", "putBoolean", "", "persistableBundle", "Landroid/os/PersistableBundle;", "key", "", "value", "", "putBooleanArray", "", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: androidx.core.os.PersistableBundleApi22ImplKt */
/* compiled from: PersistableBundle.kt */
final class PersistableBundleApi22ImplKt {
    public static final PersistableBundleApi22ImplKt INSTANCE = new PersistableBundleApi22ImplKt();

    private PersistableBundleApi22ImplKt() {
    }

    @JvmStatic
    public static final void putBoolean(PersistableBundle persistableBundle, String key, boolean value) {
        Intrinsics.checkNotNullParameter(persistableBundle, "persistableBundle");
        persistableBundle.putBoolean(key, value);
    }

    @JvmStatic
    public static final void putBooleanArray(PersistableBundle persistableBundle, String key, boolean[] value) {
        Intrinsics.checkNotNullParameter(persistableBundle, "persistableBundle");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        persistableBundle.putBooleanArray(key, value);
    }
}
