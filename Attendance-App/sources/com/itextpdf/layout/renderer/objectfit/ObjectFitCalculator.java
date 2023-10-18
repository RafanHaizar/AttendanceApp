package com.itextpdf.layout.renderer.objectfit;

import com.itextpdf.layout.property.ObjectFit;

public final class ObjectFitCalculator {
    private ObjectFitCalculator() {
    }

    /* renamed from: com.itextpdf.layout.renderer.objectfit.ObjectFitCalculator$1 */
    static /* synthetic */ class C14781 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$layout$property$ObjectFit;

        static {
            int[] iArr = new int[ObjectFit.values().length];
            $SwitchMap$com$itextpdf$layout$property$ObjectFit = iArr;
            try {
                iArr[ObjectFit.FILL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ObjectFit[ObjectFit.CONTAIN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ObjectFit[ObjectFit.COVER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ObjectFit[ObjectFit.SCALE_DOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$layout$property$ObjectFit[ObjectFit.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public static ObjectFitApplyingResult calculateRenderedImageSize(ObjectFit objectFit, double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
        switch (C14781.$SwitchMap$com$itextpdf$layout$property$ObjectFit[objectFit.ordinal()]) {
            case 1:
                return processFill(imageContainerWidth, imageContainerHeight);
            case 2:
                return processContain(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight);
            case 3:
                return processCover(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight);
            case 4:
                return processScaleDown(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight);
            case 5:
                return processNone(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight);
            default:
                throw new IllegalArgumentException("Object fit parameter cannot be null!");
        }
    }

    private static ObjectFitApplyingResult processFill(double imageContainerWidth, double imageContainerHeight) {
        return new ObjectFitApplyingResult(imageContainerWidth, imageContainerHeight, false);
    }

    private static ObjectFitApplyingResult processContain(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
        return processToFitSide(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight, false);
    }

    private static ObjectFitApplyingResult processCover(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
        return processToFitSide(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight, true);
    }

    private static ObjectFitApplyingResult processScaleDown(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
        if (imageContainerWidth < absoluteImageWidth || imageContainerHeight < absoluteImageHeight) {
            return processToFitSide(absoluteImageWidth, absoluteImageHeight, imageContainerWidth, imageContainerHeight, false);
        }
        return new ObjectFitApplyingResult(absoluteImageWidth, absoluteImageHeight, false);
    }

    private static ObjectFitApplyingResult processNone(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight) {
        return new ObjectFitApplyingResult(absoluteImageWidth, absoluteImageHeight, imageContainerWidth <= absoluteImageWidth || imageContainerHeight <= absoluteImageHeight);
    }

    private static ObjectFitApplyingResult processToFitSide(double absoluteImageWidth, double absoluteImageHeight, double imageContainerWidth, double imageContainerHeight, boolean clipToFit) {
        double renderedImageHeight;
        double renderedImageWidth;
        if ((imageContainerHeight / absoluteImageHeight > imageContainerWidth / absoluteImageWidth) ^ clipToFit) {
            renderedImageWidth = imageContainerWidth;
            renderedImageHeight = (absoluteImageHeight * imageContainerWidth) / absoluteImageWidth;
        } else {
            renderedImageWidth = (absoluteImageWidth * imageContainerHeight) / absoluteImageHeight;
            renderedImageHeight = imageContainerHeight;
        }
        return new ObjectFitApplyingResult(renderedImageWidth, renderedImageHeight, clipToFit);
    }
}
