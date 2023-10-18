package com.itextpdf.styledxmlparser.jsoup;

public final class SerializationException extends RuntimeException {
    public SerializationException() {
    }

    public SerializationException(String message) {
        super(message);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SerializationException(Throwable cause) {
        super(cause == null ? "Exception with null cause" : cause.getMessage(), cause);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
