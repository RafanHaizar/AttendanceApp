package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.jsoup.SerializationException;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.svg.SvgConstants;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import kotlin.text.Typography;

public class Attribute implements Map.Entry<String, String>, Cloneable {
    private static final String[] booleanAttributes = {"allowfullscreen", "async", "autofocus", CommonCssConstants.CHECKED, "compact", "declare", "default", SvgConstants.Values.DEFER, "disabled", "formnovalidate", "hidden", "inert", "ismap", "itemscope", "multiple", "muted", "nohref", "noresize", "noshade", "novalidate", "nowrap", XfdfConstants.OPEN, XfdfConstants.READ_ONLY, CommonCssConstants.REQUIRED, "reversed", "seamless", "selected", "sortable", "truespeed", "typemustmatch"};
    private String key;
    private String value;

    public Attribute(String key2, String value2) {
        Validate.notEmpty(key2);
        Validate.notNull(value2);
        this.key = key2.trim().toLowerCase();
        this.value = value2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        Validate.notEmpty(key2);
        this.key = key2.trim().toLowerCase();
    }

    public String getValue() {
        return this.value;
    }

    public String setValue(String value2) {
        Validate.notNull(value2);
        String old = this.value;
        this.value = value2;
        return old;
    }

    public String html() {
        StringBuilder accum = new StringBuilder();
        try {
            html(accum, new Document("").outputSettings());
            return accum.toString();
        } catch (IOException exception) {
            throw new SerializationException((Throwable) exception);
        }
    }

    /* access modifiers changed from: protected */
    public void html(Appendable accum, Document.OutputSettings out) throws IOException {
        accum.append(this.key);
        if (!shouldCollapseAttribute(out)) {
            accum.append("=\"");
            Entities.escape(accum, this.value, out, true, false, false);
            accum.append(Typography.quote);
        }
    }

    public String toString() {
        return html();
    }

    public static Attribute createFromEncoded(String unencodedKey, String encodedValue) {
        return new Attribute(unencodedKey, Entities.unescape(encodedValue, true));
    }

    /* access modifiers changed from: protected */
    public boolean isDataAttribute() {
        return this.key.startsWith("data-") && this.key.length() > "data-".length();
    }

    /* access modifiers changed from: protected */
    public final boolean shouldCollapseAttribute(Document.OutputSettings out) {
        return ("".equals(this.value) || this.value.equalsIgnoreCase(this.key)) && out.syntax() == Document.OutputSettings.Syntax.html && isBooleanAttribute();
    }

    /* access modifiers changed from: protected */
    public boolean isBooleanAttribute() {
        return Arrays.binarySearch(booleanAttributes, this.key) >= 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attribute)) {
            return false;
        }
        Attribute attribute = (Attribute) o;
        String str = this.key;
        if (str == null ? attribute.key != null : !str.equals(attribute.key)) {
            return false;
        }
        String str2 = this.value;
        if (str2 != null) {
            if (!str2.equals(attribute.value)) {
                return false;
            }
            return true;
        } else if (attribute.value == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.key;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.value;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
