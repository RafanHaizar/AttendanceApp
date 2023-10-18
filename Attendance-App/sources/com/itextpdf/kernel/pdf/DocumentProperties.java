package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.counter.event.IMetaInfo;
import java.io.Serializable;

public class DocumentProperties implements Serializable {
    private static final long serialVersionUID = -6625621282242153134L;
    protected IMetaInfo metaInfo = null;

    public DocumentProperties() {
    }

    public DocumentProperties(DocumentProperties other) {
        this.metaInfo = other.metaInfo;
    }

    public DocumentProperties setEventCountingMetaInfo(IMetaInfo metaInfo2) {
        this.metaInfo = metaInfo2;
        return this;
    }
}
