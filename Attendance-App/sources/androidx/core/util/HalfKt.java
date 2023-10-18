package androidx.core.util;

import android.util.Half;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\n\n\u0002\u0010\u000e\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0003H\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0004H\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0005H\b¨\u0006\u0006"}, mo113d2 = {"toHalf", "Landroid/util/Half;", "", "", "", "", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Half.kt */
public final class HalfKt {
    public static final Half toHalf(short $this$toHalf) {
        Half valueOf = Half.valueOf($this$toHalf);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this)");
        return valueOf;
    }

    public static final Half toHalf(float $this$toHalf) {
        Half valueOf = Half.valueOf($this$toHalf);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this)");
        return valueOf;
    }

    public static final Half toHalf(double $this$toHalf) {
        Half valueOf = Half.valueOf((float) $this$toHalf);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this)");
        return valueOf;
    }

    public static final Half toHalf(String $this$toHalf) {
        Intrinsics.checkNotNullParameter($this$toHalf, "<this>");
        Half valueOf = Half.valueOf($this$toHalf);
        Intrinsics.checkNotNullExpressionValue(valueOf, "valueOf(this)");
        return valueOf;
    }
}
