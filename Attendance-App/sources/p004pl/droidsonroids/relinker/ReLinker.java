package p004pl.droidsonroids.relinker;

import android.content.Context;
import java.io.File;

/* renamed from: pl.droidsonroids.relinker.ReLinker */
public class ReLinker {

    /* renamed from: pl.droidsonroids.relinker.ReLinker$LibraryInstaller */
    public interface LibraryInstaller {
        void installLibrary(Context context, String[] strArr, String str, File file, ReLinkerInstance reLinkerInstance);
    }

    /* renamed from: pl.droidsonroids.relinker.ReLinker$LibraryLoader */
    public interface LibraryLoader {
        void loadLibrary(String str);

        void loadPath(String str);

        String mapLibraryName(String str);

        String[] supportedAbis();

        String unmapLibraryName(String str);
    }

    /* renamed from: pl.droidsonroids.relinker.ReLinker$LoadListener */
    public interface LoadListener {
        void failure(Throwable th);

        void success();
    }

    /* renamed from: pl.droidsonroids.relinker.ReLinker$Logger */
    public interface Logger {
        void log(String str);
    }

    public static void loadLibrary(Context context, String library) {
        loadLibrary(context, library, (String) null, (LoadListener) null);
    }

    public static void loadLibrary(Context context, String library, String version) {
        loadLibrary(context, library, version, (LoadListener) null);
    }

    public static void loadLibrary(Context context, String library, LoadListener listener) {
        loadLibrary(context, library, (String) null, listener);
    }

    public static void loadLibrary(Context context, String library, String version, LoadListener listener) {
        new ReLinkerInstance().loadLibrary(context, library, version, listener);
    }

    public static ReLinkerInstance force() {
        return new ReLinkerInstance().force();
    }

    public static ReLinkerInstance log(Logger logger) {
        return new ReLinkerInstance().log(logger);
    }

    public static ReLinkerInstance recursively() {
        return new ReLinkerInstance().recursively();
    }

    private ReLinker() {
    }
}
