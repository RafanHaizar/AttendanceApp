package com.itextpdf.kernel.pdf;

import java.io.Serializable;

public class StampingProperties extends DocumentProperties implements Serializable {
    private static final long serialVersionUID = 6108082513101777457L;
    protected boolean appendMode = false;
    protected boolean preserveEncryption = false;

    public StampingProperties() {
    }

    public StampingProperties(StampingProperties other) {
        super(other);
        this.appendMode = other.appendMode;
        this.preserveEncryption = other.preserveEncryption;
    }

    public StampingProperties useAppendMode() {
        this.appendMode = true;
        return this;
    }

    public StampingProperties preserveEncryption() {
        this.preserveEncryption = true;
        return this;
    }
}
