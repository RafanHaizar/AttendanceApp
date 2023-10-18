package com.itextpdf.p026io.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/* renamed from: com.itextpdf.io.util.UrlUtil */
public final class UrlUtil {
    private UrlUtil() {
    }

    public static URL toURL(String filename) throws MalformedURLException {
        try {
            return new URL(filename);
        } catch (MalformedURLException e) {
            return new File(filename).toURI().toURL();
        }
    }

    public static URI toNormalizedURI(String filename) {
        return toNormalizedURI(new File(filename));
    }

    public static URI toNormalizedURI(File file) {
        return file.toURI().normalize();
    }

    public static InputStream openStream(URL url) throws IOException {
        return url.openStream();
    }

    public static URL getFinalURL(URL initialUrl) throws IOException {
        URL finalUrl = null;
        URL nextUrl = initialUrl;
        while (nextUrl != null) {
            finalUrl = nextUrl;
            URLConnection connection = finalUrl.openConnection();
            String location = connection.getHeaderField("location");
            connection.getInputStream().close();
            nextUrl = location != null ? new URL(location) : null;
        }
        return finalUrl;
    }

    public static String getFileUriString(String filename) throws MalformedURLException {
        return new File(filename).toURI().toURL().toExternalForm();
    }

    public static String getNormalizedFileUriString(String filename) {
        return "file://" + toNormalizedURI(filename).getPath();
    }
}
