package com.itextpdf.p026io.font;

import java.io.Serializable;

/* renamed from: com.itextpdf.io.font.FontIdentification */
public class FontIdentification implements Serializable {
    private static final long serialVersionUID = -6017656004487895604L;
    private String panose;
    private String ttfUniqueId;
    private String ttfVersion;
    private Integer type1Xuid;

    public String getTtfVersion() {
        return this.ttfVersion;
    }

    public String getTtfUniqueId() {
        return this.ttfUniqueId;
    }

    public Integer getType1Xuid() {
        return this.type1Xuid;
    }

    public String getPanose() {
        return this.panose;
    }

    /* access modifiers changed from: protected */
    public void setTtfVersion(String ttfVersion2) {
        this.ttfVersion = ttfVersion2;
    }

    /* access modifiers changed from: protected */
    public void setTtfUniqueId(String ttfUniqueId2) {
        this.ttfUniqueId = ttfUniqueId2;
    }

    /* access modifiers changed from: protected */
    public void setType1Xuid(Integer type1Xuid2) {
        this.type1Xuid = type1Xuid2;
    }

    /* access modifiers changed from: protected */
    public void setPanose(byte[] panose2) {
        this.panose = new String(panose2);
    }

    /* access modifiers changed from: protected */
    public void setPanose(String panose2) {
        this.panose = panose2;
    }
}
