package com.itextpdf.svg.exceptions;

public final class SvgLogMessageConstant {
    public static final String ARC_TO_EXPECTS_FOLLOWING_PARAMETERS_GOT_0 = "(rx ry rot largearc sweep x y)+ parameters are expected for elliptical arcs. Got: {0}";
    public static final String ATTRIBUTES_NULL = "The attributes of this element are null.";
    public static final String COORDINATE_VALUE_ABSENT = "The coordinate value is empty or null.";
    public static final String COULDNOTINSTANTIATE = "Could not instantiate Renderer for tag {0}";
    public static final String DRAW_NO_DRAW = "Can't draw current SvgNodeRenderer.";
    @Deprecated
    public static final String ERROR_CLOSING_CSS_STREAM = "An error occured when trying to close the InputStream of the default CSS.";
    public static final String ERROR_INITIALIZING_DEFAULT_CSS = "Error loading the default CSS. Initializing an empty style sheet.";
    public static final String FAILED_TO_PARSE_INPUTSTREAM = "Failed to parse InputStream.";
    @Deprecated
    public static final String FLOAT_PARSING_NAN = "The passed value is not a number.";
    public static final String FONT_NOT_FOUND = "The font wasn't found.";
    @Deprecated
    public static final String FONT_PROVIDER_CONTAINS_ZERO_FONTS = "Font Provider contains zero fonts. At least one font shall be present";
    public static final String GRADIENT_INVALID_GRADIENT_UNITS_LOG = "Could not recognize gradient units value {0}";
    public static final String GRADIENT_INVALID_SPREAD_METHOD_LOG = "Could not recognize gradient spread method value {0}";
    public static final String INODEROOTISNULL = "Input root value is null";
    public static final String INVALID_CLOSEPATH_OPERATOR_USE = "The close path operator (Z) may not be used before a move to operation (M)";
    public static final String INVALID_PATH_D_ATTRIBUTE_OPERATORS = "Invalid operators found in path data attribute: {0}";
    public static final String INVALID_TRANSFORM_DECLARATION = "Transformation declaration is not formed correctly.";
    public static final String LOOP = "Loop detected";
    public static final String MARKER_HEIGHT_IS_NEGATIVE_VALUE = "markerHeight has negative value. Marker will not be rendered.";
    public static final String MARKER_HEIGHT_IS_ZERO_VALUE = "markerHeight has zero value. Marker will not be rendered.";
    public static final String MARKER_WIDTH_IS_NEGATIVE_VALUE = "markerWidth has negative value. Marker will not be rendered.";
    public static final String MARKER_WIDTH_IS_ZERO_VALUE = "markerWidth has zero value. Marker will not be rendered.";
    public static final String MISSING_HEIGHT = "Top Svg tag has no defined height attribute and viewbox height is not present, so browser default of 150px is used";
    public static final String MISSING_WIDTH = "Top Svg tag has no defined width attribute and viewbox width is not present, so browser default of 300px is used";
    public static final String NAMED_OBJECT_NAME_NULL_OR_EMPTY = "The name of the named object can't be null or empty.";
    public static final String NAMED_OBJECT_NULL = "A named object can't be null.";
    public static final String NONINVERTIBLE_TRANSFORMATION_MATRIX_USED_IN_CLIP_PATH = "Non-invertible transformation matrix was used in a clipping path context. Clipped elements may show undefined behavior.";
    public static final String NOROOT = "No root found";
    public static final String PARAMETER_CANNOT_BE_NULL = "Parameters for this method cannot be null.";
    public static final String PATH_WRONG_NUMBER_OF_ARGUMENTS = "Path operator {0} has received {1} arguments, but expects between {2} and {3} arguments. \n Resulting SVG will be incorrect.";
    public static final String POINTS_ATTRIBUTE_INVALID_LIST = "Points attribute {0} on polyline tag does not contain a valid set of points";
    public static final String ROOT_SVG_NO_BBOX = "The root svg tag needs to have a bounding box defined.";
    public static final String TAGPARAMETERNULL = "Tag parameter must not be null";
    public static final String TRANSFORM_EMPTY = "The transformation value is empty.";
    public static final String TRANSFORM_INCORRECT_NUMBER_OF_VALUES = "Transformation doesn't contain the right number of values.";
    @Deprecated
    public static final String TRANSFORM_INCORRECT_VALUE_TYPE = "The transformation value is not a number.";
    public static final String TRANSFORM_NULL = "The transformation value is null.";
    public static final String UNABLE_TO_GET_INVERSE_MATRIX_DUE_TO_ZERO_DETERMINANT = "Unable to get inverse transformation matrix and thus calculate a viewport for the element because some of the transformation matrices, which are written to document, have a determinant of zero value. A bbox of zero values will be used as a viewport for this element.";
    public static final String UNABLE_TO_RETRIEVE_FONT = "Unable to retrieve font:\n {0}";
    public static final String UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI = "Unable to retrieve stream with given base URI ({0}) and source path ({1})";
    public static final String UNKNOWN_TRANSFORMATION_TYPE = "Unsupported type of transformation.";
    public static final String UNMAPPEDTAG = "Could not find implementation for tag {0}";

    private SvgLogMessageConstant() {
    }
}
