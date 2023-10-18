package com.itextpdf.layout;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.hyphenation.HyphenationConfig;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.BackgroundImage;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.BorderRadius;
import com.itextpdf.layout.property.FontKerning;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.TransparentColor;
import com.itextpdf.layout.property.Underline;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.splitting.ISplitCharacters;
import java.lang.Character;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ElementPropertyContainer<T extends IPropertyContainer> implements IPropertyContainer {
    protected Map<Integer, Object> properties = new HashMap();

    public void setProperty(int property, Object value) {
        this.properties.put(Integer.valueOf(property), value);
    }

    public boolean hasProperty(int property) {
        return hasOwnProperty(property);
    }

    public boolean hasOwnProperty(int property) {
        return this.properties.containsKey(Integer.valueOf(property));
    }

    public void deleteOwnProperty(int property) {
        this.properties.remove(Integer.valueOf(property));
    }

    public <T1> T1 getProperty(int property) {
        return getOwnProperty(property);
    }

    public <T1> T1 getOwnProperty(int property) {
        return this.properties.get(Integer.valueOf(property));
    }

    public <T1> T1 getDefaultProperty(int property) {
        switch (property) {
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
                return UnitValue.createPointValue(0.0f);
            default:
                return null;
        }
    }

    public T setRelativePosition(float left, float top, float right, float bottom) {
        setProperty(52, 2);
        setProperty(34, Float.valueOf(left));
        setProperty(54, Float.valueOf(right));
        setProperty(73, Float.valueOf(top));
        setProperty(14, Float.valueOf(bottom));
        return this;
    }

    public T setFixedPosition(float left, float bottom, float width) {
        setFixedPosition(left, bottom, UnitValue.createPointValue(width));
        return this;
    }

    public T setFixedPosition(float left, float bottom, UnitValue width) {
        setProperty(52, 4);
        setProperty(34, Float.valueOf(left));
        setProperty(14, Float.valueOf(bottom));
        setProperty(77, width);
        return this;
    }

    public T setFixedPosition(int pageNumber, float left, float bottom, float width) {
        setFixedPosition(left, bottom, width);
        setProperty(51, Integer.valueOf(pageNumber));
        return this;
    }

    public T setFixedPosition(int pageNumber, float left, float bottom, UnitValue width) {
        setFixedPosition(left, bottom, width);
        setProperty(51, Integer.valueOf(pageNumber));
        return this;
    }

    public T setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        setProperty(28, horizontalAlignment);
        return this;
    }

    public T setFont(PdfFont font) {
        setProperty(20, font);
        return this;
    }

    public T setFontFamily(String... fontFamilyNames) {
        setProperty(20, fontFamilyNames);
        return this;
    }

    public T setFontFamily(List<String> fontFamilyNames) {
        return setFontFamily((String[]) fontFamilyNames.toArray(new String[fontFamilyNames.size()]));
    }

    @Deprecated
    public T setFont(String font) {
        setProperty(20, font);
        return this;
    }

    public T setFontColor(Color fontColor) {
        return setFontColor(fontColor, 1.0f);
    }

    public T setFontColor(Color fontColor, float opacity) {
        setProperty(21, fontColor != null ? new TransparentColor(fontColor, opacity) : null);
        return this;
    }

    public T setFontSize(float fontSize) {
        setProperty(24, UnitValue.createPointValue(fontSize));
        return this;
    }

    public T setTextAlignment(TextAlignment alignment) {
        setProperty(70, alignment);
        return this;
    }

    public T setCharacterSpacing(float charSpacing) {
        setProperty(15, Float.valueOf(charSpacing));
        return this;
    }

    public T setWordSpacing(float wordSpacing) {
        setProperty(78, Float.valueOf(wordSpacing));
        return this;
    }

    public T setFontKerning(FontKerning fontKerning) {
        setProperty(22, fontKerning);
        return this;
    }

    public T setBackgroundColor(Color backgroundColor) {
        return setBackgroundColor(backgroundColor, 1.0f);
    }

    public T setBackgroundColor(Color backgroundColor, float opacity) {
        return setBackgroundColor(backgroundColor, opacity, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public T setBackgroundColor(Color backgroundColor, float extraLeft, float extraTop, float extraRight, float extraBottom) {
        return setBackgroundColor(backgroundColor, 1.0f, extraLeft, extraTop, extraRight, extraBottom);
    }

    public T setBackgroundColor(Color backgroundColor, float opacity, float extraLeft, float extraTop, float extraRight, float extraBottom) {
        setProperty(6, backgroundColor != null ? new Background(backgroundColor, opacity, extraLeft, extraTop, extraRight, extraBottom) : null);
        return this;
    }

    public T setBackgroundImage(BackgroundImage image) {
        setProperty(90, image);
        return this;
    }

    public T setBackgroundImage(List<BackgroundImage> imagesList) {
        setProperty(90, imagesList);
        return this;
    }

    public T setBorder(Border border) {
        setProperty(9, border);
        return this;
    }

    public T setBorderTop(Border border) {
        setProperty(13, border);
        return this;
    }

    public T setBorderRight(Border border) {
        setProperty(12, border);
        return this;
    }

    public T setBorderBottom(Border border) {
        setProperty(10, border);
        return this;
    }

    public T setBorderLeft(Border border) {
        setProperty(11, border);
        return this;
    }

    public T setBorderRadius(BorderRadius borderRadius) {
        setProperty(101, borderRadius);
        return this;
    }

    public T setBorderBottomLeftRadius(BorderRadius borderRadius) {
        setProperty(113, borderRadius);
        return this;
    }

    public T setBorderBottomRightRadius(BorderRadius borderRadius) {
        setProperty(112, borderRadius);
        return this;
    }

    public T setBorderTopLeftRadius(BorderRadius borderRadius) {
        setProperty(110, borderRadius);
        return this;
    }

    public T setBorderTopRightRadius(BorderRadius borderRadius) {
        setProperty(111, borderRadius);
        return this;
    }

    public T setSplitCharacters(ISplitCharacters splitCharacters) {
        setProperty(62, splitCharacters);
        return this;
    }

    public ISplitCharacters getSplitCharacters() {
        return (ISplitCharacters) getProperty(62);
    }

    public Integer getTextRenderingMode() {
        return (Integer) getProperty(71);
    }

    public T setTextRenderingMode(int textRenderingMode) {
        setProperty(71, Integer.valueOf(textRenderingMode));
        return this;
    }

    public Color getStrokeColor() {
        return (Color) getProperty(63);
    }

    public T setStrokeColor(Color strokeColor) {
        setProperty(63, strokeColor);
        return this;
    }

    public Float getStrokeWidth() {
        return (Float) getProperty(64);
    }

    public T setStrokeWidth(float strokeWidth) {
        setProperty(64, Float.valueOf(strokeWidth));
        return this;
    }

    public T setBold() {
        setProperty(8, true);
        return this;
    }

    public T setItalic() {
        setProperty(31, true);
        return this;
    }

    public T setLineThrough() {
        return setUnderline((Color) null, 0.75f, 0.0f, 0.0f, 0.29166666f, 0);
    }

    public T setUnderline() {
        return setUnderline((Color) null, 0.75f, 0.0f, 0.0f, -0.125f, 0);
    }

    public T setUnderline(float thickness, float yPosition) {
        return setUnderline((Color) null, thickness, 0.0f, yPosition, 0.0f, 0);
    }

    public T setUnderline(Color color, float thickness, float thicknessMul, float yPosition, float yPositionMul, int lineCapStyle) {
        return setUnderline(color, 1.0f, thickness, thicknessMul, yPosition, yPositionMul, lineCapStyle);
    }

    public T setUnderline(Color color, float opacity, float thickness, float thicknessMul, float yPosition, float yPositionMul, int lineCapStyle) {
        Underline newUnderline = new Underline(color, opacity, thickness, thicknessMul, yPosition, yPositionMul, lineCapStyle);
        Object currentProperty = getProperty(74);
        if (currentProperty instanceof List) {
            ((List) currentProperty).add(newUnderline);
        } else if (currentProperty instanceof Underline) {
            List<Underline> mergedUnderlines = new ArrayList<>();
            mergedUnderlines.add((Underline) currentProperty);
            mergedUnderlines.add(newUnderline);
            setProperty(74, mergedUnderlines);
        } else {
            setProperty(74, newUnderline);
        }
        return this;
    }

    public T setBaseDirection(BaseDirection baseDirection) {
        setProperty(7, baseDirection);
        return this;
    }

    public T setHyphenation(HyphenationConfig hyphenationConfig) {
        setProperty(30, hyphenationConfig);
        return this;
    }

    public T setFontScript(Character.UnicodeScript script) {
        setProperty(23, script);
        return this;
    }

    public T setDestination(String destination) {
        setProperty(17, destination);
        return this;
    }

    public T setOpacity(Float opacity) {
        setProperty(92, opacity);
        return this;
    }
}
