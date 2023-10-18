package androidx.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

final class MultiDexExtractor implements Closeable {
    private static final int BUFFER_SIZE = 16384;
    private static final String DEX_PREFIX = "classes";
    static final String DEX_SUFFIX = ".dex";
    private static final String EXTRACTED_NAME_EXT = ".classes";
    static final String EXTRACTED_SUFFIX = ".zip";
    private static final String KEY_CRC = "crc";
    private static final String KEY_DEX_CRC = "dex.crc.";
    private static final String KEY_DEX_NUMBER = "dex.number";
    private static final String KEY_DEX_TIME = "dex.time.";
    private static final String KEY_TIME_STAMP = "timestamp";
    private static final String LOCK_FILENAME = "MultiDex.lock";
    private static final int MAX_EXTRACT_ATTEMPTS = 3;
    private static final long NO_VALUE = -1;
    private static final String PREFS_FILE = "multidex.version";
    private static final String TAG = "MultiDex";
    private final FileLock cacheLock;
    private final File dexDir;
    private final FileChannel lockChannel;
    private final RandomAccessFile lockRaf;
    private final File sourceApk;
    private final long sourceCrc;

    private static class ExtractedDex extends File {
        public long crc = -1;

        public ExtractedDex(File dexDir, String fileName) {
            super(dexDir, fileName);
        }
    }

    MultiDexExtractor(File sourceApk2, File dexDir2) throws IOException {
        Log.i(TAG, "MultiDexExtractor(" + sourceApk2.getPath() + ", " + dexDir2.getPath() + ")");
        this.sourceApk = sourceApk2;
        this.dexDir = dexDir2;
        this.sourceCrc = getZipCrc(sourceApk2);
        File lockFile = new File(dexDir2, LOCK_FILENAME);
        RandomAccessFile randomAccessFile = new RandomAccessFile(lockFile, "rw");
        this.lockRaf = randomAccessFile;
        try {
            FileChannel channel = randomAccessFile.getChannel();
            this.lockChannel = channel;
            try {
                Log.i(TAG, "Blocking on lock " + lockFile.getPath());
                this.cacheLock = channel.lock();
                Log.i(TAG, lockFile.getPath() + " locked");
                return;
            } catch (IOException e) {
                e = e;
            } catch (RuntimeException e2) {
                e = e2;
            } catch (Error e3) {
                e = e3;
            }
            closeQuietly(this.lockChannel);
            throw e;
        } catch (IOException | Error | RuntimeException e4) {
            closeQuietly(this.lockRaf);
            throw e4;
        }
    }

    /* access modifiers changed from: package-private */
    public List<? extends File> load(Context context, String prefsKeyPrefix, boolean forceReload) throws IOException {
        List<ExtractedDex> files;
        Log.i(TAG, "MultiDexExtractor.load(" + this.sourceApk.getPath() + ", " + forceReload + ", " + prefsKeyPrefix + ")");
        if (this.cacheLock.isValid()) {
            if (forceReload || isModified(context, this.sourceApk, this.sourceCrc, prefsKeyPrefix)) {
                if (forceReload) {
                    Log.i(TAG, "Forced extraction must be performed.");
                } else {
                    Log.i(TAG, "Detected that extraction must be performed.");
                }
                files = performExtractions();
                putStoredApkInfo(context, prefsKeyPrefix, getTimeStamp(this.sourceApk), this.sourceCrc, files);
            } else {
                try {
                    files = loadExistingExtractions(context, prefsKeyPrefix);
                } catch (IOException ioe) {
                    Log.w(TAG, "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", ioe);
                    List<ExtractedDex> files2 = performExtractions();
                    putStoredApkInfo(context, prefsKeyPrefix, getTimeStamp(this.sourceApk), this.sourceCrc, files2);
                    files = files2;
                }
            }
            Log.i(TAG, "load found " + files.size() + " secondary dex files");
            return files;
        }
        throw new IllegalStateException("MultiDexExtractor was closed");
    }

    public void close() throws IOException {
        this.cacheLock.release();
        this.lockChannel.close();
        this.lockRaf.close();
    }

