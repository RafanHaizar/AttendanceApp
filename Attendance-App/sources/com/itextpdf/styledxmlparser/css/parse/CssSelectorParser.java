package com.itextpdf.styledxmlparser.css.parse;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.selector.item.CssAttributeSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.CssClassSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.CssIdSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.CssPseudoClassSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.CssPseudoElementSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.CssSeparatorSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.CssTagSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public final class CssSelectorParser {
    private static final String SELECTOR_PATTERN_STR = "(\\*)|([_a-zA-Z][\\w-]*)|(\\.[_a-zA-Z][\\w-]*)|(#[_a-z][\\w-]*)|(\\[[_a-zA-Z][\\w-]*(([~^$*|])?=((\"[^\"]+\")|([^\"]+)|('[^']+')|(\"\")|('')))?\\])|(::?[a-zA-Z-]*)|( )|(\\+)|(>)|(~)";
    private static final Set<String> legacyPseudoElements;
    private static final Pattern selectorPattern = Pattern.compile(SELECTOR_PATTERN_STR);

    static {
        HashSet hashSet = new HashSet();
        legacyPseudoElements = hashSet;
        hashSet.add("first-line");
        hashSet.add("first-letter");
        hashSet.add("before");
        hashSet.add("after");
    }

    private CssSelectorParser() {
    }

    public static List<ICssSelectorItem> parseSelectorItems(String selector) {
        List<ICssSelectorItem> selectorItems = new ArrayList<>();
        CssSelectorParserMatch match = new CssSelectorParserMatch(selector, selectorPattern);
        boolean tagSelectorDescription = false;
        while (match.success()) {
            String selectorItem = match.getValue();
            char firstChar = selectorItem.charAt(0);
            switch (firstChar) {
                case ' ':
                case '+':
                case '>':
                case '~':
                    match.next();
                    if (selectorItems.size() != 0) {
                        ICssSelectorItem lastItem = selectorItems.get(selectorItems.size() - 1);
                        CssSeparatorSelectorItem curItem = new CssSeparatorSelectorItem(firstChar);
                        if (!(lastItem instanceof CssSeparatorSelectorItem)) {
                            selectorItems.add(curItem);
                            tagSelectorDescription = false;
                            break;
                        } else if (curItem.getSeparator() == ' ') {
                            continue;
                        } else if (((CssSeparatorSelectorItem) lastItem).getSeparator() == ' ') {
                            selectorItems.set(selectorItems.size() - 1, curItem);
                            break;
                        } else {
                            throw new IllegalArgumentException(MessageFormatUtil.format("Invalid selector description. Two consequent characters occurred: {0}, {1}", Character.valueOf(((CssSeparatorSelectorItem) lastItem).getSeparator()), Character.valueOf(curItem.getSeparator())));
                        }
                    } else {
                        throw new IllegalArgumentException(MessageFormatUtil.format("Invalid token detected in the start of the selector string: {0}", Character.valueOf(firstChar)));
                    }
                case '#':
                    match.next();
                    selectorItems.add(new CssIdSelectorItem(selectorItem.substring(1)));
                    break;
                case '.':
                    match.next();
                    selectorItems.add(new CssClassSelectorItem(selectorItem.substring(1)));
                    break;
                case ':':
                    appendPseudoSelector(selectorItems, selectorItem, match);
                    break;
                case '[':
                    match.next();
                    selectorItems.add(new CssAttributeSelectorItem(selectorItem));
                    break;
                default:
                    match.next();
                    if (!tagSelectorDescription) {
                        tagSelectorDescription = true;
                        selectorItems.add(new CssTagSelectorItem(selectorItem));
                        break;
                    } else {
                        throw new IllegalStateException("Invalid selector string");
                    }
            }
        }
        if (selectorItems.size() != 0) {
            return selectorItems;
        }
        throw new IllegalArgumentException("Selector declaration is invalid");
    }

    private static void appendPseudoSelector(List<ICssSelectorItem> selectorItems, String pseudoSelector, CssSelectorParserMatch match) {
        String pseudoSelector2 = pseudoSelector.toLowerCase();
        int start = match.getIndex() + pseudoSelector2.length();
        String source = match.getSource();
        if (start >= source.length() || source.charAt(start) != '(') {
            match.next();
        } else {
            int bracketDepth = 1;
            int curr = start + 1;
            while (bracketDepth > 0 && curr < source.length()) {
                if (source.charAt(curr) == '(') {
                    bracketDepth++;
                } else if (source.charAt(curr) == ')') {
                    bracketDepth--;
                } else if (source.charAt(curr) == '\"' || source.charAt(curr) == '\'') {
                    curr = CssUtils.findNextUnescapedChar(source, source.charAt(curr), curr + 1);
                }
                curr++;
            }
            if (bracketDepth == 0) {
                match.next(curr);
                pseudoSelector2 = pseudoSelector2 + source.substring(start, curr);
            } else {
                match.next();
            }
        }
        if (pseudoSelector2.startsWith("::")) {
            selectorItems.add(new CssPseudoElementSelectorItem(pseudoSelector2.substring(2)));
        } else if (!pseudoSelector2.startsWith(":") || !legacyPseudoElements.contains(pseudoSelector2.substring(1))) {
            ICssSelectorItem pseudoClassSelectorItem = CssPseudoClassSelectorItem.create(pseudoSelector2.substring(1));
            if (pseudoClassSelectorItem != null) {
                selectorItems.add(pseudoClassSelectorItem);
            } else {
                throw new IllegalArgumentException(MessageFormatUtil.format(LogMessageConstant.UNSUPPORTED_PSEUDO_CSS_SELECTOR, pseudoSelector2));
            }
        } else {
            selectorItems.add(new CssPseudoElementSelectorItem(pseudoSelector2.substring(1)));
        }
    }
}
