package com.itextpdf.styledxmlparser.css.parse;

import com.itextpdf.styledxmlparser.css.selector.item.CssPagePseudoClassSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.CssPageTypeSelectorItem;
import com.itextpdf.styledxmlparser.css.selector.item.ICssSelectorItem;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CssPageSelectorParser {
    private static final String PAGE_SELECTOR_PATTERN_STR = "(^-?[_a-zA-Z][\\w-]*)|(:(?i)(left|right|first|blank))";
    private static final Pattern selectorPattern = Pattern.compile(PAGE_SELECTOR_PATTERN_STR);

    public static List<ICssSelectorItem> parseSelectorItems(String selectorItemsStr) {
        List<ICssSelectorItem> selectorItems = new ArrayList<>();
        Matcher itemMatcher = selectorPattern.matcher(selectorItemsStr);
        while (itemMatcher.find()) {
            String selectorItem = itemMatcher.group(0);
            if (selectorItem.charAt(0) == ':') {
                selectorItems.add(new CssPagePseudoClassSelectorItem(selectorItem.substring(1).toLowerCase()));
            } else {
                selectorItems.add(new CssPageTypeSelectorItem(selectorItem));
            }
        }
        return selectorItems;
    }
}