    private List<ExtractedDex> loadExistingExtractions(Context context, String prefsKeyPrefix) throws IOException {
        String str = prefsKeyPrefix;
        Log.i(TAG, "loading existing secondary dex files");
        String extractedFilePrefix = this.sourceApk.getName() + EXTRACTED_NAME_EXT;
        SharedPreferences multiDexPreferences = getMultiDexPreferences(context);
        int totalDexNumber = multiDexPreferences.getInt(str + KEY_DEX_NUMBER, 1);
        List<ExtractedDex> files = new ArrayList<>(totalDexNumber - 1);
        int secondaryNumber = 2;
        while (secondaryNumber <= totalDexNumber) {
            ExtractedDex extractedFile = new ExtractedDex(this.dexDir, extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX);
            if (extractedFile.isFile()) {
                extractedFile.crc = getZipCrc(extractedFile);
                long expectedCrc = multiDexPreferences.getLong(str + KEY_DEX_CRC + secondaryNumber, -1);
                long expectedModTime = multiDexPreferences.getLong(str + KEY_DEX_TIME + secondaryNumber, -1);
                long lastModified = extractedFile.lastModified();
                if (expectedModTime == lastModified) {
                    String extractedFilePrefix2 = extractedFilePrefix;
                    SharedPreferences multiDexPreferences2 = multiDexPreferences;
                    if (expectedCrc == extractedFile.crc) {
                        files.add(extractedFile);
                        secondaryNumber++;
                        extractedFilePrefix = extractedFilePrefix2;
                        multiDexPreferences = multiDexPreferences2;
                    }
                } else {
                    SharedPreferences sharedPreferences = multiDexPreferences;
                }
                int i = totalDexNumber;
                throw new IOException("Invalid extracted dex: " + extractedFile + " (key \"" + str + "\"), expected modification time: " + expectedModTime + ", modification time: " + lastModified + ", expected crc: " + expectedCrc + ", file crc: " + extractedFile.crc);
            }
            SharedPreferences sharedPreferences2 = multiDexPreferences;
            throw new IOException("Missing extracted secondary dex file '" + extractedFile.getPath() + "'");
        }
        return files;
    }

    private static boolean isModified(Context context, File archive, long currentCrc, String prefsKeyPrefix) {
        SharedPreferences prefs = getMultiDexPreferences(context);
        return (prefs.getLong(new StringBuilder().append(prefsKeyPrefix).append(KEY_TIME_STAMP).toString(), -1) == getTimeStamp(archive) && prefs.getLong(new StringBuilder().append(prefsKeyPrefix).append(KEY_CRC).toString(), -1) == currentCrc) ? false : true;
    }

    private static long getTimeStamp(File archive) {
        long timeStamp = archive.lastModified();
        if (timeStamp == -1) {
            return timeStamp - 1;
        }
        return timeStamp;
    }

    private static long getZipCrc(File archive) throws IOException {
        long computedValue = ZipUtil.getZipCrc(archive);
        if (computedValue == -1) {
            return computedValue - 1;
        }
        return computedValue;
    }

    private List<ExtractedDex> performExtractions() throws IOException {
        ExtractedDex extractedFile;
        boolean isExtractionSuccessful;
        MultiDexExtractor multiDexExtractor = this;
        String extractedFilePrefix = multiDexExtractor.sourceApk.getName() + EXTRACTED_NAME_EXT;
        clearDexDir();
        List<ExtractedDex> files = new ArrayList<>();
        ZipFile apk = new ZipFile(multiDexExtractor.sourceApk);
        try {
            ZipEntry dexFile = apk.getEntry(DEX_PREFIX + 2 + DEX_SUFFIX);
            int secondaryNumber = 2;
            while (dexFile != null) {
                String fileName = extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX;
                extractedFile = new ExtractedDex(multiDexExtractor.dexDir, fileName);
                files.add(extractedFile);
                Log.i(TAG, "Extraction is needed for file " + extractedFile);
                int numAttempts = 0;
                isExtractionSuccessful = false;
                while (numAttempts < 3 && !isExtractionSuccessful) {
                    int numAttempts2 = numAttempts + 1;
                    extract(apk, dexFile, extractedFile, extractedFilePrefix);
                    extractedFile.crc = getZipCrc(extractedFile);
                    isExtractionSuccessful = true;
                    ZipEntry dexFile2 = dexFile;
                    String fileName2 = fileName;
                    Log.i(TAG, "Extraction " + (isExtractionSuccessful ? "succeeded" : "failed") + " '" + extractedFile.getAbsolutePath() + "': length " + extractedFile.length() + " - crc: " + extractedFile.crc);
                    if (!isExtractionSuccessful) {
                        extractedFile.delete();
                        if (extractedFile.exists()) {
                            Log.w(TAG, "Failed to delete corrupted secondary dex '" + extractedFile.getPath() + "'");
                        }
                    }
                    dexFile = dexFile2;
                    numAttempts = numAttempts2;
                    fileName = fileName2;
                }
                String str = fileName;
                if (isExtractionSuccessful) {
                    secondaryNumber++;
                    dexFile = apk.getEntry(DEX_PREFIX + secondaryNumber + DEX_SUFFIX);
                    multiDexExtractor = this;
                } else {
                    throw new IOException("Could not create zip file " + extractedFile.getAbsolutePath() + " for secondary dex (" + secondaryNumber + ")");
                }
            }
            try {
                apk.close();
            } catch (IOException e) {
                Log.w(TAG, "Failed to close resource", e);
            }
            return files;
        } catch (IOException e2) {
            Log.w(TAG, "Failed to read crc from " + extractedFile.getAbsolutePath(), e2);
            isExtractionSuccessful = false;
        } catch (Throwable th) {
            Throwable th2 = th;
            try {
                apk.close();
            } catch (IOException e3) {
                Log.w(TAG, "Failed to close resource", e3);
            }
            throw th2;
        }
    }

