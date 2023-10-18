package com.itextpdf.styledxmlparser.jsoup.safety;

import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.styledxmlparser.CommonAttributeConstants;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.svg.SvgConstants;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.i18n.ErrorBundle;

public class Whitelist {
    private Map<TagName, Set<AttributeKey>> attributes = new HashMap();
    private Map<TagName, Map<AttributeKey, AttributeValue>> enforcedAttributes = new HashMap();
    private boolean preserveRelativeLinks = false;
    private Map<TagName, Map<AttributeKey, Set<Protocol>>> protocols = new HashMap();
    private Set<TagName> tagNames = new HashSet();

    public static Whitelist none() {
        return new Whitelist();
    }

    public static Whitelist simpleText() {
        return new Whitelist().addTags(SvgConstants.Attributes.PATH_DATA_REL_BEARING, CommonCssConstants.f1611EM, "i", "strong", "u");
    }

    public static Whitelist basic() {
        return new Whitelist().addTags("a", SvgConstants.Attributes.PATH_DATA_REL_BEARING, "blockquote", "br", "cite", "code", "dd", "dl", "dt", CommonCssConstants.f1611EM, "i", "li", "ol", "p", "pre", "q", CommonCssConstants.SMALL, "span", "strike", "strong", "sub", "sup", "u", "ul").addAttributes("a", "href").addAttributes("blockquote", "cite").addAttributes("q", "cite").addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addEnforcedAttribute("a", CommonAttributeConstants.REL, "nofollow");
    }

    public static Whitelist basicWithImages() {
        return basic().addTags("img").addAttributes("img", "align", "alt", "height", "src", "title", "width").addProtocols("img", "src", "http", "https");
    }

    public static Whitelist relaxed() {
        return new Whitelist().addTags("a", SvgConstants.Attributes.PATH_DATA_REL_BEARING, "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "dl", "dt", CommonCssConstants.f1611EM, "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", CommonCssConstants.SMALL, "span", "strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul").addAttributes("a", "href", "title").addAttributes("blockquote", "cite").addAttributes("col", "span", "width").addAttributes("colgroup", "span", "width").addAttributes("img", "align", "alt", "height", "src", "title", "width").addAttributes("ol", "start", PdfConst.Type).addAttributes("q", "cite").addAttributes("table", ErrorBundle.SUMMARY_ENTRY, "width").addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width").addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width").addAttributes("ul", PdfConst.Type).addProtocols("a", "href", "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addProtocols("img", "src", "http", "https").addProtocols("q", "cite", "http", "https");
    }

    public Whitelist addTags(String... tags) {
        Validate.notNull(tags);
        for (String tagName : tags) {
            Validate.notEmpty(tagName);
            this.tagNames.add(TagName.valueOf(tagName));
        }
        return this;
    }

    public Whitelist removeTags(String... tags) {
        Validate.notNull(tags);
        for (String tag : tags) {
            Validate.notEmpty(tag);
            TagName tagName = TagName.valueOf(tag);
            if (this.tagNames.remove(tagName)) {
                this.attributes.remove(tagName);
                this.enforcedAttributes.remove(tagName);
                this.protocols.remove(tagName);
            }
        }
        return this;
    }

    public Whitelist addAttributes(String tag, String... keys) {
        Validate.notEmpty(tag);
        Validate.notNull(keys);
        Validate.isTrue(keys.length > 0, "No attributes supplied.");
        TagName tagName = TagName.valueOf(tag);
        if (!this.tagNames.contains(tagName)) {
            this.tagNames.add(tagName);
        }
        Set<AttributeKey> attributeSet = new HashSet<>();
        for (String key : keys) {
            Validate.notEmpty(key);
            attributeSet.add(AttributeKey.valueOf(key));
        }
        if (this.attributes.containsKey(tagName)) {
            this.attributes.get(tagName).addAll(attributeSet);
        } else {
            this.attributes.put(tagName, attributeSet);
        }
        return this;
    }

