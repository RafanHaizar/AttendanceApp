package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;

public abstract class AbstractBoxShorthandResolver implements IShorthandResolver {
    private static final String _0_BOTTOM_1 = "{0}-bottom{1}";
    private static final String _0_LEFT_1 = "{0}-left{1}";
    private static final String _0_RIGHT_1 = "{0}-right{1}";
    private static final String _0_TOP_1 = "{0}-top{1}";

    /* access modifiers changed from: protected */
    public abstract String getPostfix();

    /* access modifiers changed from: protected */
    public abstract String getPrefix();

    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        String[] props = shorthandExpression.split("\\s+");
        List<CssDeclaration> resolvedDecl = new ArrayList<>();
        String topProperty = MessageFormatUtil.format(_0_TOP_1, getPrefix(), getPostfix());
        String rightProperty = MessageFormatUtil.format(_0_RIGHT_1, getPrefix(), getPostfix());
        String bottomProperty = MessageFormatUtil.format(_0_BOTTOM_1, getPrefix(), getPostfix());
        String leftProperty = MessageFormatUtil.format(_0_LEFT_1, getPrefix(), getPostfix());
        if (props.length == 1) {
            resolvedDecl.add(new CssDeclaration(topProperty, props[0]));
            resolvedDecl.add(new CssDeclaration(rightProperty, props[0]));
            resolvedDecl.add(new CssDeclaration(bottomProperty, props[0]));
            resolvedDecl.add(new CssDeclaration(leftProperty, props[0]));
        } else {
            for (String prop : props) {
                if (CommonCssConstants.INHERIT.equals(prop) || CommonCssConstants.INITIAL.equals(prop)) {
                    LoggerFactory.getLogger((Class<?>) AbstractBoxShorthandResolver.class).warn(MessageFormatUtil.format(LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, shorthandExpression));
                    return Collections.emptyList();
                }
            }
            if (props.length == 2) {
                resolvedDecl.add(new CssDeclaration(topProperty, props[0]));
                resolvedDecl.add(new CssDeclaration(rightProperty, props[1]));
                resolvedDecl.add(new CssDeclaration(bottomProperty, props[0]));
                resolvedDecl.add(new CssDeclaration(leftProperty, props[1]));
            } else if (props.length == 3) {
                resolvedDecl.add(new CssDeclaration(topProperty, props[0]));
                resolvedDecl.add(new CssDeclaration(rightProperty, props[1]));
                resolvedDecl.add(new CssDeclaration(bottomProperty, props[2]));
                resolvedDecl.add(new CssDeclaration(leftProperty, props[1]));
            } else if (props.length == 4) {
                resolvedDecl.add(new CssDeclaration(topProperty, props[0]));
                resolvedDecl.add(new CssDeclaration(rightProperty, props[1]));
                resolvedDecl.add(new CssDeclaration(bottomProperty, props[2]));
                resolvedDecl.add(new CssDeclaration(leftProperty, props[3]));
            }
        }
        return resolvedDecl;
    }
}
