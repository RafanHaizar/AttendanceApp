package com.itextpdf.kernel.xmp.properties;

import com.itextpdf.kernel.xmp.options.AliasOptions;

public interface XMPAliasInfo {
    AliasOptions getAliasForm();

    String getNamespace();

    String getPrefix();

    String getPropName();
}
