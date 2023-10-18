package com.itextpdf.styledxmlparser.jsoup.select;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.jsoup.PortUtil;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Comment;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.DocumentType;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
import com.itextpdf.styledxmlparser.jsoup.nodes.XmlDeclaration;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.slf4j.Marker;

public abstract class Evaluator {
    public abstract boolean matches(Element element, Element element2);

    protected Evaluator() {
    }

    public static final class Tag extends Evaluator {
        private String tagName;

        public Tag(String tagName2) {
            this.tagName = tagName2;
        }

        public boolean matches(Element root, Element element) {
            return element.tagName().equals(this.tagName);
        }

        public String toString() {
            return MessageFormatUtil.format("{0}", this.tagName);
        }
    }

    /* renamed from: com.itextpdf.styledxmlparser.jsoup.select.Evaluator$Id */
    public static final class C1589Id extends Evaluator {

        /* renamed from: id */
        private String f1630id;

        public C1589Id(String id) {
            this.f1630id = id;
        }

        public boolean matches(Element root, Element element) {
            return this.f1630id.equals(element.mo31113id());
        }

        public String toString() {
            return MessageFormatUtil.format("#{0}", this.f1630id);
        }
    }

    public static final class Class extends Evaluator {
        private String className;

        public Class(String className2) {
            this.className = className2;
        }

        public boolean matches(Element root, Element element) {
            return element.hasClass(this.className);
        }

        public String toString() {
            return MessageFormatUtil.format(".{0}", this.className);
        }
    }

    public static final class Attribute extends Evaluator {
        private String key;

        public Attribute(String key2) {
            this.key = key2;
        }

        public boolean matches(Element root, Element element) {
            return element.hasAttr(this.key);
        }

        public String toString() {
            return MessageFormatUtil.format("[{0}]", this.key);
        }
    }

    public static final class AttributeStarting extends Evaluator {
        private String keyPrefix;

        public AttributeStarting(String keyPrefix2) {
            this.keyPrefix = keyPrefix2;
        }

        public boolean matches(Element root, Element element) {
            for (com.itextpdf.styledxmlparser.jsoup.nodes.Attribute attribute : element.attributes().asList()) {
                if (attribute.getKey().startsWith(this.keyPrefix)) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return MessageFormatUtil.format("[^{0}]", this.keyPrefix);
        }
    }

    public static final class AttributeWithValue extends AttributeKeyPair {
        public AttributeWithValue(String key, String value) {
            super(key, value);
        }

        public boolean matches(Element root, Element element) {
            return element.hasAttr(this.key) && this.value.equalsIgnoreCase(element.attr(this.key).trim());
        }

        public String toString() {
            return MessageFormatUtil.format("[{0}={1}]", this.key, this.value);
        }
    }

    public static final class AttributeWithValueNot extends AttributeKeyPair {
        public AttributeWithValueNot(String key, String value) {
            super(key, value);
        }

        public boolean matches(Element root, Element element) {
            return !this.value.equalsIgnoreCase(element.attr(this.key));
        }

        public String toString() {
            return MessageFormatUtil.format("[{0}!={1}]", this.key, this.value);
        }
    }

    public static final class AttributeWithValueStarting extends AttributeKeyPair {
        public AttributeWithValueStarting(String key, String value) {
            super(key, value);
        }

        public boolean matches(Element root, Element element) {
            return element.hasAttr(this.key) && element.attr(this.key).toLowerCase().startsWith(this.value);
        }

        public String toString() {
            return MessageFormatUtil.format("[{0}^={1}]", this.key, this.value);
        }
    }

    public static final class AttributeWithValueEnding extends AttributeKeyPair {
        public AttributeWithValueEnding(String key, String value) {
            super(key, value);
        }

        public boolean matches(Element root, Element element) {
            return element.hasAttr(this.key) && element.attr(this.key).toLowerCase().endsWith(this.value);
        }

        public String toString() {
            return MessageFormatUtil.format("[{0}$={1}]", this.key, this.value);
        }
    }

    public static final class AttributeWithValueContaining extends AttributeKeyPair {
        public AttributeWithValueContaining(String key, String value) {
            super(key, value);
        }

        public boolean matches(Element root, Element element) {
            return element.hasAttr(this.key) && element.attr(this.key).toLowerCase().contains(this.value);
        }

        public String toString() {
            return MessageFormatUtil.format("[{0}*={1}]", this.key, this.value);
        }
    }

    public static final class AttributeWithValueMatching extends Evaluator {
        String key;
        Pattern pattern;

        public AttributeWithValueMatching(String key2, Pattern pattern2) {
            this.key = key2.trim().toLowerCase();
            this.pattern = pattern2;
        }

        public boolean matches(Element root, Element element) {
            return element.hasAttr(this.key) && PortUtil.hasMatch(this.pattern, element.attr(this.key));
        }

