package com.itextpdf.styledxmlparser.jsoup;

import java.io.IOException;

public class UnsupportedMimeTypeException extends IOException {
    private String mimeType;
    private String url;

    public UnsupportedMimeTypeException(String message, String mimeType2, String url2) {
        super(message);
        this.mimeType = mimeType2;
        this.url = url2;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public String getUrl() {
        return this.url;
    }

    public String toString() {
        return super.toString() + ". Mimetype=" + this.mimeType + ", URL=" + this.url;
    }
}
