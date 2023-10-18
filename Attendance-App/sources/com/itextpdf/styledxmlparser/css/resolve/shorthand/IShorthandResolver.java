package com.itextpdf.styledxmlparser.css.resolve.shorthand;

import com.itextpdf.styledxmlparser.css.CssDeclaration;
import java.util.List;

public interface IShorthandResolver {
    List<CssDeclaration> resolveShorthand(String str);
}
