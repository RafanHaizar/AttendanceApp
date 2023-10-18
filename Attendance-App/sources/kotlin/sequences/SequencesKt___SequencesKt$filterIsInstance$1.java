package kotlin.sequences;

import com.itextpdf.svg.SvgConstants;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, mo113d2 = {"<anonymous>", "", "R", "it", "", "invoke", "(Ljava/lang/Object;)Ljava/lang/Boolean;"}, mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 176)
/* compiled from: _Sequences.kt */
public final class SequencesKt___SequencesKt$filterIsInstance$1 extends Lambda implements Function1<Object, Boolean> {
    public static final SequencesKt___SequencesKt$filterIsInstance$1 INSTANCE = new SequencesKt___SequencesKt$filterIsInstance$1();

    public SequencesKt___SequencesKt$filterIsInstance$1() {
        super(1);
    }

    public final Boolean invoke(Object it) {
        Intrinsics.reifiedOperationMarker(3, SvgConstants.Attributes.PATH_DATA_CATMULL_CURVE);
        return Boolean.valueOf(it instanceof Object);
    }
}