    public Whitelist removeAttributes(String tag, String... keys) {
        Validate.notEmpty(tag);
        Validate.notNull(keys);
        Validate.isTrue(keys.length > 0, "No attributes supplied.");
        TagName tagName = TagName.valueOf(tag);
        Set<AttributeKey> attributeSet = new HashSet<>();
        for (String key : keys) {
            Validate.notEmpty(key);
            attributeSet.add(AttributeKey.valueOf(key));
        }
        if (this.tagNames.contains(tagName) && this.attributes.containsKey(tagName)) {
            Set<AttributeKey> currentSet = this.attributes.get(tagName);
            currentSet.removeAll(attributeSet);
            if (currentSet.isEmpty()) {
                this.attributes.remove(tagName);
            }
        }
        if (tag.equals(":all")) {
            for (TagName name : this.attributes.keySet()) {
                Set<AttributeKey> currentSet2 = this.attributes.get(name);
                currentSet2.removeAll(attributeSet);
                if (currentSet2.isEmpty()) {
                    this.attributes.remove(name);
                }
            }
        }
        return this;
    }

    public Whitelist addEnforcedAttribute(String tag, String key, String value) {
        Validate.notEmpty(tag);
        Validate.notEmpty(key);
        Validate.notEmpty(value);
        TagName tagName = TagName.valueOf(tag);
        if (!this.tagNames.contains(tagName)) {
            this.tagNames.add(tagName);
        }
        AttributeKey attrKey = AttributeKey.valueOf(key);
        AttributeValue attrVal = AttributeValue.valueOf(value);
        if (this.enforcedAttributes.containsKey(tagName)) {
            this.enforcedAttributes.get(tagName).put(attrKey, attrVal);
        } else {
            Map<AttributeKey, AttributeValue> attrMap = new HashMap<>();
            attrMap.put(attrKey, attrVal);
            this.enforcedAttributes.put(tagName, attrMap);
        }
        return this;
    }

    public Whitelist removeEnforcedAttribute(String tag, String key) {
        Validate.notEmpty(tag);
        Validate.notEmpty(key);
        TagName tagName = TagName.valueOf(tag);
        if (this.tagNames.contains(tagName) && this.enforcedAttributes.containsKey(tagName)) {
            AttributeKey attrKey = AttributeKey.valueOf(key);
            Map<AttributeKey, AttributeValue> attrMap = this.enforcedAttributes.get(tagName);
            attrMap.remove(attrKey);
            if (attrMap.isEmpty()) {
                this.enforcedAttributes.remove(tagName);
            }
        }
        return this;
    }

    public Whitelist preserveRelativeLinks(boolean preserve) {
        this.preserveRelativeLinks = preserve;
        return this;
    }

    public Whitelist addProtocols(String tag, String key, String... protocols2) {
        Map<AttributeKey, Set<Protocol>> attrMap;
        Set<Protocol> protSet;
        Validate.notEmpty(tag);
        Validate.notEmpty(key);
        Validate.notNull(protocols2);
        TagName tagName = TagName.valueOf(tag);
        AttributeKey attrKey = AttributeKey.valueOf(key);
        if (this.protocols.containsKey(tagName)) {
            attrMap = this.protocols.get(tagName);
        } else {
            attrMap = new HashMap<>();
            this.protocols.put(tagName, attrMap);
        }
        if (attrMap.containsKey(attrKey)) {
            protSet = attrMap.get(attrKey);
        } else {
            protSet = new HashSet<>();
            attrMap.put(attrKey, protSet);
        }
        for (String protocol : protocols2) {
            Validate.notEmpty(protocol);
            protSet.add(Protocol.valueOf(protocol));
        }
        return this;
    }

