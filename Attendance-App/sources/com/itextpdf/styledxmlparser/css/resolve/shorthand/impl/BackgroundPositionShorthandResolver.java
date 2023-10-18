package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.css.validate.CssDeclarationValidationMaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackgroundPositionShorthandResolver implements IShorthandResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) BackgroundPositionShorthandResolver.class);
    private static final int POSITION_VALUES_MAX_COUNT = 2;

    private enum BackgroundPositionType {
        NUMERIC,
        HORIZONTAL_POSITION,
        VERTICAL_POSITION,
        CENTER
    }

    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        if (CssUtils.isInitialOrInheritOrUnset(shorthandExpression)) {
            return Arrays.asList(new CssDeclaration[]{new CssDeclaration(CommonCssConstants.BACKGROUND_POSITION_X, shorthandExpression), new CssDeclaration(CommonCssConstants.BACKGROUND_POSITION_Y, shorthandExpression)});
        } else if (shorthandExpression.trim().isEmpty()) {
            LOGGER.error(MessageFormatUtil.format(LogMessageConstant.SHORTHAND_PROPERTY_CANNOT_BE_EMPTY, CommonCssConstants.BACKGROUND_POSITION));
            return new ArrayList();
        } else {
            List<List<String>> propsList = CssUtils.extractShorthandProperties(shorthandExpression);
            Map<String, String> resolvedProps = new HashMap<>();
            Map<String, String> values = new HashMap<>();
            for (List<String> props : propsList) {
                if (props.isEmpty()) {
                    LOGGER.error(MessageFormatUtil.format(LogMessageConstant.SHORTHAND_PROPERTY_CANNOT_BE_EMPTY, CommonCssConstants.BACKGROUND_POSITION));
                    return new ArrayList();
                } else if (!parsePositionShorthand(props, values)) {
                    LOGGER.error(MessageFormatUtil.format(LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, shorthandExpression));
                    return new ArrayList();
                } else {
                    updateValue(resolvedProps, values, CommonCssConstants.BACKGROUND_POSITION_X);
                    updateValue(resolvedProps, values, CommonCssConstants.BACKGROUND_POSITION_Y);
                    values.clear();
                }
            }
            if (!checkProperty(resolvedProps, CommonCssConstants.BACKGROUND_POSITION_X) || !checkProperty(resolvedProps, CommonCssConstants.BACKGROUND_POSITION_Y)) {
                return new ArrayList();
            }
            return Arrays.asList(new CssDeclaration[]{new CssDeclaration(CommonCssConstants.BACKGROUND_POSITION_X, resolvedProps.get(CommonCssConstants.BACKGROUND_POSITION_X)), new CssDeclaration(CommonCssConstants.BACKGROUND_POSITION_Y, resolvedProps.get(CommonCssConstants.BACKGROUND_POSITION_Y))});
        }
    }

    private static boolean checkProperty(Map<String, String> resolvedProps, String key) {
        if (CssDeclarationValidationMaster.checkDeclaration(new CssDeclaration(key, resolvedProps.get(key)))) {
            return true;
        }
        LOGGER.error(MessageFormatUtil.format(LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, resolvedProps.get(key)));
        return false;
    }

    private static void updateValue(Map<String, String> resolvedProps, Map<String, String> values, String key) {
        if (values.get(key) == null) {
            if (resolvedProps.get(key) == null) {
                resolvedProps.put(key, CommonCssConstants.CENTER);
            } else {
                resolvedProps.put(key, resolvedProps.get(key) + "," + CommonCssConstants.CENTER);
            }
        } else if (resolvedProps.get(key) == null) {
            resolvedProps.put(key, values.get(key));
        } else {
            resolvedProps.put(key, resolvedProps.get(key) + "," + values.get(key));
        }
    }

    private static boolean parsePositionShorthand(List<String> valuesToParse, Map<String, String> parsedValues) {
        for (String positionValue : valuesToParse) {
            if (!parseNonNumericValue(positionValue, parsedValues)) {
                return false;
            }
        }
        for (int i = 0; i < valuesToParse.size(); i++) {
            if (typeOfValue(valuesToParse.get(i)) == BackgroundPositionType.NUMERIC && !parseNumericValue(i, valuesToParse, parsedValues)) {
                return false;
            }
        }
        return true;
    }

    private static boolean parseNumericValue(int i, List<String> positionValues, Map<String, String> values) {
        if (values.get(CommonCssConstants.BACKGROUND_POSITION_X) == null || values.get(CommonCssConstants.BACKGROUND_POSITION_Y) == null) {
            return parseShortNumericValue(i, positionValues, values, positionValues.get(i));
        }
        if (i == 0) {
            return false;
        }
        return parseLargeNumericValue(positionValues.get(i - 1), values, positionValues.get(i));
    }

    private static boolean parseShortNumericValue(int i, List<String> positionValues, Map<String, String> values, String value) {
        if (positionValues.size() > 2) {
            return false;
        }
        if (values.get(CommonCssConstants.BACKGROUND_POSITION_X) == null) {
            if (i != 0) {
                return false;
            }
            values.put(CommonCssConstants.BACKGROUND_POSITION_X, value);
            return true;
        } else if (i != 0) {
            values.put(CommonCssConstants.BACKGROUND_POSITION_Y, value);
            return true;
        } else if (typeOfValue(positionValues.get(i + 1)) != BackgroundPositionType.CENTER) {
            return false;
        } else {
            values.put(CommonCssConstants.BACKGROUND_POSITION_X, value);
            values.put(CommonCssConstants.BACKGROUND_POSITION_Y, CommonCssConstants.CENTER);
            return true;
        }
    }

    private static boolean parseLargeNumericValue(String prevValue, Map<String, String> values, String value) {
        if (typeOfValue(prevValue) == BackgroundPositionType.HORIZONTAL_POSITION) {
            values.put(CommonCssConstants.BACKGROUND_POSITION_X, values.get(CommonCssConstants.BACKGROUND_POSITION_X) + " " + value);
            return true;
        } else if (typeOfValue(prevValue) != BackgroundPositionType.VERTICAL_POSITION) {
            return false;
        } else {
            values.put(CommonCssConstants.BACKGROUND_POSITION_Y, values.get(CommonCssConstants.BACKGROUND_POSITION_Y) + " " + value);
            return true;
        }
    }

    /* renamed from: com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BackgroundPositionShorthandResolver$1 */
    static /* synthetic */ class C14881 {

        /* renamed from: $SwitchMap$com$itextpdf$styledxmlparser$css$resolve$shorthand$impl$BackgroundPositionShorthandResolver$BackgroundPositionType */
        static final /* synthetic */ int[] f1621x55e445c7;

        static {
            int[] iArr = new int[BackgroundPositionType.values().length];
            f1621x55e445c7 = iArr;
            try {
                iArr[BackgroundPositionType.HORIZONTAL_POSITION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1621x55e445c7[BackgroundPositionType.VERTICAL_POSITION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1621x55e445c7[BackgroundPositionType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private static boolean parseNonNumericValue(String positionValue, Map<String, String> values) {
        switch (C14881.f1621x55e445c7[typeOfValue(positionValue).ordinal()]) {
            case 1:
                return parseHorizontal(positionValue, values);
            case 2:
                return parseVertical(positionValue, values);
            case 3:
                return parseCenter(positionValue, values);
            default:
                return true;
        }
    }

    private static boolean parseHorizontal(String positionValue, Map<String, String> values) {
        if (values.get(CommonCssConstants.BACKGROUND_POSITION_X) == null) {
            values.put(CommonCssConstants.BACKGROUND_POSITION_X, positionValue);
            return true;
        } else if (!CommonCssConstants.CENTER.equals(values.get(CommonCssConstants.BACKGROUND_POSITION_X)) || values.get(CommonCssConstants.BACKGROUND_POSITION_Y) != null) {
            return false;
        } else {
            values.put(CommonCssConstants.BACKGROUND_POSITION_X, positionValue);
            values.put(CommonCssConstants.BACKGROUND_POSITION_Y, CommonCssConstants.CENTER);
            return true;
        }
    }

    private static boolean parseVertical(String positionValue, Map<String, String> values) {
        if (values.get(CommonCssConstants.BACKGROUND_POSITION_Y) != null) {
            return false;
        }
        values.put(CommonCssConstants.BACKGROUND_POSITION_Y, positionValue);
        return true;
    }

    private static boolean parseCenter(String positionValue, Map<String, String> values) {
        if (values.get(CommonCssConstants.BACKGROUND_POSITION_X) == null) {
            values.put(CommonCssConstants.BACKGROUND_POSITION_X, positionValue);
            return true;
        } else if (values.get(CommonCssConstants.BACKGROUND_POSITION_Y) != null) {
            return false;
        } else {
            values.put(CommonCssConstants.BACKGROUND_POSITION_Y, positionValue);
            return true;
        }
    }

    private static BackgroundPositionType typeOfValue(String value) {
        if ("left".equals(value) || "right".equals(value)) {
            return BackgroundPositionType.HORIZONTAL_POSITION;
        }
        if (CommonCssConstants.TOP.equals(value) || CommonCssConstants.BOTTOM.equals(value)) {
            return BackgroundPositionType.VERTICAL_POSITION;
        }
        if (CommonCssConstants.CENTER.equals(value)) {
            return BackgroundPositionType.CENTER;
        }
        return BackgroundPositionType.NUMERIC;
    }
}
