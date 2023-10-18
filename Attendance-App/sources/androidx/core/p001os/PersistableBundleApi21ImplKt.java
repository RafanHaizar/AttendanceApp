package androidx.core.p001os;

import android.os.Build;
import android.os.PersistableBundle;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;

@Metadata(mo112d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J$\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0007¨\u0006\r"}, mo113d2 = {"Landroidx/core/os/PersistableBundleApi21ImplKt;", "", "()V", "createPersistableBundle", "Landroid/os/PersistableBundle;", "capacity", "", "putValue", "", "persistableBundle", "key", "", "value", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: androidx.core.os.PersistableBundleApi21ImplKt */
/* compiled from: PersistableBundle.kt */
final class PersistableBundleApi21ImplKt {
    public static final PersistableBundleApi21ImplKt INSTANCE = new PersistableBundleApi21ImplKt();

    private PersistableBundleApi21ImplKt() {
    }

    @JvmStatic
    public static final PersistableBundle createPersistableBundle(int capacity) {
        return new PersistableBundle(capacity);
    }

    @JvmStatic
    public static final void putValue(PersistableBundle persistableBundle, String key, Object value) {
        Intrinsics.checkNotNullParameter(persistableBundle, "persistableBundle");
        PersistableBundle $this$putValue_u24lambda_u2d0 = persistableBundle;
        if (value == null) {
            $this$putValue_u24lambda_u2d0.putString(key, (String) null);
        } else if (value instanceof Boolean) {
            if (Build.VERSION.SDK_INT >= 22) {
                PersistableBundleApi22ImplKt.putBoolean($this$putValue_u24lambda_u2d0, key, ((Boolean) value).booleanValue());
                return;
            }
            throw new IllegalArgumentException("Illegal value type boolean for key \"" + key + Typography.quote);
        } else if (value instanceof Double) {
            $this$putValue_u24lambda_u2d0.putDouble(key, ((Number) value).doubleValue());
        } else if (value instanceof Integer) {
            $this$putValue_u24lambda_u2d0.putInt(key, ((Number) value).intValue());
        } else if (value instanceof Long) {
            $this$putValue_u24lambda_u2d0.putLong(key, ((Number) value).longValue());
        } else if (value instanceof String) {
            $this$putValue_u24lambda_u2d0.putString(key, (String) value);
        } else if (value instanceof boolean[]) {
            if (Build.VERSION.SDK_INT >= 22) {
                PersistableBundleApi22ImplKt.putBooleanArray($this$putValue_u24lambda_u2d0, key, (boolean[]) value);
                return;
            }
            throw new IllegalArgumentException("Illegal value type boolean[] for key \"" + key + Typography.quote);
        } else if (value instanceof double[]) {
            $this$putValue_u24lambda_u2d0.putDoubleArray(key, (double[]) value);
        } else if (value instanceof int[]) {
            $this$putValue_u24lambda_u2d0.putIntArray(key, (int[]) value);
        } else if (value instanceof long[]) {
            $this$putValue_u24lambda_u2d0.putLongArray(key, (long[]) value);
        } else if (value instanceof Object[]) {
            Class componentType = value.getClass().getComponentType();
            Intrinsics.checkNotNull(componentType);
            if (String.class.isAssignableFrom(componentType)) {
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Array<kotlin.String>");
                $this$putValue_u24lambda_u2d0.putStringArray(key, (String[]) value);
                return;
            }
            throw new IllegalArgumentException("Illegal value array type " + componentType.getCanonicalName() + " for key \"" + key + Typography.quote);
        } else {
            throw new IllegalArgumentException("Illegal value type " + value.getClass().getCanonicalName() + " for key \"" + key + Typography.quote);
        }
    }
}
