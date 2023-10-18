package com.itextpdf.layout.margincollapse;

import java.io.Serializable;

class MarginsCollapse implements Cloneable, Serializable {
    private float maxPositiveMargin = 0.0f;
    private float minNegativeMargin = 0.0f;

    MarginsCollapse() {
    }

    /* access modifiers changed from: package-private */
    public void joinMargin(float margin) {
        if (this.maxPositiveMargin < margin) {
            this.maxPositiveMargin = margin;
        } else if (this.minNegativeMargin > margin) {
            this.minNegativeMargin = margin;
        }
    }

    /* access modifiers changed from: package-private */
    public void joinMargin(MarginsCollapse marginsCollapse) {
        joinMargin(marginsCollapse.maxPositiveMargin);
        joinMargin(marginsCollapse.minNegativeMargin);
    }

    /* access modifiers changed from: package-private */
    public float getCollapsedMarginsSize() {
        return this.maxPositiveMargin + this.minNegativeMargin;
    }

    public MarginsCollapse clone() {
        try {
            return (MarginsCollapse) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
