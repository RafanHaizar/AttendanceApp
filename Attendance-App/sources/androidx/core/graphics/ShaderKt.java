package androidx.core.graphics;

import android.graphics.Matrix;
import android.graphics.Shader;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a)\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\u0002\b\u0006H\bø\u0001\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0007"}, mo113d2 = {"transform", "", "Landroid/graphics/Shader;", "block", "Lkotlin/Function1;", "Landroid/graphics/Matrix;", "Lkotlin/ExtensionFunctionType;", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Shader.kt */
public final class ShaderKt {
    public static final void transform(Shader $this$transform, Function1<? super Matrix, Unit> block) {
        Intrinsics.checkNotNullParameter($this$transform, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        Matrix matrix = new Matrix();
        $this$transform.getLocalMatrix(matrix);
        block.invoke(matrix);
        $this$transform.setLocalMatrix(matrix);
    }
}
