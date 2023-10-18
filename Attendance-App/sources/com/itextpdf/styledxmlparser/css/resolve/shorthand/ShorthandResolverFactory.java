package com.itextpdf.styledxmlparser.css.resolve.shorthand;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BackgroundPositionShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BackgroundShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderBottomShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderColorShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderLeftShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderRadiusShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderRightShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderStyleShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderTopShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.BorderWidthShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.FontShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.ListStyleShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.MarginShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.OutlineShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.PaddingShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.impl.TextDecorationShorthandResolver;
import java.util.HashMap;
import java.util.Map;

public class ShorthandResolverFactory {
    private static final Map<String, IShorthandResolver> shorthandResolvers;

    static {
        HashMap hashMap = new HashMap();
        shorthandResolvers = hashMap;
        hashMap.put(CommonCssConstants.BACKGROUND, new BackgroundShorthandResolver());
        hashMap.put(CommonCssConstants.BACKGROUND_POSITION, new BackgroundPositionShorthandResolver());
        hashMap.put(CommonCssConstants.BORDER, new BorderShorthandResolver());
        hashMap.put(CommonCssConstants.BORDER_BOTTOM, new BorderBottomShorthandResolver());
        hashMap.put(CommonCssConstants.BORDER_COLOR, new BorderColorShorthandResolver());
        hashMap.put(CommonCssConstants.BORDER_LEFT, new BorderLeftShorthandResolver());
        hashMap.put(CommonCssConstants.BORDER_RADIUS, new BorderRadiusShorthandResolver());
        hashMap.put(CommonCssConstants.BORDER_RIGHT, new BorderRightShorthandResolver());
        hashMap.put(CommonCssConstants.BORDER_STYLE, new BorderStyleShorthandResolver());
        hashMap.put(CommonCssConstants.BORDER_TOP, new BorderTopShorthandResolver());
        hashMap.put(CommonCssConstants.BORDER_WIDTH, new BorderWidthShorthandResolver());
        hashMap.put("font", new FontShorthandResolver());
        hashMap.put(CommonCssConstants.LIST_STYLE, new ListStyleShorthandResolver());
        hashMap.put(CommonCssConstants.MARGIN, new MarginShorthandResolver());
        hashMap.put(CommonCssConstants.OUTLINE, new OutlineShorthandResolver());
        hashMap.put(CommonCssConstants.PADDING, new PaddingShorthandResolver());
        hashMap.put(CommonCssConstants.TEXT_DECORATION, new TextDecorationShorthandResolver());
    }

    public static IShorthandResolver getShorthandResolver(String shorthandProperty) {
        return shorthandResolvers.get(shorthandProperty);
    }
}
