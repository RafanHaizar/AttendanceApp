package com.itextpdf.styledxmlparser.css;

import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.Collections;
import java.util.List;

public abstract class CssStatement {
    public List<CssRuleSet> getCssRuleSets(INode node, MediaDeviceDescription deviceDescription) {
        return Collections.emptyList();
    }
}
