package androidx.core.graphics;

import android.graphics.ImageDecoder;
import com.itextpdf.kernel.xmp.PdfConst;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\n¢\u0006\u0002\b\b"}, mo113d2 = {"<anonymous>", "", "decoder", "Landroid/graphics/ImageDecoder;", "info", "Landroid/graphics/ImageDecoder$ImageInfo;", "source", "Landroid/graphics/ImageDecoder$Source;", "onHeaderDecoded"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 176)
/* compiled from: ImageDecoder.kt */
public final class ImageDecoderKt$decodeDrawable$1 implements ImageDecoder.OnHeaderDecodedListener {
    final /* synthetic */ Function3<ImageDecoder, ImageDecoder.ImageInfo, ImageDecoder.Source, Unit> $action;

    public ImageDecoderKt$decodeDrawable$1(Function3<? super ImageDecoder, ? super ImageDecoder.ImageInfo, ? super ImageDecoder.Source, Unit> function3) {
        this.$action = function3;
    }

    public final void onHeaderDecoded(ImageDecoder decoder, ImageDecoder.ImageInfo info, ImageDecoder.Source source) {
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        Intrinsics.checkNotNullParameter(info, "info");
        Intrinsics.checkNotNullParameter(source, PdfConst.Source);
        this.$action.invoke(decoder, info, source);
    }
}
