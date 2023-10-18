package com.itextpdf.layout.property;

import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;

public class BackgroundRepeat {
    private final BackgroundRepeatValue xAxisRepeat;
    private final BackgroundRepeatValue yAxisRepeat;

    public enum BackgroundRepeatValue {
        NO_REPEAT,
        REPEAT,
        ROUND,
        SPACE
    }

    public BackgroundRepeat() {
        this(BackgroundRepeatValue.REPEAT);
    }

    public BackgroundRepeat(BackgroundRepeatValue repeat) {
        this(repeat, repeat);
    }

    public BackgroundRepeat(BackgroundRepeatValue xAxisRepeat2, BackgroundRepeatValue yAxisRepeat2) {
        this.xAxisRepeat = xAxisRepeat2;
        this.yAxisRepeat = yAxisRepeat2;
    }

    public BackgroundRepeatValue getXAxisRepeat() {
        return this.xAxisRepeat;
    }

    public BackgroundRepeatValue getYAxisRepeat() {
        return this.yAxisRepeat;
    }

    public boolean isNoRepeatOnXAxis() {
        return this.xAxisRepeat == BackgroundRepeatValue.NO_REPEAT;
    }

    public boolean isNoRepeatOnYAxis() {
        return this.yAxisRepeat == BackgroundRepeatValue.NO_REPEAT;
    }

    public Point prepareRectangleToDrawingAndGetWhitespace(Rectangle imageRectangle, Rectangle backgroundArea, BackgroundSize backgroundSize) {
        if (BackgroundRepeatValue.ROUND == this.xAxisRepeat) {
            int ratio = calculateRatio(backgroundArea.getWidth(), imageRectangle.getWidth());
            float initialImageRatio = imageRectangle.getHeight() / imageRectangle.getWidth();
            imageRectangle.setWidth(backgroundArea.getWidth() / ((float) ratio));
            if (BackgroundRepeatValue.ROUND != this.yAxisRepeat && backgroundSize.getBackgroundHeightSize() == null) {
                imageRectangle.moveUp(imageRectangle.getHeight() - (imageRectangle.getWidth() * initialImageRatio));
                imageRectangle.setHeight(imageRectangle.getWidth() * initialImageRatio);
            }
        }
        if (BackgroundRepeatValue.ROUND == this.yAxisRepeat) {
            int ratio2 = calculateRatio(backgroundArea.getHeight(), imageRectangle.getHeight());
            float initialImageRatio2 = imageRectangle.getWidth() / imageRectangle.getHeight();
            imageRectangle.moveUp(imageRectangle.getHeight() - (backgroundArea.getHeight() / ((float) ratio2)));
            imageRectangle.setHeight(backgroundArea.getHeight() / ((float) ratio2));
            if (BackgroundRepeatValue.ROUND != this.xAxisRepeat && backgroundSize.getBackgroundWidthSize() == null) {
                imageRectangle.setWidth(imageRectangle.getHeight() * initialImageRatio2);
            }
        }
        return processSpaceValueAndCalculateWhitespace(imageRectangle, backgroundArea);
    }

    private Point processSpaceValueAndCalculateWhitespace(Rectangle imageRectangle, Rectangle backgroundArea) {
        Point whitespace = new Point();
        float yWhitespace = 0.0f;
        if (BackgroundRepeatValue.SPACE == this.xAxisRepeat) {
            if (imageRectangle.getWidth() * 2.0f <= backgroundArea.getWidth()) {
                imageRectangle.setX(backgroundArea.getX());
                whitespace.setLocation((double) calculateWhitespace(backgroundArea.getWidth(), imageRectangle.getWidth()), 0.0d);
            } else {
                float xWhitespace = Math.max(backgroundArea.getRight() - imageRectangle.getRight(), imageRectangle.getLeft() - backgroundArea.getLeft());
                whitespace.setLocation((double) (xWhitespace > 0.0f ? xWhitespace : 0.0f), 0.0d);
            }
        }
        if (BackgroundRepeatValue.SPACE == this.yAxisRepeat) {
            if (imageRectangle.getHeight() * 2.0f <= backgroundArea.getHeight()) {
                imageRectangle.setY((backgroundArea.getY() + backgroundArea.getHeight()) - imageRectangle.getHeight());
                whitespace.setLocation(whitespace.getX(), (double) calculateWhitespace(backgroundArea.getHeight(), imageRectangle.getHeight()));
            } else {
                float yWhitespace2 = Math.max(backgroundArea.getTop() - imageRectangle.getTop(), imageRectangle.getBottom() - backgroundArea.getBottom());
                if (yWhitespace2 > 0.0f) {
                    yWhitespace = yWhitespace2;
                }
                whitespace.setLocation(whitespace.getX(), (double) yWhitespace);
            }
        }
        return whitespace;
    }

    private static int calculateRatio(float areaSize, float backgroundSize) {
        int ratio = (int) Math.floor((double) (areaSize / backgroundSize));
        if (areaSize - (((float) ratio) * backgroundSize) >= backgroundSize / 2.0f) {
            ratio++;
        }
        if (ratio == 0) {
            return 1;
        }
        return ratio;
    }

    private static float calculateWhitespace(float areaSize, float backgroundSize) {
        int ratio = (int) Math.floor((double) (areaSize / backgroundSize));
        if (ratio <= 0) {
            return 0.0f;
        }
        float whitespace = areaSize - (((float) ratio) * backgroundSize);
        if (ratio > 1) {
            return whitespace / ((float) (ratio - 1));
        }
        return whitespace;
    }
}
