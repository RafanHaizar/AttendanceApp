package com.itextpdf.barcodes.dmcode;

public class DmParams {
    public int dataBlock;
    public int dataSize;
    public int errorBlock;
    public int height;
    public int heightSection;
    public int width;
    public int widthSection;

    public DmParams(int height2, int width2, int heightSection2, int widthSection2, int dataSize2, int dataBlock2, int errorBlock2) {
        this.height = height2;
        this.width = width2;
        this.heightSection = heightSection2;
        this.widthSection = widthSection2;
        this.dataSize = dataSize2;
        this.dataBlock = dataBlock2;
        this.errorBlock = errorBlock2;
    }
}
