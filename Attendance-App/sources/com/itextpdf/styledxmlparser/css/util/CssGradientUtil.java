package com.itextpdf.styledxmlparser.css.util;

import com.itextpdf.kernel.colors.gradients.AbstractLinearGradientBuilder;
import com.itextpdf.kernel.colors.gradients.GradientColorStop;
import com.itextpdf.kernel.colors.gradients.GradientSpreadMethod;
import com.itextpdf.kernel.colors.gradients.StrategyBasedLinearGradientBuilder;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
import com.itextpdf.styledxmlparser.exceptions.StyledXMLParserException;
import java.util.ArrayList;
import java.util.List;

public final class CssGradientUtil {
    private static final String LINEAR_GRADIENT_FUNCTION_SUFFIX = "linear-gradient(";
    private static final String REPEATING_LINEAR_GRADIENT_FUNCTION_SUFFIX = "repeating-linear-gradient(";

    private CssGradientUtil() {
    }

    public static boolean isCssLinearGradientValue(String cssValue) {
        if (cssValue == null) {
            return false;
        }
        String normalizedValue = cssValue.toLowerCase().trim();
        if (!normalizedValue.endsWith(")")) {
            return false;
        }
        if (normalizedValue.startsWith(LINEAR_GRADIENT_FUNCTION_SUFFIX) || normalizedValue.startsWith(REPEATING_LINEAR_GRADIENT_FUNCTION_SUFFIX)) {
            return true;
        }
        return false;
    }

    public static StrategyBasedLinearGradientBuilder parseCssLinearGradient(String cssGradientValue, float emValue, float remValue) {
        if (!isCssLinearGradientValue(cssGradientValue)) {
            return null;
        }
        String cssGradientValue2 = cssGradientValue.toLowerCase().trim();
        boolean isRepeating = false;
        String argumentsPart = null;
        if (cssGradientValue2.startsWith(LINEAR_GRADIENT_FUNCTION_SUFFIX)) {
            argumentsPart = cssGradientValue2.substring(LINEAR_GRADIENT_FUNCTION_SUFFIX.length(), cssGradientValue2.length() - 1);
            isRepeating = false;
        } else if (cssGradientValue2.startsWith(REPEATING_LINEAR_GRADIENT_FUNCTION_SUFFIX)) {
            argumentsPart = cssGradientValue2.substring(REPEATING_LINEAR_GRADIENT_FUNCTION_SUFFIX.length(), cssGradientValue2.length() - 1);
            isRepeating = true;
        }
        if (argumentsPart == null) {
            return null;
        }
        List<String> argumentsList = new ArrayList<>();
        StringBuilder buff = new StringBuilder();
        CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(argumentsPart);
        while (true) {
            CssDeclarationValueTokenizer.Token nextValidToken = tokenizer.getNextValidToken();
            CssDeclarationValueTokenizer.Token nextToken = nextValidToken;
            if (nextValidToken == null) {
                break;
            } else if (nextToken.getType() != CssDeclarationValueTokenizer.TokenType.COMMA) {
                buff.append(" ").append(nextToken.getValue());
            } else if (buff.length() != 0) {
                argumentsList.add(buff.toString().trim());
                buff = new StringBuilder();
            }
        }
        if (buff.length() != 0) {
            argumentsList.add(buff.toString().trim());
        }
        if (!argumentsList.isEmpty()) {
            return parseCssLinearGradient(argumentsList, isRepeating, emValue, remValue);
        }
        throw new StyledXMLParserException(MessageFormatUtil.format(StyledXMLParserException.INVALID_GRADIENT_FUNCTION_ARGUMENTS_LIST, cssGradientValue2));
    }

