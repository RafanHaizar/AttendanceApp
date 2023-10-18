package p004pl.droidsonroids.relinker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import p004pl.droidsonroids.relinker.ReLinker;

/* renamed from: pl.droidsonroids.relinker.ApkLibraryInstaller */
public class ApkLibraryInstaller implements ReLinker.LibraryInstaller {
    private static final int COPY_BUFFER_SIZE = 4096;
    private static final int MAX_TRIES = 5;

    private String[] sourceDirectories(Context context) {
        ApplicationInfo appInfo = context.getApplicationInfo();
        if (Build.VERSION.SDK_INT < 21 || appInfo.splitSourceDirs == null || appInfo.splitSourceDirs.length == 0) {
            return new String[]{appInfo.sourceDir};
        }
        String[] apks = new String[(appInfo.splitSourceDirs.length + 1)];
        apks[0] = appInfo.sourceDir;
        System.arraycopy(appInfo.splitSourceDirs, 0, apks, 1, appInfo.splitSourceDirs.length);
        return apks;
    }

    /* renamed from: pl.droidsonroids.relinker.ApkLibraryInstaller$ZipFileInZipEntry */
    private static class ZipFileInZipEntry {
        public ZipEntry zipEntry;
        public ZipFile zipFile;

        public ZipFileInZipEntry(ZipFile zipFile2, ZipEntry zipEntry2) {
            this.zipFile = zipFile2;
            this.zipEntry = zipEntry2;
        }
    }

    private ZipFileInZipEntry findAPKWithLibrary(Context context, String[] abis, String mappedLibraryName, ReLinkerInstance instance) {
        int i;
        char c;
        String[] strArr = abis;
        String[] sourceDirectories = sourceDirectories(context);
        int length = sourceDirectories.length;
        char c2 = 0;
        int i2 = 0;
        while (i2 < length) {
            String sourceDir = sourceDirectories[i2];
            ZipFile zipFile = null;
            int tries = 0;
            while (true) {
                int tries2 = tries + 1;
                i = 5;
                c = 1;
                if (tries >= 5) {
                    break;
                }
                try {
                    zipFile = new ZipFile(new File(sourceDir), 1);
                    break;
                } catch (IOException e) {
                    tries = tries2;
                }
            }
            if (zipFile == null) {
                String str = mappedLibraryName;
                ReLinkerInstance reLinkerInstance = instance;
            } else {
                int tries3 = 0;
                while (true) {
                    int tries4 = tries3 + 1;
                    if (tries3 < i) {
                        int length2 = strArr.length;
                        int i3 = 0;
                        while (i3 < length2) {
                            String jniNameInApk = "lib" + File.separatorChar + strArr[i3] + File.separatorChar + mappedLibraryName;
                            Object[] objArr = new Object[2];
                            objArr[c2] = jniNameInApk;
                            objArr[c] = sourceDir;
                            instance.log("Looking for %s in APK %s...", objArr);
                            ZipEntry libraryEntry = zipFile.getEntry(jniNameInApk);
                            if (libraryEntry != null) {
                                return new ZipFileInZipEntry(zipFile, libraryEntry);
                            }
                            i3++;
                            c2 = 0;
                            c = 1;
                        }
                        String str2 = mappedLibraryName;
                        ReLinkerInstance reLinkerInstance2 = instance;
                        tries3 = tries4;
                        c2 = 0;
                        i = 5;
                        c = 1;
                    } else {
                        String str3 = mappedLibraryName;
                        ReLinkerInstance reLinkerInstance3 = instance;
                        try {
                            zipFile.close();
                            break;
                        } catch (IOException e2) {
                        }
                    }
                }
            }
            i2++;
            c2 = 0;
        }
        String str4 = mappedLibraryName;
        ReLinkerInstance reLinkerInstance4 = instance;
        return null;
    }

    private String[] getSupportedABIs(Context context, String mappedLibraryName) {
        Pattern pattern = Pattern.compile("lib" + File.separatorChar + "([^\\" + File.separatorChar + "]*)" + File.separatorChar + mappedLibraryName);
        Set<String> supportedABIs = new HashSet<>();
        for (String sourceDir : sourceDirectories(context)) {
            try {
                Enumeration<? extends ZipEntry> elements = new ZipFile(new File(sourceDir), 1).entries();
                while (elements.hasMoreElements()) {
                    Matcher match = pattern.matcher(((ZipEntry) elements.nextElement()).getName());
                    if (match.matches()) {
                        supportedABIs.add(match.group(1));
                    }
                }
            } catch (IOException e) {
            }
        }
        return (String[]) supportedABIs.toArray(new String[supportedABIs.size()]);
    }

    public void installLibrary(Context context, String[] abis, String mappedLibraryName, File destination, ReLinkerInstance instance) {
        String[] supportedABIs;
        Context context2 = context;
        String[] strArr = abis;
        String str = mappedLibraryName;
        File file = destination;
        ReLinkerInstance reLinkerInstance = instance;
        ZipFileInZipEntry found = null;
        try {
            found = findAPKWithLibrary(context2, strArr, str, reLinkerInstance);
            if (found != null) {
                int tries = 0;
                while (true) {
                    int tries2 = tries + 1;
                    if (tries < 5) {
                        reLinkerInstance.log("Found %s! Extracting...", str);
                        try {
                            if (destination.exists() || destination.createNewFile()) {
                                InputStream inputStream = found.zipFile.getInputStream(found.zipEntry);
                                FileOutputStream fileOut = new FileOutputStream(file);
                                long written = copy(inputStream, fileOut);
                                fileOut.getFD().sync();
                                if (written != destination.length()) {
                                    closeSilently(inputStream);
                                    closeSilently(fileOut);
                                    tries = tries2;
                                } else {
                                    closeSilently(inputStream);
                                    closeSilently(fileOut);
                                    file.setReadable(true, false);
                                    file.setExecutable(true, false);
                                    file.setWritable(true);
                                    if (found != null) {
                                        try {
                                            if (found.zipFile != null) {
                                                found.zipFile.close();
                                                return;
                                            }
                                            return;
                                        } catch (IOException e) {
                                            return;
                                        }
                                    } else {
                                        return;
                                    }
                                }
                            } else {
                                tries = tries2;
                            }
                        } catch (IOException e2) {
                        }
                    } else {
                        reLinkerInstance.log("FATAL! Couldn't extract the library from the APK!");
                        if (found != null) {
                            try {
                                if (found.zipFile != null) {
                                    found.zipFile.close();
                                    return;
                                }
                                return;
                            } catch (IOException e3) {
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                }
            } else {
                try {
                    supportedABIs = getSupportedABIs(context2, str);
                } catch (Exception e4) {
                    supportedABIs = new String[]{e4.toString()};
                }
                throw new MissingLibraryException(str, strArr, supportedABIs);
            }
        } catch (FileNotFoundException e5) {
            closeSilently((Closeable) null);
            closeSilently((Closeable) null);
        } catch (IOException e6) {
            closeSilently((Closeable) null);
            closeSilently((Closeable) null);
        } catch (Throwable th) {
            Throwable th2 = th;
            if (found != null) {
                try {
                    if (found.zipFile != null) {
                        found.zipFile.close();
                    }
                } catch (IOException e7) {
                }
            }
            throw th2;
        }
    }

    private long copy(InputStream in, OutputStream out) throws IOException {
        long copied = 0;
        byte[] buf = new byte[4096];
        while (true) {
            int read = in.read(buf);
            if (read == -1) {
                out.flush();
                return copied;
            }
            out.write(buf, 0, read);
            copied += (long) read;
        }
    }

    private void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
