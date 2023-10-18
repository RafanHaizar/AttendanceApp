package com.itextpdf.styledxmlparser.css.resolve;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class CssDefaults {
    private static final Map<String, String> defaultValues;

    static {
        HashMap hashMap = new HashMap();
        defaultValues = hashMap;
        hashMap.put("color", "black");
        hashMap.put("opacity", "1");
        hashMap.put(CommonCssConstants.BACKGROUND_ATTACHMENT, CommonCssConstants.SCROLL);
        hashMap.put(CommonCssConstants.BACKGROUND_BLEND_MODE, CommonCssConstants.NORMAL);
        hashMap.put(CommonCssConstants.BACKGROUND_COLOR, CommonCssConstants.TRANSPARENT);
        hashMap.put(CommonCssConstants.BACKGROUND_IMAGE, "none");
        hashMap.put(CommonCssConstants.BACKGROUND_POSITION, "0% 0%");
        hashMap.put(CommonCssConstants.BACKGROUND_POSITION_X, "0%");
        hashMap.put(CommonCssConstants.BACKGROUND_POSITION_Y, "0%");
        hashMap.put(CommonCssConstants.BACKGROUND_REPEAT, "repeat");
        hashMap.put(CommonCssConstants.BACKGROUND_CLIP, CommonCssConstants.BORDER_BOX);
        hashMap.put(CommonCssConstants.BACKGROUND_ORIGIN, CommonCssConstants.PADDING_BOX);
        hashMap.put(CommonCssConstants.BACKGROUND_SIZE, "auto");
        hashMap.put(CommonCssConstants.BORDER_BOTTOM_COLOR, CommonCssConstants.CURRENTCOLOR);
        hashMap.put(CommonCssConstants.BORDER_LEFT_COLOR, CommonCssConstants.CURRENTCOLOR);
        hashMap.put(CommonCssConstants.BORDER_RIGHT_COLOR, CommonCssConstants.CURRENTCOLOR);
        hashMap.put(CommonCssConstants.BORDER_TOP_COLOR, CommonCssConstants.CURRENTCOLOR);
        hashMap.put(CommonCssConstants.BORDER_BOTTOM_STYLE, "none");
        hashMap.put(CommonCssConstants.BORDER_LEFT_STYLE, "none");
        hashMap.put(CommonCssConstants.BORDER_RIGHT_STYLE, "none");
        hashMap.put(CommonCssConstants.BORDER_TOP_STYLE, "none");
        hashMap.put(CommonCssConstants.BORDER_BOTTOM_WIDTH, CommonCssConstants.MEDIUM);
        hashMap.put(CommonCssConstants.BORDER_LEFT_WIDTH, CommonCssConstants.MEDIUM);
        hashMap.put(CommonCssConstants.BORDER_RIGHT_WIDTH, CommonCssConstants.MEDIUM);
        hashMap.put(CommonCssConstants.BORDER_TOP_WIDTH, CommonCssConstants.MEDIUM);
        hashMap.put(CommonCssConstants.BORDER_WIDTH, CommonCssConstants.MEDIUM);
        hashMap.put(CommonCssConstants.BORDER_IMAGE, "none");
        hashMap.put(CommonCssConstants.BORDER_RADIUS, "0");
        hashMap.put(CommonCssConstants.BORDER_BOTTOM_LEFT_RADIUS, "0");
        hashMap.put(CommonCssConstants.BORDER_BOTTOM_RIGHT_RADIUS, "0");
        hashMap.put(CommonCssConstants.BORDER_TOP_LEFT_RADIUS, "0");
        hashMap.put(CommonCssConstants.BORDER_TOP_RIGHT_RADIUS, "0");
        hashMap.put(CommonCssConstants.BOX_SHADOW, "none");
        hashMap.put("float", "none");
        hashMap.put("font-family", "times");
        hashMap.put("font-size", CommonCssConstants.MEDIUM);
        hashMap.put("font-style", CommonCssConstants.NORMAL);
        hashMap.put(CommonCssConstants.FONT_VARIANT, CommonCssConstants.NORMAL);
        hashMap.put("font-weight", CommonCssConstants.NORMAL);
        hashMap.put("height", "auto");
        hashMap.put(CommonCssConstants.HYPHENS, CommonCssConstants.MANUAL);
        hashMap.put(CommonCssConstants.LINE_HEIGHT, CommonCssConstants.NORMAL);
        hashMap.put(CommonCssConstants.LIST_STYLE_TYPE, CommonCssConstants.DISC);
        hashMap.put(CommonCssConstants.LIST_STYLE_IMAGE, "none");
        hashMap.put(CommonCssConstants.LIST_STYLE_POSITION, CommonCssConstants.OUTSIDE);
        hashMap.put(CommonCssConstants.MARGIN_BOTTOM, "0");
        hashMap.put(CommonCssConstants.MARGIN_LEFT, "0");
        hashMap.put(CommonCssConstants.MARGIN_RIGHT, "0");
        hashMap.put(CommonCssConstants.MARGIN_TOP, "0");
        hashMap.put(CommonCssConstants.MIN_HEIGHT, "0");
        hashMap.put(CommonCssConstants.OUTLINE_COLOR, CommonCssConstants.CURRENTCOLOR);
        hashMap.put(CommonCssConstants.OUTLINE_STYLE, "none");
        hashMap.put(CommonCssConstants.OUTLINE_WIDTH, CommonCssConstants.MEDIUM);
        hashMap.put(CommonCssConstants.PADDING_BOTTOM, "0");
        hashMap.put(CommonCssConstants.PADDING_LEFT, "0");
        hashMap.put(CommonCssConstants.PADDING_RIGHT, "0");
        hashMap.put(CommonCssConstants.PADDING_TOP, "0");
        hashMap.put(CommonCssConstants.PAGE_BREAK_AFTER, "auto");
        hashMap.put(CommonCssConstants.PAGE_BREAK_BEFORE, "auto");
        hashMap.put(CommonCssConstants.PAGE_BREAK_INSIDE, "auto");
        hashMap.put(CommonCssConstants.POSITION, CommonCssConstants.STATIC);
        hashMap.put(CommonCssConstants.QUOTES, "\"\\00ab\" \"\\00bb\"");
        hashMap.put(CommonCssConstants.TEXT_ALIGN, "start");
        hashMap.put(CommonCssConstants.TEXT_DECORATION, "none");
        hashMap.put(CommonCssConstants.TEXT_DECORATION_LINE, "none");
        hashMap.put(CommonCssConstants.TEXT_DECORATION_STYLE, CommonCssConstants.SOLID);
        hashMap.put(CommonCssConstants.TEXT_DECORATION_COLOR, CommonCssConstants.CURRENTCOLOR);
        hashMap.put(CommonCssConstants.TEXT_TRANSFORM, "none");
        hashMap.put(CommonCssConstants.WHITE_SPACE, CommonCssConstants.NORMAL);
        hashMap.put("width", "auto");
        hashMap.put(CommonCssConstants.ORPHANS, "2");
        hashMap.put(CommonCssConstants.WIDOWS, "2");
    }

    public static String getDefaultValue(String property) {
        String defaultVal = defaultValues.get(property);
        if (defaultVal == null) {
            LoggerFactory.getLogger((Class<?>) CssDefaults.class).error(MessageFormatUtil.format(LogMessageConstant.DEFAULT_VALUE_OF_CSS_PROPERTY_UNKNOWN, property));
        }
        return defaultVal;
    }
}
