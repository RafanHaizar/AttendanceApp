package com.itextpdf.p026io.font.otf;

import java.io.Serializable;

/* renamed from: com.itextpdf.io.font.otf.ScriptRecord */
public class ScriptRecord implements Serializable {
    private static final long serialVersionUID = 1670929244968728679L;
    public LanguageRecord defaultLanguage;
    public LanguageRecord[] languages;
    public String tag;
}
