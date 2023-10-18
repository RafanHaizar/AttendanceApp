package com.itextpdf.styledxmlparser.css.selector.item;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.regex.Pattern;

public class CssAttributeSelectorItem implements ICssSelectorItem {
    private char matchSymbol = 0;
    private String property;
    private String value = null;

    public CssAttributeSelectorItem(String attrSelector) {
        int indexOfEqual = attrSelector.indexOf(61);
        if (indexOfEqual == -1) {
            this.property = attrSelector.substring(1, attrSelector.length() - 1);
            return;
        }
        if (attrSelector.charAt(indexOfEqual + 1) == '\"' || attrSelector.charAt(indexOfEqual + 1) == '\'') {
            this.value = attrSelector.substring(indexOfEqual + 2, attrSelector.length() - 2);
        } else {
            this.value = attrSelector.substring(indexOfEqual + 1, attrSelector.length() - 1);
        }
        char charAt = attrSelector.charAt(indexOfEqual - 1);
        this.matchSymbol = charAt;
        if ("~^$*|".indexOf(charAt) == -1) {
            this.matchSymbol = 0;
            this.property = attrSelector.substring(1, indexOfEqual);
            return;
        }
        this.property = attrSelector.substring(1, indexOfEqual - 1);
    }

    public int getSpecificity() {
        return 1024;
    }

    public boolean matches(INode node) {
        String attributeValue;
        if (!(node instanceof IElementNode) || (node instanceof ICustomElementNode) || (node instanceof IDocumentNode) || (attributeValue = ((IElementNode) node).getAttribute(this.property)) == null) {
            return false;
        }
        String str = this.value;
        if (str == null) {
            return true;
        }
        switch (this.matchSymbol) {
            case 0:
                return str.equals(attributeValue);
            case '$':
                if (str.length() <= 0 || !attributeValue.endsWith(this.value)) {
                    return false;
                }
                return true;
            case '*':
                if (str.length() <= 0 || !attributeValue.contains(this.value)) {
                    return false;
                }
                return true;
            case '^':
                if (str.length() <= 0 || !attributeValue.startsWith(this.value)) {
                    return false;
                }
                return true;
            case '|':
                if (str.length() <= 0 || !attributeValue.startsWith(this.value)) {
                    return false;
                }
                if (attributeValue.length() == this.value.length() || attributeValue.charAt(this.value.length()) == '-') {
                    return true;
                }
                return false;
            case '~':
                return Pattern.compile(MessageFormatUtil.format("(^{0}\\s+)|(\\s+{1}\\s+)|(\\s+{2}$)", str, str, str)).matcher(attributeValue).matches();
            default:
                return false;
        }
    }

    public String toString() {
        if (this.value == null) {
            return MessageFormatUtil.format("[{0}]", this.property);
        }
        Object[] objArr = new Object[3];
        objArr[0] = this.property;
        char c = this.matchSymbol;
        objArr[1] = c == 0 ? "" : String.valueOf(c);
        objArr[2] = this.value;
        return MessageFormatUtil.format("[{0}{1}=\"{2}\"]", objArr);
    }
}
