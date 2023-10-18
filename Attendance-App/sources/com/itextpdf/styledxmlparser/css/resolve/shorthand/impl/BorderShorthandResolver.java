package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.ShorthandResolverFactory;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

public class BorderShorthandResolver extends AbstractBorderShorthandResolver {
    /* access modifiers changed from: protected */
    public String getPrefix() {
        return CommonCssConstants.BORDER;
    }

    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        List<CssDeclaration> preResolvedProps = super.resolveShorthand(shorthandExpression);
        List<CssDeclaration> resolvedProps = new ArrayList<>();
        for (CssDeclaration prop : preResolvedProps) {
            IShorthandResolver shorthandResolver = ShorthandResolverFactory.getShorthandResolver(prop.getProperty());
            if (shorthandResolver != null) {
                resolvedProps.addAll(shorthandResolver.resolveShorthand(prop.getExpression()));
            } else {
                LoggerFactory.getLogger((Class<?>) BorderShorthandResolver.class).error(MessageFormatUtil.format("Cannot find a shorthand resolver for the \"{0}\" property. Expected border-width, border-style or border-color properties.", prop.getProperty()));
            }
        }
        return resolvedProps;
    }
}
