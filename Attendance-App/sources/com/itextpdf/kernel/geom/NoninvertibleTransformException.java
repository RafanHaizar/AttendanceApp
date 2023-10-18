package com.itextpdf.kernel.geom;

public class NoninvertibleTransformException extends Exception {
    public static final String DETERMINANT_IS_ZERO_CANNOT_INVERT_TRANSFORMATION = "Determinant is zero. Cannot invert transformation.";
    private static final long serialVersionUID = 6137225240503990466L;

    public NoninvertibleTransformException(String message) {
        super(message);
    }
}
