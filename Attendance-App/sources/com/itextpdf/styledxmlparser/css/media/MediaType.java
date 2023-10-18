package com.itextpdf.styledxmlparser.css.media;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.HashSet;
import java.util.Set;

public final class MediaType {
    public static final String ALL = registerMediaType("all");
    public static final String AURAL = registerMediaType("aural");
    public static final String BRAILLE = registerMediaType("braille");
    public static final String EMBOSSED = registerMediaType("embossed");
    public static final String HANDHELD = registerMediaType("handheld");
    public static final String PRINT = registerMediaType(XfdfConstants.PRINT);
    public static final String PROJECTION = registerMediaType(XfdfConstants.PROJECTION);
    public static final String SCREEN = registerMediaType(CommonCssConstants.SCREEN);
    public static final String SPEECH = registerMediaType("speech");
    public static final String TTY = registerMediaType("tty");

    /* renamed from: TV */
    public static final String f1620TV = registerMediaType("tv");
    private static final Set<String> registeredMediaTypes = new HashSet();

    private MediaType() {
    }

    public static boolean isValidMediaType(String mediaType) {
        return registeredMediaTypes.contains(mediaType);
    }

    private static String registerMediaType(String mediaType) {
        registeredMediaTypes.add(mediaType);
        return mediaType;
    }
}
