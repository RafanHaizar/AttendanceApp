package p004pl.droidsonroids.relinker;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import p004pl.droidsonroids.relinker.ReLinker;
import p004pl.droidsonroids.relinker.elf.ElfParser;

/* renamed from: pl.droidsonroids.relinker.ReLinkerInstance */
public class ReLinkerInstance {
    private static final String LIB_DIR = "lib";
    protected boolean force;
    protected final ReLinker.LibraryInstaller libraryInstaller;
    protected final ReLinker.LibraryLoader libraryLoader;
    protected final Set<String> loadedLibraries;
    protected ReLinker.Logger logger;
    protected boolean recursive;

    protected ReLinkerInstance() {
        this(new SystemLibraryLoader(), new ApkLibraryInstaller());
    }

    protected ReLinkerInstance(ReLinker.LibraryLoader libraryLoader2, ReLinker.LibraryInstaller libraryInstaller2) {
        this.loadedLibraries = new HashSet();
        if (libraryLoader2 == null) {
            throw new IllegalArgumentException("Cannot pass null library loader");
        } else if (libraryInstaller2 != null) {
            this.libraryLoader = libraryLoader2;
            this.libraryInstaller = libraryInstaller2;
        } else {
            throw new IllegalArgumentException("Cannot pass null library installer");
        }
    }

    public ReLinkerInstance log(ReLinker.Logger logger2) {
        this.logger = logger2;
        return this;
    }

    public ReLinkerInstance force() {
        this.force = true;
        return this;
    }

    public ReLinkerInstance recursively() {
        this.recursive = true;
        return this;
    }

    public void loadLibrary(Context context, String library) {
        loadLibrary(context, library, (String) null, (ReLinker.LoadListener) null);
    }

    public void loadLibrary(Context context, String library, String version) {
        loadLibrary(context, library, version, (ReLinker.LoadListener) null);
    }

    public void loadLibrary(Context context, String library, ReLinker.LoadListener listener) {
        loadLibrary(context, library, (String) null, listener);
    }

    public void loadLibrary(Context context, String library, String version, ReLinker.LoadListener listener) {
        if (context == null) {
            throw new IllegalArgumentException("Given context is null");
        } else if (!TextUtils.isEmpty(library)) {
            log("Beginning load of %s...", library);
            if (listener == null) {
                loadLibraryInternal(context, library, version);
                return;
            }
            final Context context2 = context;
            final String str = library;
            final String str2 = version;
            final ReLinker.LoadListener loadListener = listener;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        ReLinkerInstance.this.loadLibraryInternal(context2, str, str2);
                        loadListener.success();
                    } catch (UnsatisfiedLinkError e) {
                        loadListener.failure(e);
                    } catch (MissingLibraryException e2) {
                        loadListener.failure(e2);
                    }
                }
            }).start();
        } else {
            throw new IllegalArgumentException("Given library is either null or empty");
        }
    }

    /* access modifiers changed from: private */
    public void loadLibraryInternal(Context context, String library, String version) {
        ElfParser parser;
        if (!this.loadedLibraries.contains(library) || this.force) {
            try {
                this.libraryLoader.loadLibrary(library);
                this.loadedLibraries.add(library);
                log("%s (%s) was loaded normally!", library, version);
            } catch (UnsatisfiedLinkError e) {
                log("Loading the library normally failed: %s", Log.getStackTraceString(e));
                log("%s (%s) was not loaded normally, re-linking...", library, version);
                File workaroundFile = getWorkaroundLibFile(context, library, version);
                if (!workaroundFile.exists() || this.force) {
                    if (this.force) {
                        log("Forcing a re-link of %s (%s)...", library, version);
                    }
                    cleanupOldLibFiles(context, library, version);
                    this.libraryInstaller.installLibrary(context, this.libraryLoader.supportedAbis(), this.libraryLoader.mapLibraryName(library), workaroundFile, this);
                }
                try {
                    if (this.recursive) {
                        parser = null;
                        ElfParser parser2 = new ElfParser(workaroundFile);
                        List<String> dependencies = parser2.parseNeededDependencies();
                        parser2.close();
                        for (String dependency : dependencies) {
                            loadLibrary(context, this.libraryLoader.unmapLibraryName(dependency));
                        }
                    }
                } catch (IOException e2) {
                }
                this.libraryLoader.loadPath(workaroundFile.getAbsolutePath());
                this.loadedLibraries.add(library);
                log("%s (%s) was re-linked!", library, version);
            } catch (Throwable th) {
                parser.close();
                throw th;
            }
        } else {
            log("%s already loaded previously!", library);
        }
    }

    /* access modifiers changed from: protected */
    public File getWorkaroundLibDir(Context context) {
        return context.getDir(LIB_DIR, 0);
    }

    /* access modifiers changed from: protected */
    public File getWorkaroundLibFile(Context context, String library, String version) {
        String libName = this.libraryLoader.mapLibraryName(library);
        if (TextUtils.isEmpty(version)) {
            return new File(getWorkaroundLibDir(context), libName);
        }
        return new File(getWorkaroundLibDir(context), libName + "." + version);
    }

    /* access modifiers changed from: protected */
    public void cleanupOldLibFiles(Context context, String library, String currentVersion) {
        File workaroundDir = getWorkaroundLibDir(context);
        File workaroundFile = getWorkaroundLibFile(context, library, currentVersion);
        final String mappedLibraryName = this.libraryLoader.mapLibraryName(library);
        File[] existingFiles = workaroundDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.startsWith(mappedLibraryName);
            }
        });
        if (existingFiles != null) {
            for (File file : existingFiles) {
                if (this.force || !file.getAbsolutePath().equals(workaroundFile.getAbsolutePath())) {
                    file.delete();
                }
            }
        }
    }

    public void log(String format, Object... args) {
        log(String.format(Locale.US, format, args));
    }

    public void log(String message) {
        ReLinker.Logger logger2 = this.logger;
        if (logger2 != null) {
            logger2.log(message);
        }
    }
}
