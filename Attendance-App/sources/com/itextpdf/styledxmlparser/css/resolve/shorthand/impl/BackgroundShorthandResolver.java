package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.ShorthandResolverFactory;
import com.itextpdf.styledxmlparser.css.util.CssBackgroundUtils;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.css.validate.CssDeclarationValidationMaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackgroundShorthandResolver implements IShorthandResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger((Class<?>) BackgroundShorthandResolver.class);

    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        if (CssUtils.isInitialOrInheritOrUnset(shorthandExpression)) {
            return Arrays.asList(new CssDeclaration[]{new CssDeclaration(CommonCssConstants.BACKGROUND_COLOR, shorthandExpression), new CssDeclaration(CommonCssConstants.BACKGROUND_IMAGE, shorthandExpression), new CssDeclaration(CommonCssConstants.BACKGROUND_POSITION, shorthandExpression), new CssDeclaration(CommonCssConstants.BACKGROUND_SIZE, shorthandExpression), new CssDeclaration(CommonCssConstants.BACKGROUND_REPEAT, shorthandExpression), new CssDeclaration(CommonCssConstants.BACKGROUND_ORIGIN, shorthandExpression), new CssDeclaration(CommonCssConstants.BACKGROUND_CLIP, shorthandExpression), new CssDeclaration(CommonCssConstants.BACKGROUND_ATTACHMENT, shorthandExpression)});
        } else if (shorthandExpression.trim().isEmpty()) {
            LOGGER.error(MessageFormatUtil.format(LogMessageConstant.SHORTHAND_PROPERTY_CANNOT_BE_EMPTY, CommonCssConstants.BACKGROUND));
            return new ArrayList();
        } else {
            List<List<String>> propsList = CssUtils.extractShorthandProperties(shorthandExpression);
            Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps = new HashMap<>();
            fillMapWithPropertiesTypes(resolvedProps);
            for (List<String> props : propsList) {
                if (!processProperties(props, resolvedProps)) {
                    return new ArrayList();
                }
            }
            if (resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR) == null) {
                resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR, CommonCssConstants.TRANSPARENT);
            }
            if (!checkProperties(resolvedProps)) {
                return new ArrayList();
            }
            return Arrays.asList(new CssDeclaration[]{new CssDeclaration(CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR), resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR)), new CssDeclaration(CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_IMAGE), resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_IMAGE)), new CssDeclaration(CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION), resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION)), new CssDeclaration(CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE), resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE)), new CssDeclaration(CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_REPEAT), resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_REPEAT)), new CssDeclaration(CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN), resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN)), new CssDeclaration(CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP), resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP)), new CssDeclaration(CssBackgroundUtils.getBackgroundPropertyNameFromType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ATTACHMENT), resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ATTACHMENT))});
        }
    }

    private static boolean checkProperties(Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps) {
        for (Map.Entry<CssBackgroundUtils.BackgroundPropertyType, String> property : resolvedProps.entrySet()) {
            if (!CssDeclarationValidationMaster.checkDeclaration(new CssDeclaration(CssBackgroundUtils.getBackgroundPropertyNameFromType(property.getKey()), property.getValue()))) {
                LOGGER.error(MessageFormatUtil.format(LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, property.getValue()));
                return false;
            }
            IShorthandResolver resolver = ShorthandResolverFactory.getShorthandResolver(CssBackgroundUtils.getBackgroundPropertyNameFromType(property.getKey()));
            if (resolver != null && resolver.resolveShorthand(property.getValue()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static void removeSpacesAroundSlash(List<String> props) {
        int i = 0;
        while (i < props.size()) {
            if ("/".equals(props.get(i))) {
                if (i != 0 && i != props.size() - 1) {
                    props.set(i + 1, props.get(i - 1) + props.get(i) + props.get(i + 1));
                    props.remove(i);
                    props.remove(i - 1);
                    return;
                }
                return;
            } else if (props.get(i).startsWith("/")) {
                if (i != 0) {
                    props.set(i, props.get(i - 1) + props.get(i));
                    props.remove(i - 1);
                    return;
                }
                return;
            } else if (!props.get(i).endsWith("/")) {
                i++;
            } else if (i != props.size() - 1) {
                props.set(i + 1, props.get(i) + props.get(i + 1));
                props.remove(i);
                return;
            } else {
                return;
            }
        }
    }

    private static void fillMapWithPropertiesTypes(Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps) {
        resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR, (Object) null);
        resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_IMAGE, (Object) null);
        resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION, (Object) null);
        resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE, (Object) null);
        resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_REPEAT, (Object) null);
        resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN, (Object) null);
        resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP, (Object) null);
        resolvedProps.put(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ATTACHMENT, (Object) null);
    }

    private static boolean processProperties(List<String> props, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps) {
        if (props.isEmpty()) {
            LOGGER.error(MessageFormatUtil.format(LogMessageConstant.SHORTHAND_PROPERTY_CANNOT_BE_EMPTY, CommonCssConstants.BACKGROUND));
            return false;
        } else if (resolvedProps.get(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR) != null) {
            LOGGER.error(LogMessageConstant.ONLY_THE_LAST_BACKGROUND_CAN_INCLUDE_BACKGROUND_COLOR);
            return false;
        } else {
            removeSpacesAroundSlash(props);
            Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes = new HashSet<>();
            if (!processAllSpecifiedProperties(props, resolvedProps, usedTypes)) {
                return false;
            }
            fillNotProcessedProperties(resolvedProps, usedTypes);
            return true;
        }
    }

    private static boolean processAllSpecifiedProperties(List<String> props, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
        List<String> boxValues = new ArrayList<>();
        boolean slashEncountered = false;
        boolean propertyProcessedCorrectly = true;
        for (String value : props) {
            int slashCharInd = value.indexOf(47);
            if (slashCharInd <= 0 || slashCharInd >= value.length() - 1 || slashEncountered || value.contains("url(")) {
                CssBackgroundUtils.BackgroundPropertyType type = CssBackgroundUtils.resolveBackgroundPropertyType(value);
                if (CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN_OR_CLIP == type) {
                    boxValues.add(value);
                    continue;
                } else {
                    propertyProcessedCorrectly = putPropertyBasedOnType(changePropertyType(type, slashEncountered), value, resolvedProps, usedTypes);
                    continue;
                }
            } else {
                slashEncountered = true;
                propertyProcessedCorrectly = processValueWithSlash(value, slashCharInd, resolvedProps, usedTypes);
                continue;
            }
            if (!propertyProcessedCorrectly) {
                return false;
            }
        }
        return addBackgroundClipAndBackgroundOriginBoxValues(boxValues, resolvedProps, usedTypes);
    }

    private static boolean addBackgroundClipAndBackgroundOriginBoxValues(List<String> boxValues, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
        if (boxValues.size() == 1) {
            return putPropertyBasedOnType(CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP, boxValues.get(0), resolvedProps, usedTypes);
        }
        if (boxValues.size() >= 2) {
            int i = 0;
            while (i < 2) {
                if (!putPropertyBasedOnType(i == 0 ? CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_ORIGIN : CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_CLIP, boxValues.get(i), resolvedProps, usedTypes)) {
                    return false;
                }
                i++;
            }
        }
        return true;
    }

    private static boolean processValueWithSlash(String value, int slashCharInd, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
        String value1 = value.substring(0, slashCharInd);
        CssBackgroundUtils.BackgroundPropertyType typeBeforeSlash = changePropertyType(CssBackgroundUtils.resolveBackgroundPropertyType(value1), false);
        if (typeBeforeSlash == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION || typeBeforeSlash == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE) {
            String value2 = value.substring(slashCharInd + 1);
            CssBackgroundUtils.BackgroundPropertyType typeAfterSlash = changePropertyType(CssBackgroundUtils.resolveBackgroundPropertyType(value2), true);
            if (typeAfterSlash != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE && typeAfterSlash != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE) {
                LOGGER.error(MessageFormatUtil.format(LogMessageConstant.UNKNOWN_PROPERTY, CommonCssConstants.BACKGROUND_SIZE, value2));
                return false;
            } else if (!putPropertyBasedOnType(typeBeforeSlash, value1, resolvedProps, usedTypes) || !putPropertyBasedOnType(typeAfterSlash, value2, resolvedProps, usedTypes)) {
                return false;
            } else {
                return true;
            }
        } else {
            LOGGER.error(MessageFormatUtil.format(LogMessageConstant.UNKNOWN_PROPERTY, CommonCssConstants.BACKGROUND_POSITION, value1));
            return false;
        }
    }

    private static void fillNotProcessedProperties(Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
        Iterator it = new ArrayList(resolvedProps.keySet()).iterator();
        while (it.hasNext()) {
            CssBackgroundUtils.BackgroundPropertyType type = (CssBackgroundUtils.BackgroundPropertyType) it.next();
            if (!usedTypes.contains(type) && type != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_COLOR) {
                if (resolvedProps.get(type) == null) {
                    resolvedProps.put(type, CssDefaults.getDefaultValue(CssBackgroundUtils.getBackgroundPropertyNameFromType(type)));
                } else {
                    resolvedProps.put(type, resolvedProps.get(type) + "," + CssDefaults.getDefaultValue(CssBackgroundUtils.getBackgroundPropertyNameFromType(type)));
                }
            }
        }
    }

    private static CssBackgroundUtils.BackgroundPropertyType changePropertyType(CssBackgroundUtils.BackgroundPropertyType propertyType, boolean slashEncountered) {
        if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_X || propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_Y) {
            propertyType = CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION;
        }
        if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION_OR_SIZE) {
            return slashEncountered ? CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE : CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION;
        }
        if (propertyType == CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_SIZE && !slashEncountered) {
            return CssBackgroundUtils.BackgroundPropertyType.UNDEFINED;
        }
        if (propertyType != CssBackgroundUtils.BackgroundPropertyType.BACKGROUND_POSITION || !slashEncountered) {
            return propertyType;
        }
        return CssBackgroundUtils.BackgroundPropertyType.UNDEFINED;
    }

    private static boolean putPropertyBasedOnType(CssBackgroundUtils.BackgroundPropertyType type, String value, Map<CssBackgroundUtils.BackgroundPropertyType, String> resolvedProps, Set<CssBackgroundUtils.BackgroundPropertyType> usedTypes) {
        if (type == CssBackgroundUtils.BackgroundPropertyType.UNDEFINED) {
            LOGGER.error(MessageFormatUtil.format(LogMessageConstant.WAS_NOT_ABLE_TO_DEFINE_BACKGROUND_CSS_SHORTHAND_PROPERTIES, value));
            return false;
        }
        if (resolvedProps.get(type) == null) {
            resolvedProps.put(type, value);
        } else if (usedTypes.contains(type)) {
            resolvedProps.put(type, resolvedProps.get(type) + " " + value);
        } else {
            resolvedProps.put(type, resolvedProps.get(type) + "," + value);
        }
        usedTypes.add(type);
        return true;
    }
}
