package com.itextpdf.kernel.xmp.properties;

import com.itextpdf.kernel.xmp.options.PropertyOptions;

public interface XMPProperty {
    String getLanguage();

    PropertyOptions getOptions();

    String getValue();
}
