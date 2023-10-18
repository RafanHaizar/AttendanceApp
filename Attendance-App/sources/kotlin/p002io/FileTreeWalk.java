package kotlin.p002io;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(mo112d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\u001a\u001b\u001cB\u0019\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0001\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\b\u00128\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0017H\u0002J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0014J\u001a\u0010\u0007\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t0\bJ \u0010\f\u001a\u00020\u00002\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000b0\rJ\u001a\u0010\n\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b0\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0004¢\u0006\u0002\n\u0000R@\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rX\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0002X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, mo113d2 = {"Lkotlin/io/FileTreeWalk;", "Lkotlin/sequences/Sequence;", "Ljava/io/File;", "start", "direction", "Lkotlin/io/FileWalkDirection;", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;)V", "onEnter", "Lkotlin/Function1;", "", "onLeave", "", "onFail", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "f", "Ljava/io/IOException;", "e", "maxDepth", "", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;I)V", "iterator", "", "depth", "function", "DirectoryState", "FileTreeWalkIterator", "WalkState", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* renamed from: kotlin.io.FileTreeWalk */
/* compiled from: FileTreeWalk.kt */
public final class FileTreeWalk implements Sequence<File> {
    /* access modifiers changed from: private */
    public final FileWalkDirection direction;
    /* access modifiers changed from: private */
    public final int maxDepth;
    /* access modifiers changed from: private */
    public final Function1<File, Boolean> onEnter;
    /* access modifiers changed from: private */
    public final Function2<File, IOException, Unit> onFail;
    /* access modifiers changed from: private */
    public final Function1<File, Unit> onLeave;
    /* access modifiers changed from: private */
    public final File start;

    private FileTreeWalk(File start2, FileWalkDirection direction2, Function1<? super File, Boolean> onEnter2, Function1<? super File, Unit> onLeave2, Function2<? super File, ? super IOException, Unit> onFail2, int maxDepth2) {
        this.start = start2;
        this.direction = direction2;
        this.onEnter = onEnter2;
        this.onLeave = onLeave2;
        this.onFail = onFail2;
        this.maxDepth = maxDepth2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* synthetic */ FileTreeWalk(java.io.File r8, kotlin.p002io.FileWalkDirection r9, kotlin.jvm.functions.Function1 r10, kotlin.jvm.functions.Function1 r11, kotlin.jvm.functions.Function2 r12, int r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r15 = r14 & 2
            if (r15 == 0) goto L_0x0008
            kotlin.io.FileWalkDirection r9 = kotlin.p002io.FileWalkDirection.TOP_DOWN
            r2 = r9
            goto L_0x0009
        L_0x0008:
            r2 = r9
        L_0x0009:
            r9 = r14 & 32
            if (r9 == 0) goto L_0x0014
            r13 = 2147483647(0x7fffffff, float:NaN)
            r6 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0015
        L_0x0014:
            r6 = r13
        L_0x0015:
            r0 = r7
            r1 = r8
            r3 = r10
            r4 = r11
            r5 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FileTreeWalk.<init>(java.io.File, kotlin.io.FileWalkDirection, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function2, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public FileTreeWalk(File start2, FileWalkDirection direction2) {
        this(start2, direction2, (Function1) null, (Function1) null, (Function2) null, 0, 32, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(start2, "start");
        Intrinsics.checkNotNullParameter(direction2, CommonCssConstants.DIRECTION);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ FileTreeWalk(File file, FileWalkDirection fileWalkDirection, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? FileWalkDirection.TOP_DOWN : fileWalkDirection);
    }

    public Iterator<File> iterator() {
        return new FileTreeWalkIterator();
    }

    @Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, mo113d2 = {"Lkotlin/io/FileTreeWalk$WalkState;", "", "root", "Ljava/io/File;", "(Ljava/io/File;)V", "getRoot", "()Ljava/io/File;", "step", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* renamed from: kotlin.io.FileTreeWalk$WalkState */
    /* compiled from: FileTreeWalk.kt */
    private static abstract class WalkState {
        private final File root;

        public abstract File step();

        public WalkState(File root2) {
            Intrinsics.checkNotNullParameter(root2, CommonCssConstants.ROOT);
            this.root = root2;
        }

        public final File getRoot() {
            return this.root;
        }
    }

    @Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, mo113d2 = {"Lkotlin/io/FileTreeWalk$DirectoryState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootDir", "Ljava/io/File;", "(Ljava/io/File;)V", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* renamed from: kotlin.io.FileTreeWalk$DirectoryState */
    /* compiled from: FileTreeWalk.kt */
    private static abstract class DirectoryState extends WalkState {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public DirectoryState(File rootDir) {
            super(rootDir);
            Intrinsics.checkNotNullParameter(rootDir, "rootDir");
            if (!rootDir.isDirectory()) {
                throw new AssertionError("rootDir must be verified to be directory beforehand.");
            }
        }
    }

    @Metadata(mo112d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\r\u000e\u000fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0002J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0010R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, mo113d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;", "Lkotlin/collections/AbstractIterator;", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk;)V", "state", "Ljava/util/ArrayDeque;", "Lkotlin/io/FileTreeWalk$WalkState;", "computeNext", "", "directoryState", "Lkotlin/io/FileTreeWalk$DirectoryState;", "root", "gotoNext", "BottomUpDirectoryState", "SingleFileState", "TopDownDirectoryState", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* renamed from: kotlin.io.FileTreeWalk$FileTreeWalkIterator */
    /* compiled from: FileTreeWalk.kt */
    private final class FileTreeWalkIterator extends AbstractIterator<File> {
        private final ArrayDeque<WalkState> state;

        @Metadata(mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
        /* renamed from: kotlin.io.FileTreeWalk$FileTreeWalkIterator$WhenMappings */
        /* compiled from: FileTreeWalk.kt */
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[FileWalkDirection.values().length];
                iArr[FileWalkDirection.TOP_DOWN.ordinal()] = 1;
                iArr[FileWalkDirection.BOTTOM_UP.ordinal()] = 2;
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public FileTreeWalkIterator() {
            ArrayDeque<WalkState> arrayDeque = new ArrayDeque<>();
            this.state = arrayDeque;
            if (FileTreeWalk.this.start.isDirectory()) {
                arrayDeque.push(directoryState(FileTreeWalk.this.start));
            } else if (FileTreeWalk.this.start.isFile()) {
                arrayDeque.push(new SingleFileState(this, FileTreeWalk.this.start));
            } else {
                done();
            }
        }

        /* access modifiers changed from: protected */
        public void computeNext() {
            File nextFile = gotoNext();
            if (nextFile != null) {
                setNext(nextFile);
            } else {
                done();
            }
        }

        private final DirectoryState directoryState(File root) {
            switch (WhenMappings.$EnumSwitchMapping$0[FileTreeWalk.this.direction.ordinal()]) {
                case 1:
                    return new TopDownDirectoryState(this, root);
                case 2:
                    return new BottomUpDirectoryState(this, root);
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        private final File gotoNext() {
            File file;
            while (true) {
                WalkState topState = this.state.peek();
                if (topState == null) {
                    return null;
                }
                file = topState.step();
                if (file == null) {
                    this.state.pop();
                } else if (Intrinsics.areEqual((Object) file, (Object) topState.getRoot()) || !file.isDirectory() || this.state.size() >= FileTreeWalk.this.maxDepth) {
                    return file;
                } else {
                    this.state.push(directoryState(file));
                }
            }
            return file;
        }

        @Metadata(mo112d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\nX\u000e¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, mo113d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$BottomUpDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "failed", "", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "step", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
        /* renamed from: kotlin.io.FileTreeWalk$FileTreeWalkIterator$BottomUpDirectoryState */
        /* compiled from: FileTreeWalk.kt */
        private final class BottomUpDirectoryState extends DirectoryState {
            private boolean failed;
            private int fileIndex;
            private File[] fileList;
            private boolean rootVisited;
            final /* synthetic */ FileTreeWalkIterator this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public BottomUpDirectoryState(FileTreeWalkIterator this$02, File rootDir) {
                super(rootDir);
                Intrinsics.checkNotNullParameter(rootDir, "rootDir");
                this.this$0 = this$02;
            }

            public File step() {
                if (!this.failed && this.fileList == null) {
                    Function1 access$getOnEnter$p = FileTreeWalk.this.onEnter;
                    boolean z = false;
                    if (access$getOnEnter$p != null && !((Boolean) access$getOnEnter$p.invoke(getRoot())).booleanValue()) {
                        z = true;
                    }
                    if (z) {
                        return null;
                    }
                    File[] listFiles = getRoot().listFiles();
                    this.fileList = listFiles;
                    if (listFiles == null) {
                        Function2 access$getOnFail$p = FileTreeWalk.this.onFail;
                        if (access$getOnFail$p != null) {
                            access$getOnFail$p.invoke(getRoot(), new AccessDeniedException(getRoot(), (File) null, "Cannot list files in a directory", 2, (DefaultConstructorMarker) null));
                        }
                        this.failed = true;
                    }
                }
                File[] fileArr = this.fileList;
                if (fileArr != null) {
                    int i = this.fileIndex;
                    Intrinsics.checkNotNull(fileArr);
                    if (i < fileArr.length) {
                        File[] fileArr2 = this.fileList;
                        Intrinsics.checkNotNull(fileArr2);
                        int i2 = this.fileIndex;
                        this.fileIndex = i2 + 1;
                        return fileArr2[i2];
                    }
                }
                if (!this.rootVisited) {
                    this.rootVisited = true;
                    return getRoot();
                }
                Function1 access$getOnLeave$p = FileTreeWalk.this.onLeave;
                if (access$getOnLeave$p != null) {
                    access$getOnLeave$p.invoke(getRoot());
                }
                return null;
            }
        }

        @Metadata(mo112d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bX\u000e¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, mo113d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "", "step", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
        /* renamed from: kotlin.io.FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState */
        /* compiled from: FileTreeWalk.kt */
        private final class TopDownDirectoryState extends DirectoryState {
            private int fileIndex;
            private File[] fileList;
            private boolean rootVisited;
            final /* synthetic */ FileTreeWalkIterator this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public TopDownDirectoryState(FileTreeWalkIterator this$02, File rootDir) {
                super(rootDir);
                Intrinsics.checkNotNullParameter(rootDir, "rootDir");
                this.this$0 = this$02;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:29:0x0083, code lost:
                if (r0.length == 0) goto L_0x0085;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.io.File step() {
                /*
                    r10 = this;
                    boolean r0 = r10.rootVisited
                    r1 = 0
                    if (r0 != 0) goto L_0x002c
                    kotlin.io.FileTreeWalk$FileTreeWalkIterator r0 = r10.this$0
                    kotlin.io.FileTreeWalk r0 = kotlin.p002io.FileTreeWalk.this
                    kotlin.jvm.functions.Function1 r0 = r0.onEnter
                    r2 = 0
                    r3 = 1
                    if (r0 == 0) goto L_0x0022
                    java.io.File r4 = r10.getRoot()
                    java.lang.Object r0 = r0.invoke(r4)
                    java.lang.Boolean r0 = (java.lang.Boolean) r0
                    boolean r0 = r0.booleanValue()
                    if (r0 != 0) goto L_0x0022
                    r2 = 1
                L_0x0022:
                    if (r2 == 0) goto L_0x0025
                    return r1
                L_0x0025:
                    r10.rootVisited = r3
                    java.io.File r0 = r10.getRoot()
                    return r0
                L_0x002c:
                    java.io.File[] r0 = r10.fileList
                    if (r0 == 0) goto L_0x004b
                    int r2 = r10.fileIndex
                    kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
                    int r0 = r0.length
                    if (r2 >= r0) goto L_0x0039
                    goto L_0x004b
                L_0x0039:
                    kotlin.io.FileTreeWalk$FileTreeWalkIterator r0 = r10.this$0
                    kotlin.io.FileTreeWalk r0 = kotlin.p002io.FileTreeWalk.this
                    kotlin.jvm.functions.Function1 r0 = r0.onLeave
                    if (r0 == 0) goto L_0x004a
                    java.io.File r2 = r10.getRoot()
                    r0.invoke(r2)
                L_0x004a:
                    return r1
                L_0x004b:
                    java.io.File[] r0 = r10.fileList
                    if (r0 != 0) goto L_0x0097
                    java.io.File r0 = r10.getRoot()
                    java.io.File[] r0 = r0.listFiles()
                    r10.fileList = r0
                    if (r0 != 0) goto L_0x007b
                    kotlin.io.FileTreeWalk$FileTreeWalkIterator r0 = r10.this$0
                    kotlin.io.FileTreeWalk r0 = kotlin.p002io.FileTreeWalk.this
                    kotlin.jvm.functions.Function2 r0 = r0.onFail
                    if (r0 == 0) goto L_0x007b
                    java.io.File r2 = r10.getRoot()
                    kotlin.io.AccessDeniedException r9 = new kotlin.io.AccessDeniedException
                    java.io.File r4 = r10.getRoot()
                    r5 = 0
                    java.lang.String r6 = "Cannot list files in a directory"
                    r7 = 2
                    r8 = 0
                    r3 = r9
                    r3.<init>(r4, r5, r6, r7, r8)
                    r0.invoke(r2, r9)
                L_0x007b:
                    java.io.File[] r0 = r10.fileList
                    if (r0 == 0) goto L_0x0085
                    kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
                    int r0 = r0.length
                    if (r0 != 0) goto L_0x0097
                L_0x0085:
                    kotlin.io.FileTreeWalk$FileTreeWalkIterator r0 = r10.this$0
                    kotlin.io.FileTreeWalk r0 = kotlin.p002io.FileTreeWalk.this
                    kotlin.jvm.functions.Function1 r0 = r0.onLeave
                    if (r0 == 0) goto L_0x0096
                    java.io.File r2 = r10.getRoot()
                    r0.invoke(r2)
                L_0x0096:
                    return r1
                L_0x0097:
                    java.io.File[] r0 = r10.fileList
                    kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
                    int r1 = r10.fileIndex
                    int r2 = r1 + 1
                    r10.fileIndex = r2
                    r0 = r0[r1]
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlin.p002io.FileTreeWalk.FileTreeWalkIterator.TopDownDirectoryState.step():java.io.File");
            }
        }

        @Metadata(mo112d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, mo113d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$SingleFileState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootFile", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "visited", "", "step", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
        /* renamed from: kotlin.io.FileTreeWalk$FileTreeWalkIterator$SingleFileState */
        /* compiled from: FileTreeWalk.kt */
        private final class SingleFileState extends WalkState {
            final /* synthetic */ FileTreeWalkIterator this$0;
            private boolean visited;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public SingleFileState(FileTreeWalkIterator this$02, File rootFile) {
                super(rootFile);
                Intrinsics.checkNotNullParameter(rootFile, "rootFile");
                this.this$0 = this$02;
                if (!rootFile.isFile()) {
                    throw new AssertionError("rootFile must be verified to be file beforehand.");
                }
            }

            public File step() {
                if (this.visited) {
                    return null;
                }
                this.visited = true;
                return getRoot();
            }
        }
    }

    public final FileTreeWalk onEnter(Function1<? super File, Boolean> function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return new FileTreeWalk(this.start, this.direction, function, this.onLeave, this.onFail, this.maxDepth);
    }

    public final FileTreeWalk onLeave(Function1<? super File, Unit> function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return new FileTreeWalk(this.start, this.direction, this.onEnter, function, this.onFail, this.maxDepth);
    }

    public final FileTreeWalk onFail(Function2<? super File, ? super IOException, Unit> function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, function, this.maxDepth);
    }

    public final FileTreeWalk maxDepth(int depth) {
        if (depth > 0) {
            return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, this.onFail, depth);
        }
        throw new IllegalArgumentException("depth must be positive, but was " + depth + '.');
    }
}
