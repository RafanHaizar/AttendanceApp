package androidx.core.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StrictMode;
import android.util.Log;
import androidx.core.provider.FontsContractCompat;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TypefaceCompatUtil {
    private static final String CACHE_FILE_PREFIX = ".font";
    private static final String TAG = "TypefaceCompatUtil";

    private TypefaceCompatUtil() {
    }

    public static File getTempFile(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        String prefix = CACHE_FILE_PREFIX + Process.myPid() + "-" + Process.myTid() + "-";
        int i = 0;
        while (i < 100) {
            File file = new File(cacheDir, prefix + i);
            try {
                if (file.createNewFile()) {
                    return file;
                }
                i++;
            } catch (IOException e) {
            }
        }
        return null;
    }

    private static ByteBuffer mmap(File file) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            fis.close();
            return map;
        } catch (IOException e) {
            return null;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public static ByteBuffer mmap(Context context, CancellationSignal cancellationSignal, Uri uri) {
        FileInputStream fis;
        try {
            ParcelFileDescriptor pfd = Api19Impl.openFileDescriptor(context.getContentResolver(), uri, "r", cancellationSignal);
            if (pfd == null) {
                if (pfd != null) {
                    pfd.close();
                }
                return null;
            }
            try {
                fis = new FileInputStream(pfd.getFileDescriptor());
                FileChannel channel = fis.getChannel();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
                fis.close();
                if (pfd != null) {
                    pfd.close();
                }
                return map;
            } catch (Throwable th) {
                if (pfd != null) {
                    pfd.close();
                }
                throw th;
            }
            throw th;
        } catch (IOException e) {
            return null;
        } catch (Throwable th2) {
            th.addSuppressed(th2);
        }
    }

    public static ByteBuffer copyToDirectBuffer(Context context, Resources res, int id) {
        File tmpFile = getTempFile(context);
        if (tmpFile == null) {
            return null;
        }
        try {
            if (!copyToFile(tmpFile, res, id)) {
                return null;
            }
            ByteBuffer mmap = mmap(tmpFile);
            tmpFile.delete();
            return mmap;
        } finally {
            tmpFile.delete();
        }
    }

    public static boolean copyToFile(File file, InputStream is) {
        StrictMode.ThreadPolicy old = StrictMode.allowThreadDiskWrites();
        try {
            FileOutputStream os = new FileOutputStream(file, false);
            byte[] buffer = new byte[1024];
            while (true) {
                int read = is.read(buffer);
                int readLen = read;
                if (read != -1) {
                    os.write(buffer, 0, readLen);
                } else {
                    closeQuietly(os);
                    StrictMode.setThreadPolicy(old);
                    return true;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error copying resource contents to temp file: " + e.getMessage());
            closeQuietly((Closeable) null);
            StrictMode.setThreadPolicy(old);
            return false;
        } catch (Throwable th) {
            closeQuietly((Closeable) null);
            StrictMode.setThreadPolicy(old);
            throw th;
        }
    }

    public static boolean copyToFile(File file, Resources res, int id) {
        InputStream is = null;
        try {
            is = res.openRawResource(id);
            return copyToFile(file, is);
        } finally {
            closeQuietly(is);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }

    public static Map<Uri, ByteBuffer> readFontInfoIntoByteBuffer(Context context, FontsContractCompat.FontInfo[] fonts, CancellationSignal cancellationSignal) {
        HashMap<Uri, ByteBuffer> out = new HashMap<>();
        for (FontsContractCompat.FontInfo font : fonts) {
            if (font.getResultCode() == 0) {
                Uri uri = font.getUri();
                if (!out.containsKey(uri)) {
                    out.put(uri, mmap(context, cancellationSignal, uri));
                }
            }
        }
        return Collections.unmodifiableMap(out);
    }

    static class Api19Impl {
        private Api19Impl() {
        }

        static ParcelFileDescriptor openFileDescriptor(ContentResolver contentResolver, Uri uri, String mode, CancellationSignal cancellationSignal) throws FileNotFoundException {
            return contentResolver.openFileDescriptor(uri, mode, cancellationSignal);
        }
    }
}
