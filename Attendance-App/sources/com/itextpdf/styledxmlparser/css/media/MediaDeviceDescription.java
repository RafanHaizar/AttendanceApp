package com.itextpdf.styledxmlparser.css.media;

public class MediaDeviceDescription {
    private static final MediaDeviceDescription DEFAULT = createDefault();
    private int bitsPerComponent;
    private int colorIndex;
    private float height;
    private boolean isGrid;
    private int monochrome;
    private String orientation;
    private float resolution;
    private String scan;
    private String type;
    private float width;

    public MediaDeviceDescription(String type2) {
        this.bitsPerComponent = 0;
        this.colorIndex = 0;
        this.type = type2;
    }

    public MediaDeviceDescription(String type2, float width2, float height2) {
        this(type2);
        this.width = width2;
        this.height = height2;
    }

    public static MediaDeviceDescription createDefault() {
        return new MediaDeviceDescription(MediaType.ALL);
    }

    public static MediaDeviceDescription getDefault() {
        return DEFAULT;
    }

    public String getType() {
        return this.type;
    }

    public int getBitsPerComponent() {
        return this.bitsPerComponent;
    }

    public MediaDeviceDescription setBitsPerComponent(int bitsPerComponent2) {
        this.bitsPerComponent = bitsPerComponent2;
        return this;
    }

    public int getColorIndex() {
        return this.colorIndex;
    }

    public MediaDeviceDescription setColorIndex(int colorIndex2) {
        this.colorIndex = colorIndex2;
        return this;
    }

    public float getWidth() {
        return this.width;
    }

    public MediaDeviceDescription setWidth(float width2) {
        this.width = width2;
        return this;
    }

    public float getHeight() {
        return this.height;
    }

    public MediaDeviceDescription setHeight(float height2) {
        this.height = height2;
        return this;
    }

    public boolean isGrid() {
        return this.isGrid;
    }

    public MediaDeviceDescription setGrid(boolean grid) {
        this.isGrid = grid;
        return this;
    }

    public String getScan() {
        return this.scan;
    }

    public MediaDeviceDescription setScan(String scan2) {
        this.scan = scan2;
        return this;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public MediaDeviceDescription setOrientation(String orientation2) {
        this.orientation = orientation2;
        return this;
    }

    public int getMonochrome() {
        return this.monochrome;
    }

    public MediaDeviceDescription setMonochrome(int monochrome2) {
        this.monochrome = monochrome2;
        return this;
    }

    public float getResolution() {
        return this.resolution;
    }

    public MediaDeviceDescription setResolution(float resolution2) {
        this.resolution = resolution2;
        return this;
    }
}
