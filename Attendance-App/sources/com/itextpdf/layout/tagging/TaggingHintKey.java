package com.itextpdf.layout.tagging;

public final class TaggingHintKey {
    private IAccessibleElement elem;
    private boolean elementBasedFinishingOnly;
    private boolean isArtifact;
    private boolean isFinished;
    private String overriddenRole;

    TaggingHintKey(IAccessibleElement elem2, boolean createdElementBased) {
        this.elem = elem2;
        this.elementBasedFinishingOnly = createdElementBased;
    }

    public IAccessibleElement getAccessibleElement() {
        return this.elem;
    }

    /* access modifiers changed from: package-private */
    public boolean isFinished() {
        return this.isFinished;
    }

    /* access modifiers changed from: package-private */
    public void setFinished() {
        this.isFinished = true;
    }

    /* access modifiers changed from: package-private */
    public boolean isArtifact() {
        return this.isArtifact;
    }

    /* access modifiers changed from: package-private */
    public void setArtifact() {
        this.isArtifact = true;
    }

    /* access modifiers changed from: package-private */
    public String getOverriddenRole() {
        return this.overriddenRole;
    }

    /* access modifiers changed from: package-private */
    public void setOverriddenRole(String overriddenRole2) {
        this.overriddenRole = overriddenRole2;
    }

    /* access modifiers changed from: package-private */
    public boolean isElementBasedFinishingOnly() {
        return this.elementBasedFinishingOnly;
    }
}
