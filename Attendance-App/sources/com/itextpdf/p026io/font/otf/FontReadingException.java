package com.itextpdf.p026io.font.otf;

import java.io.IOException;

/* renamed from: com.itextpdf.io.font.otf.FontReadingException */
public class FontReadingException extends IOException {
    private static final long serialVersionUID = -7058811479423740467L;

    public FontReadingException(String message) {
        super(message);
    }

    public FontReadingException(String message, Exception e) {
        super(message, e);
    }
}
