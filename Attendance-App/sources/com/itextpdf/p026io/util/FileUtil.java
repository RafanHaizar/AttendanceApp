package com.itextpdf.p026io.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.slf4j.LoggerFactory;

/* renamed from: com.itextpdf.io.util.FileUtil */
public final class FileUtil {
    private FileUtil() {
    }

    public static String getFontsDir() {
        try {
            String winDir = System.getenv("windir");
            return winDir + System.getProperty("file.separator") + "fonts";
        } catch (SecurityException e) {
            LoggerFactory.getLogger((Class<?>) FileUtil.class).warn("Can't access System.getenv(\"windir\") to load fonts. Please, add RuntimePermission for getenv.windir.");
            return null;
        }
    }

    public static boolean fileExists(String path) {
        if (path == null) {
            return false;
        }
        File f = new File(path);
        if (!f.exists() || !f.isFile()) {
            return false;
        }
        return true;
    }

    public static boolean directoryExists(String path) {
        if (path == null) {
            return false;
        }
        File f = new File(path);
        if (!f.exists() || !f.isDirectory()) {
            return false;
        }
        return true;
    }

    public static String[] listFilesInDirectory(String path, boolean recursive) {
        File[] files;
        if (path != null) {
            File root = new File(path);
            if (root.exists() && root.isDirectory() && (files = root.listFiles()) != null) {
                Arrays.sort(files, new CaseSensitiveFileComparator());
                List<String> list = new ArrayList<>();
                for (File file : files) {
                    if (!file.isDirectory() || !recursive) {
                        list.add(file.getAbsolutePath());
                    } else {
                        listAllFiles(file.getAbsolutePath(), list);
                    }
                }
                return (String[]) list.toArray(new String[list.size()]);
            }
        }
        return null;
    }

    public static File[] listFilesInDirectoryByFilter(String outPath, FileFilter fileFilter) {
        File[] result = null;
        if (outPath != null && !outPath.isEmpty()) {
            result = new File(outPath).listFiles(fileFilter);
        }
        if (result != null) {
            Arrays.sort(result, new CaseSensitiveFileComparator());
        }
        return result;
    }

    private static void listAllFiles(String dir, List<String> list) {
        File[] files = new File(dir).listFiles();
        if (files != null) {
            Arrays.sort(files, new CaseSensitiveFileComparator());
            for (File file : files) {
                if (file.isDirectory()) {
                    listAllFiles(file.getAbsolutePath(), list);
                } else {
                    list.add(file.getAbsolutePath());
                }
            }
        }
    }

    public static PrintWriter createPrintWriter(OutputStream output, String encoding) throws UnsupportedEncodingException {
        return new PrintWriter(new OutputStreamWriter(output, encoding));
    }

    public static OutputStream getBufferedOutputStream(String filename) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(filename));
    }

    public static OutputStream wrapWithBufferedOutputStream(OutputStream outputStream) {
        if ((outputStream instanceof ByteArrayOutputStream) || (outputStream instanceof BufferedOutputStream)) {
            return outputStream;
        }
        return new BufferedOutputStream(outputStream);
    }

    public static File createTempFile(String path) throws IOException {
        File tempFile = new File(path);
        if (tempFile.isDirectory()) {
            return File.createTempFile("pdf", (String) null, tempFile);
        }
        return tempFile;
    }

    public static FileOutputStream getFileOutputStream(File tempFile) throws FileNotFoundException {
        return new FileOutputStream(tempFile);
    }

    public static InputStream getInputStreamForFile(String path) throws IOException {
        return Files.newInputStream(Paths.get(path, new String[0]), new OpenOption[0]);
    }

    public static RandomAccessFile getRandomAccessFile(File tempFile) throws FileNotFoundException {
        return new RandomAccessFile(tempFile, "rw");
    }

    public static void createDirectories(String outPath) {
        new File(outPath).mkdirs();
    }

    @Deprecated
    public static String getParentDirectory(String file) {
        return new File(file).getParent();
    }

    public static String getParentDirectory(File file) throws MalformedURLException {
        return file != null ? Paths.get(file.getParent(), new String[0]).toUri().toURL().toExternalForm() : "";
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }

    public static String parentDirectory(URL url) throws URISyntaxException {
        return url.toURI().resolve(".").toString();
    }

    /* renamed from: com.itextpdf.io.util.FileUtil$CaseSensitiveFileComparator */
    private static class CaseSensitiveFileComparator implements Comparator<File> {
        private CaseSensitiveFileComparator() {
        }

        public int compare(File f1, File f2) {
            return f1.getPath().compareTo(f2.getPath());
        }
    }
}
