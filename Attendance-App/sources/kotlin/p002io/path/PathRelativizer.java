package kotlin.p002io.path;

import com.itextpdf.svg.SvgConstants;
import java.nio.file.Path;
import java.nio.file.Paths;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, mo113d2 = {"Lkotlin/io/path/PathRelativizer;", "", "()V", "emptyPath", "Ljava/nio/file/Path;", "kotlin.jvm.PlatformType", "parentPath", "tryRelativeTo", "path", "base", "kotlin-stdlib-jdk7"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* renamed from: kotlin.io.path.PathRelativizer */
/* compiled from: PathUtils.kt */
final class PathRelativizer {
    public static final PathRelativizer INSTANCE = new PathRelativizer();
    private static final Path emptyPath = Paths.get("", new String[0]);
    private static final Path parentPath = Paths.get("..", new String[0]);

    private PathRelativizer() {
    }

    public final Path tryRelativeTo(Path path, Path base) {
        Path path2;
        Intrinsics.checkNotNullParameter(path, SvgConstants.Tags.PATH);
        Intrinsics.checkNotNullParameter(base, "base");
        Path bn = base.normalize();
        Path pn = path.normalize();
        Path rn = bn.relativize(pn);
        int min = Math.min(bn.getNameCount(), pn.getNameCount());
        int i = 0;
        while (i < min) {
            int i2 = i;
            i++;
            Path name = bn.getName(i2);
            Path path3 = parentPath;
            if (!Intrinsics.areEqual((Object) name, (Object) path3)) {
                break;
            } else if (!Intrinsics.areEqual((Object) pn.getName(i2), (Object) path3)) {
                throw new IllegalArgumentException("Unable to compute relative path");
            }
        }
        if (Intrinsics.areEqual((Object) pn, (Object) bn) || !Intrinsics.areEqual((Object) bn, (Object) emptyPath)) {
            String rnString = rn.toString();
            String separator = rn.getFileSystem().getSeparator();
            Intrinsics.checkNotNullExpressionValue(separator, "rn.fileSystem.separator");
            if (StringsKt.endsWith$default(rnString, separator, false, 2, (Object) null)) {
                path2 = rn.getFileSystem().getPath(StringsKt.dropLast(rnString, rn.getFileSystem().getSeparator().length()), new String[0]);
            } else {
                path2 = rn;
            }
        } else {
            path2 = pn;
        }
        Path r = path2;
        Intrinsics.checkNotNullExpressionValue(r, "r");
        return r;
    }
}
