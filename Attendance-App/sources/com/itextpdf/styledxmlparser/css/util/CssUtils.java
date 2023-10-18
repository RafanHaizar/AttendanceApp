package com.itextpdf.styledxmlparser.css.util;

import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.layout.font.Range;
import com.itextpdf.layout.font.RangeBuilder;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.CommonAttributeConstants;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;
import com.itextpdf.styledxmlparser.exceptions.StyledXMLParserException;
import com.itextpdf.styledxmlparser.node.IElementNode;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CssUtils {
    private static final String[] ANGLE_MEASUREMENTS_VALUES = {CommonCssConstants.DEG, CommonCssConstants.GRAD, CommonCssConstants.RAD};
    private static final float EPSILON = 1.0E-6f;
    private static final String[] FONT_RELATIVE_MEASUREMENTS_VALUES = {CommonCssConstants.f1611EM, CommonCssConstants.f1612EX, CommonCssConstants.REM};
    private static final String[] RELATIVE_MEASUREMENTS_VALUES = {CommonCssConstants.PERCENTAGE, CommonCssConstants.f1611EM, CommonCssConstants.f1612EX, CommonCssConstants.REM};
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) CssUtils.class);

    private CssUtils() {
    }

    public static List<String> splitStringWithComma(String value) {
        if (value == null) {
            return new ArrayList();
        }
        List<String> resultList = new ArrayList<>();
        int lastComma = 0;
        int notClosedBrackets = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == ',' && notClosedBrackets == 0) {
                resultList.add(value.substring(lastComma, i));
                lastComma = i + 1;
            }
            if (value.charAt(i) == '(') {
                notClosedBrackets++;
            }
            if (value.charAt(i) == ')') {
                notClosedBrackets = Math.max(notClosedBrackets - 1, 0);
            }
        }
        String lastToken = value.substring(lastComma);
        if (!lastToken.isEmpty()) {
            resultList.add(lastToken);
        }
        return resultList;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.layout.property.BlendMode parseBlendMode(java.lang.String r1) {
        /*
            if (r1 != 0) goto L_0x0005
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.NORMAL
            return r0
        L_0x0005:
            int r0 = r1.hashCode()
            switch(r0) {
                case -2120744511: goto L_0x00b7;
                case -1427739212: goto L_0x00ad;
                case -1338968417: goto L_0x00a3;
                case -1247677005: goto L_0x0097;
                case -1091287984: goto L_0x008c;
                case -1039745817: goto L_0x0080;
                case -907689876: goto L_0x0075;
                case -230491182: goto L_0x0069;
                case -120580883: goto L_0x005f;
                case 103672: goto L_0x0054;
                case 94842723: goto L_0x0048;
                case 170546239: goto L_0x003d;
                case 653829668: goto L_0x0031;
                case 1242982905: goto L_0x0026;
                case 1686617550: goto L_0x001a;
                case 1728361789: goto L_0x000e;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x00c2
        L_0x000e:
            java.lang.String r0 = "difference"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 9
            goto L_0x00c3
        L_0x001a:
            java.lang.String r0 = "exclusion"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 10
            goto L_0x00c3
        L_0x0026:
            java.lang.String r0 = "color-burn"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 6
            goto L_0x00c3
        L_0x0031:
            java.lang.String r0 = "multiply"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 0
            goto L_0x00c3
        L_0x003d:
            java.lang.String r0 = "lighten"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 4
            goto L_0x00c3
        L_0x0048:
            java.lang.String r0 = "color"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 13
            goto L_0x00c3
        L_0x0054:
            java.lang.String r0 = "hue"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 11
            goto L_0x00c3
        L_0x005f:
            java.lang.String r0 = "color-dodge"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 5
            goto L_0x00c3
        L_0x0069:
            java.lang.String r0 = "saturation"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 12
            goto L_0x00c3
        L_0x0075:
            java.lang.String r0 = "screen"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 1
            goto L_0x00c3
        L_0x0080:
            java.lang.String r0 = "normal"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 15
            goto L_0x00c3
        L_0x008c:
            java.lang.String r0 = "overlay"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 2
            goto L_0x00c3
        L_0x0097:
            java.lang.String r0 = "soft-light"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 8
            goto L_0x00c3
        L_0x00a3:
            java.lang.String r0 = "darken"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 3
            goto L_0x00c3
        L_0x00ad:
            java.lang.String r0 = "hard-light"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 7
            goto L_0x00c3
        L_0x00b7:
            java.lang.String r0 = "luminosity"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x000c
            r0 = 14
            goto L_0x00c3
        L_0x00c2:
            r0 = -1
        L_0x00c3:
            switch(r0) {
                case 0: goto L_0x00f3;
                case 1: goto L_0x00f0;
                case 2: goto L_0x00ed;
                case 3: goto L_0x00ea;
                case 4: goto L_0x00e7;
                case 5: goto L_0x00e4;
                case 6: goto L_0x00e1;
                case 7: goto L_0x00de;
                case 8: goto L_0x00db;
                case 9: goto L_0x00d8;
                case 10: goto L_0x00d5;
                case 11: goto L_0x00d2;
                case 12: goto L_0x00cf;
                case 13: goto L_0x00cc;
                case 14: goto L_0x00c9;
                default: goto L_0x00c6;
            }
        L_0x00c6:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.NORMAL
            return r0
        L_0x00c9:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.LUMINOSITY
            return r0
        L_0x00cc:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.COLOR
            return r0
        L_0x00cf:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.SATURATION
            return r0
        L_0x00d2:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.HUE
            return r0
        L_0x00d5:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.EXCLUSION
            return r0
        L_0x00d8:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.DIFFERENCE
            return r0
        L_0x00db:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.SOFT_LIGHT
            return r0
        L_0x00de:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.HARD_LIGHT
            return r0
        L_0x00e1:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.COLOR_BURN
            return r0
        L_0x00e4:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.COLOR_DODGE
            return r0
        L_0x00e7:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.LIGHTEN
            return r0
        L_0x00ea:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.DARKEN
            return r0
        L_0x00ed:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.OVERLAY
            return r0
        L_0x00f0:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.SCREEN
            return r0
        L_0x00f3:
            com.itextpdf.layout.property.BlendMode r0 = com.itextpdf.layout.property.BlendMode.MULTIPLY
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.css.util.CssUtils.parseBlendMode(java.lang.String):com.itextpdf.layout.property.BlendMode");
    }

    public static List<List<String>> extractShorthandProperties(String str) {
        List<List<String>> result = new ArrayList<>();
        List<String> currentLayer = new ArrayList<>();
        CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(str);
        for (CssDeclarationValueTokenizer.Token currentToken = tokenizer.getNextValidToken(); currentToken != null; currentToken = tokenizer.getNextValidToken()) {
            if (currentToken.getType() == CssDeclarationValueTokenizer.TokenType.COMMA) {
                result.add(currentLayer);
                currentLayer = new ArrayList<>();
            } else {
                currentLayer.add(currentToken.getValue());
            }
        }
        result.add(currentLayer);
        return result;
    }

    public static String normalizeCssProperty(String str) {
        if (str == null) {
            return null;
        }
        return CssPropertyNormalizer.normalize(str);
    }

    public static String removeDoubleSpacesAndTrim(String str) {
        String[] parts = str.split("\\s");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.length() > 0) {
                if (sb.length() != 0) {
                    sb.append(" ");
                }
                sb.append(part);
            }
        }
        return sb.toString();
    }

    public static Integer parseInteger(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Float parseFloat(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Float.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double parseDouble(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static float parseAngle(String angle, String defaultMetric) {
        int pos = determinePositionBetweenValueAndUnit(angle);
        if (pos == 0) {
            if (angle == null) {
                angle = "null";
            }
            throw new StyledXMLParserException(MessageFormatUtil.format("The passed value (@{0}) is not a number", angle));
        }
        float floatValue = Float.parseFloat(angle.substring(0, pos));
        String unit = angle.substring(pos);
        if (unit.startsWith(CommonCssConstants.DEG) || (unit.equals("") && CommonCssConstants.DEG.equals(defaultMetric))) {
            return (3.1415927f * floatValue) / 180.0f;
        }
        if (unit.startsWith(CommonCssConstants.GRAD) || (unit.equals("") && CommonCssConstants.GRAD.equals(defaultMetric))) {
            return (3.1415927f * floatValue) / 200.0f;
        }
        if (unit.startsWith(CommonCssConstants.RAD) || (unit.equals("") && CommonCssConstants.RAD.equals(defaultMetric))) {
            return floatValue;
        }
        Logger logger2 = logger;
        Object[] objArr = new Object[1];
        objArr[0] = unit.equals("") ? defaultMetric : unit;
        logger2.error(MessageFormatUtil.format(LogMessageConstant.UNKNOWN_METRIC_ANGLE_PARSED, objArr));
        return floatValue;
    }

    public static float parseAngle(String angle) {
        return parseAngle(angle, CommonCssConstants.DEG);
    }

    public static int[] parseAspectRatio(String str) {
        int indexOfSlash = str.indexOf(47);
        try {
            return new int[]{Integer.parseInt(str.substring(0, indexOfSlash)), Integer.parseInt(str.substring(indexOfSlash + 1))};
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    public static float parseAbsoluteLength(String length, String defaultMetric) {
        int pos = determinePositionBetweenValueAndUnit(length);
        if (pos == 0) {
            if (length == null) {
                length = "null";
            }
            throw new StyledXMLParserException(MessageFormatUtil.format("The passed value (@{0}) is not a number", length));
        }
        double f = Double.parseDouble(length.substring(0, pos));
        String unit = length.substring(pos);
        if (unit.startsWith(CommonCssConstants.f1616PT) || (unit.equals("") && defaultMetric.equals(CommonCssConstants.f1616PT))) {
            return (float) f;
        }
        if (unit.startsWith(CommonCssConstants.f1613IN) || (unit.equals("") && defaultMetric.equals(CommonCssConstants.f1613IN))) {
            return (float) (72.0d * f);
        }
        if (unit.startsWith(CommonCssConstants.f1610CM) || (unit.equals("") && defaultMetric.equals(CommonCssConstants.f1610CM))) {
            return (float) ((f / 2.54d) * 72.0d);
        }
        if (unit.startsWith("q") || (unit.equals("") && defaultMetric.equals("q"))) {
            return (float) (((f / 2.54d) * 72.0d) / 40.0d);
        }
        if (unit.startsWith(CommonCssConstants.f1614MM) || (unit.equals("") && defaultMetric.equals(CommonCssConstants.f1614MM))) {
            return (float) ((f / 25.4d) * 72.0d);
        }
        if (unit.startsWith(CommonCssConstants.f1615PC) || (unit.equals("") && defaultMetric.equals(CommonCssConstants.f1615PC))) {
            return (float) (12.0d * f);
        }
        if (unit.startsWith(CommonCssConstants.f1617PX) || (unit.equals("") && defaultMetric.equals(CommonCssConstants.f1617PX))) {
            return (float) (0.75d * f);
        }
        Logger logger2 = logger;
        Object[] objArr = new Object[1];
        objArr[0] = unit.equals("") ? defaultMetric : unit;
        logger2.error(MessageFormatUtil.format(LogMessageConstant.UNKNOWN_ABSOLUTE_METRIC_LENGTH_PARSED, objArr));
        return (float) f;
    }

    public static float parseAbsoluteLength(String length) {
        return parseAbsoluteLength(length, CommonCssConstants.f1617PX);
    }

    public static float parseRelativeValue(String relativeValue, float baseValue) {
        int pos = determinePositionBetweenValueAndUnit(relativeValue);
        if (pos == 0) {
            return 0.0f;
        }
        double f = Double.parseDouble(relativeValue.substring(0, pos));
        String unit = relativeValue.substring(pos);
        if (unit.startsWith(CommonCssConstants.PERCENTAGE)) {
            double d = (double) baseValue;
            Double.isNaN(d);
            f = (d * f) / 100.0d;
        } else if (unit.startsWith(CommonCssConstants.f1611EM) || unit.startsWith(CommonCssConstants.REM)) {
            double d2 = (double) baseValue;
            Double.isNaN(d2);
            f *= d2;
        } else if (unit.startsWith(CommonCssConstants.f1612EX)) {
            double d3 = (double) baseValue;
            Double.isNaN(d3);
            f = (d3 * f) / 2.0d;
        }
        return (float) f;
    }

    public static UnitValue parseLengthValueToPt(String value, float emValue, float remValue) {
        if (isMetricValue(value) || isNumericValue(value)) {
            return new UnitValue(1, parseAbsoluteLength(value));
        }
        if (value != null && value.endsWith(CommonCssConstants.PERCENTAGE)) {
            return new UnitValue(2, Float.parseFloat(value.substring(0, value.length() - 1)));
        }
        if (isRemValue(value)) {
            return new UnitValue(1, parseRelativeValue(value, remValue));
        }
        if (isRelativeValue(value)) {
            return new UnitValue(1, parseRelativeValue(value, emValue));
        }
        return null;
    }

    public static boolean isValidNumericValue(String value) {
        if (value == null || value.contains(" ")) {
            return false;
        }
        if (isRelativeValue(value) || isMetricValue(value) || isNumericValue(value)) {
            return true;
        }
        return false;
    }

    public static float parseAbsoluteFontSize(String fontSizeValue, String defaultMetric) {
        if (fontSizeValue != null && CommonCssConstants.FONT_ABSOLUTE_SIZE_KEYWORDS_VALUES.containsKey(fontSizeValue)) {
            fontSizeValue = CommonCssConstants.FONT_ABSOLUTE_SIZE_KEYWORDS_VALUES.get(fontSizeValue);
        }
        try {
            return parseAbsoluteLength(fontSizeValue, defaultMetric);
        } catch (StyledXMLParserException e) {
            return 0.0f;
        }
    }

    public static float parseAbsoluteFontSize(String fontSizeValue) {
        return parseAbsoluteFontSize(fontSizeValue, CommonCssConstants.f1617PX);
    }

    public static float parseRelativeFontSize(String relativeFontSizeValue, float baseValue) {
        if (CommonCssConstants.SMALLER.equals(relativeFontSizeValue)) {
            double d = (double) baseValue;
            Double.isNaN(d);
            return (float) (d / 1.2d);
        } else if (!CommonCssConstants.LARGER.equals(relativeFontSizeValue)) {
            return parseRelativeValue(relativeFontSizeValue, baseValue);
        } else {
            double d2 = (double) baseValue;
            Double.isNaN(d2);
            return (float) (d2 * 1.2d);
        }
    }

    public static UnitValue[] parseSpecificCornerBorderRadius(String specificBorderRadius, float emValue, float remValue) {
        if (specificBorderRadius == null) {
            return null;
        }
        UnitValue[] cornerRadii = new UnitValue[2];
        String[] props = specificBorderRadius.split("\\s+");
        cornerRadii[0] = parseLengthValueToPt(props[0], emValue, remValue);
        cornerRadii[1] = 2 == props.length ? parseLengthValueToPt(props[1], emValue, remValue) : cornerRadii[0];
        return cornerRadii;
    }

    public static float parseResolution(String resolutionStr) {
        int pos = determinePositionBetweenValueAndUnit(resolutionStr);
        if (pos == 0) {
            return 0.0f;
        }
        double f = Double.parseDouble(resolutionStr.substring(0, pos));
        String unit = resolutionStr.substring(pos);
        if (unit.startsWith(CommonCssConstants.DPCM)) {
            f *= 2.54d;
        } else if (unit.startsWith(CommonCssConstants.DPPX)) {
            f *= 96.0d;
        } else if (!unit.startsWith(CommonCssConstants.DPI)) {
            throw new StyledXMLParserException(LogMessageConstant.INCORRECT_RESOLUTION_UNIT_VALUE);
        }
        return (float) f;
    }

    public static int determinePositionBetweenValueAndUnit(String string) {
        if (string == null) {
            return 0;
        }
        int pos = 0;
        while (pos < string.length() && (string.charAt(pos) == '+' || string.charAt(pos) == '-' || string.charAt(pos) == '.' || isDigit(string.charAt(pos)) || isExponentNotation(string, pos))) {
            pos++;
        }
        return pos;
    }

    public static boolean isMetricValue(String value) {
        if (value == null) {
            return false;
        }
        for (String metricPostfix : CommonCssConstants.METRIC_MEASUREMENTS_VALUES) {
            if (value.endsWith(metricPostfix) && isNumericValue(value.substring(0, value.length() - metricPostfix.length()).trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAngleValue(String value) {
        if (value == null) {
            return false;
        }
        for (String metricPostfix : ANGLE_MEASUREMENTS_VALUES) {
            if (value.endsWith(metricPostfix) && isNumericValue(value.substring(0, value.length() - metricPostfix.length()).trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRelativeValue(String value) {
        if (value == null) {
            return false;
        }
        for (String relativePostfix : RELATIVE_MEASUREMENTS_VALUES) {
            if (value.endsWith(relativePostfix) && isNumericValue(value.substring(0, value.length() - relativePostfix.length()).trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFontRelativeValue(String value) {
        if (value == null) {
            return false;
        }
        for (String relativePostfix : FONT_RELATIVE_MEASUREMENTS_VALUES) {
            if (value.endsWith(relativePostfix) && isNumericValue(value.substring(0, value.length() - relativePostfix.length()).trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPercentageValue(String value) {
        return value != null && value.endsWith(CommonCssConstants.PERCENTAGE) && isNumericValue(value.substring(0, value.length() - CommonCssConstants.PERCENTAGE.length()).trim());
    }

    public static boolean isRemValue(String value) {
        return value != null && value.endsWith(CommonCssConstants.REM) && isNumericValue(value.substring(0, value.length() - CommonCssConstants.REM.length()).trim());
    }

    public static boolean isEmValue(String value) {
        return value != null && value.endsWith(CommonCssConstants.f1611EM) && isNumericValue(value.substring(0, value.length() - CommonCssConstants.f1611EM.length()).trim());
    }

    public static boolean isExValue(String value) {
        return value != null && value.endsWith(CommonCssConstants.f1612EX) && isNumericValue(value.substring(0, value.length() - CommonCssConstants.f1612EX.length()).trim());
    }

    public static boolean isNumericValue(String value) {
        return value != null && (value.matches("^[-+]?\\d\\d*\\.\\d*$") || value.matches("^[-+]?\\d\\d*$") || value.matches("^[-+]?\\.\\d\\d*$"));
    }

    public static String extractUrl(String url) {
        if (!url.startsWith("url")) {
            return url;
        }
        String urlString = url.substring(3).trim().replace("(", "").replace(")", "").trim();
        if (urlString.startsWith("'") && urlString.endsWith("'")) {
            return urlString.substring(urlString.indexOf("'") + 1, urlString.lastIndexOf("'"));
        }
        if (!urlString.startsWith("\"") || !urlString.endsWith("\"")) {
            return urlString;
        }
        return urlString.substring(urlString.indexOf(34) + 1, urlString.lastIndexOf(34));
    }

    public static boolean isBase64Data(String data) {
        return data.matches("^data:([^\\s]*);base64,([^\\s]*)");
    }

    public static int findNextUnescapedChar(String source, char ch, int startIndex) {
        int symbolPos = source.indexOf(ch, startIndex);
        if (symbolPos == -1) {
            return -1;
        }
        int afterNoneEscapePos = symbolPos;
        while (afterNoneEscapePos > 0 && source.charAt(afterNoneEscapePos - 1) == '\\') {
            afterNoneEscapePos--;
        }
        return (symbolPos - afterNoneEscapePos) % 2 == 0 ? symbolPos : findNextUnescapedChar(source, ch, symbolPos + 1);
    }

    public static boolean isColorProperty(String value) {
        return value.startsWith("rgb(") || value.startsWith("rgba(") || value.startsWith("#") || WebColors.NAMES.containsKey(value.toLowerCase()) || CommonCssConstants.TRANSPARENT.equals(value);
    }

    public static boolean compareFloats(double d1, double d2) {
        return Math.abs(d1 - d2) < 9.999999974752427E-7d;
    }

    public static boolean compareFloats(float f1, float f2) {
        return Math.abs(f1 - f2) < EPSILON;
    }

    public static float[] parseRgbaColor(String colorValue) {
        float[] rgbaColor = WebColors.getRGBAColor(colorValue);
        if (rgbaColor != null) {
            return rgbaColor;
        }
        logger.error(MessageFormatUtil.format(com.itextpdf.p026io.LogMessageConstant.COLOR_NOT_PARSED, colorValue));
        return new float[]{0.0f, 0.0f, 0.0f, 1.0f};
    }

    public static Range parseUnicodeRange(String unicodeRange) {
        String[] ranges = unicodeRange.split(",");
        RangeBuilder builder = new RangeBuilder();
        for (String range : ranges) {
            if (!addRange(builder, range)) {
                return null;
            }
        }
        return builder.create();
    }

    public static float convertPtsToPx(float pts) {
        return pts / 0.75f;
    }

    public static double convertPtsToPx(double pts) {
        return pts / 0.75d;
    }

    public static float convertPxToPts(float px) {
        return 0.75f * px;
    }

    public static double convertPxToPts(double px) {
        return 0.75d * px;
    }

    public static boolean isStyleSheetLink(IElementNode headChildElement) {
        return "link".equals(headChildElement.name()) && CommonAttributeConstants.STYLESHEET.equals(headChildElement.getAttribute(CommonAttributeConstants.REL));
    }

    public static boolean isInitialOrInheritOrUnset(String value) {
        return CommonCssConstants.INITIAL.equals(value) || CommonCssConstants.INHERIT.equals(value) || CommonCssConstants.UNSET.equals(value);
    }

    private static boolean addRange(RangeBuilder builder, String range) {
        String range2 = range.trim();
        if (!range2.matches("[uU]\\+[0-9a-fA-F?]{1,6}(-[0-9a-fA-F]{1,6})?")) {
            return false;
        }
        String[] parts = range2.substring(2, range2.length()).split("-");
        if (1 != parts.length) {
            return addRange(builder, parts[0], parts[1]);
        }
        if (parts[0].contains("?")) {
            return addRange(builder, parts[0].replace('?', '0'), parts[0].replace('?', 'F'));
        }
        return addRange(builder, parts[0], parts[0]);
    }

    private static boolean addRange(RangeBuilder builder, String left, String right) {
        int l = Integer.parseInt(left, 16);
        int r = Integer.parseInt(right, 16);
        if (l > r || r > 1114111) {
            return false;
        }
        builder.addRange(l, r);
        return true;
    }

    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private static boolean isExponentNotation(String s, int index) {
        return index < s.length() && s.charAt(index) == 'e' && ((index + 1 < s.length() && isDigit(s.charAt(index + 1))) || (index + 2 < s.length() && ((s.charAt(index + 1) == '-' || s.charAt(index + 1) == '+') && isDigit(s.charAt(index + 2)))));
    }
}
