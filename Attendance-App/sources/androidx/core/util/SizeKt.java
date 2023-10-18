package androidx.core.util;

import android.util.Size;
import android.util.SizeF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n\u001a\r\u0010\u0000\u001a\u00020\u0003*\u00020\u0004H\n\u001a\r\u0010\u0000\u001a\u00020\u0003*\u00020\u0005H\n\u001a\r\u0010\u0006\u001a\u00020\u0001*\u00020\u0002H\n\u001a\r\u0010\u0006\u001a\u00020\u0003*\u00020\u0004H\n\u001a\r\u0010\u0006\u001a\u00020\u0003*\u00020\u0005H\n¨\u0006\u0007"}, mo113d2 = {"component1", "", "Landroid/util/Size;", "", "Landroid/util/SizeF;", "Landroidx/core/util/SizeFCompat;", "component2", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Size.kt */
public final class SizeKt {
    public static final int component1(Size $this$component1) {
        Intrinsics.checkNotNullParameter($this$component1, "<this>");
        return $this$component1.getWidth();
    }

    public static final int component2(Size $this$component2) {
        Intrinsics.checkNotNullParameter($this$component2, "<this>");
        return $this$component2.getHeight();
    }

    public static final float component1(SizeF $this$component1) {
        Intrinsics.checkNotNullParameter($this$component1, "<this>");
        return $this$component1.getWidth();
    }

    public static final float component2(SizeF $this$component2) {
        Intrinsics.checkNotNullParameter($this$component2, "<this>");
        return $this$component2.getHeight();
    }

    public static final float component1(SizeFCompat $this$component1) {
        Intrinsics.checkNotNullParameter($this$component1, "<this>");
        return $this$component1.getWidth();
    }

    public static final float component2(SizeFCompat $this$component2) {
        Intrinsics.checkNotNullParameter($this$component2, "<this>");
        return $this$component2.getHeight();
    }
}
