package com.itextpdf.styledxmlparser.css.media;

import java.util.ArrayList;
import java.util.List;

public final class MediaQueryParser {
    private MediaQueryParser() {
    }

    static List<MediaQuery> parseMediaQueries(String mediaQueriesStr) {
        String[] mediaQueryStrs = mediaQueriesStr.split(",");
        List<MediaQuery> mediaQueries = new ArrayList<>();
        for (String mediaQueryStr : mediaQueryStrs) {
            MediaQuery mediaQuery = parseMediaQuery(mediaQueryStr);
            if (mediaQuery != null) {
                mediaQueries.add(mediaQuery);
            }
        }
        return mediaQueries;
    }

    static MediaQuery parseMediaQuery(String mediaQueryStr) {
        List<MediaExpression> mediaExpressions;
        String mediaQueryStr2 = mediaQueryStr.trim().toLowerCase();
        boolean only = false;
        boolean not = false;
        if (mediaQueryStr2.startsWith(MediaRuleConstants.ONLY)) {
            only = true;
            mediaQueryStr2 = mediaQueryStr2.substring(MediaRuleConstants.ONLY.length()).trim();
        } else if (mediaQueryStr2.startsWith("not")) {
            not = true;
            mediaQueryStr2 = mediaQueryStr2.substring("not".length()).trim();
        }
        int indexOfSpace = mediaQueryStr2.indexOf(32);
        String firstWord = indexOfSpace != -1 ? mediaQueryStr2.substring(0, indexOfSpace) : mediaQueryStr2;
        String mediaType = null;
        if (only || not || MediaType.isValidMediaType(firstWord)) {
            mediaType = firstWord;
            mediaExpressions = parseMediaExpressions(mediaQueryStr2.substring(firstWord.length()), true);
        } else {
            mediaExpressions = parseMediaExpressions(mediaQueryStr2, false);
        }
        return new MediaQuery(mediaType, mediaExpressions, only, not);
    }

    private static List<MediaExpression> parseMediaExpressions(String mediaExpressionsStr, boolean shallStartWithAnd) {
        String mediaExpressionsStr2 = mediaExpressionsStr.trim();
        boolean startsWithEnd = mediaExpressionsStr2.startsWith(MediaRuleConstants.AND);
        boolean firstExpression = true;
        String[] mediaExpressionStrs = mediaExpressionsStr2.split(MediaRuleConstants.AND);
        List<MediaExpression> expressions = new ArrayList<>();
        for (String mediaExpressionStr : mediaExpressionStrs) {
            MediaExpression expression = parseMediaExpression(mediaExpressionStr);
            if (expression != null) {
                if (!firstExpression || !shallStartWithAnd || startsWithEnd) {
                    firstExpression = false;
                    expressions.add(expression);
                } else {
                    throw new IllegalStateException("Expected 'and' while parsing media expression");
                }
            }
        }
        return expressions;
    }

    private static MediaExpression parseMediaExpression(String mediaExpressionStr) {
        String mediaFeature;
        String mediaExpressionStr2 = mediaExpressionStr.trim();
        if (!mediaExpressionStr2.startsWith("(") || !mediaExpressionStr2.endsWith(")")) {
            return null;
        }
        String mediaExpressionStr3 = mediaExpressionStr2.substring(1, mediaExpressionStr2.length() - 1);
        if (mediaExpressionStr3.length() == 0) {
            return null;
        }
        int colonPos = mediaExpressionStr3.indexOf(58);
        String value = null;
        if (colonPos == -1) {
            mediaFeature = mediaExpressionStr3;
        } else {
            mediaFeature = mediaExpressionStr3.substring(0, colonPos).trim();
            value = mediaExpressionStr3.substring(colonPos + 1).trim();
        }
        return new MediaExpression(mediaFeature, value);
    }
}
