package com.itextpdf.svg.renderers;

import java.util.Map;

public interface ISvgNodeRenderer {
    ISvgNodeRenderer createDeepCopy();

    void draw(SvgDrawContext svgDrawContext);

    String getAttribute(String str);

    Map<String, String> getAttributeMapCopy();

    ISvgNodeRenderer getParent();

    void setAttribute(String str, String str2);

    void setAttributesAndStyles(Map<String, String> map);

    void setParent(ISvgNodeRenderer iSvgNodeRenderer);
}
