package com.itextpdf.styledxmlparser.jsoup;

import java.io.IOException;

public class HttpStatusException extends IOException {
    private int statusCode;
    private String url;

    public HttpStatusException(String message, int statusCode2, String url2) {
        super(message);
        this.statusCode = statusCode2;
        this.url = url2;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getUrl() {
        return this.url;
    }

    public String toString() {
        return super.toString() + ". Status=" + this.statusCode + ", URL=" + this.url;
    }
}
