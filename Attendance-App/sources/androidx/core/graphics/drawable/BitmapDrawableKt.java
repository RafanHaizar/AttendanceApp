package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\b¨\u0006\u0005"}, mo113d2 = {"toDrawable", "Landroid/graphics/drawable/BitmapDrawable;", "Landroid/graphics/Bitmap;", "resources", "Landroid/content/res/Resources;", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: BitmapDrawable.kt */
public final class BitmapDrawableKt {
    public static final BitmapDrawable toDrawable(Bitmap $this$toDrawable, Resources resources) {
        Intrinsics.checkNotNullParameter($this$toDrawable, "<this>");
        Intrinsics.checkNotNullParameter(resources, "resources");
        return new BitmapDrawable(resources, $this$toDrawable);
    }
}
