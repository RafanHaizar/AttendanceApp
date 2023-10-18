package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.node.INode;

public class CssSeparatorSelectorItem implements ICssSelectorItem {
    private char separator;

    public CssSeparatorSelectorItem(char separator2) {
        this.separator = separator2;
    }

    public int getSpecificity() {
        return 0;
    }

    public boolean matches(INode node) {
        throw new IllegalStateException("Separator item is not supposed to be matched against an element");
    }

    public char getSeparator() {
        return this.separator;
    }

    public String toString() {
        char c = this.separator;
        if (c == ' ') {
            return " ";
        }
        return MessageFormatUtil.format(" {0} ", Character.valueOf(c));
    }
}