        public String toString() {
            return MessageFormatUtil.format("[{0}~={1}]", this.key, this.pattern.toString());
        }
    }

    public static abstract class AttributeKeyPair extends Evaluator {
        String key;
        String value;

        public AttributeKeyPair(String key2, String value2) {
            Validate.notEmpty(key2);
            Validate.notEmpty(value2);
            this.key = key2.trim().toLowerCase();
            if ((value2.startsWith("\"") && value2.endsWith("\"")) || (value2.startsWith("'") && value2.endsWith("'"))) {
                value2 = value2.substring(1, value2.length() - 1);
            }
            this.value = value2.trim().toLowerCase();
        }
    }

    public static final class AllElements extends Evaluator {
        public boolean matches(Element root, Element element) {
            return true;
        }

        public String toString() {
            return Marker.ANY_MARKER;
        }
    }

    public static final class IndexLessThan extends IndexEvaluator {
        public IndexLessThan(int index) {
            super(index);
        }

        public boolean matches(Element root, Element element) {
            return element.elementSiblingIndex() < this.index;
        }

        public String toString() {
            return MessageFormatUtil.format(":lt({0})", Integer.valueOf(this.index));
        }
    }

    public static final class IndexGreaterThan extends IndexEvaluator {
        public IndexGreaterThan(int index) {
            super(index);
        }

        public boolean matches(Element root, Element element) {
            return element.elementSiblingIndex() > this.index;
        }

        public String toString() {
            return MessageFormatUtil.format(":gt({0})", Integer.valueOf(this.index));
        }
    }

    public static final class IndexEquals extends IndexEvaluator {
        public IndexEquals(int index) {
            super(index);
        }

        public boolean matches(Element root, Element element) {
            return element.elementSiblingIndex() == this.index;
        }

        public String toString() {
            return MessageFormatUtil.format(":eq({0})", Integer.valueOf(this.index));
        }
    }

    public static final class IsLastChild extends Evaluator {
        public boolean matches(Element root, Element element) {
            Element p = (Element) element.parent();
            return p != null && !(p instanceof Document) && element.elementSiblingIndex() == p.children().size() - 1;
        }

        public String toString() {
            return ":last-child";
        }
    }

    public static final class IsFirstOfType extends IsNthOfType {
        public IsFirstOfType() {
            super(0, 1);
        }

        public String toString() {
            return ":first-of-type";
        }
    }

    public static final class IsLastOfType extends IsNthLastOfType {
        public IsLastOfType() {
            super(0, 1);
        }

        public String toString() {
            return ":last-of-type";
        }
    }

    public static abstract class CssNthEvaluator extends Evaluator {

        /* renamed from: a */
        protected final int f1628a;

        /* renamed from: b */
        protected final int f1629b;

        /* access modifiers changed from: protected */
        public abstract int calculatePosition(Element element, Element element2);

        /* access modifiers changed from: protected */
        public abstract String getPseudoClass();

        public CssNthEvaluator(int a, int b) {
            this.f1628a = a;
            this.f1629b = b;
        }

        public CssNthEvaluator(int b) {
            this(0, b);
        }

        public boolean matches(Element root, Element element) {
            Element p = (Element) element.parent();
            if (p == null || (p instanceof Document)) {
                return false;
            }
            int pos = calculatePosition(root, element);
            int i = this.f1628a;
            if (i != 0) {
                int i2 = this.f1629b;
                return (pos - i2) * i >= 0 && (pos - i2) % i == 0;
            } else if (pos == this.f1629b) {
                return true;
            } else {
                return false;
            }
        }

        public String toString() {
            if (this.f1628a == 0) {
                return MessageFormatUtil.format(":{0}({1})", getPseudoClass(), Integer.valueOf(this.f1629b));
            } else if (this.f1629b == 0) {
                return MessageFormatUtil.format(":{0}({1}n)", getPseudoClass(), Integer.valueOf(this.f1628a));
            } else {
                return MessageFormatUtil.format(":{0}({1}n{2,number,+#;-#})", getPseudoClass(), Integer.valueOf(this.f1628a), Integer.valueOf(this.f1629b));
            }
        }
    }

    public static final class IsNthChild extends CssNthEvaluator {
        public IsNthChild(int a, int b) {
            super(a, b);
        }

        /* access modifiers changed from: protected */
        public int calculatePosition(Element root, Element element) {
            return element.elementSiblingIndex() + 1;
        }

        /* access modifiers changed from: protected */
        public String getPseudoClass() {
            return CommonCssConstants.NTH_CHILD;
        }
    }

    public static final class IsNthLastChild extends CssNthEvaluator {
        public IsNthLastChild(int a, int b) {
            super(a, b);
        }

        /* access modifiers changed from: protected */
        public int calculatePosition(Element root, Element element) {
            return ((Element) element.parent()).children().size() - element.elementSiblingIndex();
        }

