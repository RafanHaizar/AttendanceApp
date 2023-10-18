package com.itextpdf.svg.css.impl;

import com.itextpdf.styledxmlparser.css.resolve.IStyleInheritance;
import com.itextpdf.svg.SvgConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SvgAttributeInheritance implements IStyleInheritance {
    private static final Set<String> inheritableProperties = new HashSet(Arrays.asList(new String[]{SvgConstants.Attributes.STROKE, SvgConstants.Attributes.FILL, SvgConstants.Attributes.FILL_RULE, SvgConstants.Attributes.CLIP_RULE, SvgConstants.Attributes.TEXT_ANCHOR}));

    public boolean isInheritable(String cssProperty) {
        return inheritableProperties.contains(cssProperty);
    }
}
