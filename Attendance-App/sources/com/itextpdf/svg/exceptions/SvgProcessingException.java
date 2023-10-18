package com.itextpdf.svg.exceptions;

import com.itextpdf.kernel.PdfException;

public class SvgProcessingException extends PdfException {
    public SvgProcessingException(String message) {
        super(message);
    }

    public SvgProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