    private static StrategyBasedLinearGradientBuilder parseCssLinearGradient(List<String> argumentsList, boolean isRepeating, float emValue, float remValue) {
        int colorStopListStartIndex;
        StrategyBasedLinearGradientBuilder builder = new StrategyBasedLinearGradientBuilder();
        builder.setSpreadMethod(isRepeating ? GradientSpreadMethod.REPEAT : GradientSpreadMethod.PAD);
        String firstArgument = argumentsList.get(0);
        if (CssUtils.isAngleValue(firstArgument)) {
            double radAngle = (double) CssUtils.parseAngle(firstArgument);
            Double.isNaN(radAngle);
            builder.setGradientDirectionAsCentralRotationAngle(-radAngle);
            colorStopListStartIndex = 1;
        } else if (firstArgument.startsWith("to ")) {
            builder.setGradientDirectionAsStrategy(parseDirection(firstArgument));
            colorStopListStartIndex = 1;
        } else {
            builder.setGradientDirectionAsStrategy(StrategyBasedLinearGradientBuilder.GradientStrategy.TO_BOTTOM);
            colorStopListStartIndex = 0;
        }
        addStopColors(builder, argumentsList, colorStopListStartIndex, emValue, remValue);
        return builder;
    }

    private static void addStopColors(AbstractLinearGradientBuilder builder, List<String> argumentsList, int stopsStartIndex, float emValue, float remValue) {
        AbstractLinearGradientBuilder abstractLinearGradientBuilder = builder;
        float f = emValue;
        float f2 = remValue;
        GradientColorStop lastCreatedStopColor = null;
        int i = 1;
        int lastStopIndex = argumentsList.size() - 1;
        int i2 = stopsStartIndex;
        while (i2 <= lastStopIndex) {
            String argument = argumentsList.get(i2);
            List<String> elementsList = new ArrayList<>();
            CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(argument);
            while (true) {
                CssDeclarationValueTokenizer.Token nextValidToken = tokenizer.getNextValidToken();
                CssDeclarationValueTokenizer.Token nextToken = nextValidToken;
                if (nextValidToken == null) {
                    break;
                }
                elementsList.add(nextToken.getValue());
            }
            if (elementsList.isEmpty() || elementsList.size() > 3) {
                int i3 = stopsStartIndex;
                throw new StyledXMLParserException(MessageFormatUtil.format(StyledXMLParserException.INVALID_GRADIENT_COLOR_STOP_VALUE, argument));
            }
            if (CssUtils.isColorProperty(elementsList.get(0))) {
                float[] rgba = CssUtils.parseRgbaColor(elementsList.get(0));
                if (elementsList.size() == i) {
                    lastCreatedStopColor = createStopColor(rgba, i2 == stopsStartIndex ? new UnitValue(2, 0.0f) : i2 == lastStopIndex ? new UnitValue(2, 100.0f) : null);
                    abstractLinearGradientBuilder.addColorStop(lastCreatedStopColor);
                } else {
                    int i4 = stopsStartIndex;
                    int j = 1;
                    while (j < elementsList.size()) {
                        if (!CssUtils.isNumericValue(elementsList.get(j))) {
                            UnitValue offset = CssUtils.parseLengthValueToPt(elementsList.get(j), f, f2);
                            if (offset != null) {
                                lastCreatedStopColor = createStopColor(rgba, offset);
                                abstractLinearGradientBuilder.addColorStop(lastCreatedStopColor);
                                j++;
                            } else {
                                GradientColorStop gradientColorStop = lastCreatedStopColor;
                                throw new StyledXMLParserException(MessageFormatUtil.format(StyledXMLParserException.INVALID_GRADIENT_COLOR_STOP_VALUE, argument));
                            }
                        } else {
                            throw new StyledXMLParserException(MessageFormatUtil.format(StyledXMLParserException.INVALID_GRADIENT_COLOR_STOP_VALUE, argument));
                        }
                    }
                }
            } else {
                int i5 = stopsStartIndex;
                if (elementsList.size() != 1 || lastCreatedStopColor == null || lastCreatedStopColor.getHintOffsetType() != GradientColorStop.HintOffsetType.NONE || i2 == lastStopIndex) {
                    throw new StyledXMLParserException(MessageFormatUtil.format(StyledXMLParserException.INVALID_GRADIENT_COLOR_STOP_VALUE, argument));
                }
                UnitValue hint = CssUtils.parseLengthValueToPt(elementsList.get(0), f, f2);
                if (hint == null) {
                    throw new StyledXMLParserException(MessageFormatUtil.format(StyledXMLParserException.INVALID_GRADIENT_COLOR_STOP_VALUE, argument));
                } else if (hint.getUnitType() == 2) {
                    lastCreatedStopColor.setHint((double) (hint.getValue() / 100.0f), GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT);
                } else {
                    lastCreatedStopColor.setHint((double) hint.getValue(), GradientColorStop.HintOffsetType.ABSOLUTE_ON_GRADIENT);
                }
            }
            i2++;
            abstractLinearGradientBuilder = builder;
            f = emValue;
            f2 = remValue;
            i = 1;
        }
        List<String> list = argumentsList;
        int i6 = stopsStartIndex;
    }

