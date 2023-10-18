package com.itextpdf.svg.utils;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.util.WhiteSpaceUtil;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.renderers.impl.ISvgTextNodeRenderer;
import com.itextpdf.svg.renderers.impl.TextLeafSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.TextSvgBranchRenderer;

public final class SvgTextUtil {
    private SvgTextUtil() {
    }

    public static String trimLeadingWhitespace(String toTrim) {
        if (toTrim == null) {
            return "";
        }
        int current = 0;
        int end = toTrim.length();
        while (current < end) {
            char currentChar = toTrim.charAt(current);
            if (!Character.isWhitespace(currentChar) || currentChar == 10 || currentChar == 13) {
                break;
            }
            current++;
        }
        return toTrim.substring(current);
    }

    public static String trimTrailingWhitespace(String toTrim) {
        if (toTrim == null) {
            return "";
        }
        int end = toTrim.length();
        if (end <= 0) {
            return toTrim;
        }
        int current = end - 1;
        while (current >= 0) {
            char currentChar = toTrim.charAt(current);
            if (!Character.isWhitespace(currentChar) || currentChar == 10 || currentChar == 13) {
                break;
            }
            current--;
        }
        if (current < 0) {
            return "";
        }
        return toTrim.substring(0, current + 1);
    }

    public static void processWhiteSpace(TextSvgBranchRenderer root, boolean isLeadingElement) {
        String toProcess;
        boolean performLeadingTrim = isLeadingElement;
        for (ISvgTextNodeRenderer child : root.getChildren()) {
            if (child instanceof TextSvgBranchRenderer) {
                processWhiteSpace((TextSvgBranchRenderer) child, child.containsAbsolutePositionChange());
                ((TextSvgBranchRenderer) child).markWhiteSpaceProcessed();
            }
            if (child instanceof TextLeafSvgNodeRenderer) {
                TextLeafSvgNodeRenderer leafRend = (TextLeafSvgNodeRenderer) child;
                String toProcess2 = WhiteSpaceUtil.collapseConsecutiveSpaces(leafRend.getAttribute(SvgConstants.Attributes.TEXT_CONTENT).replaceAll("\\s+", " "));
                if (performLeadingTrim) {
                    toProcess = trimTrailingWhitespace(trimLeadingWhitespace(toProcess2));
                    performLeadingTrim = false;
                } else {
                    toProcess = trimTrailingWhitespace(toProcess2);
                }
                leafRend.setAttribute(SvgConstants.Attributes.TEXT_CONTENT, toProcess);
            }
        }
    }

    public static boolean isOnlyWhiteSpace(String s) {
        return "".equals(trimTrailingWhitespace(trimLeadingWhitespace(s.replaceAll("\\s+", " "))));
    }

    public static float resolveFontSize(ISvgTextNodeRenderer renderer, float parentFontSize) {
        float fontSize = Float.NaN;
        String elementFontSize = renderer.getAttribute("font-size");
        if (elementFontSize != null && !elementFontSize.isEmpty()) {
            fontSize = (CssUtils.isRelativeValue(elementFontSize) || CommonCssConstants.LARGER.equals(elementFontSize) || CommonCssConstants.SMALLER.equals(elementFontSize)) ? CssUtils.parseRelativeFontSize(elementFontSize, parentFontSize) : CssUtils.parseAbsoluteFontSize(elementFontSize, CommonCssConstants.f1617PX);
        }
        if (Float.isNaN(fontSize) || fontSize < 0.0f) {
            return parentFontSize;
        }
        return fontSize;
    }

    public static String filterReferenceValue(String name) {
        return name.replace("#", "").replace("url(", "").replace(")", "").trim();
    }
}
