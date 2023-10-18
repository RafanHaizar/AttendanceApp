package com.itextpdf.styledxmlparser.css.util;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.PortUtil;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

class CssPropertyNormalizer {
    private static final Pattern URL_PATTERN = PortUtil.createRegexPatternWithDotMatchingNewlines("^[uU][rR][lL]\\(.*?");

    CssPropertyNormalizer() {
    }

    static String normalize(String str) {
        StringBuilder sb = new StringBuilder();
        boolean isWhitespace = false;
        int i = 0;
        while (i < str.length()) {
            if (str.charAt(i) == '\\') {
                sb.append(str.charAt(i));
                i++;
                if (i < str.length()) {
                    sb.append(str.charAt(i));
                    i++;
                }
            } else if (Character.isWhitespace(str.charAt(i))) {
                isWhitespace = true;
                i++;
            } else {
                if (isWhitespace) {
                    if (sb.length() > 0 && !trimSpaceAfter(sb.charAt(sb.length() - 1)) && !trimSpaceBefore(str.charAt(i))) {
                        sb.append(" ");
                    }
                    isWhitespace = false;
                }
                if (str.charAt(i) == '\'' || str.charAt(i) == '\"') {
                    i = appendQuotedString(sb, str, i);
                } else if ((str.charAt(i) == 'u' || str.charAt(i) == 'U') && URL_PATTERN.matcher(str.substring(i)).matches()) {
                    sb.append(str.substring(i, i + 4).toLowerCase());
                    i = appendUrlContent(sb, str, i + 4);
                } else {
                    sb.append(Character.toLowerCase(str.charAt(i)));
                    i++;
                }
            }
        }
        return sb.toString();
    }

    private static int appendQuotedString(StringBuilder buffer, String source, int start) {
        int end;
        int end2 = CssUtils.findNextUnescapedChar(source, source.charAt(start), start + 1);
        if (end2 == -1) {
            end = source.length();
            LoggerFactory.getLogger((Class<?>) CssPropertyNormalizer.class).warn(MessageFormatUtil.format(LogMessageConstant.QUOTE_IS_NOT_CLOSED_IN_CSS_EXPRESSION, source));
        } else {
            end = end2 + 1;
        }
        buffer.append(source, start, end);
        return end;
    }

    private static int appendUrlContent(StringBuilder buffer, String source, int start) {
        while (Character.isWhitespace(source.charAt(start)) && start < source.length()) {
            start++;
        }
        Class<CssPropertyNormalizer> cls = CssPropertyNormalizer.class;
        if (start < source.length()) {
            int curr = start;
            if (source.charAt(curr) == '\"' || source.charAt(curr) == '\'') {
                return appendQuotedString(buffer, source, curr);
            }
            int curr2 = CssUtils.findNextUnescapedChar(source, ')', curr);
            if (curr2 == -1) {
                LoggerFactory.getLogger((Class<?>) cls).warn(MessageFormatUtil.format(LogMessageConstant.URL_IS_NOT_CLOSED_IN_CSS_EXPRESSION, source));
                return source.length();
            }
            buffer.append(source.substring(start, curr2).trim());
            buffer.append(')');
            return curr2 + 1;
        }
        LoggerFactory.getLogger((Class<?>) cls).warn(MessageFormatUtil.format(LogMessageConstant.URL_IS_EMPTY_IN_CSS_EXPRESSION, source));
        return source.length();
    }

    private static boolean trimSpaceAfter(char ch) {
        return ch == ',' || ch == '(';
    }

    private static boolean trimSpaceBefore(char ch) {
        return ch == ',' || ch == ')';
    }
}
