package kotlin.p002io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, mo113d2 = {"Lkotlin/io/TerminateException;", "Lkotlin/io/FileSystemException;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: kotlin.io.TerminateException */
/* compiled from: Utils.kt */
final class TerminateException extends FileSystemException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TerminateException(File file) {
        super(file, (File) null, (String) null, 6, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(file, "file");
    }
}
