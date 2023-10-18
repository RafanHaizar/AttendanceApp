package com.itextpdf.layout.renderer;

import com.itextpdf.layout.property.BackgroundImage;
import com.itextpdf.layout.property.BackgroundSize;
import com.itextpdf.layout.property.UnitValue;

final class BackgroundSizeCalculationUtil {
    private static final int PERCENT_100 = 100;
    private static final UnitValue PERCENT_VALUE_100 = UnitValue.createPercentValue(100.0f);

    private BackgroundSizeCalculationUtil() {
    }

    public static float[] calculateBackgroundImageSize(BackgroundImage image, float areaWidth, float areaHeight) {
        BackgroundSize size;
        boolean isGradient = image.getLinearGradientBuilder() != null;
        if (isGradient || !image.getBackgroundSize().isSpecificSize()) {
            size = image.getBackgroundSize();
        } else {
            size = calculateBackgroundSizeForArea(image, areaWidth, areaHeight);
        }
        UnitValue width = size.getBackgroundWidthSize();
        UnitValue height = size.getBackgroundHeightSize();
        Float[] widthAndHeight = new Float[2];
        if (width != null && width.getValue() >= 0.0f) {
            calculateBackgroundWidth(width, areaWidth, !isGradient && height == null, image, widthAndHeight);
        }
        if (height != null && height.getValue() >= 0.0f) {
            calculateBackgroundHeight(height, areaHeight, !isGradient && width == null, image, widthAndHeight);
        }
        setDefaultSizeIfNull(widthAndHeight, areaWidth, areaHeight, image, isGradient);
        return new float[]{widthAndHeight[0].floatValue(), widthAndHeight[1].floatValue()};
    }

    private static BackgroundSize calculateBackgroundSizeForArea(BackgroundImage image, float areaWidth, float areaHeight) {
        double widthDifference = (double) (areaWidth / image.getImageWidth());
        double heightDifference = (double) (areaHeight / image.getImageHeight());
        boolean z = true;
        if (image.getBackgroundSize().isCover()) {
            if (widthDifference <= heightDifference) {
                z = false;
            }
            return createSizeWithMaxValueSide(z);
        } else if (!image.getBackgroundSize().isContain()) {
            return new BackgroundSize();
        } else {
            if (widthDifference >= heightDifference) {
                z = false;
            }
            return createSizeWithMaxValueSide(z);
        }
    }

    private static BackgroundSize createSizeWithMaxValueSide(boolean maxWidth) {
        BackgroundSize size = new BackgroundSize();
        if (maxWidth) {
            size.setBackgroundSizeToValues(PERCENT_VALUE_100, (UnitValue) null);
        } else {
            size.setBackgroundSizeToValues((UnitValue) null, PERCENT_VALUE_100);
        }
        return size;
    }

    private static void calculateBackgroundWidth(UnitValue width, float areaWidth, boolean scale, BackgroundImage image, Float[] widthAndHeight) {
        if (scale) {
            if (width.isPercentValue()) {
                scaleWidth((width.getValue() * areaWidth) / 100.0f, image, widthAndHeight);
            } else {
                scaleWidth(width.getValue(), image, widthAndHeight);
            }
        } else if (width.isPercentValue()) {
            widthAndHeight[0] = Float.valueOf((width.getValue() * areaWidth) / 100.0f);
        } else {
            widthAndHeight[0] = Float.valueOf(width.getValue());
        }
    }

    private static void calculateBackgroundHeight(UnitValue height, float areaHeight, boolean scale, BackgroundImage image, Float[] widthAndHeight) {
        if (scale) {
            if (height.isPercentValue()) {
                scaleHeight((height.getValue() * areaHeight) / 100.0f, image, widthAndHeight);
            } else {
                scaleHeight(height.getValue(), image, widthAndHeight);
            }
        } else if (height.isPercentValue()) {
            widthAndHeight[1] = Float.valueOf((height.getValue() * areaHeight) / 100.0f);
        } else {
            widthAndHeight[1] = Float.valueOf(height.getValue());
        }
    }

    private static void scaleWidth(float newWidth, BackgroundImage image, Float[] imageWidthAndHeight) {
        float difference = image.getImageWidth() == 0.0f ? 1.0f : newWidth / image.getImageWidth();
        imageWidthAndHeight[0] = Float.valueOf(newWidth);
        imageWidthAndHeight[1] = Float.valueOf(image.getImageHeight() * difference);
    }

    private static void scaleHeight(float newHeight, BackgroundImage image, Float[] imageWidthAndHeight) {
        imageWidthAndHeight[0] = Float.valueOf(image.getImageWidth() * (image.getImageHeight() == 0.0f ? 1.0f : newHeight / image.getImageHeight()));
        imageWidthAndHeight[1] = Float.valueOf(newHeight);
    }

    private static void setDefaultSizeIfNull(Float[] widthAndHeight, float areaWidth, float areaHeight, BackgroundImage image, boolean isGradient) {
        if (isGradient) {
            widthAndHeight[0] = Float.valueOf(widthAndHeight[0] == null ? areaWidth : widthAndHeight[0].floatValue());
            widthAndHeight[1] = Float.valueOf(widthAndHeight[1] == null ? areaHeight : widthAndHeight[1].floatValue());
            return;
        }
        widthAndHeight[0] = Float.valueOf(widthAndHeight[0] == null ? image.getImageWidth() : widthAndHeight[0].floatValue());
        widthAndHeight[1] = Float.valueOf(widthAndHeight[1] == null ? image.getImageHeight() : widthAndHeight[1].floatValue());
    }
}
