package com.itextpdf.styledxmlparser.css.resolve.shorthand.impl;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.resolve.shorthand.IShorthandResolver;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCornersShorthandResolver implements IShorthandResolver {
    private static final String _0_BOTTOM_LEFT_1 = "{0}-bottom-left{1}";
    private static final String _0_BOTTOM_RIGHT_1 = "{0}-bottom-right{1}";
    private static final String _0_TOP_LEFT_1 = "{0}-top-left{1}";
    private static final String _0_TOP_RIGHT_1 = "{0}-top-right{1}";

    /* access modifiers changed from: protected */
    public abstract String getPostfix();

    /* access modifiers changed from: protected */
    public abstract String getPrefix();

    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        String[] props = shorthandExpression.split("\\s*\\/\\s*");
        String[][] properties = new String[props.length][];
        for (int i = 0; i < props.length; i++) {
            properties[i] = props[i].split("\\s+");
        }
        String[] resultExpressions = new String[4];
        for (int i2 = 0; i2 < resultExpressions.length; i2++) {
            resultExpressions[i2] = "";
        }
        List<CssDeclaration> resolvedDecl = new ArrayList<>();
        int i3 = 1;
        String topLeftProperty = MessageFormatUtil.format(_0_TOP_LEFT_1, getPrefix(), getPostfix());
        String topRightProperty = MessageFormatUtil.format(_0_TOP_RIGHT_1, getPrefix(), getPostfix());
        String bottomRightProperty = MessageFormatUtil.format(_0_BOTTOM_RIGHT_1, getPrefix(), getPostfix());
        String bottomLeftProperty = MessageFormatUtil.format(_0_BOTTOM_LEFT_1, getPrefix(), getPostfix());
        int i4 = 0;
        while (i4 < properties.length) {
            if (properties[i4].length == i3) {
                resultExpressions[0] = resultExpressions[0] + properties[i4][0] + " ";
                resultExpressions[i3] = resultExpressions[i3] + properties[i4][0] + " ";
                resultExpressions[2] = resultExpressions[2] + properties[i4][0] + " ";
                resultExpressions[3] = resultExpressions[3] + properties[i4][0] + " ";
            } else if (properties[i4].length == 2) {
                resultExpressions[0] = resultExpressions[0] + properties[i4][0] + " ";
                resultExpressions[1] = resultExpressions[1] + properties[i4][1] + " ";
                resultExpressions[2] = resultExpressions[2] + properties[i4][0] + " ";
                resultExpressions[3] = resultExpressions[3] + properties[i4][1] + " ";
            } else if (properties[i4].length == 3) {
                resultExpressions[0] = resultExpressions[0] + properties[i4][0] + " ";
                resultExpressions[1] = resultExpressions[1] + properties[i4][1] + " ";
                resultExpressions[2] = resultExpressions[2] + properties[i4][2] + " ";
                resultExpressions[3] = resultExpressions[3] + properties[i4][1] + " ";
            } else if (properties[i4].length == 4) {
                resultExpressions[0] = resultExpressions[0] + properties[i4][0] + " ";
                resultExpressions[1] = resultExpressions[1] + properties[i4][1] + " ";
                resultExpressions[2] = resultExpressions[2] + properties[i4][2] + " ";
                resultExpressions[3] = resultExpressions[3] + properties[i4][3] + " ";
            }
            i4++;
            i3 = 1;
        }
        resolvedDecl.add(new CssDeclaration(topLeftProperty, resultExpressions[0]));
        resolvedDecl.add(new CssDeclaration(topRightProperty, resultExpressions[1]));
        resolvedDecl.add(new CssDeclaration(bottomRightProperty, resultExpressions[2]));
        resolvedDecl.add(new CssDeclaration(bottomLeftProperty, resultExpressions[3]));
        return resolvedDecl;
    }
}
