package com.itextpdf.styledxmlparser.exceptions;

public class StyledXMLParserException extends RuntimeException {
    public static final String FontProviderContainsZeroFonts = "Font Provider contains zero fonts. At least one font shall be present";
    public static final String INVALID_GRADIENT_COLOR_STOP_VALUE = "Invalid color stop value: {0}";
    public static final String INVALID_GRADIENT_FUNCTION_ARGUMENTS_LIST = "Invalid gradient function arguments list: {0}";
    public static final String INVALID_GRADIENT_TO_SIDE_OR_CORNER_STRING = "Invalid direction string: {0}";
    public static final String NAN = "The passed value (@{0}) is not a number";
    public static final String UnsupportedEncodingException = "Unsupported encoding exception.";
    private static final long serialVersionUID = -136587601709625428L;

    public StyledXMLParserException(String message) {
        super(message);
    }
}
