package com.itextpdf.styledxmlparser.jsoup.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.svg.SvgConstants;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.i18n.ErrorBundle;
import org.bouncycastle.jcajce.util.AnnotatedPrivateKey;

public class Tag {
    private static final String[] blockTags;
    private static final String[] emptyTags = {"meta", "link", "base", TypedValues.AttributesType.S_FRAME, "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", PdfConst.Source, "track"};
    private static final String[] formListedTags = {"button", "fieldset", "input", "keygen", "object", "output", "select", "textarea"};
    private static final String[] formSubmitTags = {"input", "keygen", "object", "select", "textarea"};
    private static final String[] formatAsInlineTags = {"title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", "li", "th", "td", SvgConstants.Tags.SCRIPT, "style", "ins", "del", SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S};
    private static final String[] inlineTags = {"object", "base", "font", "tt", "i", SvgConstants.Attributes.PATH_DATA_REL_BEARING, "u", "big", CommonCssConstants.SMALL, CommonCssConstants.f1611EM, "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", "time", "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q", "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", AnnotatedPrivateKey.LABEL, "button", "optgroup", "option", "legend", "datalist", "keygen", "output", "progress", "meter", "area", "param", PdfConst.Source, "track", ErrorBundle.SUMMARY_ENTRY, "command", "device", "area", "basefont", "bgsound", "menuitem", "param", PdfConst.Source, "track", "data", "bdi"};
    private static final String[] preserveWhitespaceTags = {"pre", "plaintext", "title", "textarea"};
    private static final Map<String, Tag> tags = new HashMap();
    private boolean canContainBlock = true;
    private boolean canContainInline = true;
    private boolean empty = false;
    private boolean formList = false;
    private boolean formSubmit = false;
    private boolean formatAsBlock = true;
    private boolean isBlock = true;
    private boolean preserveWhitespace = false;
    private boolean selfClosing = false;
    private String tagName;

    static {
        String[] strArr = {"html", XfdfConstants.HEAD, "body", "frameset", SvgConstants.Tags.SCRIPT, "noscript", "style", "meta", "link", "title", TypedValues.AttributesType.S_FRAME, "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol", "pre", "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins", "del", SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S, "dl", "dt", "dd", "li", "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th", "td", "video", "audio", "canvas", ErrorBundle.DETAIL_ENTRY, CommonCssConstants.MENU, "plaintext", "template", "article", "main", SvgConstants.Tags.SVG, "math"};
        blockTags = strArr;
        for (String tagName2 : strArr) {
            register(new Tag(tagName2));
        }
        for (String tagName3 : inlineTags) {
            Tag tag = new Tag(tagName3);
            tag.isBlock = false;
            tag.canContainBlock = false;
            tag.formatAsBlock = false;
            register(tag);
        }
        for (String tagName4 : emptyTags) {
            Tag tag2 = tags.get(tagName4);
            Validate.notNull(tag2);
            tag2.canContainBlock = false;
            tag2.canContainInline = false;
            tag2.empty = true;
        }
        for (String tagName5 : formatAsInlineTags) {
            Tag tag3 = tags.get(tagName5);
            Validate.notNull(tag3);
            tag3.formatAsBlock = false;
        }
        for (String tagName6 : preserveWhitespaceTags) {
            Tag tag4 = tags.get(tagName6);
            Validate.notNull(tag4);
            tag4.preserveWhitespace = true;
        }
        for (String tagName7 : formListedTags) {
            Tag tag5 = tags.get(tagName7);
            Validate.notNull(tag5);
            tag5.formList = true;
        }
        for (String tagName8 : formSubmitTags) {
            Tag tag6 = tags.get(tagName8);
            Validate.notNull(tag6);
            tag6.formSubmit = true;
        }
    }

    private Tag(String tagName2) {
        this.tagName = tagName2.toLowerCase();
    }

    public String getName() {
        return this.tagName;
    }

    public static Tag valueOf(String tagName2) {
        Validate.notNull(tagName2);
        Map<String, Tag> map = tags;
        Tag tag = map.get(tagName2);
        if (tag != null) {
            return tag;
        }
        String tagName3 = tagName2.trim().toLowerCase();
        Validate.notEmpty(tagName3);
        Tag tag2 = map.get(tagName3);
        if (tag2 != null) {
            return tag2;
        }
        Tag tag3 = new Tag(tagName3);
        tag3.isBlock = false;
        tag3.canContainBlock = true;
        return tag3;
    }

    public boolean isBlock() {
        return this.isBlock;
    }

    public boolean formatAsBlock() {
        return this.formatAsBlock;
    }

    public boolean canContainBlock() {
        return this.canContainBlock;
    }

    public boolean isInline() {
        return !this.isBlock;
    }

    public boolean isData() {
        return !this.canContainInline && !isEmpty();
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isSelfClosing() {
        return this.empty || this.selfClosing;
    }

    public boolean isKnownTag() {
        return tags.containsKey(this.tagName);
    }

    public static boolean isKnownTag(String tagName2) {
        return tags.containsKey(tagName2);
    }

    public boolean preserveWhitespace() {
        return this.preserveWhitespace;
    }

    public boolean isFormListed() {
        return this.formList;
    }

    public boolean isFormSubmittable() {
        return this.formSubmit;
    }

    /* access modifiers changed from: package-private */
    public Tag setSelfClosing() {
        this.selfClosing = true;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) o;
        if (this.tagName.equals(tag.tagName) && this.canContainBlock == tag.canContainBlock && this.canContainInline == tag.canContainInline && this.empty == tag.empty && this.formatAsBlock == tag.formatAsBlock && this.isBlock == tag.isBlock && this.preserveWhitespace == tag.preserveWhitespace && this.selfClosing == tag.selfClosing && this.formList == tag.formList && this.formSubmit == tag.formSubmit) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((((((this.tagName.hashCode() * 31) + (this.isBlock ? 1 : 0)) * 31) + (this.formatAsBlock ? 1 : 0)) * 31) + (this.canContainBlock ? 1 : 0)) * 31) + (this.canContainInline ? 1 : 0)) * 31) + (this.empty ? 1 : 0)) * 31) + (this.selfClosing ? 1 : 0)) * 31) + (this.preserveWhitespace ? 1 : 0)) * 31) + (this.formList ? 1 : 0)) * 31) + (this.formSubmit ? 1 : 0);
    }

    public String toString() {
        return this.tagName;
    }

    private static void register(Tag tag) {
        tags.put(tag.tagName, tag);
    }
}
