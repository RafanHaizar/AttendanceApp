package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.jsoup.helper.KeyVal;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlinx.coroutines.DebugKt;

public class FormElement extends Element {
    private final Elements elements = new Elements();

    public FormElement(Tag tag, String baseUri, Attributes attributes) {
        super(tag, baseUri, attributes);
    }

    public Elements elements() {
        return this.elements;
    }

    public FormElement addElement(Element element) {
        this.elements.add(element);
        return this;
    }

    public List<KeyVal> formData() {
        Element option;
        ArrayList<KeyVal> data = new ArrayList<>();
        Iterator it = this.elements.iterator();
        while (it.hasNext()) {
            Element el = (Element) it.next();
            if (el.tag().isFormSubmittable() && !el.hasAttr("disabled")) {
                String name = el.attr(XfdfConstants.NAME);
                if (name.length() != 0) {
                    String type = el.attr(PdfConst.Type);
                    if ("select".equals(el.tagName())) {
                        boolean set = false;
                        Iterator it2 = el.select("option[selected]").iterator();
                        while (it2.hasNext()) {
                            data.add(KeyVal.create(name, ((Element) it2.next()).val()));
                            set = true;
                        }
                        if (!set && (option = el.select("option").first()) != null) {
                            data.add(KeyVal.create(name, option.val()));
                        }
                    } else if (!"checkbox".equalsIgnoreCase(type) && !"radio".equalsIgnoreCase(type)) {
                        data.add(KeyVal.create(name, el.val()));
                    } else if (el.hasAttr(CommonCssConstants.CHECKED)) {
                        data.add(KeyVal.create(name, el.val().length() > 0 ? el.val() : DebugKt.DEBUG_PROPERTY_VALUE_ON));
                    }
                }
            }
        }
        return data;
    }
}
