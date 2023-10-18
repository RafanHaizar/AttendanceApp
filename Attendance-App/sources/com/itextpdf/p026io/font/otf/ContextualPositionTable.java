package com.itextpdf.p026io.font.otf;

/* renamed from: com.itextpdf.io.font.otf.ContextualPositionTable */
public abstract class ContextualPositionTable extends ContextualTable<ContextualPositionRule> {
    private static final long serialVersionUID = -5767338869523894047L;

    protected ContextualPositionTable(OpenTypeFontTableReader openReader, int lookupFlag) {
        super(openReader, lookupFlag);
    }
}
