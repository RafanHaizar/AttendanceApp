package androidx.core.graphics.drawable;

import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0004H\b\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0005H\b¨\u0006\u0006"}, mo113d2 = {"toAdaptiveIcon", "Landroid/graphics/drawable/Icon;", "Landroid/graphics/Bitmap;", "toIcon", "Landroid/net/Uri;", "", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Icon.kt */
public final class IconKt {
    public static final Icon toAdaptiveIcon(Bitmap $this$toAdaptiveIcon) {
        Intrinsics.checkNotNullParameter($this$toAdaptiveIcon, "<this>");
        Icon createWithAdaptiveBitmap = Icon.createWithAdaptiveBitmap($this$toAdaptiveIcon);
        Intrinsics.checkNotNullExpressionValue(createWithAdaptiveBitmap, "createWithAdaptiveBitmap(this)");
        return createWithAdaptiveBitmap;
    }

    public static final Icon toIcon(Bitmap $this$toIcon) {
        Intrinsics.checkNotNullParameter($this$toIcon, "<this>");
        Icon createWithBitmap = Icon.createWithBitmap($this$toIcon);
        Intrinsics.checkNotNullExpressionValue(createWithBitmap, "createWithBitmap(this)");
        return createWithBitmap;
    }

    public static final Icon toIcon(Uri $this$toIcon) {
        Intrinsics.checkNotNullParameter($this$toIcon, "<this>");
        Icon createWithContentUri = Icon.createWithContentUri($this$toIcon);
        Intrinsics.checkNotNullExpressionValue(createWithContentUri, "createWithContentUri(this)");
        return createWithContentUri;
    }

    public static final Icon toIcon(byte[] $this$toIcon) {
        Intrinsics.checkNotNullParameter($this$toIcon, "<this>");
        Icon createWithData = Icon.createWithData($this$toIcon, 0, $this$toIcon.length);
        Intrinsics.checkNotNullExpressionValue(createWithData, "createWithData(this, 0, size)");
        return createWithData;
    }
}