    private static StrategyBasedLinearGradientBuilder.GradientStrategy parseDirection(String argument) {
        String[] elementsList = argument.split("\\s+");
        if (elementsList.length >= 2) {
            int topCount = 0;
            int bottomCount = 0;
            int leftCount = 0;
            int rightCount = 0;
            for (int i = 1; i < elementsList.length; i++) {
                if (CommonCssConstants.TOP.equals(elementsList[i])) {
                    topCount++;
                } else if (CommonCssConstants.BOTTOM.equals(elementsList[i])) {
                    bottomCount++;
                } else if ("left".equals(elementsList[i])) {
                    leftCount++;
                } else if ("right".equals(elementsList[i])) {
                    rightCount++;
                } else {
                    throw new StyledXMLParserException(MessageFormatUtil.format(StyledXMLParserException.INVALID_GRADIENT_TO_SIDE_OR_CORNER_STRING, argument));
                }
            }
            if (topCount == 1 && bottomCount == 0) {
                if (leftCount == 1 && rightCount == 0) {
                    return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_TOP_LEFT;
                }
                if (leftCount == 0 && rightCount == 1) {
                    return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_TOP_RIGHT;
                }
                if (leftCount == 0 && rightCount == 0) {
                    return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_TOP;
                }
            } else if (topCount == 0 && bottomCount == 1) {
                if (leftCount == 1 && rightCount == 0) {
                    return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_BOTTOM_LEFT;
                }
                if (leftCount == 0 && rightCount == 1) {
                    return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_BOTTOM_RIGHT;
                }
                if (leftCount == 0 && rightCount == 0) {
                    return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_BOTTOM;
                }
            } else if (topCount == 0 && bottomCount == 0) {
                if (leftCount == 1 && rightCount == 0) {
                    return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_LEFT;
                }
                if (leftCount == 0 && rightCount == 1) {
                    return StrategyBasedLinearGradientBuilder.GradientStrategy.TO_RIGHT;
                }
            }
            throw new StyledXMLParserException(MessageFormatUtil.format(StyledXMLParserException.INVALID_GRADIENT_TO_SIDE_OR_CORNER_STRING, argument));
        }
        throw new StyledXMLParserException(MessageFormatUtil.format(StyledXMLParserException.INVALID_GRADIENT_TO_SIDE_OR_CORNER_STRING, argument));
    }

    private static GradientColorStop createStopColor(float[] rgba, UnitValue offset) {
        double offsetValue;
        GradientColorStop.OffsetType offsetType;
        if (offset == null) {
            offsetType = GradientColorStop.OffsetType.AUTO;
            offsetValue = 0.0d;
        } else if (offset.getUnitType() == 1) {
            offsetType = GradientColorStop.OffsetType.ABSOLUTE;
            offsetValue = (double) offset.getValue();
        } else {
            offsetType = GradientColorStop.OffsetType.RELATIVE;
            offsetValue = (double) (offset.getValue() / 100.0f);
        }
        return new GradientColorStop(rgba, offsetValue, offsetType);
    }
}
