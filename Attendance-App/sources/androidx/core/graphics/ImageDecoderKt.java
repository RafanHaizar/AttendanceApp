package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aU\u0010\u0000\u001a\u00020\u0001*\u00020\u00022C\b\u0004\u0010\u0003\u001a=\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0004¢\u0006\u0002\b\fH\bø\u0001\u0000\u001aU\u0010\r\u001a\u00020\u000e*\u00020\u00022C\b\u0004\u0010\u0003\u001a=\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000b0\u0004¢\u0006\u0002\b\fH\bø\u0001\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006\u000f"}, mo113d2 = {"decodeBitmap", "Landroid/graphics/Bitmap;", "Landroid/graphics/ImageDecoder$Source;", "action", "Lkotlin/Function3;", "Landroid/graphics/ImageDecoder;", "Landroid/graphics/ImageDecoder$ImageInfo;", "Lkotlin/ParameterName;", "name", "info", "source", "", "Lkotlin/ExtensionFunctionType;", "decodeDrawable", "Landroid/graphics/drawable/Drawable;", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ImageDecoder.kt */
public final class ImageDecoderKt {
    public static final Bitmap decodeBitmap(ImageDecoder.Source $this$decodeBitmap, Function3<? super ImageDecoder, ? super ImageDecoder.ImageInfo, ? super ImageDecoder.Source, Unit> action) {
        Intrinsics.checkNotNullParameter($this$decodeBitmap, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        Bitmap decodeBitmap = ImageDecoder.decodeBitmap($this$decodeBitmap, new ImageDecoderKt$decodeBitmap$1(action));
        Intrinsics.checkNotNullExpressionValue(decodeBitmap, "crossinline action: Imag…ction(info, source)\n    }");
        return decodeBitmap;
    }

    public static final Drawable decodeDrawable(ImageDecoder.Source $this$decodeDrawable, Function3<? super ImageDecoder, ? super ImageDecoder.ImageInfo, ? super ImageDecoder.Source, Unit> action) {
        Intrinsics.checkNotNullParameter($this$decodeDrawable, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        Drawable decodeDrawable = ImageDecoder.decodeDrawable($this$decodeDrawable, new ImageDecoderKt$decodeDrawable$1(action));
        Intrinsics.checkNotNullExpressionValue(decodeDrawable, "crossinline action: Imag…ction(info, source)\n    }");
        return decodeDrawable;
    }
}