        /* access modifiers changed from: protected */
        public String getPseudoClass() {
            return CommonCssConstants.NTH_LAST_CHILD;
        }
    }

    public static class IsNthOfType extends CssNthEvaluator {
        public IsNthOfType(int a, int b) {
            super(a, b);
        }

        /* access modifiers changed from: protected */
        public int calculatePosition(Element root, Element element) {
            int pos = 0;
            Iterator it = ((Element) element.parent()).children().iterator();
            while (it.hasNext()) {
                Element el = (Element) it.next();
                if (el.tag().equals(element.tag())) {
                    pos++;
                    continue;
                }
                if (el == element) {
                    break;
                }
            }
            return pos;
        }

        /* access modifiers changed from: protected */
        public String getPseudoClass() {
            return CommonCssConstants.NTH_OF_TYPE;
        }
    }

    public static class IsNthLastOfType extends CssNthEvaluator {
        public IsNthLastOfType(int a, int b) {
            super(a, b);
        }

        /* access modifiers changed from: protected */
        public int calculatePosition(Element root, Element element) {
            int pos = 0;
            Elements family = ((Element) element.parent()).children();
            for (int i = element.elementSiblingIndex(); i < family.size(); i++) {
                if (((Element) family.get(i)).tag().equals(element.tag())) {
                    pos++;
                }
            }
            return pos;
        }

        /* access modifiers changed from: protected */
        public String getPseudoClass() {
            return CommonCssConstants.NTH_LAST_OF_TYPE;
        }
    }

    public static final class IsFirstChild extends Evaluator {
        public boolean matches(Element root, Element element) {
            Element p = (Element) element.parent();
            return p != null && !(p instanceof Document) && element.elementSiblingIndex() == 0;
        }

        public String toString() {
            return ":first-child";
        }
    }

    public static final class IsRoot extends Evaluator {
        public boolean matches(Element root, Element element) {
            if (element == (root instanceof Document ? root.child(0) : root)) {
                return true;
            }
            return false;
        }

        public String toString() {
            return ":root";
        }
    }

    public static final class IsOnlyChild extends Evaluator {
        public boolean matches(Element root, Element element) {
            Element p = (Element) element.parent();
            return p != null && !(p instanceof Document) && element.siblingElements().size() == 0;
        }

        public String toString() {
            return ":only-child";
        }
    }

    public static final class IsOnlyOfType extends Evaluator {
        public boolean matches(Element root, Element element) {
            Element p = (Element) element.parent();
            if (p == null || (p instanceof Document)) {
                return false;
            }
            int pos = 0;
            Iterator it = p.children().iterator();
            while (it.hasNext()) {
                if (((Element) it.next()).tag().equals(element.tag())) {
                    pos++;
                }
            }
            return pos == 1;
        }

        public String toString() {
            return ":only-of-type";
        }
    }

    public static final class IsEmpty extends Evaluator {
        public boolean matches(Element root, Element element) {
            for (Node n : element.childNodes()) {
                if (!(n instanceof Comment) && !(n instanceof XmlDeclaration) && !(n instanceof DocumentType)) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            return ":empty";
        }
    }

    public static abstract class IndexEvaluator extends Evaluator {
        int index;

        public IndexEvaluator(int index2) {
            this.index = index2;
        }
    }

    public static final class ContainsText extends Evaluator {
        private String searchText;

        public ContainsText(String searchText2) {
            this.searchText = searchText2.toLowerCase();
        }

        public boolean matches(Element root, Element element) {
            return element.text().toLowerCase().contains(this.searchText);
        }

        public String toString() {
            return MessageFormatUtil.format(":contains({0}", this.searchText);
        }
    }

    public static final class ContainsOwnText extends Evaluator {
        private String searchText;

        public ContainsOwnText(String searchText2) {
            this.searchText = searchText2.toLowerCase();
        }

        public boolean matches(Element root, Element element) {
            return element.ownText().toLowerCase().contains(this.searchText);
        }

        public String toString() {
            return MessageFormatUtil.format(":containsOwn({0}", this.searchText);
        }
    }

    public static final class Matches extends Evaluator {
        private Pattern pattern;

        public Matches(Pattern pattern2) {
            this.pattern = pattern2;
        }

        public boolean matches(Element root, Element element) {
            return PortUtil.hasMatch(this.pattern, element.text());
        }

        public String toString() {
            return MessageFormatUtil.format(":matches({0}", this.pattern);
        }
    }

    public static final class MatchesOwn extends Evaluator {
        private Pattern pattern;

        public MatchesOwn(Pattern pattern2) {
            this.pattern = pattern2;
        }

        public boolean matches(Element root, Element element) {
            return PortUtil.hasMatch(this.pattern, element.ownText());
        }

        public String toString() {
            return MessageFormatUtil.format(":matchesOwn({0}", this.pattern);
        }
    }
}
