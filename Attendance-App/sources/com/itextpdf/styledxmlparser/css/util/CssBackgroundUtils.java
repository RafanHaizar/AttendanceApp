package com.itextpdf.styledxmlparser.css.util;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;

public final class CssBackgroundUtils {

    public enum BackgroundPropertyType {
        BACKGROUND_COLOR,
        BACKGROUND_IMAGE,
        BACKGROUND_POSITION,
        BACKGROUND_POSITION_X,
        BACKGROUND_POSITION_Y,
        BACKGROUND_SIZE,
        BACKGROUND_REPEAT,
        BACKGROUND_ORIGIN,
        BACKGROUND_CLIP,
        BACKGROUND_ATTACHMENT,
        BACKGROUND_POSITION_OR_SIZE,
        BACKGROUND_ORIGIN_OR_CLIP,
        UNDEFINED
    }

    private CssBackgroundUtils() {
    }

    /* renamed from: com.itextpdf.styledxmlparser.css.util.CssBackgroundUtils$1 */
    static /* synthetic */ class C14891 {

        /* renamed from: $SwitchMap$com$itextpdf$styledxmlparser$css$util$CssBackgroundUtils$BackgroundPropertyType */
        static final /* synthetic */ int[] f1623xb3091de6;

        static {
            int[] iArr = new int[BackgroundPropertyType.values().length];
            f1623xb3091de6 = iArr;
            try {
                iArr[BackgroundPropertyType.BACKGROUND_COLOR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1623xb3091de6[BackgroundPropertyType.BACKGROUND_IMAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1623xb3091de6[BackgroundPropertyType.BACKGROUND_POSITION.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1623xb3091de6[BackgroundPropertyType.BACKGROUND_POSITION_X.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1623xb3091de6[BackgroundPropertyType.BACKGROUND_POSITION_Y.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1623xb3091de6[BackgroundPropertyType.BACKGROUND_SIZE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1623xb3091de6[BackgroundPropertyType.BACKGROUND_REPEAT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1623xb3091de6[BackgroundPropertyType.BACKGROUND_ORIGIN.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f1623xb3091de6[BackgroundPropertyType.BACKGROUND_CLIP.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f1623xb3091de6[BackgroundPropertyType.BACKGROUND_ATTACHMENT.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    public static String getBackgroundPropertyNameFromType(BackgroundPropertyType propertyType) {
        switch (C14891.f1623xb3091de6[propertyType.ordinal()]) {
            case 1:
                return CommonCssConstants.BACKGROUND_COLOR;
            case 2:
                return CommonCssConstants.BACKGROUND_IMAGE;
            case 3:
                return CommonCssConstants.BACKGROUND_POSITION;
            case 4:
                return CommonCssConstants.BACKGROUND_POSITION_X;
            case 5:
                return CommonCssConstants.BACKGROUND_POSITION_Y;
            case 6:
                return CommonCssConstants.BACKGROUND_SIZE;
            case 7:
                return CommonCssConstants.BACKGROUND_REPEAT;
            case 8:
                return CommonCssConstants.BACKGROUND_ORIGIN;
            case 9:
                return CommonCssConstants.BACKGROUND_CLIP;
            case 10:
                return CommonCssConstants.BACKGROUND_ATTACHMENT;
            default:
                return CommonCssConstants.UNDEFINED_NAME;
        }
    }

    public static BackgroundPropertyType resolveBackgroundPropertyType(String value) {
        if (value.startsWith("url(") && value.indexOf(40, "url(".length()) == -1 && value.indexOf(41) == value.length() - 1) {
            return BackgroundPropertyType.BACKGROUND_IMAGE;
        }
        if (CssGradientUtil.isCssLinearGradientValue(value) || "none".equals(value)) {
            return BackgroundPropertyType.BACKGROUND_IMAGE;
        }
        if (CommonCssConstants.BACKGROUND_REPEAT_VALUES.contains(value)) {
            return BackgroundPropertyType.BACKGROUND_REPEAT;
        }
        if (CommonCssConstants.BACKGROUND_ATTACHMENT_VALUES.contains(value)) {
            return BackgroundPropertyType.BACKGROUND_ATTACHMENT;
        }
        if (CommonCssConstants.BACKGROUND_POSITION_X_VALUES.contains(value) && !CommonCssConstants.CENTER.equals(value)) {
            return BackgroundPropertyType.BACKGROUND_POSITION_X;
        }
        if (CommonCssConstants.BACKGROUND_POSITION_Y_VALUES.contains(value) && !CommonCssConstants.CENTER.equals(value)) {
            return BackgroundPropertyType.BACKGROUND_POSITION_Y;
        }
        if (CommonCssConstants.CENTER.equals(value)) {
            return BackgroundPropertyType.BACKGROUND_POSITION;
        }
        Integer num = 0;
        if (num.equals(CssUtils.parseInteger(value)) || CssUtils.isMetricValue(value) || CssUtils.isRelativeValue(value)) {
            return BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE;
        }
        if (CommonCssConstants.BACKGROUND_SIZE_VALUES.contains(value)) {
            return BackgroundPropertyType.BACKGROUND_SIZE;
        }
        if (CssUtils.isColorProperty(value)) {
            return BackgroundPropertyType.BACKGROUND_COLOR;
        }
        if (CommonCssConstants.BACKGROUND_ORIGIN_OR_CLIP_VALUES.contains(value)) {
            return BackgroundPropertyType.BACKGROUND_ORIGIN_OR_CLIP;
        }
        return BackgroundPropertyType.UNDEFINED;
    }
}