    private static void putStoredApkInfo(Context context, String keyPrefix, long timeStamp, long crc, List<ExtractedDex> extractedDexes) {
        SharedPreferences.Editor edit = getMultiDexPreferences(context).edit();
        edit.putLong(keyPrefix + KEY_TIME_STAMP, timeStamp);
        edit.putLong(keyPrefix + KEY_CRC, crc);
        edit.putInt(keyPrefix + KEY_DEX_NUMBER, extractedDexes.size() + 1);
        int extractedDexId = 2;
        for (ExtractedDex dex : extractedDexes) {
            edit.putLong(keyPrefix + KEY_DEX_CRC + extractedDexId, dex.crc);
            edit.putLong(keyPrefix + KEY_DEX_TIME + extractedDexId, dex.lastModified());
            extractedDexId++;
        }
        edit.commit();
    }

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, 4);
    }

    private void clearDexDir() {
        File[] files = this.dexDir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return !pathname.getName().equals(MultiDexExtractor.LOCK_FILENAME);
            }
        });
        if (files == null) {
            Log.w(TAG, "Failed to list secondary dex dir content (" + this.dexDir.getPath() + ").");
            return;
        }
        for (File oldFile : files) {
            Log.i(TAG, "Trying to delete old file " + oldFile.getPath() + " of size " + oldFile.length());
            if (!oldFile.delete()) {
                Log.w(TAG, "Failed to delete old file " + oldFile.getPath());
            } else {
                Log.i(TAG, "Deleted old file " + oldFile.getPath());
            }
        }
    }

    private static void extract(ZipFile apk, ZipEntry dexFile, File extractTo, String extractedFilePrefix) throws IOException, FileNotFoundException {
        ZipOutputStream out;
        InputStream in = apk.getInputStream(dexFile);
        File tmp = File.createTempFile("tmp-" + extractedFilePrefix, EXTRACTED_SUFFIX, extractTo.getParentFile());
        Log.i(TAG, "Extracting " + tmp.getPath());
        try {
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(tmp)));
            ZipEntry classesDex = new ZipEntry("classes.dex");
            classesDex.setTime(dexFile.getTime());
            out.putNextEntry(classesDex);
            byte[] buffer = new byte[16384];
            for (int length = in.read(buffer); length != -1; length = in.read(buffer)) {
                out.write(buffer, 0, length);
            }
            out.closeEntry();
            out.close();
            if (tmp.setReadOnly()) {
                Log.i(TAG, "Renaming to " + extractTo.getPath());
                if (tmp.renameTo(extractTo)) {
                    closeQuietly(in);
                    tmp.delete();
                    return;
                }
                throw new IOException("Failed to rename \"" + tmp.getAbsolutePath() + "\" to \"" + extractTo.getAbsolutePath() + "\"");
            }
            throw new IOException("Failed to mark readonly \"" + tmp.getAbsolutePath() + "\" (tmp of \"" + extractTo.getAbsolutePath() + "\")");
        } catch (Throwable th) {
            closeQuietly(in);
            tmp.delete();
            throw th;
        }
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.w(TAG, "Failed to close resource", e);
        }
    }
}
