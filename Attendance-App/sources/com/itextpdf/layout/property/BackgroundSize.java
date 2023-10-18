package com.itextpdf.layout.property;

public class BackgroundSize {
    private UnitValue backgroundHeightSize;
    private UnitValue backgroundWidthSize;
    private boolean contain = false;
    private boolean cover = false;

    public void setBackgroundSizeToValues(UnitValue width, UnitValue height) {
        clear();
        this.backgroundWidthSize = width;
        this.backgroundHeightSize = height;
    }

    public void setBackgroundSizeToContain() {
        clear();
        this.contain = true;
    }

    public void setBackgroundSizeToCover() {
        clear();
        this.cover = true;
    }

    public UnitValue getBackgroundWidthSize() {
        return this.backgroundWidthSize;
    }

    public UnitValue getBackgroundHeightSize() {
        return this.backgroundHeightSize;
    }

    public boolean isSpecificSize() {
        return this.contain || this.cover;
    }

    public boolean isContain() {
        return this.contain;
    }

    public boolean isCover() {
        return this.cover;
    }

    private void clear() {
        this.contain = false;
        this.cover = false;
        this.backgroundWidthSize = null;
        this.backgroundHeightSize = null;
    }
}
