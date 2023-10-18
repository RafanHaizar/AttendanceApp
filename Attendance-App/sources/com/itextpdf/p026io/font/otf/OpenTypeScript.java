package com.itextpdf.p026io.font.otf;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.itextpdf.io.font.otf.OpenTypeScript */
public class OpenTypeScript implements Serializable {
    private static final long serialVersionUID = 381398146861429491L;
    public final String DEFAULT_SCRIPT = "DFLT";
    private OpenTypeFontTableReader openTypeReader;
    private List<ScriptRecord> records;

    public OpenTypeScript(OpenTypeFontTableReader openTypeReader2, int locationScriptTable) throws IOException {
        this.openTypeReader = openTypeReader2;
        this.records = new ArrayList();
        openTypeReader2.f1214rf.seek((long) locationScriptTable);
        for (TagAndLocation tagLoc : openTypeReader2.readTagAndLocations(locationScriptTable)) {
            readScriptRecord(tagLoc);
        }
    }

    public List<ScriptRecord> getScriptRecords() {
        return this.records;
    }

    public LanguageRecord getLanguageRecord(String[] scripts, String language) {
        ScriptRecord scriptFound = null;
        ScriptRecord scriptDefault = null;
        Iterator<ScriptRecord> it = this.records.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ScriptRecord sr = it.next();
            if ("DFLT".equals(sr.tag)) {
                scriptDefault = sr;
                break;
            }
        }
        int i = 0;
        for (String script : scripts) {
            Iterator<ScriptRecord> it2 = this.records.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                ScriptRecord sr2 = it2.next();
                if (sr2.tag.equals(script)) {
                    scriptFound = sr2;
                    break;
                } else if ("DFLT".equals(script)) {
                    scriptDefault = sr2;
                }
            }
            if (scriptFound != null) {
                break;
            }
        }
        if (scriptFound == null) {
            scriptFound = scriptDefault;
        }
        if (scriptFound == null) {
            return null;
        }
        LanguageRecord lang = null;
        LanguageRecord[] languageRecordArr = scriptFound.languages;
        int length = languageRecordArr.length;
        while (true) {
            if (i >= length) {
                break;
            }
            LanguageRecord lr = languageRecordArr[i];
            if (lr.tag.equals(language)) {
                lang = lr;
                break;
            }
            i++;
        }
        if (lang == null) {
            return scriptFound.defaultLanguage;
        }
        return lang;
    }

    private void readScriptRecord(TagAndLocation tagLoc) throws IOException {
        this.openTypeReader.f1214rf.seek((long) tagLoc.location);
        int locationDefaultLanguage = this.openTypeReader.f1214rf.readUnsignedShort();
        if (locationDefaultLanguage > 0) {
            locationDefaultLanguage += tagLoc.location;
        }
        TagAndLocation[] tagsLocs = this.openTypeReader.readTagAndLocations(tagLoc.location);
        ScriptRecord srec = new ScriptRecord();
        srec.tag = tagLoc.tag;
        srec.languages = new LanguageRecord[tagsLocs.length];
        for (int k = 0; k < tagsLocs.length; k++) {
            srec.languages[k] = readLanguageRecord(tagsLocs[k]);
        }
        if (locationDefaultLanguage > 0) {
            TagAndLocation t = new TagAndLocation();
            t.tag = "";
            t.location = locationDefaultLanguage;
            srec.defaultLanguage = readLanguageRecord(t);
        }
        this.records.add(srec);
    }

    private LanguageRecord readLanguageRecord(TagAndLocation tagLoc) throws IOException {
        LanguageRecord rec = new LanguageRecord();
        this.openTypeReader.f1214rf.seek((long) (tagLoc.location + 2));
        rec.featureRequired = this.openTypeReader.f1214rf.readUnsignedShort();
        rec.features = this.openTypeReader.readUShortArray(this.openTypeReader.f1214rf.readUnsignedShort());
        rec.tag = tagLoc.tag;
        return rec;
    }
}
