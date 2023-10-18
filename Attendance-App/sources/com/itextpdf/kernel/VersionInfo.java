package com.itextpdf.kernel;

import java.io.Serializable;

public class VersionInfo implements Serializable {
    private static final long serialVersionUID = 1514128839876564529L;
    private final String licenseKey;
    private final String producerLine;
    private final String productName;
    private final String releaseNumber;

    public VersionInfo(String productName2, String releaseNumber2, String producerLine2, String licenseKey2) {
        this.productName = productName2;
        this.releaseNumber = releaseNumber2;
        this.producerLine = producerLine2;
        this.licenseKey = licenseKey2;
    }

    public String getProduct() {
        return this.productName;
    }

    public String getRelease() {
        return this.releaseNumber;
    }

    public String getVersion() {
        return this.producerLine;
    }

    public String getKey() {
        return this.licenseKey;
    }
}
