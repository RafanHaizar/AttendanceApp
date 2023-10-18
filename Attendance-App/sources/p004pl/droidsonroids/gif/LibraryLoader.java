package p004pl.droidsonroids.gif;

import android.content.Context;
import p004pl.droidsonroids.relinker.ReLinker;

/* renamed from: pl.droidsonroids.gif.LibraryLoader */
public class LibraryLoader {
    private static final String BASE_LIBRARY_NAME = "pl_droidsonroids_gif";
    private static Context sAppContext;

    private LibraryLoader() {
    }

    public static void initialize(Context context) {
        sAppContext = context.getApplicationContext();
    }

    private static Context getContext() {
        if (sAppContext == null) {
            try {
                sAppContext = (Context) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]).invoke((Object) null, new Object[0]);
            } catch (Exception e) {
                throw new IllegalStateException("LibraryLoader not initialized. Call LibraryLoader.initialize() before using library classes.", e);
            }
        }
        return sAppContext;
    }

    static void loadLibrary() {
        try {
            System.loadLibrary(BASE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError e) {
            ReLinker.loadLibrary(getContext(), BASE_LIBRARY_NAME);
        }
    }
}
