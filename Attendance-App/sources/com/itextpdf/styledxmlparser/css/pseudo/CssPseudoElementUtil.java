package com.itextpdf.styledxmlparser.css.pseudo;

import com.itextpdf.styledxmlparser.node.IElementNode;

public class CssPseudoElementUtil {
    private static final String TAG_NAME_PREFIX = "pseudo-element::";

    public static String createPseudoElementTagName(String pseudoElementName) {
        return TAG_NAME_PREFIX + pseudoElementName;
    }

    public static boolean hasBeforeAfterElements(IElementNode node) {
        if (node == null || (node instanceof CssPseudoElementNode) || node.name().startsWith(TAG_NAME_PREFIX)) {
            return false;
        }
        return true;
    }
}
