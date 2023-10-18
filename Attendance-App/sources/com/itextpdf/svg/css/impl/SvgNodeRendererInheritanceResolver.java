package com.itextpdf.svg.css.impl;

import com.itextpdf.styledxmlparser.css.resolve.CssInheritance;
import com.itextpdf.styledxmlparser.css.resolve.IStyleInheritance;
import com.itextpdf.styledxmlparser.util.StyleUtil;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.AbstractBranchSvgNodeRenderer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SvgNodeRendererInheritanceResolver {
    public void applyInheritanceToSubTree(ISvgNodeRenderer root, ISvgNodeRenderer subTree) {
        applyStyles(root, subTree);
        if (subTree instanceof AbstractBranchSvgNodeRenderer) {
            AbstractBranchSvgNodeRenderer subTreeAsBranch = (AbstractBranchSvgNodeRenderer) subTree;
            for (ISvgNodeRenderer child : subTreeAsBranch.getChildren()) {
                applyInheritanceToSubTree(subTreeAsBranch, child);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void applyStyles(ISvgNodeRenderer parent, ISvgNodeRenderer child) {
        if (parent != null && child != null) {
            Map<String, String> childStyles = child.getAttributeMapCopy();
            if (childStyles == null) {
                childStyles = new HashMap<>();
            }
            Map<String, String> parentStyles = parent.getAttributeMapCopy();
            String parentFontSize = parent.getAttribute("font-size");
            if (parentFontSize == null) {
                parentFontSize = "0";
            }
            Set<IStyleInheritance> inheritanceRules = new HashSet<>();
            inheritanceRules.add(new CssInheritance());
            inheritanceRules.add(new SvgAttributeInheritance());
            for (Map.Entry<String, String> parentAttribute : parentStyles.entrySet()) {
                childStyles = StyleUtil.mergeParentStyleDeclaration(childStyles, parentAttribute.getKey(), parentAttribute.getValue(), parentFontSize, inheritanceRules);
            }
            child.setAttributesAndStyles(childStyles);
        }
    }
}
