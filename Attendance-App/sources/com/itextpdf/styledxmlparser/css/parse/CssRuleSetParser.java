package com.itextpdf.styledxmlparser.css.parse;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.CssRuleSet;
import com.itextpdf.styledxmlparser.css.selector.CssSelector;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CssRuleSetParser {
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) CssRuleSetParser.class);

    private CssRuleSetParser() {
    }

    public static List<CssDeclaration> parsePropertyDeclarations(String propertiesStr) {
        String[] propertySplit;
        List<CssDeclaration> declarations = new ArrayList<>();
        int openedCommentPos = propertiesStr.indexOf("/*", 0);
        if (openedCommentPos != -1) {
            declarations.addAll(parsePropertyDeclarations(propertiesStr.substring(0, openedCommentPos)));
            int closedCommentPos = propertiesStr.indexOf("*/", openedCommentPos);
            if (closedCommentPos != -1) {
                declarations.addAll(parsePropertyDeclarations(propertiesStr.substring(closedCommentPos + 2, propertiesStr.length())));
            }
        } else {
            int pos = getSemicolonPosition(propertiesStr, 0);
            while (pos != -1) {
                String[] propertySplit2 = splitCssProperty(propertiesStr.substring(0, pos));
                if (propertySplit2 != null) {
                    declarations.add(new CssDeclaration(propertySplit2[0], propertySplit2[1]));
                }
                propertiesStr = propertiesStr.substring(pos + 1);
                pos = getSemicolonPosition(propertiesStr, 0);
            }
            if (!propertiesStr.replaceAll("[\\n\\r\\t ]", "").isEmpty() && (propertySplit = splitCssProperty(propertiesStr)) != null) {
                declarations.add(new CssDeclaration(propertySplit[0], propertySplit[1]));
            }
            return declarations;
        }
        return declarations;
    }

    public static List<CssRuleSet> parseRuleSet(String selectorStr, String propertiesStr) {
        List<CssDeclaration> declarations = parsePropertyDeclarations(propertiesStr);
        List<CssRuleSet> ruleSets = new ArrayList<>();
        String[] selectors = selectorStr.split(",");
        for (int i = 0; i < selectors.length; i++) {
            selectors[i] = CssUtils.removeDoubleSpacesAndTrim(selectors[i]);
            if (selectors[i].length() == 0) {
                return ruleSets;
            }
        }
        int i2 = selectors.length;
        int i3 = 0;
        while (i3 < i2) {
            String currentSelectorStr = selectors[i3];
            try {
                ruleSets.add(new CssRuleSet(new CssSelector(currentSelectorStr), declarations));
                i3++;
            } catch (Exception exc) {
                logger.error(MessageFormatUtil.format(LogMessageConstant.ERROR_PARSING_CSS_SELECTOR, currentSelectorStr), (Throwable) exc);
                declarations.clear();
                return ruleSets;
            }
        }
        return ruleSets;
    }

    private static String[] splitCssProperty(String property) {
        if (property.trim().isEmpty()) {
            return null;
        }
        String[] result = new String[2];
        int position = property.indexOf(":");
        if (position < 0) {
            logger.error(MessageFormatUtil.format(LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, property.trim()));
            return null;
        }
        result[0] = property.substring(0, position);
        result[1] = property.substring(position + 1);
        return result;
    }

    private static int getSemicolonPosition(String propertiesStr, int fromIndex) {
        int semiColonPos = propertiesStr.indexOf(";", fromIndex);
        int closedBracketPos = propertiesStr.indexOf(")", semiColonPos + 1);
        int openedBracketPos = propertiesStr.indexOf("(", fromIndex);
        if (semiColonPos != -1 && openedBracketPos < semiColonPos && closedBracketPos > 0) {
            int nextOpenedBracketPos = openedBracketPos;
            do {
                openedBracketPos = nextOpenedBracketPos;
                nextOpenedBracketPos = propertiesStr.indexOf("(", openedBracketPos + 1);
                if (nextOpenedBracketPos >= closedBracketPos) {
                    break;
                }
            } while (nextOpenedBracketPos > 0);
        }
        if (semiColonPos == -1 || semiColonPos <= openedBracketPos || semiColonPos >= closedBracketPos) {
            return semiColonPos;
        }
        return getSemicolonPosition(propertiesStr, closedBracketPos + 1);
    }
}
