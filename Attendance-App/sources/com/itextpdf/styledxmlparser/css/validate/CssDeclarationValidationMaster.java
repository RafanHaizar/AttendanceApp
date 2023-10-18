package com.itextpdf.styledxmlparser.css.validate;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.validate.impl.datatype.ArrayDataTypeValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssBackgroundValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssBlendModeValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssColorValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssEnumValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssNumericValueValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssQuotesValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.datatype.CssTransformValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.declaration.MultiTypeDeclarationValidator;
import com.itextpdf.styledxmlparser.css.validate.impl.declaration.SingleTypeDeclarationValidator;
import java.util.HashMap;
import java.util.Map;

public class CssDeclarationValidationMaster {
    private static final Map<String, ICssDeclarationValidator> DEFAULT_VALIDATORS;

    static {
        ICssDeclarationValidator colorCommonValidator = new MultiTypeDeclarationValidator(new CssEnumValidator(CommonCssConstants.TRANSPARENT, CommonCssConstants.INITIAL, CommonCssConstants.INHERIT, CommonCssConstants.CURRENTCOLOR), new CssColorValidator());
        HashMap hashMap = new HashMap();
        DEFAULT_VALIDATORS = hashMap;
        hashMap.put(CommonCssConstants.BACKGROUND_COLOR, colorCommonValidator);
        hashMap.put("color", colorCommonValidator);
        hashMap.put(CommonCssConstants.BORDER_COLOR, colorCommonValidator);
        hashMap.put(CommonCssConstants.BORDER_BOTTOM_COLOR, colorCommonValidator);
        hashMap.put(CommonCssConstants.BORDER_TOP_COLOR, colorCommonValidator);
        hashMap.put(CommonCssConstants.BORDER_LEFT_COLOR, colorCommonValidator);
        hashMap.put(CommonCssConstants.BORDER_RIGHT_COLOR, colorCommonValidator);
        hashMap.put("float", new SingleTypeDeclarationValidator(new CssEnumValidator("left", "right", "none", CommonCssConstants.INHERIT, CommonCssConstants.CENTER)));
        hashMap.put(CommonCssConstants.PAGE_BREAK_BEFORE, new SingleTypeDeclarationValidator(new CssEnumValidator("auto", CommonCssConstants.ALWAYS, CommonCssConstants.AVOID, "left", "right")));
        hashMap.put(CommonCssConstants.PAGE_BREAK_AFTER, new SingleTypeDeclarationValidator(new CssEnumValidator("auto", CommonCssConstants.ALWAYS, CommonCssConstants.AVOID, "left", "right")));
        hashMap.put(CommonCssConstants.QUOTES, new MultiTypeDeclarationValidator(new CssEnumValidator(CommonCssConstants.INITIAL, CommonCssConstants.INHERIT, "none"), new CssQuotesValidator()));
        hashMap.put("transform", new SingleTypeDeclarationValidator(new CssTransformValidator()));
        CssEnumValidator enumValidator = new CssEnumValidator(CommonCssConstants.LARGER, CommonCssConstants.SMALLER);
        enumValidator.addAllowedValues(CommonCssConstants.FONT_ABSOLUTE_SIZE_KEYWORDS_VALUES.keySet());
        hashMap.put("font-size", new MultiTypeDeclarationValidator(new CssNumericValueValidator(true, false), enumValidator));
        hashMap.put(CommonCssConstants.WORD_SPACING, new SingleTypeDeclarationValidator(new CssNumericValueValidator(false, true)));
        hashMap.put(CommonCssConstants.LETTER_SPACING, new SingleTypeDeclarationValidator(new CssNumericValueValidator(false, true)));
        hashMap.put(CommonCssConstants.TEXT_INDENT, new SingleTypeDeclarationValidator(new CssNumericValueValidator(true, false)));
        hashMap.put(CommonCssConstants.LINE_HEIGHT, new SingleTypeDeclarationValidator(new CssNumericValueValidator(true, true)));
        hashMap.put(CommonCssConstants.BACKGROUND_REPEAT, new SingleTypeDeclarationValidator(new CssBackgroundValidator(CommonCssConstants.BACKGROUND_REPEAT)));
        hashMap.put(CommonCssConstants.BACKGROUND_IMAGE, new SingleTypeDeclarationValidator(new CssBackgroundValidator(CommonCssConstants.BACKGROUND_IMAGE)));
        hashMap.put(CommonCssConstants.BACKGROUND_POSITION_X, new SingleTypeDeclarationValidator(new CssBackgroundValidator(CommonCssConstants.BACKGROUND_POSITION_X)));
        hashMap.put(CommonCssConstants.BACKGROUND_POSITION_Y, new SingleTypeDeclarationValidator(new CssBackgroundValidator(CommonCssConstants.BACKGROUND_POSITION_Y)));
        hashMap.put(CommonCssConstants.BACKGROUND_SIZE, new SingleTypeDeclarationValidator(new CssBackgroundValidator(CommonCssConstants.BACKGROUND_SIZE)));
        hashMap.put(CommonCssConstants.BACKGROUND_CLIP, new SingleTypeDeclarationValidator(new CssBackgroundValidator(CommonCssConstants.BACKGROUND_CLIP)));
        hashMap.put(CommonCssConstants.BACKGROUND_ORIGIN, new SingleTypeDeclarationValidator(new CssBackgroundValidator(CommonCssConstants.BACKGROUND_ORIGIN)));
        hashMap.put(CommonCssConstants.BACKGROUND_BLEND_MODE, new SingleTypeDeclarationValidator(new ArrayDataTypeValidator(new CssBlendModeValidator())));
    }

    private CssDeclarationValidationMaster() {
    }

    public static boolean checkDeclaration(CssDeclaration declaration) {
        ICssDeclarationValidator validator = DEFAULT_VALIDATORS.get(declaration.getProperty());
        return validator == null || validator.isValid(declaration);
    }
}
