package kotlin.p002io.path;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.svg.SvgConstants;
import java.io.IOException;
import java.net.URI;
import java.nio.file.CopyOption;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.slf4j.Marker;

@Metadata(mo112d1 = {"\u0000²\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a*\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00012\u0012\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u001a\"\u00020\u0001H\b¢\u0006\u0002\u0010\u001b\u001a?\u0010\u001c\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007¢\u0006\u0002\u0010!\u001a6\u0010\u001c\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u0010\"\u001aK\u0010#\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007¢\u0006\u0002\u0010%\u001aB\u0010#\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u0010&\u001a\u001c\u0010'\u001a\u00020(2\u0006\u0010\u0017\u001a\u00020\u00022\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*H\u0001\u001a\r\u0010+\u001a\u00020\u0002*\u00020\u0002H\b\u001a\r\u0010,\u001a\u00020\u0001*\u00020\u0002H\b\u001a.\u0010-\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u0002000\u001a\"\u000200H\b¢\u0006\u0002\u00101\u001a\u001f\u0010-\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u00102\u001a\u000203H\b\u001a.\u00104\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u00105\u001a.\u00106\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u00105\u001a.\u00107\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u00105\u001a\u0015\u00108\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u0002H\b\u001a6\u00109\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u0010:\u001a\r\u0010;\u001a\u00020<*\u00020\u0002H\b\u001a\r\u0010=\u001a\u000203*\u00020\u0002H\b\u001a\u0015\u0010>\u001a\u00020\u0002*\u00020\u00022\u0006\u0010?\u001a\u00020\u0002H\n\u001a\u0015\u0010>\u001a\u00020\u0002*\u00020\u00022\u0006\u0010?\u001a\u00020\u0001H\n\u001a&\u0010@\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010B\u001a2\u0010C\u001a\u0002HD\"\n\b\u0000\u0010D\u0018\u0001*\u00020E*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010F\u001a4\u0010G\u001a\u0004\u0018\u0001HD\"\n\b\u0000\u0010D\u0018\u0001*\u00020E*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010F\u001a\r\u0010H\u001a\u00020I*\u00020\u0002H\b\u001a\r\u0010J\u001a\u00020K*\u00020\u0002H\b\u001a.\u0010L\u001a\u00020<*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u00012\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020<0OH\bø\u0001\u0000\u001a0\u0010P\u001a\u0004\u0018\u00010Q*\u00020\u00022\u0006\u0010R\u001a\u00020\u00012\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010S\u001a&\u0010T\u001a\u00020U*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010V\u001a(\u0010W\u001a\u0004\u0018\u00010X*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010Y\u001a,\u0010Z\u001a\b\u0012\u0004\u0012\u00020\\0[*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010]\u001a&\u0010^\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010B\u001a\r\u0010_\u001a\u000203*\u00020\u0002H\b\u001a\r\u0010`\u001a\u000203*\u00020\u0002H\b\u001a\r\u0010a\u001a\u000203*\u00020\u0002H\b\u001a&\u0010b\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010B\u001a\u0015\u0010c\u001a\u000203*\u00020\u00022\u0006\u0010?\u001a\u00020\u0002H\b\u001a\r\u0010d\u001a\u000203*\u00020\u0002H\b\u001a\r\u0010e\u001a\u000203*\u00020\u0002H\b\u001a\u001c\u0010f\u001a\b\u0012\u0004\u0012\u00020\u00020g*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u0001H\u0007\u001a.\u0010h\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u0002000\u001a\"\u000200H\b¢\u0006\u0002\u00101\u001a\u001f\u0010h\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u00102\u001a\u000203H\b\u001a&\u0010i\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010B\u001a2\u0010j\u001a\u0002Hk\"\n\b\u0000\u0010k\u0018\u0001*\u00020l*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010m\u001a<\u0010j\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010Q0n*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00012\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010o\u001a\r\u0010p\u001a\u00020\u0002*\u00020\u0002H\b\u001a\u0014\u0010q\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0016\u0010r\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0014\u0010s\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a8\u0010t\u001a\u00020\u0002*\u00020\u00022\u0006\u0010R\u001a\u00020\u00012\b\u0010u\u001a\u0004\u0018\u00010Q2\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010v\u001a\u0015\u0010w\u001a\u00020\u0002*\u00020\u00022\u0006\u0010u\u001a\u00020UH\b\u001a\u0015\u0010x\u001a\u00020\u0002*\u00020\u00022\u0006\u0010u\u001a\u00020XH\b\u001a\u001b\u0010y\u001a\u00020\u0002*\u00020\u00022\f\u0010u\u001a\b\u0012\u0004\u0012\u00020\\0[H\b\u001a\r\u0010z\u001a\u00020\u0002*\u00020{H\b\u001a@\u0010|\u001a\u0002H}\"\u0004\b\u0000\u0010}*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u00012\u0018\u0010~\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u0012\u0004\u0012\u0002H}0OH\bø\u0001\u0000¢\u0006\u0003\u0010\u0001\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001f\u0010\u0007\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\b\u0010\u0004\u001a\u0004\b\t\u0010\u0006\"\u001e\u0010\n\u001a\u00020\u0001*\u00020\u00028FX\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\u0004\u001a\u0004\b\f\u0010\u0006\"\u001e\u0010\r\u001a\u00020\u0001*\u00020\u00028FX\u0004¢\u0006\f\u0012\u0004\b\u000e\u0010\u0004\u001a\u0004\b\u000f\u0010\u0006\"\u001e\u0010\u0010\u001a\u00020\u0001*\u00020\u00028FX\u0004¢\u0006\f\u0012\u0004\b\u0011\u0010\u0004\u001a\u0004\b\u0012\u0010\u0006\"\u001f\u0010\u0013\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u0004\u001a\u0004\b\u0015\u0010\u0006\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0001"}, mo113d2 = {"extension", "", "Ljava/nio/file/Path;", "getExtension$annotations", "(Ljava/nio/file/Path;)V", "getExtension", "(Ljava/nio/file/Path;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath$annotations", "getInvariantSeparatorsPath", "invariantSeparatorsPathString", "getInvariantSeparatorsPathString$annotations", "getInvariantSeparatorsPathString", "name", "getName$annotations", "getName", "nameWithoutExtension", "getNameWithoutExtension$annotations", "getNameWithoutExtension", "pathString", "getPathString$annotations", "getPathString", "Path", "path", "base", "subpaths", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", "createTempDirectory", "directory", "prefix", "attributes", "Ljava/nio/file/attribute/FileAttribute;", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createTempFile", "suffix", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "fileAttributeViewNotAvailable", "", "attributeViewClass", "Ljava/lang/Class;", "absolute", "absolutePathString", "copyTo", "target", "options", "Ljava/nio/file/CopyOption;", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;", "overwrite", "", "createDirectories", "(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createDirectory", "createFile", "createLinkPointingTo", "createSymbolicLinkPointingTo", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "deleteExisting", "", "deleteIfExists", "div", "other", "exists", "Ljava/nio/file/LinkOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", "fileAttributesView", "V", "Ljava/nio/file/attribute/FileAttributeView;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileAttributeView;", "fileAttributesViewOrNull", "fileSize", "", "fileStore", "Ljava/nio/file/FileStore;", "forEachDirectoryEntry", "glob", "action", "Lkotlin/Function1;", "getAttribute", "", "attribute", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/lang/Object;", "getLastModifiedTime", "Ljava/nio/file/attribute/FileTime;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileTime;", "getOwner", "Ljava/nio/file/attribute/UserPrincipal;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/UserPrincipal;", "getPosixFilePermissions", "", "Ljava/nio/file/attribute/PosixFilePermission;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/util/Set;", "isDirectory", "isExecutable", "isHidden", "isReadable", "isRegularFile", "isSameFileAs", "isSymbolicLink", "isWritable", "listDirectoryEntries", "", "moveTo", "notExists", "readAttributes", "A", "Ljava/nio/file/attribute/BasicFileAttributes;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/BasicFileAttributes;", "", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/util/Map;", "readSymbolicLink", "relativeTo", "relativeToOrNull", "relativeToOrSelf", "setAttribute", "value", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/Object;[Ljava/nio/file/LinkOption;)Ljava/nio/file/Path;", "setLastModifiedTime", "setOwner", "setPosixFilePermissions", "toPath", "Ljava/net/URI;", "useDirectoryEntries", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/nio/file/Path;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 49, mo118xs = "kotlin/io/path/PathsKt")
/* renamed from: kotlin.io.path.PathsKt__PathUtilsKt */
/* compiled from: PathUtils.kt */
class PathsKt__PathUtilsKt extends PathsKt__PathReadWriteKt {
    public static /* synthetic */ void getExtension$annotations(Path path) {
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use invariantSeparatorsPathString property instead.", replaceWith = @ReplaceWith(expression = "invariantSeparatorsPathString", imports = {}))
    public static /* synthetic */ void getInvariantSeparatorsPath$annotations(Path path) {
    }

    public static /* synthetic */ void getInvariantSeparatorsPathString$annotations(Path path) {
    }

    public static /* synthetic */ void getName$annotations(Path path) {
    }

    public static /* synthetic */ void getNameWithoutExtension$annotations(Path path) {
    }

    public static /* synthetic */ void getPathString$annotations(Path path) {
    }

    public static final String getName(Path $this$name) {
        Intrinsics.checkNotNullParameter($this$name, "<this>");
        Path fileName = $this$name.getFileName();
        String obj = fileName == null ? null : fileName.toString();
        return obj == null ? "" : obj;
    }

    public static final String getNameWithoutExtension(Path $this$nameWithoutExtension) {
        Intrinsics.checkNotNullParameter($this$nameWithoutExtension, "<this>");
        Path fileName = $this$nameWithoutExtension.getFileName();
        return fileName == null ? "" : StringsKt.substringBeforeLast$default(fileName.toString(), ".", (String) null, 2, (Object) null);
    }

    public static final String getExtension(Path $this$extension) {
        Intrinsics.checkNotNullParameter($this$extension, "<this>");
        Path fileName = $this$extension.getFileName();
        return fileName == null ? "" : StringsKt.substringAfterLast(fileName.toString(), '.', "");
    }

    private static final String getPathString(Path $this$pathString) {
        Intrinsics.checkNotNullParameter($this$pathString, "<this>");
        return $this$pathString.toString();
    }

    public static final String getInvariantSeparatorsPathString(Path $this$invariantSeparatorsPathString) {
        Intrinsics.checkNotNullParameter($this$invariantSeparatorsPathString, "<this>");
        String separator = $this$invariantSeparatorsPathString.getFileSystem().getSeparator();
        if (Intrinsics.areEqual((Object) separator, (Object) "/")) {
            return $this$invariantSeparatorsPathString.toString();
        }
        String obj = $this$invariantSeparatorsPathString.toString();
        Intrinsics.checkNotNullExpressionValue(separator, "separator");
        return StringsKt.replace$default(obj, separator, "/", false, 4, (Object) null);
    }

    private static final String getInvariantSeparatorsPath(Path $this$invariantSeparatorsPath) {
        Intrinsics.checkNotNullParameter($this$invariantSeparatorsPath, "<this>");
        return PathsKt.getInvariantSeparatorsPathString($this$invariantSeparatorsPath);
    }

    private static final Path absolute(Path $this$absolute) {
        Intrinsics.checkNotNullParameter($this$absolute, "<this>");
        Path absolutePath = $this$absolute.toAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "toAbsolutePath()");
        return absolutePath;
    }

    private static final String absolutePathString(Path $this$absolutePathString) {
        Intrinsics.checkNotNullParameter($this$absolutePathString, "<this>");
        return $this$absolutePathString.toAbsolutePath().toString();
    }

    public static final Path relativeTo(Path $this$relativeTo, Path base) {
        Intrinsics.checkNotNullParameter($this$relativeTo, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        try {
            return PathRelativizer.INSTANCE.tryRelativeTo($this$relativeTo, base);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + "\nthis path: " + $this$relativeTo + "\nbase path: " + base, e);
        }
    }

    public static final Path relativeToOrSelf(Path $this$relativeToOrSelf, Path base) {
        Intrinsics.checkNotNullParameter($this$relativeToOrSelf, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        Path relativeToOrNull = PathsKt.relativeToOrNull($this$relativeToOrSelf, base);
        return relativeToOrNull == null ? $this$relativeToOrSelf : relativeToOrNull;
    }

    public static final Path relativeToOrNull(Path $this$relativeToOrNull, Path base) {
        Intrinsics.checkNotNullParameter($this$relativeToOrNull, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        try {
            return PathRelativizer.INSTANCE.tryRelativeTo($this$relativeToOrNull, base);
        } catch (IllegalArgumentException e) {
            Path path = null;
            return null;
        }
    }

    static /* synthetic */ Path copyTo$default(Path $this$copyTo_u24default, Path target, boolean overwrite, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            overwrite = false;
        }
        Intrinsics.checkNotNullParameter($this$copyTo_u24default, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        CopyOption[] options = overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        Path copy = Files.copy($this$copyTo_u24default, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(copy, "copy(this, target, *options)");
        return copy;
    }

    private static final Path copyTo(Path $this$copyTo, Path target, boolean overwrite) throws IOException {
        Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        CopyOption[] options = overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        Path copy = Files.copy($this$copyTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(copy, "copy(this, target, *options)");
        return copy;
    }

    private static final Path copyTo(Path $this$copyTo, Path target, CopyOption... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(options, "options");
        Path copy = Files.copy($this$copyTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(copy, "copy(this, target, *options)");
        return copy;
    }

    private static final boolean exists(Path $this$exists, LinkOption... options) {
        Intrinsics.checkNotNullParameter($this$exists, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.exists($this$exists, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final boolean notExists(Path $this$notExists, LinkOption... options) {
        Intrinsics.checkNotNullParameter($this$notExists, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.notExists($this$notExists, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final boolean isRegularFile(Path $this$isRegularFile, LinkOption... options) {
        Intrinsics.checkNotNullParameter($this$isRegularFile, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.isRegularFile($this$isRegularFile, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final boolean isDirectory(Path $this$isDirectory, LinkOption... options) {
        Intrinsics.checkNotNullParameter($this$isDirectory, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.isDirectory($this$isDirectory, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final boolean isSymbolicLink(Path $this$isSymbolicLink) {
        Intrinsics.checkNotNullParameter($this$isSymbolicLink, "<this>");
        return Files.isSymbolicLink($this$isSymbolicLink);
    }

    private static final boolean isExecutable(Path $this$isExecutable) {
        Intrinsics.checkNotNullParameter($this$isExecutable, "<this>");
        return Files.isExecutable($this$isExecutable);
    }

    private static final boolean isHidden(Path $this$isHidden) throws IOException {
        Intrinsics.checkNotNullParameter($this$isHidden, "<this>");
        return Files.isHidden($this$isHidden);
    }

    private static final boolean isReadable(Path $this$isReadable) {
        Intrinsics.checkNotNullParameter($this$isReadable, "<this>");
        return Files.isReadable($this$isReadable);
    }

    private static final boolean isWritable(Path $this$isWritable) {
        Intrinsics.checkNotNullParameter($this$isWritable, "<this>");
        return Files.isWritable($this$isWritable);
    }

    private static final boolean isSameFileAs(Path $this$isSameFileAs, Path other) throws IOException {
        Intrinsics.checkNotNullParameter($this$isSameFileAs, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return Files.isSameFile($this$isSameFileAs, other);
    }

    public static /* synthetic */ List listDirectoryEntries$default(Path path, String str, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            str = Marker.ANY_MARKER;
        }
        return PathsKt.listDirectoryEntries(path, str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0027, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
        kotlin.p002io.CloseableKt.closeFinally(r0, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.util.List<java.nio.file.Path> listDirectoryEntries(java.nio.file.Path r4, java.lang.String r5) throws java.io.IOException {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.lang.String r0 = "glob"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.nio.file.DirectoryStream r0 = java.nio.file.Files.newDirectoryStream(r4, r5)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = r0
            java.nio.file.DirectoryStream r1 = (java.nio.file.DirectoryStream) r1     // Catch:{ all -> 0x0025 }
            r2 = 0
            java.lang.String r3 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r3)     // Catch:{ all -> 0x0025 }
            r3 = r1
            java.lang.Iterable r3 = (java.lang.Iterable) r3     // Catch:{ all -> 0x0025 }
            java.util.List r3 = kotlin.collections.CollectionsKt.toList(r3)     // Catch:{ all -> 0x0025 }
            r1 = 0
            kotlin.p002io.CloseableKt.closeFinally(r0, r1)
            return r3
        L_0x0025:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0027 }
        L_0x0027:
            r2 = move-exception
            kotlin.p002io.CloseableKt.closeFinally(r0, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.path.PathsKt__PathUtilsKt.listDirectoryEntries(java.nio.file.Path, java.lang.String):java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003c, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003d, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
        kotlin.p002io.CloseableKt.closeFinally(r6, r0);
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0046, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object useDirectoryEntries$default(java.nio.file.Path r3, java.lang.String r4, kotlin.jvm.functions.Function1 r5, int r6, java.lang.Object r7) throws java.io.IOException {
        /*
            r7 = 1
            r6 = r6 & r7
            if (r6 == 0) goto L_0x0006
            java.lang.String r4 = "*"
        L_0x0006:
            java.lang.String r6 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r6)
            java.lang.String r6 = "glob"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r6)
            java.lang.String r6 = "block"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r6)
            java.nio.file.DirectoryStream r6 = java.nio.file.Files.newDirectoryStream(r3, r4)
            java.io.Closeable r6 = (java.io.Closeable) r6
            r0 = r6
            java.nio.file.DirectoryStream r0 = (java.nio.file.DirectoryStream) r0     // Catch:{ all -> 0x003a }
            r1 = 0
            java.lang.String r2 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r2)     // Catch:{ all -> 0x003a }
            r2 = r0
            java.lang.Iterable r2 = (java.lang.Iterable) r2     // Catch:{ all -> 0x003a }
            kotlin.sequences.Sequence r2 = kotlin.collections.CollectionsKt.asSequence(r2)     // Catch:{ all -> 0x003a }
            java.lang.Object r2 = r5.invoke(r2)     // Catch:{ all -> 0x003a }
            kotlin.jvm.internal.InlineMarker.finallyStart(r7)
            r0 = 0
            kotlin.p002io.CloseableKt.closeFinally(r6, r0)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r7)
            return r2
        L_0x003a:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x003c }
        L_0x003c:
            r1 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r7)
            kotlin.p002io.CloseableKt.closeFinally(r6, r0)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r7)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.path.PathsKt__PathUtilsKt.useDirectoryEntries$default(java.nio.file.Path, java.lang.String, kotlin.jvm.functions.Function1, int, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0041, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0037, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0038, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
        kotlin.p002io.CloseableKt.closeFinally(r0, r2);
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final <T> T useDirectoryEntries(java.nio.file.Path r5, java.lang.String r6, kotlin.jvm.functions.Function1<? super kotlin.sequences.Sequence<? extends java.nio.file.Path>, ? extends T> r7) throws java.io.IOException {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "glob"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "block"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            java.nio.file.DirectoryStream r0 = java.nio.file.Files.newDirectoryStream(r5, r6)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 1
            r2 = r0
            java.nio.file.DirectoryStream r2 = (java.nio.file.DirectoryStream) r2     // Catch:{ all -> 0x0035 }
            r3 = 0
            java.lang.String r4 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r4)     // Catch:{ all -> 0x0035 }
            r4 = r2
            java.lang.Iterable r4 = (java.lang.Iterable) r4     // Catch:{ all -> 0x0035 }
            kotlin.sequences.Sequence r4 = kotlin.collections.CollectionsKt.asSequence(r4)     // Catch:{ all -> 0x0035 }
            java.lang.Object r4 = r7.invoke(r4)     // Catch:{ all -> 0x0035 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            r2 = 0
            kotlin.p002io.CloseableKt.closeFinally(r0, r2)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return r4
        L_0x0035:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0037 }
        L_0x0037:
            r3 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlin.p002io.CloseableKt.closeFinally(r0, r2)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.path.PathsKt__PathUtilsKt.useDirectoryEntries(java.nio.file.Path, java.lang.String, kotlin.jvm.functions.Function1):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004b, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004c, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
        kotlin.p002io.CloseableKt.closeFinally(r9, r0);
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0056, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void forEachDirectoryEntry$default(java.nio.file.Path r6, java.lang.String r7, kotlin.jvm.functions.Function1 r8, int r9, java.lang.Object r10) throws java.io.IOException {
        /*
            r10 = 1
            r9 = r9 & r10
            if (r9 == 0) goto L_0x0006
            java.lang.String r7 = "*"
        L_0x0006:
            java.lang.String r9 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r9)
            java.lang.String r9 = "glob"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r9)
            java.lang.String r9 = "action"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r9)
            java.nio.file.DirectoryStream r9 = java.nio.file.Files.newDirectoryStream(r6, r7)
            java.io.Closeable r9 = (java.io.Closeable) r9
            r0 = r9
            java.nio.file.DirectoryStream r0 = (java.nio.file.DirectoryStream) r0     // Catch:{ all -> 0x0049 }
            r1 = 0
            java.lang.String r2 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r2)     // Catch:{ all -> 0x0049 }
            r2 = r0
            java.lang.Iterable r2 = (java.lang.Iterable) r2     // Catch:{ all -> 0x0049 }
            r3 = 0
            java.util.Iterator r4 = r2.iterator()     // Catch:{ all -> 0x0049 }
        L_0x002c:
            boolean r5 = r4.hasNext()     // Catch:{ all -> 0x0049 }
            if (r5 == 0) goto L_0x003a
            java.lang.Object r5 = r4.next()     // Catch:{ all -> 0x0049 }
            r8.invoke(r5)     // Catch:{ all -> 0x0049 }
            goto L_0x002c
        L_0x003a:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0049 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            r0 = 0
            kotlin.p002io.CloseableKt.closeFinally(r9, r0)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            return
        L_0x0049:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x004b }
        L_0x004b:
            r1 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            kotlin.p002io.CloseableKt.closeFinally(r9, r0)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            goto L_0x0057
        L_0x0056:
            throw r1
        L_0x0057:
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.path.PathsKt__PathUtilsKt.forEachDirectoryEntry$default(java.nio.file.Path, java.lang.String, kotlin.jvm.functions.Function1, int, java.lang.Object):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0046, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0047, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
        kotlin.p002io.CloseableKt.closeFinally(r0, r2);
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0051, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final void forEachDirectoryEntry(java.nio.file.Path r8, java.lang.String r9, kotlin.jvm.functions.Function1<? super java.nio.file.Path, kotlin.Unit> r10) throws java.io.IOException {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            java.lang.String r0 = "glob"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            java.lang.String r0 = "action"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
            java.nio.file.DirectoryStream r0 = java.nio.file.Files.newDirectoryStream(r8, r9)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 1
            r2 = r0
            java.nio.file.DirectoryStream r2 = (java.nio.file.DirectoryStream) r2     // Catch:{ all -> 0x0044 }
            r3 = 0
            java.lang.String r4 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r4)     // Catch:{ all -> 0x0044 }
            r4 = r2
            java.lang.Iterable r4 = (java.lang.Iterable) r4     // Catch:{ all -> 0x0044 }
            r5 = 0
            java.util.Iterator r6 = r4.iterator()     // Catch:{ all -> 0x0044 }
        L_0x0027:
            boolean r7 = r6.hasNext()     // Catch:{ all -> 0x0044 }
            if (r7 == 0) goto L_0x0035
            java.lang.Object r7 = r6.next()     // Catch:{ all -> 0x0044 }
            r10.invoke(r7)     // Catch:{ all -> 0x0044 }
            goto L_0x0027
        L_0x0035:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0044 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            r2 = 0
            kotlin.p002io.CloseableKt.closeFinally(r0, r2)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            return
        L_0x0044:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0046 }
        L_0x0046:
            r3 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r1)
            kotlin.p002io.CloseableKt.closeFinally(r0, r2)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r1)
            goto L_0x0052
        L_0x0051:
            throw r3
        L_0x0052:
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.path.PathsKt__PathUtilsKt.forEachDirectoryEntry(java.nio.file.Path, java.lang.String, kotlin.jvm.functions.Function1):void");
    }

    private static final long fileSize(Path $this$fileSize) throws IOException {
        Intrinsics.checkNotNullParameter($this$fileSize, "<this>");
        return Files.size($this$fileSize);
    }

    private static final void deleteExisting(Path $this$deleteExisting) throws IOException {
        Intrinsics.checkNotNullParameter($this$deleteExisting, "<this>");
        Files.delete($this$deleteExisting);
    }

    private static final boolean deleteIfExists(Path $this$deleteIfExists) throws IOException {
        Intrinsics.checkNotNullParameter($this$deleteIfExists, "<this>");
        return Files.deleteIfExists($this$deleteIfExists);
    }

    private static final Path createDirectory(Path $this$createDirectory, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter($this$createDirectory, "<this>");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path createDirectory = Files.createDirectory($this$createDirectory, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createDirectory, "createDirectory(this, *attributes)");
        return createDirectory;
    }

    private static final Path createDirectories(Path $this$createDirectories, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter($this$createDirectories, "<this>");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path createDirectories = Files.createDirectories($this$createDirectories, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createDirectories, "createDirectories(this, *attributes)");
        return createDirectories;
    }

    private static final Path moveTo(Path $this$moveTo, Path target, CopyOption... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$moveTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(options, "options");
        Path move = Files.move($this$moveTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(move, "move(this, target, *options)");
        return move;
    }

    static /* synthetic */ Path moveTo$default(Path $this$moveTo_u24default, Path target, boolean overwrite, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            overwrite = false;
        }
        Intrinsics.checkNotNullParameter($this$moveTo_u24default, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        CopyOption[] options = overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        Path move = Files.move($this$moveTo_u24default, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(move, "move(this, target, *options)");
        return move;
    }

    private static final Path moveTo(Path $this$moveTo, Path target, boolean overwrite) throws IOException {
        Intrinsics.checkNotNullParameter($this$moveTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        CopyOption[] options = overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        Path move = Files.move($this$moveTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(move, "move(this, target, *options)");
        return move;
    }

    private static final FileStore fileStore(Path $this$fileStore) throws IOException {
        Intrinsics.checkNotNullParameter($this$fileStore, "<this>");
        FileStore fileStore = Files.getFileStore($this$fileStore);
        Intrinsics.checkNotNullExpressionValue(fileStore, "getFileStore(this)");
        return fileStore;
    }

    private static final Object getAttribute(Path $this$getAttribute, String attribute, LinkOption... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$getAttribute, "<this>");
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.getAttribute($this$getAttribute, attribute, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final Path setAttribute(Path $this$setAttribute, String attribute, Object value, LinkOption... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$setAttribute, "<this>");
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        Intrinsics.checkNotNullParameter(options, "options");
        Path attribute2 = Files.setAttribute($this$setAttribute, attribute, value, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(attribute2, "setAttribute(this, attribute, value, *options)");
        return attribute2;
    }

    private static final /* synthetic */ <V extends FileAttributeView> V fileAttributesViewOrNull(Path $this$fileAttributesViewOrNull, LinkOption... options) {
        Intrinsics.checkNotNullParameter($this$fileAttributesViewOrNull, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.reifiedOperationMarker(4, SvgConstants.Attributes.PATH_DATA_LINE_TO_V);
        Class<FileAttributeView> cls = FileAttributeView.class;
        Class cls2 = cls;
        return Files.getFileAttributeView($this$fileAttributesViewOrNull, cls, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final /* synthetic */ <V extends FileAttributeView> V fileAttributesView(Path $this$fileAttributesView, LinkOption... options) {
        Intrinsics.checkNotNullParameter($this$fileAttributesView, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.reifiedOperationMarker(4, SvgConstants.Attributes.PATH_DATA_LINE_TO_V);
        Class<FileAttributeView> cls = FileAttributeView.class;
        Class cls2 = cls;
        V fileAttributeView = Files.getFileAttributeView($this$fileAttributesView, cls, (LinkOption[]) Arrays.copyOf(options, options.length));
        if (fileAttributeView != null) {
            FileAttributeView fileAttributeView2 = (FileAttributeView) fileAttributeView;
            return fileAttributeView;
        }
        Intrinsics.reifiedOperationMarker(4, SvgConstants.Attributes.PATH_DATA_LINE_TO_V);
        Class<FileAttributeView> cls3 = FileAttributeView.class;
        Class cls4 = cls3;
        PathsKt.fileAttributeViewNotAvailable($this$fileAttributesView, cls3);
        throw new KotlinNothingValueException();
    }

    public static final Void fileAttributeViewNotAvailable(Path path, Class<?> attributeViewClass) {
        Intrinsics.checkNotNullParameter(path, SvgConstants.Tags.PATH);
        Intrinsics.checkNotNullParameter(attributeViewClass, "attributeViewClass");
        throw new UnsupportedOperationException("The desired attribute view type " + attributeViewClass + " is not available for the file " + path + '.');
    }

    private static final /* synthetic */ <A extends BasicFileAttributes> A readAttributes(Path $this$readAttributes, LinkOption... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$readAttributes, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.reifiedOperationMarker(4, SvgConstants.Attributes.PATH_DATA_ELLIPTICAL_ARC_A);
        Class<BasicFileAttributes> cls = BasicFileAttributes.class;
        Class cls2 = cls;
        A readAttributes = Files.readAttributes($this$readAttributes, cls, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(readAttributes, "readAttributes(this, A::class.java, *options)");
        BasicFileAttributes basicFileAttributes = (BasicFileAttributes) readAttributes;
        return readAttributes;
    }

    private static final Map<String, Object> readAttributes(Path $this$readAttributes, String attributes, LinkOption... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$readAttributes, "<this>");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(options, "options");
        Map<String, Object> readAttributes = Files.readAttributes($this$readAttributes, attributes, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(readAttributes, "readAttributes(this, attributes, *options)");
        return readAttributes;
    }

    private static final FileTime getLastModifiedTime(Path $this$getLastModifiedTime, LinkOption... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$getLastModifiedTime, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        FileTime lastModifiedTime = Files.getLastModifiedTime($this$getLastModifiedTime, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(lastModifiedTime, "getLastModifiedTime(this, *options)");
        return lastModifiedTime;
    }

    private static final Path setLastModifiedTime(Path $this$setLastModifiedTime, FileTime value) throws IOException {
        Intrinsics.checkNotNullParameter($this$setLastModifiedTime, "<this>");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        Path lastModifiedTime = Files.setLastModifiedTime($this$setLastModifiedTime, value);
        Intrinsics.checkNotNullExpressionValue(lastModifiedTime, "setLastModifiedTime(this, value)");
        return lastModifiedTime;
    }

    private static final UserPrincipal getOwner(Path $this$getOwner, LinkOption... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$getOwner, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        return Files.getOwner($this$getOwner, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final Path setOwner(Path $this$setOwner, UserPrincipal value) throws IOException {
        Intrinsics.checkNotNullParameter($this$setOwner, "<this>");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        Path owner = Files.setOwner($this$setOwner, value);
        Intrinsics.checkNotNullExpressionValue(owner, "setOwner(this, value)");
        return owner;
    }

    private static final Set<PosixFilePermission> getPosixFilePermissions(Path $this$getPosixFilePermissions, LinkOption... options) throws IOException {
        Intrinsics.checkNotNullParameter($this$getPosixFilePermissions, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        Set<PosixFilePermission> posixFilePermissions = Files.getPosixFilePermissions($this$getPosixFilePermissions, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(posixFilePermissions, "getPosixFilePermissions(this, *options)");
        return posixFilePermissions;
    }

    private static final Path setPosixFilePermissions(Path $this$setPosixFilePermissions, Set<? extends PosixFilePermission> value) throws IOException {
        Intrinsics.checkNotNullParameter($this$setPosixFilePermissions, "<this>");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        Path posixFilePermissions = Files.setPosixFilePermissions($this$setPosixFilePermissions, value);
        Intrinsics.checkNotNullExpressionValue(posixFilePermissions, "setPosixFilePermissions(this, value)");
        return posixFilePermissions;
    }

    private static final Path createLinkPointingTo(Path $this$createLinkPointingTo, Path target) throws IOException {
        Intrinsics.checkNotNullParameter($this$createLinkPointingTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Path createLink = Files.createLink($this$createLinkPointingTo, target);
        Intrinsics.checkNotNullExpressionValue(createLink, "createLink(this, target)");
        return createLink;
    }

    private static final Path createSymbolicLinkPointingTo(Path $this$createSymbolicLinkPointingTo, Path target, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter($this$createSymbolicLinkPointingTo, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path createSymbolicLink = Files.createSymbolicLink($this$createSymbolicLinkPointingTo, target, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createSymbolicLink, "createSymbolicLink(this, target, *attributes)");
        return createSymbolicLink;
    }

    private static final Path readSymbolicLink(Path $this$readSymbolicLink) throws IOException {
        Intrinsics.checkNotNullParameter($this$readSymbolicLink, "<this>");
        Path readSymbolicLink = Files.readSymbolicLink($this$readSymbolicLink);
        Intrinsics.checkNotNullExpressionValue(readSymbolicLink, "readSymbolicLink(this)");
        return readSymbolicLink;
    }

    private static final Path createFile(Path $this$createFile, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter($this$createFile, "<this>");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path createFile = Files.createFile($this$createFile, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createFile, "createFile(this, *attributes)");
        return createFile;
    }

    static /* synthetic */ Path createTempFile$default(String prefix, String suffix, FileAttribute[] attributes, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            prefix = null;
        }
        if ((i & 2) != 0) {
            suffix = null;
        }
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path createTempFile = Files.createTempFile(prefix, suffix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempFile, "createTempFile(prefix, suffix, *attributes)");
        return createTempFile;
    }

    private static final Path createTempFile(String prefix, String suffix, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path createTempFile = Files.createTempFile(prefix, suffix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempFile, "createTempFile(prefix, suffix, *attributes)");
        return createTempFile;
    }

    public static /* synthetic */ Path createTempFile$default(Path path, String str, String str2, FileAttribute[] fileAttributeArr, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            str2 = null;
        }
        return PathsKt.createTempFile(path, str, str2, fileAttributeArr);
    }

    public static final Path createTempFile(Path directory, String prefix, String suffix, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (directory != null) {
            Path createTempFile = Files.createTempFile(directory, prefix, suffix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
            Intrinsics.checkNotNullExpressionValue(createTempFile, "createTempFile(directory…fix, suffix, *attributes)");
            return createTempFile;
        }
        Path createTempFile2 = Files.createTempFile(prefix, suffix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempFile2, "createTempFile(prefix, suffix, *attributes)");
        return createTempFile2;
    }

    static /* synthetic */ Path createTempDirectory$default(String prefix, FileAttribute[] attributes, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            prefix = null;
        }
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path createTempDirectory = Files.createTempDirectory(prefix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempDirectory, "createTempDirectory(prefix, *attributes)");
        return createTempDirectory;
    }

    private static final Path createTempDirectory(String prefix, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Path createTempDirectory = Files.createTempDirectory(prefix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempDirectory, "createTempDirectory(prefix, *attributes)");
        return createTempDirectory;
    }

    public static /* synthetic */ Path createTempDirectory$default(Path path, String str, FileAttribute[] fileAttributeArr, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            str = null;
        }
        return PathsKt.createTempDirectory(path, str, fileAttributeArr);
    }

    public static final Path createTempDirectory(Path directory, String prefix, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (directory != null) {
            Path createTempDirectory = Files.createTempDirectory(directory, prefix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
            Intrinsics.checkNotNullExpressionValue(createTempDirectory, "createTempDirectory(dire…ory, prefix, *attributes)");
            return createTempDirectory;
        }
        Path createTempDirectory2 = Files.createTempDirectory(prefix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempDirectory2, "createTempDirectory(prefix, *attributes)");
        return createTempDirectory2;
    }

    private static final Path div(Path $this$div, Path other) {
        Intrinsics.checkNotNullParameter($this$div, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Path resolve = $this$div.resolve(other);
        Intrinsics.checkNotNullExpressionValue(resolve, "this.resolve(other)");
        return resolve;
    }

    private static final Path div(Path $this$div, String other) {
        Intrinsics.checkNotNullParameter($this$div, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Path resolve = $this$div.resolve(other);
        Intrinsics.checkNotNullExpressionValue(resolve, "this.resolve(other)");
        return resolve;
    }

    private static final Path Path(String path) {
        Intrinsics.checkNotNullParameter(path, SvgConstants.Tags.PATH);
        Path path2 = Paths.get(path, new String[0]);
        Intrinsics.checkNotNullExpressionValue(path2, "get(path)");
        return path2;
    }

    private static final Path Path(String base, String... subpaths) {
        Intrinsics.checkNotNullParameter(base, "base");
        Intrinsics.checkNotNullParameter(subpaths, "subpaths");
        Path path = Paths.get(base, (String[]) Arrays.copyOf(subpaths, subpaths.length));
        Intrinsics.checkNotNullExpressionValue(path, "get(base, *subpaths)");
        return path;
    }

    private static final Path toPath(URI $this$toPath) {
        Intrinsics.checkNotNullParameter($this$toPath, "<this>");
        Path path = Paths.get($this$toPath);
        Intrinsics.checkNotNullExpressionValue(path, "get(this)");
        return path;
    }
}
