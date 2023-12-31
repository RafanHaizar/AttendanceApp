package com.itextpdf.styledxmlparser;

public final class LogMessageConstant {
    @Deprecated
    public static final String BACKGROUND_SHORTHAND_PROPERTY_CANNOT_BE_EMPTY = "Background shorthand property cannot be empty.";
    public static final String DEFAULT_VALUE_OF_CSS_PROPERTY_UNKNOWN = "Default value of the css property \"{0}\" is unknown.";
    public static final String ERROR_ADDING_CHILD_NODE = "Error adding child node.";
    public static final String ERROR_PARSING_COULD_NOT_MAP_NODE = "Could not map node type: {0}";
    public static final String ERROR_PARSING_CSS_SELECTOR = "Error while parsing css selector: {0}";
    public static final String ERROR_RESOLVING_PARENT_STYLES = "Element parent styles are not resolved. Styles for current element might be incorrect.";
    public static final String INCORRECT_RESOLUTION_UNIT_VALUE = "Resolution value unit should be either dpi, dppx or dpcm!";
    public static final String INVALID_CSS_PROPERTY_DECLARATION = "Invalid css property declaration: {0}";
    @Deprecated
    public static final String NAN = "The passed value (@{0}) is not a number";
    public static final String ONLY_THE_LAST_BACKGROUND_CAN_INCLUDE_BACKGROUND_COLOR = "Only the last background can include a background color.";
    public static final String QUOTES_PROPERTY_INVALID = "Quote property \"{0}\" is invalid. It should contain even number of <string> values.";
    public static final String QUOTE_IS_NOT_CLOSED_IN_CSS_EXPRESSION = "The quote is not closed in css expression: {0}";
    public static final String RESOURCE_WITH_GIVEN_URL_WAS_FILTERED_OUT = "Resource with given URL ({0}) was filtered out.";
    public static final String RULE_IS_NOT_SUPPORTED = "The rule @{0} is unsupported. All selectors in this rule will be ignored.";
    public static final String SHORTHAND_PROPERTY_CANNOT_BE_EMPTY = "{0} shorthand property cannot be empty.";
    public static final String UNABLE_TO_PROCESS_EXTERNAL_CSS_FILE = "Unable to process external css file";
    public static final String UNABLE_TO_RESOLVE_IMAGE_URL = "Unable to resolve image path with given base URI ({0}) and image source path ({1})";
    public static final String UNABLE_TO_RETRIEVE_FONT = "Unable to retrieve font:\n {0}";
    public static final String UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI = "Unable to retrieve image with given base URI ({0}) and image source path ({1})";
    public static final String UNABLE_TO_RETRIEVE_RESOURCE_WITH_GIVEN_RESOURCE_SIZE_BYTE_LIMIT = "Unable to retrieve resource with given URL ({0}) and resource size byte limit ({1}).";
    public static final String UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI = "Unable to retrieve stream with given base URI ({0}) and source path ({1})";
    public static final String UNKNOWN_ABSOLUTE_METRIC_LENGTH_PARSED = "Unknown absolute metric length parsed \"{0}\".";
    public static final String UNKNOWN_METRIC_ANGLE_PARSED = "Unknown metric angle parsed: \"{0}\".";
    public static final String UNKNOWN_PROPERTY = "Unknown {0} property: \"{1}\".";
    public static final String UNSUPPORTED_PSEUDO_CSS_SELECTOR = "Unsupported pseudo css selector: {0}";
    public static final String URL_IS_EMPTY_IN_CSS_EXPRESSION = "url function is empty in expression:{0}";
    public static final String URL_IS_NOT_CLOSED_IN_CSS_EXPRESSION = "url function is not properly closed in expression:{0}";
    public static final String WAS_NOT_ABLE_TO_DEFINE_BACKGROUND_CSS_SHORTHAND_PROPERTIES = "Was not able to define one of the background CSS shorthand properties: {0}";

    private LogMessageConstant() {
    }
}
