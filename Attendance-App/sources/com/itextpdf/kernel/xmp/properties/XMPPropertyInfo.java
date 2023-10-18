package com.itextpdf.kernel.xmp.properties;

import com.itextpdf.kernel.xmp.options.PropertyOptions;

public interface XMPPropertyInfo extends XMPProperty {
    String getNamespace();

    PropertyOptions getOptions();

    String getPath();

    String getValue();
}