    public Whitelist removeProtocols(String tag, String key, String... protocols2) {
        Validate.notEmpty(tag);
        Validate.notEmpty(key);
        Validate.notNull(protocols2);
        TagName tagName = TagName.valueOf(tag);
        AttributeKey attrKey = AttributeKey.valueOf(key);
        if (this.protocols.containsKey(tagName)) {
            Map<AttributeKey, Set<Protocol>> attrMap = this.protocols.get(tagName);
            if (attrMap.containsKey(attrKey)) {
                Set<Protocol> protSet = attrMap.get(attrKey);
                for (String protocol : protocols2) {
                    Validate.notEmpty(protocol);
                    protSet.remove(Protocol.valueOf(protocol));
                }
                if (protSet.isEmpty()) {
                    attrMap.remove(attrKey);
                    if (attrMap.isEmpty()) {
                        this.protocols.remove(tagName);
                    }
                }
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean isSafeTag(String tag) {
        return this.tagNames.contains(TagName.valueOf(tag));
    }

    /* access modifiers changed from: protected */
    public boolean isSafeAttribute(String tagName, Element el, Attribute attr) {
        TagName tag = TagName.valueOf(tagName);
        AttributeKey key = AttributeKey.valueOf(attr.getKey());
        if (!this.attributes.containsKey(tag) || !this.attributes.get(tag).contains(key)) {
            if (tagName.equals(":all") || !isSafeAttribute(":all", el, attr)) {
                return false;
            }
            return true;
        } else if (!this.protocols.containsKey(tag)) {
            return true;
        } else {
            Map<AttributeKey, Set<Protocol>> attrProts = this.protocols.get(tag);
            if (!attrProts.containsKey(key) || testValidProtocol(el, attr, attrProts.get(key))) {
                return true;
            }
            return false;
        }
    }

    private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols2) {
        String value = el.absUrl(attr.getKey());
        if (value.length() == 0) {
            value = attr.getValue();
        }
        if (!this.preserveRelativeLinks) {
            attr.setValue(value);
        }
        for (Protocol protocol : protocols2) {
            String prot = protocol.toString();
            if (!prot.equals("#")) {
                if (value.toLowerCase().startsWith(prot + ":")) {
                    return true;
                }
            } else if (isValidAnchor(value)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidAnchor(String value) {
        return value.startsWith("#") && !value.matches(".*\\s.*");
    }

    /* access modifiers changed from: package-private */
    public Attributes getEnforcedAttributes(String tagName) {
        Attributes attrs = new Attributes();
        TagName tag = TagName.valueOf(tagName);
        if (this.enforcedAttributes.containsKey(tag)) {
            for (Map.Entry<AttributeKey, AttributeValue> entry : this.enforcedAttributes.get(tag).entrySet()) {
                attrs.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return attrs;
    }

    static class TagName extends TypedValue {
        TagName(String value) {
            super(value);
        }

        static TagName valueOf(String value) {
            return new TagName(value);
        }
    }

    static class AttributeKey extends TypedValue {
        AttributeKey(String value) {
            super(value);
        }

        static AttributeKey valueOf(String value) {
            return new AttributeKey(value);
        }
    }

    static class AttributeValue extends TypedValue {
        AttributeValue(String value) {
            super(value);
        }

        static AttributeValue valueOf(String value) {
            return new AttributeValue(value);
        }
    }

    static class Protocol extends TypedValue {
        Protocol(String value) {
            super(value);
        }

        static Protocol valueOf(String value) {
            return new Protocol(value);
        }
    }

    static abstract class TypedValue {
        private String value;

        TypedValue(String value2) {
            Validate.notNull(value2);
            this.value = value2;
        }

        public int hashCode() {
            int i = 1 * 31;
            String str = this.value;
            return i + (str == null ? 0 : str.hashCode());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TypedValue other = (TypedValue) obj;
            String str = this.value;
            if (str == null) {
                if (other.value != null) {
                    return false;
                }
            } else if (!str.equals(other.value)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return this.value;
        }
    }
}
